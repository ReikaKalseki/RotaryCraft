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

import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Auxiliary.Interfaces.SimpleProvider;
import Reika.RotaryCraft.Auxiliary.Interfaces.TransmissionReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBeltHub extends TileEntityPowerReceiver implements PowerGenerator, SimpleProvider, TransmissionReceiver {

	public boolean isEmitting;
	private int wetTimer = 0;

	private int[] source = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
	private int[] target = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

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
		if (isEmitting) {
			write = read;
			if (this.isSplitting())
				write2 = read.getOpposite();
			else
				write2 = null;
			read = null;
			this.copyPower();
		}
		else {
			if (this.isSplitting())
				write = read.getOpposite();
			else
				write = null;
			this.getPower(false);
			if (this.isSplitting()) {
				power /= 2;
				torque /= 2;
			}
		}

		if (power > 0)
			this.playSound(world, x, y, z);

		if (world.isRaining() && world.canBlockSeeTheSky(x, y+1, z) && world.getTotalWorldTime()%1024 == 0)
			this.makeWet();

		if (wetTimer > 0 && power > 0)
			wetTimer--;
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
		source = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		target = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
	}

	public final void resetOther() {
		if (!isEmitting) {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, target[0], target[1], target[2]);
			if (m == this.getMachine()) {
				TileEntityBeltHub te = (TileEntityBeltHub)worldObj.getTileEntity(target[0], target[1], target[2]);
				te.reset();
			}
		}
		else {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]);
			if (m == this.getMachine()) {
				TileEntityBeltHub te = (TileEntityBeltHub)worldObj.getTileEntity(source[0], source[1], source[2]);
				te.reset();
			}
		}
	}

	public final boolean canConnect(int x, int y, int z) {
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
			Block id = worldObj.getBlock(xi, yi, zi);
			//ReikaJavaLibrary.pConsole(xi+", "+yi+", "+zi+" -> "+id, Side.SERVER);
			if (!ReikaWorldHelper.softBlocks(worldObj, xi, yi, zi)) {
				return false;
			}
		}
		return true;
	}

	public final boolean shouldRenderBelt() {
		if (isEmitting) {
			MachineRegistry m = MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]);
			return m == this.getMachine() && this.canConnect(source[0], source[1], source[2]);
		}
		else {
			if (target[0] != Integer.MIN_VALUE && target[1] != Integer.MIN_VALUE && target[2] != Integer.MIN_VALUE)
				//return MachineRegistry.getMachine(worldObj, target[0], target[1], target[2]) == MachineRegistry.BELT;
				return this.canConnect(target[0], target[1], target[2]);
			return false;
		}
	}

	public final boolean setTarget(int x, int y, int z) {
		if (!this.canConnect(x, y, z))
			return false;
		if (target[0] != Integer.MIN_VALUE && target[1] != Integer.MIN_VALUE && target[2] != Integer.MIN_VALUE)
			;//return false;
		if (!this.areInSamePlane((TileEntityBeltHub)worldObj.getTileEntity(x, y, z)))
			return false;
		target[0] = x;
		target[1] = y;
		target[2] = z;
		//ReikaJavaLibrary.pConsole(this, Side.SERVER);
		return true;
	}

	public final boolean setSource(int x, int y, int z) {
		if (!this.canConnect(x, y, z))
			return false;
		if (source[0] != Integer.MIN_VALUE && source[1] != Integer.MIN_VALUE && source[2] != Integer.MIN_VALUE)
			;//return false;
		if (!this.areInSamePlane((TileEntityBeltHub)worldObj.getTileEntity(x, y, z)))
			return false;
		source[0] = x;
		source[1] = y;
		source[2] = z;
		//ReikaJavaLibrary.pConsole(this, Side.SERVER);
		return true;
	}

	public static int getMaxTorque() {
		return 8192;
	}

	public int getTorque(int input) {
		int max = this.isWet() ? this.getMaxTorque()/4 : this.getMaxTorque();
		int torque = Math.min(input, max);
		return this.isSplitting() ? torque/2 : torque;
	}

	public static int getMaxSmoothSpeed() {
		return 8192;
	}

	public int getOmega(int input) {
		int s = this.isWet() ? this.getMaxSmoothSpeed()/4 : this.getMaxSmoothSpeed();
		int speed = input <= s ? input : (int)(s+Math.sqrt(input-s));
		return speed;
	}

	private void copyPower() {
		MachineRegistry m = MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]);
		//ReikaJavaLibrary.pConsole(Arrays.toString(source));
		if (m == this.getMachine() && this.canConnect(source[0], source[1], source[2])) {
			TileEntityBeltHub tile = (TileEntityBeltHub)worldObj.getTileEntity(source[0], source[1], source[2]);
			omega = this.getOmega(tile.omega);
			torque = this.getTorque(tile.torque);
			power = (long)omega*(long)torque;
		}
		else {
			if (omega > 0)
				omega *= 0.98;
			else
				torque = 0;
			power = (long)omega*(long)torque;
		}
	}

	public final ForgeDirection getBeltDirection() {
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

	public final int getDistanceToTarget() {
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
		return isEmitting || this.isSplitting();
	}

	@Override
	public final PowerSourceList getPowerSources(TileEntityIOMachine io, ShaftMerger caller) {
		if (isEmitting) {
			TileEntityBeltHub tile = (TileEntityBeltHub)worldObj.getTileEntity(source[0], source[1], source[2]);
			return tile.getPowerSources(io, caller);
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

	public final void makeWet() {
		wetTimer = Math.min(wetTimer+3600, 18000); //3 to 15 min
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("emit", isEmitting);

		NBT.setIntArray("tg", target);
		NBT.setIntArray("src", source);

		NBT.setInteger("wet", wetTimer);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isEmitting = NBT.getBoolean("emit");

		source = NBT.getIntArray("src");
		target = NBT.getIntArray("tg");

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

	public final int getTargetX() {
		return target[0];
	}

	public final int getTargetY() {
		return target[1];
	}

	public final int getTargetZ() {
		return target[2];
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

}