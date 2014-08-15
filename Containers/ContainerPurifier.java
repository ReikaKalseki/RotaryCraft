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
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPurifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerPurifier extends CoreContainer
{
	private TileEntityPurifier purifier;
	private int lastPurifierCookTime;
	private int lastPurifierBurnTime;
	private int lastPurifierItemBurnTime;

	public ContainerPurifier(EntityPlayer player, TileEntityPurifier par2TileEntityPurifier)
	{
		super(player, par2TileEntityPurifier);
		lastPurifierCookTime = 0;
		lastPurifierBurnTime = 0;
		lastPurifierItemBurnTime = 0;
		purifier = par2TileEntityPurifier;
		this.addSlotToContainer(new Slot(par2TileEntityPurifier, 0, 35, 16));
		this.addSlotToContainer(new Slot(par2TileEntityPurifier, 7, 53, 16));

		for (int i = 0; i < 5; i++)
			this.addSlotToContainer(new Slot(par2TileEntityPurifier, i+1, 8+i*18, 52));

		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityPurifier, 6, 134, 34));

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

			if (lastPurifierCookTime != purifier.cookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, purifier.cookTime);
			}
		}

		lastPurifierCookTime = purifier.cookTime;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			purifier.cookTime = par2;
		}
	}
}