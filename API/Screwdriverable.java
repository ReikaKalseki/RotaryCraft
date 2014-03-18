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

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public interface Screwdriverable {

	/** Return true to prevent further processing. */
	public boolean onShiftRightClick(World world, int x, int y, int z, ForgeDirection side);

	/** Return true to prevent further processing. */
	public boolean onRightClick(World world, int x, int y, int z, ForgeDirection side);

}
