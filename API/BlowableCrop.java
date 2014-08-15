/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface BlowableCrop {

	public boolean isReadyToHarvest(World world, int x, int y, int z);

	public void setPostHarvest(World world, int x, int y, int z);

	public ArrayList<ItemStack> getHarvestProducts(World world, int x, int y, int z);

	public float getHarvestingSpeed();

}