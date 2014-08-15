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
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiWorktable;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ToolCraftingHandler extends TemplateRecipeHandler {

	private boolean isWorktable = false;

	public class CraftingRecipe extends CachedRecipe {

		private final ItemStack[] ingredients;
		private final ItemStack product;

		public CraftingRecipe(ItemStack tool, ItemStack... items) {
			ingredients = items;
			product = tool;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(product, 93, 6);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			ItemStack[] in = new ItemStack[9];
			for (int i = 0; i < 9 && i < ingredients.length; i++) {
				in[i] = ingredients[i];
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ItemStack is = in[i*3+j];
					int dx = 21+18*j;
					int dy = 6+18*i;
					if (is != null) {
						PositionedStack pos = new PositionedStack(is, dx, dy);
						stacks.add(pos);
					}
				}
			}
			return stacks;
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		isWorktable = false;
		ItemRegistry ir = ItemRegistry.getEntry(result);
		if (result.getItemDamage() > 0) {
			if (ir != null && ir.isCharged()) {
				GuiCraftingRecipe.openRecipeGui("item", ir.getStackOf());
			}
		}
		else if (ir == ItemRegistry.BEDPACK) {
			ItemStack jet = ItemRegistry.JETPACK.getStackOf();
			arecipes.add(new CraftingRecipe(ir.getEnchantedStack(), ItemRegistry.BEDCHEST.getEnchantedStack(), jet));
			isWorktable = true;
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		isWorktable = false;
		if (ingredient != null && ingredient.getItem() == ItemRegistry.BEDCHEST.getItemInstance()) {
			ItemStack jet = ItemRegistry.JETPACK.getStackOf();
			arecipes.add(new CraftingRecipe(ItemRegistry.BEDPACK.getEnchantedStack(), ItemRegistry.BEDCHEST.getEnchantedStack(), jet));
			isWorktable = true;
		}
	}

	@Override
	public String getRecipeName() {
		return isWorktable ? "Tool Crafting" : "Shaped Crafting";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return isWorktable ? GuiWorktable.class : GuiCrafting.class;
	}

	@Override
	public String getGuiTexture() {
		return isWorktable ? "/Reika/RotaryCraft/Textures/GUI/worktablegui.png" : "/gui/crafting.png";
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

}