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


/** This is for basic RC power compatibility. Results with a machine that accepts power from multiple sides is undefined unless you specifically
 * handle for it; for such cases the use of {@link AdvancedShaftPowerReceiver} is recommended instead.
 * 
 * All "set" methods are called every tick. */
public interface ShaftPowerReceiver extends PowerAcceptor {

	/** RC machines set your machine's rotational speed with this. */
	public void setOmega(int omega);

	/** RC machines set your machine's torque with this. */
	public void setTorque(int torque);

	/** RC machines set your machine's power with this.
	 * You do not need to calculate power=omega*torque;
	 * RC code will do that for you. */
	public void setPower(long power);

	/** When there is no input machine. Usually used to set power, speed, torque = 0 */
	public void noInputMachine();

}
