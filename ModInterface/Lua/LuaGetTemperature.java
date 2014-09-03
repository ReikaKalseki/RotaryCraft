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

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;

public class LuaGetTemperature extends LuaMethod {

	public LuaGetTemperature() {
		super("getTemperature", TemperatureTE.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		return new Object[]{((TemperatureTE)te).getTemperature()};
	}

	@Override
	public String getDocumentation() {
		return "Returns the machine temperature.\nArgs: None\nReturns: Temperature";
	}

	@Override
	public String getArgsAsString() {
		return "";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.INTEGER;
	}

}
