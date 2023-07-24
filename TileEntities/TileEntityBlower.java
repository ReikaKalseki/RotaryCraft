/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
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

import Reika.ChromatiCraft.API.Interfaces.WorldRift;
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
import powercrystals.minefactoryreloaded.api.IDeepStorageUnit;

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
	public MachineRegistry getTile() {
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

		if (ReikaInventoryHelper.isAutomatableInventory(source)) {
			TileEntity target = this.getAdjacentTileEntity(dir);
			if (target instanceof WorldRift)
				target = this.getRelayedTarget(target, dir);
			if (target != null) {
				WorldLocation tg = new WorldLocation(target);

				ArrayList<TileEntity> li = new ArrayList();
				while (target instanceof TileEntityBlower) {
					TileEntityBlower te = (TileEntityBlower)target;
					dir = te.getFacingDir();
					target = te.getAdjacentTileEntity(dir);
					tg = tg.move(dir, 1);
					if (target instanceof WorldRift) {
						target = this.getRelayedTarget(target, dir);
						tg = new WorldLocation(target);
					}

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

				InventoryType src = this.getTypeForInventory(source);

				if (ReikaInventoryHelper.isAutomatableInventory(target)) {
					InventoryType tgt = this.getTypeForInventory(target);
					if (this.tryPatternInsertion((IInventory)source, target)) {

					}
					else {
						//ReikaJavaLibrary.pConsole(map);
						this.transferItems(source, target, src, tgt, from, dir);
					}
					//ReikaJavaLibrary.pConsole(map, Side.SERVER);
				}
				else if (target == null && ConfigRegistry.BLOWERSPILL.getState() && tg.isEmpty()) {
					this.dumpItems(source, src, tg, from);
				}
			}
		}
	}

	private TileEntity getRelayedTarget(TileEntity te, ForgeDirection dir) {
		WorldRift wr = (WorldRift)te;
		TileEntity te2 = wr.getTileEntityFrom(dir);
		return te2 != null ? te2 : te;
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

	private void transferItems(TileEntity source, TileEntity target, InventoryType src, InventoryType tgt, ForgeDirection from, ForgeDirection dir) {
		int max = this.getNumberTransferrableItems();
		if (max <= 0)
			return;

		if (src == InventoryType.DSU) {
			IDeepStorageUnit dsu = (IDeepStorageUnit)source;
			ItemStack is = dsu.getStoredItemType();
			if (is != null && this.isItemTransferrable(is)) {
				int has = src.removeItem(source, max, -1, false);
				int added = tgt.insertItem(target, is, has, dir);
				src.removeItem(source, added, -1, true);
			}
		}
		else {
			HashMap<Integer, ItemStack> map = src.getMovableSlots(from, source);
			for (int slot : map.keySet()) {
				ItemStack is = map.get(slot);
				if (max > 0 && this.isItemTransferrable(is)) {
					int has = src.removeItem(source, max, slot, false);
					int added = tgt.insertItem(target, is, has, dir);
					src.removeItem(source, added, slot, true);
					max -= added;
				}
			}
		}
	}

	private void dumpItems(TileEntity source, InventoryType src, WorldLocation loc, ForgeDirection from) {
		int max = this.getNumberTransferrableItems();
		if (max <= 0)
			return;

		if (src == InventoryType.DSU) {
			IDeepStorageUnit dsu = (IDeepStorageUnit)source;
			ItemStack is = dsu.getStoredItemType();
			if (is != null && this.isItemTransferrable(is)) {
				int has = src.removeItem(source, max, -1, true);
				this.dropItem(is, has, loc);
			}
		}
		else {
			HashMap<Integer, ItemStack> map = src.getMovableSlots(from, source);
			for (int slot : map.keySet()) {
				ItemStack is = map.get(slot);
				if (max > 0 && this.isItemTransferrable(is)) {
					int has = src.removeItem(source, max, slot, true);
					this.dropItem(is, has, loc);
					max -= has;
				}
			}
		}
	}

	private void dropItem(ItemStack is, int amt, WorldLocation loc) {
		for (int i = 0; i < amt; i++)
			loc.dropItem(ReikaItemHelper.getSizedItemStack(is, 1), 2+rand.nextDouble()*4);
	}

	private InventoryType getTypeForInventory(TileEntity te) {
		for (InventoryType t : InventoryType.values()) {
			if (t.isValid(te))
				return t;
		}
		return null;
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

	public static enum InventoryType {
		CHEST(),
		DSU(),
		//PLAYER(),
		;

		public boolean isValid(TileEntity te) {
			switch(this) {
				case CHEST:
					return te instanceof IInventory && !DSU.isValid(te);
				case DSU:
					return InterfaceCache.DSU.instanceOf(te);
			}
			return false;
		}

		public HashMap<Integer, ItemStack> getMovableSlots(ForgeDirection dir, TileEntity te) {
			switch(this) {
				case CHEST:
					return ReikaInventoryHelper.getLocatedTransferrables(dir, (IInventory)te);
				case DSU:
					return null;//ReikaJavaLibrary.makeMapOf(-1, ((IDeepStorageUnit)te).getStoredItemType());
			}
			return null;
		}

		public int removeItem(TileEntity te, int amt, int slot, boolean remove) {
			switch(this) {
				case CHEST:
					IInventory ii = (IInventory)te;
					int items = 0;
					if (remove) {
						boolean flag = true;
						while(flag && amt > 0) {
							ReikaInventoryHelper.decrStack(slot, ii, 1);
							amt--;
							flag = ii.getStackInSlot(slot) != null;
							items++;
						}
						return items;
					}
					else {
						return ii.getStackInSlot(slot) != null ? Math.min(ii.getStackInSlot(slot).stackSize, amt) : 0;
					}
				case DSU:
					IDeepStorageUnit dsu = (IDeepStorageUnit)te;
					int has = dsu.getStoredItemType().stackSize;
					int rem = Math.min(amt, has);
					if (remove)
						dsu.setStoredItemCount(has-rem);
					return rem;
			}
			return 0;
		}

		public int insertItem(TileEntity te, ItemStack is, int amt, ForgeDirection dir) {
			switch(this) {
				case CHEST:
					IInventory ii = (IInventory)te;
					int items = 0;
					boolean flag = true;
					is = ReikaItemHelper.getSizedItemStack(is, 1);
					while(flag && amt > 0) {
						//ReikaJavaLibrary.pConsole("Attempting to add "+is+" to "+target);
						if (this.addToIInv(is, ii, dir)) {
							amt--;
							items++;
							//ReikaJavaLibrary.pConsole("Success");
						}
						else {
							flag = false;
							//ReikaJavaLibrary.pConsole("Failure");
						}
					}
					return items;
				case DSU:
					IDeepStorageUnit dsu = (IDeepStorageUnit)te;
					int has = dsu.getStoredItemType().stackSize;
					int space = dsu.getMaxStoredCount()-has;
					int add = Math.min(amt, space);
					dsu.setStoredItemCount(has+add);
					return add;
			}
			return 0;
		}

		private boolean addToIInv(ItemStack is, IInventory ii, ForgeDirection dir) {
			if (ii instanceof ISidedInventory) {
				for (int k = 0; k < ii.getSizeInventory(); k++) {
					if (((ISidedInventory)ii).canInsertItem(k, is, dir.getOpposite().ordinal())) {
						if (ReikaInventoryHelper.addToIInv(is, ii)) {
							return true;
						}
					}
				}
			}
			else {
				if (ReikaInventoryHelper.addToIInv(is, ii)) {
					return true;
				}
			}
			return false;
		}
	}

}
