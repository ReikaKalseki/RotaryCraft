/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.SpriteRenderCallback;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;

public class ItemCraftPattern extends ItemRotaryTool implements SpriteRenderCallback {

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

	public static ItemStack[] getItems(ItemStack is) {
		ItemStack[] items = new ItemStack[9];
		if (is.stackTagCompound != null) {
			NBTTagCompound recipe = is.stackTagCompound.getCompoundTag("recipe");
			for (int i = 0; i < 9; i++) {
				String s = "slot"+i;
				if (recipe.hasKey(s)) {
					NBTTagCompound tag = recipe.getCompoundTag(s);
					ItemStack in = ItemStack.loadItemStackFromNBT(tag);
					items[i] = in;
				}
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
		for (int i = 0; i < 9; i++) {
			ItemStack in = ic.getStackInSlot(i);
			if (in != null) {
				NBTTagCompound tag = new NBTTagCompound();
				in.writeToNBT(tag);
				recipe.setTag("slot"+i, tag);
			}
		}
		ItemStack out = ir.getRecipeOutput();
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

	@Override
	public boolean onRender(RenderItem ri, ItemStack is, ItemRenderType type) {
		if (type == ItemRenderType.INVENTORY && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			ItemStack out = this.getRecipeOutput(is);
			if (out != null) {
				double s = 0.0625;
				GL11.glScaled(s, -s, s);
				ReikaGuiAPI.instance.drawItemStack(ri, out, 0, -16);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doPreGLTransforms(ItemStack is, ItemRenderType type) {
		return true;
	}

}
