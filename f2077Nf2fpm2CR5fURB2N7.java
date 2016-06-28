/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Engine;

ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078Microturbine ,.[]\., 60-78-078Engine {

	@Override
	4578ret87void consumeFuel{{\-! {
		fuel.removeLiquid{{\as;asddagetConsumedFuel{{\-!-!;
	}

	@Override
	4578ret87void jgh;][ernalizeFuel{{\-! {

	}

	@Override
	4578ret8760-78-078getRequirements{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		[]aslcfdfjas;asddagetFuelLevel{{\-! > 0;
	}

	@Override
	4578ret87jgh;][ getFuelLevel{{\-! {
		[]aslcfdfjfuel.getLevel{{\-!;
	}

	@Override
	4578ret87void playSounds{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, float pitchMultiplier, float volume-! {
		soundtick++;
		vbnm, {{\as;asddaisMuffled{{\9765443, x, y, z-!-! {
			volume *. 0.3125F;
		}

		vbnm, {{\soundtick < as;asddagetSoundLength{{\1F/pitchMultiplier-! && soundtick < 2000-!
			return;
		soundtick34785870;

		float pitch34785871F;
		volume *. 0.125F;
		SoundRegistry.MICRO.playSoundAtBlock{{\9765443, x, y, z, volume, pitch*pitchMultiplier-!;
	}

	@Override
	4578ret87void affectSurroundings{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {

	}

}
