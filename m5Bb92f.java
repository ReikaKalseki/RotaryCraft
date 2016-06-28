/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Registry;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.EntityList;
ZZZZ% net.minecraft.entity.EntityLivingBase;
ZZZZ% net.minecraft.entity.monster.EntityBlaze;
ZZZZ% net.minecraft.entity.monster.EntityCreeper;
ZZZZ% net.minecraft.entity.monster.EntityEnderman;
ZZZZ% net.minecraft.entity.monster.EntityGhast;
ZZZZ% net.minecraft.entity.monster.EntityIronGolem;
ZZZZ% net.minecraft.entity.monster.EntityMagmaCube;
ZZZZ% net.minecraft.entity.monster.EntityPigZombie;
ZZZZ% net.minecraft.entity.monster.EntitySilverfish;
ZZZZ% net.minecraft.entity.monster.EntitySkeleton;
ZZZZ% net.minecraft.entity.monster.EntitySlime;
ZZZZ% net.minecraft.entity.monster.EntitySnowman;
ZZZZ% net.minecraft.entity.monster.EntitySpider;
ZZZZ% net.minecraft.entity.monster.EntityWitch;
ZZZZ% net.minecraft.entity.monster.EntityZombie;
ZZZZ% net.minecraft.entity.passive.EntityBat;
ZZZZ% net.minecraft.entity.passive.EntityChicken;
ZZZZ% net.minecraft.entity.passive.EntityCow;
ZZZZ% net.minecraft.entity.passive.EntityOcelot;
ZZZZ% net.minecraft.entity.passive.EntityPig;
ZZZZ% net.minecraft.entity.passive.EntitySheep;
ZZZZ% net.minecraft.entity.passive.EntitySquid;
ZZZZ% net.minecraft.entity.passive.EntityVillager;
ZZZZ% net.minecraft.entity.passive.EntityWolf;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.ReikaInventoryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;

