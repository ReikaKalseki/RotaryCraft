/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.Registry.MachineRegistry;

/* To declare a machine output-only, return false for canTakeInFluid for all cases.
 * To declare a machine input-only, return null for getTanks for all cases. */
public interface PipeConnector {

	public boolean canConnectToPipe(MachineRegistry m);

	/** Side is relative to the piping block (so DOWN means the block is below the pipe); p is the pipe type */
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side);

	/** This is for "can this TE accept fluid f on this side EVER", not for
	 * "if tank has space and can accept fluid f" *//*
	public boolean canTakeInFluid(Fluid f, ForgeDirection side);*/

	/** This is called by fluid addition. Return a FluidTankInfo which accurately encapsulates
	 * the amount your TE has of fluid 'f' and the maximum amount it can hold. *//*
	public FluidTankInfo getTank(Fluid f);*/

	/** This is called by liquid removal. Return info on all tanks which can emit their
	 * contents to direction 'side'. *//*
	public FluidTankInfo getTanks(ForgeDirection side);*/

	/** This will call if your TE can accept 'amt' mB of Fluid 'f'. *//*
	public void addLiquid(Fluid f, int amt);*/

}
