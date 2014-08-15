/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.Interfaces;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface CachedConnection {

	public boolean isConnectionValidForSide(ForgeDirection dir);

	public void recomputeConnections(World world, int x, int y, int z);

	public boolean shouldTryToConnect(ForgeDirection dir);

	public void deleteFromAdjacentConnections(World world, int x, int y, int z);

	public void addToAdjacentConnections(World world, int x, int y, int z);

}