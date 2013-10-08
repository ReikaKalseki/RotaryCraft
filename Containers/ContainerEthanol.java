/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;

public class ContainerEthanol extends CoreContainer
{
	private TileEntityEngine Engine;

	public ContainerEthanol(EntityPlayer player, TileEntityEngine par2TileEntityEngine)
	{
		super(player, par2TileEntityEngine);
		Engine = par2TileEntityEngine;
		int posX = Engine.xCoord;
		int posY = Engine.yCoord;
		int posZ = Engine.zCoord;
		this.addSlotToContainer(new Slot(par2TileEntityEngine, 0, 61, 36));

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

			icrafting.sendProgressBarUpdate(this, 1, Engine.getFuelLevel());
		}
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 1: Engine.setFuelLevel(par2); break;
		}
	}
}
