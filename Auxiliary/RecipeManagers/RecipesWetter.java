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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWayMap;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;

public class RecipesWetter
{
	private static final RecipesWetter WetterBase = new RecipesWetter();

	private final ItemHashMap<OneWayMap<Fluid, WettingRecipe>> recipeList = new ItemHashMap().setOneWay();
	private final HashSet<String> fluids = new HashSet();

	public static final RecipesWetter getRecipes()
	{
		return WetterBase;
	}

	private RecipesWetter() {
		this.addRecipe(new ItemStack(Blocks.sand), "lubricant", 500, new ItemStack(Blocks.soul_sand), 200);
		this.addRecipe(new ItemStack(Blocks.sand), "oil", 125, new ItemStack(Blocks.soul_sand), 50);
		this.addRecipe(new ItemStack(Blocks.cobblestone), "jet fuel", 20, new ItemStack(Blocks.netherrack), 80);
	}

	private void addRecipe(ItemStack in, Fluid f, int amount, ItemStack out, int time)
	{
		WettingRecipe wr = new WettingRecipe(in, new FluidStack(f, amount), out, time);
		OneWayMap<Fluid, WettingRecipe> map = recipeList.get(in);
		if (map == null) {
			map = new OneWayMap();
			recipeList.put(in, map);
		}
		map.put(f, wr);
		fluids.add(f.getName());
	}

	private void addRecipe(ItemStack in, String s, int amount, ItemStack out, int time) {
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(in, f, amount, out, time);
	}

	public WettingRecipe getRecipe(ItemStack is, FluidStack liquid) {
		OneWayMap<Fluid, WettingRecipe> map = recipeList.get(is);
		if (map == null)
			return null;
		Fluid f = liquid.getFluid();
		WettingRecipe wr = map.get(f);
		return liquid.amount >= wr.fluid.amount ? wr : null;
	}

	public boolean isValidFluid(Fluid f) {
		return fluids.contains(f.getName());
	}

	public Collection<WettingRecipe> getAllRecipes() {
		Collection<WettingRecipe> c = new ArrayList();
		for (ItemStack is : recipeList.keySet()) {
			OneWayMap<Fluid, WettingRecipe> map = recipeList.get(is);
			c.addAll(map.values());
		}
		return c;
	}

	public boolean isWettable(ItemStack is) {
		return recipeList.containsKey(is);
	}

	public boolean isWettableWith(ItemStack is, Fluid f) {
		OneWayMap<Fluid, WettingRecipe> c = recipeList.get(is);
		return c != null && c.containsKey(f);
	}

	public static class WettingRecipe {

		public final int duration;
		private final FluidStack fluid;
		private final ItemStack input;
		private final ItemStack output;

		private WettingRecipe(ItemStack in, FluidStack fin, ItemStack out, int time) {
			input = in;
			output = out;
			fluid = fin;
			duration = time;
		}

		public FluidStack getFluid() {
			return fluid.copy();
		}

		public ItemStack getInput() {
			return input.copy();
		}

		public ItemStack getOutput() {
			return output.copy();
		}

	}

	public Collection<WettingRecipe> getRecipesByResult(ItemStack result) {
		Collection<WettingRecipe> c = new ArrayList();
		for (WettingRecipe r : this.getAllRecipes()) {
			if (ReikaItemHelper.matchStacks(r.output, result)) {
				c.add(r);
			}
		}
		return c;
	}

	public Collection<WettingRecipe> getRecipesByFluid(Fluid fluid) {
		Collection<WettingRecipe> c = new ArrayList();
		for (WettingRecipe r : this.getAllRecipes()) {
			if (r.fluid.getFluid() == fluid) {
				c.add(r);
			}
		}
		return c;
	}

	public Collection<WettingRecipe> getRecipesUsing(ItemStack ingredient) {
		OneWayMap<Fluid, WettingRecipe> map = recipeList.get(ingredient);
		return map != null ? Collections.unmodifiableCollection(map.values()) : null;
	}
}
