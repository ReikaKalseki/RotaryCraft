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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;

public class SlotExtractor1 extends Slot {

	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer thePlayer;
	private int field_48437_f;

	public SlotExtractor1(IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack is)
	{
		RotaryCraft.logger.debug("Mod ore: "+ModOreList.getModOreFromOre(is));
		return ReikaBlockHelper.isOre(is);
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1)
	{
		if (this.getHasStack())
		{
			field_48437_f += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}
}
