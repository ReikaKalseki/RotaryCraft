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

import net.minecraft.block.Block;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public final class MachineInteracter {

	private static Class core;
	private static Class blocks;
	private static Class items;
	private static Class machines;

	static {
		try {
			core = Class.forName("Reika.RotaryCraft.RotaryCraft", false, MachineInteracter.class.getClassLoader());
			blocks = Class.forName("Reika.RotaryCraft.Registry.BlockRegistry", false, MachineInteracter.class.getClassLoader());
			items = Class.forName("Reika.RotaryCraft.Registry.ItemRegistry", false, MachineInteracter.class.getClassLoader());
			machines = Class.forName("Reika.RotaryCraft.Registry.MachineRegistry", false, MachineInteracter.class.getClassLoader());
		}
		catch (ClassNotFoundException e) {
			System.out.println("RotaryCraft class not found!");
			e.printStackTrace();
		}
	}

	/** To get the machine's MachineRegistry enum entry by machine name. */
	public static MachineRegistry getMachineByName(String name) {
		MachineRegistry[] list = (MachineRegistry[])machines.getEnumConstants();
		for (int i = 0; i < list.length; i++) {
			String ename = list[i].getName();
			if (ename.equals(name))
				return list[i];
		}
		return null;
	}

	/** To get the machine's MachineRegistry enum entry by enum entry name.
	Easier to use if you are familiar with the code. */
	public static MachineRegistry getMachineByEnumName(String name) {
		MachineRegistry[] list = (MachineRegistry[])machines.getEnumConstants();
		for (int i = 0; i < list.length; i++) {
			String ename = list[i].name();
			if (ename.equals(name))
				return list[i];
		}
		return null;
	}

	/** To get a MachineRegistry entry from an ID and Metadata. */
	public static MachineRegistry getMachineFromIDAndMeta(int id, int meta) {
		MachineRegistry[] list = (MachineRegistry[])machines.getEnumConstants();
		for (int i = 0; i < list.length; i++) {
			int mid = list[i].getBlockID();
			int mmeta = list[i].getMachineMetadata();
			int nummeta = list[i].getNumberMetadatas();
			if (id == mid && meta >= mmeta && meta < mmeta+nummeta)
				return list[i];
		}
		return null;
	}

	/** A simple check to test if the block ID is one of RC's machine blocks. */
	public static boolean isRCMachine(Block b) {
		BlockRegistry[] list = (BlockRegistry[])blocks.getEnumConstants();
		for (int i = 0; i < list.length; i++) {
			int id = list[i].getBlockID();
			if (b.blockID == id)
				return true;
		}
		return false;
	}

	/** To get the block's BlockRegistry enum entry. */
	public static BlockRegistry getRCMachineBlockType(Block b) {
		BlockRegistry[] list = (BlockRegistry[])blocks.getEnumConstants();
		for (int i = 0; i < list.length; i++) {
			int id = list[i].getBlockID();
			if (b.blockID == id)
				return list[i];
		}
		return null;
	}

}
