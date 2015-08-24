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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

import Reika.DragonAPI.Interfaces.Registry.OreType;
import Reika.DragonAPI.Interfaces.Registry.OreType.OreRarity;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;

/** Use this to add custom ores or extracts to the Extractor processing, for handling custom ore types not natively supported by RC.
 * Be careful to avoid duplicating any entries in {@link ReikaOreHelper} or {@link ModOreList}. */
public class ExtractAPI {

	private static Class loader;
	private static Object loaderInstance;

	private static ArrayList list;


	private static Class entry;
	private static Constructor construct;
	private static Class product;

	/** Adds a custom extract type for use with a custom ore type. Uses the same registry as that which is used in the special config file.
	 * <br><br><br>
	 * Args:<br>
	 * Name - Display name of the extract, eg 'Iron', 'Monazit'. Does not include the 'Ore'.<br><br>
	 * Rarity - Rarity of the ore. See {@link OreRarity} for values.<br><br>
	 * Product Type - Type of product: Ingot/Gem/Dust/'Item' for 'other'. This must be one of the four types here.<br><br>
	 * Product Name - Used to generate the custom final product. Usually the same as the extract display name. Does not include 'Ingot'/'Dust'/etc.<br><br>
	 * Number - Number of items dropped per ore block on average (1 for coal and diamond, 4 for redstone, 2 for ThaumCraft shards...). Controls flake smelting output.<br><br>
	 * C1 - Extract color 1.<br><br>
	 * C2 - Extract color 2.<br><br>
	 * NativeOre - If you want to make the flakes smelt into a natively handled ore like a vanilla ore, or things like copper or certus quartz, supply the
	 * appropriate {@link ReikaOreHelper} or {@link ModOreList} object. Supply null to make a custom product item.<br><br>
	 * OreDict - Any and all Ore Dictionary tags for the ore blocks. This controls what items are recognized to make this extract type.<br>
	 * 
	 * */
	public static void addCustomExtractEntry(String name, OreRarity rarity, String productType, String productName, int number, int c1, int c2, OreType nativeOre, String... oreDict) {
		verify(oreDict);
		try {
			Enum prod = Enum.valueOf(product, productType.toUpperCase());
			Object entry = construct.newInstance(list.size(), name, rarity, prod, productName, number, c1, c2, nativeOre, oreDict);
			list.add(entry);
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Could not add custom extract!");
			e.printStackTrace();
		}
	}

	private static void verify(String[] oreDict) {
		for (String s : oreDict) {
			if (ReikaOreHelper.isVanillaOreType(s) || ModOreList.isModOreType(s))
				throw new IllegalArgumentException("You cannot add natively supported ores!");
		}
	}

	static {
		try {
			loader = Class.forName("Reika.RotaryCraft.Auxiliary.CustomExtractLoader");
			loaderInstance = loader.getField("instance").get(null);
			Field flist = loader.getDeclaredField("data");
			flist.setAccessible(true);
			list = (ArrayList)flist.get(loaderInstance);

			entry = Class.forName("Reika.RotaryCraft.Auxiliary.CustomExtractLoader$CustomExtractEntry");
			product = Class.forName("Reika.RotaryCraft.Auxiliary.CustomExtractLoader$ProductType");
			construct = entry.getDeclaredConstructor(int.class, String.class, OreRarity.class, product, String.class, int.class, int.class, int.class, OreType.class, String[].class);
			construct.setAccessible(true);
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Could not load Extracts API!");
			e.printStackTrace();
		}
	}

}
