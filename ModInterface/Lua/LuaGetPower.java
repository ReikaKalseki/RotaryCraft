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
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

import net.minecraft.tileentity.TileEntity;

public class LuaGetPower extends LuaMethod {

	public LuaGetPower() {
		super("getPower", TileEntityIOMachine.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityIOMachine io = (TileEntityIOMachine)te;
		Object[] o = new Object[3];
		//HashMap m = new HashMap();
		//m.put("Power", io.power);
		//m.put("Torque", io.torque);
		//m.put("Speed", io.omega);
		//return new Object[]{m};
		o[0] = io.power;
		o[1] = io.torque;
		o[2] = io.omega;
		return o;
	}

	@Override
	public String getDocumentation() {
		return "Returns the power data.\nArgs: None\nReturns: [Power, Torque, Speed]";
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