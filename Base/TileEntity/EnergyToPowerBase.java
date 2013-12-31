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
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;

public abstract class EnergyToPowerBase extends TileEntityIOMachine implements SimpleProvider, PowerGenerator, GuiController {

	private static final int MINBASE = -1;
	private static final int MAXBASE = 13;

	protected int storedEnergy;

	protected int basetorque = -1;
	protected int baseomega = -1;

	protected ForgeDirection facingDir;

	public final int getStoredPower() {
		return storedEnergy;
	}

	public final void setStoredPower(int e) {
		storedEnergy = e;
	}

	public abstract int getMaxStorage();

	public final long getPowerLevel() {
		return this.getSpeed()*this.getTorque();
	}

	public final int getSpeed() {
		if (baseomega < 0 || basetorque < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, baseomega);
	}

	public final int getTorque() {
		if (baseomega < 0 || basetorque < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, basetorque);
	}

	public final boolean hasEnoughEnergy() {
		float energy = this.getStoredPower();
		return energy > this.getConsumedUnitsPerTick();
	}

	public abstract int getConsumedUnitsPerTick();

	public final int getTier() {
		return basetorque;
	}

	public final void incrementTorque() {
		if (basetorque < MAXBASE && baseomega+basetorque < MAXBASE*2-3)
			basetorque++;
	}

	public final void decrementTorque() {
		if (basetorque > MINBASE)
			basetorque--;
	}

	public final void incrementOmega() {
		if (baseomega < MAXBASE && baseomega+basetorque < MAXBASE*2-3)
			baseomega++;
	}

	public final void decrementOmega() {
		if (baseomega > MINBASE)
			baseomega--;
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
		NBT.setInteger("tier", basetorque);
		NBT.setInteger("tiero", baseomega);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		storedEnergy = NBT.getInteger("storage");
		basetorque = NBT.getInteger("tier");
		baseomega = NBT.getInteger("tiero");
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

	public final ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

}
