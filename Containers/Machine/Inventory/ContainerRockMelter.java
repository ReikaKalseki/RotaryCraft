/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers.Machine.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;

public class ContainerRockMelter extends ContainerIOMachine {

	public ContainerRockMelter(EntityPlayer player, TileEntityLavaMaker te) {
		super(player, te);

		for (int i = 0; i < 9; i++) {
			int dx = 26+18*(i%3);
			int dy = 17+18*(i/3);
			this.addSlot(i, dx, dy);
		}

		this.addPlayerInventory(ep);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, tile, "tank");
	}

}
