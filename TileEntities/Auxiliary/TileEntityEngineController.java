/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.Auxiliary.Interfaces.PipeConnector;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping.Flow;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.EngineType.EngineClass;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityEngineController extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler {

	public static final int FUELCAP = 3000;

	private final HybridTank tank = new HybridTank("ecu", FUELCAP);

	public boolean redstoneMode;
	private int redstoneTick = 0;
	private int prevRedstone;

	private EngineSettings setting = EngineSettings.FULL;

	private enum EngineSettings {
		SHUTDOWN(0, 0),
		STANDBY(16, 64),
		LOW(4, 8),
		MEDIUM(2, 2),
		FULL(1, 1);

		public final int speedFactor;
		public final int fuelFactor;

		public static final EngineSettings[] list = values();

		private EngineSettings(int speed, int fuel) {
			speedFactor = speed;
			fuelFactor = fuel;
		}

		public double getSpeedDecimal() {
			if (this == SHUTDOWN)
				return 0;
			return 100D/speedFactor;
		}

		public int getEfficiencyFactor() {
			if (this == SHUTDOWN)
				return 0;
			return fuelFactor/speedFactor;
		}
	}

	public boolean consumeFuel() {
		return setting.fuelFactor != 0;
	}

	public boolean canProducePower() {
		if (prevRedstone > 0 && !redstoneMode)
			return false;
		return setting.speedFactor != 0;
	}

	public boolean playSound() {
		return this.canProducePower();
	}

	public float getSpeedMultiplier() {
		if (this.canProducePower())
			return 1F/setting.speedFactor;
		return 0;
	}

	public int getSpeedFactor() {
		if (this.canProducePower())
			return setting.speedFactor;
		return 0;
	}

	public int getFuelMultiplier(EngineClass e) {
		int base = setting.fuelFactor;
		if (e == EngineClass.TURBINE)
			base /= 8;
		return Math.max(1, base);
	}

	public float getSoundStretch() {
		switch(setting) {
			case FULL:
				return 1F;
			case LOW:
				return 0.6F;
			case MEDIUM:
				return 0.8F;
			case SHUTDOWN:
				return 0F;
			case STANDBY:
				return 0.4F;
			default:
				return 1F;
		}
	}

	public void increment() {
		int l = EngineSettings.list.length;
		int o = setting.ordinal();
		o++;
		if (o >= l)
			o = 0;
		setting = EngineSettings.list[o];
	}

	public void setSetting(int ordinal) {
		int o = Math.max(0, Math.min(ordinal, EngineSettings.list.length-1));
		setting = EngineSettings.list[o];
	}

	public int getSettingNumber() {
		return setting.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

		if (redstoneTick > 0)
			redstoneTick--;
		int power = redstoneTick == 0 ? world.getBlockPowerInput(x, y, z) : prevRedstone;
		if (prevRedstone != power)
			redstoneTick = 60;
		prevRedstone = power;
		//ReikaJavaLibrary.pConsole(prevRedstone+":"+this.canProducePower(), Side.SERVER);

		if (redstoneMode) {
			setting = power == 15 ? EngineSettings.SHUTDOWN : EngineSettings.list[4-power/3];
		}
		//ReikaJavaLibrary.pConsole(tank);
		if (tank.isEmpty())
			return;

		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.ENGINE)
			if (this.transferToEngine((TileEntityEngine)world.getTileEntity(x, y+1, z), false))
				return;
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.FUELENGINE)
			if (this.transferToFuelEngine((TileEntityFuelEngine)world.getTileEntity(x, y+1, z), false))
				return;

		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.ENGINE)
			if (this.transferToEngine((TileEntityEngine)world.getTileEntity(x, y-1, z), true))
				return;
		if (MachineRegistry.getMachine(world, x, y-1, z) == MachineRegistry.FUELENGINE)
			if (this.transferToFuelEngine((TileEntityFuelEngine)world.getTileEntity(x, y-1, z), true))
				return;
	}

	private boolean transferToFuelEngine(TileEntityFuelEngine te, boolean flip) {
		if (te.isFlipped != flip)
			return false;
		FluidStack liq = tank.getFluid();
		int toadd = Math.min(liq.amount/4+1, te.CAPACITY-te.getFuelLevel());
		if (toadd > 0) {
			te.addFuel(toadd);
			tank.removeLiquid(toadd);
			return true;
		}
		return false;
	}

	private boolean transferToEngine(TileEntityEngine te, boolean flip) {
		if (te.isFlipped != flip)
			return false;
		FluidStack liq = tank.getFluid();
		Fluid f = te.getEngineType().getFuelType();
		if (f == null || liq == null || !f.equals(liq.getFluid()))
			return false;
		if (te.getFuelLevel()+liq.amount > te.FUELCAP)
			return false;
		te.addFuel(liq.amount/4+1);
		tank.removeLiquid(liq.amount/4+1);
		return true;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		tank.writeToNBT(NBT);

		NBT.setInteger("lvl", setting.ordinal());

		NBT.setBoolean("redstone", redstoneMode);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		tank.readFromNBT(NBT);

		setting = EngineSettings.list[NBT.getInteger("lvl")];

		redstoneMode = NBT.getBoolean("redstone");
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.ECU;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.BEDPIPE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, ForgeDirection side) {
		return true;
	}

	public static EngineSettings[] getSettingList() {
		EngineSettings[] arr = new EngineSettings[EngineSettings.list.length];
		System.arraycopy(EngineSettings.list, 0, arr, 0, arr.length);
		return arr;
	}

	public static String getSettingsAsString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < EngineSettings.list.length; i++) {
			EngineSettings set = EngineSettings.list[i];
			sb.append(String.format("%s: %.2f%% Speed, %dx Fuel Efficiency", ReikaStringParser.capFirstChar(set.name()), set.getSpeedDecimal(), set.getEfficiencyFactor()));
			if (i < EngineSettings.list.length-1)
				sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.canFill(from, resource.getFluid()))
			return tank.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.canDrain(from, resource.getFluid()) ? tank.drain(resource.amount, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		//if (fluid.equals(FluidRegistry.LAVA)) Why was THIS here???
		//	return true;
		TileEntity te = this.getAdjacentTileEntity(ForgeDirection.UP);
		if (te instanceof TileEntityEngine) {
			TileEntityEngine eng = (TileEntityEngine)te;
			return eng.getEngineType() != EngineType.STEAM && eng.getEngineType().burnsFuel() && fluid.equals(eng.getEngineType().getFuelType());
		}
		else if (te instanceof TileEntityFuelEngine) {
			return fluid.equals(FluidRegistry.getFluid("fuel"));
		}
		if (fluid.equals(FluidRegistry.getFluid("rc jet fuel")))
			return true;
		if (fluid.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		if (fluid.equals(FluidRegistry.getFluid("fuel")))
			return true;
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return ReikaFluidHelper.isFluidDrainableFromTank(fluid, tank);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public Flow getFlowForSide(ForgeDirection side) {
		return Flow.DUAL;
	}
}
