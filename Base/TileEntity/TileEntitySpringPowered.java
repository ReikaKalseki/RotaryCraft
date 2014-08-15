/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import Reika.DragonAPI.DragonAPICore;
import Reika.RotaryCraft.API.TensionStorage;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntitySpringPowered extends InventoriedRCTileEntity implements ConditionalOperation {

	public boolean isCreativeMode;

	public abstract int getBaseDischargeTime();

	protected final int getUnwindTime() {
		if (isCreativeMode)
			return Integer.MAX_VALUE;
		if (DragonAPICore.debugtest)
			return Integer.MAX_VALUE;
		ItemStack is = inv[this.getCoilSlot()];
		int base = this.getBaseDischargeTime();
		return base*((TensionStorage)is.getItem()).getStiffness(is);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return is.getItem() instanceof TensionStorage && i == this.getCoilSlot();
	}

	@Override
	public final boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return itemstack.getItemDamage() == 0 && i == this.getCoilSlot();
	}

	public int getCoilSlot() {
		return 0;
	}

	protected final ItemStack getDecrementedCharged() {
		ItemStack in = inv[this.getCoilSlot()];
		if (isCreativeMode)
			return in;
		return new ItemStack(in.getItem(), in.stackSize, in.getItemDamage()-1);
	}

	protected final boolean hasCoil() {
		if (isCreativeMode)
			return true;
		if (DragonAPICore.debugtest)
			return true;
		ItemStack is = inv[this.getCoilSlot()];
		if (is == null)
			return false;
		Item i = is.getItem();
		return is.getItemDamage() > 0 && i instanceof TensionStorage;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public final int getInventoryStackLimit() {
		return 1;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isCreativeMode = NBT.getBoolean("creative");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("creative", isCreativeMode);
	}

	@Override
	public boolean areConditionsMet() {
		return this.hasCoil();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Coil";
	}
}