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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Fillable;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFillingStation;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FillingStationHandler extends TemplateRecipeHandler {

	public class FillingStationRecipe extends CachedRecipe {

		private final Fluid fluid;
		private final ItemStack item;

		public FillingStationRecipe(ItemStack is, Fluid f) {
			fluid = f;
			item = is;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(item, 101, 60);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return null;
		}
	}

	@Override
	public String getRecipeName() {
		return "Filling Station";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/fillingstationgui.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		drawTexturedModalRect(0, 0, 5, 11, 166, 70);
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
		if (result.getItem() instanceof Fillable) {
			Fillable item = (Fillable)result.getItem();
			Fluid f = item.getCurrentFluid(result);
			if (f != null)
				arecipes.add(new FillingStationRecipe(result, f));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ingredient.getItem() instanceof Fillable) {
			Fillable item = (Fillable)ingredient.getItem();
			Fluid f = item.getCurrentFluid(ingredient);
			if (f == null) {
				for (String name : FluidRegistry.getRegisteredFluids().keySet()) {
					Fluid fluid = FluidRegistry.getFluid(name);
					if (fluid != null && item.isValidFluid(fluid, ingredient)) {
						ItemStack is = ingredient.copy();
						arecipes.add(new FillingStationRecipe(is, fluid));
					}
				}
			}
		}
	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFillingStation.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawTexturedModalRect(134, 7, 177, 45, 6, 50);
		this.drawFluids(recipe);
	}

	private void drawFluids(int recipe) {
		FillingStationRecipe r = (FillingStationRecipe)arecipes.get(recipe);
		Fluid f = r.fluid;
		if (f != null) {
			Icon ico = f.getIcon();
			float u = ico.getMinU();
			float v = ico.getMinV();
			float du = ico.getMaxU();
			float dv = ico.getMaxV();
			ReikaTextureHelper.bindTerrainTexture();
			Tessellator v5 = Tessellator.instance;
			v5.startDrawingQuads();
			for (int i = 0; i < 4; i++) {
				int y = 10+i*16;
				v5.addVertexWithUV(77, y, 0, u, v);
				v5.addVertexWithUV(77, y+16, 0, u, dv);
				v5.addVertexWithUV(89, y+16, 0, du, dv);
				v5.addVertexWithUV(89, y, 0, du, v);
			}
			v5.draw();
			int ox = 130;
			int oy = 53;
			if (ReikaGuiAPI.instance.isMouseInBox(ox+76, ox+90, oy+9, oy+75)) {
				int mx = ReikaGuiAPI.instance.getMouseRealX();
				int my = ReikaGuiAPI.instance.getMouseRealY();
				ReikaGuiAPI.instance.drawTooltipAt(Minecraft.getMinecraft().fontRenderer, f.getLocalizedName(), mx-ox, my-oy);
			}
		}
	}

}
