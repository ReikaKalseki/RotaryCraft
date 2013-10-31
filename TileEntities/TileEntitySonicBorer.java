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
import Reika.RotaryCraft.Base.TileEntityBeamMachine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntitySonicBorer extends TileEntityBeamMachine implements PressureTE {

	private int pressure;

	public static final int FIRE_PRESSURE = 4;
	public static final int MAXPRESSURE = 1000;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, true);
		pressure += this.getPressureIncrement();
		if (pressure >= FIRE_PRESSURE) {
			if (this.canFire(world, x, y, z, meta)) {
				this.fire(world, x, y, z, meta);
				pressure -= FIRE_PRESSURE;
			}
		}
		if (pressure > MAXPRESSURE) {
			this.overpressure(world, x, y, z);
		}
	}

	private void fire(World world, int x, int y, int z, int meta) {

	}

	private boolean canFire(World world, int x, int y, int z, int meta) {
		return false;
	}

	private int getPressureIncrement() {
		return 1;
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

	@Override
	public void makeBeam(World world, int x, int y, int z, int meta) {

	}

}
