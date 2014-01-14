/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Surveying;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityCaveFinder extends TileEntityPowerReceiver implements RangedEffect {

	private int[] src = new int[3];
	private int rendermode = 0;
	public String owner;
	public boolean on;
	private boolean[][][] points = new boolean[this.getRange()*2+1][this.getRange()*2+1][this.getRange()*2+1];
	private boolean needsCalc = true;

	public TileEntityCaveFinder() {
		this.setSrc(xCoord, yCoord, zCoord);
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

		if (needsCalc)
			this.calculatePoints();

		//ReikaJavaLibrary.pConsole(Arrays.deepToString(points));
	}

	private void calculatePoints() {
		int r = this.getRange();
		for (int i = -r; i <= r; i++) {
			for (int j = -r; j <= r; j++) {
				for (int k = -r; k <= r; k++) {
					int x = this.getSourceX()+i;
					int y = this.getSourceY()+j;
					int z = this.getSourceZ()+k;
					if (ReikaWorldHelper.cornerHasAirAdjacent(worldObj, x, y, z)) {
						points[i+r][j+r][k+r] = true;
					}
					else
						points[i+r][j+r][k+r] = false;
				}
			}
		}
		needsCalc = false;
	}

	public boolean hasPointAt(int x, int y, int z) {
		int r = this.getRange();
		int ix = x-this.getSourceX();
		int iy = y-this.getSourceY();
		int iz = z-this.getSourceZ();
		try {
			return points[ix+r][iy+r][iz+r];
		}
		catch (Exception e) {
			return false;
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
		return Math.max(4, ConfigRegistry.CAVEFINDERRANGE.getValue());
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
		needsCalc = true;
	}

	public void moveSrc(int num, ForgeDirection dir) {
		switch(dir) {
		case DOWN:
			src[1] -= num;
			break;
		case UP:
			src[1] += num;
			break;
		case WEST:
			src[0] -= num;
			break;
		case EAST:
			src[0] += num;
			break;
		case NORTH:
			src[2] -= num;
			break;
		case SOUTH:
			src[2] += num;
			break;
		default:
			break;
		}
		needsCalc = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setIntArray("Source", src);
		NBT.setBoolean("calc", needsCalc);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		src = NBT.getIntArray("Source");
		needsCalc = NBT.getBoolean("calc");
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
