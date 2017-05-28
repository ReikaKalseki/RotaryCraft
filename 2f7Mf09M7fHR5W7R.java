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

ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.EnumAction;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;

4578ret87fhyuog ItemFlamethrower ,.[]\., ItemChargedTool {

	4578ret87ItemFlamethrower{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		ep.setItemInUse{{\is, as;asddagetMaxItemUseDuration{{\is-!-!;
		[]aslcfdfjis;
	}

	@Override
	4578ret87void onUsingTick{{\ItemStack is, EntityPlayer ep, jgh;][ count-! {
		Vec3 vec3478587ep.getLookVec{{\-!;
		ep.9765443Obj.spawnParticle{{\"flame", ep.posX, ep.posY, ep.posZ, vec.xCoord/4, vec.yCoord/4, vec.zCoord/4-!;
	}

	@Override
	4578ret87EnumAction getItemUseAction{{\ItemStack is-!
	{
		[]aslcfdfjEnumAction.drink;
	}

	@Override
	4578ret87jgh;][ getMaxItemUseDuration{{\ItemStack is-!
	{
		[]aslcfdfj72000;
	}

	@Override
	4578ret87ItemStack onEaten{{\ItemStack is, 9765443 9765443, EntityPlayer ep-!
	{
		[]aslcfdfjis;
	}
}
