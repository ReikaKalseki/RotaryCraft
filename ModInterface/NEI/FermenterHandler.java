/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.NEI;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
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

		private FermenterRecipe(ItemStack in, ItemStack out) {
			this(out);
			input = ReikaItemHelper.getSizedItemStack(in, 1);
		}

		private FermenterRecipe(ItemStack out) {
			output = out;
		}

		@Override
		public PositionedStack getResult() {
			ItemStack in = input != null ? input : this.getEntry(this.getBottomSlot());
			int amt = TileEntityFermenter.getPlantValue(in);
			if (amt <= 0)
				amt = 183; //very broken
			ItemStack is = output.getItem() == ItemRegistry.YEAST.getItemInstance() ? output : ReikaItemHelper.getSizedItemStack(output, amt);
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
			return output.getItem() == ItemRegistry.YEAST.getItemInstance() ? new ItemStack(Items.sugar) : ItemRegistry.YEAST.getStackOf();
		}

		private List<ItemStack> getBottomSlot() {
			return output.getItem() == ItemRegistry.YEAST.getItemInstance() ? ReikaJavaLibrary.makeListFrom(new ItemStack(Blocks.dirt)) : TileEntityFermenter.getAllValidPlants();
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
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		ReikaGuiAPI.instance.drawTexturedModalRectWithDepth(0, 6, 5, 5, 166, 76, ReikaGuiAPI.NEI_DEPTH);
	}

	@Override
	public void drawForeground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		ReikaTextureHelper.bindTexture(RotaryCraft.class, this.getGuiTexture());
		this.drawExtras(recipe);
		ReikaLiquidRenderer.bindFluidTexture(FluidRegistry.WATER);
		//ReikaGuiAPI.instance.drawTexturedModalRect(0, 0, (int)(ico.getMinU()*16), (int)(ico.getMinV()*16), 16, 16);
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 35, 23, 17), "rcferment"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId != null && outputId.equals("rcferment")) {
			arecipes.add(new FermenterRecipe(ItemRegistry.YEAST.getStackOf()));
			Collection<ItemStack> li = TileEntityFermenter.getAllValidPlants();
			for (ItemStack is : li)
				arecipes.add(new FermenterRecipe(is, ReikaItemHelper.getSizedItemStack(ItemStacks.sludge, TileEntityFermenter.getPlantValue(is))));
		}
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId != null && inputId.equals("rcferment")) {
			this.loadCraftingRecipes(inputId, ingredients);
		}
		super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result.getItem() == ItemRegistry.YEAST.getItemInstance() || ReikaItemHelper.matchStacks(result, ItemStacks.sludge)) {
			arecipes.add(new FermenterRecipe(result));
		}
		if (ReikaItemHelper.matchStacks(result, ItemStacks.ethanolbucket))
			arecipes.add(new FermenterRecipe(ItemStacks.sludge));
	}

	public boolean isEthanolIngredient(ItemStack is) {
		if (is.getItem() == ItemRegistry.YEAST.getItemInstance())
			return true;
		if (is.getItem() == Items.water_bucket)
			return true;
		return TileEntityFermenter.getPlantValue(is) > 0;
	}

	public boolean isYeastIngredient(ItemStack is) {
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.dirt))
			return true;
		if (is.getItem() == Items.sugar)
			return true;
		if (is.getItem() == Items.water_bucket)
			return true;
		return false;
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ItemRegistry.YEAST.matchItem(ingredient)) {
			List<ItemStack> li = TileEntityFermenter.getAllValidPlants();
			for (int i = 0; i < li.size(); i++) {
				arecipes.add(new FermenterRecipe(li.get(i), ItemStacks.sludge.copy()));
			}
		}
		else if (this.isEthanolIngredient(ingredient) || this.isYeastIngredient(ingredient)) {
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
		ReikaGuiAPI.instance.drawTexturedModalRect(18, 16+l, 176, 31+l, 11, 56-l);
	}

}
