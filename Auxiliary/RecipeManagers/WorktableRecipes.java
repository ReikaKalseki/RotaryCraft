/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.Data.ImmutableList;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorktableRecipes
{
	private static final WorktableRecipes instance = new WorktableRecipes();

	private ImmutableList<WorktableRecipe> recipes = new ImmutableList();
	private ImmutableList<IRecipe> display = new ImmutableList();

	private final RecipeSorter sorter = new RecipeSorter();

	public static final WorktableRecipes getInstance()
	{
		return instance;
	}

	public void addRecipe(IRecipe recipe) {
		recipes.add(new WorktableRecipe(recipe));
		display.add(recipe);
	}

	private WorktableRecipes()
	{
		//Collections.sort(recipes, sorter);
	}

	public ShapedRecipes addRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
	{
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		//ReikaJavaLibrary.spamConsole(Arrays.toString(par2ArrayOfObj));
		if (par2ArrayOfObj[i] instanceof String[])
		{
			String[] astring = ((String[])par2ArrayOfObj[i++]);

			for (int l = 0; l < astring.length; ++l)
			{
				String s1 = astring[l];
				++k;
				j = s1.length();
				s = s + s1;
			}
		}
		else
		{
			while (par2ArrayOfObj[i] instanceof String)
			{
				String s2 = (String)par2ArrayOfObj[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		HashMap hashmap;

		for (hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2)
		{
			Character character = (Character)par2ArrayOfObj[i];
			ItemStack itemstack1 = null;

			if (par2ArrayOfObj[i + 1] instanceof Item)
			{
				itemstack1 = new ItemStack((Item)par2ArrayOfObj[i + 1]);
			}
			else if (par2ArrayOfObj[i + 1] instanceof Block)
			{
				itemstack1 = new ItemStack((Block)par2ArrayOfObj[i + 1], 1, 32767);
			}
			else if (par2ArrayOfObj[i + 1] instanceof ItemStack)
			{
				itemstack1 = (ItemStack)par2ArrayOfObj[i + 1];
			}

			//ReikaJavaLibrary.pConsole(character+" -> "+itemstack1);
			hashmap.put(character, itemstack1);
		}

		ItemStack[] aitemstack = new ItemStack[j * k];

		for (int i1 = 0; i1 < j * k; ++i1)
		{
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c0)))
			{
				//ReikaJavaLibrary.spamConsole(c0+":   "+(hashmap.get(Character.valueOf(c0))));
				aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
			}
			else
			{
				aitemstack[i1] = null;
			}
		}

		ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, par1ItemStack);
		this.addRecipe(shapedrecipes);
		return shapedrecipes;
	}

	public void addShapelessRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
	{
		ArrayList arraylist = new ArrayList();
		Object[] aobject = par2ArrayOfObj;
		int i = par2ArrayOfObj.length;

		for (int j = 0; j < i; ++j)
		{
			Object object1 = aobject[j];

			if (object1 instanceof ItemStack)
			{
				arraylist.add(((ItemStack)object1).copy());
			}
			else if (object1 instanceof Item)
			{
				arraylist.add(new ItemStack((Item)object1));
			}
			else
			{
				if (!(object1 instanceof Block))
				{
					throw new RuntimeException("Invalid shapeless recipe!");
				}

				arraylist.add(new ItemStack((Block)object1));
			}
		}

		this.addRecipe(new ShapelessRecipes(par1ItemStack, arraylist));
	}

	public ItemStack findMatchingRecipe(InventoryCrafting ic, World par2World)
	{
		int i = 0;
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;
		int j;

		for (j = 0; j < ic.getSizeInventory(); ++j)
		{
			ItemStack itemstack2 = ic.getStackInSlot(j);

			if (itemstack2 != null)
			{
				if (i == 0)
				{
					itemstack = itemstack2;
				}

				if (i == 1)
				{
					itemstack1 = itemstack2;
				}

				++i;
			}
		}

		for (WorktableRecipe wr : recipes) {
			IRecipe ir = wr.recipe;

			if (ir.matches(ic, par2World))
				return wr.output.copy();//ir.getCraftingResult(ic);
		}

		return null;
	}

	@SideOnly(Side.CLIENT)
	public List<WorktableRecipe> getRecipeListCopy()
	{
		return Collections.unmodifiableList(recipes);
	}

	@SideOnly(Side.CLIENT)
	public List<IRecipe> getDisplayList()
	{
		return Collections.unmodifiableList(display);
	}

	public IRecipe getInputRecipe(ItemStack is) {
		for (WorktableRecipe wr : recipes) {
			IRecipe ir = wr.recipe;
			ItemStack is2 = ir.getRecipeOutput();
			if (ReikaItemHelper.matchStacks(is, is2) && is.stackSize >= is2.stackSize) {
				return ir;
			}
		}
		return null;
	}

	public static final class WorktableRecipe {

		private final IRecipe recipe;
		private final ItemStack output;

		private WorktableRecipe(IRecipe ir) {
			if (ir == null)
				throw new IllegalArgumentException("Invalid worktable recipe: Null!");
			if (ir.getRecipeOutput() == null)
				throw new IllegalArgumentException("Invalid worktable recipe: No output!");
			recipe = ir;
			output = ir.getRecipeOutput();
		}

		public ItemStack getOutput() {
			return output.copy();
		}

		public boolean containsItem(ItemStack is) {
			return ReikaRecipeHelper.recipeContains(recipe, is);
		}

		@SideOnly(Side.CLIENT)
		public ItemStack[] getDisplayArray() {
			return ReikaRecipeHelper.getPermutedRecipeArray(recipe);
		}

	}

	private static class RecipeSorter implements Comparator<WorktableRecipe> {

		private RecipeSorter()
		{

		}

		public int compare(WorktableRecipe ir, WorktableRecipe ir2)
		{
			return ir.recipe instanceof ShapelessRecipes && ir2.recipe instanceof ShapedRecipes ? 1 : (ir2.recipe instanceof ShapelessRecipes && ir.recipe instanceof ShapedRecipes ? -1 : (ir2.recipe.getRecipeSize() < ir.recipe.getRecipeSize() ? -1 : (ir2.recipe.getRecipeSize() > ir.recipe.getRecipeSize() ? 1 : 0)));
		}
	}
}
