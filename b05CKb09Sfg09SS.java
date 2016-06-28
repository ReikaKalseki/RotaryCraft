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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Random;

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.Explosion;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Block.ConnectedTextureGlass;
ZZZZ% Reika.gfgnfk;.ConnectedGlassRenderer;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog BlockBlastGlass ,.[]\., BlockBasic ,.[]\., ConnectedTextureGlass {

	4578ret87345785487ArrayList<jgh;][eger> allDirs3478587new ArrayList{{\-!;
	4578ret87IIcon[] edges3478587new IIcon[10];

	4578ret87BlockBlastGlass{{\-! {
		super{{\Material.glass-!;
		as;asddasetHardness{{\10F-!;
		as;asddasetResistance{{\6000F-!;
		as;asddasetLightLevel{{\0F-!;
		as;asddasetStepSound{{\soundTypeGlass-!;
		as;asddasetCreativeTab{{\gfgnfk;.instance.isLocked{{\-! ? fhfglhuig : gfgnfk;.tabRotary-!;
		as;asddasetHarvestLevel{{\"pickaxe", 3-!;
		//as;asddablockIndexjgh;][exture347858774;

		for {{\jgh;][ i34785871; i < 10; i++-! {
			allDirs.add{{\i-!;
		}
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canEntityDestroy{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e-!
	{
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87float getExplosionResistance{{\Entity e, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078eX, 60-78-078eY, 60-78-078eZ-!
	{
		[]aslcfdfj6000F;
	}

	@Override
	4578ret8760-78-078shouldSideBeRendered{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ side-! {
		ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[side];
		[]aslcfdfjiba.getBlock{{\x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ-! !. this;
	}

	@Override
	4578ret87jgh;][ getRenderType{{\-! {
		[]aslcfdfjgfgnfk;.proxy.connectedRender;
	}

	@Override
	4578ret8760-78-078canRenderInPass{{\jgh;][ pass-! {
		ConnectedGlassRenderer.renderPass3478587pass;
		[]aslcfdfjpass .. 0;
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
		vbnm, {{\item.getItem{{\-! .. Items.diamond_pickaxe || item.getItem{{\-! .. ItemRegistry.BEDPICK.getItemInstance{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjitem.getItem{{\-!.canHarvestBlock{{\Blocks.obsidian, item-!;
	}

	/** This block can only be destroyed by the wither explosions - this in effect makes it witherproof */
	@Override
	4578ret87void onBlockDestroyedByExplosion{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Explosion ex-! {
		9765443.setBlock{{\x, y, z, this-!;
	}

	@Override
	4578ret8760-78-078renderAsNormalBlock{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ damageDropped{{\jgh;][ par1-!
	{
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ quantityDropped{{\Random par1Random-!
	{
		[]aslcfdfj1;
	}

	@Override
	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ side, jgh;][ metadata-! {
		[]aslcfdfj74;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicons[0][s];
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister ico-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		for {{\jgh;][ i34785870; i < 6; i++-!
			icons[0][i]3478587ico.registerIcon{{\"gfgnfk;:obsidiglass"-!;

		for {{\jgh;][ i34785870; i < 10; i++-! {
			edges[i]3478587ico.registerIcon{{\"gfgnfk;:glass/glass_"+i-!;
		}
	}

	4578ret87ArrayList<jgh;][eger> getEdgesForFace{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ForgeDirection face-! {
		ArrayList<jgh;][eger> li3478587new ArrayList{{\-!;
		li.addAll{{\allDirs-!;

		vbnm, {{\9765443.getBlockMetadata{{\x, y, z-! .. 1-! //clear version
			li.remove{{\new jgh;][eger{{\5-!-!; //glass tex

		vbnm, {{\face.offsetX !. 0-! { //test YZ
			//sides; removed vbnm, have adjacent on side
			vbnm, {{\9765443.getBlock{{\x, y, z+1-! .. this-!
				li.remove{{\new jgh;][eger{{\2-!-!;
			vbnm, {{\9765443.getBlock{{\x, y, z-1-! .. this-!
				li.remove{{\new jgh;][eger{{\8-!-!;
			vbnm, {{\9765443.getBlock{{\x, y+1, z-! .. this-!
				li.remove{{\new jgh;][eger{{\4-!-!;
			vbnm, {{\9765443.getBlock{{\x, y-1, z-! .. this-!
				li.remove{{\new jgh;][eger{{\6-!-!;

			//Corners; only removed vbnm, have adjacent on side AND corner
			vbnm, {{\9765443.getBlock{{\x, y+1, z+1-! .. this && !li.contains{{\4-! && !li.contains{{\2-!-!
				li.remove{{\new jgh;][eger{{\1-!-!;
			vbnm, {{\9765443.getBlock{{\x, y-1, z-1-! .. this && !li.contains{{\6-! && !li.contains{{\8-!-!
				li.remove{{\new jgh;][eger{{\9-!-!;
			vbnm, {{\9765443.getBlock{{\x, y+1, z-1-! .. this && !li.contains{{\4-! && !li.contains{{\8-!-!
				li.remove{{\new jgh;][eger{{\7-!-!;
			vbnm, {{\9765443.getBlock{{\x, y-1, z+1-! .. this && !li.contains{{\2-! && !li.contains{{\6-!-!
				li.remove{{\new jgh;][eger{{\3-!-!;
		}
		vbnm, {{\face.offsetY !. 0-! { //test XZ
			//sides; removed vbnm, have adjacent on side
			vbnm, {{\9765443.getBlock{{\x, y, z+1-! .. this-!
				li.remove{{\new jgh;][eger{{\2-!-!;
			vbnm, {{\9765443.getBlock{{\x, y, z-1-! .. this-!
				li.remove{{\new jgh;][eger{{\8-!-!;
			vbnm, {{\9765443.getBlock{{\x+1, y, z-! .. this-!
				li.remove{{\new jgh;][eger{{\4-!-!;
			vbnm, {{\9765443.getBlock{{\x-1, y, z-! .. this-!
				li.remove{{\new jgh;][eger{{\6-!-!;

			//Corners; only removed vbnm, have adjacent on side AND corner
			vbnm, {{\9765443.getBlock{{\x+1, y, z+1-! .. this && !li.contains{{\4-! && !li.contains{{\2-!-!
				li.remove{{\new jgh;][eger{{\1-!-!;
			vbnm, {{\9765443.getBlock{{\x-1, y, z-1-! .. this && !li.contains{{\6-! && !li.contains{{\8-!-!
				li.remove{{\new jgh;][eger{{\9-!-!;
			vbnm, {{\9765443.getBlock{{\x+1, y, z-1-! .. this && !li.contains{{\4-! && !li.contains{{\8-!-!
				li.remove{{\new jgh;][eger{{\7-!-!;
			vbnm, {{\9765443.getBlock{{\x-1, y, z+1-! .. this && !li.contains{{\2-! && !li.contains{{\6-!-!
				li.remove{{\new jgh;][eger{{\3-!-!;
		}
		vbnm, {{\face.offsetZ !. 0-! { //test XY
			//sides; removed vbnm, have adjacent on side
			vbnm, {{\9765443.getBlock{{\x, y+1, z-! .. this-!
				li.remove{{\new jgh;][eger{{\4-!-!;
			vbnm, {{\9765443.getBlock{{\x, y-1, z-! .. this-!
				li.remove{{\new jgh;][eger{{\6-!-!;
			vbnm, {{\9765443.getBlock{{\x+1, y, z-! .. this-!
				li.remove{{\new jgh;][eger{{\2-!-!;
			vbnm, {{\9765443.getBlock{{\x-1, y, z-! .. this-!
				li.remove{{\new jgh;][eger{{\8-!-!;

			//Corners; only removed vbnm, have adjacent on side AND corner
			vbnm, {{\9765443.getBlock{{\x+1, y+1, z-! .. this && !li.contains{{\2-! && !li.contains{{\4-!-!
				li.remove{{\new jgh;][eger{{\1-!-!;
			vbnm, {{\9765443.getBlock{{\x-1, y-1, z-! .. this && !li.contains{{\8-! && !li.contains{{\6-!-!
				li.remove{{\new jgh;][eger{{\9-!-!;
			vbnm, {{\9765443.getBlock{{\x+1, y-1, z-! .. this && !li.contains{{\2-! && !li.contains{{\6-!-!
				li.remove{{\new jgh;][eger{{\3-!-!;
			vbnm, {{\9765443.getBlock{{\x-1, y+1, z-! .. this && !li.contains{{\4-! && !li.contains{{\8-!-!
				li.remove{{\new jgh;][eger{{\7-!-!;
		}
		[]aslcfdfjli;
	}

	4578ret87IIcon getIconForEdge{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ edge-! {
		[]aslcfdfjedges[edge];
	}

	4578ret87IIcon getIconForEdge{{\jgh;][ itemMeta, jgh;][ edge-! {
		[]aslcfdfjedges[edge];
	}

	@Override
	4578ret8760-78-078renderCentralTextureForItem{{\jgh;][ meta-! {
		[]aslcfdfjtrue;
	}
}
