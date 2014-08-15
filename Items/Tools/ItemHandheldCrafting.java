/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHandheldCrafting extends ItemRotaryTool {

	public ItemStack[] items = new ItemStack[9];

	public ItemHandheldCrafting(int tex) {
		super(tex);
		maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (!world.isRemote)
			ep.openGui(RotaryCraft.instance, GuiRegistry.HANDCRAFT.ordinal(), world, 0, 0, 0);
		return is;
	}

}