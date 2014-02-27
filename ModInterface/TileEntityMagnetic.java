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

import java.awt.Color;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cofh.api.energy.IEnergyHandler;

public class TileEntityMagnetic extends EnergyToPowerBase implements IEnergyHandler {

	@Override
	public long getMaxPower() {
		return (long)(ReikaBuildCraftHelper.getWattsPerMJ()/10D*this.getStoredPower());
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);

		if ((world.getWorldTime()&32) != 0)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		if (!this.hasEnoughEnergy()) {
			torque = 0;
			omega = 0;
			power = 0;
			return;
		}
		if (!world.isRemote) {

			torque = this.getTorque();
			omega = this.getSpeed();

			power = (long)torque*(long)omega;

			storedEnergy -= this.getConsumedUnitsPerTick();

			this.basicPowerReceiver();

			tickcount++;
			if (power > 0) {
				if (tickcount >= 85) {
					tickcount = 0;
					SoundRegistry.DYNAMO.playSoundAtBlock(world, x, y, z, 0.5F, 1F);
				}
			}
		}
	}

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
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.MAGNETIC;
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
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (this.canInterface(from) && !simulate) {
			int amt = Math.min(maxReceive, this.getMaxStorage()-storedEnergy);
			storedEnergy += amt;
			return amt;
		}
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return from == this.getFacing();
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storedEnergy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return this.getMaxStorage();
	}

	@Override
	public int getMaxStorage() {
		return TileEntityPneumaticEngine.maxMJ*10;
	}

	@Override
	public int getConsumedUnitsPerTick() {
		return (int)this.getRFPerTick();
	}

	public float getRFPerTick() {
		return (float)(this.getPowerLevel()*10/ReikaBuildCraftHelper.getWattsPerMJ());
	}

	@Override
	public String getUnitDisplay() {
		return "RF";
	}

	@Override
	public Color getPowerColor() {
		return Color.red;
	}

}
