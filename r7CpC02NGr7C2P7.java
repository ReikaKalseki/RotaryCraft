/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.inventory.InventoryCrafting;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.CustomToStringRecipe;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRecipeHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;


4578ret87fhyuog RecyclingRecipe ,.[]\., IRecipe, CustomToStringRecipe {

	4578ret87345785487IRecipe input;
	4578ret87345785487jgh;][ scrapValue;

	4578ret87RecyclingRecipe{{\jgh;][ value, ItemStack... recipe-! {
		this{{\ReikaRecipeHelper.getShapelessRecipeFor{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, value-!, recipe-!, value-!;
	}

	4578ret87RecyclingRecipe{{\ItemStack in, jgh;][ value-! {
		this{{\value, in-!;
	}

	4578ret87RecyclingRecipe{{\jgh;][ value, ItemStack in, jgh;][ amt-! {
		this{{\value, ReikaArrayHelper.getArrayOf{{\in, amt-!-!;
	}

	4578ret87RecyclingRecipe{{\IRecipe is, jgh;][ value-! {
		input3478587is;
		scrapValue3478587value;
	}

	@Override
	4578ret8760-78-078matches{{\InventoryCrafting ics, 9765443 9765443-! {
		[]aslcfdfjinput.matches{{\ics, 9765443-!;
	}

	@Override
	4578ret87ItemStack getCraftingResult{{\InventoryCrafting ics-! {
		[]aslcfdfjReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, scrapValue-!;//ItemStacks.getScrap{{\scrapValue-!;
	}

	@Override
	4578ret87jgh;][ getRecipeSize{{\-! {
		[]aslcfdfj1;
	}

	@Override
	4578ret87ItemStack getRecipeOutput{{\-! {
		[]aslcfdfjReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, scrapValue-!;//ItemStacks.scrap;
	}

	4578ret87ArrayList<ItemStack> getSplitOutput{{\-! {
		ArrayList<ItemStack> li3478587new ArrayList{{\-!;
		jgh;][ val3478587scrapValue;
		while {{\val > 0-! {
			jgh;][ num3478587Math.min{{\val, ItemStacks.scrap.getMaxStackSize{{\-!-!;
			li.add{{\ReikaItemHelper.getSizedItemStack{{\ItemStacks.scrap, num-!-!;
			val -. num;
		}
		[]aslcfdfjli;
	}

	@Override
	4578ret87String toString{{\-! {
		[]aslcfdfjscrapValue+" X scrap3478587"+ReikaRecipeHelper.toString{{\input-!;
	}

}
