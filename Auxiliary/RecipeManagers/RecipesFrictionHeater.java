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

import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

import net.minecraft.item.ItemStack;

public class RecipesFrictionHeater {

	private static final RecipesFrictionHeater instance = new RecipesFrictionHeater();

	private final ItemHashMap<FrictionRecipe> recipes = new ItemHashMap();
	private final ItemHashMap<FrictionRecipe> outputs = new ItemHashMap();

	public static RecipesFrictionHeater getRecipes() {
		return instance;
	}

	private RecipesFrictionHeater() {
		this.addRecipe(ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1350);
	}

	private void addRecipe(ItemStack in, ItemStack out, int temp) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp);
		recipes.put(in, rec);
		outputs.put(out, rec);
	}

	public ItemStack getSmelting(ItemStack in, int temperature) {
		FrictionRecipe rec = recipes.get(in);
		if (rec == null)
			return null;
		return temperature >= rec.requiredTemperature ? rec.getOutput() : null;
	}

	public FrictionRecipe getRecipeByOutput(ItemStack out) {
		return outputs.get(out);
	}

	public FrictionRecipe getRecipeByInput(ItemStack in) {
		return recipes.get(in);
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