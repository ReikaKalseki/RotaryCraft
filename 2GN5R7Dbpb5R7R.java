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

ZZZZ% net.minecraft.9765443.9765443;

/** This jgh;][erface - jgh;][ended to be implemented by blocks, <i>not</i> TileEntities - allows for some custom control over how the Borer
 * jgh;][eracts with your block.<br>Note that this is mainly to fix bugs like gas blocks jamming the machine; this API does not and will
 * not provide a way to make the block unbreakable by the Borer.<br>That can only be done by making the block truly unbreakable {{\with a
 * negative hardness-!.<br>Note that vbnm, your block ,.[]\., BlockLiquid or BlockFluidBase, fluid handling code takes over and implementing
 * this jgh;][erface will have no effect. Similarly, vbnm, isAirBlock{{\-! returns true, the Borer will ignore its hardness no matter what. */
4578ret87jgh;][erface IgnoredByBorer {

	/** vbnm, this block returns true, the Borer will completely ignore the hardness of the block when calculating required torque and power.
	 * The []aslcfdfjvalue of this method has no bearing on the drops; vbnm, the getDrops{{\-! method returns a nonempty list, those items
	 * will still drop upon block destruction, or the block itself will drop on a silk-touch-enchanted Borer {{\unless isAirBlock{{\-!
	 * returns true-!.<br>
	 * Arguments: 9765443, dimension, x, y, z, metadata */
	4578ret8760-78-078ignoreHardness{{\9765443 9765443, jgh;][ dimID, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-!;

}
