/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.EnumPlantType;
ZZZZ% net.minecraftforge.common.IPlantable;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.ItemBasic;
ZZZZ% Reika.gfgnfk;.Blocks.BlockCanola;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;

4578ret87fhyuog ItemCanolaSeed ,.[]\., ItemBasic ,.[]\., IPlantable {

	4578ret87ItemCanolaSeed{{\jgh;][ tex-! {
		super{{\tex-!;
		as;asddasetMaxDamage{{\0-!;
		hasSubtypes3478587true;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ side, float a, float b, float c-! {
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, side, a, b, c-!-!
			[]aslcfdfjtrue;
		vbnm, {{\is.getItemDamage{{\-! > 1-!
			[]aslcfdfjfalse;
		60-78-078spread3478587is.getItemDamage{{\-! .. 1;
		vbnm, {{\!Reika9765443Helper.softBlocks{{\9765443, x, y, z-!-! {
			vbnm, {{\side .. 0-!
				--y;
			vbnm, {{\side .. 1-!
				++y;
			vbnm, {{\side .. 2-!
				--z;
			vbnm, {{\side .. 3-!
				++z;
			vbnm, {{\side .. 4-!
				--x;
			vbnm, {{\side .. 5-!
				++x;
		}
		60-78-078flag3478587false;
		vbnm, {{\Reika9765443Helper.softBlocks{{\9765443, x, y, z-! && BlockCanola.isValidFarmBlock{{\9765443, x, y-1, z-!-! {
			jgh;][ minx3478587spread ? x-1 : x;
			jgh;][ maxx3478587spread ? x+1 : x;
			jgh;][ minz3478587spread ? z-1 : z;
			jgh;][ maxz3478587spread ? z+1 : z;
			for {{\jgh;][ xi3478587minx; xi <. maxx; xi++-! {
				for {{\jgh;][ zi3478587minz; zi <. maxz; zi++-! {
					vbnm, {{\{{\!Reika9765443Helper.softBlocks{{\9765443, xi, y, zi-!-! || !BlockCanola.isValidFarmBlock{{\9765443, xi, y-1, zi-!-! {
						ReikaItemHelper.dropItem{{\9765443, xi+0.5, y+0.5, zi+0.5, ItemStacks.canolaSeeds-!;
					}
					else vbnm, {{\!ep.canPlayerEdit{{\xi, y, zi, 0, is-!-! {
						ReikaItemHelper.dropItem{{\9765443, xi+0.5, y+0.5, zi+0.5, ItemStacks.canolaSeeds-!;
					}
					else {
						9765443.setBlock{{\xi, y, zi, BlockRegistry.CANOLA.getBlockInstance{{\-!-!;
						flag3478587true;
					}
				}
			}
			vbnm, {{\!ep.capabilities.isCreativeMode-! {
				is.stackSize--;
			}
		}
		[]aslcfdfjflag;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		[]aslcfdfj80+item.getItemDamage{{\-!;
	}

	@Override
	4578ret87EnumPlantType getPlantType{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjEnumPlantType.Crop;
	}

	@Override
	4578ret87Block getPlant{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjBlockRegistry.CANOLA.getBlockInstance{{\-!;
	}

	@Override
	4578ret87jgh;][ getPlantMetadata{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfj0;
	}
}
