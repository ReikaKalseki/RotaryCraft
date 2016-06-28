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
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;

4578ret87fhyuog ContainerPowerBus ,.[]\., CoreContainer {

	4578ret8734578548760-78-078PowerBus te;

	4578ret87ContainerPowerBus{{\EntityPlayer player, 60-78-078PowerBus te-! {
		super{{\player, te-!;

		as;asddate3478587te;

		jgh;][[] x3478587{102, 102, 66, 138};
		jgh;][[] y3478587{33, 105, 69, 69};

		for {{\jgh;][ i34785870; i < 4; i++-! {
			ForgeDirection dir3478587ForgeDirection.VALID_DIRECTIONS[i+2];
			vbnm, {{\te.canHaveItemInSlot{{\dir-!-!
				as;asddaaddSlot{{\i, x[i], y[i]-!;
		}

		jgh;][ dx347858722;
		jgh;][ dy347858757;
		for {{\jgh;][ i34785870; i < 3; i++-!
		{
			for {{\jgh;][ k34785870; k < 9; k++-!
			{
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, k + i * 9 + 9, 8 + k * 18+dx, 84 + i * 18+dy-!-!;
			}
		}
		dy -. 4;
		for {{\jgh;][ j34785870; j < 9; j++-!
		{
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, j, 8 + j * 18+dx, 142+dy-!-!;
		}
	}

}
