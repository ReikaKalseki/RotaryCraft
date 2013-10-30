/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.world.World;
import Reika.RotaryCraft.Auxiliary.PressureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySonicBorer extends TileEntityPowerReceiver implements PressureTE {

	private int pressure;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {

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
		return MachineRegistry.SONICBORER.ordinal();
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
	public void updatePressure(World world, int x, int y, int z, int meta) {

	}

	@Override
	public void addPressure(int press) {

	}

	@Override
	public int getPressure() {
		return 0;
	}

	@Override
	public void overpressure(World world, int x, int y, int z) {

	}

}
