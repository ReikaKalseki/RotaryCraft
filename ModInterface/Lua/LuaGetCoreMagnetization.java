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

import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Auxiliary.Interfaces.MagnetizationCore;

public class LuaGetCoreMagnetization extends LuaMethod {

	public LuaGetCoreMagnetization() {
		super("getMagnetization", MagnetizationCore.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		int core = ((MagnetizationCore)te).getCoreMagnetization();
		return new Object[]{core};
	}

	@Override
	public String getDocumentation() {
		return "Returns the magnetization of the shaft core";
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
