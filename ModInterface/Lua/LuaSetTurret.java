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
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;

public class LuaSetTurret extends LuaMethod {

	public LuaSetTurret() {
		super("setTurretAim", TileEntityAimedCannon.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		TileEntityAimedCannon can = (TileEntityAimedCannon) te;
		can.isCustomAim = true;
		double tphi = ((Double)args[0]).doubleValue();
		double ttheta = ((Double)args[1]).doubleValue();
		if (!can.isValidTheta(ttheta))
			throw new LuaMethodException("Invalid angle; out of gimbal limits ("+can.MAXLOWANGLE+" degrees below horizontal)");
		boolean ret = can.adjustAim(tphi, ttheta);
		return new Object[] {ret};
	}

	@Override
	public String getDocumentation() {
		return "Sets the turret aim direction.\nArgs: Phi, Theta\nReturns: isAimingAt";
	}

	@Override
	public String getArgsAsString() {
		return "int phi, int theta";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.BOOLEAN;
	}

}
