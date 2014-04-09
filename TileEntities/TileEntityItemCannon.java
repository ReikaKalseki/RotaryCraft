/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.DiscreteFunction;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityItemCannon extends InventoriedPowerReceiver implements DiscreteFunction, ConditionalOperation {

	public int[] target = new int[3];

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0;
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.ITEMCANNON;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getPowerBelow();
		if (power < MINPOWER)
			return;
		if (tickcount < this.getOperationTime())
			return;
		ItemStack is = this.getFirstStack();
		if (is == null)
			return;
		if (this.getTargetTE() == null) {
			//ReikaChatHelper.write("Item Cannon At "+xCoord+", "+yCoord+", "+zCoord+" has an invalid target!");
			//ReikaChatHelper.writeBlockAtCoords(world, target[0], target[1], target[2])
			//ReikaJavaLibrary.pConsole(this);
			return;
		}
		if (!ReikaInventoryHelper.hasSpaceFor(is, this.getTargetTE()))
			return;
		tickcount = 0;
		//ReikaJavaLibrary.pConsole(target[0]+"   "+target[1]+"   "+target[2]);
		this.fire(world, x, y, z);
	}

	private void fire(World world, int x, int y, int z) {
		if (world.isRemote)
			return;
		double v = 4;
		ItemStack is = this.getFirstStack();
		if (is == null)
			return;
		int slot = ReikaInventoryHelper.locateInInventory(is, inv, false);
		ReikaInventoryHelper.decrStack(slot, inv);
		EntityItem ei = new EntityItem(world, x+0.5, y+1.125, z+0.5, is);
		double dx = target[0]-x;
		double dy = target[1]-y;
		double dz = target[2]-z;
		double dd = ReikaMathLibrary.py3d(dx, dy, dz);
		ei.motionX = dx/dd*v;
		ei.motionY = dy/dd*v;
		ei.motionZ = dz/dd*v;
		ei.delayBeforeCanPickup = 10;
		ei.lifespan = 5;
		if (!world.isRemote)
			world.spawnEntityInWorld(ei);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.explode", 1, 1);
		TileEntityItemCannon ii = this.getTargetTE();
		if (!world.isRemote)
			ReikaInventoryHelper.addToIInv(is, this.getTargetTE());
	}

	private TileEntityItemCannon getTargetTE() {
		TileEntity te = worldObj.getBlockTileEntity(target[0], target[1], target[2]);
		if (!(te instanceof TileEntityItemCannon))
			return null;
		return (TileEntityItemCannon)te;
	}

	private ItemStack getFirstStack() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				ItemStack is = inv[i].copy();
				return ReikaItemHelper.getSizedItemStack(is, 1);
			}
		}
		return null;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		target = NBT.getIntArray("targetxyz");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setIntArray("targetxyz", target);
	}

	@Override
	public int getRedstoneOverride() {
		if (ReikaInventoryHelper.isEmpty(inv))
			return 15;
		return 0;
	}

	@Override
	public int getOperationTime() {
		return 8;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Items";
	}
}
