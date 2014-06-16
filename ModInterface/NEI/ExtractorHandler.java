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

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.OreType.OreRarity;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesExtractor;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ExtractorHandler extends TemplateRecipeHandler {

	public class ExtractorRecipe extends CachedRecipe {

		private List<ItemStack> oreBlock;
		private int extractID;
		public ModOreList modore;
		public ReikaOreHelper ore;

		public ExtractorRecipe(ModOreList modore, ReikaOreHelper ore, ItemStack block) {
			if (modore != null) {
				if (block != null)
					oreBlock = ReikaJavaLibrary.makeListFrom(block.copy());
				else
					oreBlock = modore.getAllOreBlocks();
				if (oreBlock.isEmpty()) {
					oreBlock.add(new ItemStack(Block.fire));
				}
				extractID = RotaryCraft.modextracts.itemID;
			}
			else {
				oreBlock = ReikaJavaLibrary.makeListFrom(ore.getOreBlock());
				extractID = RotaryCraft.extracts.itemID;
			}
			this.ore = ore;
			this.modore = modore;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(this.getFlakes(), 129, 44);
		}

		private ItemStack getDust() {
			if (modore != null)
				return ExtractorModOres.getDustProduct(modore);
			return new ItemStack(RotaryCraft.extracts.itemID, 1, ore.ordinal());
		}

		private ItemStack getSlurry() {
			if (modore != null)
				return ExtractorModOres.getSlurryProduct(modore);
			return new ItemStack(RotaryCraft.extracts.itemID, 1, ore.ordinal()+8);
		}

		private ItemStack getSolution() {
			if (modore != null)
				return ExtractorModOres.getSolutionProduct(modore);
			return new ItemStack(RotaryCraft.extracts.itemID, 1, ore.ordinal()+16);
		}

		private ItemStack getFlakes() {
			if (modore != null)
				return ExtractorModOres.getFlakeProduct(modore);
			return new ItemStack(RotaryCraft.extracts.itemID, 1, ore.ordinal()+24);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			int dx = 21;
			int dy = 2;
			int vx = 36;
			int vy = 42;

			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 4; j++) {
					PositionedStack pos = new PositionedStack(this.getItem(j, i), dx+vx*j, dy+vy*i);
					if (!(i == 1 && j == 3))
						stacks.add(pos);
				}
			}

			return stacks;
		}

		private int getTick() {
			return (int)((System.nanoTime()/1000000000)%oreBlock.size());
		}

		private Object getItem(int x, int y) {
			switch(x+y*4) {
			case 0:
				return oreBlock.get(this.getTick());
			case 1:
			case 4:
				return this.getDust();
			case 2:
			case 5:
				return this.getSlurry();
			case 3:
			case 6:
				return this.getSolution();
			default:
				return new ItemStack(Block.fire);
			}
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
		if (result.itemID == RotaryCraft.extracts.itemID || result.itemID == RotaryCraft.modextracts.itemID) {
			if (result.itemID == RotaryCraft.extracts.itemID && !RecipesExtractor.isFlakes(result))
				return;
			ModOreList ore = ExtractorModOres.getOreFromExtract(result);
			if (ore != null && !ore.existsInGame())
				return;
			ReikaOreHelper van = RecipesExtractor.getOreFromExtract(result);
			arecipes.add(new ExtractorRecipe(ore, van, null));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ReikaBlockHelper.isOre(ingredient) || (ingredient.itemID == RotaryCraft.extracts.itemID && ingredient.getItemDamage() < 24) || (ingredient.itemID == RotaryCraft.modextracts.itemID && ingredient.getItemDamage()%4 != 3)) {
			ModOreList ore = ModOreList.getModOreFromOre(ingredient);
			if (ingredient.itemID == RotaryCraft.modextracts.itemID)
				ore = ExtractorModOres.getOreFromExtract(ingredient);
			if (ore != null && !ore.existsInGame())
				return;
			ReikaOreHelper van = ReikaOreHelper.getEntryByOreDict(ingredient);
			if (van == null)
				van = RecipesExtractor.getOreFromExtract(ingredient);
			//List<ItemStack> ores = ore.getAllOreBlocks();
			arecipes.add(new ExtractorRecipe(ore, van, null));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiExtractor.class;
	}

	@Override
	public void drawExtras(int recipe) {
		int chance = this.getDupeChance(recipe);
		Minecraft.getMinecraft().fontRenderer.drawString(String.format("%d%s duplication chance per stage", chance, "%"), -2, 65, 0x333333, false);
		Minecraft.getMinecraft().fontRenderer.drawString(String.format("(Average %.2f units per ore)", Math.pow(1+0.01*this.getDupeChance(recipe), 4)), 9, 76, 0x333333, false);
	}

	public int getDupeChance(int recipe) {
		ExtractorRecipe ir = (ExtractorRecipe)arecipes.get(recipe);
		if (ir != null && ir.modore != null) {
			if (ir.modore.getRarity() == OreRarity.RARE)
				return TileEntityExtractor.oreCopyRare;
			else if (ir.modore.isNether()) //.isNetherOres()
				return TileEntityExtractor.oreCopyNether;
		}
		return TileEntityExtractor.oreCopy;
	}

}
