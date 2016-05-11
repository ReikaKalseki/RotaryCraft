/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;

public class WorldEditHelper {

	private static HashMap<EntityPlayer, BlockKey> commands = new HashMap();

	public static void addCommand(EntityPlayer ep, Block id, int meta) {
		commands.put(ep, new BlockKey(id, meta));
	}

	public static void addCommand(EntityPlayer ep, Item id, int meta) {
		commands.put(ep, new BlockKey(Block.getBlockFromItem(id), meta));
	}

	public static Block getCommandedID(EntityPlayer ep) {
		return commands.get(ep).blockID;
	}

	public static int getCommandedMetadata(EntityPlayer ep) {
		return Math.max(commands.get(ep).metadata, 0);
	}

	public static boolean hasPlayer(EntityPlayer ep) {
		return commands.containsKey(ep);
	}

}
