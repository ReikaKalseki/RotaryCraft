/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

/** Implement this on an entity to control how it can be deflected, i.e. blown around, by a fan. */
public interface CustomFanEntity {

	/** The required power to be able to blow this entity. */
	public long getBlowPower();

	/** The maximum speed-vector change. A magnitude, not an angle. */
	public double getMaxDeflection();

}
