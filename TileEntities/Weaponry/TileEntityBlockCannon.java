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

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityLaunchCannon;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityBlockCannon extends TileEntityLaunchCannon {

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return ReikaItemHelper.isBlock(is) || FluidContainerRegistry.getFluidForFilledItem(is) != null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

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
		tickcount++;
		if (power < MINPOWER)
			return;
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;
		this.getSummativeSidedPower();
		if (this.fire(world, x, y, z)) {
			ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode");
			ReikaParticleHelper.EXPLODE.spawnAt(world, x+0.5, y+0.5, z+0.5);
		}
	}

	private double getBlockMass(ItemStack is) {
		return ReikaPhysicsHelper.getBlockDensity(Block.blocksList[is.itemID]);
	}

	private int getReqTorque(ItemStack is) {
		double m = this.getBlockMass(is);
		return ReikaMathLibrary.ceil2exp((int)(velocity*m));
	}

	private ItemStack getNextToFire() {
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				if (ReikaItemHelper.isBlock(inv[i])) {
					ItemStack is = inv[i].copy();
					ReikaInventoryHelper.decrStack(i, inv);
					return ReikaItemHelper.getSizedItemStack(is, 1);
				}
				else if (inv[i].itemID == Item.bucketWater.itemID) {
					ReikaInventoryHelper.decrStack(i, inv);
					return new ItemStack(Block.waterMoving);
				}
				else if (inv[i].itemID == Item.bucketLava.itemID) {
					ReikaInventoryHelper.decrStack(i, inv);
					return new ItemStack(Block.lavaMoving);
				}
				else {
					ItemStack is = inv[i];
					FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(is);
					if (fs != null) {
						Fluid f = fs.getFluid();
						if (f.canBePlacedInWorld()) {
							ReikaInventoryHelper.decrStack(i, inv);
							return new ItemStack(f.getBlockID(), 1, 0);
						}
					}
				}
			}
		}
		return null;
	}

	private void fireBlock(ItemStack is, World world, int x, int y, int z) {
		EntityFallingSand e = new EntityFallingSand(world, x+0.5, y+1+0.5, z+0.5, is.itemID, is.getItemDamage());
		double[] vel = ReikaPhysicsHelper.polarToCartesian(velocity/20D, theta, phi);
		e.motionX = vel[0];
		e.motionY = vel[1];
		e.motionZ = vel[2];
		//e.shouldDropItem = false;
		e.fallTime = -10000;
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
		if (torque < this.getReqTorque(next)) {
			ReikaInventoryHelper.addToIInv(next, this);
			return false;
		}
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
