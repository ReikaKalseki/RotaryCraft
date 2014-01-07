/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import net.minecraft.world.World;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;

public abstract class TileEntityAuxiliaryEngine extends TileEntityIOMachine implements PowerGenerator {

	@Override
	public final boolean canProvidePower() {
		return true;
	}

	@Override
	public final PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
	}

	@Override
	public final long getMaxPower() {
		return power;
	}

	@Override
	public final long getCurrentPower() {
		return power;
	}

	public abstract boolean arePowerConditionsMet();

	public abstract int getEmittedTorque();

	public abstract int getEmittedSpeed();

}
