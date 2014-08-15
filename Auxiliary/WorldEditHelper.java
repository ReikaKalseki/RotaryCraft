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

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WorldEditHelper {

	private static HashMap<EntityPlayer, ItemStack> commands = new HashMap();

	public static void addCommand(EntityPlayer ep, Block id, int meta) {
		commands.put(ep, new ItemStack(id, meta));
	}

	public static void addCommand(EntityPlayer ep, Item id, int meta) {
		commands.put(ep, new ItemStack(id, meta));
	}

	public static Block getCommandedID(EntityPlayer ep) {
		return Block.getBlockFromItem(commands.get(ep).getItem());
	}

	public static int getCommandedMetadata(EntityPlayer ep) {
		return commands.get(ep).getItemDamage();
	}

	public static boolean hasPlayer(EntityPlayer ep) {
		return commands.containsKey(ep);
	}

}