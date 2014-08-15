/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.ChromatiCraft.API.SpaceRift;
import Reika.DragonAPI.Instantiable.WorldLocation;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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

		try {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileEntityIOMachine) {
				TileEntityIOMachine te = (TileEntityIOMachine)tile;
				if (te.isReadingFrom(io))
					return pwr;
				if (te.isReadingFrom2(io))
					return pwr;
				if (te.isReadingFrom3(io))
					return pwr;
				if (te.isReadingFrom4(io))
					return pwr;
				pwr.addAll(te.getPowerSources(io, caller));
			}
			else if (tile instanceof PowerGenerator) {
				pwr.addSource((PowerGenerator)tile);
			}
			else if (tile instanceof ShaftPowerEmitter) {
				//return pwr; //for now
			}
			else if (tile instanceof SpaceRift) {
				WorldLocation loc = ((SpaceRift)tile).getLinkTarget();
				if (loc != null) {
					int dx = loc.xCoord+dir.offsetX;
					int dy = loc.yCoord+dir.offsetY;
					int dz = loc.zCoord+dir.offsetZ;
					return getAllFrom(world, dir, dx, dy, dz, io, caller);
				}
			}

			if (tile instanceof ShaftMerger) {
				pwr.mergers.add((ShaftMerger)tile);
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

}