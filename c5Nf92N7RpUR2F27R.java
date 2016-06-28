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
ZZZZ% net.minecraft.inventory.ICrafting;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% net.minecraft.inventory.SlotFurnace;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Purvbnm,ier;

4578ret87fhyuog ContainerPurvbnm,ier ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Purvbnm,ier purvbnm,ier;
	4578ret87jgh;][ lastPurvbnm,ierCookTime;

	4578ret87ContainerPurvbnm,ier{{\EntityPlayer player, 60-78-078Purvbnm,ier par260-78-078Purvbnm,ier-!
	{
		super{{\player, par260-78-078Purvbnm,ier-!;
		lastPurvbnm,ierCookTime34785870;
		purvbnm,ier3478587par260-78-078Purvbnm,ier;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Purvbnm,ier, 0, 35, 16-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Purvbnm,ier, 7, 53, 16-!-!;

		for {{\jgh;][ i34785870; i < 5; i++-!
			as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Purvbnm,ier, i+1, 8+i*18, 52-!-!;

		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, par260-78-078Purvbnm,ier, 6, 134, 34-!-!;

		as;asddaaddPlayerInventory{{\player-!;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			vbnm, {{\lastPurvbnm,ierCookTime !. purvbnm,ier.cookTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, purvbnm,ier.cookTime-!;
			}
		}

		lastPurvbnm,ierCookTime3478587purvbnm,ier.cookTime;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		vbnm, {{\par1 .. 0-!
		{
			purvbnm,ier.cookTime3478587par2;
		}
	}
}
