/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base.TileEntity;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.ChromatiCraft.API.Interfaces.WorldRift;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.RotaryCraft.API.IOMachine;
import Reika.RotaryCraft.API.Power.AdvancedShaftPowerReceiver;
import Reika.RotaryCraft.API.Power.ShaftPowerReceiver;
import Reika.RotaryCraft.API.Power.SimpleShaftPowerReceiver;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;

public abstract class TileEntityIOMachine extends RotaryCraftTileEntity implements IOMachine, PowerSourceTracker {

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

	public void updateTileEntity() {
		if (iotick > 0)
			iotick -= 8;
		superCalled = true;
	}
	/*
	@Override
	protected void onDataSync(boolean fullNBT) {
		this.recursiveSyncPower(fullNBT, new ArrayList());
	}

	private void recursiveSyncPower(boolean fullNBT, Collection<TileEntityIOMachine> li) {
		li.add(this);
		TileEntity te = this.getWriteTileEntity();
		if (te instanceof TileEntityIOMachine && !li.contains(te)) {
			((TileEntityIOMachine) te).recursiveSyncPower(fullNBT, li);
		}
		TileEntity te2 = this.getWriteTileEntity2();
		if (te2 instanceof TileEntityIOMachine && !li.contains(te2)) {
			((TileEntityIOMachine) te2).recursiveSyncPower(fullNBT, li);
		}

		TileEntity tea = this.getReadTileEntity();
		//ReikaJavaLibrary.pConsole(li.contains(tea), tea instanceof TileEntityMonitor);
		if (tea instanceof TileEntityIOMachine && !li.contains(tea)) {
			((TileEntityIOMachine) tea).recursiveSyncPower(fullNBT, li);
		}
		TileEntity teb = this.getReadTileEntity2();
		if (teb instanceof TileEntityIOMachine && !li.contains(teb)) {
			((TileEntityIOMachine) teb).recursiveSyncPower(fullNBT, li);
		}
		TileEntity tec = this.getReadTileEntity3();
		if (tec instanceof TileEntityIOMachine && !li.contains(tec)) {
			((TileEntityIOMachine) tec).recursiveSyncPower(fullNBT, li);
		}
		TileEntity ted = this.getReadTileEntity4();
		if (ted instanceof TileEntityIOMachine && !li.contains(ted)) {
			((TileEntityIOMachine) ted).recursiveSyncPower(fullNBT, li);
		}
	}
	 */
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

		NBT.setBoolean("omni", isOmniSided);

		NBT.setInteger("pox", pointoffsetx);
		NBT.setInteger("poy", pointoffsety);
		NBT.setInteger("poz", pointoffsetz);
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

		isOmniSided = NBT.getBoolean("omni");

