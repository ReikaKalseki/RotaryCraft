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
import Reika.RotaryCraft.TileEntities.World.TileEntityPileDriver;

public class PileDriverImpactEvent extends TileEntityEvent {

	public final int centerX;
	public final int centerY;
	public final int centerZ;

	public PileDriverImpactEvent(TileEntityPileDriver te, int x, int y, int z) {
		super(te);

		centerX = x;
		centerY = y;
		centerZ = z;
	}

}