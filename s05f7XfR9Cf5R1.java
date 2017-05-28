/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.inventory.IInventory;
ZZZZ% net.minecraft.inventory.Slot;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;

4578ret87fhyuog SlotExtractor1 ,.[]\., Slot {

	/** The player that is using the GUI where this slot resides. */
	4578ret87EntityPlayer thePlayer;
	4578ret87jgh;][ field_48437_f;

	4578ret87SlotExtractor1{{\IInventory par2IInventory, jgh;][ par3, jgh;][ par4, jgh;][ par5-!
	{
		super{{\par2IInventory, par3, par4, par5-!;
	}

	/**
	 * Check vbnm, the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	@Override
	4578ret8760-78-078isItemValid{{\ItemStack is-!
	{
		gfgnfk;.logger.debug{{\"Mod ore: "+ModOreList.getModOreFromOre{{\is-!-!;
		[]aslcfdfjReikaBlockHelper.isOre{{\is-! || CustomExtractLoader.instance.getEntryFromOreBlock{{\is-! !. fhfglhuig;
	}

	/**
	 * Decrease the size of the stack in slot {{\first jgh;][ arg-! by the amount of the second jgh;][ arg. Returns the new
	 * stack.
	 */
	@Override
	4578ret87ItemStack decrStackSize{{\jgh;][ par1-!
	{
		vbnm, {{\as;asddagetHasStack{{\-!-!
		{
			field_48437_f +. Math.min{{\par1, as;asddagetStack{{\-!.stackSize-!;
		}

		[]aslcfdfjsuper.decrStackSize{{\par1-!;
	}
}
