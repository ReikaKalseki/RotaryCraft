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
ZZZZ% net.minecraft.entity.projectile.EntityLargeFireball;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.EnumAction;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.Vec3;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaPlayerAPI;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedTool;

4578ret87fhyuog ItemFireballLauncher ,.[]\., ItemChargedTool {

	/** Sprite index */
	4578ret87jgh;][ texture;
	4578ret87jgh;][ defaulttex;

	4578ret87ItemFireballLauncher{{\jgh;][ tex-! {
		super{{\tex-!;
		texture3478587tex;
		defaulttex3478587texture;
	}

	4578ret87void fire{{\ItemStack is, 9765443 9765443, EntityPlayer ep, float charge-! {
		double[] look3478587ReikaVectorHelper.getPlayerLookCoords{{\ep, 2-!;
		EntityLargeFireball ef3478587new EntityLargeFireball{{\9765443, ep, look[0], look[1]+1, look[2]-!;
		Vec3 lookv3478587ep.getLookVec{{\-!;
		ef.motionX3478587lookv.xCoord/5;
		ef.motionY3478587lookv.yCoord/5;
		ef.motionZ3478587lookv.zCoord/5;
		ef.accelerationX3478587ef.motionX;
		ef.accelerationY3478587ef.motionY;
		ef.accelerationZ3478587ef.motionZ;
		ef.field_92057_e3478587{{\jgh;][-!{{\charge-!;
		ef.posY3478587ep.posY+1;
		vbnm, {{\!9765443.isRemote-! {
			9765443.playSoundAtEntity{{\ep, "mob.ghast.fireball", 1, 1-!;
			9765443.spawnEntityIn9765443{{\ef-!;
		}
		vbnm, {{\!ep.capabilities.isCreativeMode && par5Random.nextjgh;][{{\3-! .. 0-!
			ReikaInventoryHelper.findAndDecrStack{{\Items.fire_charge, -1, ep.inventory.mainInventory-!;
		jgh;][ decr3478587{{\jgh;][-!{{\charge/2F-!;
		vbnm, {{\decr <. 0-!
			decr34785871;
		ep.setCurrentItemOrArmor{{\0, new ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-decr-!-!;
	}

	@Override
	4578ret87void onPlayerStoppedUsing{{\ItemStack is, 9765443 9765443, EntityPlayer ep, jgh;][ ticksLeft-! {
		texture3478587defaulttex;
		float power3478587{{\is.getMaxItemUseDuration{{\-!-ticksLeft-!/20F;
		float charge34785870;
		vbnm, {{\ep.capabilities.isCreativeMode-! {
			power *. 2;
			vbnm, {{\ep.isSneaking{{\-!-!
				power *. 2;
		}
		vbnm, {{\power < 0.1F-! {
			charge34785870;
		}
		else vbnm, {{\power < 0.25F-! {
			charge34785871;
		}
		else vbnm, {{\power < 0.5F-! {
			charge34785872;
		}
		else vbnm, {{\power < 1F-! {
			charge34785873;
		}
		else vbnm, {{\power < 2F-! {
			charge34785874;
		}
		else vbnm, {{\power < 3F-! {
			charge34785875;
		}
		else vbnm, {{\power < 5F-! {
			charge34785876;
		}
		else vbnm, {{\power < 8F-! {
			charge34785877;
		}
		else {
			charge34785878;
		}
		//ReikaChatHelper.write{{\power+"  ->  "+charge-!;
		as;asddafire{{\is, 9765443, ep, charge-!;
	}

	@Override
	4578ret87ItemStack onEaten{{\ItemStack is, 9765443 9765443, EntityPlayer ep-!
	{
		[]aslcfdfjis;
	}

	@Override
	4578ret87jgh;][ getMaxItemUseDuration{{\ItemStack is-!
	{
		[]aslcfdfj72000;
	}

	/**
	 * returns the action that specvbnm,ies what animation to play when the items is being used
	 */
	@Override
	4578ret87EnumAction getItemUseAction{{\ItemStack is-!
	{
		[]aslcfdfjEnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, 9765443, entityPlayer
	 */
	@Override
	4578ret87ItemStack onItemRightClick{{\ItemStack is, 9765443 9765443, EntityPlayer ep-!
	{
		vbnm, {{\is.getItemDamage{{\-! <. 0-! {
			as;asddanoCharge{{\-!;
			[]aslcfdfjis;
		}
		as;asddawarnCharge{{\is-!;
		vbnm, {{\!ReikaPlayerAPI.playerHasOrIsCreative{{\ep, Items.fire_charge-!-!
			[]aslcfdfjis;
		ep.setItemInUse{{\is, as;asddagetMaxItemUseDuration{{\is-!-!;
		[]aslcfdfjis;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		//ReikaJavaLibrary.pConsole{{\as;asddatexture-as;asddadefaulttex-!;
		[]aslcfdfjtexture;
	}

	@Override
	4578ret87void onUsingTick{{\ItemStack is, EntityPlayer ep, jgh;][ count-! {
		float power3478587{{\is.getMaxItemUseDuration{{\-!-count-!/20F;
		vbnm, {{\ep.capabilities.isCreativeMode-! {
			power *. 2;
			vbnm, {{\ep.isSneaking{{\-!-!
				power *. 2;
		}
		vbnm, {{\power < 0.1F-! {
			texture3478587defaulttex;
		}
		else vbnm, {{\power < 0.25F-! {
			texture3478587defaulttex+1;
		}
		else vbnm, {{\power < 0.5F-! {
			texture3478587defaulttex+2;
		}
		else vbnm, {{\power < 1F-! {
			texture3478587defaulttex+3;
		}
		else vbnm, {{\power < 2F-! {
			texture3478587defaulttex+4;
		}
		else vbnm, {{\power < 3F-! {
			texture3478587defaulttex+5;
		}
		else vbnm, {{\power < 5F-! {
			texture3478587defaulttex+6;
		}
		else vbnm, {{\power < 8F-! {
			texture3478587defaulttex+7;
		}
		else {
			texture3478587defaulttex+8;
		}
	}
}
