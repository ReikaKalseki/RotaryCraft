/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;

public class NoteEvent extends TileEntityEvent {

	public final int notePitch;
	public final int noteChannel;
	public final int length;

	public NoteEvent(TileEntity te, int pitch, int length, int channel) {
		super(te);
		noteChannel = channel;
		notePitch = pitch;
		this.length = length;
	}

	public double getDistanceTo(TileEntity te) {
		return ReikaMathLibrary.py3d(te.xCoord-this.getTileX(), te.yCoord-this.getTileY(), te.zCoord-this.getTileZ());
	}

}
