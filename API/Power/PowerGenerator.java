/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Power;

/** Implement this if your TE "spawns" power. */
public interface PowerGenerator {

	/** The maximum amount of power your machine can ever generate, in watts.
	 * If there is no cap, use Long.MAX_VALUE. */
	public long getMaxPower();

	/** The current power your machine is producing, in watts. */
	public long getCurrentPower();

	/* These three are again RC bridge methods. */
	public int getEmittingX();
	public int getEmittingY();
	public int getEmittingZ();

}
