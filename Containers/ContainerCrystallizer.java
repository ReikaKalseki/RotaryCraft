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
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCrystallizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;

public class ContainerCrystallizer extends CoreContainer
{
	private TileEntityCrystallizer te;

	public ContainerCrystallizer(EntityPlayer player, TileEntityCrystallizer par2TileEntityCrystallizer)
	{
		super(player, par2TileEntityCrystallizer);
		te = par2TileEntityCrystallizer;
		int posX = te.xCoord;
		int posY = te.yCoord;
		int posZ = te.zCoord;

		ReikaJavaLibrary.pConsole(this);
		this.addSlot(0, 80, 35);
		this.addSlot(1, 125, 35);

		this.addPlayerInventory(player);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			icrafting.sendProgressBarUpdate(this, 0, te.smeltTick);
			//icrafting.sendProgressBarUpdate(this, 1, te.getLevel());
		}

		ReikaPacketHelper.sendTankSyncPacket(RotaryCraft.packetChannel, te, "tank");
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		//case 1: te.setLevel(par2); break;
		case 0: te.smeltTick = par2; break;
		}
	}
}