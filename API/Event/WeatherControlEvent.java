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
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityWeatherController;

public class WeatherControlEvent extends TileEntityEvent {

	public final boolean setRain;
	public final boolean setThunder;
	public final boolean setSuperStorm;

	public WeatherControlEvent(TileEntityWeatherController te, boolean rain, boolean thunder, boolean storm) {
		super(te);

		setRain = rain;
		setSuperStorm = storm;
		setThunder = thunder;
	}

}
