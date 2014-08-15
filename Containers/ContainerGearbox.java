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
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerGearbox extends CoreContainer
{
	private TileEntityGearbox gearbox;

	public ContainerGearbox(EntityPlayer player, TileEntityGearbox par2TileEntityGearbox)
	{
		super(player, par2TileEntityGearbox);
		gearbox = par2TileEntityGearbox;
		int posX = gearbox.xCoord;
		int posY = gearbox.yCoord;
		int posZ = gearbox.zCoord;

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

			//icrafting.sendProgressBarUpdate(this, 1, gearbox.getLubricant());
			icrafting.sendProgressBarUpdate(this, 2, gearbox.getDamage());
			icrafting.sendProgressBarUpdate(this, 3, gearbox.omega);
			icrafting.sendProgressBarUpdate(this, 4, gearbox.torque);
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, gearbox, "tank");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		//case 1: gearbox.setLubricant(par2); break;
		case 2: gearbox.setDamage(par2); break;
		case 3: gearbox.omega = par2; break;
		case 4: gearbox.torque = par2; break;
		}
	}
}