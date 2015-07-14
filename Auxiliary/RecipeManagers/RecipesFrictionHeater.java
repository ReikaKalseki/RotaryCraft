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
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.FrictionHeaterManager;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesFrictionHeater extends RecipeHandler implements FrictionHeaterManager {

	private static final RecipesFrictionHeater instance = new RecipesFrictionHeater();

	private final ItemHashMap<FrictionRecipe> recipes = new ItemHashMap().setOneWay();
	private final ItemHashMap<FrictionRecipe> outputs = new ItemHashMap().setOneWay();

	private final ItemHashMap<FrictionRecipe> customRecipes = new ItemHashMap().setOneWay();
	private final ItemHashMap<FrictionRecipe> customOutputs = new ItemHashMap().setOneWay();

	public static RecipesFrictionHeater getRecipes() {
		return instance;
	}

	private RecipesFrictionHeater() {
		RecipeInterface.friction = this;

		this.addRecipe(ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1350, 600, RecipeLevel.CORE);
		this.addRecipe(ItemStacks.silicondust, ItemStacks.silicon, 800, 200, RecipeLevel.PROTECTED);
	}

	private void addRecipe(ItemStack in, ItemStack out, int temp, int time, RecipeLevel rl) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp, time);
		recipes.put(in, rec);
		outputs.put(out, rec);
	}

	public void addAPIRecipe(ItemStack in, ItemStack out, int temp, int time) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp, time);
		this.addRecipe(rec, RecipeLevel.API);
	}

	public void addCustomRecipe(ItemStack in, ItemStack out, int temp, int time) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp, time);
		this.addRecipe(rec, RecipeLevel.CUSTOM);
	}

	private void addRecipe(FrictionRecipe rec, RecipeLevel rl) {
		customRecipes.put(rec.input, rec);
		customOutputs.put(rec.output, rec);
	}

	public void removeCustomRecipe(ItemStack in) {
		customOutputs.remove(customRecipes.get(in).output);
		customRecipes.remove(in);
	}

	public FrictionRecipe getSmelting(ItemStack in, int temperature) {
		FrictionRecipe rec = recipes.get(in);
		if (rec == null)
			rec = customRecipes.get(in);
		if (rec == null)
			return null;
		return temperature >= rec.requiredTemperature ? rec : null;
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
		public final int duration;
		private final ItemStack input;
		private final ItemStack output;

		private FrictionRecipe(ItemStack in, ItemStack out, int temp, int time) {
			requiredTemperature = temp;
			duration = Math.abs(time);
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

	@Override
	public void addPostLoadRecipes() {

	}

}
