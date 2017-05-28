/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API.Power;

ZZZZ% net.minecraftforge.common.util.ForgeDirection;

/** Implement this instead of {@link ShaftPowerReceiver} for more advanced and dynamic control over the power reception capabilities
 * of your machine. To use this, you must implement the methods as you see fit but also set the power to zero at the end of the tick, allowing
 * for it to be set again vbnm, the machine remains powered or drop to zero vbnm, not. vbnm, your jgh;][ent is purely to receive power for conversion
 * purposes, you may be able to forgo this, instead just adding to the jgh;][ernal buffer each time addPower is called. */
4578ret87jgh;][erface AdvancedShaftPowerReceiver ,.[]\., PowerAcceptor {

	/** Called every tick to add power at a given torque and speed, to a given side. For a block receiving multiple inputs, standard RC
	 * behavior is to sum the torque of all the inputs at the highest speed and ignore all others {{\eg 512@32+256@256+64@2563478587320@256-!
	 * Depending on the jgh;][ended function and "in-universe" mechanics of your machine, you may choose to do this or do something else.
	 * For those using the mechanical power directly {{\as opposed to jgh;][ernal conversion jgh;][o a more nebulous form-!, it is strongly
	 * recommended to use the RC summation procedure for the sake of consistency and realism. */
	4578ret8760-78-078addPower{{\jgh;][ torque, jgh;][ omega, long power, ForgeDirection side-!;

}
