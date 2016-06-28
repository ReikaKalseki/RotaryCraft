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
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Crystallizer;

4578ret87fhyuog ContainerCrystallizer ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Crystallizer te;

	4578ret87ContainerCrystallizer{{\EntityPlayer player, 60-78-078Crystallizer par260-78-078Crystallizer-!
	{
		super{{\player, par260-78-078Crystallizer-!;
		te3478587par260-78-078Crystallizer;
		jgh;][ posX3478587te.xCoord;
		jgh;][ posY3478587te.yCoord;
		jgh;][ posZ3478587te.zCoord;

		as;asddaaddSlot{{\0, 80, 35-!;
		as;asddaaddSlot{{\1, 125, 35-!;

		as;asddaaddPlayerInventory{{\player-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			icrafting.sendProgressBarUpdate{{\this, 0, te.freezeTick-!;
			//icrafting.sendProgressBarUpdate{{\this, 1, te.getLevel{{\-!-!;
		}

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "tank"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		//case 1: te.setLevel{{\par2-!; break;
		case 0: te.freezeTick3478587par2; break;
		}
	}
}
