/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2018
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.ImmutablePair;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Data.BlockStruct.BlockArray;
import Reika.DragonAPI.Instantiable.Data.Immutable.Coordinate;
import Reika.DragonAPI.Instantiable.Data.Maps.ValueSortedMap;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.PlanetDimensionHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.SolarPlantBlock;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;


public class SolarPlant {

	private static final float TOWER_FALLOFF = 0.72F;
	public static int MAX_TOWER_HEIGHT = 32;
	public static int MAX_TOWER_VALUE = 96;

	private static final TreeMap<Integer, Integer> towerRounding = new TreeMap();

	private final ValueSortedMap<Coordinate, SolarTower> towers = new ValueSortedMap();
	private final HashMap<Coordinate, Coordinate> mirrors = new HashMap();
	private final HashMap<Coordinate, Integer> mirrorLevels = new HashMap();

	//private final SolarSkyCache skyCache = new SolarSkyCache();

	static {
		for (int i = 0; i <= 6; i++) {
			towerRounding.put(i, i);
		}
		for (int i = 8; i <= 12; i += 2) {
			towerRounding.put(i, i);
		}
		for (int i = 16; i <= 24; i += 4) {
			towerRounding.put(i, i);
		}
		towerRounding.put(MAX_TOWER_HEIGHT, MAX_TOWER_HEIGHT);
	}

	public static SolarPlant build(World world, int x, int y, int z) {
		SolarPlant p = new SolarPlant();
		BlockArray blocks = new BlockArray();
		blocks.recursiveAdd(world, x, y, z, BlockRegistry.SOLAR.getBlockInstance());
		HashMap<Coordinate, ImmutablePair<Integer, Integer>> towerLocations = new HashMap();
		ArrayList<Coordinate> li = new ArrayList();
		while (blocks.getSize() > 0) {
			Coordinate c = blocks.getNextAndMoveOn();
			SolarPlantBlock b = (SolarPlantBlock)c.getTileEntity(world);
			b.setPlant(p);
			MachineRegistry m = MachineRegistry.getMachine(world, c.xCoord, c.yCoord, c.zCoord);
			if (m == MachineRegistry.MIRROR) {
				li.add(c);
			}
			else if (m == MachineRegistry.SOLARTOWER) {
				ImmutablePair<Integer, Integer> get = towerLocations.get(c.to2D());
				int val1 = get != null ? get.left.intValue() : Integer.MAX_VALUE;
				int val2 = get != null ? get.right.intValue() : Integer.MIN_VALUE;
				val1 = Math.min(val1, c.yCoord);
				val2 = Math.max(val2, c.yCoord);
				towerLocations.put(c.to2D(), new ImmutablePair(val1, val2));
			}
		}
		for (Coordinate c : towerLocations.keySet()) {
			ImmutablePair<Integer, Integer> ys = towerLocations.get(c);
			int dy = ys.left;
			int h = 0;
			while (MachineRegistry.getMachine(world, c.xCoord, dy, c.zCoord) == MachineRegistry.SOLARTOWER && dy <= ys.left+MAX_TOWER_HEIGHT) {
				dy++;

				if (ReikaWorldHelper.checkForAdjBlock(world, c.xCoord, dy, c.zCoord, MachineRegistry.MIRROR.getBlock(), MachineRegistry.MIRROR.getBlockMetadata()) != null) {
					h = 0;
				}

				h++;
			}
			SolarTower s = p.new SolarTower(c, h, ys.left, ys.right);
			p.towers.put(c, s);
		}
		for (Coordinate c : li) {
			p.addMirror(c, getClosestTower(c, towerLocations.keySet()));
		}
		//ReikaJavaLibrary.pConsole("Added mirrors "+p.mirrors.keySet(), Side.SERVER);
		//ReikaJavaLibrary.pConsole("Added towers "+p.towers.keySet(), Side.SERVER);
		return p;
	}

	private void addMirror(Coordinate c, Coordinate tower) {
		mirrors.put(c, tower);
		Integer get = mirrorLevels.get(c.to2D());
		int val = get != null ? get.intValue() : -1;
		int max = Math.max(val, c.yCoord);
		mirrorLevels.put(c.to2D(), max);
	}

	private static Coordinate getClosestTower(Coordinate c, Collection<Coordinate> locs) {
		double dist = Double.POSITIVE_INFINITY;
		Coordinate closest = null;
		for (Coordinate c2 : locs) {
			double dd = c2.getDistanceTo(c);
			if (dd < dist) {
				dist = dd;
				closest = c2;
			}
		}
		return closest;
	}

