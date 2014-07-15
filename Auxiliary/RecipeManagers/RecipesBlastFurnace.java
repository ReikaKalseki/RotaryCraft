/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import Reika.DragonAPI.Instantiable.RecipePattern;
import Reika.DragonAPI.Instantiable.Data.ImmutableList;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;

public class RecipesBlastFurnace
{
	private static final RecipesBlastFurnace BlastFurnaceBase = new RecipesBlastFurnace();

	private ImmutableList<BlastRecipe> recipeList = new ImmutableList();
	private ImmutableList<BlastCrafting> craftingList = new ImmutableList();

	public static final RecipesBlastFurnace getRecipes()
	{
		return BlastFurnaceBase;
	}

	public void addRecipe(ItemStack out, int temperature, IRecipe in, int speed, float xp) {
		BlastCrafting c = new BlastCrafting(out, temperature, speed, in, xp);
		craftingList.add(c);
	}

	public void add3x3Crafting(ItemStack out, int temperature, int speed, float xp, Object... in) {
		ShapedRecipes r = ReikaRecipeHelper.getShapedRecipeFor(out, in);
		BlastCrafting c = new BlastCrafting(out, temperature, speed, r, xp);
		craftingList.add(c);
	}

	public static final class BlastCrafting implements BlastFurnacePattern {

		public final int temperature;
		private final IRecipe recipe;
		private final ItemStack output;
		public final int speed;
		public final float xp;

		/*
		public BlastCrafting(int width, int height, ItemStack[] input, ItemStack out, int temp) {
			super(width, height, input, out);
			temperature = temp;
			output = out.copy();
		}*/

		public BlastCrafting(ItemStack out, int temp, int speed, IRecipe ir, float xp) {
			recipe = ir;
			output = out;
			temperature = temp;
			this.speed = speed;
			this.xp = xp;
		}

		public final ItemStack outputItem() {
			return output.copy();
		}

		public BlastCrafting copy() {
			//return new BlastCrafting(recipeWidth, recipeHeight, recipeItems, output, temperature);
			return new BlastCrafting(output, temperature, speed, recipe, xp);
		}

		public boolean matches(RecipePattern ic, int temperature) {
			return temperature >= this.temperature && recipe.matches(ic, null);
		}

		public boolean usesItem(ItemStack is) {
			return ReikaItemHelper.listContainsItemStack(ReikaRecipeHelper.getAllItemsInRecipe(recipe), is);
		}

		public ItemStack[] getArrayForDisplay() {
			ItemStack[] iss = new ItemStack[9];
			ReikaRecipeHelper.copyRecipeToItemStackArray(iss, recipe);
			return iss;
		}

		@Override
		public boolean isValidInputForSlot(int slot, ItemStack is) {/*
			if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe) {
				ArrayList<ItemStack> li = ReikaRecipeHelper.getAllItemsInRecipe(recipe);
			}
			else if (recipe instanceof ShapedOreRecipe || recipe instanceof ShapedRecipes) {

			}*/
			if (slot == TileEntityBlastFurnace.SLOT_1 || slot > 9)
				return false;
			ItemStack[] items = new ItemStack[9];
			ReikaRecipeHelper.copyRecipeToItemStackArray(items, recipe);
			return ReikaItemHelper.matchStacks(is, items[slot-1]);
		}

		@Override
		public float getXPPerProduct() {
			return xp;
		}
	}

	public static interface BlastFurnacePattern {

		public ItemStack outputItem();

		public boolean isValidInputForSlot(int slot, ItemStack is);

		public float getXPPerProduct();
	}

	public static final class BlastInput {
		private final ItemStack item;
		public final float chanceToUse;
		public final int numberToUse;

		private BlastInput(Block in, float chance, int toDecr) {
			this(new ItemStack(in), chance, toDecr);
		}

		private BlastInput(Item in, float chance, int toDecr) {
			this(new ItemStack(in), chance, toDecr);
		}

		private BlastInput(ItemStack in, float chance, int toDecr) {
			item = in;
			chanceToUse = chance/100F;
			numberToUse = toDecr;
		}

		public ItemStack getItem() {
			return item != null ? ReikaItemHelper.getSizedItemStack(item, numberToUse) : null;
		}

		@Override
		public String toString() {
			return item+" x"+numberToUse+"@"+chanceToUse+"%";
		}

		public boolean match(ItemStack in) {
			return item == null ? in == null : ReikaItemHelper.matchStacks(in, this.getItem()) && in.stackSize >= numberToUse;
		}
	}

