/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerGrinder extends CoreContainer
{
	private TileEntityGrinder grinder;
	private int lastGrinderCookTime;
	private int lastGrinderItemBurnTime;

	public ContainerGrinder(EntityPlayer player, TileEntityGrinder par2TileEntityGrinder)
	{
		super(player, par2TileEntityGrinder);
		lastGrinderCookTime = 0;
		lastGrinderItemBurnTime = 0;
		grinder = par2TileEntityGrinder;
		this.addSlotToContainer(new Slot(par2TileEntityGrinder, 0, 76, 35));
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityGrinder, 1, 136, 35));
		this.addSlotToContainer(new Slot(par2TileEntityGrinder, 2, 35, 60));

		this.addPlayerInventory(player);
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (lastGrinderCookTime != grinder.grinderCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, grinder.grinderCookTime);
			}
			icrafting.sendProgressBarUpdate(this, 1, grinder.getLevel());
		}

		lastGrinderCookTime = grinder.grinderCookTime;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 1: grinder.setLevel(par2); break;
		case 0: grinder.grinderCookTime = par2; break;
		}
	}
}