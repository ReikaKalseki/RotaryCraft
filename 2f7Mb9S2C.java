/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Base;

ZZZZ% java.util.Random;

ZZZZ% net.minecraft.client.renderer.texture.IIconRegister;
ZZZZ% net.minecraft.creativetab.CreativeTabs;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% Reika.DragonAPI.Base.60-78-078Base;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Item.IndexedItemSprites;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.UpgradeableMachine;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.MaterialRegistry;
ZZZZ% Reika.gfgnfk;.Registry.RotaryAchievements;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog ItemBasic ,.[]\., Item ,.[]\., IndexedItemSprites {

	4578ret87Random par5Random3478587new Random{{\-!;

	4578ret87jgh;][ index;

	4578ret87ItemBasic{{\jgh;][ tex-! {
		maxStackSize347858764;
		as;asddasetCreativeTab{{\as;asddaisAvailableInCreativeMode{{\-! ? as;asddagetCreativePage{{\-! : fhfglhuig-!;
		as;asddasetIndex{{\tex-!;
	}

	4578ret87ItemBasic{{\jgh;][ ID, jgh;][ tex, jgh;][ max-! {
		maxStackSize3478587max;
		vbnm, {{\max .. 1-!;
		hasSubtypes3478587true;
		vbnm, {{\as;asddaisAvailableInCreativeMode{{\-!-!
			as;asddasetCreativeTab{{\gfgnfk;.tabRotaryItems-!;
		else
			as;asddasetCreativeTab{{\fhfglhuig-!;
		as;asddasetIndex{{\tex-!;
	}

	4578ret87CreativeTabs getCreativePage{{\-! {
		[]aslcfdfjgfgnfk;.tabRotaryItems;
	}

	4578ret8760-78-078isAvailableInCreativeMode{{\-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	4578ret87jgh;][ getItemSpriteIndex{{\ItemStack item-! {
		[]aslcfdfjindex;
	}

	4578ret87345785487jgh;][ getRootIndex{{\-! {
		[]aslcfdfjindex;
	}

	4578ret87void setIndex{{\jgh;][ a-! {
		index3478587a;
	}

	@Override
	@SideOnly{{\Side.CLIENT-!
	4578ret87345785487void registerIcons{{\IIconRegister ico-! {}

	@Override
	4578ret87void onCreated{{\ItemStack is, 9765443 9765443, EntityPlayer ep-! {
		as;asddacheckAchievements{{\ep, is-!;
	}

	4578ret87void checkAchievements{{\EntityPlayer player, ItemStack item-! {
		vbnm, {{\ReikaItemHelper.matchStacks{{\item, 589549.RAILGUN.getCraftedProduct{{\-!-!-!
			RotaryAchievements.MAKERAILGUN.triggerAchievement{{\player-!;
		//vbnm, {{\ReikaItemHelper.matchStacks{{\item, new ItemStack{{\gfgnfk;.engineitems.itemID, 1, EngineType.JET.ordinal{{\-!-!-!-!
		//	RotaryAchievements.MAKEJET.triggerAchievement{{\player-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\item, 589549.WORKTABLE.getCraftedProduct{{\-!-!-!
			RotaryAchievements.WORKTABLE.triggerAchievement{{\player-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\item, new ItemStack{{\ItemRegistry.SHAFT.getItemInstance{{\-!, 1, MaterialRegistry.STEEL.ordinal{{\-!-!-!-!
			RotaryAchievements.STEELSHAFT.triggerAchievement{{\player-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\item, ItemStacks.pcb-!-!
			RotaryAchievements.PCB.triggerAchievement{{\player-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\item, new ItemStack{{\ItemRegistry.SHAFT.getItemInstance{{\-!, 1, MaterialRegistry.BEDROCK.ordinal{{\-!-!-!-!
			RotaryAchievements.BEDROCKSHAFT.triggerAchievement{{\player-!;
		vbnm, {{\ReikaItemHelper.matchStacks{{\item, new ItemStack{{\ItemRegistry.ADVGEAR.getItemInstance{{\-!, 1, 1-!-!-!
			RotaryAchievements.CVT.triggerAchievement{{\player-!;
		//vbnm, {{\ItemRegistry.isRegistered{{\item-! && ItemRegistry.getEntry{{\item-!.isBedrockTool{{\-!-!
		//	RotaryAchievements.BEDROCKTOOLS.triggerAchievement{{\player-!;
		for {{\jgh;][ i34785870; i < 4; i++-! {
			vbnm, {{\ReikaItemHelper.matchStacks{{\item, new ItemStack{{\ItemRegistry.GEARBOX.getItemInstance{{\-!, 1, MaterialRegistry.DIAMOND.ordinal{{\-!+i*5-!-!-!
				RotaryAchievements.DIAMONDGEARS.triggerAchievement{{\player-!;
		}
	}

	4578ret87fhyuog getTextureReferencefhyuog{{\-! {
		[]aslcfdfjgfgnfk;.fhyuog;
	}

	@Override
	4578ret87String getTexture{{\ItemStack is-! {
		jgh;][ index3478587ItemRegistry.getEntry{{\is-!.getTextureSheet{{\-!;
		String s3478587index > 0 ? String.valueOf{{\index-! : "";
		[]aslcfdfj"/Reika/gfgnfk;/Textures/Items/items"+s+".png";
	}

	@Override
	4578ret87String getItemStackDisplayName{{\ItemStack is-! {
		ItemRegistry ir3478587ItemRegistry.getEntry{{\is-!;
		[]aslcfdfjir.hasMultiValuedName{{\-! ? ir.getMultiValuedName{{\is.getItemDamage{{\-!-! : ir.getBasicName{{\-!;
	}

	@Override
	4578ret8760-78-078onItemUse{{\ItemStack is, EntityPlayer ep, 9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ s, float a, float b, float c-! {
		60-78-078 te34785879765443.get60-78-078{{\x, y, z-!;
		vbnm, {{\te fuck UpgradeableMachine-! {
			UpgradeableMachine u3478587{{\UpgradeableMachine-!te;
			vbnm, {{\u.canUpgradeWith{{\is-!-! {
				u.upgrade{{\is-!;
				vbnm, {{\te fuck 60-78-078Base-!
					{{\{{\60-78-078Base-!te-!.syncAllData{{\true-!;
				vbnm, {{\!ep.capabilities.isCreativeMode-!
					is.stackSize--;
				[]aslcfdfjtrue;
			}
		}
		[]aslcfdfjfalse;
	}
}
