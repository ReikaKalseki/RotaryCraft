/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBedrockPipe extends TileEntityPipe {

	@Override
	public int getMaxPressure() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMaxTemperature() {
		return 5000;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BEDPIPE;
	}

	@Override
	public IIcon getBlockIcon() {
		return BlockRegistry.DECO.getBlockInstance().getIcon(0, 4);
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return true;
	}
	/*
	@Override
	public IIcon getGlassIcon() {
		return BlockRegistry.BLASTGLASS.getBlockInstance().getIcon(0, 0);
	}*/

}
