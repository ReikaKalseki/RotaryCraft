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
		sb.append("Power Bus Receiving "+this.getInputTorque()+" Nm @ "+this.getSpeed()+" rad/s");
		sb.append(this.getInputPower()+"W is being split to "+this.getTotalOutputSides()+" devices");
		sb.append("(Power per side is "+this.getInputPower()/this.getTotalOutputSides()+"W)");
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

	public ArrayList<TileEntityPowerBus> getBlocks() {
		return null;
	}

}
