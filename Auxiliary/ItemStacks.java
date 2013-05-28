/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.RotaryCraft.mod_RotaryCraft;

public final class ItemStacks {

	public static final ItemStack netherrackdust = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 0);
	public static final ItemStack tar = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 1);
	public static final ItemStack sludge = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 2);
	public static final ItemStack sawdust = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 3);
	public static final ItemStack bedrockdust = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 4);
	public static final ItemStack salt = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 5);
	public static final ItemStack nitrate = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 6);
	public static final ItemStack silveriodide = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 7);
	public static final ItemStack aluminumpowder = new ItemStack(mod_RotaryCraft.powders.itemID, 1, 8);

	public static final ItemStack lubebucket = new ItemStack(mod_RotaryCraft.fuelbucket.itemID, 1, 0);
	public static final ItemStack fuelbucket = new ItemStack(mod_RotaryCraft.fuelbucket.itemID, 1, 1);
	public static final ItemStack ethanolbucket = new ItemStack(mod_RotaryCraft.fuelbucket.itemID, 1, 2);

	public static final ItemStack bedingot = new ItemStack(mod_RotaryCraft.compacts.itemID, 1, 3);
	public static final ItemStack silveringot = new ItemStack(mod_RotaryCraft.compacts.itemID, 1, 7);

	public static final ItemStack basepanel = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 0);
	public static final ItemStack steelingot = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 1);

	public static final ItemStack hose = new ItemStack(mod_RotaryCraft.pipeplacer, 1, 0);
	public static final ItemStack pipe = new ItemStack(mod_RotaryCraft.pipeplacer, 1, 1);
	public static final ItemStack fuelline = new ItemStack(mod_RotaryCraft.pipeplacer, 1, 2);

	public static final ItemStack shaftitem = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 2);
	public static final ItemStack mount = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 3);
	public static final ItemStack steelgear = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 4);
	public static final ItemStack gearunit = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 5);
	public static final ItemStack gearunit4 = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 6);
	public static final ItemStack gearunit8 = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 7);
	public static final ItemStack gearunit16 = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 8);
	public static final ItemStack scrap = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 9);
	public static final ItemStack ironscrap = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 10);
	public static final ItemStack flywheelcore = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 11);
	public static final ItemStack flywheelcore2 = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 12);
	public static final ItemStack flywheelcore3 = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 13);
	public static final ItemStack flywheelcore4 = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 14);
	public static final ItemStack wormgear = new ItemStack(mod_RotaryCraft.shaftcraft, 1, 15);

	public static final ItemStack wood2x = new ItemStack(mod_RotaryCraft.gearunits, 1, 0);
	public static final ItemStack stone2x = new ItemStack(mod_RotaryCraft.gearunits, 1, 4);
	public static final ItemStack diamond2x = new ItemStack(mod_RotaryCraft.gearunits, 1, 8);
	public static final ItemStack bedrock2x = new ItemStack(mod_RotaryCraft.gearunits, 1, 12);
	public static final ItemStack wood4x = new ItemStack(mod_RotaryCraft.gearunits, 1, 1);
	public static final ItemStack stone4x = new ItemStack(mod_RotaryCraft.gearunits, 1, 5);
	public static final ItemStack diamond4x = new ItemStack(mod_RotaryCraft.gearunits, 1, 9);
	public static final ItemStack bedrock4x = new ItemStack(mod_RotaryCraft.gearunits, 1, 13);
	public static final ItemStack wood8x = new ItemStack(mod_RotaryCraft.gearunits, 1, 2);
	public static final ItemStack stone8x = new ItemStack(mod_RotaryCraft.gearunits, 1, 6);
	public static final ItemStack diamond8x = new ItemStack(mod_RotaryCraft.gearunits, 1, 10);
	public static final ItemStack bedrock8x = new ItemStack(mod_RotaryCraft.gearunits, 1, 14);
	public static final ItemStack wood16x = new ItemStack(mod_RotaryCraft.gearunits, 1, 3);
	public static final ItemStack stone16x = new ItemStack(mod_RotaryCraft.gearunits, 1, 7);
	public static final ItemStack diamond16x = new ItemStack(mod_RotaryCraft.gearunits, 1, 11);
	public static final ItemStack bedrock16x = new ItemStack(mod_RotaryCraft.gearunits, 1, 15);

	public static final ItemStack barrel = new ItemStack(mod_RotaryCraft.heatcraft, 1, 0);
	public static final ItemStack lens = new ItemStack(mod_RotaryCraft.heatcraft, 1, 1);
	public static final ItemStack power = new ItemStack(mod_RotaryCraft.heatcraft, 1, 2);
	public static final ItemStack bulb = new ItemStack(mod_RotaryCraft.heatcraft, 1, 3);
	public static final ItemStack lim = new ItemStack(mod_RotaryCraft.heatcraft, 1, 4);
	public static final ItemStack prop = new ItemStack(mod_RotaryCraft.heatcraft, 1, 5);
	public static final ItemStack hub = new ItemStack(mod_RotaryCraft.heatcraft, 1, 6);

	public static final ItemStack impeller = new ItemStack(mod_RotaryCraft.enginecraft, 1, 0);
	public static final ItemStack compressor = new ItemStack(mod_RotaryCraft.enginecraft, 1, 1);
	public static final ItemStack turbine = new ItemStack(mod_RotaryCraft.enginecraft, 1, 2);
	public static final ItemStack diffuser = new ItemStack(mod_RotaryCraft.enginecraft, 1, 3);
	public static final ItemStack combustor = new ItemStack(mod_RotaryCraft.enginecraft, 1, 4);
	public static final ItemStack cylinder = new ItemStack(mod_RotaryCraft.enginecraft, 1, 5);
	public static final ItemStack radiator = new ItemStack(mod_RotaryCraft.enginecraft, 1, 6);
	public static final ItemStack condenser = new ItemStack(mod_RotaryCraft.enginecraft, 1, 7);
	public static final ItemStack goldcoil = new ItemStack(mod_RotaryCraft.enginecraft, 1, 8);
	public static final ItemStack waterplate = new ItemStack(mod_RotaryCraft.enginecraft, 1, 13);
	public static final ItemStack shaftcore = new ItemStack(mod_RotaryCraft.enginecraft, 1, 14);
	public static final ItemStack igniter = new ItemStack(mod_RotaryCraft.enginecraft, 1, 15);

	public static final ItemStack woodgear = new ItemStack(mod_RotaryCraft.enginecraft, 1, 9);
	public static final ItemStack stonegear = new ItemStack(mod_RotaryCraft.enginecraft, 1, 10);
	public static final ItemStack diamondgear = new ItemStack(mod_RotaryCraft.enginecraft, 1, 11);
	public static final ItemStack bedrockgear = new ItemStack(mod_RotaryCraft.enginecraft, 1, 12);

	public static final ItemStack stonerod = new ItemStack(mod_RotaryCraft.borecraft, 1, 13);
	public static final ItemStack diamondshaft = new ItemStack(mod_RotaryCraft.borecraft, 1, 14);
	public static final ItemStack bedrockshaft = new ItemStack(mod_RotaryCraft.borecraft, 1, 15);

	public static final ItemStack drill = new ItemStack(mod_RotaryCraft.borecraft, 1, 0);
	public static final ItemStack presshead = new ItemStack(mod_RotaryCraft.borecraft, 1, 1);
	public static final ItemStack radar = new ItemStack(mod_RotaryCraft.borecraft, 1, 2);
	public static final ItemStack sonar = new ItemStack(mod_RotaryCraft.borecraft, 1, 3);
	public static final ItemStack pcb = new ItemStack(mod_RotaryCraft.borecraft, 1, 4);
	public static final ItemStack screen = new ItemStack(mod_RotaryCraft.borecraft, 1, 5);
	public static final ItemStack mixer = new ItemStack(mod_RotaryCraft.borecraft, 1, 6);
	public static final ItemStack saw = new ItemStack(mod_RotaryCraft.borecraft, 1, 7);
	public static final ItemStack bearing = new ItemStack(mod_RotaryCraft.borecraft, 1, 8);
	public static final ItemStack belt = new ItemStack(mod_RotaryCraft.borecraft, 1, 9);
	public static final ItemStack bearingitem = new ItemStack(mod_RotaryCraft.borecraft, 1, 10);
	public static final ItemStack brake = new ItemStack(mod_RotaryCraft.borecraft, 1, 11);
	public static final ItemStack tenscoil = new ItemStack(mod_RotaryCraft.borecraft, 1, 12);

	public static final ItemStack coaloredust = new ItemStack(mod_RotaryCraft.extracts, 1, 0);
	public static final ItemStack ironoredust = new ItemStack(mod_RotaryCraft.extracts, 1, 1);
	public static final ItemStack goldoredust = new ItemStack(mod_RotaryCraft.extracts, 1, 2);
	public static final ItemStack redoredust = new ItemStack(mod_RotaryCraft.extracts, 1, 3);
	public static final ItemStack lapisoredust = new ItemStack(mod_RotaryCraft.extracts, 1, 4);
	public static final ItemStack diamondoredust = new ItemStack(mod_RotaryCraft.extracts, 1, 5);
	public static final ItemStack emeraldoredust = new ItemStack(mod_RotaryCraft.extracts, 1, 6);
	public static final ItemStack netherquartzdust = new ItemStack(mod_RotaryCraft.extracts, 1, 7);

	public static final ItemStack coaloreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 8);
	public static final ItemStack ironoreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 9);
	public static final ItemStack goldoreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 10);
	public static final ItemStack redoreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 11);
	public static final ItemStack lapisoreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 12);
	public static final ItemStack diamondoreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 13);
	public static final ItemStack emeraldoreslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 14);
	public static final ItemStack netherquartzslurry = new ItemStack(mod_RotaryCraft.extracts, 1, 15);

	public static final ItemStack coaloresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 16);
	public static final ItemStack ironoresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 17);
	public static final ItemStack goldoresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 18);
	public static final ItemStack redoresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 19);
	public static final ItemStack lapisoresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 20);
	public static final ItemStack diamondoresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 21);
	public static final ItemStack emeraldoresolution = new ItemStack(mod_RotaryCraft.extracts, 1, 22);
	public static final ItemStack netherquartzsolution = new ItemStack(mod_RotaryCraft.extracts, 1, 23);

	public static final ItemStack coaloreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 24);
	public static final ItemStack ironoreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 25);
	public static final ItemStack goldoreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 26);
	public static final ItemStack redoreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 27);
	public static final ItemStack lapisoreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 28);
	public static final ItemStack diamondoreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 29);
	public static final ItemStack emeraldoreflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 30);
	public static final ItemStack netherquartzflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 31);
	public static final ItemStack silverflakes = new ItemStack(mod_RotaryCraft.extracts, 1, 32);

	public static final ItemStack anthracite = new ItemStack(mod_RotaryCraft.compacts, 1, 0);
	public static final ItemStack prismane = new ItemStack(mod_RotaryCraft.compacts, 1, 1);
	public static final ItemStack lonsda = new ItemStack(mod_RotaryCraft.compacts, 1, 2);

	public static final ItemStack steelblock = new ItemStack(mod_RotaryCraft.decoblock, 1, 0);
	public static final ItemStack anthrablock = new ItemStack(mod_RotaryCraft.decoblock, 1, 1);
	public static final ItemStack lonsblock = new ItemStack(mod_RotaryCraft.decoblock, 1, 2);

	private static ArrayList<ItemStack> modsteel = new ArrayList<ItemStack>();

	public static List getModSteels() {
		return modsteel;
	}

	public static void registerSteels() {
		 modsteel.addAll(OreDictionary.getOres("ingotSteel"));
		 modsteel.addAll(OreDictionary.getOres("ingotsteel"));
		 modsteel.addAll(OreDictionary.getOres("steelingot"));
		 modsteel.addAll(OreDictionary.getOres("steelIngot"));
		 modsteel.addAll(OreDictionary.getOres("SteelIngot"));
		 modsteel.addAll(OreDictionary.getOres("IngotSteel"));
		 modsteel.addAll(OreDictionary.getOres("steel"));
		 modsteel.addAll(OreDictionary.getOres("Steel"));
	}
}
