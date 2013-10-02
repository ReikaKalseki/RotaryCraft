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
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;

public class TileEntityBeamMirror extends RotaryCraftTileEntity implements RangedEffect {

	private float theta;

	@Override
	public void onEMP() {}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return 0;
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
		this.adjustAim(world, x, y, z);
	}

	private void adjustAim(World world, int x, int y, int z) {
		float suntheta = ReikaWorldHelper.getSunAngle(world);

		float movespeed = 0.5F;

		if (theta < suntheta)
			theta += movespeed;
		if (theta > suntheta)
			theta -= movespeed;
	}

}
