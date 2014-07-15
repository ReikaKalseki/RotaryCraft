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

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoriedCannon;
import Reika.RotaryCraft.Entities.EntityExplosiveShell;
import Reika.RotaryCraft.Entities.EntityRailGunShot;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityRailGun extends TileEntityInventoriedCannon {

	private boolean isExplosiveShell = false;

	public int getPowerLevel() {
		int meta = ReikaInventoryHelper.findMaxMetadataOfID(ItemRegistry.RAILGUN.getShiftedID(), inv);
		return meta;
	}

	@Override
	public boolean hasAmmo() {
		if (ReikaInventoryHelper.checkForItem(ItemRegistry.RAILGUN.getShiftedID(), inv)) {
			isExplosiveShell = false;
			return true;
		}
		else {
			isExplosiveShell = true;
			return ReikaInventoryHelper.checkForItem(ItemRegistry.SHELL.getShiftedID(), inv);
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
		AxisAlignedBB range = AxisAlignedBB.getAABBPool().getAABB(x-r, y-r, z-r, x+1+r, y+1+r, z+1+r);
		List inrange = world.getEntitiesWithinAABB(EntityLivingBase.class, range);
		double mindist = this.getRange()+2;
		int i_at_min = -1;
		for (int i = 0; i < inrange.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)inrange.get(i);
			double dist = ReikaMathLibrary.py3d(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5);
			if (this.isValidTarget(ent)) {
				if (ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange())) {
					if (!ent.isDead && ent.getHealth() > 0) {
						double dy = -(ent.posY-y);
						double reqtheta = -90+Math.toDegrees(Math.abs(Math.acos(dy/dist)));
						if ((reqtheta <= dir*MAXLOWANGLE && dir == -1) || (reqtheta >= dir*MAXLOWANGLE && dir == 1))
							if (dist < mindist) {
								mindist = dist;
								i_at_min = i;
							}
					}
				}
			}
		}
		if (i_at_min == -1)
			return xyzb;
		EntityLivingBase ent = (EntityLivingBase)inrange.get(i_at_min);
		closestMob = ent;
		xyzb[0] = ent.posX+this.randomOffset();
		xyzb[1] = ent.posY+ent.getEyeHeight()*0.25+this.randomOffset();
		xyzb[2] = ent.posZ+this.randomOffset();
		xyzb[3] = 1;
		return xyzb;
	}

	@Override
	public void fire(World world, double[] xyz) {
		double speed = 4;
		int maxmeta = this.getMaxThrust();
		if (isExplosiveShell) {
			int m = ReikaInventoryHelper.findMaxMetadataOfIDWithinMaximum(ItemRegistry.SHELL.getShiftedID(), inv, maxmeta);
			int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.SHELL.getShiftedID(), m, inv);
			ReikaInventoryHelper.decrStack(slot, inv);
		}
		else {
			int m = ReikaInventoryHelper.findMaxMetadataOfIDWithinMaximum(ItemRegistry.RAILGUN.getShiftedID(), inv, maxmeta);
			int slot = ReikaInventoryHelper.locateInInventory(ItemRegistry.RAILGUN.getShiftedID(), m, inv);
			ReikaInventoryHelper.decrStack(slot, inv);
		}
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
			if (isExplosiveShell) {
				world.spawnEntityInWorld(new EntityExplosiveShell(world, xCoord+0.5+dx, y, zCoord+0.5+dz, v[0], v[1], v[2], this));
			}
			else {
				int power = this.getPowerLevel();
				world.spawnEntityInWorld(new EntityRailGunShot(world, xCoord+0.5+dx, y, zCoord+0.5+dz, v[0], v[1], v[2], power, this));
			}
		}
	}

	private int getMaxThrust() {
		return (int)ReikaMathLibrary.logbase(torque*torque/512, 2);
	}

	public int getRange() {
		return 164;
	}

	public EntityLivingBase getClosestMob() {
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
		return is.itemID == ItemRegistry.RAILGUN.getShiftedID();
	}

	@Override
	public int getMaxRange() {
		return 256;
	}

	@Override
	public int getRedstoneOverride() {
		return this.getMaxThrust();
	}

	@Override
	protected boolean isValidTarget(EntityLivingBase ent) {
		return this.isMobOrUnlistedPlayer(ent);
	}

}
