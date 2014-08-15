/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

import net.minecraft.util.StatCollector;

public class RotaryNames {

	private static final String[] engineNames = {
		"engine.dc", "engine.wind", "engine.steam", "engine.gas", "engine.ac", "engine.sport",
		"engine.hydro", "engine.micro", "engine.jet"
	};

	private static final String[] advGearItemNames = {
		"advgear.worm", "advgear.cvt", "advgear.coil", "advgear.hi"
	};

	private static final String[] hydraulicItemNames = {
		"hydraulic.pump", "hydraulic.turbine"
	};

	private static final String[] flywheelItemNames = {
		"flywheel.wood", "flywheel.stone", "flywheel.iron", "flywheel.gold"
	};

	private static final String[] shaftItemNames = {
		"shaft.wood", "shaft.stone", "shaft.steel", "shaft.diamond", "shaft.bedrock", "shaft.cross"
	};

	private static final String[] liquidNames = {
		"fluid.lubricant", "fluid.jet fuel", "fluid.rc ethanol", "fluid.liquid nitrogen"
	};

	//Never presented directly, not in .lang
	private static final String[] gearboxItemNames = {
		"Wooden 2:1 Gearbox", "Stone 2:1 Gearbox", "Steel 2:1 Gearbox", "Diamond 2:1 Gearbox", "Bedrock 2:1 Gearbox",
		"Wooden 4:1 Gearbox", "Stone 4:1 Gearbox", "Steel 4:1 Gearbox", "Diamond 4:1 Gearbox", "Bedrock 4:1 Gearbox",
		"Wooden 8:1 Gearbox", "Stone 8:1 Gearbox", "Steel 8:1 Gearbox", "Diamond 8:1 Gearbox", "Bedrock 8:1 Gearbox",
		"Wooden 16:1 Gearbox", "Stone 16:1 Gearbox", "Steel 16:1 Gearbox", "Diamond 16:1 Gearbox", "Bedrock 16:1 Gearbox",
	};

	//Not to be edited
	public static final String[] gearboxRatios = {
		"2:1", "4:1", "8:1", "16:1"
	};

	//Not to be edited
	public static final String[] gearUnitRatios = {
		"2x", "4x", "8x", "16x"
	};

	public static final String[] enginePartNames = {
		"crafting.impeller", "crafting.compressor", "crafting.turbine", "crafting.diffuser", "crafting.combustor", "crafting.cylinder",
		"crafting.radiator", "crafting.condenser", "crafting.goldcoil", "crafting.woodgear", "crafting.stonegear", "crafting.diamondgear",
		"crafting.bedrockgear", "crafting.paddle", "crafting.shaftcore", "crafting.ignition"
	};

	public static final String[] shaftPartNames = {
		"crafting.panel", "crafting.ingot", "crafting.shaft", "crafting.mount", "crafting.gear", "crafting.gear2",
		"crafting.gear4", "crafting.gear8", "crafting.gear16", "crafting.scrap", "crafting.ironscrap",
		"crafting.woodcore", "crafting.stonecore", "crafting.ironcore", "crafting.goldcore", "crafting.worm"
	};

	public static final String[] miscPartNames = {
		"crafting.barrel", "crafting.lens", "crafting.power", "crafting.heatcore", "crafting.lim", "crafting.prop", "crafting.hub",
		"crafting.mirror", "crafting.generator", "crafting.accel", "crafting.turretbase", "crafting.aiming", "crafting.compound",
		"crafting.bedcoil", "crafting.chain"
	};

	public static final String[] gearUnitNames = {
		"Wood 2x Gear Unit", "Wood 4x Gear Unit", "Wood 8x Gear Unit", "Wood 16x Gear Unit",
		"Stone 2x Gear Unit", "Stone 4x Gear Unit", "Stone 8x Gear Unit", "Stone 16x Gear Unit",
		"Diamond 2x Gear Unit", "Diamond 4x Gear Unit", "Diamond 8x Gear Unit", "Diamond 16x Gear Unit",
		"Bedrock 2x Gear Unit", "Bedrock 4x Gear Unit", "Bedrock 8x Gear Unit", "Bedrock 16x Gear Unit"
	};

	public static final String[] borerPartNames = {
		"crafting.drill", "crafting.pressure", "crafting.radar", "crafting.sonar", "crafting.pcb", "crafting.screen", "crafting.mixer", "crafting.saw", "crafting.bearing",
		"crafting.belt", "crafting.ballbearing", "crafting.brake", "crafting.coil",
		"crafting.stoneshaft", "crafting.diamondshaft", "crafting.bedrockshaft"
	};

