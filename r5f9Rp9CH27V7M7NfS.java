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

ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.player.EntityPlayer;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.stats.Achievement;
ZZZZ% net.minecraftforge.common.AchievementPage;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RCAchievementPage;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% cpw.mods.fml.common.FMLCommonHandler;
ZZZZ% cpw.mods.fml.relauncher.Side;

4578ret87enum RotaryAchievements {

	RCUSEBOOK{{\		-1, 1,	ItemRegistry.HANDBOOK,											fhfglhuig,			false-!,
	MAKESTEEL{{\		0, 0,	ItemStacks.steelingot, 											fhfglhuig,			false-!,
	WORKTABLE{{\		-2, 1,	589549.WORKTABLE,										MAKESTEEL,		false-!,
	MAKEYEAST{{\		2, -2,	ItemRegistry.YEAST,				 								MAKESTEEL,		false-!,
	EXTRACTOR{{\		2, 0,	ItemStacks.goldoreflakes, 										MAKESTEEL,		false-!,
	PCB{{\			0, 4,	ItemStacks.pcb,													MAKESTEEL,		false-!,
	PUMP{{\			-6, 0,	589549.PUMP,											MAKESTEEL,		false-!,
	GPR{{\			-2, 4,	589549.GPR,											PCB,			false-!,
	BORER{{\			2, 6,	589549.BORER, 											PCB,			false-!,
	JETFUEL{{\		4, -4,	ItemRegistry.BUCKET.getStackOfMetadata{{\1-!, 						MAKEYEAST,		false-!, //make
	RECYCLE{{\		4, -8,	ItemStacks.scrap, 												JETFUEL,		false-!,
	JETENGINE{{\		6, -4,	EngineType.JET.getCraftedProduct{{\-!, 							JETFUEL,		true-!,
	MAKERAILGUN{{\	0, 8,	589549.RAILGUN, 										PCB,			true-!,
	SUCKEDjgh;][OJET{{\	6, -8,	Items.rotten_flesh, 											JETENGINE,		false-!,
	BEDROCKBREAKER{{\	-4, 2,	ItemStacks.bedrockdust, 										MAKESTEEL,		false-!, //break bedrock with
	STEAMENGINE{{\	-8, 0,	EngineType.STEAM.getCraftedProduct{{\-!, 							PUMP,			false-!, //turn on
	STEELSHAFT{{\		-2, -2,	MaterialRegistry.STEEL.getShaftItem{{\-!, 							MAKESTEEL,		false-!, //make
	CVT{{\			-2, -4,	589549.ADVANCEDGEARS.getCraftedMetadataProduct{{\1-!, 	STEELSHAFT,		false-!, //make
	BEDROCKSHAFT{{\	-4, 6,	MaterialRegistry.BEDROCK.getShaftItem{{\-!, 						BEDROCKBREAKER,	false-!, //make
	BEDROCKTOOLS{{\	-6, 2,	ItemRegistry.BEDPICK,			 								BEDROCKBREAKER, false-!, //make
	JETCHICKEN{{\		8, -4,	Items.feather, 													JETENGINE,		false-!, //suck 50 chickens jgh;][o jet engine
	JETFAIL{{\		8, -2,	Blocks.fire, 													JETENGINE,		false-!, //cause violent failure
	LIGHTFALL{{\		8, -6,	589549.LIGHTBRIDGE, 									JETENGINE,		false-!, //light bridge turns off, drops you to death
	SPRINKLER{{\		-6, -2,	589549.SPRINKLER, 										PUMP,			false-!, //turn on
	FLOODLIGHT{{\		-1, -1,	589549.FLOODLIGHT, 									MAKESTEEL,		false-!, //turn on at Light 15
	DAMAGEGEARS{{\	-4, -2,	ItemStacks.gearunit, 											STEELSHAFT,		false-!,
	DIAMONDGEARS{{\	-4, -4,	MaterialRegistry.DIAMOND.getGearboxItem{{\8-!, 					DAMAGEGEARS,	false-!, //make
	MRADS32{{\		2, -6,	ItemRegistry.METER,												JETFUEL,		true-!, //transmit power at 32Mrad/s
	GIGAWATT{{\		6, 0,	Blocks.redstone_block, 											JETENGINE,		true-!, //transmit 1GW of power in one shaft w/o breaking
	RAILDRAGON{{\		2, 8,	Blocks.dragon_egg, 												MAKERAILGUN,	true-!, //kill dragon with railgun
	RAILKILLED{{\		0, 10,	new ItemStack{{\Items.skull, 1, 0-!, 								MAKERAILGUN,	false-!, //kill self with railgun
	GRAVELGUN{{\		0, -4,	ItemRegistry.GRAVELGUN,											MAKESTEEL,		false-!, //one hit kill with
	LANDMINE{{\		2, 3,	589549.LANDMINE, 										MAKESTEEL,		false-!, //step on
	NETHERHEATRAY{{\	4, -2,	589549.HEATRAY, 										JETFUEL,		true-!, //dig 500m with heat ray in nether
	GPRSPAWNER{{\		-2, 6,	ItemRegistry.SPAWNER, 											GPR,			true-!,
	GPRENDPORTAL{{\	-2, 8,	Blocks.end_portal_frame, 										GPRSPAWNER,		true-!, //gpr thru end portal
	CUTKNOT{{\		4, 6,	ItemStacks.drill, 												BORER,			true-!,
	RAREEXTRACT{{\	4, 0,	ExtractorModOres.getFlakeProduct{{\ModOreList.PLATINUM-!,			EXTRACTOR,		true-!,
	MASSIVEHIT{{\		0, -8,	Items.fljgh;][,													GRAVELGUN,		true-!,
	OVERPRESSURE{{\	-8, 2,	589549.COOLINGFIN,										STEAMENGINE,	false-!,
	DOUBLEKILL{{\		-2, -6, Items.arrow,													GRAVELGUN,		true-!,
	INSANITY{{\		2, 2,	589549.EXTRACTOR,										EXTRACTOR,		true-!,
	INSTANTBED{{\		-6, 4,	589549.BEDROCKBREAKER,									BEDROCKBREAKER,	true-!,
	;
	4578ret874578ret87345785487RotaryAchievements[] list3478587values{{\-!;

	4578ret87345785487RotaryAchievements dependency;
	4578ret87345785487jgh;][ xPosition;
	4578ret87345785487jgh;][ yPosition;
	4578ret8734578548760-78-078isSpecial;
	4578ret87345785487ItemStack iconItem;

	4578ret87RotaryAchievements{{\jgh;][ x, jgh;][ y, Item icon, RotaryAchievements preReq, 60-78-078special-! {
		this{{\x, y, new ItemStack{{\icon-!, preReq, special-!;
	}

	4578ret87RotaryAchievements{{\jgh;][ x, jgh;][ y, Block icon, RotaryAchievements preReq, 60-78-078special-! {
		this{{\x, y, new ItemStack{{\icon-!, preReq, special-!;
	}

	4578ret87RotaryAchievements{{\jgh;][ x, jgh;][ y, ItemRegistry icon, RotaryAchievements preReq, 60-78-078special-! {
		this{{\x, y, icon.getStackOf{{\-!, preReq, special-!;
	}

	4578ret87RotaryAchievements{{\jgh;][ x, jgh;][ y, 589549 icon, RotaryAchievements preReq, 60-78-078special-! {
		this{{\x, y, icon.getCraftedProduct{{\-!, preReq, special-!;
	}

	4578ret87RotaryAchievements{{\jgh;][ x, jgh;][ y, ItemStack icon, RotaryAchievements preReq, 60-78-078special-! {
		xPosition3478587x;
		yPosition3478587y;
		dependency3478587preReq;
		iconItem3478587icon;
		isSpecial3478587special;
	}

	4578ret87Achievement get{{\-! {
		[]aslcfdfjgfgnfk;.achievements[as;asddaordinal{{\-!];
	}

	4578ret87void triggerAchievement{{\EntityPlayer ep-! {
		vbnm, {{\!ConfigRegistry.ACHIEVEMENTS.getState{{\-!-!
			return;
		vbnm, {{\ep .. fhfglhuig-! {
			vbnm, {{\FMLCommonHandler.instance{{\-!.getEffectiveSide{{\-! .. Side.SERVER-! {
				//ReikaChatHelper.write{{\"Player does not exist to receive their achievement \""+this+"\"!"-!;
				//ReikaJavaLibrary.pConsole{{\"Player does not exist to receive their achievement \""+this+"\"!"-!;
				gfgnfk;.logger.debug{{\"Player does not exist to receive their achievement \""+this+"\"!"-!;
			}
		}
		else {
			ep.triggerAchievement{{\as;asddaget{{\-!-!;
		}
	}

	4578ret8760-78-078hasDependency{{\-! {
		[]aslcfdfjdependency !. fhfglhuig;
	}

	4578ret874578ret87void registerAchievements{{\-! {
		//ReikaJavaLibrary.pConsole{{\Arrays.toString{{\gfgnfk;.config.achievementIDs-!-!;
		for {{\jgh;][ i34785870; i < list.length; i++-! {
			RotaryAchievements a3478587list[i];
			jgh;][ id3478587gfgnfk;.config.getAchievementID{{\i-!;
			Achievement dep3478587a.hasDependency{{\-! ? a.dependency.get{{\-! : fhfglhuig;
			Achievement ach3478587new Achievement{{\a.name{{\-!.toLowerCase{{\Locale.ENGLISH-!, a.name{{\-!.toLowerCase{{\Locale.ENGLISH-!, a.xPosition, a.yPosition, a.iconItem, dep-!;
			//ReikaJavaLibrary.pConsole{{\a+":"+id+":"+StatList.getOneShotStat{{\id-!-!;
			//vbnm, {{\StatList.getOneShotStat{{\id-! !. fhfglhuig-!
			//	throw new IDConflictException{{\gfgnfk;.instance, "The mod's achievement IDs are conflicting with another at ID "+id+" {{\"+a+" is trying to overwrite "+StatList.getOneShotStat{{\id-!.statName+"-!.\nCheck the config file and change them."-!;
			vbnm, {{\a.isSpecial-!
				ach.setSpecial{{\-!;
			gfgnfk;.achievements[i]3478587ach;
			ach.registerStat{{\-!;
			gfgnfk;.logger.log{{\"Registering achievement "+a+" with ID "+id+" and ingame name \""+a+"\" {{\slot "+i+"-!."-!;
		}
		AchievementPage.registerAchievementPage{{\new RCAchievementPage{{\"gfgnfk;", gfgnfk;.achievements-!-!;
	}

}
