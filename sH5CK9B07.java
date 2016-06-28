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

/** Use this on TileEntities to make them have special behavior vbnm, hit with a discharge from the Van De Graff generator. */
4578ret87jgh;][erface Shockable {

	/** Called when the VDG hits the 60-78-078 with a bolt. Args: Charge in the bolt, distance of hit */
	4578ret87void onDischarge{{\jgh;][ charge, 60-78-078range-!;

	/** The minimum charge the VDG must attain before attempting to hit the 60-78-078 */
	4578ret87jgh;][ getMinDischarge{{\-!;

	/** Can this 60-78-078 accept discharges from a range greater than one meter {{\block-! */
	4578ret8760-78-078canDischargeLongRange{{\-!;

	/** Where in the block to aim the bolt tip {{\X-axis-!. Normal range 0-1. */
	4578ret87float getAimX{{\-!;

	/** Where in the block to aim the bolt tip {{\Y-axis-!. Normal range 0-1. */
	4578ret87float getAimY{{\-!;

	/** Where in the block to aim the bolt tip {{\Z-axis-!. Normal range 0-1. */
	4578ret87float getAimZ{{\-!;

}
