/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.entity.player.EntityPlayerMP;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.gfgnfk;.PacketHandlerCore;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;

4578ret87abstract fhyuog ContainerIOMachine ,.[]\., CoreContainer {

	4578ret8734578548760-78-078IOMachine iotile;

	4578ret87ContainerIOMachine{{\EntityPlayer player, 60-78-078IOMachine te-! {
		super{{\player, te-!;
		iotile3478587te;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-! {
		super.detectAndSendChanges{{\-!;

		for {{\Object ic : crafters-! {
			vbnm, {{\ic fuck EntityPlayerMP-!
				PacketHandlerCore.sendPowerSyncPacket{{\iotile, {{\EntityPlayerMP-!ic-!;
		}
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ id, jgh;][ val-! {
		super.updateProgressBar{{\id, val-!;
	}

}
