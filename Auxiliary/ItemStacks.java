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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Instantiable.PreferentialItemStack;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;

public final class ItemStacks {

	public static final ItemStack netherrackdust = new ItemStack(RotaryCraft.powders.itemID, 1, 0);
	public static final ItemStack tar = new ItemStack(RotaryCraft.powders.itemID, 1, 1);
	public static final ItemStack sludge = new ItemStack(RotaryCraft.powders.itemID, 1, 2);
	public static final ItemStack sawdust = new ItemStack(RotaryCraft.powders.itemID, 1, 3);
	public static final ItemStack bedrockdust = new ItemStack(RotaryCraft.powders.itemID, 1, 4);
	public static final ItemStack salt = new ItemStack(RotaryCraft.powders.itemID, 1, 5);
	public static final ItemStack nitrate = new ItemStack(RotaryCraft.powders.itemID, 1, 6);
	public static final ItemStack silveriodide = new ItemStack(RotaryCraft.powders.itemID, 1, 7);
	public static final ItemStack aluminumpowder = new ItemStack(RotaryCraft.powders.itemID, 1, 8);
	public static final ItemStack flour = new ItemStack(RotaryCraft.powders.itemID, 1, 9);
	public static final ItemStack coaldust = new ItemStack(RotaryCraft.powders.itemID, 1, 10);
	public static final ItemStack dryice = new ItemStack(RotaryCraft.powders.itemID, 1, 11);
	public static final ItemStack redgolddust = new ItemStack(RotaryCraft.powders.itemID, 1, 12);
	public static final ItemStack compost = new ItemStack(RotaryCraft.powders.itemID, 1, 13);

	public static final ItemStack lubebucket = ItemRegistry.BUCKET.getStackOfMetadata(0);
	public static final ItemStack fuelbucket = ItemRegistry.BUCKET.getStackOfMetadata(1);
	public static final ItemStack ethanolbucket = ItemRegistry.BUCKET.getStackOfMetadata(2);
	public static final ItemStack nitrogenbucket = ItemRegistry.BUCKET.getStackOfMetadata(3);

	public static final ItemStack bedingot = new ItemStack(RotaryCraft.compacts.itemID, 1, 3);
	public static final ItemStack aluminumingot = new ItemStack(RotaryCraft.compacts.itemID, 1, 4);
	public static final ItemStack tungsteningot = new ItemStack(RotaryCraft.compacts.itemID, 1, 5);
	public static final ItemStack redgoldingot = new ItemStack(RotaryCraft.compacts.itemID, 1, 6);
	public static final ItemStack silveringot = new ItemStack(RotaryCraft.compacts.itemID, 1, 7);
	public static final ItemStack coke = new ItemStack(RotaryCraft.compacts.itemID, 1, 8);

	public static final ItemStack basepanel = new ItemStack(RotaryCraft.shaftcraft, 1, 0);
	public static final ItemStack steelingot = new ItemStack(RotaryCraft.shaftcraft, 1, 1);

	public static final ItemStack hose = MachineRegistry.HOSE.getCraftedProduct();
	public static final ItemStack pipe = MachineRegistry.PIPE.getCraftedProduct();
	public static final ItemStack fuelline = MachineRegistry.FUELLINE.getCraftedProduct();

	public static final ItemStack shaftitem = new ItemStack(RotaryCraft.shaftcraft, 1, 2);
	public static final ItemStack mount = new ItemStack(RotaryCraft.shaftcraft, 1, 3);
	public static final ItemStack steelgear = new ItemStack(RotaryCraft.shaftcraft, 1, 4);
	public static final ItemStack gearunit = new ItemStack(RotaryCraft.shaftcraft, 1, 5);
	public static final ItemStack gearunit4 = new ItemStack(RotaryCraft.shaftcraft, 1, 6);
	public static final ItemStack gearunit8 = new ItemStack(RotaryCraft.shaftcraft, 1, 7);
	public static final ItemStack gearunit16 = new ItemStack(RotaryCraft.shaftcraft, 1, 8);
	public static final ItemStack scrap = new ItemStack(RotaryCraft.shaftcraft, 1, 9);
	public static final ItemStack ironscrap = new ItemStack(RotaryCraft.shaftcraft, 1, 10);
	public static final ItemStack flywheelcore = new ItemStack(RotaryCraft.shaftcraft, 1, 11);
	public static final ItemStack flywheelcore2 = new ItemStack(RotaryCraft.shaftcraft, 1, 12);
	public static final ItemStack flywheelcore3 = new ItemStack(RotaryCraft.shaftcraft, 1, 13);
	public static final ItemStack flywheelcore4 = new ItemStack(RotaryCraft.shaftcraft, 1, 14);
	public static final ItemStack wormgear = new ItemStack(RotaryCraft.shaftcraft, 1, 15);

