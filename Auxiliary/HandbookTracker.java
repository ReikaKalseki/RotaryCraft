/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.entity.player.EntityPlayer;
import Reika.DragonAPI.Base.PlayerTracker;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class HandbookTracker extends PlayerTracker {

	public HandbookTracker(String name) {
		super(name);
	}

	@Override
	public void onNewPlayer(EntityPlayer ep) {
		if (ConfigRegistry.HANDBOOK.getState()) {
			if (!ep.inventory.addItemStackToInventory(ItemRegistry.HANDBOOK.getStackOf()))
				ep.dropPlayerItem(ItemRegistry.HANDBOOK.getStackOf());
		}
	}

}
