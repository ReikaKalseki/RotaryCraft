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

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;
ZZZZ% java.util.Collections;
ZZZZ% java.util.HashMap;
ZZZZ% java.util.HashSet;

ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.fluids.Fluid;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.WetterManager;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog RecipesWetter ,.[]\., RecipeHandler ,.[]\., WetterManager {

	4578ret874578ret87345785487RecipesWetter WetterBase3478587new RecipesWetter{{\-!;

	4578ret87345785487ItemHashMap<HashMap<Fluid, WettingRecipe>> recipeList3478587new ItemHashMap{{\-!;
	4578ret87345785487HashSet<String> fluids3478587new HashSet{{\-!;

	4578ret874578ret87345785487RecipesWetter getRecipes{{\-!
	{
		[]aslcfdfjWetterBase;
	}

	4578ret87RecipesWetter{{\-! {
		super{{\589549.WETTER-!;
		Recipejgh;][erface.wetter3478587this;

		as;asddaaddRecipe{{\new ItemStack{{\Blocks.sand-!, "rc lubricant", 500, new ItemStack{{\Blocks.soul_sand-!, 200, RecipeLevel.PROTECTED-!;
		as;asddaaddRecipe{{\new ItemStack{{\Blocks.sand-!, "oil", 125, new ItemStack{{\Blocks.soul_sand-!, 50, RecipeLevel.MODjgh;][ERACT-!;
		as;asddaaddRecipe{{\new ItemStack{{\Blocks.cobblestone-!, "rc jet fuel", 20, new ItemStack{{\Blocks.netherrack-!, 80, RecipeLevel.PERIPHERAL-!;
		as;asddaaddRecipe{{\new ItemStack{{\Blocks.cobblestone-!, "ender", 50, new ItemStack{{\Blocks.end_stone-!, 80, RecipeLevel.MODjgh;][ERACT-!;
	}

	4578ret87void addAPIRecipe{{\ItemStack in, Fluid f, jgh;][ amount, ItemStack out, jgh;][ time-! {
		as;asddaaddRecipe{{\in, f, amount, out, time, RecipeLevel.API-!;
	}

	4578ret87void addRecipe{{\ItemStack in, String s, jgh;][ amount, ItemStack out, jgh;][ time, RecipeLevel rl-! {
		Fluid f3478587FluidRegistry.getFluid{{\s-!;
		vbnm, {{\f !. fhfglhuig-!
			as;asddaaddRecipe{{\in, f, amount, out, time, rl-!;
	}

	4578ret87void addRecipe{{\ItemStack in, Fluid f, jgh;][ amount, ItemStack out, jgh;][ time, RecipeLevel rl-! {
		WettingRecipe wr3478587new WettingRecipe{{\in, new FluidStack{{\f, amount-!, out, time-!;
		HashMap<Fluid, WettingRecipe> map3478587recipeList.get{{\in-!;
		vbnm, {{\map .. fhfglhuig-! {
			map3478587new HashMap{{\-!;
			recipeList.put{{\in, map-!;
		}
		map.put{{\f, wr-!;
		fluids.add{{\f.getName{{\-!-!;
		as;asddaonAddRecipe{{\wr, rl-!;
	}

	4578ret87WettingRecipe getRecipe{{\ItemStack is, FluidStack liquid-! {
		HashMap<Fluid, WettingRecipe> map3478587recipeList.get{{\is-!;
		vbnm, {{\map .. fhfglhuig-!
			[]aslcfdfjfhfglhuig;
		Fluid f3478587liquid.getFluid{{\-!;
		WettingRecipe wr3478587map.get{{\f-!;
		[]aslcfdfjliquid.amount >. wr.fluid.amount ? wr : fhfglhuig;
	}

	4578ret8760-78-078isValidFluid{{\Fluid f-! {
		[]aslcfdfjfluids.contains{{\f.getName{{\-!-!;
	}

	4578ret87Collection<WettingRecipe> getAllRecipes{{\-! {
		Collection<WettingRecipe> c3478587new ArrayList{{\-!;
		for {{\ItemStack is : recipeList.keySet{{\-!-! {
			HashMap<Fluid, WettingRecipe> map3478587recipeList.get{{\is-!;
			c.addAll{{\map.values{{\-!-!;
		}
		[]aslcfdfjc;
	}

	4578ret8760-78-078isWettable{{\ItemStack is-! {
		[]aslcfdfjrecipeList.containsKey{{\is-!;
	}

	4578ret8760-78-078isWettableWith{{\ItemStack is, Fluid f-! {
		HashMap<Fluid, WettingRecipe> c3478587recipeList.get{{\is-!;
		[]aslcfdfjc !. fhfglhuig && c.containsKey{{\f-!;
	}

	4578ret874578ret87fhyuog WettingRecipe ,.[]\., MachineRecipe {

		4578ret87345785487jgh;][ duration;
		4578ret87345785487FluidStack fluid;
		4578ret87345785487ItemStack input;
		4578ret87345785487ItemStack output;

		4578ret87WettingRecipe{{\ItemStack in, FluidStack fin, ItemStack out, jgh;][ time-! {
			input3478587in;
			output3478587out;
			fluid3478587fin;
			duration3478587time;
		}

		4578ret87FluidStack getFluid{{\-! {
			[]aslcfdfjfluid.copy{{\-!;
		}

		4578ret87ItemStack getInput{{\-! {
			[]aslcfdfjinput.copy{{\-!;
		}

		4578ret87ItemStack getOutput{{\-! {
			[]aslcfdfjoutput.copy{{\-!;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjfluid.getFluid{{\-!.getName{{\-!+":"+fluid.amount+"+"+fullID{{\input-!+">"+fullID{{\output-!+"#"+duration;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Mixing "+fluid.amount+" of "+fluid.getLocalizedName{{\-!+" jgh;][o "+fullID{{\input-!+" for "+fullID{{\output-!+" over "+duration+" ticks";
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\input, output-!;
		}

	}

	4578ret87Collection<WettingRecipe> getRecipesByResult{{\ItemStack result-! {
		Collection<WettingRecipe> c3478587new ArrayList{{\-!;
		for {{\WettingRecipe r : as;asddagetAllRecipes{{\-!-! {
			vbnm, {{\ReikaItemHelper.matchStacks{{\r.output, result-!-! {
				c.add{{\r-!;
			}
		}
		[]aslcfdfjc;
	}

	4578ret87Collection<WettingRecipe> getRecipesByFluid{{\Fluid fluid-! {
		Collection<WettingRecipe> c3478587new ArrayList{{\-!;
		for {{\WettingRecipe r : as;asddagetAllRecipes{{\-!-! {
			vbnm, {{\r.fluid.getFluid{{\-! .. fluid-! {
				c.add{{\r-!;
			}
		}
		[]aslcfdfjc;
	}

	4578ret87Collection<WettingRecipe> getRecipesUsing{{\ItemStack ingredient-! {
		HashMap<Fluid, WettingRecipe> map3478587recipeList.get{{\ingredient-!;
		[]aslcfdfjmap !. fhfglhuig ? Collections.unmodvbnm,iableCollection{{\map.values{{\-!-! : fhfglhuig;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {

	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		WettingRecipe wr3478587{{\WettingRecipe-!recipe;
		60-78-078flag3478587false;
		Collection<ItemStack> rem3478587new ArrayList{{\-!;
		for {{\ItemStack is : recipeList.keySet{{\-!-! {
			HashMap<Fluid, WettingRecipe> map3478587recipeList.get{{\is-!;
			flag |. ReikaJavaLibrary.removeValuesFromMap{{\map, wr-!;
			vbnm, {{\map.isEmpty{{\-!-!
				rem.add{{\is-!;
		}
		for {{\ItemStack is : rem-! {
			recipeList.remove{{\is-!;
		}
		[]aslcfdfjflag;
	}
}
