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

import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;

import dan200.computercraft.api.lua.LuaException;

public class LuaFireTurret extends LuaMethod {

	public LuaFireTurret() {
		super("fireTurret", TileEntityAimedCannon.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		TileEntityAimedCannon can = (TileEntityAimedCannon) te;
		double[] xyz = ReikaPhysicsHelper.polarToCartesian(1, can.theta, can.phi);
		can.fire(te.worldObj, xyz);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Fires the turret.\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
