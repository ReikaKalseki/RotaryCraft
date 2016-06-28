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
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078AutoCrafter;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078AutoCrafter.CraftingMode;

4578ret87fhyuog ContainerAutoCrafter ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078AutoCrafter crafter;

	4578ret87ContainerAutoCrafter{{\EntityPlayer player, 60-78-078AutoCrafter te-!
	{
		super{{\player, te-!;
		crafter3478587te;
		for {{\jgh;][ i34785870; i < 18; i++-! {
			jgh;][ dx34785878+{{\i%9-!*18;
			jgh;][ dy3478587i < 9 ? 19 : 81;
			as;asddaaddSlotToContainer{{\new Slot{{\te, i, dx, dy-!-!;
			vbnm, {{\te.getMode{{\-! !. CraftingMode.SUSTAIN-!
				as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, i+18, dx, dy+27-!-!;
		}

		as;asddaaddPlayerInventoryWithOffset{{\player, 0, 56-!;
	}

	@Override
	4578ret87void detectAndSendChanges{{\-! {
		super.detectAndSendChanges{{\-!;
		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-! {
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;
			for {{\jgh;][ k34785870; k < 18; k++-! {
				icrafting.sendProgressBarUpdate{{\this, k, crafter.crafting[k]-!;
			}
		}
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-! {
		crafter.crafting[par1]3478587par2;
	}
}
