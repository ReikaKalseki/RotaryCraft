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
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Registry.LiquidRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBucketFiller extends TileEntityInventoriedPowerReceiver implements PipeConnector {

	public ItemStack[] inv = new ItemStack[18];
	public boolean filling = true;

	public int waterLevel = 0;
	public int lavaLevel = 0;
	public int lubeLevel = 0;
	public int fuelLevel = 0;

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

	public void getFuel(World world, int x, int y, int z, int metadata) {
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Water!");
		int oldLevel = 0;
		if (fuelLevel < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.FUELLINE) {
				TileEntityFuelLine tile = (TileEntityFuelLine)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && tile.fuel > 0) {
					oldLevel = tile.fuel;
					tile.fuel = ReikaMathLibrary.extrema(tile.fuel-tile.fuel/4-1, 0, "max");
					fuelLevel = ReikaMathLibrary.extrema(fuelLevel+oldLevel/4+1, 0, "max");
				}
			}
		}
	}

	public void getLube(World world, int x, int y, int z, int metadata) {
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Water!");
		int oldLevel = 0;
		if (lubeLevel < CAPACITY) {
			if (MachineRegistry.getMachine(world, x+1, y, z) == MachineRegistry.HOSE) {
				TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x+1, y, z);
				if (tile != null && tile.lubricant > 0) {
					oldLevel = tile.lubricant;
					tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-tile.lubricant/4-1, 0, "max");
					lubeLevel = ReikaMathLibrary.extrema(lubeLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x-1, y, z) == MachineRegistry.HOSE) {
				TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x-1, y, z);
				if (tile != null && tile.lubricant > 0) {
					oldLevel = tile.lubricant;
					tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-tile.lubricant/4-1, 0, "max");
					lubeLevel = ReikaMathLibrary.extrema(lubeLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z+1) == MachineRegistry.HOSE) {
				TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z+1);
				if (tile != null && tile.lubricant > 0) {
					oldLevel = tile.lubricant;
					tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-tile.lubricant/4-1, 0, "max");
					lubeLevel = ReikaMathLibrary.extrema(lubeLevel+oldLevel/4+1, 0, "max");
				}
			}
			if (MachineRegistry.getMachine(world, x, y, z-1) == MachineRegistry.HOSE) {
				TileEntityHose tile = (TileEntityHose)world.getBlockTileEntity(x, y, z-1);
				if (tile != null && tile.lubricant > 0) {
					oldLevel = tile.lubricant;
					tile.lubricant = ReikaMathLibrary.extrema(tile.lubricant-tile.lubricant/4-1, 0, "max");
					lubeLevel = ReikaMathLibrary.extrema(lubeLevel+oldLevel/4+1, 0, "max");
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
		return LiquidRegistry.isLiquidItem(is) || is.itemID == Item.bucketEmpty.itemID;
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
			//ReikaJavaLibrary.pConsole(fuelLevel);
			this.getWater(world, x, y, z, meta);
			this.getLava(world, x, y, z, meta);
			this.getLube(world, x, y, z, meta);
			this.getFuel(world, x, y, z, meta);
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
		ItemStack is = new ItemStack(Item.bucketEmpty);
		int slot = ReikaInventoryHelper.locateInInventory(Item.bucketLava.itemID, inv);
		if (slot != -1) {
			lavaLevel++;
			ReikaInventoryHelper.decrStack(slot, inv);
			if (!ReikaInventoryHelper.addToIInv(is, this))
				ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
		}
		else {
			slot = ReikaInventoryHelper.locateInInventory(Item.bucketWater.itemID, inv);
			if (slot != -1) {
				waterLevel++;
				ReikaInventoryHelper.decrStack(slot, inv);
				if (!ReikaInventoryHelper.addToIInv(is, this))
					ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
			}
			else {
				slot = ReikaInventoryHelper.locateInInventory(ItemStacks.lubebucket.itemID, ItemStacks.lubebucket.getItemDamage(), inv);
				if (slot != -1) {
					lubeLevel += ItemFuelLubeBucket.LUBE_VALUE;
					ReikaInventoryHelper.decrStack(slot, inv);
					if (!ReikaInventoryHelper.addToIInv(is, this))
						ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
				}
				else {
					slot = ReikaInventoryHelper.locateInInventory(ItemStacks.fuelbucket.itemID, ItemStacks.fuelbucket.getItemDamage(), inv);
					if (slot != -1) {
						fuelLevel += ItemFuelLubeBucket.JET_VALUE;
						ReikaInventoryHelper.decrStack(slot, inv);
						if (!ReikaInventoryHelper.addToIInv(is, this))
							ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
					}
				}
			}
		}
	}

	private void fillBuckets() {
		int slot = ReikaInventoryHelper.locateInInventory(Item.bucketEmpty.itemID, inv);
		if (slot == -1)
			return;/*
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
		}*/
		if (waterLevel > 0) {
			if (!ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketWater), this)) {
				//ReikaJavaLibrary.pConsole(false);
				return;
			}
			waterLevel--;
			ReikaInventoryHelper.decrStack(slot, inv);
		}
		slot = ReikaInventoryHelper.locateInInventory(Item.bucketEmpty.itemID, inv);
		if (slot == -1)
			return;
		if (lavaLevel > 0) {
			if (!ReikaInventoryHelper.addToIInv(new ItemStack(Item.bucketLava), this))
				return;
			lavaLevel--;
			ReikaInventoryHelper.decrStack(slot, inv);
		}
		slot = ReikaInventoryHelper.locateInInventory(Item.bucketEmpty.itemID, inv);
		if (slot == -1)
			return;
		if (lubeLevel >= ItemFuelLubeBucket.LUBE_VALUE) {
			if (!ReikaInventoryHelper.addToIInv(ItemStacks.lubebucket.copy(), this))
				return;
			lubeLevel -= ItemFuelLubeBucket.LUBE_VALUE;
			ReikaInventoryHelper.decrStack(slot, inv);
		}
		slot = ReikaInventoryHelper.locateInInventory(Item.bucketEmpty.itemID, inv);
		if (slot == -1)
			return;
		if (fuelLevel >= ItemFuelLubeBucket.JET_VALUE) {
			if (!ReikaInventoryHelper.addToIInv(ItemStacks.fuelbucket.copy(), this))
				return;
			fuelLevel -= ItemFuelLubeBucket.JET_VALUE;
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
		lavaLevel = NBT.getInteger("lava");
		lubeLevel = NBT.getInteger("lube");
		fuelLevel = NBT.getInteger("fuel");
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
		NBT.setInteger("lube", lubeLevel);
		NBT.setInteger("fuel", fuelLevel);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if (filling)
			return j == 0 && LiquidRegistry.isLiquidItem(itemstack);
		return j == 0 && itemstack.itemID == Item.bucketEmpty.itemID;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.HOSE || m == MachineRegistry.PIPE || m == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return !side.isTopOrBottom();
	}

}
