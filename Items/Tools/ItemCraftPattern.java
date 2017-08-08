/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Interfaces.Item.SpriteRenderCallback;
import Reika.DragonAPI.Libraries.IO.ReikaGuiAPI;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastCrafting;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.WorktableRecipes.WorktableRecipe;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

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
			if (item != null) {
				li.add("Crafts "+item.stackSize+" "+item.getDisplayName());//+" with:");
			}
			else {
				li.add("Items, No Output.");
			}
		}
		li.add("Recipe Mode: "+this.getMode(is).displayName);
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

	public static int getStackInputLimit(ItemStack is) {
		if (is.stackTagCompound != null) {
			int amt = is.stackTagCompound.getInteger("stacklimit");
			return amt > 0 ? amt : 64;
		}
		return 64;
	}

	private static void resetNBT(ItemStack is) {
		if (is.stackTagCompound != null) {
			is.stackTagCompound.removeTag("output");
			is.stackTagCompound.removeTag("recipe");
		}
	}

	public static void setRecipe(ItemStack is, InventoryCrafting ic, World world) {
		resetNBT(is);
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		ItemStack out = getMode(is).getRecipe(ic, world);
		boolean valid = out != null;
		NBTTagCompound recipe = new NBTTagCompound();
		for (int i = 0; i < 9; i++) {
			ItemStack in = ic.getStackInSlot(i);
			if (in != null) {
				NBTTagCompound tag = new NBTTagCompound();
				in.writeToNBT(tag);
				recipe.setTag("slot"+i, tag);
			}
		}
		is.stackTagCompound.setTag("recipe", recipe);
		is.stackTagCompound.setBoolean("valid", valid);
		if (valid) {
			NBTTagCompound outt = new NBTTagCompound();
			out.writeToNBT(outt);
			is.stackTagCompound.setTag("output", outt);
		}
		//ReikaJavaLibrary.pConsole(Arrays.toString(items)+" -> "+out);
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

	public static RecipeMode getMode(ItemStack is) {
		return is.stackTagCompound != null ? RecipeMode.list[is.stackTagCompound.getInteger("mode")] : RecipeMode.CRAFTING;
	}

	public static void setMode(ItemStack is, RecipeMode md) {
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("mode", md.ordinal());
	}

	public static void changeStackLimit(ItemStack is, int change) {
		if (is.stackTagCompound == null)
			is.stackTagCompound = new NBTTagCompound();
		int limit = getStackInputLimit(is);
		is.stackTagCompound.setInteger("stacklimit", MathHelper.clamp_int(limit+change, 1, 64));
	}

	public static enum RecipeMode {
		CRAFTING("Crafting Recipe", new ItemStack(Blocks.crafting_table)),
		WORKTABLE("Worktable Recipe", MachineRegistry.WORKTABLE.getCraftedProduct()),
		BLASTFURN("Blast Furnace Crafting", MachineRegistry.BLASTFURNACE.getCraftedProduct());

		private final ItemStack item;
		public final String displayName;

		public static final RecipeMode[] list = values();

		private RecipeMode(String s, ItemStack is) {
			item = is;
			displayName = s;
		}

		public ItemStack getIcon() {
			return item.copy();
		}

		public RecipeMode next() {
			return this.ordinal() == list.length-1 ? list[0] : list[this.ordinal()+1];
		}

		public ItemStack getRecipe(InventoryCrafting ic, World world) {
			switch(this) {
				case CRAFTING:
					List<IRecipe> li = CraftingManager.getInstance().getRecipeList();
					for (IRecipe ir : li)  {
						if (ir.matches(ic, null)) {
							return ir.getRecipeOutput();
						}
					}
					return null;
				case BLASTFURN:
					for (BlastCrafting ir : RecipesBlastFurnace.getRecipes().getAllCraftingRecipes())  {
						if (ir.matches(ic, Integer.MAX_VALUE)) {
							return ir.outputItem();
						}
					}
					return null;
				case WORKTABLE:
					WorktableRecipe wr = WorktableRecipes.getInstance().findMatchingRecipe(ic, null);
					return wr != null ? wr.getOutput() : null;
			}
			return null;
		}
	}

	public static boolean checkPatternForMatch(IInventory te, RecipeMode type, int invslot, int patternslot, ItemStack is, ItemStack p) {
		ItemStack in = te.getStackInSlot(invslot);
		return getMode(p) == type && checkItemAndSize(patternslot, is, p, in != null ? in.stackSize : 0);
	}

	private static boolean checkItemAndSize(int slot, ItemStack is, ItemStack p, int current) {
		return ReikaItemHelper.matchStacks(is, getItems(p)[slot]) && current+is.stackSize <= Math.min(is.getMaxStackSize(), getStackInputLimit(p));
	}

}
