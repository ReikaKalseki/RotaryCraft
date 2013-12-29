/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Reika.DragonAPI.Libraries.IO.ReikaColorAPI;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;

public class BlockColorInterface {

	private static Class map;
	private static Object instance;
	private static Method add;
	private static Method get;

	public static int getColor(int id, int meta) {
		try {
			return (Integer)get.invoke(instance, id, meta);
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Error getting color mapping for "+id+":"+meta);
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			ReikaJavaLibrary.pConsole("Error getting color mapping for "+id+":"+meta);
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			ReikaJavaLibrary.pConsole("Cannot get color map when the class failed to load!");
			e.printStackTrace();
		}
		return 0;
	}

	public static void addGPRBlockColor(int id, int meta, int color) {
		try {
			add.invoke(instance, id, meta, color);
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Error adding color mapping for "+id+":"+meta);
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			if (e.getCause() instanceof IllegalArgumentException)
				throw new IllegalArgumentException(e.getMessage());
			ReikaJavaLibrary.pConsole("Error adding color mapping for "+id+":"+meta);
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			ReikaJavaLibrary.pConsole("Cannot add color map when the class failed to load!");
			e.printStackTrace();
		}
	}

	public static void addGPRBlockColor(int id, int meta, int r, int g, int b) {
		addGPRBlockColor(id, meta, ReikaColorAPI.RGBtoHex(r, g, b));
	}

	static {
		try {
			map = Class.forName("Reika.RotaryCraft.Auxiliary.BlockColorMapper");
			add = map.getMethod("addModBlockColor", int.class, int.class, int.class);
			get = map.getMethod("getColorForBlock", int.class, int.class);
			Field f = map.getField("instance");
			instance = f.get(null);
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
		catch (IllegalArgumentException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			ReikaJavaLibrary.pConsole("Could not read RotaryCraft class!");
			e.printStackTrace();
		}
	}

}
