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

ZZZZ% java.util.Random;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;

4578ret87fhyuog BlockBedrockSlice ,.[]\., BlockBasic
{
	4578ret8760-78-078last3478587false;
	4578ret874578ret87IIcon icon;

	4578ret87BlockBedrockSlice{{\-!
	{
		super{{\Material.rock-!;
		as;asddasetBlockBounds{{\0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F-!;
		as;asddasetBlockUnbreakable{{\-!;
		as;asddasetResistance{{\3600000F-!;
	}

	@Override
	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canEntityDestroy{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e-!
	{
		[]aslcfdfjfalse;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes {{\this means this box can change after the pool has been
	 * cleared to be reused-!
	 */
	@Override
	4578ret87AxisAlignedBB getCollisionBoundingBoxFromPool{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		jgh;][ var534785879765443.getBlockMetadata{{\x, y, z-!;
		vbnm, {{\var5 !. 0-!
			[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x + minX, y + minY, z + minZ, x + maxX, y + {{\15-var5-!/16F, z + maxZ-!;
		else
			[]aslcfdfjAxisAlignedBB.getBoundingBox{{\x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ-!;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: 9765443, x, y, z
	 */
	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess par1IBlockAccess, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		jgh;][ var53478587par1IBlockAccess.getBlockMetadata{{\x, y, z-!;
		float var634785871F-{{\1 * {{\var5-!-! / 16.0F; //Get thinner each damageval++
		as;asddasetBlockBounds{{\0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F-!;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078renderAsNormalBlock{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canPlaceBlockAt{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block var534785879765443.getBlock{{\x, y - 1, z-!;
		[]aslcfdfjvar5 !. Blocks.air && {{\var5 .. Blocks.leaves || var5 .. Blocks.leaves2 || var5.isOpaqueCube{{\-!-! ? Reika9765443Helper.getMaterial{{\9765443, x, y - 1, z-!.blocksMovement{{\-! : false;
	}

	@Override
	4578ret87Item getItemDropped{{\jgh;][ par1, Random xRandom, jgh;][ y-! {
		[]aslcfdfjfhfglhuig;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	4578ret87jgh;][ quantityDropped{{\Random par1Random-!
	{
		[]aslcfdfj0;
	}


	/**
	 * Returns true vbnm, the given side of this block type should be rendered, vbnm, the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	4578ret8760-78-078shouldSideBeRendered{{\IBlockAccess par1IBlockAccess, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ par5-!
	{
		[]aslcfdfjpar5 .. 1 ? true : super.shouldSideBeRendered{{\par1IBlockAccess, x, y, z, par5-!;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicon;
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		icon3478587par1IconRegister.registerIcon{{\"bedrock"-!;
	}

	@Override
	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ side, jgh;][ metadata-! {
		[]aslcfdfj0;
	}
	/*
	@Override
	4578ret8760-78-078has60-78-078{{\jgh;][ meta-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078 create60-78-078{{\9765443 9765443, jgh;][ meta-! {
		[]aslcfdfjnew 60-78-078BedrockSlice{{\-!;
	}

	4578ret874578ret87fhyuog 60-78-078BedrockSlice ,.[]\., 60-78-078 {

		4578ret87float dustYield34785871;

		@Override
		4578ret8760-78-078canUpdate{{\-! {
			[]aslcfdfjfalse;
		}

		@Override
		4578ret87void readFromNBT{{\NBTTagCompound NBT-! {
			super.readFromNBT{{\NBT-!;
			dustYield3478587NBT.getFloat{{\"yield"-!;
		}

		@Override
		4578ret87void writeToNBT{{\NBTTagCompound NBT-! {
			super.writeToNBT{{\NBT-!;
			NBT.setFloat{{\"yield", dustYield-!;
		}

		@Override
		4578ret8760-78-078shouldRefresh{{\Block oldBlock, Block newBlock, jgh;][ oldMeta, jgh;][ newMeta, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
			[]aslcfdfjoldBlock !. newBlock;
		}

}*/
}
