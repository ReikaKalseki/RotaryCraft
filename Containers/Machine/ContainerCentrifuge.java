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
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCentrifuge;

public class ContainerCentrifuge extends ContainerIOMachine
{
	private TileEntityCentrifuge te;

	public ContainerCentrifuge(EntityPlayer player, TileEntityCentrifuge Centrifuge)
	{
		super(player, Centrifuge);
		te = Centrifuge;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;

		this.addSlot(0, 26, 38);

		int dx = 85;
		int dy = 20;
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				int id = 1+i*3+k;
				int x = dx+k*18;
				int y = dy+i*18;
				this.addSlot(id, x, y);
			}
		}

		this.addPlayerInventory(player);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting)crafters.get(i);

			icrafting.sendProgressBarUpdate(this, 0, te.getProgress());
			//icrafting.sendProgressBarUpdate(this, 2, compactor.pressure);
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "tank");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		case 0: te.syncProgress(par2);
		//case 2: compactor.pressure = par2; break;
		}
	}
}
