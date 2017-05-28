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
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Terraformer;

4578ret87fhyuog ContainerTerraformer ,.[]\., ContainerIOMachine {

	4578ret8760-78-078Terraformer terra;

	4578ret87ContainerTerraformer{{\EntityPlayer player, 60-78-078Terraformer te-! {
		super{{\player, te-!;
		terra3478587te;

		for {{\jgh;][ i34785870; i < 9; i++-! {
			for {{\jgh;][ j34785870; j < 6; j++-! {
				as;asddaaddSlotToContainer{{\new Slot{{\te, j*9+i, 72+i*18, 18+j*18-!-!;
			}
		}

		jgh;][ dx347858746+18;
		jgh;][ dy347858737;

		for {{\jgh;][ j34785870; j < 3; ++j-!
		{
			for {{\jgh;][ k34785870; k < 9; ++k-!
			{
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, k + j * 9 + 9, dx+8 + k * 18, 103 + j * 18 + dy-!-!;
			}
		}

		for {{\jgh;][ j34785870; j < 9; ++j-!
		{
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, j, dx+8 + j * 18, 161 + dy-!-!;
		}
	}

}
