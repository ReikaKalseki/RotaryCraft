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

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityWeatherController extends TileEntityInventoriedPowerReceiver {

	/** 0 = no control; 1 = set sun; 2 = set rain; 3 = set thunder, 4 = superstorm */
	public int rainmode = 0;

	public int cooldown = 0;

	public ItemStack[] inventory = new ItemStack[18];

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		cooldown--;
		if (power < MINPOWER)
			return;
		if (!world.canBlockSeeTheSky(x, y+1, z))
			return;
		if (cooldown > 0)
			return;
		this.getRainMode();
		//ReikaJavaLibrary.pConsole(this.rainmode);
		if (rainmode == 0)
			return;
		boolean isRain = world.isRaining();
		boolean isThunder = world.isThundering();
		WorldInfo wi = world.getWorldInfo();
		if (rainmode == 1 && !isRain && !isThunder)
			return;
		if (rainmode == 2 && isRain && !isThunder)
			return;
		if (rainmode == 3 && isThunder)
			return;
		if (rainmode == 4) {
			if (rand.nextInt(30) == 0) {
				int xl = x-64+rand.nextInt(129);
				int zl = z-64+rand.nextInt(129);
				int yl = world.getTopSolidOrLiquidBlock(xl, zl);
				world.addWeatherEffect(new EntityLightningBolt(world, xl, yl, zl));
				if (isThunder)
					return;
			}
		}
		switch(rainmode) {
		case 1:
			wi.setRaining(false);
			wi.setThundering(false);
			break;
		case 2:
			wi.setRaining(true);
			wi.setThundering(false);
			break;
		case 3:
			wi.setRaining(true);
			wi.setThundering(true);
			break;
		case 4:
			wi.setRaining(true);
			wi.setThundering(true);
			break;
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	private void fire(ItemStack is, ItemStack is2) {
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.explode", 1F, 1F);
		if (is != null) {
			EntityItem ei = new EntityItem(worldObj, xCoord+0.5, yCoord+1.0625, zCoord+0.5, new ItemStack(is.itemID, 1, is.getItemDamage()));
			ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
			ei.delayBeforeCanPickup = 5000;
			ei.age = 5900;
			ei.motionY = 3;
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(ei);
		}
		if (is2 != null) {
			EntityItem ei = new EntityItem(worldObj, xCoord+0.5, yCoord+1.0625, zCoord+0.5, new ItemStack(is2.itemID, 1, is2.getItemDamage()));
			ReikaEntityHelper.addRandomDirVelocity(ei, 0.2);
			ei.delayBeforeCanPickup = 5000;
			ei.age = 5900;
			ei.motionY = 3;
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(ei);
		}
	}

	public void getRainMode() {
		boolean isRain = worldObj.isRaining();
		boolean isThunder = worldObj.isThundering();
		int oldrain = rainmode;
		ItemStack is = null;
		ItemStack is2 = null;
		boolean sawdust = ReikaInventoryHelper.checkForItemStack(ItemStacks.sawdust.itemID, ItemStacks.sawdust.getItemDamage(), inventory);
		boolean silverio = ReikaInventoryHelper.checkForItemStack(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inventory);
		boolean redstone = ReikaInventoryHelper.checkForItem(Item.redstone.itemID, inventory);
		boolean glowdust = ReikaInventoryHelper.checkForItem(Item.glowstone.itemID, inventory);
		if (sawdust && (isRain || isThunder)) {
			rainmode = 1;
			is = ItemStacks.sawdust;
		}
		else if (silverio && !isRain) {
			rainmode = 2;
			is = ItemStacks.silveriodide;
			if (redstone && !isThunder) {
				rainmode = 3;
				is2 = new ItemStack(Item.redstone.itemID, 1, 0);
			}
			else if (glowdust) {
				rainmode = 4;
				is2 = new ItemStack(Item.glowstone.itemID, 1, 0);
			}
		}
		else
			return;
		if (oldrain == rainmode)
			return;
		cooldown = 200+rand.nextInt(400);
		this.fire(is, is2);
		int slot = -1;
		switch(rainmode) {
		case 0:
			return;
		case 1:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.sawdust.itemID, ItemStacks.sawdust.getItemDamage(), inventory);
			ReikaInventoryHelper.decrStack(slot, inventory);
			return;
		case 2:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inventory);
			ReikaInventoryHelper.decrStack(slot, inventory);
			return;
		case 3:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inventory);
			ReikaInventoryHelper.decrStack(slot, inventory);
			slot = ReikaInventoryHelper.locateInInventory(Item.redstone.itemID, inventory);
			ReikaInventoryHelper.decrStack(slot, inventory);
			return;
		case 4:
			slot = ReikaInventoryHelper.locateInInventory(ItemStacks.silveriodide.itemID, ItemStacks.silveriodide.getItemDamage(), inventory);
			ReikaInventoryHelper.decrStack(slot, inventory);
			slot = ReikaInventoryHelper.locateInInventory(Item.glowstone.itemID, inventory);
			ReikaInventoryHelper.decrStack(slot, inventory);
			return;
		}
	}

	private boolean isValidWeatherItem(ItemStack is) {
		if (is.itemID == ItemStacks.sawdust.itemID && is.getItemDamage() == ItemStacks.sawdust.getItemDamage())
			return true;
		if (is.itemID == ItemStacks.silveriodide.itemID && is.getItemDamage() == ItemStacks.silveriodide.getItemDamage())
			return true;
		if (is.itemID == Item.redstone.itemID)
			return true;
		if (is.itemID == Item.glowstone.itemID)
			return true;
		return false;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
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
	}

	/**
	 * Writes a tile entity to NBT.
	 */
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
		return MachineRegistry.WEATHERCONTROLLER.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isValidWeatherItem(is);
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
