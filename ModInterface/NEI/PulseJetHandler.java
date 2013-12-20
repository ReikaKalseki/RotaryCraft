/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPulseFurnace;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class PulseJetHandler extends TemplateRecipeHandler {

	public class PulseJetRecipe extends CachedRecipe {

		private List<ItemStack> input;
		private ItemStack output;

		public PulseJetRecipe(ItemStack in, ItemStack out) {
			input = ReikaJavaLibrary.makeListFrom(in);
			output = out;
		}

		public PulseJetRecipe(List<ItemStack> in) {
			input = in;
			output = RecipesPulseFurnace.smelting().getSmeltingResult(in.get(0));
		}

		@Override
		public PositionedStack getResult() {
			ItemStack is = output;
			if (input != null && input.size() > 1)
				is = RecipesPulseFurnace.smelting().getSmeltingResult(this.getEntry());
			return new PositionedStack(is, 120, 41);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(this.getEntry(), 120, 5);
		}

		public ItemStack getEntry() {
			return input.get((int)(System.nanoTime()/1000000000)%input.size());
		}
	}

	@Override
	public String getRecipeName() {
		return "Pulse Jet Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/pulsejetgui.png";
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
		if (RecipesPulseFurnace.smelting().isProduct(result)) {
			List<ItemStack> is = RecipesPulseFurnace.smelting().getSources(result);
			if (is != null && !is.isEmpty())
				arecipes.add(new PulseJetRecipe(is));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesPulseFurnace.smelting().isSmeltable(ingredient)) {
			arecipes.add(new PulseJetRecipe(ReikaJavaLibrary.makeListFrom(ingredient)));
		}
		else if (ReikaItemHelper.matchStacks(ItemStacks.scrap, ingredient)) {
			arecipes.add(new PulseJetRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.steelingot));
		}
		else if (ReikaItemHelper.matchStacks(ItemStacks.ironscrap, ingredient)) {
			arecipes.add(new PulseJetRecipe(ItemStacks.ironscrap, new ItemStack(Item.ingotIron)));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiPulseFurnace.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawTexturedModalRect(85, 4, 247, 0, 7, 54);
		drawTexturedModalRect(53, 4, 198, 0, 7, 54);
		drawTexturedModalRect(15, 11, 176, 7, 11, 49);
	}

}
