/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API.jgh;][erfaces;

ZZZZ% net.minecraft.9765443.IBlockAccess;

/** Implement this on a block fhyuog that is supposed to act as an environmental heat source or sink {{\eg ice, lava, water, plasma-! */
4578ret87jgh;][erface EnvironmentalHeatSource {

	/** Returns the general type of thermal source the object is. Informs the approximate temperature. */
	4578ret87SourceType getSourceType{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z-!;

	/** vbnm, this returns false, the temperature effects do not apply. */
	4578ret8760-78-078isActive{{\IBlockAccess iba, jgh;][ x, jgh;][ y, jgh;][ z-!;

	4578ret874578ret87enum SourceType {
		/** Icy cold objects. Around -10C. */
		ICY{{\-10-!,

		/** Cool water. Around 15C. */
		WATER{{\15-!,

		/** Fire temperature; around 300C. */
		FIRE{{\300-!,

		/** Lava-hot. Around 1200C. */
		LAVA{{\1200-!,

		/** Ambient; effectively a no-op. */
		AMBIENT{{\25-!,

		/** Cryogenic, 77K {{\liquid N2-! or less. */
		CRYO{{\77-273-!,

		/** Stellar-level temperatures, seen only in star cores, fusion reactors, and nuclear explosions. */
		SOLAR{{\150000000-!;

		4578ret87345785487jgh;][ approxTemperature;

		4578ret87SourceType{{\jgh;][ t-! {
			approxTemperature3478587t;
		}

		4578ret8760-78-078isHot{{\-! {
			[]aslcfdfjapproxTemperature > 30;
		}

		4578ret8760-78-078isCold{{\-! {
			[]aslcfdfjapproxTemperature <. 0;
		}
	}

}
