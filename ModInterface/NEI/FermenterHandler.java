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
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaLiquidRenderer;
import Reika.DragonAPI.Libraries.IO.ReikaTextureHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.GUIs.Machine.Inventory.GuiFermenter;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class FermenterHandler extends TemplateRecipeHandler {

	public class FermenterRecipe extends CachedRecipe {

		private ItemStack output;
		private ItemStack input;

		public FermenterRecipe(ItemStack in, ItemStack out) {
			this(out);
			input = in;
		}

		public FermenterRecipe(ItemStack out) {
			output = out;
		}

		@Override
		public PositionedStack getResult() {
			ItemStack in = input != null ? input : this.getEntry(this.getBottomSlot());
			ItemStack is = output.itemID == ItemRegistry.YEAST.getShiftedID() ? output : ReikaItemHelper.getSizedItemStack(output, TileEntityFermenter.getPlantValue(in));
			return new PositionedStack(is, 111, 36);
		}

		@Override
		public ArrayList<PositionedStack> getIngredients()
		{
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(new PositionedStack(this.getTopSlot(), 50, 18));
			if (input != null) {
				stacks.add(new PositionedStack(input, 50, 54));
			}
			else {
				//stacks.add(new PositionedStack(this.getMiddleSlot(), 50, 36));
				List li = this.getBottomSlot();
				stacks.add(new PositionedStack(this.getEntry(li), 50, 54));
			}
			return stacks;
		}

		private ItemStack getTopSlot() {
			return output.itemID == ItemRegistry.YEAST.getShiftedID() ? new ItemStack(Item.sugar) : ItemRegistry.YEAST.getStackOf();
		}

		private List<ItemStack> getBottomSlot() {
			return output.itemID == ItemRegistry.YEAST.getShiftedID() ? ReikaJavaLibrary.makeListFrom(new ItemStack(Block.dirt)) : TileEntityFermenter.getAllValidPlants();
		}

		public ItemStack getEntry(List<ItemStack> li) {
			return li.get((int)(System.nanoTime()/1000000000)%li.size());
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
		drawTexturedModalRect(0, 6, 5, 5, 166, 76);
	}

	@Override
	public void drawForeground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		this.drawExtras(recipe);
		ReikaLiquidRenderer.bindFluidTexture(FluidRegistry.WATER);
		//drawTexturedModalRect(0, 0, (int)(ico.getMinU()*16), (int)(ico.getMinV()*16), 16, 16);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result.itemID == ItemRegistry.YEAST.getShiftedID() || ReikaItemHelper.matchStacks(result, ItemStacks.sludge)) {
			arecipes.add(new FermenterRecipe(result));
		}
		if (ReikaItemHelper.matchStacks(result, ItemStacks.ethanolbucket))
			arecipes.add(new FermenterRecipe(ItemStacks.sludge));
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
				arecipes.add(new FermenterRecipe(ingredient, ItemRegistry.YEAST.getStackOf()));
			else
				arecipes.add(new FermenterRecipe(ingredient, ItemStacks.sludge.copy()));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiFermenter.class;
	}

	@Override
	public void drawExtras(int recipe)
	{
		int l = 27;
		drawTexturedModalRect(18, 16+l, 176, 31+l, 11, 56-l);
	}

}
