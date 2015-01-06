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

import net.minecraft.block.Block;

/** WIP */
public final class MachineInteracter {

	private static Class core;
	private static Class blocks;
	private static Class items;
	private static Class machines;
	private static Block[] blockList;

	static {
		try {
			core = Class.forName("Reika.RotaryCraft.RotaryCraft", false, MachineInteracter.class.getClassLoader());
			blockList = (Block[])core.getField("blocks").get(null);
		}
		catch (ClassNotFoundException e) {
			System.out.println("RotaryCraft class not found!");
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			System.out.println("RotaryCraft class not read!");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			System.out.println("RotaryCraft class not read!");
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			System.out.println("RotaryCraft class not read!");
			e.printStackTrace();
		}
		catch (SecurityException e) {
			System.out.println("RotaryCraft class not read!");
			e.printStackTrace();
		}
	}

}
