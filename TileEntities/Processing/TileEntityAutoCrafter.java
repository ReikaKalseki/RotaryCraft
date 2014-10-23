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

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.ItemCollection;
import Reika.DragonAPI.Instantiable.Data.ItemHashMap;
import Reika.DragonAPI.Instantiable.Data.MENetwork;
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
	public int[] crafting = new int[18];
	private MENetwork network;

	private final StepTimer updateTimer = new StepTimer(50);

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		this.tickCraftingDisplay();
		if (power >= MINPOWER) {
			//this.eatIngredients();
			updateTimer.update();
			if (updateTimer.checkCap() && !world.isRemote) {
				this.buildCache();
			}

			if (continuous) {
				this.attemptAllSlotCrafting();
			}

			this.injectItems();
		}
	}

	private void injectItems() {
		if (ModList.APPENG.isLoaded()) {

		}
	}

	private void tickCraftingDisplay() {
		for (int i = 0; i < 18; i++) {
			crafting[i] = Math.max(crafting[i]-1, 0);
		}
	}

	private void buildCache() {
		ingredients.clear();
		TileEntity te = this.getAdjacentTileEntity(ForgeDirection.UP);
		if (te instanceof IInventory) {
			ingredients.addInventory((IInventory)te);
		}

		if (ModList.APPENG.isLoaded()) {
			network = MENetwork.getConnectedTo(this);
		}
	}

	public void triggerCraftingCycle(int slot) {
		if (power >= MINPOWER) {
			ItemStack out = this.getSlotRecipeOutput(slot);
			if (out != null)
				this.attemptSlotCrafting(slot);
		}
	}

	public void triggerCrafting(int slot, int amt) {
		if (power >= MINPOWER) {
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
	}

	private ItemStack getSlotRecipeOutput(int slot) {
		ItemStack is = inv[slot];
		if (is != null && is.getItem() == ItemRegistry.CRAFTPATTERN.getItemInstance() && is.stackTagCompound != null) {
			return ItemCraftPattern.getRecipeOutput(is);
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
			ArrayList<ItemStack>[] items = ItemCraftPattern.getItems(is);
			ItemStack out = ItemCraftPattern.getRecipeOutput(is);
			if (out != null) {
				//ReikaJavaLibrary.pConsole("crafting "+out+" from "+Arrays.toString(items));
				return this.tryCrafting(i, out, items);
			}
		}
		return false;
	}

	private boolean tryCrafting(int i, ItemStack out, ArrayList<ItemStack>[] items) {
		int slot = i+18;
		int size = inv[slot] != null ? inv[slot].stackSize : 0;
		if (inv[slot] == null || (ReikaItemHelper.matchStacks(out, inv[slot]) && size+out.stackSize <= out.getMaxStackSize())) {
			ItemHashMap<Integer> counts = new ItemHashMap(); //ingredient requirements
			ItemHashMap<ArrayList<ItemStack>> options = new ItemHashMap();
			for (int k = 0; k < 9; k++) {
				if (items[k] != null && !items[k].isEmpty()) {
					Integer req = counts.get(items[k].get(0));
					int val = req != null ? req.intValue() : 0;
					counts.put(items[k].get(0), val+1); // items[k].stackSize ?
					options.put(items[k].get(0), items[k]);
				}
			}
			for (ItemStack is : counts.keySet()) {
				int req = counts.get(is);
				int has = this.getAvailableIngredients(options.get(is));
				int missing = req-has;
				if (missing > 0) {
					//ReikaJavaLibrary.pConsole(options+":"+has+"/"+req);
					if (!this.tryCraftIntermediates(missing, options.get(is))) {
						//ReikaJavaLibrary.pConsole("missing "+missing+": "+options.get(is)+", needed "+req+", had "+has);
						return false;
					}
				}
			}
			this.craft(slot, size, out, counts);
			return true;
		}
		return false;
	}

	private int getAvailableIngredients(ArrayList<ItemStack> li) {
		int count = 0;
		ItemHashMap<Long> map = network != null ? network.getMEContents() : null;
		//ReikaJavaLibrary.pConsole(map);
		for (ItemStack is : li) {
			//ReikaJavaLibrary.pConsole(is+":"+ingredients.getItemCount(is)+" > "+ingredients);
			count += ingredients.getItemCount(is);
			if (map != null) {
				Long me = map.get(is);
				count += me != null ? me.intValue() : 0;
			}
		}

		return count;
	}

	private boolean tryCraftIntermediates(int num, ArrayList<ItemStack> li) {
		int run = 0;
		HashMap<Integer, Integer> ranSlots = new HashMap();
		for (ItemStack is : li) {
			for (int i = 0; i < 18 && run < num; i++) {
				ItemStack out = this.getSlotRecipeOutput(i);
				//ReikaJavaLibrary.pConsole(i+":"+out+" & "+is);
				if (out != null && ReikaItemHelper.matchStacks(is, out)) {
					//ReikaJavaLibrary.pConsole("attempting slot "+i+", because "+out+" matches "+is);
					while (run < num && this.attemptSlotCrafting(i)) {
						run += out.stackSize;
						Integer get = ranSlots.get(i);
						int val = get != null ? get.intValue() : 0;
						ranSlots.put(i, Math.min(num, val+out.stackSize));
					}
				}
			}
			if (run >= num)
				break;
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

	private void craft(int slot, int size, ItemStack out, ItemHashMap<Integer> counts) {
		inv[slot] = ReikaItemHelper.getSizedItemStack(out, size+out.stackSize);
		if (out.stackTagCompound != null)
			inv[slot].stackTagCompound = (NBTTagCompound)out.stackTagCompound.copy();
		for (ItemStack is : counts.keySet()) {
			int req = counts.get(is);
			if (is.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
				int dec = req;
				for (int k = 0; k < OreDictionary.WILDCARD_VALUE; k++) {
					ItemStack is2 = new ItemStack(is.getItem(), 1, k);
					int rem = ingredients.removeXItems(is2, req);
					dec -= rem;
					if (dec > 0) {
						int diff = req-rem;
						if (diff > 0 && network != null) {
							dec -= network.removeFromMESystem(is2, diff);
						}
					}
					if (dec <= 0)
						break;
				}
			}
			else {
				int rem = ingredients.removeXItems(is, req);
				int diff = req-rem;
				if (diff > 0 && network != null) {
					network.removeFromMESystem(is, diff);
				}
			}
		}
		crafting[slot-18] = 5;
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
