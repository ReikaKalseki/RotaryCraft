/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Interfaces.Configuration.ConfigList;
import Reika.DragonAPI.Interfaces.Registry.IDRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.BlastGate;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class RotaryConfig extends ControlledConfig {

	private static final ArrayList<String> entries = ReikaJavaLibrary.getEnumEntriesWithoutInitializing(RotaryAchievements.class);
	private final DataElement<Integer>[] achievementIDs = new DataElement[entries.size()]; //

	private DataElement<String[]> blastGate;
	private DataElement<String> bedrockGate;
	private DataElement<String> gravelGate;

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
		bedrockGate = this.registerAdditionalOption("Other Options", "Bedrock Armor Gating Material", "");
		gravelGate = this.registerAdditionalOption("Other Options", "Gravel Gun Gating Material", "");
	}

	@Override
	protected void onInit() {

	}

	public int getAchievementID(int idx) {
		return achievementIDs[idx].getData();
	}

	public ItemStack getBedrockArmorGatingMaterial(boolean check, ItemStack obj) {
		String item = bedrockGate.getData();
		if (!check || item == null || item.length() == 0)
			return obj;
		return this.getGatedMaterial(item, obj);
	}

	public ItemStack getGravelGunGatingMaterial(boolean check, ItemStack obj) {
		String item = gravelGate.getData();
		if (!check || item == null || item.length() == 0)
			return obj;
		return this.getGatedMaterial(item, obj);
	}

	private ItemStack getGatedMaterial(String item, ItemStack obj) {
		BlastGate g = null;
		try {
			g = BlastGate.valueOf(item.toUpperCase());
		}
		catch (IllegalArgumentException e) {

		}
		if (g == null) {
			RotaryCraft.logger.logError("Gating material '"+item+"' is invalid.");
			return obj;
		}
		else {
			ItemStack ret = ReikaItemHelper.parseItem(g.getItem());
			if (ret == null) {
				RotaryCraft.logger.logError("Selected gating material "+g+" could not be found; either the item does not exist or its mods have not yet loaded.");
			}
			else {

			}
		}
		return obj;
	}

	public Object[] getBlastFurnaceGatingMaterials(boolean check, Object obj1, Object obj2, Object obj3, Object obj4) {
		String[] arr = blastGate.getData();
		if (!check || arr == null || arr.length == 0)
			return new Object[]{obj1, obj2, obj3, obj4};
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

		switch(c.size()) {
			case 1:
				obj1 = obj2 = obj3 = obj4 = c.get(0);
				break;
			case 2:
				obj1 = obj4 = c.get(0);
				obj2 = obj3 = c.get(1);
				break;
			case 3:
				obj1 = obj4 = c.get(0);
				obj2 = c.get(1);
				obj3 = c.get(2);
				break;
			case 4:
				obj1 = c.get(0);
				obj2 = c.get(1);
				obj3 = c.get(2);
				obj4 = c.get(3);
				break;
			default:
				break;
		}

		return new Object[]{obj1, obj2, obj3, obj4};
	}
}
