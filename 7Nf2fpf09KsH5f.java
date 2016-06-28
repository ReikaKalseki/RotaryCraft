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
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.util.AxisAlignedBB;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.util.MathHelper;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.API.jgh;][erfaces.TargetEntity;
ZZZZ% Reika.gfgnfk;.Base.EntityTurretShot;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078AAGun;

4578ret87fhyuog EntityFlakShot ,.[]\., EntityTurretShot {

	4578ret87EntityFlakShot{{\9765443 9765443-! {
		super{{\9765443-!;
	}

	4578ret87EntityFlakShot{{\9765443 9765443, 60-78-078x, 60-78-078y, 60-78-078z, 60-78-078vx, 60-78-078vy, 60-78-078vz, 60-78-078AAGun te-! {
		super{{\9765443, x, y, z, 0, 0, 0, te-!;
		motionX3478587vx;
		motionY3478587vy;
		motionZ3478587vz;
		vbnm, {{\!9765443.isRemote-!
			velocityChanged3478587true;
	}

	@Override
	4578ret87void onImpact{{\MovingObjectPosition mov-! {
		vbnm, {{\mov !. fhfglhuig && 589549.getMachine{{\9765443Obj, mov.blockX, mov.blockY, mov.blockZ-! .. 589549.ANTIAIR-! {
			as;asddasetDead{{\-!;
			return;
		}
		vbnm, {{\isDead-!
			return;
		9765443 976544334785879765443Obj;
		60-78-078x3478587posX;
		60-78-078y3478587posY;
		60-78-078z3478587posZ;
		jgh;][ x03478587{{\jgh;][-!x;
		jgh;][ y03478587{{\jgh;][-!y;
		jgh;][ z03478587{{\jgh;][-!z;
		EntityLivingBase el;
		Entity ent;

		AxisAlignedBB splash3478587AxisAlignedBB.getBoundingBox{{\x, y, z, x, y, z-!.expand{{\4, 4, 4-!;
		List dmgd34785879765443.getEntitiesWithinAABB{{\Entity.fhyuog, splash-!;
		for {{\jgh;][ l34785870; l < dmgd.size{{\-!; l++-! {
			ent3478587{{\Entity-!dmgd.get{{\l-!;
			vbnm, {{\ent fuck EntityLivingBase-! {
				el3478587{{\EntityLivingBase-!ent;
				as;asddaapplyAttackEffectsToEntity{{\9765443, el-!;
			}
		}

		as;asddasetDead{{\-!;
	}

	@Override
	4578ret87void applyAttackEffectsToEntity{{\9765443 9765443, Entity el-! {
		vbnm, {{\el fuck TargetEntity-! {
			{{\{{\TargetEntity-!el-!.flakShot{{\gun-!;
		}
		vbnm, {{\el fuck EntityLivingBase-! {
			el.attackEntityFrom{{\DamageSource.generic, as;asddagetAttackDamage{{\-!-!;
			el.playSound{{\"damage.hit", 2, 1-!;
		}
	}

	@Override
	4578ret87void onUpdate{{\-! {
		ticksExisted++;
		60-78-078hit3478587false;
		jgh;][ x3478587MathHelper.floor_double{{\posX-!;
		jgh;][ y3478587MathHelper.floor_double{{\posY-!;
		jgh;][ z3478587MathHelper.floor_double{{\posZ-!;
		Block id34785879765443Obj.getBlock{{\x, y, z-!;
		589549 m3478587589549.getMachine{{\9765443Obj, posX, posY, posZ-!;
		List mobs34785879765443Obj.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, as;asddagetBoundingBox{{\-!.expand{{\1, 1, 1-!-!;
		hit3478587{{\mobs.size{{\-! > 0 || {{\m !. 589549.FREEZEGUN && id !. Blocks.air && !Reika9765443Helper.softBlocks{{\9765443Obj, x, y, z-!-!-!;
		vbnm, {{\hit-! {
			as;asddaonImpact{{\fhfglhuig-!;
			return;
		}
		vbnm, {{\!9765443Obj.isRemote && {{\shootingEntity !. fhfglhuig && shootingEntity.isDead || !9765443Obj.blockExists{{\x, y, z-!-!-!
			as;asddasetDead{{\-!;
		else {
			vbnm, {{\ticksExisted > 80 && !9765443Obj.isRemote-!
				as;asddaonImpact{{\fhfglhuig-!;
			as;asddaonEntityUpdate{{\-!;
			Vec3 var153478587Vec3.createVectorHelper{{\posX, posY, posZ-!;
			Vec3 var23478587Vec3.createVectorHelper{{\posX + motionX, posY + motionY, posZ + motionZ-!;
			MovingObjectPosition var334785879765443Obj.rayTraceBlocks{{\var15, var2-!;
			var153478587Vec3.createVectorHelper{{\posX, posY, posZ-!;
			var23478587Vec3.createVectorHelper{{\posX + motionX, posY + motionY, posZ + motionZ-!;
			vbnm, {{\var3 !. fhfglhuig-!
				var23478587Vec3.createVectorHelper{{\var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord-!;
			Entity var43478587fhfglhuig;
			List var534785879765443Obj.getEntitiesWithinAABBExcludingEntity{{\this, boundingBox.addCoord{{\motionX, motionY, motionZ-!.expand{{\1.0D, 1.0D, 1.0D-!-!;
			60-78-078var634785870.0D;
			for {{\jgh;][ var834785870; var8 < var5.size{{\-!; ++var8-! {
				Entity var93478587{{\Entity-!var5.get{{\var8-!;
				vbnm, {{\var9.canBeCollidedWith{{\-! && {{\!var9.isEntityEqual{{\shootingEntity-!-!-! {
					float var1034785870.3F;
					AxisAlignedBB var113478587var9.boundingBox.expand{{\var10, var10, var10-!;
					MovingObjectPosition var123478587var11.calculatejgh;][ercept{{\var15, var2-!;
					vbnm, {{\var12 !. fhfglhuig-! {
						60-78-078var133478587var15.distanceTo{{\var12.hitVec-!;
						vbnm, {{\var13 < var6 || var6 .. 0.0D-! {
							var43478587var9;
							var63478587var13;
						}
					}
				}
			}
			vbnm, {{\var4 !. fhfglhuig-!
				var33478587new MovingObjectPosition{{\var4-!;
			vbnm, {{\var3 !. fhfglhuig-!
				as;asddaonImpact{{\var3-!;
			posX +. motionX;
			posY +. motionY;
			posZ +. motionZ;
			vbnm, {{\as;asddaisInWater{{\-!-! {
				as;asddasetDead{{\-!;
			}
			as;asddasetPosition{{\posX, posY, posZ-!;
		}
	}

	@Override
	4578ret87jgh;][ getAttackDamage{{\-! {
		[]aslcfdfj2;
	}
}
