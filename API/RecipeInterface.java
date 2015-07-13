package Reika.RotaryCraft.API;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RecipeInterface {

	public static CompactorManager compactor;
	public static GrinderManager grinder;
	public static CentrifugeManager centrifuge;
	public static PulseFurnaceManager pulsefurn;
	public static CrystallizerManager crystallizer;
	public static RockMelterManager rockmelt;
	public static WetterManager wetter;
	public static DryingBedManager dryingbed;

	public static interface CompactorManager extends RecipeManager {

		public void addAPIRecipe(ItemStack in, ItemStack out, int pressure, int temperature);

	}

	public static interface GrinderManager extends RecipeManager {

		public void addAPIRecipe(ItemStack in, ItemStack out);

	}

	public static interface CentrifugeManager extends RecipeManager {

		public void addAPIRecipe(ItemStack in, FluidStack fout, float chanceout, Object... items);

	}

	public static interface PulseFurnaceManager extends RecipeManager {

		public void addAPISmelting(ItemStack in, ItemStack out);

	}

	public static interface CrystallizerManager extends RecipeManager {

		public void addAPIRecipe(Fluid f, int amount, ItemStack out);

	}

	public static interface RockMelterManager extends RecipeManager {

		public void addAPIRecipe(ItemStack in, FluidStack out, int temperature, long energy);

	}

	public static interface WetterManager extends RecipeManager {

		public void addAPIRecipe(ItemStack in, Fluid f, int amount, ItemStack out, int time);

	}

	public static interface DryingBedManager extends RecipeManager {

		public void addAPIRecipe(Fluid f, int amount, ItemStack out);

	}

	public static interface RecipeManager {

	}

	private static boolean isValid(ItemStack out) {
		return !out.getItem().getClass().getName().startsWith("Reika.RotaryCraft.Items");
	}

}
