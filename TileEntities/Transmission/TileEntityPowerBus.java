/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Transmission;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Interfaces.TileEntity.InertIInv;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.RotaryCraft.API.Interfaces.ComplexIO;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.ShaftPowerBus;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoryIOMachine;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityPowerBus extends TileEntityInventoryIOMachine implements InertIInv, BreakAction, ComplexIO {

	private static final boolean ALLOW_VERTICAL_CHAINS = true;

	private boolean[] modes = new boolean[4];

	private ForgeDirection inputSide;

	private ShaftPowerBus bus;

	private int hubX = Integer.MIN_VALUE;
	private int hubY = Integer.MIN_VALUE;
	private int hubZ = Integer.MIN_VALUE;

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
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			if (this.canHaveItemInSlot(dir) && this.getAdjacentTileEntity(dir) != null) {
				int speed = this.getSpeedToSide(dir);
				int torque = this.getTorqueToSide(dir);
				this.writeToPowerReceiver(dir, speed, torque);
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
		return GearboxTypes.getRatioFromPartItem(inv[dir.ordinal()-2]);
	}

	public boolean insertItem(ItemStack is, ForgeDirection side) {
		if (GearboxTypes.getRatioFromPartItem(is) > 0 && inv[side.ordinal()-2] == null) {
			inv[side.ordinal()-2] = is.copy();
			return true;
		}
		return false;
	}

	public boolean isSideSpeedMode(ForgeDirection dir) {
		return modes[dir.ordinal()-2];
	}

	public void setMode(ForgeDirection dir, boolean speed) {
		modes[dir.ordinal()-2] = speed;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public boolean canOutputToSide(ForgeDirection dir) {
		return !this.isReceivingFromSide(dir) && this.hasValidItem(dir) && !this.isDisabledClutch(dir);
	}

	private boolean isDisabledClutch(ForgeDirection dir) {
		TileEntity te = this.getAdjacentTileEntity(dir);
		return te instanceof TileEntityClutch && !((TileEntityClutch)te).isOutputEnabled();
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
		GearboxTypes mat = GearboxTypes.getMaterialFromCraftingItem(is);
		return mat != null && mat.material.getLimits().maxSpeed >= speed;
	}

	private boolean verifyTorque(int torque, ForgeDirection dir) {
		ItemStack is = inv[dir.ordinal()-2];
		GearboxTypes mat = GearboxTypes.getMaterialFromCraftingItem(is);
		return mat != null && mat.material.getLimits().maxTorque >= torque;
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

		modes = ReikaArrayHelper.booleanFromByteBitflags(NBT.getByte("mode"), 4);

		hubX = NBT.getInteger("hx");
		hubY = NBT.getInteger("hy");
		hubZ = NBT.getInteger("hz");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("in", this.getInputSide().ordinal());

		NBT.setByte("mode", ReikaArrayHelper.booleanToByteBitflags(modes));

		NBT.setInteger("hx", hubX);
		NBT.setInteger("hy", hubY);
		NBT.setInteger("hz", hubZ);
	}

	public void findNetwork(World world, int x, int y, int z) {
		for (int i = ALLOW_VERTICAL_CHAINS ? 0 : 2; i < 6; i++) {
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

	private void removeFromBus() {
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
	public boolean isWritingTo(PowerSourceTracker te) {
		for (int i = 2; i < 6; i++) {
			if (this.canOutputToSide(dirs[i])) {
				//TileEntity tile = this.getAdjacentTileEntity(dirs[i]);
				if (this.matchTile(te, dirs[i]))
					return true;
			}
		}
		return false;
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		return bus != null && bus.getController() != null ? bus.getController().getPowerSources(io, caller) : new PowerSourceList();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public void breakBlock() {
		this.removeFromBus();
	}

	@Override
	public void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		if (dir == read)
			c.add(this.getAdjacentTileEntity(write));
	}
}
