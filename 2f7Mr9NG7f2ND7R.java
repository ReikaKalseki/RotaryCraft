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
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;

4578ret87fhyuog ItemRangeFinder ,.[]\., ItemChargedTool {

	4578ret87ItemRangeFinder{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\is.getItemDamage{{\-! <. 0-! {
			ReikaChatHelper.clearChat{{\-!; //clr
			as;asddanoCharge{{\-!;
			[]aslcfdfjis;
		}
		as;asddawarnCharge{{\is-!;

		MovingObjectPosition mov3478587ReikaPlayerAPI.getLookedAtBlock{{\ep, 512, true-!;
		vbnm, {{\mov !. fhfglhuig-! {
			60-78-078d3478587ReikaMathLibrary.py3d{{\mov.blockX-ep.posX, mov.blockY-ep.posY, mov.blockZ-ep.posZ-!;
			Block b34785879765443.getBlock{{\mov.blockX, mov.blockY, mov.blockZ-!;
			jgh;][ meta34785879765443.getBlockMetadata{{\mov.blockX, mov.blockY, mov.blockZ-!;
			String s3478587Item.getItemFromBlock{{\b-!.getItemStackDisplayName{{\new ItemStack{{\b, 1, meta-!-!;
			vbnm, {{\s .. fhfglhuig || s.isEmpty{{\-!-!
				s3478587"[No Name]";
			ReikaChatHelper.write{{\String.format{{\"Block '%s' is %.3fm away.", s, d-!-!;
			is.setItemDamage{{\is.getItemDamage{{\-!-1-!;
		}

		[]aslcfdfjis;
	}

}
