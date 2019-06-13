package Reika.RotaryCraft.Auxiliary;

import java.util.HashMap;

import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;

@Deprecated
public class SolarSkyCache {

	//public final int xPosition;
	//public final int zPosition;

	//private boolean needsCalculation;
	/*
	public SolarSkyCache(int x, int z) {
		xPosition = x;
		zPosition = z;
	}*/

	private final HashMap<Coordinate, Integer> data = new HashMap();

	public void markDirty(int x, int z) {
		//needsCalculation = true;
		data.remove(new Coordinate(x, 0, z));
	}

	public boolean canSeeTheSky(World world, int x, int y, int z) {
		Coordinate key = new Coordinate(x, y, z).to2D();
		Integer cache = data.get(key);
		if (cache != null) {
			return y >= cache.intValue();
		}
		int ref = this.calculateSkyAccess(world, x, y, z);
		data.put(key, ref);
		return y >= ref;
	}

	private int calculateSkyAccess(World world, int x, int y, int z) {
		int dy = 256;
		while (dy > 0 && this.isTransparent(world, x, dy-1, z)) {
			dy--;
		}
		return dy;
	}

	private boolean isTransparent(World world, int x, int y, int z) {
		/*
		Block b = world.getBlock(x, y, z);
		if (b == Blocks.air || b.isAir(world, x, y, z))
			return true;
		if (MachineRegistry.getMachine(world, x, y, z) == MachineRegistry.MIRROR)
			;//return false;
		 */
		return false;
	}

}
