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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.Data.ChancedOutputList;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ForestryRecipeHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesCentrifuge
{
	private static final RecipesCentrifuge CentrifugeBase = new RecipesCentrifuge();

	private HashMap<List<Integer>, ArrayList<ItemStack>> recipeList = new HashMap();
	private HashMap<List<Integer>, FluidStack> fluidList = new HashMap();

	private List<ItemStack> outputs = new ArrayList();
	private List<ItemStack> inputs = new ArrayList();

	public static final RecipesCentrifuge recipes()
	{
		return CentrifugeBase;
	}

	private RecipesCentrifuge() {

		this.addRecipe(Item.magmaCream, null, Item.slimeBall, Item.blazePowder);
		this.addRecipe(Item.melon, null, new ItemStack(Item.melonSeeds, 4));
		this.addRecipe(Block.pumpkin, null, new ItemStack(Item.pumpkinSeeds, 12));
		this.addRecipe(Item.wheat, null, new ItemStack(Item.seeds, 4));
		this.addRecipe(Block.gravel, null, new ItemStack(Item.flint), new ItemStack(Block.sand));
		this.addRecipe(ItemStacks.netherrackdust, null, new ItemStack(Item.glowstone), new ItemStack(Item.gunpowder));

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

	private void addRecipe(int itemID, int metadata, ArrayList<ItemStack> out, FluidStack fs)
	{
		List key = Arrays.asList(itemID, metadata);
		recipeList.put(key, out);
		inputs.add(new ItemStack(itemID, 1, metadata));
		for (int i = 0; i < out.size(); i++)
			if (!ReikaItemHelper.listContainsItemStack(outputs, out.get(i)))
				outputs.add(out.get(i));
		if (fs != null)
			fluidList.put(key, fs);
	}

	private void addRecipe(ItemStack in, ArrayList<ItemStack> out, FluidStack fs)
	{
		this.addRecipe(in.itemID, in.getItemDamage(), out, fs);

	}

	private void addRecipe(ItemStack in, FluidStack fs, ItemStack... itemstack)
	{
		this.addRecipe(in.itemID, in.getItemDamage(), fs, itemstack);
	}

	private void addRecipe(Block b, FluidStack fs, ItemStack... itemstack)
	{
		this.addRecipe(b.blockID, fs, itemstack);
	}

	/*
	private void addRecipe(ItemStack item, FluidStack fs, Item... items)
	{
		ItemStack[] iss = new ItemStack[items.length];
		for (int i = 0; i < iss.length; i++) {
			iss[i] = new ItemStack(items[i]);
		}
		this.addRecipe(item, fs, iss);
	}*/

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
		this.addRecipe(item.itemID, fs, itemstack);
	}

	private void addRecipe(int itemID, FluidStack fs, ItemStack... itemstack)
	{
		this.addRecipe(itemID, 0, fs, itemstack);
	}

	private void addRecipe(int itemID, int metadata, FluidStack fs, ItemStack... itemstack)
	{
		ArrayList<ItemStack> li = new ArrayList();
		for (int i = 0; i < itemstack.length; i++) {
			li.add(itemstack[i]);
		}
		this.addRecipe(itemID, metadata, li, fs);
	}

	public ArrayList<ItemStack> getRecipeResult(ItemStack item)
	{
		List<Integer> key = Arrays.asList(item.itemID, item.getItemDamage());
		return item != null ? recipeList.get(key) : null;
	}

	public FluidStack getFluidResult(ItemStack item)
	{
		List<Integer> key = Arrays.asList(item.itemID, item.getItemDamage());
		return item != null ? fluidList.get(key) : null;
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
		for (List<Integer> key : fluidList.keySet()) {
			ItemStack in = new ItemStack(key.get(0), 1, key.get(1));
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
