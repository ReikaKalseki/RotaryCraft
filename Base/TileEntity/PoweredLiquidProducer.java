/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public abstract class PoweredLiquidProducer extends PoweredLiquidBase implements IFluidHandler, PipeConnector, IPipeConnection {

	protected HybridTank tank = new HybridTank(ReikaStringParser.stripSpaces(this.getTEName().toLowerCase()), this.getCapacity());

	@Override
	public final FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		int maxDrain = resource.amount;
		if (this.canDrain(from, null))
			return tank.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (this.canDrain(from, null))
			return tank.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public final boolean canDrain(ForgeDirection from, Fluid fluid) {
		return this.canOutputTo(from);
	}

	@Override
	public final FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public final int getLevel() {
		return tank.getLevel();
	}

	public Fluid getContainedFluid() {
		return tank.getActualFluid();
	}

	public final void removeLiquid(int amt) {
		tank.removeLiquid(amt);
	}

	@Override
	public final boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	public abstract boolean canOutputTo(ForgeDirection to);

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canOutputTo(side) && this.canConnectToPipe(p);
	}

	public final ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		return type == PipeType.FLUID ? (this.canOutputTo(side) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT) : ConnectOverride.DEFAULT;
	}

	@Override
	public final Flow getFlowForSide(ForgeDirection side) {
		return this.canOutputTo(side) ? Flow.OUTPUT : Flow.NONE;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

}
