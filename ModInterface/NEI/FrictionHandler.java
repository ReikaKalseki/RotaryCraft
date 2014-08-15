/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater.FrictionRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FrictionHandler extends TemplateRecipeHandler {

	public class FrictionHeaterRecipe extends CachedRecipe {

		private final ItemStack input;
		private final ItemStack output;
		public final int temperature;

		public FrictionHeaterRecipe(ItemStack in) {
			input = in;
			FrictionRecipe rec = RecipesFrictionHeater.getRecipes().getRecipeByInput(in);
			output = rec != null ? rec.getOutput() : null;
			temperature = rec != null ? rec.requiredTemperature : Integer.MIN_VALUE;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(output, 111, 24);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(input, 51, 6);
		}
	}

	@Override
	public String getRecipeName() {
		return "Friction Heater";
	}

	@Override
	public String getGuiTexture() {
		return "textures/gui/container/furnace.png";
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		FrictionRecipe rec = RecipesFrictionHeater.getRecipes().getRecipeByOutput(result);
		if (rec != null) {
			arecipes.add(new FrictionHeaterRecipe(rec.getInput()));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		FrictionRecipe rec = RecipesFrictionHeater.getRecipes().getRecipeByInput(ingredient);
		if (rec != null) {
			arecipes.add(new FrictionHeaterRecipe(ingredient));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFurnace.class;
	}

	@Override
	public void drawExtras(int recipe) {
		FrictionHeaterRecipe rec = (FrictionHeaterRecipe)arecipes.get(recipe);
		String temp = String.format("Requires %dC", rec.temperature);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(Minecraft.getMinecraft().fontRenderer, temp, 83, 65, 0);
	}

}