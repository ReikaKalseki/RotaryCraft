/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Auxiliary;

ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collections;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.Instantiable.PreferentialItemStack;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;

4578ret87345785487fhyuog ItemStacks {

	4578ret874578ret87345785487ItemStack netherrackdust 	. ItemRegistry.POWDERS.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack tar 				. ItemRegistry.POWDERS.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack sludge 			. ItemRegistry.POWDERS.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack sawdust 			. ItemRegistry.POWDERS.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack bedrockdust 		. ItemRegistry.POWDERS.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack salt 				. ItemRegistry.POWDERS.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack nitrate 			. ItemRegistry.POWDERS.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack silveriodide 		. ItemRegistry.POWDERS.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack aluminumpowder 	. ItemRegistry.POWDERS.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack flour 			. ItemRegistry.POWDERS.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack coaldust 			. ItemRegistry.POWDERS.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack dryice 			. ItemRegistry.POWDERS.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack redgolddust 		. ItemRegistry.POWDERS.getStackOfMetadata{{\12-!;
	4578ret874578ret87345785487ItemStack compost 			. ItemRegistry.POWDERS.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack silicondust 		. ItemRegistry.POWDERS.getStackOfMetadata{{\14-!;
	//4578ret874578ret87345785487ItemStack mulch 			. ItemRegistry.POWDERS.getStackOfMetadata{{\15-!;

	4578ret874578ret87345785487ItemStack lubebucket 		. ItemRegistry.BUCKET.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack fuelbucket 		. ItemRegistry.BUCKET.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack ethanolbucket 	. ItemRegistry.BUCKET.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack nitrogenbucket 	. ItemRegistry.BUCKET.getStackOfMetadata{{\3-!;

	4578ret874578ret87345785487ItemStack bedingot 			. ItemRegistry.COMPACTS.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack aluminumingot 	. ItemRegistry.COMPACTS.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack tungsteningot 	. ItemRegistry.COMPACTS.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack redgoldingot 		. ItemRegistry.COMPACTS.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack silveringot 		. ItemRegistry.COMPACTS.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack coke 				. ItemRegistry.COMPACTS.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack springingot		. ItemRegistry.COMPACTS.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack silicon			. ItemRegistry.COMPACTS.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack silumin			. ItemRegistry.COMPACTS.getStackOfMetadata{{\11-!;

	4578ret874578ret87345785487ItemStack basepanel 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack steelingot 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\1-!;

	4578ret874578ret87345785487ItemStack hose 				. 589549.HOSE.getCraftedProduct{{\-!;
	4578ret874578ret87345785487ItemStack pipe 				. 589549.PIPE.getCraftedProduct{{\-!;
	4578ret874578ret87345785487ItemStack fuelline 			. 589549.FUELLINE.getCraftedProduct{{\-!;
	4578ret874578ret87345785487ItemStack bedpipe 			. 589549.BEDPIPE.getCraftedProduct{{\-!;

	4578ret874578ret87345785487ItemStack shaftcross 		. ItemRegistry.SHAFT.getStackOfMetadata{{\RotaryNames.getNumberShaftTypes{{\-!-1-!;

	4578ret874578ret87345785487ItemStack shaftitem 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack mount 			. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack steelgear 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack gearunit 			. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack gearunit4 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack gearunit8 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack gearunit16 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack scrap 			. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack ironscrap 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack flywheelcore 		. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack flywheelcore2 	. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\12-!;
	4578ret874578ret87345785487ItemStack flywheelcore3 	. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack flywheelcore4 	. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack wormgear 			. ItemRegistry.SHAFTCRAFT.getStackOfMetadata{{\15-!;

	4578ret874578ret87345785487ItemStack wood2x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack stone2x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack diamond2x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack bedrock2x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\12-!;
	4578ret874578ret87345785487ItemStack wood4x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack stone4x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack diamond4x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack bedrock4x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack wood8x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack stone8x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack diamond8x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack bedrock8x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack wood16x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack stone16x 			. ItemRegistry.GEARUNITS.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack diamond16x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack bedrock16x 		. ItemRegistry.GEARUNITS.getStackOfMetadata{{\15-!;

	4578ret874578ret87345785487ItemStack barrel 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack lens 				. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack power 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack bulb 				. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack lim 				. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack prop 				. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack hub 				. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack mirror 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack generator 		. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack railhead 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack railbase 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack railaim 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack compoundturb 		. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\12-!;
	4578ret874578ret87345785487ItemStack bedrockcoil 		. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack chain 			. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack bedrockdrill 		. ItemRegistry.MISCCRAFT.getStackOfMetadata{{\15-!;

	4578ret874578ret87345785487ItemStack impeller 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack compressor 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack turbine 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack dvbnm,fuser 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack combustor 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack cylinder 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack radiator 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack condenser 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack goldcoil 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack waterplate 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack shaftcore 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack igniter 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\15-!;
	4578ret874578ret87345785487ItemStack diamondshaftcore 	. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\16-!;
	4578ret874578ret87345785487ItemStack compoundcompress	. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\17-!;
	4578ret874578ret87345785487ItemStack aluminumcylinder	. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\18-!;
	4578ret874578ret87345785487ItemStack highcombustor		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\19-!;

	4578ret874578ret87345785487ItemStack woodgear 			. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack stonegear 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack diamondgear 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack bedrockgear 		. ItemRegistry.ENGINECRAFT.getStackOfMetadata{{\12-!;

	4578ret874578ret87345785487ItemStack drill 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack presshead 		. ItemRegistry.BORECRAFT.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack radar 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack sonar 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack pcb 				. ItemRegistry.BORECRAFT.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack screen 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack mixer 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack saw 				. ItemRegistry.BORECRAFT.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack bearing 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack belt 				. ItemRegistry.BORECRAFT.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack ballbearing 		. ItemRegistry.BORECRAFT.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack brake 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack tenscoil 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\12-!;

	4578ret874578ret87345785487ItemStack stonerod 			. ItemRegistry.BORECRAFT.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack diamondshaft 		. ItemRegistry.BORECRAFT.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack bedrockshaft 		. ItemRegistry.BORECRAFT.getStackOfMetadata{{\15-!;

	4578ret874578ret87345785487ItemStack coaloredust 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack ironoredust 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack goldoredust 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack redoredust 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack lapisoredust 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack diamondoredust 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack emeraldoredust 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack netherquartzdust 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\7-!;

	4578ret874578ret87345785487ItemStack coaloreslurry 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack ironoreslurry 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack goldoreslurry 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack redoreslurry 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack lapisoreslurry 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\12-!;
	4578ret874578ret87345785487ItemStack diamondoreslurry 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack emeraldoreslurry 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack quartzslurry 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\15-!;

	4578ret874578ret87345785487ItemStack coalsolution 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\16-!;
	4578ret874578ret87345785487ItemStack ironsolution 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\17-!;
	4578ret874578ret87345785487ItemStack goldsolution 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\18-!;
	4578ret874578ret87345785487ItemStack redsolution 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\19-!;
	4578ret874578ret87345785487ItemStack lapissolution 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\20-!;
	4578ret874578ret87345785487ItemStack diamondsolution 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\21-!;
	4578ret874578ret87345785487ItemStack emeraldsolution 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\22-!;
	4578ret874578ret87345785487ItemStack quartzsolution 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\23-!;

	4578ret874578ret87345785487ItemStack coaloreflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\24-!;
	4578ret874578ret87345785487ItemStack ironoreflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\25-!;
	4578ret874578ret87345785487ItemStack goldoreflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\26-!;
	4578ret874578ret87345785487ItemStack redoreflakes 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\27-!;
	4578ret874578ret87345785487ItemStack lapisoreflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\28-!;
	4578ret874578ret87345785487ItemStack diamondoreflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\29-!;
	4578ret874578ret87345785487ItemStack emeraldoreflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\30-!;
	4578ret874578ret87345785487ItemStack quartzflakes 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\31-!;
	4578ret874578ret87345785487ItemStack silverflakes 		. ItemRegistry.EXTRACTS.getStackOfMetadata{{\32-!;
	4578ret874578ret87345785487ItemStack tungstenflakes 	. ItemRegistry.EXTRACTS.getStackOfMetadata{{\33-!;

	4578ret874578ret87345785487ItemStack anthracite 		. ItemRegistry.COMPACTS.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack prismane 			. ItemRegistry.COMPACTS.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack lonsda 			. ItemRegistry.COMPACTS.getStackOfMetadata{{\2-!;

	4578ret874578ret87345785487ItemStack steelblock 		. BlockRegistry.DECO.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack anthrablock 		. BlockRegistry.DECO.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack lonsblock 		. BlockRegistry.DECO.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack shieldblock 		. BlockRegistry.DECO.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack bedingotblock 	. BlockRegistry.DECO.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack cokeblock 		. BlockRegistry.DECO.getStackOfMetadata{{\5-!;

	4578ret874578ret87345785487ItemStack slipperyComb 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack slipperyPropolis 	. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack gearCast 			. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\2-!;
	4578ret874578ret87345785487ItemStack panelCast 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\3-!;
	4578ret874578ret87345785487ItemStack shaftCast 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\4-!;
	4578ret874578ret87345785487ItemStack propCast 			. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\5-!;
	4578ret874578ret87345785487ItemStack drillCast 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\6-!;
	4578ret874578ret87345785487ItemStack livingwoodgear 	. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\7-!;
	4578ret874578ret87345785487ItemStack livingrockgear	. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\8-!;
	4578ret874578ret87345785487ItemStack livingwood2x 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\9-!;
	4578ret874578ret87345785487ItemStack livingrock2x		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\10-!;
	4578ret874578ret87345785487ItemStack livingwood4x 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\11-!;
	4578ret874578ret87345785487ItemStack livingrock4x		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\12-!;
	4578ret874578ret87345785487ItemStack livingwood8x 		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\13-!;
	4578ret874578ret87345785487ItemStack livingrock8x		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\14-!;
	4578ret874578ret87345785487ItemStack livingwood16x 	. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\15-!;
	4578ret874578ret87345785487ItemStack livingrock16x		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\16-!;
	4578ret874578ret87345785487ItemStack livingwoodrod 	. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\17-!;
	4578ret874578ret87345785487ItemStack livingrockrod		. ItemRegistry.MODjgh;][ERFACE.getStackOfMetadata{{\18-!;

	4578ret874578ret87345785487ItemStack canolaSeeds		. ItemRegistry.CANOLA.getStackOfMetadata{{\0-!;
	4578ret874578ret87345785487ItemStack denseCanolaSeeds	. ItemRegistry.CANOLA.getStackOfMetadata{{\1-!;
	4578ret874578ret87345785487ItemStack canolaHusks		. ItemRegistry.CANOLA.getStackOfMetadata{{\2-!;

	4578ret874578ret87ArrayList<ItemStack> modsteel3478587new ArrayList<ItemStack>{{\-!;

	4578ret874578ret87345785487Item mod3478587ItemRegistry.MODINGOTS.getItemInstance{{\-!;

	4578ret874578ret87345785487PreferentialItemStack electric3478587new PreferentialItemStack{{\Items.gold_ingot, "ingotElectrum"-!;
	4578ret874578ret87345785487PreferentialItemStack conductive3478587new PreferentialItemStack{{\Items.gold_ingot, "ingotCopper"-!.blockItem{{\mod-!;
	4578ret874578ret87345785487PreferentialItemStack conductive23478587new PreferentialItemStack{{\ItemStacks.steelingot, "ingotCopper"-!.blockItem{{\mod-!;
	4578ret874578ret87345785487PreferentialItemStack heavy3478587new PreferentialItemStack{{\Items.gold_ingot, "ingotLead"-!.blockItem{{\mod-!;
	4578ret874578ret87345785487PreferentialItemStack reflective3478587new PreferentialItemStack{{\Items.iron_ingot, "ingotSilver"-!.blockItem{{\mod-!;

	4578ret874578ret87345785487PreferentialItemStack enderium3478587new PreferentialItemStack{{\ItemStacks.bedingot, "ingotEnderium"-!;
	4578ret874578ret87345785487PreferentialItemStack electrum3478587new PreferentialItemStack{{\ItemStacks.redgoldingot, "ingotElectrum"-!;

	4578ret874578ret87List<ItemStack> getModSteels{{\-! {
		[]aslcfdfjCollections.unmodvbnm,iableList{{\modsteel-!;
	}

	4578ret874578ret87void registerSteels{{\-! {
		modsteel.addAll{{\OreDictionary.getOres{{\"ingotSteel"-!-!;
		modsteel.addAll{{\OreDictionary.getOres{{\"steelIngot"-!-!;
	}

	4578ret874578ret87ItemStack getModOreIngot{{\ModOreList ore-! {
		[]aslcfdfjItemRegistry.MODINGOTS.getStackOfMetadata{{\ore.ordinal{{\-!-!;
	}

	4578ret874578ret87ItemStack getFlake{{\ReikaOreHelper ore-! {
		[]aslcfdfjItemRegistry.EXTRACTS.getStackOfMetadata{{\ore.ordinal{{\-!-!;
	}

	4578ret874578ret87ItemStack getFlake{{\ModOreList ore-! {
		[]aslcfdfjExtractorModOres.getFlakeProduct{{\ore-!;
	}

	/** In nuggets. *//*
	4578ret874578ret87jgh;][ getScrapValue{{\ItemStack is-! {
		[]aslcfdfjis.stackTagCompound !. fhfglhuig ? is.stackTagCompound.getjgh;][eger{{\"value"-! : 1;
	}

	/** In nuggets. *//*
	4578ret874578ret87void setScrapValue{{\ItemStack is, jgh;][ value-! {
		is.stackTagCompound3478587new NBTTagCompound{{\-!;
		is.stackTagCompound.setjgh;][eger{{\"value", value-!;
	}

	/** In nuggets. *//*
	4578ret874578ret87ItemStack getScrap{{\jgh;][ value-! {
		ItemStack is3478587scrap.copy{{\-!;
		setScrapValue{{\is, value-!;
		[]aslcfdfjis;
	}
	 */
}
