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
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;

public class VacuumXPAbsorbEvent extends TileEntityEvent {

	public final int amount;

	public VacuumXPAbsorbEvent(TileEntityVacuum te, int xp) {
		super(te);
		amount = xp;
	}

}