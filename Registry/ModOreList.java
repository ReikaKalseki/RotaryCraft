/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;

public enum ModOreList {

	TIN("Tin", "ingotTin", 1, "oreTin"),
	COPPER("Copper", "ingotCopper", 1, "oreCopper"),
	LEAD("Lead", "ingotLead", 1, "oreLead"),
	FERROUS("Ferrous", "ingotFerrous", 1, "oreFerrous"),
	SILVER("Silver", "ingotSilver", 1, "oreSilver"),
	GALENA("Galena", "ingotGalena", 1, "oreGalena"),
	ALUMINUM("Aluminum", "ingotAluminum", 1, "oreAluminum", "oreBauxite"),
	IRIDIUM("Iridium", "ingotIridium", 1, "oreIridium"),
	PERIDOT("Peridot", "gemPeridot", 1, "orePeridot"),
	CERTUSQUARTZ("Certus Quartz", "quartz", 3, "oreCertus"),
	URANIUM("Uranium", "ingotUranium", 1, "oreUranium"),
	CINNABAR("Cinnabar", "cinnabar", 1, "oreCinnabar"),
	AMBER("Amber", "amber", 3, "oreAmber"),
	INFUSEDFIRE("Fire Infused", "infusedFire", 4, "oreInfusedFire"),
	INFUSEDAIR("Air Infused", "infusedAir", 4, "oreInfusedAir"),
	INFUSEDWATER("Water Infused", "infusedWater", 4, "oreInfusedWater"),
	INFUSEDDULL("Dull Infused", "infusedDull", 4, "oreInfusedDull"),
	INFUSEDEARTH("Earth Infused", "infusedEarth", 4, "oreInfusedEarth"),
	APATITE("Apatite", "gemApatite", 3, "oreApatite"),
	SALTPETER("Saltpeter", "dustSaltpeter", 2, "oreSaltpeter"),
	TUNGSTEN("Tungsten", "ingotTungsten", 1, "oreTungsten"),
	NIKOLITE("Nikolite", "dustNikolite", 5, "oreNikolite"),
	GREENSAPPHIRE("Green Sapphire", "gemGreenSapphire", 1, "oreGreenSapphire"),
	RUBY("Ruby", "gemRuby", 1, "oreRuby"),
	SAPPHIRE("Sapphire", "gemSapphire", 1, "oreSapphire");

	private ArrayList<ItemStack> ores;
	private String name;
	private String[] oreLabel;
	private int dropCount;
	private String product;

	public static final ModOreList[] oreList = ModOreList.values();

	private ModOreList(String n, String prod, int count, String... ore) {
		oreLabel = new String[ore.length];
		for (int i = 0; i < ore.length; i++) {
			oreLabel[i] = ore[i];
		}
		ores = new ArrayList<ItemStack>();
		for (int i = 0; i < ore.length; i++) {
			ores.addAll(OreDictionary.getOres(ore[i]));
		}
		dropCount = count;
		name = n;
		product = prod;
	}

	public static void registerRCIngots() {
		for (int i = 0; i < oreList.length; i++) {
			OreDictionary.registerOre(oreList[i].product, new ItemStack(RotaryCraft.modingots.itemID, 1, i));
		}
	}

	public static void addSmelting() {
		for (int i = 0; i < oreList.length; i++) {
			FurnaceRecipes.smelting().addSmelting(RotaryCraft.modextracts.itemID, oreList[i].getFlakesIndex(), oreList[i].getSmeltedIngot(), 0.7F);
		}
	}

	public static boolean isModOreIngredient(ItemStack is) {
		if (is == null)
			return false;
		if (is.itemID != RotaryCraft.modextracts.itemID)
			return false;
		return getEntryFromDamage(is) != null;
	}

	public static boolean isModOre(ItemStack is) {
		for (int i = 0; i < oreList.length; i++) {
			if (oreList[i].ores.contains(is))
				return true;
		}
		return false;
	}

	public static ModOreList getEntryFromDamage(ItemStack is) {
		int dmg = is.getItemDamage();
		if (isModOre(is)) {

		}
		if (ReikaMathLibrary.isValueInsideBoundsIncl(0, oreList.length-1, dmg/5))
			return oreList[dmg/5];
		return null;
	}

	public static int getStageFromMetadata(ItemStack is) {
		if (isModOre(is))
			return -1;
		return is.getItemDamage()%4;
	}

	public int getDustIndex() {
		return this.ordinal();
	}

	public int getSlurryIndex() {
		return this.getDustIndex()+1;
	}

	public int getSolutionIndex() {
		return this.getDustIndex()+2;
	}

	public int getFlakesIndex() {
		return this.getDustIndex()+3;
	}

	public boolean isDust(int index) {
		return index == this.getDustIndex();
	}

	public boolean isSlurry(int index) {
		return index == this.getSlurryIndex();
	}

	public boolean isSolution(int index) {
		return index == this.getSolutionIndex();
	}

	public boolean isFlakes(int index) {
		return index == this.getFlakesIndex();
	}

	public ItemStack getDustProduct() {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, this.getDustIndex());
	}

	public ItemStack getSlurryProduct() {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, this.getSlurryIndex());
	}

	public ItemStack getSolutionProduct() {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, this.getSolutionIndex());
	}

	public ItemStack getFlakeProduct() {
		return new ItemStack(RotaryCraft.modextracts.itemID, 1, this.getFlakesIndex());
	}

	public ItemStack getSmeltedIngot() {
		return new ItemStack(RotaryCraft.modingots.itemID, 1, this.ordinal());
	}

	public String[] getOreDictNames() {
		String[] arr = new String[oreLabel.length];
		System.arraycopy(oreLabel, 0, arr, 0, oreLabel.length);
		return arr;
	}

	public String[] getOreDictIngots() {
		return new String[]{product};
	}
}
