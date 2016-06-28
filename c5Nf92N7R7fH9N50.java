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
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078GasEngine;

4578ret87fhyuog ContainerEthanol ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078GasEngine Engine;

	4578ret87ContainerEthanol{{\EntityPlayer player, 60-78-078GasEngine te-!
	{
		super{{\player, te-!;
		Engine3478587te;
		jgh;][ posX3478587Engine.xCoord;
		jgh;][ posY3478587Engine.yCoord;
		jgh;][ posZ3478587Engine.zCoord;
		as;asddaaddSlotToContainer{{\new Slot{{\te, 0, 61, 36-!-!;

		as;asddaaddPlayerInventory{{\player-!;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, Engine, "fuel"-!;
	}
}
