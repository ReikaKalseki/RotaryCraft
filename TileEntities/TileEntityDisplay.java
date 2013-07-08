/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.ArrayList;

import net.minecraft.world.World;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDisplay extends TileEntityPowerReceiver {

	private int scroll;
	private ArrayList<String> message = new ArrayList<String>();
	public static final int displayHeight = 12; //in lines
	public static final int displayWidth = 80; //in chars

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.DISPLAY.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (message.size() == 0) {
			message.add("Hello World");
			message.add("Line 2");
			message.add("Testing 3");
			message.add(this.getName());
			message.add(placer);
		}
	}

	public boolean hasList() {
		if (message == null)
			return false;
		if (message.size() <= 0)
			return false;
		return true;
	}

	public String getMessageLine(int line) {
		return message.get(line);
	}

	public int getMessageLength() {
		return message.size();
	}

	public int getScrollPos() {
		return scroll;
	}
}
