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

import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
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

	private static final HashMap<String, FlameAttack> damageMultipliers = new HashMap();

	static {
		damageMultipliers.put("oil", new FlameAttack(0.75F, 6, 0.4F, 0));
		damageMultipliers.put("fuel", new FlameAttack(1F, 3));
		damageMultipliers.put("rc ethanol", new FlameAttack(1.2F, 4, 1, 8));
		damageMultipliers.put("bioethanol", new FlameAttack(1.35F, 4, 1, 8)); //forestry
		damageMultipliers.put("rc jet fuel", new FlameAttack(1.8F, 6));
		damageMultipliers.put("rocket fuel", new FlameAttack(2F, 10));
	}

	@Override
	public int getRange() {
		return tank.isEmpty() ? 0 : (int)(32*damageMultipliers.get(tank.getActualFluid().getName()).rangeMultiplier);
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
			world.spawnEntityInWorld(new EntityFlameTurretShot(world, xCoord+0.5+dx, y, zCoord+0.5+dz, v[0], v[1], v[2], this, damageMultipliers.get(tank.getActualFluid().getName())));
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
		return damageMultipliers.containsKey(f.getName());
	}

	@Override
	public boolean canReceiveFrom(ForgeDirection from) {
		return true;
	}

	public static class FlameAttack {

		public final float damageMultiplier;
		public final int burnTime;
		public final float rangeMultiplier;
		public final int fireBlockLife;

		private static final int DEFAULT_FIRE_LIFE = 4; //lower is higher life

		private FlameAttack(float f, int burn) {
			this(f, burn, 1, DEFAULT_FIRE_LIFE);
		}

		private FlameAttack(float f, int burn, int life) {
			this(f, burn, 1, life);
		}

		private FlameAttack(float f, int burn, float r) {
			this(f, burn, r, DEFAULT_FIRE_LIFE);
		}

		private FlameAttack(float f, int burn, float r, int life) {
			damageMultiplier = f;
			burnTime = burn;
			rangeMultiplier = r;
			fireBlockLife = life;
		}

	}

}
