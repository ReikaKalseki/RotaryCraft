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
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryCraft;

public enum ModOreList {

	TIN("Tin", "ingotTin", 1, "oreTin"),
	COPPER("Copper", "ingotCopper", 1, "oreCopper"),
	LEAD("Lead", "ingotLead", 1, "oreLead"),
	GALENA("Galena", "ingotGalena", 1, "oreGalena"),
	SILVER("Silver", "ingotSilver", 1, "oreSilver"),
	ALUMINUM("Aluminum", "ingotAluminum", 1, "oreAluminum", "oreBauxite"),
	IRIDIUM("Iridium", "ingotIridium", 1, "oreIridium"),
	PERIDOT("Peridot", "peridot", 1, "orePeridot"),
	CINNABAR("Cinnabar", "cinnabar", 1, "oreCinnabar"),
	CERTUSQUARTZ("Certus Quartz", "quartz", 3, "oreCertus"),
	AMBER("Amber", "amber", 3, "oreAmber"),
	INFUSEDFIRE("Fire Infused", "infusedFire", 4, "oreInfusedFire"),
	INFUSEDAIR("Air Infused", "infusedAir", 4, "oreInfusedAir"),
	INFUSEDWATER("Water Infused", "infusedWater", 4, "oreInfusedWater"),
	INFUSEDDULL("Dull Infused", "infusedDull", 4, "oreInfusedDull"),
	INFUSEDEARTH("Earth Infused", "infusedEarth", 4, "oreInfusedEarth"),
	APATITE("Apatite", "apatite", 3, "oreApatite"),
	SALTPETER("Saltpeter", "saltpeter", 2, "oreSaltpeter"),
	TUNGSTEN("Tungsten", "ingotTungsten", 1, "oreTungsten"),
	NIKOLITE("Nikolite", "dustNikolite", 5, "oreNikolite"),
	GREENSAPPHIRE("Green Sapphire", "greenSapphire", 1, "oreGreenSapphire"),
	RUBY("Ruby", "ruby", 1, "oreRuby"),
	SAPPHIRE("Sapphire", "sapphire", 1, "oreSapphire");

	private ArrayList<ItemStack> ores;
	private String name;
	private String[] oreLabel;
	private int dropCount;
	private String product;

	private static final ModOreList[] oreList = ModOreList.values();

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
}
