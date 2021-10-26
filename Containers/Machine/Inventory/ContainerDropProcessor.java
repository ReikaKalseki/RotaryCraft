/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDropProcessor;

public class ContainerDropProcessor extends ContainerIOMachine
{
	private TileEntityDropProcessor drops;
	private int lastdropProcessTime;
	private int lastOverflow;

	public ContainerDropProcessor(EntityPlayer player, TileEntityDropProcessor te)
	{
		super(player, te);
		lastdropProcessTime = 0;
		drops = te;
		this.addSlotToContainer(new Slot(te, 0, 52, 35));
		this.addSlotToContainer(new SlotFurnace(player, te, 1, 112, 35));

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

			if (lastdropProcessTime != drops.dropProcessTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, drops.dropProcessTime);
			}
			if (lastOverflow != drops.overflowCount)
			{
				icrafting.sendProgressBarUpdate(this, 1, drops.overflowCount);
			}
		}

		lastdropProcessTime = drops.dropProcessTime;
		lastOverflow = drops.overflowCount;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
			case 0: drops.dropProcessTime = par2; break;
			case 1: drops.overflowCount = par2; break;
		}
	}
}
