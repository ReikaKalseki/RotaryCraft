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
		int dw = ((Double)args[0]).intValue();
		int dh = ((Double)args[1]).intValue();
		/*
		int dh = te.yCoord+((Double)args[1]).intValue();
		int[] xz = tg.getHorizontalInterval();
		int[] yy = tg.getVerticalInterval();
		BlockVector bv = tg.getLookDirection();
		dw += te.xCoord*Math.abs(bv.direction.offsetX);
		dw += te.zCoord*Math.abs(bv.direction.offsetZ);
		if (dw < xz[0] || dw > xz[1] || dh < yy[0] || dh > yy[1]) //out of bounds
			return null;
		 */
		if (Math.abs(dw) > tg.getRange() || dh < 0 || dh > tg.MAX_HEIGHT)
			return null;
		BlockKey bk = tg.getBlock(dw, dh);
		return new Object[]{bk.blockID, bk.metadata};
	}

	@Override
	public String getDocumentation() {
		return "Returns the block at a given position.\nArgs: horizontal offset, relative depth\nReturns: Block ID, metadata";
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
