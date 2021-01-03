/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import java.awt.Rectangle;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Rendering.ReikaLiquidRenderer;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesWetter;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesWetter.WettingRecipe;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiWetter;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class WetterHandler extends TemplateRecipeHandler {

	public class WetterRecipe extends CachedRecipe {

		private WettingRecipe recipe;

		private WetterRecipe(WettingRecipe r) {
			recipe = r;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(recipe.getOutput(), 111, 25);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(recipe.getInput(), 39, 25);
		}
	}

	@Override
	public String getRecipeName() {
		return "Liquefaction Machine";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/wettergui.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth(0, 1, 5, 11, 166, 70, ReikaGuiAPI.NEI_DEPTH);
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
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(103, 15, 44, 22), "rcwetter"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcwetter")) {
			Collection<WettingRecipe> li = RecipesWetter.getRecipes().getAllRecipes();
			for (WettingRecipe r : li)
				arecipes.add(new WetterRecipe(r));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcwetter")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Collection<WettingRecipe> c = RecipesWetter.getRecipes().getRecipesByResult(result);
		if (c != null) {
			for (WettingRecipe r : c) {
				arecipes.add(new WetterRecipe(r));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(ingredient);
		if (fs != null) {
			Collection<WettingRecipe> c = RecipesWetter.getRecipes().getRecipesByFluid(fs.getFluid());
			if (c != null) {
				for (WettingRecipe r : c) {
					arecipes.add(new WetterRecipe(r));
				}
			}
		}
		Collection<WettingRecipe> c = RecipesWetter.getRecipes().getRecipesUsing(ingredient);
		if (c != null) {
			for (WettingRecipe r : c) {
				arecipes.add(new WetterRecipe(r));
			}
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiWetter.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		this.drawFluids(recipe);
	}

	private void drawFluids(int recipe) {
		WetterRecipe r = (WetterRecipe)arecipes.get(recipe);
		Fluid f = r.recipe.getFluid().getFluid();
		IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		ReikaTextureHelper.bindTerrainTexture();
		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		int x = 75;
		for (int i = 0; i < 3; i++) {
			int y = 7+i*16;
			v5.addVertexWithUV(x, y, 0, u, v);
			v5.addVertexWithUV(x, y+16, 0, u, dv);
			v5.addVertexWithUV(x+16, y+16, 0, du, dv);
			v5.addVertexWithUV(x+16, y, 0, du, v);
		}
		int y2 = 55;
		int h2 = 4;
		float v2 = v+(dv-v)*h2/16F;
		v5.addVertexWithUV(x, y2, 0, u, v2);
		v5.addVertexWithUV(x, y2+h2, 0, u, v);
		v5.addVertexWithUV(x+16, y2+h2, 0, du, v);
		v5.addVertexWithUV(x+16, y2, 0, du, v2);
		v5.draw();

		String s = r.recipe.getFluid().getLocalizedName();
		int l = Minecraft.getMinecraft().fontRenderer.getStringWidth(s);
		Minecraft.getMinecraft().fontRenderer.drawString(s, x-l-5, y2-4, 0);
		Minecraft.getMinecraft().fontRenderer.drawString(r.recipe.getFluid().amount+" mB", x+20, y2-4, 0);
	}

}
