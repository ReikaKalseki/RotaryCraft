/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.ChromatiCraft.API.WorldRift;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.PowerTracker;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.Auxiliary.Interfaces.PowerSourceTracker;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

public class PowerSourceList implements PowerTracker {

	private ArrayList<PowerGenerator> engines = new ArrayList<PowerGenerator>();
	private ShaftMerger caller;
	private ArrayList<ShaftMerger> mergers = new ArrayList();
	private boolean isLooping = false;

	public long getMaxGennablePower() {
		long pwr = 0;

		for (int i = 0; i < engines.size(); i++) {
			pwr += engines.get(i).getMaxPower();
		}

		return pwr;
	}

	public long getRealMaxPower() {
		long pwr = 0;

		for (int i = 0; i < engines.size(); i++) {
			pwr += engines.get(i).getCurrentPower();
		}
		return pwr;
	}

	public PowerSourceList addSource(PowerGenerator te) {
		engines.add(te);
		return this;
	}

	public boolean isLooping() {
		return isLooping;
	}

	public static PowerSourceList getAllFrom(World world, ForgeDirection dir, int x, int y, int z, PowerSourceTracker io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();

		TileEntity tile = world.getTileEntity(x, y, z);

		if (caller != null) {
			if (pwr.passesThrough(caller) || (tile instanceof ShaftMerger && pwr.passesThrough((ShaftMerger)tile)) || tile == caller) {
				pwr.isLooping = true;
				caller.onPowerLooped(pwr);
				return pwr;
			}
		}

		if (tile instanceof ShaftMerger) {
			pwr.mergers.add((ShaftMerger)tile);
			if (tile == caller) {
				pwr.isLooping = true;
			}
		}

		try {
			if (tile instanceof TileEntityIOMachine) {
				TileEntityIOMachine te = (TileEntityIOMachine)tile;
				if (!te.isWritingTo(io) && !te.isWritingTo2(io)) {
					return pwr;
				}
				if (te.isReadingFrom(io) || te.isReadingFrom2(io) || te.isReadingFrom3(io) || te.isReadingFrom4(io)) {
					return pwr;
				}
				pwr.addAll(te.getPowerSources(io, caller));
			}
			else if (tile instanceof PowerSourceTracker) {
				pwr.addAll(((PowerSourceTracker)tile).getPowerSources(io, caller));
			}
			else if (tile instanceof PowerGenerator) {
				pwr.addSource((PowerGenerator)tile);
			}
			else if (tile instanceof WorldRift) {
				WorldLocation loc = ((WorldRift)tile).getLinkTarget();
				if (loc != null) {
					int dx = loc.xCoord+dir.offsetX;
					int dy = loc.yCoord+dir.offsetY;
					int dz = loc.zCoord+dir.offsetZ;
					return getAllFrom(world, dir, dx, dy, dz, io, caller);
				}
			}
			pwr.caller = caller;

			if (pwr.passesThrough(caller)) {
				pwr.isLooping = true;
			}

			return pwr;
		}
		catch (StackOverflowError e) {
			//e.printStackTrace();
			ReikaJavaLibrary.pConsole("PowerSourceList SOE!");
			return pwr;
		}
	}

	public void addAll(PowerSourceList pwr) {
		for (int i = 0; i < pwr.engines.size(); i++) {
			PowerGenerator te = pwr.engines.get(i);
			this.addSource(te);
		}
	}

	@Override
	public String toString() {
		if (engines.isEmpty()) {
			return "[None]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (int i = 0; i < engines.size(); i++) {
			sb.append(engines.get(i));
			if (i < engines.size()-1)
				sb.append("\n");
		}
		return sb.toString();
	}

	public boolean contains(PowerGenerator te) {
		return engines.contains(te);
	}

	public boolean calledFrom(ShaftMerger sm) {
		return caller == sm;
	}

	public boolean passesThrough(ShaftMerger sm) {
		return mergers.contains(sm);
	}

	public boolean isEngineSpam() {
		/*
		if (engines.size() < 8)
			return false;
		long last = engines.get(0).getMaxPower();
		Class c = engines.get(0).getClass();
		for (int i = 1; i < engines.size(); i++) {
			long pow = engines.get(i).getMaxPower();
			Class c2 = engines.get(i).getClass();
			if (pow != last || c2 != c)
				return false;
		}
		if (sum = )
			return true;*/
		if (engines.isEmpty())
			return false;
		long sum = this.getMaxGennablePower();
		long avg = sum/engines.size();
		return avg > 0 && sum/avg > 4;
	}

	public static PowerSourceList combine(PowerSourceList in1, PowerSourceList in2, ShaftMerger caller) {
		PowerSourceList psl = new PowerSourceList();
		psl.engines.addAll(in1.engines);
		psl.engines.addAll(in2.engines);

		psl.mergers.addAll(in1.mergers);
		psl.mergers.addAll(in2.mergers);

		if (psl.mergers.contains(caller)) {
			psl.isLooping = true;
		}

		psl.caller = caller;

		return psl;
	}

}
