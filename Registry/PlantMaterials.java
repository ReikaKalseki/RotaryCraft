/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum PlantMaterials {

	SUGARCANE(Item.reed, 1),
	TALLGRASS(Block.tallGrass, 2),
	LILYPAD(Block.waterlily, 1),
	SAPLING(Block.sapling, 1),
	ROSE(Block.plantRed, 1),
	FLOWER(Block.plantYellow, 1),
	VINES(Block.vine, 2),
	LEAVES(Block.leaves, 2),
	POTATO(Item.potato, 1);

	private ItemStack item;
	private int multiplier;

	public static final PlantMaterials[] plantList = PlantMaterials.values();

	private PlantMaterials(Item i, int num) {
		item = new ItemStack(i);
		multiplier = num;
	}

	private PlantMaterials(Block i, int num) {
		item = new ItemStack(i);
		multiplier = num;
	}

	public static boolean isValidPlant(ItemStack is) {
		if (is == null)
			return false;
		for (int i = 1; i < plantList.length; i++) {
			if (plantList[i].item.itemID == is.itemID)
				return true;
		}
		return false;
	}

	public static PlantMaterials getPlantEntry(ItemStack is) {
		if (is == null)
			return null;
		for (int i = 0; i < plantList.length; i++) {
			if (plantList[i].item.itemID == is.itemID)
				return plantList[i];
		}
		return null;
	}

	public int getPlantValue() {
		return multiplier;
	}

	public ItemStack getPlantItem() {
		return item.copy();
	}

	public ItemStack getPlantItemForIcon() {
		if (this == TALLGRASS)
			return new ItemStack(item.itemID, 1, 1);
		return item.copy();
	}

}
