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

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;

public class ItemCraftPattern extends ItemRotaryTool {

	public ItemCraftPattern(int index) {
		super(index);
	}

	//right click to open programming gui

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (ep.isSneaking()) {
			is.stackTagCompound = null;
		}
		else {
			ep.openGui(RotaryCraft.instance, GuiRegistry.PATTERN.ordinal(), world, 0, 0, 0);
		}
		return is;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		if (is.stackTagCompound == null) {
			li.add("No Crafting Pattern.");
		}
		else {
			ItemStack[] items = this.getItems(is);
			li.add("Crafts "+items[9].stackSize+" "+items[9].getDisplayName());//+" with:");
			/*
			for (int i = 0; i < 3; i++) {
				StringBuilder sb = new StringBuilder();
				for (int k = 0; k < 3; k++) {
					String name = items[i*3+k] != null ? items[i].getDisplayName() : "-Nothing-";
					sb.append(name);
					if (k < 2)
						sb.append(", ");
				}
				li.add("  "+sb.toString());
			}*/
		}
	}

	public static ItemStack getRecipeOutput(ItemStack is) {
		ItemStack[] items = getItems(is);
		return items[9] != null ? items[9].copy() : null;
	}

	public static ItemStack[] getItems(ItemStack is) {
		ItemStack[] items = new ItemStack[10];
		if (is.stackTagCompound != null) {
			NBTTagList nbttaglist = is.stackTagCompound.getTagList("Items", NBTTypes.COMPOUND.ID);
			for (int k = 0; k < nbttaglist.tagCount(); k++)				{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(k);
				short byte0 = nbttagcompound.getShort("Slot");
				items[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		return items;
	}

	public static void setRecipe(ItemStack is, InventoryCrafting ic) {
		is.stackTagCompound = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < 9; i++) {
			ItemStack in = ic.getStackInSlot(i);
			if (in != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("Slot", (short)i);
				in.writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		is.stackTagCompound.setTag("Items", nbttaglist);
		ItemStack out = CraftingManager.getInstance().findMatchingRecipe(ic, null);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("Slot", (short)9);
		if (out != null)
			out.writeToNBT(nbttagcompound);
		//ReikaJavaLibrary.pConsole(Arrays.toString(items)+" -> "+out);
		nbttaglist.appendTag(nbttagcompound);
	}

}
