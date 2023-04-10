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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Interfaces.TileEntity.Connectable;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TransmissionReceiver;
import Reika.RotaryCraft.Auxiliary.Interfaces.Wettable;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityBeltHub extends TileEntityPowerReceiver implements PowerGenerator, SimpleProvider, TransmissionReceiver, Wettable, Connectable<Coordinate> {

	public boolean isReceivingEnd;
	private int wetTimer = 0;

	private boolean isSlippingTorque;
	private boolean isSlippingOmega;

	private Coordinate otherEnd = null;

	private StepTimer sound = new StepTimer(26);

	public boolean isSplitting() {
		return this.getBlockMetadata() >= 6;
	}

	@Override
	public final void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta%6);

		sound.update();
		//isEmitting = true;
		if (isReceivingEnd) {
			this.setReceiverIOSides();
			this.copyPowerFromDriver();
		}
		else {
			if (this.isSplitting())
				write = read.getOpposite();
			else
				write = null;
			this.getPower(false);
			if (this.isSplitting()) {
				torque = Math.max(0, this.getPropagatedTorque(torque));
				power = (long)omega*(long)torque;
			}
		}

		if (power > 0)
			this.playSound(world, x, y, z);

		if (!world.isRemote && world.isRaining() && world.canLightningStrikeAt(x, y+1, z) && rand.nextInt(900) == 0)
			this.makeWet(1);

		if (wetTimer > 0 && power > 0)
			wetTimer--;
	}

	protected int getPropagatedTorque(int input) {
		return input/2;
	}

	protected void setReceiverIOSides() {
		write = read;
		if (this.isSplitting())
			write2 = read.getOpposite();
		else
			write2 = null;
		read = null;
	}

	private void playSound(World world, int x, int y, int z) {
		if (sound.checkCap()) {
			SoundRegistry.BELT.playSoundAtBlock(world, x, y, z, 0.6F, 1F);
		}
	}

	public final boolean areInSamePlane(TileEntityBeltHub belt) {
		int meta = this.getBlockMetadata()%6;
		int meta2 = belt.getBlockMetadata()%6;
		if (meta == 0 || meta == 1)
			return meta2 == 0 || meta2 == 1;
		if (meta == 2 || meta == 3)
			return meta2 == 2 || meta2 == 3;
		if (meta == 4 || meta == 5)
			return meta2 == 4 || meta2 == 5;
		return false;
	}

	public final void reset() {
		otherEnd = null;
	}

	public final void resetOther() {
		if (otherEnd == null)
			return;
		MachineRegistry m = MachineRegistry.getMachine(worldObj, otherEnd.xCoord, otherEnd.yCoord, otherEnd.zCoord);
		if (m == this.getMachine()) {
			TileEntityBeltHub te = (TileEntityBeltHub)otherEnd.getTileEntity(worldObj);
			te.reset();
		}
	}

	private final boolean canConnect(World world, int x, int y, int z) {
		int dx = x-xCoord;
		int dy = y-yCoord;
		int dz = z-zCoord;

		//ReikaJavaLibrary.pConsole(isEmitting ? Arrays.toString(source) : Arrays.toString(target));

		if (!ReikaMathLibrary.nBoolsAreTrue(1, dx != 0, dy != 0, dz != 0))
			return false;

		ForgeDirection dir = ForgeDirection.UNKNOWN;

		if (dx > 0)
			dir = ForgeDirection.EAST;
		if (dx < 0)
			dir = ForgeDirection.WEST;
		if (dy > 0)
			dir = ForgeDirection.UP;
		if (dy < 0)
			dir = ForgeDirection.DOWN;
		if (dz > 0)
			dir = ForgeDirection.SOUTH;
		if (dz < 0)
			dir = ForgeDirection.NORTH;

		if (dir == null)
			return false;
		if (!this.isValidDirection(dir))
			return false;

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityBeltHub) {
			TileEntityBeltHub tb = (TileEntityBeltHub)te;
			if (tb.isReceivingEnd == isReceivingEnd)
				return false;
			if (!this.areInSamePlane(tb))
				return false;
			for (int i = 1; i < Math.abs(dx+dy+dz); i++) {
				int xi = xCoord+dir.offsetX*i;
				int yi = yCoord+dir.offsetY*i;
				int zi = zCoord+dir.offsetZ*i;
				if (!ReikaWorldHelper.softBlocks(worldObj, xi, yi, zi)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public final boolean hasValidConnection() {
		if (otherEnd == null)
			return false;
		MachineRegistry m = MachineRegistry.getMachine(worldObj, otherEnd.xCoord, otherEnd.yCoord, otherEnd.zCoord);
		return m == this.getMachine() && this.canConnect(worldObj, otherEnd.xCoord, otherEnd.yCoord, otherEnd.zCoord);
	}

	public final boolean tryConnect(World world, int x, int y, int z) {
		if (otherEnd != null)
			return false;
		if (x == xCoord && y == yCoord && z == zCoord)
			return false;
		if (!this.canConnect(world, x, y, z))
			return false;
		otherEnd = new Coordinate(x, y, z);
		return true;
	}

	public int getMaxTorque() {
		return 8192;
	}

	public int getMaxSmoothSpeed() {
		return 8192;
	}

	public int copyTorqueFromDriverSide(int input) {
		int max = this.isWet() ? this.getMaxTorque()/4 : this.getMaxTorque();
		isSlippingTorque = input > max;
		int torque = Math.min(input, max);
		return this.isSplitting() ? torque/2 : torque;
	}

	public int copyOmegaFromDriverSide(int input) {
		int s = this.isWet() ? this.getMaxSmoothSpeed()/4 : this.getMaxSmoothSpeed();
		isSlippingOmega = input > s;
		int speed = input <= s ? input : (int)(s+Math.sqrt(input-s));
		return speed;
	}

	private void copyPowerFromDriver() {
		boolean noInput = true;
		omega = 0;
		torque = 0;
		power = 0;
		if (this.hasValidConnection()) {
			TileEntityBeltHub tile = (TileEntityBeltHub)otherEnd.getTileEntity(worldObj);
			omega = this.copyOmegaFromDriverSide(tile.omegain);
			torque = this.copyTorqueFromDriverSide(tile.torquein);
			power = (long)omega*(long)torque;
			noInput = false;
		}
		if (this.mergeReceivingPower())
			noInput = false;
		if (noInput) {
			if (omega > 0)
				omega *= 0.98;
			else
				torque = 0;
			power = (long)omega*(long)torque;
		}
	}

	protected boolean mergeReceivingPower() {
		return false;
	}

	public final ForgeDirection getBeltDirection() {
		if (otherEnd == null)
			return ForgeDirection.UNKNOWN;
		int dx = 0;
		int dy = 0;
		int dz = 0;

		dx = xCoord-otherEnd.xCoord;
		dy = yCoord-otherEnd.yCoord;
		dz = zCoord-otherEnd.zCoord;

		ForgeDirection dir = ForgeDirection.UNKNOWN;
		if (dx < 0)
			dir = ForgeDirection.EAST;
		if (dx > 0)
			dir = ForgeDirection.WEST;
		if (dy < 0)
			dir = ForgeDirection.UP;
		if (dy > 0)
			dir = ForgeDirection.DOWN;
		if (dz < 0)
			dir = ForgeDirection.SOUTH;
		if (dz > 0)
			dir = ForgeDirection.NORTH;
		return dir;
	}

	public final int getDistanceToTarget() {
		return otherEnd == null ? -1 : otherEnd.getTaxicabDistanceTo(xCoord, yCoord, zCoord);
	}

	public final boolean isValidDirection(ForgeDirection dir) {
		switch(this.getBlockMetadata()%6) {
			case 0:
			case 1:
				return dir.offsetX == 0;
			case 2:
			case 3:
				return dir.offsetZ == 0;
			case 4:
			case 5:
				return dir.offsetY == 0;
		}
		return false;
	}

	@Override
	public final boolean canProvidePower() {
		return isReceivingEnd || this.isSplitting();
	}

	@Override
	public PowerSourceList getPowerSources(PowerSourceTracker io, ShaftMerger caller) {
		if (isReceivingEnd) {
			TileEntityBeltHub tile = (TileEntityBeltHub)otherEnd.getTileEntity(worldObj);
			return tile != null ? tile.getPowerSources(io, caller) : new PowerSourceList();
		}
		else {
			return PowerSourceList.getAllFrom(worldObj, read, xCoord+read.offsetX, yCoord+read.offsetY, zCoord+read.offsetZ, this, caller);
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BELT;
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
	public final long getMaxPower() {
		return this.isSplitting() ? power/2 : power;
	}

	@Override
	public final long getCurrentPower() {
		return this.isSplitting() ? power/2 : power;
	}

	public final boolean isWet() {
		return wetTimer > 0;
	}

	public final void makeWet(float fac) {
		wetTimer = Math.min(wetTimer+(int)(3600*fac), 18000); //3 to 15 min
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("emit", isReceivingEnd);

		if (otherEnd != null)
			otherEnd.writeToNBT("endpoint", NBT);

		NBT.setInteger("wet", wetTimer);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		isReceivingEnd = NBT.getBoolean("emit");

		otherEnd = Coordinate.readFromNBT("endpoint", NBT);

		wetTimer = NBT.getInteger("wet");
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {/*
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord);
		ForgeDirection dir = this.getBeltDirection();
		int a = this.getDistanceToTarget();
		box.maxX += a*dir.offsetX;
		box.maxX -= a*dir.offsetX;
		box.maxY += a*dir.offsetY;
		box.maxY -= a*dir.offsetY;
		box.maxZ += a*dir.offsetZ;
		box.maxZ -= a*dir.offsetZ;*/
		//return AxisAlignedBB.getBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
		return INFINITE_EXTENT_AABB;
		//int a = this.getDistanceToTarget();
		//return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord).expand(a, a, a);
	}

	public final Coordinate getConnection() {
		return otherEnd;
	}

	@Override
	public final int getEmittingX() {
		return xCoord+write.offsetX;
	}

	@Override
	public final int getEmittingY() {
		return yCoord+write.offsetY;
	}

	@Override
	public final int getEmittingZ() {
		return zCoord+write.offsetZ;
	}

	public int[] getBeltColor() {
		return new int[]{192, 120, 70};
	}

	public ItemStack getBeltItem() {
		return ItemStacks.belt.copy();
	}

	@Override
	public boolean isEmitting() {
		return isReceivingEnd;
	}

	@Override
	public final void breakBlock() {
		if (!worldObj.isRemote) {
			int num = this.getDistanceToTarget()-1;
			num = Math.min(num, ItemStacks.belt.getMaxStackSize());
			if (!this.hasValidConnection())
				num = 0;
			for (int i = 0; i < num; i++) {
				ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, this.getBeltItem());
			}
			this.resetOther();
		}
	}

	@Override
	public final void getOutputs(Collection<TileEntity> c, ForgeDirection dir) {
		if (otherEnd != null) {
			TileEntity te = otherEnd.getTileEntity(worldObj);
			if (te instanceof TileEntityBeltHub) {
				TileEntityBeltHub belt = (TileEntityBeltHub)te;
				c.add(belt.getAdjacentTileEntity(belt.write));
				c.add(belt.getAdjacentTileEntity(belt.write2));
			}
		}
	}

	public boolean isSlipping() {
		return isSlippingOmega || isSlippingTorque;
	}

	@Override
	public void wet() {
		this.makeWet(0.25F);
	}
}
