/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityDefoliator extends InventoriedPowerReceiver implements RangedEffect {

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		int r = this.getRange();
		int n = this.getNumberPasses();
		for (int i = 0; i < n; i++) {
			int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
			int ry = ReikaRandomHelper.getRandomPlusMinus(y, r);
			int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
			this.decay(world, rx, ry, rz);
		}
	}

	private int getNumberPasses() {
		return 65536;
	}

	private void decay(World world, int x, int y, int z) {
		if (world.isRemote)
			return;
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		boolean flag = false;
		if (id != 0) {
			Block b = Block.blocksList[id];
			Material mat = b.blockMaterial;
			if (mat == Material.leaves) {
				flag = true;
			}
			else if (mat == Material.plants || mat == Material.vine || mat == Material.cactus) {
				flag = true;
			}
			else if (id == Block.wood.blockID) {
				flag = true;
			}
			else if (id == Block.sapling.blockID) {
				flag = true;
			}
			else if (ModWoodList.isModWood(id, meta)) {
				flag = true;
			}
			else if (ModWoodList.isModLeaf(id, meta)) {
				flag = true;
			}
			else if (ModWoodList.isModSapling(id, meta)) {
				flag = true;
			}

			if (flag) {
				ReikaSoundHelper.playBreakSound(world, x, y, z, b);
				List<ItemStack> li = b.getBlockDropped(world, x, y, z, meta, 0);
				world.setBlock(x, y, z, 0);
				//ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
			}
		}
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.DEFOLIATOR;
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
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return false;
	}

	@Override
	public int getRange() {
		return 64;
	}

	@Override
	public int getMaxRange() {
		return 0;
	}

}
