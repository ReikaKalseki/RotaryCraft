/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.Data.ImmutableList;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;

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

public class WorktableRecipes
{
	/** The static instance of this class */
	private static final WorktableRecipes instance = new WorktableRecipes();

	/** A list of all the recipes added */
	private ImmutableList<IRecipe> recipes = new ImmutableList();

	/**
	 * Returns the static instance of this class
	 */
	public static final WorktableRecipes getInstance()
	{
		return instance;
	}

	public static void addRecipe(IRecipe recipe)
	{
		getInstance().recipes.add(recipe);
	}

	private WorktableRecipes()
	{

		Collections.sort(recipes, new RecipeSorter(this));
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
		recipes.add(shapedrecipes);
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

		recipes.add(new ShapelessRecipes(par1ItemStack, arraylist));
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

		if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
		{
			Item item = itemstack.getItem();
			int k = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
			int l = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
			int i1 = k + l + item.getMaxDamage() * 5 / 100;
			int j1 = item.getMaxDamage() - i1;

			if (j1 < 0)
			{
				j1 = 0;
			}

			return new ItemStack(itemstack.getItem(), 1, j1);
		}
		else
		{
			for (j = 0; j < recipes.size(); ++j)
			{
				IRecipe irecipe = recipes.get(j);

				if (irecipe.matches(ic, par2World))
				{
					return irecipe.getCraftingResult(ic);
				}
			}

			return null;
		}
	}

	/**
	 * returns the List<> of all recipes in copy to disallow editing<br>
	 * This one is for you, NEI.
	 */
	public List getRecipeListCopy()
	{
		return ReikaJavaLibrary.copyList(recipes);
	}

	public IRecipe getInputRecipe(ItemStack is) {
		for (int i = 0; i < recipes.size(); i++) {
			IRecipe ir = recipes.get(i);
			ItemStack is2 = ir.getRecipeOutput();
			if (ReikaItemHelper.matchStacks(is, is2) && is.stackSize >= is2.stackSize) {
				return ir;
			}
		}
		return null;
	}
}

class RecipeSorter implements Comparator
{
	final WorktableRecipes worktableRecipes;

	RecipeSorter(WorktableRecipes par1WorktableRecipes)
	{
		worktableRecipes = par1WorktableRecipes;
	}

	public int compareRecipes(IRecipe ir, IRecipe ir2)
	{
		return ir instanceof ShapelessRecipes && ir2 instanceof ShapedRecipes ? 1 : (ir2 instanceof ShapelessRecipes && ir instanceof ShapedRecipes ? -1 : (ir2.getRecipeSize() < ir.getRecipeSize() ? -1 : (ir2.getRecipeSize() > ir.getRecipeSize() ? 1 : 0)));
	}

	public int compare(Object obj1, Object obj2)
	{
		return this.compareRecipes((IRecipe)obj1, (IRecipe)obj2);
	}
}