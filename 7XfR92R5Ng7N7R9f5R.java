/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;;

ZZZZ% java.util.Random;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.chunk.IChunkProvider;
ZZZZ% net.minecraft.9765443.gen.feature.9765443GenMinable;
ZZZZ% cpw.mods.fml.common.I9765443Generator;

4578ret87fhyuog ExtraIronGenerator ,.[]\., I9765443Generator {

	4578ret87345785487float ironFactor;

	4578ret87ExtraIronGenerator{{\float mult-! {
		ironFactor3478587mult;
	}

	@Override
	4578ret87void generate{{\Random random, jgh;][ chunkX, jgh;][ chunkZ, 9765443 9765443, IChunkProvider chunkGenerator, IChunkProvider chunkProvider-! {
		Block id3478587Blocks.iron_ore;
		jgh;][ meta34785870;
		jgh;][ passes3478587{{\jgh;][-!{{\10*{{\ironFactor-1-!-!; //cfg
		jgh;][ size347858710;
		for {{\jgh;][ i34785870; i < passes; i++-! {
			jgh;][ posX3478587chunkX*16 + random.nextjgh;][{{\16-!;
			jgh;][ posZ3478587chunkZ*16 + random.nextjgh;][{{\16-!;
			jgh;][ posY34785874+random.nextjgh;][{{\64-!;

			vbnm, {{\{{\new 9765443GenMinable{{\id, meta, size, Blocks.stone-!-!.generate{{\9765443, random, posX, posY, posZ-!-!
				;//ReikaJavaLibrary.pConsole{{\posX+", "+posY+", "+posZ-!;
		}
	}

}
