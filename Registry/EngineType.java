/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityACEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityDCEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityGasEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityHydroEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityMicroturbine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityPerformanceEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntitySteamEngine;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityWindEngine;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public enum EngineType {
	DC(256, 4, TileEntityDCEngine.class),
	WIND(1024, 4, TileEntityWindEngine.class),
	STEAM(512, 32, TileEntitySteamEngine.class),
	GAS(512, 128, TileEntityGasEngine.class),
	AC(256, 512, TileEntityACEngine.class),
	SPORT(1024, 256, TileEntityPerformanceEngine.class),
	HYDRO(32, 16384, TileEntityHydroEngine.class), //double speed, add new lava engine as 524kW?
	MICRO(131072, 16, TileEntityMicroturbine.class),
	JET(65536, 1024, TileEntityJetEngine.class);

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
	public final Class<? extends TileEntityEngine> engineClass;
	private TileEntity renderInstance;

	public static final EngineType[] engineList = values();

	private EngineType(int rpm, int tq, Class c)
	{
		omega = rpm;
		torque = tq;
		engineClass = c;
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

	public int getSoundLength() {
		if (this.carNoise()) {
			return 88;
		}
		if (this.electricNoise()) {
			return 74;
		}
		if (this.steamNoise()) {
			return 49;
		}
		if (this.waterNoise()) {
			return 59;
		}
		if (this.windNoise()) {
			return 105;
		}
		if (this.jetNoise()) {
			return 79;
		}
		if (this.turbineNoise()) {
			return 20;
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

	public boolean isValidFuel(ItemStack is) {
		if (this == STEAM)
			return is.getItem() == Items.water_bucket;
		if (this == GAS)
			return is.getItem() == ItemRegistry.ETHANOL.getItemInstance();
		if (this == SPORT)
			return is.getItem() == ItemRegistry.ETHANOL.getItemInstance() || this.isAdditive(is);
		if (this == AC)
			return ReikaItemHelper.matchStacks(is, ItemStacks.shaftcore);
		return false;
	}

	public boolean isAdditive(ItemStack is) {
		Item i = is.getItem();
		if (this == SPORT)
			return i == Items.redstone || i == Items.gunpowder || i == Items.blaze_powder;
		return false;
	}

	/** Returns ticks */
	public int getFuelUnitDuration() {
		switch(this) {
		case STEAM:
			return 18;
		case GAS:
			return 12;
		case AC:
			return 1200;
		case SPORT:
			return 6;
		case MICRO:
			return 24;
		case JET:
			return 4;
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

	public int getSizeInventory() {
		switch(this) {
		case STEAM:
		case GAS:
		case AC:
			return 1;
		case SPORT:
			return 2;
		default:
			return 0;
		}
	}

	public boolean allowInventoryStacking() {
		switch(this) {
		case GAS:
		case SPORT:
			return true;
		default:
			return false;
		}
	}

	public ItemStack getItem() {
		return MachineRegistry.ENGINE.getCraftedMetadataProduct(this.ordinal());
	}

	public boolean usesAdditives() {
		return this == SPORT;
	}

	public boolean hasInventory() {
		return this.getSizeInventory() > 0;
	}

	public boolean needsWater() {
		return this == STEAM || this == SPORT;
	}

	public TileEntityEngine newTileEntity() {
		try {
			return engineClass.newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			throw new RegistrationException(RotaryCraft.instance, "Engine type "+this+" has a noninstantiable class!");
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RegistrationException(RotaryCraft.instance, "Engine type "+this+" has an inaccessible class!");
		}
	}

	public TileEntity getTEInstanceForRender() {
		if (renderInstance == null) {
			renderInstance = this.newTileEntity();
		}
		return renderInstance;
	}
}