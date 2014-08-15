/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerEnergyToPower extends CoreContainer {

	private EnergyToPowerBase engine;

	public ContainerEnergyToPower(EntityPlayer player, EnergyToPowerBase te) {
		super(player, te);
		engine = te;
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		ReikaPacketHelper.sendSyncPacket(RotaryCraft.packetChannel, engine, "storedEnergy");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, engine, "tank");
	}

}