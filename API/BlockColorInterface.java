/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
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
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;

/** This is used to add block color codes to the GPR (Ground Penetrating Radar) map. Without doing this, your blocks will be the same
 * shade of purple in the GUI as MCEdit uses for unknown blocks. */
public class BlockColorInterface {

	private static final BlockMap<Integer> map = new BlockMap();
	private static final BlockMap<BlockKey> mimics = new BlockMap();

	public static void addGPRBlockColor(Block blockID, int color) {
		for (int i = 0; i < 16; i++) {
			addGPRBlockColor(blockID, i, color);
		}
	}

	public static void addGPRBlockColor(Block blockID, int metadata, int color) {
		if (!map.containsKey(blockID, metadata))
			map.put(blockID, metadata, color);
	}

	public static void addGPRBlockMimic(Block blockID, int metadata, Block mimic) {
		addGPRBlockMimic(blockID, metadata, new BlockKey(mimic));
	}

	public static void addGPRBlockMimic(Block blockID, int metadata, Block mimic, int mimicM) {
		addGPRBlockMimic(blockID, metadata, new BlockKey(mimic, mimicM));
	}

	public static void addGPRBlockMimic(Block blockID, int metadata, BlockKey mimic) {
		if (!mimics.containsKey(blockID, metadata))
			mimics.put(blockID, metadata, mimic);
	}

	public static void addGPRBlockColor(Block blockID, int metadata, int red, int green, int blue) {
		addGPRBlockColor(blockID, metadata, ReikaColorAPI.RGBtoHex(red, green, blue));
	}

	public static Set<BlockKey> getMappedBlocks() {
		return map.keySet();
	}

	public static Integer getColor(Block ID, int meta) {
		return map.get(ID, meta);
	}

	public static BlockKey getMimic(Block ID, int meta) {
		return mimics.get(ID, meta);
	}

}
