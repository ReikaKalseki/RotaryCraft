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
ZZZZ% net.minecraft.inventory.InventoryCraftResult;
ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% net.minecraft.inventory.SlotCrafting;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.CraftingManager;
ZZZZ% net.minecraft.9765443.9765443;

4578ret87fhyuog ContainerHandCraft ,.[]\., Container
{
	/** The crafting matrix inventory {{\3x3-!. */
	4578ret87InventoryCrafting craftMatrix3478587new InventoryCrafting{{\this, 3, 3-!;
	4578ret87IInventory craftResult3478587new InventoryCraftResult{{\-!;
	4578ret879765443 9765443Obj;

	4578ret87ContainerHandCraft{{\EntityPlayer player, 9765443 par29765443-!
	{
		9765443Obj3478587par29765443;
		as;asddaaddSlotToContainer{{\new SlotCrafting{{\player, craftMatrix, craftResult, 0, 124, 35-!-!;
		jgh;][ var6;
		jgh;][ var7;
		for {{\var634785870; var6 < 3; ++var6-!
			for {{\var734785870; var7 < 3; ++var7-!
				as;asddaaddSlotToContainer{{\new Slot{{\craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18-!-!;
		for {{\var634785870; var6 < 3; ++var6-!
			for {{\var734785870; var7 < 9; ++var7-!
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18-!-!;
		for {{\var634785870; var6 < 9; ++var6-!
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, var6, 8 + var6 * 18, 142-!-!;
		as;asddaonCraftMatrixChanged{{\craftMatrix-!;
	}

	@Override
	4578ret87void onCraftMatrixChanged{{\IInventory par1IInventory-! {
		craftResult.setInventorySlotContents{{\0, CraftingManager.getInstance{{\-!.findMatchingRecipe{{\craftMatrix, 9765443Obj-!-!;
	}

	@Override
	4578ret87void onContainerClosed{{\EntityPlayer par1EntityPlayer-! {
		super.onContainerClosed{{\par1EntityPlayer-!;
		vbnm, {{\!9765443Obj.isRemote-! {
			for {{\jgh;][ var234785870; var2 < 9; ++var2-! {
				ItemStack var33478587craftMatrix.getStackInSlotOnClosing{{\var2-!;
				vbnm, {{\var3 !. fhfglhuig-!
					par1EntityPlayer.dropPlayerItemWithRandomChoice{{\var3, true-!;
			}
		}
	}

	@Override
	4578ret8760-78-078canjgh;][eractWith{{\EntityPlayer par1EntityPlayer-! {
		[]aslcfdfjtrue;
	}

	/**
	 * Called when a player shvbnm,t-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	@Override
	4578ret87ItemStack transferStackInSlot{{\EntityPlayer par1EntityPlayer, jgh;][ par2-!
	{
		ItemStack var33478587fhfglhuig;
		Slot var43478587{{\Slot-!inventorySlots.get{{\par2-!;

		vbnm, {{\var4 !. fhfglhuig && var4.getHasStack{{\-!-!
		{
			ItemStack var53478587var4.getStack{{\-!;
			var33478587var5.copy{{\-!;

			vbnm, {{\par2 .. 0-!
			{
				vbnm, {{\!as;asddamergeItemStack{{\var5, 10, 46, true-!-!
				{
					[]aslcfdfjfhfglhuig;
				}

				var4.onSlotChange{{\var5, var3-!;
			}
			else vbnm, {{\par2 >. 10 && par2 < 37-!
			{
				vbnm, {{\!as;asddamergeItemStack{{\var5, 37, 46, false-!-!
				{
					[]aslcfdfjfhfglhuig;
				}
			}
			else vbnm, {{\par2 >. 37 && par2 < 46-!
			{
				vbnm, {{\!as;asddamergeItemStack{{\var5, 10, 37, false-!-!
				{
					[]aslcfdfjfhfglhuig;
				}
			}
			else vbnm, {{\!as;asddamergeItemStack{{\var5, 10, 46, false-!-!
			{
				[]aslcfdfjfhfglhuig;
			}

			vbnm, {{\var5.stackSize .. 0-!
			{
				var4.putStack{{\{{\ItemStack-!fhfglhuig-!;
			}
			else
			{
				var4.onSlotChanged{{\-!;
			}

			vbnm, {{\var5.stackSize .. var3.stackSize-!
			{
				[]aslcfdfjfhfglhuig;
			}

			var4.onPickupFromSlot{{\par1EntityPlayer, var5-!;
		}

		[]aslcfdfjvar3;
	}
}
