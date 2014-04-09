/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.electricity.IVoltageInput;
import universalelectricity.api.energy.IEnergyInterface;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityElectricMotor extends TileEntityIOMachine implements PowerGenerator, SimpleProvider, IEnergyInterface, IVoltageInput {

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

		public double getPowerForDisplay() {
			return ReikaMathLibrary.getThousandBase(outputSpeed*outputTorque);
		}
	}

	public static final int MAXCOILS = 5;
	private Tier type = Tier.LOW;

	private int numberCoils = 0;

	private ForgeDirection facingDir;

	private boolean hasPower = false;

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			facingDir = ForgeDirection.EAST;
			break;
		}
		read = facingDir;
		write = read.getOpposite();
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
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.ELECTRICMOTOR;
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
			if (tickcount >= EngineType.DC.getSoundLength(0, pit*m)) {
				SoundRegistry.ELECTRIC.playSoundAtBlock(world, x, y, z, 0.36F, pit);
				tickcount = 0;
			}
		}
		else {
			omega = torque = 0;
			tickcount = 2000;
		}
		power = (long)omega*(long)torque;

		this.basicPowerReceiver();
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
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		numberCoils = NBT.getInteger("coils");

		//voltage = NBT.getFloat("v");
		//current = NBT.getFloat("a");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("coils", numberCoils);

		//NBT.setFloat("v", voltage);
		//NBT.setFloat("a", current);
	}

	public int getNumberCoils() {
		return numberCoils;
	}

	@Override
	public boolean canConnect(ForgeDirection dir, Object src) {
		return dir == facingDir || dir == ForgeDirection.DOWN;
	}

	public boolean isGettingSufficientPower() {
		return hasPower;//current >= type.inputCurrent && voltage >= type.inputVoltage;
	}

	@Override
	public long getVoltageInput(ForgeDirection dir) {
		return dir == facingDir ? type.inputVoltage : 0;
	}

	@Override
	public void onWrongVoltage(ForgeDirection dir, long voltage) {
		int req = type.inputVoltage;
		float factor = voltage/(float)req;
		if (factor > 100) {
			worldObj.newExplosion(null, xCoord+0.5, yCoord+0.5, zCoord+0.5, 9F, true, true);
		}
		else if (factor > 10) {
			ReikaParticleHelper.SMOKE.spawnAroundBlock(worldObj, xCoord, yCoord, zCoord, 12);
			ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.fizz");
		}
		else if (factor > 1) {
			if (rand.nextInt(5) == 0) {
				ReikaParticleHelper.SMOKE.spawnAroundBlock(worldObj, xCoord, yCoord, zCoord, 12);
				ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.fizz");
			}
			if (rand.nextInt(15) == 0)
				SoundRegistry.ELECTRIC.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 0.36F, 2F);
		}
		else if (factor < 1) {
			omega = 0;
			torque = 0;
			power = 0;
		}
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive) {
		hasPower = true;
		return receive;
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract) {
		return 0;
	}

	@Override
	public int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public int getEmittingZ() {
		return zCoord+write.offsetZ;
	}
}
