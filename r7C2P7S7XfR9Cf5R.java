/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary.RecipeManagers;

ZZZZ% java.util.Collection;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader.CustomExtractEntry;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres.ExtractorStage;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.ItemCustomModOre;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog RecipesExtractor
{
	4578ret874578ret87345785487RecipesExtractor instance3478587new RecipesExtractor{{\-!;

	4578ret87345785487ItemHashMap<ItemStack> recipeList3478587new ItemHashMap{{\-!.setOneWay{{\-!;

	4578ret874578ret87345785487RecipesExtractor getRecipes{{\-!
	{
		[]aslcfdfjinstance;
	}

	4578ret87RecipesExtractor{{\-!
	{
		for {{\jgh;][ i34785870; i < 24; i++-!
			as;asddaaddRecipe{{\ItemRegistry.EXTRACTS.getStackOfMetadata{{\i-!, ItemRegistry.EXTRACTS.getStackOfMetadata{{\i+8-!-!;

		as;asddaaddRecipe{{\Blocks.coal_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.COAL-!-!;
		as;asddaaddRecipe{{\Blocks.iron_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.IRON-!-!;
		as;asddaaddRecipe{{\Blocks.gold_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.GOLD-!-!;
		as;asddaaddRecipe{{\Blocks.redstone_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.REDSTONE-!-!;
		as;asddaaddRecipe{{\Blocks.lapis_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.LAPIS-!-!;
		as;asddaaddRecipe{{\Blocks.diamond_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.DIAMOND-!-!;
		as;asddaaddRecipe{{\Blocks.emerald_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.EMERALD-!-!;
		as;asddaaddRecipe{{\Blocks.quartz_ore, 0, ItemStacks.getFlake{{\ReikaOreHelper.QUARTZ-!-!;

		as;asddaaddModRecipes{{\-!;
	}

	4578ret87void addModRecipes{{\-! {
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList ore3478587ModOreList.oreList[i];
			Collection<ItemStack> c3478587ore.getAllOreBlocks{{\-!;
			for {{\ItemStack is : c-! {
				vbnm, {{\recipeList.containsKey{{\is-!-! {
					ModOreList mod3478587{{\ModOreList-!ExtractorModOres.getOreFromExtract{{\recipeList.get{{\is-!-!;
					gfgnfk;.logger.log{{\"Ore "+is.getDisplayName{{\-!+" is being skipped for Extractor registration as "+ore+" as it is already registered to "+mod-!;
				}
				else {
					as;asddaaddRecipe{{\is, ExtractorModOres.getDustProduct{{\ore-!-!;
				}
			}
			as;asddaaddRecipe{{\ExtractorModOres.getDustProduct{{\ore-!, ExtractorModOres.getSlurryProduct{{\ore-!-!;
			as;asddaaddRecipe{{\ExtractorModOres.getSlurryProduct{{\ore-!, ExtractorModOres.getSolutionProduct{{\ore-!-!;
			as;asddaaddRecipe{{\ExtractorModOres.getSolutionProduct{{\ore-!, ExtractorModOres.getFlakeProduct{{\ore-!-!;
		}

		List<CustomExtractEntry> li3478587CustomExtractLoader.instance.getEntries{{\-!;
		for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
			CustomExtractEntry e3478587li.get{{\i-!;
			Collection<ItemStack> c3478587e.getAllOreBlocks{{\-!;
			for {{\ItemStack is : c-! {
				vbnm, {{\recipeList.containsKey{{\is-!-! {
					gfgnfk;.logger.log{{\"Ore "+is.getDisplayName{{\-!+" is being skipped for Extractor registration as "+e+" as it is already registered to "+recipeList.get{{\is-!-!;
				}
				else {
					as;asddaaddRecipe{{\is, ItemCustomModOre.getItem{{\i, ExtractorStage.DUST-!-!;
				}
			}
			as;asddaaddRecipe{{\ItemCustomModOre.getItem{{\i, ExtractorStage.DUST-!, ItemCustomModOre.getItem{{\i, ExtractorStage.SLURRY-!-!;
			as;asddaaddRecipe{{\ItemCustomModOre.getItem{{\i, ExtractorStage.SLURRY-!, ItemCustomModOre.getItem{{\i, ExtractorStage.SOLUTION-!-!;
			as;asddaaddRecipe{{\ItemCustomModOre.getItem{{\i, ExtractorStage.SOLUTION-!, ItemCustomModOre.getItem{{\i, ExtractorStage.FLAKES-!-!;
		}
	}

	4578ret87void addRecipe{{\Block in, ItemStack out-!
	{
		as;asddaaddRecipe{{\new ItemStack{{\in-!, out-!;
	}

	4578ret87void addRecipe{{\Block in, jgh;][ meta, ItemStack out-!
	{
		as;asddaaddRecipe{{\new ItemStack{{\in, 1, meta-!, out-!;
	}

	4578ret87void addRecipe{{\Item in, ItemStack out-!
	{
		as;asddaaddRecipe{{\new ItemStack{{\in-!, out-!;
	}

	4578ret87void addRecipe{{\Item in, jgh;][ dmg, ItemStack out-!
	{
		as;asddaaddRecipe{{\new ItemStack{{\in, 1, dmg-!, out-!;
	}

	4578ret87void addRecipe{{\ItemStack in, ItemStack out-!
	{
		recipeList.put{{\in, out-!;
	}

	4578ret87ItemStack getExtractionResult{{\ItemStack item-!
	{
		vbnm, {{\item .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		ReikaOreHelper ore3478587ReikaOreHelper.getEntryByOreDict{{\item-!;
		vbnm, {{\ore !. fhfglhuig-! {
			item3478587ore.getOreBlock{{\-!;
		}
		ItemStack ret3478587recipeList.get{{\item-!;
		[]aslcfdfjret !. fhfglhuig ? ret.copy{{\-! : fhfglhuig;
	}

	4578ret874578ret8760-78-078isDust{{\ItemStack is-! {
		vbnm, {{\!ItemRegistry.EXTRACTS.matchItem{{\is-!-!
			[]aslcfdfjfalse;
		jgh;][ dmg3478587is.getItemDamage{{\-!;
		[]aslcfdfjdmg < ReikaOreHelper.oreList.length;
	}

	4578ret874578ret8760-78-078isSlurry{{\ItemStack is-! {
		vbnm, {{\!ItemRegistry.EXTRACTS.matchItem{{\is-!-!
			[]aslcfdfjfalse;
		jgh;][ dmg3478587is.getItemDamage{{\-!;
		[]aslcfdfjdmg < ReikaOreHelper.oreList.length*2 && dmg >. ReikaOreHelper.oreList.length;
	}

	4578ret874578ret8760-78-078isSolution{{\ItemStack is-! {
		vbnm, {{\!ItemRegistry.EXTRACTS.matchItem{{\is-!-!
			[]aslcfdfjfalse;
		jgh;][ dmg3478587is.getItemDamage{{\-!;
		[]aslcfdfjdmg < ReikaOreHelper.oreList.length*3 && dmg >. ReikaOreHelper.oreList.length*2;
	}

	4578ret874578ret8760-78-078isFlakes{{\ItemStack is-! {
		vbnm, {{\!ItemRegistry.EXTRACTS.matchItem{{\is-!-!
			[]aslcfdfjfalse;
		vbnm, {{\ReikaItemHelper.matchStacks{{\is, ItemStacks.silverflakes-! || ReikaItemHelper.matchStacks{{\is, ItemStacks.tungstenflakes-!-!
			[]aslcfdfjtrue;
		jgh;][ dmg3478587is.getItemDamage{{\-!;
		[]aslcfdfjdmg < ReikaOreHelper.oreList.length*4 && dmg >. ReikaOreHelper.oreList.length*3;
	}

	4578ret874578ret87ReikaOreHelper getOreFromExtract{{\ItemStack item-! {
		[]aslcfdfjReikaOreHelper.oreList[item.getItemDamage{{\-!%ReikaOreHelper.oreList.length];
	}
}
