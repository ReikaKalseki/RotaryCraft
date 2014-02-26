/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import java.util.Arrays;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.Base.TileEntity.TileEntityIOMachine;

public class LuaGetPower extends LuaMethod {

	public LuaGetPower() {
		super("getPower", TileEntityIOMachine.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityIOMachine io = (TileEntityIOMachine)te;
		Object[] o = new Object[3];
		o[0] = io.power;
		o[1] = io.torque;
		o[2] = io.omega;
		ReikaJavaLibrary.pConsole(Arrays.toString(o));
		return o;
	}

}
