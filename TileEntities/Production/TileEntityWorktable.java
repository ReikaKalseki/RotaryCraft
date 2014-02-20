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

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.ChargeableTool;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Base.ItemChargedArmor;
import Reika.RotaryCraft.Base.ItemChargedTool;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWorktable extends InventoriedRCTileEntity {

	private ItemStack[] inventory = new ItemStack[18];
	public boolean craftable = false;
	private ItemStack toCraft;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (!world.isRemote) {
			this.chargeTools();
			this.makeJetplate();
			this.makeBedjump();

			if (world.isBlockIndirectlyGettingPowered(x, y, z))
				if (this.canUncraft())
					this.uncraft();
		}
	}

	private void makeBedjump() {
		int armorslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDBOOTS.getShiftedID(), inventory);
		int jumpslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JUMP.getShiftedID(), inventory);
		if (jumpslot != -1 && armorslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inventory, 16)) {
			inventory[jumpslot] = null;
			inventory[armorslot] = null;
			ItemStack is = ItemRegistry.BEDJUMP.getEnchantedStack();
			inventory[9] = is;
		}
	}

	public boolean canUncraft() {
		boolean can = false;
		for (int i = 0; i < 9; i++) {
			ItemStack is = inventory[i];
			if (i == 4) {
				if (is == null)
					return false;
				else {
					IRecipe ir = WorktableRecipes.getInstance().getInputRecipe(is);
					if (ir == null)
						return false;
					else {
						ItemStack[] in = new ItemStack[9];
						ReikaRecipeHelper.copyRecipeToItemStackArray(in, ir);
						boolean flag = true;
						for (int k = 0; k < 9; k++) {
							if (inventory[k+9] != null) {
								if (!ReikaItemHelper.matchStacks(inventory[k+9], in[k]))
									flag = false;
								if (inventory[k+9].stackSize >= Math.min(this.getInventoryStackLimit(), inventory[k+9].getMaxStackSize()))
									flag = false;
							}
						}
						can = flag;
					}
				}
			}
			else {
				if (is != null)
					return false;
			}
		}
		return can;
	}

	private void uncraft() {
		ItemStack is = inventory[4];
		IRecipe ir = WorktableRecipes.getInstance().getInputRecipe(is);
		ItemStack[] in = new ItemStack[9];
		ReikaRecipeHelper.copyRecipeToItemStackArray(in, ir);

		for (int i = 0; i < ir.getRecipeOutput().stackSize; i++)
			ReikaInventoryHelper.decrStack(4, inventory);

		for (int i = 0; i < 9; i++) {
			if (inventory[i+9] == null) {
				inventory[i+9] = in[i];
			}
			else {
				inventory[i+9] = ReikaItemHelper.getSizedItemStack(inventory[i+9], inventory[i+9].stackSize+1);
			}
		}
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.WORKTABLE;
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
		if (coilslot == -1)
			coilslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.STRONGCOIL.getShiftedID(), inventory);
		int toolid = this.getTool();
		int toolslot = ReikaInventoryHelper.locateInInventory(toolid, inventory);
		if (toolslot != -1 && coilslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inventory, 16)) {
			int coilid = inventory[coilslot].itemID;
			int toolmeta = inventory[toolslot].getItemDamage();
			int coilmeta = inventory[coilslot].getItemDamage();
			ItemStack newtool = new ItemStack(toolid, 1, coilmeta);
			ItemStack newcoil = new ItemStack(coilid, 1, toolmeta);
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
				if (is.getItem() instanceof ItemChargedTool || is.getItem() instanceof ItemChargedArmor || is.getItem() instanceof ChargeableTool)
					return inventory[i].itemID;
			}
		}
		return -1;
	}

	private void makeJetplate() {
		int plateslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.BEDCHEST.getShiftedID(), inventory);
		int jetslot = ReikaInventoryHelper.locateInInventory(ItemRegistry.JETPACK.getShiftedID(), inventory);
		if (jetslot != -1 && plateslot != -1 && ReikaInventoryHelper.hasNEmptyStacks(inventory, 16)) {
			ItemStack jet = inventory[jetslot];
			int original = jet.stackTagCompound != null ? jet.stackTagCompound.getInteger("fuel") : 0;
			inventory[jetslot] = null;
			inventory[plateslot] = null;
			ItemStack is = ItemRegistry.BEDPACK.getEnchantedStack();
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
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
