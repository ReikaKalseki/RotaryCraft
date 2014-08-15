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

import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;

import net.minecraft.tileentity.TileEntity;

public class LuaGetName extends LuaMethod {

	public LuaGetName() {
		super("getName", RotaryCraftTileEntity.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
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