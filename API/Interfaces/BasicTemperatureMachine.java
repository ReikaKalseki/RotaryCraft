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

import Reika.DragonAPI.Interfaces.TileEntity.ThermalTile;

public interface BasicTemperatureMachine extends ThermalTile, TemperatureTile {

	public void resetAmbientTemperatureTimer();

}
