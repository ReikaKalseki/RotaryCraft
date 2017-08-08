/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import Reika.DragonAPI.Interfaces.TileEntity.ThermalTile;

public interface BasicTemperatureMachine extends ThermalTile, TemperatureTile {

	/** If you have "cool to ambient" behavior, and it has a timer, this resets it so as to pause such logic. */
	public void resetAmbientTemperatureTimer();

}
