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
import Reika.RotaryCraft.TileEntities.TileEntityScreen;

public class ContainerScreen extends CoreContainer {

	public ContainerScreen(EntityPlayer player, TileEntityScreen tile) {
		super(player, tile);

		this.addSlotToContainer(new Slot(tile, 0, 62, 35));
		this.addSlotToContainer(new Slot(tile, 1, 80, 35));
		this.addSlotToContainer(new Slot(tile, 2, 98, 35));

		this.addPlayerInventory(player);
	}

}
