/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Auxiliary.Interfaces.MagnetizationCore;
import dan200.computercraft.api.lua.LuaException;

public class LuaCoreMagnetization extends LuaMethod {

	public LuaCoreMagnetization() {
		super("getMagnetization", MagnetizationCore.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
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
