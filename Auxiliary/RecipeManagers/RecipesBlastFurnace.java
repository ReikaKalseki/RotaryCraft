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
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;

public class RecipesBlastFurnace
{
	private static final RecipesBlastFurnace BlastFurnaceBase = new RecipesBlastFurnace();

	private ArrayList<BlastRecipe> recipeList = new ArrayList();

	public static final RecipesBlastFurnace getRecipes()
	{
		return BlastFurnaceBase;
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

	public static final class BlastRecipe {
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
	}

	private RecipesBlastFurnace()
	{
		BlastInput in1 = new BlastInput(Item.coal, 100, 1);
		BlastInput in2 = new BlastInput(Item.gunpowder, 1.8F, 1);
		BlastInput in3 = new BlastInput(Block.sand, 0.1F, 1);
		BlastRecipe hsla = new BlastRecipe(in1, in2, in3, Item.ingotIron, ItemStacks.steelingot, true, TileEntityBlastFurnace.SMELT_XP, TileEntityBlastFurnace.SMELTTEMP);
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
		ArrayList li = new ArrayList();
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
		ArrayList li = new ArrayList();
		for (int i = 0; i < recipeList.size(); i++) {
			BlastRecipe r = recipeList.get(i);
			if (ReikaItemHelper.matchStacks(is, r.outputItem()))
				li.add(r);
		}
		return li;
	}
}
