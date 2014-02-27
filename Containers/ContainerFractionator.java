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
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;

public class ContainerFractionator extends CoreContainer
{
	private TileEntityFractionator Fraction;

	public ContainerFractionator(EntityPlayer player, TileEntityFractionator par2TileEntityFractionator)
	{
		super(player, par2TileEntityFractionator);
		Fraction = par2TileEntityFractionator;
		int posX = Fraction.xCoord;
		int posY = Fraction.yCoord;
		int posZ = Fraction.zCoord;

		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 0, 26, 18));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 1, 26, 36));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 2, 26, 54));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 3, 44, 18));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 4, 44, 36));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 5, 44, 54));

		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 6, 98, 36));
		this.addSlotToContainer(new Slot(par2TileEntityFractionator, 7, 119, 52));

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
			icrafting.sendProgressBarUpdate(this, 3, Fraction.storeTime);
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Fraction, "tank");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		//case 1: Fraction.setFuelLevel(par2); break;
		case 2: Fraction.mixTime = par2; break;
		case 3: Fraction.storeTime = par2; break;
		}
	}
}
