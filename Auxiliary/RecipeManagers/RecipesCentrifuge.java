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

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.ChancedOutputList;
import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ForestryRecipeHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipesCentrifuge
{
	private static final RecipesCentrifuge CentrifugeBase = new RecipesCentrifuge();

	private ItemHashMap<ArrayList<ItemStack>> recipeList = new ItemHashMap();
	private ItemHashMap<FluidStack> fluidList = new ItemHashMap();

	private List<ItemStack> outputs = new ArrayList();
	private List<ItemStack> inputs = new ArrayList();

	public static final RecipesCentrifuge recipes()
	{
		return CentrifugeBase;
	}

	private RecipesCentrifuge() {

		this.addRecipe(Items.magma_cream, null, Items.slime_ball, Items.blaze_powder);
		this.addRecipe(Items.melon, null, new ItemStack(Items.melon_seeds, 4));
		this.addRecipe(Blocks.pumpkin, null, new ItemStack(Items.pumpkin_seeds, 12));
		this.addRecipe(Items.wheat, null, new ItemStack(Items.wheat_seeds, 4));
		this.addRecipe(Blocks.gravel, null, new ItemStack(Items.flint), new ItemStack(Blocks.sand));
		this.addRecipe(ItemStacks.netherrackdust, null, new ItemStack(Items.glowstone_dust), new ItemStack(Items.gunpowder));

		if (ModList.FORESTRY.isLoaded()) {
			HashMap<ItemStack, ChancedOutputList> centrifuge = ForestryRecipeHelper.getInstance().getCentrifugeRecipes();
			for (ItemStack in : centrifuge.keySet()) {
				ArrayList<ItemStack> out = new ArrayList();
				out.addAll(centrifuge.get(in).getAllItems());
				this.addRecipe(in, out, null);
			}
		}

		this.addRecipe(ItemStacks.slipperyComb, new FluidStack(FluidRegistry.getFluid("lubricant"), 50), ItemStacks.slipperyPropolis);
		this.addRecipe(ItemStacks.slipperyPropolis, new FluidStack(FluidRegistry.getFluid("lubricant"), 150));
	}

	private void addRecipe(ItemStack in, ArrayList<ItemStack> out, FluidStack fs)
	{
		recipeList.put(in, out);
		inputs.add(in);
		for (int i = 0; i < out.size(); i++)
			if (!ReikaItemHelper.listContainsItemStack(outputs, out.get(i)))
				outputs.add(out.get(i));
		if (fs != null)
			fluidList.put(in, fs);
	}

	private void addRecipe(Item in, ArrayList<ItemStack> out, FluidStack fs)
	{
		this.addRecipe(new ItemStack(in), out, fs);

	}

	private void addRecipe(ItemStack in, FluidStack fs, ItemStack... itemstack)
	{
		ArrayList<ItemStack> li = new ArrayList();
		for (int i = 0; i < itemstack.length; i++)
			li.add(itemstack[i]);
		this.addRecipe(in, li, fs);
	}

	private void addRecipe(Block b, FluidStack fs, ItemStack... itemstack)
	{
		this.addRecipe(new ItemStack(b), fs, itemstack);
	}

	private void addRecipe(Item item, FluidStack fs, Item... items)
	{
		ItemStack[] iss = new ItemStack[items.length];
		for (int i = 0; i < iss.length; i++) {
			iss[i] = new ItemStack(items[i]);
		}
		this.addRecipe(item, fs, iss);
	}

	private void addRecipe(Item item, FluidStack fs, ItemStack... itemstack)
	{
		this.addRecipe(new ItemStack(item), fs, itemstack);
	}

	public ArrayList<ItemStack> getRecipeResult(ItemStack item)
	{
		return item != null ? recipeList.get(item) : null;
	}

	public FluidStack getFluidResult(ItemStack item)
	{
		return item != null ? fluidList.get(item) : null;
	}

	public ArrayList<ItemStack> getSources(ItemStack result) {
		ArrayList<ItemStack> li = new ArrayList();
		for (int i = 0; i < inputs.size(); i++) {
			ItemStack in = inputs.get(i);
			ArrayList<ItemStack> out = this.getRecipeResult(in);
			if (ReikaItemHelper.listContainsItemStack(out, result))
				li.add(in);
		}
		return li;
	}

	public ArrayList<ItemStack> getSources(Fluid result) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack in : fluidList.keySet()) {
			FluidStack fs = this.getFluidResult(in);
			if (fs != null && fs.getFluid().equals(result))
				li.add(in);
		}
		return li;
	}

	public boolean isProduct(ItemStack result) {
		return ReikaItemHelper.listContainsItemStack(outputs, result);
	}

	public boolean isCentrifugable(ItemStack ingredient) {
		return this.getRecipeResult(ingredient) != null;
	}
}