/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Minetweaker;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.item.ItemStack;

import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rotarycraft.PulseJet")
public class PulseJetTweaker {

	private static final RecipesPulseFurnace recipes = RecipesPulseFurnace.getRecipes();

	@ZenMethod
	public static void addRecipe(IIngredient input, IIngredient output) {
		ItemStack out = MinetweakerHelper.getStack(output);
		if (isValid(out)) {
			MineTweakerAPI.apply(new AddRecipe(input, output));
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

		public AddRecipe(IIngredient input, IIngredient output) {
			Collection<ItemStack> toAddRecipe = MinetweakerHelper.getStacks(input);

			for (ItemStack in : toAddRecipe) {
				if (!recipes.isSmeltable(in)) {
					inputs.add(in);
				}
			}
		}

		@Override
		public void apply() {
			for (ItemStack in : inputs) {
				recipes.addCustomRecipe(in, output);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (ItemStack in : inputs) {
				recipes.removeRecipe(in+">"+output);
			}
		}

		@Override
		public String describe() {
			return "Adding "+inputs.size()+" recipe"+(inputs.size() > 1 ? "s" : "")+" to Pulse Jet for "+output.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing "+inputs.size()+" recipe"+(inputs.size() > 1 ? "s" : "")+" to Pulse Jet for "+output.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
