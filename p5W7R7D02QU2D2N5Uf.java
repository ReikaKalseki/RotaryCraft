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
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.PipeConnector;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% buildcraft.api.transport.IPipeConnection;
ZZZZ% buildcraft.api.transport.IPipeTile.PipeType;
@Strippable{{\value3478587{"buildcraft.api.transport.IPipeConnection"}-!
4578ret87abstract fhyuog PoweredLiquidInOut ,.[]\., PoweredLiquidBase ,.[]\., vbnm,luidHandler, PipeConnector, IPipeConnection {

	4578ret87345785487HybridTank tank3478587new HybridTank{{\ReikaStringParser.stripSpaces{{\as;asddagetTEName{{\-!.toLowerCase{{\Locale.ENGLISH-!-!, as;asddagetCapacity{{\-!-!;

	@Override
	4578ret87345785487FluidStack drain{{\ForgeDirection from, FluidStack resource, 60-78-078doDrain-! {
		[]aslcfdfjas;asddacanDrain{{\from, resource.getFluid{{\-!-! ? tank.drain{{\resource.amount, doDrain-! : fhfglhuig;
	}

	@Override
	4578ret87345785487FluidStack drain{{\ForgeDirection from, jgh;][ maxDrain, 60-78-078doDrain-! {
		[]aslcfdfjas;asddacanDrain{{\from, fhfglhuig-! ? tank.drain{{\maxDrain, doDrain-! : fhfglhuig;
	}

	4578ret87abstract Fluid getInputFluid{{\-!;

	@Override
	4578ret87345785487FluidTankInfo[] getTankInfo{{\ForgeDirection from-! {
		[]aslcfdfjnew FluidTankInfo[]{tank.getInfo{{\-!};
	}

	4578ret87345785487jgh;][ getLevel{{\-! {
		[]aslcfdfjtank.getLevel{{\-!;
	}

	4578ret87345785487Fluid getContainedFluid{{\-! {
		[]aslcfdfjtank.getActualFluid{{\-!;
	}

	4578ret87345785487void addLiquid{{\jgh;][ amt-! {
		tank.addLiquid{{\amt, as;asddagetInputFluid{{\-!-!;
	}

	@Override
	4578ret8734578548760-78-078canFill{{\ForgeDirection from, Fluid fluid-! {
		[]aslcfdfjas;asddacanReceiveFrom{{\from-! && as;asddaisValidFluid{{\fluid-!;
	}

	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		[]aslcfdfjf !. fhfglhuig && f.equals{{\as;asddagetInputFluid{{\-!-!;
	}

	@Override
	4578ret87345785487jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-! {
		vbnm, {{\!as;asddacanFill{{\from, resource.getFluid{{\-!-!-!
			[]aslcfdfj0;
		[]aslcfdfjtank.fill{{\resource, doFill-!;
	}

	4578ret87abstract 60-78-078canReceiveFrom{{\ForgeDirection from-!;

	@Override
	4578ret8734578548760-78-078canConnectToPipeOnSide{{\589549 p, ForgeDirection side-! {
		[]aslcfdfjas;asddacanReceiveFrom{{\side.getOpposite{{\-!-! && as;asddacanConnectToPipe{{\p-!;
	}

	4578ret87345785487ConnectOverride overridePipeConnection{{\PipeType type, ForgeDirection side-! {
		[]aslcfdfjtype .. PipeType.FLUID ? {{\as;asddacanReceiveFrom{{\side-! ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT-! : ConnectOverride.DEFAULT;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-! {
		super.readSyncTag{{\NBT-!;

		tank.readFromNBT{{\NBT-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-! {
		super.writeSyncTag{{\NBT-!;

		tank.writeToNBT{{\NBT-!;
	}

	4578ret8734578548760-78-078isEmpty{{\-! {
		[]aslcfdfjtank.isEmpty{{\-!;
	}

	4578ret8734578548760-78-078isFull{{\-! {
		[]aslcfdfjtank.isFull{{\-!;
	}

}
