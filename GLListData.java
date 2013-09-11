/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.HashMap;

import Reika.RotaryCraft.Registry.MachineRegistry;

public class GLListData {

	private static final HashMap<MachineRegistry, int[]> renders = new HashMap<MachineRegistry, int[]>();

	public static void addListData(MachineRegistry m, int[] data) {
		renders.put(m, data);
	}

	public static int getMachineRenderList(MachineRegistry m, int index) {
		int[] li = renders.get(m);
		if (li != null && li.length > index)
			return li[index];
		else
			return 0;
	}

}
