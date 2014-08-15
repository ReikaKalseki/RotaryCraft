/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockColorInterface {

	private static final HashMap<ItemStack, Integer> map = new HashMap(2);

	public static void addGPRBlockColor(Block blockID, int color) {
		for (int i = 0; i < 16; i++) {
			addGPRBlockColor(blockID, i, color);
		}
	}

	public static void addGPRBlockColor(Block blockID, int metadata, int color) {
		map.put(new ItemStack(blockID, metadata), color);
	}

	public static void addGPRBlockColor(Block blockID, int metadata, int red, int green, int blue) {
		addGPRBlockColor(blockID, metadata, ReikaColorAPI.RGBtoHex(red, green, blue));
	}

	public static Set<ItemStack> getMappedBlocks() {
		return map.keySet();
	}

	public static int getColor(Block ID, int meta) {
		return map.get(new ItemStack(ID, meta));
	}

}