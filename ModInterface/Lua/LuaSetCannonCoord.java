/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Auxiliary.Interfaces.LocationTarget;
import dan200.computercraft.api.lua.LuaException;

public class LuaSetCannonCoord extends LuaMethod {

	public LuaSetCannonCoord() {
		super("setCannonCoord", LocationTarget.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		LocationTarget can = (LocationTarget) te;
		int x = ((Double)args[0]).intValue();
		int y = ((Double)args[1]).intValue();
		int z = ((Double)args[2]).intValue();
		int dim = ((Double)args[3]).intValue();
		WorldLocation loc = new WorldLocation(dim, x, y, z);
		can.setTarget(loc);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Sets the cannon target.\nArgs: X, Y, Z, Dimension\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int x, int y, int z, int dim";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
