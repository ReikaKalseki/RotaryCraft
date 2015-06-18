/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.Collection;
import java.util.Collections;

import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.RotaryCraft.Registry.ConfigRegistry;

public class ModOreCompat {

	static final ModOreCompat instance = new ModOreCompat();

	private final MultiMap<ReikaOreHelper, String> modOres = new MultiMap();

	private ModOreCompat() {
		if (ConfigRegistry.GREGORES.getState()) {
			modOres.addValue(ReikaOreHelper.IRON, "oreBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreNetherrackBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreEndstoneBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreBlackgraniteBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreRedgraniteBrownLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreNetherrackYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreEndstoneYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreBlackgraniteYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreRedgraniteYellowLimonite");
			modOres.addValue(ReikaOreHelper.IRON, "oreBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreNetherrackBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreEndstoneBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreBlackgraniteBandedIron");
			modOres.addValue(ReikaOreHelper.IRON, "oreRedgraniteBandedIron");

			modOres.addValue(ReikaOreHelper.QUARTZ, "oreNetherrackNetherQuartz");
			modOres.addValue(ReikaOreHelper.QUARTZ, "oreEndstoneNetherQuartz");
			modOres.addValue(ReikaOreHelper.QUARTZ, "oreBlackgraniteNetherQuartz");
			modOres.addValue(ReikaOreHelper.QUARTZ, "oreRedgraniteNetherQuartz");
		}
	}

	Collection<String> getAlternateNames(ReikaOreHelper ore) {
		return modOres.get(ore);
	}

	Collection<ReikaOreHelper> keySet() {
		return Collections.unmodifiableCollection(modOres.keySet());
	}

}
