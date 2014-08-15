/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEngine extends BlockModelledMachine {

	public BlockEngine(Material mat) {
		super(mat);
		//this.blockIndexInTexture = 14;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;

		TileEntityEngine tile = (TileEntityEngine)par1IBlockAccess.getTileEntity(x, y, z);
		if (tile == null)
			return;

		switch(tile.getEngineType()) {
		case DC:
			maxy -= 0.1875F;
			break;/*
		case WIND:
			maxy = 1.5F;
			miny = -0.5F;
			switch(tile.getBlockMetadata()) {
			case 0:
				minz = -0.5F;
				maxz = 1.5F;
				maxx = 1.1875F;
			break;
			case 1:
				minz = -0.5F;
				maxz = 1.5F;
				minx = -0.1875F;
			break;
			case 2:
				maxx = 1.5F;
				minx = -0.5F;
				maxz = 1.1875F;
			break;
			case 3:
				maxx = 1.5F;
				minx = -0.5F;
				minz = -0.1875F;
			break;
			}
			break;*/
		case STEAM:
			maxy -= 0.125F;
			break;
		case GAS:
			maxy -= 0.0625F;
			break;/*
		case HYDRO:
			maxy = 1.5F;
			miny = -0.5F;
			if (tile.getBlockMetadata() < 2) {
				minz = -0.5F;
				maxz = 1.5F;
			}
			else {
				maxx = 1.5F;
				minx = -0.5F;
			}
			break;*/
		case MICRO:
			maxy -= 0.125F;
			break;
		case JET:
			maxy -= 0.125F;
			break;
		default:
			break;
		}

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harv)
	{
		if (this.canHarvest(world, player, x, y, z))
			this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlockToAir(x, y, z);
	}

	private boolean canHarvest(World world, EntityPlayer ep, int x, int y, int z) {
		return RotaryAux.canHarvestSteelMachine(ep);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntityEngine eng = (TileEntityEngine)world.getTileEntity(x, y, z);
		if (eng != null) {
			if (eng.getEngineType() == EngineType.JET && ((TileEntityJetEngine)eng).FOD >= 8) {
				ItemStack todrop = ReikaItemHelper.getSizedItemStack(ItemStacks.steelgear, 1+par5Random.nextInt(5));	//drop gears
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote)
					world.spawnEntityInWorld(item);
				todrop = ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 16+par5Random.nextInt(17));	//drop scrap
				item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
			}
			else {
				ItemStack todrop = eng.getEngineType().getCraftedProduct(); //drop engine item
				if (eng.getEngineType() == EngineType.JET) {
					TileEntityJetEngine tj = (TileEntityJetEngine)eng;
					if (tj.FOD > 0) {
						todrop.stackTagCompound = new NBTTagCompound();
						todrop.stackTagCompound.setInteger("damage", tj.FOD);
					}
				}
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
				for (int i = 0; i < eng.getSizeInventory(); i++) {
					if (eng.getStackInSlot(i) != null) {
						todrop = eng.getStackInSlot(i);
						item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
						item.delayBeforeCanPickup = 10;
						if (!world.isRemote && !ep.capabilities.isCreativeMode)
							world.spawnEntityInWorld(item);
					}
				}
			}
		}
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return EngineType.engineList[meta].newTileEntity();
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntityEngine te = (TileEntityEngine)world.getTileEntity(x, y, z);
		if (te != null) {
			te.temperature = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		}
	}

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntityEngine tile = (TileEntityEngine)world.getTileEntity(x, y, z);
		if (tile == null)
			return ret;
		ItemStack is = tile.getEngineType().getCraftedProduct();
		ret.add(is);
		if (tile.getEngineType() == EngineType.JET) {
			TileEntityJetEngine tj = (TileEntityJetEngine)tile;
			is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setInteger("damage", tj.FOD);
		}
		return ret;
	}
}