/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityHydroEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;

public class BlockEngine extends BlockModelledMachine {

	public BlockEngine(Material mat) {
		super(mat);
		//this.blockIndexInTexture = 14;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int side, float par7, float par8, float par9) {
		if (RotaryCraft.instance.isLocked())
			return false;
		TileEntity te = world.getTileEntity(x, y, z);

		ItemStack is = ep.getCurrentEquippedItem();
		if (te instanceof TileEntityEngine) {
			if (is != null && is.getItem() == ItemRegistry.FUEL.getItemInstance())
				return false;
			TileEntityEngine tile = (TileEntityEngine)te;
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.turbine)) {
				if (tile.getEngineType() == EngineType.JET && ((TileEntityJetEngine)tile).FOD > 0) {
					((TileEntityJetEngine)tile).repairJet();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.compressor)) {
				if (tile.getEngineType() == EngineType.JET && ((TileEntityJetEngine)tile).FOD > 0) {
					((TileEntityJetEngine)tile).repairJetPartial();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && ReikaItemHelper.matchStacks(is, ItemStacks.bedrockshaft)) {
				if (tile.getEngineType() == EngineType.HYDRO && !((TileEntityHydroEngine)tile).isBedrock()) {
					((TileEntityHydroEngine)tile).makeBedrock();
					if (!ep.capabilities.isCreativeMode)
						--is.stackSize;
					return true;
				}
			}
			if (is != null && is.stackSize == 1) {
				if (is.getItem() == Items.bucket) {
					if (tile.getEngineType().isEthanolFueled()) {
						if (tile.getFuelLevel() >= 1000) {
							ep.setCurrentItemOrArmor(0, ItemStacks.ethanolbucket.copy());
							tile.subtractFuel(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
					if (tile.getEngineType().isJetFueled()) {
						if (tile.getFuelLevel() >= 1000) {
							ep.setCurrentItemOrArmor(0, ItemStacks.fuelbucket.copy());
							tile.subtractFuel(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
					if (tile.getEngineType().requiresLubricant()) {
						if (tile.getLube() >= 1000) {
							ep.setCurrentItemOrArmor(0, ItemStacks.lubebucket.copy());
							tile.removeLubricant(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine does not have enough fuel to extract!");
						}
						return true;
					}
				}
				if (tile.getEngineType().isJetFueled()) {
					if (ReikaItemHelper.matchStacks(is, ItemStacks.fuelbucket)) {
						if (tile.getFuelLevel() <= tile.FUELCAP-1000) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
							tile.addFuel(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add fuel!");
						}
						return true;
					}
				}
				if (tile.getEngineType().isEthanolFueled()) {
					if (ReikaItemHelper.matchStacks(is, ItemStacks.ethanolbucket)) {
						if (tile.getFuelLevel() <= tile.FUELCAP-1000) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
							tile.addFuel(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add fuel!");
						}
						return true;
					}
				}
				if (tile.getEngineType().requiresLubricant()) {
					if (ReikaItemHelper.matchStacks(is, ItemStacks.lubebucket)) {
						if (tile.getLube() <= tile.LUBECAP-1000) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
							tile.addLubricant(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add lubricant!");
						}
						return true;
					}
				}
				if (tile.getEngineType().needsWater()) {
					if (is != null && is.getItem() == Items.water_bucket) {
						if (tile.getWater() <= tile.CAPACITY-1000) {
							if (!ep.capabilities.isCreativeMode)
								ep.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
							tile.addWater(1000);
						}
						else {
							if (ConfigRegistry.CLEARCHAT.getState())
								ReikaChatHelper.clearChat();
							ReikaChatHelper.write("Engine is too full to add water!");
						}
						return true;
					}
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, ep, side, par7, par8, par9);
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
						if (todrop.stackTagCompound == null)
							todrop.stackTagCompound = new NBTTagCompound();
						todrop.stackTagCompound.setInteger("damage", tj.FOD);
					}
				}
				else if (eng.getEngineType() == EngineType.HYDRO) {
					if (todrop.stackTagCompound == null)
						todrop.stackTagCompound = new NBTTagCompound();
					todrop.stackTagCompound.setBoolean("bed", ((TileEntityHydroEngine)eng).isBedrock());
				}
				if (eng.isUnHarvestable()) {
					todrop = ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 2+par5Random.nextInt(12));
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
	public RotaryCraftTileEntity createTileEntity(World world, int meta) {
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
