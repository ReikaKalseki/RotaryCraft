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

ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Base.BlockModelledMachine;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog BlockFlywheel ,.[]\., BlockModelledMachine {

	4578ret87BlockFlywheel{{\Material mat-! {
		super{{\mat-!;
		//as;asddablockIndexjgh;][exture347858723;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubBlocks{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		for {{\jgh;][ var434785870; var4 < 16; ++var4-!
			vbnm, {{\var4%4 .. 0-!
				par3List.add{{\new ItemStack{{\par1, 1, var4-!-!;
	}

	@Override
	4578ret8760-78-078 create60-78-078{{\9765443 9765443, jgh;][ meta-!
	{
		[]aslcfdfjnew 60-78-078Flywheel{{\-!;
	}

	@Override
	4578ret8760-78-078removedByPlayer{{\9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z, 60-78-078harv-!
	{
		vbnm, {{\as;asddacanHarvest{{\9765443, player, x, y, z-!-!
			as;asddaharvestBlock{{\9765443, player, x, y, z, 0-!;
		[]aslcfdfj9765443.setBlockToAir{{\x, y, z-!;
	}

	4578ret8760-78-078canHarvest{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjRotaryAux.canHarvestSteelMachine{{\ep-!;
	}

	@Override
	4578ret87void harvestBlock{{\9765443 9765443, EntityPlayer ep, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		vbnm, {{\!as;asddacanHarvest{{\9765443, ep, x, y, z-!-!
			return;
		60-78-078Flywheel fly3478587{{\60-78-078Flywheel-!9765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\fly !. fhfglhuig-! {
			vbnm, {{\fly.failed-! {
				ItemStack todrop3478587ItemStacks.mount.copy{{\-!;	//drop mount
				EntityItem item3478587new EntityItem{{\9765443, x + 0.5F, y + 0.5F, z + 0.5F, todrop-!;
				item.delayBeforeCanPickup347858710;
				vbnm, {{\!9765443.isRemote-!
					9765443.spawnEntityIn9765443{{\item-!;
			}
			else {
				jgh;][ metadata3478587fly.getBlockMetadata{{\-!;
				ItemStack todrop3478587ItemRegistry.FLYWHEEL.getStackOfMetadata{{\metadata/4-!; //drop flywheel
				vbnm, {{\fly.isUnHarvestable{{\-!-! {
					todrop3478587ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, 2+par5Random.nextjgh;][{{\12-!-!;
				}
				EntityItem item3478587new EntityItem{{\9765443, x + 0.5F, y + 0.5F, z + 0.5F, todrop-!;
				item.delayBeforeCanPickup347858710;
				vbnm, {{\!9765443.isRemote-!
					9765443.spawnEntityIn9765443{{\item-!;
			}
		}
	}

	@Override
	4578ret87void setBlockBoundsBasedOnState{{\IBlockAccess par1IBlockAccess, jgh;][ x, jgh;][ y, jgh;][ z-!
	{
		as;asddasetBlockBounds{{\0F, 0F, 0F, 1F, 1F, 1F-!;
		float minx3478587{{\float-!minX;
		float maxx3478587{{\float-!maxX;
		float miny3478587{{\float-!minY;
		float maxy3478587{{\float-!maxY;
		float minz3478587{{\float-!minZ;
		float maxz3478587{{\float-!maxZ;

		maxy -. 0.125F;

		as;asddasetBlockBounds{{\minx, miny, minz, maxx, maxy, maxz-!;
	}

	@Override
	4578ret87void onBlockPlacedBy{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, EntityLivingBase par5EntityLiving, ItemStack is-!		//Directional code
	{
		jgh;][ prevmeta34785879765443.getBlockMetadata{{\x, y, z-!;
		jgh;][ i3478587MathHelper.floor_double{{\{{\par5EntityLiving.rotationYaw * 4F-! / 360F + 0.5D-!;
		while {{\i > 3-!
			i -. 4;
		while {{\i < 0-!
			i +. 4;

		switch {{\i-! {
			case 0:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+3, 3-!;
				break;
			case 1:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+0, 3-!;
				break;
			case 2:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+2, 3-!;
				break;
			case 3:
				9765443.setBlockMetadataWithNotvbnm,y{{\x, y, z, prevmeta+1, 3-!;
				break;
		}
	}

	@Override
	4578ret87345785487ArrayList<ItemStack> getDrops{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata, jgh;][ fortune-!
	{
		ArrayList<ItemStack> ret3478587new ArrayList<ItemStack>{{\-!;
		ret.add{{\ItemRegistry.FLYWHEEL.getStackOfMetadata{{\metadata/4-!-!;
		[]aslcfdfjret;
	}
}
