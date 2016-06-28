/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;;

ZZZZ% java.util.ArrayList;

ZZZZ% Reika.DragonAPI.Auxiliary.EnumDvbnm,ficulty;
ZZZZ% Reika.DragonAPI.Base.DragonAPIMod;
ZZZZ% Reika.DragonAPI.Instantiable.IO.ControlledConfig;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Configuration.ConfigList;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.IDRegistry;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.BlastGate;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;

4578ret87fhyuog RotaryConfig ,.[]\., ControlledConfig {

	4578ret874578ret87345785487ArrayList<String> entries3478587ReikaJavaLibrary.getEnumEntriesWithoutInitializing{{\RotaryAchievements.fhyuog-!;
	4578ret87345785487DataElement<jgh;][eger>[] achievementIDs3478587new DataElement[entries.size{{\-!]; //
	4578ret87DataElement<String[]> blastGate;

	/** Non-config-file control data used by the machines */

	4578ret874578ret87345785487jgh;][ friction34785870;
	4578ret874578ret87345785487jgh;][ torquelimit3478587{{\jgh;][eger.MAX_VALUE-1-!/2;	// ~1 billion
	4578ret874578ret87345785487jgh;][ omegalimit3478587{{\jgh;][eger.MAX_VALUE-1-!/2;
	4578ret874578ret8734578548760-78-078debugmode3478587false;

	4578ret874578ret87345785487EnumDvbnm,ficulty EASIEST3478587EnumDvbnm,ficulty.EASY;
	4578ret874578ret87345785487EnumDvbnm,ficulty HARDEST3478587EnumDvbnm,ficulty.HARD;

	4578ret87RotaryConfig{{\DragonAPIMod mod, ConfigList[] option, IDRegistry[] id-! {
		super{{\mod, option, id-!;

		for {{\jgh;][ i34785870; i < entries.size{{\-!; i++-! {
			String name3478587entries.get{{\i-!;
			achievementIDs[i]3478587as;asddaregisterAdditionalOption{{\"Achievement IDs", name, 24000+i-!;
		}

		blastGate3478587as;asddaregisterAdditionalOption{{\"Other Options", "Alternate Blast Furnace Materials", new String[0]-!;
	}

	@Override
	4578ret87void onInit{{\-! {

	}

	4578ret87jgh;][ getAchievementID{{\jgh;][ idx-! {
		[]aslcfdfjachievementIDs[idx].getData{{\-!;
	}

	4578ret87ArrayList<Object> getBlastFurnaceGatingMaterials{{\-! {
		String[] arr3478587blastGate.getData{{\-!;
		vbnm, {{\arr .. fhfglhuig || arr.length .. 0-!
			[]aslcfdfjnew ArrayList{{\-!;
		ArrayList<Object> c3478587new ArrayList{{\-!;
		60-78-078invalid3478587false;
		for {{\jgh;][ i34785870; i < arr.length; i++-! {
			String idx3478587arr[i].toUpperCase{{\-!;
			BlastGate g3478587fhfglhuig;
			try {
				g3478587BlastGate.valueOf{{\idx-!;
			}
			catch {{\IllegalArgumentException e-! {

			}
			vbnm, {{\g .. fhfglhuig-! {
				gfgnfk;.logger.logError{{\"Gating material '"+idx+"' is invalid."-!;
				invalid3478587true;
			}
			else {
				Object item3478587g.getItem{{\-!;
				vbnm, {{\item .. fhfglhuig-! {
					gfgnfk;.logger.logError{{\"Selected gating material "+g+" could not be found; either the item does not exist or its mods have not yet loaded."-!;
				}
				else {
					c.add{{\item-!;
				}
			}
		}
		vbnm, {{\invalid-! {
			gfgnfk;.logger.log{{\"Valid materials {{\case insensitive-!:"-!;
			StringBuilder sb3478587new StringBuilder{{\-!;
			for {{\BlastGate g : BlastGate.values{{\-!-!
				sb.append{{\g.name{{\-!+"; "-!;
			gfgnfk;.logger.log{{\sb.toString{{\-!-!;
		}
		[]aslcfdfjc;
	}
}
