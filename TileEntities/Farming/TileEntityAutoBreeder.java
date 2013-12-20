/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAutoBreeder extends InventoriedPowerReceiver implements RangedEffect {

	public ItemStack[] inventory = new ItemStack[18];

	public static final int FALLOFF = 2048; //2kW per extra meter

	public boolean idle = false;

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public int getRange() {
		int range = 8+(int)((power-MINPOWER)/FALLOFF);
		if (range > this.getMaxRange())
			return this.getMaxRange();
		return range;
	}

	public int getMaxRange() {
		return ConfigRegistry.BREEDERRANGE.getValue();
	}

	public void testIdle() {
		boolean wheat = false;
		boolean carrot = false;
		boolean meat = false;
		boolean fish = false;
		boolean seed = false;
		wheat = ReikaInventoryHelper.checkForItem(Item.wheat.itemID, inventory);
		carrot = ReikaInventoryHelper.checkForItem(Item.carrot.itemID, inventory);
		meat = ReikaInventoryHelper.checkForItem(Item.porkRaw.itemID, inventory);
		fish = ReikaInventoryHelper.checkForItem(Item.fishRaw.itemID, inventory);
		seed = ReikaInventoryHelper.checkForItem(Item.seeds.itemID, inventory);
		idle = (!wheat && !carrot && !meat && !fish && !seed);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		//ReikaChunkHelper.emptyChunk(world, x, z);
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		this.testIdle();
		List inrange = this.getEntities(world, x, y, z, EntityAnimal.class);
		this.breed(world, x, y, z, inrange);
	}

	private boolean canBreed(EntityAnimal ent) {
		ItemStack item = null;
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				item = new ItemStack(inventory[i].itemID, 1, inventory[i].getItemDamage());
				if (ent.isBreedingItem(item)) {
					return true;
				}
			}
		}
		return false;
	}

	private int getFeedItem(EntityAnimal ent) {
		ItemStack item = ReikaEntityHelper.getBreedItem(ent);
		int slot = ReikaInventoryHelper.locateInInventory(item, inventory, false);
		return slot;
	}

	private void useFeedItem(EntityAnimal ent) {
		int slot = this.getFeedItem(ent);
		if (slot == -1)
			return;
		if (inventory[slot] == null)
			return;
		ReikaInventoryHelper.decrStack(slot, inventory);
	}

	private void breed(World world, int x, int y, int z, List inroom) {
		boolean pathing = false;
		if (tickcount >= 20) {
			tickcount = 0;
			pathing = true;
		}
		for (int i = 0; i < inroom.size(); i++) {
			EntityAnimal ent = (EntityAnimal)inroom.get(i);
			//ReikaJavaLibrary.pConsole(this.canBreed(ent)+" for "+ent.getEntityName());
			if (this.canBreed(ent)) {
				if (!(ent instanceof EntityTameable) || (ent instanceof EntityTameable && !((EntityTameable)ent).isSitting())) {
					if (!ent.isInLove() && !ent.isChild() && pathing && ent.getGrowingAge() == 0) {
						ent.getNavigator().clearPathEntity();
						PathEntity path = ent.getNavigator().getPathToXYZ(x, y, z);
						ent.getNavigator().setPath(path, 1F);
					}
					else if (pathing) {
						ent.getNavigator().clearPathEntity();
					}
				}
				if (!ent.isChild() && ent.getGrowingAge() <= 0 && ReikaMathLibrary.py3d(x-ent.posX, y-ent.posY, z-ent.posZ) <= 2.4) {
					if (!(ent instanceof EntityTameable) || (ent instanceof EntityTameable && ((EntityTameable)ent).isTamed())) {
						if (!ent.isInLove())
							this.useFeedItem(ent);
						ent.inLove = 600;
						for (int var3 = 0; var3 < 1; ++var3)
						{
							double var4 = rand.nextGaussian() * 0.02D;
							double var6 = rand.nextGaussian() * 0.02D;
							double var8 = rand.nextGaussian() * 0.02D;
							ent.worldObj.spawnParticle("heart", ent.posX + rand.nextFloat() * ent.width * 2.0F - ent.width, ent.posY + 0.5D + rand.nextFloat() * ent.height, ent.posZ + rand.nextFloat() * ent.width * 2.0F - ent.width, var4, var6, var8);
						}
					}
				}
			}
		}
	}

	private List getEntities(World world, int x, int y, int z, Class entity) {
		AxisAlignedBB room = this.getBox(x, y, z, this.getRange());
		List inroom = world.getEntitiesWithinAABB(entity, room);
		return inroom;
	}

	private AxisAlignedBB getBox(int x, int y, int z, int range) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		return box;
	}

	private AxisAlignedBB getRoom(World world, int x, int y, int z) {
		int minx = x;
		int maxx = x;
		int miny = y;
		int maxy = y;
		int minz = z;
		int maxz = z;

		boolean exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x+i+1, y, z)])
				exit = true;
			else
				maxx = x+i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x-i, y, z)])
				exit = true;
			else
				minx = x-i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y+i+1, z)])
				exit = true;
			else
				maxy = y+i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y-i, z)])
				exit = true;
			else
				miny = x-i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y, z+i+1)])
				exit = true;
			else
				maxz = z+i;
		}
		exit = false;
		for (int i = 1; i < 15 && !exit; i++) {
			if (Block.opaqueCubeLookup[world.getBlockId(x, y, z-i)])
				exit = true;
			else
				minz = z-i;
		}
		exit = false;
		return AxisAlignedBB.getBoundingBox(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return inventory[var1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");
			if (byte0 >= 0 && byte0 < inventory.length)
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
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
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.AUTOBREEDER.ordinal();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return (is.itemID == Item.wheat.itemID || is.itemID == Item.carrot.itemID || is.itemID == Item.fishRaw.itemID || is.itemID == Item.seeds.itemID || is.itemID == Item.porkRaw.itemID);
	}

	@Override
	public int getRedstoneOverride() {
		if (idle)
			return 15;
		return 0;
	}

}
