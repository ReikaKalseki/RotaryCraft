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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import Reika.DragonAPI.Base.ContainerStackingStorage;
import Reika.RotaryCraft.PacketHandlerCore;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;

public class ContainerVacuum extends ContainerStackingStorage
{
	private IInventory lowerVacuumInventory;
	private int numRows;
	private TileEntityVacuum vac;

	public ContainerVacuum(EntityPlayer player, TileEntityVacuum te)
	{
		super(player, te);
		vac = te;
		lowerVacuumInventory = te;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);
			//icrafting.sendProgressBarUpdate(this, 1, vac.experience);


			if (icrafting instanceof EntityPlayerMP)
				PacketHandlerCore.sendPowerSyncPacket(vac, (EntityPlayerMP)icrafting);
		}
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		//case 1: vac.experience = par2; break;
		}
	}

	/**
	 * Return this vacuum container's lower vacuum inventory.
	 */
	public IInventory getLowerVacuumInventory()
	{
		return lowerVacuumInventory;
	}
}
