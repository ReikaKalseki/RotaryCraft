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

import net.minecraft.world.World;

/** Make a TE, Block, or Entity implement this to have the heat ray give it special effects. */
public interface Laserable {

	/** This is called every tick the TE/Entity/Block is in the beam of the Heat Ray.
	 * Args: Heat Ray power input, distance from heat ray */
	public void whenInBeam(World world, int x, int y, int z, long power, int range);

	/** Whether the object blocks the beam. Returning true shields everything behind it. */
	public boolean blockBeam(World world, int x, int y, int z, long power);

}
