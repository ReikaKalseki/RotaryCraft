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
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoriedCannon;
import Reika.RotaryCraft.Entities.EntityFreezeGunShot;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

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
		if (slot != -1 && ReikaInventoryHelper.canAcceptMoreOf(Items.snowball, 0, inv)) {
			ReikaInventoryHelper.decrStack(slot, inv);
			ReikaInventoryHelper.addToIInv(new ItemStack(Items.snowball, 4, 0), this);
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
		List inrange = world.getEntitiesWithinAABB(EntityLivingBase.class, range);
		double mindist = this.getRange()+2;
		int i_at_min = -1;
		for (int i = 0; i < inrange.size(); i++) {
			EntityLivingBase ent = (EntityLivingBase)inrange.get(i);
			double dist = ReikaMathLibrary.py3d(ent.posX-x-0.5, ent.posY-y-0.5, ent.posZ-z-0.5);
			if (this.isValidTarget(ent)) {
				if (ReikaWorldHelper.canBlockSee(world, x, y, z, ent.posX, ent.posY, ent.posZ, this.getRange())) {
					if (!ent.isDead && ent.getHealth() > 0 && ent.getActivePotionEffect(RotaryCraft.freeze) == null) {
						//ReikaJavaLibrary.pConsole(ent);
						double dy = -(ent.posY-y);
						double reqtheta = -90+Math.toDegrees(Math.abs(Math.acos(dy/dist)));
						if ((reqtheta <= dir*MAXLOWANGLE && dir == -1) || (reqtheta >= dir*MAXLOWANGLE && dir == 1))
							if (dist < mindist && !ent.getActivePotionEffects().contains(RotaryCraft.freeze)) {
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
			EntityFreezeGunShot snow = new EntityFreezeGunShot(world, xCoord+0.5+dx, y, zCoord+0.5+dz, 3*v[0], 3*v[1], 3*v[2]);
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
	protected boolean isValidTarget(EntityLivingBase ent) {
		return this.isMobOrUnlistedPlayer(ent);
	}
}