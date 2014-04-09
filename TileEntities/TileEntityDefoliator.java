/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
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
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;

public class TileEntityDefoliator extends InventoriedPowerReceiver implements RangedEffect {

	private int potionLevel;

	public static final int CAPACITY = 4000;

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(omega+1, 2), 1.05);
	}

	public int getPotionScaled(int i) {
		return potionLevel * i / CAPACITY;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();

		if (world.isRemote)
			return;

		this.consumePotions();

		if (potionLevel > 0) {
			int r = this.getRange();
			int n = this.getNumberPasses();
			for (int i = 0; i < n && potionLevel > 0; i++) {
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
		if (inv[0] != null && potionLevel+1000 <= CAPACITY) {
			if (this.isItemValidForSlot(0, inv[0])) {
				potionLevel += 1000;
				ReikaInventoryHelper.decrStack(0, inv);
				ReikaInventoryHelper.addOrSetStack(Item.glassBottle.itemID, 1, 0, inv, 1);
			}
		}
	}

	public int getPotionLevel() {
		return potionLevel;
	}

	private void decay(World world, int x, int y, int z) {
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
				potionLevel--;
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
		int r = 8*(int)ReikaMathLibrary.logbase(torque, 2);
		return r > this.getMaxRange() ? this.getMaxRange() : r;
	}

	@Override
	public int getMaxRange() {
		return 128;
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		potionLevel = NBT.getInteger("level");
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setInteger("level", potionLevel);
	}

}
