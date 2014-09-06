package Reika.RotaryCraft.TileEntities.Piping;

import net.minecraft.util.IIcon;
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
	/*
	@Override
	public IIcon getGlassIcon() {
		return BlockRegistry.BLASTGLASS.getBlockInstance().getIcon(0, 0);
	}*/

}
