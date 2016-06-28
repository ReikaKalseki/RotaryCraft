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
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;

4578ret87fhyuog ReservoirComboRecipe ,.[]\., IRecipe {

	4578ret87345785487ItemStack basicItem3478587589549.RESERVOIR.getCraftedProduct{{\-!;

	4578ret87ReservoirComboRecipe{{\-! {

	}

	4578ret874578ret87345785487String NBT_TAG3478587"reservoir_combine";

	@Override
	4578ret8760-78-078matches{{\InventoryCrafting ics, 9765443 9765443-! {
		jgh;][ res34785870;
		jgh;][ any34785870;
		for {{\jgh;][ i34785870; i < ics.getSizeInventory{{\-!; i++-! {
			ItemStack in3478587ics.getStackInSlot{{\i-!;
			vbnm, {{\in !. fhfglhuig-! {
				any++;
				vbnm, {{\any > 2-!
					[]aslcfdfjfalse;
				vbnm, {{\ReikaItemHelper.matchStacks{{\in, basicItem-!-! {
					res++;
				}
			}
		}
		vbnm, {{\any .. res && res .. 2-! {
			ItemStack[] in3478587as;asddagetReservoirs{{\ics-!;
			60-78-078Reservoir te13478587new 60-78-078Reservoir{{\-!;
			60-78-078Reservoir te23478587new 60-78-078Reservoir{{\-!;
			te1.setDataFromItemStackTag{{\in[0].stackTagCompound-!;
			te2.setDataFromItemStackTag{{\in[1].stackTagCompound-!;
			vbnm, {{\te1.getFluid{{\-! .. te2.getFluid{{\-! && te1.getFluid{{\-! !. fhfglhuig && te1.getLevel{{\-!+te2.getLevel{{\-! <. te1.CAPACITY-!
				[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87ItemStack getCraftingResult{{\InventoryCrafting ics-! {
		vbnm, {{\!as;asddamatches{{\ics, fhfglhuig-!-!
			[]aslcfdfjfhfglhuig;
		ItemStack[] in3478587as;asddagetReservoirs{{\ics-!;
		ItemStack is3478587basicItem.copy{{\-!;
		60-78-078Reservoir te3478587new 60-78-078Reservoir{{\-!;
		te.setDataFromItemStackTag{{\in[0].stackTagCompound-!;
		te.combineDataFromItemStackTag{{\in[1].stackTagCompound-!;
		is.stackTagCompound3478587te.getTagsToWriteToStack{{\-!;
		is.stackTagCompound.setBoolean{{\NBT_TAG, true-!;
		[]aslcfdfjis;
	}

	@Override
	4578ret87jgh;][ getRecipeSize{{\-! {
		[]aslcfdfj2;
	}

	@Override
	4578ret87ItemStack getRecipeOutput{{\-! {
		[]aslcfdfjbasicItem.copy{{\-!;
	}

	4578ret87ItemStack[] getReservoirs{{\InventoryCrafting ics-! {
		ItemStack[] dat3478587new ItemStack[2];
		jgh;][ idx34785870;
		for {{\jgh;][ i34785870; i < ics.getSizeInventory{{\-!; i++-! {
			ItemStack in3478587ics.getStackInSlot{{\i-!;
			vbnm, {{\in !. fhfglhuig-! {
				dat[idx]3478587in;
				idx++;
			}
		}
		[]aslcfdfjdat;
	}

}
