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

import net.minecraft.world.IBlockAccess;

public interface EnvironmentalHeatSource {

	/** Returns the general type of thermal source the object is. Informs the approximate temperature. */
	public SourceType getSourceType(IBlockAccess iba, int x, int y, int z);

	/** If this returns false, the temperature effects do not apply. */
	public boolean isActive(IBlockAccess iba, int x, int y, int z);

	public static enum SourceType {
		/** Icy cold objects. Around -10C. */
		ICY(),

		/** Cool water. Around 15C. */
		WATER(),

		/** Fire temperature; around 300C. */
		FIRE(),

		/** Lava-hot. Around 1200C. */
		LAVA(),

		/** Ambient; effectively a no-op. */
		AMBIENT();
	}

}
