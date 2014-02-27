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

import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCompactor;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiGrinder;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CompactorHandler extends TemplateRecipeHandler {

	public class CompactorRecipe extends CachedRecipe {

		private ItemStack input;
		private ItemStack output;


		public CompactorRecipe(ItemStack in) {
			input = in;
			output = RecipesCompactor.getRecipes().getCompactingResult(in);
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(output, 75, 35);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(new ItemStack(input.itemID, 1, input.getItemDamage()), 71, 24);
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			PositionedStack stack = this.getIngredient();
			if(stack != null) {
				for (int i = 0; i < 4; i++)
					stacks.add(new PositionedStack(stack.item, 21, 8+18*i));
			}
			return stacks;
		}
	}

	@Override
	public String getRecipeName() {
		return "Compactor";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/compactorgui2.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		int dy = 4;
		drawTexturedModalRect(0, dy, 5, dy, 166, 75);
	}

	@Override
	public void drawForeground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		this.drawExtras(recipe);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		ItemStack is = RecipesCompactor.getRecipes().getSource(result);
		if (is != null)
			arecipes.add(new CompactorRecipe(is));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesCompactor.getRecipes().isCompactable(ingredient)) {
			arecipes.add(new CompactorRecipe(ingredient));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiGrinder.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawTexturedModalRect(142, 26, 176, 37, 4, 44);
		drawTexturedModalRect(112, 25, 181, 41, 11, 46);

		int pressure = this.getPressure(recipe);
		int temperature = this.getTemperature(recipe);
		Minecraft.getMinecraft().fontRenderer.drawString(String.format("Required Pressure: %d kPa", pressure), 0, 85, 0x333333, false);
		Minecraft.getMinecraft().fontRenderer.drawString(String.format("Required Temperature: %dC", temperature), 0, 95, 0x333333, false);
	}

	private int getPressure(int recipe) {
		CompactorRecipe comp = (CompactorRecipe)arecipes.get(recipe);
		return RecipesCompactor.getRecipes().getReqPressure(comp.input);
	}

	private int getTemperature(int recipe) {
		CompactorRecipe comp = (CompactorRecipe)arecipes.get(recipe);
		return RecipesCompactor.getRecipes().getReqTemperature(comp.input);
	}

}
