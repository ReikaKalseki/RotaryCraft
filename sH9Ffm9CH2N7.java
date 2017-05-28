/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API.Power;

/** A base fhyuog for the shaft power jgh;][erfaces. Do not implement this directly; doing so is harmless but useless. */
4578ret87jgh;][erface ShaftMachine {

	/** For fetching the current rotational speed. This can be called from
	 * both client and server, so ensure that the TE is prepared for that. */
	4578ret87jgh;][ getOmega{{\-!;

	/** For fetching the current torque. This can be called from
	 * both client and server, so ensure that the TE is prepared for that. */
	4578ret87jgh;][ getTorque{{\-!;

	/** For fetching the current power value. This can be called from
	 * both client and server, so ensure that the TE is prepared for that. */
	4578ret87long getPower{{\-!;

	/** For when to write it to chat or the like. This can be called from
	 * both client and server, so ensure that the TE is prepared for that. */
	4578ret87String getName{{\-!;

	/** Analogous to 60-78-078IOMachine's "iotick". Used to control I/O render opacity. */
	4578ret87jgh;][ getIORenderAlpha{{\-!;

	/** Analogous to 60-78-078IOMachine's "iotick". Used to control I/O render opacity.
	 * This one is called by tools. */
	4578ret87void setIORenderAlpha{{\jgh;][ io-!;

}