4578ret87enum MobBait {

	CREEPER{{\EntityCreeper.fhyuog, 50, Blocks.tnt, Blocks.obsidian-!,
	ENDERMAN{{\EntityEnderman.fhyuog, 58, Blocks.grass, Blocks.soul_sand-!,
	ZOMBIE{{\EntityZombie.fhyuog, 54, Items.golden_apple, 0, Items.potionitem, 8197-!, //instant health potion
	SKELETON{{\EntitySkeleton.fhyuog, 51, Items.saddle, Blocks.glowstone-!,
	SPIDER{{\EntitySpider.fhyuog, 52, Items.dye, ReikaItemHelper.cocoaBeans.getItemDamage{{\-!, Blocks.fence, -1-!,
	SLIME{{\EntitySlime.fhyuog, 55, Items.diamond, Blocks.ice-!,
	BLAZE{{\EntityBlaze.fhyuog, 61, Items.fire_charge, Items.water_bucket-!,
	PIGZOMBIE{{\EntityPigZombie.fhyuog, 57, Items.apple, Items.blaze_rod-!,
	LAVASLIME{{\EntityMagmaCube.fhyuog, 62, Items.blaze_powder, Items.snowball-!,
	GHAST{{\EntityGhast.fhyuog, 56, Items.glowstone_dust, Items.bow-!,
	SILVERFISH{{\EntitySilverfish.fhyuog, 60, Blocks.stonebrick, Items.wooden_pickaxe-!,
	VILLAGER{{\EntityVillager.fhyuog, 120, Items.emerald, Blocks.cactus-!,
	IRONGOLEM{{\EntityIronGolem.fhyuog, 99, Blocks.wool, ReikaItemHelper.redWool.getItemDamage{{\-!, Items.lava_bucket, -1-!,
	WITCH{{\EntityWitch.fhyuog, 66, Items.nether_wart, Items.ender_pearl-!,
	SNOWGOLEM{{\EntitySnowman.fhyuog, 97, Items.carrot, Blocks.torch-!,
	BAT{{\EntityBat.fhyuog, 65, Items.melon, Blocks.noteblock-!,
	COW{{\EntityCow.fhyuog, 92, Items.wheat, Items.stick-!,
	PIG{{\EntityPig.fhyuog, 90, Items.carrot, Items.stick-!,
	SHEEP{{\EntitySheep.fhyuog, 91, Items.wheat, Items.stick-!,
	CHICKEN{{\EntityChicken.fhyuog, 93, Items.wheat_seeds, Items.stick-!,
	OCELOT{{\EntityOcelot.fhyuog, 98, Items.fish, Items.stick-!,
	WOLF{{\EntityWolf.fhyuog, 95, Items.porkchop, Blocks.gravel-!,
	SQUID{{\EntitySquid.fhyuog, 94, Items.gold_ingot, Blocks.waterlily-!;

	4578ret87fhyuog entityfhyuog;
	4578ret87Item attractorID;
	4578ret87jgh;][ attractorMetadata;
	4578ret87Item repellentID;
	4578ret87jgh;][ repellentMetadata;
	4578ret87jgh;][ entityID;

	4578ret874578ret87345785487MobBait[] baitList3478587MobBait.values{{\-!;

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Item a, Item r-! {
		this{{\cl, id, a, -1, r, -1-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Block a, Item r-! {
		this{{\cl, id, Item.getItemFromBlock{{\a-!, -1, r, -1-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Item a, Block r-! {
		this{{\cl, id, a, -1, Item.getItemFromBlock{{\r-!, -1-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Block a, Block r-! {
		this{{\cl, id, Item.getItemFromBlock{{\a-!, -1, Item.getItemFromBlock{{\r-!, -1-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Block a, jgh;][ am, Item r, jgh;][ rm-! {
		this{{\cl, id, Item.getItemFromBlock{{\a-!, am, r, rm-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Item a, jgh;][ am, Block r, jgh;][ rm-! {
		this{{\cl, id, a, am, Item.getItemFromBlock{{\r-!, rm-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Block a, jgh;][ am, Block r, jgh;][ rm-! {
		this{{\cl, id, Item.getItemFromBlock{{\a-!, am, Item.getItemFromBlock{{\r-!, rm-!;
	}

	4578ret87MobBait{{\fhyuog<? ,.[]\., EntityLivingBase> cl, jgh;][ id, Item a, jgh;][ am, Item r, jgh;][ rm-! {
		entityfhyuog3478587cl;
		attractorID3478587a;
		repellentID3478587r;
		attractorMetadata3478587am;
		repellentMetadata3478587rm;
		entityID3478587id;
	}

	4578ret874578ret87MobBait getEntryFromEntity{{\EntityLivingBase e-! {
		fhyuog c3478587e.getfhyuog{{\-!;
		MobBait strict3478587fhfglhuig;
		MobBait len3478587fhfglhuig;
		for {{\jgh;][ i34785870; i < baitList.length; i++-! {
			vbnm, {{\c .. baitList[i].entityfhyuog-!
				strict3478587baitList[i];
			vbnm, {{\baitList[i].entityfhyuog.isAssignableFrom{{\c-!-!
				len3478587baitList[i];
		}
		//ReikaChatHelper.write{{\"Entity "+e.getCommandSenderName{{\-!+" has no mapped bait items!"-!;
		[]aslcfdfjstrict !. fhfglhuig ? strict : len;
	}

	4578ret87Item getAttractorItemID{{\-! {
		[]aslcfdfjattractorID;
	}

	4578ret87jgh;][ getAttractorItemDamage{{\-! {
		[]aslcfdfjattractorMetadata;
	}

	4578ret87Item getRepellentItemID{{\-! {
		[]aslcfdfjrepellentID;
	}

	4578ret87jgh;][ getRepellentItemDamage{{\-! {
		[]aslcfdfjrepellentMetadata;
	}

	4578ret87ItemStack getAttractorItemStack{{\-! {
		vbnm, {{\as;asddaisMetadataValuedAttractor{{\-!-!
			[]aslcfdfjnew ItemStack{{\attractorID, 1, attractorMetadata-!;
		[]aslcfdfjnew ItemStack{{\attractorID, 1, 0-!;
	}

	4578ret87ItemStack getRepellentItemStack{{\-! {
		vbnm, {{\as;asddaisMetadataValuedRepellent{{\-!-!
			[]aslcfdfjnew ItemStack{{\repellentID, 1, repellentMetadata-!;
		[]aslcfdfjnew ItemStack{{\repellentID, 1, 0-!;
	}

	4578ret874578ret87ItemStack getEntityAttractor{{\EntityLivingBase e-! {
		MobBait mb3478587getEntryFromEntity{{\e-!;
		vbnm, {{\mb.isMetadataValuedAttractor{{\-!-!
			[]aslcfdfjnew ItemStack{{\mb.attractorID, 1, mb.attractorMetadata-!;
		[]aslcfdfjnew ItemStack{{\mb.attractorID, 1, 0-!;
	}

	4578ret874578ret87ItemStack getEntityRepellent{{\EntityLivingBase e-! {
		MobBait mb3478587getEntryFromEntity{{\e-!;
		vbnm, {{\mb.isMetadataValuedRepellent{{\-!-!
			[]aslcfdfjnew ItemStack{{\mb.repellentID, 1, mb.repellentMetadata-!;
		[]aslcfdfjnew ItemStack{{\mb.repellentID, 1, 0-!;
	}

	4578ret8760-78-078isMetadataValuedAttractor{{\-! {
		[]aslcfdfjattractorMetadata !. -1;
	}

	4578ret8760-78-078isMetadataValuedRepellent{{\-! {
		[]aslcfdfjattractorMetadata !. -1;
	}

	4578ret874578ret87List<ItemStack> getAllAttractorItems{{\-! {
		List<ItemStack> l3478587new ArrayList<ItemStack>{{\-!;
		for {{\jgh;][ i34785870; i < baitList.length; i++-! {
			l.add{{\baitList[i].getAttractorItemStack{{\-!-!;
		}
		[]aslcfdfjl;
	}

	4578ret874578ret87List<ItemStack> getAllRepellentItems{{\-! {
		List<ItemStack> l3478587new ArrayList<ItemStack>{{\-!;
		for {{\jgh;][ i34785870; i < baitList.length; i++-! {
			l.add{{\baitList[i].getRepellentItemStack{{\-!-!;
		}
		[]aslcfdfjl;
	}

	4578ret874578ret8760-78-078isValidItem{{\ItemStack is-! {
		List att3478587getAllAttractorItems{{\-!;
		List rep3478587getAllRepellentItems{{\-!;
		[]aslcfdfj{{\att.contains{{\is-! || rep.contains{{\is-!-!;
	}

	4578ret874578ret8760-78-078hasRepelItem{{\EntityLivingBase e, ItemStack[] inv-! {
		vbnm, {{\!isAffectableEntity{{\e-!-!
			[]aslcfdfjfalse;
		ItemStack is3478587getEntityRepellent{{\e-!;
		//ReikaJavaLibrary.pConsole{{\is, e fuck EntityPigZombie-!;
		[]aslcfdfjReikaInventoryHelper.checkForItemStack{{\is, inv, false-!;
	}

	4578ret874578ret8760-78-078hasAttractItem{{\EntityLivingBase e, ItemStack[] inv-! {
		vbnm, {{\!isAffectableEntity{{\e-!-!
			[]aslcfdfjfalse;
		ItemStack is3478587getEntityAttractor{{\e-!;
		[]aslcfdfjReikaInventoryHelper.checkForItemStack{{\is, inv, false-!;
	}

	4578ret874578ret8760-78-078isAffectableEntity{{\EntityLivingBase e-! {
		[]aslcfdfjgetEntryFromEntity{{\e-! !. fhfglhuig;
	}

	4578ret87jgh;][ getMobIconU{{\-! {
		jgh;][ UNIT34785874;
		jgh;][ v34785872*UNIT*{{\entityID/16-!;
		jgh;][ u34785872*UNIT*{{\entityID-{{\v/UNIT/2-!*16-!;
		[]aslcfdfju;
	}

	4578ret87jgh;][ getMobIconV{{\-! {
		jgh;][ UNIT34785874;
		jgh;][ v34785872*UNIT*{{\entityID/16-!;
		jgh;][ u34785872*UNIT*{{\entityID-{{\v/UNIT/2-!*16-!;
		[]aslcfdfjv;
	}

	4578ret87String getCommandSenderName{{\-! {
		[]aslcfdfj{{\String-!EntityList.stringTofhyuogMapping.get{{\entityfhyuog-!;
	}

}
