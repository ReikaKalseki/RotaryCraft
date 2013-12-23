/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.awt.Color;

import net.minecraft.nbt.NBTTagCompound;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;

public abstract class EnergyToPowerBase extends TileEntityIOMachine implements SimpleProvider, PowerGenerator, GuiController {

	private static final int MINBASE = -1;
	private static final int MAXBASE = 11; //2048 Nm -> 2.09 MW

	protected int storedEnergy;

	protected int base = -1;

	public final int getStoredPower() {
		return storedEnergy;
	}

	public final void setStoredPower(int e) {
		storedEnergy = e;
	}

	public abstract int getBaseOmega();

	public abstract int getMaxStorage();

	public final long getPowerLevel() {
		return this.getSpeed()*this.getTorqueLevel();
	}

	public final int getSpeed() {
		if (base < 0)
			return 0;
		return this.getBaseOmega();
	}

	public final int getTorqueLevel() {
		if (base < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, base);
	}

	public final boolean hasEnoughEnergy() {
		float energy = this.getStoredPower();
		return energy > this.getConsumedUnitsPerTick();
	}

	public abstract int getConsumedUnitsPerTick();

	public final int getTier() {
		return base;
	}

	public final void increment() {
		if (base < MAXBASE)
			base++;
	}

	public final void decrement() {
		if (base > MINBASE)
			base--;
	}

	public final int getEnergyScaled(int h) {
		return (int)(this.getPercentStorage()*h);
	}

	public final float getPercentStorage() {
		return this.getStoredPower()/(float)this.getMaxStorage();
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("storage", storedEnergy);
		NBT.setInteger("tier", base);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		storedEnergy = NBT.getInteger("storage");
		base = NBT.getInteger("tier");
	}

	@Override
	public final long getCurrentPower() {
		return power;
	}

	@Override
	public final PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public final boolean canProvidePower() {
		return true;
	}

	public abstract String getUnitDisplay();

	public abstract Color getPowerColor();

}
