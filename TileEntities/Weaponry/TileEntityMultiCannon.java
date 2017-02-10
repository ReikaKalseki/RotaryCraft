package Reika.RotaryCraft.TileEntities.Weaponry;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.Java.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaPhysicsHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.API.Interfaces.TargetEntity;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.TileEntity.TileEntityInventoriedCannon;
import Reika.RotaryCraft.Entities.EntityGatlingShot;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;


public class TileEntityMultiCannon extends TileEntityInventoriedCannon {

	private static final int FIRE_RATE = 1; //ticks per shot
	private static final int AMMO_RATE = 10; //ticks per unit ammo
	public static final double AMMO_PER_SHOT = FIRE_RATE/(double)AMMO_RATE;

	private static final int RELOAD_TIME = 80; //ticks
	private static final int FEED_TIME = 4;

	public static final int LOAD_SLOT = 0;
	public static final int CLIP_SLOT = 36;

	private static final double SPIN_RATE = 25;
	private static final double SPIN_DELTA = 5;

	private static final double JITTER = 0.625;
	public static final String SLIME_NBT = "embedded_rounds";

	private double spinAngle;
	private double spinSpeed;

	private int reloadTimer;
	private int feedTick;

	public double getSpinAngle() {
		return spinAngle;
	}

	private void startReload() {
		reloadTimer = RELOAD_TIME;
		if (inv[CLIP_SLOT-1] != null)
			SoundRegistry.GATLINGRELOAD.playSoundAtBlock(this, 0.75F, 0.95F);
	}

	private void doReload() {
		reloadTimer--;
		if (reloadTimer == 0) {
			ReikaArrayHelper.cycleArray(inv, null);
			SoundRegistry.PROJECTOR.playSoundAtBlock(this, 2, 0.75F);
		}
	}

	@Override
	public int getSizeInventory() {
		return CLIP_SLOT-LOAD_SLOT+1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack is) {
		return slot == LOAD_SLOT && ReikaItemHelper.matchStacks(is, ItemStacks.ballbearing);
	}

	@Override
	public int getRange() {
		return 120;
	}

	@Override
	public int getMaxRange() {
		return 120;
	}

	@Override
	public boolean hasAmmo() {
		return ReikaItemHelper.matchStacks(ItemStacks.ballbearing, inv[CLIP_SLOT]);
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
		double speed = 1.5;
		if (ReikaRandomHelper.doWithChance(AMMO_PER_SHOT)) {
			ReikaInventoryHelper.decrStack(CLIP_SLOT, inv);
			if (inv[CLIP_SLOT] == null) {
				this.startReload();
			}
		}
		double[] v = ReikaPhysicsHelper.polarToCartesian(speed, theta, -phi+90);
		double dx = v[0];
		double dy = v[1];
		double dz = v[2];
		if (!world.isRemote) {
			double y = this.getFiringPositionY(dy);
			world.spawnEntityInWorld(new EntityGatlingShot(world, xCoord+0.5+dx, y, zCoord+0.5+dz, v[0], v[1], v[2], this));
		}
		SoundRegistry.GATLING.playSoundAtBlock(this, 0.25F, 1.125F);
		//ReikaSoundHelper.playSoundAtBlock(world, xCoord, yCoord, zCoord, "mob.blaze.hit", 0.25F, 0.25F);
	}

	@Override
	protected double randomOffset() {
		return JITTER;
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
		return MachineRegistry.GATLING;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();

		spinAngle += spinSpeed;

		if (power < MINPOWER || omega < MINSPEED) {
			spinSpeed = Math.max(spinSpeed-SPIN_DELTA, 0);
			return;
		}

		if (!world.isRemote) {
			if (feedTick > 0)
				feedTick--;
			else
				this.continuousFeed();

			if (reloadTimer == 0) {
				if (inv[CLIP_SLOT] == null) {
					this.startReload();
				}
			}

			if (reloadTimer > 0) {
				this.doReload();
				return;
			}
		}

		if (!this.hasAmmo()) {
			spinSpeed = Math.max(spinSpeed-SPIN_DELTA, 0);
			return;
		}
		//if (!this.isAimingAtTarget(world, x, y, z, target)) //fire constantly
		//	return;

		if (tickcount < this.getOperationTime())
			return;

		tickcount = 0;
		if (!world.isRemote) {
			if (target[3] == 1) {
				if (spinSpeed < SPIN_RATE)
					spinSpeed = Math.min(spinSpeed+SPIN_DELTA, SPIN_RATE);
				else
					this.fire(world, target);
			}
			else {
				spinSpeed = Math.max(spinSpeed-SPIN_DELTA, 0);
			}
		}
	}

	private void continuousFeed() {
		for (int i = CLIP_SLOT-1; i > LOAD_SLOT; i--) {
			if (inv[i-1] != null) {
				boolean flag = false;
				if (inv[i] == null) {
					inv[i] = inv[i-1];
					inv[i-1] = null;
					flag = true;
				}
				else if (inv[i].stackSize < 64) {
					int amt = Math.min(64-inv[i].stackSize, inv[i-1].stackSize);
					inv[i].stackSize += amt;
					ReikaInventoryHelper.decrStack(i-1, this, amt);
					flag = true;
				}
				if (flag) {
					feedTick = FEED_TIME;
					return;
				}
			}
		}
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public int getOperationTime() {
		return FIRE_RATE;
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);
		NBT.setDouble("spin", spinAngle);
		NBT.setInteger("reload", reloadTimer);
		NBT.setInteger("feed", feedTick);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);
		spinAngle = NBT.getDouble("spin");
		feedTick = NBT.getInteger("feed");
		reloadTimer = NBT.getInteger("reload");
	}

	public boolean isReloading() {
		return reloadTimer > 0;
	}

}
