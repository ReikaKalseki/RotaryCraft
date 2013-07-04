/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import Reika.RotaryCraft.Registry.ModOreList;

public class IntegrityChecker {

	private static final ArrayList<String> hostileMods = new ArrayList<String>();

	public static boolean isRCTamperedWith() {
		return false;
	}

	public static void punishTamperer() {
		for (int i = 0; i < hostileMods.size(); i++) {
			String culprit = hostileMods.get(i);
			if (culprit == null)
				return;
			if (culprit.equalsIgnoreCase("gregtech")) { //If you tamper with my mod, Greg...
				destroyTechtree();
			}
		}
	}

	private static void destroyTechtree() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			String[] name = ModOreList.oreList[i].getOreDictNames();
			String[] name2 = ModOreList.oreList[i].getOreDictIngots();
			for (int j = 0; j < name.length; j++) {
				OreDictionary.registerOre(name[j], Block.dirt);
				OreDictionary.registerOre(name[j], Block.cobblestone);
				OreDictionary.registerOre(name[j], Block.planks);
				OreDictionary.registerOre(name[j], Block.gravel);
				OreDictionary.registerOre(name[j], Item.coal);
				OreDictionary.registerOre(name[j], Item.stick);
				OreDictionary.registerOre(name[j], Item.seeds);
			}
			for (int j = 0; j < name2.length; j++) {
				OreDictionary.registerOre(name2[j], Block.dirt);
				OreDictionary.registerOre(name2[j], Block.cobblestone);
				OreDictionary.registerOre(name2[j], Block.planks);
				OreDictionary.registerOre(name2[j], Block.gravel);
				OreDictionary.registerOre(name2[j], Item.coal);
				OreDictionary.registerOre(name2[j], Item.stick);
				OreDictionary.registerOre(name2[j], Item.seeds);
			}
		}
	}

}
