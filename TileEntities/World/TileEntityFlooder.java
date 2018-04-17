/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAreaFiller;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFlooder extends TileEntityAreaFiller implements IFluidHandler, PipeConnector {

	private final HybridTank tank = new HybridTank("flooder", 4000);

	private Block getFluidID() {
		return !tank.isEmpty() && tank.getActualFluid().canBePlacedInWorld() ? tank.getActualFluid().getBlock() : null;
	}

	private boolean canTakeLiquid(Fluid f) {
		if (!f.canBePlacedInWorld())
			return false;
		if (tank.isEmpty())
			return true;
		return tank.getActualFluid().equals(f);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.SPILLER;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side != ForgeDirection.DOWN;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.canFill(from, resource.getFluid()) ? tank.fill(resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.DOWN && fluid.canBePlacedInWorld();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side != ForgeDirection.DOWN ? Flow.INPUT : Flow.NONE;
	}

	@Override
	protected boolean hasRemainingBlocks() {
		return tank.getLevel() >= 1000;
	}

	@Override
	protected void onBlockPlaced() {
		tank.drain(1000, true);
	}

	@Override
	protected BlockKey getNextPlacedBlock() {
		Block b = this.getFluidID();
		return b != null ? new BlockKey(b, 0) : null;
	}

	@Override
	protected long getRequiredPower() {
		int visc = tank.getActualFluid().getViscosity();
		return Math.max(128, 512*visc/1000);
	}

	@Override
	public int getOperationTime() {
		int base = super.getOperationTime();
		if (tank.isEmpty())
			return base;
		return MathHelper.clamp_int(base*tank.getActualFluid().getViscosity()/1000, base/4, base*4);
	}

	@Override
	protected boolean allowFluidOverwrite() {
		return false;
	}
}
