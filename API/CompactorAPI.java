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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;

public class CompactorAPI {

	private static Class recipes;
	private static Method get;
	private static Object instance;
	private static Method add;

	/** Adds a compactor recipe. Note that it will follow the general rules of
	 * 4 items in, one item out, producing 1 or 2 of the product. 'Pressure' is required pressure. */
	public static void addCompactorRecipe(ItemStack in, ItemStack out, int pressure, int temperature) {
		try {
			add.invoke(instance, in.itemID, in.getItemDamage(), out, 0, pressure, temperature);
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Error adding compactor recipe for "+in);
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("Error adding compactor recipe for "+in);
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			ReikaJavaLibrary.pConsole("Error adding compactor recipe for "+in);
			e.printStackTrace();
		}
	}

	static {
		try {
			recipes = Class.forName("Reika.RotaryCraft.Auxiliary.RecipesCompactor");
			get = recipes.getMethod("getRecipes");
			instance = get.invoke(null);
			add = recipes.getMethod("addCompacting", int.class, int.class, ItemStack.class, float.class, int.class, int.class);
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
