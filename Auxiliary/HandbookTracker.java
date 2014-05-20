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
import Reika.DragonAPI.Interfaces.PlayerTracker;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class HandbookTracker implements PlayerTracker {

	@Override
	public void onNewPlayer(EntityPlayer ep) {
		if (ReikaInventoryHelper.checkForItem(this.getItem().itemID, ep.inventory))
			return;
		if (!ep.inventory.addItemStackToInventory(this.getItem()))
			ep.dropPlayerItem(this.getItem());
	}

	public ItemStack getItem() {
		return ItemRegistry.HANDBOOK.getStackOf();
	}

	@Override
	public String getID() {
		return "RotaryCraft_Handbook";
	}

}
