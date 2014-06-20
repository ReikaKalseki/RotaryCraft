/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.HybridTank;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.UpgradeableMachine;
import Reika.RotaryCraft.Registry.ItemRegistry;

public abstract class EnergyToPowerBase extends TileEntityIOMachine implements SimpleProvider, PowerGenerator, GuiController, UpgradeableMachine {

	private static final int MINBASE = -1;

	public static final int TIERS = 6;

	protected int storedEnergy;

	protected int baseomega = -1;

	private ForgeDirection facingDir;

	private static final boolean reika = DragonAPICore.isReikasComputer();
	private final HybridTank tank = new HybridTank("energytopower", 24000);

	private int tier;

	private RedstoneState rsState;

	private RedstoneState getRedstoneState() {
		return rsState != null ? rsState : RedstoneState.IGNORE;
	}

	@Override
	public void updateTileEntity() {
		super.updateTileEntity();
		if (DragonAPICore.debugtest) {
			storedEnergy = this.getMaxStorage();
		}
		if (storedEnergy < 0) {
			storedEnergy = 0;
		}
	}

	public final int getTier() {
		return tier;
	}

	@Override
	public final void upgrade() {
		tier++;
		this.syncAllData();
	}

	public final boolean canUpgradeWith(ItemStack item) {
		if (item.getItemDamage() == 2) {
			if (item.stackTagCompound == null)
				return false;
			if (item.stackTagCompound.getInteger("magnet") < 720)
				return false;
		}
		return item.itemID == ItemRegistry.UPGRADE.getShiftedID() && item.getItemDamage() == tier+1;
	}

	public final int getTierFromPowerOutput(long power) {
		for (int i = 0; i < TIERS; i++) {
			long tier = this.getTierPower(i);
			if (tier >= power)
				return i;
		}
		return 0;
	}

	public final long getTierPower(int tier) {
		return this.getGenTorque(tier)*ReikaMathLibrary.intpow2(2, this.getMaxSpeedBase(tier));
	}

	public final int getGenTorque(int tier) {
		return 8*ReikaMathLibrary.intpow2(4, tier);
	}

	public final int getMaxSpeedBase(int tier) {
		return 8+tier;
	}

	public abstract boolean isValidSupplier(TileEntity te);

	private static enum RedstoneState {
		IGNORE(Item.gunpowder),
		LOW(Block.torchRedstoneIdle),
		HI(Block.torchRedstoneActive);

		private final ItemStack iconItem;

		public static final RedstoneState[] list = values();

		private RedstoneState(Item i) {
			this(new ItemStack(i));
		}

		private RedstoneState(Block i) {
			this(new ItemStack(i));
		}

		private RedstoneState(ItemStack is) {
			iconItem = is.copy();
		}

		public ItemStack getDisplayIcon() {
			return iconItem.copy();
		}

		public RedstoneState next() {
			return this.ordinal() < list.length-1 ? list[this.ordinal()+1] : list[0];
		}
	}

	public boolean isRedstoneControlEnabled() {
		return this.getRedstoneState() != RedstoneState.IGNORE;
	}

	public ItemStack getRedstoneStateIcon() {
		return this.getRedstoneState().getDisplayIcon();
	}

	public boolean isEmitting() {
		if (this.isRedstoneControlEnabled()) {
			boolean red = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			RedstoneState rs = this.getRedstoneState();
			return red ? rs == RedstoneState.HI : rs == RedstoneState.LOW;
		}
		else
			return true;
	}

	public void incrementRedstoneState() {
		rsState = this.getRedstoneState().next();
	}

	public final int getStoredPower() {
		return storedEnergy;
	}

	public final void setStoredPower(int e) {
		storedEnergy = e;
	}

	public abstract int getMaxStorage();

	public final long getPowerLevel() {
		return this.isEmitting() ? this.getSpeed()*(long)this.getTorque() : 0;
	}

	public final int getSpeed() {
		if (!this.isEmitting())
			return 0;
		if (baseomega < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, baseomega);
	}

	public final int getTorque() {
		return omega > 0 ? this.getGenTorque(this.getTier()) : 0;
	}

	public final boolean hasEnoughEnergy() {
		float energy = this.getStoredPower();
		return energy > this.getConsumedUnitsPerTick() && (reika || !tank.isEmpty());
	}

	public abstract int getConsumedUnitsPerTick();

	public final void setTierFromItemTag(NBTTagCompound nbt) {
		if (nbt != null) {
			tier = nbt.getInteger("tier");
		}
	}

	public final void setItemTagFromTier(ItemStack is) {
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("tier", tier);
	}

	public final void incrementOmega() {
		if (baseomega < this.getMaxSpeedBase(this.getTier()))
			baseomega++;
	}

	public final void decrementOmega() {
		if (baseomega > MINBASE)
			baseomega--;
	}

	public final int getEnergyScaled(int h) {
		return (int)(this.getPercentStorage()*h);
	}

	public final float getPercentStorage() {
		return this.getStoredPower()/(float)this.getMaxStorage();
	}

	protected final void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
		case 0:
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			facingDir = ForgeDirection.EAST;
			break;
		}
		read = facingDir;
		write = read.getOpposite();
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("storage", storedEnergy);

		NBT.setInteger("tiero", baseomega);

		NBT.setInteger("rs", this.getRedstoneState().ordinal());

		if (baseomega > this.getMaxSpeedBase(tier)) {
			baseomega = MINBASE;
		}
		NBT.setInteger("level", tier);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		storedEnergy = NBT.getInteger("storage");

		rsState = RedstoneState.list[NBT.getInteger("rs")];

		tier = NBT.getInteger("level");

		if (baseomega > this.getMaxSpeedBase(tier)) {
			baseomega = MINBASE;
		}

		baseomega = NBT.getInteger("tiero");
	}

	@Override
	public final long getCurrentPower() {
		return power;
	}

	@Override
	public final PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
	}

	@Override
	public final boolean canProvidePower() {
		return true;
	}

	public abstract String getUnitDisplay();

	public abstract Color getPowerColor();

	public final ForgeDirection getFacing() {
		return facingDir != null ? facingDir : ForgeDirection.EAST;
	}

	public final TileEntity getProvidingTileEntity() {
		int x = xCoord+this.getFacing().offsetX;
		int y = yCoord+this.getFacing().offsetY;
		int z = zCoord+this.getFacing().offsetZ;
		TileEntity te = worldObj.getBlockTileEntity(x, y, z);
		return te;
	}

	@Override
	public int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

}
