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
import net.minecraft.inventory.ICrafting;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDryingBed;

public class ContainerDryingBed extends CoreContainer
{
	private TileEntityDryingBed te;

	public ContainerDryingBed(EntityPlayer player, TileEntityDryingBed par2TileEntityDryingBed)
	{
		super(player, par2TileEntityDryingBed);
		te = par2TileEntityDryingBed;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;

		this.addSlot(0, 125, 35);

		this.addPlayerInventory(player);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			icrafting.sendProgressBarUpdate(this, 0, te.progress);
			//icrafting.sendProgressBarUpdate(this, 1, te.getLevel());
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "tank");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		//case 1: te.setLevel(par2); break;
		case 0: te.progress = par2; break;
		}
	}
}
