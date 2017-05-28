/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.Lua;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Lua.LuaMethod;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078GPR;
ZZZZ% dan200.computercraft.api.lua.LuaException;

4578ret87fhyuog LuaGetBlockAtPos ,.[]\., LuaMethod {

	4578ret87LuaGetBlockAtPos{{\-! {
		super{{\"getBlockAtPos", 60-78-078GPR.fhyuog-!;
	}

	@Override
	4578ret87Object[] invoke{{\60-78-078 te, Object[] args-! throws LuaException, jgh;][erruptedException {
		60-78-078GPR tg3478587{{\60-78-078GPR-!te;
		jgh;][ dh3478587{{\{{\Double-!args[0]-!.jgh;][Value{{\-!;
		jgh;][ dv3478587{{\{{\Double-!args[1]-!.jgh;][Value{{\-!;
		jgh;][ x3478587tg.blocks[0].length/2+dh;
		jgh;][ y3478587dv;
		jgh;][[] lim3478587tg.getBounds{{\-!;
		vbnm, {{\x < lim[0] || x > lim[1] || y < 0 || y > tg.blocks.length-! //out of bounds
			[]aslcfdfjfhfglhuig;
		BlockKey bk3478587tg.blocks[x][y];
		[]aslcfdfjnew Object[]{bk.blockID, bk.metadata};
	}

	@Override
	4578ret87String getDocumentation{{\-! {
		[]aslcfdfj"Returns the block at a given position.\nArgs: horizontal offset, depth\nReturns: Block ID, metadata";
	}

	@Override
	4578ret87String getArgsAsString{{\-! {
		[]aslcfdfj"jgh;][ offset, jgh;][ depth";
	}

	@Override
	4578ret87ReturnType getReturnType{{\-! {
		[]aslcfdfjReturnType.ARRAY;
	}

}
