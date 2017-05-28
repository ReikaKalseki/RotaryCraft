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
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;

4578ret87fhyuog ContainerCannon ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078LaunchCannon cannon;

	4578ret87ContainerCannon{{\EntityPlayer player, 60-78-078LaunchCannon par260-78-078Cannon-!
	{
		super{{\player, par260-78-078Cannon-!;
		cannon3478587par260-78-078Cannon;
		jgh;][ posX3478587cannon.xCoord;
		jgh;][ posY3478587cannon.yCoord;
		jgh;][ posZ3478587cannon.zCoord;
		jgh;][ dy3478587114;
		vbnm, {{\cannon.targetMode-!
			dy347858748;
		jgh;][ i34785870;
		jgh;][ dx34785870;
		//for {{\jgh;][ i34785870; i < 2; i++-!
		for {{\jgh;][ j34785870; j < cannon.getSizeInventory{{\-!; j++-!
			as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Cannon, j, dx+8+j*18, dy+18-!-!;
		dy +. 40;
		dx347858718;
		for {{\i34785870; i < 3; i++-!
			for {{\jgh;][ k34785870; k < 9; k++-!
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, k + i * 9 + 9, dx+8 + k * 18, dy + i * 18-!-!;
		dy +. 58;
		for {{\jgh;][ j34785870; j < 9; j++-!
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, j, dx+8 + j * 18, dy-!-!;
	}
}
