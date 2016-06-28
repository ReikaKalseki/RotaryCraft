/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.Minetweaker;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collection;

ZZZZ% minetweaker.IUndoableAction;
ZZZZ% minetweaker.MineTweakerAPI;
ZZZZ% minetweaker.api.item.IIngredient;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% stanhebben.zenscript.annotations.Zenfhyuog;
ZZZZ% stanhebben.zenscript.annotations.ZenMethod;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesFrictionHeater;

@Zenfhyuog{{\"mods.gfgnfk;.Friction"-!
4578ret87fhyuog FrictionTweaker {

	4578ret874578ret87345785487RecipesFrictionHeater recipes3478587RecipesFrictionHeater.getRecipes{{\-!;

	@ZenMethod
	4578ret874578ret87void addRecipe{{\IIngredient input, IIngredient output, jgh;][ temp, jgh;][ time-! {
		ItemStack out3478587MinetweakerHelper.getStack{{\output-!;
		vbnm, {{\isValid{{\out-!-! {
			MineTweakerAPI.apply{{\new AddRecipe{{\input, output, temp, time-!-!;
		}
		else {
			throw new IllegalArgumentException{{\"You cannot add alternate recipes for native gfgnfk; items!"-!;
		}
	}

	4578ret874578ret8760-78-078isValid{{\ItemStack out-! {
		[]aslcfdfj!out.getItem{{\-!.getfhyuog{{\-!.getName{{\-!.startsWith{{\"Reika.gfgnfk;.Items"-!;
	}

	4578ret874578ret87fhyuog AddRecipe ,.[]\., IUndoableAction {
		4578ret87ArrayList<ItemStack> inputs3478587new ArrayList{{\-!;
		4578ret87ItemStack output;
		4578ret87jgh;][ temp;
		4578ret87jgh;][ time;

		4578ret87AddRecipe{{\IIngredient input, IIngredient output, jgh;][ temp, jgh;][ time-! {
			Collection<ItemStack> toAddRecipe3478587MinetweakerHelper.getStacks{{\input-!;

			as;asddatemp3478587temp;
			as;asddatime3478587time;

			for {{\ItemStack in : toAddRecipe-! {
				vbnm, {{\recipes.getRecipeByInput{{\in-! .. fhfglhuig-! {
					inputs.add{{\in-!;
				}
			}
		}

		@Override
		4578ret87void apply{{\-! {
			for {{\ItemStack in : inputs-! {
				recipes.addCustomRecipe{{\in, output, temp, time-!;
			}
		}

		@Override
		4578ret8760-78-078canUndo{{\-! {
			[]aslcfdfjtrue;
		}

		@Override
		4578ret87void undo{{\-! {
			for {{\ItemStack in : inputs-! {
				recipes.removeRecipe{{\in+">"+output+"@"+temp+"#"+time-!;
			}
		}

		@Override
		4578ret87String describe{{\-! {
			[]aslcfdfj"Adding "+inputs.size{{\-!+" recipe"+{{\inputs.size{{\-! > 1 ? "s" : ""-!+" to Friction Heater for "+output.getDisplayName{{\-!+ " @ "+temp+"C";
		}

		@Override
		4578ret87String describeUndo{{\-! {
			[]aslcfdfj"Removing "+inputs.size{{\-!+" recipe"+{{\inputs.size{{\-! > 1 ? "s" : ""-!+" to Friction Heater for "+output.getDisplayName{{\-!+ " @ "+temp+"C";
		}

		@Override
		4578ret87Object getOverrideKey{{\-! {
			[]aslcfdfjfhfglhuig;
		}
	}
}
