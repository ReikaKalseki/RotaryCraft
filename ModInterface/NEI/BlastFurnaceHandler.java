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

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiBlastFurnace;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class BlastFurnaceHandler extends TemplateRecipeHandler {

	public class SteelRecipe extends CachedRecipe {

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 9), 143, 24);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int dx = 57;
			int dy = 6;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					stacks.add(new PositionedStack(new ItemStack(Item.ingotIron), dx+18*j, dy+18*i));
				}
			}

			stacks.add(new PositionedStack(new ItemStack(Item.coal), 21, 24));
			stacks.add(new PositionedStack(new ItemStack(Item.gunpowder), 21, 43));

			return stacks;
		}
	}

	public class BedrockRecipe extends CachedRecipe {

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(ReikaItemHelper.getSizedItemStack(ItemStacks.bedingot, 1), 143, 24);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int dx = 57;
			int dy = 6;
			stacks.add(new PositionedStack(ItemStacks.steelingot, dx, dy));

			stacks.add(new PositionedStack(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockdust, 4), 21, 24));

			return stacks;
		}
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
		if (ReikaItemHelper.matchStacks(result, ItemStacks.steelingot)) {
			arecipes.add(new SteelRecipe());
		}
		if (ReikaItemHelper.matchStacks(result, ItemStacks.bedingot)) {
			arecipes.add(new BedrockRecipe());
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ingredient.itemID == Item.ingotIron.itemID || ingredient.itemID == Item.coal.itemID || ingredient.itemID == Item.gunpowder.itemID) {
			arecipes.add(new SteelRecipe());
		}
		if (ReikaItemHelper.matchStacks(ItemStacks.steelingot, ingredient) || ReikaItemHelper.matchStacks(ItemStacks.bedrockdust, ingredient)) {
			arecipes.add(new BedrockRecipe());
		}
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
	}

}
