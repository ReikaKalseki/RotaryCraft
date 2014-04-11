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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDeco extends BlockBasic {

	public BlockDeco(int ID) {
		super(ID, Material.iron);
		this.setHardness(4F);
		this.setResistance(10F);
		this.setLightValue(0F);
		this.setStepSound(soundMetalFootstep);

		//this.blockIndexInTexture = 29;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < RotaryNames.blockNames.length; ++var4)
			par3List.add(new ItemStack(par1, 1, var4));
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return blockID;
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(RotaryCraft.decoblock, 1, metadata));
		return ret;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch(meta) {
		case 0:
			return 29;
		case 1:
			return 75;
		case 2:
			return 76;
		default:
			return 0;
		}
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		if (metadata != ItemStacks.anthrablock.getItemDamage())
			return 0;
		return 30;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[meta][0];
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		if (RotaryCraft.instance.isLocked())
			return;
		icons[0][0] = par1IconRegister.registerIcon("RotaryCraft:steel");
		icons[1][0] = par1IconRegister.registerIcon("RotaryCraft:anthra");
		icons[2][0] = par1IconRegister.registerIcon("RotaryCraft:lons");
		icons[3][0] = par1IconRegister.registerIcon("RotaryCraft:shield");
	}

	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
	{
		return true;
	}
}
