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
import Reika.RotaryCraft.API.IOMachine;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.API.ShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public abstract class TileEntityIOMachine extends RotaryCraftTileEntity implements IOMachine {

	public int iotick = 512;

	protected ForgeDirection write;
	protected ForgeDirection write2;

	//public for now
	public long power = 0;
	public int torque = 0;
	public int omega = 0;

	protected ForgeDirection read;
	protected ForgeDirection read2;
	protected ForgeDirection read3;
	protected ForgeDirection read4;

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
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);
		NBT.setInteger("torque", torque);
		NBT.setInteger("omega", omega);
		NBT.setLong("power", power);
		NBT.setInteger("io", iotick);

		NBT.setInteger("read1", read != null ? read.ordinal() : -1);
		NBT.setInteger("read2", read2 != null ? read2.ordinal() : -1);
		NBT.setInteger("read3", read3 != null ? read3.ordinal() : -1);
		NBT.setInteger("read4", read4 != null ? read4.ordinal() : -1);

		NBT.setInteger("write1", write != null ? write.ordinal() : -1);
		NBT.setInteger("write2", write2 != null ? write2.ordinal() : -1);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);
		torque = NBT.getInteger("torque");
		omega = NBT.getInteger("omega");
		power = NBT.getLong("power");
		iotick = NBT.getInteger("io");

		if (torque < 0)
			torque = 0;
		if (omega < 0)
			omega = 0;

		int r1 = NBT.getInteger("read1");
		int r2 = NBT.getInteger("read2");
		int r3 = NBT.getInteger("read3");
		int r4 = NBT.getInteger("read4");
		read = r1 != -1 ? dirs[r1] : null;
		read2 = r2 != -1 ? dirs[r2] : null;
		read3 = r3 != -1 ? dirs[r3] : null;
		read4 = r4 != -1 ? dirs[r4] : null;

		int w1 = NBT.getInteger("write1");
		int w2 = NBT.getInteger("write2");
		write = w1 != -1 ? dirs[w1] : null;
		write2 = w2 != -1 ? dirs[w2] : null;
	}

	public final ForgeDirection getReadDirection() {
		return read;
	}

	public final ForgeDirection getReadDirection2() {
		return read2;
	}

	public final ForgeDirection getReadDirection3() {
		return read3;
	}

	public final ForgeDirection getReadDirection4() {
		return read4;
	}

	public final ForgeDirection getWriteDirection() {
		return write;
	}

	public final ForgeDirection getWriteDirection2() {
		return write2;
	}

	protected boolean isProvider(TileEntity te) {
		if (te instanceof ShaftPowerEmitter)
			return true;
		if (!(te instanceof TileEntityIOMachine))
			return false;
		return ((TileEntityIOMachine)te).canProvidePower();
	}

	public final TileEntity getReadTileEntity() {
		return this.getAdjacentTileEntity(read);
	}

	public final TileEntity getReadTileEntity2() {
		return this.getAdjacentTileEntity(read2);
	}

	public final TileEntity getReadTileEntity3() {
		return this.getAdjacentTileEntity(read3);
	}

	public final TileEntity getReadTileEntity4() {
		return this.getAdjacentTileEntity(read4);
	}

	public final TileEntity getWriteTileEntity() {
		return this.getAdjacentTileEntity(write);
	}

	public final TileEntity getWriteTileEntity2() {
		return this.getAdjacentTileEntity(write2);
	}

	public abstract boolean canProvidePower();

	public TileEntityIOMachine getInput() {
		TileEntity te = this.getAdjacentTileEntity(read);
		if (te instanceof TileEntityIOMachine)
			return (TileEntityIOMachine)te;
		return null;
	}

	public TileEntityIOMachine getOutput() {
		TileEntity te = this.getAdjacentTileEntity(write);
		if (te instanceof TileEntityIOMachine)
			return (TileEntityIOMachine)te;
		return null;
	}

	public final int getPointingOffsetX() {
		return pointoffsetx;
	}

	public final int getPointingOffsetY() {
		return pointoffsety;
	}

	public final int getPointingOffsetZ() {
		return pointoffsetz;
	}

	protected final void setPointingOffset(int x, int y, int z) {
		pointoffsetx = x;
		pointoffsety = y;
		pointoffsetz = z;
	}

	protected void writePowerToReciever(ShaftPowerReceiver sp) {
		if (sp.isReceiving() && sp.canReadFromBlock(xCoord, yCoord, zCoord)) {
			sp.setOmega(omega);
			sp.setTorque(torque);
			sp.setPower((long)omega*(long)torque);
		}
		else {
			sp.setOmega(0);
			sp.setTorque(0);
			sp.setPower(0);
		}
	}

	public final boolean isWritingToCoordinate(int x, int y, int z) {
		if (write == null)
			return false;
		boolean wx = xCoord+write.offsetX == x;
		boolean wy = yCoord+write.offsetY == y;
		boolean wz = zCoord+write.offsetZ == z;
		return wx && wy && wz;
	}

	public final boolean isWritingToCoordinate2(int x, int y, int z) {
		if (write2 == null)
			return false;
		boolean wx = xCoord+write2.offsetX == x;
		boolean wy = yCoord+write2.offsetY == y;
		boolean wz = zCoord+write2.offsetZ == z;
		return wx && wy && wz;
	}

	public final boolean isWritingTo(TileEntityIOMachine te) {
		if (write == null)
			return false;
		boolean x = xCoord+write.offsetX == te.xCoord+te.pointoffsetx;
		boolean y = yCoord+write.offsetY == te.yCoord+te.pointoffsety;
		boolean z = zCoord+write.offsetZ == te.zCoord+te.pointoffsetz;
		return x && y && z;
	}

	public final boolean isWritingTo2(TileEntityIOMachine te) {
		if (write2 == null)
			return false;
		boolean x = xCoord+write2.offsetX == te.xCoord+te.pointoffsetx;
		boolean y = yCoord+write2.offsetY == te.yCoord+te.pointoffsety;
		boolean z = zCoord+write2.offsetZ == te.zCoord+te.pointoffsetz;
		return x && y && z;
	}

	public final boolean isReadingFrom(TileEntityIOMachine te) {
		if (read == null)
			return false;
		boolean x = xCoord+read.offsetX == te.xCoord+te.pointoffsetx;
		boolean y = yCoord+read.offsetY == te.yCoord+te.pointoffsety;
		boolean z = zCoord+read.offsetZ == te.zCoord+te.pointoffsetz;
		return x && y && z;
	}

	public final boolean isReadingFrom2(TileEntityIOMachine te) {
		if (read2 == null)
			return false;
		boolean x = xCoord+read2.offsetX == te.xCoord+te.pointoffsetx;
		boolean y = yCoord+read2.offsetY == te.yCoord+te.pointoffsety;
		boolean z = zCoord+read2.offsetZ == te.zCoord+te.pointoffsetz;
		return x && y && z;
	}

	public final boolean isReadingFrom3(TileEntityIOMachine te) {
		if (read3 == null)
			return false;
		boolean x = xCoord+read3.offsetX == te.xCoord+te.pointoffsetx;
		boolean y = yCoord+read3.offsetY == te.yCoord+te.pointoffsety;
		boolean z = zCoord+read3.offsetZ == te.zCoord+te.pointoffsetz;
		return x && y && z;
	}

	public final boolean isReadingFrom4(TileEntityIOMachine te) {
		if (read4 == null)
			return false;
		boolean x = xCoord+read4.offsetX == te.xCoord+te.pointoffsetx;
		boolean y = yCoord+read4.offsetY == te.yCoord+te.pointoffsety;
		boolean z = zCoord+read4.offsetZ == te.zCoord+te.pointoffsetz;
		return x && y && z;
	}

	protected final void basicPowerReceiver() {
		TileEntity te = this.getAdjacentTileEntity(write);
		if (te instanceof ShaftPowerReceiver) {
			if (this.isBlacklistedReceiver(te)) {
				if (omega > 0 && torque > 0)
					this.affectBlacklistedReceiver(te);
			}
			else
				this.writePowerToReciever((ShaftPowerReceiver)te);
		}
	}

	protected final void copyStandardPower(TileEntity te) {
		this.copyStandardPower((TileEntityIOMachine)te);
	}

	protected final void copyStandardPower(TileEntityIOMachine te) {
		if (te instanceof TileEntityShaft)
			return;
		if (!te.isWritingTo(this) && !te.isWritingTo2(this)) {
			omegain = 0;
			torquein = 0;
			return;
		}
		torquein = te.torque;
		omegain = te.omega;
	}

	private boolean isBlacklistedReceiver(TileEntity te) {
		return RotaryAux.isBlacklistedIOMachine(te);
	}

	private void affectBlacklistedReceiver(TileEntity te) {
		te.worldObj.setBlockToAir(te.xCoord, te.yCoord, te.zCoord);
		te.worldObj.createExplosion(null, te.xCoord, te.yCoord, te.zCoord, 3, true);
	}

	protected void writeToPowerReceiverAt(World world, int x, int y, int z, int om, int tq) {
		TileEntity te = this.getTileEntity(x, y, z);
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
		return read;
	}

	@Override
	public int getWriteX() {
		return write != null ? xCoord+write.offsetX : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteY() {
		return write != null ? yCoord+write.offsetY : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteZ() {
		return write != null ? zCoord+write.offsetZ : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteX2() {
		return write2 != null ? xCoord+write2.offsetX : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteY2() {
		return write2 != null ? yCoord+write2.offsetY : Integer.MIN_VALUE;
	}

	@Override
	public int getWriteZ2() {
		return write2 != null ? zCoord+write2.offsetZ : Integer.MIN_VALUE;
	}
}
