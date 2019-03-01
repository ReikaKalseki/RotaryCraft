/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Base.OneSlotMachine;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Interfaces.TileEntity.InertIInv;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAreaFiller;
import Reika.RotaryCraft.Registry.MachineRegistry;

import vazkii.botania.api.item.IBlockProvider;


public class TileEntityBlockFiller extends TileEntityAreaFiller implements ISidedInventory, OneSlotMachine {

	private ItemStack[] inv = new ItemStack[1];

	@Override
	protected void onBlockPlaced() {
		if (ModList.BOTANIA.isLoaded() && inv[0].getItem() instanceof IBlockProvider) {
			BlockKey bk = this.getBlockFromBotania((IBlockProvider)inv[0].getItem(), inv[0]);
			((IBlockProvider)inv[0].getItem()).provideBlock(this.getPlacer(), null, inv[0], bk.blockID, bk.metadata, true);
		}
		else {
			ReikaInventoryHelper.decrStack(0, inv);
		}
	}

	@Override
	protected boolean hasRemainingBlocks() {
		if (inv[0] == null)
			return false;
		if (ModList.BOTANIA.isLoaded() && inv[0].getItem() instanceof IBlockProvider) {
			BlockKey bk = this.getBlockFromBotania((IBlockProvider)inv[0].getItem(), inv[0]);
			return bk != null && ((IBlockProvider)inv[0].getItem()).provideBlock(this.getPlacer(), null, inv[0], bk.blockID, bk.metadata, false);
		}
		return inv[0].stackSize > 0;
	}

	@Override
	protected BlockKey getNextPlacedBlock() {
		return this.getBlock(inv[0]);
	}

	private BlockKey getBlock(ItemStack is) {
		if (is == null)
			return null;
		if (ModList.BOTANIA.isLoaded() && is.getItem() instanceof IBlockProvider) {
			return this.getBlockFromBotania((IBlockProvider)is.getItem(), is);
		}
		BlockKey bk = ReikaItemHelper.getWorldBlockFromItem(is);
		return bk.blockID != Blocks.air ? bk : null;
	}

	private BlockKey getBlockFromBotania(IBlockProvider item, ItemStack is) {
		if (is.stackTagCompound == null)
			return null;
		String n = is.stackTagCompound.getString("blockName");
		Block b = Block.getBlockFromName(n);
		int meta = is.stackTagCompound.getInteger("blockMeta");
		return b != null ? new BlockKey(b, meta) : null;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FILLER;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	protected void onInventoryChanged() {

	}

	public final ItemStack getStackInSlot(int par1) {
		return inv[par1];
	}

	public final void setInventorySlotContents(int par1, ItemStack is) {
		inv[par1] = is;
		this.onInventoryChanged();
	}

	public final String getInventoryName() {
		return this.getMultiValuedName();
	}

	public void openInventory() {}

	public void closeInventory() {}

	@Override
	public final boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public final void markDirty() {
		blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);

		if (this.getBlockType() != Blocks.air)
		{
			worldObj.func_147453_f(xCoord, yCoord, zCoord, this.getBlockType());
		}
		//this.onInventoryChanged();
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public final ItemStack decrStackSize(int par1, int par2) {
		ItemStack ret = ReikaInventoryHelper.decrStackSize(this, par1, par2);
		this.onInventoryChanged();
		return ret;
	}

	public final ItemStack getStackInSlotOnClosing(int par1) {
		ItemStack ret = ReikaInventoryHelper.getStackInSlotOnClosing(this, par1);
		this.onInventoryChanged();
		return ret;
	}

	public final int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided(this);
	}

	public final boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isItemValidForSlot(i, is);
	}

	public boolean isUseableByPlayer(EntityPlayer var1) {
		return this.isPlayerAccessible(var1);
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setShort("Slot", (short)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
				//ReikaJavaLibrary.pConsole(i+":"+inv[i]);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		NBTTagList nbttaglist = NBT.getTagList("Items", NBTTypes.COMPOUND.ID);
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			short byte0 = nbttagcompound.getShort("Slot");

			if (byte0 >= 0 && byte0 < inv.length) {
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
				//ReikaJavaLibrary.pConsole(byte0+":"+inv[byte0]);
			}
			else {
				RotaryCraft.logger.logError(this+" tried to load an inventory slot "+byte0+" from NBT!");
				//Thread.dumpStack();
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.getBlock(is) != null;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack is, int side) {
		return false;
	}

	@Override
	protected long getRequiredPower() {
		Material mat = this.getNextPlacedBlock().blockID.getMaterial();
		if (!mat.isSolid())
			return 512;
		if (mat == Material.iron || mat == Material.anvil)
			return 4096;
		return mat == Material.rock ? 2048 : 1024;
	}

	@Override
	protected boolean allowFluidOverwrite() {
		return true;
	}

}
