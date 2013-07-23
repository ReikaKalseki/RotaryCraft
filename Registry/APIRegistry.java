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

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import cpw.mods.fml.common.Loader;

public enum APIRegistry {

	BUILDCRAFT("Buildcraft"),
	THAUMCRAFT("Thaumcraft"),
	IC2("IndustrialCraft"),
	GREGTECH("Gregtech"),
	FORESTRY("Forestry");

	private boolean condition;
	private boolean preset = false;
	private String modlabel;

	public static final APIRegistry[] apiList = APIRegistry.values();

	private APIRegistry(boolean c) {
		condition = c;
		preset = true;
		if (c)
			ReikaJavaLibrary.pConsole(this+" detected in the MC installation. Adjusting accordingly.");
	}

	private APIRegistry(String s) {
		this(Loader.isModLoaded(s));
		modlabel = s;
	}

	public boolean conditionsMet() {
		if (preset)
			return condition;
		return false;
	}

	public String getModLabel() {
		return modlabel;
	}

	@Override
	public String toString() {
		return this.getModLabel();
	}

}
