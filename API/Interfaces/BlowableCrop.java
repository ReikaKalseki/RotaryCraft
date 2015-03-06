/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Interfaces;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


/** Implement this to make your crop have special compatibility with fans that goes beyond the raw harvesting already implemented. */
@Deprecated
public interface BlowableCrop {

	public boolean isReadyToHarvest(World world, int x, int y, int z);

	/** Usually sets the metadata to zero. */
	public void setPostHarvest(World world, int x, int y, int z);

	/** Similar to and often returns the value of world.getBlock(x, y, z).getDrops(world, x, y, z, world.getBlockMetadata(), 0) */
	public ArrayList<ItemStack> getHarvestProducts(World world, int x, int y, int z);

	/** A simple multiplier. Higher numbers harvest faster. */
	public float getHarvestingSpeed();

}
