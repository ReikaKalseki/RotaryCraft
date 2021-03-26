/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import Reika.DragonAPI.Instantiable.PreferentialItemStack;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaOreHelper;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.GearboxTypes.GearPart;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

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
	public static final ItemStack silicondust 		= ItemRegistry.POWDERS.getStackOfMetadata(14);
	//public static final ItemStack mulch 			= ItemRegistry.POWDERS.getStackOfMetadata(15);
	public static final ItemStack cleansludge 		= ItemRegistry.POWDERS.getStackOfMetadata(16);

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
	public static final ItemStack springingot		= ItemRegistry.COMPACTS.getStackOfMetadata(9);
	public static final ItemStack silicon			= ItemRegistry.COMPACTS.getStackOfMetadata(10);
	public static final ItemStack silumin			= ItemRegistry.COMPACTS.getStackOfMetadata(11);
	public static final ItemStack springtungsten	= ItemRegistry.COMPACTS.getStackOfMetadata(12);

	public static final ItemStack basepanel 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(0);
	public static final ItemStack steelingot 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(1);

	public static final ItemStack hose 				= MachineRegistry.HOSE.getCraftedProduct();
	public static final ItemStack pipe 				= MachineRegistry.PIPE.getCraftedProduct();
	public static final ItemStack fuelline 			= MachineRegistry.FUELLINE.getCraftedProduct();
	public static final ItemStack bedpipe 			= MachineRegistry.BEDPIPE.getCraftedProduct();

	public static final ItemStack gearunit	 		= GearboxTypes.STEEL.getPart(GearPart.UNIT2);
	public static final ItemStack gearunit4	 		= GearboxTypes.STEEL.getPart(GearPart.UNIT4);
	public static final ItemStack gearunit8	 		= GearboxTypes.STEEL.getPart(GearPart.UNIT8);
	public static final ItemStack gearunit16 		= GearboxTypes.STEEL.getPart(GearPart.UNIT16);
	public static final ItemStack shaftitem 		= MaterialRegistry.STEEL.getShaftUnitItem();
	public static final ItemStack tungstenshaft 	= MaterialRegistry.TUNGSTEN.getShaftUnitItem();
	public static final ItemStack diamondshaft 		= MaterialRegistry.DIAMOND.getShaftUnitItem();
	public static final ItemStack bedrockshaft 		= MaterialRegistry.BEDROCK.getShaftUnitItem();
	public static final ItemStack stonerod	 		= MaterialRegistry.STONE.getShaftUnitItem();

	public static final ItemStack woodgear	 		= GearboxTypes.WOOD.getPart(GearPart.GEAR);
	public static final ItemStack stonegear	 		= GearboxTypes.STONE.getPart(GearPart.GEAR);
	public static final ItemStack steelgear 		= GearboxTypes.STEEL.getPart(GearPart.GEAR);
	public static final ItemStack tungstengear 		= GearboxTypes.TUNGSTEN.getPart(GearPart.GEAR);
	public static final ItemStack diamondgear	 	= GearboxTypes.DIAMOND.getPart(GearPart.GEAR);
	public static final ItemStack bedrockgear	 	= GearboxTypes.BEDROCK.getPart(GearPart.GEAR);

	public static final ItemStack bearing 			= GearboxTypes.STEEL.getPart(GearPart.BEARING);
	public static final ItemStack shaftcore 		= GearboxTypes.STEEL.getPart(GearPart.SHAFTCORE);
	public static final ItemStack tungstenshaftcore	= GearboxTypes.TUNGSTEN.getPart(GearPart.SHAFTCORE);
	public static final ItemStack diamondshaftcore 	= GearboxTypes.DIAMOND.getPart(GearPart.SHAFTCORE);

	public static final ItemStack mount 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(3);
	public static final ItemStack scrap 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(9);
	public static final ItemStack ironscrap 		= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(10);
	public static final ItemStack wormgear 			= ItemRegistry.SHAFTCRAFT.getStackOfMetadata(15);

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
	public static final ItemStack bedrockdrill 		= ItemRegistry.MISCCRAFT.getStackOfMetadata(15);

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
	public static final ItemStack igniter 			= ItemRegistry.ENGINECRAFT.getStackOfMetadata(15);
	public static final ItemStack compoundcompress	= ItemRegistry.ENGINECRAFT.getStackOfMetadata(17);
	public static final ItemStack aluminumcylinder	= ItemRegistry.ENGINECRAFT.getStackOfMetadata(18);
	public static final ItemStack highcombustor		= ItemRegistry.ENGINECRAFT.getStackOfMetadata(19);

	public static final ItemStack drill 			= ItemRegistry.BORECRAFT.getStackOfMetadata(0);
	public static final ItemStack presshead 		= ItemRegistry.BORECRAFT.getStackOfMetadata(1);
	public static final ItemStack radar 			= ItemRegistry.BORECRAFT.getStackOfMetadata(2);
	public static final ItemStack sonar 			= ItemRegistry.BORECRAFT.getStackOfMetadata(3);
	public static final ItemStack pcb 				= ItemRegistry.BORECRAFT.getStackOfMetadata(4);
	public static final ItemStack screen 			= ItemRegistry.BORECRAFT.getStackOfMetadata(5);
	public static final ItemStack mixer 			= ItemRegistry.BORECRAFT.getStackOfMetadata(6);
	public static final ItemStack saw 				= ItemRegistry.BORECRAFT.getStackOfMetadata(7);
	public static final ItemStack belt 				= ItemRegistry.BORECRAFT.getStackOfMetadata(9);
	public static final ItemStack ballbearing 		= ItemRegistry.BORECRAFT.getStackOfMetadata(10);
	public static final ItemStack brake 			= ItemRegistry.BORECRAFT.getStackOfMetadata(11);
	public static final ItemStack tenscoil 			= ItemRegistry.BORECRAFT.getStackOfMetadata(12);

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
	public static final ItemStack bedingotblock 	= BlockRegistry.DECO.getStackOfMetadata(4);
	public static final ItemStack cokeblock 		= BlockRegistry.DECO.getStackOfMetadata(5);

	public static final ItemStack slipperyComb 		= ItemRegistry.MODINTERFACE.getStackOfMetadata(0);
	public static final ItemStack slipperyPropolis 	= ItemRegistry.MODINTERFACE.getStackOfMetadata(1);
	public static final ItemStack gearCast 			= ItemRegistry.MODINTERFACE.getStackOfMetadata(2);
	public static final ItemStack panelCast 		= ItemRegistry.MODINTERFACE.getStackOfMetadata(3);
	public static final ItemStack shaftCast 		= ItemRegistry.MODINTERFACE.getStackOfMetadata(4);
	public static final ItemStack propCast 			= ItemRegistry.MODINTERFACE.getStackOfMetadata(5);
	public static final ItemStack drillCast 		= ItemRegistry.MODINTERFACE.getStackOfMetadata(6);

	public static final ItemStack canolaSeeds		= ItemRegistry.CANOLA.getStackOfMetadata(0);
	public static final ItemStack denseCanolaSeeds	= ItemRegistry.CANOLA.getStackOfMetadata(1);
	public static final ItemStack canolaHusks		= ItemRegistry.CANOLA.getStackOfMetadata(2);

	private static ArrayList<ItemStack> modsteel = new ArrayList<ItemStack>();

	private static final Item mod = ItemRegistry.MODINGOTS.getItemInstance();

	public static final PreferentialItemStack electric = new PreferentialItemStack(Items.gold_ingot, "ingotElectrum");
	public static final PreferentialItemStack conductive = new PreferentialItemStack(Items.gold_ingot, "ingotCopper").blockItem(mod);
	public static final PreferentialItemStack conductive2 = new PreferentialItemStack(ItemStacks.steelingot, "ingotCopper").blockItem(mod);
	public static final PreferentialItemStack heavy = new PreferentialItemStack(Items.gold_ingot, "ingotLead").blockItem(mod);
	public static final PreferentialItemStack reflective = new PreferentialItemStack(Items.iron_ingot, "ingotSilver").blockItem(mod);

	public static final PreferentialItemStack enderium = new PreferentialItemStack(ItemStacks.bedingot, "ingotEnderium");
	public static final PreferentialItemStack electrum = new PreferentialItemStack(ItemStacks.redgoldingot, "ingotElectrum");

	public static List<ItemStack> getModSteels() {
		return Collections.unmodifiableList(modsteel);
	}

	public static void registerSteels() {
		modsteel.addAll(OreDictionary.getOres("ingotSteel"));
		modsteel.addAll(OreDictionary.getOres("steelIngot"));
	}

	public static ItemStack getModOreIngot(ModOreList ore) {
		return ItemRegistry.MODINGOTS.getStackOfMetadata(ore.ordinal());
	}

	public static ItemStack getDust(ReikaOreHelper ore) {
		return ItemRegistry.EXTRACTS.getStackOfMetadata(+ore.ordinal());
	}

	public static ItemStack getSlurry(ReikaOreHelper ore) {
		return ItemRegistry.EXTRACTS.getStackOfMetadata(ReikaOreHelper.oreList.length+ore.ordinal());
	}

	public static ItemStack getSolution(ReikaOreHelper ore) {
		return ItemRegistry.EXTRACTS.getStackOfMetadata(ReikaOreHelper.oreList.length*2+ore.ordinal());
	}

	public static ItemStack getFlake(ReikaOreHelper ore) {
		return ItemRegistry.EXTRACTS.getStackOfMetadata(ReikaOreHelper.oreList.length*3+ore.ordinal());
	}

	/** Does NOT include multiplication yields! */
	public static ItemStack getSmeltedProduct(ReikaOreHelper ore) {
		switch (ore) {
			case COAL:
				return new ItemStack(Items.coal);
			case IRON:
				return new ItemStack(Items.iron_ingot);
			case GOLD:
				return new ItemStack(Items.gold_ingot);
			case REDSTONE:
				return new ItemStack(Items.redstone);
			case LAPIS:
				return ReikaItemHelper.lapisDye.copy();
			case DIAMOND:
				return new ItemStack(Items.diamond);
			case EMERALD:
				return new ItemStack(Items.emerald);
			case QUARTZ:
				return new ItemStack(Items.quartz);
		}
		return null;
	}

	/** In nuggets. *//*
	public static int getScrapValue(ItemStack is) {
		return is.stackTagCompound != null ? is.stackTagCompound.getInteger("value") : 1;
	}

	/** In nuggets. *//*
	public static void setScrapValue(ItemStack is, int value) {
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setInteger("value", value);
	}

	/** In nuggets. *//*
	public static ItemStack getScrap(int value) {
		ItemStack is = scrap.copy();
		setScrapValue(is, value);
		return is;
	}
	 */
}
