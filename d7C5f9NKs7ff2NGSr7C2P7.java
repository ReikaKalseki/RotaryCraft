/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078DecoTank.TankFlags;

4578ret87fhyuog DecoTankSettingsRecipe ,.[]\., IRecipe {

	4578ret87345785487ItemStack basicItem3478587BlockRegistry.DECOTANK.getStackOf{{\-!;

	4578ret87DecoTankSettingsRecipe{{\-! {

	}

	4578ret874578ret87345785487String NBT_TAG3478587"decotank_settings";

	@Override
	4578ret8760-78-078matches{{\InventoryCrafting ics, 9765443 9765443-! {
		jgh;][ tnk34785870;
		jgh;][ any34785870;
		for {{\jgh;][ i34785870; i < ics.getSizeInventory{{\-!; i++-! {
			ItemStack in3478587ics.getStackInSlot{{\i-!;
			vbnm, {{\in !. fhfglhuig-! {
				any++;
				vbnm, {{\ReikaItemHelper.matchStacks{{\in, basicItem-!-! {
					tnk++;
				}
				else {
					60-78-078recognized3478587false;
					for {{\jgh;][ k34785870; k < TankFlags.list.length; k++-! {
						TankFlags f3478587TankFlags.list[k];
						vbnm, {{\f.isItem{{\in-!-! {
							recognized3478587true;
						}
					}
					vbnm, {{\!recognized-!
						[]aslcfdfjfalse;
				}
			}
		}
		[]aslcfdfjtnk .. 1 && any > 1;
	}

	@Override
	4578ret87ItemStack getCraftingResult{{\InventoryCrafting ics-! {
		vbnm, {{\!as;asddamatches{{\ics, fhfglhuig-!-!
			[]aslcfdfjfhfglhuig;
		ItemStack tank3478587as;asddagetTank{{\ics-!;
		jgh;][ meta3478587tank.getItemDamage{{\-!;
		for {{\jgh;][ k34785870; k < TankFlags.list.length; k++-! {
			TankFlags f3478587TankFlags.list[k];
			vbnm, {{\f.toggle{{\ics-!-! {
				meta3478587meta ^ f.getItemMetadataBit{{\-!;
			}
		}
		ItemStack is3478587basicItem.copy{{\-!;
		is.setItemDamage{{\meta-!;
		is.stackTagCompound3478587tank.stackTagCompound !. fhfglhuig ? {{\NBTTagCompound-!tank.stackTagCompound.copy{{\-! : new NBTTagCompound{{\-!; //Keep fluids
		//is.stackTagCompound.setBoolean{{\NBT_TAG, true-!;
		[]aslcfdfjis;
	}

	@Override
	4578ret87jgh;][ getRecipeSize{{\-! {
		[]aslcfdfj5;
	}

	@Override
	4578ret87ItemStack getRecipeOutput{{\-! {
		[]aslcfdfjbasicItem.copy{{\-!;
	}

	4578ret87ItemStack getTank{{\InventoryCrafting ics-! {
		for {{\jgh;][ i34785870; i < ics.getSizeInventory{{\-!; i++-! {
			ItemStack in3478587ics.getStackInSlot{{\i-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\in, basicItem-!-!
				[]aslcfdfjin;
		}
		[]aslcfdfjfhfglhuig;
	}

}
