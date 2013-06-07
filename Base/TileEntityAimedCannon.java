/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaPhysicsHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;

public abstract class TileEntityAimedCannon extends TileEntityPowerReceiver implements RangedEffect {

	public float theta;
	protected double[] target = new double[4];

	protected EntityLiving closestMob;
	protected double voffset = -0.125;

	public static final int MAXLOWANGLE = -10;
	/** Up/down angle */
	protected int dir = 1;

	public final double[] getTarget() {
		return target;
	}

	public void openChest() {}

	public void closeChest() {}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public void updateTileEntity() {
		super.updateTileEntity();
		tickcount++;
		switch(this.getBlockMetadata()) {
		case 0:
			this.getPowerBelow();
			dir = 1;
			break;
		case 1:
			this.getPowerAbove();
			dir = -1;
			break;
		}
		if (power < MINPOWER)
			return;
		target = this.getTarget(worldObj, xCoord, yCoord, zCoord);
		this.adjustAim(worldObj, xCoord, yCoord, zCoord, target);
	}

	public boolean isAimingAtTarget(World world, int x, int y, int z, double[] t) {
		double[] tg = ReikaPhysicsHelper.cartesianToPolar(x-t[0], y-t[1], z-t[2]);
		tg[1] = Math.abs(tg[1])-90;
		float phi2 = phi;
		while (phi2 < 0)
			phi2 += 360;
		while (phi2 >= 360)
			phi2 -= 360;
		//ReikaJavaLibrary.pConsole("PHI: "+phi2+" THETA: "+theta+" for "+tg[2]+", "+tg[1]);
		if (tg[2] - phi2 > 180)
			tg[2] -= 360;
		if (!ReikaMathLibrary.approxr(theta, tg[1], 3))
			return false;
		if (!ReikaMathLibrary.approxr(phi2, tg[2], 3))
			return false;
		return true;
	}

	public void adjustAim(World world, int x, int y, int z, double[] t) {
		if (t[3] != 1)
			return;
		double dx = x+0.5-t[0];
		double dy = y+0.5-t[1];
		double dz = z+0.5-t[2];
		double[] tg = ReikaPhysicsHelper.cartesianToPolar(dx, dy, dz);
		tg[1] = Math.abs(tg[1])-90+0.25;
		//ReikaJavaLibrary.pConsole("PHI: "+phi+" THETA: "+theta+" for "+tg[2]+", "+tg[1]);
		if (tg[2] - phi > 180)
			tg[2] -= 360;
		float movespeed = 1F;
		if (phi < tg[2])
			phi += movespeed*2;
		if (phi > tg[2])
			phi -= movespeed*2;
		if (theta < tg[1])
			theta += movespeed;
		if (theta > tg[1])
			theta -= movespeed;
		if (theta < MAXLOWANGLE && dir == 1)
			theta = MAXLOWANGLE;
		if (theta > -MAXLOWANGLE && dir == -1)
			theta = MAXLOWANGLE;
	}

	public abstract boolean hasAmmo();

	protected abstract double[] getTarget(World world, int x, int y, int z);

	public abstract void fire(World world, double[] xyz);

	@Override
	public final void animateWithTick(World world, int x, int y, int z) {}

	protected abstract double randomOffset();

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setFloat("theta", theta);
		NBT.setInteger("direction", dir);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		theta = NBT.getFloat("theta");
		dir = NBT.getInteger("direction");
	}

	@Override
	public final AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

}
