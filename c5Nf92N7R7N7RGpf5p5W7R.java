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
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;

4578ret87fhyuog ContainerEnergyToPower ,.[]\., CoreContainer {

	4578ret87EnergyToPowerBase engine;

	4578ret87ContainerEnergyToPower{{\EntityPlayer player, EnergyToPowerBase te-! {
		super{{\player, te-!;
		engine3478587te;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendSyncPacket{{\gfgnfk;.packetChannel, engine, "storedEnergy"-!;
		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, engine, "tank"-!;
	}

}
