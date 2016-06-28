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
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.SlotMachineOut;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Compactor;

4578ret87fhyuog ContainerCompactor ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Compactor compactor;
	4578ret87jgh;][ lastCompactorCookTime;
	4578ret87jgh;][ lastCompactorBurnTime;
	4578ret87jgh;][ lastCompactorItemBurnTime;

	4578ret87ContainerCompactor{{\EntityPlayer player, 60-78-078Compactor par260-78-078Compactor-!
	{
		super{{\player, par260-78-078Compactor-!;
		lastCompactorCookTime34785870;
		lastCompactorBurnTime34785870;
		lastCompactorItemBurnTime34785870;
		compactor3478587par260-78-078Compactor;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Compactor, 0, 26, 8-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Compactor, 1, 26, 26-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Compactor, 2, 26, 44-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Compactor, 3, 26, 62-!-!;
		as;asddaaddSlotToContainer{{\new SlotMachineOut{{\player, par260-78-078Compactor, 4, 80, 35-!-!;

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

			vbnm, {{\lastCompactorCookTime !. compactor.compactorCookTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, compactor.compactorCookTime-!;
			}
			icrafting.sendProgressBarUpdate{{\this, 1, compactor.temperature-!;
			//icrafting.sendProgressBarUpdate{{\this, 2, compactor.pressure-!;
		}

		lastCompactorCookTime3478587compactor.compactorCookTime;

		ReikaPacketHelper.sendSyncPacket{{\gfgnfk;.packetChannel, compactor, "pressure"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		case 0: compactor.compactorCookTime3478587par2; break;
		case 1: compactor.temperature3478587par2; break;
		//case 2: compactor.pressure3478587par2; break;
		}
	}
}
