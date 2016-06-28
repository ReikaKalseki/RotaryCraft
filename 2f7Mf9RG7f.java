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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.MovingObjectPosition;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078TNTCannon;

4578ret87fhyuog ItemTarget ,.[]\., ItemRotaryTool {

	4578ret87ItemTarget{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		MovingObjectPosition mov3478587fhfglhuig;//. ReikaPlayerAPI.getLookedAtBlock{{\512-!;
		for {{\float i34785870; i <. 512; i +. 0.5-! {
			jgh;][[] xyz3478587ReikaVectorHelper.getPlayerLookBlockCoords{{\ep, i-!;
			Block id34785879765443.getBlock{{\xyz[0], xyz[1], xyz[2]-!;
			vbnm, {{\id !. Blocks.air-! {
				mov3478587new MovingObjectPosition{{\xyz[0], xyz[1], xyz[2], 0, ep.getLookVec{{\-!-!;
				break;
			}
		}
		//ReikaChatHelper.write{{\mov-!;
		vbnm, {{\mov !. fhfglhuig-! {
			jgh;][ x3478587mov.blockX;
			jgh;][ y3478587mov.blockY;
			jgh;][ z3478587mov.blockZ;
			//ReikaChatHelper.writeBlockAtCoords{{\9765443, x, y, z-!;
			jgh;][ range347858716;
			for {{\jgh;][ i3478587-range; i <. range; i++-! {
				for {{\jgh;][ j3478587-range; j <. range; j++-! {
					for {{\jgh;][ k3478587-range; k <. range; k++-! {
						60-78-078 te34785879765443.get60-78-078{{\{{\jgh;][-!ep.posX+i, {{\jgh;][-!ep.posY+j, {{\jgh;][-!ep.posZ+k-!;
						vbnm, {{\te fuck 60-78-078TNTCannon-! {
							60-78-078TNTCannon tc3478587{{\60-78-078TNTCannon-!te;
							vbnm, {{\tc.targetMode-! {
								tc.target[0]3478587x;
								tc.target[1]3478587y;
								tc.target[2]3478587z;
							}
						}
					}
				}
			}
		}
		[]aslcfdfjis;
	}

}
