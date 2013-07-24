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
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Auxiliary.ModOreList;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import cpw.mods.fml.common.registry.GameRegistry;

public final class OreForcer {

	public static void registerModItemsToDictionary() {
		//test();
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

	private static void test() {
		List<IRecipe> li = CraftingManager.getInstance().getRecipeList();
		ReikaJavaLibrary.pConsole("Loading "+li.size()+" recipes.");
		ItemStack piston = new ItemStack(Block.pistonBase);
		for (int i = 0; i < li.size(); i++) {
			IRecipe ir = li.get(i);
			if (ir instanceof ShapedRecipes) {
				ShapedRecipes sr = (ShapedRecipes)ir;
				ItemStack[] in = sr.recipeItems;
				int num = in.length;
				boolean[] has = new boolean[num];
				for (int j = 0; j < num; j++) {
					if (ReikaItemHelper.matchStacks(piston, in[j]))
						has[j] = true;
				}
				if (ReikaArrayHelper.containsTrue(has))
					ReikaJavaLibrary.pConsole(sr);
			}
		}
	}

	private static void force(String s) {
		ReikaJavaLibrary.pConsole("ROTARYCRAFT: Forcing compatibility with "+s);
		if (s.equals(APIRegistry.THAUMCRAFT.getModLabel())) {
			registerThaumcraft();
		}
		if (s.equals(APIRegistry.FORESTRY.getModLabel()))
			intercraftApatite();
	}

	private static void intercraftApatite() {
		try {
			Class forest = Class.forName("forestry.core.config.ForestryItem");
			Field apa = forest.getField("apatite");
			Item item = (Item)apa.get(null);
			ItemStack apatite = new ItemStack(item.itemID, 1, 0);
			GameRegistry.addShapelessRecipe(apatite, ItemStacks.getModOreIngot(ModOreList.APATITE));
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: RotaryCraft apatite can now be crafted into Forestry apatite!");
		}
		catch (ClassNotFoundException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Forestry Config class not found! Cannot read its items for compatibility forcing!");
		}
		catch (NoSuchFieldException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Forestry config field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Cannot read Forestry config (Security Exception)! Apatite not convertible!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Illegal argument for reading Forestry config!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Illegal access exception for reading Forestry config!");
			e.printStackTrace();
		}
	}

	private static void registerThaumcraft() {
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

			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Thaumcraft ores are being registered to Ore Dictionary!");

			for (int i = 0; i < ModOreList.oreList.length; i++) {
				if (ModOreList.oreList[i].isThaumcraft()) {
					ModOreList.oreList[i].reloadOreList();
					ReikaJavaLibrary.pConsole("ROTARYCRAFT: Registering "+ModOreList.oreList[i].getName());
				}
			}

			ItemStack[] out = {dropCinnabar, dropAmber, shardAir, shardFire, shardWater, shardEarth, shardVis, shardDull};
			int k = 0;
			for (int i = 0; i < ModOreList.oreList.length; i++) {
				if (ModOreList.oreList[i].isThaumcraft()) {
					GameRegistry.addShapelessRecipe(out[k], ItemStacks.getModOreIngot(ModOreList.oreList[i]));
					k++;
					ReikaJavaLibrary.pConsole("ROTARYCRAFT: "+ModOreList.oreList[i].getName()+" can now be crafted with RotaryCraft equivalents!");
				}
			}

		}
		catch (ClassNotFoundException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Thaumcraft Config class not found! Cannot read its items for ore dictionary registration!");
		}
		catch (NoSuchFieldException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Thaumcraft config field not found! "+e.getMessage());
		}
		catch (SecurityException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Cannot read Thaumcraft config (Security Exception)! Ores not registered!"+e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Illegal argument for reading Thaumcraft config!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Illegal access exception for reading Thaumcraft config!");
			e.printStackTrace();
		}
	}

}
