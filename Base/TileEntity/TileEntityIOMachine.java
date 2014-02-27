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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public abstract class TileEntityIOMachine extends RotaryCraftTileEntity {

	public int iotick = 512;

	public int writex = Integer.MIN_VALUE;
	public int writey = Integer.MIN_VALUE;
	public int writez = Integer.MIN_VALUE;
	public int writex2 = Integer.MIN_VALUE;
	public int writey2 = Integer.MIN_VALUE;
	public int writez2 = Integer.MIN_VALUE;

	public long power = 0;
	public int torque = 0;
	public int omega = 0;
	public int readx = Integer.MIN_VALUE;
	public int ready = Integer.MIN_VALUE;
	public int readz = Integer.MIN_VALUE;
	public int readx2 = Integer.MIN_VALUE;
	public int ready2 = Integer.MIN_VALUE;
	public int readz2 = Integer.MIN_VALUE;
	public int readx3 = Integer.MIN_VALUE;
	public int ready3 = Integer.MIN_VALUE;
	public int readz3 = Integer.MIN_VALUE;
	public int readx4 = Integer.MIN_VALUE;
	public int ready4 = Integer.MIN_VALUE;
	public int readz4 = Integer.MIN_VALUE;

	private int pointoffsetx = 0;
	private int pointoffsety = 0;
	private int pointoffsetz = 0;

	public boolean isOmniSided = false;

	protected int torquein;
	protected int omegain;

	private boolean superCalled = false;
	private int superTick = 0;

	public void updateTileEntity() {
		if (iotick > 0)
			iotick -= 8;
		superCalled = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("torque", torque);
		NBT.setInteger("omega", omega);
		NBT.setLong("pwr", power);
		NBT.setInteger("io", iotick);
		NBT.setInteger("rx", readx);
		NBT.setInteger("ry", ready);
		NBT.setInteger("rz", readz);
		NBT.setInteger("rx2", readx2);
		NBT.setInteger("ry2", ready2);
		NBT.setInteger("rz2", readz2);
		NBT.setInteger("rx3", readx3);
		NBT.setInteger("ry3", ready3);
		NBT.setInteger("rz3", readz3);
		NBT.setInteger("rx4", readx4);
		NBT.setInteger("ry4", ready4);
		NBT.setInteger("rz4", readz4);

		NBT.setInteger("wx", writex);
		NBT.setInteger("wy", writey);
		NBT.setInteger("wz", writez);
		NBT.setInteger("wx2", writex2);
		NBT.setInteger("wy2", writey2);
		NBT.setInteger("wz2", writez2);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		torque = NBT.getInteger("torque");
		omega = NBT.getInteger("omega");
		power = NBT.getLong("pwr");
		iotick = NBT.getInteger("io");
		readx = NBT.getInteger("rx");
		ready = NBT.getInteger("ry");
		readz = NBT.getInteger("rz");
		readx2 = NBT.getInteger("rx2");
		ready2 = NBT.getInteger("ry2");
		readz2 = NBT.getInteger("rz2");
		readx3 = NBT.getInteger("rx3");
		ready3 = NBT.getInteger("ry3");
		readz3 = NBT.getInteger("rz3");
		readx4 = NBT.getInteger("rx4");
		ready4 = NBT.getInteger("ry4");
		readz4 = NBT.getInteger("rz4");

		writex = NBT.getInteger("wx");
		writey = NBT.getInteger("wy");
		writez = NBT.getInteger("wz");
		writex2 = NBT.getInteger("wx2");
		writey2 = NBT.getInteger("wy2");
		writez2 = NBT.getInteger("wz2");

		if (torque < 0)
			torque = 0;
		if (omega < 0)
			omega = 0;
	}

	protected boolean isProvider(TileEntity te) {
		if (te instanceof ShaftPowerEmitter)
			return true;
		if (!(te instanceof TileEntityIOMachine))
			return false;
		return ((TileEntityIOMachine)te).canProvidePower();
	}

	public abstract boolean canProvidePower();

	protected void copyStandardPower(World world, int x, int y, int z) {
		TileEntityIOMachine devicein = (TileEntityIOMachine)this.getTileEntity(x, y, z);
		if (devicein instanceof TileEntityShaft)
			return;
		if (!this.isPointingAt(world, x, y, z)) {
			omegain = 0;
			torquein = 0;
			return;
		}
		torquein = devicein.torque;
		omegain = devicein.omega;
	}

	private boolean isPointingAt(World world, int x, int y, int z) {
		boolean cy = false;
		TileEntityIOMachine devicein = (TileEntityIOMachine)this.getTileEntity(x, y, z);
		if (devicein instanceof TileEntityBevelGear || devicein instanceof TileEntityMultiClutch || devicein instanceof TileEntityBeltHub)
			cy = true;
		if (devicein.writex == xCoord+pointoffsetx && devicein.writez == zCoord+pointoffsetz) {
			if (!cy || devicein.writey == yCoord+pointoffsety) {
				return true;
			}
		}
		return false;
	}

	protected void setPointingOffset(int x, int y, int z) {
		pointoffsetx = x;
		pointoffsety = y;
		pointoffsetz = z;
	}

	public TileEntityIOMachine getInput() {
		if (ready == Integer.MIN_VALUE)
			ready = yCoord;
		TileEntity te = this.getTileEntity(readx, ready, readz);
		if (te instanceof TileEntityIOMachine)
			return (TileEntityIOMachine)te;
		return null;
	}

	public TileEntityIOMachine getOutput() {
		if (writey == Integer.MIN_VALUE)
			writey = yCoord;
		TileEntity te = this.getTileEntity(writex, writey, writez);
		if (te instanceof TileEntityIOMachine)
			return (TileEntityIOMachine)te;
		return null;
	}

	public boolean isOutputBlock(int x, int y, int z) {
		if (x == writex && y == writey && z == writez && writex != Integer.MIN_VALUE && writey != Integer.MIN_VALUE && writez != Integer.MIN_VALUE)
			return true;
		if (x == writex2 && y == writey2 && z == writez2 && writex2 != Integer.MIN_VALUE && writey2 != Integer.MIN_VALUE && writez2 != Integer.MIN_VALUE)
			return true;
		return false;
	}

	protected void writePowerToReciever(ShaftPowerReceiver sp) {
		if (sp.isReceiving() && sp.canReadFromBlock(xCoord, yCoord, zCoord)) {
			sp.setOmega(omega);
			sp.setTorque(torque);
			sp.setPower(omega*torque);
		}
		else {
			sp.setOmega(0);
			sp.setTorque(0);
			sp.setPower(0);
		}
	}

	protected void basicPowerReceiver() {
		TileEntity te = this.getTileEntity(writex, writey, writez);
		if (te instanceof ShaftPowerReceiver) {
			if (this.isBlacklistedReceiver(te)) {
				if (omega > 0 && torque > 0)
					this.affectBlacklistedReceiver(te);
			}
			else
				this.writePowerToReciever((ShaftPowerReceiver)te);
		}
	}

	private boolean isBlacklistedReceiver(TileEntity te) {
		return RotaryAux.isBlacklistedIOMachine(te);
	}

	private void affectBlacklistedReceiver(TileEntity te) {
		te.worldObj.setBlockToAir(te.xCoord, te.yCoord, te.zCoord);
		te.worldObj.createExplosion(null, te.xCoord, te.yCoord, te.zCoord, 3, true);
	}

	protected void writeToPowerReceiverAt(World world, int x, int y, int z, int om, int tq) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof ShaftPowerReceiver) {
			if (this.isBlacklistedReceiver(te)) {
				if (om > 0 && tq > 0)
					this.affectBlacklistedReceiver(te);
			}
			else {
				ShaftPowerReceiver sp = (ShaftPowerReceiver)te;
				if (sp.isReceiving() && sp.canReadFromBlock(xCoord, yCoord, zCoord)) {
					sp.setOmega(om);
					sp.setTorque(tq);
					sp.setPower(om*tq);
				}
				else {
					sp.setOmega(0);
					sp.setTorque(0);
					sp.setPower(0);
				}
			}
		}
	}

	public abstract PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller);

	public final ForgeDirection getInputForgeDirection() {
		int dx = readx-xCoord;
		int dy = ready-yCoord;
		int dz = readz-zCoord;
		if (dx == 1 && dy == 0 && dz == 0)
			return ForgeDirection.EAST;
		if (dx == -1 && dy == 0 && dz == 0)
			return ForgeDirection.WEST;

		if (dx == 0 && dy == 0 && dz == 1)
			return ForgeDirection.SOUTH;
		if (dx == 0 && dy == 0 && dz == -1)
			return ForgeDirection.NORTH;

		if (dx == 0 && dy == 1 && dz == 0)
			return ForgeDirection.UP;
		if (dx == 0 && dy == -1 && dz == 0)
			return ForgeDirection.DOWN;

		return ForgeDirection.UNKNOWN;
	}
}
