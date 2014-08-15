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

import Reika.DragonAPI.ModInteract.Lua.LuaMethod;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;

import net.minecraft.tileentity.TileEntity;

public class LuaClearChannel extends LuaMethod {

	public LuaClearChannel() {
		super("clearChannel", TileEntityMusicBox.class);
	}

	@Override
	public Object[] invoke(TileEntity te, Object[] args) throws Exception {
		TileEntityMusicBox mus = (TileEntityMusicBox) te;
		int channel = ((Double)args[0]).intValue();
		mus.clearChannel(channel);
		return null;
	}

	@Override
	public String getDocumentation() {
		return "Clears the music box channel.\nArgs: Channel (0-15)\nReturns: Nothing";
	}

	@Override
	public String getArgsAsString() {
		return "int channel";
	}

	@Override
	public ReturnType getReturnType() {
		return ReturnType.VOID;
	}

}