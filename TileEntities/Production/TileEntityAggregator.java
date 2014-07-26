/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaBiomeHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Base.TileEntity.PoweredLiquidProducer;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAggregator extends PoweredLiquidProducer implements TemperatureTE, DiscreteFunction {

	public static final int CAPACITY = 64000;

	private StepTimer timer = new StepTimer(20);

	private int temperature;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		timer.update();
		if (timer.checkCap())
			this.updateTemperature(world, x, y, z, meta);

		if (!tank.isEmpty())
			this.dumpLiquid(world, x, y, z);

		if (power < MINPOWER || omega < MINSPEED)
			return;

		if (tank.isFull())
			return;

		tickcount++;
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;

		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		if (temperature < Tamb) {
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			float h = biome.rainfall; //Not used by any biome
			int amt = this.getWaterProduced(biome);
			tank.addLiquid(amt, FluidRegistry.WATER);
		}
	}

	private void dumpLiquid(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			TileEntity te = this.getAdjacentTileEntity(dir);
			if (te instanceof IFluidHandler) {
				IFluidHandler ifl = (IFluidHandler)te;
				int added = ifl.fill(dir.getOpposite(), tank.getFluid(), true);
				if (added > 0) {
					tank.removeLiquid(added);
					if (tank.isEmpty())
						return;
				}
			}
		}
	}

	public int getOperationTime() {
		return Math.max(0, (int)(80-5*ReikaMathLibrary.logbase(omega+1-MINSPEED, 2)));
	}

	private int getWaterProduced(BiomeGenBase biome) {
		return Math.max(2, (int)(torque*torque*ReikaBiomeHelper.getBiomeHumidity(biome)));
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
		return MachineRegistry.AGGREGATOR;
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
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean canOutputTo(ForgeDirection to) {
		return to.offsetY == 0;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getAmbientTemperatureAt(world, x, y, z);
		int dT = Tamb-temperature;
		temperature += dT/4;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public void overheat(World world, int x, int y, int z) {

	}
}
