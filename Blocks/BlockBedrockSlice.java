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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;

public class BlockBedrockSlice extends Block
{
	private boolean last = false;
	public static Icon icon;

	public BlockBedrockSlice(int par1)
	{
		super(par1, Material.rock);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setBlockUnbreakable();
		this.setResistance(3600000F);
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z, Entity e)
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
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		int var5 = world.getBlockId(x, y - 1, z);
		return var5 != 0 && (var5 == Block.leaves.blockID || Block.blocksList[var5].isOpaqueCube()) ? world.getBlockMaterial(x, y - 1, z).blocksMovement() : false;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random xRandom, int y)
	{
		return 0;
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
	public Icon getIcon(int s, int meta) {
		return icon;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		icon = par1IconRegister.registerIcon("bedrock");
	}
}
