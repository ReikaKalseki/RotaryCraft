/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Auxiliary.Interfaces.InertIInv;
import Reika.RotaryCraft.Base.TileEntity.InventoriedRCTileEntity;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityPowerBus extends InventoriedRCTileEntity implements InertIInv {

	private ItemStack[] inv = new ItemStack[4];
	private HashMap<ForgeDirection, Boolean> modes = new HashMap();

	private HybridTank lubricant = new HybridTank("buslube", 8000);

	private ForgeDirection inputSide;

	private ShaftPowerBus bus;

	public TileEntityPowerBus() {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			modes.put(dir, false);
		}
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.POWERBUS.ordinal();
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

	}

	private int getAbsRatio(ForgeDirection dir) {
		return this.getRatioFromItem(inv[dir.ordinal()-2]);
	}

	private int getRatioFromItem(ItemStack is) {
		if (is == null)
			return 0;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.gearunit))
			return 2;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.gearunit4))
			return 4;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.gearunit8))
			return 8;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.gearunit16))
			return 16;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.shaftitem))
			return 1;
		return 0;
	}

	public boolean isSideSpeedMode(ForgeDirection dir) {
		return modes.get(dir);
	}

	public void setMode(ForgeDirection dir, boolean speed) {
		modes.put(dir, speed);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public boolean canOutputToSide(ForgeDirection dir) {
		return !this.isReceivingFromSide(dir) && this.hasValidItem(dir);
	}

	public boolean isReceivingFromSide(ForgeDirection dir) {
		return dir == this.getInputSide();
	}

	private boolean hasValidItem(ForgeDirection dir) {
		return this.getAbsRatio(dir) != 0;
	}

	public long getPowerPerSide() {
		return this.getInputPower()/bus.getTotalOutputSides();
	}

	public long getInputPower() {
		return bus != null ? bus.getInputPower() : 0;
	}

	public int getInputTorque() {
		return bus != null ? bus.getInputTorque() : 0;
	}

	public int getTorquePerSide() {
		return bus != null ? this.getInputTorque()/bus.getTotalOutputSides() : 0;
	}

	public int getTorqueToSide(ForgeDirection dir) {
		int tbase = this.getTorquePerSide();
		int ratio = this.getAbsRatio(dir);
		if (ratio == 0)
			return 0;
		else
			return this.isSideSpeedMode(dir) ? tbase/ratio : tbase*ratio;
	}

	public int getSpeedToSide(ForgeDirection dir) {
		if (bus == null)
			return 0;
		int sbase = bus.getSpeed();
		int ratio = this.getAbsRatio(dir);
		if (ratio == 0)
			return 0;
		else
			return this.isSideSpeedMode(dir) ? sbase*ratio : sbase/ratio;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int a, ItemStack b) {
		return false;
	}

	public ForgeDirection getInputSide() {
		return inputSide != null ? inputSide : ForgeDirection.EAST;
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		lubricant.readFromNBT(NBT);

		inputSide = dirs[NBT.getInteger("in")];

		for (int i = 2; i < 6; i++) {
			modes.put(dirs[i], NBT.getBoolean("spd"+i));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		lubricant.writeToNBT(NBT);

		NBT.setInteger("in", this.getInputSide().ordinal());

		for (int i = 2; i < 6; i++) {
			NBT.setBoolean("spd"+i, modes.get(dirs[i]));
		}
	}

	public void findNetwork(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.BUSCONTROLLER) {
				TileEntityBusController te = (TileEntityBusController)world.getBlockTileEntity(dx, dy, dz);
				ShaftPowerBus bus = te.getBus();
				if (bus != null) {
					bus.addBlock(this);
					this.bus = bus;
					inputSide = dir;
					return;
				}
			}
			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus te = (TileEntityPowerBus)world.getBlockTileEntity(dx, dy, dz);
				ShaftPowerBus bus = te.getBus();
				if (bus != null) {
					bus.addBlock(this);
					this.bus = bus;
					inputSide = dir;
					return;
				}
			}
		}
	}

	public void removeFromBus() {
		if (bus != null)
			bus.removeBlock(this);
	}

	public ShaftPowerBus getBus() {
		return bus;
	}

	public void clearBus() {
		bus = null;
	}
}
