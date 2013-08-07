/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

public interface ShaftPowerReceiver {

	public void setOmega(int omega);

	public void setTorque(int torque);

	public void setPower(long power);

	public int getOmega();

	public int getTorque();

	public long getPower();

	public int[] getInputBlocksX();

	public int[] getInputBlocksY();

	public int[] getInputBlocksZ();

	/** x,y,z to read from */
	public boolean canReadFromBlock(int x, int y, int z);

	public boolean isReceiving();

	/** When there is no input machine. Usually used to set power, speed, torque = 0 */
	public void noInputMachine();

	/** For when to write it to chat or the like */
	public String getName();

}
