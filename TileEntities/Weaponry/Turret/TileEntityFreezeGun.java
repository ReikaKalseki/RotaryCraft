/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Weaponry.Turret;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Interfaces.TargetEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoriedCannon;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityFreezeGun extends TileEntityInventoriedCannon {

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (!this.isAimingAtTarget(world, x, y, z, target))
			return;
		this.convertSnow();
		if (!this.hasAmmo())
			return;
		if (tickcount < this.getOperationTime())
			return;
		if (power < MINPOWER)
			return;
		tickcount = 0;
		if (target[3] == 1) {
			this.fire(world, target);
		}
	}

	public static PotionEffect getFreezeEffect(int duration) {
		PotionEffect pot = new PotionEffect(RotaryCraft.freeze.id, duration, 0);
		pot.setCurativeItems(new ArrayList());
		return pot;
	}

	private void convertSnow() {
		int slot = ReikaInventoryHelper.locateInInventory(Blocks.snow, inv);
		if (slot != -1 && ReikaInventoryHelper.canAcceptMoreOf(Items.snowball, 0, 4, this)) {
			ReikaInventoryHelper.decrStack(slot, inv);
			ReikaInventoryHelper.addToIInv(new ItemStack(Items.snowball, 4, 0), this);
		}
		slot = ReikaInventoryHelper.locateInInventory(Blocks.ice, inv);
		if (slot != -1 && ReikaInventoryHelper.canAcceptMoreOf(Items.snowball, 0, 16, this)) {
			ReikaInventoryHelper.decrStack(slot, inv);
			ReikaInventoryHelper.addToIInv(new ItemStack(Items.snowball, 16, 0), this);
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRange() {
		return 64;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FREEZEGUN;
	}

	@Override
	public int getMaxRange() {
		return 256;
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public boolean hasAmmo() {
		return ReikaInventoryHelper.checkForItem(Blocks.ice, inv) || ReikaInventoryHelper.checkForItem(Items.snowball, inv);
	}

	@Override
	protected double[] getTarget(World world, int x, int y, int z) {
		double[] xyzb = new double[4];
		int r = this.getRange();
		AxisAlignedBB range = AxisAlignedBB.getBoundingBox(x-r, y-r, z-r, x+1+r, y+1+r, z+1+r);
		List<Entity> inrange = world.getEntitiesWithinAABB(Entity.class, range);
		double mindist = this.getRange()+2;
		Entity i_at_min = null;
		for (Entity ent : inrange) {
			double dist = ReikaMathLibrary.py3d(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5);
			if (this.isValidTarget(ent)) {
				if (ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange())) {
					//ReikaJavaLibrary.pConsole(ent);
					double dy = -(ent.posY-y);
					double reqtheta = -90+Math.toDegrees(Math.abs(Math.acos(dy/dist)));
					if ((reqtheta <= dir*MAXLOWANGLE && dir == -1) || (reqtheta >= dir*MAXLOWANGLE && dir == 1)) {
						if (dist < mindist) {
							mindist = dist;
							i_at_min = ent;
						}
					}
				}
			}
		}
		if (i_at_min == null)
			return xyzb;
		closestMob = i_at_min;
		xyzb[0] = closestMob.posX+this.randomOffset();
		xyzb[1] = closestMob.posY+closestMob.getEyeHeight()*0.25+this.randomOffset();
		xyzb[2] = closestMob.posZ+this.randomOffset();
		xyzb[3] = 1;
		return xyzb;
	}

	@Override
	public void fire(World world, double[] xyz) {
		double speed = 1;
		int slot = ReikaInventoryHelper.locateInInventory(Items.snowball, inv);
		ReikaInventoryHelper.decrStack(slot, inv);
		double[] v = new double[3];
		v[0] = xyz[0]-xCoord;
		v[1] = xyz[1]-yCoord;
		v[2] = xyz[2]-zCoord;
		double dd = ReikaMathLibrary.py3d(v[0], v[1], v[2]);
		for (int i = 0; i < 3; i++)
			v[i] /= dd;
		for (int i = 0; i < 3; i++)
			v[i] *= speed;
		dd = ReikaMathLibrary.py3d(v[0], v[1], v[2]);
		double dx = v[0]/dd;
		double dy = v[1]/dd;
		double dz = v[2]/dd;
		//ReikaJavaLibrary.pConsole(dx+"  "+dy+"  "+dz);
		if (!world.isRemote) {
			double y = this.getFiringPositionY(dy);
			EntityFreezeGunShot snow = new EntityFreezeGunShot(world, xCoord+0.5+dx, y, zCoord+0.5+dz, 3*v[0], 3*v[1], 3*v[2], this);
			world.spawnEntityInWorld(snow);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return ReikaItemHelper.matchStackWithBlock(is, Blocks.ice) || ReikaItemHelper.matchStackWithBlock(is, Blocks.snow) || is.getItem() == Items.snowball;
	}

	@Override
	protected double randomOffset() {
		return 0;
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.hasAmmo())
			return 15;
		return 0;
	}

	@Override
	protected boolean isValidTarget(Entity ent) {
		if (ent.isDead)
			return false;
		if (ent instanceof TargetEntity)
			return ((TargetEntity)ent).shouldTarget(this, placerUUID);
		if (!(ent instanceof EntityLivingBase))
			return false;
		EntityLivingBase elb = (EntityLivingBase)ent;
		return elb.getHealth() > 0 && this.isMobOrUnlistedPlayer(elb) && elb.getActivePotionEffect(RotaryCraft.freeze) == null;
	}
}
