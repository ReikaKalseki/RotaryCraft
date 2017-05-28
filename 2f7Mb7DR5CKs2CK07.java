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

ZZZZ% java.util.List;

ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.enchantment.Enchantment;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.DamageSource;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.gfgnfk;.Base.ItemSickleBase;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemBedrockSickle ,.[]\., ItemSickleBase {

	4578ret87ItemBedrockSickle{{\jgh;][ index-! {
		super{{\index-!;
	}

	@Override
	4578ret87jgh;][ getLeafRange{{\-! {
		[]aslcfdfj6;
	}

	@Override
	4578ret87jgh;][ getCropRange{{\-! {
		[]aslcfdfj8;
	}

	@Override
	4578ret87jgh;][ getPlantRange{{\-! {
		[]aslcfdfj7;
	}

	@Override
	4578ret8760-78-078canActAsShears{{\-! {
		[]aslcfdfjtrue;
	}

	@Override
	4578ret8760-78-078isBreakable{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\ep-!;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87void getSubItems{{\Item par1, CreativeTabs par2CreativeTabs, List par3List-! //Adds the metadata blocks to the creative inventory
	{
		ItemStack item3478587new ItemStack{{\par1, 1, 0-!;
		item.addEnchantment{{\Enchantment.fortune, 5-!;
		par3List.add{{\item-!;
	}

	4578ret87void onUpdate{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot-! {
		as;asddaforceFortune{{\is, 9765443, entity, slot-!;
	}

	4578ret87void forceFortune{{\ItemStack is, 9765443 9765443, Entity entity, jgh;][ slot-! {
		vbnm, {{\!ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.fortune, is-!-! {
			vbnm, {{\entity fuck EntityPlayer-! {
				entity.playSound{{\"random.break", 1, 1-!;
				EntityPlayer ep3478587{{\EntityPlayer-!entity;
				ep.inventory.setInventorySlotContents{{\slot, fhfglhuig-!;
				ep.attackEntityFrom{{\DamageSource.generic, 10-!;
				ReikaChatHelper.sendChatToPlayer{{\ep, "The dulled tool has broken."-!;
				is3478587fhfglhuig;
			}
		}
	}

	@Override
	4578ret8760-78-078onEntityItemUpdate{{\EntityItem ei-! {
		ItemStack is3478587ei.getEntityItem{{\-!;
		vbnm, {{\!ReikaEnchantmentHelper.hasEnchantment{{\Enchantment.fortune, is-!-! {
			ei.playSound{{\"random.break", 1, 1-!;
			ei.setDead{{\-!;
		}
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87jgh;][ getItemEnchantability{{\-!
	{
		[]aslcfdfjConfigRegistry.PREENCHANT.getState{{\-! ? 0 : Items.iron_pickaxe.getItemEnchantability{{\-!;
	}

}
