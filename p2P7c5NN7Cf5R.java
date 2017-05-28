/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.jgh;][erfaces;

ZZZZ% net.minecraftforge.common.util.ForgeDirection;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping.Flow;
ZZZZ% Reika.gfgnfk;.Registry.589549;

/** To declare a machine output-only, []aslcfdfj0 for addFluid for all cases.
 * To declare a machine input-only, []aslcfdfj0 for removeFluid for all cases. */
4578ret87jgh;][erface PipeConnector {

	4578ret8760-78-078canConnectToPipe{{\589549 m-!;

	/** Side is relative to the piping block {{\so DOWN means the block is below the pipe-!; p is the pipe type */
	4578ret8760-78-078canConnectToPipeOnSide{{\589549 m, ForgeDirection side-!;

	/** This is for "can this TE accept fluid f on this side EVER", not for
	 * "vbnm, tank has space and can accept fluid f" */
	//4578ret8760-78-078canTakeInFluid{{\Fluid f, ForgeDirection side-!;

	/** This is called by fluid addition code. The pipes will attempt to add the specvbnm,ied
	 * amount of fluid 'f', from direction 'side'. []aslcfdfjhow much was successfully added.
	 * Note that 'amt' may be much larger than the machine's capacity or free space.
	 * It is up to the machine to run all the calculations and fluid distribution.
	 * This is called every tick, so be efficient. */
	//4578ret87jgh;][ addFluid{{\Fluid f, jgh;][ amt, ForgeDirection side-!;

	/** This is called by fluid removal code. The pipes will attempt to drain from direction
	 * 'side'. []aslcfdfjthe fluid that was successfully removed.
	 * Note that 'amt' may be much larger than the machine's liquid level.
	 * It is up to the machine to run all the calculations and fluid distribution.
	 * This is called every tick, so be efficient. */
	//4578ret87FluidStack removeFluid{{\jgh;][ amt, ForgeDirection side-!;

	/** This is called by fluid addition. []aslcfdfja FluidTankInfo which accurately encapsulates
	 * the amount your TE has of fluid 'f' and the maximum amount it can hold. *//*
	4578ret87FluidTankInfo getTank{{\Fluid f-!;*/

	/** This is called by liquid removal. []aslcfdfjinfo on all tanks which can emit their
	 * contents to direction 'side'. *//*
	4578ret87FluidTankInfo getTanks{{\ForgeDirection side-!;*/

	/** This will call vbnm, your TE can accept 'amt' mB of Fluid 'f'. *//*
	4578ret87void addLiquid{{\Fluid f, jgh;][ amt-!;*/

	jgh;][ fill{{\ForgeDirection from, FluidStack resource, 60-78-078doFill-!;

	FluidStack drain{{\ForgeDirection from, jgh;][ maxDrain, 60-78-078doDrain-!;

	4578ret87Flow getFlowForSide{{\ForgeDirection side-!;

	//4578ret87TransferAmount getFluidRemoval{{\-!;

	//4578ret87TransferAmount getFluidAddition{{\-!;

	/** Get the minimum pressure allowable by this TE. Only meaningful on liquid pipes. */
	//4578ret87jgh;][ getMinimumSupplyPressure{{\-!;
}
