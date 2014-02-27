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

import net.minecraft.block.Block;

public final class MachineInteracter {

	private static Class core;
	private static Class blocks;
	private static Class items;
	private static Class machines;
	private static Block[] blockList;

	static {
		try {
			core = Class.forName("Reika.RotaryCraft.RotaryCraft", false, MachineInteracter.class.getClassLoader());
			blockList = (Block[])core.getField("machineBlocks").get(null);
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

	/** A simple check to test if the block ID is one of RC's machine blocks. */
	public static boolean isRCMachine(Block b) {
		for (int i = 0; i < blockList.length; i++) {
			int id = blockList[i].blockID;
			if (b.blockID == id)
				return true;
		}
		return false;
	}

}
