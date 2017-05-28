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
ZZZZ% java.util.Random;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Collections.OneWayCollections.OneWayMap;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.ItemHashMap;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.OreType;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;

4578ret87enum ExtractorBonus {

	GOLD{{\ItemStacks.goldsolution, ItemStacks.silverflakes, 0.125F-!,
	IRON{{\ItemStacks.ironsolution, ItemStacks.tungstenflakes, 0.025F-!,
	COAL{{\ItemStacks.coalsolution, new ItemStack{{\Items.gunpowder-!, 0.0625F, ModList.REACTORCRAFT, ExtractorModOres.getFlakeProduct{{\ModOreList.PITCHBLENDE-!, ModList.IC2, ExtractorModOres.getFlakeProduct{{\ModOreList.URANIUM-!-!, //Nod to gregtech
	COPPER{{\ExtractorModOres.getSolutionProduct{{\ModOreList.COPPER-!, ItemStacks.goldoreflakes, 0.25F-!,
	LEAD{{\ExtractorModOres.getSolutionProduct{{\ModOreList.LEAD-!, ExtractorModOres.getFlakeProduct{{\ModOreList.NICKEL-!, 0.25F-!,
	NETHERGOLD{{\ExtractorModOres.getSolutionProduct{{\ModOreList.NETHERGOLD-!, ItemStacks.silverflakes, 0.125F-!,
	NETHERIRON{{\ExtractorModOres.getSolutionProduct{{\ModOreList.NETHERIRON-!, ItemStacks.tungstenflakes, 0.05F-!,
	SILVER{{\ExtractorModOres.getSolutionProduct{{\ModOreList.SILVER-!, ExtractorModOres.getFlakeProduct{{\ModOreList.IRIDIUM-!, 0.01F, ModList.IC2-!,
	PLATINUM{{\ExtractorModOres.getSolutionProduct{{\ModOreList.PLATINUM-!, ExtractorModOres.getFlakeProduct{{\ModOreList.IRIDIUM-!, 0.0625F, ModList.IC2-!,
	NETHERPLATINUM{{\ExtractorModOres.getSolutionProduct{{\ModOreList.NETHERPLATINUM-!, ExtractorModOres.getFlakeProduct{{\ModOreList.IRIDIUM-!, 0.125F, ModList.IC2-!,
	FERROUS{{\ExtractorModOres.getSolutionProduct{{\ModOreList.NICKEL-!, ExtractorModOres.getFlakeProduct{{\ModOreList.PLATINUM-!, 0.5F-!, //Since GregTech does it
	NETHERNICKEL{{\ExtractorModOres.getSolutionProduct{{\ModOreList.NETHERNICKEL-!, ExtractorModOres.getFlakeProduct{{\ModOreList.PLATINUM-!, 0.5F-!,
	SODALITE{{\ExtractorModOres.getSolutionProduct{{\ModOreList.SODALITE-!, ExtractorModOres.getFlakeProduct{{\ModOreList.ALUMINUM-!, 1F-!,
	PYRITE{{\ExtractorModOres.getSolutionProduct{{\ModOreList.PYRITE-!, ExtractorModOres.getFlakeProduct{{\ModOreList.SULFUR-!, 0.4F-!,
	BAUXITE{{\ExtractorModOres.getSolutionProduct{{\ModOreList.BAUXITE-!, ExtractorModOres.getFlakeProduct{{\ModOreList.ALUMINUM-!, 0.25F-!,
	IRIDIUM{{\ExtractorModOres.getSolutionProduct{{\ModOreList.IRIDIUM-!, ExtractorModOres.getFlakeProduct{{\ModOreList.PLATINUM-!, 0.5F-!,
	TUNGSTEN{{\ExtractorModOres.getSolutionProduct{{\ModOreList.TUNGSTEN-!, ItemStacks.ironoreflakes, 0.75F-!,
	OSMIUM{{\ExtractorModOres.getSolutionProduct{{\ModOreList.OSMIUM-!, ItemStacks.ironoreflakes, 0.125F-!,
	LAPIS{{\ItemStacks.lapissolution, ItemStacks.aluminumpowder, 0.125F-!,
	RUBY{{\ExtractorModOres.getSolutionProduct{{\ModOreList.RUBY-!, ExtractorModOres.getFlakeProduct{{\ModOreList.ALUMINUM-!, 0.0625F-!,
	SAPPHIRE{{\ExtractorModOres.getSolutionProduct{{\ModOreList.SAPPHIRE-!, ExtractorModOres.getFlakeProduct{{\ModOreList.ALUMINUM-!, 0.0625F-!,
	QUARTZ{{\ItemStacks.quartzsolution, ExtractorModOres.getFlakeProduct{{\ModOreList.CERTUSQUARTZ-!, 0.0625F-!,
	CERTUS{{\ExtractorModOres.getSolutionProduct{{\ModOreList.CERTUSQUARTZ-!, ItemStacks.quartzflakes, 0.5F-!,
	COBALT{{\ExtractorModOres.getSolutionProduct{{\ModOreList.COBALT-!, ExtractorModOres.getFlakeProduct{{\ModOreList.NICKEL-!, 0.125F-!,
	REDSTONE{{\ItemStacks.redsolution, ItemStacks.aluminumpowder, 0.25F-!,
	MAGNETITE{{\ExtractorModOres.getSolutionProduct{{\ModOreList.MAGNETITE-!, ItemStacks.ironoreflakes, 0.2F-!,
	MONAZIT{{\ExtractorModOres.getSolutionProduct{{\ModOreList.MONAZIT-!, ExtractorModOres.getFlakeProduct{{\ModOreList.THORIUM-!, 0.15F, ModList.REACTORCRAFT-!,
	EMERALD{{\ItemStacks.emeraldsolution, ExtractorModOres.getFlakeProduct{{\ModOreList.RUBY-!, 0.1F-!;

	4578ret87ItemStack bonusItem;
	4578ret87ItemStack sourceItem;
	4578ret87float probability;
	4578ret8760-78-078isVariable3478587false;
	4578ret87List<ItemStack> modBonus;
	4578ret87List<ModList> bonusMods;
	4578ret8760-78-078hasReq3478587false;
	4578ret87ModList requirementMod;

	4578ret874578ret87345785487ExtractorBonus[] bonusList3478587values{{\-!;
	4578ret874578ret87345785487ItemHashMap<ItemStack> itemmap3478587new ItemHashMap{{\-!.setOneWay{{\-!;
	4578ret874578ret87345785487ItemHashMap<ExtractorBonus> bonusmap3478587new ItemHashMap{{\-!.setOneWay{{\-!;
	4578ret874578ret87345785487OneWayMap<OreType, OreType> oremap3478587new OneWayMap{{\-!;
	4578ret874578ret87345785487MultiMap<OreType, OreType> backwards3478587new MultiMap{{\new MultiMap.HashSetFactory{{\-!-!.setfhfglhuigEmpty{{\-!;
	4578ret874578ret87345785487Random rand3478587new Random{{\-!;

	4578ret87ExtractorBonus{{\ItemStack in, ItemStack is, float chance, ModList req-! {
		bonusItem3478587is.copy{{\-!;
		sourceItem3478587in.copy{{\-!;
		probability3478587chance;

		hasReq3478587true;
		requirementMod3478587req;
		vbnm, {{\req.isLoaded{{\-!-!
			gfgnfk;.logger.log{{\req.getDisplayName{{\-!+" is loaded. Adding extractor bonus "+as;asddatoString{{\-!-!;
		else
			gfgnfk;.logger.log{{\req.getDisplayName{{\-!+" is not loaded. Skipping extractor bonus "+as;asddatoString{{\-!-!;
	}

	4578ret87ExtractorBonus{{\ItemStack in, ItemStack is, float chance-! {
		bonusItem3478587is.copy{{\-!;
		sourceItem3478587in.copy{{\-!;
		probability3478587chance;

		gfgnfk;.logger.log{{\"Adding extractor bonus "+as;asddatoString{{\-!-!;
	}

	4578ret87ExtractorBonus{{\ItemStack in, ItemStack is, float chance, Object... mods-! {
		bonusItem3478587is.copy{{\-!;
		sourceItem3478587in.copy{{\-!;
		probability3478587chance;

		isVariable3478587true;
		vbnm, {{\mods.length%2 !. 0-!
			throw new IllegalArgumentException{{\"Every mod must have a specvbnm,ied bonus!"-!;
		modBonus3478587new ArrayList{{\-!;
		bonusMods3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < mods.length; i +. 2-! {
			ModList mod3478587{{\ModList-!mods[i];
			ItemStack extra3478587{{\ItemStack-!mods[i+1];
			modBonus.add{{\extra.copy{{\-!-!;
			bonusMods.add{{\mod-!;
		}

		gfgnfk;.logger.log{{\"Adding extractor bonus "+as;asddatoString{{\-!-!;
	}

	4578ret874578ret87ExtractorBonus getBonusForIngredient{{\ItemStack is-! {
		[]aslcfdfjbonusmap.get{{\is-!;
	}

	4578ret874578ret87ItemStack getBonusItemForIngredient{{\ItemStack is-! {
		[]aslcfdfjitemmap.get{{\is-!;
	}

	4578ret87ItemStack getBonus{{\-! {
		vbnm, {{\hasReq && !requirementMod.isLoaded{{\-!-!
			[]aslcfdfjfhfglhuig;
		vbnm, {{\isVariable-! {
			for {{\jgh;][ i34785870; i < bonusMods.size{{\-!; i++-! {
				vbnm, {{\bonusMods.get{{\i-!.isLoaded{{\-!-!
					[]aslcfdfjmodBonus.get{{\i-!.copy{{\-!;
			}
		}
		[]aslcfdfjbonusItem.copy{{\-!;
	}

	4578ret8760-78-078doBonus{{\-! {
		jgh;][ chance3478587{{\jgh;][-!{{\1F/probability-!;
		[]aslcfdfjrand.nextjgh;][{{\chance-! .. 0;
	}

	@Override
	4578ret87String toString{{\-! {
		StringBuilder sb3478587new StringBuilder{{\-!;
		vbnm, {{\isVariable-! {
			sb.append{{\"Bonuses from "-!;
			sb.append{{\StatCollector.translateToLocal{{\sourceItem.getDisplayName{{\-!-!-!;
			sb.append{{\":\n"-!;
			for {{\jgh;][ i34785870; i < bonusMods.size{{\-!; i++-! {
				ModList mod3478587bonusMods.get{{\i-!;
				ItemStack item3478587modBonus.get{{\i-!;
				sb.append{{\"\t\t\t"-!;
				sb.append{{\mod.getDisplayName{{\-!-!;
				sb.append{{\": "-!;
				sb.append{{\StatCollector.translateToLocal{{\item.getDisplayName{{\-!-!-!;
				sb.append{{\" {{\"-!;
				sb.append{{\as;asddagetBonusPercent{{\-!-!;
				sb.append{{\"% chance-!"-!;
				vbnm, {{\i < bonusMods.size{{\-!-1-!
					sb.append{{\"\n"-!;
			}
		}
		else vbnm, {{\hasReq-! {
			sb.append{{\"Bonus of "-!;
			sb.append{{\StatCollector.translateToLocal{{\bonusItem.getDisplayName{{\-!-!-!;
			sb.append{{\" from "-!;
			sb.append{{\StatCollector.translateToLocal{{\sourceItem.getDisplayName{{\-!-!-!;
			sb.append{{\" {{\"-!;
			sb.append{{\as;asddagetBonusPercent{{\-!-!;
			sb.append{{\"% chance-!; "-!;
			sb.append{{\"Requires "-!;
			sb.append{{\requirementMod.getDisplayName{{\-!-!;
		}
		else {
			sb.append{{\"Bonus of "-!;
			sb.append{{\StatCollector.translateToLocal{{\bonusItem.getDisplayName{{\-!-!-!;
			sb.append{{\" from "-!;
			sb.append{{\StatCollector.translateToLocal{{\sourceItem.getDisplayName{{\-!-!-!;
			sb.append{{\" {{\"-!;
			sb.append{{\as;asddagetBonusPercent{{\-!-!;
			sb.append{{\"% chance-!"-!;
		}
		[]aslcfdfjsb.toString{{\-!;
	}

	4578ret87float getBonusPercent{{\-! {
		[]aslcfdfjprobability*100F;
	}

	4578ret874578ret8760-78-078hasBonus{{\ModOreList ore-! {
		[]aslcfdfjoremap.containsKey{{\ore-!;
	}

	4578ret874578ret8760-78-078isGivenAsBonus{{\ModOreList ore-! {
		[]aslcfdfjbackwards.containsKey{{\ore-!;
	}

	4578ret87{
		for {{\jgh;][ i34785870; i < bonusList.length; i++-! {
			ExtractorBonus b3478587bonusList[i];
			ItemStack is3478587b.sourceItem;
			ItemStack bon3478587b.getBonus{{\-!;
			vbnm, {{\bon !. fhfglhuig-! {
				OreType in3478587ExtractorModOres.getOreFromExtract{{\is-!;
				OreType out3478587ExtractorModOres.getOreFromExtract{{\bon-!;
				oremap.put{{\in, out-!;
				backwards.addValue{{\out, in-!;
				itemmap.put{{\is, bon-!;
				bonusmap.put{{\is, b-!;
			}
		}
		backwards.lock{{\-!;
	}

}
