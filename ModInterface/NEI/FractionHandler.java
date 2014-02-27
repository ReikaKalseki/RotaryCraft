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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FractionHandler extends TemplateRecipeHandler {

	public class FractionatorRecipe extends CachedRecipe {

		public FractionatorRecipe() {

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
			ItemStack[] in = TileEntityFractionator.getIngredients();
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			for (int i = 0; i < in.length; i++) {
				stacks.add(new PositionedStack(in[i], 21+(i%2)*18, 7+i/2*18));
			}
			stacks.add(new PositionedStack(new ItemStack(Item.ghastTear), 93, 25));
			return stacks;
		}
	}

	@Override
	public String getRecipeName() {
		return "Fractionation Unit";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/fractiongui.png";
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
		if (ReikaItemHelper.matchStacks(result, ItemStacks.fuelbucket))
			arecipes.add(new FractionatorRecipe());
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (TileEntityFractionator.isJetFuelIngredient(ingredient))
			arecipes.add(new FractionatorRecipe());
		if (ingredient.itemID == Item.ghastTear.itemID)
			arecipes.add(new FractionatorRecipe());
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFractionator.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawTexturedModalRect(134, 7, 177, 45, 6, 50);
	}

}
