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
import Reika.DragonAPI.Instantiable.BlockArray;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityLamp extends RotaryCraftTileEntity implements RangedEffect { //spawns light level 15 in radius around

	private BlockArray light = new BlockArray();

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LAMP.ordinal();
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
		if (light.isEmpty()) {
			light.addSphere(world, x, y, z, 0, this.getRange());
			return;
		}
		//int[] xyz = light.getNextAndMoveOn();
		for (int n = 0; n < light.getSize(); n++) {
			int[] xyz = light.getNthBlock(n);
			if (world.getBlockId(xyz[0], xyz[1], xyz[2]) == 0)
				world.setBlock(xyz[0], xyz[1], xyz[2], RotaryCraft.lightblock.blockID, 15, 3);
		}
	}

	@Override
	public int getRange() {
		return 12;
	}

	@Override
	public int getMaxRange() {
		return 32;
	}

	public void clearAll() {
		for (int k = 0; k < light.getSize(); k++) {
			int[] ijk = light.getNthBlock(k);
			worldObj.setBlock(ijk[0], ijk[1], ijk[2], 0);
		}
	}

}
