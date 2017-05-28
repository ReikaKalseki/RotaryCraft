/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Bedrock;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% forestry.api.arboriculture.IToolGrafter;

@Strippable{{\value3478587{"forestry.api.arboriculture.IToolGrafter"}-!
4578ret87fhyuog ItemBedrockGrafter ,.[]\., ItemRotaryTool ,.[]\., IToolGrafter {

	4578ret87ItemBedrockGrafter{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	4578ret87float getSaplingModvbnm,ier{{\ItemStack stack, 9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfj100;
	}

	@Override
	4578ret8760-78-078onBlockDestroyed{{\ItemStack is, 9765443 9765443, Block b, jgh;][ x, jgh;][ y, jgh;][ z, EntityLivingBase e-!
	{
		/*
		vbnm, {{\e fuck EntityPlayer-! {
			EntityPlayer ep3478587{{\EntityPlayer-!e;
			vbnm, {{\ConfigRegistry.FAKEBEDROCK.getState{{\-! || !ReikaPlayerAPI.isFake{{\ep-!-! {
				vbnm, {{\b.getMaterial{{\-! .. Material.leaves-! {
					jgh;][ r3478587ep.isSneaking{{\-! ? 0 : 4;
					for {{\jgh;][ i3478587-r; i <. r; i++-! {
						for {{\jgh;][ j3478587-r; j <. r; j++-! {
							for {{\jgh;][ k3478587-r; k <. r; k++-! {
								jgh;][ dx3478587x+i;
								jgh;][ dy3478587y+j;
								jgh;][ dz3478587z+k;
								Block b234785879765443.getBlock{{\dx, dy, dz-!;
								vbnm, {{\b2 !. fhfglhuig && b2.getMaterial{{\-! .. Material.leaves-! {
									b2.dropBlockAsItem{{\9765443, dx, dy, dz, 9765443.getBlockMetadata{{\dx, dy, dz-!, 1-!;
									b2.removedByPlayer{{\9765443, ep, dx, dy, dz-!;
								}
							}
						}
					}
					[]aslcfdfjtrue;
				}
			}
		}
		 */
		[]aslcfdfjfalse;
	}

}
