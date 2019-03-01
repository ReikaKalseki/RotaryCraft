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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Interfaces.RailGunAmmo;
import Reika.RotaryCraft.API.Interfaces.RailGunAmmo.RailGunAmmoType;
import Reika.RotaryCraft.API.Interfaces.TargetEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoriedCannon;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityRailGun extends TileEntityInventoriedCannon {

	private RailGunAmmoType ammoType;

	@Override
	public boolean hasAmmo() {
		this.checkAmmo();
		return ammoType != null;
	}

	private void checkAmmo() {
		for (int i = 0; i < inv.length; i++) {
			RailGunAmmoType rg = this.getAmmo(inv[i]);
			if (rg != null) {
				if (torque >= rg.getRequiredTorque()) {
					if (ammoType == null || rg.compareTo(ammoType) > 0)
						ammoType = rg;
				}
			}
		}
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (power < MINPOWER)
			return;
		if (!this.hasAmmo())
			return;
		if (!this.isAimingAtTarget(world, x, y, z, target))
			return;
		if (tickcount < this.getOperationTime())
			return;
		tickcount = 0;
		if (target[3] == 1) {
			if (!world.isRemote)
				this.fire(world, target);
		}
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
		double speed = 4;
		ItemStack is = ammoType.getItem();
		int slot = ReikaInventoryHelper.locateInInventory(is.getItem(), is.getItemDamage(), inv);
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
			Entity e = ammoType.getProjectileEntity(world, xCoord+0.5+dx, y, zCoord+0.5+dz, v[0], v[1], v[2], this);
			if (e != null) {
				world.spawnEntityInWorld(e);
			}
		}
	}

	public int getRange() {
		return 164;
	}

	public Entity getClosestMob() {
		return closestMob;
	}

	/*
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }*/

	@Override
	protected double randomOffset() {
		//return -0.5+par5Random.nextFloat();
		return 0;
	}

	@Override
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.RAILGUN;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return is.getItem() instanceof RailGunAmmo;
	}

	@Override
	public int getMaxRange() {
		return 256;
	}

	@Override
	public int getRedstoneOverride() {
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
		return elb.getHealth() > 0 && this.isMobOrUnlistedPlayer(elb);
	}

	private static RailGunAmmoType getAmmo(ItemStack is) {
		if (is == null)
			return null;
		if (is.getItem() instanceof RailGunAmmo) {
			return ((RailGunAmmo)is.getItem()).getAmmo(is);
		}
		return null;
	}

}
