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
import net.minecraftforge.oredict.OreDictionary;

public class RecipesLavaMaker {

	private static final RecipesLavaMaker recipes = new RecipesLavaMaker();

	public static final RecipesLavaMaker getRecipes() {
		return recipes;
	}

	private final HashMap<List<Integer>, FluidStack> list = new HashMap();

	private RecipesLavaMaker() {
		this.addRecipe(Block.stone, FluidRegistry.LAVA, 1000);
		this.addRecipe(Block.cobblestone, FluidRegistry.LAVA, 500);
		this.addRecipe(Block.netherrack, FluidRegistry.LAVA, 2000);
		this.addRecipe(Block.stoneBrick, FluidRegistry.LAVA, 1000);

		this.addRecipe("stone", FluidRegistry.LAVA, 1000);
		this.addRecipe("cobblestone", FluidRegistry.LAVA, 500);

		this.addRecipe("dustGlowstone", "glowstone", 250);
		this.addRecipe(Block.glowStone, "glowstone", 1000);
		this.addRecipe("dustRedstone", "redstone", 100);
		this.addRecipe(Block.blockRedstone, "redstone", 900);
		this.addRecipe(Item.enderPearl, "ender", 250);
		this.addRecipe("dustCoal", "coal", 250);

		this.addRecipe("shardCrystal", "potion crystal", 8000);
	}

	private void addRecipe(String in, String out, int amt) {
		if (this.validateFluid(out)) {
			ArrayList<ItemStack> li = OreDictionary.getOres(in);
			for (int i = 0; i < li.size(); i++)
				this.addRecipe(li.get(i), new FluidStack(FluidRegistry.getFluid(out), amt));
		}
	}

	private void addRecipe(String in, Fluid out, int amt) {
		ArrayList<ItemStack> li = OreDictionary.getOres(in);
		for (int i = 0; i < li.size(); i++)
			this.addRecipe(li.get(i), new FluidStack(out, amt));
	}

	private void addRecipe(Item in, String out, int amt) {
		if (this.validateFluid(out))
			this.addRecipe(new ItemStack(in), new FluidStack(FluidRegistry.getFluid(out), amt));
	}

	private void addRecipe(Block in, String out, int amt) {
		if (this.validateFluid(out))
			this.addRecipe(new ItemStack(in), new FluidStack(FluidRegistry.getFluid(out), amt));
	}

	private void addRecipe(Block in, Fluid out, int amt) {
		this.addRecipe(new ItemStack(in), new FluidStack(out, amt));
	}

	private void addRecipe(Item in, Fluid out, int amt) {
		this.addRecipe(new ItemStack(in), new FluidStack(out, amt));
	}

	private void addRecipe(ItemStack in, FluidStack out) {
		list.put(Arrays.asList(in.itemID, in.getItemDamage()), out);
	}

	private boolean validateFluid(String s) {
		return FluidRegistry.getFluid(s) != null;
	}

	public FluidStack getMelting(ItemStack is) {
		return list.get(Arrays.asList(is.itemID, is.getItemDamage()));
	}

	public boolean isValidFuel(ItemStack is) {
		return list.containsKey(Arrays.asList(is.itemID, is.getItemDamage()));
	}

}
