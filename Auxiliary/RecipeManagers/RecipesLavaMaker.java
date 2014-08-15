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
import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.ModInteract.MagicCropHandler;
import Reika.RotaryCraft.Registry.ItemRegistry;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesLavaMaker {

	private static final RecipesLavaMaker recipes = new RecipesLavaMaker();

	public static final RecipesLavaMaker getRecipes() {
		return recipes;
	}

	private final ItemHashMap<FluidStack> list = new ItemHashMap();
	private final ItemHashMap<Integer> temperatures = new ItemHashMap();
	private final ItemHashMap<Long> energies = new ItemHashMap();

	private RecipesLavaMaker() {
		this.addRecipe(Blocks.stone, FluidRegistry.LAVA, 1000, 1000, 5200000);
		this.addRecipe(Blocks.cobblestone, FluidRegistry.LAVA, 500, 1000, 2820000);
		this.addRecipe(Blocks.netherrack, FluidRegistry.LAVA, 2000, 600, 480000);
		this.addRecipe(Blocks.stonebrick, FluidRegistry.LAVA, 1000, 1200, 4000000);

		this.addRecipe("stone", FluidRegistry.LAVA, 1000, 1000, 5200000);
		this.addRecipe("cobblestone", FluidRegistry.LAVA, 500, 1000, 2820000);

		this.addRecipe("dustGlowstone", "glowstone", 250, 400, 80000);
		this.addRecipe(Blocks.glowstone, "glowstone", 1000, 500, 320000);
		this.addRecipe("dustRedstone", "redstone", 100, 600, 120000);
		this.addRecipe(Blocks.redstone_block, "redstone", 900, 750, 1080000);
		this.addRecipe(Items.ender_pearl, "ender", 250, 400, 240000);
		this.addRecipe("dustCoal", "coal", 250, 300, 60000);

		this.addRecipe("shardCrystal", "potion crystal", 8000, 500, 80000);
		this.addRecipe(ItemRegistry.ETHANOL.getStackOf(), "rc ethanol", 100, 180, 6000);

		if (ModList.MAGICCROPS.isLoaded())
			this.addRecipe(MagicCropHandler.EssenceType.XP.getEssence(), "mobessence", 200, 600, 360000);
	}

	private void addRecipe(String in, String out, int amt, int temperature, long energy) {
		if (this.validateFluid(out)) {
			ArrayList<ItemStack> li = OreDictionary.getOres(in);
			for (int i = 0; i < li.size(); i++)
				this.addRecipe(li.get(i), new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy);
		}
	}

	private void addRecipe(String in, Fluid out, int amt, int temperature, long energy) {
		ArrayList<ItemStack> li = OreDictionary.getOres(in);
		for (int i = 0; i < li.size(); i++)
			this.addRecipe(li.get(i), new FluidStack(out, amt), temperature, energy);
	}

	private void addRecipe(ItemStack in, String out, int amt, int temperature, long energy) {
		if (this.validateFluid(out))
			this.addRecipe(in, new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy);
	}

	private void addRecipe(Item in, String out, int amt, int temperature, long energy) {
		if (this.validateFluid(out))
			this.addRecipe(new ItemStack(in), new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy);
	}

	private void addRecipe(Block in, String out, int amt, int temperature, long energy) {
		if (this.validateFluid(out))
			this.addRecipe(new ItemStack(in), new FluidStack(FluidRegistry.getFluid(out), amt), temperature, energy);
	}

	private void addRecipe(Block in, Fluid out, int amt, int temperature, long energy) {
		this.addRecipe(new ItemStack(in), new FluidStack(out, amt), temperature, energy);
	}

	private void addRecipe(Item in, Fluid out, int amt, int temperature, long energy) {
		this.addRecipe(new ItemStack(in), new FluidStack(out, amt), temperature, energy);
	}

	private void addRecipe(ItemStack in, FluidStack out, int temperature, long energy) {
		list.put(in, out);
		temperatures.put(in, temperature);
		energies.put(in, energy);
	}

	private boolean validateFluid(String s) {
		return FluidRegistry.getFluid(s) != null;
	}

	public FluidStack getMelting(ItemStack is) {
		return list.get(is);
	}

	public int getMeltTemperature(ItemStack is) {
		return temperatures.containsKey(is) ? temperatures.get(is) : Integer.MIN_VALUE;
	}

	public long getMeltingEnergy(ItemStack is) {
		return energies.containsKey(is) ? energies.get(is) : Integer.MIN_VALUE;
	}

	public boolean isValidFuel(ItemStack is) {
		return list.containsKey(is);
	}

	public ArrayList<ItemStack> getSourceItems(Fluid f) {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack key : list.keySet()) {
			FluidStack fs = list.get(key);
			if (fs.getFluid().equals(f))
				li.add(key.copy());
		}
		return li;
	}

}