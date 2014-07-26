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

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;

public class LuaSetJunction extends LuaMethod {

	public LuaSetJunction() {
		super("setJunction", TileEntitySplitter.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntitySplitter spl = (TileEntitySplitter) te;
		int ratio = ((Double)args[0]).intValue();
		int test = Math.abs(ratio);
		if (test != 1 && test != 4 && test != 8 && test != 16 && test != 32)
			throw new IllegalArgumentException("Invalid Ratio!");
		spl.setMode(ratio);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Sets the junction setting. Use negative numbers to favor bend.\nArgs: Setting Ratio\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int ratio";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
