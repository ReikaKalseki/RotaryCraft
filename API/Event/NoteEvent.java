/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API.Event;

import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.Event.TileEntityEvent;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Instrument;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Note;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.NoteLength;

public class NoteEvent extends TileEntityEvent {

	public final int notePitch;
	public final int noteChannel;
	public final Instrument voice;
	public final NoteLength length;

	public NoteEvent(TileEntityMusicBox te, Note n, int channel) {
		super(te);
		noteChannel = channel;
		notePitch = n.pitch;
		voice = n.voice;
		length = n.length;
	}

	public boolean match(TileEntityMusicBox te) {
		return this.getTile() == te;
	}

	public double getDistanceTo(TileEntity te) {
		return ReikaMathLibrary.py3d(te.xCoord-this.getTileX(), te.yCoord-this.getTileY(), te.zCoord-this.getTileZ());
	}

}
