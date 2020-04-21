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

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Instantiable.EntityPathSpline;
import Reika.DragonAPI.Instantiable.AI.AITaskAvoidMachine;
import Reika.DragonAPI.Instantiable.AI.AITaskSeekMachine;
import Reika.DragonAPI.Interfaces.TileEntity.MobAttractor;
import Reika.DragonAPI.Interfaces.TileEntity.MobRepellent;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.Bees.ReikaBeeHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ForestryHandler;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MobBait;

import forestry.api.lepidopterology.IEntityButterfly;

public class TileEntityBaitBox extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation, MobAttractor, MobRepellent {

	public static final int FALLOFF = 4096; //4 kW per extra meter

	private EntityPathSpline pathfinder;

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		if (ModList.FORESTRY.isLoaded() && !world.isRemote)
			pathfinder = new EntityPathSpline(this);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		tickcount++;
		this.getSummativeSidedPower();
		if (power < MINPOWER)
			return;
		int range = this.getRange();
		AxisAlignedBB box = this.getBox(x, y, z, range);
		List<EntityLiving> inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		if (!inbox.isEmpty() && (world.getTotalWorldTime()&3) == 0) {
			for (EntityLiving ent : inbox) {
				if (this.canRepel(ent)) {
					this.applyEffect(world, x, y, z, ent, false);
				}
				else if (this.canAttract(ent)) {
					this.applyEffect(world, x, y, z, ent, true);
				}
				if (ModList.FORESTRY.isLoaded() && pathfinder != null && ent instanceof IEntityButterfly) {
					pathfinder.addEntity(ent);
				}
			}
		}
		if (ModList.FORESTRY.isLoaded() && ReikaInventoryHelper.checkForItem(ForestryHandler.ItemEntry.POLLEN.getItem(), inv)) {
			ReikaBeeHelper.attractButterflies(world, x+0.5, y+1.5, z+0.5, range, pathfinder);
			ReikaBeeHelper.collectButterflies(world, ReikaAABBHelper.getBlockAABB(this).expand(4, 2, 4), null);
		}
		if (rand.nextInt(20) == 0)
			this.doEggIncubation(world, x, y, z);
		tickcount = 0;
	}

	private void doEggIncubation(World world, int x, int y, int z) {
		int slot = ReikaInventoryHelper.locateIDInInventory(Items.egg, this);
		if (slot >= 0) {
			if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.fire) != null) {
				if (ReikaWorldHelper.checkForAdjMaterial(world, x, y, z, Material.lava) == null) {
					EntityChicken ec = new EntityChicken(world);
					ec.setLocationAndAngles(x+rand.nextDouble(), y+rand.nextDouble()+0.5, z+rand.nextDouble(), rand.nextFloat()*90, rand.nextFloat()*360);
					if (!world.isRemote)
						world.spawnEntityInWorld(ec);
				}
				else {
					ReikaInventoryHelper.addToIInv(new ItemStack(Items.cooked_chicken), this, true);
				}
				ReikaInventoryHelper.decrStack(slot, inv);
			}
		}
	}

	private int maxMobs() { //Omega + config file
		return Math.max(24, ConfigRegistry.BAITMOBS.getValue());
	}

	public int getMaxRange() {
		return Math.max(24, ConfigRegistry.BAITRANGE.getValue());
	}

	private void silverfishStone(World world, int x, int y, int z) {
		for (int i = x-5; i <= x+5; i++) {
			for (int j = y-5; j <= y+5; j++) {
				for (int k = z-5; z <= z+5; k++) {
					if (world.getBlock(i, j, k) == Blocks.monster_egg) {
						world.setBlockToAir(i, j, k);
						world.playSoundEffect(i+0.5, j+0.5, k+0.5, "step.stone", 0.5F+0.5F*rand.nextFloat(), 0.8F+0.2F*rand.nextFloat());
					}
				}
			}
		}
	}

	private void dropHeldItem(World world, int x, int y, int z, EntityLivingBase ent) {
		ItemStack held = ent.getHeldItem();
		ent.setCurrentItemOrArmor(0, null);
		if (held != null && !world.isRemote) {
			EntityItem ei = new EntityItem(world, ent.posX, ent.posY+ent.getEyeHeight(), ent.posZ, held);
			ei.motionX = -0.2F+0.4F*rand.nextFloat();
			ei.motionZ = -0.2F+0.4F*rand.nextFloat();
			ei.motionY = 0.4F*rand.nextFloat();
			ei.delayBeforeCanPickup = 200;
			world.spawnEntityInWorld(ei);
		}
	}

	public boolean canRepel(EntityLiving ent) {
		return power >= MINPOWER && this.getRange() >= ent.getDistance(xCoord+0.5, yCoord+0.5, zCoord+0.5) && MobBait.hasRepelItem(ent, inv);
	}

	public boolean canAttract(EntityLiving ent) {
		return power >= MINPOWER && this.getRange() >= ent.getDistance(xCoord+0.5, yCoord+0.5, zCoord+0.5) && MobBait.hasAttractItem(ent, inv);
	}

	private void applyEffect(World world, int x, int y, int z, EntityLiving ent, boolean attract) {
		if (world.isRemote)
			return;
		if (ent instanceof EntityTameable && ((EntityTameable)ent).isSitting()) {
			return;
		}

		long time = worldObj.getTotalWorldTime();
		if (time-ent.getEntityData().getLong("baitbox") < 20)
			return;
		ent.getEntityData().setLong("baitbox", time);

		if (attract) {
			ReikaEntityHelper.addAITask(ent, new AITaskSeekMachine(ent, 1, this), -1000000);
		}
		else {
			ReikaEntityHelper.addAITask(ent, new AITaskAvoidMachine(ent, 1, this), -1000000);
			this.dropHeldItem(world, x, y, z, ent);
		}

		if (ent instanceof EntityBlaze || ent instanceof EntitySlime || ent instanceof EntityMagmaCube || ent instanceof EntityGhast || ent instanceof EntitySquid) {
			if (attract) {
				if (!(ent instanceof EntitySlime) || !ent.onGround) {
					ent.motionX = 0.02*(x-ent.posX);
					if (!(ent instanceof EntityWaterMob) || ent.isInWater())
						ent.motionY = 0.02*(y-ent.posY);
					ent.motionZ = 0.02*(z-ent.posZ);
				}
			}
			else {
				if (!(ent instanceof EntitySlime) || !ent.onGround) {
					ent.motionX = -0.02*(x-ent.posX);
					if (!(ent instanceof EntityWaterMob) || ent.isInWater())
						ent.motionY = -0.02*(y-ent.posY);
					ent.motionZ = -0.02*(z-ent.posZ);
				}
			}
			if (ent instanceof EntityBlaze) {
				ent.motionX *= 4;
				ent.motionZ *= 4;
			}
			float var1 = (float)ReikaMathLibrary.py3d(ent.motionX, 0, ent.motionZ);
			ent.renderYawOffset += (-((float)Math.atan2(ent.motionX, ent.motionZ)) * 180.0F / (float)Math.PI - ent.renderYawOffset) * 0.1F;
			ent.rotationYaw = ent.renderYawOffset;
			if (!world.isRemote)
				ent.velocityChanged = true;
		}
		if (ent instanceof EntityBat) {
			if (attract) {
				ent.motionX = 0.1*(x-ent.posX);
				ent.motionY = 0.1*(y-ent.posY);
				ent.motionZ = 0.1*(z-ent.posZ);
			}
			else {
				ent.motionX = -0.1*(x-ent.posX);
				ent.motionY = -0.1*(y-ent.posY);
				ent.motionZ = -0.1*(z-ent.posZ);
			}
			float var1 = (float)ReikaMathLibrary.py3d(ent.motionX, 0, ent.motionZ);
			ent.renderYawOffset += (-((float)Math.atan2(ent.motionX, ent.motionZ)) * 180.0F / (float)Math.PI - ent.renderYawOffset) * 0.1F;
			ent.rotationYaw = ent.renderYawOffset;
			if (!world.isRemote)
				ent.velocityChanged = true;
		}
	}

	public int getRange() {
		int range = 8+(int)((power-MINPOWER)/FALLOFF);
		if (range > this.getMaxRange())
			return this.getMaxRange();
		return range;
	}

	private AxisAlignedBB getBox(int x, int y, int z, int range) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(range, range, range);
		return box;
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	public int getLeftoverSlots() {
		int slots = inv.length;
		while (slots >= 9)
			slots -= 9;
		return slots;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.BAITBOX;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return true;
	}

	@Override
	public int getRedstoneOverride() {
		if (ReikaInventoryHelper.isEmpty(inv))
			return 15;
		for (int i = 0; i < inv.length; i++) {
			if (MobBait.isValidItem(inv[i]))
				return 0;
		}
		return 15;
	}

	@Override
	public boolean areConditionsMet() {
		return !ReikaInventoryHelper.isEmpty(inv);
	}

	@Override
	public String getOperationalStatus() {
		return this.areConditionsMet() ? "Operational" : "No Items";
	}
}
