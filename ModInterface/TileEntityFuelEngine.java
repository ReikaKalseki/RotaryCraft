/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;

public class TileEntityFuelEngine extends TileEntityIOMachine implements IFluidHandler, SimpleProvider, PowerGenerator {

	public static final int GEN_OMEGA = 1024;
	public static final int GEN_TORQUE = 512;

	private HybridTank tank = new HybridTank("fuelengine", 24000);
	private HybridTank watertank = new HybridTank("waterfuelengine", 24000);

	private StepTimer fuelTimer = new StepTimer(240);

	private boolean canEmitPower(World world, int x, int y, int z) {
		if (tank.isEmpty())
			return false;
		MachineRegistry m = MachineRegistry.getMachine(world, x, y-1, z);
		if (m == MachineRegistry.ECU) {
			TileEntityEngineController te = (TileEntityEngineController)world.getBlockTileEntity(x, y-1, z);
			return te.getFuelMultiplier() > 0;
		}
		return true;
	}

	private int getFuelDuration(World world, int x, int y, int z) {
		MachineRegistry m = MachineRegistry.getMachine(world, x, y-1, z);
		if (m == MachineRegistry.ECU) {
			TileEntityEngineController te = (TileEntityEngineController)world.getBlockTileEntity(x, y-1, z);
			return 240*te.getFuelMultiplier();
		}
		return 240;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		fuelTimer.setCap(this.getFuelDuration(world, x, y, z));
		if (this.canEmitPower(world, x, y, z)) {
			fuelTimer.update();
			if (fuelTimer.checkCap()) {
				tank.removeLiquid(1);
			}
		}
	}

	@Override
	public boolean canProvidePower() {
		return !tank.isEmpty();
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FUELENGINE.ordinal();
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
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.canFill(from, resource.getFluid()))
			return tank.fill(resource, doFill);
		return 0;
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
		return from == ForgeDirection.DOWN && fluid.equals(FluidRegistry.getFluid("fuel"));
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
	public long getMaxPower() {
		return power;
	}

	@Override
	public long getCurrentPower() {
		return power;
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

}
