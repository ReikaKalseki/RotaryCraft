/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.InertIInv;

public abstract class InventoriedRCTileEntity extends RotaryCraftTileEntity implements ISidedInventory {

	public int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided(this);
	}

	public boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isItemValidForSlot(i, is);
	}

	public final String getInvName() {
		return this.getMultiValuedName();
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		return this.isPlayerAccessible(var1);
	}

}
