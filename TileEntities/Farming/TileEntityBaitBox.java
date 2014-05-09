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

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.RotaryCraft.Auxiliary.Interfaces.ConditionalOperation;
import Reika.RotaryCraft.Auxiliary.Interfaces.RangedEffect;
import Reika.RotaryCraft.Base.TileEntity.InventoriedPowerReceiver;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MobBait;

public class TileEntityBaitBox extends InventoriedPowerReceiver implements RangedEffect, ConditionalOperation {

	public static final int FALLOFF = 4096; //4 kW per extra meter

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
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
		List inbox = world.getEntitiesWithinAABB(EntityLiving.class, box);
		Iterator<EntityLiving> it = inbox.iterator();
		while (it.hasNext()) {
			EntityLiving e = it.next();
			NBTTagCompound nbt = e.getEntityData();
			if (world.getTotalWorldTime()-nbt.getLong("baitbox") < 400)
				it.remove();
		}
		if (!inbox.isEmpty() && (world.getTotalWorldTime()&3) == 0) {
			for (int i = 0; i < Math.min(1, inbox.size()); i++) {
				EntityLiving ent = (EntityLiving)inbox.get(rand.nextInt(inbox.size()));
				//ReikaChatHelper.write(this.canAttract(ent)+"  "+ent.getEntityName());
				//ReikaJavaLibrary.pConsole(this.canRepel(ent), ent instanceof EntityPigZombie);
				if (this.canRepel(ent)) {
					this.applyEffect(world, x, y, z, ent, false);
					//ReikaChatHelper.write(this.canAttract(ent));
				}
				else if (this.canAttract(ent)) {
					this.applyEffect(world, x, y, z, ent, true);
				}
				ent.getEntityData().setLong("baitbox", world.getTotalWorldTime());
				//ReikaChatHelper.write(ent.getNavigator().getPath());
				//PathEntity path = ent.getNavigator().getPathToXYZ(x, y, z);
				//ent.getNavigator().setPath(path, 0.3F);
			}
		}
		tickcount = 0;
	}

	private int maxMobs() { //Omega + config file
		return Math.max(24, ConfigRegistry.BAITMOBS.getValue());
	}

	public int getMaxRange() {
		return Math.max(64, ConfigRegistry.BAITRANGE.getValue());
	}

	private void silverfishStone(World world, int x, int y, int z) {
		for (int i = x-5; i <= x+5; i++) {
			for (int j = y-5; j <= y+5; j++) {
				for (int k = z-5; z <= z+5; k++) {
					if (world.getBlockId(i, j, k) == Block.silverfish.blockID) {
						world.setBlock(i, j, k, 0);
						world.playSoundEffect(i+0.5, j+0.5, k+0.5, "step.stone", 0.5F+0.5F*rand.nextFloat(), 0.8F+0.2F*rand.nextFloat());
					}
				}
			}
		}
	}

	private void dropHeldItemAndRun(World world, int x, int y, int z, EntityLivingBase ent) {
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

	private boolean canRepel(EntityLivingBase ent) {
		return MobBait.hasRepelItem(ent, inv);
	}

	private boolean canAttract(EntityLivingBase ent) {
		return MobBait.hasAttractItem(ent, inv);
	}

	private int[] getRepelTo(World world, int x, int y, int z, EntityLivingBase ent) {
		double[] machinecoords = {x+0.5, y+0.5, z+0.5};
		int[] entitycoords = {MathHelper.floor_double(ent.posX), MathHelper.floor_double(ent.posY), MathHelper.floor_double(ent.posZ)};
		int[] repelcoords = new int[3];
		for (int i = 0; i < 3; i++) {
			repelcoords[i] = MathHelper.floor_double(entitycoords[i]+(entitycoords[i]-machinecoords[i]));
			if (i != 1) //not y coord
				repelcoords[i] += -2+rand.nextInt(5); // 2-block random factor
		}
		return repelcoords;
	}

	private void applyEffect(World world, int x, int y, int z, EntityLiving ent, boolean attract) {
		if (world.isRemote)
			;//return;
		//ReikaChatHelper.write(attract+" for "+ent.getEntityName());
		int r = this.getRange();
		PathEntity path = null;
		int[] xyz = new int[3];
		if (!attract) {
			xyz = this.getRepelTo(world, x, y, z, ent);
			this.dropHeldItemAndRun(world, x, y, z, ent);
		}
		if (!((ent instanceof EntityTameable && ((EntityTameable)ent).isSitting()))) {
			ent.getNavigator().clearPathEntity();
			if (attract) {
				path = ent.getNavigator().getPathToXYZ(x, y, z);
				if (path == null)
					path =  worldObj.getEntityPathToXYZ(ent, x, y, z, r, true, false, true, true);
				//ReikaJavaLibrary.pConsole(x+":"+y+":"+z, Side.SERVER);
				//ReikaJavaLibrary.pConsole(ent.getNavigator().getPathToXYZ(x, y, z), Side.SERVER);
			}
			else {
				//path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
				path =  worldObj.getEntityPathToXYZ(ent, xyz[0], xyz[1], xyz[2], r, true, false, true, true);
			}/*
			ent.posY = 75;
			if (ent.posY > 60) {
			if (path != null)
				ReikaChatHelper.write(path.getFinalPathPoint().xCoord+", "+path.getFinalPathPoint().yCoord+", "+path.getFinalPathPoint().zCoord);
			else
				ReikaChatHelper.write(null);
			//ReikaChatHelper.write(ent.getEntityName());
			}
			if (ent instanceof EntityCreeper && ent.getNavigator().getPath() != null)
			ReikaChatHelper.write(ent.getNavigator().getPath().isSamePath(path));
			else if (ent instanceof EntityCreeper && ent.posY >= 65) ReikaChatHelper.write(null);*/
			ent.getNavigator().setPath(path, 1F);
			//if (ent instanceof EntityCreeper && ent.getNavigator().getPath() != null)
			//ReikaChatHelper.write(ent.getNavigator().getPath().isSamePath(path));
		}
		//ReikaJavaLibrary.pConsole(ent, ent.getClass() == EntityCreeper.class);
		if (ent instanceof EntitySpider) {
			if (attract)
				path = ent.getNavigator().getPathToXYZ(x, y, z);
			else {
				path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
			}
			((EntitySpider)ent).setPathToEntity(path);
		}
		if (ent instanceof EntityBlaze) {
			if (attract)
				path = ent.getNavigator().getPathToXYZ(x, y, z);
			else {
				path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
			}
			((EntityBlaze)ent).setPathToEntity(path);
		}
		if (ent instanceof EntitySilverfish) {
			if (attract)
				path = ent.getNavigator().getPathToXYZ(x, y, z);
			else {
				path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
			}
			((EntitySilverfish)ent).setPathToEntity(path);
		}
		if (ent instanceof EntityPigZombie) {
			if (ent.getNavigator().getPath() != null)
				//ReikaChatHelper.write(ent.getNavigator().getPath().getFinalPathPoint().xCoord+", "+ent.getNavigator().getPath().getFinalPathPoint().yCoord+", "+ent.getNavigator().getPath().getFinalPathPoint().zCoord);
				if (attract)
					path = ent.getNavigator().getPathToXYZ(x, y, z);
				else {
					path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
				}
			((EntityPigZombie)ent).setPathToEntity(path);
		}
		if (ent instanceof EntityEnderman) {
			if (attract)
				path = ent.getNavigator().getPathToXYZ(x, y, z);
			else {
				path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
			}
			((EntityEnderman)ent).setPathToEntity(path);
		}
		if (ent instanceof EntitySlime || ent instanceof EntityMagmaCube || ent instanceof EntityGhast || ent instanceof EntitySquid) {
			if (attract) {
				if (!(ent instanceof EntitySlime) || !ent.onGround) {
					path = ent.getNavigator().getPathToXYZ(x, y, z);
					ent.motionX = 0.02*(x-ent.posX);
					if (!(ent instanceof EntityWaterMob) || ent.isInWater())
						ent.motionY = 0.02*(y-ent.posY);
					ent.motionZ = 0.02*(z-ent.posZ);
				}
			}
			else {
				if (!(ent instanceof EntitySlime) || !ent.onGround) {
					path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
					ent.motionX = -0.02*(x-ent.posX);
					if (!(ent instanceof EntityWaterMob) || ent.isInWater())
						ent.motionY = -0.02*(y-ent.posY);
					ent.motionZ = -0.02*(z-ent.posZ);
				}
			}
			float var1 = (float)ReikaMathLibrary.py3d(ent.motionX, 0, ent.motionZ);
			ent.renderYawOffset += (-((float)Math.atan2(ent.motionX, ent.motionZ)) * 180.0F / (float)Math.PI - ent.renderYawOffset) * 0.1F;
			ent.rotationYaw = ent.renderYawOffset;
			if (!world.isRemote)
				ent.velocityChanged = true;
		}
		if (ent instanceof EntityBat) {
			if (attract) {
				path = ent.getNavigator().getPathToXYZ(x, y, z);
				ent.motionX = 0.1*(x-ent.posX);
				ent.motionY = 0.1*(y-ent.posY);
				ent.motionZ = 0.1*(z-ent.posZ);
			}
			else {
				path = ent.getNavigator().getPathToXYZ(xyz[0], xyz[1], xyz[2]);
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
