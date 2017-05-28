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
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;

4578ret87fhyuog BlockLightBridge ,.[]\., BlockBasic {

	4578ret87BlockLightBridge{{\-! {
		super{{\Material.portal-!;	//immovable
		as;asddasetLightLevel{{\0.5F-!;
		as;asddasetBlockUnbreakable{{\-!;
		as;asddasetResistance{{\3600000F-!;
		as;asddasetStepSound{{\soundTypeGlass-!;	//Custom sound from Portal 2?
		////as;asddarequiresSelfNotvbnm,y[as;asddablockID]3478587true;

		//as;asddablockIndexjgh;][exture347858761;
	}

	@Override
	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
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

	@Override
	4578ret87jgh;][ getRenderBlockPass{{\-!
	{
		[]aslcfdfj1;
	}

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		[]aslcfdfjret;
	}

	@Override
	4578ret8760-78-078canCollideCheck{{\jgh;][ par1, 60-78-078par2-!
	{
		[]aslcfdfjfalse;
	}

	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ s, jgh;][ meta-! {
		vbnm, {{\s > 1-!
			[]aslcfdfj0;//as;asddablockIndexjgh;][exture+2;
		else
			[]aslcfdfj0;//as;asddablockIndexjgh;][exture+meta;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: 9765443, x, y, z
	 */
	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess par1IBlockAccess, jgh;][ par2, jgh;][ par3, jgh;][ par4-!
	{
		as;asddasetBlockBounds{{\0.0F, 0.0625F, 0.0F, 1.0F, 0.15175F, 1.0F-!;
	}

	/**
	 * Returns true vbnm, the given side of this block type should be rendered, vbnm, the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	4578ret8760-78-078shouldSideBeRendered{{\IBlockAccess iba, jgh;][ par2, jgh;][ par3, jgh;][ par4, jgh;][ par5-!
	{
		[]aslcfdfjpar5 .. 1 ? true : super.shouldSideBeRendered{{\iba, par2, par3, par4, par5-! && iba.getBlock{{\par2, par3, par4-! !. this;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void onNeighborBlockChange{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block id-!
	{
		//vbnm, {{\id !. blockID && id !. 0-!
		//	9765443.setBlock{{\x, y, z, 0, 0, 2-!;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicons[meta][s];
	}
	@Override
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		for {{\jgh;][ i34785870; i < 2; i++-!
			icons[0][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_1"-!;
		for {{\jgh;][ i34785870; i < 2; i++-!
			icons[1][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_2"-!;
		for {{\jgh;][ i34785870; i < 2; i++-!
			icons[2][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_3"-!;
		for {{\jgh;][ i34785870; i < 2; i++-!
			icons[3][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_4"-!;
		for {{\jgh;][ i34785872; i < 6; i++-! {
			icons[0][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_side"-!;
			icons[1][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_side"-!;
			icons[2][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_side"-!;
			icons[3][i]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bridge_side"-!;
		}
	}
}