	public static final ItemStack wood2x = new ItemStack(RotaryCraft.gearunits, 1, 0);
	public static final ItemStack stone2x = new ItemStack(RotaryCraft.gearunits, 1, 4);
	public static final ItemStack diamond2x = new ItemStack(RotaryCraft.gearunits, 1, 8);
	public static final ItemStack bedrock2x = new ItemStack(RotaryCraft.gearunits, 1, 12);
	public static final ItemStack wood4x = new ItemStack(RotaryCraft.gearunits, 1, 1);
	public static final ItemStack stone4x = new ItemStack(RotaryCraft.gearunits, 1, 5);
	public static final ItemStack diamond4x = new ItemStack(RotaryCraft.gearunits, 1, 9);
	public static final ItemStack bedrock4x = new ItemStack(RotaryCraft.gearunits, 1, 13);
	public static final ItemStack wood8x = new ItemStack(RotaryCraft.gearunits, 1, 2);
	public static final ItemStack stone8x = new ItemStack(RotaryCraft.gearunits, 1, 6);
	public static final ItemStack diamond8x = new ItemStack(RotaryCraft.gearunits, 1, 10);
	public static final ItemStack bedrock8x = new ItemStack(RotaryCraft.gearunits, 1, 14);
	public static final ItemStack wood16x = new ItemStack(RotaryCraft.gearunits, 1, 3);
	public static final ItemStack stone16x = new ItemStack(RotaryCraft.gearunits, 1, 7);
	public static final ItemStack diamond16x = new ItemStack(RotaryCraft.gearunits, 1, 11);
	public static final ItemStack bedrock16x = new ItemStack(RotaryCraft.gearunits, 1, 15);

	public static final ItemStack barrel = new ItemStack(RotaryCraft.misccraft, 1, 0);
	public static final ItemStack lens = new ItemStack(RotaryCraft.misccraft, 1, 1);
	public static final ItemStack power = new ItemStack(RotaryCraft.misccraft, 1, 2);
	public static final ItemStack bulb = new ItemStack(RotaryCraft.misccraft, 1, 3);
	public static final ItemStack lim = new ItemStack(RotaryCraft.misccraft, 1, 4);
	public static final ItemStack prop = new ItemStack(RotaryCraft.misccraft, 1, 5);
	public static final ItemStack hub = new ItemStack(RotaryCraft.misccraft, 1, 6);
	public static final ItemStack mirror = new ItemStack(RotaryCraft.misccraft, 1, 7);
	public static final ItemStack generator = new ItemStack(RotaryCraft.misccraft, 1, 8);
	public static final ItemStack railhead = new ItemStack(RotaryCraft.misccraft, 1, 9);
	public static final ItemStack railbase = new ItemStack(RotaryCraft.misccraft, 1, 10);
	public static final ItemStack railaim = new ItemStack(RotaryCraft.misccraft, 1, 11);
	public static final ItemStack compoundturb = new ItemStack(RotaryCraft.misccraft, 1, 12);
	public static final ItemStack bedrockcoil = new ItemStack(RotaryCraft.misccraft, 1, 13);
	public static final ItemStack chain = new ItemStack(RotaryCraft.misccraft, 1, 14);

