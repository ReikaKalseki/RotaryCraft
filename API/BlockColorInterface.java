/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.util.Set;

import net.minecraft.block.Block;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;

/** This is used to add block color codes to the GPR (Ground Penetrating Radar) map. Without doing this, your blocks will be the same
 * shade of purple in the GUI as MCEdit uses for unknown blocks. */
public class BlockColorInterface {

	private static final BlockMap<Integer> map = new BlockMap();

	public static void addGPRBlockColor(Block blockID, int color) {
		for (int i = 0; i < 16; i++) {
			addGPRBlockColor(blockID, i, color);
		}
	}

	public static void addGPRBlockColor(Block blockID, int metadata, int color) {
		if (!map.containsKey(blockID, metadata))
			map.put(blockID, metadata, color);
	}

	public static void addGPRBlockColor(Block blockID, int metadata, int red, int green, int blue) {
		addGPRBlockColor(blockID, metadata, ReikaColorAPI.RGBtoHex(red, green, blue));
	}

	public static Set<BlockKey> getMappedBlocks() {
		return map.keySet();
	}

	public static int getColor(Block ID, int meta) {
		return map.get(ID, meta);
	}

}
