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
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;


public class LuaSetAfterburner extends LuaMethod {

	public LuaSetAfterburner() {
		super("setAfterburner", TileEntityJetEngine.class);
	}

	@Override
	protected Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		if (((TileEntityJetEngine)te).canAfterBurn()) {
			((TileEntityJetEngine)te).setBurnerActive((Boolean)args[1]);
		}
		else {
			throw new LuaMethodException("This engine ("+te+") does not have an afterburner!");
		}
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Allows for control of the jet engine afterburner.";
	}

	@Override
	public String getArgsAsString() {
		return "boolean active";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
