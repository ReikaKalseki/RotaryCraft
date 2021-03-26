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
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;

public class LuaBorerReqs extends LuaMethod {

	public LuaBorerReqs() {
		super("getRequirements", TileEntityBorer.class);
	}

	@Override
	protected Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		TileEntityBorer io = (TileEntityBorer)te;
		Object[] o = new Object[3];
		o[0] = io.getRequiredTorque();
		o[1] = io.getRequiredPower();
		o[2] = io.isJammed();
		return o;
	}

	@Override
	public String getDocumentation() {
		return "Returns the current mining requirements.\nArgs: None\nReturns: [Required Torque, Required Power, isJammed]";
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
