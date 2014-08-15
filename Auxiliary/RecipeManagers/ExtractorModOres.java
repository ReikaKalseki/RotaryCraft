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

import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Registry.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExtractorModOres {

	public static void registerRCIngots() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			String[] ingots = ModOreList.oreList[i].getOreDictIngots();
			for (int j = 0; j < ingots.length; j++) {
				OreDictionary.registerOre(ingots[j], ItemRegistry.MODINGOTS.getStackOfMetadata(i));
			}
		}
	}

	public static void addSmelting() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ReikaRecipeHelper.addSmelting(ItemRegistry.MODEXTRACTS.getStackOfMetadata(getFlakesIndex(ModOreList.oreList[i])), ReikaItemHelper.getSizedItemStack(getSmeltedIngot(ModOreList.oreList[i]), ModOreList.oreList[i].getDropCount()), 0.7F);
		}
	}

	public static int getSpritesheet(ModOreList ore) {
		return ore.ordinal()/64;
	}

	public static boolean isModOreIngredient(ItemStack is) {
		if (is == null)
			return false;
		if (!ItemRegistry.MODEXTRACTS.matchItem(is))
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
		return ItemRegistry.MODEXTRACTS.getStackOfMetadata(getDustIndex(ore));
	}

	public static ItemStack getSlurryProduct(ModOreList ore) {
		return ItemRegistry.MODEXTRACTS.getStackOfMetadata(getSlurryIndex(ore));
	}

	public static ItemStack getSolutionProduct(ModOreList ore) {
		return ItemRegistry.MODEXTRACTS.getStackOfMetadata(getSolutionIndex(ore));
	}

	public static ItemStack getFlakeProduct(ModOreList ore) {
		return ItemRegistry.MODEXTRACTS.getStackOfMetadata(getFlakesIndex(ore));
	}

	public static ItemStack getSmeltedIngot(ModOreList ore) {
		switch(ore) {
		case NETHERCOAL:
			return new ItemStack(Items.coal);
		case NETHERCOPPER:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.COPPER.ordinal());
		case NETHERDIAMOND:
			return new ItemStack(Items.diamond);
		case NETHEREMERALD:
			return new ItemStack(Items.emerald);
		case NETHERGOLD:
			return new ItemStack(Items.gold_ingot);
		case NETHERIRON:
			return new ItemStack(Items.iron_ingot);
		case NETHERLAPIS:
			return ReikaItemHelper.lapisDye.copy();
		case NETHERLEAD:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.LEAD.ordinal());
		case NETHERNICKEL:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.NICKEL.ordinal());
		case NETHERNIKOLITE:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.NIKOLITE.ordinal());
		case NETHERREDSTONE:
			return new ItemStack(Items.redstone);
		case NETHERSILVER:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.SILVER.ordinal());
		case NETHERTIN:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.TIN.ordinal());
		case NETHERPLATINUM:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.PLATINUM.ordinal());
		case NETHERURANIUM:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.URANIUM.ordinal());
		case NETHERIRIDIUM:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.IRIDIUM.ordinal());
		case NETHERSULFUR:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.SULFUR.ordinal());
		case NETHERTITANIUM:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.TITANIUM.ordinal());
		case NETHEROSMIUM:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.OSMIUM.ordinal());
		default:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ore.ordinal());
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