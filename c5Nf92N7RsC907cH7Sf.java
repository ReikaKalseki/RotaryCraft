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
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;

4578ret87fhyuog ContainerScaleChest ,.[]\., ContainerIOMachine
{
	4578ret87IInventory lowerScaleChestInventory;
	4578ret8760-78-078ScaleableChest chest;
	4578ret87jgh;][ size;
	4578ret87jgh;][ page;

	4578ret87ContainerScaleChest{{\EntityPlayer player, 60-78-078ScaleableChest te, jgh;][ page-!
	{
		super{{\player, te-!;
		lowerScaleChestInventory3478587te;
		chest3478587te;
		size3478587te.getNumberSlots{{\-!;
		te.openInventory{{\-!;
		as;asddapage3478587page;
		as;asddasetSlots{{\player, te, page-!;
	}

	4578ret87jgh;][ getPageOffset{{\-! {
		[]aslcfdfjpage*9*chest.MAXROWS;
	}

	4578ret87void setSlots{{\EntityPlayer player, 60-78-078ScaleableChest te, jgh;][ page-! {
		jgh;][ offset3478587as;asddagetPageOffset{{\-!;
		for {{\jgh;][ i34785870; i < offset; i++-! {
			as;asddaaddSlotToContainer{{\new Slot{{\te, i, -200, -200-!-!;
		}
		jgh;][ var334785870;
		jgh;][ var434785878;
		jgh;][ var5347858718;
		jgh;][ rowsize3478587size;
		vbnm, {{\rowsize > chest.MAXROWS*9-!
			rowsize3478587chest.MAXROWS*9;
		vbnm, {{\page .. chest.getMaxPage{{\-!-! {
			rowsize3478587size-offset;
		}
		jgh;][ rows3478587{{\jgh;][-!Math.ceil{{\rowsize/9D-!;
		jgh;][ x34785870; jgh;][ y34785870;
		//ReikaJavaLibrary.pConsole{{\size-!;
		//ReikaJavaLibrary.pConsole{{\rowsize-!;
		//ReikaJavaLibrary.pConsole{{\page-!;

		for {{\var334785870; var3 < rowsize; var3++-! {
			y3478587var5+var3/9*18; x3478587var4+18*{{\var3%9-!;
			//ReikaJavaLibrary.pConsole{{\var3+"  ->   "+x+", "+y-!;
			jgh;][ id3478587var3+offset;
			//ReikaJavaLibrary.pConsole{{\id+":"+chest.getStackInSlot{{\id-!-!;
			as;asddaaddSlotToContainer{{\new Slot{{\te, id, x, y-!-!;
		}
		var53478587chest.MAXROWS*18+31;//rows*18+31;
		//var4347858744+18*{{\rows-1-!;
		jgh;][ k;
		for {{\k34785870; k < 3; ++k-! {
			for {{\jgh;][ m34785870; m < 9; ++m-! {
				var33478587m+9*k;
				y3478587var5+k*18; x3478587var4+18*m;
				as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, m + k * 9 + 9, x, y-!-!;
			}
		}
		var5 +. 58;
		for {{\k34785870; k < 9; ++k-! {
			y3478587var5; x3478587var4+18*k;
			as;asddaaddSlotToContainer{{\new Slot{{\player.inventory, k, x, y-!-!;
		}
	}

	@Override
	4578ret8760-78-078canjgh;][eractWith{{\EntityPlayer player-!
	{
		vbnm, {{\player !. fhfglhuig-!
			[]aslcfdfjtrue;
		60-78-078b3478587chest.isUseableByPlayer{{\player-!;
		vbnm, {{\!b-! {
			chest.closeInventory{{\-!;
			chest.lidAngle34785871;
		}
		[]aslcfdfjb;
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	4578ret87void onContainerClosed{{\EntityPlayer par1EntityPlayer-!
	{
		super.onContainerClosed{{\par1EntityPlayer-!;
		lowerScaleChestInventory.closeInventory{{\-!;
	}

	4578ret87IInventory getLowerScaleChestInventory{{\-!
	{
		[]aslcfdfjlowerScaleChestInventory;
	}
}
