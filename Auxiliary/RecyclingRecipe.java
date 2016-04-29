/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.CustomToStringRecipe;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;


public class RecyclingRecipe implements IRecipe, CustomToStringRecipe {

	private final IRecipe input;
	public final int scrapValue;

	public RecyclingRecipe(int value, ItemStack... recipe) {
		this(ReikaRecipeHelper.getShapelessRecipeFor(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, value), recipe), value);
	}

	public RecyclingRecipe(ItemStack in, int value) {
		this(value, in);
	}

	public RecyclingRecipe(int value, ItemStack in, int amt) {
		this(value, ReikaArrayHelper.getArrayOf(in, amt));
	}

	public RecyclingRecipe(IRecipe is, int value) {
		input = is;
		scrapValue = value;
	}

	@Override
	public boolean matches(InventoryCrafting ics, World world) {
		return input.matches(ics, world);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ics) {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, scrapValue);//ItemStacks.getScrap(scrapValue);
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, scrapValue);//ItemStacks.scrap;
	}

	public ArrayList<ItemStack> getSplitOutput() {
		ArrayList<ItemStack> li = new ArrayList();
		int val = scrapValue;
		while (val > 0) {
			int num = Math.min(val, ItemStacks.scrap.getMaxStackSize());
			li.add(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, num));
			val -= num;
		}
		return li;
	}

	@Override
	public String toString() {
		return scrapValue+" X scrap = "+ReikaRecipeHelper.toString(input);
	}

}
