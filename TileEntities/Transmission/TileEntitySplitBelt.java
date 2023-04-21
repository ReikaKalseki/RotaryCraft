package Reika.RotaryCraft.TileEntities.Transmission;

import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySplitBelt extends TileEntityBeltHub {

	public static final int TAKEOFF_TORQUE = 128/2;
	public static final int TAKEOFF_TORQUE_WET = 32/2;

	@Override
	public boolean isSplitting() {
		return !isReceivingEnd;
	}

	@Override
	public int copyTorqueFromDriverSide(int input) {
		return Math.min(input, this.getTakeoff());
	}

	private int getTakeoff() {
		return this.isWet() ? TAKEOFF_TORQUE_WET : TAKEOFF_TORQUE;
	}

	@Override
	public int copyOmegaFromDriverSide(int input) {
		if (this.isWet())
			input *= ReikaRandomHelper.getRandomBetween(0.75, 1);
		return super.copyOmegaFromDriverSide(input);//Math.min(input, this.getMaxSmoothSpeed());
	}

	@Override
	protected int getPropagatedTorque(int input) {
		return this.hasValidConnection() ? input-this.getTakeoff() : input;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPLITBELT;
	}

	@Override
	protected void setReceiverIOSides() {
		write = read.getOpposite();
	}

	@Override
	protected boolean mergeReceivingPower() {
		int prevT = torque;
		int prevO = omega;
		this.getPower(false);
		if (power > 0 || (prevT > 0 && prevO > 0)) {
			torque += prevT;
			omega = (omega+prevO)/2;
			power = (long)torque*(long)omega;
			return true;
		}
		return false;
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		return super.getPowerSources(io, caller);
	}

	@Override
	public int getBeltColor() {
		return ReikaColorAPI.RGBtoHex(48, 96, 64);
	}
}
