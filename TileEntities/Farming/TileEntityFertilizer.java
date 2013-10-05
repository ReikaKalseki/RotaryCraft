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

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.ModInteract.ForestryHandler;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFertilizer extends TileEntityInventoriedPowerReceiver implements RangedEffect {

	private ItemStack[] inv = new ItemStack[9];

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
		return 0;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		this.getSummativeSidedPower();

		if (!world.isRemote) {
			for (int i = 0; i < this.getUpdatesPerTick(); i++)
				this.tickBlock(world, x, y, z);
		}
	}

	private int getUpdatesPerTick() {
		if (power < MINPOWER)
			return 0;
		return (int)ReikaMathLibrary.logbase(omega, 2);
	}

	private void tickBlock(World world, int x, int y, int z) {
		int dx = ReikaRandomHelper.getRandomPlusMinus(x, this.getRange());
		int dy = ReikaRandomHelper.getRandomPlusMinus(y, this.getRange());
		int dz = ReikaRandomHelper.getRandomPlusMinus(z, this.getRange());
		int id = world.getBlockId(dx, dy, dz);
		if (id != 0 && ReikaMathLibrary.py3d(x-dx, y-dy, z-dz) <= this.getRange()) {
			Block b = Block.blocksList[id];
			b.updateTick(world, dx, dy, dz, par5Random);
			ReikaParticleHelper.BONEMEAL.spawnAroundBlockWithOutset(world, dx, dy, z, 2, 0.0625);
			world.markBlockForUpdate(dx, dy, dz);
		}
	}

	@Override
	public int getRange() {
		return 6;
	}

	@Override
	public int getMaxRange() {
		return 6;
	}

	@Override
	public int getSizeInventory() {
		return 9;
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
		return ReikaItemHelper.matchStacks(is, ReikaItemHelper.bonemeal) || is.itemID == ForestryHandler.getInstance().apatiteID;
	}
}
