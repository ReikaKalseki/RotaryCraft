/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Base.PlayerTracker;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class HandbookTracker extends PlayerTracker {

	public HandbookTracker(String name) {
		super(name);
	}

	@Override
	public void onNewPlayer(EntityPlayer ep) {
		if (!ep.inventory.addItemStackToInventory(this.getItem()))
			ep.dropPlayerItem(this.getItem());
	}

	public ItemStack getItem() {
		return ItemRegistry.HANDBOOK.getStackOf();
	}

}
