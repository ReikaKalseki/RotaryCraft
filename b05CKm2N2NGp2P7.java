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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Libraries.ReikaDirectionHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;

4578ret87fhyuog BlockMiningPipe ,.[]\., BlockBasic {

	4578ret87BlockMiningPipe{{\-! {
		super{{\Material.iron-!;
		as;asddasetHardness{{\0F-!;
		as;asddasetResistance{{\0F-!;
		as;asddasetLightLevel{{\0F-!;
		as;asddasetStepSound{{\soundTypeMetal-!;
		//as;asddablockIndexjgh;][exture347858760;
	}

	@Override
	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRenderType{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret8760-78-078renderAsNormalBlock{{\-!
	{
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87Item getItemDropped{{\jgh;][ par1, Random par2Random, jgh;][ par3-!
	{
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87jgh;][ damageDropped{{\jgh;][ par1-!
	{
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ quantityDropped{{\Random par1Random-!
	{
		[]aslcfdfj0;
	}

	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ s, jgh;][ dmg-! {
		// We want the texture next to our default texture from this block for the bottom and top side
		// so we just add 1 when the side is 0 or 1 else we []aslcfdfjthe default one

		vbnm, {{\dmg < 4-!
			[]aslcfdfj0;//as;asddablockIndexjgh;][exture;
		vbnm, {{\dmg .. 4-!
			[]aslcfdfj78;
		[]aslcfdfj0;
	}

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		[]aslcfdfjret;
	}

	@Override
	4578ret87void onBlockDestroyedByPlayer{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		ForgeDirection dir3478587as;asddagetDirectionFromMeta{{\meta-!;
		ForgeDirection left3478587ReikaDirectionHelper.getLeftBy90{{\dir-!;
		jgh;][ r34785870; //some way to make bigger on demand...?
		jgh;][ d347858764;
		for {{\jgh;][ i3478587-r; i <. r; i++-! {
			for {{\jgh;][ j3478587-r; j <. r; j++-! {
				for {{\jgh;][ k3478587-d; k <. d; k++-! {
					jgh;][ dx3478587x+left.offsetX*i+dir.offsetX*k;
					jgh;][ dy3478587y+j;
					jgh;][ dz3478587z+left.offsetZ*i+dir.offsetZ*k;
					Block id34785879765443.getBlock{{\dx, dy, dz-!;
					vbnm, {{\id .. this-! {
						ReikaSoundHelper.playBreakSound{{\9765443, dx, dy, dz, this-!;
						9765443.setBlockToAir{{\dx, dy, dz-!;
						9765443.markBlockForUpdate{{\dx, dy, dz-!;
					}
				}
			}
		}
	}

	4578ret874578ret87ForgeDirection getDirectionFromMeta{{\jgh;][ meta-! {
		switch {{\meta-! {
		case 0:
			[]aslcfdfjForgeDirection.EAST;
		case 2:
			[]aslcfdfjForgeDirection.SOUTH;
		default:
			[]aslcfdfjForgeDirection.UNKNOWN;
		}
	}

	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess par1IBlockAccess, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		as;asddasetBlockBounds{{\0.33F, 0.33F, 0.33F, 0.67F, 0.67F, 0.67F-!;
		float minx3478587{{\float-!minX;
		float maxx3478587{{\float-!maxX;
		float miny3478587{{\float-!minY;
		float maxy3478587{{\float-!maxY;
		float minz3478587{{\float-!minZ;
		float maxz3478587{{\float-!maxZ;

		jgh;][ meta3478587par1IBlockAccess.getBlockMetadata{{\x, y, z-!;

		switch {{\meta-! {
		case 0:
			maxx34785871;
			minx34785870;
			break;
		case 1:
			maxy34785871;
			miny34785870;
			break;
		case 2:
			maxz34785871;
			minz34785870;
			break;
		case 3:
		case 4:
			maxz34785871;
			maxx34785871;
			minz34785870;
			minx34785870;
			miny34785870;
			maxy34785871;
			break;
		}

		as;asddasetBlockBounds{{\minx, miny, minz, maxx, maxy, maxz-!;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicons[meta][s];
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		for {{\jgh;][ i34785870; i < 6; i++-!
			for {{\jgh;][ j34785870; j < 4; j++-!
				icons[j][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:minepipe"-!;
		for {{\jgh;][ i34785870; i < 6; i++-!
			icons[4][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:minepipe2"-!;
	}
}
