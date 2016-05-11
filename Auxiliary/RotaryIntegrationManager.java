/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Exception.InstallationException;
import Reika.DragonAPI.ModRegistry.PowerTypes;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

public class RotaryIntegrationManager {

	public static void verifyClassIntegrity() {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList.get(i);
			if (m.getPowerDependency() != PowerTypes.RF) {
				if (IEnergyHandler.class.isAssignableFrom(m.getTEClass()) || IEnergyReceiver.class.isAssignableFrom(m.getTEClass())) {
					throw new InstallationException(RotaryCraft.instance, "Something has attempted to override the RC power system with RF!");
				}
			}
		}
	}

}
