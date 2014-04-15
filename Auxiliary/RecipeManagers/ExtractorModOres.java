/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;

public class ExtractorModOres {

	public static void registerRCIngots() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			String[] ingots = ModOreList.oreList[i].getOreDictIngots();
			for (int j = 0; j < ingots.length; j++) {
				OreDictionary.registerOre(ingots[j], new ItemStack(RotaryCraft.modingots.itemID, 1, i));
			}
		}
	}

	public static void addSmelting() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			FurnaceRecipes.smelting().addSmelting(RotaryCraft.modextracts.itemID, getFlakesIndex(ModOreList.oreList[i]), ReikaItemHelper.getSizedItemStack(getSmeltedIngot(ModOreList.oreList[i]), ModOreList.oreList[i].getDropCount()), 0.7F);
		}
	}

	public static int getSpritesheet(ModOreList ore) {
		return ore.ordinal()/64;
	}

	public static boolean isModOreIngredient(ItemStack is) {
		if (is == null)
			return false;
		if (is.itemID != RotaryCraft.modextracts.itemID)
			return false;
		return ModOreList.getEntryFromDamage(is.getItemDamage()/4) != null;
	}

	public static ExtractorStage getStageFromMetadata(ItemStack is) {
		if (ModOreList.isModOre(is))
			return null;
		return ExtractorStage.values()[is.getItemDamage()%4];
	}

	public static int getIndexOffsetForIngot(ItemStack is) {
		ModOreList ore = ModOreList.getEntryFromDamage(is.getItemDamage());
		if (ore.isIngotType())
			return 3;
		if (ore.isDustType())
			return 1;
		if (ore.isGemType())
			return 2;
		return 0;
	}

	public static int getDustIndex(ModOreList ore) {
		return ore.ordinal()*4;
	}

	public static int getSlurryIndex(ModOreList ore) {
		return getDustIndex(ore)+1;
	}

	public static int getSolutionIndex(ModOreList ore) {
		return getDustIndex(ore)+2;
	}

	public static int getFlakesIndex(ModOreList ore) {
		return getDustIndex(ore)+3;
	}

	public static boolean isDust(ModOreList ore, int index) {
		return index == getDustIndex(ore);
	}

	public static boolean isSlurry(ModOreList ore, int index) {
		return index == getSlurryIndex(ore);
	}

	public static boolean isSolution(ModOreList ore, int index) {
		return index == getSolutionIndex(ore);
	}

	public static boolean isFlakes(ModOreList ore, int index) {
		return index == getFlakesIndex(ore);
	}

	public static ItemStack getDustProduct(ModOreList ore) {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, getDustIndex(ore));
	}

	public static ItemStack getSlurryProduct(ModOreList ore) {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, getSlurryIndex(ore));
	}

	public static ItemStack getSolutionProduct(ModOreList ore) {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, getSolutionIndex(ore));
	}

	public static ItemStack getFlakeProduct(ModOreList ore) {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, getFlakesIndex(ore));
	}

	public static ItemStack getSmeltedIngot(ModOreList ore) {
		switch(ore) {
		case NETHERCOAL:
			return new ItemStack(Item.coal);
		case NETHERCOPPER:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.COPPER.ordinal());
		case NETHERDIAMOND:
			return new ItemStack(Item.diamond);
		case NETHEREMERALD:
			return new ItemStack(Item.emerald);
		case NETHERGOLD:
			return new ItemStack(Item.ingotGold);
		case NETHERIRON:
			return new ItemStack(Item.ingotIron);
		case NETHERLAPIS:
			return ReikaItemHelper.lapisDye.copy();
		case NETHERLEAD:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.LEAD.ordinal());
		case NETHERNICKEL:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.NICKEL.ordinal());
		case NETHERNIKOLITE:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.NIKOLITE.ordinal());
		case NETHERREDSTONE:
			return new ItemStack(Item.redstone);
		case NETHERSILVER:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.SILVER.ordinal());
		case NETHERTIN:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.TIN.ordinal());
		case NETHERPLATINUM:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.PLATINUM.ordinal());
		case NETHERURANIUM:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.URANIUM.ordinal());
		case NETHERIRIDIUM:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.IRIDIUM.ordinal());
		case NETHERSULFUR:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.SULFUR.ordinal());
		case NETHERTITANIUM:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.TITANIUM.ordinal());
		case NETHEROSMIUM:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ModOreList.OSMIUM.ordinal());
		default:
			return new ItemStack(RotaryCraft.modingots.itemID, 1, ore.ordinal());
		}
	}

	public static ModOreList getOreFromExtract(ItemStack item) {
		return ModOreList.oreList[(item.getItemDamage()/4)];
	}

	public static enum ExtractorStage {
		DUST(),
		SLURRY(),
		SOLUTION(),
		FLAKES();
	}
}
