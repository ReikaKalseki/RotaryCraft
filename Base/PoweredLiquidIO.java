/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Registry.MachineRegistry;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public abstract class PoweredLiquidIO extends PoweredLiquidBase implements IFluidHandler, PipeConnector, IPipeConnection {

	protected HybridTank output = new HybridTank(ReikaStringParser.stripSpaces(this.getTEName().toLowerCase()), this.getCapacity());
	protected HybridTank input = new HybridTank(ReikaStringParser.stripSpaces(this.getTEName().toLowerCase()), this.getCapacity());

	@Override
	public final FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		int maxDrain = resource.amount;
		if (this.canDrain(from, null))
			return output.drain(maxDrain, doDrain);
		return null;
	}

	public abstract Fluid getInputFluid();

	@Override
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (this.canDrain(from, null))
			return output.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public final boolean canDrain(ForgeDirection from, Fluid fluid) {
		return this.canOutputTo(from);
	}

	@Override
	public final FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{input.getInfo(), output.getInfo()};
	}

	public final int getInputLevel() {
		return input.getLevel();
	}

	public final int getOutputLevel() {
		return output.getLevel();
	}

	public final Fluid getContainedFluid() {
		return output.getActualFluid();
	}

	public final void removeLiquid(int amt) {
		output.removeLiquid(amt);
	}

	public final void addLiquid(int amt) {
		input.addLiquid(amt, this.getInputFluid());
	}

	@Override
	public final boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.canReceiveFrom(from) && fluid.equals(this.getInputFluid());
	}

	@Override
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!this.canFill(from, resource.getFluid()))
			return 0;
		return input.fill(resource, doFill);
	}

	public abstract boolean canOutputTo(ForgeDirection to);

	public abstract boolean canReceiveFrom(ForgeDirection from);

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canOutputTo(side) && this.canConnectToPipe(p);
	}

	public final ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		return type == PipeType.FLUID ? (this.canOutputTo(side) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT) : ConnectOverride.DEFAULT;
	}

	@Override
	public final Flow getFlowForSide(ForgeDirection side) {
		if (this.canOutputTo(side))
			return Flow.OUTPUT;
		else if (this.canReceiveFrom(side))
			return Flow.INPUT;
		return Flow.NONE;
	}

}
