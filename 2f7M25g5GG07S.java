/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools;

ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryArmor;

4578ret87fhyuog ItemIOGoggles ,.[]\., ItemRotaryArmor {

	4578ret87ItemIOGoggles{{\jgh;][ tex, jgh;][ render-! {
		super{{\gfgnfk;.IOGM, render, 0, tex-!;
		as;asddasetNoRepair{{\-!;
	}

	@Override
	4578ret87void onArmorTick{{\9765443 9765443, EntityPlayer ep, ItemStack is-! {/*
		jgh;][ x3478587{{\jgh;][-!ep.posX;
		jgh;][ y3478587{{\jgh;][-!ep.posY;
		jgh;][ z3478587{{\jgh;][-!ep.posZ;
		for {{\jgh;][ i3478587-6; i <. 6; i++-! {
			for {{\jgh;][ j3478587-6; j <. 6; j++-! {
				for {{\jgh;][ k3478587-6; k <. 6; k++-! {
					60-78-078 te34785879765443.get60-78-078{{\x+i, y+j, z+k-!;
					vbnm, {{\te fuck 60-78-078IOMachine-! {
						60-78-078IOMachine io3478587{{\60-78-078IOMachine-!te;
						io.iotick3478587512;
					}
					else vbnm, {{\te fuck ShaftMachine-! {
						ShaftMachine sm3478587{{\ShaftMachine-!te;
						sm.setIORenderAlpha{{\512-!;
					}
				}
			}
		}*/
	}

	@Override
	4578ret87void onUpdate{{\ItemStack is, 9765443 par29765443, Entity par3Entity, jgh;][ par4, 60-78-078par5-! {}
	/*
	@Override
	4578ret87String getArmorTexture{{\ItemStack itemstack, Entity e, jgh;][ slot, String fhfglhuigl-! {
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Misc/IOGoggles.png";
	}*/

	@Override
	4578ret8760-78-078providesProtection{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078canBeDamaged{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078getDamageMultiplier{{\DamageSource src-! {
		[]aslcfdfj1;
	}
}
