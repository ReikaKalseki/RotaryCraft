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

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.SidedTextureIndex;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class BlockBlastGlass extends BlockPane implements SidedTextureIndex {

	public Icon icon;

	public BlockBlastGlass(int ID) {
		super(ID, RotaryCraft.instance.isLocked() ? "" : "RotaryCraft:obsidiglass", "RotaryCraft:obsidiglass_side", Material.glass, true);
		//there was a 74 here ^^
		this.setHardness(12.5F);
		this.setResistance(6000F);
		this.setLightValue(0F);
		this.setStepSound(soundGlassFootstep);
		////this.requiresSelfNotify[this.blockID] = true;
		//this.blockIndexInTexture = 74;
		this.setCreativeTab(RotaryCraft.instance.isLocked() ? null : RotaryCraft.tabRotary);
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z, Entity e)
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
		if (item.itemID != Item.pickaxeDiamond.itemID && item.itemID != ItemRegistry.BEDPICK.getShiftedID())
			return false;
		return true;
	}

	/** This block can only be destroyed by the wither explosions - this in effect makes it witherproof */
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex) {
		world.setBlock(x, y, z, blockID);
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
	public void registerIcons(IconRegister par1IconRegister) {
		this.icon = par1IconRegister.registerIcon("RotaryCraft:obsidiglass");
	}*/

}
