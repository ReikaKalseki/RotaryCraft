/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntitySteamEngine;

public class ContainerSteam extends ContainerIOMachine
{
	private TileEntitySteamEngine Steam;

	public ContainerSteam(EntityPlayer player, TileEntitySteamEngine te)
	{
		super(player, te);
		Steam = te;
		int posX = Steam.xCoord;
		int posY = Steam.yCoord;
		int posZ = Steam.zCoord;
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

			icrafting.sendProgressBarUpdate(this, 1, Steam.temperature);
			//icrafting.sendProgressBarUpdate(this, 2, Steam.getWater()/2);
		}
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, Steam, "water");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 1: Steam.temperature = par2; break;
		//case 2: Steam.setWater(par2*2); break;
		}
	}
}
