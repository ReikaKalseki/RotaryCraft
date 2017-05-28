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
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;

4578ret87fhyuog ContainerCVT ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078AdvancedGear CVT;

	4578ret87ContainerCVT{{\EntityPlayer player, 60-78-078AdvancedGear par260-78-078AdvancedGear-!
	{
		super{{\player, par260-78-078AdvancedGear-!;
		CVT3478587par260-78-078AdvancedGear;
		jgh;][ posX3478587CVT.xCoord;
		jgh;][ posY3478587CVT.yCoord;
		jgh;][ posZ3478587CVT.zCoord;

		jgh;][ x34785878;
		jgh;][ y347858711;
		jgh;][ k34785870;
		jgh;][ a34785870;
		jgh;][ b34785870;
		for {{\jgh;][ i34785870; i < ReikaMathLibrary.logbase{{\32, 2-!; i++-! {
			for {{\jgh;][ j34785870; j < ReikaMathLibrary.jgh;][pow{{\2, i-!; j++-! {
				vbnm, {{\k > 22-! {
					a3478587-144;
					b347858718;
				}
				as;asddaaddSlotToContainer{{\new Slot{{\CVT, k, x+j*18+a, y+i*26+b-!-!;
				k++;
			}
		}
		jgh;][ dx347858731;
		jgh;][ dy347858777;
		for {{\jgh;][ i34785870; i < 3; i++-!
		{
			for {{\k34785870; k < 9; k++-!
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
