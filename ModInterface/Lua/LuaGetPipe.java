package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPiping;

public class LuaGetPipe extends LuaMethod {

	public LuaGetPipe() {
		super("getPipe", TileEntityPiping.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityPiping p = (TileEntityPiping) te;
		return new Object[]{p.getLiquidType().getLocalizedName(), p.getLiquidLevel()};
	}

}
