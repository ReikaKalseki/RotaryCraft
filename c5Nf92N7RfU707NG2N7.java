/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87fhyuog ContainerFuelEngine ,.[]\., CoreContainer {

	4578ret8734578548760-78-078FuelEngine engine;

	4578ret87ContainerFuelEngine{{\EntityPlayer player, 60-78-078FuelEngine te-! {
		super{{\player, te-!;
		engine3478587te;
		as;asddaaddPlayerInventory{{\player-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, engine, "tank"-!;
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, engine, "watertank"-!;
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, engine, "lubetank"-!;

		ReikaPacketHelper.sendSyncPacket{{\gfgnfk;.packetChannel, engine, "temperature"-!;
	}

}
