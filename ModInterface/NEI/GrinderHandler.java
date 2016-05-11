/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiGrinder;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class GrinderHandler extends TemplateRecipeHandler {

	public class GrinderRecipe extends CachedRecipe {

		private List<ItemStack> input;
		private ItemStack output;

		private GrinderRecipe(ItemStack in) {
			this(ReikaJavaLibrary.makeListFrom(in));
		}

		private GrinderRecipe(List<ItemStack> in) {
			input = in;
			output = RecipesGrinder.getRecipes().getGrindingResult(in.get(0));
		}

		@Override
		public PositionedStack getResult() {
			ItemStack result = RecipesGrinder.getRecipes().getGrindingResult(this.getEntry());
			ItemStack is = result;
			return is != null ? new PositionedStack(is, 131, 24) : null;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(this.getEntry(), 71, 24);
		}

		public ItemStack getEntry() {
			ItemStack is = input.get((int)(System.nanoTime()/1000000000)%input.size());
			return ReikaItemHelper.getSizedItemStack(is, 1);
		}
	}

	public class SeedRecipe extends CachedRecipe {

		private ArrayList<ItemStack> inputs = new ArrayList();

		private SeedRecipe() {
			inputs.addAll(TileEntityGrinder.getGrindableSeeds());
		}

		private SeedRecipe(ItemStack seed) {
			inputs.add(seed);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(this.getInput(), 71, 24);
		}

		@Override
		public PositionedStack getResult() {
			ItemStack is = RecipesGrinder.getRecipes().getGrindingResult(this.getInput());
			return is != null ? new PositionedStack(is, 131, 24) : null;
		}

		private ItemStack getInput() {
			ItemStack is = inputs.get((int)(System.nanoTime()/1000000000)%inputs.size());
			return ReikaItemHelper.getSizedItemStack(is, 1);
		}

	}

	@Override
	public String getRecipeName() {
		return "Grinder";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/grindergui.png";
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
		CachedRecipe c = arecipes.get(recipe);
		if (c.getIngredient() != null && c.getIngredient().item.getItem() == ItemRegistry.CANOLA.getItemInstance()) {
			ReikaGuiAPI.instance.drawTexturedModalRect(19, 10, 176, 71, 8, 55);
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(95, 23, 23, 18), "rcgrinder"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcgrinder")) {
			Collection<ItemStack> li = RecipesGrinder.getRecipes().getAllGrindables();
			for (ItemStack is : li)
				arecipes.add(new GrinderRecipe(is));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcgrinder")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (ReikaItemHelper.matchStacks(result, ItemStacks.lubebucket)) {
			arecipes.add(new SeedRecipe());
		}
		else if (RecipesGrinder.getRecipes().isProduct(result)) {
			List<ItemStack> is = RecipesGrinder.getRecipes().getSources(result);
			if (is != null && !is.isEmpty())
				arecipes.add(new GrinderRecipe(is));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (TileEntityGrinder.isGrindableSeed(ingredient)) {
			arecipes.add(new SeedRecipe(ingredient));
		}
		else if (ReikaBlockHelper.isOre(ingredient)) {
			arecipes.add(new GrinderRecipe(ReikaJavaLibrary.makeListFrom(ingredient)));
		}
		else if (RecipesGrinder.getRecipes().isGrindable(ingredient)) {
			arecipes.add(new GrinderRecipe(ReikaJavaLibrary.makeListFrom(ingredient)));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiGrinder.class;
	}

}
