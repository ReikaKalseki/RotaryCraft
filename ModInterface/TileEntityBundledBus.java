/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.ModInterface;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ASM.APIStripper.Strippable;
import Reika.DragonAPI.ASM.DependentMethodStripper.ModDependent;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Instantiable.Data.Collections.ItemCollection;
import Reika.DragonAPI.Instantiable.ModInteract.BasicAEInterface;
import Reika.DragonAPI.Instantiable.ModInteract.MEWorkTracker;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.DeepInteract.MESystemReader;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

import appeng.api.AEApi;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.util.AECableType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mrtjp.projectred.api.IBundledTile;
import mrtjp.projectred.api.ProjectRedAPI;

@Strippable(value={"mrtjp.projectred.api.IBundledTile", "appeng.api.networking.IActionHost"})
public class TileEntityBundledBus extends TileEntityPowerReceiver implements IBundledTile, IActionHost {

	@ModDependent(ModList.APPENG)
	private MESystemReader network;
	private Object aeGridBlock;
	private Object aeGridNode;
	private int AEPowerCost = 1;

	private final ItemCollection output = new ItemCollection();

	private StepTimer checkTimer = new StepTimer(10);
	private StepTimer cacheTimer = new StepTimer(40);

	public static final int NSLOTS = ReikaDyeHelper.dyes.length;

	private ItemStack[] filter = new ItemStack[NSLOTS];

	private MEWorkTracker hasWork = new MEWorkTracker();

	public TileEntityBundledBus() {
		if (ModList.APPENG.isLoaded()) {
			aeGridBlock = new BasicAEInterface(this, this.getMachine().getCraftedProduct());
			aeGridNode = FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? AEApi.instance().createGridNode((IGridBlock)aeGridBlock) : null;
		}
	}

	@Override
	protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalid) {
		super.onInvalidateOrUnload(world, x, y, z, invalid);
		if (ModList.APPENG.isLoaded() && aeGridNode != null)
			((IGridNode)aeGridNode).destroy();
	}

	private int[] getBundledInput(World world, int x, int y, int z) {
		int[] ret = new int[16];
		for (int i = 0; i < 6; i++) {
			byte[] data = ProjectRedAPI.transmissionAPI.getBundledInput(world, x, y, z, i);
			if (data != null) {
				for (int k = 0; k < 16; k++)
					ret[k] = Math.max(ret[k], data[k] & 255);
			}
		}
		return ret;
	}

	private int getBundledInput(World world, int x, int y, int z, ForgeDirection dir, ReikaDyeHelper color) {
		byte[] data = ProjectRedAPI.transmissionAPI.getBundledInput(world, x, y, z, dir.ordinal());
		return data != null ? data[color.ordinal()] & 255 : 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getIOSidesDefault(world, x, y, z, meta);
		this.getPower(false);

		if (world.isRemote) {

		}
		else {
			cacheTimer.update();
			if (cacheTimer.checkCap()) {
				this.buildCache();
			}

			if (aeGridBlock != null && !world.isRemote) {
				((BasicAEInterface)aeGridBlock).setPowerCost(AEPowerCost);
			}
			if (AEPowerCost > 1)
				AEPowerCost -= Math.max(1, AEPowerCost/40);

			//ReikaJavaLibrary.pConsole(hasWork);
			if (network != null && power >= MINPOWER) {
				checkTimer.update();
				hasWork.tick();
				if (hasWork.hasWork()) {
					if (checkTimer.checkCap()) {
						checkTimer.setCap(Math.min(40, checkTimer.getCap()+2));
						output.clear();
						TileEntity te = this.getAdjacentTileEntity(this.getFacing());
						if (te instanceof IInventory) {
							output.addInventory((IInventory)te);
						}
						int[] input = this.getBundledInput(world, x, y, z);
						for (int i = 0; i < Math.min(this.getActiveChannels(), NSLOTS); i++) {
							ItemStack f1 = filter[i];
							if (f1 != null && input[i] > 0) {
								int fit = f1.getMaxStackSize()-output.addItemsToUnderlyingInventories(ReikaItemHelper.getSizedItemStack(f1, f1.getMaxStackSize()), true);
								if (fit > 0) {
									hasWork.reset();
									this.transferItem(ReikaItemHelper.getSizedItemStack(f1, Math.min(fit, f1.getMaxStackSize())), (IInventory)te);
								}
							}
						}
					}
				}
			}
		}
	}

	private int getActiveChannels() {
		return (int)Math.min(16, power/MINPOWER);
	}

	private void buildCache() {
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
		}
	}

	private void buildCallbacks() {
		if (network != null) {
			network.clearCallbacks();
			for (int i = 0; i < filter.length; i++) {
				ItemStack pattern = filter[i];
				if (pattern != null) {
					network.addCallback(pattern, hasWork);
				}
			}
		}
		hasWork.markDirty();
	}

	private void transferItem(ItemStack is, IInventory ii) {
		long rem = network.removeItem(is, true, true);
		if (rem > 0) {
			is.stackSize = (int)Math.min(is.stackSize, rem);
			if (ReikaInventoryHelper.addToIInv(is, ii)) {
				network.removeItem(is, false, true);
			}
			AEPowerCost = Math.min(500, AEPowerCost+Math.max(1, is.stackSize/4));
			checkTimer.setCap(Math.max(4, checkTimer.getCap()-4));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBTTagCompound fil = new NBTTagCompound();

		for (int i = 0; i < filter.length; i++) {
			ItemStack is = filter[i];
			if (is != null) {
				NBTTagCompound tag = new NBTTagCompound();
				is.writeToNBT(tag);
				fil.setTag("filter_"+i, tag);
			}
		}

		NBT.setTag("filter", fil);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		filter = new ItemStack[filter.length];
		NBTTagCompound fil = NBT.getCompoundTag("filter");
		for (int i = 0; i < filter.length; i++) {
			String name = "filter_"+i;
			if (fil.hasKey(name)) {
				NBTTagCompound tag = fil.getCompoundTag(name);
				ItemStack is = ItemStack.loadItemStackFromNBT(tag);
				filter[i] = is;
			}
		}
	}

	public void setMapping(int slot, ItemStack is) {
		filter[slot] = is;
		if (ModList.APPENG.isLoaded())
			this.buildCallbacks();
		this.syncAllData(true);
	}

	public ItemStack getMapping(int slot) {
		return filter[slot] != null ? filter[slot].copy() : null;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

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

	private ForgeDirection getFacing() {
		return read != null ? read.getOpposite() : ForgeDirection.UP;
	}

	@Override
	@ModDependent(ModList.APPENG)
	public void securityBreak() {

	}

	@Override
	@ModDependent(ModList.APPENG)
	public IGridNode getActionableNode() {
		return (IGridNode)aeGridNode;
	}

	@Override
	public byte[] getBundledSignal(int dir) {
		return null;
	}

	@Override
	public boolean canConnectBundled(int side) {
		return true;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BUNDLEDBUS;
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

