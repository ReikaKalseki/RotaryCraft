/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Interfaces.OreType;
import Reika.DragonAPI.Interfaces.OreType.OreRarity;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;
import Reika.RotaryCraft.ModInterface.ItemCustomModOre;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class ExtractorModOres {

	public static void registerRCIngots() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			OreDictionary.registerOre(ModOreList.oreList[i].getProductOreDictName(), ItemRegistry.MODINGOTS.getStackOfMetadata(i));
		}
	}

	public static void addSmelting() {
		for (int i = 0; i < ModOreList.oreList.length; i++) {
			ModOreList ore = ModOreList.oreList[i];
			ItemStack in = ItemRegistry.MODEXTRACTS.getStackOfMetadata(getFlakesIndex(ore));
			ItemStack out = ReikaItemHelper.getSizedItemStack(getSmeltedIngot(ore), ore.getDropCount());
			ReikaRecipeHelper.addSmelting(in, out, ore.rarity == OreRarity.RARE ? 1 : ore.rarity == OreRarity.EVERYWHERE ? 0.5F : 0.7F);
		}
	}

	public static void addCustomSmelting() {
		List<CustomExtractEntry> li = CustomExtractLoader.instance.getEntries();
		for (int i = 0; i < li.size(); i++) {
			CustomExtractEntry e = li.get(i);
			ItemStack in = ItemCustomModOre.getItem(i, ExtractorStage.FLAKES);
			OreType nat = e.nativeOre;
			ItemStack out = ItemCustomModOre.getSmeltedItem(i);
			out.stackSize = e.numberSmelted;
			if (nat instanceof ReikaOreHelper) {
				out = ((ReikaOreHelper)nat).getDrop();
				out.stackSize = ((ReikaOreHelper)nat).blockDrops;
			}
			else if (nat instanceof ModOreList) {
				out = getSmeltedIngot((ModOreList)nat);
				out.stackSize = ((ModOreList)nat).dropCount;
			}
			ReikaRecipeHelper.addSmelting(in, out, e.rarity == OreRarity.RARE ? 1 : e.rarity == OreRarity.EVERYWHERE ? 0.5F : 0.7F);
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
		return ExtractorStage.list[is.getItemDamage()%4];
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
		case NETHERSALTPETER:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ModOreList.SALTPETER.ordinal());
		default:
			return ItemRegistry.MODINGOTS.getStackOfMetadata(ore.ordinal());
		}
	}

	public static ModOreList getOreFromExtract(ItemStack item) {
		return ItemRegistry.MODEXTRACTS.matchItem(item) ? ModOreList.oreList[(item.getItemDamage()/4)] : null;
	}

	public static enum ExtractorStage {
		DUST(),
		SLURRY(),
		SOLUTION(),
		FLAKES();

		private static final ExtractorStage[] list = values();
	}
}
