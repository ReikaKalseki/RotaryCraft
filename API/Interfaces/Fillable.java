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

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/** For items that use fuel or some other fluid and can be filled in the filling station. */
public interface Fillable {

	/** Return true if the item can currently accept this fluid. */
	public boolean isValidFluid(Fluid f, ItemStack is);

	/** The max amount of fluid (in millibuckets) the item can take. */
	public int getCapacity(ItemStack is);

	/** The current fluid level in millibuckets. */
	public int getCurrentFillLevel(ItemStack is);

	/** This adds fluid to the item and returns how much was successfully added. */
	public int addFluid(ItemStack is, Fluid f, int amt);

	public boolean isFull(ItemStack is);

	public Fluid getCurrentFluid(ItemStack is);

}
