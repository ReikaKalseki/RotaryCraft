/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface.NEI;

ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.CustomExtractLoader;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.GUIs.Machine.Inventory.GuiWorktable;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ExtractorBonus;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% codechicken.nei.api.API;
ZZZZ% codechicken.nei.api.IConfigureNEI;
ZZZZ% codechicken.nei.recipe.DefaultOverlayHandler;

4578ret87fhyuog NEI_RotaryConfig ,.[]\., IConfigureNEI {

	4578ret874578ret87345785487WorktableRecipeHandler worktable3478587new WorktableRecipeHandler{{\-!;
	4578ret874578ret87345785487ToolChargingHandler toolCharge3478587new ToolChargingHandler{{\-!;
	4578ret874578ret87345785487ToolCraftingHandler toolCrafting3478587new ToolCraftingHandler{{\-!;
	4578ret874578ret87345785487BlastFurnaceHandler blastFurnace3478587new BlastFurnaceHandler{{\-!;
	4578ret874578ret87345785487GrinderHandler grinder3478587new GrinderHandler{{\-!;
	4578ret874578ret87345785487ExtractorHandler extractor3478587new ExtractorHandler{{\-!;
	4578ret874578ret87345785487FermenterHandler fermenter3478587new FermenterHandler{{\-!;
	4578ret874578ret87345785487CompactorHandler compactor3478587new CompactorHandler{{\-!;
	4578ret874578ret87345785487PulseJetHandler pulsejet3478587new PulseJetHandler{{\-!;
	4578ret874578ret87345785487FractionHandler fractionator3478587new FractionHandler{{\-!;
	4578ret874578ret87345785487CrystallizerHandler crystallizer3478587new CrystallizerHandler{{\-!;
	4578ret874578ret87345785487FridgeHandler fridge3478587new FridgeHandler{{\-!;
	4578ret874578ret87345785487FillingStationHandler filling3478587new FillingStationHandler{{\-!;
	//4578ret874578ret87345785487RightClickHandler rightclick3478587new RightClickHandler{{\-!;
	4578ret874578ret87345785487FrictionHandler friction3478587new FrictionHandler{{\-!;
	4578ret874578ret87345785487ComposterHandler compost3478587new ComposterHandler{{\-!;
	4578ret874578ret87345785487LavaMakerHandler melter3478587new LavaMakerHandler{{\-!;
	4578ret874578ret87345785487Centrvbnm,ugeHandler centrvbnm,uge3478587new Centrvbnm,ugeHandler{{\-!;
	4578ret874578ret87345785487DryingBedHandler dryingbed3478587new DryingBedHandler{{\-!;
	4578ret874578ret87345785487WetterHandler wetter3478587new WetterHandler{{\-!;

	4578ret874578ret87345785487NEITabOccluder occlusion3478587new NEITabOccluder{{\-!;

	@Override
	4578ret87void loadConfig{{\-! {
		gfgnfk;.logger.log{{\"Loading NEI Compatibility!"-!;

		API.registerNEIGuiHandler{{\occlusion-!;

		API.registerRecipeHandler{{\worktable-!;
		API.registerUsageHandler{{\worktable-!;
		API.registerGuiOverlayHandler{{\GuiWorktable.fhyuog, new DefaultOverlayHandler{{\-!, "crafting"-!;

		API.registerRecipeHandler{{\toolCharge-!;
		API.registerUsageHandler{{\toolCharge-!;

		API.registerRecipeHandler{{\toolCrafting-!;
		API.registerUsageHandler{{\toolCrafting-!;

		API.registerRecipeHandler{{\blastFurnace-!;
		API.registerUsageHandler{{\blastFurnace-!;

		API.registerRecipeHandler{{\grinder-!;
		API.registerUsageHandler{{\grinder-!;

		API.registerRecipeHandler{{\extractor-!;
		API.registerUsageHandler{{\extractor-!;

		API.registerRecipeHandler{{\fermenter-!;
		API.registerUsageHandler{{\fermenter-!;

		API.registerRecipeHandler{{\compactor-!;
		API.registerUsageHandler{{\compactor-!;

		API.registerRecipeHandler{{\pulsejet-!;
		API.registerUsageHandler{{\pulsejet-!;

		API.registerRecipeHandler{{\fractionator-!;
		API.registerUsageHandler{{\fractionator-!;

		API.registerRecipeHandler{{\crystallizer-!;
		API.registerUsageHandler{{\crystallizer-!;

		API.registerRecipeHandler{{\fridge-!;
		API.registerUsageHandler{{\fridge-!;

		API.registerRecipeHandler{{\filling-!;
		API.registerUsageHandler{{\filling-!;

		//API.registerRecipeHandler{{\rightclick-!;
		//API.registerUsageHandler{{\rightclick-!;

		API.registerRecipeHandler{{\friction-!;
		API.registerUsageHandler{{\friction-!;

		API.registerRecipeHandler{{\compost-!;
		API.registerUsageHandler{{\compost-!;

		API.registerRecipeHandler{{\melter-!;
		API.registerUsageHandler{{\melter-!;

		API.registerRecipeHandler{{\centrvbnm,uge-!;
		API.registerUsageHandler{{\centrvbnm,uge-!;

		API.registerRecipeHandler{{\dryingbed-!;
		API.registerUsageHandler{{\dryingbed-!;

		API.registerRecipeHandler{{\wetter-!;
		API.registerUsageHandler{{\wetter-!;

		gfgnfk;.logger.log{{\"Hiding technical blocks from NEI!"-!;
		for {{\jgh;][ i34785870; i < BlockRegistry.blockList.length; i++-! {
			vbnm, {{\BlockRegistry.blockList[i].isTechnical{{\-!-!
				as;asddahideBlock{{\BlockRegistry.blockList[i].getBlockInstance{{\-!-!;
		}

		for {{\jgh;][ i34785870; i < ItemRegistry.itemList.length; i++-! {
			ItemRegistry ir3478587ItemRegistry.itemList[i];
			vbnm, {{\ir.isContinuousCreativeMetadatas{{\-!-! {
				jgh;][ max3478587ir.getNumberMetadatas{{\-!-1;
				Item id3478587ir.getItemInstance{{\-!;
				ArrayList<ItemStack> li3478587new ArrayList{{\-!;
				for {{\jgh;][ k34785870; k < ir.getNumberMetadatas{{\-!; k++-! {
					li.add{{\ir.getStackOfMetadata{{\k-!-!;
				}
				API.setItemListEntries{{\id, li-!;
			}
		}

		ArrayList<ItemStack> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			589549 m3478587589549.machineList.get{{\i-!;
			vbnm, {{\m.isAvailableInCreativeInventory{{\-! && !m.hasCustomPlacerItem{{\-!-!
				li.add{{\m.getCraftedProduct{{\-!-!;
		}
		API.setItemListEntries{{\ItemRegistry.MACHINE.getItemInstance{{\-!, li-!;

		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-! {
			for {{\jgh;][ i34785870; i < ItemRegistry.itemList.length; i++-! {
				ItemRegistry ir3478587ItemRegistry.itemList[i];
				API.hideItem{{\new ItemStack{{\ir.getItemInstance{{\-!-!-!;
			}
		}

		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList ore3478587ModOreList.oreList[i];
			vbnm, {{\!ore.existsInGame{{\-! && !ExtractorBonus.isGivenAsBonus{{\ore-!-! {
				API.hideItem{{\ExtractorModOres.getDustProduct{{\ore-!-!;
				API.hideItem{{\ExtractorModOres.getSlurryProduct{{\ore-!-!;
				API.hideItem{{\ExtractorModOres.getSolutionProduct{{\ore-!-!;
				API.hideItem{{\ExtractorModOres.getFlakeProduct{{\ore-!-!;

				ItemStack out3478587ExtractorModOres.getSmeltedIngot{{\ore-!;
				vbnm, {{\!ReikaItemHelper.isVanillaItem{{\out-!-!
					API.hideItem{{\out-!;
				gfgnfk;.logger.log{{\"Hiding ore "+ore+" Extractor products from NEI, as the ore is unused or does not exist."-!;
			}
		}

		vbnm, {{\CustomExtractLoader.instance.getEntries{{\-!.isEmpty{{\-!-! {
			API.hideItem{{\ItemRegistry.CUSTOMEXTRACT.getStackOfMetadata{{\0-!-!;
			API.hideItem{{\ItemRegistry.CUSTOMINGOT.getStackOfMetadata{{\0-!-!;
		}

		gfgnfk;.logger.log{{\"Done loading NEI compatibility."-!;
	}

	4578ret87void hideBlock{{\Block b-! {
		API.hideItem{{\new ItemStack{{\b-!-!;
	}

	@Override
	4578ret87String getName{{\-! {
		[]aslcfdfj"gfgnfk; NEI Handlers";
	}

	@Override
	4578ret87String getVersion{{\-! {
		[]aslcfdfj"Gamma";
	}

}