	public static final ItemStack impeller = new ItemStack(RotaryCraft.enginecraft, 1, 0);
	public static final ItemStack compressor = new ItemStack(RotaryCraft.enginecraft, 1, 1);
	public static final ItemStack turbine = new ItemStack(RotaryCraft.enginecraft, 1, 2);
	public static final ItemStack diffuser = new ItemStack(RotaryCraft.enginecraft, 1, 3);
	public static final ItemStack combustor = new ItemStack(RotaryCraft.enginecraft, 1, 4);
	public static final ItemStack cylinder = new ItemStack(RotaryCraft.enginecraft, 1, 5);
	public static final ItemStack radiator = new ItemStack(RotaryCraft.enginecraft, 1, 6);
	public static final ItemStack condenser = new ItemStack(RotaryCraft.enginecraft, 1, 7);
	public static final ItemStack goldcoil = new ItemStack(RotaryCraft.enginecraft, 1, 8);
	public static final ItemStack waterplate = new ItemStack(RotaryCraft.enginecraft, 1, 13);
	public static final ItemStack shaftcore = new ItemStack(RotaryCraft.enginecraft, 1, 14);
	public static final ItemStack igniter = new ItemStack(RotaryCraft.enginecraft, 1, 15);

	public static final ItemStack woodgear = new ItemStack(RotaryCraft.enginecraft, 1, 9);
	public static final ItemStack stonegear = new ItemStack(RotaryCraft.enginecraft, 1, 10);
	public static final ItemStack diamondgear = new ItemStack(RotaryCraft.enginecraft, 1, 11);
	public static final ItemStack bedrockgear = new ItemStack(RotaryCraft.enginecraft, 1, 12);

	public static final ItemStack drill = new ItemStack(RotaryCraft.borecraft, 1, 0);
	public static final ItemStack presshead = new ItemStack(RotaryCraft.borecraft, 1, 1);
	public static final ItemStack radar = new ItemStack(RotaryCraft.borecraft, 1, 2);
	public static final ItemStack sonar = new ItemStack(RotaryCraft.borecraft, 1, 3);
	public static final ItemStack pcb = new ItemStack(RotaryCraft.borecraft, 1, 4);
	public static final ItemStack screen = new ItemStack(RotaryCraft.borecraft, 1, 5);
	public static final ItemStack mixer = new ItemStack(RotaryCraft.borecraft, 1, 6);
	public static final ItemStack saw = new ItemStack(RotaryCraft.borecraft, 1, 7);
	public static final ItemStack bearing = new ItemStack(RotaryCraft.borecraft, 1, 8);
	public static final ItemStack belt = new ItemStack(RotaryCraft.borecraft, 1, 9);
	public static final ItemStack ballbearing = new ItemStack(RotaryCraft.borecraft, 1, 10);
	public static final ItemStack brake = new ItemStack(RotaryCraft.borecraft, 1, 11);
	public static final ItemStack tenscoil = new ItemStack(RotaryCraft.borecraft, 1, 12);

	public static final ItemStack stonerod = new ItemStack(RotaryCraft.borecraft, 1, 13);
	public static final ItemStack diamondshaft = new ItemStack(RotaryCraft.borecraft, 1, 14);
	public static final ItemStack bedrockshaft = new ItemStack(RotaryCraft.borecraft, 1, 15);

	public static final ItemStack coaloredust = new ItemStack(RotaryCraft.extracts, 1, 0);
	public static final ItemStack ironoredust = new ItemStack(RotaryCraft.extracts, 1, 1);
	public static final ItemStack goldoredust = new ItemStack(RotaryCraft.extracts, 1, 2);
	public static final ItemStack redoredust = new ItemStack(RotaryCraft.extracts, 1, 3);
	public static final ItemStack lapisoredust = new ItemStack(RotaryCraft.extracts, 1, 4);
	public static final ItemStack diamondoredust = new ItemStack(RotaryCraft.extracts, 1, 5);
	public static final ItemStack emeraldoredust = new ItemStack(RotaryCraft.extracts, 1, 6);
	public static final ItemStack netherquartzdust = new ItemStack(RotaryCraft.extracts, 1, 7);

	public static final ItemStack coaloreslurry = new ItemStack(RotaryCraft.extracts, 1, 8);
	public static final ItemStack ironoreslurry = new ItemStack(RotaryCraft.extracts, 1, 9);
	public static final ItemStack goldoreslurry = new ItemStack(RotaryCraft.extracts, 1, 10);
	public static final ItemStack redoreslurry = new ItemStack(RotaryCraft.extracts, 1, 11);
	public static final ItemStack lapisoreslurry = new ItemStack(RotaryCraft.extracts, 1, 12);
	public static final ItemStack diamondoreslurry = new ItemStack(RotaryCraft.extracts, 1, 13);
	public static final ItemStack emeraldoreslurry = new ItemStack(RotaryCraft.extracts, 1, 14);
	public static final ItemStack netherquartzslurry = new ItemStack(RotaryCraft.extracts, 1, 15);

