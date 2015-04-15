/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

public interface CustomFanEntity {

	/** The required power to be able to blow this entity. */
	public long getBlowPower();

	/** The maximum speed-vector change. */
	public double getMaxDeflection();

}
