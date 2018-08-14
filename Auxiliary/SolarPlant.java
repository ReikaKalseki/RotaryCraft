package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.world.World;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;


public class SolarPlant {

	private final ArrayList<SolarTower> towers = new ArrayList();
	private final HashSet<Coordinate> mirrors = new HashSet();

	public static SolarPlant build(World world, int x, int y, int z) {
		solarBlocks.recursiveAdd(world, x, y, z, this.getTileEntityBlockID());
		numberMirrors = solarBlocks.getSize();
		while (solarBlocks.getSize() > 0) {
			Coordinate c = solarBlocks.getNextAndMoveOn();
			MachineRegistry m = MachineRegistry.getMachine(world, c.xCoord, c.yCoord, c.zCoord);
			if (m == MachineRegistry.MIRROR) {
				TileEntityMirror te = (TileEntityMirror)world.getTileEntity(c.xCoord, c.yCoord, c.zCoord);
				te.targetloc = new Coordinate(x, y, z);
				float light = te.getLightLevel()*te.getAimingAccuracy();
				lightMultiplier += light;
			}
			else numberMirrors--;
		}
	}

	public int towerCount() {
		return towers.size();
	}

	public int rawTowerBlocks() {
		int ret = 0;
		for (SolarTower val : towers)
			ret += val.effectiveHeight;
		return ret;
	}

	public int getEffectiveTowerBlocks() {
		float f = 1;
		int sum = 0;
		for (SolarTower val : towers) {
			sum += val.effectiveHeight*f;
			f *= 0.72;
		}
		return sum;
	}

	public Coordinate getPrimaryTower() {
		return towers.isEmpty() ? null : towers.get(0).location;
	}

	private class SolarTower implements Comparable<SolarTower> {

		private final Coordinate location;
		/** Effective since tower blocks with a mirror >= their y coordinate do not count */
		private int effectiveHeight;

		private SolarTower(Coordinate c) {
			location = c;
		}

		@Override
		public int hashCode() {
			return location.hashCode();
		}

		@Override
		public int compareTo(SolarTower o) {
			int ret = Integer.compare(effectiveHeight, o.effectiveHeight);
			return ret != 0 ? ret : Integer.compare(this.hashCode(), o.hashCode());
		}

	}

}
