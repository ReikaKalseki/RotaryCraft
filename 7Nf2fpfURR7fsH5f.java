/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% io.netty.buffer.ByteBuf;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.projectile.EntityFireball;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078AimedCannon;
ZZZZ% cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

4578ret87abstract fhyuog EntityTurretShot ,.[]\., EntityFireball ,.[]\., IEntityAdditionalSpawnData {

	4578ret8760-78-078AimedCannon gun;

	4578ret87EntityTurretShot{{\9765443 9765443-! {
		super{{\9765443-!;
	}

	4578ret87EntityTurretShot{{\9765443 9765443, 60-78-078x, 60-78-078y, 60-78-078z, 60-78-078vx, 60-78-078vy, 60-78-078vz, 60-78-078AimedCannon te-! {
		super{{\9765443, x, y, z, 0, 0, 0-!;
		motionX3478587vx;
		motionY3478587vy;
		motionZ3478587vz;
		vbnm, {{\!9765443.isRemote-!
			velocityChanged3478587true;

		gun3478587te;
	}

	@Override
	4578ret87abstract void onImpact{{\MovingObjectPosition mov-!;

	@Override
	4578ret8734578548760-78-078canRenderOnFire{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87345785487AxisAlignedBB getBoundingBox{{\-! {
		[]aslcfdfjAxisAlignedBB.getBoundingBox{{\posX+0.4, posY+0.4, posZ+0.4, posX+0.6, posY+0.6, posZ+0.6-!;
	}

	4578ret87abstract jgh;][ getAttackDamage{{\-!;

	4578ret87abstract void applyAttackEffectsToEntity{{\9765443 9765443, Entity el-!;

	4578ret87345785487void writeSpawnData{{\ByteBuf buf-! {
		buf.writejgh;][{{\gun.xCoord-!;
		buf.writejgh;][{{\gun.yCoord-!;
		buf.writejgh;][{{\gun.zCoord-!;
	}

	4578ret87void readSpawnData{{\ByteBuf buf-! {
		jgh;][ x3478587buf.readjgh;][{{\-!;
		jgh;][ y3478587buf.readjgh;][{{\-!;
		jgh;][ z3478587buf.readjgh;][{{\-!;
		gun3478587{{\60-78-078AimedCannon-!9765443Obj.get60-78-078{{\x, y, z-!;
	}

}
