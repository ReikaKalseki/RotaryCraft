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

import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TileEntityLaserGun extends TileEntityAimedCannon {

	private int range;

	@Override
	public int getRange() {
		return range;
	}

	@Override
	public int getMaxRange() {
		return 256;
	}

	@Override
	public boolean hasAmmo() {
		return true;
	}

	@Override
	protected double[] getTarget(World world, int x, int y, int z) {
		double[] xyzb = new double[4];
		int r = this.getRange();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x-r, y-r, z-r, x+1+r, y+1+r, z+1+r);
		List inrange = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
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
		if (world.isRemote)
			return;
		for (float i = 0; i <= this.getMaxRange(); i += 0.5F) {
			double dx = i*Math.cos(Math.toRadians(theta))*Math.cos(Math.toRadians(-phi+90));
			double dy = i*Math.sin(Math.toRadians(theta));
			double dz = i*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(-phi+90));
			int r = 1;
			AxisAlignedBB light = AxisAlignedBB.getBoundingBox(xCoord+dx, yCoord+dy, zCoord+dz, xCoord+dx, yCoord+dy, zCoord+dz).expand(r, r, r);
			List in = world.getEntitiesWithinAABB(EntityLivingBase.class, light);
			for (int k = 0; k < in.size(); k++) {
				EntityLivingBase e = (EntityLivingBase)in.get(k);
				e.attackEntityFrom(DamageSource.lava, 4);
				e.setFire(7);
			}
			int x = xCoord+(int)dx;
			int y = yCoord+(int)dy;
			int z = zCoord+(int)dz;
			Block id = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			Block id2 = this.getAffectedID(id, meta);
			int meta2 = this.getAffectedMetadata(id2, meta);
			//ReikaJavaLibrary.pConsole(id+"  to  "+id2+"  @  "+x+", "+y+", "+z);
			//ReikaJavaLibrary.pConsole(theta);
			if (ConfigRegistry.ATTACKBLOCKS.getState()) {
				if (id2 != id || meta2 != meta) {
					world.setBlock(x, y, z, id2, meta2, 3);
					world.markBlockForUpdate(x, y, z);
					this.setRange((int)i+1);
					return;
				}
				if (id == Blocks.netherrack) {
					world.newExplosion(null, x+0.5, y+0.5, z+0.5, 3F, true, true);
					world.markBlockForUpdate(x, y, z);
					this.setRange((int)i+1);
					return;
				}
				if (id == Blocks.tnt) {
					world.setBlockToAir(x, y, z);
					EntityTNTPrimed var6 = new EntityTNTPrimed(world, x+0.5D, y+0.5D, z+0.5D, null);
					world.spawnEntityInWorld(var6);
					world.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
					world.spawnParticle("lava", x+rand.nextFloat(), y+rand.nextFloat(), z+rand.nextFloat(), 0, 0, 0);
					this.setRange((int)i+1);
					return;
				}
			}
			if (id != Blocks.air && id.isOpaqueCube()) {
				this.setRange((int)i+1);
				return;
			}
			if (i == this.getMaxRange()) {
				this.setRange(this.getMaxRange());
			}
		}
	}

	private Block getAffectedID(Block id, int meta) {
		if (id == Blocks.air)
			return Blocks.air;
		if (id == Blocks.sand)
			return Blocks.glass;
		if (id == Blocks.stone || id == Blocks.stonebrick || id == Blocks.sandstone)
			return Blocks.flowing_lava;
		if (id == Blocks.grass || id == Blocks.mycelium)
			return Blocks.dirt;
		if (id == Blocks.dirt || id == Blocks.farmland)
			return Blocks.sand;
		if (id == Blocks.cobblestone)
			return Blocks.flowing_lava;
		if (id == Blocks.gravel)
			return Blocks.cobblestone;
		if (id == Blocks.gravel)
			return Blocks.cobblestone;
		if (id == Blocks.tallgrass || id == Blocks.web || id == Blocks.yellow_flower || id == Blocks.snow ||
				id == Blocks.red_flower || id == Blocks.red_mushroom || id == Blocks.brown_mushroom ||
				id == Blocks.deadbush || id == Blocks.wheat || id == Blocks.carrots || id == Blocks.potatoes || id == Blocks.vine ||
				id == Blocks.melon_stem || id == Blocks.pumpkin_stem || id == Blocks.waterlily)
			return Blocks.air;
		if (ReikaWorldHelper.flammable(id))
			return Blocks.fire;
		if (id == Blocks.ice || id == Blocks.snow)
			return Blocks.flowing_water;
		return id;
	}

	private int getAffectedMetadata(Block id, int meta) {
		return meta;
	}

	private void setRange(int i) {
		range = i;
	}

	@Override
	protected double randomOffset() {
		return 0;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.LASERGUN;
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		if (power < MINPOWER) {
			this.setRange(0);
			return;
		}
		this.setRange(this.getMaxRange());
		if (tickcount < 0)
			return;
		tickcount = 0;
		this.fire(world, null);
		//if (!this.isAimingAtTarget(world, x, y, z, target))
		//	return;
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
	protected boolean isValidTarget(EntityLivingBase ent) {
		return this.isMobOrUnlistedPlayer(ent);
	}

}