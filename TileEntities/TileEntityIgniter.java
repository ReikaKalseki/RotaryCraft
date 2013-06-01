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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;

public class TileEntityIgniter extends TileEntityInventoriedPowerReceiver implements TemperatureTE, RangedEffect {

	public int temperature;
	private ItemStack[] inv = new ItemStack[18];

	public static final int ANIMALIGNITION = 280; //Fat ignition temperature
	public static final int MAXTEMP = 2500;

	public int theta;

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
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
		if (tickcount >= 40) {
			this.updateTemperature(world, x, y, z, meta);
			tickcount = 0;
			int fueltemp = this.getMaxFuelTemperature();
			if (temperature < fueltemp)
				this.burnFuel(fueltemp);
		}
		int spread = this.getRange();
		int yspread = this.getRange()/2;
		for (int i = 0; i < 1; i++) {
			int fx = x-spread+par5Random.nextInt(spread*2+1);
			int fz = z-spread+par5Random.nextInt(spread*2+1);
			int fy = y-yspread+par5Random.nextInt(yspread+1);
			this.fire(world, x, y, z, fx, fy, fz);
		}
		if (temperature < ANIMALIGNITION)
			return;
		List in = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(spread, yspread, spread));
		for (int i = 0; i < in.size(); i++) {
			EntityLiving ent = (EntityLiving)in.get(i);
			ent.setFire(1);
		}
	}

	private void fire (World world, int x, int y, int z, int fx, int fy, int fz) {
		double dd = ReikaMathLibrary.py3d(x-fx, y-fy, z-fz);
		int d = this.getRange();
		//ReikaWorldHelper.spawnParticleLine(world, x+0.5, y+0.5, z+0.5, fx+0.5, fy+0.5, fz+0.5, "flame", 0, 0, 0, 20);
		for (int i = 0; i < this.getRange()*this.getRange()/2; i++) {
			int dx = x-d+par5Random.nextInt(d*2+1);
			int dz = z-d+par5Random.nextInt(d*2+1);
			int dy = y-d/2+par5Random.nextInt(d/2+1);
			world.spawnParticle("flame", dx, dy+1, dz, 0, 0, 0);
			world.spawnParticle("smoke", dx, dy+1, dz, 0, 0, 0);
		}
		ReikaWorldHelper.temperatureEnvironment(world, fx, fy, fz, temperature);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		if (inv[par1] != null)
		{
			if (inv[par1].stackSize <= par2)
			{
				ItemStack itemstack = inv[par1];
				inv[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inv[par1].splitStack(par2);

			if (inv[par1].stackSize <= 0)
			{
				inv[par1] = null;
			}

			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inv[par1] != null)
		{
			ItemStack itemstack = inv[par1];
			inv[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void updateTemperature(World world, int x, int y, int z, int meta) {
		int Tamb = ReikaWorldHelper.getBiomeTemp(world, x, z);
		if (temperature > Tamb) {
			int Tdiff = temperature-Tamb;
			temperature -= (int)Math.log(Tdiff);
		}
		if (temperature < Tamb) {
			int Tdiff = Tamb-temperature;
			temperature += Tdiff/24;
		}
		if (temperature > MAXTEMP)
			temperature = MAXTEMP;
	}

	private void burnFuel(int temp) {
		int slot = -1;
		int slot2 = -1;
		switch(temp) {
		case 300:
			slot = ReikaInventoryHelper.locateInInventory(Block.planks.blockID, inv);
			break;
		case 400:
			slot = ReikaInventoryHelper.locateInInventory(Block.wood.blockID, inv);
			break;
		case 600:
			slot = ReikaInventoryHelper.locateInInventory(Item.coal.itemID, inv);
			break;
		case 800:
			slot = ReikaInventoryHelper.locateInInventory(Item.blazePowder.itemID, inv);
			break;
		case 1200:
			slot = ReikaInventoryHelper.locateInInventory(Item.bucketLava.itemID, inv);
			break;
		case 2500:
			slot = ReikaInventoryHelper.locateInInventory(Item.ingotIron.itemID, inv);
			slot2 = ReikaInventoryHelper.locateInInventory(ItemStacks.aluminumpowder.itemID, inv);
			break;
		}
		ReikaInventoryHelper.decrStack(slot, inv);
		if (slot2 > -1)
			ReikaInventoryHelper.decrStack(slot2, inv);
		if (temperature < temp)
			temperature += (temp-temperature)/4;
		if (temp == 1200) {
			int leftover = ReikaInventoryHelper.addToInventoryWithLeftover(Item.bucketEmpty.itemID, -1, 1, inv);
			if (leftover > 0) {
				EntityItem ei = new EntityItem(worldObj, xCoord+par5Random.nextFloat(), yCoord+par5Random.nextFloat(), zCoord+par5Random.nextFloat(), new ItemStack(Item.bucketLava.itemID, leftover, 0));
				ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
				if (!worldObj.isRemote)
					worldObj.spawnEntityInWorld(ei);
			}
		}
	}

	private int getMaxFuelTemperature() {
		if (!this.hasValidItems())
			return Integer.MIN_VALUE;
		if (ReikaInventoryHelper.checkForItem(Item.ingotIron.itemID, inv) && ReikaInventoryHelper.checkForItemStack(ItemStacks.aluminumpowder.itemID, ItemStacks.aluminumpowder.getItemDamage(), inv))
			return 2500;
		if (ReikaInventoryHelper.checkForItem(Item.bucketLava.itemID, inv))
			return 1200;
		if (ReikaInventoryHelper.checkForItem(Item.blazePowder.itemID, inv))
			return 800;
		if (ReikaInventoryHelper.checkForItem(Item.coal.itemID, inv))
			return 600; //really 580
		if (ReikaInventoryHelper.checkForItem(Block.wood.blockID, inv))
			return 400;
		if (ReikaInventoryHelper.checkForItem(Block.planks.blockID, inv))
			return 300;
		return 0;
	}

	private boolean hasValidItems() {
		if (ReikaInventoryHelper.checkForItem(Item.ingotIron.itemID, inv) && ReikaInventoryHelper.checkForItemStack(ItemStacks.aluminumpowder.itemID, ItemStacks.aluminumpowder.getItemDamage(), inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Item.bucketLava.itemID, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Item.blazePowder.itemID, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Item.coal.itemID, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Block.planks.blockID, inv))
			return true;
		if (ReikaInventoryHelper.checkForItem(Block.wood.blockID, inv))
			return true;
		return false;
	}

	private boolean isValidFuel(ItemStack is) {
		if (is.itemID == Item.ingotIron.itemID)
			return true;
		if (is.itemID == ItemStacks.aluminumpowder.itemID && is.getItemDamage() == ItemStacks.aluminumpowder.getItemDamage())
			return true;
		if (is.itemID == Item.bucketLava.itemID)
			return true;
		if (is.itemID == Item.blazePowder.itemID)
			return true;
		if (is.itemID == Item.coal.itemID)
			return true;
		if (is.itemID == Block.planks.blockID)
			return true;
		if (is.itemID == Block.wood.blockID)
			return true;
		return false;
	}

	@Override
	public int getRange() {
		return temperature/50;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT, inv);
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
		temperature = NBT.getInteger("temperature");
	}

	/**
	 * Writes a tile entity to NBT.  Maybe was not saving inv since seems to be acting like
	 * extends TileEntityPowerReceiver, NOT InventoriedPowerReceiver
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT, inv);
		NBT.setInteger("temperature", temperature);
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

	@Override
	public int getMachineIndex() {
		return MachineRegistry.IGNITER.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		return this.isValidFuel(is);
	}

	@Override
	public int getMaxRange() {
		return this.getRange();
	}

	@Override
	public int getThermalDamage() {
		return (temperature)/50;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasValidItems())
			return 15;
		return 0;
	}
}
