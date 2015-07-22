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

import java.util.Collection;
import java.util.HashSet;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Instantiable.Data.Maps.FluidHashMap;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.RecipeInterface.CrystallizerManager;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class RecipesCrystallizer extends RecipeHandler implements CrystallizerManager {

	private static final RecipesCrystallizer CrystallizerBase = new RecipesCrystallizer();

	private final FluidHashMap<CrystallizerRecipe> recipeList = new FluidHashMap().setGEMatching(true);

	public static final RecipesCrystallizer getRecipes()
	{
		return CrystallizerBase;
	}

	private RecipesCrystallizer() {
		super(MachineRegistry.CRYSTALLIZER);
		RecipeInterface.crystallizer = this;

		this.addRecipe(FluidRegistry.WATER, 1000, new ItemStack(Blocks.ice), RecipeLevel.CORE);
		this.addRecipe(FluidRegistry.LAVA, 1000, new ItemStack(Blocks.stone), RecipeLevel.PERIPHERAL);

		this.addRecipe("rc ethanol", 1000, ItemRegistry.ETHANOL.getStackOf(), RecipeLevel.PROTECTED);
	}

	public static class CrystallizerRecipe implements MachineRecipe {

		private final FluidStack input;
		private final ItemStack output;

		private CrystallizerRecipe(FluidStack fs, ItemStack is) {
			input = fs;
			output = is;
		}

		@Override
		public String getUniqueID() {
			return input.getFluid().getName()+":"+input.amount+">"+output;
		}

	}

	public void addAPIRecipe(Fluid f, int amount, ItemStack out) {
		this.addRecipe(f, amount, out, RecipeLevel.API);
	}

	private void addRecipe(Fluid f, int amount, ItemStack out, RecipeLevel rl) {
		CrystallizerRecipe rec = new CrystallizerRecipe(new FluidStack(f, amount), out);
		recipeList.put(f, amount, rec);
		this.onAddRecipe(rec, rl);
	}

	private void addRecipe(String s, int amount, ItemStack out, RecipeLevel rl) {
		Fluid f = FluidRegistry.getFluid(s);
		if (f != null)
			this.addRecipe(f, amount, out, rl);
	}

	public ItemStack getFreezingResult(FluidStack liquid)
	{
		Fluid f = liquid.getFluid();
		CrystallizerRecipe cr = recipeList.get(liquid);
		if (cr == null)
			return null;
		int req = cr.input.amount;
		if (req > liquid.amount)
			return null;
		return recipeList.get(liquid).output.copy();
	}

	public Fluid getRecipe(ItemStack result) {
		for (FluidStack f : recipeList.keySet()) {
			CrystallizerRecipe cr = recipeList.get(f);
			if (ReikaItemHelper.matchStacks(result, cr.output))
				return f.getFluid();
		}
		return null;
	}

	public int getRecipeConsumption(ItemStack result) {
		for (FluidStack f : recipeList.keySet()) {
			CrystallizerRecipe cr = recipeList.get(f);
			if (ReikaItemHelper.matchStacks(result, cr.output))
				return cr.input.amount;
		}
		return 0;
	}

	public boolean isValidFluid(Fluid f) {
		return recipeList.containsKey(f);
	}

	public Collection<Fluid> getAllRecipes() {
		HashSet<Fluid> c = new HashSet();
		for (CrystallizerRecipe cr : recipeList.values()) {
			c.add(cr.input.getFluid());
		}
		return c;
	}

	@Override
	public void addPostLoadRecipes() {
		this.addRecipe("ender", 250, new ItemStack(Items.ender_pearl), RecipeLevel.MODINTERACT);
		this.addRecipe("redstone", 100, new ItemStack(Items.redstone), RecipeLevel.MODINTERACT);
		this.addRecipe("glowstone", 250, new ItemStack(Items.glowstone_dust), RecipeLevel.MODINTERACT);
		this.addRecipe("coal", 100, new ItemStack(Items.coal), RecipeLevel.MODINTERACT);
	}

	@Override
	protected boolean removeRecipe(MachineRecipe recipe) {
		return recipeList.removeValue((CrystallizerRecipe)recipe);
	}
}