	public static final ItemStack coaloresolution = new ItemStack(RotaryCraft.extracts, 1, 16);
	public static final ItemStack ironoresolution = new ItemStack(RotaryCraft.extracts, 1, 17);
	public static final ItemStack goldoresolution = new ItemStack(RotaryCraft.extracts, 1, 18);
	public static final ItemStack redoresolution = new ItemStack(RotaryCraft.extracts, 1, 19);
	public static final ItemStack lapisoresolution = new ItemStack(RotaryCraft.extracts, 1, 20);
	public static final ItemStack diamondoresolution = new ItemStack(RotaryCraft.extracts, 1, 21);
	public static final ItemStack emeraldoresolution = new ItemStack(RotaryCraft.extracts, 1, 22);
	public static final ItemStack netherquartzsolution = new ItemStack(RotaryCraft.extracts, 1, 23);

	public static final ItemStack coaloreflakes = new ItemStack(RotaryCraft.extracts, 1, 24);
	public static final ItemStack ironoreflakes = new ItemStack(RotaryCraft.extracts, 1, 25);
	public static final ItemStack goldoreflakes = new ItemStack(RotaryCraft.extracts, 1, 26);
	public static final ItemStack redoreflakes = new ItemStack(RotaryCraft.extracts, 1, 27);
	public static final ItemStack lapisoreflakes = new ItemStack(RotaryCraft.extracts, 1, 28);
	public static final ItemStack diamondoreflakes = new ItemStack(RotaryCraft.extracts, 1, 29);
	public static final ItemStack emeraldoreflakes = new ItemStack(RotaryCraft.extracts, 1, 30);
	public static final ItemStack netherquartzflakes = new ItemStack(RotaryCraft.extracts, 1, 31);
	public static final ItemStack silverflakes = new ItemStack(RotaryCraft.extracts, 1, 32);
	public static final ItemStack tungstenflakes = new ItemStack(RotaryCraft.extracts, 1, 33);

	public static final ItemStack anthracite = new ItemStack(RotaryCraft.compacts, 1, 0);
	public static final ItemStack prismane = new ItemStack(RotaryCraft.compacts, 1, 1);
	public static final ItemStack lonsda = new ItemStack(RotaryCraft.compacts, 1, 2);

	public static final ItemStack steelblock = new ItemStack(RotaryCraft.decoblock, 1, 0);
	public static final ItemStack anthrablock = new ItemStack(RotaryCraft.decoblock, 1, 1);
	public static final ItemStack lonsblock = new ItemStack(RotaryCraft.decoblock, 1, 2);
	public static final ItemStack shieldblock = new ItemStack(RotaryCraft.decoblock, 1, 3);

	public static final ItemStack slipperyComb = new ItemStack(RotaryCraft.modinterface, 1, 0);
	public static final ItemStack slipperyPropolis = new ItemStack(RotaryCraft.modinterface, 1, 1);

	private static ArrayList<ItemStack> modsteel = new ArrayList<ItemStack>();

	public static final PreferentialItemStack electric = new PreferentialItemStack(new ItemStack(Item.ingotGold), "ingotElectrum");
	public static final PreferentialItemStack conductive = new PreferentialItemStack(new ItemStack(Item.ingotGold), "ingotCopper");
	public static final PreferentialItemStack conductiveCheap = new PreferentialItemStack(ItemStacks.steelingot, "ingotCopper");
	public static final PreferentialItemStack heavy = new PreferentialItemStack(new ItemStack(Item.ingotGold), "ingotLead");
	public static final PreferentialItemStack reflective = new PreferentialItemStack(new ItemStack(Item.ingotIron), "ingotSilver");

	public static List getModSteels() {
		return modsteel;
	}

	public static void registerSteels() {
		modsteel.addAll(OreDictionary.getOres("ingotSteel"));
		modsteel.addAll(OreDictionary.getOres("steelIngot"));
	}

	public static ItemStack getModOreIngot(ModOreList ore) {
		return new ItemStack(RotaryCraft.modingots.itemID, 1, ore.ordinal());
	}
}
