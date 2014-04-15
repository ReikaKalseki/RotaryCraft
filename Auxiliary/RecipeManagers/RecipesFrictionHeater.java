/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesFrictionHeater {

	private static final RecipesFrictionHeater instance = new RecipesFrictionHeater();

	private final HashMap<List<Integer>, FrictionRecipe> recipes = new HashMap();
	private final HashMap<List<Integer>, FrictionRecipe> outputs = new HashMap();

	public static RecipesFrictionHeater getRecipes() {
		return instance;
	}

	private RecipesFrictionHeater() {
		this.addRecipe(ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1350);
	}

	private void addRecipe(ItemStack in, ItemStack out, int temp) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp);
		recipes.put(Arrays.asList(in.itemID, in.getItemDamage()), rec);
		outputs.put(Arrays.asList(out.itemID, out.getItemDamage()), rec);
	}

	public ItemStack getSmelting(ItemStack in, int temperature) {
		FrictionRecipe rec = recipes.get(Arrays.asList(in.itemID, in.getItemDamage()));
		if (rec == null)
			return null;
		return temperature >= rec.requiredTemperature ? rec.getOutput() : null;
	}

	public FrictionRecipe getRecipeByOutput(ItemStack out) {
		return outputs.get(Arrays.asList(out.itemID, out.getItemDamage()));
	}

	public FrictionRecipe getRecipeByInput(ItemStack in) {
		return recipes.get(Arrays.asList(in.itemID, in.getItemDamage()));
	}

	public static class FrictionRecipe {

		public final int requiredTemperature;
		private final ItemStack input;
		private final ItemStack output;

		private FrictionRecipe(ItemStack in, ItemStack out, int temp) {
			requiredTemperature = temp;
			input = in;
			output = out;
		}

		public ItemStack getInput() {
			return input.copy();
		}

		public ItemStack getOutput() {
			return output.copy();
		}
	}

}
