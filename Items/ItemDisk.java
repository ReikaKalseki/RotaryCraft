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
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;

public class ItemDisk extends ItemRotaryTool {

	public ItemDisk(int ID, int tex) {
		super(ID, tex);
		this.setCreativeTab(RotaryCraft.tabRotaryTools);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List par3List, boolean par4) {
		if (is.stackTagCompound == null)
			return;
		if (is.stackTagCompound.hasKey("music")) {
			par3List.add("Track: ");
			String[] sg = is.stackTagCompound.getString("music").split(" ");
			for (int i = 0; i < sg.length; i++)
				par3List.add(ReikaStringParser.capFirstChar(sg[i]));
		}
	}

}
