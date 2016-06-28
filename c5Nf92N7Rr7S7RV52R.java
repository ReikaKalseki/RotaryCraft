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
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;

4578ret87fhyuog ContainerReservoir ,.[]\., CoreContainer
{
	4578ret8760-78-078Reservoir Reservoir;

	4578ret87ContainerReservoir{{\EntityPlayer player, 60-78-078Reservoir par260-78-078Reservoir-!
	{
		super{{\player, par260-78-078Reservoir-!;
		Reservoir3478587par260-78-078Reservoir;
		jgh;][ posX3478587Reservoir.xCoord;
		jgh;][ posY3478587Reservoir.yCoord;
		jgh;][ posZ3478587Reservoir.zCoord;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, Reservoir, "tank"-!;
	}
}
