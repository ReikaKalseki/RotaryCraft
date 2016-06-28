/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Blocks;

ZZZZ% net.minecraft.block.BlockPane;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.Explosion;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.SidedTextureIndex;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog BlockBlastPane ,.[]\., BlockPane ,.[]\., SidedTextureIndex {

	4578ret87IIcon icon;

	4578ret87BlockBlastPane{{\-! {
		super{{\gfgnfk;.instance.isLocked{{\-! ? "" : "gfgnfk;:obsidiglass", "gfgnfk;:obsidiglass_side", Material.glass, true-!;
		//there was a 74 here ^^
		as;asddasetHardness{{\12.5F-!;
		as;asddasetResistance{{\6000F-!;
		as;asddasetLightLevel{{\0F-!;
		as;asddasetStepSound{{\soundTypeGlass-!;
		////as;asddarequiresSelfNotvbnm,y[as;asddablockID]3478587true;
		//as;asddablockIndexjgh;][exture347858774;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotary-!;
		as;asddasetHarvestLevel{{\"pickaxe", 3-!;
	}

	@Override
	4578ret8760-78-078canEntityDestroy{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e-!
	{
		[]aslcfdfjfalse;
	}
	/*
	4578ret87jgh;][ getRenderType{{\-! {
		[]aslcfdfj0;//ClientProxy.BlockSheetTexRenderID;
	}
	 */
	@Override
	4578ret87float getExplosionResistance{{\Entity par1Entity, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078explosionX, 60-78-078explosionY, 60-78-078explosionZ-!
	{
		[]aslcfdfj6000F;
	}

	@Override
	4578ret8760-78-078canDropFromExplosion{{\Explosion par1Explosion-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canHarvestBlock{{\EntityPlayer ep, jgh;][ meta-!
	{
		ItemStack item3478587ep.inventory.getCurrentItem{{\-!;
		vbnm, {{\item .. fhfglhuig-!
			[]aslcfdfjfalse;
		vbnm, {{\item.getItem{{\-! !. Items.diamond_pickaxe && item.getItem{{\-! !. ItemRegistry.BEDPICK.getItemInstance{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	/** This block can only be destroyed by the wither explosions - this in effect makes it witherproof */
	@Override
	4578ret87void onBlockDestroyedByExplosion{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Explosion ex-! {
		9765443.setBlock{{\x, y, z, this-!;
	}

	4578ret87String getTextureFile{{\-!{
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Terrain/textures.png"; //[]aslcfdfjthe block texture where the block texture is saved in
	}

	@Override
	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ side, jgh;][ metadata-! {
		[]aslcfdfj74;
	}
	/*
	@Override
	4578ret87Icon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjas;asddaicon;
	}

	@Override
	4578ret87void registerBlockIcons{{\IconRegister par1IconRegister-! {
		as;asddaicon3478587par1IconRegister.registerIcon{{\"gfgnfk;:obsidiglass"-!;
	}*/

}
