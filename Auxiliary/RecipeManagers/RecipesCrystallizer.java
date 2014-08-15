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

import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipesCrystallizer
{
	private static final RecipesCrystallizer CrystallizerBase = new RecipesCrystallizer();

	/** The list of smelting results. */
	private HashMap<Fluid, ItemStack> recipeList = new HashMap();
	private HashMap<Fluid, Integer> amounts = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesCrystallizer getRecipes()
	{
		return CrystallizerBase;
	}

	private RecipesCrystallizer()
	{
		this.addRecipe("ender", 250, new ItemStack(Items.ender_pearl));
		this.addRecipe("redstone", 100, new ItemStack(Items.redstone));
		this.addRecipe("glowstone", 250, new ItemStack(Items.glowstone_dust));
		this.addRecipe("coal", 100, new ItemStack(Items.coal));

		this.addRecipe(FluidRegistry.WATER, 1000, new ItemStack(Blocks.ice));
		this.addRecipe(FluidRegistry.LAVA, 1000, new ItemStack(Blocks.stone));

		this.addRecipe("rc ethanol", 100, ItemRegistry.ETHANOL.getStackOf());
	}

	private void addRecipe(Fluid f, int amount, ItemStack out)
	{
		recipeList.put(f, out);
		amounts.put(f, amount);
	}

	private void addRecipe(String s, int amount, ItemStack out)
	{
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(f, amount, out);
	}

	public ItemStack getFreezingResult(FluidStack liquid)
	{
		Fluid f = liquid.getFluid();
		if (amounts.containsKey(f)) {
			int req = amounts.get(f);
			if (req > liquid.amount)
				return null;
			return recipeList.get(f);
		}
		else
			return null;
	}

	public Fluid getRecipe(ItemStack result) {
		for (Fluid f : recipeList.keySet()) {
			ItemStack is = recipeList.get(f);
			if (ReikaItemHelper.matchStacks(result, is))
				return f;
		}
		return null;
	}

	public int getRecipeConsumption(ItemStack result) {
		for (Fluid f : recipeList.keySet()) {
			ItemStack is = recipeList.get(f);
			if (ReikaItemHelper.matchStacks(result, is))
				return amounts.get(f);
		}
		return 0;
	}
}