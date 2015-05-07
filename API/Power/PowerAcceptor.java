/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Power;

import net.minecraftforge.common.util.ForgeDirection;

/** Anything that receives power implements this, usually indirectly. Do not implement this directly. */
public interface PowerAcceptor extends ShaftMachine {

	/** Can the machine receive power from this side. Usually either "dir == facing" for 1D machines and "true" for omnidirectional machines. */
	public boolean canReadFrom(ForgeDirection dir);

	/** Whether your machine is able to receive power right now. If this returns false, no other power code is called. */
	public boolean isReceiving();

	/** The minimum torque the machine requires to operate. Also controls flywheel deceleration; a value of zero means flywheels driving it
	 * never decelerate, yielding infinite energy. To have no effective minimum, pick a value of one; a torque of zero yields zero power.
	 * Pick something reasonable, preferably as realistic as possible. */
	public int getMinTorque(int available);

}
