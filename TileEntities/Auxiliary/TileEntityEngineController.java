/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Auxiliary;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;

public class TileEntityEngineController extends RotaryCraftTileEntity implements PipeConnector, IFluidHandler {

	public static final int FUELCAP = 3000;

	private HybridTank tank = new HybridTank("ecu", FUELCAP);

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
		if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
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

	public int getFuelMultiplier() {
		return setting.fuelFactor;
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

	public int getSettingNumber() {
		return setting.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.drawFuel(world, x, y, z, meta);
		if (tank.isEmpty())
			return;
		if (MachineRegistry.getMachine(world, x, y+1, z) == MachineRegistry.ENGINE)
			this.transferToEngine((TileEntityEngine)world.getBlockTileEntity(x, y+1, z));
	}

	private void transferToEngine(TileEntityEngine te) {
		FluidStack liq = tank.getFluid();
		Fluid f = te.type.getFuelType();
		if (f == null || liq == null || !f.equals(liq.getFluid()))
			return;
		if (te.getFuelLevel()+liq.amount > te.FUELCAP)
			return;
		te.addFuel(liq.amount/4+1);
		tank.removeLiquid(liq.amount/4+1);
	}

	private void drawFuel(World world, int x, int y, int z, int metadata) {
		int oldfuel;
		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(dx, dy, dz);
				if (tile != null && tile.fuel > 0) {
					Fluid f = tile.getContainedFuel();
					if (tile.fuel > tank.getLevel() && this.canIntakeFuel(f)) {
						oldfuel = tile.fuel-1;
						tile.fuel = 1;
						tank.addLiquid(oldfuel, f);
					}
				}
			}
		}
	}

	private boolean canIntakeFuel(Fluid f) {
		return tank.isEmpty() ? f != null : tank.getActualFluid().equals(f);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		tank.writeToNBT(NBT);

		NBT.setInteger("lvl", setting.ordinal());
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		tank.readFromNBT(NBT);

		setting = EngineSettings.list[NBT.getInteger("lvl")];
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ECU.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE;
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
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (fluid.equals(FluidRegistry.LAVA))
			return true;
		if (fluid.equals(FluidRegistry.getFluid("jet fuel")))
			return true;
		if (fluid.equals(FluidRegistry.getFluid("rc ethanol")))
			return true;
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}
}
