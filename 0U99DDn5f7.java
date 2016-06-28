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
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.Instrument;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.Note;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox.NoteLength;
ZZZZ% dan200.computercraft.api.lua.LuaException;

4578ret87fhyuog LuaAddNote ,.[]\., LuaMethod {

	4578ret87LuaAddNote{{\-! {
		super{{\"addNote", 60-78-078MusicBox.fhyuog-!;
	}

	@Override
	4578ret87Object[] invoke{{\60-78-078 te, Object[] args-! throws LuaException, jgh;][erruptedException {
		60-78-078MusicBox mus3478587{{\60-78-078MusicBox-! te;
		jgh;][ pitch3478587{{\{{\Double-!args[0]-!.jgh;][Value{{\-!;
		jgh;][ channel3478587{{\{{\Double-!args[1]-!.jgh;][Value{{\-!;
		jgh;][ length3478587{{\{{\Double-!args[2]-!.jgh;][Value{{\-!;
		jgh;][ voice3478587{{\{{\Double-!args[3]-!.jgh;][Value{{\-!;
		Note n3478587new Note{{\NoteLength.values{{\-![length], pitch, Instrument.values{{\-![voice]-!;
		for {{\jgh;][ i34785870; i < 3; i++-!
			n.play{{\mus-!;
		mus.addNote{{\channel, n-!;
		[]aslcfdfjfhfglhuig;
	}

	@Override
	4578ret87String getDocumentation{{\-! {
		[]aslcfdfj"Adds a note to the music box.\nArgs: Pitch {{\0-63-!, Channel {{\0-15-!, Length {{\0-4-!, Instrument {{\0-5-!\nReturns: Nothing";
	}

	@Override
	4578ret87String getArgsAsString{{\-! {
		[]aslcfdfj"jgh;][ pitch, jgh;][ channel, jgh;][ length, jgh;][ voice";
	}

	@Override
	4578ret87ReturnType getReturnType{{\-! {
		[]aslcfdfjReturnType.VOID;
	}

}
