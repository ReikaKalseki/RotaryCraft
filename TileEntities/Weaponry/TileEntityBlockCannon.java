/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Instantiable.Data.Immutable.BlockKey;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBlockCannon extends TileEntityLaunchCannon {

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		if (ReikaItemHelper.isBlock(is))
			return true;
		return ItemRegistry.SPAWNER.matchItem(is) || FluidContainerRegistry.getFluidForFilledItem(is) != null;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BLOCKCANNON;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		tickcount++;
		if (power < MINPOWER)
			return;
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;
		if (this.fire(world, x, y, z, 0)) {
			ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode");
			ReikaParticleHelper.EXPLODE.spawnAt(world, x+0.5, y+0.5, z+0.5);
		}
	}

	private double getBlockMass(BlockKey bk) {
		return ReikaPhysicsHelper.getBlockDensity(bk.blockID);
	}

	private int getReqTorque(BlockKey bk) {
		double m = this.getBlockMass(bk);
		int base = ReikaMathLibrary.ceil2exp((int)(velocity*m))/4;
		return base;
	}

	private BlockToFire getNextToFire() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (ReikaItemHelper.isBlock(inv[i])) {
					ItemStack is = inv[i].copy();
					BlockKey bk = ReikaItemHelper.getWorldBlockFromItem(is);
					if (torque >= this.getReqTorque(bk)) {
						return new BlockToFire(inv[i], bk, i);
					}
				}
				else if (ItemRegistry.SPAWNER.matchItem(inv[i])) {
					ItemStack is = inv[i].copy();
					BlockKey bk = new BlockKey(Blocks.mob_spawner, 0);
					if (torque >= this.getReqTorque(bk)) {
						return new BlockToFire(is, bk, i);
					}
				}
				else {
					FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(inv[i]);
					if (fs != null) {
						Fluid f = fs.getFluid();
						if (f.canBePlacedInWorld()) {
							BlockKey bk = new BlockKey(f.getBlock(), 0);
							if (torque >= this.getReqTorque(bk)) {
								return new BlockToFire(inv[i], bk, i);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private void dropItem(ItemStack is) {
		for (int i = 2; i < 6; i++) {
			ForgeDirection dir = dirs[i];
			TileEntity te = this.getAdjacentTileEntity(dir);
			if (te instanceof IInventory) {
				if (ReikaInventoryHelper.addToIInv(is, (IInventory)te)) {
					return;
				}
			}
		}
		ReikaItemHelper.dropItem(worldObj, xCoord+0.5, yCoord+1, zCoord+0.5, is);
	}

	private void fireBlock(BlockToFire b, World world, int x, int y, int z) {
		EntityFallingBlock e = new EntityFallingBlock(world, x+0.5, y+1+0.5, z+0.5, b.toFire.blockID, b.toFire.metadata);
		if (b.toFire.blockID == Blocks.mob_spawner) {
			TileEntityMobSpawner spw = new TileEntityMobSpawner();
			ReikaSpawnerHelper.setSpawnerFromItemNBT(b.referenceItem, spw);
			NBTTagCompound nbt = new NBTTagCompound();
			spw.writeToNBT(nbt);
			e.field_145810_d = nbt;
		}
		double[] vel = ReikaPhysicsHelper.polarToCartesian(velocity/20D, theta, phi);
		e.motionX = vel[0];
		e.motionY = vel[1];
		e.motionZ = vel[2];
		//e.shouldDropItem = false;
		e.field_145812_b = -10000;
		if (!world.isRemote)
			world.spawnEntityInWorld(e);
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
	protected boolean fire(World world, int x, int y, int z, int slot) {
		BlockToFire b = this.getNextToFire();
		if (b == null)
			return false;
		if (inv[b.inventorySlot] == null)
			return false;
		//ReikaJavaLibrary.pConsole(this.getReqTorque(next));
		ReikaInventoryHelper.decrStack(b.inventorySlot, inv);
		this.dropContainers(world, x, y, z, b.referenceItem);
		this.fireBlock(b, world, x, y, z);
		return true;
	}

	private void dropContainers(World world, int x, int y, int z, ItemStack next) {
		if (FluidContainerRegistry.isFilledContainer(next)) {
			ItemStack cont = FluidContainerRegistry.drainFluidContainer(next);
			if (cont != null)
				this.dropItem(cont);
		}
	}

	@Override
	public int getMaxLaunchVelocity() {
		if (power < MINPOWER)
			return 0;
		return 1000;
	}

	@Override
	public int getMaxTheta() {
		if (power < MINPOWER)
			return 0;
		return 1000;
	}

	@Override
	public double getMaxLaunchDistance() {
		if (power < MINPOWER)
			return 0;
		return 1000;
	}

	private static class BlockToFire {

		private final ItemStack referenceItem;
		private final BlockKey toFire;
		private final int inventorySlot;

		private BlockToFire(ItemStack is, BlockKey bk, int s) {
			referenceItem = is;
			toFire = bk;
			inventorySlot = s;
		}

	}

}
