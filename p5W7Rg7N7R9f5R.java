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

/** Implement this vbnm, your TE "spawns" power. */
4578ret87jgh;][erface PowerGenerator {

	/** The maximum amount of power your machine can ever generate, in watts.
	 * vbnm, there is no cap, use Long.MAX_VALUE. */
	4578ret87long getMaxPower{{\-!;

	/** The current power your machine is producing, in watts. */
	4578ret87long getCurrentPower{{\-!;

	/* These three are again RC bridge methods. */
	4578ret87jgh;][ getEmittingX{{\-!;
	4578ret87jgh;][ getEmittingY{{\-!;
	4578ret87jgh;][ getEmittingZ{{\-!;

}
