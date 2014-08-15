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

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesPulseFurnace;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiPulseFurnace;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class PulseJetHandler extends TemplateRecipeHandler {

	public class PulseJetRecipe extends CachedRecipe {

		private ItemStack input;
		private List<ItemStack> inputs;

		public PulseJetRecipe(ItemStack in) {
			input = ReikaItemHelper.getSizedItemStack(in, 1);
		}

		public PulseJetRecipe(List<ItemStack> in) {
			inputs = in;
		}

		@Override
		public PositionedStack getResult() {
			ItemStack in = this.getInput();
			ItemStack out = RecipesPulseFurnace.smelting().getSmeltingResult(in);
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
		ReikaGuiAPI.instance.drawTexturedModalRect(0, 0, 5, 11, 166, 70);
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
			List<ItemStack> li = RecipesPulseFurnace.smelting().getSources(result);
			if (li != null && !li.isEmpty())
				arecipes.add(new PulseJetRecipe(li));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (RecipesPulseFurnace.smelting().isSmeltable(ingredient)) {
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