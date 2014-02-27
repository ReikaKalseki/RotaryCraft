/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;

/** To declare a machine output-only, return 0 for addFluid for all cases.
 * To declare a machine input-only, return 0 for removeFluid for all cases. */
public interface PipeConnector {

	public boolean canConnectToPipe(MachineRegistry m);

	/** Side is relative to the piping block (so DOWN means the block is below the pipe); p is the pipe type */
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side);

	/** This is for "can this TE accept fluid f on this side EVER", not for
	 * "if tank has space and can accept fluid f" */
	//public boolean canTakeInFluid(Fluid f, ForgeDirection side);

	/** This is called by fluid addition code. The pipes will attempt to add the specified
	 * amount of fluid 'f', from direction 'side'. Return how much was successfully added.
	 * Note that 'amt' may be much larger than the machine's capacity or free space.
	 * It is up to the machine to run all the calculations and fluid distribution.
	 * This is called every tick, so be efficient. */
	//public int addFluid(Fluid f, int amt, ForgeDirection side);

	/** This is called by fluid removal code. The pipes will attempt to drain from direction
	 * 'side'. Return the fluid that was successfully removed.
	 * Note that 'amt' may be much larger than the machine's liquid level.
	 * It is up to the machine to run all the calculations and fluid distribution.
	 * This is called every tick, so be efficient. */
	//public FluidStack removeFluid(int amt, ForgeDirection side);

	/** This is called by fluid addition. Return a FluidTankInfo which accurately encapsulates
	 * the amount your TE has of fluid 'f' and the maximum amount it can hold. *//*
	public FluidTankInfo getTank(Fluid f);*/

	/** This is called by liquid removal. Return info on all tanks which can emit their
	 * contents to direction 'side'. *//*
	public FluidTankInfo getTanks(ForgeDirection side);*/

	/** This will call if your TE can accept 'amt' mB of Fluid 'f'. *//*
	public void addLiquid(Fluid f, int amt);*/

	int fill(ForgeDirection from, FluidStack resource, boolean doFill);

	FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain);

	public Flow getFlowForSide(ForgeDirection side);

	//public TransferAmount getFluidRemoval();

	//public TransferAmount getFluidAddition();

	/** Get the minimum pressure allowable by this TE. Only meaningful on liquid pipes. */
	//public int getMinimumSupplyPressure();
}
