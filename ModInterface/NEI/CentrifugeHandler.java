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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList.ItemWithChance;
import Reika.DragonAPI.Instantiable.ModInteract.PositionedStackWithTooltip;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Rendering.ReikaLiquidRenderer;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge.CentrifugeRecipe;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCentrifuge;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CentrifugeHandler extends TemplateRecipeHandler {

	public class CentrifugeNEIRecipe extends CachedRecipe {

		private CentrifugeRecipe recipe;

		private CentrifugeNEIRecipe(CentrifugeRecipe in) {
			recipe = in;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> pos = new ArrayList();

			int num = 1+(int)((System.currentTimeMillis()/1000)%recipe.maxStack);

			int dx = 80;
			int dy = 10;
			int i = 0;
			for (ItemWithChance is : recipe.getItems()) {
				int x = dx+i%3*18;
				int y = dy+i/3*18;
				String tip = String.format("%.2f%s chance", is.getChance(), "%");
				pos.add(new PositionedStackWithTooltip(ReikaItemHelper.getSizedItemStack(is.getItem(), num), x, y, tip));
				i++;
			}
			return pos;
		}

		@Override
		public PositionedStack getIngredient() {
			int num = 1+(int)((System.currentTimeMillis()/1000)%recipe.maxStack);
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(recipe.getInput(), num), 21, 28);
		}
	}

	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe) {
		if (stack != null) {
			for (PositionedStack is : ((CentrifugeNEIRecipe)arecipes.get(recipe)).getOtherStacks()) {
				if (is instanceof PositionedStackWithTooltip && gui.isMouseOver(is, recipe))
					currenttip.add(((PositionedStackWithTooltip)is).tooltip);
			}
		}
		return super.handleTooltip(gui, currenttip, recipe);
	}

	@Override
	public String getRecipeName() {
		return "Centrifuge";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/centrifugegui.png";
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
		transferRects.add(new RecipeTransferRect(new Rectangle(39, 16, 40, 40), "rccentri"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rccentri")) {
			Collection<ItemStack> li = RecipesCentrifuge.getRecipes().getAllCentrifugables();
			for (ItemStack is : li)
				arecipes.add(new CentrifugeNEIRecipe(RecipesCentrifuge.getRecipes().getRecipeResult(is)));
		}
		else if (outputId != null && outputId.equals("liquid")) {
			FluidStack fs = (FluidStack)results[0];
			ArrayList<ItemStack> li = RecipesCentrifuge.getRecipes().getSources(fs.getFluid());
			for (ItemStack is : li)
				arecipes.add(new CentrifugeNEIRecipe(RecipesCentrifuge.getRecipes().getRecipeResult(is)));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rccentri")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(result);
		if (fs != null) {
			ArrayList<ItemStack> li = RecipesCentrifuge.getRecipes().getSources(fs.getFluid());
			for (ItemStack is : li)
				arecipes.add(new CentrifugeNEIRecipe(RecipesCentrifuge.getRecipes().getRecipeResult(is)));
		}

		ArrayList<ItemStack> li = RecipesCentrifuge.getRecipes().getSources(result);
		for (ItemStack is : li)
			arecipes.add(new CentrifugeNEIRecipe(RecipesCentrifuge.getRecipes().getRecipeResult(is)));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesCentrifuge.getRecipes().isCentrifugable(ingredient)) {
			arecipes.add(new CentrifugeNEIRecipe(RecipesCentrifuge.getRecipes().getRecipeResult(ingredient)));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiCentrifuge.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		this.drawFluids(recipe);
	}

	private void drawFluids(int recipe) {
		CentrifugeNEIRecipe r = (CentrifugeNEIRecipe)arecipes.get(recipe);
		FluidStack fs = r.recipe.getFluid();
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
			int x = 147;
			for (int i = 0; i < 4; i++) {
				int y = 1+i*16;
				v5.addVertexWithUV(x, y, 0, u, v);
				v5.addVertexWithUV(x, y+16, 0, u, dv);
				v5.addVertexWithUV(x+16, y+16, 0, du, dv);
				v5.addVertexWithUV(x+16, y, 0, du, v);
			}
			v5.addVertexWithUV(x, 65, 0, u, v);
			v5.addVertexWithUV(x, 68, 0, u, dv);
			v5.addVertexWithUV(x+16, 68, 0, du, dv);
			v5.addVertexWithUV(x+16, 65, 0, du, v);
			v5.draw();
			FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
			String s = f.getLocalizedName()+" ("+fs.amount+" mB) ("+r.recipe.getFluidChance()+"%)";
			int l = fr.getStringWidth(s);
			fr.drawString(s, 166-l, 70, 0);
		}
	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

}
