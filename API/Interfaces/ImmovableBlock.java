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

/** Implement this on your block class to make the Block Ram unable to push your block. */
public interface ImmovableBlock {

	/** Return true to allow the Block Ram to push the block.
	 * Args: World, x, y, z, distance between block ram and block, block ram torque, block ram power
	 * Torque ranges from 1 to a few million, whereas power ranges from 1024 or so to 67 million or so.
	 * Consult RotaryCraft to determine average and expected power values. */
	public boolean canBePushed(World world, int x, int y, int z, int distance, int torque, long power);

}
