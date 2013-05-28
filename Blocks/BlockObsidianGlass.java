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

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.BlockBasic;

public class BlockObsidianGlass extends BlockBasic implements SidedTextureIndex {

	public BlockObsidianGlass(int blockID) {
		super(blockID, Material.glass);
		this.setHardness(25F);
		this.setResistance(6000F);
		this.setLightValue(0F);
		this.setStepSound(soundGlassFootstep);
		this.setCreativeTab(RotaryCraft.tabRotary);

		//this.blockIndexInTexture = 74;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		return 6000F;
	}

	@Override
	public int getRenderType() {
		return 0;//ClientProxy.BlockSheetTexRenderID;
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion) {
		return false;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{
		ItemStack item = ep.inventory.getCurrentItem();
		if (item == null)
			return false;
		if (item.itemID != Item.pickaxeDiamond.itemID && item.itemID != RotaryCraft.bedpick.itemID)
			return false;
		return true;
	}

	/** This block can only be destroyed by the wither explosions - this in effect makes it witherproof */
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex) {
		ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, blockID);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return blockID;
	}

	@Override
	public int damageDropped(int par1)
	{
		return 0;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 74;
	}

	@Override
	public Icon getIcon(int s, int meta) {
		return icons[0][s];
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		for (int i = 0; i < 6; i++)
			icons[0][i] = par1IconRegister.registerIcon("RotaryCraft:obsidiglass");
	}
}
