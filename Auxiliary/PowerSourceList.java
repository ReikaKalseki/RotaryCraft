/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMerger;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

public class PowerSourceList {

	//but what about ShaftPowerEmitters?
	private ArrayList<PowerGenerator> engines = new ArrayList<PowerGenerator>();

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

	public static PowerSourceList getAllFrom(World world, int x, int y, int z, TileEntityIOMachine io, ShaftMerger caller) {
		PowerSourceList pwr = new PowerSourceList();
		try {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if (tile instanceof TileEntityIOMachine) {
				TileEntityIOMachine te = (TileEntityIOMachine)tile;
				if (te.readx == io.xCoord && te.ready == io.yCoord && te.readz == io.zCoord)
					return pwr;
				if (te.readx2 == io.xCoord && te.ready2 == io.yCoord && te.readz2 == io.zCoord)
					return pwr;
				if (te.readx3 == io.xCoord && te.ready3 == io.yCoord && te.readz3 == io.zCoord)
					return pwr;
				if (te.readx4 == io.xCoord && te.ready4 == io.yCoord && te.readz4 == io.zCoord)
					return pwr;
				pwr.addAll(te.getPowerSources(io, caller));
				return pwr;
			}
			else if (tile instanceof PowerGenerator) {
				return pwr.addSource((PowerGenerator)tile);
			}
			else if (tile instanceof ShaftPowerEmitter) {
				return pwr; //for now
			}
			else
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

}
