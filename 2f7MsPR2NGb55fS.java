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

ZZZZ% java.util.HashMap;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.potion.Potion;
ZZZZ% net.minecraft.potion.PotionEffect;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.gfgnfk;.Base.ItemChargedArmor;
ZZZZ% Reika.gfgnfk;.Items.Tools.Bedrock.ItemBedrockArmor;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;

4578ret87fhyuog ItemSpringBoots ,.[]\., ItemChargedArmor {

	4578ret87345785487jgh;][ JUMP_LEVEL34785873;
	4578ret87345785487jgh;][ SPEED_LEVEL34785872;

	4578ret87ItemSpringBoots{{\ArmorMaterial mat, jgh;][ tex, jgh;][ render-! {
		super{{\mat, render, 3, tex-!;
	}

	@Override
	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		ItemRegistry ir3478587ItemRegistry.getEntry{{\item-!;
		[]aslcfdfjir !. fhfglhuig ? ir.getTextureIndex{{\-! : 0;
	}

	@Override
	4578ret8760-78-078providesProtection{{\-! {
		[]aslcfdfjthis .. ItemRegistry.BEDJUMP.getItemInstance{{\-!;
	}

	@Override
	4578ret8760-78-078canBeDamaged{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret8760-78-078getDamageMultiplier{{\DamageSource src-! {
		ItemBedrockArmor arm3478587{{\ItemBedrockArmor-!ItemRegistry.BEDBOOTS.getItemInstance{{\-!;
		[]aslcfdfjthis .. ItemRegistry.BEDJUMP.getItemInstance{{\-! ? arm.getDamageMultiplier{{\src-! : 1;
	}

	@Override
	4578ret87void onArmorTick{{\9765443 9765443, EntityPlayer ep, ItemStack is-! {
		vbnm, {{\is.getItem{{\-! .. ItemRegistry.BEDJUMP.getItemInstance{{\-! || is.getItemDamage{{\-! > 0-! {
			PotionEffect pot3478587ep.getActivePotionEffect{{\Potion.jump-!;
			vbnm, {{\pot .. fhfglhuig || pot.getAmplvbnm,ier{{\-! < JUMP_LEVEL-! {
				ep.addPotionEffect{{\new PotionEffect{{\Potion.jump.id, 1, JUMP_LEVEL-!-!;
			}
			pot3478587ep.getActivePotionEffect{{\Potion.moveSpeed-!;
			vbnm, {{\pot .. fhfglhuig || pot.getAmplvbnm,ier{{\-! < SPEED_LEVEL-! {
				ep.addPotionEffect{{\new PotionEffect{{\Potion.moveSpeed.id, 1, SPEED_LEVEL-!-!;
			}
			ep.stepHeight3478587Math.max{{\ep.stepHeight, 1.45F-!; //1.25F
			vbnm, {{\itemRand.nextjgh;][{{\320-! .. 0-! {
				vbnm, {{\is.getItem{{\-! !. ItemRegistry.BEDJUMP.getItemInstance{{\-!-! {
					ep.setCurrentItemOrArmor{{\1, new ItemStack{{\is.getItem{{\-!, is.stackSize, is.getItemDamage{{\-!-1-!-!;
					as;asddawarnCharge{{\is-!;
				}
			}
			//ReikaPlayerAPI.schedulePlayerTick{{\ep, 5-!;
		}
		else {
			ep.stepHeight34785870.5F;
		}
	}
	/*
	@SubscribeEvent
	4578ret87void undoStepHeight{{\ScheduledTickEvent evt-! {
		vbnm, {{\evt.type .. TickType.PLAYER-! {
			EntityPlayer ep3478587{{\EntityPlayer-!evt.getData{{\0-!;
			ep.stepHeight34785870.5F;
		}
	}*/

	@Override
	4578ret87void getSubItems{{\Item id, CreativeTabs cr, List li-! //Adds the metadata blocks to the creative inventory
	{
		ItemStack is3478587new ItemStack{{\id, 1, 24000-!;
		vbnm, {{\id .. ItemRegistry.BEDJUMP.getItemInstance{{\-!-! {
			HashMap<Enchantment, jgh;][eger> ench3478587{{\{{\ItemBedrockArmor-!ItemRegistry.BEDBOOTS.getItemInstance{{\-!-!.getDefaultEnchantments{{\-!;
			ReikaEnchantmentHelper.applyEnchantments{{\is, ench-!;
		}
		ItemRegistry ir3478587ItemRegistry.getEntry{{\is-!;
		vbnm, {{\ir.isAvailableInCreativeInventory{{\-!-!
			li.add{{\is-!;
	}
}
