/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockGravity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGravLeaves extends BlockGravity implements SidedTextureIndex {

	public static final String[] LEAF_TYPES = new String[] {"oak", "spruce", "birch", "jungle"};
	int[] adjacentTreeBlocks;

	Random par5Random = new Random();

	/**
	 * Used to determine how to display leaves based on the graphics level. May also be used in rendering for
	 * transparency, not sure.
	 */
	//public boolean graphicsLevel = ModLoader.getMinecraftInstance().isFancyGraphicsEnabled();

	public BlockGravLeaves(int ID) {
		super(ID, Material.leaves);
		this.setHardness(0.2F);
		this.setResistance(1F);
		this.setLightValue(0F);
		this.setLightOpacity(1);
		this.setStepSound(soundGrassFootstep);
		this.setTickRandomly(true);
		this.setBurnProperties(blockID, 60, 120); //2x

		//this.blockIndexInTexture = 52;
		//setCreativeTab(RotaryCraft.tabRotary);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		boolean permaleaves = false;
		super.updateTick(world, x, y, z, rand);/*
		if (this.nonRandomTick) {
	        this.nonRandomTick = false;
			return;
		}*/
		if (rand.nextInt(4) > 0 || permaleaves)
			return;
		if (!world.isRemote) { //Halved drop rates since falling counts as a destruction event
			int meta = world.getBlockMetadata(x, y, z);
			if ((rand.nextInt(10) == 0 && meta != 3) || (rand.nextInt(20) == 0 && meta == 3)) {
				ItemStack is = new ItemStack(Block.sapling.blockID, 1, meta);
				EntityItem ent = new EntityItem(world, x+0.5, y+0.5, z+0.5, is);
				ent.motionY = 0.2*rand.nextFloat();
				world.spawnEntityInWorld(ent);
			}
			if (rand.nextInt(400) == 0 && meta == 0) {
				ItemStack is = new ItemStack(Item.appleRed.itemID, 1, 0);
				EntityItem ent = new EntityItem(world, x+0.5, y+0.5, z+0.5, is);
				ent.motionY = 0.2*rand.nextFloat();
				world.spawnEntityInWorld(ent);
			}
		}
		ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerFoliage.getFoliageColor(var1, var3);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	public int getRenderColor(int par1)
	{
		return (par1 & 3) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((par1 & 3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

		if ((var5 & 3) == 1)
		{
			return ColorizerFoliage.getFoliageColorPine();
		}
		else if ((var5 & 3) == 2)
		{
			return ColorizerFoliage.getFoliageColorBirch();
		}
		else
		{
			int var6 = 0;
			int var7 = 0;
			int var8 = 0;

			for (int var9 = -1; var9 <= 1; ++var9)
			{
				for (int var10 = -1; var10 <= 1; ++var10)
				{
					int var11 = par1IBlockAccess.getBiomeGenForCoords(par2 + var10, par4 + var9).getBiomeFoliageColor();
					var6 += (var11 & 16711680) >> 16;
				var7 += (var11 & 65280) >> 8;
				var8 += var11 & 255;
				}
			}

			return (var6 / 9 & 255) << 16 | (var7 / 9 & 255) << 8 | var8 / 9 & 255;
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Pass true to draw this block using fancy graphics, or false for fast graphics.
	 */
	public void setGraphicsLevel(boolean par1)
	{
		//graphicsLevel = par1;
		//if (par1)
		//this.blockIndexInTexture--;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
		//return !graphicsLevel && var6 == blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int var4 = 0; var4 < 4; ++var4)
			par3List.add(new ItemStack(par1, 1, var4));
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}
	/*
    public boolean renderAsNormalBlock() {
    	return false;
    }*/

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (meta) {
		case 1:
			return 132;
		case 2:
			return 52;
		case 3:
		case 7:
		case 11:
			return 196;
		}
		return 0;//this.blockIndexInTexture;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		this.propagate(world, x, y, z);

		super.onBlockAdded(world, x, y, z);
		/*
    	if (!this.canFallBelow(world, x, y-1, z)); {
	    	this.breakBlock(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	    	ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 0);
    	}*/
		//this.metadata = world.getBlockMetadata(x, y, z);
	}

	public boolean canPropLeaves(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) != Block.leaves.blockID)
			return false;
		if (par5Random.nextInt(4) > 0)
			return false;
		if (RotaryConfig.alwaysGravLeaves)
			return true;
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		int meta = world.getBlockMetadata(x, y, z);
		if ((biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills) && (meta == 3 || meta == 7 || meta == 11 /*|| meta == 0 || meta == 4 || meta == 8*/) && y > 64) // If jungle biome, jungle/oak leaves, and on the surface
			return false;
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d:%d @ %d in ", world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z), y)+String.valueOf(world.getBiomeGenForCoords(x, z)));
		return true;
	}

	public void propagate(World world, int x, int y, int z) {
		if (world.getBlockId(x, y-1, z) == Block.vine.blockID)
			ReikaWorldHelper.legacySetBlockWithNotify(world, x, y-1, z, 0);


		if (world.getBlockId(x+1, y, z) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x+1, y, z)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x-1, y, z) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x-1, y, z)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x, y+1, z) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y+1, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y+1, z, RotaryCraft.gravlog.blockID, meta);
		}
		if (world.getBlockId(x, y+1, z) == Block.leaves.blockID) { //Always propagate up
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y+1, z), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y+1, z, RotaryCraft.gravleaves.blockID, meta);
		}
		/*	Do not propagate downwards - to avoid affecting an entire biome, especially jungle
    	if (world.getBlockId(x, y-1, z) == Block.wood.blockID) {
    		int meta = world.getBlockMetadata(x, y-1, z);
    		ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y-1, z, RotaryCraft.gravlog.blockID, meta);
    	}
    	if (par5Random.nextInt(4) == 0 && world.getBlockId(x, y-1, z) == Block.leaves.blockID) {
    		int meta = world.getBlockMetadata(x, y-1, z);
    		ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y-1, z, RotaryCraft.gravleaves.blockID, meta);
    	}*/

		if (world.getBlockId(x, y, z+1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z+1, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x, y, z+1)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z+1, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x, y, z-1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z-1, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x, y, z-1)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x, y, z-1, RotaryCraft.gravleaves.blockID, meta);
		}

		if (world.getBlockId(x-1, y, z-1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z-1, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x-1, y, z-1)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z-1, RotaryCraft.gravleaves.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z-1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z-1, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x+1, y, z-1)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z-1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z-1, RotaryCraft.gravleaves.blockID, meta);
		}
		if (world.getBlockId(x+1, y, z+1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z+1, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x+1, y, z+1)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x+1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x+1, y, z+1, RotaryCraft.gravleaves.blockID, meta);
		}
		if (world.getBlockId(x-1, y, z+1) == Block.wood.blockID) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z+1, RotaryCraft.gravlog.blockID, meta);
		}
		if (this.canPropLeaves(world, x-1, y, z+1)) {
			int meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x-1, y, z+1), 4);
			ReikaWorldHelper.legacySetBlockAndMetadataWithNotify(world, x-1, y, z+1, RotaryCraft.gravleaves.blockID, meta);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int meta, int a) {
		this.propagate(world, x, y, z);

		if (!world.isRemote && par5Random.nextInt(2) == 0) { // Ensure client side
			//Reason for 50% cut is that falling counts as a break - spawns 2x saplings, apples
			meta = ReikaWorldHelper.capMetadata(world.getBlockMetadata(x, y, z), 4);
			if ((par5Random.nextInt(20) == 0 && meta != 3) || (par5Random.nextInt(40) == 0 && meta == 3)) {
				ItemStack is = new ItemStack(Block.sapling.blockID, 1, meta);
				EntityItem ent = new EntityItem(world, x+0.5, y+0.5, z+0.5, is);
				ent.motionY = 0.2*par5Random.nextFloat();
				world.spawnEntityInWorld(ent);
			}
			if (par5Random.nextInt(200) == 0 && meta == 0) {
				ItemStack is = new ItemStack(Item.appleRed.itemID, 1, 0);
				EntityItem ent = new EntityItem(world, x+0.5, y+0.5, z+0.5, is);
				ent.motionY = 0.2*par5Random.nextFloat();
				world.spawnEntityInWorld(ent);
			}
		}
		super.breakBlock(world, x, y, z, meta, a);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[meta][0];
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		icons[0][0] = par1IconRegister.registerIcon("RotaryCraft:oakleaf");
		icons[1][0] = par1IconRegister.registerIcon("RotaryCraft:spruceleaf");
		icons[2][0] = par1IconRegister.registerIcon("RotaryCraft:oakleaf");
		icons[3][0] = par1IconRegister.registerIcon("RotaryCraft:jungleleaf");
	}

	/*
	@Override
	public String getTextureFile(){
		return "/Reika/RotaryCraft/textures.png"; //return the block texture where the block texture is saved in
	}*/
}
