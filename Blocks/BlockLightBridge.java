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

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasic;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightBridge extends BlockBasic {

	public BlockLightBridge() {
		super(Material.portal);	//immovable
		this.setLightLevel(0.5F);
		this.setBlockUnbreakable();
		this.setResistance(3600000F);
		this.setStepSound(soundTypeGlass);	//Custom sound from Portal 2?
		////this.requiresSelfNotify[this.blockID] = true;

		//this.blockIndexInTexture = 61;
	}

	@Override
	protected boolean isAvailableInCreativeMode() {
		return false;
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
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public final ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return false;
	}

	public int getBlockTextureFromSideAndMetadata(int s, int meta) {
		if (s > 1)
			return 0;//this.blockIndexInTexture+2;
		else
			return 0;//this.blockIndexInTexture+meta;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15175F, 1.0F);
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block id)
	{
		//if (id != blockID && id != 0)
		//	world.setBlock(x, y, z, 0, 0, 2);
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		return icons[meta][s];
	}
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		for (int i = 0; i < 2; i++)
			icons[0][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_1");
		for (int i = 0; i < 2; i++)
			icons[1][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_2");
		for (int i = 0; i < 2; i++)
			icons[2][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_3");
		for (int i = 0; i < 2; i++)
			icons[3][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_4");
		for (int i = 2; i < 6; i++) {
			icons[0][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_side");
			icons[1][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_side");
			icons[2][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_side");
			icons[3][i] = par1IconRegister.registerIcon("RotaryCraft:bridge_side");
		}
	}
}