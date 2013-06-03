/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.Auxiliary.EnumLook;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Models.ModelCave;

public class TileEntityCaveFinder extends TileEntityPowerReceiver implements RangedEffect {

	private int[] src = new int[3];
	int rendermode = 0;
	public String owner;
	public boolean on;

	public TileEntityCaveFinder() {
		this.setSrc(xCoord, yCoord, zCoord);
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelCave();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		if (power < MINPOWER) {
			on = false;
			return;
		}
		on = true;
		if (src[0] == 0 && src[1] == 0 && src[2] == 0)
			this.setSrc(x, y, z);
		if (rendermode == 0) {

		}
		else if (rendermode == 1) {
			EntityPlayer ep = world.getClosestPlayer(x, y, z, -1);
			if (ep == null)
				return;
			int px = (int)ep.posX;
			int py = (int)ep.posY;
			int pz = (int)ep.posZ;
			this.setSrc(px, py, pz);
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}
	/*
	@Override
	public double getMaxRenderDistanceSquared() {
		return 65536D;
	}*/

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	public int getRange() {
		return RotaryConfig.cavefinderrange;
	}

	public int getSourceX() {
		return src[0];
	}

	public int getSourceY() {
		return src[1];
	}

	public int getSourceZ() {
		return src[2];
	}

	public void setSrc(int x, int y, int z) {
		src[0] = x;
		src[1] = y;
		src[2] = z;
	}

	public void moveSrc(int num, EnumLook dir) {
		switch(dir) {
		case DOWN:
			src[1] -= num;
			break;
		case UP:
			src[1] += num;
			break;
		case MINX:
			src[0] -= num;
			break;
		case PLUSX:
			src[0] += num;
			break;
		case MINZ:
			src[2] -= num;
			break;
		case PLUSZ:
			src[2] += num;
			break;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setIntArray("Source", src);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		src = NBT.getIntArray("Source");
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.CAVESCANNER.ordinal();
	}

	@Override
	public int getMaxRange() {
		return 128;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
