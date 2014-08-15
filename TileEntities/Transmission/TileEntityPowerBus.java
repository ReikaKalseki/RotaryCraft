/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import Reika.DragonAPI.Interfaces.InertIInv;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoryIOMachine;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPowerBus extends TileEntityInventoryIOMachine implements InertIInv {

	private HashMap<ForgeDirection, Boolean> modes = new HashMap();

	private ForgeDirection inputSide;

	private ShaftPowerBus bus;

	private int hubX = Integer.MIN_VALUE;
	private int hubY = Integer.MIN_VALUE;
	private int hubZ = Integer.MIN_VALUE;

	public TileEntityPowerBus() {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			modes.put(dir, false);
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.POWERBUS;
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
		if (bus == null && this.hasHubCoordinates()) {
			MachineRegistry m = MachineRegistry.getMachine(world, hubX, hubY, hubZ);
			if (m == MachineRegistry.BUSCONTROLLER) {
				TileEntityBusController hub = (TileEntityBusController)world.getTileEntity(hubX, hubY, hubZ);
				if (hub != null) {
					ShaftPowerBus bus = hub.getBus();
					this.configureBusData(bus);
				}
			}
		}
	}

	public boolean canHaveItemInSlot(ForgeDirection dir) {
		MachineRegistry m = MachineRegistry.getMachine(worldObj, xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
		return m != MachineRegistry.BUSCONTROLLER && m != MachineRegistry.POWERBUS;
	}

	private boolean hasHubCoordinates() {
		return hubX != Integer.MIN_VALUE && hubY != Integer.MIN_VALUE && hubZ != Integer.MIN_VALUE;
	}

	public int getAbsRatio(ForgeDirection dir) {
		if (dir.offsetY != 0)
			return 0;
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
		if (ItemRegistry.GEARUNITS.matchItem(is))
			return ReikaMathLibrary.intpow2(2, 1+is.getItemDamage()%4);
		if (is.getItem() == Items.stick)
			return 1;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.stonerod))
			return 1;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.shaftitem))
			return 1;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.diamondshaft))
			return 1;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.bedrockshaft))
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
		if (dir == ForgeDirection.UNKNOWN)
			return 0;
		int tbase = this.getTorquePerSide();
		int ratio = this.getAbsRatio(dir);
		if (ratio == 0)
			return 0;
		else {
			int trq = this.isSideSpeedMode(dir) ? tbase/ratio : tbase*ratio;
			if (this.verifyTorque(trq, dir))
				return trq;
			else {
				this.breakItem(dir);
				return 0;
			}
		}
	}

	public int getSpeedToSide(ForgeDirection dir) {
		if (bus == null)
			return 0;
		if (dir == ForgeDirection.UNKNOWN)
			return 0;
		int sbase = bus.getSpeed();
		int ratio = this.getAbsRatio(dir);
		if (ratio == 0)
			return 0;
		else {
			int spd = this.isSideSpeedMode(dir) ? sbase*ratio : sbase/ratio;
			if (this.verifySpeed(spd, dir))
				return spd;
			else {
				this.breakItem(dir);
				return 0;
			}
		}
	}

	private boolean verifySpeed(int speed, ForgeDirection dir) {
		ItemStack is = inv[dir.ordinal()-2];
		MaterialRegistry mat = MaterialRegistry.getMaterialFromItem(is);
		return mat.getMaxShaftSpeed() >= speed;
	}

	private boolean verifyTorque(int torque, ForgeDirection dir) {
		ItemStack is = inv[dir.ordinal()-2];
		MaterialRegistry mat = MaterialRegistry.getMaterialFromItem(is);
		return mat.getMaxShaftTorque() >= torque;
	}

	private void breakItem(ForgeDirection dir) {
		inv[dir.ordinal()-2] = null;
		for (int i = 0; i < 3; i++)
			ReikaSoundHelper.playSoundAtBlock(worldObj, xCoord, yCoord, zCoord, "random.break", 2, 1);
	}

	@Override
	public int getSizeInventory() {
		return 4;
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
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		inputSide = dirs[NBT.getInteger("in")];

		for (int i = 2; i < 6; i++) {
			modes.put(dirs[i], NBT.getBoolean("spd"+i));
		}

		hubX = NBT.getInteger("hx");
		hubY = NBT.getInteger("hy");
		hubZ = NBT.getInteger("hz");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("in", this.getInputSide().ordinal());

		for (int i = 2; i < 6; i++) {
			NBT.setBoolean("spd"+i, modes.get(dirs[i]));
		}

		NBT.setInteger("hx", hubX);
		NBT.setInteger("hy", hubY);
		NBT.setInteger("hz", hubZ);
	}

	public void findNetwork(World world, int x, int y, int z) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(world, dx, dy, dz);
			if (m == MachineRegistry.BUSCONTROLLER) {
				TileEntityBusController te = (TileEntityBusController)world.getTileEntity(dx, dy, dz);
				ShaftPowerBus bus = te.getBus();
				if (bus != null) {
					this.configureBusData(bus);
					inputSide = dir;
					return;
				}
			}
			if (m == MachineRegistry.POWERBUS) {
				TileEntityPowerBus te = (TileEntityPowerBus)world.getTileEntity(dx, dy, dz);
				ShaftPowerBus bus = te.getBus();
				if (bus != null) {
					this.configureBusData(bus);
					inputSide = dir;
					return;
				}
			}
		}
	}

	private void configureBusData(ShaftPowerBus bus) {
		bus.addBlock(this);
		this.bus = bus;
		TileEntityBusController hub = bus.getController();
		hubX = hub.xCoord;
		hubY = hub.yCoord;
		hubZ = hub.zCoord;
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
		hubX = Integer.MIN_VALUE;
		hubY = Integer.MIN_VALUE;
		hubZ = Integer.MIN_VALUE;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return bus != null && bus.getController() != null ? bus.getController().getPowerSources(io, caller) : new PowerSourceList();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}
}