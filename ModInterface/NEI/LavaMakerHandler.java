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
import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesLavaMaker;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiRockMelter;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class LavaMakerHandler extends TemplateRecipeHandler {

	public class LavaMakerRecipe extends CachedRecipe {

		private ArrayList<ItemStack> input;

		private LavaMakerRecipe(ArrayList<ItemStack> in) {
			input = in;
		}

		private LavaMakerRecipe(ItemStack in) {
			input = new ArrayList();
			input.add(in);
		}

		public ItemStack getInput() {
			int index = (int)((System.currentTimeMillis()/1000)%input.size());
			return input.get(index);
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(this.getInput(), 3, 8);
		}
	}

	@Override
	public String getRecipeName() {
		return "Rock Melter";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/basicstorage.png";
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
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(77, 10, 50, 60), "rcrockmelt"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcrockmelt")) {
			Collection<ItemStack> li = RecipesLavaMaker.getRecipes().getAllRecipes();
			for (ItemStack is : li)
				arecipes.add(new LavaMakerRecipe(is));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcrockmelt")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(result);
		if (fs != null) {
			ArrayList<ItemStack> li = RecipesLavaMaker.getRecipes().getSourceItems(fs.getFluid());
			if (li != null && !li.isEmpty()) {
				arecipes.add(new LavaMakerRecipe(li));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(ingredient);
		if (fs != null) {
			arecipes.add(new LavaMakerRecipe(ingredient));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiRockMelter.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		this.drawFluids(recipe);
		this.drawTemperatures(recipe);
	}

	private void drawTemperatures(int recipe) {
		LavaMakerRecipe r = (LavaMakerRecipe)arecipes.get(recipe);
		ItemStack is = r.getInput();
		if (is != null) {
			FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(is);
			int melt = RecipesLavaMaker.getRecipes().getMeltTemperature(is);
			FontRenderer f = Minecraft.getMinecraft().fontRenderer;
			String s = String.format("%dmB @ %dC", fs.amount, melt);
			f.drawString(s, 165-f.getStringWidth(s), 73, 0);

			s = String.format("%s", fs.getLocalizedName());
			f.drawString(s, 165-f.getStringWidth(s), 83, 0);
		}
	}

	private void drawFluids(int recipe) {
		LavaMakerRecipe r = (LavaMakerRecipe)arecipes.get(recipe);
		ItemStack in = r.getInput();
		FluidStack fs = RecipesLavaMaker.getRecipes().getMelting(in);
		if (fs != null) {
			Fluid f = fs.getFluid();
			IIcon ico = f.getIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			ReikaTextureHelper.bindTerrainTexture();
			Tessellator v5 = Tessellator.instance;
			v5.startDrawingQuads();
			for (int i = 0; i < 4; i++) {
				int x = 147;
				int y = 7+i*16;
				v5.addVertexWithUV(x, y, 0, u, v);
				v5.addVertexWithUV(x, y+16, 0, u, dv);
				v5.addVertexWithUV(x+16, y+16, 0, du, dv);
				v5.addVertexWithUV(x+16, y, 0, du, v);
			}
			v5.draw();
		}
	}

}
