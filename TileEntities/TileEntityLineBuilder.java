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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityLineBuilder extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation {

	private ForgeDirection dir;
	private ItemStack[] inv = new ItemStack[9];

	private StepTimer timer = new StepTimer(40);
	private boolean isOut = false;

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (power >= MINPOWER && torque >= MINTORQUE)
			phi = 1-timer.getFraction()-0.01F;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LINEBUILDER.ordinal();
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getPower(false, true);

		if (power < MINPOWER || torque < MINTORQUE)
			return;

		timer.update();

		if (timer.checkCap()) {
			if (!world.isRemote) {
				this.shiftBlocks(world, x, y, z);
				phi = 0.5F;
			}
		}
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			readx = xCoord+1;
			readz = zCoord;
			ready = yCoord;
			dir = ForgeDirection.WEST;
			break;
		case 1:
			readx = xCoord-1;
			readz = zCoord;
			ready = yCoord;
			dir = ForgeDirection.EAST;
			break;
		case 2:
			readz = zCoord+1;
			readx = xCoord;
			ready = yCoord;
			dir = ForgeDirection.NORTH;
			break;
		case 3:
			readz = zCoord-1;
			readx = xCoord;
			ready = yCoord;
			dir = ForgeDirection.SOUTH;
			break;
		case 4:	//moving up
			readx = xCoord;
			readz = zCoord;
			ready = yCoord-1;
			dir = ForgeDirection.UP;
			break;
		case 5:	//moving down
			readx = xCoord;
			readz = zCoord;
			ready = yCoord+1;
			dir = ForgeDirection.DOWN;
			break;
		}
	}

	private void shiftBlocks(World world, int x, int y, int z) {
		ItemStack is = this.getNextBlockToAdd();
		if (is == null)
			return;
		if (!this.canShift(world, x, y, z))
			return;
		int r = this.getLineLength();
		for (int i = r; i > 0; i--) {
			int rx = x+dir.offsetX*i;
			int ry = y+dir.offsetY*i;
			int rz = z+dir.offsetZ*i;
			int rx2 = x+dir.offsetX*(i+1);
			int ry2 = y+dir.offsetY*(i+1);
			int rz2 = z+dir.offsetZ*(i+1);
			int id = world.getBlockId(rx, ry, rz);
			int meta = world.getBlockMetadata(rx, ry, rz);
			world.setBlock(rx2, ry2, rz2, id, meta, 3);
			world.setBlock(rx, ry, rz, 0);
		}
		int rx = x+dir.offsetX;
		int ry = y+dir.offsetY;
		int rz = z+dir.offsetZ;
		world.setBlock(rx, ry, rz, is.itemID, is.getItemDamage(), 3);
		SoundRegistry.LINEBUILDER.playSoundAtBlock(world, rx, ry, rz);
		SoundRegistry.LINEBUILDER.playSoundAtBlock(world, x, y, z);
	}

	public boolean canShift(World world, int x, int y, int z) {
		int r = this.getLineLength()+1;
		int rx = xCoord+dir.offsetX*r;
		int ry = yCoord+dir.offsetY*r;
		int rz = zCoord+dir.offsetZ*r;
		boolean softend = ReikaWorldHelper.softBlocks(world, rx, ry, rz);
		return softend && r <= this.getMaxRange() && r > 0;
	}

	public ItemStack getNextBlockToAdd() {
		ItemStack is = ReikaInventoryHelper.getNextBlockInInventory(inv, true);
		if (is == null)
			return null;
		return new ItemStack(ReikaItemHelper.getWorldBlockIDFromItem(is), 1, ReikaItemHelper.getWorldBlockMetaFromItem(is));
	}

	@Override
	public int getRange() {
		int range = this.getLineLength();
		return range;
	}

	public int getLineLength() {
		int len = 0;
		int i = 1;
		int rx = xCoord+dir.offsetX*i;
		int ry = yCoord+dir.offsetY*i;
		int rz = zCoord+dir.offsetZ*i;
		int id = worldObj.getBlockId(rx, ry, rz);
		if (id == Block.bedrock.blockID)
			return Integer.MIN_VALUE;
		int maxr = this.getMaxRange();
		TileEntity te = worldObj.getBlockTileEntity(rx, ry, rz);
		if (te != null)
			return Integer.MIN_VALUE;
		while (!ReikaWorldHelper.softBlocks(worldObj, rx, ry, rz) && i <= maxr) {
			i++;
			rx = xCoord+dir.offsetX*i;
			ry = yCoord+dir.offsetY*i;
			rz = zCoord+dir.offsetZ*i;
			id = worldObj.getBlockId(rx, ry, rz);
			if (id == Block.bedrock.blockID)
				return Integer.MIN_VALUE;
			TileEntity tile = worldObj.getBlockTileEntity(rx, ry, rz);
			if (tile != null)
				return Integer.MIN_VALUE;
		}
		return i-1;
	}

	@Override
	public int getMaxRange() {
		return Math.max(64, ConfigRegistry.LINEBUILDER.getValue());
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv [i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
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

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Blocks";
	}

}
