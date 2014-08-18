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
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;

public class BorerDigEvent extends TileEntityEvent {

	public final int range;
	public final int centerX;
	public final int centerY;
	public final int centerZ;

	public final boolean isSilkTouch;

	public BorerDigEvent(TileEntityBorer te, int distance, int x, int y, int z, boolean silk) {
		super(te);

		range = distance;
		centerX = x;
		centerY = y;
		centerZ = z;

		isSilkTouch = silk;
	}

}
