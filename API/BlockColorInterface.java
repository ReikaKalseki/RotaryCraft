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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;

public class BlockColorInterface {

	private static final HashMap<List<Integer>, Integer> map = new HashMap(2);

	public static void addGPRBlockColor(int blockID, int metadata, int color) {
		map.put(toList(blockID, metadata), color);
	}

	public static void addGPRBlockColor(int blockID, int metadata, int red, int green, int blue) {
		addGPRBlockColor(blockID, metadata, ReikaColorAPI.RGBtoHex(red, green, blue));
	}

	public static Set<List<Integer>> getMappedBlocks() {
		return map.keySet();
	}

	public static int getColor(int ID, int meta) {
		return map.get(toList(ID, meta));
	}

	private static List<Integer> toList(int ID, int meta) {
		List li = new ArrayList();
		li.add(ID);
		li.add(meta);
		return li;
	}

}
