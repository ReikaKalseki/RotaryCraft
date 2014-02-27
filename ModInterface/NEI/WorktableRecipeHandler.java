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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiWorktable;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class WorktableRecipeHandler extends TemplateRecipeHandler {

	public class WorktableRecipe extends CachedRecipe {

		private IRecipe recipe;

		public WorktableRecipe(IRecipe rec) {
			recipe = rec;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(recipe.getRecipeOutput(), 111, 24);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			ItemStack[] in = new ItemStack[9];
			ReikaRecipeHelper.copyRecipeToItemStackArray(in, recipe);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ItemStack is = in[i*3+j];
					int dx = 21+18*j;
					int dy = 6+18*i;
					if (is != null) {
						PositionedStack pos = new PositionedStack(is, dx, dy);
						stacks.add(pos);
					}
				}
			}
			return stacks;
		}
	}

	@Override
	public String getRecipeName() {
		return "Worktable";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/worktablegui.png";
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "crafting";
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
		List<IRecipe> li = WorktableRecipes.getInstance().getRecipeListCopy();
		for (int i = 0; i < li.size(); i++) {
			IRecipe ir = li.get(i);
			if (ReikaItemHelper.matchStacks(result, ir.getRecipeOutput()))
				arecipes.add(new WorktableRecipe(ir));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<IRecipe> li = WorktableRecipes.getInstance().getRecipeListCopy();
		for (int i = 0; i < li.size(); i++) {
			IRecipe ir = li.get(i);
			ItemStack[] in = new ItemStack[9];
			ReikaRecipeHelper.copyRecipeToItemStackArray(in, ir);
			for (int k = 0; k < 9; k++) {
				if (ReikaItemHelper.matchStacks(ingredient, in[k])) {
					arecipes.add(new WorktableRecipe(ir));
					k = 9;
				}
			}
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiWorktable.class;
	}

	@Override
	public void loadTransferRects()
	{/*
		List<IRecipe> li = WorktableRecipes.getInstance().getRecipeListCopy();
		List<ItemStack> items = new ArrayList();
		for (int i = 0; i < li.size(); i++) {
			items.add(li.get(i).getRecipeOutput());
		}
		RecipeTransferRect rect = new RecipeTransferRect(new Rectangle(75, 24, 18, 18), "item", (Object[])new ItemStack[]{ItemStacks.salt, ItemStacks.aluminumingot});
		transferRects.add(rect);*/
	}

	@Override
	public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe)
	{
		IOverlayHandler ioh = super.getOverlayHandler(gui, recipe);
		return ioh;
	}

}
