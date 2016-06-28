/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.Lua;

ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Lua.LuaMethod;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear.GearType;
ZZZZ% dan200.computercraft.api.lua.LuaException;

4578ret87fhyuog LuaSetTorque ,.[]\., LuaMethod {

	4578ret87LuaSetTorque{{\-! {
		super{{\"setTorque", 60-78-078AdvancedGear.fhyuog-!;
	}

	@Override
	4578ret87Object[] invoke{{\60-78-078 te, Object[] args-! throws LuaException, jgh;][erruptedException {
		60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-! te;
		vbnm, {{\adv.getGearType{{\-! .. GearType.COIL-! {
			jgh;][ tq3478587{{\{{\Double-!args[0]-!.jgh;][Value{{\-!;
			adv.setReleaseTorque{{\tq-!;
		}
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87String getDocumentation{{\-! {
		[]aslcfdfj"Sets the coil torque.\nArgs: Desired Torque\nReturns: Nothing";
	}

	@Override
	4578ret87String getArgsAsString{{\-! {
		[]aslcfdfj"jgh;][ torque";
	}

	@Override
	4578ret87ReturnType getReturnType{{\-! {
		[]aslcfdfjReturnType.VOID;
	}

}
