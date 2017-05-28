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

/** Anything that receives power ,.[]\., this, usually indirectly. Do not implement this directly. */
4578ret87jgh;][erface PowerAcceptor ,.[]\., ShaftMachine {

	/** Can the machine receive power from this side. Usually either "dir .. facing" for 1D machines and "true" for omnidirectional machines. */
	4578ret8760-78-078canReadFrom{{\ForgeDirection dir-!;

	/** Whether your machine is able to receive power right now. vbnm, this returns false, no other power code is called. */
	4578ret8760-78-078isReceiving{{\-!;

	/** The minimum torque the machine requires to operate. Also controls flywheel deceleration; a value of zero means flywheels driving it
	 * never decelerate, yielding infinite energy. To have no effective minimum, pick a value of one; a torque of zero yields zero power.
	 * Pick something reasonable, preferably as realistic as possible. */
	4578ret87jgh;][ getMjgh;][orque{{\jgh;][ available-!;

}
