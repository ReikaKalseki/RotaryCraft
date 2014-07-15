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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastRecipe;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiBlastFurnace;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class BlastFurnaceHandler extends TemplateRecipeHandler {

	public class BlastFurnRecipe extends CachedRecipe implements BlastTempRecipe {

		public final BlastRecipe recipe;
		private final ArrayList<Integer> inputs;

		private BlastFurnRecipe(BlastRecipe r) {
			recipe = r;
			inputs = r.getValidInputNumbers();
		}

		private int getInput() {
			long time = System.currentTimeMillis();
			int index = (int)((time/1000)%inputs.size());
			return inputs.get(index);
		}

		@Override
		public PositionedStack getResult() {
			int in = this.getInput();
			int num = recipe.getNumberProduced(in);
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(recipe.outputItem(), num), 143, 24);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int dx = 57;
			int dy = 6;
			int num = this.getInput();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i*3+j < num) {
						stacks.add(new PositionedStack(recipe.mainItem(), dx+18*j, dy+18*i));
					}
				}
			}

			if (recipe.tertiary.getItem() != null)
				stacks.add(new PositionedStack(recipe.tertiary.getItem(), 21, 5));
			if (recipe.primary.getItem() != null)
				stacks.add(new PositionedStack(recipe.primary.getItem(), 21, 24));
			if (recipe.secondary.getItem() != null)
				stacks.add(new PositionedStack(recipe.secondary.getItem(), 21, 43));

			return stacks;
		}

		@Override
		public int getRecipeTemperature() {
			return recipe.temperature;
		}
	}

	public class BlastFurnCrafting extends CachedRecipe implements BlastTempRecipe {

		public final BlastCrafting recipe;

		private BlastFurnCrafting(BlastCrafting c) {
			recipe = c;
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			ItemStack[] items = recipe.getArrayForDisplay();
			int dx = 57;
			int dy = 6;
			try {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						ItemStack is = items[i*3+j];
						if (is != null) {
							stacks.add(new PositionedStack(is, dx+18*j, dy+18*i));
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(recipe.outputItem(), 143, 24);
		}

		@Override
		public int getRecipeTemperature() {
			return recipe.temperature;
		}

	}

	public static interface BlastTempRecipe {

		public int getRecipeTemperature();

	}

	@Override
	public String getRecipeName() {
		return "Blast Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/blastfurngui.png";
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
		ArrayList<BlastRecipe> li = RecipesBlastFurnace.getRecipes().getAllRecipesMaking(result);
		for (int i = 0; i < li.size(); i++)
			arecipes.add(new BlastFurnRecipe(li.get(i)));
		ArrayList<BlastCrafting> li2 = RecipesBlastFurnace.getRecipes().getAllCraftingMaking(result);
		for (int i = 0; i < li2.size(); i++)
			arecipes.add(new BlastFurnCrafting(li2.get(i)));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ArrayList<BlastRecipe> li = RecipesBlastFurnace.getRecipes().getAllRecipesUsing(ingredient);
		for (int i = 0; i < li.size(); i++)
			arecipes.add(new BlastFurnRecipe(li.get(i)));
		ArrayList<BlastCrafting> li2 = RecipesBlastFurnace.getRecipes().getAllCraftingUsing(ingredient);
		for (int i = 0; i < li2.size(); i++)
			arecipes.add(new BlastFurnCrafting(li2.get(i)));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiBlastFurnace.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawTexturedModalRect(6, 17, 176, 44, 11, 43);
		BlastTempRecipe r = ((BlastTempRecipe)arecipes.get(recipe));
		String s = String.format("%dC", r.getRecipeTemperature());
		FontRenderer f = Minecraft.getMinecraft().fontRenderer;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(f, s, f.getStringWidth(s)/2-2*(s.length()/5), 61, 0);
	}

}
