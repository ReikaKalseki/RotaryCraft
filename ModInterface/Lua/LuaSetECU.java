package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;

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

}
