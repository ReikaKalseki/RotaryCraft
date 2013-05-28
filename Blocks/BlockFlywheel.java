/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.mod_RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlywheel extends BlockModelledMachine {

	public BlockFlywheel(int blockID, Material mat) {
		super(blockID, mat);
		//this.blockIndexInTexture = 23;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < 16; ++var4)
			if (var4%4 == 0)
				par3List.add(new ItemStack(par1, 1, var4));
	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityFlywheel();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int a, int b) {
		TileEntityFlywheel fly = (TileEntityFlywheel)world.getBlockTileEntity(x, y, z);
		if (fly != null) {
			if (fly.failed) {
				ItemStack todrop = new ItemStack(ItemStacks.mount.itemID, 1, ItemStacks.mount.getItemDamage());	//drop mount
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote)
					world.spawnEntityInWorld(item);
			}
			else {
				int meta = fly.getBlockMetadata();
				ItemStack todrop = new ItemStack(mod_RotaryCraft.flywheelitems.itemID, 1, meta/4); //drop flywheel
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote)
					world.spawnEntityInWorld(item);
			}
		}
		super.breakBlock(world, x, y, z, a, b);
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving par5EntityLiving, ItemStack is)		//Directional code
	{
		int prevmeta = world.getBlockMetadata(x, y, z);
		int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
		while (i > 3)
			i -= 4;
		while (i < 0)
			i += 4;

		switch (i) {
		case 0:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+3);
			break;
		case 1:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+0);
			break;
		case 2:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+2);
			break;
		case 3:
			ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, prevmeta+1);
			break;
		}
	}
}
