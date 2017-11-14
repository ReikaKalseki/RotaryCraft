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

import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Auxiliary.ModularLogger;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.Collections.ItemCollection;
import Reika.DragonAPI.Instantiable.Data.Maps.CountMap;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.ModInteract.BasicAEInterface;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.MESystemReader;
import Reika.DragonAPI.ModInteract.DeepInteract.MESystemReader.ChangeCallback;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern.RecipeMode;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import appeng.api.AEApi;
import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.security.IActionHost;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

@Strippable(value={"appeng.api.networking.IActionHost"/*, "appeng.api.networking.crafting.ICraftingRequester"*/})
public class TileEntityAutoCrafter extends InventoriedPowerReceiver implements IActionHost/*, ICraftingRequester*/, ChangeCallback {

	private static final String LOGGER_ID = "autocrafter_workflag";

	public static final int SIZE = 18;

	private final ItemCollection ingredients = new ItemCollection();
	public int[] crafting = new int[SIZE];

	@ModDependent(ModList.APPENG)
	private MESystemReader network;
	private Object aeGridBlock;
	private Object aeGridNode;

	private final StepTimer updateTimer = new StepTimer(50);

	private static final int MAX_TICK_DELAY = 100; //5s
	private int tickTimer = 1;
	private int tick;

	private int threshold[] = new int[SIZE];
	//private final IdentityHashMap<ICraftingLink, Integer> locks = new IdentityHashMap();
	//private boolean[] lock = new boolean[SIZE];

	private CraftingMode mode = CraftingMode.REQUEST;

	private static final int OUTPUT_OFFSET = SIZE;
	private static final int CONTAINER_OFFSET = SIZE*2;

	private boolean hasWork = true;

	static {
		ModularLogger.instance.addLogger(RotaryCraft.instance, LOGGER_ID);
	}

	public TileEntityAutoCrafter() {
		if (ModList.APPENG.isLoaded()) {
			aeGridBlock = new BasicAEInterface(this, this.getMachine().getCraftedProduct());
			aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;

			//for (int i = 0; i < lock.length; i++) {
			//	lock[i] = new CraftingLock();
			//}
		}
	}
	/*
	private class CraftingLock implements CraftCompleteCallback {

		private final int slot;
		//private boolean isRunning;

		private CraftingLock(int s) {
			slot = s;
		}

		@Override
		@ModDependent(ModList.APPENG)
		public void onCraftingLinkReturned(ICraftingLink link) {
			locks.put(link, slot);
		}

		@Override
		@ModDependent(ModList.APPENG)
		public void onCraftingComplete(ICraftingLink link) {
			//isRunning = false;
		}

	}
	 */
	public static enum CraftingMode {
		REQUEST("Request", "Crafts one cycle per request.", 0xff0000, "2"),
		CONTINUOUS("Continuous", "Crafts continuously as long as there are ingredients", 0x00aaff, "2"),
		SUSTAIN("Sustain", "Tries to sustain a given number of a certain item", 0xbb22ff, "4");

		public final String label;
		public final String desc;
		public final int color;
		public final String imageSuffix;

		private static final CraftingMode[] list = values();

		private CraftingMode(String l, String d, int c, String img) {
			label = l;
			desc = d;
			color = c;
			imageSuffix = img;
		}

		private void tick(TileEntityAutoCrafter te) {
			switch(this) {
				case REQUEST:
					//Do nothing tick-based
					break;
				case CONTINUOUS:
					if (te.tick >= te.tickTimer) {
						te.tick = 0;
						long time = System.nanoTime();
						te.attemptAllSlotCrafting();
						te.profileCraftingTime(time);
					}
					break;
				case SUSTAIN:
					te.tickTimer = 20;
					if (te.tick >= te.tickTimer) {
						te.tick = 0;
						te.craftMissingItems();
					}
					break;
			}
		}

		public boolean isValid() {
			switch(this) {
				case SUSTAIN:
					return ModList.APPENG.isLoaded();
				default:
					return true;
			}
		}

		public CraftingMode next() {
			CraftingMode mode = this.calcNext();
			while (!mode.isValid())
				mode = mode.calcNext();
			return mode;
		}

		private CraftingMode calcNext() {
			return list[(this.ordinal()+1)%list.length];
		}
	}

