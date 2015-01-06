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
import net.minecraftforge.common.util.ForgeDirection;

/** Implement this to make your block able to have the screwdriver used on it. Overrides normal IWrench behavior if your block also acts on that. */
public interface Screwdriverable {

	/** Return true to prevent further processing. */
	public boolean onShiftRightClick(World world, int x, int y, int z, ForgeDirection side);

	/** Return true to prevent further processing. */
	public boolean onRightClick(World world, int x, int y, int z, ForgeDirection side);

}
