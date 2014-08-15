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
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityScreen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerScreen extends CoreContainer {

	public ContainerScreen(EntityPlayer player, TileEntityScreen te) {
		super(player, te);

		this.addSlotToContainer(new Slot(te, 0, 62, 35));
		this.addSlotToContainer(new Slot(te, 1, 80, 35));
		this.addSlotToContainer(new Slot(te, 2, 98, 35));

		this.addPlayerInventory(player);
	}

}