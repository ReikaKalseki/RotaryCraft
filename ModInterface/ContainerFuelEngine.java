/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;

public class ContainerFuelEngine extends CoreContainer {

	private final TileEntityFuelEngine engine;

	public ContainerFuelEngine(EntityPlayer player, TileEntityFuelEngine te) {
		super(player, te);
		engine = te;
		this.addPlayerInventory(player);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, engine, "tank");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, engine, "watertank");
		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, engine, "lubetank");

		ReikaPacketHelper.sendSyncPacket(RotaryCraft.packetChannel, engine, "temperature");
	}

}
