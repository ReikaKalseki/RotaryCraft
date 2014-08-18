/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;

public class SpawnerControllerSpawnEvent extends TileEntityEvent {

	public final Class entityClass;

	public SpawnerControllerSpawnEvent(TileEntitySpawnerController te, Class entity) {
		super(te);

		entityClass = entity;
	}
}
