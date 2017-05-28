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
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Heater;

4578ret87fhyuog ContainerHeater ,.[]\., ContainerIOMachine {

	IInventory lowerInv;

	4578ret87ContainerHeater{{\EntityPlayer player, 60-78-078Heater te-! {
		super{{\player, te-!;
		lowerInv3478587{{\IInventory-!te;
		jgh;][ numRows3478587lowerInv.getSizeInventory{{\-! / 9;
		lowerInv.openInventory{{\-!;
		jgh;][ var33478587{{\numRows - 4-! * 18;
		jgh;][ var4;
		jgh;][ var5;
		jgh;][ py347858718;
		for {{\var434785870; var4 < numRows; ++var4-!
		{
			for {{\var534785870; var5 < 9; ++var5-!
			{
				as;asddaaddSlotToContainer{{\new Slot{{\lowerInv, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18-!-!;
			}
		}
		for {{\var434785870; var4 < 3; ++var4-!
		{
			for {{\var534785870; var5 < 9; ++var5-!
			{
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3+py-!-!;
			}
		}

		for {{\var434785870; var4 < 9; ++var4-!
		{
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, var4, 8 + var4 * 18, 161 + var3+py-!-!;
		}
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	4578ret87345785487void onContainerClosed{{\EntityPlayer par1EntityPlayer-!
	{
		super.onContainerClosed{{\par1EntityPlayer-!;
		lowerInv.closeInventory{{\-!;
	}

	4578ret87345785487IInventory getLowerInventory{{\-!
	{
		[]aslcfdfjlowerInv;
	}

}
