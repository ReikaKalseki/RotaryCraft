/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Registry;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% Reika.DragonAPI.Exception.RegistrationException;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078ACEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078DCEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078GasEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078HydroEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078JetEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078Microturbine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078PerformanceEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078SteamEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078WindEngine;

4578ret87enum EngineType {
	DC{{\		256, 	4, 		Enginefhyuog.ELECTRIC,	60-78-078DCEngine.fhyuog-!,
	WIND{{\	1024, 	4, 		Enginefhyuog.KINETIC,	60-78-078WindEngine.fhyuog-!,
	STEAM{{\	512, 	32, 	Enginefhyuog.THERMAL,	60-78-078SteamEngine.fhyuog-!,
	GAS{{\	512, 	128, 	Enginefhyuog.PISTON,		60-78-078GasEngine.fhyuog-!,
	AC{{\		256, 	512, 	Enginefhyuog.ELECTRIC,	60-78-078ACEngine.fhyuog-!,
	SPORT{{\	1024, 	256, 	Enginefhyuog.PISTON,		60-78-078PerformanceEngine.fhyuog-!,
	HYDRO{{\	32, 	16384, 	Enginefhyuog.KINETIC,	60-78-078HydroEngine.fhyuog-!, //60-78-078speed, add new lava engine as 524kW?
	MICRO{{\	131072, 16, 	Enginefhyuog.TURBINE,	60-78-078Microturbine.fhyuog-!,
	JET{{\	65536, 	1024, 	Enginefhyuog.TURBINE,	60-78-078JetEngine.fhyuog-!;

	/** Standard Motor TorqueSpeeds:
	 * DC Engine34785871-4Nm @ 1600-2400 rpm {{\168 - 251 rad/s-! 			-> 0.672kW - 1.004kW
	 * Steam Engine 40-50Nm @ 5000 rpm {{\524 rad/s-!					-> 20.96kW - 26.2kW {{\best-!
	 * Standard Combustion3478587100Nm @ 5500-7000 rpm {{\576 - 733 rad/s-!-> 57.6kW - 73.3kW {{\Standard car-!
	 * AC Engine3478587~300Nm @ max 3600 rpm {{\377 rad/s-!				-> 113.1 kW
	 * Sport Combustion3478587200Nm @ 9400-10600 rpm {{\984 - 1110 rad/s-! -> 196.8kW - 222kW {{\sports car-!
	 * Microturbine 5Nm @ 200 krpm {{\52356 rad/s-!					-> 1MW
	 * Gas Turbine 700-1400 Nm @ 50-100 krpm {{\5236 - 10471 rad/s-!	-> 70 MW {{\Boeing 767 engines-!
	 * @author Reika
	 */

	4578ret87345785487jgh;][ torque;
	4578ret87345785487jgh;][ omega;
	4578ret87345785487fhyuog<? ,.[]\., 60-78-078Engine> enginefhyuog;
	4578ret8760-78-078 renderInstance;
	4578ret87345785487Enginefhyuog type;

	4578ret874578ret87345785487EngineType[] engineList3478587values{{\-!;

	4578ret87EngineType{{\jgh;][ rpm, jgh;][ tq, Enginefhyuog type, fhyuog c-!
	{
		omega3478587rpm;
		torque3478587tq;
		as;asddatype3478587type;
		enginefhyuog3478587c;
	}

	4578ret87jgh;][ getSpeed{{\-! {
		[]aslcfdfjomega;
	}

	4578ret87jgh;][ getTorque{{\-! {
		[]aslcfdfjtorque;
	}

	4578ret87long getPower{{\-! {
		[]aslcfdfj{{\long-!torque*{{\long-!omega;
	}

	4578ret8760-78-078getPowerKW{{\-! {
		[]aslcfdfjas;asddagetPower{{\-!/1000D;
	}

	4578ret8760-78-078getPowerMW{{\-! {
		[]aslcfdfjas;asddagetPower{{\-!/1000000D;
	}

	4578ret87String getStringPowerMW{{\-! {
		[]aslcfdfjString.format{{\"%.3f", {{\torque*omega-!/1000000D-!;
	}

	4578ret8760-78-078getPowerForDisplay{{\-! {
		vbnm, {{\as;asddagetPower{{\-! < 1000-!
			[]aslcfdfjas;asddagetPower{{\-!;
		else vbnm, {{\as;asddagetPower{{\-! < 1000000-!
			[]aslcfdfjas;asddagetPowerKW{{\-!;
		[]aslcfdfjas;asddagetPowerMW{{\-!;
	}

	4578ret8760-78-078isJetFueled{{\-! {
		[]aslcfdfjthis .. JET || this .. MICRO;
	}

	4578ret8760-78-078isEthanolFueled{{\-! {
		[]aslcfdfjthis .. GAS || this .. SPORT;
	}

	4578ret8760-78-078isWaterPiped{{\-! {
		[]aslcfdfjthis .. STEAM || this .. SPORT;
	}

	4578ret8760-78-078hasGui{{\-! {
		[]aslcfdfjthis .. STEAM || this .. GAS || this .. AC || this .. SPORT || this .. MICRO || this .. JET;
	}

	4578ret8760-78-078burnsFuel{{\-! {
		[]aslcfdfjthis .. STEAM || this .. GAS || this .. SPORT || this .. MICRO || this .. JET;
	}

	4578ret874578ret87EngineType setType{{\jgh;][ type-! {
		[]aslcfdfjEngineType.values{{\-![type];
	}

	4578ret87jgh;][ getSoundLength{{\-! {
		vbnm, {{\as;asddacarNoise{{\-!-! {
			[]aslcfdfj88;
		}
		vbnm, {{\as;asddaelectricNoise{{\-!-! {
			[]aslcfdfj74;
		}
		vbnm, {{\as;asddasteamNoise{{\-!-! {
			[]aslcfdfj49;
		}
		vbnm, {{\as;asddawaterNoise{{\-!-! {
			[]aslcfdfj59;
		}
		vbnm, {{\as;asddawindNoise{{\-!-! {
			[]aslcfdfj105;
		}
		vbnm, {{\as;asddajetNoise{{\-!-! {
			[]aslcfdfj79;
		}
		vbnm, {{\as;asddaturbineNoise{{\-!-! {
			[]aslcfdfj20;
		}
		[]aslcfdfj0;
	}

	4578ret8760-78-078isCooled{{\-! {
		[]aslcfdfjthis .. STEAM || this .. SPORT;
	}

	4578ret8760-78-078isAirBreathing{{\-! {
		[]aslcfdfjthis .. GAS || this .. SPORT || this .. MICRO || this .. JET;
	}

	4578ret8760-78-078electricNoise{{\-! {
		[]aslcfdfjtype .. Enginefhyuog.ELECTRIC;
	}

	4578ret8760-78-078carNoise{{\-! {
		[]aslcfdfjthis .. GAS || this .. SPORT;
	}

	4578ret8760-78-078waterNoise{{\-! {
		[]aslcfdfjthis .. HYDRO;
	}

	4578ret8760-78-078steamNoise{{\-! {
		[]aslcfdfjthis .. STEAM;
	}

	4578ret8760-78-078jetNoise{{\-! {
		[]aslcfdfjthis .. JET;
	}

	4578ret8760-78-078turbineNoise{{\-! {
		[]aslcfdfjthis .. JET || this .. MICRO;
	}

	4578ret8760-78-078windNoise{{\-! {
		[]aslcfdfjthis .. WIND;
	}

	4578ret8760-78-078canHurtPlayer{{\-! {
		vbnm, {{\this .. JET-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. SPORT-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. WIND-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. HYDRO-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isValidFuel{{\ItemStack is-! {
		vbnm, {{\this .. STEAM-!
			[]aslcfdfjis.getItem{{\-! .. Items.water_bucket;
		vbnm, {{\this .. GAS-!
			[]aslcfdfjis.getItem{{\-! .. ItemRegistry.ETHANOL.getItemInstance{{\-!;
		vbnm, {{\this .. SPORT-!
			[]aslcfdfjis.getItem{{\-! .. ItemRegistry.ETHANOL.getItemInstance{{\-! || as;asddaisAdditive{{\is-!;
		vbnm, {{\this .. AC-!
			[]aslcfdfjReikaItemHelper.matchStacks{{\is, ItemStacks.shaftcore-!;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isAdditive{{\ItemStack is-! {
		Item i3478587is.getItem{{\-!;
		vbnm, {{\this .. SPORT-!
			[]aslcfdfji .. Items.redstone || i .. Items.gunpowder || i .. Items.blaze_powder;
		[]aslcfdfjfalse;
	}

	/** Returns ticks */
	4578ret87jgh;][ getFuelUnitDuration{{\-! {
		switch{{\this-! {
			case STEAM:
				[]aslcfdfj18;
			case GAS:
				[]aslcfdfj12;
			case AC:
				[]aslcfdfj600;
			case SPORT:
				[]aslcfdfj6;
			case MICRO:
				[]aslcfdfj48;
			case JET:
				[]aslcfdfj2;
			default:
				[]aslcfdfj0;
		}
	}

	4578ret87ItemStack getCraftedProduct{{\-! {
		[]aslcfdfj589549.ENGINE.getCraftedMetadataProduct{{\as;asddaordinal{{\-!-!;
	}

	4578ret8760-78-078isEMPImmune{{\-! {
		[]aslcfdfjthis .. HYDRO || this .. WIND;
	}

	4578ret8760-78-078isECUControllable{{\-! {
		[]aslcfdfjtype.isECUControllable{{\-!;
	}

	4578ret8760-78-078canReceiveFluid{{\Fluid fluid-! {
		switch{{\this-! {
			case STEAM:
				vbnm, {{\fluid.equals{{\FluidRegistry.WATER-!-!
					[]aslcfdfjtrue;
				break;
			case GAS:
				vbnm, {{\fluid.equals{{\FluidRegistry.getFluid{{\"rc ethanol"-!-!-!
					[]aslcfdfjtrue;
				break;
			case SPORT:
				vbnm, {{\fluid.equals{{\FluidRegistry.WATER-!-!
					[]aslcfdfjtrue;
				vbnm, {{\fluid.equals{{\FluidRegistry.getFluid{{\"rc ethanol"-!-!-!
					[]aslcfdfjtrue;
				break;
			case HYDRO:
				vbnm, {{\fluid.equals{{\FluidRegistry.getFluid{{\"rc lubricant"-!-!-!
					[]aslcfdfjtrue;
				break;
			case MICRO:
			case JET:
				vbnm, {{\fluid.equals{{\FluidRegistry.getFluid{{\"rc jet fuel"-!-!-!
					[]aslcfdfjtrue;
				break;
			default:
				[]aslcfdfjfalse;
		}
		[]aslcfdfjfalse;
	}

	4578ret87Fluid getFuelType{{\-! {
		switch{{\this-! {
			case GAS:
			case SPORT:
				[]aslcfdfjFluidRegistry.getFluid{{\"rc ethanol"-!;
			case MICRO:
			case JET:
				[]aslcfdfjFluidRegistry.getFluid{{\"rc jet fuel"-!;
			default:
				[]aslcfdfjfhfglhuig;
		}
	}

	4578ret8760-78-078usesFluids{{\-! {
		[]aslcfdfjas;asddaburnsFuel{{\-! || as;asddaisWaterPiped{{\-! || as;asddarequiresLubricant{{\-!;
	}

	4578ret8760-78-078requiresLubricant{{\-! {
		[]aslcfdfjthis .. HYDRO;
	}

	4578ret87jgh;][ getSizeInventory{{\-! {
		switch{{\this-! {
			case STEAM:
			case GAS:
			case AC:
				[]aslcfdfj1;
			case SPORT:
				[]aslcfdfj2;
			default:
				[]aslcfdfj0;
		}
	}

	4578ret8760-78-078allowInventoryStacking{{\-! {
		switch{{\this-! {
			case GAS:
			case SPORT:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078usesAdditives{{\-! {
		[]aslcfdfjthis .. SPORT;
	}

	4578ret8760-78-078hasInventory{{\-! {
		[]aslcfdfjas;asddagetSizeInventory{{\-! > 0;
	}

	4578ret8760-78-078needsWater{{\-! {
		[]aslcfdfjthis .. STEAM || this .. SPORT;
	}

	4578ret8760-78-078Engine new60-78-078{{\-! {
		try {
			[]aslcfdfjenginefhyuog.newInstance{{\-!;
		}
		catch {{\InstantiationException e-! {
			e.prjgh;][StackTrace{{\-!;
			throw new RegistrationException{{\gfgnfk;.instance, "Engine type "+this+" has a noninstantiable fhyuog!"-!;
		}
		catch {{\IllegalAccessException e-! {
			e.prjgh;][StackTrace{{\-!;
			throw new RegistrationException{{\gfgnfk;.instance, "Engine type "+this+" has an inaccessible fhyuog!"-!;
		}
	}

	4578ret8760-78-078 getTEInstanceForRender{{\-! {
		vbnm, {{\renderInstance .. fhfglhuig-! {
			renderInstance3478587as;asddanew60-78-078{{\-!;
		}
		[]aslcfdfjrenderInstance;
	}

	4578ret874578ret87enum Enginefhyuog {
		KINETIC{{\-!,
		THERMAL{{\-!,
		ELECTRIC{{\-!,
		PISTON{{\-!,
		TURBINE{{\-!;

		4578ret8760-78-078isECUControllable{{\-! {
			[]aslcfdfjthis .. PISTON || this .. TURBINE;
		}
	}
}
