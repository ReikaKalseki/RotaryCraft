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

import java.util.ArrayList;

import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

public interface ConnectedTextureGlass {

	/** Returns the unconnected sides. Each integer represents one of 8 adjacent corners to a face, with the same
	 * numbering convention as is found on a calculator or computer number pad. */
	public ArrayList<Integer> getEdgesForFace(IBlockAccess world, int x, int y, int z, ForgeDirection face);

	public Icon getIconForEdge(int edge);

	public boolean renderCentralTextureForItem(int meta);

}
