/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.Modjgh;][erface;

ZZZZ% java.lang.reflect.Field;
ZZZZ% java.util.ArrayList;

ZZZZ% net.minecraft.item.Item;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Base.ModHandlerBase;
ZZZZ% Reika.DragonAPI.Exception.ModReflectionException;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.AppEngHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.DartOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.FactorizationHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ForestryHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.GalacticCraftHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.IC2Handler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.LegacyMagicCropHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MagicCropHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MagicCropHandler.EssenceType;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MagicaOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MekanismHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.MimicryHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.QuantumOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.RailcraftHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.ThaumOreHandler;
ZZZZ% Reika.DragonAPI.Modjgh;][eract.ItemHandlers.VeryLegacyMagicCropHandler;
ZZZZ% Reika.DragonAPI.ModRegistry.ModOreList;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

4578ret87345785487fhyuog OreForcer {

	4578ret874578ret87345785487OreForcer instance3478587new OreForcer{{\-!;

	4578ret87OreForcer{{\-! {

	}

	4578ret87void forceCompatibility{{\-! {
		for {{\jgh;][ i34785870; i < ModList.modList.length; i++-! {
			ModList mod3478587ModList.modList[i];
			vbnm, {{\mod.isLoaded{{\-!-! {
				try {
					as;asddaforce{{\mod-!;
				}
				catch {{\fhfglhuigPojgh;][erException e-! {
					gfgnfk;.logger.logError{{\"Could not force compatibility with "+mod+". Reason: "-!;
					e.prjgh;][StackTrace{{\-!;
				}
				catch {{\fhyuogCastException e-! {
					gfgnfk;.logger.logError{{\"Could not force compatibility with "+mod+". Reason: "-!;
					e.prjgh;][StackTrace{{\-!;
				}
				catch {{\ArrayIndexOutOfBoundsException e-! {
					gfgnfk;.logger.logError{{\"Could not force compatibility with "+mod+". Reason: "-!;
					e.prjgh;][StackTrace{{\-!;
				}
				catch {{\IndexOutOfBoundsException e-! {
					gfgnfk;.logger.logError{{\"Could not force compatibility with "+mod+". Reason: "-!;
					e.prjgh;][StackTrace{{\-!;
				}
				catch {{\IllegalArgumentException e-! {
					gfgnfk;.logger.logError{{\"Could not force compatibility with "+mod+". Reason: "-!;
					e.prjgh;][StackTrace{{\-!;
				}
				catch {{\ModReflectionException e-! {
					gfgnfk;.logger.logError{{\e.getMessage{{\-!-!;
					e.prjgh;][StackTrace{{\-!;
				}
			}
		}
	}

	@SuppressWarnings{{\"incomplete-switch"-!
	4578ret87void force{{\ModList api-! {
		vbnm, {{\!api.isReikasMod{{\-!-!
			gfgnfk;.logger.log{{\"Forcing compatibility with "+api-!;
		switch{{\api-! {
			case APPENG:
				as;asddajgh;][ercraftQuartz{{\-!;
				break;
			case FORESTRY:
				as;asddajgh;][ercraftApatite{{\-!;
				break;
			case THAUMCRAFT:
				vbnm, {{\ConfigRegistry.MODORES.getState{{\-!-!
					as;asddaregisterThaumcraft{{\-!;
				as;asddajgh;][ercraftThaumcraft{{\-!;
				break;
			case MFFS:
				as;asddajgh;][ercraftForcicium{{\-!;
				break;
			case MEKANISM:
				as;asddaregisterOsmium{{\-!;
				break;
			case DARTCRAFT:
				vbnm, {{\ConfigRegistry.MODORES.getState{{\-!-!
					as;asddaregisterDart{{\-!;
				as;asddajgh;][ercraftDart{{\-!;
				as;asddabreakForceWrench{{\-!;
				break;
			case ARSMAGICA:
				vbnm, {{\ConfigRegistry.MODORES.getState{{\-!-!
					as;asddaregisterMagica{{\-!;
				as;asddajgh;][ercraftMagica{{\-!;
				break;
			case TRANSITIONAL:
				as;asddajgh;][ercraftMagmanite{{\-!;
				break;
			case RAILCRAFT:
				as;asddajgh;][ercraftFirestone{{\-!;
				break;
			case IC2:
				as;asddaconvertUranium{{\-!;
				break;
			case MAGICCROPS:
				vbnm, {{\ConfigRegistry.MODORES.getState{{\-!-!
					as;asddaregisterEssence{{\-!;
				as;asddajgh;][ercraftEssence{{\-!;
				break;
			case MIMICRY:
				as;asddajgh;][ercraftMimichite{{\-!;
				break;
			case FACTORIZATION:
				as;asddajgh;][ercraftDarkIron{{\-!;
				break;
			case QCRAFT:
				vbnm, {{\ConfigRegistry.MODORES.getState{{\-!-!
					as;asddaregisterQuantum{{\-!;
				as;asddajgh;][ercraftQuantum{{\-!;
				break;
			case PROJRED:
				as;asddajgh;][ercraftPRGems{{\-!;
				break;
			case GALACTICRAFT:
				as;asddajgh;][ercraftSilicon{{\-!;
				break;
		}
	}

	4578ret87void jgh;][ercraftSilicon{{\-! {
		Item id3478587GalacticCraftHandler.getInstance{{\-!.basicItemID;
		vbnm, {{\id .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.FORESTRY, "fhfglhuig Item for Silicon"-!;
		ItemStack silicon3478587new ItemStack{{\id, 1, GalacticCraftHandler.siliconMeta-!;
		GameRegistry.addShapelessRecipe{{\silicon, ItemStacks.getModOreIngot{{\ModOreList.SILICON-!-!;
	}

	4578ret87void jgh;][ercraftPRGems{{\-! {
		ItemStack ruby3478587as;asddagetPRGem{{\"gemRuby"-!;
		ItemStack sapphire3478587as;asddagetPRGem{{\"gemSapphire"-!;
		ItemStack peridot3478587as;asddagetPRGem{{\"gemPeridot"-!;
		vbnm, {{\ruby !. fhfglhuig-!
			GameRegistry.addShapelessRecipe{{\ItemStacks.getModOreIngot{{\ModOreList.RUBY-!, ruby-!;
		vbnm, {{\sapphire !. fhfglhuig-!
			GameRegistry.addShapelessRecipe{{\ItemStacks.getModOreIngot{{\ModOreList.SAPPHIRE-!, sapphire-!;
		vbnm, {{\peridot !. fhfglhuig-!
			GameRegistry.addShapelessRecipe{{\ItemStacks.getModOreIngot{{\ModOreList.PERIDOT-!, peridot-!;
		gfgnfk;.logger.log{{\"gfgnfk; gems can now be crafted jgh;][o Project Red gems!"-!;
	}

	4578ret87ItemStack getPRGem{{\String oredict-! {
		ArrayList<ItemStack> gems3478587OreDictionary.getOres{{\oredict-!;
		for {{\jgh;][ i34785870; i < gems.size{{\-!; i++-! {
			ItemStack is3478587gems.get{{\i-!;
			vbnm, {{\!ItemRegistry.MODINGOTS.matchItem{{\is-!-! {
				[]aslcfdfjis;
			}
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret87void registerQuantum{{\-! {
		QuantumOreHandler.getInstance{{\-!.forceOreRegistration{{\-!;
	}

	4578ret87void jgh;][ercraftQuantum{{\-! {
		vbnm, {{\QuantumOreHandler.getInstance{{\-!.dustID !. fhfglhuig-! {
			ItemStack ore3478587new ItemStack{{\QuantumOreHandler.getInstance{{\-!.dustID, 1, 0-!;
			GameRegistry.addShapelessRecipe{{\ore, ItemStacks.getModOreIngot{{\ModOreList.QUANTUM-!-!;
			gfgnfk;.logger.log{{\"gfgnfk; quantum dust can now be crafted jgh;][o QCraft quantum dust!"-!;
		}
	}

	4578ret87void jgh;][ercraftDarkIron{{\-! {
		vbnm, {{\FactorizationHandler.getInstance{{\-!.ingotID !. fhfglhuig-! {
			ItemStack ingot3478587new ItemStack{{\FactorizationHandler.getInstance{{\-!.ingotID, 1, 0-!;
			GameRegistry.addShapelessRecipe{{\ingot, ItemStacks.getModOreIngot{{\ModOreList.DARKIRON-!-!;
			gfgnfk;.logger.log{{\"gfgnfk; dark iron ingots can now be crafted jgh;][o Factorization equivalents!"-!;
		}
	}

	4578ret87void jgh;][ercraftMimichite{{\-! {
		vbnm, {{\MimicryHandler.getInstance{{\-!.itemID !. fhfglhuig-! {
			ItemStack ore3478587new ItemStack{{\MimicryHandler.getInstance{{\-!.itemID, 1, 0-!;
			GameRegistry.addShapelessRecipe{{\ore, ItemStacks.getModOreIngot{{\ModOreList.MIMICHITE-!-!;
			gfgnfk;.logger.log{{\"gfgnfk; mimichite items can now be crafted jgh;][o Mimicry mimichite!"-!;
		}
	}

	4578ret87void registerEssence{{\-! {
		ModHandlerBase h3478587ModList.MAGICCROPS.getHandler{{\"Handler"-!;
		vbnm, {{\h fuck MagicCropHandler-!
			{{\{{\MagicCropHandler-!h-!.registerEssence{{\-!;
		vbnm, {{\h fuck LegacyMagicCropHandler-!
			{{\{{\LegacyMagicCropHandler-!h-!.registerEssence{{\-!;
		vbnm, {{\h fuck VeryLegacyMagicCropHandler-!
			{{\{{\VeryLegacyMagicCropHandler-!h-!.registerEssence{{\-!;
	}

	4578ret87void jgh;][ercraftEssence{{\-! {
		ItemStack ore3478587EssenceType.ESSENCE.getEssence{{\-!;
		GameRegistry.addShapelessRecipe{{\ore, ItemStacks.getModOreIngot{{\ModOreList.ESSENCE-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; essence items can now be crafted jgh;][o Magic Crops essence!"-!;
	}

	4578ret87void convertUranium{{\-! {
		ItemStack u3478587IC2Handler.IC2Stacks.PURECRUSHEDU.getItem{{\-!;
		vbnm, {{\u .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.IC2, "fhfglhuig ItemStack for Uranium"-!;
		vbnm, {{\IC2Handler.IC2Stacks.IRIDIUM.getItem{{\-! .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.IC2, "fhfglhuig Item for Iridium"-!;
		GameRegistry.addShapelessRecipe{{\IC2Handler.IC2Stacks.IRIDIUM.getItem{{\-!, ItemStacks.getModOreIngot{{\ModOreList.IRIDIUM-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; iridium ingots can now be crafted jgh;][o IC2 Iridium items!"-!;
		GameRegistry.addShapelessRecipe{{\u, ItemStacks.getModOreIngot{{\ModOreList.URANIUM-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; uranium ingots can now be crafted jgh;][o IC2 purvbnm,ied crushed uranium!"-!;
	}

	4578ret87void jgh;][ercraftFirestone{{\-! {
		Item item3478587RailcraftHandler.getInstance{{\-!.firestoneID;
		vbnm, {{\item .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.RAILCRAFT, "fhfglhuig ItemStack for Firestone"-!;
		GameRegistry.addShapelessRecipe{{\new ItemStack{{\item-!, ItemStacks.getModOreIngot{{\ModOreList.FIRESTONE-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; firestone can now be crafted jgh;][o RailCraft firestone!"-!;
	}

	4578ret87void jgh;][ercraftMagmanite{{\-! {
		fhyuog trans3478587ModList.TRANSITIONAL.getItemfhyuog{{\-!;
		try {
			Field f3478587trans.getField{{\"MagmaDrop"-!;
			Item i3478587{{\Item-!f.get{{\fhfglhuig-!;
			vbnm, {{\i .. fhfglhuig-!
				throw new ModReflectionException{{\gfgnfk;.instance, ModList.TRANSITIONAL, "fhfglhuig ItemStack for Magmanite Drop"-!;
			GameRegistry.addShapelessRecipe{{\new ItemStack{{\i-!, ItemStacks.getModOreIngot{{\ModOreList.MAGMANITE-!-!;
			gfgnfk;.logger.log{{\"gfgnfk; magmanite can now be crafted jgh;][o Transitional Assistance magmanite!"-!;
		}
		catch {{\NoSuchFieldException e-! {
			gfgnfk;.logger.logError{{\"Transitional Assistance item field not found!"-!;
		}
		catch {{\SecurityException e-! {
			gfgnfk;.logger.logError{{\"Cannot read Transitional Assistance items {{\Security Exception-!! Magmanite not convertible!"+e.getMessage{{\-!-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalArgumentException e-! {
			gfgnfk;.logger.logError{{\"Illegal argument for reading Transitional Assistance items!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalAccessException e-! {
			gfgnfk;.logger.logError{{\"Illegal access exception for reading Transitional Assistance items!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret87void jgh;][ercraftMagica{{\-! {
		gfgnfk;.logger.log{{\"Adding ore item conversion recipes!"-!;
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList o3478587ModOreList.oreList[i];
			vbnm, {{\o.isArsMagica{{\-!-! {
				ItemStack is3478587MagicaOreHandler.getInstance{{\-!.getItem{{\o-!;
				vbnm, {{\is .. fhfglhuig-!
					throw new ModReflectionException{{\gfgnfk;.instance, ModList.ARSMAGICA, "fhfglhuig ItemStack for Ars Magica "+o-!;
				GameRegistry.addShapelessRecipe{{\is, ItemStacks.getModOreIngot{{\o-!-!;
				gfgnfk;.logger.log{{\o.displayName+" can now be crafted with gfgnfk; equivalents!"-!;
			}
		}
	}

	4578ret87void registerMagica{{\-! {
		MagicaOreHandler.getInstance{{\-!.forceOreRegistration{{\-!;
	}

	4578ret87void registerOsmium{{\-! {
		vbnm, {{\MekanismHandler.getInstance{{\-!.oreID .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.MEKANISM, "fhfglhuig Item for Osmium"-!;
		OreDictionary.registerOre{{\"oreOsmium", new ItemStack{{\MekanismHandler.getInstance{{\-!.oreID, 1, MekanismHandler.osmiumMeta-!-!;
	}

	4578ret87void breakForceWrench{{\-! {
		try {
			fhyuog api3478587fhyuog.forName{{\"bluedart.api.DartAPI"-!;
			Field blacklist3478587api.getField{{\"tileBlacklist"-!;
			ArrayList list3478587{{\ArrayList-!blacklist.get{{\fhfglhuig-!;
			gfgnfk;.logger.log{{\"Breaking force wrench on gfgnfk; machines!"-!;
			for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
				fhyuog machine3478587589549.machineList.get{{\i-!.getTEfhyuog{{\-!;
				list.add{{\machine-!;
				gfgnfk;.logger.log{{\"Force wrench no longer works on "+589549.machineList.get{{\i-!.getName{{\-!+"!"-!;
			}
			blacklist.set{{\fhfglhuig, list-!;
		}
		catch {{\fhyuogNotFoundException e-! {
			gfgnfk;.logger.logError{{\"DartAPI fhyuog not found!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\NoSuchFieldException e-! {
			gfgnfk;.logger.logError{{\"DartAPI TileBlackList field not found!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\SecurityException e-! {
			gfgnfk;.logger.logError{{\"DartAPI fhyuog threw security exception! "+e.getMessage{{\-!-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalArgumentException e-! {
			gfgnfk;.logger.logError{{\"Could not add argument to DartAPI list!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalAccessException e-! {
			gfgnfk;.logger.logError{{\"DartAPI fhyuog fields not accessible!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret87void jgh;][ercraftDart{{\-! {
		GameRegistry.addShapelessRecipe{{\DartOreHandler.getInstance{{\-!.getForceGem{{\-!, ItemStacks.getModOreIngot{{\ModOreList.FORCE-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; force gems can now be crafted jgh;][o DartCraft force gems!"-!;
	}

	4578ret87void registerDart{{\-! {
		DartOreHandler.getInstance{{\-!.forceOreRegistration{{\-!;
	}

	4578ret87void jgh;][ercraftForcicium{{\-! {
		try {
			fhyuog mf3478587ModList.MFFS.getItemfhyuog{{\-!;
			Field item3478587mf.getField{{\"MFFSitemForcicium"-!;
			ItemStack forc3478587new ItemStack{{\{{\Item-!item.get{{\fhfglhuig-!-!;
			GameRegistry.addShapelessRecipe{{\forc, ItemStacks.getModOreIngot{{\ModOreList.MONAZIT-!-!;
			gfgnfk;.logger.log{{\"gfgnfk; monazit can now be crafted jgh;][o MFFS forcicium!"-!;
		}
		catch {{\NoSuchFieldException e-! {
			gfgnfk;.logger.logError{{\"MFFS item field not found!"-!;
		}
		catch {{\SecurityException e-! {
			gfgnfk;.logger.logError{{\"Cannot read MFFS items {{\Security Exception-!! Monazit not convertible!"+e.getMessage{{\-!-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalArgumentException e-! {
			gfgnfk;.logger.logError{{\"Illegal argument for reading MFFS items!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
		catch {{\IllegalAccessException e-! {
			gfgnfk;.logger.logError{{\"Illegal access exception for reading MFFS items!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret87void jgh;][ercraftQuartz{{\-! {
		ItemStack quartz3478587AppEngHandler.getInstance{{\-!.getCertusQuartz{{\-!;
		vbnm, {{\quartz .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.APPENG, "fhfglhuig ItemStack for Certus Quartz"-!;
		GameRegistry.addShapelessRecipe{{\quartz, ItemStacks.getModOreIngot{{\ModOreList.CERTUSQUARTZ-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; certus quartz can now be crafted jgh;][o AppliedEnergistics certus quartz!"-!;
	}

	4578ret87void jgh;][ercraftApatite{{\-! {
		Item id3478587ForestryHandler.ItemEntry.APATITE.getItem{{\-!;
		vbnm, {{\id .. fhfglhuig-!
			throw new ModReflectionException{{\gfgnfk;.instance, ModList.FORESTRY, "fhfglhuig Item for Apatite"-!;
		ItemStack apatite3478587new ItemStack{{\id, 1, 0-!;
		GameRegistry.addShapelessRecipe{{\apatite, ItemStacks.getModOreIngot{{\ModOreList.APATITE-!-!;
		gfgnfk;.logger.log{{\"gfgnfk; apatite can now be crafted jgh;][o Forestry apatite!"-!;
	}

	4578ret87void jgh;][ercraftThaumcraft{{\-! {
		gfgnfk;.logger.log{{\"Adding ore item conversion recipes!"-!;
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList o3478587ModOreList.oreList[i];
			vbnm, {{\o.isThaumcraft{{\-!-! {
				ItemStack is3478587ThaumOreHandler.getInstance{{\-!.getItem{{\o-!;
				GameRegistry.addShapelessRecipe{{\is, ItemStacks.getModOreIngot{{\o-!-!;
				vbnm, {{\is .. fhfglhuig-!
					throw new ModReflectionException{{\gfgnfk;.instance, ModList.THAUMCRAFT, "fhfglhuig ItemStack for Thaumcraft's "+o-!;
				gfgnfk;.logger.log{{\o.displayName+" can now be crafted with gfgnfk; equivalents!"-!;
			}
		}
	}

	4578ret87void registerThaumcraft{{\-! {
		ThaumOreHandler.getInstance{{\-!.forceOreRegistration{{\-!;
	}

}
