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
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import Reika.DragonAPI.Auxiliary.ModOreList;
import Reika.DragonAPI.Instantiable.ConfigReader;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.RotaryCraft.Registry.APIRegistry;

public final class OreForcer {

	public static void registerModItemsToDictionary() {
		if (APIRegistry.THAUMCRAFT.conditionsMet())
			registerThaumcraft();
		if (APIRegistry.FORESTRY.conditionsMet())
			forceForestryOreDict();
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

	private static void forceForestryOreDict() {
		List<IRecipe> li = CraftingManager.getInstance().getRecipeList();
		List<IRecipe> add = new ArrayList<IRecipe>();
		for (int i = 0; i < li.size(); i++) {
			IRecipe ir = li.get(i);
			ItemStack product = ir.getRecipeOutput().copy();
			if (ir instanceof ShapedRecipes) {
				ShapedRecipes sr = (ShapedRecipes)ir;
				for (int k = 0; k < sr.recipeItems.length; k++) {
					boolean[] over = new boolean[9];
					if (ReikaItemHelper.listContainsItemStack(OreDictionary.getOres(ModOreList.APATITE.getProductLabel()), sr.recipeItems[k])) {
						over[k] = true;
					}
					if (ReikaArrayHelper.containsTrue(over)) {
						Object[] obj = new Object[12];
						obj[0] = "012";
						obj[1] = "345";
						obj[2] = "678";
						for (int b = 0; b < 9; b++) {
							if (over[b])
								obj[b+3] = OreDictionary.getOres(ModOreList.APATITE.getProductLabel());
							else
								obj[b+3] = sr.recipeItems[b];
						}
						ShapedOreRecipe so = new ShapedOreRecipe(product, obj);
					}
				}
			}
			else if (ir instanceof ShapelessRecipes) {
				ShapelessRecipes sr = (ShapelessRecipes)ir;
				for (int k = 0; k < sr.recipeItems.size(); k++) {
					boolean[] over = new boolean[sr.recipeItems.size()];
					if (ReikaItemHelper.listContainsItemStack(OreDictionary.getOres(ModOreList.APATITE.getProductLabel()), (ItemStack)sr.recipeItems.get(k))) {
						over[k] = true;
					}
					if (ReikaArrayHelper.containsTrue(over)) {
						Object[] obj = new Object[12];
						obj[0] = "012";
						obj[1] = "345";
						obj[2] = "678";
						for (int b = 0; b < 9; b++) {
							if (over[b])
								obj[b+3] = OreDictionary.getOres(ModOreList.APATITE.getProductLabel());
							else
								obj[b+3] = sr.recipeItems.get(b);
						}
						ShapedOreRecipe so = new ShapedOreRecipe(product, obj);
					}
				}
			}
		}
	}

}
