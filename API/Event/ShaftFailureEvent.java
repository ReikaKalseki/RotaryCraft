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
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public class ShaftFailureEvent extends TileEntityEvent {

	public final boolean isSpeedFailure;
	public final MaterialRegistry materialType;

	public ShaftFailureEvent(TileEntityShaft te, boolean wasSpeed, MaterialRegistry type) {
		super(te);

		isSpeedFailure = wasSpeed;
		materialType = type;
	}

}