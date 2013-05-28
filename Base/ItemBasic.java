/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.mod_RotaryCraft;

public class ItemBasic extends Item implements IndexedItemSprites {

	protected Random par5Random = new Random();

	private int index;

	public ItemBasic(int itemID, int index) {
		super(itemID);
		maxStackSize = 64;
		this.setCreativeTab(mod_RotaryCraft.tabRotaryItems);
		this.setIndex(index);
	}

	public ItemBasic(int itemID, int index, int max) {
		super(itemID);
		maxStackSize = max;
		if (max == 1);
		hasSubtypes = true;
		this.setCreativeTab(mod_RotaryCraft.tabRotaryItems);
		this.setIndex(index);
	}

	public final void addCreativeItems(ArrayList list) {}

	public static String getTextureFile() {
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png"; //return the block texture where the block texture is saved in
	}

	public int getItemSpriteIndex(ItemStack item) {
		return index;
	}

	public void setIndex(int a) {
		index = a;
	}
}