	private boolean isHighestMirror(TileEntityMirror te) {
		Integer get = mirrorLevels.get(new Coordinate(te).to2D());
		return get != null ? te.yCoord >= get : false;
	}

	public int towerCount() {
		return towers.size();
	}

	public int mirrorCount() {
		return mirrors.size();
	}

	public int rawTowerBlocks() {
		int ret = 0;
		for (SolarTower val : towers.values())
			ret += val.effectiveHeight;
		return ret;
	}

	public int getEffectiveTowerBlocks() {
		float f = 1;
		int sum = 0;
		for (SolarTower val : towers.values()) {
			sum += val.effectiveHeight*f;
			f *= TOWER_FALLOFF;
		}
		sum = towerRounding.floorEntry(sum).getValue();
		return sum;
	}

	public Coordinate getPrimaryTower() {
		return towers.isEmpty() ? null : towers.getFirst().location;
	}

	public Coordinate getAimingPositionForMirror(TileEntityMirror te) {
		if (towers.isEmpty())
			return null;
		Coordinate c = mirrors.get(new Coordinate(te));
		return c.offset(0, towers.get(c).topBlock, 0);
	}

	public float getOverallBrightness(World world) {
		float f = 0;
		for (Coordinate c : mirrors.keySet()) {
			TileEntity te = c.getTileEntity(world);
			if (te instanceof TileEntityMirror) {
				TileEntityMirror tm = (TileEntityMirror)te;
				if (tm.isFunctional())
					f++;
			}
		}
		f /= this.mirrorCount();
		return f*this.getLightLevel(world)/15F;
	}

	public float getLightLevel(World world) {
		if (world.provider.dimensionId == -1 || world.provider.dimensionId == 1)
			return 0;
		if (world.provider.hasNoSky)
			return 0;
		double sun = (ReikaWorldHelper.getSunIntensity(world, true, 0)*0.8F+0.2F)*PlanetDimensionHandler.getSunIntensity(world);
		if (sun > 0.21) {
			return (int)(15*sun);
		}
		int moon = world.provider.getMoonPhase(world.getWorldInfo().getWorldTime());
		float phase;
		switch(moon) {
			case 0:
				phase = 1;
				break;
			case 1:
			case 7:
				phase = 0.8F;
				break;
			case 2:
			case 6:
				phase = 0.5F;
				break;
			case 3:
			case 5:
				phase = 0.2F;
				break;
			case 4:
				phase = 0.05F;
				break;
			default:
				phase = 0;
		}
		//ReikaJavaLibrary.pConsole(phase);
		return 15*0.2F*phase;
	}

	public int getTowerMultiplier() {
		return Math.min(this.getEffectiveTowerBlocks(), MAX_TOWER_VALUE);
	}

	public void invalidate(World world) {
		for (Coordinate c : mirrors.keySet()) {
			SolarPlantBlock b = (SolarPlantBlock)c.getTileEntity(world);
			b.setPlant(null);
		}
		for (SolarTower s : towers.values()) {
			for (int y = s.bottomBlock; y <= s.topBlock; y++) {
				SolarPlantBlock b = (SolarPlantBlock)s.location.offset(0, y, 0).getTileEntity(world);
				b.setPlant(null);
			}
		}
		towers.clear();
		mirrors.clear();
	}

	private class SolarTower implements Comparable<SolarTower> {

		/** 2D only */
		private final Coordinate location;

		/** Effective since tower blocks with a mirror >= their y coordinate do not count */
		private final int effectiveHeight;
		private final int bottomBlock;
		private final int topBlock;

		private SolarTower(Coordinate c, int h, int bottom, int top) {
			location = c;
			effectiveHeight = h;
			bottomBlock = bottom;
			topBlock = top;
		}

		@Override
		public int hashCode() {
			return location.hashCode() | (effectiveHeight << 24);
		}

		@Override
		public int compareTo(SolarTower o) {
			int ret = -Integer.compare(effectiveHeight, o.effectiveHeight); //to put bigger at the beginning
			return ret != 0 ? ret : Integer.compare(this.hashCode(), o.hashCode());
		}

	}

	public boolean canSeeTheSky(TileEntityMirror te) {
		//return skyCache.canSeeTheSky(world, x, y, z);
		return te.worldObj.canBlockSeeTheSky(te.xCoord, te.yCoord+1, te.zCoord) && this.isHighestMirror(te);
	}

}
