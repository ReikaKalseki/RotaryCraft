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
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;

public class ContainerPowerBus extends CoreContainer {

	private final TileEntityPowerBus te;

	public ContainerPowerBus(EntityPlayer player, TileEntityPowerBus te) {
		super(player, te);

		this.te = te;

		this.addSlot(0, 102, 33);
		this.addSlot(1, 102, 105);

		this.addSlot(2, 66, 69);
		this.addSlot(3, 138, 69);

		int dx = 22;
		int dy = 57;
		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 9; k++)
			{
				this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18+dx, 84 + i * 18+dy));
			}
		}
		dy -= 4;
		for (int j = 0; j < 9; j++)
		{
			this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18+dx, 142+dy));
		}
	}

}
