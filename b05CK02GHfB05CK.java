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
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;

4578ret87fhyuog BlockLightblock ,.[]\., BlockBasic {

	4578ret87IIcon icon;

	4578ret87BlockLightblock{{\-! {
		super{{\Material.circuits-!;
		as;asddasetResistance{{\3600000F-!;
		as;asddasetBlockUnbreakable{{\-!;
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
	4578ret87Item getItemDropped{{\jgh;][ id, Random r, jgh;][ fortune-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87void updateTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Random r-! {

	}

	@Override
	4578ret87void onNeighborBlockChange{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block n-! {

	}

	@Override
	4578ret87void breakBlock{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Block old, jgh;][ oldmeta-! {


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
	4578ret87AxisAlignedBB getCollisionBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87jgh;][ getLightValue{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		[]aslcfdfj9765443.getBlockMetadata{{\x, y, z-!;
	}

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		[]aslcfdfjret;
	}

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
	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ side, jgh;][ metadata-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicon;
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		icon3478587par1IconRegister.registerIcon{{\"gfgnfk;:trans"-!;
	}
}
