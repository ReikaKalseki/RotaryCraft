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

import Reika.DragonAPI.ModInteract.Lua.LuaMethod;

public class LuaMethods {

	private static final LuaMethod getName = new LuaGetName();
	private static final LuaMethod getTemp = new LuaGetTemperature();
	private static final LuaMethod getPressure = new LuaGetPressure();
	private static final LuaMethod getPower = new LuaGetPower();

}
