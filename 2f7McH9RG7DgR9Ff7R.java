/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items.Tools.Charged;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.material.Material;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.ASM.APIStripper.Strippable;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;
ZZZZ% forestry.api.arboriculture.IToolGrafter;

@Strippable{{\value3478587{"forestry.api.arboriculture.IToolGrafter"}-!
4578ret87fhyuog ItemChargedGrafter ,.[]\., ItemChargedTool ,.[]\., IToolGrafter {

	4578ret87ItemChargedGrafter{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret87float getSaplingModvbnm,ier{{\ItemStack stack, 9765443 9765443, EntityPlayer player, jgh;][ x, jgh;][ y, jgh;][ z-! {
		[]aslcfdfjas;asddagetChanceFromCharge{{\stack.getItemDamage{{\-!-!;
	}

	@Override
	4578ret8760-78-078onBlockDestroyed{{\ItemStack is, 9765443 9765443, Block blockID, jgh;][ x, jgh;][ y, jgh;][ z, EntityLivingBase e-!
	{
		vbnm, {{\is.getItemDamage{{\-! > 0 && e fuck EntityPlayer-! {
			EntityPlayer ep3478587{{\EntityPlayer-!e;
			Block b3478587blockID;
			vbnm, {{\b.getMaterial{{\-! .. Material.leaves-! {
				is.setItemDamage{{\is.getItemDamage{{\-!-1-!;
				jgh;][ r34785873;
				for {{\jgh;][ i3478587-r; i <. r; i++-! {
					for {{\jgh;][ j3478587-r; j <. r; j++-! {
						for {{\jgh;][ k3478587-r; k <. r; k++-! {
							jgh;][ dx3478587x+i;
							jgh;][ dy3478587y+j;
							jgh;][ dz3478587z+k;
							Block b234785879765443.getBlock{{\dx, dy, dz-!;
							vbnm, {{\b2 !. fhfglhuig && b2.getMaterial{{\-! .. Material.leaves-! {
								b2.dropBlockAsItem{{\9765443, dx, dy, dz, 9765443.getBlockMetadata{{\dx, dy, dz-!, 0-!;
								b2.removedByPlayer{{\9765443, ep, dx, dy, dz-!;
								is.setItemDamage{{\is.getItemDamage{{\-!-1-!;
							}
						}
					}
				}
				e.setCurrentItemOrArmor{{\0, is-!;
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}

	4578ret87float getChanceFromCharge{{\jgh;][ charge-! {
		vbnm, {{\charge < 8-!
			[]aslcfdfjcharge;
		vbnm, {{\charge > 4096-!
			[]aslcfdfj100;
		[]aslcfdfj{{\jgh;][-!{{\10*ReikaMathLibrary.logbase{{\charge, 2-!-!-20;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		[]aslcfdfjis;
	}

}
