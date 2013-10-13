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
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.GuiFermenter;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FermenterHandler extends TemplateRecipeHandler {

	public class FermenterRecipe extends CachedRecipe {

		private ItemStack input;
		private ItemStack output;

		public FermenterRecipe(ItemStack in) {
			input = in;
			output = in;
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(output, 111, 24);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(new PositionedStack(this.getTopSlot(), 50, 6));
			stacks.add(new PositionedStack(this.getMiddleSlot(), 50, 24));
			stacks.add(new PositionedStack(this.getBottomSlot(), 50, 42));
			return stacks;
		}

		private ItemStack getTopSlot() {
			return output.itemID == ItemRegistry.YEAST.getShiftedID() ? new ItemStack(Item.sugar) : ItemRegistry.YEAST.getStackOf();
		}

		private List<ItemStack> getMiddleSlot() {
			if (output.itemID == ItemRegistry.YEAST.getShiftedID())
				return ReikaJavaLibrary.makeListFrom(new ItemStack(Item.bucketWater));
			else {
				return TileEntityFermenter.getAllValidPlants();
			}
		}

		private ItemStack getBottomSlot() {
			return output.itemID == ItemRegistry.YEAST.getShiftedID() ? new ItemStack(Block.dirt) : new ItemStack(Item.bucketWater);
		}
	}

	@Override
	public String getRecipeName() {
		return "Fermenter";
	}

	@Override
	public String getGuiTexture() {
		return "/Reika/RotaryCraft/Textures/GUI/fermentergui.png";
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
		if (result.itemID == ItemRegistry.YEAST.getShiftedID() || ReikaItemHelper.matchStacks(result, ItemStacks.sludge)) {
			arecipes.add(new FermenterRecipe(result));
		}
	}

	public boolean isEthanolIngredient(ItemStack is) {
		if (is.itemID == ItemRegistry.YEAST.getShiftedID())
			return true;
		if (is.itemID == Item.bucketWater.itemID)
			return true;
		return TileEntityFermenter.getPlantValue(is) > 0;
	}

	public boolean isYeastIngredient(ItemStack is) {
		if (is.itemID == Block.dirt.blockID)
			return true;
		if (is.itemID == Item.sugar.itemID)
			return true;
		if (is.itemID == Item.bucketWater.itemID)
			return true;
		return false;
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (this.isEthanolIngredient(ingredient) || this.isYeastIngredient(ingredient)) {
			if (this.isYeastIngredient(ingredient))
				arecipes.add(new FermenterRecipe(ItemRegistry.YEAST.getStackOf()));
			else
				arecipes.add(new FermenterRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, TileEntityFermenter.getPlantValue(ingredient))));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFermenter.class;
	}

}
