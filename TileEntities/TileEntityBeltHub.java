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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.PowerSourceList;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.relauncher.Side;

public class TileEntityBeltHub extends TileEntityPowerReceiver implements PowerGenerator {

	private boolean isEmitting;

	private int[] source = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
	private int[] target = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSidesDefault(world, x, y, z, meta);
		//isEmitting = true;
		if (isEmitting) {
			this.copyPower();
		}
		else {
			this.getPower(false, true);
		}
	}

	public void reset() {
		source = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		target = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
	}

	public boolean canConnect(int x, int y, int z) {
		int dx = x-xCoord;
		int dy = y-yCoord;
		int dz = z-zCoord;

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
			ReikaJavaLibrary.pConsole(xi+", "+yi+", "+zi+" -> "+id, Side.SERVER);
			if (!ReikaWorldHelper.softBlocks(worldObj, xi, yi, zi)) {
				return false;
			}
		}
		return true;
	}

	public boolean shouldRenderBelt() {
		if (isEmitting) {
			return MachineRegistry.getMachine(worldObj, source[0], source[1], source[2]) == MachineRegistry.BELT;
		}
		else {
			//return MachineRegistry.getMachine(worldObj, target[0], target[1], target[2]) == MachineRegistry.BELT;
			return false;
		}
	}

	public boolean setTarget(int x, int y, int z) {
		if (!this.canConnect(x, y, z))
			return false;
		target[0] = x;
		target[1] = y;
		target[2] = z;
		return true;
	}

	public boolean setSource(int x, int y, int z) {
		if (!this.canConnect(x, y, z))
			return false;
		source[0] = x;
		source[1] = y;
		source[2] = z;
		return true;
	}

	public int getMaxTorque() {
		return 8192;
	}

	public int getMaxSmoothSpeed() {
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
		if (m == MachineRegistry.BELT) {
			TileEntityBeltHub tile = (TileEntityBeltHub)worldObj.getBlockTileEntity(source[0], source[1], source[2]);
			omega = this.getSlipOmega(tile.omega);
			torque = Math.min(this.getMaxTorque(), tile.torque);
			power = tile.power;
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
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.BELT.ordinal();
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
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBT.setBoolean("emit", isEmitting);

		NBT.setIntArray("tg", target);
		NBT.setIntArray("src", source);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		isEmitting = NBT.getBoolean("emit");

		source = NBT.getIntArray("src");
		target = NBT.getIntArray("tg");
	}

}
