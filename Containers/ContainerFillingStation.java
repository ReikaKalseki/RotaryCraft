/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFillingStation;

public class ContainerFillingStation extends CoreContainer {

	TileEntityFillingStation tile;

	public ContainerFillingStation(EntityPlayer player, TileEntityFillingStation te) {
		super(player, te);
		tile = te;

		this.addSlot(0, 106, 71);
		this.addSlot(1, 54, 21);

		this.addPlayerInventoryWithOffset(player, 0, 21);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			//icrafting.sendProgressBarUpdate(this, 1, Reservoir.get);
			icrafting.sendProgressBarUpdate(this, 2, tile.getLevel());
		}
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		switch(par1) {
		//case 1: Reservoir.liquidID = par2; break;
		//case 2: tile.setLevel(par2); break;
		}
	}

}
