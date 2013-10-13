/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

public class TileEntityElectricMotor {

	public enum Tier {
		LOW(240, 36, 32, 256),
		MEDIUM(560, 250, 128, 1024),
		HIGH(2400, 480, 512, 2048);

		public final int inputVoltage;
		public final int inputCurrent;
		public final int outputTorque;
		public final int outputSpeed;

		public static final Tier[] list = values();

		private Tier(int voltage, int current, int torque, int speed) {
			inputCurrent = current;
			inputVoltage = voltage;
			outputSpeed = speed;
			outputTorque = torque;
		}
	}

}
