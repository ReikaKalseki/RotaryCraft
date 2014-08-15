/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import Reika.DragonAPI.Libraries.IO.ReikaFormatHelper;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;

import net.minecraft.tileentity.TileEntity;

public class LuaGetFuelTime extends LuaMethod {

	public LuaGetFuelTime() {
		super("getTime", TileEntityEngine.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
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