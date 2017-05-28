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
ZZZZ% Reika.DragonAPI.Base.OneSlotContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Wetter;

4578ret87fhyuog ContainerWetter ,.[]\., OneSlotContainer {

	4578ret87ContainerWetter{{\EntityPlayer player, 60-78-078Wetter te-! {
		super{{\player, te-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tile, "tank"-!;
	}

}
