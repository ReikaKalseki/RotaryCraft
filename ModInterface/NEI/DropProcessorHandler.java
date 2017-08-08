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
import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.ModInteract.PositionedStackWithTooltip;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiDropProcessor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDropProcessor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDropProcessor.DropProcessing;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class DropProcessorHandler extends TemplateRecipeHandler {

	public class DropRecipe extends CachedRecipe {

		private ItemStack fixedResult;
		private DropProcessing recipe;

		private DropRecipe(DropProcessing dp) {
			this(null, dp);
		}

		private DropRecipe(ItemStack is, DropProcessing dp) {
			recipe = dp;
			fixedResult = is;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> pos = new ArrayList();
			List<ItemStack> li = fixedResult != null ? recipe.getOutputsOfInputForDisplay(fixedResult) : new ArrayList(recipe.getPotentialOutputsForDisplay());
			//Collections.shuffle(li);

			int dx = 57;
			int dy = -2;
			int i = 0;
			int w = 6;
			int h = 4;
			Random rand = new Random(System.currentTimeMillis()/2000);
			ArrayList<Integer> indices = ReikaJavaLibrary.makeIntListFromArray(ReikaArrayHelper.getLinearArray(li.size()));
			ArrayList<Integer> indices2 = new ArrayList();
			while (!indices.isEmpty() && indices2.size() < w*h) {
				indices2.add(indices.remove(rand.nextInt(indices.size())));
			}
			for (int idx : indices2) {
				ItemStack is = li.get(idx);
				int x = dx+i%w*18;
				int y = dy+i/w*18;
				String tip = null;
				if (is.stackTagCompound != null && is.stackTagCompound.hasKey("dropTooltip")) {
					tip = is.stackTagCompound.getString("dropTooltip");
					is.stackTagCompound.removeTag("dropTooltip");
				}
				pos.add(tip != null ? new PositionedStackWithTooltip(is, x, y, tip) : new PositionedStack(is, x, y));
				i++;
			}
			return pos;
		}

		@Override
		public PositionedStack getIngredient() {
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(this.getInput(), 1), 5, 25);
		}

		private ItemStack getInput() {
			if (fixedResult != null)
				return fixedResult;
			ArrayList<ItemStack> c = recipe.getAllInputsForDisplay();
			int idx = (int)((System.currentTimeMillis()/2000)%c.size());
			return c.get(idx);
		}
	}

	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe) {
		if (stack != null) {
			for (PositionedStack is : ((DropRecipe)arecipes.get(recipe)).getOtherStacks()) {
				if (is instanceof PositionedStackWithTooltip && gui.isMouseOver(is, recipe))
					currenttip.add(((PositionedStackWithTooltip)is).tooltip);
			}
		}
		return super.handleTooltip(gui, currenttip, recipe);
	}

	@Override
	public String getRecipeName() {
		return "Drop Processor";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/dropgui-nei.png";
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
		transferRects.add(new RecipeTransferRect(new Rectangle(71, 16, 25, 40), "rcdrops"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcdrops")) {
			Collection<DropProcessing> li = TileEntityDropProcessor.getAllProcessors(); //load generic recipes
			for (DropProcessing d : li)
				arecipes.add(new DropRecipe(d));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcdrops")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Collection<DropProcessing> c = TileEntityDropProcessor.getAllHandlersProducing(result);
		for (DropProcessing d : c)
			arecipes.add(new DropRecipe(d));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (TileEntityDropProcessor.isProcessable(ingredient)) {
			arecipes.add(new DropRecipe(ingredient, TileEntityDropProcessor.getHandler(ingredient)));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiDropProcessor.class;
	}

	@Override
	public void drawExtras(int recipe)
	{

	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

}
