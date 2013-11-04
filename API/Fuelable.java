/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/** For items that use fuel and can be filled in the filling station. */
public interface Fuelable {

	/** The fuel type. */
	public Fluid getFuel();

	/** The max amount of fuel (in millibuckets) the item can take. */
	public int getCapacity(ItemStack is);

	/** The current fuel level in millibuckets. */
	public int getCurrentFuel(ItemStack is);

	/** This adds fuel to the item and returns how much was successfully added. */
	public int addFuel(ItemStack is, int amt);

}
