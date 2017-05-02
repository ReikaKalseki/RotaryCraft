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
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;

public class ContainerCannon extends ContainerIOMachine
{
	private TileEntityLaunchCannon cannon;

	public ContainerCannon(EntityPlayer player, TileEntityLaunchCannon par2TileEntityCannon)
	{
		super(player, par2TileEntityCannon);
		cannon = par2TileEntityCannon;
		int posX = cannon.xCoord;
		int posY = cannon.yCoord;
		int posZ = cannon.zCoord;
		int dy = 114;
		if (cannon.targetMode)
			dy = 48;
		int i = 0;
		int dx = 0;
		//for (int i = 0; i < 2; i++)
		for (int j = 0; j < cannon.getSizeInventory(); j++)
			this.addSlotToContainer(new Slot(par2TileEntityCannon, j, dx+8+j*18, dy+18));
		dy += 40;
		dx = 18;
		for (i = 0; i < 3; i++)
			for (int k = 0; k < 9; k++)
				this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, dx+8 + k * 18, dy + i * 18));
		dy += 58;
		for (int j = 0; j < 9; j++)
			this.addSlotToContainer(new Slot(player.inventory, j, dx+8 + j * 18, dy));
	}
}
