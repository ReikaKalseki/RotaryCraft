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
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;

import net.minecraft.tileentity.TileEntity;

public class LuaSetCannon extends LuaMethod {

	public LuaSetCannon() {
		super("setCannon", TileEntityLaunchCannon.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityLaunchCannon can = (TileEntityLaunchCannon) te;
		int theta = ((Double)args[0]).intValue();
		int ang = ((Double)args[1]).intValue();
		int v = ((Double)args[2]).intValue();
		int maxth = can.getMaxTheta();
		int maxv = can.getMaxLaunchVelocity();
		can.velocity = Math.min(v, maxv);
		can.theta = Math.min(theta, maxth);
		can.phi = ang;
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Sets the launch cannon trajectory.\nArgs: Inclination, Compass, Velocity\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int angle, int compass, int velocity";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}