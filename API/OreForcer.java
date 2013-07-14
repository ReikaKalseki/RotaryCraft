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

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Auxiliary.ModOreList;
import Reika.DragonAPI.Instantiable.ConfigReader;
import Reika.RotaryCraft.Registry.APIRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;

public final class OreForcer {

	public static void registerModItemsToDictionary() {
		if (APIRegistry.THAUMCRAFT.conditionsMet() && ConfigRegistry.THAUMCRAFTORES.getState())
			registerThaumcraft();
	}

	private static void registerThaumcraft() {
		ModOreList[] ores = {ModOreList.CINNABAR, ModOreList.AMBER, ModOreList.INFUSEDAIR, ModOreList.INFUSEDFIRE, ModOreList.INFUSEDWATER,
				ModOreList.INFUSEDEARTH, ModOreList.INFUSEDVIS, ModOreList.INFUSEDDULL};
		int[] metas = {0, 7, 1, 2, 3, 4, 5, 6};
		int[] imetas = {3, 6, 0, 1, 2, 3, 4, 5};

		ConfigReader cfg = new ConfigReader("Thaumcraft");

		int oreID = cfg.getConfigInt("block", "BlockCustomOre");
		int shardID = cfg.getConfigInt("item", "ItemShard");
		int dropID = cfg.getConfigInt("item", "ItemResource");

		for (int i = 0; i < ores.length; i++) {
			ItemStack oreblock = new ItemStack(oreID, 1, metas[i]);
			String[] oname = ores[i].getOreDictNames();
			String[] iname = ores[i].getOreDictIngots();
			if (i >= 2) {
				ItemStack shard = new ItemStack(shardID, 1, imetas[i]);
				for (int j = 0; j < iname.length; j++)
					OreDictionary.registerOre(iname[j], shard);
			}
			else {
				ItemStack drop = new ItemStack(dropID, 1, imetas[i]);
				for (int j = 0; j < iname.length; j++)
					OreDictionary.registerOre(iname[j], drop);
			}
			for (int j = 0; j < oname.length; j++)
				OreDictionary.registerOre(oname[j], oreblock);
		}
	}

}
