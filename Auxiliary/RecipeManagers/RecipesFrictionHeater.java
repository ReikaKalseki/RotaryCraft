package Reika.RotaryCraft.Auxiliary.RecipeManagers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public class RecipesFrictionHeater {

	private static final RecipesFrictionHeater instance = new RecipesFrictionHeater();

	private final HashMap<List<Integer>, FrictionRecipe> recipes = new HashMap();

	public static RecipesFrictionHeater getRecipes() {
		return instance;
	}

	private RecipesFrictionHeater() {
		this.addRecipe(ItemStacks.tungstenflakes, ItemStacks.tungsteningot, 1800);
	}

	private void addRecipe(ItemStack in, ItemStack out, int temp) {
		FrictionRecipe rec = new FrictionRecipe(in, out, temp);
		recipes.put(Arrays.asList(in.itemID, in.getItemDamage()), rec);
	}

	public ItemStack getSmelting(ItemStack in, int temperature) {
		FrictionRecipe rec = recipes.get(Arrays.asList(in.itemID, in.getItemDamage()));
		return temperature >= rec.requiredTemperature ? rec.getOutput() : null;
	}

	private static class FrictionRecipe {

		public final int requiredTemperature;
		private final ItemStack input;
		private final ItemStack output;

		private FrictionRecipe(ItemStack in, ItemStack out, int temp) {
			requiredTemperature = temp;
			input = in;
			output = out;
		}

		public ItemStack getInput() {
			return input.copy();
		}

		public ItemStack getOutput() {
			return output.copy();
		}
	}

}
