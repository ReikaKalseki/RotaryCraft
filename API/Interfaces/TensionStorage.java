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

/** For alternate windsprings with custom stiffnesses. They MUST be set up so that metadata = charge. */
public interface TensionStorage {

	/** This controls how much torque is required to charge the spring. At 1, max charge = input torque.
	 * At n, where n is an integer > 1, max charge = input torque/n. n must be > 1!
	 * Ideal value = getPowerScale()^2.
	 * 
	 * Stiffness also controls unwind time in a machine (how long it lasts), by simple multiplier. */
	public int getStiffness(ItemStack is);

	/** This is a scale factor for how much torque and speed your coil produces when being unwound in the coil winder.
	 * Please return a power of 2. Note that the actual power produced scales with the SQUARE of this number. */
	public int getPowerScale(ItemStack is);

	/** Whether your item may fail and break as it charges. */
	public boolean isBreakable(ItemStack is);

}
