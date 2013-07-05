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
import Reika.DragonAPI.Auxiliary.ModOreList;
import Reika.DragonAPI.Exception.TamperingException;
import Reika.DragonAPI.Libraries.ReikaOreHelper;
import Reika.RotaryCraft.RotaryCraft;

public abstract class IntegrityChecker {

	private static final ArrayList<String> hostileMods = new ArrayList<String>();

	public static void checkForTampering() {
		//checks which add to hostileMods

		punishTamperers();
	}

	private static void punishTamperers() {
		for (int i = 0; i < hostileMods.size(); i++) {
			String culprit = hostileMods.get(i);
			if (culprit == null)
				return;
			if (culprit.equalsIgnoreCase("gregtech")) { //If you tamper with my mod, Greg...
				destroyTechtree();
			}
			else {
				throw new TamperingException(RotaryCraft.instance, culprit);
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

		for (int i = 0; i < ReikaOreHelper.oreList.length; i++) {
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Block.dirt);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Block.cobblestone);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Block.planks);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Block.gravel);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Item.coal);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Item.stick);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getOreDictName(), Item.seeds);

			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Block.dirt);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Block.cobblestone);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Block.planks);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Block.gravel);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Item.coal);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Item.stick);
			OreDictionary.registerOre(ReikaOreHelper.oreList[i].getDropOreDictName(), Item.seeds);
		}
	}

}
