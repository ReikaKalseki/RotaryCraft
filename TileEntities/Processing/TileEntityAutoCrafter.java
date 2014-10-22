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

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.ItemCollection;
import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityAutoCrafter extends InventoriedPowerReceiver {

	public boolean continuous = true;
	//private final HashMap<ItemStack, Integer> ingredients = new HashMap();
	private final ItemCollection ingredients = new ItemCollection();
	public boolean[] crafting = new boolean[18];

	private final StepTimer updateTimer = new StepTimer(50);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		this.resetCraftingDisplay();
		if (power >= MINPOWER) {
			//this.eatIngredients();
			updateTimer.update();
			if (updateTimer.checkCap()) {
				//this.buildCache();
			}

			if (continuous) {
				this.attemptAllSlotCrafting();
			}
		}
	}

	private void resetCraftingDisplay() {
		crafting = new boolean[18];
	}

	private void buildCache() {
		ingredients.clear();
		TileEntity te = this.getAdjacentTileEntity(ForgeDirection.UP);
		if (te instanceof IInventory) {
			ingredients.addInventory((IInventory)te);
		}
	}

	public void triggerCraftingCycle(int slot) {
		ItemStack out = this.getSlotRecipeOutput(slot);
		if (out != null)
			this.attemptSlotCrafting(slot);
	}

	public void triggerCrafting(int slot, int amt) {
		ItemStack out = this.getSlotRecipeOutput(slot);
		if (out != null) {
			int space = inv[slot+18].getMaxStackSize()-inv[slot+18].stackSize;
			int tocraft = ReikaMathLibrary.multiMin(amt, this.getInventoryStackLimit(), out.getMaxStackSize(), space);
			int cycles = tocraft/out.stackSize;
			for (int i = 0; i < cycles; i++) {
				boolean flag = this.attemptSlotCrafting(slot);
				if (!flag)
					break;
			}
		}
	}

	private ItemStack getSlotRecipeOutput(int slot) {
		ItemStack is = inv[slot];
		if (is != null && is.getItem() == ItemRegistry.CRAFTPATTERN.getItemInstance() && is.stackTagCompound != null) {
			ItemStack[] items = ItemCraftPattern.getItems(is);
			return items[9];
		}
		return null;
	}

	private void attemptAllSlotCrafting() {
		for (int i = 0; i < 18; i++) {
			this.attemptSlotCrafting(i);
		}
	}

	private boolean attemptSlotCrafting(int i) {
		ItemStack is = inv[i];
		if (is != null && is.getItem() == ItemRegistry.CRAFTPATTERN.getItemInstance() && is.stackTagCompound != null) {
			ItemStack[] items = ItemCraftPattern.getItems(is);
			ItemStack out = items[9];//this.getRecipeOutput(items);
			if (out != null) {
				return this.tryCrafting(i, out, items);
			}
		}
		return false;
	}

	private boolean tryCrafting(int i, ItemStack out, ItemStack[] items) {
		int slot = i+18;
		int size = inv[slot] != null ? inv[slot].stackSize : 0;
		if (inv[slot] == null || (ReikaItemHelper.matchStacks(out, inv[slot]) && size+out.stackSize <= out.getMaxStackSize())) {
			ItemHashMap<Integer> counts = new ItemHashMap();
			for (int k = 0; k < 9; k++) {
				if (items[k] != null) {
					Integer req = counts.get(items[k]);
					int val = req != null ? req.intValue() : 0;
					counts.put(items[k], val+items[k].stackSize);
				}
			}
			for (ItemStack is : counts.keySet()) {
				int req = counts.get(is);
				int has = ingredients.getItemCountWithOreEquivalence(is);
				int missing = req-has;
				if (missing > 0) {
					if (!this.tryCraftIntermediates(missing, is)) {
						return false;
					}
				}
			}
			this.craft(slot, size, out, counts);
			return true;
		}
		return false;
	}

	private boolean tryCraftIntermediates(int num, ItemStack is) {
		int run = 0;
		HashMap<Integer, Integer> ranSlots = new HashMap();
		for (int i = 0; i < 18; i++) {
			ItemStack out = this.getSlotRecipeOutput(i);
			//ReikaJavaLibrary.pConsole(i+":"+out+" & "+is);
			if (out != null && this.matchStacks(is, out)) {
				while (run < num && this.attemptSlotCrafting(i)) {
					run += out.stackSize;
					Integer get = ranSlots.get(i);
					int val = get != null ? get.intValue() : 0;
					ranSlots.put(i, Math.min(num, val+out.stackSize));
				}
			}
		}
		if (run >= num) {
			for (int slot : ranSlots.keySet()) {
				inv[slot+18].stackSize -= ranSlots.get(slot);
				if (inv[slot+18].stackSize <= 0)
					inv[slot+18] = null;
			}
			//ReikaJavaLibrary.pConsole(ranSlots+"/"+num+" for "+is);
			return true;
		}
		//ReikaJavaLibrary.pConsole("false, "+ranSlots+"/"+num);
		return false;
	}

	private boolean matchStacks(ItemStack is, ItemStack out) {
		return ReikaItemHelper.matchStacks(is, out) || Arrays.equals(OreDictionary.getOreIDs(out), OreDictionary.getOreIDs(is));
	}

	private void craft(int slot, int size, ItemStack out, ItemHashMap<Integer> counts) {
		inv[slot] = ReikaItemHelper.getSizedItemStack(out, size+out.stackSize);
		if (out.stackTagCompound != null)
			inv[slot].stackTagCompound = (NBTTagCompound)out.stackTagCompound.copy();
		for (ItemStack is : counts.keySet()) {
			int req = counts.get(is);
			ingredients.removeXItems(is, req);
		}
		this.markDirty();
	}

	private boolean hasIngredient(ItemStack is) {
		return ingredients.hasItem(is);
	}

	/*
	private boolean hasIngredient(ItemStack is) {
		return ingredients.containsKey(is);
	}

	private void eatIngredients() {
		if (inv[54] != null) {
			this.feedInItem(inv[54]);
			inv[54] = null;
		}
	}
	 */
	@Override
	public boolean canExtractItem(int i, ItemStack is, int j) {
		return i >= 18;//j == 0 ? i == 55 : i == 56; //pull ingredients from bottom; products elsewhere
	}

	@Override
	public int getSizeInventory() {
		return 46;//57; //54 patterns + 1 ingredients in + 1 ingredient feed out + 1 product feed out
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return i < 18 && ItemRegistry.CRAFTPATTERN.matchItem(is);//i == 54;
	}
	/*
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
	 */
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

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("cont", continuous);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		continuous = NBT.getBoolean("cont");
	}

}
