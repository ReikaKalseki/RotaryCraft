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

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.GuiBlastFurnace;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class BlastFurnaceHandler extends TemplateRecipeHandler {

	public class BlastFurnaceRecipe extends CachedRecipe {

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

	@Override
	public String getRecipeName() {
		return "Blast Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/blastfurngui.png";
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (ReikaItemHelper.matchStacks(result, ItemStacks.steelingot)) {
			arecipes.add(new BlastFurnaceRecipe());
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ingredient.itemID == Item.ingotIron.itemID || ingredient.itemID == Item.coal.itemID || ingredient.itemID == Item.gunpowder.itemID) {
			arecipes.add(new BlastFurnaceRecipe());
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiBlastFurnace.class;
	}

}
