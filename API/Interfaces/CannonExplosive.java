/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/** Implement this on the item class to make the TNT cannon able to fire your explosive item or block like it would fire TNT */
public interface CannonExplosive {

	/** Creates and returns, but does not spawn, a new instance of the entity spawned when normally using the explosive.
	 * Do NOT return null. Args: ItemStack */
	public Entity getExplosiveEntity(ItemStack is);

	/** The entity that your explosive spawns must implement this. */
	public static interface ExplosiveEntity {

		/** Set the delay until the explosive detonates. */
		public void setFuse(int fuse);

	}

}
