/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;

public class LuaGetBlockAtPos extends LuaMethod {

	public LuaGetBlockAtPos() {
		super("getBlockAtPos", TileEntityGPR.class);
	}

	@Override
	protected Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		TileEntityGPR tg = (TileEntityGPR)te;
		int dh = ((Double)args[0]).intValue();
		int dv = ((Double)args[1]).intValue();
		int x = tg.blocks[0].length/2+dh;
		int y = dv;
		int[] lim = tg.getBounds();
		if (x < lim[0] || x > lim[1] || y < 0 || y > tg.blocks.length) //out of bounds
			return null;
		BlockKey bk = tg.blocks[x][y];
		return new Object[]{bk.blockID, bk.metadata};
	}

	@Override
	public String getDocumentation() {
		return "Returns the block at a given position.\nArgs: horizontal offset, depth\nReturns: Block ID, metadata";
	}

	@Override
	public String getArgsAsString() {
		return "int offset, int depth";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.ARRAY;
	}

}
