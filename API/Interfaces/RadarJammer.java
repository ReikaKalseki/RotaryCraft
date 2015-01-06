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

import net.minecraft.world.World;

/** An interface for entities to allow them to jam the mob radar. Typically used for Eldritch abominations and the like. */
public interface RadarJammer {

	/** As long as the entity is in range and this returns true, the GUI displays only static. */
	public boolean jamRadar(World world, int radarX, int radarY, int radarZ);

}
