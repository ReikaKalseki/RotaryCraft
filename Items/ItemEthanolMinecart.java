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

import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Entities.EntityGasMinecart;

public class ItemEthanolMinecart extends ItemRotaryTool {

	public ItemEthanolMinecart(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int s, float a, float b, float c)
	{
		int id = world.getBlockId(x, y, z);
		if (BlockRailBase.isRailBlock(id)) {
			if (!world.isRemote) {
				EntityGasMinecart cart = new EntityGasMinecart(world, x+0.5, y+0.5, z+0.5);
				world.spawnEntityInWorld(cart);
			}
			if (!ep.capabilities.isCreativeMode)
				--is.stackSize;
			return true;
		}
		return false;
	}

}
