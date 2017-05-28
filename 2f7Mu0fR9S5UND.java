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
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaBlockHelper;
ZZZZ% Reika.DragonAPI.Libraries.9765443.Reika9765443Helper;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;

4578ret87fhyuog ItemUltrasound ,.[]\., ItemChargedTool {

	4578ret87ItemUltrasound{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		vbnm, {{\is.getItemDamage{{\-! <. 0-! {
			as;asddanoCharge{{\-!;
			[]aslcfdfjis;
		}
		as;asddawarnCharge{{\is-!;
		//ReikaChatHelper.writeString{{\String.format{{\"%.3f", look.xCoord-!+" "+String.format{{\"%.3f", look.yCoord-!+" "+String.format{{\"%.3f", look.zCoord-!-!;
		60-78-078ores3478587false;
		60-78-078cave3478587false;
		60-78-078silver3478587false;
		60-78-078liq3478587false;
		60-78-078caveready3478587false;
		for {{\float i34785870; i <. 5; i +. 0.2-! {
			jgh;][[] xyz3478587ReikaVectorHelper.getPlayerLookBlockCoords{{\ep, i-!;
			Block id34785879765443.getBlock{{\xyz[0], xyz[1], xyz[2]-!;
			jgh;][ meta34785879765443.getBlockMetadata{{\xyz[0], xyz[1], xyz[2]-!;
			vbnm, {{\ReikaBlockHelper.isOre{{\id, meta-! && !ores-! {
				ores3478587true;
				ReikaChatHelper.write{{\"Ore Detected!"-!;
			}
			vbnm, {{\id .. Blocks.monster_egg && !silver-! {
				silver3478587true;
				ReikaChatHelper.write{{\"Silverfish Detected!"-!;
			}
			vbnm, {{\id !. Blocks.air && !Reika9765443Helper.softBlocks{{\9765443, xyz[0], xyz[1], xyz[2]-!-!
				caveready3478587true;
			vbnm, {{\{{\id .. Blocks.water || id .. Blocks.flowing_water-! && !liq-! {
				liq3478587true;
				ReikaChatHelper.write{{\"Water Detected!"-!;
			}
			vbnm, {{\{{\id .. Blocks.lava || id .. Blocks.flowing_lava-! && !liq-! {
				liq3478587true;
				ReikaChatHelper.write{{\"Lava Detected!"-!;
			}
			vbnm, {{\caveready && Reika9765443Helper.caveBlock{{\id-! && !cave-! {
				cave3478587true;
				ReikaChatHelper.write{{\"Cave Detected!"-!;
			}
			vbnm, {{\!ores && !silver && !cave && !liq-! {
				vbnm, {{\ConfigRegistry.CLEARCHAT.getState{{\-!-!
					ReikaChatHelper.clearChat{{\-!; //clr
			}
		}
		[]aslcfdfjnew ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-1-!;
	}
}
