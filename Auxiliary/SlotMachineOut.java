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

public class SlotMachineOut extends Slot {

	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer thePlayer;
	private int field_48437_f;

	public SlotMachineOut(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
		thePlayer = par1EntityPlayer;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack is)
	{
		return false;
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

	/**
	 * Called when the player picks up an item from an inventory slot
	 */
	public void onPickupFromSlot(ItemStack is)
	{
		this.func_48434_c(is);
		super.onPickupFromSlot(thePlayer, is);
	}

	protected void func_48435_a(ItemStack is, int par2)
	{
		field_48437_f += par2;
		this.func_48434_c(is);
	}

	@SuppressWarnings("deprecation")
	protected void func_48434_c(ItemStack is)
	{
		is.onCrafting(thePlayer.worldObj, thePlayer, field_48437_f);
		field_48437_f = 0;
		//ModLoader.takenFromFurnace(thePlayer, is);
	}
}