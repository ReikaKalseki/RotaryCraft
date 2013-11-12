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

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.PoweredLiquidProducer;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAggregator extends PoweredLiquidProducer {

	public static final int CAPACITY = 6000;

	private StepTimer timer = new StepTimer(2);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		if (power >= MINPOWER && omega >= MINSPEED) {
			if (!tank.isFull()) {
				BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
				float h = biome.rainfall; //Not used by any biome
				tank.addLiquid((int)(40*ReikaWorldHelper.getBiomeHumidity(biome)), FluidRegistry.WATER);
			}
		}
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
}
