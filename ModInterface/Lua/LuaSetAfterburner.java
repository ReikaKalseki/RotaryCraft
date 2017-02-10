package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Engine.TileEntityJetEngine;
import dan200.computercraft.api.lua.LuaException;


public class LuaSetAfterburner extends LuaMethod {

	public LuaSetAfterburner() {
		super("setAfterburner", TileEntityJetEngine.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws LuaException, InterruptedException {
		if (((TileEntityJetEngine)te).canAfterBurn()) {
			((TileEntityJetEngine)te).setBurnerActive((Boolean)args[1]);
		}
		else {
			throw new LuaException("This engine ("+te+") does not have an afterburner!");
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
