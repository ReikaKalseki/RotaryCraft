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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiComposter;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ComposterHandler extends TemplateRecipeHandler {

	public class ComposterRecipe extends CachedRecipe {

		private ArrayList<ItemStack> input;

		private ComposterRecipe() {
			input = TileEntityComposter.getAllCompostables();
		}

		private ComposterRecipe(ItemStack in) {
			input = ReikaJavaLibrary.makeListFrom(in);
		}

		@Override
		public PositionedStack getResult() {
			ItemStack in = this.getEntry();
			ItemStack is = ReikaItemHelper.getSizedItemStack(ItemStacks.compost, TileEntityComposter.getCompostValue(in));
			return new PositionedStack(is, 111, 36);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(new PositionedStack(ItemRegistry.YEAST.getStackOf(), 50, 45));
			stacks.add(new PositionedStack(this.getEntry(), 50, 27));
			return stacks;
		}

		public ItemStack getEntry() {
			return input.get((int)(System.nanoTime()/1000000000)%input.size());
		}
	}

	@Override
	public String getRecipeName() {
		return "Composter";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/compostergui.png";
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth(0, 6, 5, 5, 166, 76, ReikaGuiAPI.NEI_DEPTH);
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
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 22, 17), "rccompost"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rccompost")) {
			Collection<ItemStack> li = TileEntityComposter.getAllCompostables();
			for (ItemStack is : li)
				arecipes.add(new ComposterRecipe(is));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rccompost")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (ReikaItemHelper.matchStacks(result, ItemStacks.compost))
			arecipes.add(new ComposterRecipe());
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (TileEntityComposter.getCompostValue(ingredient) > 0) {
			arecipes.add(new ComposterRecipe(ingredient));
		}
		else if (ItemRegistry.YEAST.matchItem(ingredient)) {
			arecipes.add(new ComposterRecipe());
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiComposter.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		int l = 27;
		ReikaGuiAPI.instance.drawTexturedModalRect(18, 16+l, 176, 31+l, 11, 56-l);
	}

}
