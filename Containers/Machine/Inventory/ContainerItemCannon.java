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
import net.minecraft.inventory.Slot;
import Reika.RotaryCraft.Base.ContainerIOMachine;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;

public class ContainerItemCannon extends ContainerIOMachine
{
	private TileEntityItemCannon cannon;

	public ContainerItemCannon(EntityPlayer player, TileEntityItemCannon par2TileEntityItemCannon)
	{
		super(player, par2TileEntityItemCannon);
		cannon = par2TileEntityItemCannon;
		int posX = cannon.xCoord;
		int posY = cannon.yCoord;
		int posZ = cannon.zCoord;
		int dy = 48;
		int i = 0;
		for (int j = 0; j < 9; j++)
			this.addSlotToContainer(new Slot(par2TileEntityItemCannon, j+9*i, 8+j*18, dy+i*18+18));
		dy += 40;
		for (i = 0; i < 3; i++)
			for (int k = 0; k < 9; k++)
				this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, dy + i * 18));
		dy += 58;
		for (int j = 0; j < 9; j++)
			this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, dy));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
}
