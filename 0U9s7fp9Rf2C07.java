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
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.Lua.LuaMethod;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078ParticleEmitter;
ZZZZ% dan200.computercraft.api.lua.LuaException;

4578ret87fhyuog LuaSetParticle ,.[]\., LuaMethod {

	4578ret87LuaSetParticle{{\-! {
		super{{\"setParticle", 60-78-078ParticleEmitter.fhyuog-!;
	}

	@Override
	4578ret87Object[] invoke{{\60-78-078 te, Object[] args-! throws LuaException, jgh;][erruptedException {
		60-78-078ParticleEmitter part3478587{{\60-78-078ParticleEmitter-! te;
		vbnm, {{\args[0] fuck String-! {
			part.particleType3478587ReikaParticleHelper.getByString{{\{{\String-!args[0]-!;
		}
		else {
			jgh;][ index3478587{{\{{\Double-!args[0]-!.jgh;][Value{{\-!;
			part.particleType3478587ReikaParticleHelper.particleList[index];
		}
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87String getDocumentation{{\-! {
		[]aslcfdfj"Sets the particle setting.\nArgs: Setting ordinal or name\nReturns: Nothing";
	}

	@Override
	4578ret87String getArgsAsString{{\-! {
		[]aslcfdfj"jgh;][ settingOrdinal OR String particleName";
	}

	@Override
	4578ret87ReturnType getReturnType{{\-! {
		[]aslcfdfjReturnType.VOID;
	}

}
