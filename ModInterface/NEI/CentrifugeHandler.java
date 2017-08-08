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

import Reika.DragonAPI.Instantiable.Data.Collections.ChancedOutputList;
import Reika.DragonAPI.Instantiable.ModInteract.PositionedStackWithTooltip;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesCentrifuge;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiCentrifuge;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CentrifugeHandler extends TemplateRecipeHandler {

	public class CentrifugeRecipe extends CachedRecipe {

		private ItemStack input;

		private CentrifugeRecipe(ItemStack in) {
			input = in;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}

		@Override
		public List<PositionedStack> getOtherStacks()
		{
			List<PositionedStack> pos = new ArrayList();
			ChancedOutputList c = RecipesCentrifuge.getRecipes().getRecipeResult(input);
			Collection<ItemStack> li = c.keySet();

			int dx = 80;
			int dy = 10;
			int i = 0;
			for (ItemStack is : li) {
				int x = dx+i%3*18;
				int y = dy+i/3*18;
				String tip = String.format("%.2f%s chance", c.getItemChance(is), "%");
				pos.add(new PositionedStackWithTooltip(is, x, y, tip));
				i++;
			}
			return pos;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(input, 1), 21, 28);
		}
	}

	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe) {
		if (stack != null) {
			for (PositionedStack is : ((CentrifugeRecipe)arecipes.get(recipe)).getOtherStacks()) {
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
				arecipes.add(new CentrifugeRecipe(is));
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
			for (int i = 0; i < li.size(); i++)
				arecipes.add(new CentrifugeRecipe(li.get(i)));
		}

		ArrayList<ItemStack> li = RecipesCentrifuge.getRecipes().getSources(result);
		for (int i = 0; i < li.size(); i++)
			arecipes.add(new CentrifugeRecipe(li.get(i)));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesCentrifuge.getRecipes().isCentrifugable(ingredient)) {
			arecipes.add(new CentrifugeRecipe(ingredient));
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
		CentrifugeRecipe r = (CentrifugeRecipe)arecipes.get(recipe);
		ItemStack in = r.input;
		FluidStack fs = RecipesCentrifuge.getRecipes().getFluidResult(in);
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
			String s = f.getLocalizedName()+" ("+fs.amount+" mB) ("+RecipesCentrifuge.getRecipes().getFluidChance(in)+"%)";
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
