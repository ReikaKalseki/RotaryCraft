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
import Reika.RotaryCraft.Base.TileEntity.TileEntityAuxiliaryEngine;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCoalGas extends TileEntityAuxiliaryEngine {

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.COALGAS.ordinal();
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
	public boolean arePowerConditionsMet() {
		return false;
	}

	@Override
	public int getEmittedTorque() {
		return 0;
	}

	@Override
	public int getEmittedSpeed() {
		return 0;
	}

}
