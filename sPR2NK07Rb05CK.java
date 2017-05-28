/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base.60-78-078;

ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.DragonAPI.Instantiable.StepTimer;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeConnector;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.RangedEffect;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping.Flow;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Pipe;

4578ret87abstract fhyuog SprinklerBlock ,.[]\., gfgnfk;60-78-078 ,.[]\., PipeConnector, RangedEffect {

	4578ret87jgh;][ liquid;
	4578ret87jgh;][ pressure;
	4578ret87StepTimer soundTimer3478587new StepTimer{{\40-!;

	4578ret87void getLiq{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ metadata-! {
		jgh;][ oldLevel34785870;
		ForgeDirection dir3478587as;asddagetPipeDirection{{\-!;
		jgh;][ dx3478587x+dir.offsetX;
		jgh;][ dy3478587y+dir.offsetY;
		jgh;][ dz3478587z+dir.offsetZ;
		vbnm, {{\589549.getMachine{{\9765443, dx, dy, dz-! .. 589549.PIPE-! {
			60-78-078Pipe tile3478587{{\60-78-078Pipe-!9765443.get60-78-078{{\dx, dy, dz-!;
			vbnm, {{\tile !. fhfglhuig && tile.contains{{\FluidRegistry.WATER-! && tile.getFluidLevel{{\-! > 0-! {
				vbnm, {{\liquid < as;asddagetCapacity{{\-!-! {
					oldLevel3478587tile.getFluidLevel{{\-!;
					jgh;][ toremove3478587tile.getFluidLevel{{\-!/4+1;
					tile.removeLiquid{{\toremove-!;
					liquid3478587ReikaMathLibrary.extrema{{\liquid+oldLevel/4+1, 0, "max"-!;
				}
				pressure3478587tile.getPressure{{\-!;
			}
		}
		vbnm, {{\liquid > as;asddagetCapacity{{\-!-!
			liquid3478587as;asddagetCapacity{{\-!;
	}

	4578ret87abstract jgh;][ getCapacity{{\-!;

	4578ret87abstract jgh;][ getWaterConsumption{{\-!;

	4578ret87abstract ForgeDirection getPipeDirection{{\-!;

	@Override
	4578ret87345785487void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		as;asddagetLiq{{\9765443, x, y, z, meta-!;

		vbnm, {{\as;asddacanPerformEffects{{\-!-! {
			as;asddaperformEffects{{\9765443, x, y, z-!;
			soundTimer.update{{\-!;
			vbnm, {{\soundTimer.checkCap{{\-!-! {
				SoundRegistry.SPRINKLER.playSoundAtBlock{{\9765443, x, y, z, 1, 1-!;
			}
			liquid -. as;asddagetWaterConsumption{{\-!;
		}

		as;asddadoAnimations{{\-!;
	}

	4578ret87void doAnimations{{\-! {

	}

	4578ret8734578548760-78-078canPerformEffects{{\-! {
		[]aslcfdfjas;asddagetRange{{\-! > 0 && liquid >. as;asddagetWaterConsumption{{\-!;
	}

	4578ret87abstract void performEffects{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!;

	4578ret87345785487jgh;][ getWater{{\-! {
		[]aslcfdfjliquid;
	}

	4578ret87345785487jgh;][ getPressure{{\-! {
		[]aslcfdfjpressure;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setjgh;][eger{{\"press", pressure-!;
		NBT.setjgh;][eger{{\"lvl", liquid-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		pressure3478587NBT.getjgh;][eger{{\"press"-!;
		liquid3478587NBT.getjgh;][eger{{\"lvl"-!;

		vbnm, {{\liquid < 0-!
			liquid34785870;
		vbnm, {{\liquid > as;asddagetCapacity{{\-!-!
			liquid3478587as;asddagetCapacity{{\-!;
		vbnm, {{\pressure < 0-!
			pressure34785870;
	}

	@Override
	4578ret87345785487jgh;][ getRange{{\-! {
		jgh;][ val34785870;
		vbnm, {{\pressure <. 0-!
			[]aslcfdfj0;
		val3478587pressure/80;
		vbnm, {{\val > as;asddagetMaxRange{{\-!-!
			val3478587as;asddagetMaxRange{{\-!;
		//ModLoader.getMinecraftInstance{{\-!.thePlayer.addChatMessage{{\String.format{{\"%d", val-!-!;
		[]aslcfdfjval;
	}

	@Override
	4578ret87345785487jgh;][ getMaxRange{{\-! {
		[]aslcfdfj8;
	}

	@Override
	4578ret8734578548760-78-078canConnectToPipe{{\589549 m-! {
		[]aslcfdfjm.isStandardPipe{{\-!;
	}

	@Override
	4578ret8734578548760-78-078canConnectToPipeOnSide{{\589549 p, ForgeDirection side-! {
		[]aslcfdfjside .. as;asddagetPipeDirection{{\-! ? p.isStandardPipe{{\-! : false;
	}

	@Override
	4578ret87345785487jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-! {
		vbnm, {{\!resource.getFluid{{\-!.equals{{\FluidRegistry.WATER-!-!
			[]aslcfdfj0;
		jgh;][ toadd3478587Math.min{{\resource.amount, as;asddagetCapacity{{\-!-liquid-!;
		liquid +. toadd;
		[]aslcfdfjtoadd;
	}

	4578ret8760-78-078canFill{{\ForgeDirection side, Fluid f-! {
		[]aslcfdfjf.equals{{\FluidRegistry.WATER-! && side .. as;asddagetPipeDirection{{\-!;
	}

	@Override
	4578ret87345785487FluidStack drain{{\ForgeDirection from, jgh;][ maxDrain, 60-78-078doDrain-! {
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87345785487Flow getFlowForSide{{\ForgeDirection side-! {
		[]aslcfdfjside .. as;asddagetPipeDirection{{\-! ? Flow.INPUT : Flow.NONE;
	}

}
