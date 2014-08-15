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

import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Registry.MachineRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public abstract class PoweredLiquidInOut extends PoweredLiquidBase implements IFluidHandler, PipeConnector, IPipeConnection {

	protected HybridTank tank = new HybridTank(ReikaStringParser.stripSpaces(this.getTEName().toLowerCase()), this.getCapacity());

	@Override
	public final FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.canDrain(from, resource.getFluid()) ? tank.drain(resource.amount, doDrain) : null;
	}

	@Override
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.canDrain(from, null) ? tank.drain(maxDrain, doDrain) : null;
	}

	public abstract Fluid getInputFluid();

	@Override
	public final FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public final int getLevel() {
		return tank.getLevel();
	}

	public final Fluid getContainedFluid() {
		return tank.getActualFluid();
	}

	public final void addLiquid(int amt) {
		tank.addLiquid(amt, this.getInputFluid());
	}

	@Override
	public final boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.canReceiveFrom(from) && this.isValidFluid(fluid);
	}

	public boolean isValidFluid(Fluid f) {
		return f != null && f.equals(this.getInputFluid());
	}

	@Override
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!this.canFill(from, resource.getFluid()))
			return 0;
		return tank.fill(resource, doFill);
	}

	public abstract boolean canReceiveFrom(ForgeDirection from);

	@Override
	public final boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canReceiveFrom(side.getOpposite()) && this.canConnectToPipe(p);
	}

	public final ConnectOverride overridePipeConnection(PipeType type, ForgeDirection side) {
		return type == PipeType.FLUID ? (this.canReceiveFrom(side) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT) : ConnectOverride.DEFAULT;
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

	public final boolean isEmpty() {
		return tank.isEmpty();
	}

	public final boolean isFull() {
		return tank.isFull();
	}

}