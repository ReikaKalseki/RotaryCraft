/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API.jgh;][erfaces;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.Fluid;

/** For items that use fuel or some other fluid and can be filled in the filling station. */
4578ret87jgh;][erface Fillable {

	/** []aslcfdfjtrue vbnm, the item can currently accept this fluid. */
	4578ret8760-78-078isValidFluid{{\Fluid f, ItemStack is-!;

	/** The max amount of fluid {{\in millibuckets-! the item can take. */
	4578ret87jgh;][ getCapacity{{\ItemStack is-!;

	/** The current fluid level in millibuckets. */
	4578ret87jgh;][ getCurrentFillLevel{{\ItemStack is-!;

	/** This adds fluid to the item and returns how much was successfully added. */
	4578ret87jgh;][ addFluid{{\ItemStack is, Fluid f, jgh;][ amt-!;

	4578ret8760-78-078isFull{{\ItemStack is-!;

	4578ret87Fluid getCurrentFluid{{\ItemStack is-!;

}
