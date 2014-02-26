package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.API.ShaftMachine;

public class LuaGetAPIPower extends LuaMethod {

	public LuaGetAPIPower() {
		super("getPower", ShaftMachine.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		ShaftMachine s = (ShaftMachine) te;
		return new Object[]{s.getPower(), s.getTorque(), s.getOmega()};
	}

}
