/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum EngineType {
	DC(256, 4),
	WIND(1024, 4),
	STEAM(512, 32),
	GAS(512, 128),
	AC(256, 512),
	SPORT(1024, 256),
	HYDRO(32, 16384),
	MICRO(131072, 16),
	JET(65536, 1024);

	/** Standard Motor TorqueSpeeds:
	 * DC Engine = 1-4Nm @ 1600-2400 rpm (168 - 251 rad/s) 			-> 0.672kW - 1.004kW
	 * Steam Engine 40-50Nm @ 5000 rpm (524 rad/s)					-> 20.96kW - 26.2kW (best)
	 * Standard Combustion = 100Nm @ 5500-7000 rpm (576 - 733 rad/s)-> 57.6kW - 73.3kW (Standard car)
	 * AC Engine = ~300Nm @ max 3600 rpm (377 rad/s)				-> 113.1 kW
	 * Sport Combustion = 200Nm @ 9400-10600 rpm (984 - 1110 rad/s) -> 196.8kW - 222kW (sports car)
	 * Microturbine 5Nm @ 200 krpm (52356 rad/s)					-> 1MW
	 * Gas Turbine 700-1400 Nm @ 50-100 krpm (5236 - 10471 rad/s)	-> 70 MW (Boeing 767 engines)
	 * @author Reika
	 */

	private final int torque;
	private final int omega;

	public static final EngineType[] engineList = EngineType.values();

	private EngineType(int rpm, int tq)
	{
		omega = rpm;
		torque = tq;
	}

	public int getSpeed() {
		return omega;
	}

	public int getTorque() {
		return torque;
	}

	public long getPower() {
		return torque*omega;
	}

	public double getPowerKW() {
		return (torque*omega)/1000D;
	}

	public double getPowerMW() {
		return (torque*omega)/1000000D;
	}

	public String getStringPowerMW() {
		return String.format("%.3f", (torque*omega)/1000000D);
	}

	public double getPowerForDisplay() {
		if (this.getPower() < 1000)
			return this.getPower();
		else if (this.getPower() < 1000000)
			return this.getPowerKW();
		return this.getPowerMW();
	}

	public boolean isJetFueled() {
		return (this == JET || this == MICRO);
	}

	public boolean isEthanolFueled() {
		return (this == GAS || this == SPORT);
	}

	public boolean isWaterPiped() {
		return (this == STEAM || this == SPORT);
	}

	public boolean hasGui() {
		return (this == STEAM || this == GAS || this == AC || this == SPORT || this == MICRO || this == JET);
	}

	public boolean burnsFuel() {
		return (this == STEAM || this == GAS || this == SPORT || this == MICRO || this == JET);
	}

	public static EngineType setType(int type) {
		return EngineType.values()[type];
	}

	public int getSoundLength(int FOD, float factor) {
		//Minor corrections
		if (factor == 2.5F && this.carNoise())
			factor = 1.81F;
		if (factor == 2.5F && this.turbineNoise()) {
			factor = 2F;
		}
		if (this.jetNoise()) {
			factor += 0.0125F;
		}

		if (this.carNoise()) {
			return (int)(88*factor);
		}
		if (this.electricNoise()) {
			return (int)(74*factor);
		}
		if (this.steamNoise()) {
			return (int)(49*factor);
		}
		if (this.waterNoise()) {
			return (int)(59*factor);
		}
		if (this.windNoise()) {
			return (int)(105*factor);
		}
		if (this.jetNoise()) {
			return (int)((79+FOD*11)*factor);
		}
		if (this.turbineNoise()) {
			return (int)(20*factor);
		}
		return 0;
	}

	public boolean isCooled() {
		return (this == STEAM || this == SPORT);
	}

	public boolean isAirBreathing() {
		return (this == GAS || this == SPORT || this == MICRO || this == JET);
	}

	public boolean electricNoise() {
		return (this == DC || this == AC);
	}

	public boolean carNoise() {
		return (this == GAS || this == SPORT);
	}

	public boolean waterNoise() {
		return (this == HYDRO);
	}

	public boolean steamNoise() {
		return (this == STEAM);
	}

	public boolean jetNoise() {
		return (this == JET);
	}

	public boolean turbineNoise() {
		return (this == JET || this == MICRO);
	}

	public boolean windNoise() {
		return (this == WIND);
	}

	public boolean canHurtPlayer() {
		if (this == JET)
			return true;
		if (this == SPORT)
			return true;
		if (this == WIND)
			return true;
		if (this == HYDRO)
			return true;
		return false;
	}

	public boolean hasTemperature() {
		if (this == SPORT)
			return true;
		if (this == STEAM)
			return true;
		if (this == JET)
			return true;
		return false;
	}

	public boolean isValidFuel(ItemStack is) {
		if (this == GAS)
			return is.itemID == ItemRegistry.ETHANOL.getShiftedID();
		if (this == SPORT)
			return is.itemID == ItemRegistry.ETHANOL.getShiftedID() || this.isAdditive(is);
		if (this == AC)
			return is.itemID == ItemStacks.shaftcore.itemID && is.getItemDamage() == ItemStacks.shaftcore.getItemDamage();
		return false;
	}

	public boolean isAdditive(ItemStack is) {
		if (this == SPORT)
			return is.itemID == Item.redstone.itemID || is.itemID == Item.gunpowder.itemID || is.itemID == Item.blazePowder.itemID;
		return false;
	}

	/** Returns ticks */
	public int getFuelUnitDuration() {
		switch(this) {
		case STEAM:
			return 1800;
		case GAS:
			return 1200;
		case AC:
			return 1200;
		case SPORT:
			return 600;
		case MICRO:
			return 450;
		case JET:
			return 225;
		default:
			return 0;
		}
	}

	public ItemStack getCraftedProduct() {
		return MachineRegistry.ENGINE.getCraftedMetadataProduct(this.ordinal());
	}

	public boolean isEMPImmune() {
		return this == HYDRO || this == WIND;
	}

	public boolean isECUControllable() {
		switch(this) {
		case DC:
		case HYDRO:
		case STEAM:
		case WIND:
			return false;
		default:
			return true;
		}
	}

	public boolean canReceiveFluid(Fluid fluid) {
		switch(this) {
		case STEAM:
			if (fluid.equals(FluidRegistry.WATER))
				return true;
			break;
		case GAS:
			if (fluid.equals(FluidRegistry.getFluid("rc ethanol")))
				return true;
			break;
		case SPORT:
			if (fluid.equals(FluidRegistry.WATER))
				return true;
			if (fluid.equals(FluidRegistry.getFluid("rc ethanol")))
				return true;
			break;
		case HYDRO:
			if (fluid.equals(FluidRegistry.getFluid("lubricant")))
				return true;
			break;
		case MICRO:
		case JET:
			if (fluid.equals(FluidRegistry.getFluid("jet fuel")))
				return true;
			break;
		default:
			return false;
		}
		return false;
	}

	public Fluid getFuelType() {
		switch(this) {
		case GAS:
		case SPORT:
			return FluidRegistry.getFluid("rc ethanol");
		case MICRO:
		case JET:
			return FluidRegistry.getFluid("jet fuel");
		default:
			return null;
		}
	}

	public boolean requiresLubricant() {
		return this == HYDRO;
	}
}
