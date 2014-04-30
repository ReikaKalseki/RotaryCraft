/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntitySteam extends TileEntityIOMachine implements PowerGenerator, SimpleProvider, IPipeConnection, IFluidHandler, PipeConnector {

	public static final int CAPACITY = 300000;
	public static final int GEN_OMEGA = 2048;
	public static final int MAX_TORQUE = 256;

	private HybridTank steam = new HybridTank("steamturb", CAPACITY);

	private ForgeDirection facingDir;

	@Override
	public boolean canProvidePower() {
		return true;
	}

	public ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.UNKNOWN;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.STEAMTURBINE;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 1:
			facingDir = ForgeDirection.EAST;
			break;
		case 2:
			facingDir = ForgeDirection.NORTH;
			break;
		case 3:
			facingDir = ForgeDirection.WEST;
			break;
		}
		write = this.getFacing();

		if (this.getTicksExisted() < 2)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);

		if (steam.isEmpty()) {
			power = 0;
			torque = omega = 0;
		}
		else
			this.genPower();
	}

	private void genPower() {
		omega = Math.min(GEN_OMEGA, this.getGenOmega());
		torque = Math.min(this.getGenTorque(), MAX_TORQUE);
		power = (long)omega*(long)torque;
		steam.removeLiquid(steam.getLevel()/200+1);
	}

	public long getGenPower() {
		return this.getGenOmega() * this.getGenTorque();
	}

	public int getGenTorque() {
		return !steam.isEmpty() ? 8*steam.getLevel()/FluidContainerRegistry.BUCKET_VOLUME : 0;
	}

	public int getGenOmega() {
		return this.getGenTorque() > 0 ? (int)(this.getGenTorque()/(double)MAX_TORQUE*GEN_OMEGA) : 0;
	}

	private void getSteam(World world, int dx, int dy, int dz) {
		if (steam.getLevel() <= CAPACITY-FluidContainerRegistry.BUCKET_VOLUME) {
			TileEntity te = world.getBlockTileEntity(dx, dy, dz);
			if (te instanceof IFluidHandler) {
				IFluidHandler ic = (IFluidHandler)te;
				FluidStack liq = ic.drain(facingDir.getOpposite(), FluidContainerRegistry.BUCKET_VOLUME, true);
				if (liq != null && liq.amount > 0 && liq.getFluid().equals(FluidRegistry.getFluid("water")))
					steam.addLiquid(liq.amount, FluidRegistry.getFluid("steam"));
			}
		}
	}

	public int getSteam() {
		return steam.getLevel();
	}

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		steam.writeToNBT(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		steam.readFromNBT(NBT);
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection dir) {
		if (facingDir == null)
			return ConnectOverride.DEFAULT;
		return dir == facingDir.getOpposite() && type == PipeType.FLUID ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}

	@Override
	public int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side == this.getFacing().getOpposite();
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == this.getFacing().getOpposite() ? Flow.INPUT : Flow.NONE;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.canFill(from, resource.getFluid()) ? steam.fill(resource, doFill) : 0;
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
		return from == this.getFacing().getOpposite() && fluid.equals(FluidRegistry.getFluid("steam"));
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{steam.getInfo()};
	}

}
