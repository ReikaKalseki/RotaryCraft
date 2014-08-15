/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry;

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

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

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
		if (this.fire(world, x, y, z)) {
			ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode");
			ReikaParticleHelper.EXPLODE.spawnAt(world, x+0.5, y+0.5, z+0.5);
		}
	}

	private double getBlockMass(ItemStack is) {
		return ReikaPhysicsHelper.getBlockDensity(Block.getBlockFromItem(is.getItem()));
	}

	private int getReqTorque(ItemStack is) {
		double m = this.getBlockMass(is);
		int base = ReikaMathLibrary.ceil2exp((int)(velocity*m))/4;
		return base;
	}

	private ItemStack getNextToFire() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (ReikaItemHelper.isBlock(inv[i])) {
					ItemStack is = inv[i].copy();
					if (torque >= this.getReqTorque(is)) {
						ReikaInventoryHelper.decrStack(i, inv);
						return ReikaItemHelper.getSizedItemStack(is, 1);
					}
				}
				else if (ItemRegistry.SPAWNER.matchItem(inv[i])) {
					ItemStack is = new ItemStack(Blocks.mob_spawner);
					is.stackTagCompound = inv[i].stackTagCompound;
					if (torque >= this.getReqTorque(is)) {
						ReikaInventoryHelper.decrStack(i, inv);
						return is;
					}
				}
				else if (inv[i].getItem() == Items.water_bucket) {
					ItemStack is = new ItemStack(Blocks.flowing_water);
					if (torque >= this.getReqTorque(is)) {
						ReikaInventoryHelper.decrStack(i, inv);
						this.dropItem(new ItemStack(Items.bucket));
						return is;
					}
				}
				else if (inv[i].getItem() == Items.lava_bucket) {
					ItemStack is = new ItemStack(Blocks.flowing_lava);
					if (torque >= this.getReqTorque(is)) {
						ReikaInventoryHelper.decrStack(i, inv);
						this.dropItem(new ItemStack(Items.bucket));
						return is;
					}
				}
				else {
					FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(inv[i]);
					if (fs != null) {
						Fluid f = fs.getFluid();
						if (f.canBePlacedInWorld()) {
							ItemStack is = new ItemStack(f.getBlock());
							if (torque >= this.getReqTorque(is)) {
								Item cont = inv[i].getItem().getContainerItem();
								ReikaInventoryHelper.decrStack(i, inv);
								if (cont != null) {
									this.dropItem(new ItemStack(cont));
								}
								return is;
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

	private void fireBlock(ItemStack is, World world, int x, int y, int z) {
		EntityFallingBlock e = new EntityFallingBlock(world, x+0.5, y+1+0.5, z+0.5, Block.getBlockFromItem(is.getItem()), is.getItemDamage());
		if (ReikaItemHelper.matchStackWithBlock(is, Blocks.mob_spawner)) {
			TileEntityMobSpawner spw = new TileEntityMobSpawner();
			ReikaSpawnerHelper.setSpawnerFromItemNBT(is, spw);
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
	protected boolean fire(World world, int x, int y, int z) {
		ItemStack next = this.getNextToFire();
		if (next == null)
			return false;
		//ReikaJavaLibrary.pConsole(this.getReqTorque(next));
		this.fireBlock(next, world, x, y, z);
		return true;
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

}