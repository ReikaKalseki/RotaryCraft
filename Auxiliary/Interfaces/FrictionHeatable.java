/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import Reika.RotaryCraft.API.Interfaces.BasicTemperatureMachine;
import Reika.RotaryCraft.API.Interfaces.ThermalMachine;

public interface FrictionHeatable extends BasicTemperatureMachine, ThermalMachine {

	public void addTemperature(int add);

	public int getMaxTemperature();

}
