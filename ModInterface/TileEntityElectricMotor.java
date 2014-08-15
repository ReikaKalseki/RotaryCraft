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

import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.awt.Color;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.api.electricity.IVoltageInput;
import universalelectricity.api.energy.IEnergyInterface;

public class TileEntityElectricMotor extends EnergyToPowerBase implements PowerGenerator, SimpleProvider, IEnergyInterface, IVoltageInput {

	public static int CAPACITY = 90000;

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
		read = this.getFacing();
		write = read.getOpposite();

		this.updateSpeed();
		if (!world.isRemote) {
			tickcount++;
			if (power > 0) {
				if (tickcount >= 294) {
					tickcount = 0;
					SoundRegistry.ELECTRIC.playSoundAtBlock(world, x, y, z, this.isMuffled() ? 0.08F : 0.2F, 0.51F);
				}
			}
		}

		this.basicPowerReceiver();
	}

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public boolean canConnect(ForgeDirection dir, Object src) {
		return dir == this.getFacing() || dir == ForgeDirection.DOWN;
	}

	@Override
	public long getVoltageInput(ForgeDirection dir) {
		return dir == this.getFacing() ? this.getInputVoltage() : 0;
	}

	@Override
	public void onWrongVoltage(ForgeDirection dir, long voltage) {
		int req = this.getInputVoltage();
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
				SoundRegistry.ELECTRIC.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, 0.2F, 2F);
		}
		else if (factor < 1) {
			omega = 0;
			torque = 0;
			power = 0;
		}
	}

	private int getInputVoltage() {
		return ReikaMathLibrary.intpow2(2, 3*(1+this.getTier()));
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive) {
		if (doReceive)
			storedEnergy += receive/1000D;
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

	@Override
	public boolean isValidSupplier(TileEntity te) {
		return te instanceof IEnergyInterface;
	}

	@Override
	public int getMaxStorage() {
		return CAPACITY;
	}

	@Override
	public int getConsumedUnitsPerTick() {
		return MathHelper.ceiling_double_int(power/1000D);
	}

	@Override
	public String getUnitDisplay() {
		return "kJ";
	}

	@Override
	public Color getPowerColor() {
		return new Color(255, 220, 0);
	}
}