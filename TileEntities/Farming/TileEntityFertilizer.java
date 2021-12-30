/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Farming;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent;
import Reika.DragonAPI.Instantiable.Event.BlockTickEvent.UpdateFlags;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Interfaces.Block.Reedlike;
import Reika.DragonAPI.Interfaces.Registry.ModCrop;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaCropHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.DragonAPI.ModRegistry.ModCropList;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Interfaces.BlowableCrop;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.MachineEnchantmentHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class TileEntityFertilizer extends InventoriedPowerLiquidReceiver implements RangedEffect, ConditionalOperation, EnchantableMachine {

	private static final ArrayList<Block> fertilizables = new ArrayList();

	private final MachineEnchantmentHandler enchantments = new MachineEnchantmentHandler().addFilter(Enchantment.efficiency).addFilter(Enchantment.aquaAffinity).addFilter(Enchantment.fortune).addFilter(Enchantment.unbreaking).addFilter(Enchantment.power);

	private int firstValidSlot;

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
			inv[0] = new ItemStack(Items.dye, 64, 15);
		}

		if (!world.isRemote) {
			this.checkFertilizer();
			int n = this.getUpdatesPerTick();
			for (int i = 0; i < n && this.hasFertilizer(); i++)
				this.tickBlock(world, x, y, z);
		}
	}

	private int getUpdatesPerTick() {
		if (power < MINPOWER)
			return 0;
		return 4*ReikaMathLibrary.logbase2(omega)+enchantments.getEnchantment(Enchantment.efficiency)*3;
	}

	private int getConsecutiveUpdates() {
		if (omega < 1048576)
			return 1;
		return 1+ReikaMathLibrary.logbase2(omega/1048576);
	}

	private void tickBlock(World world, int x, int y, int z) {
		int r = this.getRange();
		int dx = ReikaRandomHelper.getRandomPlusMinus(x, r);
		int dy = ReikaRandomHelper.getRandomPlusMinus(y, r);
		while (dy > y+1)
			dy = ReikaRandomHelper.getRandomPlusMinus(y, r);
		int dz = ReikaRandomHelper.getRandomPlusMinus(z, r);
		Block id = world.getBlock(dx, dy, dz);
		int meta = world.getBlockMetadata(dx, dy, dz);
		int ddx = dx-x;
		int ddy = dy-y;
		int ddz = dz-z;
		double dd = ReikaMathLibrary.py3d(ddx, ddy, ddz);
		if (id != Blocks.air && dd <= this.getRange() && this.canTick(world, dx, dy, dz)) {
			int n = this.getConsecutiveUpdates()+enchantments.getEnchantment(Enchantment.fortune);
			for (int i = 0; i < n; i++) {
				BlockTickEvent.fire(id, world, dx, dy, dz, rand, UpdateFlags.FORCED);
			}
			world.markBlockForUpdate(dx, dy, dz);
			if (this.didSomething(world, dx, dy, dz)) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.FERTILIZER.ordinal(), dx, dy, dz, new PacketTarget.RadiusTarget(world, dx, dy, dz, 32));
				if (ReikaRandomHelper.doWithChance(20))
					this.consumeItem();
			}
			else if (id == Blocks.grass) {
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.FERTILIZER.ordinal(), dx, dy, dz, new PacketTarget.RadiusTarget(world, dx, dy, dz, 32));
			}
		}
	}

	private boolean canTick(World world, int dx, int dy, int dz) {
		Block b = world.getBlock(dx, dy, dz);
		return !(b instanceof BlockRedstoneDiode || b instanceof BlockRedstoneComparator || b instanceof BlockRedstoneWire);
	}

	private void consumeItem() {
		tank.removeLiquid(Math.max(1, 5-enchantments.getEnchantment(Enchantment.aquaAffinity)/2));
		if (rand.nextInt(4+enchantments.getEnchantment(Enchantment.unbreaking)) == 0) {
			int in = inv[firstValidSlot].stackSize;
			ReikaInventoryHelper.decrStack(firstValidSlot, inv);
			if (in == 1) {
				this.checkFertilizer();
			}
		}
	}

	private boolean didSomething(World world, int x, int y, int z) {
		Block id = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		ReikaCropHelper crop = ReikaCropHelper.getCrop(id);
		ModCrop mod = ModCropList.getModCrop(id, meta);
		ModWoodList sapling = ModWoodList.getModWoodFromSapling(id, meta);
		boolean fert = fertilizables.contains(id);
		if (crop != null)
			return true;
		if (mod != null)
			return true;
		if (sapling != null)
			return true;
		if (fert)
			return true;
		if (id instanceof BlowableCrop)
			return true;
		if (id instanceof Reedlike)
			return true;
		return false;
	}

	@Override
	public int getRange() {
		if (torque <= 0)
			return 0;
		int r = 2*(int)ReikaMathLibrary.logbase(torque, 2)+enchantments.getEnchantment(Enchantment.power);
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
		if (is.getItem() == ForestryHandler.ItemEntry.FERTILIZER.getItem())
			return true;
		return false;
	}

	public boolean hasFertilizer() {
		return firstValidSlot >= 0;
	}

	private void checkFertilizer() {
		firstValidSlot = -1;
		if (tank.isEmpty())
			return;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (this.isValidFertilizer(inv[i])) {
					firstValidSlot = i;
					return;
				}
			}
		}
	}

	static {
		addFertilizable(Blocks.sapling);
		addFertilizable(Blocks.cactus);
		addFertilizable(Blocks.reeds);
		addFertilizable(Blocks.mycelium);
		addFertilizable(Blocks.melon_stem);
		addFertilizable(Blocks.pumpkin_stem);
		addFertilizable(Blocks.vine);
	}

	private static void addFertilizable(Block b) {
		fertilizables.add(b);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m.isStandardPipe();
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

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setTag("enchants", enchantments.writeToNBT());
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		enchantments.readFromNBT(NBT.getTagList("enchants", NBTTypes.COMPOUND.ID));
	}

	@Override
	public MachineEnchantmentHandler getEnchantmentHandler() {
		return enchantments;
	}
}
