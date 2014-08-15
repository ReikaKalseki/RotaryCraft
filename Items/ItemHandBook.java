/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHandBook extends ItemRotaryTool {

	public ItemHandBook(int tex) {
		super(tex);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer ep)
	{
		ep.openGui(RotaryCraft.instance, GuiRegistry.HANDBOOK.ordinal(), world, 0, 0, 0);
		return itemstack;
	}
}