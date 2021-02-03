package Reika.RotaryCraft.API.Interfaces;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface GPRReactive {

	/** This will be fired every tick the GPR scans the block, not just once! */
	public void onScanned(World world, int x, int y, int z, Block b, int meta);

}
