/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.World;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaPotionHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DragonAPI.ModRegistry.ModWoodList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerLiquidReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class TileEntityDefoliator extends InventoriedPowerLiquidReceiver implements RangedEffect {

	public static final int CAPACITY = 4000;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	public int getPoisonScaled(int i) {
		return tank.getLevel() * i / CAPACITY;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		if (world.isRemote)
			return;

		this.consumePotions();

		if (!tank.isEmpty()) {
			int r = this.getRange();
			int n = this.getNumberPasses();
			for (int i = 0; i < n && !tank.isEmpty(); i++) {
				int rx = ReikaRandomHelper.getRandomPlusMinus(x, r);
				int ry = ReikaRandomHelper.getRandomPlusMinus(y, r);
				int rz = ReikaRandomHelper.getRandomPlusMinus(z, r);
				this.decay(world, rx, ry, rz);
			}
		}
	}

	private int getNumberPasses() {
		if (power < MINPOWER)
			return 0;
		return 2*(int)Math.sqrt(omega);
	}

	private void consumePotions() {
		if (inv[0] != null && tank.canTakeIn(1000)) {
			if (this.isItemValidForSlot(0, inv[0])) {
				tank.addLiquid(1000, FluidRegistry.getFluid("poison"));
				ReikaInventoryHelper.decrStack(0, inv);
				ReikaInventoryHelper.addOrSetStack(Item.glassBottle.itemID, 1, 0, inv, 1);
			}
		}
	}

	private void decay(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (!ReikaPlayerAPI.playerCanBreakAt(worldObj, x, y, z, id, meta, placer))
			return;
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
				ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);

				AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).expand(3, 3, 3);
				List<EntityLivingBase> li2 = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
				for (int i = 0; i < li2.size(); i++) {
					EntityLivingBase e = li2.get(i);
					e.addPotionEffect(new PotionEffect(Potion.poison.id, 50, 3));
					e.attackEntityFrom(DamageSource.generic, 0.5F);
				}

				if (world.checkChunksExist(x, y, z, x, y, z))
					ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.DEFOLIATOR.getMinValue(), this, x, y, z);
				tank.removeLiquid(1);
			}
		}
	}

	public void onBlockBreak(World world, int x, int y, int z) {
		int r = 3;
		for (int i = -r; i <= r; i++)
			for (int j = -r; j <= r; j++)
				for (int k = -r; k <= r; k++)
					ReikaParticleHelper.spawnColoredParticlesWithOutset(world, x+i, y+j, z+k, 0, 20, 0, 1, 2);
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
		return itemstack.itemID == Item.glassBottle.itemID;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.itemID == Item.potion.itemID && ReikaPotionHelper.getPotionDamageValue(Potion.poison) == is.getItemDamage();
	}

	@Override
	public int getRange() {
		int r = (int)(8*ReikaMathLibrary.logbase(torque, 2));
		return r > this.getMaxRange() ? this.getMaxRange() : r;
	}

	@Override
	public int getMaxRange() {
		return 128;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.PIPE;
	}

	@Override
	public Fluid getInputFluid() {
		return FluidRegistry.getFluid("poison");
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return from.offsetY == 0;
	}

	@Override
	public int getCapacity() {
		return CAPACITY;
	}

}
