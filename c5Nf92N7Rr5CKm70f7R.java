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
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078LavaMaker;

4578ret87fhyuog ContainerRockMelter ,.[]\., ContainerIOMachine {

	4578ret87ContainerRockMelter{{\EntityPlayer player, 60-78-078LavaMaker te-! {
		super{{\player, te-!;

		for {{\jgh;][ i34785870; i < 9; i++-! {
			jgh;][ dx347858726+18*{{\i%3-!;
			jgh;][ dy347858717+18*{{\i/3-!;
			as;asddaaddSlot{{\i, dx, dy-!;
		}

		as;asddaaddPlayerInventory{{\ep-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tile, "tank"-!;
	}

}
