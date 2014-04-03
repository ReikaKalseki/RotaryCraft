/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import Reika.RotaryCraft.Base.ItemRotaryTool;

public class ItemDisk extends ItemRotaryTool {

	public ItemDisk(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		li.add("Contains stored music:");
		for (int i = 0; i < 16; i++) {
			if (is.stackTagCompound.hasKey("ch"+i)) {
				NBTTagList track = is.stackTagCompound.getTagList("ch"+i);
				if (track.tagCount() > 0)
					li.add("Track "+i+": "+track.tagCount()+" entries");
			}
		}
	}

}
