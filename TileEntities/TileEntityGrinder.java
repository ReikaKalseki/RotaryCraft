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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RecipesGrinder;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Models.ModelGrinder;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityGrinder extends TileEntityInventoriedPowerReceiver
{
	private ItemStack inventory[];

	/** The number of ticks that the current item has been cooking for */
	public int grinderCookTime;

	public boolean idle = false;

	public int lubricant = 0;
	public static final int MAXLUBE = 4000;

	public TileEntityGrinder()
	{
		inventory = new ItemStack[3];
		grinderCookTime = 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	public void testIdle() { //Test if have sufficient power, only idling if powered but empty
		idle = (!this.canSmelt() && power > MINPOWER && torque > MINTORQUE);
	}

	public boolean getReceptor(World world, int x, int y, int z, int metadata) {
		if (y == 0)
			return false;
		int id = 0;
		switch (metadata) {
			case 0:
				id = world.getBlockId(x+1, y, z);
				readx = x+1;
				readz = z;
				break;
			case 1:
				id = world.getBlockId(x-1, y, z);
				readx = x-1;
				readz = z;
				break;
			case 2:
				id = world.getBlockId(x, y, z+1);
				readx = x;
				readz = z+1;
				break;
			case 3:
				id = world.getBlockId(x, y, z-1);
				readx = x;
				readz = z-1;
				break;
			default:
				id = 0;
				break;
		}
		ready = yCoord;
		//ReikaWorldHelper.legacySetBlockWithNotify(world, readx, ready+3, readz, 4);
		return true;
	}

	public void readPower() {
		if (!this.getReceptor(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata()))
			return;
		super.getPower(false, false);
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 1200-this.omega, "max")));
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inventory.length;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	public ItemStack getStackInSlot(int par1)
	{
		return inventory[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
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

		grinderCookTime = NBT.getShort("CookTime");
		lubricant = NBT.getInteger("lube");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setShort("CookTime", (short)grinderCookTime);
		NBT.setInteger("lube", lubricant);
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
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	public int getCookProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.tickcount);
		return (grinderCookTime * par1)/2 / this.operationTime(omega, 0);
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.testIdle();
		boolean flag1 = false;
		tickcount++;
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d  %d  %d", this.power, this.omega, this.torque));

		if (this.canSmelt()) {
			flag1 = true;/*
				if (inventory[1] != null) {
					inventory[1].stackSize--;
					if (inventory[1].stackSize <= 0)
						inventory[1] = null;
				}*/
		}
		if (this.canSmelt()) {
			grinderCookTime++;
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(String.format("%d", ReikaMathLibrary.extrema(2, 600-this.omega, "max")));
			if (this.operationComplete(grinderCookTime, 0)) {
				grinderCookTime = 0;
				tickcount = 0;
				this.smeltItem();
				flag1 = true;
			}
		}
		else
			grinderCookTime = 0;
		if (flag1)
			this.onInventoryChanged();
		if (inventory[2] != null && lubricant >= ItemFuelLubeBucket.value[0]) {
			if (inventory[2].itemID == Item.bucketEmpty.itemID && inventory[2].stackSize == 1) {
				inventory[2] = ItemStacks.lubebucket;
				lubricant -= ItemFuelLubeBucket.value[0];
			}
		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canSmelt()
	{
		this.readPower();
		if (!(power >= MINPOWER && torque >= MINTORQUE))
			return false;
		if (inventory[0] == null)
		{
			return false;
		}

		if (inventory[0].itemID == ItemRegistry.CANOLA.getID() && inventory[0].getItemDamage() == 0) {
			return (lubricant < MAXLUBE);
		}
		if (inventory[0].itemID == ItemRegistry.CANOLA.getID() && inventory[0].getItemDamage() == 1) {
			return (lubricant < MAXLUBE-9);
		}

		ItemStack itemstack = RecipesGrinder.smelting().getSmeltingResult(inventory[0].getItem().itemID);

		if (itemstack == null)
		{
			return false;
		}

		if (inventory[1] == null)
		{
			return true;
		}

		if (!inventory[1].isItemEqual(itemstack))
		{
			return false;
		}

		if (inventory[1].stackSize < this.getInventoryStackLimit() && inventory[1].stackSize < inventory[1].getMaxStackSize())
		{
			return true;
		}

		return inventory[1].stackSize < itemstack.getMaxStackSize();
	}

	public int getLubricantScaled(int par1)
	{
		return (lubricant*par1)/MAXLUBE;
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	public void smeltItem()
	{
		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("$#%#%$#");
		if (!this.canSmelt())
		{
			return;
		}
		if (inventory[0] != null && inventory[0].itemID == ItemRegistry.CANOLA.getID()) {
			int num = 1;
			if (inventory[0].getItemDamage() == 1)
				num = 9;
			inventory[0].stackSize -= num;
			lubricant = ReikaMathLibrary.extrema(lubricant+(par5Random.nextInt(16)+1)*num, MAXLUBE, "min");
			if (inventory[0].stackSize <= 0)
				inventory[0] = null;
			return;
		}

		ItemStack itemstack = RecipesGrinder.smelting().getSmeltingResult(inventory[0].getItem().itemID);
		if (inventory[1] == null)
		{
			inventory[1] = itemstack.copy();
		}
		else if (inventory[1].itemID == itemstack.itemID)
		{
			inventory[1].stackSize += itemstack.stackSize;
		}

		//  if (inventory[0].getItem().func_46056_k())
		//  {
		//          inventory[0] = new ItemStack(inventory[0].getItem().setFull3D());
		//  }
		//  else
		//   {
		inventory[0].stackSize--;
		//  }

		if (inventory[0].stackSize <= 0)
		{
			inventory[0] = null;
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelGrinder();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER || torque < MINTORQUE)
			return;
		phi += 0.85F*ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.GRINDER.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		if (slot == 1)
			return false;
		if (slot == 2)
			return is.itemID == Item.bucketEmpty.itemID;
		return RecipesGrinder.smelting().getSmeltingResult(is.itemID) != null;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.canSmelt())
			return 15;
		return 0;
	}
}
