/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import cpw.mods.fml.common.registry.GameRegistry;

/** Use this to add custom recipes for the worktable. Note that the worktable does support the Ore Dictionary and all custom recipe types,
 * but you will have to add it specially.
 * <br><br>
 * Call this during post init.
 * <br><br>
 * Note that the recipe list is semi-immutable, so once you add recipes, they cannot be removed or modified.
 * <br><br>
 * Any recipes whose output is RotaryCraft items will be rejected with an error log, as such recipes will damage the progression of the mod. */
public class WorktableAPI {

	private static Class ref;
	private static Object instance;
	private static Method add;
	private static Method addShaped;
	private static Method addShapeless;

	private static boolean loaded;

	/** Use this to add custom recipe objects, including recipes with Ore Dictionary support, to the worktable. */
	public static void addRecipe(IRecipe ir) {
		if (ir == null)
			throw new IllegalArgumentException("Cannot add null to the Worktable recipe list!");
		if (!loaded) {
			ReikaJavaLibrary.pConsole("Class did not initialize correctly, worktable recipes cannot be added!");
			return;
		}
		ItemStack out = ir.getRecipeOutput();
		if (!isValid(out)) {
			ReikaJavaLibrary.pConsole("You cannot add alternate recipes for native RotaryCraft items!");
			return;
		}
		try {
			add.invoke(instance, ir);
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Could not add worktable recipe for "+out.getDisplayName()+"!");
			e.printStackTrace();
		}
	}

	/** Use this to add standard shaped recipes. It works exactly like {@link GameRegistry.addRecipe} does. */
	public static void addShapedRecipe(ItemStack out, Object... in) {
		if (!loaded) {
			ReikaJavaLibrary.pConsole("Class did not initialize correctly, worktable recipes cannot be added!");
			return;
		}
		if (!isValid(out)) {
			ReikaJavaLibrary.pConsole("You cannot add alternate recipes for native RotaryCraft items!");
			return;
		}
		try {
			addShaped.invoke(instance, out, in);
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Could not add worktable recipe for "+out.getDisplayName()+"!");
			e.printStackTrace();
		}
	}

	/** Use this to add standard shapeless recipes. It works exactly like {@link GameRegistry.addShapelessRecipe} does. */
	public static void addshapelessRecipe(ItemStack out, Object... in) {
		if (!loaded) {
			ReikaJavaLibrary.pConsole("Class did not initialize correctly, worktable recipes cannot be added!");
			return;
		}
		if (!isValid(out)) {
			ReikaJavaLibrary.pConsole("You cannot add alternate recipes for native RotaryCraft items!");
			return;
		}
		try {
			addShapeless.invoke(instance, out, in);
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Could not add worktable recipe for "+out.getDisplayName()+"!");
			e.printStackTrace();
		}
	}

	private static boolean isValid(ItemStack out) {
		return !out.getItem().getClass().getName().startsWith("Reika.RotaryCraft.Items");
	}

	static {
		try {
			loaded = false;

			ref = Class.forName("Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes");
			Field i = ref.getDeclaredField("instance");
			i.setAccessible(true);
			instance = i.get(null);
			add = ref.getMethod("addRecipe", IRecipe.class);
			addShaped = ref.getMethod("addAPIRecipe", ItemStack.class, Object[].class);
			addShapeless = ref.getMethod("addShapelessAPIRecipe", ItemStack.class, Object[].class);

			loaded = true;
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole("Could not initialize worktable handler!");
			e.printStackTrace();
		}
	}

}
