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
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class TileEntityFertilizer extends TileEntityInventoriedPowerReceiver implements RangedEffect {

	private ItemStack[] inv = new ItemStack[9];

	private static final ArrayList<Integer> fertilizables = new ArrayList();

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

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
		this.getSummativeSidedPower();

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
		int dx = ReikaRandomHelper.getRandomPlusMinus(x, this.getRange());
		int dy = ReikaRandomHelper.getRandomPlusMinus(y, this.getRange());
		int dz = ReikaRandomHelper.getRandomPlusMinus(z, this.getRange());
		int id = world.getBlockId(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		if (id != 0 && ReikaMathLibrary.py3d(x-dx, y-dy, z-dz) <= this.getRange()) {
			Block b = Block.blocksList[id];
			b.updateTick(world, dx, dy, dz, par5Random);
			//ReikaParticleHelper.BONEMEAL.spawnAroundBlockWithOutset(world, dx, dy, z, 2, 0.0625);
			world.markBlockForUpdate(dx, dy, dz);
			if (this.didSomething(world, dx, dy, dz)) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.FERTILIZER.getMinValue(), world, dx, dy, dz);
				this.consumeItem();
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
}
