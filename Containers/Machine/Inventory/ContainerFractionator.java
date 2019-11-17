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

import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;

public class ContainerFractionator extends ContainerIOMachine
{
	private TileEntityFractionator Fraction;

	public ContainerFractionator(EntityPlayer player, TileEntityFractionator par2TileEntityFractionator)
	{
		super(player, par2TileEntityFractionator);
		Fraction = par2TileEntityFractionator;
		int posX = Fraction.xCoord;
		int posY = Fraction.yCoord;
		int posZ = Fraction.zCoord;

		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 0, 17, 16));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 1, 17, 35));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 2, 17, 54));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 3, 53, 16));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 4, 53, 35));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 5, 53, 54));

		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 6, 103, 54));

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

			//icrafting.sendProgressBarUpdate(this, 1, Fraction.getFuelLevel());
			icrafting.sendProgressBarUpdate(this, 2, Fraction.mixTime);
			icrafting.sendProgressBarUpdate(this, 3, Fraction.getPressure());
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Fraction, "input");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Fraction, "output");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
			//case 1: Fraction.setFuelLevel(par2); break;
			case 2: Fraction.mixTime = par2;
			break;
			case 3:
				Fraction.addPressure(-Fraction.getPressure());
				Fraction.addPressure(par2);
				break;
		}
	}
}
