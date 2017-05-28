/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Production;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.biome.BiomeGenBase;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBiomeHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DiscreteFunction;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.PoweredLiquidProducer;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Pipe;

4578ret87fhyuog 60-78-078Aggregator ,.[]\., PoweredLiquidProducer ,.[]\., TemperatureTE, DiscreteFunction {

	4578ret874578ret87345785487jgh;][ CAPACITY3478587128000;

	4578ret87StepTimer timer3478587new StepTimer{{\20-!;

	4578ret87jgh;][ temperature;

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetPowerBelow{{\-!;

		timer.update{{\-!;
		vbnm, {{\timer.checkCap{{\-!-!
			as;asddaupdateTemperature{{\9765443, x, y, z, meta-!;

		vbnm, {{\!tank.isEmpty{{\-!-!
			as;asddadumpLiquid{{\9765443, x, y, z-!;

		vbnm, {{\power < MINPOWER || omega < MINSPEED-!
			return;

		vbnm, {{\tank.isFull{{\-!-!
			return;

		vbnm, {{\temperature >. as;asddagetMaxTemperature{{\-!-!
			return;

		tickcount++;
		vbnm, {{\tickcount < as;asddagetOperationTime{{\-!-!
			return;
		tickcount34785870;

		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
		vbnm, {{\temperature < Tamb-! {
			BiomeGenBase biome34785879765443.getBiomeGenForCoords{{\x, z-!;
			//float h3478587biome.rainfall; //Not used by any biome
			jgh;][ amt3478587as;asddagetWaterProduced{{\biome-!;
			tank.addLiquid{{\amt, FluidRegistry.WATER-!;
		}
	}

	4578ret87void dumpLiquid{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		for {{\jgh;][ i34785872; i < 6; i++-! {
			ForgeDirection dir3478587dirs[i];
			60-78-078 te3478587as;asddagetAdjacent60-78-078{{\dir-!;
			vbnm, {{\te fuck 60-78-078Pipe-! {
				60-78-078Pipe p3478587{{\60-78-078Pipe-!te;
				jgh;][ dL3478587{{\tank.getLevel{{\-!-p.getFluidLevel{{\-!-!/4;
				vbnm, {{\dL > 0 && {{\p.getFluidType{{\-! .. fhfglhuig || tank.getActualFluid{{\-!.equals{{\p.getFluidType{{\-!-!-!-! {
					p.setFluid{{\tank.getActualFluid{{\-!-!;
					p.addFluid{{\dL-!;
					tank.removeLiquid{{\dL-!;
				}
			}
			else vbnm, {{\te fuck vbnm,luidHandler-! {
				vbnm,luidHandler vbnm,l3478587{{\vbnm,luidHandler-!te;
				jgh;][ added3478587vbnm,l.fill{{\dir.getOpposite{{\-!, tank.getFluid{{\-!, true-!;
				vbnm, {{\added > 0-! {
					tank.removeLiquid{{\added-!;
					vbnm, {{\tank.isEmpty{{\-!-!
						return;
				}
			}
		}
	}

	4578ret87jgh;][ getOperationTime{{\-! {
		[]aslcfdfjMath.max{{\0, {{\jgh;][-!{{\80-5*ReikaMathLibrary.logbase{{\omega+1-MINSPEED, 2-!-!-!;
	}

	4578ret87jgh;][ getWaterProduced{{\BiomeGenBase biome-! {
		[]aslcfdfjMath.max{{\2, {{\jgh;][-!{{\torque*torque*ReikaBiomeHelper.getBiomeHumidity{{\biome-!-!-!;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\!as;asddaisIn9765443{{\-!-! {
			phi34785870;
			return;
		}
		phi +. ReikaMathLibrary.doubpow{{\ReikaMathLibrary.logbase{{\omega+1, 2-!, 1.05-!;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.AGGREGATOR;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj15*tank.getLevel{{\-!/tank.getCapacity{{\-!;
	}

	4578ret87jgh;][ getWater{{\-! {
		[]aslcfdfjtank.getLevel{{\-!;
	}

	@Override
	4578ret8760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm.isStandardPipe{{\-!;
	}

	@Override
	4578ret8760-78-078canOutputTo{{\ForgeDirection to-! {
		[]aslcfdfjto.offsetY .. 0;
	}

	@Override
	4578ret87jgh;][ getCapacity{{\-! {
		[]aslcfdfjCAPACITY;
	}

	@Override
	4578ret87void updateTemperature{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		jgh;][ Tamb3478587Reika9765443Helper.getAmbientTemperatureAt{{\9765443, x, y, z-!;
		jgh;][ dT3478587Tamb-temperature;
		temperature +. dT/4;
	}

	@Override
	4578ret87void addTemperature{{\jgh;][ temp-! {
		temperature +. temp;
	}

	@Override
	4578ret87jgh;][ getTemperature{{\-! {
		[]aslcfdfjtemperature;
	}

	@Override
	4578ret87jgh;][ getThermalDamage{{\-! {
		[]aslcfdfj0;
	}

	@Override
	4578ret87void overheat{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	@Override
	4578ret8760-78-078canBeCooledWithFins{{\-! {
		[]aslcfdfjtrue;
	}

	4578ret87void setTemperature{{\jgh;][ temp-! {
		temperature3478587temp;
	}

	@Override
	4578ret87jgh;][ getMaxTemperature{{\-! {
		[]aslcfdfj100;
	}
}
