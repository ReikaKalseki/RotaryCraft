package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityDistributionClutch;

public class LuaSetDistributionClutchOutput extends LuaMethod {

	public LuaSetDistributionClutchOutput() {
		super("setTorqueToSide", TileEntityDistributionClutch.class);
	}

	@Override
	protected Object[] invoke(TileEntity te, Object[] args) throws LuaMethodException, InterruptedException {
		TileEntityDistributionClutch tile = (TileEntityDistributionClutch)te;
		int side = ((Double)args[0]).intValue();
		boolean enable = ((Boolean)args[1]).booleanValue();
		int amt = ((Double)args[2]).intValue();
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];
		tile.setSideEnabled(dir, enable);
		if (enable)
			tile.setTorqueRequest(dir, amt);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Sets the requested torque amount out a given side. Remainder is passed directly through.";
	}

	@Override
	public String getArgsAsString() {
		return "int side, int torque";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
