/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipesCrystallizer
{
	private static final RecipesCrystallizer CrystallizerBase = new RecipesCrystallizer();

	/** The list of smelting results. */
	private HashMap<FluidStack, ItemStack> recipeList = new HashMap();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final RecipesCrystallizer getRecipes()
	{
		return CrystallizerBase;
	}

	private RecipesCrystallizer()
	{

	}

	public void addRecipe(FluidStack in, ItemStack out)
	{
		this.addRecipe("ender", 250, new ItemStack(Item.enderPearl));
		this.addRecipe("redstone", 100, new ItemStack(Item.redstone));
		this.addRecipe("glowstone", 250, new ItemStack(Item.glowstone));
		this.addRecipe("coal", 100, new ItemStack(Item.coal));

		this.addRecipe(FluidRegistry.WATER, 1000, new ItemStack(Block.ice));
		this.addRecipe(FluidRegistry.LAVA, 1000, new ItemStack(Block.stone));
	}

	public void addRecipe(Fluid f, int amount, ItemStack out)
	{
		this.addRecipe(new FluidStack(f, amount), out);
	}

	public void addRecipe(String s, int amount, ItemStack out)
	{
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(new FluidStack(f, amount), out);
	}

	/**
	 * Used to get the resulting ItemStack form a source ItemStack
	 * @param item The Source ItemStack
	 * @return The result ItemStack
	 */
	public ItemStack getSmeltingResult(FluidStack liquid)
	{
		for (FluidStack fs : recipeList.keySet()) {

		}
		return null;
	}

	public ItemStack getSource(ItemStack result) {
		for (FluidStack fs : recipeList.keySet()) {

		}
		return null;
	}
}
