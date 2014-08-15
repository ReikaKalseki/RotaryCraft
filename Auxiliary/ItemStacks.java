/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.PreferentialItemStack;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemStacks {

	public static final ItemStack netherrackdust 	= ItemRegistry.POWDERS.getStackOfMetadata(0);
	public static final ItemStack tar 				= ItemRegistry.POWDERS.getStackOfMetadata(1);
	public static final ItemStack sludge 			= ItemRegistry.POWDERS.getStackOfMetadata(2);
	public static final ItemStack sawdust 			= ItemRegistry.POWDERS.getStackOfMetadata(3);
	public static final ItemStack bedrockdust 		= ItemRegistry.POWDERS.getStackOfMetadata(4);
	public static final ItemStack salt 				= ItemRegistry.POWDERS.getStackOfMetadata(5);
	public static final ItemStack nitrate 			= ItemRegistry.POWDERS.getStackOfMetadata(6);
	public static final ItemStack silveriodide 		= ItemRegistry.POWDERS.getStackOfMetadata(7);
	public static final ItemStack aluminumpowder 	= ItemRegistry.POWDERS.getStackOfMetadata(8);
	public static final ItemStack flour 			= ItemRegistry.POWDERS.getStackOfMetadata(9);
	public static final ItemStack coaldust 			= ItemRegistry.POWDERS.getStackOfMetadata(10);
	public static final ItemStack dryice 			= ItemRegistry.POWDERS.getStackOfMetadata(11);
	public static final ItemStack redgolddust 		= ItemRegistry.POWDERS.getStackOfMetadata(12);
	public static final ItemStack compost 			= ItemRegistry.POWDERS.getStackOfMetadata(13);

	public static final ItemStack lubebucket 		= ItemRegistry.BUCKET.getStackOfMetadata(0);
	public static final ItemStack fuelbucket 		= ItemRegistry.BUCKET.getStackOfMetadata(1);
	public static final ItemStack ethanolbucket 	= ItemRegistry.BUCKET.getStackOfMetadata(2);
	public static final ItemStack nitrogenbucket 	= ItemRegistry.BUCKET.getStackOfMetadata(3);

	public static final ItemStack bedingot 			= ItemRegistry.COMPACTS.getStackOfMetadata(3);
	public static final ItemStack aluminumingot 	= ItemRegistry.COMPACTS.getStackOfMetadata(4);
	public static final ItemStack tungsteningot 	= ItemRegistry.COMPACTS.getStackOfMetadata(5);
	public static final ItemStack redgoldingot 		= ItemRegistry.COMPACTS.getStackOfMetadata(6);
	public static final ItemStack silveringot 		= ItemRegistry.COMPACTS.getStackOfMetadata(7);
	public static final ItemStack coke 				= ItemRegistry.COMPACTS.getStackOfMetadata(8);

	public static final ItemStack basepanel 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(0);
	public static final ItemStack steelingot 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(1);

	public static final ItemStack hose 				= MachineRegistry.HOSE.getCraftedProduct();
	public static final ItemStack pipe 				= MachineRegistry.PIPE.getCraftedProduct();
	public static final ItemStack fuelline 			= MachineRegistry.FUELLINE.getCraftedProduct();

	public static final ItemStack shaftcross 		= ItemRegistry.SHAFT.getStackOfMetadata(RotaryNames.getNumberShaftTypes()-1);

	public static final ItemStack shaftitem 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(2);
	public static final ItemStack mount 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(3);
	public static final ItemStack steelgear 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(4);
	public static final ItemStack gearunit 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(5);
	public static final ItemStack gearunit4 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(6);
	public static final ItemStack gearunit8 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(7);
	public static final ItemStack gearunit16 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(8);
	public static final ItemStack scrap 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(9);
	public static final ItemStack ironscrap 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(10);
	public static final ItemStack flywheelcore 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(11);
	public static final ItemStack flywheelcore2 	= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(12);
	public static final ItemStack flywheelcore3 	= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(13);
	public static final ItemStack flywheelcore4 	= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(14);
	public static final ItemStack wormgear 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(15);

	public static final ItemStack wood2x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(0);
	public static final ItemStack stone2x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(4);
	public static final ItemStack diamond2x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(8);
	public static final ItemStack bedrock2x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(12);
	public static final ItemStack wood4x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(1);
	public static final ItemStack stone4x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(5);
	public static final ItemStack diamond4x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(9);
	public static final ItemStack bedrock4x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(13);
	public static final ItemStack wood8x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(2);
	public static final ItemStack stone8x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(6);
	public static final ItemStack diamond8x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(10);
	public static final ItemStack bedrock8x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(14);
	public static final ItemStack wood16x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(3);
	public static final ItemStack stone16x 			= ItemRegistry.GEARUNITS.getStackOfMetadata(7);
	public static final ItemStack diamond16x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(11);
	public static final ItemStack bedrock16x 		= ItemRegistry.GEARUNITS.getStackOfMetadata(15);

	public static final ItemStack barrel 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(0);
	public static final ItemStack lens 				= ItemRegistry.MISCCRAFT.getStackOfMetadata(1);
	public static final ItemStack power 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(2);
	public static final ItemStack bulb 				= ItemRegistry.MISCCRAFT.getStackOfMetadata(3);
	public static final ItemStack lim 				= ItemRegistry.MISCCRAFT.getStackOfMetadata(4);
	public static final ItemStack prop 				= ItemRegistry.MISCCRAFT.getStackOfMetadata(5);
	public static final ItemStack hub 				= ItemRegistry.MISCCRAFT.getStackOfMetadata(6);
	public static final ItemStack mirror 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(7);
	public static final ItemStack generator 		= ItemRegistry.MISCCRAFT.getStackOfMetadata(8);
	public static final ItemStack railhead 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(9);
	public static final ItemStack railbase 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(10);
	public static final ItemStack railaim 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(11);
	public static final ItemStack compoundturb 		= ItemRegistry.MISCCRAFT.getStackOfMetadata(12);
	public static final ItemStack bedrockcoil 		= ItemRegistry.MISCCRAFT.getStackOfMetadata(13);
	public static final ItemStack chain 			= ItemRegistry.MISCCRAFT.getStackOfMetadata(14);

	public static final ItemStack impeller 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(0);
	public static final ItemStack compressor 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(1);
	public static final ItemStack turbine 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(2);
	public static final ItemStack diffuser 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(3);
	public static final ItemStack combustor 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(4);
	public static final ItemStack cylinder 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(5);
	public static final ItemStack radiator 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(6);
	public static final ItemStack condenser 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(7);
	public static final ItemStack goldcoil 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(8);
	public static final ItemStack waterplate 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(13);
	public static final ItemStack shaftcore 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(14);
	public static final ItemStack igniter 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(15);

	public static final ItemStack woodgear 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(9);
	public static final ItemStack stonegear 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(10);
	public static final ItemStack diamondgear 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(11);
	public static final ItemStack bedrockgear 		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(12);

	public static final ItemStack drill 			= ItemRegistry.BORECRAFT.getStackOfMetadata(0);
	public static final ItemStack presshead 		= ItemRegistry.BORECRAFT.getStackOfMetadata(1);
	public static final ItemStack radar 			= ItemRegistry.BORECRAFT.getStackOfMetadata(2);
	public static final ItemStack sonar 			= ItemRegistry.BORECRAFT.getStackOfMetadata(3);
	public static final ItemStack pcb 				= ItemRegistry.BORECRAFT.getStackOfMetadata(4);
	public static final ItemStack screen 			= ItemRegistry.BORECRAFT.getStackOfMetadata(5);
	public static final ItemStack mixer 			= ItemRegistry.BORECRAFT.getStackOfMetadata(6);
	public static final ItemStack saw 				= ItemRegistry.BORECRAFT.getStackOfMetadata(7);
	public static final ItemStack bearing 			= ItemRegistry.BORECRAFT.getStackOfMetadata(8);
	public static final ItemStack belt 				= ItemRegistry.BORECRAFT.getStackOfMetadata(9);
	public static final ItemStack ballbearing 		= ItemRegistry.BORECRAFT.getStackOfMetadata(10);
	public static final ItemStack brake 			= ItemRegistry.BORECRAFT.getStackOfMetadata(11);
	public static final ItemStack tenscoil 			= ItemRegistry.BORECRAFT.getStackOfMetadata(12);

	public static final ItemStack stonerod 			= ItemRegistry.BORECRAFT.getStackOfMetadata(13);
	public static final ItemStack diamondshaft 		= ItemRegistry.BORECRAFT.getStackOfMetadata(14);
	public static final ItemStack bedrockshaft 		= ItemRegistry.BORECRAFT.getStackOfMetadata(15);

	public static final ItemStack coaloredust 		= ItemRegistry.EXTRACTS.getStackOfMetadata(0);
	public static final ItemStack ironoredust 		= ItemRegistry.EXTRACTS.getStackOfMetadata(1);
	public static final ItemStack goldoredust 		= ItemRegistry.EXTRACTS.getStackOfMetadata(2);
	public static final ItemStack redoredust 		= ItemRegistry.EXTRACTS.getStackOfMetadata(3);
	public static final ItemStack lapisoredust 		= ItemRegistry.EXTRACTS.getStackOfMetadata(4);
	public static final ItemStack diamondoredust 	= ItemRegistry.EXTRACTS.getStackOfMetadata(5);
	public static final ItemStack emeraldoredust 	= ItemRegistry.EXTRACTS.getStackOfMetadata(6);
	public static final ItemStack netherquartzdust 	= ItemRegistry.EXTRACTS.getStackOfMetadata(7);

	public static final ItemStack coaloreslurry 	= ItemRegistry.EXTRACTS.getStackOfMetadata(8);
	public static final ItemStack ironoreslurry 	= ItemRegistry.EXTRACTS.getStackOfMetadata(9);
	public static final ItemStack goldoreslurry 	= ItemRegistry.EXTRACTS.getStackOfMetadata(10);
	public static final ItemStack redoreslurry 		= ItemRegistry.EXTRACTS.getStackOfMetadata(11);
	public static final ItemStack lapisoreslurry 	= ItemRegistry.EXTRACTS.getStackOfMetadata(12);
	public static final ItemStack diamondoreslurry 	= ItemRegistry.EXTRACTS.getStackOfMetadata(13);
	public static final ItemStack emeraldoreslurry 	= ItemRegistry.EXTRACTS.getStackOfMetadata(14);
	public static final ItemStack quartzslurry 		= ItemRegistry.EXTRACTS.getStackOfMetadata(15);

	public static final ItemStack coalsolution 		= ItemRegistry.EXTRACTS.getStackOfMetadata(16);
	public static final ItemStack ironsolution 		= ItemRegistry.EXTRACTS.getStackOfMetadata(17);
	public static final ItemStack goldsolution 		= ItemRegistry.EXTRACTS.getStackOfMetadata(18);
	public static final ItemStack redsolution 		= ItemRegistry.EXTRACTS.getStackOfMetadata(19);
	public static final ItemStack lapissolution 	= ItemRegistry.EXTRACTS.getStackOfMetadata(20);
	public static final ItemStack diamondsolution 	= ItemRegistry.EXTRACTS.getStackOfMetadata(21);
	public static final ItemStack emeraldsolution 	= ItemRegistry.EXTRACTS.getStackOfMetadata(22);
	public static final ItemStack quartzsolution 	= ItemRegistry.EXTRACTS.getStackOfMetadata(23);

	public static final ItemStack coaloreflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(24);
	public static final ItemStack ironoreflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(25);
	public static final ItemStack goldoreflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(26);
	public static final ItemStack redoreflakes 		= ItemRegistry.EXTRACTS.getStackOfMetadata(27);
	public static final ItemStack lapisoreflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(28);
	public static final ItemStack diamondoreflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(29);
	public static final ItemStack emeraldoreflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(30);
	public static final ItemStack quartzflakes 		= ItemRegistry.EXTRACTS.getStackOfMetadata(31);
	public static final ItemStack silverflakes 		= ItemRegistry.EXTRACTS.getStackOfMetadata(32);
	public static final ItemStack tungstenflakes 	= ItemRegistry.EXTRACTS.getStackOfMetadata(33);

	public static final ItemStack anthracite 		= ItemRegistry.COMPACTS.getStackOfMetadata(0);
	public static final ItemStack prismane 			= ItemRegistry.COMPACTS.getStackOfMetadata(1);
	public static final ItemStack lonsda 			= ItemRegistry.COMPACTS.getStackOfMetadata(2);

	public static final ItemStack steelblock 		= BlockRegistry.DECO.getStackOfMetadata(0);
	public static final ItemStack anthrablock 		= BlockRegistry.DECO.getStackOfMetadata(1);
	public static final ItemStack lonsblock 		= BlockRegistry.DECO.getStackOfMetadata(2);
	public static final ItemStack shieldblock 		= BlockRegistry.DECO.getStackOfMetadata(3);

	public static final ItemStack slipperyComb 		= ItemRegistry.MODINTERFACE.getStackOfMetadata(0);
	public static final ItemStack slipperyPropolis 	= ItemRegistry.MODINTERFACE.getStackOfMetadata(1);

	private static ArrayList<ItemStack> modsteel = new ArrayList<ItemStack>();

	public static final PreferentialItemStack electric = new PreferentialItemStack(Items.gold_ingot, "ingotElectrum");
	public static final PreferentialItemStack conductive = new PreferentialItemStack(Items.gold_ingot, "ingotCopper");
	public static final PreferentialItemStack conductiveCheap = new PreferentialItemStack(ItemStacks.steelingot, "ingotCopper");
	public static final PreferentialItemStack heavy = new PreferentialItemStack(Items.gold_ingot, "ingotLead");
	public static final PreferentialItemStack reflective = new PreferentialItemStack(Items.iron_ingot, "ingotSilver");

	public static List getModSteels() {
		return modsteel;
	}

	public static void registerSteels() {
		modsteel.addAll(OreDictionary.getOres("ingotSteel"));
		modsteel.addAll(OreDictionary.getOres("steelIngot"));
	}

	public static ItemStack getModOreIngot(ModOreList ore) {
		return ItemRegistry.MODINGOTS.getStackOfMetadata(ore.ordinal());
	}
}