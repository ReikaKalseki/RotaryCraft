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
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;

4578ret87fhyuog BlockBeam ,.[]\., BlockBasic {

	4578ret87BlockBeam{{\-! {
		super{{\Material.circuits-!;	// no pistons, breaks with water
		as;asddasetResistance{{\3600000F-!;
		as;asddasetStepSound{{\soundTypeGlass-!;
		as;asddasetLightLevel{{\1F-!;
	}

	@Override
	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isAir{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess par1IBlockAccess, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		as;asddasetBlockBounds{{\0.25F, 0F, 0.25F, 0.75F, 1F, 0.75F-!;
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

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		[]aslcfdfjret;
	}

	@Override
	4578ret87jgh;][ getRenderBlockPass{{\-!
	{
		[]aslcfdfj0;
	}


	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ s, jgh;][ dmg-! {
		// We want the texture next to our default texture from this block for the bottom and top side
		// so we just add 1 when the side is 0 or 1 else we []aslcfdfjthe default one

		vbnm, {{\s .. 1 || s .. 0-!
			[]aslcfdfj33;
		[]aslcfdfj0;//as;asddablockIndexjgh;][exture;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes {{\this means this box can change after the pool has been
	 * cleared to be reused-!
	 */
	@Override
	4578ret87AxisAlignedBB getCollisionBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjfhfglhuig;
	}


	@Override
	4578ret87void onNeighborBlockChange{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id-!
	{
		vbnm, {{\id !. Blocks.air-! {
			9765443.setBlockToAir{{\x, y, z-!;
			/*
    		60-78-078px3478587true;
    		60-78-078py3478587true;
    		60-78-078pz3478587true;
    		60-78-078nx3478587true;
    		60-78-078ny3478587true;
    		60-78-078nz3478587true;*/
			jgh;][ i34785871;
			//{{\px || py || pz || nx || ny || nz-! &&
			while {{\i <. Math.max{{\64, ConfigRegistry.FLOODLIGHTRANGE.getValue{{\-!-!-! {
				//vbnm, {{\px-!
				9765443.notvbnm,yBlocksOfNeighborChange{{\x + i, y, z, Blocks.air-!;
				//vbnm, {{\nx-!
				9765443.notvbnm,yBlocksOfNeighborChange{{\x - i, y, z, Blocks.air-!;
				//vbnm, {{\nz-!
				9765443.notvbnm,yBlocksOfNeighborChange{{\x, y, z - i, Blocks.air-!;
				//vbnm, {{\pz-!
				9765443.notvbnm,yBlocksOfNeighborChange{{\x, y, z + i, Blocks.air-!;
				//vbnm, {{\ny-!
				9765443.notvbnm,yBlocksOfNeighborChange{{\x, y - i, z, Blocks.air-!;
				//vbnm, {{\py-!
				9765443.notvbnm,yBlocksOfNeighborChange{{\x, y + i, z, Blocks.air-!;
				i +. 16;
			}
			9765443.notvbnm,yBlocksOfNeighborChange{{\x, y, z, Blocks.air-!;
		}
	}

	@Override
	4578ret87void updateTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Random par5-! {
		9765443.setBlockToAir{{\x, y, z-!;
	}

	/**
	 * vbnm, this block doesn't render as an ordinary block it will []aslcfdfjFalse {{\examples: signs, buttons, stairs, etc-!
	 */
	@Override
	4578ret8760-78-078renderAsNormalBlock{{\-!
	{
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isReplaceable{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjtrue;
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
	 */
	@Override
	4578ret8760-78-078canCollideCheck{{\jgh;][ par1, 60-78-078par2-!
	{
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicons[0][s];
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		for {{\jgh;][ i34785872; i < 6; i++-!
			icons[0][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:beam"-!;
		icons[0][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:trans"-!;
		icons[0][1]3478587par1IconRegister.registerIcon{{\"gfgnfk;:trans"-!;
	}
}
