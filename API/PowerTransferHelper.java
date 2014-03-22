/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class PowerTransferHelper {

	public static boolean checkPowerFrom(TileEntity tile, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		return checkPowerFrom(tile, world.getBlockTileEntity(x, y, z));
	}

	public static boolean checkPowerFrom(TileEntity tile, TileEntity toCheck) {
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		if (toCheck instanceof PowerGenerator || toCheck instanceof IOMachine) {
			if (toCheck instanceof IOMachine) {
				int wx = ((IOMachine) toCheck).getWriteX();
				int wy = ((IOMachine) toCheck).getWriteY();
				int wz = ((IOMachine) toCheck).getWriteZ();
				int wx2 = ((IOMachine) toCheck).getWriteX2();
				int wy2 = ((IOMachine) toCheck).getWriteY2();
				int wz2 = ((IOMachine) toCheck).getWriteZ2();
				if ((wx == x && wy == y && wz == z) || (wx2 == x && wy2 == y && wz2 == z)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				int wx = ((PowerGenerator) toCheck).getEmittingX();
				int wy = ((PowerGenerator) toCheck).getEmittingY();
				int wz = ((PowerGenerator) toCheck).getEmittingZ();
				if (wx == x && wy == y && wz == z) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else {
			return false;
		}
	}

	public static boolean checkPowerFromAllSides(TileEntity tile, boolean vertical) {
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		for (int i = vertical ? 0 : 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			if (checkPowerFrom(tile, tile.worldObj, dx, dy, dz)) {
				return true;
			}
		}
		return false;
	}

}
