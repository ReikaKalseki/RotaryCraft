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
import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import dan200.computercraft.api.lua.LuaException;

public class LuaGetFuelTime extends LuaMethod {

	public LuaGetFuelTime() {
		super("getTime", TileEntityEngine.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		int time = ((TileEntityEngine)te).getFuelDuration();
		return new Object[]{ReikaFormatHelper.getSecondsAsClock(time)};
	}

	@Override
	public String getDocumentation() {
		return "Returns the remaining fuel time in an engine";
	}

	@Override
	public String getArgsAsString() {
		return "";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.STRING;
	}

}
