/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import cpw.mods.fml.common.Loader;

public enum APIRegistry {

	BUILDCRAFT(Loader.isModLoaded("BuildCraft")),
	THAUMCRAFT(Loader.isModLoaded("ThaumCraft")),
	IC2(Loader.isModLoaded("IndustrialCraft")),
	GREGTECH(Loader.isModLoaded("GregTech")),
	FORESTRY(Loader.isModLoaded("Forestry"));

	private boolean condition;
	private boolean preset = false;

	private APIRegistry(boolean c) {
		condition = c;
		preset = true;
	}

	public boolean conditionsMet() {
		if (preset)
			return condition;
		return false;
	}

}
