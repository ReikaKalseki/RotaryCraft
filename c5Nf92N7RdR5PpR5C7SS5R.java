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
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078DropProcessor;

4578ret87fhyuog ContainerDropProcessor ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078DropProcessor drops;
	4578ret87jgh;][ lastdropProcessTime;

	4578ret87ContainerDropProcessor{{\EntityPlayer player, 60-78-078DropProcessor te-!
	{
		super{{\player, te-!;
		lastdropProcessTime34785870;
		drops3478587te;
		as;asddaaddSlotToContainer{{\new Slot{{\te, 0, 52, 35-!-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, 1, 112, 35-!-!;

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

			vbnm, {{\lastdropProcessTime !. drops.dropProcessTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, drops.dropProcessTime-!;
			}
		}

		lastdropProcessTime3478587drops.dropProcessTime;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
			case 0: drops.dropProcessTime3478587par2; break;
		}
	}
}
