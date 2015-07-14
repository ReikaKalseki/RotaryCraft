/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPulseFurnace;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class PulseJetHandler extends TemplateRecipeHandler {

	public class PulseJetRecipe extends CachedRecipe {

		private ItemStack input;
		private List<ItemStack> inputs;

		private PulseJetRecipe(ItemStack in) {
			input = ReikaItemHelper.getSizedItemStack(in, 1);
		}

		private PulseJetRecipe(List<ItemStack> in) {
			inputs = in;
		}

		@Override
		public PositionedStack getResult() {
			ItemStack in = this.getInput();
			ItemStack out = RecipesPulseFurnace.getRecipes().getSmeltingResult(in);
			return new PositionedStack(out, 120, 41);
		}

		private ItemStack getInput() {
			return input != null ? input : this.getEntry();
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(this.getInput(), 120, 5);
		}

		public ItemStack getEntry() {
			return inputs.get((int)(System.nanoTime()/1000000000)%inputs.size());
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
		transferRects.add(new RecipeTransferRect(new Rectangle(119, 22, 18, 17), "rcpulsej"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcpulsej")) {
			Collection<ItemStack> li = RecipesPulseFurnace.getRecipes().getAllSmeltables();
			for (ItemStack is : li)
				arecipes.add(new PulseJetRecipe(is));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcpulsejet")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (RecipesPulseFurnace.getRecipes().isProduct(result)) {
			List<ItemStack> li = RecipesPulseFurnace.getRecipes().getSources(result);
			if (li != null && !li.isEmpty())
				arecipes.add(new PulseJetRecipe(li));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesPulseFurnace.getRecipes().isSmeltable(ingredient)) {
			arecipes.add(new PulseJetRecipe(ingredient));
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
		ReikaGuiAPI.instance.drawTexturedModalRect(85, 4, 247, 0, 7, 54);
		ReikaGuiAPI.instance.drawTexturedModalRect(53, 4, 198, 0, 7, 54);
		ReikaGuiAPI.instance.drawTexturedModalRect(15, 11, 176, 7, 11, 49);
	}

}
