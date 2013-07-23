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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import Reika.DragonAPI.Auxiliary.ModOreList;
import Reika.DragonAPI.Instantiable.ConfigReader;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Registry.APIRegistry;

public final class OreForcer {

	public static void registerModItemsToDictionary() {
		for (int i = 0; i < APIRegistry.apiList.length; i++) {
			if (APIRegistry.apiList[i].conditionsMet()) {
				String mod = APIRegistry.apiList[i].getModLabel();
				try {
					force(mod);
				}
				catch (NullPointerException e) {
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (ClassCastException e) {
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}/*
				catch (IOException e) {
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}*/
				catch (ArrayIndexOutOfBoundsException e) {
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IndexOutOfBoundsException e) {
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					ReikaJavaLibrary.pConsole("Could not force compatibility with "+mod+". Reason: ");
					e.printStackTrace();
				}
			}
		}
	}

	private static void force(String s) {
		if (s.equals(APIRegistry.THAUMCRAFT.getModLabel()))
			registerThaumcraft2();
		if (s.equals(APIRegistry.FORESTRY.getModLabel()))
			;//forceForestryOreDict();
	}

	private static void registerThaumcraft2() {
		try {
			Class thaum = Class.forName("thaumcraft.common.Config");
			Field ore = thaum.getField("blockCustomOre");
			Field item = thaum.getField("itemResource");
			Field shard = thaum.getField("itemShard");

			Block oreBlock = (Block)ore.get(null);
			Item oreItem = (Item)item.get(null);
			Item oreShard = (Item)shard.get(null);

			ItemStack oreCinnabar = new ItemStack(oreBlock.blockID, 1, 0);
			ItemStack oreAir = new ItemStack(oreBlock.blockID, 1, 1);
			ItemStack oreFire = new ItemStack(oreBlock.blockID, 1, 2);
			ItemStack oreWater = new ItemStack(oreBlock.blockID, 1, 3);
			ItemStack oreEarth = new ItemStack(oreBlock.blockID, 1, 4);
			ItemStack oreVis = new ItemStack(oreBlock.blockID, 1, 5);
			ItemStack oreDull = new ItemStack(oreBlock.blockID, 1, 6);
			ItemStack oreAmber = new ItemStack(oreBlock.blockID, 1, 7);

			ItemStack dropCinnabar = new ItemStack(oreItem.itemID, 1, 3); //quicksilver
			ItemStack dropAmber = new ItemStack(oreItem.itemID, 1, 6);

			ItemStack shardAir = new ItemStack(oreShard.itemID, 1, 0);
			ItemStack shardFire = new ItemStack(oreShard.itemID, 1, 1);
			ItemStack shardWater = new ItemStack(oreShard.itemID, 1, 2);
			ItemStack shardEarth = new ItemStack(oreShard.itemID, 1, 3);
			ItemStack shardVis = new ItemStack(oreShard.itemID, 1, 4);
			ItemStack shardDull = new ItemStack(oreShard.itemID, 1, 5);

			OreDictionary.registerOre(ModOreList.CINNABAR.getOreDictNames()[0], oreCinnabar);
			OreDictionary.registerOre(ModOreList.AMBER.getOreDictNames()[0], oreAmber);

			OreDictionary.registerOre(ModOreList.INFUSEDAIR.getOreDictNames()[0], oreAir);
			OreDictionary.registerOre(ModOreList.INFUSEDDULL.getOreDictNames()[0], oreDull);
			OreDictionary.registerOre(ModOreList.INFUSEDEARTH.getOreDictNames()[0], oreEarth);
			OreDictionary.registerOre(ModOreList.INFUSEDFIRE.getOreDictNames()[0], oreFire);
			OreDictionary.registerOre(ModOreList.INFUSEDVIS.getOreDictNames()[0], oreVis);
			OreDictionary.registerOre(ModOreList.INFUSEDWATER.getOreDictNames()[0], oreWater);

			OreDictionary.registerOre(ModOreList.INFUSEDAIR.getProductLabel(), shardAir);
			OreDictionary.registerOre(ModOreList.INFUSEDDULL.getProductLabel(), shardDull);
			OreDictionary.registerOre(ModOreList.INFUSEDEARTH.getProductLabel(), shardEarth);
			OreDictionary.registerOre(ModOreList.INFUSEDFIRE.getProductLabel(), shardFire);
			OreDictionary.registerOre(ModOreList.INFUSEDVIS.getProductLabel(), shardVis);
			OreDictionary.registerOre(ModOreList.INFUSEDWATER.getProductLabel(), shardWater);

			OreDictionary.registerOre(ModOreList.CINNABAR.getProductLabel(), dropCinnabar);
			OreDictionary.registerOre(ModOreList.AMBER.getProductLabel(), dropAmber);

		}
		catch (ClassNotFoundException e) {
			ReikaJavaLibrary.pConsole("Thaumcraft Config class not found! Cannot read its items for ore dictionary registration!");
		}
		catch (NoSuchFieldException e) {
			ReikaJavaLibrary.pConsole("Thaumcraft config field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			ReikaJavaLibrary.pConsole("Cannot read Thaumcraft config (Security Exception)! Ores not registered!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("Illegal argument for reading thaumcraft config!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Illegal access exception for reading thaumcraft config!");
			e.printStackTrace();
		}
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
			if (ir != null && ir.getRecipeOutput() != null) {
				ReikaJavaLibrary.pConsole("ROTARYCRAFT: Overwriting recipe "+ir+" for "+ir.getRecipeOutput()+" to use OreDictionary (\""+ModOreList.APATITE.getProductLabel()+"\")");
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
									obj[b+3] = ModOreList.APATITE.getProductLabel();
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
							Object[] obj = new Object[sr.recipeItems.size()];
							for (int b = 0; b < obj.length; b++) {
								if (over[b])
									obj[b] = ModOreList.APATITE.getProductLabel();
								else
									obj[b] = sr.recipeItems.get(b);
							}
							ShapelessOreRecipe so = new ShapelessOreRecipe(product, obj);
						}
					}
				}
			}
		}
	}

}
