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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.electricity.ElectricityPack;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityElectricMotor extends TileEntityIOMachine implements PowerGenerator, SimpleProvider, IElectrical {

	public static enum Tier {
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

		public ElectricityPack getRequirements() {
			return new ElectricityPack(inputCurrent, inputVoltage);
		}

		public double getPowerForDisplay() {
			return ReikaMathLibrary.getThousandBase(outputSpeed*outputTorque);
		}
	}

	public static final int MAXCOILS = 5;
	private Tier type = Tier.LOW;

	private int numberCoils = 0;

	private ForgeDirection facingDir;

	private void getIOSides(World world, int x, int y, int z, int meta) {
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 0:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.EAST;
			break;
		}
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ELECTRICMOTOR.ordinal();
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
		super.updateTileEntity();
		tickcount++;
		this.getIOSides(world, x, y, z, meta);
		if (numberCoils == 5) {
			type = Tier.HIGH;
		}
		else if (numberCoils >= 2) {
			type = Tier.MEDIUM;
		}
		else
			type = Tier.LOW;
		if (this.isGettingSufficientPower()) {
			omega = type.outputSpeed;
			torque = type.outputTorque;
			float pit = 1+((type.ordinal()-1)/3F);
			float m = 1.025F;
			if (type == Tier.LOW)
				m = 2.31F;
			if (type == Tier.HIGH)
				m = 0.572F;
			if (tickcount >= EnumEngineType.DC.getSoundLength(0, pit*m)) {
				SoundRegistry.playSoundAtBlock(SoundRegistry.ELECTRIC, world, x, y, z, 0.36F, pit);
				tickcount = 0;
			}
		}
		else {
			omega = torque = 0;
			tickcount = 2000;
		}
		power = omega*torque;
	}

	public boolean addCoil() {
		if (numberCoils < MAXCOILS) {
			numberCoils++;
			return true;
		}
		return false;
	}

	@Override
	public long getMaxPower() {
		return this.getPowerOut();
	}

	@Override
	public long getCurrentPower() {
		return this.getPowerOut();
	}

	public long getPowerOut() {
		return type.outputSpeed*type.outputTorque;
	}

	public Tier getVoltageTier() {
		return type;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		numberCoils = NBT.getInteger("coils");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBT.setInteger("coils", numberCoils);
	}

	public int getNumberCoils() {
		return numberCoils;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir == facingDir.getOpposite();
	}

	@Override
	public float receiveElectricity(ForgeDirection from, ElectricityPack receive, boolean doReceive) {
		return 0;
	}

	@Override
	public ElectricityPack provideElectricity(ForgeDirection from, ElectricityPack in, boolean doProvide) {
		if (!doProvide)
			return null;
		if (!this.canConnect(from))
			return null;
		if (in.voltage < type.inputVoltage || in.amperes < type.inputCurrent)
			return null;
		return in;
	}

	@Override
	public float getRequest(ForgeDirection dir) {
		return this.canConnect(dir) ? type.inputCurrent*type.inputVoltage : 0;
	}

	@Override
	public float getProvide(ForgeDirection direction) {
		return 0;
	}

	@Override
	public float getVoltage() {
		return this.isGettingSufficientPower() ? type.inputVoltage : 0;
	}

	public boolean isGettingSufficientPower() {
		return false;
	}
}
