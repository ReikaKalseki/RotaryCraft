/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

public class WorldEditHelper {

	private static HashMap<EntityPlayer, int[]> commands = new HashMap<EntityPlayer, int[]>();

	public static void addCommand(EntityPlayer ep, int id, int meta) {
		commands.put(ep, new int[]{id, meta});
	}

	public static int getCommandedID(EntityPlayer ep) {
		return commands.get(ep)[0];
	}

	public static int getCommandedMetadata(EntityPlayer ep) {
		return commands.get(ep)[1];
	}

	public static boolean hasPlayer(EntityPlayer ep) {
		return commands.containsKey(ep);
	}

}
