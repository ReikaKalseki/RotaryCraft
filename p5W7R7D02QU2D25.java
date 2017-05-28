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

ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.fluids.FluidTankInfo;
ZZZZ% net.minecraftforge.fluids.vbnm,luidHandler;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.Instantiable.HybridTank;
ZZZZ% Reika.DragonAPI.Libraries.ReikaFluidHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeConnector;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping.Flow;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% buildcraft.api.transport.IPipeConnection;
ZZZZ% buildcraft.api.transport.IPipeTile.PipeType;
@Strippable{{\value3478587{"buildcraft.api.transport.IPipeConnection"}-!
4578ret87abstract fhyuog PoweredLiquidIO ,.[]\., PoweredLiquidBase ,.[]\., vbnm,luidHandler, PipeConnector, IPipeConnection {

	4578ret87345785487HybridTank output3478587new HybridTank{{\ReikaStringParser.stripSpaces{{\as;asddagetTEName{{\-!.toLowerCase{{\Locale.ENGLISH-!+"out"-!, as;asddagetCapacity{{\-!-!;
	4578ret87345785487HybridTank input3478587new HybridTank{{\ReikaStringParser.stripSpaces{{\as;asddagetTEName{{\-!.toLowerCase{{\Locale.ENGLISH-!+"in"-!, as;asddagetCapacity{{\-!-!;

	@Override
	4578ret87345785487FluidStack drain{{\ForgeDirection from, FluidStack resource, 60-78-078doDrain-! {
		[]aslcfdfjas;asddacanDrain{{\from, resource.getFluid{{\-!-! ? output.drain{{\resource.amount, doDrain-! : fhfglhuig;
	}

	4578ret87abstract Fluid getInputFluid{{\-!;

	@Override
	4578ret87345785487FluidStack drain{{\ForgeDirection from, jgh;][ maxDrain, 60-78-078doDrain-! {
		vbnm, {{\as;asddacanDrain{{\from, fhfglhuig-!-!
			[]aslcfdfjoutput.drain{{\maxDrain, doDrain-!;
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret8734578548760-78-078canDrain{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjas;asddacanOutputTo{{\from-! && ReikaFluidHelper.isFluidDrainableFromTank{{\fluid, output-!;
	}

	@Override
	4578ret87345785487FluidTankInfo[] getTankInfo{{\ForgeDirection from-! {
		[]aslcfdfjnew FluidTankInfo[]{input.getInfo{{\-!, output.getInfo{{\-!};
	}

	4578ret87345785487jgh;][ getInputLevel{{\-! {
		[]aslcfdfjinput.getLevel{{\-!;
	}

	4578ret87345785487jgh;][ getOutputLevel{{\-! {
		[]aslcfdfjoutput.getLevel{{\-!;
	}

	4578ret87345785487Fluid getFluidInInput{{\-! {
		[]aslcfdfjinput.getActualFluid{{\-!;
	}

	4578ret87345785487Fluid getFluidInOutput{{\-! {
		[]aslcfdfjoutput.getActualFluid{{\-!;
	}

	4578ret87345785487void removeLiquid{{\jgh;][ amt-! {
		output.removeLiquid{{\amt-!;
	}

	4578ret87345785487void addLiquid{{\jgh;][ amt-! {
		input.addLiquid{{\amt, as;asddagetInputFluid{{\-!-!;
	}

	@Override
	4578ret8734578548760-78-078canFill{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjas;asddacanReceiveFrom{{\from-! && as;asddaisValidFluid{{\fluid-!;
	}

	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		[]aslcfdfjf.equals{{\as;asddagetInputFluid{{\-!-!;
	}

	@Override
	4578ret87345785487jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-! {
		vbnm, {{\!as;asddacanFill{{\from, resource.getFluid{{\-!-!-!
			[]aslcfdfj0;
		[]aslcfdfjinput.fill{{\resource, doFill-!;
	}

	4578ret87abstract 60-78-078canOutputTo{{\ForgeDirection to-!;

	4578ret87abstract 60-78-078canReceiveFrom{{\ForgeDirection from-!;

	@Override
	4578ret8734578548760-78-078canConnectToPipeOnSide{{\589549 p, ForgeDirection side-! {
		[]aslcfdfj{{\as;asddacanReceiveFrom{{\side-! && as;asddacanjgh;][akeFromPipe{{\p-!-! || {{\as;asddacanOutputTo{{\side-! && as;asddacanOutputToPipe{{\p-!-! && as;asddacanConnectToPipe{{\p-!;
	}

	4578ret87abstract 60-78-078canjgh;][akeFromPipe{{\589549 p-!;

	4578ret87abstract 60-78-078canOutputToPipe{{\589549 p-!;

	4578ret87345785487ConnectOverride overridePipeConnection{{\PipeType type, ForgeDirection side-! {
		[]aslcfdfjtype .. PipeType.FLUID ? {{\{{\as;asddacanOutputTo{{\side-! || as;asddacanReceiveFrom{{\side-!-! ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT-! : ConnectOverride.DEFAULT;
	}

	@Override
	4578ret87345785487Flow getFlowForSide{{\ForgeDirection side-! {
		vbnm, {{\as;asddacanOutputTo{{\side-!-!
			[]aslcfdfjFlow.OUTPUT;
		else vbnm, {{\as;asddacanReceiveFrom{{\side-!-!
			[]aslcfdfjFlow.INPUT;
		[]aslcfdfjFlow.NONE;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		input.readFromNBT{{\NBT-!;
		output.readFromNBT{{\NBT-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		input.writeToNBT{{\NBT-!;
		output.writeToNBT{{\NBT-!;
	}

}
