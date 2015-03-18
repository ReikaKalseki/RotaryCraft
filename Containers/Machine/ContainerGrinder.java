/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;

public class ContainerGrinder extends ContainerIOMachine
{
	private TileEntityGrinder grinder;
	private int lastGrinderCookTime;
	private int lastGrinderItemBurnTime;

	public ContainerGrinder(EntityPlayer player, TileEntityGrinder te)
	{
		super(player, te);
		lastGrinderCookTime = 0;
		lastGrinderItemBurnTime = 0;
		grinder = te;
		this.addSlotToContainer(new Slot(te, 0, 76, 35));
		this.addSlotToContainer(new SlotFurnace(player, te, 1, 136, 35));
		this.addSlotToContainer(new Slot(te, 2, 35, 60));

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
