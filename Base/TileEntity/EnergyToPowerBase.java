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
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;

public abstract class EnergyToPowerBase extends TileEntityIOMachine implements SimpleProvider, PowerGenerator, GuiController {

	private static final int MINBASE = -1;

	protected int storedEnergy;

	protected int baseomega = -1;

	private ForgeDirection facingDir;

	private int tier;

	private RedstoneState rsState;

	private static final boolean reika = DragonAPICore.isReikasComputer();

	private RedstoneState getRedstoneState() {
		return rsState != null ? rsState : RedstoneState.IGNORE;
	}

	public final int getTier() {
		return tier;
	}

	public final void upgrade() {
		tier++;
	}

	public final int getTierFromPowerOutput(long power) {
		for (int i = 0; i < this.tierCount(); i++) {
			long tier = this.getTierPower(i);
			if (tier >= power)
				return i;
		}
		return 0;
	}

	public final long getTierPower(int tier) {
		return this.getTierTorque(tier)*ReikaMathLibrary.intpow2(2, this.getMaxSpeedBase(tier));
	}

	public abstract int tierCount();

	public abstract int getTierTorque(int tier);

	public abstract int getMaxSpeedBase(int tier);

	public abstract ItemStack getUpgradeItemFromTier(int tier);

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
		return this.isEmitting() ? (long)this.getSpeed()*(long)this.getTorque() : 0;
	}

	public final int getSpeed() {
		if (!this.isEmitting())
			return 0;
		if (baseomega < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, baseomega);
	}

	public final int getTorque() {
		return baseomega >= 0 ? this.getTierTorque(this.getTier()) : 0;
	}

	public final boolean hasEnoughEnergy() {
		float energy = this.getStoredPower();
		return energy > this.getConsumedUnitsPerTick();
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
		readx = x;
		ready = y;
		readz = z;
		writex = x;
		writey = y;
		writez = z;
		switch(meta) {
		case 0:
			readz = z-1;
			writez = z+1;
			facingDir = ForgeDirection.NORTH;
			break;
		case 1:
			readx = x-1;
			writex = x+1;
			facingDir = ForgeDirection.WEST;
			break;
		case 2:
			readz = z+1;
			writez = z-1;
			facingDir = ForgeDirection.SOUTH;
			break;
		case 3:
			readx = x+1;
			writex = x-1;
			facingDir = ForgeDirection.EAST;
			break;
		}
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("storage", storedEnergy);

		NBT.setInteger("tiero", baseomega);

		NBT.setInteger("rs", this.getRedstoneState().ordinal());

		if (reika && power > 0) {
			tier = Math.max(tier, this.getTierFromPowerOutput(power));
		}
		NBT.setInteger("level", tier);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		storedEnergy = NBT.getInteger("storage");
		baseomega = NBT.getInteger("tiero");

		rsState = RedstoneState.list[NBT.getInteger("rs")];

		tier = NBT.getInteger("level");

		if (reika && power > 0) {
			tier =  Math.max(tier, this.getTierFromPowerOutput(power));
		}
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
	public final int getEmittingX() {
		return writex;
	}

	@Override
	public final int getEmittingY() {
		return writey;
	}

	@Override
	public final int getEmittingZ() {
		return writez;
	}

}
