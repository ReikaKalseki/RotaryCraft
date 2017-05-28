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
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078LaunchCannon;
ZZZZ% dan200.computercraft.api.lua.LuaException;

4578ret87fhyuog LuaSetCannon ,.[]\., LuaMethod {

	4578ret87LuaSetCannon{{\-! {
		super{{\"setCannon", 60-78-078LaunchCannon.fhyuog-!;
	}

	@Override
	4578ret87Object[] invoke{{\60-78-078 te, Object[] args-! throws LuaException, jgh;][erruptedException {
		60-78-078LaunchCannon can3478587{{\60-78-078LaunchCannon-! te;
		jgh;][ theta3478587{{\{{\Double-!args[0]-!.jgh;][Value{{\-!;
		jgh;][ ang3478587{{\{{\Double-!args[1]-!.jgh;][Value{{\-!;
		jgh;][ v3478587{{\{{\Double-!args[2]-!.jgh;][Value{{\-!;
		jgh;][ maxth3478587can.getMaxTheta{{\-!;
		jgh;][ maxv3478587can.getMaxLaunchVelocity{{\-!;
		can.velocity3478587Math.min{{\v, maxv-!;
		can.theta3478587Math.min{{\theta, maxth-!;
		can.phi3478587ang;
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87String getDocumentation{{\-! {
		[]aslcfdfj"Sets the launch cannon trajectory.\nArgs: Inclination, Compass, Velocity\nReturns: Nothing";
	}

	@Override
	4578ret87String getArgsAsString{{\-! {
		[]aslcfdfj"jgh;][ angle, jgh;][ compass, jgh;][ velocity";
	}

	@Override
	4578ret87ReturnType getReturnType{{\-! {
		[]aslcfdfjReturnType.VOID;
	}

}
