/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import Reika.RotaryCraft.Base.ItemBasic;

public class ItemDisk extends ItemBasic {

	public ItemDisk(int ID, int tex) {
		super(ID, tex);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		if (is.stackTagCompound.hasKey("music")) {
			par3List.add("Track: ");
			String[] sg = is.stackTagCompound.getString("music").split(" ");
			for (int i = 0; i < sg.length; i++)
				par3List.add(sg[i].substring(0, 1).toUpperCase()+sg[i].substring(1, sg[i].length()));
		}
	}

}
