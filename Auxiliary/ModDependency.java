/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2018
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.HashSet;

import Reika.DragonAPI.Exception.MisuseException;
import Reika.DragonAPI.Interfaces.Registry.ModEntry;


public class ModDependency {

	private final HashSet<ModEntry> dependencies = new HashSet();

	public ModDependency(ModEntry... mods) {
		if (mods.length == 0)
			throw new MisuseException("You cannot have a mod dependency list with no mods!");
		for (int i = 0; i < mods.length; i++) {
			dependencies.add(mods[i]);
		}
	}

	public boolean isLoaded() {
		for (ModEntry e : dependencies) {
			if (!e.isLoaded())
				return false;
		}
		return true;
	}

	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();
		for (ModEntry e : dependencies) {
			sb.append(e.getDisplayName()+", ");
		}
		String s = sb.toString();
		return s.substring(0, s.length()-2);
	}

}
