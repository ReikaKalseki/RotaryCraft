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
import Reika.DragonAPI.Instantiable.Data.WorldLocation;
import Reika.RotaryCraft.API.Power.PowerGenerator;
import Reika.RotaryCraft.API.Power.ShaftMerger;
import Reika.RotaryCraft.API.Power.ShaftPowerEmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

public class PowerSourceList {

	//but what about ShaftPowerEmitters?
	private ArrayList<PowerGenerator> engines = new ArrayList<PowerGenerator>();
	private ShaftMerger caller;
	private ArrayList<ShaftMerger> mergers = new ArrayList();

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

	public static PowerSourceList getAllFrom(World world, ForgeDirection dir, int x, int y, int z, TileEntityIOMachine io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();

		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof ShaftMerger) {
			if (pwr.mergers.contains(tile)) {
				pwr.engines.clear();
				pwr.mergers.clear();
				return pwr;
			}
			pwr.mergers.add((ShaftMerger)tile);
		}

		try {
			//ReikaJavaLibrary.pConsole(tile, Side.SERVER, io.xCoord == -1011);
			if (tile instanceof TileEntityIOMachine) {
				TileEntityIOMachine te = (TileEntityIOMachine)tile;
				if (te.isReadingFrom(io)) {
					//ReikaJavaLibrary.pConsole(0, Side.SERVER, io.xCoord == -1011);
					return pwr;
				}
				if (te.isReadingFrom2(io)) {
					//ReikaJavaLibrary.pConsole(1, Side.SERVER, io.xCoord == -1011);
					return pwr;
				}
				if (te.isReadingFrom3(io)) {
					//ReikaJavaLibrary.pConsole(2, Side.SERVER, io.xCoord == -1011);
					return pwr;
				}
				if (te.isReadingFrom4(io)) {
					//ReikaJavaLibrary.pConsole(3, Side.SERVER, io.xCoord == -1011);
					return pwr;
				}
				//ReikaJavaLibrary.pConsole(4+": "+te, Side.SERVER, io.xCoord == -1011);
				pwr.addAll(te.getPowerSources(io, caller));
			}
			else if (tile instanceof PowerGenerator) {
				pwr.addSource((PowerGenerator)tile);
			}
			else if (tile instanceof ShaftPowerEmitter) {
				//return pwr; //for now
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
			return pwr;
		}
		catch (StackOverflowError e) {
			e.printStackTrace();
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

		psl.caller = caller;
		return psl;
	}

}