	private void craftMissingItems() {
		if (ModList.APPENG.isLoaded() && network != null) {
			for (int i = 0; i < SIZE; i++) {
				//if (this.canTryMaintaining(i)) {
				ItemStack is = this.getSlotRecipeOutput(i);
				if (is != null) {
					long thresh = this.getThreshold(i);
					int has = (int)network.getItemCount(is, false);
					long missing = thresh-has;
					if (missing > 0) {
						//ReikaJavaLibrary.pConsole("Crafting missing "+is+" x "+missing+" (has "+has+")");
						//lock[i] = true;
						//network.triggerCrafting(worldObj, is, missing, null, new CraftingLock(i));

						//int num = Math.min(is.getMaxStackSize(), (int)Math.min(missing, Integer.MAX_VALUE));
						this.attemptSlotCrafting(i, 0);
					}
				}
				//}
			}
		}
	}
	/*
	private boolean canTryMaintaining(int i) {
		return !lock[i];
	}
	 */
	public int getThreshold(int i) {
		return threshold[i];
	}

	public void setThreshold(int i, int amt) {
		threshold[i] = amt;
		this.syncAllData(true);
	}

	public void incrementMode() {
		mode = mode.next();
	}

	private void profileCraftingTime(long start) {
		long duration = System.nanoTime()-start;
		if (ConfigRegistry.CRAFTERPROFILE.getState() && duration > 1000000*tickTimer && tickTimer < MAX_TICK_DELAY) {
			tickTimer += this.getTickIncrement();
		}
		else if (tickTimer > 0) {
			tickTimer--;
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		this.tickCraftingDisplay();

		updateTimer.update();
		if (updateTimer.checkCap() && !world.isRemote) {
			this.buildCache();
		}

		if (ModList.APPENG.isLoaded()) {
			if (network != null)
				network.tick();
			if (aeGridBlock != null && !world.isRemote) {
				((BasicAEInterface)aeGridBlock).setPowerCost(power >= MINPOWER ? 4 : 1);
			}
		}

		if (power >= MINPOWER) {
			tick++;
			if (!world.isRemote) {
				if (hasWork) {
					//ReikaJavaLibrary.pConsole("Executing tick");
					mode.tick(this);
					if (ModList.APPENG.isLoaded())
						hasWork = false;
				}
				this.injectItems();
			}
		}
	}

	private int getTickIncrement() {
		if (tickTimer < 10)
			return 1;
		else if (tickTimer < 20)
			return 2;
		else if (tickTimer < 40)
			return 5;
		else
			return 10;
	}

	@Override
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalid) {
		super.onInvalidateOrUnload(world, x, y, z, invalid);
		if (ModList.APPENG.isLoaded() && aeGridNode != null)
			((IGridNode)aeGridNode).destroy();
	}

	private void injectItems() {
		if (ModList.APPENG.isLoaded() && network != null) {
			for (int i = 0; i < SIZE; i++) {
				ItemStack in = inv[i+OUTPUT_OFFSET];
				if (in != null) {
					in.stackSize = (int)network.addItem(in, false);
					if (in.stackSize <= 0)
						inv[i+OUTPUT_OFFSET] = null;
				}

				in = inv[i+CONTAINER_OFFSET];
				if (in != null) {
					in.stackSize = (int)network.addItem(in, false);
					if (in.stackSize <= 0)
						inv[i+CONTAINER_OFFSET] = null;
				}
			}
		}
	}

	private void tickCraftingDisplay() {
		for (int i = 0; i < SIZE; i++) {
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
			Object oldNode = aeGridNode;
			if (aeGridNode == null) {
				aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;
			}
			if (aeGridNode != null)
				((IGridNode)aeGridNode).updateState();

			if (oldNode != aeGridNode || network == null) {
				if (aeGridNode == null)
					network = null;
				else if (network == null)
					network = new MESystemReader((IGridNode)aeGridNode, this);
				else
					network = new MESystemReader((IGridNode)aeGridNode, network);

				this.buildCallbacks();
			}
			//network.setRequester(this);
		}
	}

