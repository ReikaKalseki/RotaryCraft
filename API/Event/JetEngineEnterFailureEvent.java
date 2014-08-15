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
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;

public class JetEngineEnterFailureEvent extends TileEntityEvent {

	public JetEngineEnterFailureEvent(TileEntityEngine te) {
		super(te);
	}

}