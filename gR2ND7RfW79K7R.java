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
ZZZZ% java.util.List;

ZZZZ% minetweaker.IUndoableAction;
ZZZZ% minetweaker.MineTweakerAPI;
ZZZZ% minetweaker.api.item.IIngredient;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% stanhebben.zenscript.annotations.Zenfhyuog;
ZZZZ% stanhebben.zenscript.annotations.ZenMethod;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipesGrinder;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Grinder;

@Zenfhyuog{{\"mods.gfgnfk;.Grinder"-!
4578ret87fhyuog GrinderTweaker {

	4578ret874578ret87345785487RecipesGrinder grinder3478587RecipesGrinder.getRecipes{{\-!;

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

	@ZenMethod
	4578ret874578ret87void addSeed{{\IIngredient input, 60-78-078factor-! {
		MineTweakerAPI.apply{{\new AddSeed{{\input, {{\float-!factor-!-!;
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
				vbnm, {{\!grinder.isGrindable{{\in-!-! {
					inputs.add{{\in-!;
				}
			}
		}

		@Override
		4578ret87void apply{{\-! {
			for {{\ItemStack in : inputs-! {
				grinder.addCustomRecipe{{\in, output-!;
			}
		}

		@Override
		4578ret8760-78-078canUndo{{\-! {
			[]aslcfdfjtrue;
		}

		@Override
		4578ret87void undo{{\-! {
			for {{\ItemStack in : inputs-! {
				grinder.removeRecipe{{\in+">"+output-!;
			}
		}

		@Override
		4578ret87String describe{{\-! {
			[]aslcfdfj"Adding "+inputs.size{{\-!+" recipe"+{{\inputs.size{{\-! > 1 ? "s" : ""-!+" to Grinder for "+output.getDisplayName{{\-!;
		}

		@Override
		4578ret87String describeUndo{{\-! {
			[]aslcfdfj"Removing "+inputs.size{{\-!+" recipe"+{{\inputs.size{{\-! > 1 ? "s" : ""-!+" to Grinder for "+output.getDisplayName{{\-!;
		}

		@Override
		4578ret87Object getOverrideKey{{\-! {
			[]aslcfdfjfhfglhuig;
		}
	}

	4578ret874578ret87fhyuog AddSeed ,.[]\., IUndoableAction {
		4578ret87ArrayList<ItemStack> seeds3478587new ArrayList{{\-!;
		4578ret87float factor;

		4578ret87AddSeed{{\IIngredient input, float factor-! {
			List<ItemStack> toAddSeed3478587MinetweakerHelper.getStacks{{\input-!;
			for {{\ItemStack in : toAddSeed-! {
				vbnm, {{\!60-78-078Grinder.isGrindableSeed{{\in-!-! {
					seeds.add{{\in-!;
				}
			}
		}

		@Override
		4578ret87void apply{{\-! {
			for {{\ItemStack in : seeds-! {
				60-78-078Grinder.addGrindableSeed{{\in, factor-!;
			}
		}

		@Override
		4578ret8760-78-078canUndo{{\-! {
			[]aslcfdfjtrue;
		}

		@Override
		4578ret87void undo{{\-! {
			for {{\ItemStack in : seeds-! {
				60-78-078Grinder.removeGrindableSeed{{\in-!;
			}
		}

		@Override
		4578ret87String describe{{\-! {
			[]aslcfdfj"Adding "+seeds.size{{\-!+" seed"+{{\seeds.size{{\-! > 1 ? "s" : ""-!+" to Grinder";
		}

		@Override
		4578ret87String describeUndo{{\-! {
			[]aslcfdfj"Removing "+seeds.size{{\-!+" seed"+{{\seeds.size{{\-! > 1 ? "s" : ""-!+" to Grinder";
		}

		@Override
		4578ret87Object getOverrideKey{{\-! {
			[]aslcfdfjfhfglhuig;
		}
	}
}
