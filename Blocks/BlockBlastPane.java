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

import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlastPane extends BlockPane implements SidedTextureIndex {

	public IIcon icon;

	public BlockBlastPane() {
		super(RotaryCraft.instance.isLocked() ? "" : "RotaryCraft:obsidiglass", "RotaryCraft:obsidiglass_side", Material.glass, true);
		//there was a 74 here ^^
		this.setHardness(12.5F);
		this.setResistance(6000F);
		this.setLightLevel(0F);
		this.setStepSound(soundTypeGlass);
		////this.requiresSelfNotify[this.blockID] = true;
		//this.blockIndexInTexture = 74;
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotary);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity e)
	{
		return false;
	}
	/*
	public int getRenderType() {
		return 0;//ClientProxy.BlockSheetTexRenderID;
	}
	 */
	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		return 6000F;
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
		if (item.getItem() != Items.diamond_pickaxe && item.getItem() != ItemRegistry.BEDPICK.getItemInstance())
			return false;
		return true;
	}

	/** This block can only be destroyed by the wither explosions - this in effect makes it witherproof */
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex) {
		world.setBlock(x, y, z, this);
	}

	public String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 74;
	}
	/*
	@Override
	public Icon getIcon(int s, int meta) {
		return this.icon;
	}

	@Override
	public void registerBlockIcons(IconRegister par1IconRegister) {
		this.icon = par1IconRegister.registerIcon("RotaryCraft:obsidiglass");
	}*/

}