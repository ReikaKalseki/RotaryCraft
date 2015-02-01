/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Interfaces.ConfigList;
import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.BlastGate;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class RotaryConfig extends ControlledConfig {

	public RotaryConfig(DragonAPIMod mod, ConfigList[] option, IDRegistry[] id, int cfg) {
		super(mod, option, id, cfg);
	}

	private static final ArrayList<String> entries = ReikaJavaLibrary.getEnumEntriesWithoutInitializing(RotaryAchievements.class);
	private int[] achievementIDs = new int[entries.size()]; //
	private String[] blastGate;

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

		blastGate = config.get("Other Options", "Alternate Blast Furnace Materials", new String[0]).getStringList();
	}

	@Override
	protected void onInit() {

	}

	public int getAchievementID(int idx) {
		return achievementIDs[idx];
	}

	public Collection<ItemStack> getBlastFurnaceGatingMaterials() {
		if (blastGate == null || blastGate.length == 0)
			return new ArrayList();
		Collection<ItemStack> c = new ArrayList();
		boolean invalid = false;
		for (int i = 0; i < blastGate.length; i++) {
			String idx = blastGate[i].toUpperCase();
			BlastGate g = null;
			try {
				g = BlastGate.valueOf(idx);
			}
			catch (IllegalArgumentException e) {

			}
			if (g == null) {
				RotaryCraft.logger.logError("Gating material '"+idx+"' is invalid.");
				invalid = true;
			}
			else {
				ItemStack item = g.getItem();
				if (item == null) {
					RotaryCraft.logger.logError("Selected gating material "+g+" could not be found; either the item does not exist or its mod has not yet loaded.");
				}
				else {
					c.add(item);
				}
			}
		}
		if (invalid) {
			RotaryCraft.logger.log("Valid materials (case insensitive):");
			StringBuilder sb = new StringBuilder();
			for (BlastGate g : BlastGate.values())
				sb.append(g.name()+"; ");
			ReikaJavaLibrary.pConsole(sb.toString());
		}
		return c;
	}
}
