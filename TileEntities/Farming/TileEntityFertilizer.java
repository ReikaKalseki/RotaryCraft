/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.DragonAPICore;
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
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class TileEntityFertilizer extends InventoriedPowerLiquidReceiver implements RangedEffect, ConditionalOperation {

	private static final ArrayList<Integer> fertilizables = new ArrayList();

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FERTILIZER;
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

		if (DragonAPICore.debugtest) {
			tank.addLiquid(100, FluidRegistry.WATER);
			inv[0] = new ItemStack(Item.dyePowder.itemID, 64, 15);
		}

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
		tank.removeLiquid(5);
		if (rand.nextInt(4) == 0) {
			for (int i = 0; i < inv.length; i++) {
				if (inv[i] != null) {
					ReikaInventoryHelper.decrStack(i, inv);
					return;
				}
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
		return 18;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return this.isValidFertilizer(is);
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public boolean isValidFertilizer(ItemStack is) {
		if (ReikaItemHelper.matchStacks(is, ReikaItemHelper.bonemeal))
			return true;
		if (ReikaItemHelper.matchStacks(is, ItemStacks.compost))
			return true;
		if (is.itemID == ForestryHandler.getInstance().fertilizerID)
			return true;
		return false;
	}

	public boolean hasFertilizer() {
		if (tank.isEmpty())
			return false;
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

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.WATER;
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from != ForgeDirection.DOWN;
	}

	@Override
	public int getCapacity() {
		return 6000;
	}

	@Override
	public boolean areConditionsMet() {
		return !tank.isEmpty() && this.hasFertilizer();
	}

	@Override
	public String getOperationalStatus() {
		return tank.isEmpty() ? "No Water" : this.areConditionsMet() ? "Operational" : "No Fertilizer Items";
	}
}
