/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesFrictionHeater {

	private static final RecipesFrictionHeater instance = new RecipesFrictionHeater();

	private final ItemHashMap<FrictionRecipe> recipes = new ItemHashMap().setOneWay();
	private final ItemHashMap<FrictionRecipe> outputs = new ItemHashMap().setOneWay();

	private final ItemHashMap<FrictionRecipe> customRecipes = new ItemHashMap().setOneWay();
	private final ItemHashMap<FrictionRecipe> customOutputs = new ItemHashMap().setOneWay();

	public static RecipesFrictionHeater getRecipes() {
		return instance;
	}

	private RecipesFrictionHeater() {
		this.addRecipe(ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1350);
		this.addRecipe(ItemStacks.silicondust, ItemStacks.silicon, 800);
	}

	private void addRecipe(ItemStack in, ItemStack out, int temp) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp);
		recipes.put(in, rec);
		outputs.put(out, rec);
	}

	public void addCustomRecipe(ItemStack in, ItemStack out, int temp) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp);
		customRecipes.put(in, rec);
		customOutputs.put(out, rec);
	}

	public void removeCustomRecipe(ItemStack in) {
		customOutputs.remove(customRecipes.get(in).output);
		customRecipes.remove(in);
	}

	public ItemStack getSmelting(ItemStack in, int temperature) {
		FrictionRecipe rec = recipes.get(in);
		if (rec == null)
			rec = customRecipes.get(in);
		if (rec == null)
			return null;
		return temperature >= rec.requiredTemperature ? rec.getOutput() : null;
	}

	public FrictionRecipe getRecipeByOutput(ItemStack out) {
		FrictionRecipe rec = outputs.get(out);
		if (rec == null)
			rec = customOutputs.get(out);
		return rec;
	}

	public FrictionRecipe getRecipeByInput(ItemStack in) {
		FrictionRecipe rec = recipes.get(in);
		if (rec == null)
			rec = customRecipes.get(in);
		return rec;
	}

	public static final class FrictionRecipe {

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

	public Collection<ItemStack> getAllSmeltables() {
		Collection<ItemStack> li = new ArrayList(recipes.keySet());
		li.addAll(customRecipes.keySet());
		return li;
	}

}
