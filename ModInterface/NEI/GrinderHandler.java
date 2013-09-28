/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.Auxiliary.RecipesGrinder;
import Reika.RotaryCraft.GUIs.GuiGrinder;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class GrinderHandler extends TemplateRecipeHandler {

	public class GrinderRecipe extends CachedRecipe {

		private ItemStack input;
		private ItemStack output;


		public GrinderRecipe(ItemStack in) {
			input = in;
			output = RecipesGrinder.getRecipes().getSmeltingResult(in.itemID);
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(output, 131, 24);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(input, 71, 24);
		}
	}

	@Override
	public String getRecipeName() {
		return "Grinder";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/grindergui.png";
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (RecipesGrinder.getRecipes().isProduct(result)) {
			arecipes.add(new GrinderRecipe(RecipesGrinder.getRecipes().getSources(result)));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesGrinder.getRecipes().isGrindable(ingredient)) {
			arecipes.add(new GrinderRecipe(ingredient));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiGrinder.class;
	}

}
