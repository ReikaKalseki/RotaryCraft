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

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.InertIInv;
import Reika.RotaryCraft.Base.InventoriedRCTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;

public class TileEntityDeadMachine extends InventoriedRCTileEntity implements InertIInv {

	private Icon[] tex = new Icon[6];
	private String[] img = new String[6];

	private int tileID;
	private int tileMeta;

	private Block parentBlock;

	private ItemStack[] parentInv;

	public void setInvSize(int size) {
		parentInv = new ItemStack[size];
	}

	public void setBlock(Block b, int id, int meta) {
		parentBlock = b;
		tileID = id;
		tileMeta = meta;
	}

	public ArrayList<ItemStack> getDrops(int fortune) {
		ArrayList li = parentBlock.getBlockDropped(worldObj, xCoord, yCoord, zCoord, tileMeta, fortune);
		return li;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (parentBlock == null)
			parentBlock = Block.blocksList[tileID];
		//ReikaJavaLibrary.pConsole(tileID+":"+tileMeta);
	}

	@Override
	public String toString() {
		return "Disabled Machine (Imitating "+this.getImitatedMachine()+")"+" @ "+xCoord+", "+yCoord+", "+zCoord;
	}

	public String getImitatedMachine() {
		return Block.blocksList[tileID].getLocalizedName();
	}

	public ItemStack getOriginalTile() {
		return new ItemStack(tileID, 1, tileMeta);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int getMachineIndex() {
		return 0;//MachineRegistry.DEAD.ordinal();
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
	public int getSizeInventory() {
		if (parentInv == null)
			return 0;
		return parentInv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return parentInv[i];
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
		parentInv[i] = itemstack;
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
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("id", tileID);
		NBT.setInteger("meta", tileMeta);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < parentInv.length; i++)
		{
			if (parentInv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				parentInv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		tileID = NBT.getInteger("id");
		tileMeta = NBT.getInteger("meta");

		NBTTagList nbttaglist = NBT.getTagList("Items");
		parentInv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < parentInv.length)
			{
				parentInv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

}
