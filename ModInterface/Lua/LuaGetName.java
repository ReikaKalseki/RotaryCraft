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
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import dan200.computercraft.api.lua.LuaException;

public class LuaGetName extends LuaMethod {

	public LuaGetName() {
		super("getName", RotaryCraftTileEntity.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		return new Object[]{((RotaryCraftTileEntity)te).getMultiValuedName()};
	}

	@Override
	public String getDocumentation() {
		return "Returns the machine name.\nArgs: None\nReturns: Display name";
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
