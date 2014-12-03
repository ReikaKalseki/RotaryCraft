/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import net.minecraft.entity.EntityLiving;

public interface ControllableSpawner {

	public int getCurrentSpawnDelay();

	public void setCurrentSpawnDelay(int delay);

	/** Run a full spawn cycle, spawning mobs, particles, any any other operations.
	 This may be called up to every tick, and is always called from both client and server. */
	public void performSpawnCycle();

	/** Set the spawner inactive, so that it will not spawn or do anything for the next tick or few.
	 * This is called every tick if the controller is set to disable the spawner, so a vanilla-style spawner implementation
	 * would set the countdown up a few ticks, to prevent a spawn before the controller allows it. */
	public void setInactive();

	/** The class object of the entity spawned by this spawner. */
	public Class<? extends EntityLiving> getSpawningEntityClass();

}
