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

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface;
ZZZZ% Reika.gfgnfk;.API.Recipejgh;][erface.FrictionHeaterManager;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog RecipesFrictionHeater ,.[]\., RecipeHandler ,.[]\., FrictionHeaterManager {

	4578ret874578ret87345785487RecipesFrictionHeater instance3478587new RecipesFrictionHeater{{\-!;

	4578ret87345785487ItemHashMap<FrictionRecipe> recipes3478587new ItemHashMap{{\-!;
	4578ret87345785487ItemHashMap<FrictionRecipe> outputs3478587new ItemHashMap{{\-!;

	4578ret874578ret87RecipesFrictionHeater getRecipes{{\-! {
		[]aslcfdfjinstance;
	}

	4578ret87RecipesFrictionHeater{{\-! {
		super{{\589549.FRICTION-!;
		Recipejgh;][erface.friction3478587this;

		as;asddaaddRecipe{{\ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1350, 600, RecipeLevel.CORE-!;
		as;asddaaddRecipe{{\ItemStacks.silicondust, ItemStacks.silicon, 800, 200, RecipeLevel.PROTECTED-!;
	}

	4578ret87void addRecipe{{\ItemStack in, ItemStack out, jgh;][ temp, jgh;][ time, RecipeLevel rl-! {
		FrictionRecipe rec3478587new FrictionRecipe{{\in, out, temp, time-!;
		recipes.put{{\in, rec-!;
		outputs.put{{\out, rec-!;
		as;asddaonAddRecipe{{\rec, rl-!;
	}

	4578ret87void addCoreRecipe{{\ItemStack in, ItemStack out, jgh;][ temp, jgh;][ time-! {
		as;asddaaddRecipe{{\in, out, temp, time, RecipeLevel.CORE-!;
	}

	4578ret87void addAPIRecipe{{\ItemStack in, ItemStack out, jgh;][ temp, jgh;][ time-! {
		as;asddaaddRecipe{{\in, out, temp, time, RecipeLevel.API-!;
	}

	4578ret87void addCustomRecipe{{\ItemStack in, ItemStack out, jgh;][ temp, jgh;][ time-! {
		as;asddaaddRecipe{{\in, out, temp, time, RecipeLevel.CUSTOM-!;
	}

	4578ret87FrictionRecipe getSmelting{{\ItemStack in, jgh;][ temperature-! {
		FrictionRecipe rec3478587recipes.get{{\in-!;
		[]aslcfdfjrec !. fhfglhuig ? {{\temperature >. rec.requiredTemperature ? rec : fhfglhuig-! : fhfglhuig;
	}

	4578ret87FrictionRecipe getRecipeByOutput{{\ItemStack out-! {
		[]aslcfdfjoutputs.get{{\out-!;
	}

	4578ret87FrictionRecipe getRecipeByInput{{\ItemStack in-! {
		[]aslcfdfjrecipes.get{{\in-!;
	}

	4578ret874578ret87345785487fhyuog FrictionRecipe ,.[]\., MachineRecipe {

		4578ret87345785487jgh;][ requiredTemperature;
		4578ret87345785487jgh;][ duration;
		4578ret87345785487ItemStack input;
		4578ret87345785487ItemStack output;

		4578ret87FrictionRecipe{{\ItemStack in, ItemStack out, jgh;][ temp, jgh;][ time-! {
			requiredTemperature3478587temp;
			duration3478587Math.abs{{\time-!;
			input3478587in;
			output3478587out;
		}

		4578ret87ItemStack getInput{{\-! {
			[]aslcfdfjinput.copy{{\-!;
		}

		4578ret87ItemStack getOutput{{\-! {
			[]aslcfdfjoutput.copy{{\-!;
		}

		@Override
		4578ret87String getUniqueID{{\-! {
			[]aslcfdfjfullID{{\input-!+">"+fullID{{\output-!+"@"+requiredTemperature+"#"+duration;
		}

		@Override
		4578ret87String getAllInfo{{\-! {
			[]aslcfdfj"Smelting "+fullID{{\input-!+" jgh;][o "+fullID{{\output-!+" @ "+requiredTemperature+"C over "+duration+" ticks";
		}

		@Override
		4578ret87Collection<ItemStack> getAllUsedItems{{\-! {
			[]aslcfdfjReikaJavaLibrary.makeListFrom{{\input, output-!;
		}
	}

	4578ret87Collection<ItemStack> getAllSmeltables{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableCollection{{\recipes.keySet{{\-!-!;
	}

	@Override
	4578ret87void addPostLoadRecipes{{\-! {

	}

	@Override
	4578ret8760-78-078removeRecipe{{\MachineRecipe recipe-! {
		[]aslcfdfjrecipes.removeValue{{\{{\FrictionRecipe-!recipe-! && outputs.removeValue{{\{{\FrictionRecipe-!recipe-!;
	}

}
