/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Conversion;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.Power.ReikaRFHelper;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;

//@Strippable(value = {"cofh.api.energy.IEnergyHandler"})
public class TileEntityMagnetic extends EnergyToPowerBase implements IEnergyHandler { //Handler because EnderIO uses it

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
		return te instanceof IEnergyHandler || te instanceof IEnergyProvider || te instanceof IEnergyStorage;
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
		if (this.canConnectEnergy(from) && maxReceive >= this.getMinimumCurrent()) {
			int amt = Math.min(maxReceive, this.getMaxStorage()-storedEnergy);
			if (!simulate)
				storedEnergy += amt;
			return amt;
		}
		return 0;
	}

	private int getMinimumCurrent() {
		switch(this.getTier()) {
			case 0:
				return 1;
			case 1:
				return 3;
			case 2:
				return 24;
			case 3:
				return 187;
			case 4:
				return 1491;
			case 5:
				return 11925;
			default:
				return 0;
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from == this.getFacing();// && this.isValidSupplier(this.getAdjacentTileEntity(from));
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
		return 1+this.getMinimumCurrent()*200;//ReikaMathLibrary.intpow2(10, this.getTier());//TileEntityPneumaticEngine.maxMJ*10;
	}

	@Override
	protected int getIdealConsumedUnitsPerTick() {
		return this.getRFPerTick();
	}

	private int getRFPerTick() {
		return (int)(this.getPowerLevel()/ReikaRFHelper.getWattsPerRF());
	}

	@Override
	public String getUnitDisplay() {
		return "RF";
	}

	@Override
	public int getPowerColor() {
		return 0xff0000;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	//@Override
	//public int getMaxSpeedBase(int tier) {
	//	return 5+3*tier;
	//}

}
