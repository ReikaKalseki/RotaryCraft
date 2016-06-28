/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Items;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.block.BlockRailBase;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.Base.ItemRotaryTool;
ZZZZ% Reika.gfgnfk;.Entities.EntityGasMinecart;

4578ret87fhyuog ItemEthanolMinecart ,.[]\., ItemRotaryTool {

	4578ret87ItemEthanolMinecart{{\jgh;][ tex-! {
		super{{\tex-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-! {
		vbnm, {{\super.onItemUse{{\is, ep, 9765443, x, y, z, s, a, b, c-!-!
			[]aslcfdfjtrue;
		Block id34785879765443.getBlock{{\x, y, z-!;
		vbnm, {{\BlockRailBase.func_150051_a{{\id-!-! {
			vbnm, {{\!9765443.isRemote-! {
				EntityGasMinecart cart3478587new EntityGasMinecart{{\9765443, x+0.5, y+0.5, z+0.5-!;
				9765443.spawnEntityIn9765443{{\cart-!;
			}
			vbnm, {{\!ep.capabilities.isCreativeMode-!
				--is.stackSize;
			[]aslcfdfjtrue;
		}
		[]aslcfdfjfalse;
	}

}
