/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

/** Make a TE or Entity implement this to have the heat ray give it special effects. */
public interface Laserable {

	/** This is called every tick the TE/Entity is in the beam of the Heat Ray.
	 * Args: Heat Ray power input, distance from heat ray */
	public void whenInBeam(long power, int range);

}
