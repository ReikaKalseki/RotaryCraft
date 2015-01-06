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

/** Implement this to make your tool chargable with windsprings. */
public interface ChargeableTool {

	/** Sets the tool charge to a given value (anywhere from 0 to 30000), and returns the previous value. Operates on the given tool stack.
	 * "Strongcoil" is whether or not the bedrock windspring variant is being used. To refuse any operation, just do nothing and return charge. */
	public int setCharged(ItemStack is, int charge, boolean strongcoil);

}
