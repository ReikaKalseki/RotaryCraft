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

		this.updateSpeed();
		this.basicPowerReceiver();
	}

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
		return m == MachineRegistry.PIPE || super.canConnectToPipe(m);
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side == this.getFacing() || super.canConnectToPipeOnSide(p, side);
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return side == this.getFacing() ? Flow.INPUT : super.getFlowForSide(side);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (super.canFill(from, resource.getFluid()))
			return super.fill(from, resource, doFill);
		return this.canFill(from, resource.getFluid()) ? this.addEnergy(resource.amount, doFill) : 0;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return super.canFill(from, fluid) || from == this.getFacing() && fluid.equals(FluidRegistry.getFluid("steam"));
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
