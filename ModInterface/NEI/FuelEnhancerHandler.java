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
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Strings;

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
import Reika.RotaryCraft.Base.GuiBasicStorage;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityFuelConverter;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityFuelConverter.FuelConversion;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FuelEnhancerHandler extends TemplateRecipeHandler {

	public class ConverterRecipe extends CachedRecipe {

		private final FuelConversion recipe;

		private ConverterRecipe(FuelConversion c) {
			recipe = c;
		}

		@Override
		public PositionedStack getResult() {
			return null;//new PositionedStack(null, 131, 24);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return null;
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			Collection<ItemStack> in = recipe.getIngredientsForDisplay();
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int i = 0;
			for (ItemStack is : in) {
				stacks.add(new PositionedStack(is, 21+i*18, 7));
				i++;
			}
			return stacks;
		}
	}

	@Override
	public String getRecipeName() {
		return "Fuel Enhancer";
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
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth(0, 0, 5, 11, 166, 60, ReikaGuiAPI.NEI_DEPTH);
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
	public void loadCraftingRecipes(ItemStack is) {
		FluidStack fs = ReikaFluidHelper.getFluidForItem(is);
		if (fs != null) {
			Collection<FuelConversion> li = TileEntityFuelConverter.getByOutput(fs.getFluid());
			for (FuelConversion c : li) {
				arecipes.add(new ConverterRecipe(c));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack is) {
		Collection<FuelConversion> li = TileEntityFuelConverter.getByInput(is);
		for (FuelConversion c : li) {
			arecipes.add(new ConverterRecipe(c));
		}
		FluidStack fs = ReikaFluidHelper.getFluidForItem(is);
		if (fs != null) {
			li = TileEntityFuelConverter.getByInput(fs.getFluid());
			for (FuelConversion c : li) {
				arecipes.add(new ConverterRecipe(c));
			}
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(32, -12, 105, 10), "fuelenhance"));
		//transferRects.add(new RecipeTransferRect(new Rectangle(23, 23, 46, 18), "fuelenhance"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("fuelenhance")) {
			Collection<FuelConversion> li = TileEntityFuelConverter.getAllRecipes();
			for (FuelConversion f : li)
				arecipes.add(new ConverterRecipe(f));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("fuelenhance")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiBasicStorage.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		ReikaGuiAPI.instance.drawTexturedModalRect(134, 7, 177, 45, 6, 50);
		ConverterRecipe r = (ConverterRecipe)arecipes.get(recipe);
		Fluid out = r.recipe.output;
		Fluid in = r.recipe.input;

		IIcon ico = ReikaLiquidRenderer.getFluidIconSafe(in);
		float u = ico.getMinU();
		float v = ico.getMinV();
		float du = ico.getMaxU();
		float dv = ico.getMaxV();
		ReikaTextureHelper.bindTerrainTexture();
		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		for (int i = 0; i < 2; i++) {
			int x = 3;
			int y = 7+i*17;
			v5.addVertexWithUV(x, y, 0, u, v);
			v5.addVertexWithUV(x, y+17, 0, u, dv);
			v5.addVertexWithUV(x+16, y+17, 0, du, dv);
			v5.addVertexWithUV(x+16, y, 0, du, v);
		}
		v5.draw();

		ico = ReikaLiquidRenderer.getFluidIconSafe(out);
		u = ico.getMinU();
		v = ico.getMinV();
		du = ico.getMaxU();
		dv = ico.getMaxV();
		v5.startDrawingQuads();
		for (int i = 0; i < 2; i++) {
			int x = 147;
			int y = 7+i*17;
			v5.addVertexWithUV(x, y, 0, u, v);
			v5.addVertexWithUV(x, y+17, 0, u, dv);
			v5.addVertexWithUV(x+16, y+17, 0, du, dv);
			v5.addVertexWithUV(x+16, y, 0, du, v);
		}
		v5.draw();

		ReikaTextureHelper.bindFinalTexture(RotaryCraft.class, "/Reika/RotaryCraft/Textures/GUI/buttons.png");
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth(3, 43, 94, 239, 160, 16, 0);

		GuiContainer gui = (GuiContainer)Minecraft.getMinecraft().currentScreen;
		int i = gui.guiLeft+5;
		int k = gui.guiTop+22+(recipe%2)*65;
		int mx = ReikaGuiAPI.instance.getMouseRealX()-i;
		int my = ReikaGuiAPI.instance.getMouseRealY()-k;
		if (mx > 0 && mx < 176 && my > 0 && my < 54) {
			ArrayList<String> li = new ArrayList();
			if (mx >= 3 && mx <= 19 && my <= 34)
				li.add(in.getLocalizedName(new FluidStack(in, r.recipe.fluidRatio))+" x"+(r.recipe.fluidRatio*r.recipe.speedFactor));
			else if (mx >= 147 && mx <= 147+16 && my <= 34)
				li.add(out.getLocalizedName(new FluidStack(out, 1))+" x"+(r.recipe.speedFactor));
			else if (mx >= 21 && mx <= 145 && my >= 36) {
				li.add(String.format("%.3f%% Item Consumption Chance", r.recipe.itemConsumptionChance*100));
				String s = r.recipe.getCondition();
				if (!Strings.isNullOrEmpty(s)) {
					for (String s2 : s.split("\n"))
						li.add(s2);
				}
			}
			if (!li.isEmpty())
				ReikaGuiAPI.instance.drawMultilineTooltip(li, 99, 23, 18, true);
		}
	}

}
