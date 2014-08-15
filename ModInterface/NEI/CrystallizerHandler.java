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
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCrystallizer;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCrystallizer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCrystallizer;

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

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CrystallizerHandler extends TemplateRecipeHandler {

	public class CrystallizerRecipe extends CachedRecipe {

		private Fluid input;

		public CrystallizerRecipe(Fluid in) {
			input = in;
		}

		@Override
		public PositionedStack getResult() {
			if (input != null) {
				ItemStack is = RecipesCrystallizer.getRecipes().getFreezingResult(this.getEntry());
				return new PositionedStack(is, 75, 24);
			}
			return null;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return null;//new PositionedStack(this.getEntry(), 120, 5);
		}

		public FluidStack getEntry() {
			return new FluidStack(input, 16000);
		}
	}

	@Override
	public String getRecipeName() {
		return "Fluid Crystallizer";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/crystalgui.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		ReikaGuiAPI.instance.drawTexturedModalRect(0, 1, 5, 11, 166, 70);
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
		Fluid f = RecipesCrystallizer.getRecipes().getRecipe(result);
		if (f != null) {
			arecipes.add(new CrystallizerRecipe(f));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(ingredient);
		if (fs != null) {
			ItemStack is = RecipesCrystallizer.getRecipes().getFreezingResult(fs);
			if (is != null) {
				arecipes.add(new CrystallizerRecipe(fs.getFluid()));
			}
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiCrystallizer.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		this.drawFluids(recipe);
		this.drawTemperatures(recipe);
	}

	private void drawTemperatures(int recipe) {
		CrystallizerRecipe r = (CrystallizerRecipe)arecipes.get(recipe);
		FluidStack fs = r.getEntry();
		if (fs != null) {
			int freeze = TileEntityCrystallizer.getFreezingPoint(fs);
			FontRenderer f = Minecraft.getMinecraft().fontRenderer;
			String s = String.format("%dC", freeze);
			ReikaGuiAPI.instance.drawCenteredStringNoShadow(f, s, 45, 20, 0);
		}
	}

	private void drawFluids(int recipe) {
		CrystallizerRecipe r = (CrystallizerRecipe)arecipes.get(recipe);
		FluidStack fs = r.getEntry();
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
		}
	}

}