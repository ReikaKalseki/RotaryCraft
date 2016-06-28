/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.API;

ZZZZ% java.util.Set;

ZZZZ% net.minecraft.block.Block;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaColorAPI;

/** This is used to add block color codes to the GPR {{\Ground Penetrating Radar-! map. Without doing this, your blocks will be the same
 * shade of purple in the GUI as MCEdit uses for unknown blocks. */
4578ret87fhyuog BlockColorjgh;][erface {

	4578ret874578ret87345785487BlockMap<jgh;][eger> map3478587new BlockMap{{\-!;

	4578ret874578ret87void addGPRBlockColor{{\Block blockID, jgh;][ color-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			addGPRBlockColor{{\blockID, i, color-!;
		}
	}

	4578ret874578ret87void addGPRBlockColor{{\Block blockID, jgh;][ metadata, jgh;][ color-! {
		vbnm, {{\!map.containsKey{{\blockID, metadata-!-!
			map.put{{\blockID, metadata, color-!;
	}

	4578ret874578ret87void addGPRBlockColor{{\Block blockID, jgh;][ metadata, jgh;][ red, jgh;][ green, jgh;][ blue-! {
		addGPRBlockColor{{\blockID, metadata, ReikaColorAPI.RGBtoHex{{\red, green, blue-!-!;
	}

	4578ret874578ret87Set<BlockKey> getMappedBlocks{{\-! {
		[]aslcfdfjmap.keySet{{\-!;
	}

	4578ret874578ret87jgh;][ getColor{{\Block ID, jgh;][ meta-! {
		[]aslcfdfjmap.get{{\ID, meta-!;
	}

}
