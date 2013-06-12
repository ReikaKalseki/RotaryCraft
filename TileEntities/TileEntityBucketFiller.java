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

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBucketFiller extends TileEntityInventoriedPowerReceiver {

	public ItemStack[] inv = new ItemStack[18];
	public boolean filling = true;

	public int waterLevel = 0;
	public int lavaLevel = 0;

	public static final int CAPACITY = 24;

	public void getLava(World world, int x, int y, int z, int metadata) {
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Lava!");
		int oldLevel = 0;
		if (lavaLevel < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && tile.liquidID == 11 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					lavaLevel = ReikaMathLibrary.extrema(lavaLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && tile.liquidID == 11 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					lavaLevel = ReikaMathLibrary.extrema(lavaLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && tile.liquidID == 11 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					lavaLevel = ReikaMathLibrary.extrema(lavaLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && tile.liquidID == 11 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					lavaLevel = ReikaMathLibrary.extrema(lavaLevel+oldLevel/4+1, 0, "max");
				}
			}
		}
	}

	public void getWater(World world, int x, int y, int z, int metadata) {
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Water!");
		int oldLevel = 0;
		if (waterLevel < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && tile.liquidID == 9 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && tile.liquidID == 9 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && tile.liquidID == 9 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.PIPE) {
				TileEntityPipe tile = (TileEntityPipe)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && tile.liquidID == 9 && tile.liquidLevel > 0) {
					oldLevel = tile.liquidLevel;
					tile.liquidLevel = ReikaMathLibrary.extrema(tile.liquidLevel-tile.liquidLevel/4-1, 0, "max");
					waterLevel = ReikaMathLibrary.extrema(waterLevel+oldLevel/4+1, 0, "max");
				}
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		if (filling)
			return is.itemID == Item.bucketEmpty.itemID;
		return is.itemID == Item.bucketLava.itemID || is.itemID == Item.bucketWater.itemID;
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
		return MachineRegistry.BUCKETFILLER.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		if (omega < MINSPEED)
			return;
		if (filling) {
			this.getWater(world, x, y, z, meta);
			this.getLava(world, x, y, z, meta);
			if (!this.operationComplete(tickcount, 0))
				return;
			tickcount = 0;
			this.fillBuckets();
		}
		else {
			if (!this.operationComplete(tickcount, 0))
				return;
			tickcount = 0;
			this.emptyBuckets();
		}
	}

	private void emptyBuckets() {
		int slot = ReikaInventoryHelper.locateInInventory(Item.bucketLava.itemID, inv);
		if (slot != -1) {
			lavaLevel++;
			ReikaInventoryHelper.decrStack(slot, inv);
			ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketEmpty), this);
		}
		else {
			slot = ReikaInventoryHelper.locateInInventory(Item.bucketWater.itemID, inv);
			if (slot != -1) {
				waterLevel++;
				ReikaInventoryHelper.decrStack(slot, inv);
				ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketEmpty), this);
			}
		}
	}

	private void fillBuckets() {
		int slot = ReikaInventoryHelper.locateInInventory(Item.bucketEmpty.itemID, inv);
		if (slot == -1)
			return;
		if (waterLevel > 0 && lavaLevel > 0) {
			if (par5Random.nextBoolean()) {
				if (!ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketLava), this))
					return;
				lavaLevel--;
			}
			else {
				if (!ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketWater), this))
					return;
				waterLevel--;
			}
			ReikaInventoryHelper.decrStack(slot, inv);
			//ReikaJavaLibrary.pConsole(inv);
		}
		else if (waterLevel > 0) {
			if (!ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketWater), this))
				return;
			waterLevel--;
			ReikaInventoryHelper.decrStack(slot, inv);
		}
		else if (lavaLevel > 0) {
			if (!ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketLava), this))
				return;
			lavaLevel--;
			ReikaInventoryHelper.decrStack(slot, inv);
		}
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
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");
			if (byte0 >= 0 && byte0 < inv.length)
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
		waterLevel = NBT.getInteger("water");
		lavaLevel = NBT.getInteger("level");
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		NBT.setTag("Items", nbttaglist);

		NBT.setInteger("water", waterLevel);
		NBT.setInteger("lava", lavaLevel);
	}

}
