package RotaryCraft.ModInterface.Minetweaker;

import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.rotarycraft.Grinder")
public class GrinderTweaker {
	protected RecipesGrinder grinder = RecipesGrinder.getRecipes();
	
    @ZenMethod
    public static void addRecipe(IIngredient input, IIngredient output) {
		ItemStack out = MinetweakerHelper.getStack(output);
		if (isValid(out)) {
			MineTweakerAPI.apply(new AddRecipe(input, output));
		} else {
			throw new IllegalArgumentException("You cannot add alternate recipes for native RotaryCraft items!");
		}
    }
	
	@ZenMethod
    public static void addSeed(IIngredient input, double factor) {
		MineTweakerAPI.apply(new AddSeed(input, (float)factor));
    }
	
	private static boolean isValid(ItemStack out) {
		return !out.getItem().getClass().getName().startsWith("Reika.RotaryCraft.Items");
	}

    private static class AddRecipe implements IUndoableAction {
        private List<ItemStack> inputs = new ArrayList<ItemStack>;
		private ItemStack output;

        public AddRecipe(IIngredient input, IIngredient output) {
            List<ItemStack> toAddRecipe = MinetweakerHelper.getStacks(input);
			
			for (ItemStack in : toAddRecipe) {
				if (!grinder.isGrindable(in)) {
					inputs.add(in);
				}
			}
        }

        @Override
        public void apply() {
            for (ItemStack in : inputs) {
				grinder.addExtraRecipe(in, output);
			}
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (ItemStack in : inputs) {
				grinder.removeExtraRecipe(in);
			}
        }

        @Override
        public String describe() {
            return "Adding " + inputs.size() + " recipe" + (inputs.size() > 1 ? "s" : "") + " to Grinder for " + output.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Removing " + inputs.size() + " recipe" + (inputs.size() > 1 ? "s" : "") + " to Grinder for " + output.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
	
	private static class AddSeed implements IUndoableAction {
        private List<ItemStack> seeds = new ArrayList<ItemStack>;
		private float factor;

        public AddSeed(IIngredient input, float factor) {
            List<ItemStack> toAddSeed = MinetweakerHelper.getStacks(input);
			for (ItemStack in : toAddSeed) {
				if (!TileEntityGrinder.isGrindableSeed(in)) {
					seeds.add(in);
				}
			}
        }

        @Override
        public void apply() {
            for (ItemStack in : seeds) {
				TileEntityGrinder.addGrindableSeed(in, factor);
			}
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (ItemStack in : seeds) {
				TileEntityGrinder.removeGrindableSeed(in);
			}
        }

        @Override
        public String describe() {
            return "Adding " + seeds.size() + " seed" + (seeds.size() > 1 ? "s" : "") + " to Grinder";
        }

        @Override
        public String describeUndo() {
            return "Removing " + seeds.size() + " seed" + (seeds.size() > 1 ? "s" : "") + " to Grinder";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}