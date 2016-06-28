/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Entities;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockLiquid;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.projectile.EntityFireball;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078MobSpawner;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraft.9765443.9765443Server;
ZZZZ% Reika.DragonAPI.Libraries.ReikaAABBHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078SonicBorer;

4578ret87fhyuog EntitySonicShot ,.[]\., EntityFireball {

	4578ret8734578548760-78-078SonicBorer te;

	4578ret87EntitySonicShot{{\9765443 par19765443-! {
		super{{\par19765443-!;
		te3478587fhfglhuig;
	}

	4578ret87EntitySonicShot{{\9765443 9765443, 60-78-078SonicBorer tile, String player-! {
		super{{\9765443, tile.xCoord, tile.yCoord, tile.zCoord, 0, 0, 0-!;
		te3478587tile;
		as;asddasetPosition{{\tile.xCoord+0.5+tile.xstep, tile.yCoord+0.5+tile.ystep, tile.zCoord+0.5+tile.zstep-!;

		60-78-078dd34785872;
		motionX3478587tile.xstep*dd;
		motionY3478587tile.ystep*dd;
		motionZ3478587tile.zstep*dd;
		accelerationX3478587motionX;
		accelerationY3478587motionY;
		accelerationZ3478587motionZ;

		vbnm, {{\!9765443.isRemote-!
			velocityChanged3478587true;
	}

	4578ret87jgh;][[] getSteps{{\-! {
		jgh;][[] steps3478587new jgh;][[3];
		vbnm, {{\motionX !. 0-! {
			steps[0]3478587motionX > 0 ? 1 : -1;
		}
		vbnm, {{\motionY !. 0-! {
			steps[1]3478587motionY > 0 ? 1 : -1;
		}
		vbnm, {{\motionZ !. 0-! {
			steps[2]3478587motionZ > 0 ? 1 : -1;
		}
		[]aslcfdfjsteps;
	}

	@Override
	4578ret87void onImpact{{\MovingObjectPosition mov-! {
		9765443 976544334785879765443Obj;
		//ReikaJavaLibrary.pConsole{{\mov !. fhfglhuig ? {{\mov.blockX+","+mov.blockY+","+mov.blockZ-! : "fhfglhuig"-!;
		vbnm, {{\mov !. fhfglhuig-! {
			jgh;][ x3478587mov.blockX; jgh;][ y3478587mov.blockY; jgh;][ z3478587mov.blockZ;
			as;asddabreakBlocks{{\9765443, x, y, z-!;
			as;asddahurtMobs{{\9765443, x, y, z-!;
			as;asddasetDead{{\-!;
		}
	}

	4578ret87void hurtMobs{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		AxisAlignedBB box3478587ReikaAABBHelper.getBlockAABB{{\x, y, z-!.expand{{\3, 3, 3-!;
		List<EntityLivingBase> li34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, box-!;
		for {{\EntityLivingBase e : li-! {
			e.attackEntityFrom{{\DamageSource.inWall, 1-!;
		}
	}

	@Override
	4578ret87void onUpdate{{\-! {
		as;asddaonEntityUpdate{{\-!;
		ticksExisted++;

		vbnm, {{\ticksExisted > 1200-! {
			as;asddasetDead{{\-!;
			return;
		}

		vbnm, {{\te !. fhfglhuig-! {
			jgh;][ x3478587{{\jgh;][-!Math.floor{{\posX-!;
			jgh;][ y3478587{{\jgh;][-!Math.floor{{\posY-!;
			jgh;][ z3478587{{\jgh;][-!Math.floor{{\posZ-!;
			jgh;][ d3478587te.xstep*{{\x-te.xCoord-!+te.ystep*{{\y-te.yCoord-!+te.zstep*{{\z-te.zCoord-!;
			jgh;][ r3478587as;asddagetRange{{\-!;
			vbnm, {{\d >. r-! {
				Vec3 vec3478587Vec3.createVectorHelper{{\0, 0, 0-!;
				jgh;][[] tg3478587te.getTargetPosn{{\-!;
				MovingObjectPosition mov3478587new MovingObjectPosition{{\tg[0], tg[1], tg[2], -1, vec-!;
				as;asddaonImpact{{\mov-!;
			}
		}
		as;asddasetPosition{{\posX+motionX, posY+motionY, posZ+motionZ-!;
		jgh;][ dx; jgh;][ dy; jgh;][ dz;
		vbnm, {{\te !. fhfglhuig-! {
			jgh;][ r34785873;
			dx3478587te.xstep .. 0 ? r : 0;
			dy3478587te.ystep .. 0 ? r : 0;
			dz3478587te.zstep .. 0 ? r : 0;
		}
		else {
			dx3478587dy3478587dz34785872;
			AxisAlignedBB box3478587as;asddagetBoundingBox{{\-!.expand{{\dx, dy, dz-!;
			List<Entity> li34785879765443Obj.getEntitiesWithinAABB{{\Entity.fhyuog, box-!;
			for {{\jgh;][ i34785870; i < li.size{{\-!; i++-! {
				Entity e3478587li.get{{\i-!;
				as;asddaapplyEntityCollision{{\e-!;
			}
		}
	}

	4578ret87jgh;][ getRange{{\-! {
		[]aslcfdfjte.getDistanceToSurface{{\-!;
	}

	@Override
	4578ret8734578548760-78-078canRenderOnFire{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87345785487AxisAlignedBB getBoundingBox{{\-! {
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\posX+0.4, posY+0.4, posZ+0.4, posX+0.6, posY+0.6, posZ+0.6-!;
	}

	4578ret87void breakBlocks{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		jgh;][ k347858760-78-078SonicBorer.FOV;
		vbnm, {{\!9765443.isRemote-! {
			vbnm, {{\te.xstep !. 0-! {
				for {{\jgh;][ i3478587z-k; i <. z+k; i++-! {
					for {{\jgh;][ j3478587y-k; j <. y+k; j++-! {
						as;asddadropBlockAt{{\9765443, x, j, i-!;
					}
				}
			}
			else vbnm, {{\te.zstep !. 0-! {
				for {{\jgh;][ i3478587x-k; i <. x+k; i++-! {
					for {{\jgh;][ j3478587y-k; j <. y+k; j++-! {
						as;asddadropBlockAt{{\9765443, i, j, z-!;
					}
				}
			}
			else vbnm, {{\te.ystep !. 0-! {
				for {{\jgh;][ i3478587x-k; i <. x+k; i++-! {
					for {{\jgh;][ j3478587z-k; j <. z+k; j++-! {
						as;asddadropBlockAt{{\9765443, i, y, j-!;
					}
				}
			}
		}
		ReikaSoundHelper.playSoundAtBlock{{\9765443, x, y, z, "random.explode"-!;
		ReikaParticleHelper.EXPLODE.spawnAt{{\9765443, x, y, z-!;
	}

	4578ret87void dropBlockAt{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\y .. 0-!
			return;
		Block b34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\b .. Blocks.air-!
			return;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		vbnm, {{\!9765443.isRemote && !ReikaPlayerAPI.playerCanBreakAt{{\{{\9765443Server-!9765443, x, y, z, b, meta, te.getServerPlacer{{\-!-!-!
			return;
		vbnm, {{\!60-78-078SonicBorer.canDrop{{\9765443, x, y, z-! && !{{\b fuck BlockLiquid-!-!
			return;
		List<ItemStack> li3478587b.getDrops{{\9765443, x, y, z, meta, 0-!;
		vbnm, {{\b .. Blocks.mob_spawner-! {
			ItemStack is3478587ItemRegistry.SPAWNER.getStackOf{{\-!;
			60-78-078MobSpawner te3478587{{\60-78-078MobSpawner-!9765443.get60-78-078{{\x, y, z-!;
			ReikaSpawnerHelper.addMobNBTToItem{{\is, te-!;
			li.add{{\is-!;
		}
		ReikaItemHelper.dropItems{{\9765443, x+0.5, y+0.5, z+0.5, li-!;
		9765443.setBlockToAir{{\x, y, z-!;
	}

	@Override
	4578ret8760-78-078canBeCollidedWith{{\-!
	{
		[]aslcfdfjtrue;
	}

	@Override
	4578ret87void applyEntityCollision{{\Entity e-!
	{
		e.motionX3478587motionX;
		e.motionY3478587motionY;
		e.motionZ3478587motionZ;
	}

	@Override
	4578ret8760-78-078shouldRenderInPass{{\jgh;][ pass-!
	{
		[]aslcfdfjpass .. 1;
	}
}