	public static final class BlastRecipe implements BlastFurnacePattern {
		public final BlastInput primary;
		public final BlastInput secondary;
		public final BlastInput tertiary;
		private final ItemStack main;
		public final int mainRequired;
		private boolean matchNumberExactly;
		private final ItemStack output;
		public final boolean hasBonus;
		public final float xp;
		public final int temperature;

		private BlastRecipe(BlastInput in1, BlastInput in2, BlastInput in3, Item main, ItemStack out, boolean bonus, float xp, int temp) {
			this(in1, in2, in3, new ItemStack(main), out, bonus, xp, temp);
		}

		private BlastRecipe(BlastInput in1, BlastInput in2, BlastInput in3, Block main, ItemStack out, boolean bonus, float xp, int temp) {
			this(in1, in2, in3, new ItemStack(main), out, bonus, xp, temp);
		}

		private BlastRecipe(BlastInput in1, BlastInput in2, BlastInput in3, ItemStack main, ItemStack out, boolean bonus, float xp, int temp) {
			this(in1, in2, in3, main, 1, out, bonus, xp, temp);
			matchNumberExactly = false;
		}

		private BlastRecipe(BlastInput in1, BlastInput in2, BlastInput in3, ItemStack main, int req, ItemStack out, boolean bonus, float xp, int temp) {
			primary = in1;
			secondary = in2;
			tertiary = in3;
			hasBonus = bonus;
			this.main = main;
			mainRequired = req;
			this.xp = xp;
			output = out;
			matchNumberExactly = true;
			temperature = temp;
		}

		public ItemStack mainItem() {
			return main != null ? main.copy() : null;
		}

		public ItemStack outputItem() {
			return output != null ? output.copy() : null;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(primary+" + ");
			sb.append(secondary+" + ");
			sb.append(tertiary+" + ");
			sb.append(matchNumberExactly ? "== " : ">= ");
			sb.append(mainRequired+" of ");
			sb.append(main);
			sb.append(" >> ");
			sb.append(output);
			if (hasBonus)
				sb.append("*");
			return sb.toString();
		}

		public int getNumberProduced(int main) {
			return mainRequired > 1 ? main/mainRequired : main;
		}

		public boolean matchInputExactly() {
			return matchNumberExactly;
		}

		public ArrayList<Integer> getValidInputNumbers() {
			ArrayList<Integer> li = new ArrayList();
			for (int i = mainRequired; i <= 9; i += mainRequired) {
				if (i == mainRequired || !this.matchInputExactly())
					li.add(i);
			}
			return li;
		}

		@Override
		public boolean isValidInputForSlot(int slot, ItemStack is) {
			if (slot == TileEntityBlastFurnace.SLOT_1)
				return ReikaItemHelper.matchStacks(is, primary.getItem());
			if (slot == TileEntityBlastFurnace.SLOT_2)
				return ReikaItemHelper.matchStacks(is, secondary.getItem());
			if (slot == TileEntityBlastFurnace.SLOT_3)
				return ReikaItemHelper.matchStacks(is, tertiary.getItem());
			if (slot >= 1 && slot < 10)
				return ReikaItemHelper.matchStacks(is, main);
			return false;
		}

		@Override
		public float getXPPerProduct() {
			return xp;
		}
	}

	private RecipesBlastFurnace()
	{
		BlastInput in1 = new BlastInput(Item.coal, 100, 1);
		BlastInput in2 = new BlastInput(Item.gunpowder, 3.6F, 1);
		BlastInput in3 = new BlastInput(Block.sand, 0.2F, 1);
		BlastRecipe hsla = new BlastRecipe(in1, in2, in3, Item.ingotIron, ItemStacks.steelingot, false, TileEntityBlastFurnace.SMELT_XP, TileEntityBlastFurnace.SMELTTEMP);
		recipeList.add(hsla);

		in1 = new BlastInput(ItemStacks.bedrockdust, 100, 4);
		in2 = new BlastInput((ItemStack)null, 0, 1);
		in3 = new BlastInput((ItemStack)null, 0, 1);
		BlastRecipe bedrock = new BlastRecipe(in1, in2, in3, ItemStacks.steelingot, 1, ItemStacks.bedingot, false, 0, TileEntityBlastFurnace.BEDROCKTEMP);
		recipeList.add(bedrock);

		in1 = new BlastInput((ItemStack)null, 0, 1);
		in2 = new BlastInput((ItemStack)null, 0, 1);
		in3 = new BlastInput((ItemStack)null, 0, 1);
		BlastRecipe scrap = new BlastRecipe(in1, in2, in3, ItemStacks.scrap, 9, ItemStacks.steelingot, false, 0, TileEntityBlastFurnace.SMELTTEMP);
		recipeList.add(scrap);

		in1 = new BlastInput(ItemStacks.coke, 100, 1);
		in2 = new BlastInput(Item.gunpowder, 1.8F, 1);
		in3 = new BlastInput(Block.sand, 0.1F, 1);
		BlastRecipe hsla2 = new BlastRecipe(in1, in2, in3, Item.ingotIron, ItemStacks.steelingot, true, TileEntityBlastFurnace.SMELT_XP, TileEntityBlastFurnace.SMELTTEMP);
		recipeList.add(hsla2);

		in1 = new BlastInput((ItemStack)null, 0, 1);
		in2 = new BlastInput((ItemStack)null, 0, 1);
		in3 = new BlastInput((ItemStack)null, 0, 1);
		BlastRecipe coke = new BlastRecipe(in1, in2, in3, Item.coal, ItemStacks.coke, false, 0, 400);
		recipeList.add(coke);
	}

