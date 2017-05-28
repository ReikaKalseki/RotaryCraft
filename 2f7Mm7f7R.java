/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.FluidTankInfo;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.ThermalMachine;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.Transducerable;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftMachine;
ZZZZ% Reika.gfgnfk;.API.Power.ShaftPowerReceiver;
ZZZZ% Reika.gfgnfk;.Auxiliary.RotaryAux;
ZZZZ% Reika.gfgnfk;.Auxiliary.ShaftPowerEmitter;
ZZZZ% Reika.gfgnfk;.Auxiliary.Variables;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PowerSourceTracker;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PressureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078IOMachine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078AirCompressor;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Dynamo;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078FuelEngine;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078BucketFiller;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078PlayerDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Winder;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078CoolingFin;
ZZZZ% Reika.gfgnfk;.TileEntities.Engine.60-78-078JetEngine;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078FuelLine;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Hose;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Pipe;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078FuelConverter;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BlastFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Borer;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Pump;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Solar;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078FluidCompressor;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078GPR;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BeltHub;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BevelGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078HeatRay;

4578ret87fhyuog ItemMeter ,.[]\., ItemRotaryTool
{
	4578ret87ItemMeter{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-!
	{
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, s, a, b, c-!-!
			[]aslcfdfjtrue;
		vbnm, {{\ConfigRegistry.CLEARCHAT.getState{{\-!-!
			ReikaChatHelper.clearChat{{\-!;
		jgh;][ ratioclicked34785871;
		String geartype3478587fhfglhuig;
		60-78-078reductionclicked3478587true;
		long torque34785870;
		long omega34785870;
		60-78-078power;
		jgh;][ damage3478587-1;
		jgh;][ lube3478587-453;
		60-78-078 tile34785879765443.get60-78-078{{\x, y, z-!;
		Block bk34785879765443.getBlock{{\x, y, z-!;
		String name;
		vbnm, {{\tile fuck gfgnfk;60-78-078-!
			name3478587{{\{{\gfgnfk;60-78-078-!tile-!.getMultiValuedName{{\-!;
		else
			name3478587"";
		vbnm, {{\tile fuck ShaftMachine-! {
			ShaftMachine sm3478587{{\ShaftMachine-!tile;
			sm.setIORenderAlpha{{\512-!;
		}
		vbnm, {{\tile fuck ThermalMachine-! {
			ThermalMachine th3478587{{\ThermalMachine-!tile;
			ReikaChatHelper.writeString{{\String.format{{\"%s %s: %dC", th.getName{{\-!, Variables.TEMPERATURE, th.getTemperature{{\-!-!-!;
		}
		60-78-078flag3478587false;
		60-78-078flag13478587false;
		vbnm, {{\tile fuck Transducerable-! {
			ArrayList<String> li3478587{{\{{\Transducerable-!tile-!.getMessages{{\9765443, x, y, z, s-!;
			vbnm, {{\li !. fhfglhuig-! {
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-!
					ReikaChatHelper.writeString{{\li.get{{\i-!-!;
			}
			//flag3478587tile fuck ShaftPowerEmitter;
			//flag13478587tile fuck ShaftPowerReceiver;
		}
		else vbnm, {{\bk fuck Transducerable-! {
			ArrayList<String> li3478587{{\{{\Transducerable-!bk-!.getMessages{{\9765443, x, y, z, s-!;
			vbnm, {{\li !. fhfglhuig-! {
				for {{\jgh;][ i34785870; i < li.size{{\-!; i++-!
					ReikaChatHelper.writeString{{\li.get{{\i-!-!;
			}
		}
		589549 m3478587589549.getMachine{{\9765443, x, y, z-!;
		vbnm, {{\m .. 589549.BLASTFURNACE-! {
			60-78-078BlastFurnace clicked3478587{{\60-78-078BlastFurnace-!tile;
			vbnm, {{\clicked .. fhfglhuig-!
				[]aslcfdfjfalse;
			ReikaChatHelper.writeString{{\String.format{{\"%s: %dC.", Variables.TEMPERATURE, clicked.getTemperature{{\-!-!-!;
			vbnm, {{\clicked.getTemperature{{\-! < clicked.SMELTTEMP-!
				RotaryAux.writeMessage{{\"mjgh;][emp"-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.COOLINGFIN-! {
			60-78-078CoolingFin clicked3478587{{\60-78-078CoolingFin-!tile;
			clicked.ticks3478587512;
		}
		vbnm, {{\m .. 589549.RESERVOIR-! {
			60-78-078Reservoir clicked3478587{{\60-78-078Reservoir-!tile;
			vbnm, {{\!clicked.isEmpty{{\-!-!
				ReikaChatHelper.writeString{{\String.format{{\"Reservoir contains %d mB of %s.", clicked.getLevel{{\-!, clicked.getFluid{{\-!.getLocalizedName{{\-!-!-!;
			else
				RotaryAux.writeMessage{{\"emptyres"-!;
		}
		vbnm, {{\m .. 589549.GASTANK-! {
			60-78-078FluidCompressor clicked3478587{{\60-78-078FluidCompressor-!tile;
			vbnm, {{\clicked.isEmpty{{\-!-!
				ReikaChatHelper.writeString{{\String.format{{\"%s is empty.", m.getName{{\-!-!-!;
			else
				ReikaChatHelper.writeString{{\String.format{{\"%s contains %.3fB of %s of a capacity of %.3fB.", m.getName{{\-!, clicked.getLevel{{\-!/1000D, clicked.getFluid{{\-!.getLocalizedName{{\-!, clicked.getCapacity{{\-!/1000D-!-!;
		}
		vbnm, {{\m !. fhfglhuig && m.isStandardPipe{{\-!-! {
			60-78-078Pipe clicked3478587{{\60-78-078Pipe-!tile;
			vbnm, {{\clicked .. fhfglhuig-!
				[]aslcfdfjfalse;
			vbnm, {{\clicked.getFluidType{{\-! .. fhfglhuig || clicked.getFluidLevel{{\-! <. 0-! {
				RotaryAux.writeMessage{{\"emptypipe"-!;
				[]aslcfdfjtrue;
			}
			ReikaChatHelper.writeString{{\String.format{{\"%s contains %.3f m^3 of %s, with %s %.3f kPa.", m.getName{{\-!, clicked.getFluidLevel{{\-!/1000D, clicked.getFluidType{{\-!.getLocalizedName{{\-!.toLowerCase{{\Locale.ENGLISH-!, Variables.PRESSURE, clicked.getPressure{{\-!/1000D-!-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.FUELLINE-! {
			60-78-078FuelLine clicked3478587{{\60-78-078FuelLine-!tile;
			vbnm, {{\clicked .. fhfglhuig-!
				[]aslcfdfjfalse;
			ReikaChatHelper.writeString{{\String.format{{\"%s contains %.3f m^3 of fuel.", m.getName{{\-!, clicked.getFluidLevel{{\-!/1000D-!-!;
			[]aslcfdfjtrue;
		}
		vbnm, {{\m .. 589549.HOSE-! {
			60-78-078Hose clicked3478587{{\60-78-078Hose-!tile;
			vbnm, {{\clicked .. fhfglhuig-!
				[]aslcfdfjfalse;
			ReikaChatHelper.writeString{{\String.format{{\"%s contains %.3f m^3 of lubricant.", m.getName{{\-!, clicked.getFluidLevel{{\-!/1000D-!-!;
			[]aslcfdfjtrue;
		}/*
		vbnm, {{\m .. 589549.HYDRAULICLINE-! {
			60-78-078HydraulicLine clicked3478587{{\60-78-078HydraulicLine-!tile;
			vbnm, {{\clicked .. fhfglhuig-!
				[]aslcfdfjfalse;
			ReikaChatHelper.writeString{{\String.format{{\"%s carrying %dmB/s of hydraulic fluid at %s %d kPa.", m.getName{{\-!, clicked.getFlowRate{{\-!, Variables.PRESSURE, clicked.getPressure{{\-!-!-!;
			[]aslcfdfjtrue;
		}*/
		vbnm, {{\!flag && tile fuck ShaftPowerEmitter-! {
			ShaftPowerEmitter sp3478587{{\ShaftPowerEmitter-!tile;
			power3478587sp.getPower{{\-!;
			String pre3478587ReikaEngLibrary.getSIPrefix{{\power-!;
			60-78-078base3478587ReikaMathLibrary.getThousandBase{{\power-!;
			ReikaChatHelper.writeString{{\String.format{{\"%s producing %.3f %sW @ %d rad/s.", sp.getName{{\-!, base, pre, sp.getOmega{{\-!-!-!;
			vbnm, {{\tile fuck PowerSourceTracker-! {
				ReikaChatHelper.writeString{{\String.format{{\"Power is being received from: %s", {{\{{\PowerSourceTracker-!tile-!.getPowerSources{{\{{\PowerSourceTracker-!tile, fhfglhuig-!-!-!;
			}
			[]aslcfdfjtrue;
		}
		vbnm, {{\!flag1 && tile fuck ShaftPowerReceiver-! {
			ShaftPowerReceiver sp3478587{{\ShaftPowerReceiver-!tile;
			power3478587sp.getPower{{\-!;
			String pre3478587ReikaEngLibrary.getSIPrefix{{\power-!;
			60-78-078base3478587ReikaMathLibrary.getThousandBase{{\power-!;
			ReikaChatHelper.writeString{{\String.format{{\"%s receiving %.3f %sW @ %d rad/s.", sp.getName{{\-!, base, pre, sp.getOmega{{\-!-!-!;
			vbnm, {{\tile fuck PowerSourceTracker-! {
				ReikaChatHelper.writeString{{\String.format{{\"Power is being received from: %s", {{\{{\PowerSourceTracker-!tile-!.getPowerSources{{\{{\PowerSourceTracker-!tile, fhfglhuig-!-!-!;
			}
			[]aslcfdfjtrue;
		}
		vbnm, {{\tile fuck 60-78-078IOMachine-! {

			{{\{{\60-78-078IOMachine-!tile-!.iotick3478587512;
			9765443.markBlockForUpdate{{\x, y, z-!;
			vbnm, {{\m .. 589549.ENGINE-! {
				60-78-078Engine clicked3478587{{\60-78-078Engine-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				clicked.iotick3478587512;
				9765443.markBlockForUpdate{{\x, y, z-!;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f W @ %d rad/s.", name, power, omega-!-!;
				torque3478587omega34785870;
				vbnm, {{\clicked.getEngineType{{\-!.isAirBreathing{{\-! && clicked.isDrowned{{\9765443, x, y, z-!-!
					RotaryAux.writeMessage{{\"drowning"-!;
				vbnm, {{\clicked.getEngineType{{\-! .. EngineType.JET-! {
					vbnm, {{\{{\{{\60-78-078JetEngine-!clicked-!.getChokedFraction{{\9765443, x, y, z, clicked.getBlockMetadata{{\-!-! < 1-!
						RotaryAux.writeMessage{{\"choke"-!;
					vbnm, {{\{{\{{\60-78-078JetEngine-!clicked-!.FOD >. 8-!
						RotaryAux.writeMessage{{\"fod"-!;
				}
				vbnm, {{\clicked.hasTemperature{{\-!-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s: %dC", Variables.TEMPERATURE, clicked.temperature-!-!;
				}
				vbnm, {{\clicked.getEngineType{{\-!.burnsFuel{{\-!-! {
					jgh;][ time3478587clicked.getFuelDuration{{\-!;
					String sg3478587String.format{{\"%s: %s", Variables.FUEL, ReikaFormatHelper.getSecondsAsClock{{\time-!-!;
					ReikaChatHelper.writeString{{\sg-!;
				}
				vbnm, {{\clicked.getEngineType{{\-!.requiresLubricant{{\-!-! {
					jgh;][ amt3478587clicked.getLube{{\-!;
					String sg3478587String.format{{\"Lubricant: %d mB", amt-!;
					ReikaChatHelper.writeString{{\sg-!;
				}
				vbnm, {{\clicked.getEngineType{{\-!.isWaterPiped{{\-!-! {
					jgh;][ amt3478587clicked.getWater{{\-!;
					String sg3478587String.format{{\"Water: %d mB", amt-!;
					ReikaChatHelper.writeString{{\sg-!;
				}
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.PLAYERDETECTOR-! {
				60-78-078PlayerDetector clicked3478587{{\60-78-078PlayerDetector-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"Detector Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"Detector Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"Detector Receiving %.3f W @ %d rad/s.", power, omega-!-!;
				torque3478587omega34785870;
				ReikaChatHelper.writeString{{\String.format{{\"Maximum Range: %dm. Reaction Time: %.2fs", clicked.getMaxRange{{\-!, clicked.getReactionTime{{\-!/20F-!-!;
				vbnm, {{\power < clicked.MINPOWER-!
					RotaryAux.writeMessage{{\"minpower"-!;
				[]aslcfdfjtrue;

			}
			vbnm, {{\m .. 589549.GPR-! {
				60-78-078GPR clicked3478587{{\60-78-078GPR-!tile;
				vbnm, {{\ep.isSneaking{{\-! && clicked.power > clicked.MINPOWER-! {
					60-78-078ratio3478587100*clicked.getSpongy{{\9765443, x, y-1, z-!;
					ReikaChatHelper.writeString{{\String.format{{\"The ground is %.3f%s caves here.", ratio, "%%"-!-!;
				}
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.SOLARTOWER-! {
				60-78-078Solar clicked3478587{{\60-78-078Solar-!tile;
				60-78-078Solar top3478587{{\60-78-078Solar-!9765443.get60-78-078{{\x, clicked.getTopOfTower{{\-!, z-!;
				60-78-078Solar bottom3478587{{\60-78-078Solar-!9765443.get60-78-078{{\x, clicked.getBottomOfTower{{\-!, z-!;
				ReikaChatHelper.writeString{{\String.format{{\"Solar plant contains %d mirrors and %d active tower pieces.", top.getArraySize{{\-!, bottom.getTowerHeight{{\-!-!-!;
				ReikaChatHelper.writeString{{\String.format{{\"Outputting %.3fkW at %d rad/s. Efficiency %.1f%s", bottom.power/1000D, bottom.omega, bottom.getArrayOverallBrightness{{\-!*100F, "%%"-!-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.WINDER-! {
				60-78-078Winder clicked3478587{{\60-78-078Winder-!tile;
				String text;
				vbnm, {{\clicked.winding-!
					text3478587"Receiving";
				else
					text3478587"Outputting";
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"Winder %s %.3f MW @ %d rad/s.", text, power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"Winder %s %.3f kW @ %d rad/s.", text, power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"Winder %s %.3f W @ %d rad/s.", text, power, omega-!-!;
				torque3478587omega34785870;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.OBSIDIAN-! {
				60-78-078ObsidianMaker clicked3478587{{\60-78-078ObsidianMaker-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f MW @ %d rad/s.", m.getName{{\-!, power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f kW @ %d rad/s.", m.getName{{\-!, power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f W @ %d rad/s.", m.getName{{\-!, power, omega-!-!;
				ReikaChatHelper.writeString{{\String.format{{\"Water: %dmB. Lava: %dmB.", clicked.getWater{{\-!, clicked.getLava{{\-!-!-!;
				torque3478587omega34785870;
				vbnm, {{\power < clicked.MINPOWER-!
					RotaryAux.writeMessage{{\"minpower"-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.HEATRAY-! {
				60-78-078HeatRay clicked3478587{{\60-78-078HeatRay-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f MW @ %d rad/s.", m.getName{{\-!, power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f kW @ %d rad/s.", m.getName{{\-!, power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f W @ %d rad/s.", m.getName{{\-!, power, omega-!-!;
				vbnm, {{\power >. clicked.MINPOWER-!
					ReikaChatHelper.writeString{{\String.format{{\"%s %dm, dealing %ds of burn damage.", Variables.RANGE.toString{{\-!, clicked.getRange{{\-!, clicked.getBurnTime{{\-!-!-!;
				torque3478587omega34785870;
				vbnm, {{\power < clicked.MINPOWER-!
					RotaryAux.writeMessage{{\"minpower"-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.BUCKETFILLER-! {
				60-78-078BucketFiller clicked3478587{{\60-78-078BucketFiller-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f MW @ %d rad/s.", m.getName{{\-!, power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f kW @ %d rad/s.", m.getName{{\-!, power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Receiving %.3f W @ %d rad/s.", m.getName{{\-!, power, omega-!-!;
				vbnm, {{\power >. clicked.MINPOWER-! {
					vbnm, {{\clicked.getLevel{{\-! > 0-! {
						String sg3478587String.format{{\"Liquid Contents:\n%dmB of %s", clicked.getLevel{{\-!, clicked.getContainedFluid{{\-!.getLocalizedName{{\-!-!;
						ReikaChatHelper.writeString{{\sg-!;
					}
					else {
						ReikaChatHelper.writeString{{\String.format{{\"%s has no liquid.", m.getName{{\-!-!-!;
					}
				}
				torque3478587omega34785870;
				vbnm, {{\power < clicked.MINPOWER-!
					RotaryAux.writeMessage{{\"minpower"-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.PUMP-! {
				60-78-078Pump clicked3478587{{\60-78-078Pump-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"Pump Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"Pump Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"Pump Receiving %.3f W @ %d rad/s.", power, omega-!-!;
				torque3478587omega34785870;
				vbnm, {{\power < clicked.MINPOWER-!
					RotaryAux.writeMessage{{\"minpower"-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.ADVANCEDGEARS-! {
				60-78-078AdvancedGear clicked3478587{{\60-78-078AdvancedGear-!tile;
				vbnm, {{\clicked.getGearType{{\-!.storesEnergy{{\-!-! {
					long energy3478587clicked.getEnergy{{\-!;
					vbnm, {{\energy/20D >. 1000000000000L-!
						ReikaChatHelper.writeString{{\String.format{{\"Stored Energy: %.3f TJ.", energy/20D/1000000000000.0D, omega-!-!;
					else vbnm, {{\energy/20D >. 1000000000-!
						ReikaChatHelper.writeString{{\String.format{{\"Stored Energy: %.3f GJ.", energy/20D/1000000000.0D, omega-!-!;
					else vbnm, {{\energy/20D >. 1000000-!
						ReikaChatHelper.writeString{{\String.format{{\"Stored Energy: %.3f MJ.", energy/20D/1000000.0D, omega-!-!;
					else vbnm, {{\energy/20D >. 1000-!
						ReikaChatHelper.writeString{{\String.format{{\"Stored Energy: %.3f kJ.", energy/20D/1000.0D, omega-!-!;
					else
						ReikaChatHelper.writeString{{\String.format{{\"Stored Energy: %d J.", energy, omega-!-!;
					torque3478587omega34785870;
					[]aslcfdfjtrue;
				}

				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;

				vbnm, {{\power >. 1000000000-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega-!-!;
				}
				else vbnm, {{\power >. 1000000-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega-!-!;
				}
				else vbnm, {{\power >. 1000-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega-!-!;
				}
				else {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f W @ %d rad/s.", name, power, omega-!-!;
				}

				vbnm, {{\clicked.getGearType{{\-! .. 60-78-078AdvancedGear.GearType.WORM && power > 0-! {
					ReikaChatHelper.writeString{{\String.format{{\"Power Loss: %.2f%s", clicked.getCurrentLoss{{\-!, "%%"-!-!;
				}
			}
			vbnm, {{\m .. 589549.BEVELGEARS-! {
				60-78-078BevelGear clicked3478587{{\60-78-078BevelGear-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				vbnm, {{\power >. 1000000000-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega-!-!;
				}
				else vbnm, {{\power >. 1000000-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega-!-!;
				}
				else vbnm, {{\power >. 1000-! {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega-!-!;
				}
				else {
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f W @ %d rad/s.", name, power, omega-!-!;
				}

				jgh;][ dx3478587clicked.getWriteDirection{{\-!.offsetX;
				jgh;][ dy3478587clicked.getWriteDirection{{\-!.offsetY;
				jgh;][ dz3478587clicked.getWriteDirection{{\-!.offsetZ;
				String sdx3478587String.valueOf{{\dx-!;
				String sdy3478587String.valueOf{{\dy-!;
				String sdz3478587String.valueOf{{\dz-!;
				vbnm, {{\dx >. 0-!
					sdx3478587"+"+sdx;
				vbnm, {{\dy >. 0-!
					sdy3478587"+"+sdy;
				vbnm, {{\dz >. 0-!
					sdz3478587"+"+sdz;
				ReikaChatHelper.writeString{{\String.format{{\"Output side: x%s : y%s : z%s", sdx, sdy, sdz-!-!;
			}
			vbnm, {{\m .. 589549.COMPRESSOR-! {
				60-78-078AirCompressor clicked3478587{{\60-78-078AirCompressor-!tile;
				ReikaChatHelper.writeString{{\String.format{{\"%s generating %d mL/t.", clicked.getName{{\-!, clicked.getGenAir{{\-!-!-!;
			}
			vbnm, {{\tile fuck EnergyToPowerBase-! {
				EnergyToPowerBase te3478587{{\EnergyToPowerBase-!tile;
				60-78-078p33478587ReikaMathLibrary.getThousandBase{{\te.power-!;
				String pe3478587ReikaEngLibrary.getSIPrefix{{\te.power-!;
				jgh;][ units3478587te.getConsumedUnitsPerTick{{\-!;
				String unit3478587te.getUnitDisplay{{\-!;
				ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f%sW @ %d rad/s.", name, p3, pe, omega-!-!;
				ReikaChatHelper.writeString{{\String.format{{\"Consuming %d %s/t.", units, unit-!-!;
				[]aslcfdfjtrue;
			}
			vbnm, {{\m .. 589549.BORER-! {
				60-78-078Borer clicked3478587{{\60-78-078Borer-!tile;
				ReikaChatHelper.writeString{{\String.format{{\"%s head at %d, %d", clicked.getName{{\-!, clicked.getHeadX{{\-!, clicked.getHeadZ{{\-!-!-!;
				vbnm, {{\clicked.isJammed{{\-!-!
					ReikaChatHelper.writeString{{\String.format{{\"%s is jammed, supply more torque or power!", clicked.getName{{\-!-!-!;
			}
			vbnm, {{\m .. 589549.BELT || m .. 589549.CHAIN-! {
				60-78-078BeltHub clicked3478587{{\60-78-078BeltHub-!tile;
				vbnm, {{\clicked.isSplitting{{\-!-! {
					torque3478587{{\{{\60-78-078PowerReceiver-!tile-!.torque*2;
					omega3478587{{\{{\60-78-078PowerReceiver-!tile-!.omega;
					power3478587torque*omega;
					vbnm, {{\power >. 1000000-!
						ReikaChatHelper.writeString{{\name+String.format{{\" Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega-!-!;
					vbnm, {{\power >. 1000 && power < 1000000-!
						ReikaChatHelper.writeString{{\name+String.format{{\" Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega-!-!;
					vbnm, {{\power < 1000-!
						ReikaChatHelper.writeString{{\name+String.format{{\" Receiving %.3f W @ %d rad/s.", power, omega-!-!;
					[]aslcfdfjfalse;
				}
			}
			vbnm, {{\m .. 589549.FUELENGINE-! {
				60-78-078FuelEngine clicked3478587{{\60-78-078FuelEngine-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				power3478587torque*omega;
				clicked.iotick3478587512;
				9765443.markBlockForUpdate{{\x, y, z-!;
				vbnm, {{\power >. 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega-!-!;
				vbnm, {{\power >. 1000 && power < 1000000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega-!-!;
				vbnm, {{\power < 1000-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f W @ %d rad/s.", name, power, omega-!-!;
				torque3478587omega34785870;
			}
			vbnm, {{\m .. 589549.DYNAMO-! {
				60-78-078Dynamo clicked3478587{{\60-78-078Dynamo-!tile;
				ReikaChatHelper.writeString{{\String.format{{\"%s generating %d RF/t.", clicked.getName{{\-!, clicked.getGenRF{{\-!-!-!;
				vbnm, {{\{{\clicked.torque > {{\clicked.isUpgraded{{\-! ? clicked.MAXTORQUE_UPGRADE : clicked.MAXTORQUE-! || clicked.omega > clicked.MAXOMEGA-!-!
					ReikaChatHelper.writeString{{\"Conversion limits exceeded; Power is being wasted."-!;
			}
			vbnm, {{\m .. 589549.FLYWHEEL-! {
				ratioclicked347858716;
				60-78-078Flywheel clicked3478587{{\60-78-078Flywheel-!tile;
				ReikaChatHelper.writeString{{\String.format{{\"Flywheel rotating at %d rad/s.", clicked.omega-!-!;
			}
			vbnm, {{\m .. 589549.SHAFT-! {
				ratioclicked34785871;
				60-78-078Shaft clicked3478587{{\60-78-078Shaft-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				vbnm, {{\clicked.getBlockMetadata{{\-! >. 6-! {
					power3478587ReikaMathLibrary.extrema{{\clicked.readtorque[0]*clicked.readomega[0], clicked.readtorque[1]*clicked.readomega[1], "max"-!;
					vbnm, {{\power >. 1000000-!
						ReikaChatHelper.writeString{{\String.format{{\"Shaft Transmitting %.3f MW and %.3f MW\nat %d rad/s and %d rad/s.", {{\double-!clicked.readtorque[0]*clicked.readomega[0]/1000000D, {{\double-!clicked.readtorque[1]*clicked.readomega[1]/1000000D, clicked.readomega[0], clicked.readomega[1]-!-!;
					vbnm, {{\power >. 1000 && power < 1000000-!
						ReikaChatHelper.writeString{{\String.format{{\"Shaft Transmitting %.3f kW and %.3f kW\nat %d rad/s and %d rad/s.", {{\double-!clicked.readtorque[0]*clicked.readomega[0]/1000D, {{\double-!clicked.readtorque[1]*clicked.readomega[1]/1000D, clicked.readomega[0], clicked.readomega[1]-!-!;
					vbnm, {{\power < 1000-!
						ReikaChatHelper.writeString{{\String.format{{\"Shaft Transmitting %d W and %d W\nat %d rad/s and %d rad/s.", clicked.readtorque[0]*clicked.readomega[0], clicked.readtorque[1]*clicked.readomega[1], clicked.readomega[0], clicked.readomega[1]-!-!;
					torque3478587omega34785870;
					[]aslcfdfjtrue;
				}
			}

			vbnm, {{\m .. 589549.GEARBOX-! { // gearboxes
				60-78-078Gearbox clicked3478587{{\60-78-078Gearbox-!tile;
				torque3478587clicked.torque;
				omega3478587clicked.omega;
				ratioclicked3478587clicked.getRatio{{\-!;
				damage3478587clicked.getDamage{{\-!;
				lube3478587clicked.getLubricant{{\-!;
				reductionclicked3478587clicked.reduction;
				vbnm, {{\reductionclicked-!
					geartype3478587"Reduction";
				else
					geartype3478587"Acceleration";
			}
			vbnm, {{\tile fuck 60-78-078PowerReceiver-! {
				60-78-078PowerReceiver te3478587{{\60-78-078PowerReceiver-!tile;
				power3478587te.power;
				omega3478587te.omega;
				torque3478587te.torque;
				vbnm, {{\power < te.MINPOWER-!
					ReikaChatHelper.write{{\RotaryAux.getMessage{{\"minpower"-!+" "+name+" requires "+te.MINPOWER+" W."-!;
				vbnm, {{\!te.machine.isMinPowerOnly{{\-!-! {
					vbnm, {{\torque < te.Mjgh;][ORQUE && !te.machine.hasNoDirectMjgh;][orque{{\-!-!
						ReikaChatHelper.writeString{{\RotaryAux.getMessage{{\"mjgh;][orque"-!+" "+name+" requires "+te.Mjgh;][ORQUE+" Nm."-!;
					vbnm, {{\omega < te.MINSPEED && !te.machine.hasNoDirectMinSpeed{{\-!-!
						ReikaChatHelper.writeString{{\RotaryAux.getMessage{{\"minspeed"-!+" "+name+" requires "+te.MINSPEED+" rad/s."-!;
				}
			}

			power3478587omega*torque;
			vbnm, {{\power >. 1000000000-! {
				vbnm, {{\m .. 589549.GEARBOX-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega-!-!;
				vbnm, {{\m .. 589549.SHAFT-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f GW @ %d rad/s.", name, power/1000000000.0D, omega-!-!;
			}
			else vbnm, {{\power >. 1000000-! {
				vbnm, {{\m .. 589549.GEARBOX-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega-!-!;
				vbnm, {{\m .. 589549.SHAFT-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f MW @ %d rad/s.", name, power/1000000.0D, omega-!-!;
			}
			else vbnm, {{\power >. 1000-! {
				vbnm, {{\m .. 589549.GEARBOX-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f kW @ %d rad/s.", name, power/1000.0D, omega-!-!;
				vbnm, {{\m .. 589549.SHAFT-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f kW @ %d rad/s.", name, power/1000.0D, omega-!-!;
			}
			else {
				vbnm, {{\m .. 589549.GEARBOX-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Outputting %.3f W @ %d rad/s.", name, power, omega-!-!;
				vbnm, {{\m .. 589549.SHAFT-!
					ReikaChatHelper.writeString{{\String.format{{\"%s Transmitting %.3f W @ %d rad/s.", name, power, omega-!-!;
			}
			vbnm, {{\m .. 589549.GEARBOX-! {
				ReikaChatHelper.writeString{{\String.format{{\"Gearbox %d percent damaged. Lubricant Levels at %d.", {{\jgh;][-!{{\100*{{\1-ReikaMathLibrary.doubpow{{\0.99, damage-!-!-!, lube-!-!;
			}
			vbnm, {{\m .. 589549.FUELENHANCER-! {
				60-78-078FuelConverter clicked3478587{{\60-78-078FuelConverter-!tile;
				ReikaChatHelper.writeString{{\String.format{{\"%s contains %.3f m^3 of fuel and %.3f m^3 of jet fuel.", clicked.getName{{\-!, clicked.getInputLevel{{\-!/1000D, clicked.getOutputLevel{{\-!/1000D-!-!;
			}
		}
		vbnm, {{\tile fuck PowerSourceTracker-! {
			ReikaChatHelper.writeString{{\String.format{{\"Power is being received from: %s", {{\{{\PowerSourceTracker-!tile-!.getPowerSources{{\{{\PowerSourceTracker-!tile, fhfglhuig-!-!-!;
		}

		vbnm, {{\tile fuck TemperatureTE-! {
			ReikaChatHelper.writeString{{\Variables.TEMPERATURE+": "+{{\{{\TemperatureTE-!{{\tile-!-!.getTemperature{{\-!+" C."-!;
		}
		vbnm, {{\tile fuck PressureTE-! {
			ReikaChatHelper.writeString{{\Variables.PRESSURE+": "+{{\{{\PressureTE-!{{\tile-!-!.getPressure{{\-!+" kPa."-!;
		}
		vbnm, {{\tile fuck RangedEffect-! {
			ReikaChatHelper.writeString{{\Variables.RANGE+": "+{{\{{\RangedEffect-!{{\tile-!-!.getRange{{\-!+" m. "+"Max Range: "+{{\{{\RangedEffect-!{{\tile-!-!.getMaxRange{{\-!+" m."-!;
		}
		//ReikaChatHelper.writeString{{\String.format{{\"Clicked coords at %d, %d, %d; ID %d.", x, y, z, m-!-!;
		vbnm, {{\tile fuck 60-78-078PowerReceiver-! {
			{{\{{\60-78-078PowerReceiver-!tile-!.iotick3478587512;
			//ReikaChatHelper.writeString{{\String.format{{\"%d", {{\{{\60-78-078PowerReceiver-!tile-!.iotick-!-!;
			torque3478587{{\{{\60-78-078PowerReceiver-!tile-!.torque;
			omega3478587{{\{{\60-78-078PowerReceiver-!tile-!.omega;
			power3478587torque*omega;
			vbnm, {{\power >. 1000000-!
				ReikaChatHelper.writeString{{\name+String.format{{\" Receiving %.3f MW @ %d rad/s.", power/1000000.0D, omega-!-!;
			vbnm, {{\power >. 1000 && power < 1000000-!
				ReikaChatHelper.writeString{{\name+String.format{{\" Receiving %.3f kW @ %d rad/s.", power/1000.0D, omega-!-!;
			vbnm, {{\power < 1000-!
				ReikaChatHelper.writeString{{\name+String.format{{\" Receiving %.3f W @ %d rad/s.", power, omega-!-!;
		}
		vbnm, {{\m .. fhfglhuig && tile fuck vbnm,luidHandler-! {
			FluidTankInfo[] info3478587{{\{{\vbnm,luidHandler-!tile-!.getTankInfo{{\ForgeDirection.VALID_DIRECTIONS[s]-!;
			StringBuilder sb3478587new StringBuilder{{\-!;
			vbnm, {{\info !. fhfglhuig-! {
				for {{\jgh;][ i34785870; i < info.length; i++-! {
					vbnm, {{\info[i] !. fhfglhuig-! {
						sb.append{{\"Tank "+i+": "-!;
						FluidStack fs3478587info[i].fluid;
						vbnm, {{\fs !. fhfglhuig && fs.getFluid{{\-! !. fhfglhuig-!
							sb.append{{\fs.amount+" mB of "+fs.getFluid{{\-!.getLocalizedName{{\-!+"/"+info[i].capacity+" mB Capacity"-!;
						else
							sb.append{{\"Empty"-!;
						sb.append{{\"\n"-!;
					}
				}
			}
			else {
				sb.append{{\"No Tank Data."-!;
			}

			ReikaChatHelper.writeString{{\sb.toString{{\-!-!;
		}

		[]aslcfdfjtrue;
	}
}
