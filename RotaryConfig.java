/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;

import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Interfaces.Configuration.ConfigList;
import Reika.DragonAPI.Interfaces.Registry.IDRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.BlastGate;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class RotaryConfig extends ControlledConfig {

	private static final ArrayList<String> entries = ReikaJavaLibrary.getEnumEntriesWithoutInitializing(RotaryAchievements.class);
	private final DataElement<Integer>[] achievementIDs = new DataElement[entries.size()]; //
	private DataElement<String[]> blastGate;

	/** Non-config-file control data used by the machines */

	public static final int friction = 0;
	public static final int torquelimit = (Integer.MAX_VALUE-1)/2;	// ~1 billion
	public static final int omegalimit = (Integer.MAX_VALUE-1)/2;
	public static final boolean debugmode = false;

	public static final EnumDifficulty EASIEST = EnumDifficulty.EASY;
	public static final EnumDifficulty HARDEST = EnumDifficulty.HARD;

	public RotaryConfig(DragonAPIMod mod, ConfigList[] option, IDRegistry[] id) {
		super(mod, option, id);

		for (int i = 0; i < entries.size(); i++) {
			String name = entries.get(i);
			achievementIDs[i] = this.registerAdditionalOption("Achievement IDs", name, 24000+i);
		}

		blastGate = this.registerAdditionalOption("Other Options", "Alternate Blast Furnace Materials", new String[0]);
	}

	@Override
	protected void onInit() {

	}

	public int getAchievementID(int idx) {
		return achievementIDs[idx].getData();
	}

	public ArrayList<Object> getBlastFurnaceGatingMaterials() {
		String[] arr = blastGate.getData();
		if (arr == null || arr.length == 0)
			return new ArrayList();
		ArrayList<Object> c = new ArrayList();
		boolean invalid = false;
		for (int i = 0; i < arr.length; i++) {
			String idx = arr[i].toUpperCase();
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
				Object item = g.getItem();
				if (item == null) {
					RotaryCraft.logger.logError("Selected gating material "+g+" could not be found; either the item does not exist or its mods have not yet loaded.");
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
			RotaryCraft.logger.log(sb.toString());
		}
		return c;
	}
}
