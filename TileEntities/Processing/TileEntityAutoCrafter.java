/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Processing;

import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.Collections.ItemCollection;
import Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
import Reika.DragonAPI.Instantiable.ModInteract.BasicAEInterface;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.MESystemReader;
import Reika.DragonAPI.ModInteract.DeepInteract.MESystemReader.SourceType;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Items.Tools.ItemCraftPattern;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import appeng.api.AEApi;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.util.AECableType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

@Strippable(value={"appeng.api.networking.IGridHost"})
public class TileEntityAutoCrafter extends InventoriedPowerReceiver implements IGridHost {

	public boolean continuous = true;
	private final ItemCollection ingredients = new ItemCollection();
	public int[] crafting = new int[18];

	@ModDependent(ModList.APPENG)
	private MESystemReader network;
	private Object aeGridBlock;
	private Object aeGridNode;

	private final StepTimer updateTimer = new StepTimer(50);

	private static final int MAX_TICK_DELAY = 100; //5s
	private int tickTimer = 1;
	private int tick;

	private static final int OUTPUT_OFFSET = 18;
	private static final int CONTAINER_OFFSET = 36;

	public TileEntityAutoCrafter() {
		if (ModList.APPENG.isLoaded()) {
			aeGridBlock = new BasicAEInterface(this, this.getMachine().getCraftedProduct());
			aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;
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

		if (power >= MINPOWER) {

			tick++;
			if (continuous && (tick >= tickTimer)) {
				tick = 0;
				long time = System.nanoTime();
				this.attemptAllSlotCrafting();
				long duration = System.nanoTime()-time;
				if (duration > 1000000*tickTimer && tickTimer < MAX_TICK_DELAY) {
					tickTimer += this.getTickIncrement();
				}
				else {
					tickTimer--;
				}
			}

			this.injectItems();
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
			for (int i = 0; i < 18; i++) {
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
			if (aeGridNode == null) {
				aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;
			}
			((IGridNode)aeGridNode).updateState();
			network = aeGridNode != null ? new MESystemReader((IGridNode)aeGridNode, SourceType.MACHINE) : null;
		}
	}

	public void triggerCraftingCycle(int slot) {
		if (power >= MINPOWER) {
			ItemStack out = this.getSlotRecipeOutput(slot);
			if (out != null)
				this.attemptSlotCrafting(slot);
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
			ItemStack[] items = ItemCraftPattern.getItems(is);
			ItemStack out = ItemCraftPattern.getRecipeOutput(is);
			if (out != null) {
				//ReikaJavaLibrary.pConsole("crafting "+out+" from "+Arrays.toString(items));
				return this.tryCrafting(i, out, items);
			}
		}
		return false;
	}

	private boolean tryCrafting(int i, ItemStack out, ItemStack[] items) {
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
					int req = counts.get(is);
					int has = this.getAvailableIngredients(is);
					int missing = req-has;
					//ReikaJavaLibrary.pConsole("need "+req+" / have "+has+" '"+is+" ("+is.getDisplayName()+")'; making '"+out+" ("+out.getDisplayName()+")'");
					if (missing > 0) {
						//ReikaJavaLibrary.pConsole(options+":"+has+"/"+req);
						if (!this.tryCraftIntermediates(missing, is)) {
							//ReikaJavaLibrary.pConsole("missing "+missing+": "+options.get(is)+", needed "+req+", had "+has);
							return false;
						}
					}
				}
				this.craft(slot, size, out, counts);
				return true;
			}
		}
		return false;
	}

	private int getAvailableIngredients(ItemStack is) {
		int count = 0;
		//ItemHashMap<Long> map = ModList.APPENG.isLoaded() && network != null ? network.getMESystemContents() : null;
		//ReikaJavaLibrary.pConsole(map);
		//for (ItemStack is : li) {
		//ReikaJavaLibrary.pConsole(is+":"+ingredients.getItemCount(is)+" > "+ingredients);
		count += ingredients.getItemCount(is);
		if (ModList.APPENG.isLoaded() && network != null) {
			count += network.removeItem(ReikaItemHelper.getSizedItemStack(is, Integer.MAX_VALUE), true);
		}
		//}

		return count;
	}

	private boolean tryCraftIntermediates(int num, ItemStack is) {
		int run = 0;
		HashMap<Integer, Integer> ranSlots = new HashMap();
		//for (ItemStack is : li) {
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
		//if (run >= num)
		//	break;
		//}
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
								dec -= network.removeItem(is2, false);//network.removeFromMESystem(is2, diff);
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
						network.removeItem(isc, false);//network.removeFromMESystem(is, diff);
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
		return i >= 18;
	}

	@Override
	public int getSizeInventory() {
		return 18+18+18; //18 for patterns, 18 for output, additional 18 for container items; last 18 is hidden
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is) {
		return i < 18 && ItemRegistry.CRAFTPATTERN.matchItem(is);
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

		NBT.setBoolean("cont", continuous);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		continuous = NBT.getBoolean("cont");
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

}
