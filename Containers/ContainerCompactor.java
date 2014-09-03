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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.SlotMachineOut;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;

public class ContainerCompactor extends CoreContainer
{
	private TileEntityCompactor compactor;
	private int lastCompactorCookTime;
	private int lastCompactorBurnTime;
	private int lastCompactorItemBurnTime;

	public ContainerCompactor(EntityPlayer player, TileEntityCompactor par2TileEntityCompactor)
	{
		super(player, par2TileEntityCompactor);
		lastCompactorCookTime = 0;
		lastCompactorBurnTime = 0;
		lastCompactorItemBurnTime = 0;
		compactor = par2TileEntityCompactor;
		this.addSlotToContainer(new Slot(par2TileEntityCompactor, 0, 26, 8));
		this.addSlotToContainer(new Slot(par2TileEntityCompactor, 1, 26, 26));
		this.addSlotToContainer(new Slot(par2TileEntityCompactor, 2, 26, 44));
		this.addSlotToContainer(new Slot(par2TileEntityCompactor, 3, 26, 62));
		this.addSlotToContainer(new SlotMachineOut(player, par2TileEntityCompactor, 4, 80, 35));

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

			if (lastCompactorCookTime != compactor.compactorCookTime)
			{
				icrafting.sendProgressBarUpdate(this, 0, compactor.compactorCookTime);
			}
			icrafting.sendProgressBarUpdate(this, 1, compactor.temperature);
			//icrafting.sendProgressBarUpdate(this, 2, compactor.pressure);
		}

		lastCompactorCookTime = compactor.compactorCookTime;

		ReikaPacketHelper.sendSyncPacket(RotaryCraft.packetChannel, compactor, "pressure");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 0: compactor.compactorCookTime = par2; break;
		case 1: compactor.temperature = par2; break;
		//case 2: compactor.pressure = par2; break;
		}
	}
}
