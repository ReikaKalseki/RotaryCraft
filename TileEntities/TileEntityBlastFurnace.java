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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;

public class TileEntityBlastFurnace extends RotaryCraftTileEntity implements IInventory, TemperatureTE, ISidedInventory {

	public int temperature;
	public ItemStack[] inventory = new ItemStack[14];
	public int meltTime = 0;

	public static final int SMELTTEMP = 600;
	public static final int MAXTEMP = 1200;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		tickcount++;
		if (tickcount >= 20) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
		}
		if (temperature < SMELTTEMP) {
			meltTime = 0;
			return;
		}
		if (!this.haveIngredients()) {
			meltTime = 0;
			return;
		}
		meltTime++;
		if (meltTime >= this.getMeltTime()) {
			this.smelt();
		}
	}

	private boolean haveIngredients() {
		if (inventory[0] == null)
			return false;
		if (inventory[0].itemID != Item.coal.itemID)
			return false;
		if (inventory[11] == null)
			return false;
		if (inventory[11].itemID != Item.gunpowder.itemID)
			return false;


		if (inventory[10] != null) {
			if (inventory[10].itemID != ItemStacks.steelingot.itemID || inventory[10].getItemDamage() != ItemStacks.steelingot.getItemDamage() || inventory[10].stackSize+9 >= ItemStacks.steelingot.getMaxStackSize()) {
				if (inventory[13] != null) {
					if (inventory[13].itemID != ItemStacks.steelingot.itemID || inventory[13].getItemDamage() != ItemStacks.steelingot.getItemDamage() || inventory[13].stackSize+9 >= ItemStacks.steelingot.getMaxStackSize()) {
						if (inventory[12] != null) {
							if (inventory[12].itemID != ItemStacks.steelingot.itemID || inventory[12].getItemDamage() != ItemStacks.steelingot.getItemDamage() || inventory[12].stackSize+9 >= ItemStacks.steelingot.getMaxStackSize()) {
								return false;
							}
						}
					}
				}
			}
		}
		if (!ReikaInventoryHelper.checkForItem(Item.ingotIron.itemID, inventory))
			return false;
		return true;
	}

	public int getTemperatureScaled(int p1) {
		return ((p1*temperature)/MAXTEMP);
	}

	private void smelt() {
		meltTime = 0;
		if (worldObj.isRemote)
			return;
		ReikaInventoryHelper.decrStack(0, inventory);
		int num = ReikaInventoryHelper.countNumStacks(Item.ingotIron.itemID, -1, inventory);
		if ((int)Math.sqrt(num) > 1 && par5Random.nextInt(3) == 0) {
			if (par5Random.nextInt((int)Math.sqrt(num)) > 0) {
				ReikaInventoryHelper.decrStack(11, inventory);
			}
		}
		if (ReikaMathLibrary.doWithChance(ReikaMathLibrary.intpow(1.005, num*num))) {
			num *= 1+par5Random.nextFloat();
		}
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(num));
		if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inventory, 10))
			if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inventory, 12))
				if (!ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, num, ItemStacks.steelingot.getItemDamage(), inventory, 13))
					if (!this.checkSpreadFit(num))
						return;
		for (int i = 1; i < inventory.length-1; i++) {
			if (inventory[i] != null) {
				if (inventory[i].itemID == Item.ingotIron.itemID) {
					ReikaInventoryHelper.decrStack(i, inventory);
				}
			}
		}
		ReikaWorldHelper.splitAndSpawnXP(worldObj, xCoord+par5Random.nextFloat(), yCoord+1.25F, zCoord+par5Random.nextFloat(), (int)(0.6*num));
	}

	private boolean checkSpreadFit(int num) {
		int maxfit = 0;
		int f1 = ItemStacks.steelingot.getMaxStackSize()-inventory[10].stackSize;
		int f2 = ItemStacks.steelingot.getMaxStackSize()-inventory[12].stackSize;
		int f3 = ItemStacks.steelingot.getMaxStackSize()-inventory[13].stackSize;
		maxfit = f1+f2+f3;
		if (num > maxfit)
			return false;
		if (f1 > num) {
			inventory[10].stackSize += num;
			return true;
		}
		else {
			inventory[10].stackSize = inventory[10].getMaxStackSize();
			num -= f1;
		}
		if (f2 > num) {
			inventory[12].stackSize += num;
			return true;
		}
		else {
			inventory[12].stackSize = inventory[12].getMaxStackSize();
			num -= f2;
		}
		if (f3 > num) {
			inventory[12].stackSize += num;
			return true;
		}
		else {
			inventory[13].stackSize = inventory[13].getMaxStackSize();
			num -= f3;
		}
		return true;
	}

	public int getMeltTime() {
		int time = 2*((MAXTEMP-(temperature-SMELTTEMP))/12);
		if (time < 1)
			return 1;
		return time;
	}

	public int getCookScaled(int p1) {
		if (temperature < SMELTTEMP)
			return 0;
		return ((p1*meltTime)/this.getMeltTime());
	}

	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);

		int waterside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.water);
		if (waterside != -1) {
			Tamb /= 2;
		}
		int iceside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.ice.blockID);
		if (iceside != -1) {
			if (Tamb > 0)
				Tamb /= 4;
			ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Block.waterMoving.blockID);
		}
		int fireside = ReikaWorldHelper.checkForAdjBlock(world, x, y, z, Block.fire.blockID);
		if (fireside != -1) {
			Tamb += 200;
		}
		int lavaside = ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava);
		if (lavaside != -1) {
			Tamb += 600;
		}
		if (temperature > Tamb)
			temperature--;
		if (temperature > Tamb*2)
			temperature--;
		if (temperature < Tamb)
			temperature++;
		if (temperature*2 < Tamb)
			temperature++;
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
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

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inventory[par1];
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
	 * stack.
	 */
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (inventory[par1] != null)
		{
			if (inventory[par1].stackSize <= par2)
			{
				ItemStack itemstack = inventory[par1];
				inventory[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inventory[par1].splitStack(par2);

			if (inventory[par1].stackSize <= 0)
			{
				inventory[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	/**


	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inventory[par1] != null)
		{
			ItemStack itemstack = inventory[par1];
			inventory[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 *
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		double dist = ReikaMathLibrary.py3d(xCoord-var1.posX, yCoord-var1.posY, zCoord-var1.posZ);
		return (dist <= 8);
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("melt", meltTime);
		NBT.setInteger("temp", temperature);

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
		meltTime = NBT.getInteger("melt");
		temperature = NBT.getInteger("temp");

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
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is) {
		if (is == null)
			return false;
		if (i == 0)
			return is.itemID == Item.coal.itemID;
		else if (i <= 9)
			return is.itemID == Item.ingotIron.itemID;
		else if (i == 11)
			return is.itemID == Item.gunpowder.itemID;
		else
			return false;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
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
		return MachineRegistry.BLASTFURNACE.ordinal();
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}
}
