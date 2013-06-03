/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;

public class ItemHandheldCrafting extends ItemRotaryTool {

	public ItemStack[] items = new ItemStack[9];

	public ItemHandheldCrafting(int ID) {
		super(ID, 33);
		maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (!world.isRemote)
			ep.openGui(RotaryCraft.instance, 10, world, 0, 0, 0);
		return is;
	}

}
