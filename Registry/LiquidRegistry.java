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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.RegistrationException;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum LiquidRegistry {

	WATER(Item.bucketWater.itemID),
	LAVA(Item.bucketLava.itemID),
	LUBRICANT(ItemStacks.lubebucket.itemID, ItemStacks.lubebucket.getItemDamage()),
	JETFUEL(ItemStacks.fuelbucket.itemID, ItemStacks.fuelbucket.getItemDamage());
	//ETHANOL(ItemStacks.ethanolbucket.itemID, ItemStacks.ethanolbucket.getItemDamage());

	public static final LiquidRegistry[] liquidList = LiquidRegistry.values();

	private int liquidID;
	private int liquidMeta;

	private LiquidRegistry(int id) {
		liquidID = id;
		liquidMeta = -1;
	}

	private LiquidRegistry(int id, int meta) {
		liquidID = id;
		liquidMeta = meta;
	}

	public boolean isMetadata() {
		return liquidMeta > -1;
	}

	public static LiquidRegistry getLiquidFromIDAndMetadata(int id, int meta) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].liquidID == id && (!liquidList[i].isMetadata() || liquidList[i].liquidMeta == meta))
				return liquidList[i];
		}
		throw new RegistrationException(RotaryCraft.instance, "Unregistered liquid ID "+id+" and metadata "+meta+"!");
	}

	public boolean hasBlock() {
		return this == WATER || this == LAVA;
	}

	public int getLiquidBlockID() {
		if (this == WATER)
			return 9;
		if (this == LAVA)
			return 11;
		throw new RegistrationException(RotaryCraft.instance, "Liquid "+this+" is not registered to have a block form and yet was called!");
	}

	public static LiquidRegistry getLiquidFromBlock(int block) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].getLiquidBlockID() == block)
				return liquidList[i];
		}
		throw new RegistrationException(RotaryCraft.instance, "Unregistered liquid for block "+block+"!");
	}

	public static boolean hasLiquid(LiquidRegistry liq, ItemStack[] inv) {
		if (liq.isMetadata())
			return ReikaInventoryHelper.checkForItemStack(liq.liquidID, liq.liquidMeta, inv);
		else
			return ReikaInventoryHelper.checkForItem(liq.liquidID, inv);
	}

	public static boolean isLiquidItem(ItemStack is) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].liquidID == is.itemID && (!liquidList[i].isMetadata() || liquidList[i].liquidMeta == is.getItemDamage()))
				return true;
		}
		return false;
	}

	public String getName() {
		String name = this.name();
		String truename = name.charAt(0)+name.substring(1).toLowerCase();
		return truename;
	}
}
