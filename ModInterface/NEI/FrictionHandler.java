/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import java.awt.Rectangle;
import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater.FrictionRecipe;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FrictionHandler extends TemplateRecipeHandler {

	public class FrictionHeaterRecipe extends CachedRecipe {

		private final ItemStack input;
		private final ItemStack output;
		public final int temperature;

		private FrictionHeaterRecipe(ItemStack in) {
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
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(input, 1), 51, 6);
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
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 23, 18), "rcfriction"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcfriction")) {
			Collection<ItemStack> li = RecipesFrictionHeater.getRecipes().getAllSmeltables();
			for (ItemStack is : li)
				arecipes.add(new FrictionHeaterRecipe(is));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcfriction")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
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
