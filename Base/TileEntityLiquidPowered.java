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

public abstract class TileEntityLiquidPowered extends TileEntityPowerReceiver implements IFluidHandler, PipeConnector {

	protected HybridTank tank = new HybridTank(ReikaStringParser.stripSpaces(this.getTEName().toLowerCase()), this.getCapacity());

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	public abstract Fluid getInputFluid();

	public abstract int getCapacity();

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public int getLevel() {
		return tank.getLevel();
	}

	public Fluid getContainedFluid() {
		return tank.getActualFluid();
	}

	public void addLiquid(int amt) {
		tank.addLiquid(amt, this.getInputFluid());
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.canReceiveFrom(from) && fluid.equals(this.getInputFluid());
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!this.canFill(from, resource.getFluid()))
			return 0;
		return tank.fill(resource, doFill);
	}

	public abstract boolean canReceiveFrom(ForgeDirection from);

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canReceiveFrom(side) && this.canConnectToPipe(p);
	}

}
