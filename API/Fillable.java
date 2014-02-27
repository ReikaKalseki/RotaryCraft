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

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/** For items that use fuel and can be filled in the filling station. */
public interface Fillable {

	/** The current fluid type. */
	public Fluid getFluidType(ItemStack is);

	/** The max amount of fluid (in millibuckets) the item can take. */
	public int getCapacity(ItemStack is);

	/** The current fluid level in millibuckets. */
	public int getCurrentFillLevel(ItemStack is);

	/** This adds fluid to the item and returns how much was successfully added. */
	public int addFluid(ItemStack is, Fluid f, int amt);

	public boolean isFull(ItemStack is);

}
