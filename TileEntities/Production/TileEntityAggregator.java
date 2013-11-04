/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;

public class TileEntityAggregator extends TileEntityPowerReceiver implements IFluidHandler, PipeConnector {

	public static final int CAPACITY = 6000;

	private HybridTank tank = new HybridTank("aggregator", CAPACITY);
	private StepTimer timer = new StepTimer(2);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		if (power >= MINPOWER && omega >= MINSPEED) {
			if (!tank.isFull()) {
				BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
				float h = biome.rainfall; //Not used by any biome
				tank.addLiquid(25, FluidRegistry.WATER);
			}
		}
		if (!tank.isEmpty())
			this.dumpLiquid(world, x, y, z);
	}

	private void dumpLiquid(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			if (!tank.isEmpty()) {
				ForgeDirection dir = dirs[i];
				int dx = x+dir.offsetX;
				int dz = z+dir.offsetZ;
				int id = world.getBlockId(dx, y, dz);
				MachineRegistry m = MachineRegistry.getMachine(world, dx, y, dz);
				TileEntity te = world.getBlockTileEntity(dx, y, dz);
				if (m == MachineRegistry.PIPE) {
					TileEntityPipe tp = (TileEntityPipe)te;
					if (tp.canTakeInFluid(FluidRegistry.WATER));
					int amt = tank.getLevel()/4+1;
					tp.liquidLevel += amt;
					tp.setFluid(FluidRegistry.WATER);
				}
				if (id != 0) {
					if (te instanceof IFluidHandler) {
						IFluidHandler fl = (IFluidHandler)te;
						if (fl.canFill(dir.getOpposite(), FluidRegistry.WATER)) {
							FluidStack fs = fl.drain(dir, Integer.MAX_VALUE, false);
							int max = tank.getLevel()/4+1;
							int amt = fl.fill(ForgeDirection.DOWN, new FluidStack(tank.getActualFluid(), max), true);
							tank.removeLiquid(amt);
						}
					}
				}
			}
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (this.canDrain(from, null))
			return tank.drain(resource.amount, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (this.canDrain(from, null))
			return tank.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from.offsetY == 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.AGGREGATOR.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 15*tank.getLevel()/tank.getCapacity();
	}

	public int getWater() {
		return tank.getLevel();
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		tank.writeToNBT(NBT);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		tank.readFromNBT(NBT);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return this.canConnectToPipe(p) && side.offsetY == 0;
	}

	@Override
	public boolean canTakeInFluid(Fluid f, ForgeDirection side) {
		return false;
	}

	@Override
	public FluidTankInfo getTank(ForgeDirection side) {
		return null;
	}

	@Override
	public void addLiquid(Fluid f, int amt) {}

}
