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
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaGuiAPI;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace.PulseJetRecipe;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPulseFurnace;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class PulseJetHandler extends TemplateRecipeHandler {

	public class PulseJetRecipeNEI extends CachedRecipe {

		private PulseJetRecipe reference;
		private ItemStack input;

		private PulseJetRecipeNEI(PulseJetRecipe ref) {
			input = ref.getInput();
			reference = ref;
		}

		private PulseJetRecipeNEI(ItemStack in) {
			input = ReikaItemHelper.getSizedItemStack(in, 1);
			reference = RecipesPulseFurnace.getRecipes().getSmeltingResult(in);
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(reference.getOutput(), 120, 41);
		}

		@Override
		public PositionedStack getIngredient() {
			return new PositionedStack(input, 120, 5);
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
				arecipes.add(new PulseJetRecipeNEI(is));
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
			List<PulseJetRecipe> li = RecipesPulseFurnace.getRecipes().getSources(result);
			for (PulseJetRecipe r : li)
				arecipes.add(new PulseJetRecipeNEI(r));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesPulseFurnace.getRecipes().isSmeltable(ingredient)) {
			arecipes.add(new PulseJetRecipeNEI(ingredient));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiPulseFurnace.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		ReikaGuiAPI.instance.drawTexturedModalRect(85, 4, 247, 0, 7, 54);
		ReikaGuiAPI.instance.drawTexturedModalRect(53, 4, 198, 0, 7, 54);
		ReikaGuiAPI.instance.drawTexturedModalRect(15, 11, 176, 7, 11, 49);

		PulseJetRecipeNEI r = (PulseJetRecipeNEI)arecipes.get(recipe);
		String sg = String.format("Min Temperature: %dC", r.reference.requiredTemperature);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(Minecraft.getMinecraft().fontRenderer, sg, 83, 61, 0);
	}

}
