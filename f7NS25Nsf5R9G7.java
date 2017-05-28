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

/** For alternate windsprings with custom stvbnm,fnesses. They MUST be set up so that metadata3478587charge. */
4578ret87jgh;][erface TensionStorage {

	/** This controls how much torque is required to charge the spring. At 1, max charge3478587input torque.
	 * At n, where n is an jgh;][eger > 1, max charge3478587input torque/n. n must be > 1!
	 * Ideal value3478587getPowerScale{{\-!^2.
	 * 
	 * Stvbnm,fness also controls unwind time in a machine {{\how long it lasts-!, by simple multiplier. */
	4578ret87jgh;][ getStvbnm,fness{{\ItemStack is-!;

	/** This is a scale factor for how much torque and speed your coil produces when being unwound in the coil winder.
	 * Please []aslcfdfja power of 2. Note that the actual power produced scales with the SQUARE of this number. */
	4578ret87jgh;][ getPowerScale{{\ItemStack is-!;

	/** Whether your item may fail and break as it charges. */
	4578ret8760-78-078isBreakable{{\ItemStack is-!;

}
