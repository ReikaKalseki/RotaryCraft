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
import net.minecraftforge.fluids.FluidStack;

import Reika.DragonAPI.Libraries.ReikaFluidHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Rendering.ReikaLiquidRenderer;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesDryingBed;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesDryingBed.DryingRecipe;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiDryer;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class DryingBedHandler extends TemplateRecipeHandler {

	public class DryingBedRecipe extends CachedRecipe {

		private final DryingRecipe input;

		private DryingBedRecipe(DryingRecipe in) {
			input = in;
		}

		@Override
		public PositionedStack getResult() {
			if (input != null) {
				return new PositionedStack(input.getOutput(), 120, 25);
			}
			return null;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return null;//new PositionedStack(this.getEntry(), 120, 5);
		}
	}

	@Override
	public String getRecipeName() {
		return "Drying Bed";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/drygui.png";
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
		transferRects.add(new RecipeTransferRect(new Rectangle(23, 15, 94, 22), "rcdrying"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcdrying")) {
			Collection<DryingRecipe> li = RecipesDryingBed.getRecipes().getAllRecipes();
			for (DryingRecipe f : li)
				arecipes.add(new DryingBedRecipe(f));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcdrying")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		DryingRecipe f = RecipesDryingBed.getRecipes().getRecipe(result);
		if (f != null) {
			arecipes.add(new DryingBedRecipe(f));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		FluidStack fs = ReikaFluidHelper.getFluidForItem(ingredient);
		if (fs != null) {
			ItemStack is = RecipesDryingBed.getRecipes().getDryingResult(fs);
			if (is != null) {
				arecipes.add(new DryingBedRecipe(RecipesDryingBed.getRecipes().getRecipe(is)));
			}
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiDryer.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		this.drawFluids(recipe);
	}

	private void drawFluids(int recipe) {
		DryingBedRecipe r = (DryingBedRecipe)arecipes.get(recipe);
		FluidStack fs = r.input.getFluid();
		if (fs != null) {
			Fluid f = fs.getFluid();
			IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(f);
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			ReikaTextureHelper.bindTerrainTexture();
			Tessellator v5 = Tessellator.instance;
			v5.startDrawingQuads();
			for (int i = 0; i < 4; i++) {
				int y = 2+i*16;
				v5.addVertexWithUV(3, y, 0, u, v);
				v5.addVertexWithUV(3, y+16, 0, u, dv);
				v5.addVertexWithUV(19, y+16, 0, du, dv);
				v5.addVertexWithUV(19, y, 0, du, v);
			}
			float v2 = v+(dv-v)*5/16F;
			v5.addVertexWithUV(3, 64, 0, u, v2);
			v5.addVertexWithUV(3, 69, 0, u, v);
			v5.addVertexWithUV(19, 69, 0, du, v);
			v5.addVertexWithUV(19, 64, 0, du, v2);
			v5.draw();

			int x = 23;
			int y = 57;
			String s = String.format("%s: %d mB", fs.getLocalizedName(), fs.amount);
			Minecraft.getMinecraft().fontRenderer.drawString(s, x, y, 0);
		}
	}

}
