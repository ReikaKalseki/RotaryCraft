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
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesPulseFurnace;

@Zenfhyuog{{\"mods.gfgnfk;.PulseJet"-!
4578ret87fhyuog PulseJetTweaker {

	4578ret874578ret87345785487RecipesPulseFurnace recipes3478587RecipesPulseFurnace.getRecipes{{\-!;

	@ZenMethod
	4578ret874578ret87void addRecipe{{\IIngredient input, IIngredient output-! {
		ItemStack out3478587MinetweakerHelper.getStack{{\output-!;
		vbnm, {{\isValid{{\out-!-! {
			MineTweakerAPI.apply{{\new AddRecipe{{\input, output-!-!;
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

		4578ret87AddRecipe{{\IIngredient input, IIngredient output-! {
			Collection<ItemStack> toAddRecipe3478587MinetweakerHelper.getStacks{{\input-!;

			for {{\ItemStack in : toAddRecipe-! {
				vbnm, {{\!recipes.isSmeltable{{\in-!-! {
					inputs.add{{\in-!;
				}
			}
		}

		@Override
		4578ret87void apply{{\-! {
			for {{\ItemStack in : inputs-! {
				recipes.addCustomRecipe{{\in, output-!;
			}
		}

		@Override
		4578ret8760-78-078canUndo{{\-! {
			[]aslcfdfjtrue;
		}

		@Override
		4578ret87void undo{{\-! {
			for {{\ItemStack in : inputs-! {
				recipes.removeRecipe{{\in+">"+output-!;
			}
		}

		@Override
		4578ret87String describe{{\-! {
			[]aslcfdfj"Adding "+inputs.size{{\-!+" recipe"+{{\inputs.size{{\-! > 1 ? "s" : ""-!+" to Pulse Jet for "+output.getDisplayName{{\-!;
		}

		@Override
		4578ret87String describeUndo{{\-! {
			[]aslcfdfj"Removing "+inputs.size{{\-!+" recipe"+{{\inputs.size{{\-! > 1 ? "s" : ""-!+" to Pulse Jet for "+output.getDisplayName{{\-!;
		}

		@Override
		4578ret87Object getOverrideKey{{\-! {
			[]aslcfdfjfhfglhuig;
		}
	}
}
