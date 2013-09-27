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
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipesExtractor;
import Reika.RotaryCraft.GUIs.GuiExtractor;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ExtractorHandler extends TemplateRecipeHandler {

	public class ExtractorRecipe extends CachedRecipe {

		private List<ItemStack> oreBlock;
		private int extractID;

		public ExtractorRecipe(ModOreList modore, ReikaOreHelper ore, ItemStack block) {
			if (modore != null) {
				if (block != null)
					oreBlock = ReikaJavaLibrary.makeListFrom(block.copy());
				else
					oreBlock = modore.getAllOreBlocks();
				extractID = RotaryCraft.modextracts.itemID;
			}
			else {
				oreBlock = ReikaJavaLibrary.makeListFrom(ore.getOreBlock());
				extractID = RotaryCraft.extracts.itemID;
			}
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(this.getFlakes(), 0, 0);
		}

		private ItemStack getDust() {
			return new ItemStack(Block.fire);
		}

		private ItemStack getSlurry() {
			return new ItemStack(Block.fire);
		}

		private ItemStack getSolution() {
			return new ItemStack(Block.fire);
		}

		private ItemStack getFlakes() {
			return new ItemStack(Block.fire);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int dx = 0;
			int dy = 0;
			int vx = 0;
			int vy = 0;

			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 4; j++) {
					PositionedStack pos = new PositionedStack(this.getItem(j, i), dx+vx*j, dy+vy*i);
					stacks.add(pos);
				}
			}

			return stacks;
		}

		private Object getItem(int x, int y) {
			if (x == 0 && y == 0)
				return oreBlock;
			return new ItemStack(Block.fire);
		}

		private int getSizeAt(int x, int y) {
			return 1;
		}
	}

	@Override
	public String getRecipeName() {
		return "Extractor";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/extractorgui.png";
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result.itemID == RotaryCraft.extracts.itemID || result.itemID == RotaryCraft.modextracts.itemID) {
			if (result.itemID == RotaryCraft.extracts.itemID && !RecipesExtractor.isFlakes(result))
				return;
			ModOreList ore = ExtractorModOres.getOreFromExtract(result);
			ReikaOreHelper van = RecipesExtractor.getOreFromExtract(result);
			arecipes.add(new ExtractorRecipe(ore, van, null));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ReikaBlockHelper.isOre(ingredient) || (ingredient.itemID == RotaryCraft.extracts.itemID && ingredient.getItemDamage() < 24) || ingredient.itemID == RotaryCraft.modextracts.itemID) {
			ModOreList ore = ModOreList.getModOreFromOre(ingredient);
			if (ingredient.itemID == RotaryCraft.modextracts.itemID)
				ore = ExtractorModOres.getOreFromExtract(ingredient);
			ReikaOreHelper van = ReikaOreHelper.getEntryByOreDict(ingredient);
			arecipes.add(new ExtractorRecipe(ore, van, ingredient));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiExtractor.class;
	}

	@Override
	public void drawExtras(GuiContainerManager gui, int recipe) {
		gui.drawText(0, 0, String.format("%d%s Duplication Chance", this.getDupeChance(recipe), "%%"));
	}

	public int getDupeChance(int recipe) {
		return 50;
	}

}
