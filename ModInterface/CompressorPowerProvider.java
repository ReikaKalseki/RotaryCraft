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

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerProvider;

public class CompressorPowerProvider extends PowerProvider {

	public CompressorPowerProvider()
	{
		powerLoss = 0;
		powerLossRegularity = 72000;

		this.configure();
	}

	public void configure()
	{
		super.configure(0, 0, 0, 0, 0);
	}

	@Override
	public boolean update(IPowerReceptor receptor)
	{
		return false;
	}

	public void addEnergy(float quantity)
	{
		energyStored += quantity;

		if (energyStored > maxEnergyStored)
			energyStored = maxEnergyStored;
	}

	public void subtractEnergy(float quantity)
	{
		energyStored -= quantity;

		if (energyStored < 0.0F)
			energyStored = 0.0F;
	}

	public void setEnergyStored(float quantity)
	{
		energyStored = quantity;

		if (energyStored > maxEnergyStored)
			energyStored = maxEnergyStored;
		else if (energyStored < 0.0F)
			energyStored = 0.0F;
	}

	public void roundEnergyStored()
	{
		energyStored = Math.round(energyStored);
	}
}