	private void buildCallbacks() {
		if (network != null) {
			network.clearCallbacks();
			for (int i = 0; i < SIZE; i++) {
				ItemStack pattern = inv[i];
				if (this.isItemValidForSlot(i, pattern)) {
					ItemStack[] in = this.getIngredients(pattern);
					for (int k = 0; k < in.length; k++) {
						if (in[k] != null)
							network.addCallback(in[k], this);
					}
					ItemStack out = this.getSlotRecipeOutput(i);
					network.addCallback(out, this);
				}
			}
		}
	}

	@Override
	protected void onInventoryChanged(int slot) {
		if (ModList.APPENG.isLoaded())
			this.buildCallbacks();
	}

	public void triggerCraftingCycle(int slot) {
		if (power >= MINPOWER) {
			ItemStack out = this.getSlotRecipeOutput(slot);
			if (out != null)
				this.attemptSlotCrafting(slot, 0);
		}
	}

	/*
	public void triggerCrafting(int slot, int amt) {
		if (power >= MINPOWER) {
			ItemStack out = this.getSlotRecipeOutput(slot);
			if (out != null) {
				int space = inv[slot+OUTPUT_OFFSET].getMaxStackSize()-inv[slot+OUTPUT_OFFSET].stackSize;
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
	 */

	public ItemStack getSlotRecipeOutput(int slot) {
		ItemStack is = inv[slot];
		if (is == null)
			return null;
		return this.getOutput(is);
	}

	private void attemptAllSlotCrafting() {
		for (int i = 0; i < SIZE; i++) {
			this.attemptSlotCrafting(i, 0);
		}
	}

	private boolean attemptSlotCrafting(int i, int d) {
		return this.attemptSlotCrafting(i, 1, d);
	}

	private boolean attemptSlotCrafting(int i, int n, int d) {
		ItemStack is = inv[i];
		if (is == null)
			return false;
		ItemStack[] items = this.getIngredients(is);
		ItemStack out = this.getOutput(is);
		//ReikaJavaLibrary.pConsole(is+":"+Arrays.toString(items)+":"+out);
		if (items != null && out != null) {
			//ReikaJavaLibrary.pConsole("Crafting "+out+" from "+Arrays.toString(items));
			boolean flag = false;
			for (int a = 0; a < n; a++)
				flag |= this.tryCrafting(i, out, items, d);
			return flag;
		}
		return false;
	}

