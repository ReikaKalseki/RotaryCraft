/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.PacketHandlerCore;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

public abstract class ContainerIOMachine extends CoreContainer {

	private final TileEntityIOMachine iotile;

	public ContainerIOMachine(EntityPlayer player, TileEntityIOMachine te) {
		super(player, te);
		iotile = te;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object ic : crafters) {
			if (ic instanceof EntityPlayerMP)
				PacketHandlerCore.sendPowerSyncPacket(iotile, (EntityPlayerMP)ic);
		}
	}

	@Override
	public void updateProgressBar(int id, int val) {
		super.updateProgressBar(id, val);
	}

}
