/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

public enum GuiRegistry {

	MACHINE(),
	HANDCRAFT(),
	HANDBOOK(),
	HANDBOOKPAGE(),
	WORLDEDIT(),
	LOADEDHANDBOOK(),
	SAFEPLAYERS(),
	SPYCAM(),
	SLIDE();

	private static final GuiRegistry[] guiList = GuiRegistry.values();

	public boolean hasContainer() {
		if (this == MACHINE)
			return true;
		if (this == HANDCRAFT)
			return true;
		if (this == WORLDEDIT)
			return true;
		return false;
	}

	public static GuiRegistry getEntry(int id) {
		return guiList[id];
	}

}