/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import Reika.DragonAPI.Instantiable.MusicScore;
import Reika.DragonAPI.Interfaces.Item.MusicDataItem;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox.Note;

public class ItemDisk extends ItemRotaryTool implements MusicDataItem {

	public ItemDisk(int tex) {
		super(tex);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		li.add("Contains stored music:");
		for (int i = 0; i < 16; i++) {
			if (is.stackTagCompound.hasKey("ch"+i)) {
				NBTTagList track = is.stackTagCompound.getTagList("ch"+i, NBTTypes.COMPOUND.ID);
				if (track.tagCount() > 0)
					li.add("Track "+i+": "+track.tagCount()+" entries");
			}
		}
	}

	@Override
	public MusicScore getMusic(ItemStack is) {
		MusicScore mus = new MusicScore(16);
		int[] pos = new int[16];
		for (int i = 0; i < 16; i++) {
			if (is.stackTagCompound.hasKey("ch"+i)) {
				NBTTagList li = is.stackTagCompound.getTagList("ch"+i, NBTTypes.COMPOUND.ID);
				for (int k = 0; k < li.tagCount(); k++) {
					NBTTagCompound nbt = li.getCompoundTagAt(k);
					//ReikaJavaLibrary.pConsole(i+":"+k+":"+nbt, Side.SERVER);
					Note n = Note.readFromNBT(nbt);
					if (!n.isRest())
						mus.addNote(pos[i], i, n.getMusicKey(), n.voice.MIDIvalue, 128, n.getTickLength());
					pos[i] += n.getTickLength();
				}
			}
		}
		return mus;
	}

}
