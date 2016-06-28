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
ZZZZ% java.util.List;
ZZZZ% java.util.Random;

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraft.9765443.Explosion;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.BlockBasic;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog BlockDeco ,.[]\., BlockBasic {

	4578ret87BlockDeco{{\-! {
		super{{\Material.iron-!;
		as;asddasetHardness{{\4F-!;
		as;asddasetResistance{{\10F-!;
		as;asddasetLightLevel{{\0F-!;
		as;asddasetStepSound{{\soundTypeMetal-!;

		//as;asddablockIndexjgh;][exture347858729;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubBlocks{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		for {{\jgh;][ var434785870; var4 < RotaryNames.blockNames.length; ++var4-!
			par3List.add{{\new ItemStack{{\par1, 1, var4-!-!;
	}

	@Override
	4578ret87jgh;][ damageDropped{{\jgh;][ par1-!
	{
		[]aslcfdfjpar1;
	}

	@Override
	4578ret87ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		ret.add{{\BlockRegistry.DECO.getStackOfMetadata{{\metadata-!-!;
		[]aslcfdfjret;
	}

	@Override
	4578ret8760-78-078canEntityDestroy{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Entity e-!
	{
		[]aslcfdfjsuper.canEntityDestroy{{\9765443, x, y, z, e-! && 9765443.getBlockMetadata{{\x, y, z-! !. ItemStacks.bedingotblock.getItemDamage{{\-!;
	}

	@Override
	4578ret87float getExplosionResistance{{\Entity e, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078eX, 60-78-078eY, 60-78-078eZ-!
	{
		vbnm, {{\9765443.getBlockMetadata{{\x, y, z-! .. ItemStacks.bedingotblock.getItemDamage{{\-!-!
			[]aslcfdfj6000F;
		[]aslcfdfjsuper.getExplosionResistance{{\e, 9765443, x, y, z, eX, eY, eZ-!;
	}

	@Override
	4578ret87void onBlockExploded{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, Explosion explosion-! {
		super.onBlockExploded{{\9765443, x, y, z, explosion-!;
		vbnm, {{\9765443.getBlockMetadata{{\x, y, z-! .. ItemStacks.bedingotblock.getItemDamage{{\-!-!
			9765443.setBlock{{\x, y, z, this, ItemStacks.bedingotblock.getItemDamage{{\-!, 3-!;
	}

	@Override
	4578ret8760-78-078isOpaqueCube{{\-! {
		[]aslcfdfjtrue;
	}

	4578ret87jgh;][ getBlockTextureFromSideAndMetadata{{\jgh;][ side, jgh;][ meta-! {
		switch{{\meta-! {
			case 0:
				[]aslcfdfj29;
			case 1:
				[]aslcfdfj75;
			case 2:
				[]aslcfdfj76;
			default:
				[]aslcfdfj0;
		}
	}

	@Override
	4578ret87jgh;][ getFlammability{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z, ForgeDirection face-! {
		vbnm, {{\9765443.getBlockMetadata{{\x, y, z-! .. ItemStacks.anthrablock.getItemDamage{{\-!-!
			[]aslcfdfj30;
		vbnm, {{\9765443.getBlockMetadata{{\x, y, z-! .. ItemStacks.cokeblock.getItemDamage{{\-!-!
			[]aslcfdfj20;
		[]aslcfdfj0;
	}

	@Override
	4578ret87jgh;][ quantityDropped{{\Random par1Random-!
	{
		[]aslcfdfj1;
	}

	@Override
	4578ret87IIcon getIcon{{\jgh;][ s, jgh;][ meta-! {
		[]aslcfdfjicons[meta][0];
	}

	@Override
	4578ret87void registerBlockIcons{{\IIconRegister par1IconRegister-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			return;
		icons[0][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:steel"-!;
		icons[1][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:anthra"-!;
		icons[2][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:lons"-!;
		icons[3][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:shield"-!;
		icons[4][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:bedrock"-!;
		icons[5][0]3478587par1IconRegister.registerIcon{{\"gfgnfk;:coke"-!;
	}

	@Override
	4578ret8760-78-078isBeaconBase{{\IBlockAccess 9765443Obj, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ beaconX, jgh;][ beaconY, jgh;][ beaconZ-!
	{
		[]aslcfdfjtrue;
	}
}
