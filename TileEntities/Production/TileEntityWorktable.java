/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Production;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Auxiliary.ModList;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWorktable extends RotaryCraftTileEntity implements ISidedInventory {

	private ItemStack[] inventory = new ItemStack[18];
	public boolean craftable = false;
	private ItemStack toCraft;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.chargeTools();
		if (ModList.INDUSTRIALCRAFT.isLoaded())
			this.makeJetplate();
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.WORKTABLE.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	private void chargeTools() {
		int coilslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.SPRING.getShiftedID(), inventory);
		int toolid = this.getTool();
		int toolslot = ReikaInventoryHelper.locateInInventory(toolid, inventory);
		if (toolslot != -1 && coilslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inventory, 16)) {
			int toolmeta = inventory[toolslot].getItemDamage();
			int coilmeta = inventory[coilslot].getItemDamage();
			ItemStack newtool = new ItemStack(toolid, 1, coilmeta);
			ItemStack newcoil = new ItemStack(ItemRegistry.SPRING.getShiftedID(), 1, toolmeta);
			inventory[toolslot] = null;
			inventory[coilslot] = null;
			inventory[9] = newtool;
			inventory[10] = newcoil;
		}
	}

	private int getTool() {
		for (int i = 0; i < 9; i++) {
			ItemStack is = inventory[i];
			if (is != null) {
				if (is.getItem() instanceof ItemChargedTool || is.itemID == ItemRegistry.NVG.getShiftedID())
					return inventory[i].itemID;
			}
		}
		return -1;
	}

	private void makeJetplate() {
		int plateslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDCHEST.getShiftedID(), inventory);
		int jetslot = ReikaInventoryHelper.locateInInventory(ic2.api.item.Items.getItem("electricJetpack").itemID, inventory);
		if (jetslot != -1 && plateslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inventory, 16)) {
			int original = (int)(((float)(inventory[jetslot].getMaxDamage()-inventory[jetslot].getItemDamage()))/(inventory[jetslot].getMaxDamage()-1)*30000);
			inventory[jetslot] = null;
			inventory[plateslot] = null;
			ItemStack is = ItemRegistry.JETCHEST.getEnchantedStack();
			if (is.stackTagCompound == null)
				is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setInteger("charge", original);
			inventory[9] = is;
		}
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return ReikaInventoryHelper.decrStackSize(this, i, j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return ReikaInventoryHelper.getStackInSlotOnClosing(this, i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i >= 9)
			return false;
		return i < 9;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i >= 9;
	}

	public ItemStack getToCraft() {
		if (toCraft == null)
			return null;
		return toCraft.copy();
	}

	public void setToCraft(ItemStack is) {
		if (is != null)
			toCraft = is.copy();
		else toCraft = null;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inventory.length)
			{
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public void onEMP() {}
}
