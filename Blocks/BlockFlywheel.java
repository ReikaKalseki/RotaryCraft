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

import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlywheel extends BlockModelledMachine {

	public BlockFlywheel(Material mat) {
		super(mat);
		//this.blockIndexInTexture = 23;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < 16; ++var4)
			if (var4%4 == 0)
				par3List.add(new ItemStack(par1, 1, var4));
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityFlywheel();
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
		TileEntityFlywheel fly = (TileEntityFlywheel)world.getTileEntity(x, y, z);
		if (fly != null) {
			if (fly.failed) {
				ItemStack todrop = ItemStacks.mount.copy();	//drop mount
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote)
					world.spawnEntityInWorld(item);
			}
			else {
				int metadata = fly.getBlockMetadata();
				ItemStack todrop = ItemRegistry.FLYWHEEL.getStackOfMetadata(metadata/4); //drop flywheel
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote)
					world.spawnEntityInWorld(item);
			}
		}
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

		maxy -= 0.125F;

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack is)		//Directional code
	{
		int prevmeta = world.getBlockMetadata(x, y, z);
		int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;

		switch (i) {
		case 0:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+3, 3);
			break;
		case 1:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+0, 3);
			break;
		case 2:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+2, 3);
			break;
		case 3:
			world.setBlockMetadataWithNotify(x, y, z, prevmeta+1, 3);
			break;
		}
	}

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(ItemRegistry.FLYWHEEL.getStackOfMetadata(metadata/4));
		return ret;
	}
}