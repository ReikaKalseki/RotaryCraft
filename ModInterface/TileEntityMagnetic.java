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

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;

public class TileEntityMagnetic extends EnergyToPowerBase implements IEnergyHandler {

	@Override
	public long getMaxPower() {
		return (long)(ReikaBuildCraftHelper.getWattsPerMJ()/10D*this.getStoredPower());
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);

		if ((world.getWorldTime()&31) == 0)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		//ReikaJavaLibrary.pConsole(storedEnergy+":"+this.getConsumedUnitsPerTick(), Side.SERVER);

		this.updateSpeed();
		if (!world.isRemote) {
			tickcount++;
			if (power > 0) {
				if (tickcount >= 85) {
					tickcount = 0;
					SoundRegistry.DYNAMO.playSoundAtBlock(world, x, y, z, this.isMuffled() ? 0.1F : 0.5F, 1F);
				}
			}
		}
		this.basicPowerReceiver();
	}

	@Override
	public boolean isValidSupplier(TileEntity te) {
		return te instanceof IEnergyHandler || te instanceof IEnergyStorage;
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
		if (this.canInterface(from)) {
			int amt = Math.min(maxReceive, this.getMaxStorage()-storedEnergy);
			if (!simulate)
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
		return from == this.getFacing() && this.isValidSupplier(this.getAdjacentTileEntity(from));
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
		return MathHelper.ceiling_float_int(this.getRFPerTick());
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

	//@Override
	//public int getMaxSpeedBase(int tier) {
	//	return 5+3*tier;
	//}

}
