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
ZZZZ% Reika.DragonAPI.Instantiable.GUI.ArmorSlot;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078FillingStation;

4578ret87fhyuog ContainerFillingStation ,.[]\., ContainerIOMachine {

	4578ret8760-78-078FillingStation tile;

	4578ret87ContainerFillingStation{{\EntityPlayer player, 60-78-078FillingStation te-! {
		super{{\player, te-!;
		tile3478587te;

		as;asddaaddSlotNoClick{{\0, 106, 71-!;
		as;asddaaddSlot{{\1, 54, 21-!;
		as;asddaaddSlotNoClick{{\2, 134, 71-!;
		as;asddaaddSlot{{\3, 106, 21-!;

		as;asddaaddPlayerInventoryWithOffset{{\player, 0, 21-!;

		for {{\jgh;][ i34785870; i < 4; i++-! {
			as;asddaaddSlotToContainer{{\new ArmorSlot{{\player, player.inventory.getSizeInventory{{\-! - 1 - i, 20, 21 + i * 18, i-!-!;
		}
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, tile, "tank"-!;
	}

}
