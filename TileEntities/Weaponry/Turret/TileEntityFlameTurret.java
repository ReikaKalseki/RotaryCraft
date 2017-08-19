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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Interfaces.TargetEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityFluidCannon;
import Reika.RotaryCraft.Entities.EntityFlameTurretShot;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;


public class TileEntityFlameTurret extends TileEntityFluidCannon {

	@Override
	public int getRange() {
		return 32;
	}

	@Override
	public int getMaxRange() {
		return 32;
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
						if (dist < mindist && dist >= 6) { //has a min range too
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
		double dist = ReikaMathLibrary.py3d(xyz[0]-xCoord, xyz[1]-yCoord, xyz[2]-zCoord);
		double speed = 0.25*(Math.pow(dist, 0.7)/7D)*ReikaRandomHelper.getRandomPlusMinus(1, 0.125);
		tank.removeLiquid(1);
		double[] v = ReikaPhysicsHelper.polarToCartesian(speed, theta+20, -phi+90);
		double dx = v[0];
		double dy = v[1];
		double dz = v[2];
		if (!world.isRemote) {
			double y = this.getFiringPositionY(dy);
			world.spawnEntityInWorld(new EntityFlameTurretShot(world, xCoord+0.5+dx, y, zCoord+0.5+dz, v[0], v[1], v[2], this, tank.getActualFluid().equals(FluidRegistry.getFluid("rc jet fuel"))));
		}
		if (this.getTicksExisted()%34 == 0)
			SoundRegistry.FLAMETURRET.playSoundAtBlock(this, 1, 1);
		//ReikaSoundHelper.playSoundAtBlock(world, xCoord, yCoord, zCoord, "mob.blaze.hit", 0.25F, 0.25F);
	}

	@Override
	protected double getThetaOffset() {
		return 20;
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

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.FLAMETURRET;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		if (power < MINPOWER || omega < MINSPEED) {
			return;
		}

		//if (!this.isAimingAtTarget(world, x, y, z, target)) //fire constantly
		//	return;

		if (tickcount < this.getOperationTime())
			return;

		tickcount = 0;
		if (!world.isRemote) {
			if (target[3] == 1 && this.hasAmmo()) {
				this.fire(world, target);
			}
		}
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE || m == MachineRegistry.BEDPIPE;
	}

	@Override
	public boolean isValidFluid(Fluid f) {
		return f.equals(FluidRegistry.getFluid("rc jet fuel")) || f.equals(FluidRegistry.getFluid("rc ethanol"));
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

}
