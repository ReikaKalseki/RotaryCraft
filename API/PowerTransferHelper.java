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
import net.minecraftforge.common.ForgeDirection;
import Reika.ChromatiCraft.API.SpaceRift;
import Reika.DragonAPI.Instantiable.WorldLocation;

public class PowerTransferHelper {

	public static boolean checkPowerFrom(TileEntity tile, ForgeDirection dir) {
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		int dx = x+dir.offsetX;
		int dy = y+dir.offsetY;
		int dz = z+dir.offsetZ;
		TileEntity toCheck = tile.worldObj.getBlockTileEntity(dx, dy, dz);
		if (toCheck instanceof SpaceRift) {
			SpaceRift sr = (SpaceRift)toCheck;
			WorldLocation loc = sr.getLinkTarget();
			if (loc != null) {
				return checkPowerFrom(loc.getTileEntity(), dir);
			}
		}
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
			if (checkPowerFrom(tile, dir)) {
				return true;
			}
		}
		return false;
	}

}
