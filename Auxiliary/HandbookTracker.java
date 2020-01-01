/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Instantiable.ItemOnSpawnTracker;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class HandbookTracker extends ItemOnSpawnTracker {

	@Override
	public ItemStack getItem() {
		return ItemRegistry.HANDBOOK.getStackOf();
	}

	@Override
	public String getID() {
		return "RotaryCraft_Handbook";
	}

}
