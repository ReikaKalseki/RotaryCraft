/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;

public interface PowerSourceTracker {

	public abstract PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller);

	public abstract World getWorld();

	public int getX();
	public int getY();
	public int getZ();

	public int getIoOffsetX();
	public int getIoOffsetY();
	public int getIoOffsetZ();

}
