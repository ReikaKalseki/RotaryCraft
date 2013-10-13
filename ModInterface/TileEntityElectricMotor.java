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

import net.minecraft.world.World;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;

public class TileEntityElectricMotor extends TileEntityIOMachine {

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

	@Override
	public boolean canProvidePower() {
		return false;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return null;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return 0;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

	}

	public void addCoil() {

	}

}
