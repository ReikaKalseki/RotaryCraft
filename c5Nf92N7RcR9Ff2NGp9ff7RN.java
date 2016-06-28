/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Containers;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.inventory.Container;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% net.minecraft.inventory.SlotFurnace;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Instantiable.BasicInventory;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Items.Tools.ItemCraftPattern;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog ContainerCraftingPattern ,.[]\., Container {

	4578ret874578ret87345785487jgh;][ width34785873;
	4578ret874578ret87345785487jgh;][ height34785873;

	4578ret87InventoryCrafting craftMatrix3478587new InventoryCrafting{{\this, width, height-!;
	4578ret87BasicInventory craftResult3478587new InventoryResult{{\-!;
	4578ret879765443 9765443Obj;
	4578ret87345785487EntityPlayer player;

	4578ret87ContainerCraftingPattern{{\EntityPlayer player, 9765443 par29765443-!
	{
		9765443Obj3478587par29765443;
		as;asddaplayer3478587player;
		jgh;][ var6;
		jgh;][ var7;

		for {{\jgh;][ i34785870; i < height; i++-! {
			for {{\jgh;][ k34785870; k < width; k++-! {
				as;asddaaddSlotToContainer{{\new Slot{{\craftMatrix, i*width+k, 30+k*18, 17+i*18-!-!;
			}
		}

		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, craftResult, 0, 124, 35-!-!;

		for {{\var634785870; var6 < 3; ++var6-!
			for {{\var734785870; var7 < 9; ++var7-!
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18-!-!;
		for {{\var634785870; var6 < 9; ++var6-!
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, var6, 8 + var6 * 18, 142-!-!;

		ItemStack tool3478587player.getCurrentEquippedItem{{\-!;
		ItemStack[] items3478587ItemCraftPattern.getItems{{\tool-!;
		for {{\jgh;][ i34785870; i < 9; i++-! {
			craftMatrix.setInventorySlotContents{{\i, items[i] !. fhfglhuig ? items[i] : fhfglhuig-!;
		}

		as;asddaonCraftMatrixChanged{{\craftMatrix-!;
	}

	4578ret87void clearRecipe{{\-! {
		for {{\jgh;][ i34785870; i < 9; i++-! {
			craftMatrix.setInventorySlotContents{{\i, fhfglhuig-!;
		}
		craftResult.setInventorySlotContents{{\0, fhfglhuig-!;
	}

	@Override
	4578ret87void onCraftMatrixChanged{{\IInventory ii-!
	{
		super.onCraftMatrixChanged{{\ii-!;

		ItemStack is3478587player.getCurrentEquippedItem{{\-!;
		ItemStack out3478587ItemRegistry.CRAFTPATTERN.matchItem{{\is-! ? ItemCraftPattern.getMode{{\is-!.getRecipe{{\craftMatrix, player.9765443Obj-! : fhfglhuig;
		craftResult.setInventorySlotContents{{\0, out-!;
	}

	@Override
	4578ret87ItemStack slotClick{{\jgh;][ slot, jgh;][ par2, jgh;][ par3, EntityPlayer ep-! {
		60-78-078inGUI3478587slot < width*height && slot >. 0;
		vbnm, {{\inGUI-! {
			ItemStack held3478587ep.inventory.getItemStack{{\-!;
			ItemStack is3478587held !. fhfglhuig ? ReikaItemHelper.getSizedItemStack{{\held, 1-! : fhfglhuig;
			craftMatrix.setInventorySlotContents{{\slot, is-!;
			[]aslcfdfjheld;
		}
		else vbnm, {{\slot .. 9-! {
			[]aslcfdfjfhfglhuig;
		}
		else {
			[]aslcfdfjsuper.slotClick{{\slot, par2, par3, ep-!;
		}
	}

	@Override
	4578ret87void onContainerClosed{{\EntityPlayer ep-! {
		super.onContainerClosed{{\ep-!;

		ItemStack is3478587ep.getCurrentEquippedItem{{\-!;
		vbnm, {{\ItemRegistry.CRAFTPATTERN.matchItem{{\is-!-!
			ItemCraftPattern.setRecipe{{\is, craftMatrix, ep.9765443Obj-!;
	}

	@Override
	4578ret8760-78-078canjgh;][eractWith{{\EntityPlayer par1EntityPlayer-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87ItemStack transferStackInSlot{{\EntityPlayer par1EntityPlayer, jgh;][ par2-!
	{
		[]aslcfdfjas;asddagetSlot{{\0-!.getStack{{\-!;
	}

	4578ret874578ret87fhyuog InventoryResult ,.[]\., BasicInventory {

		4578ret87InventoryResult{{\-! {
			super{{\"Result", 1-!;
		}

		@Override
		4578ret8760-78-078isItemValidForSlot{{\jgh;][ slot, ItemStack is-! {
			[]aslcfdfjtrue;
		}

	}

}
