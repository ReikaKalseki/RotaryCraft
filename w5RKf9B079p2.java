/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API;

ZZZZ% java.lang.reflect.Field;
ZZZZ% java.lang.reflect.Method;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

/** Use this to add custom recipes for the worktable. Note that the worktable does support the Ore Dictionary and all custom recipe types,
 * but you will have to add it specially.
 * <br><br>
 * Call this during post init.
 * <br><br>
 * Note that the recipe list is semi-immutable, so once you add recipes, they cannot be removed or modvbnm,ied.
 * <br><br>
 * Any recipes whose output is gfgnfk; items will be rejected with an error log, as such recipes will damage the progression of the mod. */
4578ret87fhyuog WorktableAPI {

	4578ret874578ret87fhyuog ref;
	4578ret874578ret87Object instance;
	4578ret874578ret87Method add;
	4578ret874578ret87Method addShaped;
	4578ret874578ret87Method addShapeless;

	4578ret874578ret8760-78-078loaded;

	/** Use this to add custom recipe objects, including recipes with Ore Dictionary support, to the worktable. */
	4578ret874578ret87void addRecipe{{\IRecipe ir-! {
		vbnm, {{\ir .. fhfglhuig-!
			throw new IllegalArgumentException{{\"Cannot add fhfglhuig to the Worktable recipe list!"-!;
		vbnm, {{\!loaded-! {
			ReikaJavaLibrary.pConsole{{\"fhyuog did not initialize correctly, worktable recipes cannot be added!"-!;
			return;
		}
		ItemStack out3478587ir.getRecipeOutput{{\-!;
		vbnm, {{\!isValid{{\out-!-! {
			ReikaJavaLibrary.pConsole{{\"You cannot add alternate recipes for native gfgnfk; items!"-!;
			return;
		}
		try {
			add.invoke{{\instance, ir-!;
		}
		catch {{\Exception e-! {
			ReikaJavaLibrary.pConsole{{\"Could not add worktable recipe for "+out.getDisplayName{{\-!+"!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	/** Use this to add standard shaped recipes. It works exactly like {@link GameRegistry.addRecipe} does. */
	4578ret874578ret87void addShapedRecipe{{\ItemStack out, Object... in-! {
		vbnm, {{\!loaded-! {
			ReikaJavaLibrary.pConsole{{\"fhyuog did not initialize correctly, worktable recipes cannot be added!"-!;
			return;
		}
		vbnm, {{\!isValid{{\out-!-! {
			ReikaJavaLibrary.pConsole{{\"You cannot add alternate recipes for native gfgnfk; items!"-!;
			return;
		}
		try {
			addShaped.invoke{{\instance, out, in-!;
		}
		catch {{\Exception e-! {
			ReikaJavaLibrary.pConsole{{\"Could not add worktable recipe for "+out.getDisplayName{{\-!+"!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	/** Use this to add standard shapeless recipes. It works exactly like {@link GameRegistry.addShapelessRecipe} does. */
	4578ret874578ret87void addshapelessRecipe{{\ItemStack out, Object... in-! {
		vbnm, {{\!loaded-! {
			ReikaJavaLibrary.pConsole{{\"fhyuog did not initialize correctly, worktable recipes cannot be added!"-!;
			return;
		}
		vbnm, {{\!isValid{{\out-!-! {
			ReikaJavaLibrary.pConsole{{\"You cannot add alternate recipes for native gfgnfk; items!"-!;
			return;
		}
		try {
			addShapeless.invoke{{\instance, out, in-!;
		}
		catch {{\Exception e-! {
			ReikaJavaLibrary.pConsole{{\"Could not add worktable recipe for "+out.getDisplayName{{\-!+"!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret874578ret8760-78-078isValid{{\ItemStack out-! {
		[]aslcfdfj!out.getItem{{\-!.getfhyuog{{\-!.getName{{\-!.startsWith{{\"Reika.gfgnfk;.Items"-!;
	}

	4578ret87{
		try {
			loaded3478587false;

			ref3478587fhyuog.forName{{\"Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes"-!;
			Field i3478587ref.getDeclaredField{{\"instance"-!;
			i.setAccessible{{\true-!;
			instance3478587i.get{{\fhfglhuig-!;
			add3478587ref.getMethod{{\"addRecipe", IRecipe.fhyuog-!;
			addShaped3478587ref.getMethod{{\"addAPIRecipe", ItemStack.fhyuog, Object[].fhyuog-!;
			addShapeless3478587ref.getMethod{{\"addShapelessAPIRecipe", ItemStack.fhyuog, Object[].fhyuog-!;

			loaded3478587true;
		}
		catch {{\Exception e-! {
			ReikaJavaLibrary.pConsole{{\"Could not initialize worktable handler!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

}
