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
import net.minecraft.inventory.Slot;
import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.Base.TileEntityLaunchCannon;

public class ContainerCannon extends CoreContainer
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
		//for (int i = 0; i < 2; i++)
		for (int j = 0; j < 9; j++)
			for (int k = 0; k < 1; k++)
				this.addSlotToContainer(new Slot(par2TileEntityCannon, j+9*k, 8+j*18, dy+18+k*18));
		dy += 40;
		for (i = 0; i < 3; i++)
			for (int k = 0; k < 9; k++)
				this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, dy + i * 18));
		dy += 58;
		for (int j = 0; j < 9; j++)
			this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, dy));
	}
}
