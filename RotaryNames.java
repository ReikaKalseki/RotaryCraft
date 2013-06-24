/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class RotaryNames {

	public static final String[] engineNames = {
		"DC Electric Engine", "Wind Turbine", "Steam Engine", "Gasoline Engine", "AC Electric Engine", "Performance Engine",
		"Hydrokinetic Engine", "Microturbine", "Gas Turbine"
	};

	public static final String[] gearboxNames = {
		"2:1 Gearbox", "2:1 Gearbox", "2:1 Gearbox", "2:1 Gearbox", "4:1 Gearbox", "4:1 Gearbox",
		"4:1 Gearbox", "4:1 Gearbox", "8:1 Gearbox", "8:1 Gearbox", "8:1 Gearbox", "8:1 Gearbox",
		"16:1 Gearbox",	"16:1 Gearbox", "16:1 Gearbox", "16:1 Gearbox"
	};

	public static final String[] advGearNames = {
		"Worm Gear", "Worm Gear", "Worm Gear", "Worm Gear", "CVT Unit", "CVT Unit",	"CVT Unit", "CVT Unit",
		"Industrial Coil",  "Industrial Coil", "Industrial Coil", "Industrial Coil"
	};

	public static final String[] advGearItemNames = {
		"Worm Gear", "CVT Unit", "Industrial Coil"
	};

	public static final String[] flywheelNames = {
		"Wood Flywheel", "Wood Flywheel", "Wood Flywheel", "Wood Flywheel", "Stone Flywheel", "Stone Flywheel",
		"Stone Flywheel", "Stone Flywheel", "Iron Flywheel", "Iron Flywheel", "Iron Flywheel", "Iron Flywheel",
		"Gold Flywheel", "Gold Flywheel", "Gold Flywheel", "Gold Flywheel"
	};

	public static final String[] flywheelItemNames = {
		"Wood Flywheel", "Stone Flywheel", "Iron Flywheel", "Gold Flywheel"
	};

	public static final String[] shaftNames = {
		"Shaft", "Shaft", "Shaft", "Shaft", "Shaft", "Shaft", "Shaft Cross"
	};

	public static final String[] shaftItemNames = {
		"Wood Shaft", "Stone Shaft", "Steel Shaft", "Diamond Shaft", "Bedrock Shaft", "Shaft Cross"
	};

	public static final String[] bucketNames = {
		"Lubricant Bucket", "Jet Fuel Bucket", "Ethanol Bucket"
	};

	public static final String[] gearboxItemNames = {
		"Wooden 2:1 Gearbox", "Stone 2:1 Gearbox", "Steel 2:1 Gearbox", "Diamond 2:1 Gearbox", "Bedrock 2:1 Gearbox",
		"Wooden 4:1 Gearbox", "Stone 4:1 Gearbox", "Steel 4:1 Gearbox", "Diamond 4:1 Gearbox", "Bedrock 4:1 Gearbox",
		"Wooden 8:1 Gearbox", "Stone 8:1 Gearbox", "Steel 8:1 Gearbox", "Diamond 8:1 Gearbox", "Bedrock 8:1 Gearbox",
		"Wooden 16:1 Gearbox", "Stone 16:1 Gearbox", "Steel 16:1 Gearbox", "Diamond 16:1 Gearbox", "Bedrock 16:1 Gearbox",
	};

	public static final String[] enginePartNames = {
		"Impeller", "Compressor", "Turbine", "Diffuser", "Combustor", "Cylinder", "Radiator", "Condenser", "Gold Coil", "Wood Gear",
		"Stone Gear", "Diamond Gear", "Bedrock Gear", "Paddle Panel", "Shaft Core", "Ignition Unit"
	};

	public static final String[] shaftPartNames = {
		"Base Panel", "HSLA Steel Ingot", "Shaft Unit", "Mount", "HSLA Steel Gear", "2x Gear Unit",
		"4x Gear Unit", "8x Gear Unit", "16x Gear Unit", "HSLA Steel Scrap", "Iron Scrap",
		"Wood Flywheel Core", "Stone Flywheel Core", "Iron Flywheel Core", "Gold Flywheel Core", "Worm Gear"
	};

	public static final String[] heatPartNames = {
		"Heat Ray Barrel", "Lens", "Power Module", "Heat Ray Core", "Linear Induction Motor", "Propeller Blade", "Hub", "Mirror",
		"Generator", "Railgun Accelerator", "Turret Base", "Turret Aiming Unit"
	};

	public static final String[] gearUnitNames = {
		"Wood 2x Gear Unit", "Wood 4x Gear Unit", "Wood 8x Gear Unit", "Wood 16x Gear Unit",
		"Stone 2x Gear Unit", "Stone 4x Gear Unit", "Stone 8x Gear Unit", "Stone 16x Gear Unit",
		"Diamond 2x Gear Unit", "Diamond 4x Gear Unit", "Diamond 8x Gear Unit", "Diamond 16x Gear Unit",
		"Bedrock 2x Gear Unit", "Bedrock 4x Gear Unit", "Bedrock 8x Gear Unit", "Bedrock 16x Gear Unit"
	};

	public static final String[] borerPartNames = {
		"Drill", "Pressure Head", "Radar Unit", "Sonar Unit", "Circuit Board", "Screen", "Mixer", "Saw", "Shaft Bearing", "Belt",
		"Ball Bearing", "Brake Disc", "Tension Coil", "Stone Rod", "Diamond Shaft Unit", "Bedrock Shaft Unit"
	};

	public static final String[] blockNames = {
		"Steel Block", "Anthracite Block", "Lonsdaleite Block"
	};

	public static final String[] powderNames = {
		"Netherrack Dust", "Tar Sand", "Sludge", "Sawdust", "Bedrock Dust", "Salt", "Ammonium Nitrate" , "Silver Iodide",
		"Aluminum Powder"
	};

	public static final String[] extractNames = {
		"Coal Ore Dust", "Iron Ore Dust", "Gold Ore Dust", "Redstone Ore Dust",
		"Lapis Ore Dust", "Diamond Ore Dust", "Emerald Ore Dust", "Nether Quartz Dust",
		"Coal Ore Slurry", "Iron Ore Slurry", "Gold Ore Slurry", "Redstone Ore Slurry",
		"Lapis Ore Slurry", "Diamond Ore Slurry", "Emerald Ore Slurry", "Nether Quartz Slurry",
		"Coal Solution", "Iron Solution", "Gold Solution", "Redstone Solution",
		"Lapis Lazuli Solution", "Diamond Solution", "Emerald Solution", "Nether Quartz Solution",
		"Coal Flakes", "Iron Flakes", "Gold Flakes", "Redstone Flakes",
		"Lapis Lazuli Flakes", "Diamond Flakes", "Emerald Flakes", "Nether Quartz Flakes", "Silver Flakes"
	};

	public static final String[] compactNames = {
		"Anthracite", "Prismane", "Lonsdaleite", "Bedrock Ingot", "", "", "", "Silver Ingot"
	};

	public static final String[] spawnerNames = {
		"Zombie Spawner", "Spider Spawner", "Cave Spider Spawner", "Skeleton Spawner", "Silverfish Spawner", "Blaze Spawner"
	};

	public static final String[] pipeNames = {
		"Lubricant Hose", "Liquid Pipe", "Fuel Line", "Liquid Spiller"
	};

	public static final String[] modOreNames = {
		"Lead", "Copper", "Silver", "Titanium", "Tungsten", "Galena", "Tin", "Platinum", "Nickel", "Iridium", "Aluminum", "Sulfur",
		"Cobalt", "Uranium", "Saltpeter", "Bauxite", "Ruby", "Sapphire", "Nikolite", "Cinnabar", "Green Sapphire", "Amethyst",
		"Black Diamond", "Sitrine", "Cubic Zirconia", "Fuschia", "Jet" ,"Olivine", "Opal", "Smoky Quartz", "Sun", "Topaz", "Turquoise",
		"White Alabaster", "Ardite", "Amber", "Pyrite", "Sphalerite", "Sodalite", "Sheldonite", "Certus Quartz"
	};

	public static void addNames() {

		for (int i = 0; i < blockNames.length; i++) {
			ItemStack machineStack = new ItemStack(RotaryCraft.decoblock, 1, i);
			LanguageRegistry.addName(machineStack, blockNames[machineStack.getItemDamage()]);
		}

		LanguageRegistry.addName(RotaryCraft.bedrockslice, "Bedrock Slice (Technical Block)");

		LanguageRegistry.addName(RotaryCraft.blastglass, "Blast Glass Pane");
		LanguageRegistry.addName(RotaryCraft.obsidianglass, "Blast Glass");
		LanguageRegistry.addName(RotaryCraft.gravlog, "Gravity Log Block (Technical Block)");
		LanguageRegistry.addName(RotaryCraft.gravleaves, "Gravity Leaf Block (Technical Block)");

		LanguageRegistry.addName(RotaryCraft.canola, "Canola Plant (Technical Block)");

		LanguageRegistry.addName(RotaryCraft.miningpipe, "Mining Pipe (Technical Block)");
		LanguageRegistry.addName(RotaryCraft.lightblock, "Light Block (Technical Block)");
		LanguageRegistry.addName(RotaryCraft.beamblock, "Beam Block (Technical Block)");
		LanguageRegistry.addName(RotaryCraft.lightbridge, "Bridge Block (Technical Block)");

		for (int i = 0; i < shaftPartNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.shaftcraft.itemID, 1, i), shaftPartNames[i]);
		}
		for (int i = 0; i < heatPartNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.heatcraft, 1, i), heatPartNames[i]);
		}
		for (int i = 0; i < enginePartNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.enginecraft, 1, i), enginePartNames[i]);
		}
		for (int i = 0; i < borerPartNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.borecraft, 1, i), borerPartNames[i]);
		}
		for (int i = 0; i < extractNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.extracts, 1, i), extractNames[i]);
		}
		for (int i = 0; i < modOreNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.modextracts, 1, i), modOreNames[i]);
		}
		for (int i = 0; i < compactNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.compacts, 1, i), compactNames[i]);
		}
		for (int i = 0; i < engineNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.engineitems, 1, i), engineNames[i]);
		}
		for (int i = 0; i < powderNames.length; i++) {
			ItemStack jetfuelstack = new ItemStack(RotaryCraft.powders, 1, i);
			LanguageRegistry.addName(jetfuelstack, powderNames[jetfuelstack.getItemDamage()]);
		}
		for (int i = 0; i < spawnerNames.length; i++) {
			ItemStack spawnerstack = new ItemStack(RotaryCraft.spawner, 1, i);
			LanguageRegistry.addName(spawnerstack, spawnerNames[spawnerstack.getItemDamage()]);
		}
		for (int i = 0; i < pipeNames.length; i++) {
			ItemStack pipestack = new ItemStack(RotaryCraft.pipeplacer, 1, i);
			LanguageRegistry.addName(pipestack, pipeNames[pipestack.getItemDamage()]);
		}
		for (int i = 0; i < shaftItemNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.shaftitems, 1, i), shaftItemNames[i]);
		}
		for (int i = 0; i < gearboxItemNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.gbxitems, 1, i), gearboxItemNames[i]);
		}
		for (int i = 0; i < flywheelItemNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.flywheelitems, 1, i), flywheelItemNames[i]);
		}
		for (int i = 0; i < advGearItemNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.advgearitems, 1, i), advGearItemNames[i]);
		}
		for (int i = 0; i < gearUnitNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.gearunits, 1, i), gearUnitNames[i]);
		}

		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			LanguageRegistry.addName(new ItemStack(RotaryCraft.machineplacer, 1, i), MachineRegistry.machineList[i].getName());
		}

		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			LanguageRegistry.addName(BlockRegistry.blockList[i].getBlockVariable(), "TECHNICAL BLOCK "+BlockRegistry.blockList[i].getName());
		}

		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			ItemRegistry r = ItemRegistry.itemList[i];
			if (r.hasMultiValuedName()) {
				for (int j = 0; j < r.getNumberMetadatas(); j++) {
					ItemStack is = new ItemStack(r.getID(), 1, j);
					LanguageRegistry.addName(is, r.getMultiValuedName(j));
				}
			}
			else
				LanguageRegistry.addName(r.getItemInstance(), r.getBasicName());
		}
	}
}
