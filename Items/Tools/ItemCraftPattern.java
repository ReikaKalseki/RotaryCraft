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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
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
		//FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		if (is.stackTagCompound == null) {
			li.add("No Crafting Pattern.");
		}
		else {
			ItemStack item = this.getRecipeOutput(is);
			if (item != null)
				li.add("Crafts "+item.stackSize+" "+item.getDisplayName());//+" with:");
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
		ItemStack item = is.stackTagCompound != null ? ItemStack.loadItemStackFromNBT(is.stackTagCompound.getCompoundTag("output")) : null;
		return item != null ? item.copy() : null;
	}

	public static ArrayList<ItemStack>[] getItems(ItemStack is) {
		ArrayList<ItemStack>[] items = new ArrayList[9];
		if (is.stackTagCompound != null) {
			NBTTagCompound recipe = is.stackTagCompound.getCompoundTag("recipe");
			for (int i = 0; i < 9; i++) {
				NBTTagList tag = recipe.getTagList(String.valueOf(i), NBTTypes.COMPOUND.ID);
				ArrayList<ItemStack> li = new ArrayList();
				for (int k = 0; k < tag.tagCount(); k++) {
					NBTTagCompound nbt = tag.getCompoundTagAt(k);
					ItemStack in = ItemStack.loadItemStackFromNBT(nbt);
					if (in != null)
						li.add(in);
				}
				items[i] = li;
			}
		}
		return items;
	}

	private static void resetNBT(ItemStack is) {
		if (is.stackTagCompound != null) {
			is.stackTagCompound.removeTag("output");
			is.stackTagCompound.removeTag("recipe");
		}
	}

	public static void setRecipe(ItemStack is, InventoryCrafting ic) {
		resetNBT(is);
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		IRecipe ir = getRecipe(ic);
		if (ir == null)
			return;
		NBTTagCompound recipe = new NBTTagCompound();
		ItemStack out = ir.getRecipeOutput();
		ArrayList<Object> reqs = ReikaRecipeHelper.getAllInputsInRecipe(ir);
		Object[] items = new Object[10];
		for (int i = 0; i < reqs.size(); i++) {
			Object in = reqs.get(i);
			NBTTagList tag = new NBTTagList();
			if (in instanceof ArrayList) {
				ArrayList<ItemStack> li = (ArrayList<ItemStack>)in;
				for (ItemStack item : li) {
					NBTTagCompound nbt = new NBTTagCompound();
					item.writeToNBT(nbt);
					tag.appendTag(nbt);
				}
			}
			else if (in instanceof ItemStack) {
				ItemStack item = (ItemStack)in;
				NBTTagCompound nbt = new NBTTagCompound();
				item.writeToNBT(nbt);
				tag.appendTag(nbt);
				if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
					nbt = new NBTTagCompound();
					ItemStack is2 = item.copy();
					is2.setItemDamage(0);
					is2.writeToNBT(nbt);
					tag.appendTag(nbt);
				}
			}
			else {

			}
			recipe.setTag(String.valueOf(i), tag);
		}
		is.stackTagCompound.setTag("recipe", recipe);
		NBTTagCompound outt = new NBTTagCompound();
		if (out != null)
			out.writeToNBT(outt);
		is.stackTagCompound.setTag("output", outt);
		//ReikaJavaLibrary.pConsole(Arrays.toString(items)+" -> "+out);
	}

	private static IRecipe getRecipe(InventoryCrafting ic) {
		List<IRecipe> li = CraftingManager.getInstance().getRecipeList();
		for (IRecipe ir : li)  {
			if (ir.matches(ic, null)) {
				return ir;
			}
		}
		return null;
	}

}
