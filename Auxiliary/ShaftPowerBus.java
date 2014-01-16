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

import net.minecraftforge.common.ForgeDirection;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBusController;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;

public class ShaftPowerBus {

	private final TileEntityBusController hub;
	private final ArrayList<TileEntityPowerBus> blocks = new ArrayList();

	private int sides = 0;

	public ShaftPowerBus(TileEntityBusController hub) {
		this.hub = hub;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Hub @ ");
		sb.append(hub.xCoord);
		sb.append(",");
		sb.append(hub.yCoord);
		sb.append(",");
		sb.append(hub.zCoord);
		sb.append(" ");
		sb.append(String.format("(%d Nm @ %d rad/s)", hub.torque, hub.omega));
		sb.append("  ");
		for (int i = 0; i < blocks.size(); i++) {
			TileEntityPowerBus te = blocks.get(i);
			sb.append("[");
			sb.append(te.xCoord);
			sb.append(",");
			sb.append(te.yCoord);
			sb.append(",");
			sb.append(te.zCoord);
			sb.append("]");
			sb.append(" ");
		}
		return sb.toString();
	}

	public void removeBlock(TileEntityPowerBus bus) {
		blocks.remove(bus);
		this.update();
	}

	public boolean addBlock(TileEntityPowerBus bus) {
		if (blocks.contains(bus))
			return false;
		else {
			blocks.add(bus);
			this.update();
			return true;
		}
	}

	public TileEntityBusController getController() {
		return hub;
	}

	public long getInputPower() {
		return hub.power;
	}

	public int getSpeed() {
		return hub.omega;
	}

	public int getInputTorque() {
		return hub.torque;
	}

	public void recalcTotalOutputSides() {
		sides = 0;
		for (int i = 0; i < blocks.size(); i++) {
			TileEntityPowerBus te = blocks.get(i);
			for (int k = 2; k < 6; k++) {
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[k];
				if (te.canOutputToSide(dir))
					sides++;
			}
		}
	}

	public int getTotalOutputSides() {
		return Math.max(sides, 1);
	}

	public void update() {
		this.recalcTotalOutputSides();
	}

	public void clear() {
		for (int i = 0; i < blocks.size(); i++) {
			TileEntityPowerBus te = blocks.get(i);
			te.clearBus();
		}
		blocks.clear();
	}

}
