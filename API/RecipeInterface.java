/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
	public static FrictionHeaterManager friction;
	public static BlastFurnaceManager blastfurn;
	public static MagnetizerManager magnetizer;
	public static WorktableManager worktable;


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

	public static interface BlastFurnaceManager extends RecipeManager {

		/**
		 * The first nine arguments are for the 3 slots on the left - three groups of ItemStack, chance-to-consume, and number-to-consume.
		 * To ignore a slot, supply an empty collection, NOT null.
		 *
		 * The other arguments control the rest of the recipe. 'Main' is the main grid item (like iron for HSLA), 'req' is how many of it are required
		 * (supply -1 for "any"), 'bonus' is whether and how much bonus output amounts can be given (>0 to give any, with the actual value as a multiplier).
		 */
		public void addAPIAlloying(Collection<ItemStack> in1, float c1, int decr1, Collection<ItemStack> in2, float c2, int decr2, Collection<ItemStack> in3, float c3, int decr3, ItemStack main, ItemStack out, int req, float bonus, float xp, int temp);

		/** For adding 3x3 crafting recipes like bedrock tool crafting. 'Speed' works inversely; higher values mean slower recipes. */
		public void addAPIRecipe(ItemStack out, int temperature, IRecipe in, int speed, float xp);

	}

	public static interface FrictionHeaterManager extends RecipeManager {

		public void addAPIRecipe(ItemStack in, ItemStack out, int temperature, int time);

	}

	public static interface MagnetizerManager extends RecipeManager {

		/** Null is acceptable for an action, in which case it will use the native NBT "magnet" behavior. */
		public void addAPIRecipe(ItemStack in, int minSpeed, int reqSpeedPerMicroTesla, int timeFactor, boolean allowStacks, MagnetizationAction a);

		public static interface MagnetizationAction {

			public void step(int omega, ItemStack is);

		}

	}

	public static interface WorktableManager extends RecipeManager {

		public void addAPIRecipe(IRecipe ir);

	}

	private static interface RecipeManager {

	}

	private static boolean isValid(ItemStack out) {
		return !out.getItem().getClass().getName().startsWith("Reika.RotaryCraft.Items");
	}

}
