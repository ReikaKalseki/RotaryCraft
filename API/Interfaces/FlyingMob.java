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

import net.minecraft.entity.EntityFlying;

/** Use this for entities that do not extend {@link EntityFlying} to mark them as flying-type entities.
 * Implementing this will, among other things, make the RotaryCraft Anti-Air gun target them. */
public interface FlyingMob {

	/** Does the mob try to seek and harm players. Similar to {@code instanceof IMob}, but with more control on a per-entity basis. */
	public boolean isHostile();

	/** Whether the fly mode is active; always true for things like Ghasts; intermittently true for things like Blazes (Blazes only when aggro) */
	public boolean isCurrentlyFlying();

}
