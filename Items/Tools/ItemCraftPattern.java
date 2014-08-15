/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import Reika.RotaryCraft.Base.ItemRotaryTool;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemCraftPattern extends ItemRotaryTool {

	public ItemCraftPattern(int index) {
		super(index);
	}

	//right click to open programming gui

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		if (is.stackTagCompound == null) {
			li.add("No Crafting Pattern.");
		}
		else {
			ItemStack[] items = new ItemStack[10];
			NBTTagList nbttaglist = is.stackTagCompound.getTagList("Items", is.stackTagCompound.getId());
			for (int k = 0; k < nbttaglist.tagCount(); k++)				{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(k);
				short byte0 = nbttagcompound.getShort("Slot");
				items[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
			li.add("Crafts "+items[9].stackSize+" "+items[9].getDisplayName()+" with:");
			for (int i = 0; i < 3; i++) {
				StringBuilder sb = new StringBuilder();
				for (int k = 0; k < 3; k++) {
					String name = items[i*3+k] != null ? items[i].getDisplayName() : "-Nothing-";
					sb.append(name);
					if (k < 2)
						sb.append(", ");
				}
				li.add("  "+sb.toString());
			}
		}
	}

}