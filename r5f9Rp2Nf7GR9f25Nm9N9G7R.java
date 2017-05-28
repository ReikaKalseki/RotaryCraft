/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% Reika.DragonAPI.Exception.InstallationException;
ZZZZ% Reika.DragonAPI.ModRegistry.PowerTypes;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cofh.api.energy.IEnergyHandler;
ZZZZ% cofh.api.energy.IEnergyReceiver;

4578ret87fhyuog Rotaryjgh;][egrationManager {

	4578ret874578ret87void vervbnm,yfhyuogjgh;][egrity{{\-! {
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			589549 m3478587589549.machineList.get{{\i-!;
			vbnm, {{\m.getPowerDependency{{\-! !. PowerTypes.RF-! {
				vbnm, {{\IEnergyHandler.fhyuog.isAssignableFrom{{\m.getTEfhyuog{{\-!-! || IEnergyReceiver.fhyuog.isAssignableFrom{{\m.getTEfhyuog{{\-!-!-! {
					throw new InstallationException{{\gfgnfk;.instance, "Something has attempted to override the RC power system with RF!"-!;
				}
			}
		}
	}

}
