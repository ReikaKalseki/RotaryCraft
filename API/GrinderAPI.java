/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;

public class GrinderAPI {

	private static Class recipes;
	private static Method get;
	private static Method add;
	private static Object instance;

	private static Class grinder;
	private static Method addseed;

	/** Adds a grinder recipe. Args: Item in, Item out. Neither may be null.
	 * Call this in your postLoad method. */
	public static void addRecipe(ItemStack in, ItemStack out) {
		try {
			add.invoke(instance, in, out, 0);
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Error adding grinder recipe for "+in);
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("Error adding grinder recipe for "+in);
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			ReikaJavaLibrary.pConsole("Error adding grinder recipe for "+in);
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			ReikaJavaLibrary.pConsole("Cannot add recipe when the class failed to load!");
			e.printStackTrace();
		}
	}

	/** Adds a grindable seed, so that it may be used to make lubricant.
	 * Will make 1 unit/seed. Args: Item in. Call this in your postLoad method. */
	public static void addGrindableSeed(ItemStack seed) {
		try {
			addseed.invoke(null, seed);
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Error adding grindable seed recipe for "+seed);
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("Error adding grindable seed recipe for "+seed);
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			ReikaJavaLibrary.pConsole("Error adding grindable seed recipe for "+seed);
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			ReikaJavaLibrary.pConsole("Cannot add recipe when the class failed to load!");
			e.printStackTrace();
		}
	}

	static {
		try {
			recipes = Class.forName("Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesGrinder");
			get = recipes.getMethod("getRecipes");
			instance = get.invoke(null);
			add = recipes.getMethod("addRecipe", ItemStack.class, ItemStack.class, float.class);

			grinder = Class.forName("Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder");
			addseed = grinder.getMethod("addGrindableSeed", ItemStack.class);
		}
		catch (ClassNotFoundException e) {
			ReikaJavaLibrary.pConsole("Could not load RotaryCraft class!");
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
		catch (SecurityException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
	}

}