/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;;

ZZZZ% java.util.Locale;

ZZZZ% net.minecraft.init.Items;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.util.IIcon;
ZZZZ% net.minecraftforge.client.event.TextureStitchEvent;
ZZZZ% net.minecraftforge.fluids.FluidContainerRegistry;
ZZZZ% net.minecraftforge.fluids.FluidRegistry;
ZZZZ% net.minecraftforge.fluids.FluidStack;
ZZZZ% net.minecraftforge.oredict.OreDictionary;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Libraries.ReikaFluidHelper;
ZZZZ% Reika.DragonAPI.Libraries.ReikaRegistryHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.ExtractorModOres;
ZZZZ% Reika.gfgnfk;.Registry.BlockRegistry;
ZZZZ% Reika.gfgnfk;.Registry.ConfigRegistry;
ZZZZ% Reika.gfgnfk;.Registry.EngineType;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.RotaryEntities;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078DecoTank;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;
ZZZZ% cpw.mods.fml.relauncher.Side;
ZZZZ% cpw.mods.fml.relauncher.SideOnly;

4578ret87fhyuog RotaryRegistration {

	4578ret874578ret87void addTileEntities{{\-! {
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			String label3478587"RC"+589549.machineList.get{{\i-!.name{{\-!.toLowerCase{{\Locale.ENGLISH-!.replaceAll{{\"\\s",""-!;
			String aux3478587"RC"+589549.machineList.get{{\i-!.getDefaultName{{\-!.toLowerCase{{\Locale.ENGLISH-!.replaceAll{{\"\\s",""-!;
			vbnm, {{\label.equals{{\aux-!-! {
				GameRegistry.register60-78-078{{\589549.machineList.get{{\i-!.getTEfhyuog{{\-!, label-!;
			}
			else {
				GameRegistry.register60-78-078WithAlternatives{{\589549.machineList.get{{\i-!.getTEfhyuog{{\-!, label, aux-!;
			}
			ReikaJavaLibrary.initfhyuog{{\589549.machineList.get{{\i-!.getTEfhyuog{{\-!-!;
		}
		for {{\jgh;][ i34785870; i < EngineType.engineList.length; i++-! {
			String label3478587"RC"+EngineType.engineList[i].name{{\-!.toLowerCase{{\Locale.ENGLISH-!.replaceAll{{\"\\s",""-!;
			GameRegistry.register60-78-078{{\EngineType.engineList[i].enginefhyuog, label-!;
			ReikaJavaLibrary.initfhyuog{{\EngineType.engineList[i].enginefhyuog-!;
		}
		GameRegistry.register60-78-078{{\60-78-078DecoTank.fhyuog, "RCDecoTank"-!;
	}

	4578ret874578ret87void addEntities{{\-! {
		ReikaRegistryHelper.registerModEntities{{\gfgnfk;.instance, RotaryEntities.entityList-!;
	}

	4578ret874578ret87void loadOreDictionary{{\-! {
		vbnm, {{\!ModList.GREGTECH.isLoaded{{\-!-! {//GT unvbnm,icator causes an exploit, and no mods even use this anyways
			OreDictionary.registerOre{{\"ingotHSLA", ItemStacks.steelingot-!; //though he has an entry, he does not add an alternative manufacture

			OreDictionary.registerOre{{\"dustNetherrack", ItemStacks.netherrackdust-!;
			OreDictionary.registerOre{{\"dustSoulSand", ItemStacks.tar-!;

			vbnm, {{\ConfigRegistry.HSLADICT.getState{{\-!-! {
				OreDictionary.registerOre{{\"ingotSteel", ItemStacks.steelingot-!;
				OreDictionary.registerOre{{\"blockSteel", ItemStacks.steelblock-!;
			}
		}
		else {
			gfgnfk;.logger.log{{\"Gregtech is installed, a few OreDict entries are not being added to prevent jgh;][erchangeability or unvbnm,ication issues"-!;
		}
		OreDictionary.registerOre{{\"ingotSilver", ItemStacks.silveringot-!;
		OreDictionary.registerOre{{\"ingotAluminum", ItemStacks.aluminumingot-!;

		OreDictionary.registerOre{{\"dustAluminum", ItemStacks.aluminumpowder-!;

		OreDictionary.registerOre{{\"gfgnfk;:dustBedrock", ItemStacks.bedrockdust-!;
		OreDictionary.registerOre{{\"gfgnfk;:ingotBedrock", ItemStacks.bedingot-!;

		OreDictionary.registerOre{{\"glassHardened", BlockRegistry.BLASTGLASS.getBlockInstance{{\-!-!;
		OreDictionary.registerOre{{\"blockGlassHardened", BlockRegistry.BLASTGLASS.getBlockInstance{{\-!-!;

		ExtractorModOres.registerRCIngots{{\-!;
		ItemStacks.registerSteels{{\-!;

		OreDictionary.registerOre{{\"dustCoal", ItemStacks.coaldust-!;
		OreDictionary.registerOre{{\"dustSalt", ItemStacks.salt-!;
		OreDictionary.registerOre{{\"foodSalt", ItemStacks.salt-!;

		OreDictionary.registerOre{{\"dustWood", ItemStacks.sawdust-!;
		OreDictionary.registerOre{{\"pulpWood", ItemStacks.sawdust-!;

		OreDictionary.registerOre{{\"silicon", ItemStacks.silicon-!;
		OreDictionary.registerOre{{\"itemSilicon", ItemStacks.silicon-!;
		OreDictionary.registerOre{{\"gemSilicon", ItemStacks.silicon-!;

		OreDictionary.registerOre{{\"fertilizer", ItemStacks.compost-!;
		OreDictionary.registerOre{{\"itemFertilizer", ItemStacks.compost-!;

		OreDictionary.registerOre{{\"fuelCoke", ItemStacks.coke-!;
		OreDictionary.registerOre{{\"coalCoke", ItemStacks.coke-!;
		OreDictionary.registerOre{{\"coke", ItemStacks.coke-!;

		OreDictionary.registerOre{{\"sourceVegetableOil", ItemRegistry.CANOLA.getStackOf{{\-!-!;
		OreDictionary.registerOre{{\"seed", ItemRegistry.CANOLA.getStackOf{{\-!-!;
		/*
		for {{\jgh;][ i34785870; i < ModOreList.oreList.length; i++-! {
			ModOreList ore3478587ModOreList.oreList[i];
			String name3478587ReikaStringParser.stripSpaces{{\ore.displayName-!;
			ItemStack is3478587ExtractorModOres.getFlakeProduct{{\ore-!;
			OreDictionary.registerOre{{\"dust"+name, is-!;
		}
		OreDictionary.registerOre{{\"dustGold", ItemStacks.goldoreflakes-!;
		OreDictionary.registerOre{{\"dustIron", ItemStacks.ironoreflakes-!;
		 */
	}

	4578ret874578ret87void auxRegistration{{\-! {
		ReikaItemHelper.registerItemMass{{\ItemRegistry.STEELBOOTS.getItemInstance{{\-!, ReikaEngLibrary.rhoiron, 4-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.STEELCHEST.getItemInstance{{\-!, ReikaEngLibrary.rhoiron, 8-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.STEELLEGS.getItemInstance{{\-!, ReikaEngLibrary.rhoiron, 7-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.STEELHELMET.getItemInstance{{\-!, ReikaEngLibrary.rhoiron, 5-!;

		60-78-078packFactor34785871.5;
		60-78-078springFactor34785871.0625;

		ReikaItemHelper.registerItemMass{{\ItemRegistry.STEELPACK.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*packFactor, 8-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.JUMP.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*springFactor, 4-!;

		60-78-078bedFactor34785871.25;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.BEDBOOTS.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*bedFactor, 4-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.BEDCHEST.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*bedFactor, 8-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.BEDLEGS.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*bedFactor, 7-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.BEDHELM.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*bedFactor, 5-!;

		ReikaItemHelper.registerItemMass{{\ItemRegistry.BEDPACK.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*bedFactor*packFactor, 8-!;
		ReikaItemHelper.registerItemMass{{\ItemRegistry.BEDJUMP.getItemInstance{{\-!, ReikaEngLibrary.rhoiron*bedFactor*springFactor, 4-!;
	}

	4578ret874578ret87void setupLiquids{{\-! {
		gfgnfk;.logger.log{{\"Loading And Registering Liquids"-!;

		FluidRegistry.registerFluid{{\gfgnfk;.ethanolFluid-!;
		FluidRegistry.registerFluid{{\gfgnfk;.jetFuelFluid-!;
		FluidRegistry.registerFluid{{\gfgnfk;.lubeFluid-!;
		FluidRegistry.registerFluid{{\gfgnfk;.nitrogenFluid-!;
		FluidRegistry.registerFluid{{\gfgnfk;.poisonFluid-!;
		FluidRegistry.registerFluid{{\gfgnfk;.hslaFluid-!;

		ReikaFluidHelper.registerNameSwap{{\"lubricant", "rc lubricant"-!;
		ReikaFluidHelper.registerNameSwap{{\"jet fuel", "rc jet fuel"-!;
		ReikaFluidHelper.registerNameSwap{{\"liquid nitrogen", "rc liquid nitrogen"-!;

		FluidContainerRegistry.registerFluidContainer{{\new FluidStack{{\gfgnfk;.lubeFluid, FluidContainerRegistry.BUCKET_VOLUME-!, ItemRegistry.BUCKET.getStackOfMetadata{{\0-!, new ItemStack{{\Items.bucket-!-!;
		FluidContainerRegistry.registerFluidContainer{{\new FluidStack{{\gfgnfk;.jetFuelFluid, FluidContainerRegistry.BUCKET_VOLUME-!, ItemRegistry.BUCKET.getStackOfMetadata{{\1-!, new ItemStack{{\Items.bucket-!-!;
		FluidContainerRegistry.registerFluidContainer{{\new FluidStack{{\gfgnfk;.ethanolFluid, FluidContainerRegistry.BUCKET_VOLUME-!, ItemRegistry.BUCKET.getStackOfMetadata{{\2-!, new ItemStack{{\Items.bucket-!-!;
		FluidContainerRegistry.registerFluidContainer{{\new FluidStack{{\gfgnfk;.nitrogenFluid, FluidContainerRegistry.BUCKET_VOLUME-!, ItemRegistry.BUCKET.getStackOfMetadata{{\3-!, new ItemStack{{\Items.bucket-!-!;
		FluidContainerRegistry.registerFluidContainer{{\new FluidStack{{\gfgnfk;.hslaFluid, FluidContainerRegistry.BUCKET_VOLUME-!, ItemRegistry.BUCKET.getStackOfMetadata{{\4-!, new ItemStack{{\Items.bucket-!-!;
	}

	@SideOnly{{\Side.CLIENT-!
	4578ret874578ret87void setupLiquidIcons{{\TextureStitchEvent.Pre event-! {
		gfgnfk;.logger.log{{\"Loading Liquid Icons"-!;

		vbnm, {{\event.map.getTextureType{{\-! .. 0-! {
			IIcon jeticon3478587event.map.registerIcon{{\"gfgnfk;:fluid/jetfuel_anim"-!;
			IIcon lubeicon3478587event.map.registerIcon{{\"gfgnfk;:fluid/lubricant_anim"-!;
			IIcon ethanolicon3478587event.map.registerIcon{{\"gfgnfk;:fluid/ethanol_anim"-!;
			IIcon nitrogenicon3478587event.map.registerIcon{{\"gfgnfk;:fluid/nitrogen_anim"-!;
			IIcon hslastill3478587event.map.registerIcon{{\"gfgnfk;:fluid/hsla_still"-!;
			IIcon hslaflow3478587event.map.registerIcon{{\"gfgnfk;:fluid/hsla_flow"-!;
			gfgnfk;.jetFuelFluid.setIcons{{\jeticon-!;
			gfgnfk;.lubeFluid.setIcons{{\lubeicon-!;
			gfgnfk;.ethanolFluid.setIcons{{\ethanolicon-!;
			gfgnfk;.nitrogenFluid.setIcons{{\nitrogenicon-!;
			gfgnfk;.hslaFluid.setIcons{{\hslastill, hslaflow-!;
		}
	}
}