	public static final String[] blockNames = {
		"block.steel", "block.anthra", "block.lonsda", "block.shield"
	};

	public static final String[] powderNames = {
		"misc.netherdust", "misc.tar", "misc.sludge", "misc.sawdust", "misc.beddust", "misc.salt", "misc.nh4" , "misc.agi",
		"misc.alum", "misc.flour", "misc.coaldust", "misc.dryice", "misc.redgolddust", "misc.compost"
	};

	public static final String[] interfaceNames = {
		"interface.slipperycomb", "interface.slipperypropolis"
	};

	public static final String[] extractNames = {
		"extract.coaldust", "extract.irondust", "extract.golddust", "extract.reddust",
		"extract.bluedust", "extract.diadust", "extract.greendust", "extract.quartzdust",

		"extract.coalslurry", "extract.ironslurry", "extract.goldslurry", "extract.redslurry",
		"extract.blueslurry", "extract.diaslurry", "extract.greenslurry", "extract.quartzslurry",

		"extract.coalsolu", "extract.ironsolu", "extract.goldsolu", "extract.redsolu",
		"extract.bluesolu", "extract.diasolu", "extract.greensolu", "extract.quartzsolu",

		"extract.coalflakes", "extract.ironflakes", "extract.goldflakes", "extract.redflakes",
		"extract.blueflakes", "extract.diaflakes", "extract.greenflakes", "extract.quartzflakes",

		"extract.silverflakes", "extract.tungstenflakes"
	};

	public static final String[] compactNames = {
		"misc.anthra", "misc.prisma", "misc.lonsda", "misc.bedingot", "misc.alingot", "misc.wingot", "misc.redgoldingot", "misc.agingot",
		"misc.coke"
	};

	public static final String[] pipeNames = {
		"machine.hose", "machine.pipe", "machine.fuelline", "machine.spiller", "machine.valve", "machine.bypass", "machine.separation"
	};

	private static String getName(String[] names, int i) {
		return StatCollector.translateToLocal(names[i]);
	}

	public static String getGearboxName(int i) {
		String mat = MaterialRegistry.matList[i%MaterialRegistry.matList.length].getName();
		String ratio = gearboxRatios[i/5];
		String name = MachineRegistry.GEARBOX.getName();
		return mat+" "+ratio+" "+name;
	}

	public static String getGearUnitName(int i) {
		MaterialRegistry[] mats = {MaterialRegistry.WOOD, MaterialRegistry.STONE, MaterialRegistry.DIAMOND, MaterialRegistry.BEDROCK};
		String mat = mats[i%4].getName();
		String ratio = gearUnitRatios[i/5];
		return mat+" "+ratio+" "+StatCollector.translateToLocal("crafting.gearunit");
	}

	public static String getName(String tag, boolean tech) {
		String sg = StatCollector.translateToLocal(tag);
		if (tech && DragonOptions.DEBUGMODE.getState())
			sg += " (Technical Block)";
		return sg;
	}

	public static String getBucketName(int i) {
		String liq = StatCollector.translateToLocal(liquidNames[i]);
		String item = StatCollector.translateToLocal("item.rcbucket");
		return liq+" "+item;
	}

	public static String getEngineName(int i) {
		return getName(engineNames, i);
	}

	public static String getAdvGearName(int i) {
		return getName(advGearItemNames, i);
	}

	public static String getHydraulicName(int i) {
		return getName(hydraulicItemNames, i);
	}

	public static String getShaftName(int i) {
		return getName(shaftItemNames, i);
	}

	public static String getFlywheelName(int i) {
		return getName(flywheelItemNames, i);
	}

	public static int getNumberShaftTypes() {
		return shaftItemNames.length;
	}

	public static int getNumberGearTypes() {
		return gearboxItemNames.length;
	}

	public static int getNumberAdvGearTypes() {
		return advGearItemNames.length;
	}

	public static int getNumberEngineTypes() {
		return engineNames.length;
	}

	public static int getNumberFlywheelTypes() {
		return flywheelItemNames.length;
	}

	public static int getNumberHydraulicTypes() {
		return hydraulicItemNames.length;
	}

	public static String getModExtractName(int dmg) {
		String base = ModOreList.oreList[dmg/4].displayName;
		switch(dmg%4) {
		case 0:
			return base+" Dust";
		case 1:
			return base+" Slurry";
		case 2:
			return base+" Solution";
		case 3:
			return base+" Flakes";
		default:
			return base;
		}
	}

	public static String getModIngotName(int dmg) {
		return ModOreList.oreList[dmg].displayName+" "+ModOreList.oreList[dmg].getTypeName();
	}
}