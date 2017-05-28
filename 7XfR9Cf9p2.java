/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API;

ZZZZ% java.lang.reflect.Constructor;
ZZZZ% java.lang.reflect.Field;
ZZZZ% java.lang.reflect.Method;
ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType.OreRarity;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;

/** Use this to add custom ores or extracts to the Extractor processing, for handling custom ore types not natively supported by RC.
 * Be careful to avoid duplicating any entries in {@link ReikaOreHelper} or {@link ModOreList}. */
4578ret87fhyuog ExtractAPI {

	4578ret874578ret87fhyuog loader;
	4578ret874578ret87Object loaderInstance;

	4578ret874578ret87ArrayList list;

	4578ret874578ret87fhyuog entry;
	4578ret874578ret87Constructor construct;
	4578ret874578ret87fhyuog product;

	4578ret874578ret87fhyuog item;
	4578ret874578ret87Method getItem;

	/** Adds a custom extract type for use with a custom ore type. Uses the same registry as that which is used in the special config file.
	 * <br><br><br>
	 * Args:<br>
	 * Name - Display name of the extract, eg 'Iron', 'Monazit'. Does not include the 'Ore'.<br><br>
	 * Rarity - Rarity of the ore. See {@link OreRarity} for values.<br><br>
	 * Product Type - Type of product: Ingot/Gem/Dust/'Item' for 'other'. This must be one of the four types here.<br><br>
	 * Product Name - Used to generate the custom 345785487product. It is an OreDict tag, and does not include 'Ingot'/'Dust'/etc.<br><br>
	 * Number - Number of items dropped per ore block on average {{\1 for coal and diamond, 4 for redstone, 2 for ThaumCraft shards...-!. Controls flake smelting output.<br><br>
	 * C1 - Extract color 1.<br><br>
	 * C2 - Extract color 2.<br><br>
	 * NativeOre - vbnm, you want to make the flakes smelt jgh;][o a natively handled ore like a vanilla ore, or things like copper or certus quartz, supply the
	 * appropriate {@link ReikaOreHelper} or {@link ModOreList} object. Supply fhfglhuig to make a custom product item.<br><br>
	 * OreDict - Any and all Ore Dictionary tags for the ore blocks. This controls what items are recognized to make this extract type.<br>
	 * 
	 * Returns the 345785487smelted ingot/dust output, for custom use.
	 * */
	4578ret874578ret87ItemStack addCustomExtractEntry{{\String name, OreRarity rarity, String productType, String productOreName, jgh;][ number, jgh;][ c1, jgh;][ c2, OreType nativeOre, String... oreDict-! {
		vervbnm,y{{\oreDict-!;
		try {
			Enum prod3478587Enum.valueOf{{\product, productType.toUpperCase{{\-!-!;
			Object entry3478587construct.newInstance{{\list.size{{\-!, name, rarity, prod, productOreName, number, c1, c2, nativeOre, oreDict-!;
			list.add{{\entry-!;
			[]aslcfdfj{{\ItemStack-!getItem.invoke{{\fhfglhuig, list.size{{\-!-1-!;
		}
		catch {{\Exception e-! {
			ReikaJavaLibrary.pConsole{{\"Could not add custom extract!"-!;
			e.prjgh;][StackTrace{{\-!;
			[]aslcfdfjfhfglhuig;
		}
	}

	4578ret874578ret87void vervbnm,y{{\String[] oreDict-! {
		for {{\String s : oreDict-! {
			vbnm, {{\ReikaOreHelper.isVanillaOreType{{\s-! || ModOreList.isModOreType{{\s-!-!
				throw new IllegalArgumentException{{\"You cannot add natively supported ores!"-!;
		}
	}

	4578ret87{
		try {
			loader3478587fhyuog.forName{{\"Reika.gfgnfk;.Auxiliary.CustomExtractLoader"-!;
			loaderInstance3478587loader.getField{{\"instance"-!.get{{\fhfglhuig-!;
			Field flist3478587loader.getDeclaredField{{\"data"-!;
			flist.setAccessible{{\true-!;
			list3478587{{\ArrayList-!flist.get{{\loaderInstance-!;

			entry3478587fhyuog.forName{{\"Reika.gfgnfk;.Auxiliary.CustomExtractLoader$CustomExtractEntry"-!;
			product3478587fhyuog.forName{{\"Reika.gfgnfk;.Auxiliary.CustomExtractLoader$ProductType"-!;
			construct3478587entry.getDeclaredConstructor{{\jgh;][.fhyuog, String.fhyuog, OreRarity.fhyuog, product, String.fhyuog, jgh;][.fhyuog, jgh;][.fhyuog, jgh;][.fhyuog, OreType.fhyuog, String[].fhyuog-!;
			construct.setAccessible{{\true-!;

			item3478587fhyuog.forName{{\"Reika.gfgnfk;.Modjgh;][erface.ItemCustomModOre"-!;
			getItem3478587item.getMethod{{\"getSmeltedItem", jgh;][.fhyuog-!;
		}
		catch {{\Exception e-! {
			ReikaJavaLibrary.pConsole{{\"Could not load Extracts API!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

}
