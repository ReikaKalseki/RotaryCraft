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
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Grinder;

4578ret87fhyuog ContainerGrinder ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Grinder grinder;
	4578ret87jgh;][ lastGrinderCookTime;

	4578ret87ContainerGrinder{{\EntityPlayer player, 60-78-078Grinder te-!
	{
		super{{\player, te-!;
		lastGrinderCookTime34785870;
		grinder3478587te;
		as;asddaaddSlotToContainer{{\new Slot{{\te, 0, 76, 35-!-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, 1, 136, 35-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\te, 2, 35, 60-!-!;

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

			vbnm, {{\lastGrinderCookTime !. grinder.grinderCookTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, grinder.grinderCookTime-!;
			}
			icrafting.sendProgressBarUpdate{{\this, 1, grinder.getLevel{{\-!-!;
		}

		lastGrinderCookTime3478587grinder.grinderCookTime;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
			case 1: grinder.setLevel{{\par2-!; break;
			case 0: grinder.grinderCookTime3478587par2; break;
		}
	}
}
