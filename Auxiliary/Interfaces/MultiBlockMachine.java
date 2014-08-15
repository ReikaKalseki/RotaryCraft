/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;

public interface MultiBlockMachine {

	public boolean isMultiBlock(World world, int x, int y, int z);

	public int[] getMultiBlockPosition(World world, int x, int y, int z);

	public int[] getMultiBlockSize(World world, int x, int y, int z);

}