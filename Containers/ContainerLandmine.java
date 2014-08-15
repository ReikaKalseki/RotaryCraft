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
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLandmine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerLandmine extends CoreContainer {

	public ContainerLandmine(EntityPlayer player, TileEntityLandmine te) {
		super(player, te);
		IInventory ii = (IInventory)te;

		this.addSlotToContainer(new Slot(ii, 0, 80, 34));

		this.addSlotToContainer(new Slot(ii, 1, 16, 25));
		this.addSlotToContainer(new Slot(ii, 2, 34, 25));
		this.addSlotToContainer(new Slot(ii, 3, 16, 43));
		this.addSlotToContainer(new Slot(ii, 4, 34, 43));

		this.addSlotToContainer(new Slot(ii, 5, 126, 25));
		this.addSlotToContainer(new Slot(ii, 6, 144, 25));
		this.addSlotToContainer(new Slot(ii, 7, 126, 43));
		this.addSlotToContainer(new Slot(ii, 8, 144, 43));

		this.addPlayerInventory(player);
	}

}