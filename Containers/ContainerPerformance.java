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
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerPerformance extends CoreContainer
{
	private TileEntityPerformanceEngine Engine;

	public ContainerPerformance(EntityPlayer player, TileEntityPerformanceEngine par2TileEntityEngine)
	{
		super(player, par2TileEntityEngine);
		Engine = par2TileEntityEngine;
		int posX = Engine.xCoord;
		int posY = Engine.yCoord;
		int posZ = Engine.zCoord;

		this.addSlotToContainer(new Slot(par2TileEntityEngine, 0, 58, 36));
		this.addSlotToContainer(new Slot(par2TileEntityEngine, 1, 103, 36));

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

			icrafting.sendProgressBarUpdate(this, 1, Engine.temperature);
			//icrafting.sendProgressBarUpdate(this, 2, Engine.getWater()/2);
			//icrafting.sendProgressBarUpdate(this, 3, Engine.getFuelLevel());
			icrafting.sendProgressBarUpdate(this, 4, Engine.additives);
		}
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Engine, "water");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Engine, "fuel");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 1: Engine.temperature = par2; break;
		//case 2: Engine.setWater(par2*2); break;
		//case 3: Engine.setFuelLevel(par2); break;
		case 4: Engine.additives = par2; break;
		}
	}
}