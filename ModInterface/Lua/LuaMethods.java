/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
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
	private static final LuaMethod getPower2 = new LuaGetAPIPower();
	private static final LuaMethod getRatio = new LuaGetRatio();
	private static final LuaMethod getEnergy = new LuaGetEnergy();
	private static final LuaMethod setRatio = new LuaSetRatio();
	private static final LuaMethod setTorque = new LuaSetTorque();
	private static final LuaMethod getSpeed = new LuaSetSpeed();
	private static final LuaMethod getRange = new LuaGetRange();
	private static final LuaMethod getPipe = new LuaGetPipe();
	private static final LuaMethod setECU = new LuaSetECU();
	private static final LuaMethod addNote = new LuaAddNote();
	private static final LuaMethod clearChannel = new LuaClearChannel();
	private static final LuaMethod getFuelTime = new LuaGetFuelTime();
	private static final LuaMethod setJunction = new LuaSetJunction();
	private static final LuaMethod setParticle = new LuaSetParticle();
	private static final LuaMethod setCannon = new LuaSetCannon();
	private static final LuaMethod toggleOre = new LuaToggleOreDict();
	private static final LuaMethod toggleMeta = new LuaToggleMetadata();
	private static final LuaMethod toggleNBT = new LuaToggleNBT();
	private static final LuaMethod toggleBlacklist = new LuaToggleBlacklist();
	private static final LuaMethod getBlockAtPos = new LuaGetBlockAtPos();
	private static final LuaMethod shiftPlane = new LuaShiftPlane();
	private static final LuaMethod getCoreMagnet = new LuaCoreMagnetization();
	private static final LuaMethod setCannonCoord = new LuaSetCannonCoord();
	private static final LuaMethod afterburner = new LuaSetAfterburner();

}
