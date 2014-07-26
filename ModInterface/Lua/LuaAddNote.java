/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface.Lua;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Instrument;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Note;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.NoteLength;

public class LuaAddNote extends LuaMethod {

	public LuaAddNote() {
		super("addNote", TileEntityMusicBox.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityMusicBox mus = (TileEntityMusicBox) te;
		int pitch = ((Double)args[0]).intValue();
		int channel = ((Double)args[1]).intValue();
		int length = ((Double)args[2]).intValue();
		int voice = ((Double)args[3]).intValue();
		Note n = new Note(NoteLength.values()[length], pitch, Instrument.values()[voice]);
		for (int i = 0; i < 3; i++)
			n.play(mus);
		mus.addNote(channel, n);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Adds a note to the music box.\nArgs: Pitch (0-63), Channel (0-15), Length (0-4), Instrument (0-5)\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int pitch, int channel, int length, int voice";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}
