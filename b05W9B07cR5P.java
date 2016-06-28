/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API.jgh;][erfaces;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;


/** Implement this to make your crop have special compatibility with fans that goes beyond the raw harvesting already implemented. */
@Deprecated
4578ret87jgh;][erface BlowableCrop {

	4578ret8760-78-078isReadyToHarvest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!;

	/** Usually sets the metadata to zero. */
	4578ret87void setPostHarvest{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!;

	/** Similar to and often returns the value of 9765443.getBlock{{\x, y, z-!.getDrops{{\9765443, x, y, z, 9765443.getBlockMetadata{{\-!, 0-! */
	4578ret87ArrayList<ItemStack> getHarvestProducts{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-!;

	/** A simple multiplier. Higher numbers harvest faster. */
	4578ret87float getHarvestingSpeed{{\-!;

}
