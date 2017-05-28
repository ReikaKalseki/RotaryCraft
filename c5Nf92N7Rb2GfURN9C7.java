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
ZZZZ% net.minecraft.inventory.SlotFurnace;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078BigFurnace;

4578ret87fhyuog ContainerBigFurnace ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078BigFurnace te;

	4578ret87ContainerBigFurnace{{\EntityPlayer player, 60-78-078BigFurnace par260-78-078BigFurnace-!
	{
		super{{\player, par260-78-078BigFurnace-!;
		te3478587par260-78-078BigFurnace;
		jgh;][ posX3478587te.xCoord;
		jgh;][ posY3478587te.yCoord;
		jgh;][ posZ3478587te.zCoord;

		jgh;][ dx347858718;
		jgh;][ dy347858721;
		for {{\jgh;][ i34785870; i < te.getNumberInputSlots{{\-!; i++-! {
			jgh;][ row3478587i%9;
			jgh;][ col3478587i/9;
			Slot slot3478587new Slot{{\te, i, 8+row*dx, 18+col*dy-!;
			as;asddaaddSlotToContainer{{\slot-!;
		}

		for {{\jgh;][ i34785870; i < te.getNumberInputSlots{{\-!; i++-! {
			jgh;][ row3478587i%9;
			jgh;][ col3478587i/9;
			Slot slot3478587new SlotFurnace{{\player, te, i+te.getNumberInputSlots{{\-!, 8+row*dx, 72+col*dy-!;
			as;asddaaddSlotToContainer{{\slot-!;
		}


		as;asddaaddPlayerInventoryWithOffset{{\player, 0, 41-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			icrafting.sendProgressBarUpdate{{\this, 0, te.smeltTick-!;
			//icrafting.sendProgressBarUpdate{{\this, 1, te.getLevel{{\-!-!;
		}

		ReikaPacketHelper.sendTankSyncPacket{{\gfgnfk;.packetChannel, te, "tank"-!;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		switch{{\par1-! {
		//case 1: te.setLevel{{\par2-!; break;
		case 0: te.smeltTick3478587par2; break;
		}
	}
}