	public BlastCrafting getCrafting(ItemStack[] main, int temp) {
		RecipePattern ic = new RecipePattern(main);
		for (int i = 0; i < craftingList.size(); i++) {
			BlastCrafting c = craftingList.get(i);
			if (c.matches(ic, temp)) {
				return c;
			}
		}
		return null;
	}

	public BlastRecipe getRecipe(ItemStack in1, ItemStack in2, ItemStack in3, ItemStack[] main, int temp) {
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (temp >= r.temperature && this.matchRecipe(r, in1, in2, in3, main)) //allows for two recipes with same items but diff temp
				return r;
		}
		return null;
	}

	private boolean matchRecipe(BlastRecipe r, ItemStack in1, ItemStack in2, ItemStack in3, ItemStack[] main) {
		if (!r.primary.match(in1))
			return false;
		if (!r.secondary.match(in2))
			return false;
		if (!r.tertiary.match(in3))
			return false;
		ItemStack main1 = r.mainItem();
		int num = 0;
		for (int i = 0; i < main.length; i++) {
			ItemStack is = main[i];
			if (is != null) {
				if (ReikaItemHelper.matchStacks(is, main1)) {
					num++;
				}
				else {
					return false;
				}
			}
		}
		return r.matchNumberExactly ? num == r.mainRequired : num >= r.mainRequired;
	}

	public boolean isProduct(ItemStack result) {
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(result, r.outputItem()))
				return true;
		}
		return false;
	}

	public boolean isInput(ItemStack ingredient) {
		return this.getInputTypeForItem(ingredient) >= 0;
	}

	public int getInputTypeForItem(ItemStack is) {
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(is, r.mainItem()))
				return 0;
			if (ReikaItemHelper.matchStacks(is, r.primary.getItem()))
				return 1;
			if (ReikaItemHelper.matchStacks(is, r.secondary.getItem()))
				return 2;
			if (ReikaItemHelper.matchStacks(is, r.tertiary.getItem()))
				return 3;
		}
		return -1;
	}

	public ArrayList<BlastRecipe> getAllRecipesUsing(ItemStack is) {
		ArrayList<BlastRecipe> li = new ArrayList();
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(is, r.mainItem()))
				li.add(r);
			else if (ReikaItemHelper.matchStacks(is, r.primary.getItem()))
				li.add(r);
			else if (ReikaItemHelper.matchStacks(is, r.secondary.getItem()))
				li.add(r);
			else if (ReikaItemHelper.matchStacks(is, r.tertiary.getItem()))
				li.add(r);
		}
		return li;
	}

	public ArrayList<BlastRecipe> getAllRecipesMaking(ItemStack is) {
		ArrayList<BlastRecipe> li = new ArrayList();
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(is, r.outputItem()))
				li.add(r);
		}
		return li;
	}

	public ArrayList<BlastCrafting> getAllCraftingUsing(ItemStack is) {
		ArrayList<BlastCrafting> li = new ArrayList();
		for (int i = 0; i < craftingList.size(); i++) {
			BlastCrafting r = craftingList.get(i);
			if (r.usesItem(is)) {
				li.add(r.copy());
			}
			/*
			for (int k = 0; k < r.recipeItems.length; k++) {
				if (ReikaItemHelper.matchStacks(is, r.recipeItems[k])) {
					li.add(r.copy());
					k = r.recipeItems.length;
				}
			}
			 */
		}
		return li;
	}

	public ArrayList<BlastCrafting> getAllCraftingMaking(ItemStack is) {
		ArrayList<BlastCrafting> li = new ArrayList();
		for (int i = 0; i < craftingList.size(); i++) {
			BlastCrafting r = craftingList.get(i);
			if (ReikaItemHelper.matchStacks(is, r.outputItem()))
				li.add(r.copy());
		}
		return li;
	}
}
