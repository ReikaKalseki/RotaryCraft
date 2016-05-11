/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import dan200.computercraft.api.lua.LuaException;

public class LuaShiftPlane extends LuaMethod {

	public LuaShiftPlane() {
		super("shiftPlane", TileEntityGPR.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		TileEntityGPR tg = (TileEntityGPR)te;
		int dir = (int)Math.signum((Double)args[0]);
		tg.shiftInt(dir);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Returns the block at a given position.\nArgs: shift direction +/- 1\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
