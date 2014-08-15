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

import Reika.RotaryCraft.Base.BlockBasic;
import Reika.RotaryCraft.Registry.ConfigRegistry;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBeam extends BlockBasic {

	public BlockBeam() {
		super(Material.circuits);	// no pistons, breaks with water
		this.setResistance(3600000F);
		this.setStepSound(soundTypeGlass);
		this.setLightLevel(1F);
	}

	@Override
	protected boolean isAvailableInCreativeMode() {
		return false;
	}

	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
	{
		this.setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 1F, 0.75F);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;
	}

	@Override
	public int damageDropped(int par1)
	{
		return 0;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public int getRenderBlockPass()
	{
		return 0;
	}


	public int getBlockTextureFromSideAndMetadata(int s, int dmg) {
		// We want the texture next to our default texture from this block for the bottom and top side
		// so we just add 1 when the side is 0 or 1 else we return the default one

		if (s == 1 || s == 0)
			return 33;
		return 0;//this.blockIndexInTexture;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}


	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block id)
	{
		if (id != Blocks.air) {
			world.setBlockToAir(x, y, z);
			/*
    		boolean px = true;
    		boolean py = true;
    		boolean pz = true;
    		boolean nx = true;
    		boolean ny = true;
    		boolean nz = true;*/
			int i = 1;
			//(px || py || pz || nx || ny || nz) &&
			while (i <= Math.max(64, ConfigRegistry.FLOODLIGHTRANGE.getValue())) {
				//if (px)
				world.notifyBlocksOfNeighborChange(x + i, y, z, Blocks.air);
				//if (nx)
				world.notifyBlocksOfNeighborChange(x - i, y, z, Blocks.air);
				//if (nz)
				world.notifyBlocksOfNeighborChange(x, y, z - i, Blocks.air);
				//if (pz)
				world.notifyBlocksOfNeighborChange(x, y, z + i, Blocks.air);
				//if (ny)
				world.notifyBlocksOfNeighborChange(x, y - i, z, Blocks.air);
				//if (py)
				world.notifyBlocksOfNeighborChange(x, y + i, z, Blocks.air);
				i += 16;
			}
			world.notifyBlocksOfNeighborChange(x, y, z, Blocks.air);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random par5) {
		world.setBlockToAir(x, y, z);
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
	 */
	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		return icons[0][s];
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		for (int i = 2; i < 6; i++)
			icons[0][i] = par1IconRegister.registerIcon("RotaryCraft:beam");
		icons[0][0] = par1IconRegister.registerIcon("RotaryCraft:trans");
		icons[0][1] = par1IconRegister.registerIcon("RotaryCraft:trans");
	}
}