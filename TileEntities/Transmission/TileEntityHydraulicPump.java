/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHydraulicLine;

public class TileEntityHydraulicPump extends TileEntityPowerReceiver implements SimpleProvider, PowerGenerator {

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	public static int getPressureFromTorque(int torque) {
		return torque*16;
	}

	public static int getFlowRateFromOmega(int omega) {
		return omega/16;
	}

	public static int getOutputTorque(int pressure) {
		return pressure/16;
	}

	public static int getOutputSpeed(int flow) {
		return flow*16;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, false);

		if (this.isTurbine()) {
			int pressure = 0;
			int flow = 0;
			MachineRegistry m = MachineRegistry.getMachine(world, readx, ready, readz);
			if (m == MachineRegistry.HYDRAULICLINE) {
				TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(readx, ready, readz);
				pressure = te.getPressure();
				flow = te.getFlowRate();
			}
			omega = this.getOutputSpeed(flow);
			torque = this.getOutputTorque(pressure);
			power = (long)omega*(long)torque;
		}
		else {
			int pressure = this.getPressureFromTorque(torque);
			int flowrate = this.getFlowRateFromOmega(omega);

			MachineRegistry m = MachineRegistry.getMachine(world, writex, writey, writez);
			if (m == MachineRegistry.HYDRAULICLINE) {
				TileEntityHydraulicLine te = (TileEntityHydraulicLine)world.getBlockTileEntity(writex, writey, writez);
				te.setPressure(pressure);
				te.setFlowRate(flowrate);
			}
		}
	}

	public boolean isTurbine() {
		return this.getBlockMetadata() >= 6 && this.getBlockMetadata() < 12;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 0:
		case 6:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			writex = xCoord-1;
			writey = yCoord;
			writez = zCoord;
			break;
		case 1:
		case 7:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			writex = xCoord+1;
			writey = yCoord;
			writez = zCoord;
			break;
		case 2:
		case 8:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			writex = xCoord;
			writey = yCoord;
			writez = zCoord-1;
			break;
		case 3:
		case 9:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			writex = xCoord;
			writey = yCoord;
			writez = zCoord+1;
			break;
		case 4:
		case 10:
			readz = zCoord;
			readx = xCoord;
			ready = yCoord+1;
			writex = xCoord;
			writey = yCoord-1;
			writez = zCoord;
			break;
		case 5:
		case 11:
			readz = zCoord;
			readx = xCoord;
			ready = yCoord-1;
			writex = xCoord;
			writey = yCoord+1;
			writez = zCoord;
			break;
		}
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.HYDRAULIC.ordinal();
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
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
	}

	@Override
	public long getMaxPower() {
		return this.getCurrentPower();
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public boolean canProvidePower() {
		return this.isTurbine();
	}


}
