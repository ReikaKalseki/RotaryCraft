/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import Reika.DragonAPI.Instantiable.RecipePattern;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class TileEntityAutoCrafter extends InventoriedPowerReceiver {

	public boolean continuous;
	private final HashMap<ItemStack, Integer> ingredients = new HashMap();

	private static final HashMap<RecipePattern, ItemStack> recipeTable = new HashMap();

	private ItemStack getRecipeOutput(ItemStack... in) {
		RecipePattern ic = new RecipePattern(in);
		if (!recipeTable.containsKey(ic)) {
			ItemStack is = CraftingManager.getInstance().findMatchingRecipe(ic, worldObj);
			recipeTable.put(ic, is);
		}
		return recipeTable.get(ic);
	}

	private void feedInItem(ItemStack is) {
		Integer num = ingredients.get(is);
		if (num == null) {
			num = new Integer(is.stackSize);
		}
		else {
			num = new Integer(num.intValue()+is.stackSize);
		}
		ingredients.put(is, num);
	}

	private void removeOneIngredient(ItemStack is) {
		ItemStack con = is.getItem().getContainerItem(is);
		if (con != null)
			this.feedInItem(con);
		int num = ingredients.get(is);
		if (num > 1) {
			ingredients.put(is, num-1);
		}
		else {
			ingredients.remove(is);
		}
	}

	private ItemStack getFirstIngredientToRemove() {
		if (ingredients.isEmpty())
			return null;
		ItemStack is = ingredients.keySet().iterator().next();
		int max = is.getMaxStackSize();
		int num = ingredients.get(is);
		return is.copy();
	}

	private void removeFirstIngredient() {
		if (ingredients.isEmpty())
			return;
		ItemStack is = ingredients.keySet().iterator().next();
		int max = is.getMaxStackSize();
		int num = ingredients.get(is);
		if (num > max)
			ingredients.put(is, num-max);
		else
			ingredients.remove(is);
	}

	public ArrayList<ItemStack> getAllIngredients() {
		ArrayList<ItemStack> li = new ArrayList();
		for (ItemStack is : ingredients.keySet()) {
			Integer num = ingredients.get(is);
			int items = num.intValue();
			int max = is.getMaxStackSize();
			if (items > max) {
				while (items > 0) {
					int drop = Math.min(items, max);
					li.add(is.copy());
					items -= drop;
				}
			}
			else {
				li.add(is.copy());
			}
		}
		return li;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		if (power >= MINPOWER) {
			this.eatIngredients();
			this.attemptCrafting();
		}
	}

	public void writePattern(ItemStack pattern, ItemStack... items) {
		pattern.stackTagCompound = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < 9; i++) {
			if (items[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("Slot", (short)i);
				items[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		pattern.stackTagCompound.setTag("Items", nbttaglist);
		ItemStack out = this.getRecipeOutput(items);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("Slot", (short)9);
		if (out != null)
			out.writeToNBT(nbttagcompound);
		//ReikaJavaLibrary.pConsole(Arrays.toString(items)+" -> "+out);
		nbttaglist.appendTag(nbttagcompound);
	}

	private boolean attemptCrafting() {
		for (int i = 0; i < 54; i++) {
			ItemStack is = inv[i];
			if (is != null && is.getItem() == ItemRegistry.CRAFTPATTERN.getItemInstance() && is.stackTagCompound != null) {
				ItemStack[] items = new ItemStack[10];
				NBTTagList nbttaglist = is.stackTagCompound.getTagList("Items", is.stackTagCompound.getId());
				for (int k = 0; k < nbttaglist.tagCount(); k++)				{
					NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(k);
					short byte0 = nbttagcompound.getShort("Slot");
					items[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
				}
				ItemStack out = items[9];//this.getRecipeOutput(items);
				if (out != null) {
					int size = inv[56] == null ? 0 : inv[56].stackSize;
					if (inv[56] == null || (ReikaItemHelper.matchStacks(out, inv[56]) && size+out.stackSize <= out.getMaxStackSize())) {
						boolean hasno = false;
						for (int k = 0; k < 9 && !hasno; k++) {
							if (items[k] != null) {
								if (this.hasIngredient(items[k]))
									this.removeOneIngredient(items[k]);
								else
									hasno = true;
							}
						}
						if (!hasno) {
							inv[56] = ReikaItemHelper.getSizedItemStack(out, size+out.stackSize);
							if (out.stackTagCompound != null)
								inv[56].stackTagCompound = (NBTTagCompound)out.stackTagCompound.copy();
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean hasIngredient(ItemStack is) {
		return ingredients.containsKey(is);
	}

	private void eatIngredients() {
		if (inv[54] != null) {
			this.feedInItem(inv[54]);
			inv[54] = null;
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j == 0 ? i == 55 : i == 56; //pull ingredients from bottom; products elsewhere
	}

	@Override
	public int getSizeInventory() {
		return 57; //54 patterns + 1 ingredients in + 1 ingredient feed out + 1 product feed out
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 54;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return par1 == 55 ? this.getFirstIngredientToRemove() : super.getStackInSlot(par1);
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack is) {
		if (par1 == 54) {
			this.feedInItem(is);
		}
		else if (par1 == 55) {
			if (is == null)
				this.removeFirstIngredient();
		}
		else {
			super.setInventorySlotContents(par1, is);
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.CRAFTER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}