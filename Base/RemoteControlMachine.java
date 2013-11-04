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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class RemoteControlMachine extends TileEntitySpringPowered {

	public int[] colors = new int[3];

	protected int tickcount2 = 0;
	protected boolean on = false;

	public abstract void activate(World world, EntityPlayer ep, int x, int y, int z);

	protected void setColors() {
		for (int i = 0; i < 3; i++) {
			if (inv[i+1] == null)
				colors[i] = -1;
			else if (inv[i+1].itemID != Item.dyePowder.itemID)
				colors[i] = -1;
			else
				colors[i] = inv[i+1].getItemDamage();
			if (colors[i] == -1)
				on = false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		colors = NBT.getIntArray("color");
	}

	/**
	 * Writes a tile entity to NBT.  Maybe was not saving inv since seems to be acting like
	 * extends TileEntityPowerReceiver, NOT InventoriedPowerReceiver
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setIntArray("color", colors);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		if (i == 0)
			return super.isItemValidForSlot(i, is);
		return is.itemID == Item.dyePowder.itemID;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public int getBaseDischargeTime() {
		return 120;
	}

}
