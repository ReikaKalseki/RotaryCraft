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
ZZZZ% net.minecraft.inventory.SlotFurnace;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Refrigerator;

4578ret87fhyuog ContainerFridge ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Refrigerator fridge;
	4578ret87jgh;][ time;

	4578ret87ContainerFridge{{\EntityPlayer player, 60-78-078Refrigerator par260-78-078Refrigerator-!
	{
		super{{\player, par260-78-078Refrigerator-!;
		time34785870;
		fridge3478587par260-78-078Refrigerator;

		as;asddaaddSlot{{\0, 99, 58-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, fridge, 1, 132, 72-!-!;

		as;asddaaddPlayerInventoryWithOffset{{\player, 0, 22-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			icrafting.sendProgressBarUpdate{{\this, 0, fridge.time-!;
			icrafting.sendProgressBarUpdate{{\this, 1, fridge.getLevel{{\-!-!;
		}

		time3478587fridge.time;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		vbnm, {{\par1 .. 0-!
		{
			fridge.time3478587par2;
		}
		vbnm, {{\par1 .. 1-!
		{
			fridge.setLevel{{\par2-!;
		}
	}
}
