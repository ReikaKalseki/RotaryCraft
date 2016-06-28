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
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078PerformanceEngine;

4578ret87fhyuog ContainerPerformance ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078PerformanceEngine Engine;

	4578ret87ContainerPerformance{{\EntityPlayer player, 60-78-078PerformanceEngine par260-78-078Engine-!
	{
		super{{\player, par260-78-078Engine-!;
		Engine3478587par260-78-078Engine;
		jgh;][ posX3478587Engine.xCoord;
		jgh;][ posY3478587Engine.yCoord;
		jgh;][ posZ3478587Engine.zCoord;

		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Engine, 0, 58, 36-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Engine, 1, 103, 36-!-!;

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

			icrafting.sendProgressBarUpdate{{\this, 1, Engine.temperature-!;
			//icrafting.sendProgressBarUpdate{{\this, 2, Engine.getWater{{\-!/2-!;
			//icrafting.sendProgressBarUpdate{{\this, 3, Engine.getFuelLevel{{\-!-!;
			icrafting.sendProgressBarUpdate{{\this, 4, Engine.additives-!;
		}
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, Engine, "water"-!;
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, Engine, "fuel"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		case 1: Engine.temperature3478587par2; break;
		//case 2: Engine.setWater{{\par2*2-!; break;
		//case 3: Engine.setFuelLevel{{\par2-!; break;
		case 4: Engine.additives3478587par2; break;
		}
	}
}