		pointoffsetx = NBT.getInteger("pox");
		pointoffsety = NBT.getInteger("poy");
		pointoffsetz = NBT.getInteger("poz");
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
		return read != null ? this.getAdjacentTileEntity(read) : null;
	}

	public final TileEntity getReadTileEntity2() {
		return read2 != null ? this.getAdjacentTileEntity(read2) : null;
	}

	public final TileEntity getReadTileEntity3() {
		return read3 != null ? this.getAdjacentTileEntity(read3) : null;
	}

	public final TileEntity getReadTileEntity4() {
		return read4 != null ? this.getAdjacentTileEntity(read4) : null;
	}

	public final TileEntity getWriteTileEntity() {
		return write != null ? this.getAdjacentTileEntity(write) : null;
	}

	public final TileEntity getWriteTileEntity2() {
		return write2 != null ? this.getAdjacentTileEntity(write2) : null;
	}

	public final WorldLocation getReadLocation() {
		return read != null ? this.getAdjacentLocation(read) : null;
	}

	public final WorldLocation getReadLocation2() {
		return read2 != null ? this.getAdjacentLocation(read2) : null;
	}

	public final WorldLocation getReadLocation3() {
		return read3 != null ? this.getAdjacentLocation(read3) : null;
	}

	public final WorldLocation getReadLocation4() {
		return read4 != null ? this.getAdjacentLocation(read4) : null;
	}

	public final WorldLocation getWriteLocation() {
		return write != null ? this.getAdjacentLocation(write) : null;
	}

	public final WorldLocation getWriteLocation2() {
		return write2 != null ? this.getAdjacentLocation(write2) : null;
	}

	public abstract boolean canProvidePower();
	/*
	public ShaftMachine getInput() {
		TileEntity te = this.getAdjacentTileEntity(read);
		if (te instanceof ShaftMachine)
			return (ShaftMachine)te;
		return null;
	}

	public ShaftMachine getInput2() {
		TileEntity te = this.getAdjacentTileEntity(read2);
		if (te instanceof ShaftMachine)
			return (ShaftMachine)te;
		return null;
	}

	public ShaftMachine getInput3() {
		TileEntity te = this.getAdjacentTileEntity(read3);
		if (te instanceof ShaftMachine)
			return (ShaftMachine)te;
		return null;
	}

	public ShaftMachine getInput4() {
		TileEntity te = this.getAdjacentTileEntity(read4);
		if (te instanceof ShaftMachine)
			return (ShaftMachine)te;
		return null;
	}

	public ShaftMachine getOutput() {
		TileEntity te = this.getAdjacentTileEntity(write);
		if (te instanceof ShaftMachine)
			return (ShaftMachine)te;
		return null;
	}

	public ShaftMachine getOutput2() {
		TileEntity te = this.getAdjacentTileEntity(write2);
		if (te instanceof ShaftMachine)
			return (ShaftMachine)te;
		return null;
	}
	 */
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
	/*
	protected final void processTileSimply(TileEntity te, MachineRegistry m, int tgx, int tgy, int tgz) {
		if (m == MachineRegistry.SHAFT) {
			TileEntityShaft devicein = (TileEntityShaft)te;
			if (devicein.isCross()) {
				this.readFromCross(devicein);
				return;
			}
			if (devicein.isWritingTo(this)) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}
		if (m == MachineRegistry.POWERBUS) {
			TileEntityPowerBus pwr = (TileEntityPowerBus)te;
			ForgeDirection dir = this.getInputForgeDirection().getOpposite();
			omegain = pwr.getSpeedToSide(dir);
			torquein = pwr.getTorqueToSide(dir);
		}
		if (te instanceof SimpleProvider) {
			this.copyStandardPower(te);
		}
		if (te instanceof ShaftPowerEmitter) {
			ShaftPowerEmitter sp = (ShaftPowerEmitter)te;
			if (sp.isEmitting() && sp.canWriteToBlock(tgx, tgy, tgz)) {
				torquein = sp.getTorque();
				omegain = sp.getOmega();
			}
		}
		if (m == MachineRegistry.SPLITTER) {
			TileEntitySplitter devicein = (TileEntitySplitter)te;
			if (devicein.isSplitting()) {
				this.readFromSplitter(devicein);
				return;
			}
			else if (devicein.isWritingTo(this)) {
				torquein = devicein.torque;
				omegain = devicein.omega;
			}
		}
		omega = omegain;
		torque = torquein;
	}*/

	//protected void readFromSplitter(TileEntitySplitter te) {}
	//protected void readFromCross(TileEntityShaft te) {}

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

	protected final boolean matchTile(PowerSourceTracker te, ForgeDirection dir) {
		if (dir == null)
			return false;
		int dim = te.getWorld().provider.dimensionId;
		int tx = te.getX()+te.getIoOffsetX();
		int ty = te.getY()+te.getIoOffsetY();
		int tz = te.getZ()+te.getIoOffsetZ();
		TileEntity out = this.getAdjacentTileEntity(dir);
		while (out instanceof WorldRift) {
			out = ((WorldRift)out).getTileEntityFrom(dir);
		}
		if (out == null)
			return false;
		return !out.isInvalid() && out.worldObj.provider.dimensionId == dim && out.xCoord == tx && out.yCoord == ty && out.zCoord == tz;
	}

	public boolean isWritingTo(PowerSourceTracker te) {
		return this.matchTile(te, write);
	}

	public final boolean isWritingTo2(PowerSourceTracker te) {
		return this.matchTile(te, write2);
	}

	public final boolean isReadingFrom(PowerSourceTracker te) {
		return this.matchTile(te, read);
	}

	public final boolean isReadingFrom2(PowerSourceTracker te) {
		return this.matchTile(te, read2);
	}

	public final boolean isReadingFrom3(PowerSourceTracker te) {
		return this.matchTile(te, read3);
	}

	public final boolean isReadingFrom4(PowerSourceTracker te) {
		return this.matchTile(te, read4);
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

	private void setPower(TileEntity te, ForgeDirection from, int om, int tq) {
		if (te instanceof SimpleShaftPowerReceiver) {
			if (this.isBlacklistedReceiver(te)) {
				if (omega > 0 && torque > 0)
					this.affectBlacklistedReceiver(te);
			}
			else {
				SimpleShaftPowerReceiver sp = (SimpleShaftPowerReceiver)te;
				sp.setPowered(sp.canReadFrom(from.getOpposite()) && tq > 0 && om > 0);
			}
		}
		else if (te instanceof ShaftPowerReceiver) {
			if (this.isBlacklistedReceiver(te)) {
				if (omega > 0 && torque > 0)
					this.affectBlacklistedReceiver(te);
			}
			else {
				ShaftPowerReceiver sp = (ShaftPowerReceiver)te;
				if (sp.isReceiving() && sp.canReadFrom(from.getOpposite())) {
					sp.setOmega(om);
					sp.setTorque(tq);
					sp.setPower((long)om*(long)tq);
				}
				else {
					sp.setOmega(0);
					sp.setTorque(0);
					sp.setPower(0);
				}
			}
		}
		else if (te instanceof AdvancedShaftPowerReceiver) {
			if (this.isBlacklistedReceiver(te)) {
				if (omega > 0 && torque > 0)
					this.affectBlacklistedReceiver(te);
			}
			else {
				AdvancedShaftPowerReceiver sp = (AdvancedShaftPowerReceiver)te;
				if (sp.canReadFrom(from.getOpposite())) {
					sp.addPower(tq, om, (long)tq*(long)om, from.getOpposite());
				}
			}
		}
		else if (te instanceof WorldRift) {
			this.setPower(((WorldRift)te).getTileEntityFrom(from), from, om, tq);
		}/*
		else if (te instanceof TileEntityIOMachine) {
			TileEntityIOMachine io = (TileEntityIOMachine)te;
			io.torque = tq;
			io.omega = om;
			io.power = (long)om*(long)tq;
		}*/
	}

	protected final void basicPowerReceiver() {
		this.writeToReceiver(write);
	}

	private void writeToReceiver(ForgeDirection dir) {
		this.writeToReceiver(dir, omega, torque);
	}

	private void writeToReceiver(ForgeDirection dir, int om, int tq) {
		TileEntity te = this.getAdjacentTileEntity(dir);
		this.setPower(te, dir, om, tq);
	}

	protected void writeToPowerReceiver(ForgeDirection dir, int om, int tq) {
		this.writeToReceiver(dir, om, tq);
		/*
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
					sp.setPower((long)om*(long)tq);
				}
				else {
					sp.setOmega(0);
					sp.setTorque(0);
					sp.setPower(0);
				}
			}
		}*/
	}

	private void writePowerToReciever(ShaftPowerReceiver sp) {

	}

	private boolean isBlacklistedReceiver(TileEntity te) {
		return RotaryAux.isBlacklistedIOMachine(te);
	}

	private void affectBlacklistedReceiver(TileEntity te) {
		te.worldObj.setBlockToAir(te.xCoord, te.yCoord, te.zCoord);
		te.worldObj.createExplosion(null, te.xCoord, te.yCoord, te.zCoord, 3, true);
	}

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

	@Override
	public final World getWorld() {
		return worldObj;
	}

	@Override
	public final int getX() {
		return xCoord;
	}

	@Override
	public final int getY() {
		return yCoord;
	}

	@Override
	public final int getZ() {
		return zCoord;
	}

	@Override
	public final int getIoOffsetX() {
		return pointoffsetx;
	}

	@Override
	public final int getIoOffsetY() {
		return pointoffsety;
	}

	@Override
	public final int getIoOffsetZ() {
		return pointoffsetz;
	}

	public boolean canTransmitPower() {
		return true;
	}

	public abstract void getAllOutputs(Collection<TileEntity> c, ForgeDirection dir);
	/*
	public final int getOmega() {
		return omega;
	}

	public final int getTorque() {
		return torque;
	}

	public final long getPower() {
		return power;
	}

	public final int getIORenderAlpha() {
		return iotick;
	}

	public final void setIORenderAlpha(int io) {
		iotick = io;
	}*/
}
