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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.SimpleProvider;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntity1DTransmitter;
import Reika.RotaryCraft.Models.ModelCVT;
import Reika.RotaryCraft.Models.ModelCoil;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAdvancedGear extends TileEntity1DTransmitter implements ISidedInventory {

	public boolean worm = true;
	public boolean coil = false;
	boolean isReleasing = false;
	public int releaseTorque = 0;
	public int releaseOmega = 0;
	/** Stored energy, in joules */
	public long energy;

	public static final int WORMRATIO = 16;

	public ItemStack[] belts = new ItemStack[31];


	//-ve ratio is torque mode for cvt
	public void readFromSplitter(TileEntitySplitter spl) { //Complex enough to deserve its own function
		int sratio = spl.getRatioFromMode();
		if (sratio == 0)
			return;
		boolean favorbent = false;
		if (sratio < 0) {
			favorbent = true;
			sratio = -sratio;
		}
		if (worm || (!coil && ratio < 0)) {
			if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
				omega = (int)(spl.omega/this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega)); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = (int)(spl.torque/2*this.getEffectiveRatio());
					return;
				}
				if (favorbent) {
					torque = (int)(spl.torque/sratio*this.getEffectiveRatio());
				}
				else {
					torque = (int)(this.getEffectiveRatio()*(int)(spl.torque*((sratio-1D)/(sratio))));
				}
			}
			else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
				omega = (int)(spl.omega/this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega)); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = (int)(spl.torque/2*this.getEffectiveRatio());
					return;
				}
				if (favorbent) {
					torque = (int)(this.getEffectiveRatio()*(int)(spl.torque*((sratio-1D)/(sratio))));
				}
				else {
					torque = (int)(spl.torque/sratio*this.getEffectiveRatio());
				}
			}
			else //We are not one of its write-to blocks
				return;
		}
		else {
			if (xCoord == spl.writeinline[0] && zCoord == spl.writeinline[1]) { //We are the inline
				omega = (int)(spl.omega*this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega)); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = (int)(spl.torque/2/this.getEffectiveRatio());
					return;
				}
				if (favorbent) {
					torque = (int)(spl.torque/sratio/this.getEffectiveRatio());
				}
				else {
					torque = (int)((spl.torque*((sratio-1D))/sratio)/(this.getEffectiveRatio()));
				}
			}
			else if (xCoord == spl.writebend[0] && zCoord == spl.writebend[1]) { //We are the bend
				omega = (int)(spl.omega*this.getEffectiveRatio()*this.getPowerLossFraction(spl.omega)); //omega always constant
				if (sratio == 1) { //Even split, favorbent irrelevant
					torque = (int)(spl.torque/2/this.getEffectiveRatio());
					return;
				}
				if (favorbent) {
					torque = (int)(spl.torque*((sratio-1D)/(sratio)));
				}
				else {
					torque = (int)(spl.torque/sratio/this.getEffectiveRatio());
				}
			}
			else //We are not one of its write-to blocks
				return;
		}
	}

	private double getEffectiveRatio() {
		if (coil)
			return 1;
		if (worm)
			return WORMRATIO;
		if (ratio < 0)
			return -ratio;
		return ratio;
	}

	private double getPowerLossFraction(int speed) {
		if (worm)
			return (128-4*ReikaMathLibrary.logbase(speed, 2))/100;
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		if (worm)
			return false;
		return super.isUseableByPlayer(var1);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta)
	{
		super.updateTileEntity();
		this.getIOSides(world, x, y, z, meta);
		this.getType();
		if (!coil)
			this.transferPower(world, x, y, z, meta);
		else
			this.store(world, x, y, z, meta);
		power = omega*torque;

		this.basicPowerReceiver();
	}

	private void store(World world, int x, int y, int z, int meta) {
		this.transferPower(world, x, y, z, meta);
		isReleasing = world.isBlockIndirectlyGettingPowered(x, y, z);
		if (!isReleasing) {
			torque = omega = 0;
			power = 0;
			if (energy + ((long)torquein*(long)omegain) < 0 || energy + ((long)torquein*(long)omegain) > Long.MAX_VALUE) {
				world.createExplosion(null, x+0.5, y+0.5, z+0.5, 6F, true);
			}
			else
				energy += ((long)torquein*(long)omegain);
		}
		else if (energy > 0 && releaseTorque > 0 && releaseOmega > 0) {
			torque = releaseTorque;
			omega = releaseOmega;
			power = (long)torque*(long)omega;
			energy -= power;
			if (energy <= 0) {
				energy = 0;
				torque = 0;
				omega = 0;
				power = 0;
			}
		}
	}

	private void getType() {
		worm = (this.getBlockMetadata() < 4);
		coil = ReikaMathLibrary.isValueInsideBoundsIncl(8, 12, this.getBlockMetadata());
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		while (metadata > 3)
			metadata -= 4;
		super.getIOSides(world, x, y, z, metadata, false);
	}

	public void getRatio() {
		int sign = 1;
		if (ratio < 0)
			sign = -1;
		if (Math.abs(ratio) > this.getMaxRatio())
			ratio = this.getMaxRatio()*sign;
		if (ratio == 0)
			ratio = 1;
	}

	public int getMaxRatio() {
		if (belts[0] == null)
			return 1;
		if (belts[0].itemID != ItemStacks.belt.itemID || belts[0].getItemDamage() != ItemStacks.belt.getItemDamage())
			return 1;
		for (int i = 1; i <= 2; i++) {
			if (belts[i] == null)
				return 2;
			if (belts[i].itemID != ItemStacks.belt.itemID || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 2;
		}
		for (int i = 3; i <= 6; i++) {
			if (belts[i] == null)
				return 4;
			if (belts[i].itemID != ItemStacks.belt.itemID || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 4;
		}
		for (int i = 7; i <= 14; i++) {
			if (belts[i] == null)
				return 8;
			if (belts[i].itemID != ItemStacks.belt.itemID || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 8;
		}
		for (int i = 15; i <= 30; i++) {
			if (belts[i] == null)
				return 16;
			if (belts[i].itemID != ItemStacks.belt.itemID || belts[i].getItemDamage() != ItemStacks.belt.getItemDamage())
				return 16;
		}
		return 32;
	}

	public void readFromCross(TileEntityShaft cross) {
		if (xCoord == cross.writex && zCoord == cross.writez) {
			omega = cross.readomega[0];
			if (worm)
				omega = (int)((((omega / WORMRATIO)*(100-4*ReikaMathLibrary.logbase(omega, 2)+28)))/100);
			torque = cross.readtorque[0];
			if (worm)
				torque = torque * WORMRATIO;
		}
		else if (xCoord == cross.writex2 && zCoord == cross.writez2) {
			omega = cross.readomega[1];
			if (worm)
				omega = (int)((((omega / WORMRATIO)*(100-4*ReikaMathLibrary.logbase(omega, 2)+28)))/100);
			torque = cross.readtorque[1];
			if (worm)
				torque = torque * WORMRATIO;
		}
		else
			return; //not its output
	}

	@Override
	public void transferPower(World world, int x, int y, int z, int meta) {
		this.getRatio();
		omegain = torquein = 0;
		TileEntity te = worldObj.getBlockTileEntity(readx, ready, readz);
		if (!this.isProvider(te) || !this.isIDTEMatch(world, readx, ready, readz)) {
			omega = 0;
			torque = 0;
			power = 0;
			return;
		}
		MachineRegistry m = MachineRegistry.machineList[((RotaryCraftTileEntity)(te)).getMachineIndex()];
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft devicein = (TileEntityShaft)world.getBlockTileEntity(readx, y, readz);
			if (devicein.getBlockMetadata() >= 6) {
				this.readFromCross(devicein);
				return;
			}
			if (devicein.writex == x && devicein.writey == y && devicein.writez == z) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}
		if (te instanceof SimpleProvider) {
			this.copyStandardPower(worldObj, readx, ready, readz);
		}
		if (te instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
			if (sp.isEmitting() && sp.canWriteToBlock(xCoord, yCoord, zCoord)) {
				torquein = sp.getTorque();
				omegain = sp.getOmega();
			}
		}
		if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter devicein = (TileEntitySplitter)world.getBlockTileEntity(readx, y, readz);
			if (devicein.getBlockMetadata() >= 8) {
				this.readFromSplitter(devicein);
				return;
			}
			else if (devicein.writex == x && devicein.writez == z) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}

		if (worm) {
			omega = (int)((omegain / WORMRATIO)*this.getPowerLossFraction(omegain));
			if (torquein <= RotaryConfig.torquelimit/WORMRATIO)
				torque = torquein * WORMRATIO;
			else {
				torque = RotaryConfig.torquelimit;
				world.spawnParticle("crit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), par5Random.nextFloat(), -0.5+par5Random.nextFloat());
				world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
			}
		}
		else if (!coil){ //CVT
			boolean speed = true;
			if (ratio > 0) {
				if (omegain <= RotaryConfig.omegalimit/ratio)
					omega = omegain * ratio;
				else {
					omega = RotaryConfig.omegalimit;
					world.spawnParticle("crit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
				}
				torque = torquein / ratio;
			}
			else {
				if (torquein <= RotaryConfig.torquelimit/-ratio)
					torque = torquein * -ratio;
				else {
					torque = RotaryConfig.torquelimit;
					world.spawnParticle("crit", x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), -0.5+par5Random.nextFloat(), par5Random.nextFloat(), -0.5+par5Random.nextFloat());
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "mob.blaze.hit", 0.1F, 1F);
				}
				omega = omegain / -ratio;
			}
		}
		else if (coil) {

		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setBoolean("worm", worm);
		NBT.setInteger("ratio", ratio);
		NBT.setBoolean("coil", coil);
		NBT.setLong("e", energy);
		NBT.setInteger("relo", releaseOmega);
		NBT.setInteger("relt", releaseTorque);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < belts.length; i++)
		{
			if (belts[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				belts[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		worm = NBT.getBoolean("worm");
		ratio = NBT.getInteger("ratio");
		coil = NBT.getBoolean("coil");
		energy = NBT.getLong("e");
		releaseOmega = NBT.getInteger("relo");
		releaseTorque = NBT.getInteger("relt");
		//if (this.ratio > this.getMaxRatio())
		//this.ratio = this.getMaxRatio();

		NBTTagList nbttaglist = NBT.getTagList("Items");
		belts = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < belts.length)
			{
				belts[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return belts.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return belts[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if (belts[var1] == null)
			return null;
		belts[var1] = null;
		return new ItemStack(ItemStacks.belt.itemID, 1, ItemStacks.belt.getItemDamage());
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		belts[var1] = var2;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return itemstack.itemID == ItemStacks.belt.itemID && itemstack.getItemDamage() == ItemStacks.belt.getItemDamage();
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		if (this.getBlockMetadata() >= 8)
			return new ModelCoil();
		return new ModelCVT();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.ADVANCEDGEARS.ordinal();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}
}
