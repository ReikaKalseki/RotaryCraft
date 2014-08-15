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
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerPulseFurnace extends CoreContainer {
	private TileEntityPulseFurnace pulseFurnace;

	public ContainerPulseFurnace(EntityPlayer player, TileEntityPulseFurnace par2TileEntityPulseFurnace)
	{
		super(player, par2TileEntityPulseFurnace);
		pulseFurnace = par2TileEntityPulseFurnace;

		this.addSlotToContainer(new Slot(par2TileEntityPulseFurnace, 0, 125, 16));
		//addSlotToContainer(new Slot(par2TileEntityPulseFurnace, 1, 70, 34));
		this.addSlotToContainer(new SlotFurnace(player, par2TileEntityPulseFurnace, 2, 125, 52));

		this.addPlayerInventory(player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, pulseFurnace.pulseFurnaceCookTime);
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
			icrafting.sendProgressBarUpdate(this, 0, pulseFurnace.pulseFurnaceCookTime);
			icrafting.sendProgressBarUpdate(this, 1, pulseFurnace.temperature);
			icrafting.sendProgressBarUpdate(this, 2, pulseFurnace.smelttick);
			//icrafting.sendProgressBarUpdate(this, 3, pulseFurnace.getFuel());
			icrafting.sendProgressBarUpdate(this, 4, pulseFurnace.omega);
			//icrafting.sendProgressBarUpdate(this, 5, pulseFurnace.getWater());
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, pulseFurnace, "water");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, pulseFurnace, "fuel");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 0: pulseFurnace.pulseFurnaceCookTime = par2; break;
		case 1: pulseFurnace.temperature = par2; break;
		case 2: pulseFurnace.smelttick = par2; break;
		//case 3: pulseFurnace.setFuel(par2); break;
		case 4: pulseFurnace.omega = par2; break;
		//case 5: pulseFurnace.setWater(par2); break;
		}
	}
}