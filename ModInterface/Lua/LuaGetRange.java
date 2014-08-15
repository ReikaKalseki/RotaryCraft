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
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;

import net.minecraft.tileentity.TileEntity;

public class LuaGetRange extends LuaMethod {

	public LuaGetRange() {
		super("getRange", RangedEffect.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		return new Object[]{((RangedEffect)te).getRange(), ((RangedEffect)te).getMaxRange()};
	}

	@Override
	public String getDocumentation() {
		return "Returns the effect range.\nArgs: None\nReturns: [Range, Max Range]";
	}

	@Override
	public String getArgsAsString() {
		return "";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.ARRAY;
	}

}