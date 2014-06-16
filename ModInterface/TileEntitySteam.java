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

import java.awt.Color;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntitySteam extends EnergyToPowerBase implements PowerGenerator, SimpleProvider, IPipeConnection, IFluidHandler, PipeConnector {

	public static final int CAPACITY = 300000;

	//private HybridTank steam = new HybridTank("steamturb", CAPACITY);

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 4), 1.0);
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
		this.getIOSides(world, x, y, z, meta);
		write = this.getFacing().getOpposite();

		if (this.getTicksExisted() < 2)
			ReikaWorldHelper.causeAdjacentUpdates(world, x, y, z);
		/*
		if (steam.isEmpty()) {
			power = 0;
			torque = omega = 0;
		}
		else
			this.genPower();*/

		if (!this.hasEnoughEnergy()) {
			torque = 0;
			omega = 0;
			power = 0;
			//storedEnergy = 0;
		}
		else {
			omega = this.getSpeed();
			torque = this.getTorque();

			power = (long)torque*(long)omega;

			if (!world.isRemote) {
				storedEnergy -= this.getConsumedUnitsPerTick();
			}
		}
		this.basicPowerReceiver();
	}
	/*
	private void genPower() {
		omega = Math.min(GEN_OMEGA, this.getGenOmega());
		torque = Math.min(this.getGenTorque(), MAX_TORQUE);
		power = (long)omega*(long)torque;
		steam.removeLiquid(steam.getLevel()/200+1);
	}

	public long getGenPower() {
		return this.getGenOmega() * this.getGenTorque();
	}

	@Override
	public int getGenTorque() {
		return !steam.isEmpty() ? 8*steam.getLevel()/FluidContainerRegistry.BUCKET_VOLUME : 0;
	}

	public int getGenOmega() {
		return this.getGenTorque() > 0 ? (int)(this.getGenTorque()/(double)MAX_TORQUE*GEN_OMEGA) : 0;
	}
	 */
	private void getSteam(World world, int dx, int dy, int dz) {
		int drain = 25;
		if (storedEnergy <= this.getMaxStorage()-drain) {
			TileEntity te = world.getBlockTileEntity(dx, dy, dz);
			if (te instanceof IFluidHandler) {
				IFluidHandler ic = (IFluidHandler)te;
				FluidStack liq = ic.drain(this.getFacing().getOpposite(), drain, true);
				if (liq != null && liq.amount > 0 && liq.getFluid().equals(FluidRegistry.getFluid("water")))
					//steam.addLiquid(liq.amount, FluidRegistry.getFluid("steam"));
					this.addEnergy(liq.amount, true);
			}
		}
	}

	private int addEnergy(int amount, boolean doAdd) {
		int max = this.getMaxStorage()-storedEnergy;
		int add = Math.min(max, amount);
		if (doAdd)
			storedEnergy += add;
		return add;
	}

	@Override
	public long getMaxPower() {
		return power;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection dir) {
		return dir == this.getFacing().getOpposite() && type == PipeType.FLUID ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
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
		return this.canFill(from, resource.getFluid()) ? this.addEnergy(resource.amount, doFill) : 0;
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
		return new FluidTankInfo[0];
	}

	@Override
	public boolean isValidSupplier(TileEntity te) {
		return te instanceof IFluidHandler || te instanceof TileEntityPipe;
	}

	@Override
	public int getMaxStorage() {
		return CAPACITY;
	}

	@Override
	public int getConsumedUnitsPerTick() {
		return (int)Math.ceil(Math.sqrt(power));
	}

	@Override
	public String getUnitDisplay() {
		return "mB";
	}

	@Override
	public Color getPowerColor() {
		return new Color(255, 255, 255);
	}

}
