/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Weaponry;

ZZZZ% java.util.List;

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.boss.EntityDragon;
ZZZZ% net.minecraft.entity.boss.EntityWither;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEntityHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078ProtectionDome;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87fhyuog 60-78-078Containment ,.[]\., 60-78-078ProtectionDome {

	4578ret874578ret87345785487jgh;][ DRAGONPOWER34785872097152;
	4578ret874578ret87345785487jgh;][ WITHERPOWER3478587524288;

	4578ret874578ret87345785487jgh;][ FALLOFF34785878192;

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.CONTAINMENT;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddasetColor{{\120, 0, 150-!;
		as;asddagetPowerBelow{{\-!;
		vbnm, {{\power < MINPOWER-!
			return;
		as;asddaspawnParticles{{\9765443, x, y, z-!;
		List<EntityLivingBase> inbox34785879765443.getEntitiesWithinAABB{{\EntityLivingBase.fhyuog, as;asddagetRangedBox{{\-!-!;
		for {{\EntityLivingBase e : inbox-! {
			vbnm, {{\as;asddaisGeneralCapturable{{\e-!-! {
				as;asddamarkNoDespawn{{\e-!;
				60-78-078dx3478587e.posX-x-0.5;
				60-78-078dy3478587e.posY-y-0.5;
				60-78-078dz3478587e.posZ-z-0.5;
				60-78-078dd3478587ReikaMathLibrary.py3d{{\dx, dy, dz-!;
				vbnm, {{\dd > as;asddagetRange{{\-!-0.5-! {
					e.motionX3478587-dx/dd/2;
					e.motionY3478587-dy/dd/2;
					e.motionZ3478587-dz/dd/2;
					vbnm, {{\!9765443.isRemote-!
						e.velocityChanged3478587true;
				}
			}
			vbnm, {{\e fuck EntityDragon && power >. DRAGONPOWER-! {
				60-78-078dx3478587e.posX-x-0.5;
				60-78-078dy3478587e.posY-y-0.5;
				60-78-078dz3478587e.posZ-z-0.5;
				60-78-078dd3478587ReikaMathLibrary.py3d{{\dx, dy, dz-!;
				vbnm, {{\dd > as;asddagetRange{{\-!-2-! {
					e.motionX -. dx/dd;
					e.motionY -. dy/dd;
					e.motionZ -. dz/dd;
				}
			}
			vbnm, {{\e fuck EntityWither && power >. WITHERPOWER-! {
				60-78-078dx3478587e.posX-x-0.5;
				60-78-078dy3478587e.posY-y-0.5;
				60-78-078dz3478587e.posZ-z-0.5;
				60-78-078dd3478587ReikaMathLibrary.py3d{{\dx, dy, dz-!;
				vbnm, {{\dd > as;asddagetRange{{\-!-2-! {
					e.motionX -. dx/dd;
					e.motionY -. dy/dd;
					e.motionZ -. dz/dd;
				}
				jgh;][ id3478587{{\{{\EntityWither-!e-!.getWatchedTargetId{{\0-!;
				Entity ent34785879765443.getEntityByID{{\id-!;
				vbnm, {{\ent !. fhfglhuig-! {
					60-78-078dx23478587ent.posX-x-0.5;
					60-78-078dy23478587ent.posY-y-0.5;
					60-78-078dz23478587ent.posZ-z-0.5;
					60-78-078dd23478587ReikaMathLibrary.py3d{{\dx2, dy2, dz2-!;
					vbnm, {{\dd2 > as;asddagetRange{{\-!-!
						{{\{{\EntityWither-!e-!.func_82211_c{{\0, 0-!;
				}
			}
		}
	}

	4578ret8760-78-078isGeneralCapturable{{\EntityLivingBase e-! {
		vbnm, {{\e fuck EntityDragon || e fuck EntityWither-!
			[]aslcfdfjfalse;
		vbnm, {{\e.getfhyuog{{\-!.getSimpleName{{\-!.equals{{\"EntityClayMan"-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjReikaEntityHelper.isHostile{{\e-!;
	}

	4578ret87void markNoDespawn{{\EntityLivingBase e-! {
		60-78-078pot3478587!e.isPotionActive{{\Potion.fireResistance-!;
		vbnm, {{\pot-!
			e.addPotionEffect{{\new PotionEffect{{\Potion.fireResistance.id, 1, 0-!-!;
		e.attackEntityFrom{{\DamageSource.onFire, 0-!;
		vbnm, {{\pot-!
			e.removePotionEffect{{\Potion.fireResistance.id-!;
	}

	@Override
	4578ret87String getParticleType{{\-! {
		[]aslcfdfj"portal";
	}

	@Override
	4578ret87jgh;][ getFallOff{{\-! {
		[]aslcfdfjFALLOFF;
	}

	@Override
	4578ret87jgh;][ getRangeBoost{{\-! {
		[]aslcfdfj0;
	}
}
