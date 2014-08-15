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

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedrockSlice extends BlockBasic
{
	private boolean last = false;
	public static IIcon icon;

	public BlockBedrockSlice()
	{
		super(Material.rock);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setBlockUnbreakable();
		this.setResistance(3600000F);
	}

	@Override
	protected boolean isAvailableInCreativeMode() {
		return false;
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity e)
	{
		return false;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int var5 = world.getBlockMetadata(x, y, z);
		if (var5 != 0)
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + (15-var5)/16F, z + maxZ);
		else
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(x, y, z);
		float var6 = 1F-(1 * (var5)) / 16.0F; //Get thinner each damageval++
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this Blocks.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block var5 = world.getBlock(x, y - 1, z);
		return var5 != Blocks.air && (var5 == Blocks.leaves || var5 == Blocks.leaves2 || var5.isOpaqueCube()) ? ReikaWorldHelper.getMaterial(world, x, y - 1, z).blocksMovement() : false;
	}

	@Override
	public Item getItemDropped(int par1, Random xRandom, int y)
	{
		return null;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}


	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int x, int y, int z, int par5)
	{
		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, x, y, z, par5);
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		icon = par1IconRegister.registerIcon("bedrock");
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 0;
	}
}