	private ItemStack[] getIngredients(ItemStack is) {
		if (is.getItem() == ItemRegistry.CRAFTPATTERN.getItemInstance() && is.stackTagCompound != null) {
			return ItemCraftPattern.getItems(is);
		}
		else if (ModList.APPENG.isLoaded() && is.getItem() instanceof ICraftingPatternItem) {
			ICraftingPatternDetails icpd = ((ICraftingPatternItem)is.getItem()).getPatternForItem(is, worldObj);
			if (icpd.isCraftable()) {
				ItemStack[] ret = new ItemStack[9];
				IAEItemStack[] in = icpd.getInputs();
				for (int i = 0; i < ret.length && i < in.length; i++) {
					if (in[i] != null)
						ret[i] = in[i].getItemStack();
				}
				return ret;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	private ItemStack getOutput(ItemStack is) {
		if (is.getItem() == ItemRegistry.CRAFTPATTERN.getItemInstance() && is.stackTagCompound != null && ItemCraftPattern.getMode(is) == RecipeMode.CRAFTING) {
			return ItemCraftPattern.getRecipeOutput(is);
		}
		else if (ModList.APPENG.isLoaded() && is.getItem() instanceof ICraftingPatternItem) {
			ICraftingPatternDetails icpd = ((ICraftingPatternItem)is.getItem()).getPatternForItem(is, worldObj);
			return icpd.isCraftable() ? icpd.getCondensedOutputs()[0].getItemStack() : null;
		}
		else {
			return null;
		}
	}

	private boolean tryCrafting(int i, ItemStack out, ItemStack[] items, int d) {
		int slot = i+OUTPUT_OFFSET;
		int size = inv[slot] != null ? inv[slot].stackSize : 0;
		if (inv[slot] == null || (ReikaItemHelper.matchStacks(out, inv[slot]) && size+out.stackSize <= out.getMaxStackSize())) {
			if (inv[i+CONTAINER_OFFSET] == null) {
				ItemHashMap<Integer> counts = new ItemHashMap(); //ingredient requirements
				for (int k = 0; k < 9; k++) {
					if (items[k] != null) {
						Integer req = counts.get(items[k]);
						int val = req != null ? req.intValue() : 0;
						counts.put(items[k], val+1); // items[k].stackSize ? no, recipes take 1 per slot
					}
				}
				for (ItemStack is : counts.keySet()) {
					if (!ReikaItemHelper.matchStacks(out, is)) {
						int req = counts.get(is);
						int has = this.getAvailableIngredients(is);
						int missing = req-has;
						//ReikaJavaLibrary.pConsole("need "+req+" / have "+has+" '"+is+" ("+is.getDisplayName()+")'; making '"+out+" ("+out.getDisplayName()+")'");
						if (missing > 0) {
							if (d < 40) {
								//ReikaJavaLibrary.pConsole("Going to attempt intermediate crafting. Can? "+this.canCraftIntermediates(out, counts));
								//ReikaJavaLibrary.pConsole(options+":"+has+"/"+req);
								if (!this.canCraftIntermediates(out, counts))
									return false;
								if (!this.tryCraftIntermediates(missing, is, d+1)) {
									//ReikaJavaLibrary.pConsole("Failed to craft intermediates ("+is+" x "+missing+")");
									//ReikaJavaLibrary.pConsole("missing "+missing+": "+options.get(is)+", needed "+req+", had "+has);
									return false;
								}
							}
							else {
								//ReikaJavaLibrary.pConsole("Recursed too deep to craft intermediates");
								return false;
							}
						}
					}
				}
				//ReikaJavaLibrary.pConsole("Performing crafting of "+out);
				this.craft(slot, size, out, counts);
				return true;
			}
		}
		return false;
	}

	private boolean canCraftIntermediates(ItemStack out, ItemHashMap<Integer> req) {
		Block b = Block.getBlockFromItem(out.getItem());
		if (b == BlockRegistry.DECO.getBlockInstance())
			return false;
		if (b instanceof BlockCompressed)
			return false;
		if (this.isLoopable(out, req))
			return false;
		if (out.getItem().getClass().getName().equals("ItemReactorCondensator")) //to be safe, since these tend to glitch
			return false;
		return true;
	}

	private boolean isLoopable(ItemStack out, ItemHashMap<Integer> req) { //recipe contains output, or recipes for inputs contain output
		Collection<ItemStack> c = req.keySet();
		if (ReikaItemHelper.collectionContainsItemStack(c, out))
			return true;
		for (ItemStack is : c) {
			List<IRecipe> li = ReikaRecipeHelper.getAllRecipesByOutput(CraftingManager.getInstance().getRecipeList(), is);
			for (IRecipe ir : li) {
				if (ReikaItemHelper.collectionContainsItemStack(ReikaRecipeHelper.getAllItemsInRecipe(ir), out))
					return true;
			}
		}
		return false;
	}

	private int getAvailableIngredients(ItemStack is) { //never needs to return more than 9 (one per slot)
		int count = 0;
		//ItemHashMap<Long> map = ModList.APPENG.isLoaded() && network != null ? network.getMESystemContents() : null;
		//ReikaJavaLibrary.pConsole(map);
		//for (ItemStack is : li) {
		//ReikaJavaLibrary.pConsole(is+":"+ingredients.getItemCount(is)+" > "+ingredients);
		count += ingredients.getItemCount(is);
		if (ModList.APPENG.isLoaded() && network != null) {
			count += network.getItemCount(is, false);//network.removeItem(ReikaItemHelper.getSizedItemStack(is, is.getMaxStackSize()), true, false);
		}
		//}

		return count;
	}

	private boolean tryCraftIntermediates(int num, ItemStack is, int d) {
		int run = 0;
		//ReikaJavaLibrary.pConsole("Crafting intermediate "+is+" x"+num);
		CountMap<Integer> ranSlots = new CountMap();
		//for (ItemStack is : li) {
		for (int i = 0; i < SIZE && run < num; i++) {
			ItemStack out = this.getSlotRecipeOutput(i);
			//ReikaJavaLibrary.pConsole(i+":"+out+" & "+is);
			if (out != null && ReikaItemHelper.matchStacks(is, out)) {
				//ReikaJavaLibrary.pConsole("Intermediate crafting: attempting slot "+i+", because "+out+" matches "+is);
				while (run < num && this.attemptSlotCrafting(i, d)) {
					run += out.stackSize;
					ranSlots.set(i, Math.min(num, ranSlots.get(i)+out.stackSize));
				}
			}
		}
		//if (run >= num)
		//	break;
		//}
		//ReikaJavaLibrary.pConsole(run+" & "+num);
		if (run >= num) {
			for (int slot : ranSlots.keySet()) {
				inv[slot+OUTPUT_OFFSET].stackSize -= ranSlots.get(slot);
				if (inv[slot+OUTPUT_OFFSET].stackSize <= 0)
					inv[slot+OUTPUT_OFFSET] = null;
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
						if (ModList.APPENG.isLoaded()) {
							if (diff > 0 && network != null) {
								ItemStack is2c = is2.copy();
								is2c.stackSize = diff;
								dec -= network.removeItem(is2, false, false);//network.removeFromMESystem(is2, diff);
							}
						}
					}
					if (dec <= 0)
						break;
				}
			}
			else {
				int rem = ingredients.removeXItems(is, req);
				int diff = req-rem;
				if (ModList.APPENG.isLoaded()) {
					if (diff > 0 && network != null) {
						ItemStack isc = is.copy();
						isc.stackSize = diff;
						network.removeItem(isc, false, false);//network.removeFromMESystem(is, diff);
					}
				}
			}
			this.addContainers(is, req, slot-OUTPUT_OFFSET);
		}
		crafting[slot-OUTPUT_OFFSET] = 5;
		this.markDirty();
	}

	private void addContainers(ItemStack is, int req, int slot) {
		ItemStack con = is.getItem().getContainerItem(is);
		if (con != null)
			inv[36+slot] = ReikaItemHelper.getSizedItemStack(con, req);
	}

	private boolean hasIngredient(ItemStack is) {
		return ingredients.hasItem(is);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack is, int j) {
		return i >= SIZE;
	}

	@Override
	public int getSizeInventory() {
		return SIZE*3;//18+18+18; //18 for patterns, 18 for output, additional 18 for container items; last 18 is hidden
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return i < SIZE && ItemRegistry.CRAFTPATTERN.matchItem(is) && ItemCraftPattern.getMode(is) == RecipeMode.CRAFTING && ItemCraftPattern.getRecipeOutput(is) != null;
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

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setInteger("mode", mode.ordinal());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		mode = CraftingMode.list[NBT.getInteger("mode")];
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBTTagCompound fil = new NBTTagCompound();
		for (int i = 0; i < threshold.length; i++) {
			fil.setInteger("thresh_"+i, threshold[i]);
		}

		NBT.setTag("filter", fil);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		NBTTagCompound fil = NBT.getCompoundTag("filter");

		threshold = new int[threshold.length];
		for (int i = 0; i < threshold.length; i++) {
			String name = "filter_"+i;
			threshold[i] = fil.getInteger("thresh_"+i);
		}
	}

	public CraftingMode getMode() {
		return mode;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public IGridNode getGridNode(ForgeDirection dir) {
		return (IGridNode)aeGridNode;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.COVERED;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public void securityBreak() {

	}
	/*
	@Override
	@ModDependent(ModList.APPENG)
	public IGridNode getActionableNode() {
		return (IGridNode)aeGridNode;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public ImmutableSet<ICraftingLink> getRequestedJobs() {
		ImmutableSet.Builder<ICraftingLink> b = ImmutableSet.builder();
		return b.addAll(locks.keySet()).build();
	}

	@Override
	@ModDependent(ModList.APPENG)
	public IAEItemStack injectCraftedItems(ICraftingLink link, IAEItemStack items, Actionable mode) {
		return items;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public void jobStateChange(ICraftingLink link) {
		Integer get = locks.get(link);
		if (get != null) {
			lock[get.intValue()] = false;
		}
	}
	 */
	@Override
	@ModDependent(ModList.APPENG)
	public IGridNode getActionableNode() {
		return (IGridNode)aeGridNode;
	}

	@Override
	public void onItemChange(IAEItemStack iae) {
		hasWork = true;
		//ModularLogger.instance.log(LOGGER_ID, "Activating "+this+" due to change in "+iae.getItemStack());
		//ReikaJavaLibrary.pConsole("Activating "+this+" due to change in "+(iae != null ? iae.getItemStack() : "Network"));
	}
}
