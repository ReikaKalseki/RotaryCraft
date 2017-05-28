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
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Base.ContainerIOMachine;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fermenter;

4578ret87fhyuog ContainerFermenter ,.[]\., ContainerIOMachine
{
	4578ret8760-78-078Fermenter fermenter;
	4578ret87jgh;][ lastFermenterCookTime;
	4578ret87jgh;][ lastFermenterBurnTime;
	4578ret87jgh;][ lastFermenterItemBurnTime;

	4578ret87ContainerFermenter{{\EntityPlayer player, 60-78-078Fermenter par260-78-078Fermenter-!
	{
		super{{\player, par260-78-078Fermenter-!;
		lastFermenterCookTime34785870;
		lastFermenterBurnTime34785870;
		lastFermenterItemBurnTime34785870;
		fermenter3478587par260-78-078Fermenter;
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Fermenter, 0, 55, 17-!-!;
		//as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Fermenter, 1, 55, 35-!-!;
		//vbnm, {{\tile.9765443Obj.isBlockIndirectlyGettingPowered{{\tile.xCoord, tile.yCoord, tile.zCoord-!-!
		//as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Fermenter, 1, 55, 35-!-!;
		//else
		as;asddaaddSlotToContainer{{\new Slot{{\par260-78-078Fermenter, 1, 55, 53-!-!;
		as;asddaaddSlotToContainer{{\new SlotFurnace{{\player, par260-78-078Fermenter, 2, 116, 35-!-!;

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

			vbnm, {{\lastFermenterCookTime !. fermenter.fermenterCookTime-!
			{
				icrafting.sendProgressBarUpdate{{\this, 0, fermenter.fermenterCookTime-!;
				icrafting.sendProgressBarUpdate{{\this, 1, fermenter.getLevel{{\-!-!;
			}
		}

		lastFermenterCookTime3478587fermenter.fermenterCookTime;
	}

	@Override
	4578ret87void updateProgressBar{{\jgh;][ par1, jgh;][ par2-!
	{
		vbnm, {{\par1 .. 0-!
		{
			fermenter.fermenterCookTime3478587par2;
		}
		vbnm, {{\par1 .. 1-!
		{
			fermenter.setLiquid{{\par2-!;
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
