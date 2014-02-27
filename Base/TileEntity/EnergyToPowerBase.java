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

	protected int basetorque = -1;
	protected int baseomega = -1;

	protected ForgeDirection facingDir;

	private RedstoneState rsState;

	private static final boolean reika = DragonAPICore.isReikasComputer();

	private RedstoneState getRedstoneState() {
		return rsState != null ? rsState : RedstoneState.IGNORE;
	}

	private int getMaxBase() {
		return this.isFlexibleMode() ? 15 : 11;
	}

	public final boolean isFlexibleMode() {
		return reika;
	}

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
		if (baseomega > this.getMaxBase() || basetorque > this.getMaxBase())
			baseomega = basetorque = -1;
		if (!this.isEmitting())
			return 0;
		if (baseomega < 0 || basetorque < 0)
			return 0;
		return ReikaMathLibrary.intpow2(2, baseomega);
	}

	public final int getTorque() {
		if (baseomega > this.getMaxBase() || basetorque > this.getMaxBase())
			baseomega = basetorque = -1;
		if (!this.isEmitting())
			return 0;
		if (baseomega < 0 || basetorque < 0)
			return 0;
		return this.isFlexibleMode() ? ReikaMathLibrary.intpow2(2, basetorque) : 2048;
	}

	public final boolean hasEnoughEnergy() {
		float energy = this.getStoredPower();
		return energy > this.getConsumedUnitsPerTick();
	}

	public abstract int getConsumedUnitsPerTick();

	public final int getTier() {
		return basetorque;
	}

	public final void incrementTorque() {
		if (basetorque < this.getMaxBase() && baseomega+basetorque < this.getMaxBase()*2-3)
			basetorque++;
	}

	public final void decrementTorque() {
		if (basetorque > MINBASE)
			basetorque--;
	}

	public final void incrementOmega() {
		if (baseomega < this.getMaxBase() && baseomega+basetorque < this.getMaxBase()*2-3)
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

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("storage", storedEnergy);

		if (!this.isFlexibleMode())
			basetorque = 0;
		NBT.setInteger("tier", basetorque);
		NBT.setInteger("tiero", baseomega);

		NBT.setInteger("rs", this.getRedstoneState().ordinal());
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		storedEnergy = NBT.getInteger("storage");
		basetorque = NBT.getInteger("tier");
		if (!this.isFlexibleMode())
			basetorque = 0;
		baseomega = NBT.getInteger("tiero");

		rsState = RedstoneState.list[NBT.getInteger("rs")];
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

}
