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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TransmissionReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityBeltHub extends TileEntityPowerReceiver implements PowerGenerator, SimpleProvider, TransmissionReceiver {

	private boolean isEmitting;

	private int[] source = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
	private int[] target = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

	private StepTimer sound = new StepTimer(25);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);

		sound.update();
		//isEmitting = true;
		if (isEmitting) {
			write = read;
			read = null;
			this.copyPower();
		}
		else {
			write = null;
			this.getPower(false);
		}

		if (omega > 0)
			this.playSound(world, x, y, z);
	}

	private void playSound(World world, int x, int y, int z) {
		if (sound.checkCap()) {
			SoundRegistry.BELT.playSoundAtBlock(world, x, y, z, 0.6F, 1F);
		}
	}

	public boolean areInSamePlane(TileEntityBeltHub belt) {
		int meta = this.getBlockMetadata();
		int meta2 = belt.getBlockMetadata();
		if (meta == 0 || meta == 1)
			return meta2 == 0 || meta2 == 1;
		if (meta == 2 || meta == 3)
			return meta2 == 2 || meta2 == 3;
		if (meta == 4 || meta == 5)
			return meta2 == 4 || meta2 == 5;
		return false;
	}

	public void reset() {
		//ReikaJavaLibrary.pConsole(this);
		source = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		target = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
	}

	public void resetOther() {
		if (!isEmitting) {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, target[0], target[1], target[2]);
			if (m == MachineRegistry.BELT) {
				TileEntityBeltHub te = (TileEntityBeltHub)worldObj.getBlockTileEntity(target[0], target[1], target[2]);
				te.reset();
			}
		}
		else {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]);
			if (m == MachineRegistry.BELT) {
				TileEntityBeltHub te = (TileEntityBeltHub)worldObj.getBlockTileEntity(source[0], source[1], source[2]);
				te.reset();
			}
		}
	}

	public boolean canConnect(int x, int y, int z) {
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

		for (int i = 1; i < Math.abs(dx+dy+dz); i++) {
			int xi = xCoord+dir.offsetX*i;
			int yi = yCoord+dir.offsetY*i;
			int zi = zCoord+dir.offsetZ*i;
			int id = worldObj.getBlockId(xi, yi, zi);
			//ReikaJavaLibrary.pConsole(xi+", "+yi+", "+zi+" -> "+id, Side.SERVER);
			if (!ReikaWorldHelper.softBlocks(worldObj, xi, yi, zi)) {
				return false;
			}
		}
		return true;
	}

	public boolean shouldRenderBelt() {
		if (isEmitting) {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]);
			return m == MachineRegistry.BELT && this.canConnect(source[0], source[1], source[2]);
		}
		else {
			if (target[0] != Integer.MIN_VALUE && target[1] != Integer.MIN_VALUE && target[2] != Integer.MIN_VALUE)
				//return MachineRegistry.getMachine(worldObj, target[0], target[1], target[2]) == MachineRegistry.BELT;
				return this.canConnect(target[0], target[1], target[2]);
			return false;
		}
	}

	public boolean setTarget(int x, int y, int z) {
		if (!this.canConnect(x, y, z))
			return false;
		if (target[0] != Integer.MIN_VALUE && target[1] != Integer.MIN_VALUE && target[2] != Integer.MIN_VALUE)
			;//return false;
		if (!this.areInSamePlane((TileEntityBeltHub)worldObj.getBlockTileEntity(x, y, z)))
			return false;
		target[0] = x;
		target[1] = y;
		target[2] = z;
		return true;
	}

	public boolean setSource(int x, int y, int z) {
		if (!this.canConnect(x, y, z))
			return false;
		if (source[0] != Integer.MIN_VALUE && source[1] != Integer.MIN_VALUE && source[2] != Integer.MIN_VALUE)
			;//return false;
		if (!this.areInSamePlane((TileEntityBeltHub)worldObj.getBlockTileEntity(x, y, z)))
			return false;
		source[0] = x;
		source[1] = y;
		source[2] = z;
		return true;
	}

	public boolean setEmitting(boolean emit) {
		boolean flag = isEmitting;
		isEmitting = emit;
		return flag;
	}

	public static int getMaxTorque() {
		return 8192;
	}

	public static int getMaxSmoothSpeed() {
		return 8192;
	}

	public int getSlipOmega(int omega) {
		int s = this.getMaxSmoothSpeed();
		if (omega <= s)
			return omega;
		else
			return (int)(s+Math.sqrt(omega-s));
	}

	private void copyPower() {
		MachineRegistry m = MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]);
		//ReikaJavaLibrary.pConsole(Arrays.toString(source));
		if (m == MachineRegistry.BELT && this.canConnect(source[0], source[1], source[2])) {
			TileEntityBeltHub tile = (TileEntityBeltHub)worldObj.getBlockTileEntity(source[0], source[1], source[2]);
			omega = this.getSlipOmega(tile.omega);
			torque = Math.min(this.getMaxTorque(), tile.torque);
			power = tile.power;
		}
		else {
			if (omega > 0)
				omega--;
			else
				torque = 0;
			power = omega*torque;
		}
	}

	public ForgeDirection getBeltDirection() {
		int dx = 0;
		int dy = 0;
		int dz = 0;

		if (isEmitting) {
			dx = xCoord-source[0];
			dy = yCoord-source[1];
			dz = zCoord-source[2];
		}
		else {
			dx = xCoord-target[0];
			dy = yCoord-target[1];
			dz = zCoord-target[2];
		}

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

	public int getDistanceToTarget() {
		if (isEmitting) {
			int dx = xCoord-source[0];
			int dy = yCoord-source[1];
			int dz = zCoord-source[2];
			return Math.abs(dx+dy+dz);
		}
		else {
			int dx = xCoord-target[0];
			int dy = yCoord-target[1];
			int dz = zCoord-target[2];
			return Math.abs(dx+dy+dz);
		}
	}

	public boolean isValidDirection(ForgeDirection dir) {
		switch(this.getBlockMetadata()) {
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
	public boolean canProvidePower() {
		return isEmitting;
	}

	public boolean isEmitting() {
		return isEmitting;
	}

	@Override
	public PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		return new PowerSourceList().addSource(this);
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
	public long getMaxPower() {
		return power;
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("emit", isEmitting);

		NBT.setIntArray("tg", target);
		NBT.setIntArray("src", source);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isEmitting = NBT.getBoolean("emit");

		source = NBT.getIntArray("src");
		target = NBT.getIntArray("tg");
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {/*
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord);
		ForgeDirection dir = this.getBeltDirection();
		int a = this.getDistanceToTarget();
		box.maxX += a*dir.offsetX;
		box.maxX -= a*dir.offsetX;
		box.maxY += a*dir.offsetY;
		box.maxY -= a*dir.offsetY;
		box.maxZ += a*dir.offsetZ;
		box.maxZ -= a*dir.offsetZ;*/
		//return AxisAlignedBB.getAABBPool().getAABB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
		return INFINITE_EXTENT_AABB;
		//int a = this.getDistanceToTarget();
		//return ReikaAABBHelper.getBlockAABB(xCoord, yCoord, zCoord).expand(a, a, a);
	}

	public int getTargetX() {
		return target[0];
	}

	public int getTargetY() {
		return target[1];
	}

	public int getTargetZ() {
		return target[2];
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
