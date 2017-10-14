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
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import dan200.computercraft.api.lua.LuaException;

public class LuaSetOutputLevel extends LuaMethod {

	public LuaSetOutputLevel() {
		super("setOutputLevel", EnergyToPowerBase.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		EnergyToPowerBase e = (EnergyToPowerBase) te;
		if (!e.setOmega(((Integer)args[0]).intValue()))
			throw new IllegalArgumentException("Invalid power setting out of bounds.");
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Sets the power output level of a converter engine (as the exponent), starting at -1 for no output.\nArgs: Setting\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int setting";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
