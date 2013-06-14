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
import Reika.RotaryCraft.Auxiliary.MultiBlockMachine;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMirror extends RotaryCraftTileEntity implements MultiBlockMachine {

	//2.3 kW/m^2 (392MW/170000) -> 2kW/block; sunlight is 15 kW per m^2, so thus efficiency of 13%

	public float theta;

	@Override
	public boolean isMultiBlock(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int[] getMultiBlockPosition(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int[] getMultiBlockSize(World world, int x, int y, int z) {
		return null;
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
		return MachineRegistry.MIRROR.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.adjustAim(world, x, y, z, meta);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private int getLightLevel(World world, int x, int y, int z) {
		if (!world.canBlockSeeTheSky(x, y, z))
			return 0;
		return 15;
	}

	private void adjustAim(World world, int x, int y, int z, int meta) {
		float sun = world.getSunBrightness(0);
		long time = world.getWorldTime()%24000;
		phi = 90+180*time/12000F;
	}

}
