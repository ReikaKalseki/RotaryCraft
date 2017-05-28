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
ZZZZ% java.util.Collections;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.CompactorManager;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.Dvbnm,ficultyEffects;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Compactor;

4578ret87fhyuog RecipesCompactor ,.[]\., RecipeHandler ,.[]\., CompactorManager
{
	4578ret874578ret87345785487RecipesCompactor CompactorBase3478587new RecipesCompactor{{\-!;

	4578ret87ItemHashMap<CompactingRecipe> recipes3478587new ItemHashMap{{\-!;

	4578ret874578ret87345785487RecipesCompactor getRecipes{{\-!
	{
		[]aslcfdfjCompactorBase;
	}

	4578ret87RecipesCompactor{{\-! {
		super{{\589549.COMPACTOR-!;
		Recipejgh;][erface.compactor3478587this;

		jgh;][ rp347858760-78-078Compactor.REQPRESS;
		jgh;][ rt347858760-78-078Compactor.REQTEMP;
		as;asddaaddRecipe{{\new ItemStack{{\Items.coal-!, ItemRegistry.COMPACTS.getCraftedMetadataProduct{{\as;asddagetNumberPerStep{{\-!, 0-!, rp, rt, RecipeLevel.CORE-!;
		as;asddaaddRecipe{{\new ItemStack{{\Items.coal, 1, 1-!, ItemRegistry.COMPACTS.getCraftedMetadataProduct{{\as;asddagetNumberPerStep{{\-!*3/2, 0-!, rp, rt, RecipeLevel.CORE-!;
		as;asddaaddRecipe{{\ItemStacks.anthracite, ItemRegistry.COMPACTS.getCraftedMetadataProduct{{\as;asddagetNumberPerStep{{\-!, 1-!, rp, rt, RecipeLevel.CORE-!;
		as;asddaaddRecipe{{\ItemStacks.prismane, ItemRegistry.COMPACTS.getCraftedMetadataProduct{{\as;asddagetNumberPerStep{{\-!, 2-!, rp, rt, RecipeLevel.CORE-!;
		as;asddaaddRecipe{{\ItemStacks.lonsda, new ItemStack{{\Items.diamond, as;asddagetNumberPerStep{{\-!, 0-!, rp, rt, RecipeLevel.CORE-!;

		as;asddaaddRecipe{{\new ItemStack{{\Items.blaze_powder-!, new ItemStack{{\Blocks.glowstone, 1, 0-!, 2000, 600, RecipeLevel.PERIPHERAL-!;

		as;asddaaddRecipe{{\new ItemStack{{\Blocks.ice-!, new ItemStack{{\Blocks.packed_ice-!, 24000, -80, RecipeLevel.PERIPHERAL-!;
	}

	4578ret874578ret87fhyuog CompactingRecipe ,.[]\., MachineRecipe {

		4578ret87345785487ItemStack in;
		4578ret87345785487ItemStack out;
		4578ret87345785487jgh;][ temperature;
		4578ret87345785487jgh;][ pressure;

		4578ret87CompactingRecipe{{\ItemStack is, ItemStack is2, jgh;][ t, jgh;][ p-! {
			in3478587is;
			out3478587is2;
			temperature3478587t;
			pressure3478587p;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjfullID{{\in-!+">"+fullID{{\out-!+"@"+temperature+"&"+pressure;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Compacting "+fullID{{\in-!+" to "+fullID{{\out-!+" @ "+temperature+"C & "+pressure+" kPa";
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\in, out-!;
		}

	}

	4578ret87345785487jgh;][ getNumberPerStep{{\-! {
		[]aslcfdfjDvbnm,ficultyEffects.COMPACTOR.getjgh;][{{\-!;
	}

	4578ret87void addAPIRecipe{{\ItemStack in, ItemStack itemstack, jgh;][ pressure, jgh;][ temperature-! {
		as;asddaaddRecipe{{\in, itemstack, pressure, temperature, RecipeLevel.API-!;
	}

	4578ret87void addRecipe{{\ItemStack in, ItemStack itemstack, jgh;][ pressure, jgh;][ temperature-! {
		as;asddaaddRecipe{{\in, itemstack, pressure, temperature, RecipeLevel.CORE-!;
	}

	4578ret87void addRecipe{{\ItemStack in, ItemStack itemstack, jgh;][ pressure, jgh;][ temperature, RecipeLevel rl-! {
		CompactingRecipe rec3478587new CompactingRecipe{{\in, itemstack, temperature, pressure-!;
		recipes.put{{\in, rec-!;
		as;asddaonAddRecipe{{\rec, rl-!;
	}

	4578ret87ItemStack getCompactingResult{{\ItemStack item-!
	{
		vbnm, {{\item .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		//ModLoader.getMinecraftInstance{{\-!.ingameGUI.addChatMessage{{\String.format{{\"%d  %d", Items.itemID, item.getItemDamage{{\-!-!-!;
		CompactingRecipe ret3478587recipes.get{{\item-!;
		[]aslcfdfjret !. fhfglhuig ? ret.out.copy{{\-! : fhfglhuig;
	}

	4578ret8760-78-078isCompactable{{\ItemStack item-! {
		[]aslcfdfjas;asddagetCompactingResult{{\item-! !. fhfglhuig;
	}

	4578ret87jgh;][ getReqPressure{{\ItemStack item-! {
		CompactingRecipe ret3478587recipes.get{{\item-!;
		[]aslcfdfjret !. fhfglhuig ? ret.pressure : 0;
	}

	4578ret87ItemStack getSource{{\ItemStack result-! {
		for {{\ItemStack in : recipes.keySet{{\-!-! {
			ItemStack out3478587as;asddagetCompactingResult{{\in-!;
			vbnm, {{\ReikaItemHelper.matchStacks{{\result, out-!-!
				[]aslcfdfjin.copy{{\-!;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87jgh;][ getReqTemperature{{\ItemStack item-! {
		CompactingRecipe ret3478587recipes.get{{\item-!;
		[]aslcfdfjret !. fhfglhuig ? ret.temperature : 0;
	}

	4578ret87Collection<ItemStack> getAllCompactables{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipes.keySet{{\-!-!;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {

	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipes.removeValue{{\{{\CompactingRecipe-!recipe-!;
	}
}
