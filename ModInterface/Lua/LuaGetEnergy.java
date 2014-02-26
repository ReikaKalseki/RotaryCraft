package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;

public class LuaGetEnergy extends LuaMethod {

	public LuaGetEnergy() {
		super("getEnergy", TileEntityAdvancedGear.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityAdvancedGear adv = (TileEntityAdvancedGear) te;
		return adv.getGearType().storesEnergy() ? new Object[]{adv.getEnergy(), adv.getMaxStorageCapacity()} : null;
	}

}
