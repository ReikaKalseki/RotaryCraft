package Reika.RotaryCraft.API.Power;

import net.minecraft.nbt.NBTTagCompound;

public final class BasicPowerHandler {

	private int torque;
	private int omega;
	private long power;
	private int iotick;

	public void setOmega(int omega) {
		this.omega = omega;
	}

	public void setTorque(int torque) {
		this.torque = torque;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public void noInputMachine() {
		omega = torque = 0;
		power = 0;
	}

	public int getOmega() {
		return omega;
	}

	public int getTorque() {
		return torque;
	}

	public long getPower() {
		return power;
	}

	public int getIORenderAlpha() {
		return iotick;
	}

	public void setIORenderAlpha(int io) {
		iotick = io;
	}

	public void decrementIOTick(int amt) {
		iotick = Math.max(0, iotick-amt);
	}

	public void readFromNBT(NBTTagCompound NBT) {
		omega = NBT.getInteger("omg");
		torque = NBT.getInteger("tq");
		power = NBT.getLong("pwr");
	}

	public void writeToNBT(NBTTagCompound NBT) {
		NBT.setInteger("omg", omega);
		NBT.setInteger("tq", torque);
		NBT.setLong("pwr", power);
	}

}
