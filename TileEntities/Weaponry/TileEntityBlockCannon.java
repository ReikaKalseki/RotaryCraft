/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Base.TileEntityLaunchCannon;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBlockCannon extends TileEntityLaunchCannon {

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.getItem() instanceof ItemBlock || is.itemID == Item.bucketLava.itemID || is.itemID == Item.bucketWater.itemID; //Blocks only
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BLOCKCANNON.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		if (tickcount < 10)
			return;
		tickcount = 0;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		this.fire(world, x, y, z);
	}

	private double getBlockMass(ItemStack is) {
		return ReikaPhysicsHelper.getBlockDensity(Block.blocksList[is.itemID]);
	}

	private int getReqTorque(ItemStack is) {
		double m = this.getBlockMass(is);
		return ReikaMathLibrary.ceil2exp((int)(velocity*m));
	}

	private ItemStack getNextToFire() {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (ReikaItemHelper.isBlock(inventory[i])) {
					ItemStack is = inventory[i].copy();
					ReikaInventoryHelper.decrStack(i, inventory);
					return ReikaItemHelper.getSizedItemStack(is, 1);
				}
				else if (inventory[i].itemID == Item.bucketWater.itemID) {
					ReikaInventoryHelper.decrStack(i, inventory);
					return new ItemStack(Block.waterMoving);
				}
				else if (inventory[i].itemID == Item.bucketLava.itemID) {
					ReikaInventoryHelper.decrStack(i, inventory);
					return new ItemStack(Block.lavaMoving);
				}
			}
		}
		return null;
	}

	private void fireBlock(ItemStack is, World world, int x, int y, int z) {
		EntityFallingSand e = new EntityFallingSand(world, x+0.5, y+1+0.5, z+0.5, is.itemID, is.getItemDamage());
		double[] vel = ReikaPhysicsHelper.polarToCartesian(velocity, theta, phi);
		e.motionX = vel[0];
		e.motionY = vel[1];
		e.motionZ = vel[2];
		//e.shouldDropItem = false;
		e.fallTime = -10000;
		if (!world.isRemote)
			world.spawnEntityInWorld(e);
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
	protected void fire(World world, int x, int y, int z) {
		ItemStack next = this.getNextToFire();
		if (next == null)
			return;
		ReikaJavaLibrary.pConsole(this.getReqTorque(next));
		if (torque < this.getReqTorque(next)) {
			ReikaInventoryHelper.addToIInv(next, this);
			return;
		}
		this.fireBlock(next, world, x, y, z);
	}

	@Override
	public int getMaxLaunchVelocity() {
		if (power < MINPOWER)
			return 0;
		return 1000;
	}

	@Override
	public int getMaxTheta() {
		if (power < MINPOWER)
			return 0;
		return 1000;
	}

	@Override
	public double getMaxLaunchDistance() {
		if (power < MINPOWER)
			return 0;
		return 1000;
	}

}
