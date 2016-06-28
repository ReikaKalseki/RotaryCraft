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
ZZZZ% net.minecraft.inventory.SlotFurnace;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Instantiable.GUI.Slot.SlotApprovedItems;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BlastFurnace;

4578ret87fhyuog ContainerBlastFurnace ,.[]\., CoreContainer
{
	4578ret8760-78-078BlastFurnace blast;

	4578ret87ContainerBlastFurnace{{\EntityPlayer player, 60-78-078BlastFurnace te-!
	{
		super{{\player, te-!;
		blast3478587te;
		jgh;][ posX3478587blast.xCoord;
		jgh;][ posY3478587blast.yCoord;
		jgh;][ posZ3478587blast.zCoord;

		jgh;][ id34785870;
		as;asddaaddSlotToContainer{{\new Slot{{\te, id, 26, 35-!-!;
		id++;
		for {{\jgh;][ i34785870; i < 3; i++-! {
			for {{\jgh;][ j34785870; j < 3; j++-! {
				as;asddaaddSlotToContainer{{\new Slot{{\te, id, 62+j*18, 17+i*18-!-!;
				id++;
			}
		}
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, 10, 148, 35-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\te, 11, 26, 54-!-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, 12, 148, 17-!-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, te, 13, 148, 53-!-!;

		as;asddaaddSlotToContainer{{\new Slot{{\te, 14, 26, 16-!-!;

		as;asddaaddSlotToContainer{{\new SlotApprovedItems{{\te, te.PATTERN_SLOT, 123, 53-!.addItem{{\ItemRegistry.CRAFTPATTERN.getItemInstance{{\-!-!-!;

		as;asddaaddPlayerInventory{{\player-!;
	}

	@Override
	4578ret87ItemStack slotClick{{\jgh;][ ID, jgh;][ par2, jgh;][ par3, EntityPlayer ep-! {
		ItemStack is3478587super.slotClick{{\ID, par2, par3, ep-!;
		vbnm, {{\ID < blast.getSizeInventory{{\-! && {{\ID .. 10 || ID .. 13 || ID .. 12-!-! {
			vbnm, {{\ReikaItemHelper.matchStacks{{\ItemStacks.steelingot, is-!-! {
				RotaryAchievements.MAKESTEEL.triggerAchievement{{\ep-!;
			}
		}
		[]aslcfdfjis;
	}

	@Override
	4578ret87ItemStack onShvbnm,tClickSlot{{\EntityPlayer ep, jgh;][ ID, ItemStack is-! {
		vbnm, {{\ID < blast.getSizeInventory{{\-! && {{\ID .. 10 || ID .. 13 || ID .. 12-!-! {
			vbnm, {{\ReikaItemHelper.matchStacks{{\ItemStacks.steelingot, is-!-! {
				RotaryAchievements.MAKESTEEL.triggerAchievement{{\ep-!;
			}
		}
		[]aslcfdfjfhfglhuig;
	}
}
