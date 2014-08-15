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
import Reika.RotaryCraft.TileEntities.Production.TileEntityRefrigerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.SlotFurnace;

public class ContainerFridge extends CoreContainer
{
	private TileEntityRefrigerator fridge;
	private int time;

	public ContainerFridge(EntityPlayer player, TileEntityRefrigerator par2TileEntityRefrigerator)
	{
		super(player, par2TileEntityRefrigerator);
		time = 0;
		fridge = par2TileEntityRefrigerator;

		this.addSlot(0, 99, 58);
		this.addSlotToContainer(new SlotFurnace(player, fridge, 1, 132, 72));

		this.addPlayerInventoryWithOffset(player, 0, 22);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			icrafting.sendProgressBarUpdate(this, 0, fridge.time);
			icrafting.sendProgressBarUpdate(this, 1, fridge.getLevel());
		}

		time = fridge.time;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			fridge.time = par2;
		}
		if (par1 == 1)
		{
			fridge.setLevel(par2);
		}
	}
}