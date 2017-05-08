/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModRegistry.InterfaceCache;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import appeng.api.implementations.ICraftingPatternItem;
import cpw.mods.fml.relauncher.Side;

public class TileEntityBlower extends TileEntityPowerReceiver {

	private ForgeDirection facing;

	public ItemStack[] matchingItems = new ItemStack[18];
	public boolean isWhitelist = false;
	public boolean useOreDict = true;
	public boolean checkMeta = false;
	public boolean checkNBT = false;

	private static Method getPatterns;
	private static Field dualityField;

	static {
		if (ModList.APPENG.isLoaded()) {
			try {
				Class c = Class.forName("appeng.helpers.DualityInterface");
				getPatterns = c.getDeclaredMethod("getPatterns");
				getPatterns.setAccessible(true);

				c = InterfaceCache.MEINTERFACE.getClassType();
				dualityField = c.getDeclaredField("duality");
				dualityField.setAccessible(true);
			}
			catch (Exception e) {
				RotaryCraft.logger.logError("Could not add Item Pump AE pattern interfacing!");
				e.printStackTrace();
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.APPENG, e);
			}
		}
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BLOWER;
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
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getIOSides(world, x, y, z, meta);
		this.getSummativeSidedPower();

		if (MINPOWER > power || MINSPEED > omega)
			return;
		if (world.isRemote)
			return;

		ForgeDirection dir = this.getFacingDir();
		ForgeDirection from = dir.getOpposite();

		TileEntity source = this.getAdjacentTileEntity(from);

