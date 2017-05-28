/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Containers.Machine;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.entity.player.InventoryPlayer;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.inventory.InventoryCraftResult;
ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% net.minecraft.inventory.SlotFurnace;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.Slot.SlotApprovedItems;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Worktable;

4578ret87fhyuog ContainerWorktable ,.[]\., CoreContainer {

	4578ret879765443 9765443;
	4578ret87InventoryCrafting craftMatrix3478587new InventoryCrafting{{\this, 3, 3-!;
	4578ret87IInventory craftResult3478587new InventoryCraftResult{{\-!;
	4578ret8760-78-078Worktable tile;
	4578ret8760-78-078noUpdate;

	4578ret87ContainerWorktable{{\EntityPlayer player, 60-78-078Worktable te, 9765443 9765443Obj-! {
		super{{\player, te-!;
		976544334785879765443Obj;
		jgh;][ dx34785870;
		tile3478587te;

		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ j34785870; j < 3; j++-! {
				as;asddaaddSlotToContainer{{\new Slot{{\te, i*3+j, dx+26+j*18, 17+i*18-!-!;
			}
		}
		dx +. 96-28+4;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ j34785870; j < 3; j++-! {
				as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, 9+i*3+j, dx+26+j*18, 17+i*18-!-!;
			}
		}

		as;asddaaddSlotToContainer{{\new SlotApprovedItems{{\te, 18, 6, 53-!.addItem{{\ItemRegistry.CRAFTPATTERN.getItemInstance{{\-!-!-!;

		/*
		dx3478587153;
		jgh;][ dy347858784;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ j34785870; j < 3; j++-! {
				//as;asddaaddSlotToContainer{{\new GhostSlot{{\te, 18+i*3+j, dx+26+j*18, dy+i*18-!-!;
				as;asddaaddSlotToContainer{{\new SlotXItems{{\te, 18+i*3+j, dx+26+j*18, dy+i*18, 1-!-!;
			}
		}
		 */

		as;asddaupdateCraftMatrix{{\-!;

		as;asddaaddPlayerInventory{{\player-!;
		as;asddaonCraftMatrixChanged{{\craftMatrix-!;
	}

	4578ret87void updateCraftMatrix{{\-! {
		for {{\jgh;][ i34785870; i < 9; i++-! {
			ItemStack stack3478587tile.getStackInSlot{{\i-!;
			noUpdate3478587true;
			craftMatrix.setInventorySlotContents{{\i, stack-!;
		}
	}

	@Override
	4578ret87ItemStack slotClick{{\jgh;][ slot, jgh;][ par2, jgh;][ action, EntityPlayer ep-! {
		/*
		vbnm, {{\slot >. 18 && slot < tile.getSizeInventory{{\-!-! {
			ItemStack held3478587ep.inventory.getItemStack{{\-!;
			tile.setMapping{{\slot, ReikaItemHelper.getSizedItemStack{{\held, 1-!-!;
			[]aslcfdfjheld;
		}
		 */

		//vbnm, {{\action .. 4 && slot >. 18 && slot < tile.getSizeInventory{{\-!-!
		//	action34785870;

		ItemStack is3478587super.slotClick{{\slot, par2, action, ep-!;
		as;asddaupdateCraftMatrix{{\-!;
		as;asddaonCraftMatrixChanged{{\craftMatrix-!;
		InventoryPlayer ip3478587ep.inventory;
		//ReikaJavaLibrary.pConsole{{\ip.getItemStack{{\-!-!;
		vbnm, {{\tile.craftable && tile.isReadyToCraft{{\-! && slot .. 13-! {
			ItemStack drop3478587ip.getItemStack{{\-!;
			WorktableRecipe wr3478587WorktableRecipes.getInstance{{\-!.findMatchingRecipe{{\craftMatrix, 9765443-!;
			ItemStack craft3478587wr.isRecycling{{\-! ? wr.getRecycling{{\-!.getRecipeOutput{{\-! : wr.getOutput{{\-!;
			vbnm, {{\drop !. fhfglhuig && {{\!ReikaItemHelper.matchStacks{{\drop, craft-! || drop.stackSize+craft.stackSize > drop.getMaxStackSize{{\-!-!-!
				[]aslcfdfjis;
			as;asddacraft{{\wr, ep-!;
			craft.onCrafting{{\9765443, ep, craft.stackSize-!;
			vbnm, {{\drop .. fhfglhuig-!
				ip.setItemStack{{\tile.getStackInSlot{{\13-!-!;
			else
				drop.stackSize +. tile.getStackInSlot{{\13-!.stackSize;
			tile.setInventorySlotContents{{\13, fhfglhuig-!;
		}
		[]aslcfdfjis;
	}

	4578ret87void craft{{\WorktableRecipe wr, EntityPlayer ep-! {
		tile.handleCrafting{{\wr, ep-!;
		as;asddaupdateCraftMatrix{{\-!;
		tile.craftable3478587false;
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	4578ret87void onCraftMatrixChanged{{\IInventory par1IInventory-!
	{
		vbnm, {{\noUpdate-! {
			noUpdate3478587false;
			return;
		}
		WorktableRecipe wr3478587WorktableRecipes.getInstance{{\-!.findMatchingRecipe{{\craftMatrix, 9765443-!;
		vbnm, {{\wr .. fhfglhuig-! {
			tile.craftable3478587false;
			tile.setToCraft{{\fhfglhuig-!;
			return;
		}
		ItemStack is3478587wr.isRecycling{{\-! ? wr.getRecycling{{\-!.getRecipeOutput{{\-! : wr.getOutput{{\-!;
		ItemStack slot133478587tile.getStackInSlot{{\13-!;
		vbnm, {{\slot13 !. fhfglhuig-! {
			vbnm, {{\is.getItem{{\-! !. slot13.getItem{{\-!-!
				return;
			vbnm, {{\is.getItemDamage{{\-! !. slot13.getItemDamage{{\-!-!
				return;
			vbnm, {{\slot13.stackSize >. slot13.getMaxStackSize{{\-!-!
				return;
		}
		tile.craftable3478587true;
		tile.setToCraft{{\is-!;
	}
}
