/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityPurifier extends InventoriedPowerReceiver implements TemperatureTE {

	private ItemStack[] inv = new ItemStack[7];

	public int cookTime = 0;

	public int temperature;

	public static final int SMELTTEMP = 600;
	public static final int MAXTEMP = 1000;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == 6;
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		cookTime = tickcount;
		tickcount++;
		this.updateTemperature(world, x, y, z, meta);
		this.getSummativeSidedPower();
		if (power < MINPOWER) {
			tickcount = 0;
			return;
		}
		if (!this.canSmelt()) {
			tickcount = 0;
			return;
		}
		if (!this.operationComplete(tickcount, 0))
			return;
		this.smelt();
	}

	private void smelt() {
		tickcount = 0;
		int count = 0;
		for (int i = 1; i < 6; i++) {
			if (this.isModSteel(inv[i])) {
				ReikaInventoryHelper.decrStack(i, inv);
				count++;
			}
		}
		if (count <= 0)
			return;
		ReikaInventoryHelper.addOrSetStack(ItemStacks.steelingot.itemID, count, ItemStacks.steelingot.getItemDamage(), inv, 6);
		if (rand.nextInt(5) == 0)
			ReikaInventoryHelper.decrStack(0, inv);
	}

	public int getCookScaled(int par1) {
		return (par1*cookTime)/this.operationTime(omega, 0);
	}

	private boolean canSmelt() {
		if (temperature < SMELTTEMP)
			return false;
		if (inv[0] == null)
			return false;
		if (inv[0].itemID != Item.gunpowder.itemID)
			return false;
		for (int i = 1; i < 6; i++) {
			if (this.isModSteel(inv[i]))
				return true;
		}
		return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack is) {
		inv[i] = is;
	}

	private boolean isModSteel(ItemStack is) {
		if (is == null)
			return false;
		List steel = ItemStacks.getModSteels();
		for (int i = 0; i < steel.size(); i++) {
			ItemStack s = (ItemStack)steel.get(i);
			if (is.itemID == s.itemID && (is.getItemDamage() == s.getItemDamage() || !s.getHasSubtypes()))
				return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (slot == 0)
			return is.itemID == Item.gunpowder.itemID;
		if (slot == 6)
			return false;
		return this.isModSteel(is);
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.PURIFIER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		temperature = NBT.getInteger("temperature");
		cookTime = NBT.getInteger("time");
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
		NBT.setInteger("temperature", temperature);
		NBT.setInteger("time", cookTime);
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
			ReikaWorldHelper.changeAdjBlock(world, x, y, z, iceside, Block.waterMoving.blockID, 0);
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
		if (temperature > MAXTEMP) {
			temperature = MAXTEMP;
			this.overheat(world, x, y, z);
		}
	}

	public void overheat(World world, int x, int y, int z) {
		world.setBlockToAir(x, y, z);
		ReikaWorldHelper.overheat(world, x, y, z, ItemStacks.scrap.itemID, ItemStacks.scrap.getItemDamage(), 0, 7, false, 1F, false, true, 2F);
	}

	@Override
	public int getThermalDamage() {
		return 0;
	}

	@Override
	public int getRedstoneOverride() {
		if (inv[0] == null)
			return 15;
		if (inv[0].itemID != Item.gunpowder.itemID)
			return 0;
		boolean hasModSteel = false;
		for (int i = 1; i < 6; i++) {
			if (this.isModSteel(inv[i]))
				hasModSteel = true;
		}
		if (!hasModSteel)
			return 15;
		if (inv[6] == null)
			return 0;
		if (ReikaItemHelper.isMatch(inv[6], ItemStacks.steelingot))
			return 15;
		if (inv[6].stackSize >= ItemStacks.steelingot.getMaxStackSize())
			return 15;
		return 0;
	}

	@Override
	public void addTemperature(int temp) {
		temperature += temp;
	}

	@Override
	public int getTemperature() {
		return temperature;
	}

	@Override
	public void onEMP() {}

}
