/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ForestryHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class TileEntityFertilizer extends InventoriedPowerReceiver implements RangedEffect {

	private ItemStack[] inv = new ItemStack[18];

	private static final ArrayList<Integer> fertilizables = new ArrayList();

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FERTILIZER.ordinal();
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return this.hasFertilizer() ? 0 : 15;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		if (!world.isRemote && this.hasFertilizer()) {
			int n = this.getUpdatesPerTick();
			for (int i = 0; i < n; i++)
				this.tickBlock(world, x, y, z);
		}
	}

	private int getUpdatesPerTick() {
		if (power < MINPOWER)
			return 0;
		return 4*(int)ReikaMathLibrary.logbase(omega, 2);
	}

	private void tickBlock(World world, int x, int y, int z) {
		int r = this.getRange();
		int dx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int dy = ReikaRandomHelper.getRandomPlusMinus(y, r);
		int dz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		int id = world.getBlockId(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		int ddx = dx-x;
		int ddy = dy-y;
		int ddz = dz-z;
		double dd = ReikaMathLibrary.py3d(ddx, ddy, ddz);
		if (id != 0 && dd <= this.getRange()) {
			Block b = Block.blocksList[id];
			b.updateTick(world, dx, dy, dz, rand);
			//ReikaParticleHelper.BONEMEAL.spawnAroundBlockWithOutset(world, dx, dy, z, 2, 0.0625);
			world.markBlockForUpdate(dx, dy, dz);
			if (this.didSomething(world, dx, dy, dz)) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.FERTILIZER.getMinValue(), world, dx, dy, dz);
				if (ReikaRandomHelper.doWithChance(20))
					this.consumeItem();
			}
			else if (id == Block.grass.blockID) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.FERTILIZER.getMinValue(), world, dx, dy, dz);
			}
		}
	}

	private void consumeItem() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				ReikaInventoryHelper.decrStack(i, inv);
				return;
			}
		}
	}

	private boolean didSomething(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
		ModCropList mod = ModCropList.getModCrop(id, meta);
		ModWoodList sapling = ModWoodList.getModWoodFromSapling(new ItemStack(id, 1, meta));
		boolean fert = fertilizables.contains(id);
		if (crop != null)
			return true;
		if (mod != null)
			return true;
		if (sapling != null)
			return true;
		if (fert)
			return true;
		return false;
	}

	@Override
	public int getRange() {
		if (torque <= 0)
			return 0;
		int r = 2*(int)ReikaMathLibrary.logbase(torque, 2);
		if (r > this.getMaxRange())
			return this.getMaxRange();
		return r;
	}

	@Override
	public int getMaxRange() {
		return 32;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isValidFertilizer(is);
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public boolean isValidFertilizer(ItemStack is) {
		List<ItemStack> li = OreDictionary.getOres("gemApatite");
		if (ReikaItemHelper.matchStacks(is, ReikaItemHelper.bonemeal))
			return true;
		if (is.itemID == ForestryHandler.getInstance().apatiteID)
			return true;
		return ReikaItemHelper.listContainsItemStack(li, is);
	}

	public boolean hasFertilizer() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (this.isValidFertilizer(inv[i]))
					return true;
			}
		}
		return false;
	}

	static {
		addFertilizable(Block.sapling);
		addFertilizable(Block.cactus);
		addFertilizable(Block.reed);
		addFertilizable(Block.mycelium);
		addFertilizable(Block.melonStem);
		addFertilizable(Block.pumpkinStem);
		addFertilizable(Block.vine);
	}

	private static void addFertilizable(Block b) {
		fertilizables.add(b.blockID);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}
}
