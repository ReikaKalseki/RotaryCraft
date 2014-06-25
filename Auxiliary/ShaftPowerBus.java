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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Instantiable.Data.BlockArray;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.Registry.MachineRegistry;
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
		sb.append("Power Bus Receiving "+this.getInputTorque()+" Nm @ "+this.getSpeed()+" rad/s\n");
		sb.append(this.getInputPower()+"W is being split to "+this.getTotalOutputSides()+" devices\n");
		sb.append("(Power per side is "+this.getInputPower()/this.getTotalOutputSides()+"W)");
		return sb.toString();
	}

	public void removeBlock(TileEntityPowerBus bus) {
		blocks.remove(bus);
		this.rebuild();
	}

	private void rebuild() {
		BlockArray b = new BlockArray();
		int x = hub.xCoord;
		int y = hub.yCoord;
		int z = hub.zCoord;
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
			int dx = x+dir.offsetX;
			int dy = y+dir.offsetY;
			int dz = z+dir.offsetZ;
			MachineRegistry m = MachineRegistry.getMachine(hub.worldObj, dx, dy, dz);
			if (m == MachineRegistry.POWERBUS) {
				b.recursiveAddWithMetadata(hub.worldObj, dx, dy, dz, m.getBlockID(), m.getMachineMetadata());
			}
		}
		Iterator<TileEntityPowerBus> it = blocks.iterator();
		while (it.hasNext()) {
			TileEntityPowerBus te = it.next();
			if (b.hasBlock(te.xCoord, te.yCoord, te.zCoord)) {

			}
			else {
				te.clearBus();
				it.remove();
			}
		}
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

	public List<TileEntityPowerBus> getBlocks() {
		return ReikaJavaLibrary.copyList(blocks);
	}

	public int getSize() {
		return blocks.size();
	}

}
