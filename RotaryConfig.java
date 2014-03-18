/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;

import net.minecraftforge.fluids.FluidContainerRegistry;
import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Interfaces.ConfigList;
import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class RotaryConfig extends ControlledConfig {

	public RotaryConfig(DragonAPIMod mod, ConfigList[] option, IDRegistry[] blocks, IDRegistry[] items, IDRegistry[] id, int cfg) {
		super(mod, option, blocks, items, id, cfg);
	}

	private static final ArrayList<String> entries = ReikaJavaLibrary.getEnumEntriesWithoutInitializing(RotaryAchievements.class);
	public int[] achievementIDs = new int[entries.size()]; //

	/** Non-config-file control data used by the machines */

	public static final int friction = 0;
	public static final int torquelimit = (Integer.MAX_VALUE-1)/2;	// ~1 billion
	public static final int omegalimit = (Integer.MAX_VALUE-1)/2;
	public static final boolean debugmode = false;

	public static final EnumDifficulty EASIEST = EnumDifficulty.EASY;
	public static final EnumDifficulty HARDEST = EnumDifficulty.HARD;

	@Deprecated
	public static final int MILLIBUCKET = FluidContainerRegistry.BUCKET_VOLUME;

	//Initialization of the config
	@Override
	protected void loadAdditionalData() {
		for (int i = 0; i < entries.size(); i++) {
			String name = entries.get(i);
			achievementIDs[i] = config.get("Achievement IDs", name, 24000+i).getInt();
		}
	}

	@Override
	protected void onInit() {

	}
}
