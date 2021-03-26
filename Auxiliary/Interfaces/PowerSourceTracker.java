/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import java.util.Collection;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;

public interface PowerSourceTracker {

	public abstract PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller);

	/** c may contain nulls. */
	public abstract void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir);

	public abstract World getWorld();

	public int getX();
	public int getY();
	public int getZ();

	public int getIoOffsetX();
	public int getIoOffsetY();
	public int getIoOffsetZ();

}
