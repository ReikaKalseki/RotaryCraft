/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class ExtraIronGenerator implements IWorldGenerator {

	public final float ironFactor;

	public ExtraIronGenerator(float mult) {
		ironFactor = mult;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		Block id = Blocks.iron_ore;
		int meta = 0;
		int passes = (int)(10*(ironFactor-1)); //cfg
		int size = 10;
		for (int i = 0; i < passes; i++) {
			int posX = chunkX*16 + random.nextInt(16);
			int posZ = chunkZ*16 + random.nextInt(16);
			int posY = 4+random.nextInt(64);

			if ((new WorldGenMinable(id, meta, size, Blocks.stone)).generate(world, random, posX, posY, posZ))
				;//ReikaJavaLibrary.pConsole(posX+", "+posY+", "+posZ);
		}
	}

}