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
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;

import net.minecraft.tileentity.TileEntity;

public class LuaSetECU extends LuaMethod {

	public LuaSetECU() {
		super("setECU", TileEntityEngineController.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityEngineController ecu = (TileEntityEngineController) te;
		int index = ((Double)args[0]).intValue();
		ecu.setSetting(index);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Sets the ECU setting.\nArgs: Setting Ordinal\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int settingOrdinal";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}