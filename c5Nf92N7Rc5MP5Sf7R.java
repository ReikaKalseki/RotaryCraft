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
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Base.CoreContainer;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Composter;

4578ret87fhyuog ContainerComposter ,.[]\., CoreContainer
{
	4578ret8760-78-078Composter composter;
	4578ret87jgh;][ lastComposterCookTime;

	4578ret87ContainerComposter{{\EntityPlayer player, 60-78-078Composter par260-78-078Composter-!
	{
		super{{\player, par260-78-078Composter-!;
		lastComposterCookTime34785870;
		composter3478587par260-78-078Composter;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Composter, 0, 55, 26-!-!;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Composter, 1, 55, 44-!-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, par260-78-078Composter, 2, 116, 35-!-!;

		as;asddaaddPlayerInventory{{\player-!;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	4578ret87void detectAndSendChanges{{\-!
	{
		super.detectAndSendChanges{{\-!;

		for {{\jgh;][ i34785870; i < crafters.size{{\-!; i++-!
		{
			ICrafting icrafting3478587{{\ICrafting-!crafters.get{{\i-!;

			vbnm, {{\lastComposterCookTime !. composter.composterCookTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, composter.composterCookTime-!;
			}
		}

		lastComposterCookTime3478587composter.composterCookTime;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		vbnm, {{\par1 .. 0-!
		{
			composter.composterCookTime3478587par2;
		}
	}

	@Override
	4578ret87ItemStack slotClick{{\jgh;][ par1, jgh;][ par2, jgh;][ par3, EntityPlayer ep-! {
		ItemStack is3478587super.slotClick{{\par1, par2, par3, ep-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\ItemRegistry.YEAST.getStackOf{{\-!, is-!-!
			RotaryAchievements.MAKEYEAST.triggerAchievement{{\ep-!;
		[]aslcfdfjis;
	}
}
