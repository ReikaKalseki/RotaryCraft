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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Instantiable.Data.KeyedItemStack;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaGuiAPI;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FractionHandler extends TemplateRecipeHandler {

	public class FractionatorRecipe extends CachedRecipe {

		private FractionatorRecipe() {

		}

		@Override
		public PositionedStack getResult() {
			return null;//new PositionedStack(null, 131, 24);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return null;
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			Collection<KeyedItemStack> in = TileEntityFractionator.getIngredients();
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int i = 0;
			for (KeyedItemStack is : in) {
				stacks.add(new PositionedStack(is.getItemStack(), 29+(i%2)*18, 7+i/2*18));
				i++;
			}
			stacks.add(new PositionedStack(new ItemStack(Items.ghast_tear), 101, 25));
			return stacks;
		}
	}

	@Override
	public String getRecipeName() {
		return "Fractionation Unit";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/fractiongui2.png";
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
	public void loadCraftingRecipes(ItemStack result) {
		if (ReikaItemHelper.matchStacks(result, ItemStacks.fuelbucket))
			arecipes.add(new FractionatorRecipe());
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (TileEntityFractionator.isJetFuelIngredient(ingredient))
			arecipes.add(new FractionatorRecipe());
		if (ingredient.getItem() == Items.ghast_tear)
			arecipes.add(new FractionatorRecipe());
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFractionator.class;
	}

	@Override
	public void drawExtras(int recipe) {
		ReikaGuiAPI.instance.drawTexturedModalRect(142, 7, 177, 45, 6, 50);
		ReikaGuiAPI.instance.drawTexturedModalRect(19, 7, 185, 45, 8, 52);
	}

}
