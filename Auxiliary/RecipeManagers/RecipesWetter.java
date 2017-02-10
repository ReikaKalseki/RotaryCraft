/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.WetterManager;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class RecipesWetter extends RecipeHandler implements WetterManager {

	private static final RecipesWetter WetterBase = new RecipesWetter();

	private final ItemHashMap<HashMap<Fluid, WettingRecipe>> recipeList = new ItemHashMap();
	private final HashSet<String> fluids = new HashSet();

	public static final RecipesWetter getRecipes()
	{
		return WetterBase;
	}

	private RecipesWetter() {
		super(MachineRegistry.WETTER);
		RecipeInterface.wetter = this;

		this.addRecipe(new ItemStack(Blocks.sand), "rc lubricant", 500, new ItemStack(Blocks.soul_sand), 200, RecipeLevel.PROTECTED);
		this.addRecipe(new ItemStack(Blocks.sand), "oil", 125, new ItemStack(Blocks.soul_sand), 50, RecipeLevel.MODINTERACT);
		this.addRecipe(new ItemStack(Blocks.cobblestone), "rc jet fuel", 20, new ItemStack(Blocks.netherrack), 80, RecipeLevel.PERIPHERAL);
		this.addRecipe(new ItemStack(Blocks.cobblestone), "ender", 50, new ItemStack(Blocks.end_stone), 80, RecipeLevel.MODINTERACT);
	}

	public void addAPIRecipe(ItemStack in, Fluid f, int amount, ItemStack out, int time) {
		this.addRecipe(in, f, amount, out, time, RecipeLevel.API);
	}

	private void addRecipe(ItemStack in, String s, int amount, ItemStack out, int time, RecipeLevel rl) {
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(in, f, amount, out, time, rl);
	}

	private void addRecipe(ItemStack in, Fluid f, int amount, ItemStack out, int time, RecipeLevel rl) {
		WettingRecipe wr = new WettingRecipe(in, new FluidStack(f, amount), out, time);
		HashMap<Fluid, WettingRecipe> map = recipeList.get(in);
		if (map == null) {
			map = new HashMap();
			recipeList.put(in, map);
		}
		map.put(f, wr);
		fluids.add(f.getName());
		this.onAddRecipe(wr, rl);
	}

	public WettingRecipe getRecipe(ItemStack is, FluidStack liquid) {
		HashMap<Fluid, WettingRecipe> map = recipeList.get(is);
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
			HashMap<Fluid, WettingRecipe> map = recipeList.get(is);
			c.addAll(map.values());
		}
		return c;
	}

	public boolean isWettable(ItemStack is) {
		return recipeList.containsKey(is);
	}

	public boolean isWettableWith(ItemStack is, Fluid f) {
		HashMap<Fluid, WettingRecipe> c = recipeList.get(is);
		return c != null && c.containsKey(f);
	}

	public static class WettingRecipe implements MachineRecipe {

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

		@Override
		public String getUniqueID() {
			return fluid.getFluid().getName()+":"+fluid.amount+"+"+fullID(input)+">"+fullID(output)+"#"+duration;
		}

		@Override
		public String getAllInfo() {
			return "Mixing "+fluid.amount+" of "+fluid.getLocalizedName()+" into "+fullID(input)+" for "+fullID(output)+" over "+duration+" ticks";
		}

		@Override
		public Collection<ItemStack> getAllUsedItems() {
			return ReikaJavaLibrary.makeListFrom(input, output);
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
		HashMap<Fluid, WettingRecipe> map = recipeList.get(ingredient);
		return map != null ? Collections.unmodifiableCollection(map.values()) : null;
	}

	@Override
	public void addPostLoadRecipes() {

	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		WettingRecipe wr = (WettingRecipe)recipe;
		boolean flag = false;
		Collection<ItemStack> rem = new ArrayList();
		for (ItemStack is : recipeList.keySet()) {
			HashMap<Fluid, WettingRecipe> map = recipeList.get(is);
			flag |= ReikaJavaLibrary.removeValuesFromMap(map, wr);
			if (map.isEmpty())
				rem.add(is);
		}
		for (ItemStack is : rem) {
			recipeList.remove(is);
		}
		return flag;
	}

	@Override
	protected boolean addCustomRecipe(LuaBlock lb, CustomRecipeList crl) throws Exception {
		ItemStack in = crl.parseItemString(lb.getString("input"), null, false);
		ItemStack out = crl.parseItemString(lb.getString("output"), lb.getChild("output_nbt"), false);
		this.verifyOutputItem(out);
		String fluid = lb.getString("input_fluid");
		Fluid f = FluidRegistry.getFluid(fluid);
		if (f == null)
			throw new IllegalArgumentException("Fluid '"+fluid+"' does not exist!");
		int amt = lb.getInt("input_amount");
		int time = lb.getInt("duration");
		this.addRecipe(in, f, amt, out, time, RecipeLevel.CUSTOM);
		return true;
	}
}
