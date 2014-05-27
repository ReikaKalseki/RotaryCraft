/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.TileEntities.World.TileEntitySonicBorer;

public class EntitySonicShot extends EntityFireball {

	private final TileEntitySonicBorer te;
	private final String player;

	public EntitySonicShot(World par1World) {
		super(par1World);
		te = null;
		player = null;
	}

	public EntitySonicShot(World world, TileEntitySonicBorer tile, String player) {
		super(world, tile.xCoord, tile.yCoord, tile.zCoord, 0, 0, 0);
		te = tile;
		this.player = player;
		this.setPosition(tile.xCoord+0.5+tile.xstep, tile.yCoord+0.5+tile.ystep, tile.zCoord+0.5+tile.zstep);

		double dd = 2;
		motionX = tile.xstep*dd;
		motionY = tile.ystep*dd;
		motionZ = tile.zstep*dd;
		accelerationX = motionX;
		accelerationY = motionY;
		accelerationZ = motionZ;

		if (!world.isRemote)
			velocityChanged = true;
	}

	public int[] getSteps() {
		int[] steps = new int[3];
		if (motionX != 0) {
			steps[0] = motionX > 0 ? 1 : -1;
		}
		if (motionY != 0) {
			steps[1] = motionY > 0 ? 1 : -1;
		}
		if (motionZ != 0) {
			steps[2] = motionZ > 0 ? 1 : -1;
		}
		return steps;
	}

	@Override
	protected void onImpact(MovingObjectPosition mov) {
		World world = worldObj;
		//ReikaJavaLibrary.pConsole(mov != null ? (mov.blockX+","+mov.blockY+","+mov.blockZ) : "null");
		if (mov != null) {
			int x = mov.blockX; int y = mov.blockY; int z = mov.blockZ;
			this.breakBlocks(world, x, y, z);
			this.hurtMobs(world, x, y, z);
			this.setDead();
		}
	}

	private void hurtMobs(World world, int x, int y, int z) {
		AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, y, z).expand(3, 3, 3);
		List<EntityLivingBase> li = world.getEntitiesWithinAABB(EntityLivingBase.class, box);
		for (int i = 0; i < li.size(); i++) {
			EntityLivingBase e = li.get(i);
			e.attackEntityFrom(DamageSource.inWall, 1);
		}
	}

	@Override
	public void onUpdate() {
		this.onEntityUpdate();
		ticksExisted++;

		if (ticksExisted > 1200) {
			this.setDead();
			return;
		}

		if (te != null) {
			int x = (int)Math.floor(posX);
			int y = (int)Math.floor(posY);
			int z = (int)Math.floor(posZ);
			int d = te.xstep*(x-te.xCoord)+te.ystep*(y-te.yCoord)+te.zstep*(z-te.zCoord);
			int r = this.getRange();
			if (d >= r) {
				Vec3 vec = Vec3.fakePool.getVecFromPool(0, 0, 0);
				int[] tg = te.getTargetPosn();
				MovingObjectPosition mov = new MovingObjectPosition(tg[0], tg[1], tg[2], -1, vec);
				this.onImpact(mov);
			}
		}
		this.setPosition(posX+motionX, posY+motionY, posZ+motionZ);
		int dx; int dy; int dz;
		if (te != null) {
			int r = 3;
			dx = te.xstep == 0 ? r : 0;
			dy = te.ystep == 0 ? r : 0;
			dz = te.zstep == 0 ? r : 0;
		}
		else {
			dx = dy = dz = 2;
			AxisAlignedBB box = this.getBoundingBox().expand(dx, dy, dz);
			List<Entity> li = worldObj.getEntitiesWithinAABB(Entity.class, box);
			for (int i = 0; i < li.size(); i++) {
				Entity e = li.get(i);
				this.applyEntityCollision(e);
			}
		}
	}

	private int getRange() {
		return te.getDistanceToSurface();
	}

	@Override
	public final boolean canRenderOnFire() {
		return false;
	}

	@Override
	public final AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(posX+0.4, posY+0.4, posZ+0.4, posX+0.6, posY+0.6, posZ+0.6);
	}

	private void breakBlocks(World world, int x, int y, int z) {
		int k = TileEntitySonicBorer.FOV;
		if (!world.isRemote) {
			if (te.xstep != 0) {
				for (int i = z-k; i <= z+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						this.dropBlockAt(world, x, j, i);
					}
				}
			}
			else if (te.zstep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = y-k; j <= y+k; j++) {
						this.dropBlockAt(world, i, j, z);
					}
				}
			}
			else if (te.ystep != 0) {
				for (int i = x-k; i <= x+k; i++) {
					for (int j = z-k; j <= z+k; j++) {
						this.dropBlockAt(world, i, y, j);
					}
				}
			}
		}
		ReikaSoundHelper.playSoundAtBlock(world, x, y, z, "random.explode");
		ReikaParticleHelper.EXPLODE.spawnAt(world, x, y, z);
	}

	private void dropBlockAt(World world, int x, int y, int z) {
		if (y == 0)
			return;
		int id = world.getBlockId(x, y, z);
		if (id == 0)
			return;
		int meta = world.getBlockMetadata(x, y, z);
		if (!ReikaPlayerAPI.playerCanBreakAt(world, x, y, z, id, meta, player))
			return;
		Block b = Block.blocksList[id];
		if (!TileEntitySonicBorer.canDrop(world, x, y, z) && !(b instanceof BlockFluid))
			return;
		List<ItemStack> li = b.getBlockDropped(world, x, y, z, meta, 0);
		if (b.blockID == Block.mobSpawner.blockID) {
			ItemStack is = new ItemStack(RotaryCraft.spawner);
			TileEntityMobSpawner te = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);
			ReikaSpawnerHelper.addMobNBTToItem(is, te);
			li.add(is);
		}
		ReikaItemHelper.dropItems(world, x+0.5, y+0.5, z+0.5, li);
		world.setBlock(x, y, z, 0);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public void applyEntityCollision(Entity e)
	{
		e.motionX = motionX;
		e.motionY = motionY;
		e.motionZ = motionZ;
	}

	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 1;
	}
}
