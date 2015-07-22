/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Minetweaker;

import java.util.ArrayList;
import java.util.Collection;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;

@ZenClass("mods.rotarycraft.Friction")
public class FrictionTweaker {

	private static final RecipesFrictionHeater recipes = RecipesFrictionHeater.getRecipes();

	@ZenMethod
	public static void addRecipe(IIngredient input, IIngredient output, int temp, int time) {
		ItemStack out = MinetweakerHelper.getStack(output);
		if (isValid(out)) {
			MineTweakerAPI.apply(new AddRecipe(input, output, temp, time));
		}
		else {
			throw new IllegalArgumentException("You cannot add alternate recipes for native RotaryCraft items!");
		}
	}

	private static boolean isValid(ItemStack out) {
		return !out.getItem().getClass().getName().startsWith("Reika.RotaryCraft.Items");
	}

	private static class AddRecipe implements IUndoableAction {
		private ArrayList<ItemStack> inputs = new ArrayList();
		private ItemStack output;
		private int temp;
		private int time;

		public AddRecipe(IIngredient input, IIngredient output, int temp, int time) {
			Collection<ItemStack> toAddRecipe = MinetweakerHelper.getStacks(input);

			this.temp = temp;
			this.time = time;

			for (ItemStack in : toAddRecipe) {
				if (recipes.getRecipeByInput(in) == null) {
					inputs.add(in);
				}
			}
		}

		@Override
		public void apply() {
			for (ItemStack in : inputs) {
				recipes.addCustomRecipe(in, output, temp, time);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (ItemStack in : inputs) {
				recipes.removeRecipe(in+">"+output+"@"+temp+"#"+time);
			}
		}

		@Override
		public String describe() {
			return "Adding "+inputs.size()+" recipe"+(inputs.size() > 1 ? "s" : "")+" to Friction Heater for "+output.getDisplayName()+ " @ "+temp+"C";
		}

		@Override
		public String describeUndo() {
			return "Removing "+inputs.size()+" recipe"+(inputs.size() > 1 ? "s" : "")+" to Friction Heater for "+output.getDisplayName()+ " @ "+temp+"C";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