		if (source instanceof IInventory) {
			TileEntity target = this.getAdjacentTileEntity(dir);
			if (target != null) {
				WorldLocation tg = new WorldLocation(target);

				ArrayList<TileEntity> li = new ArrayList();
				while (target instanceof TileEntityBlower) {
					TileEntityBlower te = (TileEntityBlower)target;
					target = te.getAdjacentTileEntity(te.getFacingDir());
					tg = tg.move(te.getFacingDir(), 1);

					if (li.contains(target))
						return;
					else
						li.add(target);

					if (this.equals(target)) { //to prevent stackoverflow from loops, because some idiot is going to try
						return;
					}
					if (source.equals(target)) { //to prevent dupe glitch, and because this would be stupid to do
						return;
					}
				}

				//this.printTEs(source, target);

				if (target instanceof IInventory) {
					if (InterfaceCache.DSU.instanceOf(target)) {
						this.transferItems(null, (IInventory)source, (IInventory)target, dir);
					}
					else if (this.tryPatternInsertion((IInventory)source, target)) {

					}
					else {
						HashMap<Integer, ItemStack> map = ReikaInventoryHelper.getLocatedTransferrables(from, (IInventory)source);
						if (map != null && !map.isEmpty())
							this.transferItems(map, (IInventory)source, (IInventory)target, dir);
					}
					//ReikaJavaLibrary.pConsole(map, Side.SERVER);
				}
				else if (target == null && ConfigRegistry.BLOWERSPILL.getState()) {
					if (tg.isEmpty()) {
						if (InterfaceCache.DSU.instanceOf(source)) {
							this.dumpItems(null, (IInventory)source, tg);
						}
						else {
							HashMap<Integer, ItemStack> map = ReikaInventoryHelper.getLocatedTransferrables(from, (IInventory)source);
							if (map != null && !map.isEmpty()) {
								this.dumpItems(map, (IInventory)source, tg);
							}
						}
					}
				}
			}
		}
	}

	private boolean tryPatternInsertion(IInventory source, TileEntity target) {
		if (InterfaceCache.MEINTERFACE.instanceOf(target)) {
			for (int i = 0; i < source.getSizeInventory(); i++) {
				ItemStack is = source.getStackInSlot(i);
				if (is != null && is.getItem() instanceof ICraftingPatternItem) {
					try {
						IInventory patterns = this.getPatterns(target);
						if (ReikaInventoryHelper.addToIInv(is, patterns))
							return true;
					}
					catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return false;
	}

	@ModDependent(ModList.APPENG)
	public static IInventory getPatterns(TileEntity target) throws Exception {
		return (IInventory)getPatterns.invoke(dualityField.get(target));
	}

	private void printTEs(TileEntity source, TileEntity target) {
		if (source == null && target == null)
			ReikaJavaLibrary.pConsole("null >> null", Side.SERVER);
		else if (source == null)
			ReikaJavaLibrary.pConsole("null >> "+target.getClass().getSimpleName(), Side.SERVER);
		else if (target == null)
			ReikaJavaLibrary.pConsole(source.getClass().getSimpleName()+" >> null", Side.SERVER);
		else
			ReikaJavaLibrary.pConsole(source.getClass().getSimpleName()+" >> "+target.getClass().getSimpleName(), Side.SERVER);
	}

	private int getNumberTransferrableItems() {
		return (int)(power/1024);
	}

	/** Supply null map for custom handling like DSU. */
	private void transferItems(HashMap<Integer, ItemStack> map, IInventory source, IInventory target, ForgeDirection dir) {
		int max = this.getNumberTransferrableItems();
		if (max <= 0)
			return;

		if (map == null) {
			IDeepStorageUnit dsu = (IDeepStorageUnit)source;
			ItemStack is = dsu.getStoredItemType();
			if (is != null && this.isItemTransferrable(is)) {
				int add = Math.min(max, is.stackSize);
				int rem = this.addItemToInventory(is, target,  null, -1, add, dir);
				dsu.setStoredItemCount(is.stackSize-rem);
			}
		}
		else {
			for (int slot : map.keySet()) {
				ItemStack is = map.get(slot);
				if (this.isItemTransferrable(is)) {
					int rem = this.addItemToInventory(is, target, source, slot, max, dir);
				}
			}
		}
	}

	/** Supply null map for custom handling like DSU. */
	private void dumpItems(HashMap<Integer, ItemStack> map, IInventory source, WorldLocation loc) {
		int max = this.getNumberTransferrableItems();
		if (max <= 0)
			return;

		if (map == null) {
			IDeepStorageUnit dsu = (IDeepStorageUnit)source;
			ItemStack is = dsu.getStoredItemType();
			if (is != null && this.isItemTransferrable(is)) {
				int drop = Math.min(max, is.stackSize);
				int rem = this.dropItem(is, null, -1, drop, loc);
				dsu.setStoredItemCount(is.stackSize-rem);
			}
		}
		else {
			for (int slot : map.keySet()) {
				ItemStack is = map.get(slot);
				if (this.isItemTransferrable(is)) {
					this.dropItem(is, source, slot, max, loc);
				}
			}
		}
	}

	/** Supply null source for custom handling like DSU. */
	private int dropItem(ItemStack is, IInventory source, int slot, int amt, WorldLocation loc) {
		int items = 0;
		int maxadd = Math.min(amt-items, is.getMaxStackSize());
		for (int i = 0; i < maxadd; i++) {
			if (source == null || (source.getStackInSlot(slot) != null && source.getStackInSlot(slot).stackSize > 0)) {
				loc.dropItem(ReikaItemHelper.getSizedItemStack(is, 1), 2+rand.nextDouble()*4);
				if (source != null)
					ReikaInventoryHelper.decrStack(slot, source, 1);
				items += 1;
			}
			if (items >= amt)
				return amt;
		}
		return items;
	}

	/** Supply null source for custom handling like DSU. */
	private int addItemToInventory(ItemStack is, IInventory ii, IInventory source, int slot, int amt, ForgeDirection dir) {
		int items = 0;
		int size = ii.getSizeInventory();
		int maxadd = Math.min(amt-items, Math.min(is.getMaxStackSize(), ii.getInventoryStackLimit()));
		ItemStack one = ReikaItemHelper.getSizedItemStack(is, 1);
		for (int i = 0; i < maxadd; i++) {
			for (int k = 0; k < size; k++) {
				if (source == null || (source.getStackInSlot(slot) != null && source.getStackInSlot(slot).stackSize > 0)) {
					if (ii instanceof ISidedInventory) {
						if (((ISidedInventory)ii).canInsertItem(k, is, dir.getOpposite().ordinal())) {
							if (ReikaInventoryHelper.addToIInv(one, ii)) {
								if (source != null)
									ReikaInventoryHelper.decrStack(slot, source, 1);
								items += 1;
							}
						}
					}
					else {
						if (ReikaInventoryHelper.addToIInv(one, ii)) {
							if (source != null)
								ReikaInventoryHelper.decrStack(slot, source, 1);
							items += 1;
						}
					}
					if (items >= amt)
						return amt;
				}
			}
		}
		return items;
	}

	public boolean isIntake() {
		return this.getAdjacentTileEntity(this.getFacingDir().getOpposite()) instanceof IInventory;
	}

	private void getIOSides(World world, int x, int y, int z, int meta) {
		switch(meta) {
			case 4:
				facing = ForgeDirection.DOWN;
				break;
			case 5:
				facing = ForgeDirection.UP;
				break;
			case 3:
				facing = ForgeDirection.NORTH;
				break;
			case 1:
				facing = ForgeDirection.WEST;
				break;
			case 2:
				facing = ForgeDirection.SOUTH;
				break;
			case 0:
				facing = ForgeDirection.EAST;
				break;
		}
	}

	/** The side we are emitting items to */
	public ForgeDirection getFacingDir() {
		return facing != null ? facing : ForgeDirection.UP;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("ore", useOreDict);
		NBT.setBoolean("metac", checkMeta);
		NBT.setBoolean("cnbt", checkNBT);
		NBT.setBoolean("white", isWhitelist);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < matchingItems.length; i++)
		{
			if (matchingItems[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				matchingItems[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isWhitelist = NBT.getBoolean("white");
		checkMeta = NBT.getBoolean("metac");
		checkNBT = NBT.getBoolean("cnbt");
		useOreDict = NBT.getBoolean("ore");

		NBTTagList nbttaglist = NBT.getTagList("Items", NBTTypes.COMPOUND.ID);
		matchingItems = new ItemStack[18];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < matchingItems.length)
			{
				matchingItems[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	public boolean isItemTransferrable(ItemStack is) {
		boolean match = this.isItemStackMatched(is);
		return isWhitelist ? match : !match;
	}

	private boolean isItemStackMatched(ItemStack is) {
		for (int i = 0; i < matchingItems.length; i++) {
			ItemStack is1 = matchingItems[i];
			if (is1 != null) {
				if (this.doStacksMatch(is, is1))
					return true;
			}
		}
		return false;
	}

	private boolean doStacksMatch(ItemStack is, ItemStack is1) {
		if (checkMeta && is.getItemDamage() != is1.getItemDamage())
			return false;
		if (checkNBT && !ItemStack.areItemStackTagsEqual(is, is1))
			return false;
		if (ReikaItemHelper.matchStacks(is, is1))
			return true;
		if (useOreDict) {
			int oreID = OreDictionary.getOreID(is);
			if (oreID > -1 && oreID == OreDictionary.getOreID(is1))
				return true;
		}
		return is.getItem() == is1.getItem();
	}

	@Override
	public int getTextureStateForSide(int s) {
		TileEntity source = this.getAdjacentTileEntity(this.getFacingDir().getOpposite());
		if (source instanceof TileEntityBlower) {
			TileEntityBlower te = (TileEntityBlower)source;
			return te.getFacingDir() != this.getFacingDir() ? 0 : 1;
		}
		return 0;
	}

}
