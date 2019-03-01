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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastFurnacePattern;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastInput;
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
			int index = (int)((time/500)%inputs.size());
			return inputs.get(index);
		}

		@Override
		public PositionedStack getResult() {
			if (ReikaItemHelper.matchStacks(recipe.outputItem(), ItemStacks.steelblock)) {
				return null;
			}
			int in = this.getInput();
			int num = recipe.getNumberProduced(in);
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(recipe.outputItem(), num), 143, 24);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			ArrayList li = new ArrayList();
			if (ReikaItemHelper.matchStacks(recipe.outputItem(), ItemStacks.steelblock)) {
				li.add(new PositionedStack(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 2), 143, 24));
				li.add(new PositionedStack(new ItemStack(Items.coal, 3, 1), 143, 24-18));
				li.add(new PositionedStack(new ItemStack(Items.iron_ingot, 5, 1), 143, 24+18));
			}
			return li;
		}

		@Override
		public ArrayList<PositionedStack> getIngredients() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int dx = 57;
			int dy = 6;
			int num = this.getInput();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i*3+j < num) {
						stacks.add(new PositionedStack(recipe.mainItemForDisplay(), dx+18*i, dy+18*j));
					}
				}
			}

			if (recipe.tertiary.exists())
				stacks.add(new PositionedStack(recipe.tertiary.getItemForDisplay(), 21, 5));
			if (recipe.primary.exists())
				stacks.add(new PositionedStack(recipe.primary.getItemForDisplay(), 21, 24));
			if (recipe.secondary.exists())
				stacks.add(new PositionedStack(recipe.secondary.getItemForDisplay(), 21, 43));

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
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ItemStack is = items[i+j*3];
					if (is != null) {
						stacks.add(new PositionedStack(is, dx+18*i, dy+18*j));
					}
				}
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
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth(0, 0, 5, 11, 166, 70, ReikaGuiAPI.NEI_DEPTH);
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
		transferRects.add(new RecipeTransferRect(new Rectangle(115, 23, 23, 18), "rcblastf"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcblastf")) {
			Collection<BlastFurnacePattern> li = RecipesBlastFurnace.getRecipes().getAllRecipes();
			for (BlastFurnacePattern p : li) {
				if (p instanceof BlastRecipe)
					arecipes.add(new BlastFurnRecipe((BlastRecipe)p));
				else if (p instanceof BlastCrafting)
					arecipes.add(new BlastFurnCrafting((BlastCrafting)p));
				else
					RotaryCraft.logger.logError("Unrenderable recipe, makes "+p.outputItem()+"!");
			}
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcblastf")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
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
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void drawExtras(int recipe)
	{
		ReikaGuiAPI.instance.drawTexturedModalRect(6, 17, 176, 44, 11, 43);
		BlastTempRecipe r = ((BlastTempRecipe)arecipes.get(recipe));
		String s = String.format("%dC", r.getRecipeTemperature());
		FontRenderer f = Minecraft.getMinecraft().fontRenderer;
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(f, s, f.getStringWidth(s)/2-2*(s.length()/5), 61, 0);

		int dy = 0;
		if (r instanceof BlastFurnRecipe) {
			BlastRecipe br = ((BlastFurnRecipe)r).recipe;
			BlastInput in1 = br.primary;
			if (in1.exists()) {
				String sg = String.format("%s: x%d (%.1f%%)", in1.getItemForDisplay().getDisplayName(), in1.numberToUse, 100*in1.chanceToUse);
				f.drawString(sg, 21, 72, 0);
				dy += f.FONT_HEIGHT+2;
			}

			BlastInput in2 = br.secondary;
			if (in2.exists()) {
				String sg = String.format("%s: x%d (%.1f%%)", in2.getItemForDisplay().getDisplayName(), in2.numberToUse, 100*in2.chanceToUse);
				f.drawString(sg, 21, 72+dy, 0);
				dy += f.FONT_HEIGHT+2;
			}

			BlastInput in3 = br.tertiary;
			if (in3.exists()) {
				String sg = String.format("%s: x%d (%.1f%%)", in3.getItemForDisplay().getDisplayName(), in3.numberToUse, 100*in3.chanceToUse);
				f.drawString(sg, 21, 72+dy, 0);
				dy += f.FONT_HEIGHT+2;
			}

			f.drawString("Bonus output: "+(br.hasBonus ? "Yes" : "No"), 21, 72+dy, 0);
		}
		else if (r instanceof BlastFurnCrafting) {
			BlastCrafting br = ((BlastFurnCrafting)r).recipe;
			String sg = String.format("Time Factor: %dx", br.speed);
			ReikaGuiAPI.instance.drawCenteredStringNoShadow(f, sg, 83, 64+dy, 0);
		}
	}

}
