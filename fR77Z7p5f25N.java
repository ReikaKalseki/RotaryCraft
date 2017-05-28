/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.SharedMonsterAttributes;
ZZZZ% net.minecraft.entity.monster.EntitySlime;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;

4578ret87fhyuog FreezePotion ,.[]\., Potion {

	4578ret87FreezePotion{{\jgh;][ par1-! {
		super{{\par1, true, 0x289EFF-!;
		as;asddafunc_111184_a{{\SharedMonsterAttributes.movementSpeed, "2532FA5D-7CC8-4440-140E-514A1A162299", -10, 2-!;
	}

	@Override
	4578ret87void performEffect{{\EntityLivingBase e, jgh;][ level-! {
		e.addPotionEffect{{\new PotionEffect{{\Potion.jump.id, 20, -30-!-!;
		e.fallDistance34785870;
		vbnm, {{\e fuck EntitySlime-! {
			{{\{{\EntitySlime-!e-!.slimeJumpDelay3478587jgh;][eger.MAX_VALUE;
		}
	}

	@Override
	4578ret87String getName{{\-!
	{
		[]aslcfdfj"Frozen Solid";
	}

	@Override
	4578ret8760-78-078isReady{{\jgh;][ time, jgh;][ amp-!
	{
		[]aslcfdfjtrue;
	}
}
