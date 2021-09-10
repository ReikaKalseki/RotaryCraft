/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.Trackers.ReflectiveFailureTracker;
import Reika.DragonAPI.Exception.InstallationException;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.ReikaMystcraftHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.GregOreHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.HardOresHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.IC2Handler;
import Reika.DragonAPI.ModInteract.ItemHandlers.MystCraftHandler;
import Reika.DragonAPI.ModInteract.ItemHandlers.ThaumItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.MultiOperational;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.DurationRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.Recipes;
import thaumcraft.api.internal.WeightedRandomLoot;


public class TileEntityDropProcessor extends InventoriedPowerReceiver implements ConditionalOperation, MultiOperational, EnchantableMachine {

	private static final ArrayList<DropProcessing> dropHandlers = new ArrayList();
	private static final NoProcessing INVALID = new NoProcessing();
	private static final ItemHashMap<DropProcessing> itemMap = new ItemHashMap();

	private final ArrayList<ItemStack> overflow = new ArrayList();

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.fortune);

	public int dropProcessTime;

	@Override
	public boolean canExtractItem(int slot, ItemStack is, int side) {
		return slot > 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		if (world.isRemote)
			return;

		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);
		boolean flag1 = false;
		if (power >= MINPOWER && torque >= MINTORQUE) {
			int n = this.getNumberConsecutiveOperations();
			for (int i = 0; i < n; i++)
				flag1 |= this.doOperation(world, x, y, z, n > 1);
		}
		else {
			dropProcessTime = 0;
		}

		if (flag1)
			this.markDirty();
	}

	private boolean doOperation(World world, int x, int y, int z, boolean multiple) {
		if (inv[0] != null) {
			if (inv[1] == null || this.canContinueProcessingWithOutput()) {
				if (overflow.isEmpty()) {
					if (this.isProcessable(inv[0])) {
						dropProcessTime++;
						if (multiple || dropProcessTime >= this.getOperationTime()) {
							dropProcessTime = 0;
							this.processItem(world, x, y, z);
						}
						return true;
					}
					else {
						dropProcessTime = 0;
						return false;
					}
				}
				else {
					if (inv[1] == null) {
						inv[1] = overflow.remove(0);
					}
					else {

					}
					dropProcessTime = 0;
					return false;
				}
			}
			else {
				dropProcessTime = Math.max(0, Math.min(dropProcessTime, this.getOperationTime()-1));//0;
				return false;
			}
		}
		else {
			dropProcessTime = 0;
			return false;
		}
	}

	private boolean canContinueProcessingWithOutput() {
		DropProcessing h = getHandler(inv[0]);
		if (h.allowsStacking()) {
			Collection<ItemStack> c = this.runHandler(h, inv[0]);
			if (c.size() != 1) {
				return false;
			}
			ItemStack is = c.iterator().next();
			if (ReikaItemHelper.matchStacks(is, inv[1]) && is.stackSize+inv[1].stackSize <= Math.min(this.getInventoryStackLimit(), is.getMaxStackSize())) {
				return true;
			}
		}
		return false;
	}

	private Collection<ItemStack> runHandler(DropProcessing dp, ItemStack in) {
		try {
			int fortune = enchantments.getEnchantment(Enchantment.fortune);
			EntityPlayer ep = this.getPlacer();
			return dp.generateItems(worldObj, xCoord, yCoord, zCoord, fortune, ep, rand, in);
		}
		catch (Exception e) {
			e.printStackTrace();
			this.writeError(e);
			return new ArrayList();
		}
	}

	public static DropProcessing getHandler(ItemStack is) {
		if (is == null)
			return INVALID;
		DropProcessing d = itemMap.get(is);
		if (d != null) {
			return d;
		}
		for (DropProcessing d2 : dropHandlers) {
			if (d2.isValidItem(is)) {
				d = d2;
				itemMap.put(is, d);
				return d;
			}
		}
		itemMap.put(is, INVALID);
		return INVALID;
	}

	public static boolean isProcessable(ItemStack is) {
		return getHandler(is) != INVALID;
	}

	@SideOnly(Side.CLIENT)
	public static Collection<ItemStack> getAllProcessableItems() {
		ArrayList<ItemStack> li = new ArrayList();
		for (DropProcessing p : dropHandlers) {
			li.addAll(p.getAllInputsForDisplay());
		}
		return li;
	}

	@SideOnly(Side.CLIENT)
	public static Collection<DropProcessing> getAllHandlersProducing(ItemStack is) {
		ArrayList<DropProcessing> li = new ArrayList();
		for (DropProcessing p : dropHandlers) {
			if (!(p instanceof BlockDropProcessing)) //glitchy
				if (ReikaItemHelper.listContainsItemStack(p.getPotentialOutputsForDisplay(), is, false))
					li.add(p);
		}
		return li;
	}

	@SideOnly(Side.CLIENT)
	public static Collection<DropProcessing> getAllProcessors() {
		return Collections.unmodifiableCollection(dropHandlers);
	}

	private void processItem(World world, int x, int y, int z) {
		DropProcessing dp = this.getHandler(inv[0]);
		Collection<ItemStack> c = this.runHandler(dp, inv[0]);
		ArrayList<ItemStack> li = ReikaItemHelper.collateItemList(c);
		if (!li.isEmpty()) {
			ReikaInventoryHelper.addOrSetStack(li.remove(0), inv, 1);
			overflow.addAll(li);
		}
		ReikaInventoryHelper.decrStack(0, inv);
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isProcessable(is) && slot == 0;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.DROPS;
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
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setTag("enchants", enchantments.writeToNBT());

		NBTTagList li = new NBTTagList();
		NBT.setTag("extra", li);
		for (ItemStack is : overflow) {
			NBTTagCompound tag = new NBTTagCompound();
			is.writeToNBT(tag);
			li.appendTag(tag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));

		NBTTagList li = NBT.getTagList("extra", NBTTypes.COMPOUND.ID);
		overflow.clear();
		for (Object o : li.tagList) {
			NBTTagCompound tag = (NBTTagCompound)o;
			ItemStack is = ItemStack.loadItemStackFromNBT(tag);
			if (is != null)
				overflow.add(is);
		}
	}

	@Override
	public int getOperationTime() {
		return DurationRegistry.DROPS.getOperationTime(omega);
	}

	@Override
	public int getNumberConsecutiveOperations() {
		return DurationRegistry.DROPS.getNumberOperations(omega);
	}

	@Override
	public boolean areConditionsMet() {
		return inv[0] != null && ReikaItemHelper.isBlock(inv[0]) && inv[1] == null && overflow.isEmpty();
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "Missing Items/Full Output";
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		dropProcessTime = NBT.getShort("CookTime");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setShort("CookTime", (short)dropProcessTime);
	}

	public int getProgressScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.tickcount);
		return (dropProcessTime * par1)/2 / this.getOperationTime();
	}

	public void dropCache() {
		ReikaItemHelper.dropItems(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, overflow);
		overflow.clear();
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}

	static {
		new BlockDropProcessing().register();
		new ScrapBoxProcessing().register();
		new ThaumBagProcessing().register();
		new CratedItemProcessing().register();
		MystFolderProcessing.instance.register();
		new MystNotebookProcessing().register();
		new GregCrateProcessing().register();
		new HardOreProcessing().register();
	}

	public static abstract class DropProcessing {

		final void register() {
			if (this.isLoadable()) {
				this.create();
				dropHandlers.add(this);
			}
		}

		protected void create() {

		}

		protected abstract boolean isLoadable();
		protected abstract boolean allowsStacking();
		public abstract boolean isValidItem(ItemStack is);
		protected abstract Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception;

		@SideOnly(Side.CLIENT)
		public abstract ArrayList<ItemStack> getAllInputsForDisplay();
		@SideOnly(Side.CLIENT)
		/** Add an NBT tag 'dropTooltip' as a string for controlling display tooltips. */
		public abstract List<ItemStack> getOutputsOfInputForDisplay(ItemStack src);
		@SideOnly(Side.CLIENT)
		/** Add an NBT tag 'dropTooltip' as a string for controlling display tooltips. */
		public abstract Collection<ItemStack> getPotentialOutputsForDisplay();

	}

	/** Used to flag unprocessable items. Not registered to the code. */
	private static class NoProcessing extends DropProcessing {

		@Override
		protected boolean isLoadable() {
			return true;
		}

		@Override
		protected boolean allowsStacking() {
			return false;
		}

		@Override
		public boolean isValidItem(ItemStack is) {
			return true;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			return new ArrayList();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			return new ArrayList();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			return new ArrayList();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			return this.getOutputsOfInputForDisplay(null);
		}

	}

	private static class BlockDropProcessing extends DropProcessing {

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			Block b = Block.getBlockFromItem(src.getItem());
			ArrayList<ItemStack> li = b.getDrops(world, x, y, z, src.getItemDamage(), fortune);
			return li;
		}

		@Override
		protected boolean allowsStacking() {
			return false;
		}

		@Override
		public boolean isValidItem(ItemStack is) {
			return ReikaItemHelper.isBlock(is);
		}

		@Override
		protected boolean isLoadable() {
			return true;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			ArrayList<ItemStack> li = new ArrayList();
			for (Object o : Block.blockRegistry.getKeys()) {
				Block b = (Block)Block.blockRegistry.getObject(o);
				if (!ReikaBlockHelper.alwaysDropsSelf(b) && b.getItemDropped(0, rand, 0) != null && b.getItemDropped(0, rand, 0) != Item.getItemFromBlock(Blocks.fire)) {
					Item i = Item.getItemFromBlock(b);
					if (i != null) {
						try {
							b.getSubBlocks(i, b.getCreativeTabToDisplayOn(), li);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			return li;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			Block b = Block.getBlockFromItem(src.getItem());
			Random rand = new Random(System.currentTimeMillis()/2000);
			return ReikaJavaLibrary.makeListFrom(new ItemStack(b.getItemDropped(src.getItemDamage(), rand, 0), b.quantityDropped(src.getItemDamage(), 0, rand), b.damageDropped(src.getItemDamage())));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			Collection<ItemStack> li = new ArrayList();
			for (ItemStack is : this.getAllInputsForDisplay()) {
				li.addAll(this.getOutputsOfInputForDisplay(is));
			}
			return li;
		}

	}

	private static class ScrapBoxProcessing extends DropProcessing {

		@Override
		public boolean isValidItem(ItemStack is) {
			return IC2Handler.IC2Stacks.SCRAPBOX.match(is);
		}

		@Override
		protected boolean allowsStacking() {
			return false;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			return ReikaJavaLibrary.makeListFrom(Recipes.scrapboxDrops.getDrop(src, false));
		}

		@Override
		protected boolean isLoadable() {
			return ModList.IC2.isLoaded();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			return ReikaJavaLibrary.makeListFrom(IC2Handler.IC2Stacks.SCRAPBOX.getItem());
		}

		@Override
		@SideOnly(Side.CLIENT)
		public List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			Map<ItemStack, Float> map = Recipes.scrapboxDrops.getDrops();
			ArrayList<ItemStack> c = new ArrayList();
			for (ItemStack is : map.keySet()) {
				ItemStack is2 = is.copy();
				if (is2.stackTagCompound == null)
					is2.stackTagCompound = new NBTTagCompound();
				is2.stackTagCompound.setString("dropTooltip", String.valueOf(map.get(is)*100)+"%");
				c.add(is2);
			}
			return c;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			return this.getOutputsOfInputForDisplay(null);
		}

	}

	private static class HardOreProcessing extends DropProcessing {

		@Override
		public boolean isValidItem(ItemStack is) {
			return this.getItem(is) != null;
		}

		@Override
		protected boolean allowsStacking() {
			return true;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			return ReikaJavaLibrary.makeListFrom(this.getItem(src));
		}

		private ItemStack getItem(ItemStack src) {
			return ReikaItemHelper.getSizedItemStack(HardOresHandler.instance.getRootOre(src), HardOresHandler.instance.getYield(src));
		}

		@Override
		protected boolean isLoadable() {
			return HardOresHandler.instance.isLoaded();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			return HardOresHandler.instance.getAllHardOres();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			return ReikaJavaLibrary.makeListFrom(this.getItem(src));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			/*
			Collection<ItemStack> ret = new ArrayList();
			for (ItemStack is : HardOresHandler.instance.getAllHardOres()) {
				ItemStack from = this.getItem(is);
				if (from != null) {
					ret.add(from);
				}
			}
			return ret;
			 */
			return HardOresHandler.instance.getOresWithHardVersions();
		}

	}

	private static class ThaumBagProcessing extends DropProcessing {

		private Class lootBagClass;
		private Method generateBagLoot;
		private Method getToolLoot;

		@Override
		protected void create() {
			try {
				lootBagClass = Class.forName("thaumcraft.common.items.ItemLootBag");
				Class c = Class.forName("thaumcraft.common.lib.utils.Utils");
				generateBagLoot = c.getMethod("generateLoot", int.class, Random.class);
				getToolLoot = c.getDeclaredMethod("getGearItemForSlot", int.class, int.class);
				getToolLoot.setAccessible(true);
			}
			catch (Exception e) {
				RotaryCraft.logger.logError("Could not initialize TC loot bag processing:");
				e.printStackTrace();
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.THAUMCRAFT, e);
			}
		}

		@Override
		protected boolean allowsStacking() {
			return false;
		}

		@Override
		public boolean isValidItem(ItemStack is) {
			return is.getItem().getClass() == lootBagClass;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			ArrayList<ItemStack> li = new ArrayList();
			int n = 8 + rand.nextInt(5);
			for (int i = 0; i < n; i++) {
				ItemStack is = (ItemStack)generateBagLoot.invoke(null, src.getItemDamage(), rand);
				if (is != null) {
					li.add(is);
				}
			}
			return li;
		}

		@Override
		protected boolean isLoadable() {
			return ModList.THAUMCRAFT.isLoaded();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			ArrayList<ItemStack> li = new ArrayList();
			li.add(ThaumItemHelper.ItemEntry.LOOTBAG1.getItem());
			li.add(ThaumItemHelper.ItemEntry.LOOTBAG2.getItem());
			li.add(ThaumItemHelper.ItemEntry.LOOTBAG3.getItem());
			return li;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			ArrayList<ItemStack> li = new ArrayList();
			Collection<WeightedRandomLoot> c = null;
			switch(src.getItemDamage()) {
				case 0:
					c = WeightedRandomLoot.lootBagCommon;
					break;
				case 1:
					c = WeightedRandomLoot.lootBagUncommon;
					break;
				case 2:
					c = WeightedRandomLoot.lootBagRare;
					break;
			}
			if (c != null) {
				int totalWeight = WeightedRandom.getTotalWeight(c);
				for (WeightedRandomLoot w : c) {
					if (w.item == null) {
						throw new InstallationException(ModList.THAUMCRAFT.getDisplayName(), "You have specified a null item in the TC loot bags! Check your configs or MT scripts!");
					}
					ItemStack is = w.item.copy();
					if (is.stackTagCompound == null)
						is.stackTagCompound = new NBTTagCompound();
					is.stackTagCompound.setString("dropTooltip", String.valueOf(100F*w.itemWeight/totalWeight)+"%");
					li.add(is);
				}
			}
			try {
				if (src.getItemDamage() > 0) {
					float toolc = 0.025F * src.getItemDamage();
					for (int slot = 0; slot <= 4; slot++) {
						for (int quality = 0; quality <= 6; quality++) {
							Item tool = (Item)getToolLoot.invoke(null, slot, quality);
							float ch = toolc*0.2F*(1+1F/quality);
							ItemStack is = new ItemStack(tool);
							is.stackTagCompound = new NBTTagCompound();
							is.stackTagCompound.setString("dropTooltip", "~"+String.valueOf(100F*ch)+"%");
							li.add(is);
						}
					}
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			return li;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			ArrayList<ItemStack> li = new ArrayList();
			for (ItemStack is : this.getAllInputsForDisplay()) {
				li.addAll(this.getOutputsOfInputForDisplay(is));
			}
			return li;
		}

	}

	private static class CratedItemProcessing extends DropProcessing {

		private Class cratedItemClass;
		private Field cratedItem;

		@Override
		protected void create() {
			try {
				cratedItemClass = Class.forName("forestry.core.items.ItemCrated");
				cratedItem = cratedItemClass.getDeclaredField("contained");
				cratedItem.setAccessible(true);
			}
			catch (Exception e) {
				RotaryCraft.logger.logError("Could not initialize Foestry crated item processing:");
				e.printStackTrace();
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.FORESTRY, e);
			}
		}

		@Override
		protected boolean allowsStacking() {
			return true;
		}

		@Override
		public boolean isValidItem(ItemStack is) {
			return is.getItem().getClass() == cratedItemClass;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			ItemStack is = null;
			is = (ItemStack)cratedItem.get(src.getItem());
			return is != null ? ReikaJavaLibrary.makeListFrom(ReikaItemHelper.getSizedItemStack(is, 9)) : new ArrayList();
		}

		@Override
		protected boolean isLoadable() {
			return ModList.FORESTRY.isLoaded();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			ArrayList<ItemStack> li = new ArrayList();
			for (Item i : ForestryHandler.getInstance().getAllCrates()) {
				li.add(new ItemStack(i));
			}
			return li;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			try {
				return (ArrayList<ItemStack>)this.generateItems(null, 0, 0, 0, 0, null, rand, src);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			Collection<ItemStack> li = new ArrayList();
			for (ItemStack is : this.getAllInputsForDisplay()) {
				li.addAll(this.getOutputsOfInputForDisplay(is));
			}
			return li;
		}

	}

	private static class MystNotebookProcessing extends MystFolderProcessing {

		private Method generate;

		@Override
		protected void create() {
			try {
				Class c = Class.forName("com.xcompwiz.mystcraft.item.ItemBoosterPack");
				generate = c.getDeclaredMethod("generateBooster", ItemStack.class, Random.class, int.class, int.class, int.class, int.class);
			}
			catch (Exception e) {
				RotaryCraft.logger.logError("Could not initialize Mystcraft notebook processing:");
				e.printStackTrace();
				ReflectiveFailureTracker.instance.logModReflectiveFailure(ModList.MYSTCRAFT, e);
			}
		}

		@Override
		protected boolean allowsStacking() {
			return false;
		}

		@Override
		public boolean isValidItem(ItemStack is) {
			return is.getItem() == MystCraftHandler.getInstance().sealedBookID;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			ItemStack is = (ItemStack)generate.invoke(null, null, rand, 7, 4, 4, 1); //from internal myst code
			if (MystFolderProcessing.instance.isValidItem(is)) {
				return MystFolderProcessing.instance.generateItems(world, x, y, z, fortune, ep, rand, is);
			}
			else {
				return ReikaJavaLibrary.makeListFrom(is);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			return ReikaJavaLibrary.makeListFrom(new ItemStack(MystCraftHandler.getInstance().sealedBookID));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			return super.getPotentialOutputsForDisplay();//ReikaJavaLibrary.makeListFrom(new ItemStack(MystCraftHandler.getInstance().folderID));
		}

	}

	private static class MystFolderProcessing extends DropProcessing {

		private static final MystFolderProcessing instance = new MystFolderProcessing();

		@Override
		public boolean isValidItem(ItemStack is) {
			return is.getItem() == MystCraftHandler.getInstance().folderID;
		}

		@Override
		protected boolean allowsStacking() {
			return false;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			return ReikaMystcraftHelper.getPagesInFolder(ep, src, true);
		}

		@Override
		protected final boolean isLoadable() {
			return ModList.MYSTCRAFT.isLoaded();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			return ReikaJavaLibrary.makeListFrom(new ItemStack(MystCraftHandler.getInstance().folderID));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public final List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			try {
				return (List<ItemStack>)this.generateItems(null, 0, 0, 0, 0, Minecraft.getMinecraft().thePlayer, rand, src);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			return new ArrayList(ReikaMystcraftHelper.getAllAgePages());
		}

	}

	private static class GregCrateProcessing extends DropProcessing {

		private final ItemHashMap<ItemStack> inputs = new ItemHashMap();

		private ItemStack crate;

		@Override
		protected void create() {
			crate = ReikaItemHelper.lookupItem("gregtech:gt.metaitem.01:32403");

			this.addCrate("gregtech:gt.metaitem.03:67", "gregtech:gt.metaitem.01:2067");
			this.addCrate("gregtech:gt.metaitem.03:1031", "gregtech:gt.metaitem.01:11031");
			this.addCrate("gregtech:gt.metaitem.03:1300", "gregtech:gt.metaitem.01:11300");
			this.addCrate("gregtech:gt.metaitem.03:1335", "gregtech:gt.metaitem.01:11335");
			this.addCrate("gregtech:gt.metaitem.03:1305", "gregtech:gt.metaitem.01:11305");
			this.addCrate("gregtech:gt.metaitem.03:30", "gregtech:gt.metaitem.01:2030");
		}

		private void addCrate(String in, String out) {
			ItemStack isout = ReikaItemHelper.lookupItem(out);
			isout.stackSize = 16;
			inputs.put(ReikaItemHelper.lookupItem(in), isout);
		}

		@Override
		public boolean isValidItem(ItemStack is) {
			return inputs.containsKey(is);
		}

		@Override
		protected boolean allowsStacking() {
			return true;
		}

		@Override
		protected Collection<ItemStack> generateItems(World world, int x, int y, int z, int fortune, EntityPlayer ep, Random rand, ItemStack src) throws Exception {
			return ReikaJavaLibrary.makeListFrom(crate, inputs.get(src));
		}

		@Override
		protected final boolean isLoadable() {
			return ModList.GREGTECH.isLoaded() && !GregOreHandler.getInstance().isGreg6();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ItemStack> getAllInputsForDisplay() {
			return new ArrayList(inputs.keySet());
		}

		@Override
		@SideOnly(Side.CLIENT)
		public final List<ItemStack> getOutputsOfInputForDisplay(ItemStack src) {
			return ReikaJavaLibrary.makeListFrom(crate, inputs.get(src));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Collection<ItemStack> getPotentialOutputsForDisplay() {
			return Collections.unmodifiableCollection(inputs.values());
		}

	}

}
