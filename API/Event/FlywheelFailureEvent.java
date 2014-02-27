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
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;

public class FlywheelFailureEvent extends TileEntityEvent {

	public final float explosivePower;

	public FlywheelFailureEvent(TileEntityFlywheel te, float power) {
		super(te);

		explosivePower = power;
	}

}
