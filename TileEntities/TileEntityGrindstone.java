/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGrindstone extends InventoriedPowerLiquidReceiver implements DiscreteFunction, ConditionalOperation, OneSlotMachine {

	ItemStack[] inv = new ItemStack[1];

	private static final String NBT_TAG = "repairs";

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSides(world, x, y, z, meta);
		this.getPower(true, false);
		tickcount++;
		if (power < MINPOWER || torque < MINTORQUE) {
			return;
		}
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;

		if (world.isRemote)
			return;

		if (this.hasValidItem()) {
			this.createUsesTag();
			this.repair();
		}
	}

	private void createUsesTag() {
		if (inv[0].stackTagCompound == null)
			inv[0].stackTagCompound = new NBTTagCompound();
		if (!inv[0].stackTagCompound.hasKey(NBT_TAG))
			inv[0].stackTagCompound.setInteger(NBT_TAG, inv[0].getMaxDamage()*2);
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		switch(metadata) {
		case 1:
			readx = xCoord+1;
			readz = zCoord;
			readx2 = xCoord-1;
			readz2 = zCoord;
			break;
		case 0:
			readz = zCoord+1;
			readx = xCoord;
			readx2 = xCoord;
			readz2 = zCoord-1;
			break;
		}
		ready = yCoord;
		ready2 = yCoord;
	}

	private void repair() {
		int dmg = inv[0].getItemDamage();
		int newdmg = dmg-1;
		inv[0].setItemDamage(newdmg);
		int repair = inv[0].stackTagCompound.getInteger(NBT_TAG);
		inv[0].stackTagCompound.setInteger(NBT_TAG, repair-1);
		//ReikaJavaLibrary.pConsole(inv[0].stackTagCompound);
	}

	public int getMinimumDamageForItem(ItemStack is) {
		return is.stackTagCompound != null && is.stackTagCompound.hasKey(NBT_TAG) ? is.getMaxDamage()-MathHelper.ceiling_float_int(is.stackTagCompound.getInteger(NBT_TAG)/2F) : 0;
	}

	public boolean hasValidItem() {
		if (inv[0] == null)
			return false;
		if (!this.isItemValidForSlot(0, inv[0]))
			return false;
		return inv[0].getItemDamage() > this.getMinimumDamageForItem(inv[0]);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int j) {
		return is.getItemDamage() <= this.getMinimumDamageForItem(is);
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is) {
		inv[i] = is;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.isItemStackDamageable() && (is.getItem() instanceof ItemShears || is.getItem() instanceof ItemSword || is.getItem() instanceof ItemTool);
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	@Override
	public int getCapacity() {
		return 1000;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.GRINDSTONE.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public boolean areConditionsMet() {
		return this.hasValidItem();
	}

	@Override
	public String getOperationalStatus() {
		return inv[0] == null ? "No Tool" : this.areConditionsMet() ? "Operational" : "Invalid Item";
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.GRINDSTONE.getOperationTime(omega);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

}
