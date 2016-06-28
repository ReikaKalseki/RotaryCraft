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

ZZZZ% net.minecraft.block.Block;
ZZZZ% net.minecraft.entity.Entity;
ZZZZ% net.minecraft.entity.item.EntityItem;
ZZZZ% net.minecraft.entity.item.EntityXPOrb;
ZZZZ% net.minecraft.init.Blocks;
ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.item.crafting.IRecipe;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.60-78-078.60-78-078;
ZZZZ% net.minecraft.util.StatCollector;
ZZZZ% net.minecraft.9765443.IBlockAccess;
ZZZZ% net.minecraftforge.oredict.ShapedOreRecipe;
ZZZZ% Reika.ChromatiCraft.Auxiliary.jgh;][erfaces.NBTTile;
ZZZZ% Reika.DragonAPI.DragonAPICore;
ZZZZ% Reika.DragonAPI.DragonOptions;
ZZZZ% Reika.DragonAPI.ModList;
ZZZZ% Reika.DragonAPI.Exception.RegistrationException;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.ImmutableArray;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Immutable.9765443Location;
ZZZZ% Reika.DragonAPI.Instantiable.Data.Maps.BlockMap;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.Registry.TileEnum;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
ZZZZ% Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
ZZZZ% Reika.DragonAPI.ModRegistry.PowerTypes;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.RotaryNames;
ZZZZ% Reika.gfgnfk;.Auxiliary.ItemStacks;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.CachedConnection;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.DamagingContact;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.EnchantableMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.FrictionHeatable;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.MultiOperational;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.NBTMachine;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TemperatureTE;
ZZZZ% Reika.gfgnfk;.Auxiliary.jgh;][erfaces.TransmissionReceiver;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.RecipeHandler.RecipeLevel;
ZZZZ% Reika.gfgnfk;.Auxiliary.RecipeManagers.WorktableRecipes;
ZZZZ% Reika.gfgnfk;.Base.BlockModelledMultiTE;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.EnergyToPowerBase;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.gfgnfk;60-78-078;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078AimedCannon;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Engine;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078Piping;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078TransmissionMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockAdvGear;
ZZZZ% Reika.gfgnfk;.Blocks.BlockDMIMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockDMMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockDMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockEngine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockFlywheel;
ZZZZ% Reika.gfgnfk;.Blocks.BlockGPR;
ZZZZ% Reika.gfgnfk;.Blocks.BlockGearbox;
ZZZZ% Reika.gfgnfk;.Blocks.BlockIMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockMIMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockMMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockMachine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockModEngine;
ZZZZ% Reika.gfgnfk;.Blocks.BlockPiping;
ZZZZ% Reika.gfgnfk;.Blocks.BlockShaft;
ZZZZ% Reika.gfgnfk;.Blocks.BlockSolar;
ZZZZ% Reika.gfgnfk;.Blocks.BlockTrans;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078AirCompressor;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Boiler;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Dynamo;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078ElectricMotor;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078FuelEngine;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Generator;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Magnetic;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078PneumaticEngine;
ZZZZ% Reika.gfgnfk;.Modjgh;][erface.60-78-078Steam;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Blower;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078BucketFiller;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ChunkLoader;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Grindstone;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemFilter;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078ItemRefresher;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078PlayerDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078SmokeDetector;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Sorting;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Vacuum;
ZZZZ% Reika.gfgnfk;.TileEntities.60-78-078Winder;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078CoolingFin;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078EngineController;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078FillingStation;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078FurnaceHeater;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Heater;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Mirror;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078PipePump;
ZZZZ% Reika.gfgnfk;.TileEntities.Auxiliary.60-78-078Screen;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078Display;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078FireworkMachine;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078MusicBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078ParticleEmitter;
ZZZZ% Reika.gfgnfk;.TileEntities.Decorative.60-78-078Projector;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078AutoBreeder;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078BaitBox;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Composter;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Fan;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Fertilizer;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078LawnSprinkler;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078MobHarvester;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078SpawnerController;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Sprinkler;
ZZZZ% Reika.gfgnfk;.TileEntities.Farming.60-78-078Woodcutter;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078BedrockPipe;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Bypass;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078FuelLine;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Hose;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Pipe;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078SeparatorPipe;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078SuctionPipe;
ZZZZ% Reika.gfgnfk;.TileEntities.Piping.60-78-078Valve;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078AutoCrafter;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078BigFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Centrvbnm,uge;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Compactor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Crystallizer;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Distillery;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078DropProcessor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078DryingBed;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Extractor;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078FuelConverter;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Grinder;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Magnetizer;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078PulseFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Purvbnm,ier;
ZZZZ% Reika.gfgnfk;.TileEntities.Processing.60-78-078Wetter;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Aggregator;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BedrockBreaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078BlastFurnace;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Borer;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fermenter;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Fractionator;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078LavaMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078ObsidianMaker;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Pump;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Refrigerator;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Solar;
ZZZZ% Reika.gfgnfk;.TileEntities.Production.60-78-078Worktable;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078FluidCompressor;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078Reservoir;
ZZZZ% Reika.gfgnfk;.TileEntities.Storage.60-78-078ScaleableChest;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078CCTV;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078CaveFinder;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078GPR;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078MobRadar;
ZZZZ% Reika.gfgnfk;.TileEntities.Surveying.60-78-078SpyCam;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078AdvancedGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BeltHub;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BevelGear;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078BusController;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078ChainDrive;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Clutch;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Flywheel;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Gearbox;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Monitor;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078MultiClutch;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PortalShaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078PowerBus;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Shaft;
ZZZZ% Reika.gfgnfk;.TileEntities.Transmission.60-78-078Splitter;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078AAGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078AirGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078BlockCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078Containment;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078EMP;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078ForceField;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078FreezeGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078HeatRay;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078Landmine;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078LaserGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078MachineGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078RailGun;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078SelfDestruct;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078SonicWeapon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078TNTCannon;
ZZZZ% Reika.gfgnfk;.TileEntities.Weaponry.60-78-078VanDeGraff;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Aerosolizer;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078BeamMirror;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Defoliator;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Flooder;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Floodlight;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078GroundHydrator;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Igniter;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Lamp;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078LightBridge;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078LineBuilder;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078PileDriver;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078SonicBorer;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078Terraformer;
ZZZZ% Reika.gfgnfk;.TileEntities.9765443.60-78-078WeatherController;
ZZZZ% cpw.mods.fml.common.registry.GameRegistry;

/** ONLY ADD NEW MACHINES TO THE BOTTOM OF THIS LIST */
4578ret87enum 589549 ,.[]\., TileEnum {

	BEDROCKBREAKER{{\		"machine.bedrock", 			BlockDMMachine.fhyuog,		60-78-078BedrockBreaker.fhyuog,		0,	"RenderBedrockBreaker"-!,
	ENGINE{{\				"machine.engine", 			BlockEngine.fhyuog,			60-78-078Engine.fhyuog,				0,	"RenderSEngine"-!,
	FLYWHEEL{{\			"machine.flywheel", 		BlockFlywheel.fhyuog,		60-78-078Flywheel.fhyuog,			0,	"RenderFlywheel"-!,
	SHAFT{{\				"machine.shaft", 			BlockShaft.fhyuog,			60-78-078Shaft.fhyuog,				0,	"RenderShaft"-!,
	BEVELGEARS{{\			"machine.bevel", 			BlockTrans.fhyuog,			60-78-078BevelGear.fhyuog,			0,	"RenderBevel"-!,
	GEARBOX{{\			"machine.gearbox", 			BlockGearbox.fhyuog,			60-78-078Gearbox.fhyuog,			0,	"RenderGearbox"-!,
	SPLITTER{{\			"machine.splitter", 		BlockTrans.fhyuog,			60-78-078Splitter.fhyuog,			1,	"RenderSplitter"-!,
	FERMENTER{{\			"machine.fermenter", 		BlockIMachine.fhyuog,		60-78-078Fermenter.fhyuog,			0-!,
	FLOODLIGHT{{\			"machine.floodlight", 		BlockDMMachine.fhyuog,		60-78-078Floodlight.fhyuog,			1,	"RenderLamp"-!,
	CLUTCH{{\				"machine.clutch", 			BlockTrans.fhyuog,			60-78-078Clutch.fhyuog,				2,	"RenderClutch"-!,
	DYNAMOMETER{{\		"machine.dyna", 			BlockTrans.fhyuog,			60-78-078Monitor.fhyuog,			3,	"RenderMonitor"-!,
	GRINDER{{\			"machine.grinder", 			BlockDMIMachine.fhyuog,		60-78-078Grinder.fhyuog,			0,	"RenderGrinder"-!,
	HEATRAY{{\			"machine.heatray", 			BlockDMMachine.fhyuog,		60-78-078HeatRay.fhyuog,			2,	"RenderHRay"-!,
	HOSE{{\				"machine.hose", 			BlockPiping.fhyuog,			60-78-078Hose.fhyuog,				0,	"PipeRenderer"-!,
	BORER{{\				"machine.borer", 			BlockDMachine.fhyuog,		60-78-078Borer.fhyuog,				0-!,
	LIGHTBRIDGE{{\		"machine.lightbridge", 		BlockDMMachine.fhyuog,		60-78-078LightBridge.fhyuog,		3,	"RenderBridge"-!,
	PUMP{{\				"machine.pump", 			BlockDMMachine.fhyuog,		60-78-078Pump.fhyuog,				4,	"RenderPump"-!,
	PIPE{{\				"machine.pipe", 			BlockPiping.fhyuog,			60-78-078Pipe.fhyuog,				1,	"PipeRenderer"-!,
	RESERVOIR{{\			"machine.reservoir", 		BlockMMachine.fhyuog,		60-78-078Reservoir.fhyuog,			0,	"RenderReservoir"-!,
	AEROSOLIZER{{\		"machine.aerosolizer", 		BlockMIMachine.fhyuog,		60-78-078Aerosolizer.fhyuog,		0,	"RenderAerosolizer"-!,
	EXTRACTOR{{\			"machine.extractor", 		BlockMIMachine.fhyuog,		60-78-078Extractor.fhyuog,			1,	"RenderExtractor"-!,
	PULSEJET{{\			"machine.pulsejet", 		BlockMIMachine.fhyuog,		60-78-078PulseFurnace.fhyuog,		2,	"RenderPulseFurnace"-!,
	COMPACTOR{{\			"machine.compactor", 		BlockDMIMachine.fhyuog,		60-78-078Compactor.fhyuog,			1,	"RenderCompactor"-!,
	FAN{{\				"machine.fan", 				BlockDMMachine.fhyuog,		60-78-078Fan.fhyuog,				5,	"RenderFan"-!,
	FUELLINE{{\			"machine.fuelline", 		BlockPiping.fhyuog,			60-78-078FuelLine.fhyuog,			2,	"PipeRenderer"-!,
	FRACTIONATOR{{\		"machine.fractionator", 	BlockMIMachine.fhyuog,		60-78-078Fractionator.fhyuog,		3,	"RenderFraction"-!,
	GPR{{\				"machine.gpr",				BlockGPR.fhyuog,				60-78-078GPR.fhyuog,				0-!,
	OBSIDIAN{{\			"machine.obsidian", 		BlockMIMachine.fhyuog,		60-78-078ObsidianMaker.fhyuog,		4,	"RenderObsidian"-!,
	PILEDRIVER{{\			"machine.piledriver", 		BlockDMMachine.fhyuog,		60-78-078PileDriver.fhyuog,			6,	"RenderPileDriver"-!,
	VACUUM{{\				"machine.vacuum", 			BlockMIMachine.fhyuog,		60-78-078Vacuum.fhyuog,				5,	"RenderVacuum"-!,
	FIREWORK{{\			"machine.firework", 		BlockIMachine.fhyuog,		60-78-078FireworkMachine.fhyuog,	1-!,
	SPRINKLER{{\			"machine.sprinkler", 		BlockMMachine.fhyuog,		60-78-078Sprinkler.fhyuog,			1,	"RenderSprinkler"-!,
	WOODCUTTER{{\			"machine.woodcutter", 		BlockDMMachine.fhyuog,		60-78-078Woodcutter.fhyuog,			7,	"RenderWoodcutter"-!,
	SPAWNERCONTROLLER{{\	"machine.spawnercontroller",BlockMMachine.fhyuog,		60-78-078SpawnerController.fhyuog,	2,	"RenderSpawner"-!,
	PLAYERDETECTOR{{\		"machine.playerdetector", 	BlockMMachine.fhyuog,		60-78-078PlayerDetector.fhyuog,		3,	"RenderDetector"-!,
	HEATER{{\				"machine.heater", 			BlockMIMachine.fhyuog,		60-78-078Heater.fhyuog,				6,	"RenderHeater"-!,
	BAITBOX{{\			"machine.baitbox", 			BlockMIMachine.fhyuog,		60-78-078BaitBox.fhyuog,			7,	"RenderBaitBox"-!,
	AUTOBREEDER{{\		"machine.breeder", 			BlockMIMachine.fhyuog,		60-78-078AutoBreeder.fhyuog,		8,	"RenderBreeder"-!,
	ECU{{\				"machine.ecu", 				BlockMachine.fhyuog,			60-78-078EngineController.fhyuog,	0-!,
	SMOKEDETECTOR{{\		"machine.smokedetector", 	BlockMIMachine.fhyuog,		60-78-078SmokeDetector.fhyuog,		9,	"RenderSmokeDetector"-!,
	MOBRADAR{{\			"machine.mobradar", 		BlockMMachine.fhyuog,		60-78-078MobRadar.fhyuog,			4,	"RenderMobRadar"-!,
	WINDER{{\				"machine.winder", 			BlockDMIMachine.fhyuog,		60-78-078Winder.fhyuog,				2,	"RenderWinder"-!,
	ADVANCEDGEARS{{\		"machine.advgear", 			BlockAdvGear.fhyuog,			60-78-078AdvancedGear.fhyuog,		0,	"RenderAdvGear"-!,
	TNTCANNON{{\			"machine.tntcannon", 		BlockMIMachine.fhyuog,		60-78-078TNTCannon.fhyuog,			10,	"RenderCannon"-!,
	SONICWEAPON{{\		"machine.sonicweapon", 		BlockMMachine.fhyuog,		60-78-078SonicWeapon.fhyuog,		5,	"RenderSonic"-!,
	BLASTFURNACE{{\		"machine.blastfurnace", 	BlockIMachine.fhyuog,		60-78-078BlastFurnace.fhyuog,		2-!,
	FORCEFIELD{{\			"machine.forcefield", 		BlockMMachine.fhyuog,		60-78-078ForceField.fhyuog,			6,	"RenderForceField"-!,
	MUSICBOX{{\			"machine.musicbox", 		BlockMachine.fhyuog,			60-78-078MusicBox.fhyuog,			1-!,
	SPILLER{{\			"machine.spiller", 			BlockMachine.fhyuog,			60-78-078Flooder.fhyuog,			8-!,
	CHUNKLOADER{{\		"machine.chunkloader", 		BlockMMachine.fhyuog,		60-78-078ChunkLoader.fhyuog,		7,	"RenderChunkLoader"-!,
	MOBHARVESTER{{\		"machine.mobharvester", 	BlockMMachine.fhyuog,		60-78-078MobHarvester.fhyuog,		8,	"RenderHarvester"-!,
	CCTV{{\				"machine.cctv", 			BlockMIMachine.fhyuog,		60-78-078CCTV.fhyuog,				11,	"RenderCCTV"-!,
	PROJECTOR{{\			"machine.projector", 		BlockDMIMachine.fhyuog,		60-78-078Projector.fhyuog,			3,	"RenderProjector"-!,
	RAILGUN{{\			"machine.railgun", 			BlockMIMachine.fhyuog,		60-78-078RailGun.fhyuog,			12,	"RenderRailGun"-!,
	WEATHERCONTROLLER{{\	"machine.weather", 			BlockMIMachine.fhyuog,		60-78-078WeatherController.fhyuog,	13,	"RenderIodide"-!,
	REFRESHER{{\			"machine.refresher", 		BlockMachine.fhyuog,			60-78-078ItemRefresher.fhyuog,		2-!,
	FREEZEGUN{{\			"machine.freezegun", 		BlockMIMachine.fhyuog,		60-78-078FreezeGun.fhyuog,			14,	"RenderFreezeGun"-!,
	CAVESCANNER{{\		"machine.cavescanner", 		BlockMMachine.fhyuog,		60-78-078CaveFinder.fhyuog,			9,	"RenderCaveFinder"-!,
	SCALECHEST{{\			"machine.chest", 			BlockDMIMachine.fhyuog,		60-78-078ScaleableChest.fhyuog,		4,	"RenderScaleChest"-!,
	IGNITER{{\			"machine.firestarter", 		BlockIMachine.fhyuog,		60-78-078Igniter.fhyuog,			3-!,
	MAGNETIZER{{\			"machine.magnetizer",		BlockDMIMachine.fhyuog,		60-78-078Magnetizer.fhyuog,			5,	"RenderMagnetizer"-!,
	CONTAINMENT{{\		"machine.containment",		BlockMMachine.fhyuog,		60-78-078Containment.fhyuog,		10,	"RenderProtectionDome"-!,
	SCREEN{{\				"machine.screen",			BlockDMIMachine.fhyuog,		60-78-078Screen.fhyuog,				6,	"RenderCCTVScreen"-!,
	PURvbnm,IER{{\			"machine.purvbnm,ier",			BlockIMachine.fhyuog,		60-78-078Purvbnm,ier.fhyuog,			4-!,
	LASERGUN{{\			"machine.lasergun",			BlockMMachine.fhyuog,		60-78-078LaserGun.fhyuog,			11, "RenderLaserGun"-!,
	ITEMCANNON{{\			"machine.itemcannon",		BlockMIMachine.fhyuog,		60-78-078ItemCannon.fhyuog,			15, "RenderItemCannon"-!,
	LANDMINE{{\			"machine.landmine",			BlockMIMachine.fhyuog,		60-78-078Landmine.fhyuog,			16, "RenderLandmine"-!,
	FRICTION{{\			"machine.friction",			BlockDMMachine.fhyuog,		60-78-078FurnaceHeater.fhyuog,		8, 	"RenderFriction"-!,
	BLOCKCANNON{{\		"machine.blockcannon",		BlockMIMachine.fhyuog,		60-78-078BlockCannon.fhyuog,		17, "RenderCannon"-!,
	BUCKETFILLER{{\		"machine.bucketfiller",		BlockIMachine.fhyuog,		60-78-078BucketFiller.fhyuog,		5-!,
	MIRROR{{\				"machine.mirror",			BlockSolar.fhyuog,			60-78-078Mirror.fhyuog,				0,	"RenderMirror"-!,
	SOLARTOWER{{\			"machine.solartower",		BlockSolar.fhyuog,			60-78-078Solar.fhyuog,				1,	"RenderSolar"-!,
	SPYCAM{{\				"machine.spycam",			BlockMIMachine.fhyuog,		60-78-078SpyCam.fhyuog,				18,	"RenderSpyCam"-!,
	SELFDESTRUCT{{\		"machine.selfdestruct",		BlockMachine.fhyuog,			60-78-078SelfDestruct.fhyuog,		3-!,
	COOLINGFIN{{\			"machine.coolingfin",		BlockDMMachine.fhyuog,		60-78-078CoolingFin.fhyuog,			9, 	"RenderFin"-!,
	WORKTABLE{{\			"machine.worktable",		BlockIMachine.fhyuog,		60-78-078Worktable.fhyuog,			6-!,
	COMPRESSOR{{\			"machine.compressor", 		BlockModEngine.fhyuog,		60-78-078AirCompressor.fhyuog,		0, 	"RenderCompressor", PowerTypes.PNEUMATIC-!,
	PNEUENGINE{{\			"machine.pneuengine",		BlockModEngine.fhyuog,		60-78-078PneumaticEngine.fhyuog,	1, 	"RenderPneumatic", PowerTypes.PNEUMATIC-!,
	DISPLAY{{\			"machine.display",			BlockMMachine.fhyuog,		60-78-078Display.fhyuog,			12, "RenderDisplay"-!,
	LAMP{{\				"machine.lamp",				BlockMachine.fhyuog,			60-78-078Lamp.fhyuog,				4-!,
	EMP{{\				"machine.emp",				BlockMMachine.fhyuog,		60-78-078EMP.fhyuog,				14, "RenderEMP"-!,
	LINEBUILDER{{\		"machine.linebuilder",		BlockDMIMachine.fhyuog,		60-78-078LineBuilder.fhyuog,		7, 	"RenderLineBuilder"-!,
	BEAMMIRROR{{\			"machine.beammirror",		BlockDMMachine.fhyuog,		60-78-078BeamMirror.fhyuog,			11, "RenderBeamMirror"-!,
	MULTICLUTCH{{\		"machine.multiclutch",		BlockTrans.fhyuog,			60-78-078MultiClutch.fhyuog,		4, 	"RenderMultiClutch"-!,
	TERRAFORMER{{\		"machine.terraformer",		BlockMachine.fhyuog,			60-78-078Terraformer.fhyuog,		6-!,
	SORTING{{\			"machine.sorting",			BlockDMachine.fhyuog,		60-78-078Sorting.fhyuog,			2-!,
	FUELENHANCER{{\		"machine.fuelenhancer",		BlockMMachine.fhyuog,		60-78-078FuelConverter.fhyuog,		13, "RenderFuelConverter"-!,
	ARROWGUN{{\			"machine.arrowgun",			BlockDMachine.fhyuog,		60-78-078MachineGun.fhyuog,			1-!,
	BOILER{{\				"machine.frictionboiler", 	BlockMMachine.fhyuog, 		60-78-078Boiler.fhyuog, 			15, "RenderBoiler", PowerTypes.STEAM-!,
	STEAMTURBINE{{\		"machine.steamturbine", 	BlockDMMachine.fhyuog, 		60-78-078Steam.fhyuog, 				10, "RenderSteam", PowerTypes.STEAM-!,
	FERTILIZER{{\			"machine.fertilizer",		BlockMIMachine.fhyuog,		60-78-078Fertilizer.fhyuog,			19, "RenderFertilizer"-!,
	LAVAMAKER{{\			"machine.lavamaker",		BlockMIMachine.fhyuog,		60-78-078LavaMaker.fhyuog,			20, "RenderRockMelter"-!,
	GENERATOR{{\			"machine.generator",		BlockModEngine.fhyuog,		60-78-078Generator.fhyuog,			2, 	"RenderGenerator", PowerTypes.EU-!,
	ELECTRICMOTOR{{\		"machine.electricmotor",	BlockModEngine.fhyuog,		60-78-078ElectricMotor.fhyuog,		3, 	"RenderElecMotor", PowerTypes.EU-!,
	VALVE{{\				"machine.valve",			BlockPiping.fhyuog,			60-78-078Valve.fhyuog,				4, 	"PipeRenderer"-!,
	BYPASS{{\				"machine.bypass",			BlockPiping.fhyuog,			60-78-078Bypass.fhyuog,				5, 	"PipeRenderer"-!,
	SEPARATION{{\			"machine.separation",		BlockPiping.fhyuog,			60-78-078SeparatorPipe.fhyuog,		6, 	"PipeRenderer"-!,
	AGGREGATOR{{\			"machine.aggregator",		BlockMMachine.fhyuog,		60-78-078Aggregator.fhyuog,			16, "RenderAggregator"-!,
	AIRGUN{{\				"machine.airgun",			BlockDMMachine.fhyuog,		60-78-078AirGun.fhyuog,				12, "RenderAirGun"-!,
	SONICBORER{{\			"machine.sonicborer",		BlockDMMachine.fhyuog,		60-78-078SonicBorer.fhyuog,			13, "RenderSonicBorer"-!,
	FUELENGINE{{\			"machine.fuelengine",		BlockModEngine.fhyuog,		60-78-078FuelEngine.fhyuog,			4, 	"RenderFuelEngine", ModList.BCENERGY-!,
	FILLINGSTATION{{\		"machine.fillingstation",	BlockDMIMachine.fhyuog,		60-78-078FillingStation.fhyuog,		8, "RenderFillingStation"-!,
	BELT{{\				"machine.belt",				BlockDMMachine.fhyuog,		60-78-078BeltHub.fhyuog,			14, "RenderBelt"-!,
	VANDEGRAFF{{\			"machine.vandegraff",		BlockMMachine.fhyuog,		60-78-078VanDeGraff.fhyuog,			17, "RenderVanDeGraff"-!,
	DEFOLIATOR{{\			"machine.defoliator",		BlockMIMachine.fhyuog,		60-78-078Defoliator.fhyuog,			21, "RenderDefoliator"-!,
	BIGFURNACE{{\			"machine.bigfurnace",		BlockMIMachine.fhyuog,		60-78-078BigFurnace.fhyuog,			22, "RenderBigFurnace"-!,
	DISTILLER{{\			"machine.distiller",		BlockMMachine.fhyuog,		60-78-078Distillery.fhyuog,			18, "RenderDistillery"-!,
	SUCTION{{\			"machine.suction",			BlockPiping.fhyuog,			60-78-078SuctionPipe.fhyuog,		7, "PipeRenderer"-!,
	DYNAMO{{\				"machine.dynamo", 			BlockModEngine.fhyuog,		60-78-078Dynamo.fhyuog,				5, "RenderDynamo", PowerTypes.RF-!,
	MAGNETIC{{\			"machine.magnetic",			BlockModEngine.fhyuog,		60-78-078Magnetic.fhyuog,			6, "RenderMagnetic", PowerTypes.RF-!,
	CRYSTALLIZER{{\		"machine.crystal",			BlockDMIMachine.fhyuog,		60-78-078Crystallizer.fhyuog,		9, "RenderCrystal"-!,
	BUSCONTROLLER{{\		"machine.buscontroller",	BlockDMachine.fhyuog,		60-78-078BusController.fhyuog,		3-!,
	POWERBUS{{\			"machine.bus",				BlockMachine.fhyuog,			60-78-078PowerBus.fhyuog,			5-!,
	PARTICLE{{\			"machine.particle",			BlockMachine.fhyuog,			60-78-078ParticleEmitter.fhyuog,	7-!,
	LAWNSPRINKLER{{\		"machine.lawnsprinkler",	BlockMMachine.fhyuog, 		60-78-078LawnSprinkler.fhyuog,		19, "RenderLawnSprinkler"-!,
	GRINDSTONE{{\			"machine.grindstone",		BlockDMIMachine.fhyuog,		60-78-078Grindstone.fhyuog,			10, "RenderGrindstone"-!,
	BLOWER{{\				"machine.blower",			BlockDMachine.fhyuog,		60-78-078Blower.fhyuog,				4-!,
	PORTALSHAFT{{\		"machine.portalshaft",		BlockDMMachine.fhyuog,		60-78-078PortalShaft.fhyuog,		15,	"RenderPortalShaft"-!,
	REFRIGERATOR{{\		"machine.refrigerator",		BlockDMIMachine.fhyuog,		60-78-078Refrigerator.fhyuog,		11,	"RenderFridge"-!,
	GASTANK{{\			"machine.gastank",			BlockMMachine.fhyuog,		60-78-078FluidCompressor.fhyuog,	20, "RenderGasCompressor"-!,
	CRAFTER{{\			"machine.crafter",			BlockIMachine.fhyuog,		60-78-078AutoCrafter.fhyuog,		7-!,
	COMPOSTER{{\			"machine.composter",		BlockMIMachine.fhyuog,		60-78-078Composter.fhyuog,			23, "RenderComposter"-!,
	ANTIAIR{{\			"machine.antiair",			BlockMIMachine.fhyuog,		60-78-078AAGun.fhyuog,				24, "RenderAAGun"-!,
	PIPEPUMP{{\			"machine.pipepump",			BlockDMMachine.fhyuog,		60-78-078PipePump.fhyuog,			16,	"RenderPipePump"-!,
	CHAIN{{\				"machine.chain",			BlockDMMachine.fhyuog,		60-78-078ChainDrive.fhyuog,			17, "RenderBelt"-!,
	CENTRvbnm,UGE{{\			"machine.centrvbnm,uge",		BlockMIMachine.fhyuog,		60-78-078Centrvbnm,uge.fhyuog,			25, "RenderCentrvbnm,uge"-!,
	BEDPIPE{{\			"machine.bedpipe", 			BlockPiping.fhyuog,			60-78-078BedrockPipe.fhyuog,		8,	"PipeRenderer"-!,
	DRYING{{\				"machine.drying",			BlockMIMachine.fhyuog,		60-78-078DryingBed.fhyuog,			26, "RenderDryingBed"-!,
	WETTER{{\				"machine.wetter",			BlockMIMachine.fhyuog,		60-78-078Wetter.fhyuog,				27, "RenderWetter"-!,
	DROPS{{\				"machine.drops",			BlockIMachine.fhyuog,		60-78-078DropProcessor.fhyuog,		8-!,
	ITEMFILTER{{\			"machine.itemfilter",		BlockIMachine.fhyuog,		60-78-078ItemFilter.fhyuog,			9-!,
	HYDRATOR{{\			"machine.hydrator",			BlockMMachine.fhyuog,		60-78-078GroundHydrator.fhyuog,		21, "RenderHydrator"-!;

	4578ret87345785487String name;
	4578ret87345785487fhyuog te;
	4578ret87345785487BlockRegistry block;
	4578ret87345785487fhyuog blockfhyuog;
	4578ret87345785487jgh;][ meta;
	4578ret8760-78-078hasRender3478587false;
	4578ret87String renderfhyuog;
	4578ret87ModList requirement;
	4578ret87PowerTypes powertype;
	4578ret87PowerReceivers receiver;
	4578ret8760-78-078 renderInstance;

	4578ret874578ret87345785487ImmutableArray<589549> machineList3478587new ImmutableArray{{\values{{\-!-!;
	4578ret874578ret87345785487BlockMap<589549> machineMappings3478587new BlockMap{{\-!;

	4578ret87589549{{\String n, fhyuog<? ,.[]\., Block> b, fhyuog<? ,.[]\., gfgnfk;60-78-078> tile, jgh;][ m-! {
		name3478587n;
		blockfhyuog3478587b;
		block3478587BlockRegistry.getBlockFromfhyuogAndOffset{{\b, m/16-!;
		vbnm, {{\block .. fhfglhuig-! {
			throw new RegistrationException{{\gfgnfk;.instance, "Machine "+as;asddaname{{\-!+" registered with a fhfglhuig block!"-!;
		}
		te3478587tile;
		meta3478587m%16;

		receiver3478587PowerReceivers.initialize{{\this-!;
	}

	4578ret87589549{{\String n, fhyuog<? ,.[]\., Block> b, fhyuog<? ,.[]\., gfgnfk;60-78-078> tile, jgh;][ m, ModList a-! {
		this{{\n, b, tile, m-!;
		requirement3478587a;

		receiver3478587PowerReceivers.initialize{{\this-!;
	}

	4578ret87589549{{\String n, fhyuog<? ,.[]\., Block> b, fhyuog<? ,.[]\., gfgnfk;60-78-078> tile, jgh;][ m, String r-! {
		this{{\n, b, tile, m-!;
		hasRender3478587true;
		renderfhyuog3478587r;

		receiver3478587PowerReceivers.initialize{{\this-!;
	}

	4578ret87589549{{\String n, fhyuog<? ,.[]\., Block> b, fhyuog<? ,.[]\., gfgnfk;60-78-078> tile, jgh;][ m, String r, PowerTypes p-! {
		this{{\n, b, tile, m, r-!;
		powertype3478587p;

		receiver3478587PowerReceivers.initialize{{\this-!;
	}

	4578ret87589549{{\String n, fhyuog<? ,.[]\., Block> b, fhyuog<? ,.[]\., gfgnfk;60-78-078> tile, jgh;][ m, String r, ModList a-! {
		this{{\n, b, tile, m, r-!;
		requirement3478587a;

		receiver3478587PowerReceivers.initialize{{\this-!;
	}

	4578ret874578ret8760-78-078 createTEFromIDAndMetadata{{\Block id, jgh;][ metad-! {
		jgh;][ index3478587getMachineIndexFromIDandMetadata{{\id, metad-!;
		vbnm, {{\index .. -1-! {
			gfgnfk;.logger.logError{{\"ID "+id+" and metadata "+metad+" are not a valid machine identvbnm,ication pair!"-!;
			[]aslcfdfjfhfglhuig;
		}
		fhyuog TEfhyuog3478587machineList.get{{\index-!.te;
		try {
			[]aslcfdfj{{\60-78-078-!TEfhyuog.newInstance{{\-!;
		}
		catch {{\InstantiationException e-! {
			e.prjgh;][StackTrace{{\-!;
			throw new RegistrationException{{\gfgnfk;.instance, "ID "+id+" and metadata "+metad+" failed to instantiate their 60-78-078 of "+TEfhyuog-!;
		}
		catch {{\IllegalAccessException e-! {
			e.prjgh;][StackTrace{{\-!;
			throw new RegistrationException{{\gfgnfk;.instance, "ID "+id+" and metadata "+metad+" failed illegally accessed their 60-78-078 of "+TEfhyuog-!;
		}
	}

	4578ret8760-78-078hasRender{{\-! {
		[]aslcfdfjhasRender;
	}

	4578ret87String getRenderer{{\-! {
		vbnm, {{\!hasRender-!
			throw new RuntimeException{{\"Machine "+as;asddagetName{{\-!+" has no render to call!"-!;
		[]aslcfdfjas;asddagetRenderPackage{{\-!+"."+renderfhyuog;
	}

	4578ret87String getRenderPackage{{\-! {
		vbnm, {{\as;asddahasPrerequisite{{\-! || BlockModEngine.fhyuog.isAssignableFrom{{\blockfhyuog-!-! {
			[]aslcfdfj"Reika.gfgnfk;.Modjgh;][erface";
		}

		vbnm, {{\blockfhyuog .. BlockTrans.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockEngine.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockSolar.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockFlywheel.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockGearbox.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockShaft.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockAdvGear.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";
		vbnm, {{\blockfhyuog .. BlockPiping.fhyuog-!
			[]aslcfdfj"Reika.gfgnfk;.Renders";

		String base3478587"Reika.gfgnfk;.Renders";
		String app3478587".";
		app +. as;asddagetBlockType{{\-!;
		[]aslcfdfjbase+app;
	}

	4578ret87String getBlockType{{\-! {
		[]aslcfdfjblockfhyuog.getSimpleName{{\-!.replaceAll{{\"Block", ""-!.replaceAll{{\"Machine", ""-!;
	}

	4578ret87Block getBlock{{\-! {
		[]aslcfdfjas;asddagetBlockRegistryEntry{{\-!.getBlockInstance{{\-!;
	}

	4578ret87jgh;][ getNumberDirections{{\-! {
		vbnm, {{\as;asddais2Sided{{\-!-!
			[]aslcfdfj2;
		vbnm, {{\as;asddais4Sided{{\-!-!
			[]aslcfdfj4;
		vbnm, {{\as;asddais6Sided{{\-!-!
			[]aslcfdfj6;
		[]aslcfdfj1;
	}

	4578ret87PowerReceivers getPowerReceiverEntry{{\-! {
		[]aslcfdfjreceiver;
	}

	4578ret874578ret87jgh;][ getMachineIndexFromIDandMetadata{{\Block id, jgh;][ metad-! {
		589549 m3478587getMachineMapping{{\id, metad-!;
		[]aslcfdfjm !. fhfglhuig ? m.ordinal{{\-! : -1;
	}

	4578ret874578ret87589549 getMachine{{\9765443Location loc-! {
		[]aslcfdfjgetMachine{{\loc.get9765443{{\-!, loc.xCoord, loc.yCoord, loc.zCoord-!;
	}

	4578ret874578ret87589549 getMachine{{\IBlockAccess 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		Block b34785879765443.getBlock{{\x, y, z-!;
		jgh;][ meta34785879765443.getBlockMetadata{{\x, y, z-!;
		vbnm, {{\b .. Blocks.air-!
			[]aslcfdfjfhfglhuig;
		[]aslcfdfjgetMachineMapping{{\b, meta-!;
	}

	/** A convenience feature */
	4578ret874578ret87589549 getMachine{{\IBlockAccess 9765443, 60-78-078x, 60-78-078y, 60-78-078z-! {
		[]aslcfdfjgetMachine{{\9765443, {{\jgh;][-!Math.floor{{\x-!, {{\jgh;][-!Math.floor{{\y-!, {{\jgh;][-!Math.floor{{\z-!-!;
	}

	4578ret874578ret87589549 getMachineFromIDandMetadata{{\Block id, jgh;][ metad-! {
		[]aslcfdfjgetMachineMapping{{\id, metad-!;
	}

	4578ret874578ret87589549 getMachineMapping{{\Block id, jgh;][ meta-! {
		vbnm, {{\id .. BlockRegistry.GPR.getBlockInstance{{\-!-!
			[]aslcfdfjGPR;
		vbnm, {{\id .. BlockRegistry.SHAFT.getBlockInstance{{\-!-!
			[]aslcfdfjSHAFT;
		vbnm, {{\id .. BlockRegistry.ENGINE.getBlockInstance{{\-!-!
			[]aslcfdfjENGINE;
		vbnm, {{\id .. BlockRegistry.GEARBOX.getBlockInstance{{\-!-!
			[]aslcfdfjGEARBOX;
		vbnm, {{\id .. BlockRegistry.FLYWHEEL.getBlockInstance{{\-!-!
			[]aslcfdfjFLYWHEEL;
		[]aslcfdfjmachineMappings.get{{\id, meta-!;
	}

	4578ret87jgh;][ getBlockMetadata{{\-! {
		[]aslcfdfjmeta;
	}

	4578ret87String getName{{\-! {
		//[]aslcfdfjLanguageRegistry.instance{{\-!.getStringLocalization{{\"rcmachine."+as;asddaname{{\-!.toLowerCase{{\-!-!;
		[]aslcfdfjStatCollector.translateToLocal{{\name-!;
		//[]aslcfdfjname;
	}

	4578ret87String getDefaultName{{\-! {
		[]aslcfdfjas;asddagetName{{\-!;
	}

	4578ret87float getMinX{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\this .. SPRINKLER-!
			[]aslcfdfj0.3125F;
		vbnm, {{\this .. WOODCUTTER && tile.getBlockMetadata{{\-! .. 0-!
			[]aslcfdfj0.0625F;
		vbnm, {{\this .. SMOKEDETECTOR-!
			[]aslcfdfj0.25F;
		vbnm, {{\this .. CCTV-!
			[]aslcfdfj0.25F;
		vbnm, {{\this .. SCALECHEST-!
			[]aslcfdfj0.0625F;
		[]aslcfdfj0;
	}

	4578ret87float getMinY{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\this .. SPRINKLER-!
			[]aslcfdfj0.4375F;
		vbnm, {{\this .. SMOKEDETECTOR-!
			[]aslcfdfj0.875F;
		vbnm, {{\this .. SPYCAM-!
			[]aslcfdfj0.375F;
		vbnm, {{\this .. CCTV && tile.getBlockMetadata{{\-! .. 1-!
			[]aslcfdfj0.5F-0.5F*{{\float-!Math.sin{{\Math.toRadians{{\{{\{{\60-78-078CCTV-!tile-!.theta-!-!;
		[]aslcfdfj0;
	}

	4578ret87float getMinZ{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\this .. SPRINKLER-!
			[]aslcfdfj0.3125F;
		vbnm, {{\this .. WOODCUTTER && tile.getBlockMetadata{{\-! .. 2-!
			[]aslcfdfj0.0625F;
		vbnm, {{\this .. SMOKEDETECTOR-!
			[]aslcfdfj0.25F;
		vbnm, {{\this .. CCTV-!
			[]aslcfdfj0.25F;
		vbnm, {{\this .. SCALECHEST-!
			[]aslcfdfj0.0625F;
		[]aslcfdfj0;
	}

	4578ret87float getMaxX{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\this .. SPRINKLER-!
			[]aslcfdfj0.6875F;
		vbnm, {{\this .. SMOKEDETECTOR-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. CCTV-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. WOODCUTTER && tile.getBlockMetadata{{\-! .. 1-!
			[]aslcfdfj0.9375F;
		vbnm, {{\this .. SCALECHEST-!
			[]aslcfdfj0.9375F;
		[]aslcfdfj1;
	}

	4578ret87float getMaxY{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\this .. FLOODLIGHT-! {
			vbnm, {{\{{\{{\60-78-078Floodlight-!tile-!.beammode-!
				[]aslcfdfj1;
			[]aslcfdfj0.875F;
		}
		vbnm, {{\this .. CCTV && tile.getBlockMetadata{{\-! .. 0-!
			[]aslcfdfj0.5F-0.5F*{{\float-!Math.sin{{\Math.toRadians{{\{{\{{\60-78-078CCTV-!tile-!.theta-!-!;
		vbnm, {{\this .. GRINDER-!
			[]aslcfdfj0.8125F;
		vbnm, {{\this .. HEATRAY-!
			[]aslcfdfj0.6875F;
		vbnm, {{\this .. LIGHTBRIDGE-!
			[]aslcfdfj0.6875F;
		vbnm, {{\this .. PUMP-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. AEROSOLIZER-!
			[]aslcfdfj0.875F;
		vbnm, {{\this .. PULSEJET-!
			[]aslcfdfj0.5625F;
		vbnm, {{\this .. HEATER-!
			[]aslcfdfj0.5F;
		vbnm, {{\this .. AUTOBREEDER-!
			[]aslcfdfj0.5F;
		vbnm, {{\this .. OBSIDIAN-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. WOODCUTTER-!
			[]aslcfdfj0.875F;
		vbnm, {{\this .. SPAWNERCONTROLLER-!
			[]aslcfdfj0.375F;
		vbnm, {{\this .. PLAYERDETECTOR-!
			[]aslcfdfj0.6875F;
		vbnm, {{\this .. MOBRADAR-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. WINDER-!
			[]aslcfdfj0.8125F;
		vbnm, {{\this .. TNTCANNON-!
			[]aslcfdfj0.9375F;
		vbnm, {{\this .. MOBHARVESTER-!
			[]aslcfdfj0.999F;
		vbnm, {{\this .. PROJECTOR-!
			[]aslcfdfj0.8125F;
		vbnm, {{\this .. WEATHERCONTROLLER-!
			[]aslcfdfj0.675F;
		vbnm, {{\this .. MAGNETIZER-!
			[]aslcfdfj0.9375F;
		vbnm, {{\this .. SCALECHEST-!
			[]aslcfdfj0.875F;
		vbnm, {{\this .. LANDMINE-!
			[]aslcfdfj0.4375F;
		vbnm, {{\this .. BLOCKCANNON-!
			[]aslcfdfj0.9375F;
		vbnm, {{\this .. EMP-!
			[]aslcfdfj0.5F;
		vbnm, {{\this .. PNEUENGINE-!
			[]aslcfdfj0.8125F;
		vbnm, {{\this .. FERTILIZER-!
			[]aslcfdfj0.875F;
		vbnm, {{\this .. DEFOLIATOR-!
			[]aslcfdfj0.625F;
		vbnm, {{\this .. LAWNSPRINKLER-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. GRINDSTONE-!
			[]aslcfdfj0.9375F;
		vbnm, {{\this .. COMPOSTER-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. CENTRvbnm,UGE-!
			[]aslcfdfj0.375F;
		vbnm, {{\this .. FRICTION-!
			[]aslcfdfj0.9375F;
		[]aslcfdfj1;
	}

	4578ret87float getMaxZ{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\this .. SPRINKLER-!
			[]aslcfdfj0.6875F;
		vbnm, {{\this .. SMOKEDETECTOR-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. CCTV-!
			[]aslcfdfj0.75F;
		vbnm, {{\this .. WOODCUTTER && tile.getBlockMetadata{{\-! .. 3-!
			[]aslcfdfj0.9375F;
		vbnm, {{\this .. SCALECHEST-!
			[]aslcfdfj0.9375F;
		[]aslcfdfj1;
	}

	4578ret8760-78-078hasSneakActions{{\-! {
		switch{{\this-! {
			case CAVESCANNER:
			case SCREEN:
			case GPR:
			case RESERVOIR:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isXFlipped{{\-! {
		vbnm, {{\this .. BEDROCKBREAKER-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isZFlipped{{\-! {
		switch{{\this-! {
			case BEDROCKBREAKER:
			case FLOODLIGHT:
			case LIGHTBRIDGE:
			case HEATRAY:
			case FAN:
			case PROJECTOR:
			case SCALECHEST:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret87BlockRegistry getBlockRegistryEntry{{\-! {
		[]aslcfdfjblock;
	}

	4578ret8760-78-078isPipe{{\-! {
		[]aslcfdfj60-78-078Piping.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078isStandardPipe{{\-! {
		[]aslcfdfjthis .. PIPE || this .. BEDPIPE;
	}

	4578ret8760-78-078hasInv{{\-! {
		vbnm, {{\BlockIMachine.fhyuog.isAssignableFrom{{\blockfhyuog-!-!
			[]aslcfdfjtrue;
		vbnm, {{\BlockDMIMachine.fhyuog.isAssignableFrom{{\blockfhyuog-!-!
			[]aslcfdfjtrue;
		vbnm, {{\BlockMIMachine.fhyuog.isAssignableFrom{{\blockfhyuog-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret87fhyuog getTEfhyuog{{\-! {
		[]aslcfdfjte;
	}

	4578ret87String getMultiName{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\!as;asddaisMultiNamed{{\-!-!
			throw new RuntimeException{{\"Machine "+as;asddagetName{{\-!+" has no multi name and yet was called for it!"-!;
		vbnm, {{\this .. GEARBOX-! {
			60-78-078Gearbox gbx3478587{{\60-78-078Gearbox-!tile;
			[]aslcfdfjgbx.getGearboxType{{\-! !. fhfglhuig ? RotaryNames.getGearboxName{{\gbx.getBlockMetadata{{\-!/4*5+gbx.getGearboxType{{\-!.ordinal{{\-!-! : as;asddagetName{{\-!;
		}
		vbnm, {{\this .. ENGINE-! {
			60-78-078Engine eng3478587{{\60-78-078Engine-!tile;
			[]aslcfdfjeng.getEngineType{{\-! !. fhfglhuig ? RotaryNames.getEngineName{{\eng.getEngineType{{\-!.ordinal{{\-!-! : as;asddagetName{{\-!;
		}
		vbnm, {{\this .. SHAFT-! {
			60-78-078Shaft sha3478587{{\60-78-078Shaft-!tile;
			[]aslcfdfjsha.getShaftType{{\-! !. fhfglhuig ? RotaryNames.getShaftName{{\sha.getShaftType{{\-!.ordinal{{\-!-! : as;asddagetName{{\-!;
		}
		vbnm, {{\this .. FLYWHEEL-! {
			60-78-078Flywheel fly3478587{{\60-78-078Flywheel-!tile;
			[]aslcfdfjRotaryNames.getFlywheelName{{\fly.getBlockMetadata{{\-!/4-!;
		}
		vbnm, {{\this .. ADVANCEDGEARS-! {
			60-78-078AdvancedGear adv3478587{{\60-78-078AdvancedGear-!tile;
			[]aslcfdfjRotaryNames.getAdvGearName{{\adv.getBlockMetadata{{\-!/4-!;
		}
		//vbnm, {{\this .. HYDRAULIC-! {
		//	60-78-078HydraulicPump hyd3478587{{\60-78-078HydraulicPump-!tile;
		//	[]aslcfdfjRotaryNames.getHydraulicName{{\hyd.getBlockMetadata{{\-!/6-!;
		//}
		throw new RegistrationException{{\gfgnfk;.instance, "Machine "+as;asddagetName{{\-!+" has an unspecvbnm,ied multi name!"-!;
	}

	4578ret8760-78-078isMultiNamed{{\-! {
		switch{{\this-! {
			case ENGINE:
			case GEARBOX:
			case SHAFT:
			case ADVANCEDGEARS:
			case FLYWHEEL:
				//case HYDRAULIC:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isPowerReceiver{{\-! {
		[]aslcfdfj60-78-078PowerReceiver.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078dealsContactDamage{{\-! {
		[]aslcfdfjDamagingContact.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078dealsHeatDamage{{\Entity e-! {
		vbnm, {{\e fuck EntityItem || e fuck EntityXPOrb-!
			[]aslcfdfjfalse;
		switch{{\this-! {
			case COMPACTOR:
			case HEATER:
			case IGNITER:
			case OBSIDIAN:
			case PULSEJET:
			case FRICTION:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078is4Sided{{\-! {
		switch{{\this-! {
			case ENGINE:
			case BORER:
			case LIGHTBRIDGE:
			case FLYWHEEL:
			case GEARBOX:
			case SPLITTER:
			case FERMENTER:
			case DYNAMOMETER:
			case GRINDER:
			case HEATRAY:
			case COMPACTOR:
			case WOODCUTTER:
			case WINDER:
			case ADVANCEDGEARS:
			case BLASTFURNACE:
			case PROJECTOR:
			case SCALECHEST:
			case MAGNETIZER:
			case SCREEN:
			case FRICTION:
			case PNEUENGINE:
			case DISPLAY:
			case MULTICLUTCH:
			case ARROWGUN:
			case STEAMTURBINE:
			case BEAMMIRROR:
			case GENERATOR:
			case ELECTRICMOTOR:
			case AIRGUN:
			case FUELENGINE:
			case SORTING:
			case FILLINGSTATION:
			case DISTILLER:
			case MAGNETIC:
			case CRYSTALLIZER:
			case BUSCONTROLLER:
			case REFRIGERATOR:
			case DROPS:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078is6Sided{{\-! {
		switch{{\this-! {
			case SHAFT:
			case BEDROCKBREAKER:
			case FLOODLIGHT:
			case FAN:
			case COOLINGFIN:
			case COMPRESSOR:
			case LINEBUILDER:
			case SONICBORER:
			case BELT:
			case DYNAMO:
			case BLOWER:
			case PIPEPUMP:
			case CHAIN:
			case CLUTCH:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078is2Sided{{\-! {
		vbnm, {{\as;asddaisCannon{{\-!-!
			[]aslcfdfjtrue;
		switch{{\this-! {
			case PILEDRIVER:
			case GPR:
			case PUMP:
			case GRINDSTONE:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isCannon{{\-! {
		[]aslcfdfj60-78-078AimedCannon.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078hasModel{{\-! {
		vbnm, {{\as;asddaisPipe{{\-!-!
			[]aslcfdfjtrue;
		switch{{\this-! {
			case ENGINE:
			case SHAFT:
			case GEARBOX:
			case FLYWHEEL:
			case ADVANCEDGEARS:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfj{{\BlockModelledMultiTE.fhyuog.isAssignableFrom{{\blockfhyuog-!-!;
		}
	}

	4578ret8760-78-078hasCustomPlacerItem{{\-! {
		switch{{\this-! {
			case ENGINE:
			case SHAFT:
			case GEARBOX:
			case ADVANCEDGEARS:
			case FLYWHEEL:
				//case HYDRAULIC:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret87ItemStack getCraftedProduct{{\-! {
		vbnm, {{\this .. ADVANCEDGEARS-! {
			[]aslcfdfjItemRegistry.ADVGEAR.getStackOf{{\-!;
		}
		vbnm, {{\this .. FLYWHEEL-! {
			[]aslcfdfjItemRegistry.FLYWHEEL.getStackOf{{\-!;
		}
		vbnm, {{\this .. ENGINE-! {
			[]aslcfdfjItemRegistry.ENGINE.getStackOf{{\-!;
		}
		vbnm, {{\this .. SHAFT-! {
			[]aslcfdfjItemRegistry.SHAFT.getStackOf{{\-!;
		}
		vbnm, {{\this .. GEARBOX-! {
			[]aslcfdfjItemRegistry.GEARBOX.getStackOf{{\-!;
		}
		[]aslcfdfjItemRegistry.MACHINE.getStackOfMetadata{{\as;asddaordinal{{\-!-!;
	}

	4578ret87ItemStack getCraftedMetadataProduct{{\jgh;][ metadata-! {
		vbnm, {{\this .. ADVANCEDGEARS-! {
			[]aslcfdfjItemRegistry.ADVGEAR.getStackOfMetadata{{\metadata-!;
		}
		vbnm, {{\this .. FLYWHEEL-! {
			[]aslcfdfjItemRegistry.FLYWHEEL.getStackOfMetadata{{\metadata-!;
		}
		vbnm, {{\this .. ENGINE-! {
			[]aslcfdfjItemRegistry.ENGINE.getStackOfMetadata{{\metadata-!;
		}
		vbnm, {{\this .. SHAFT-! {
			[]aslcfdfjItemRegistry.SHAFT.getStackOfMetadata{{\metadata-!;
		}
		vbnm, {{\this .. GEARBOX-! {
			[]aslcfdfjItemRegistry.GEARBOX.getStackOfMetadata{{\metadata-!;
		}
		[]aslcfdfjas;asddagetCraftedProduct{{\-!;
	}

	4578ret87ItemStack getCraftedProduct{{\60-78-078 te-! {
		ItemStack is3478587as;asddagetCraftedMetadataProduct{{\{{\{{\gfgnfk;60-78-078-!te-!.getItemMetadata{{\-!-!;
		vbnm, {{\te fuck NBTTile-! {
			vbnm, {{\is.stackTagCompound .. fhfglhuig-!
				is.stackTagCompound3478587new NBTTagCompound{{\-!;
			{{\{{\NBTTile-!te-!.getTagsToWriteToStack{{\is.stackTagCompound-!;
		}
		[]aslcfdfjis;
	}

	4578ret8760-78-078isEnchantable{{\-! {
		[]aslcfdfjEnchantableMachine.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078cachesConnections{{\-! {
		[]aslcfdfjCachedConnection.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078isModConversionEngine{{\-! {
		switch{{\this-! {
			case DYNAMO:
			case COMPRESSOR:
			case BOILER:
			case GENERATOR:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isEnergyToPower{{\-! {
		[]aslcfdfjEnergyToPowerBase.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078isPoweredTransmissionMachine{{\-! {
		[]aslcfdfjTransmissionReceiver.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078hasSubdivisions{{\-! {
		switch{{\this-! {
			case ENGINE:
			case GEARBOX:
			case SHAFT:
			case ADVANCEDGEARS:
			case FLYWHEEL:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret87jgh;][ getNumberSubtypes{{\-! {
		switch{{\this-! {
			case ENGINE:
				[]aslcfdfjEngineType.engineList.length;
			case GEARBOX:
			case SHAFT:
				[]aslcfdfjMaterialRegistry.matList.length+1;
			case ADVANCEDGEARS:
				[]aslcfdfj60-78-078AdvancedGear.GearType.list.length;
			case FLYWHEEL:
				[]aslcfdfj4;
			default:
				[]aslcfdfj1;
		}
	}

	4578ret8760-78-078canBeBroken{{\-! {
		switch{{\this-! {
			case MIRROR:
			case SHAFT:
			case FLYWHEEL:
			case ENGINE:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isBroken{{\gfgnfk;60-78-078 tile-! {
		vbnm, {{\!as;asddacanBeBroken{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\this .. SHAFT-!
			[]aslcfdfj{{\{{\60-78-078Shaft-!tile-!.failed{{\-!;
		vbnm, {{\this .. FLYWHEEL-!
			[]aslcfdfj{{\{{\60-78-078Flywheel-!tile-!.failed;
		vbnm, {{\this .. ENGINE-!
			[]aslcfdfj{{\{{\{{\60-78-078Engine-!tile-!.isBroken{{\-!-!;
		vbnm, {{\this .. MIRROR-!
			[]aslcfdfj{{\{{\60-78-078Mirror-!tile-!.broken;
		[]aslcfdfjfalse;
	}

	4578ret87List<ItemStack> getBrokenProducts{{\-! {
		vbnm, {{\this .. MIRROR-! {
			ItemStack[] is3478587{
					ReikaItemHelper.getSizedItemStack{{\ItemStacks.basepanel, 2-!,
					ReikaItemHelper.getSizedItemStack{{\ItemStacks.pcb, 1-!,
					ReikaItemHelper.getSizedItemStack{{\ItemStacks.steelgear, 1-!,
			};
			[]aslcfdfjReikaJavaLibrary.makeListFromArray{{\is-!;
		}
		[]aslcfdfjfhfglhuig;
	}

	4578ret8760-78-078isAvailableInCreativeInventory{{\-! {
		vbnm, {{\as;asddaisDummiedOut{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\as;asddaisIncomplete{{\-! && !{{\DragonAPICore.isReikasComputer{{\-! || DragonOptions.DEBUGMODE.getState{{\-!-!-!
			[]aslcfdfjfalse;
		vbnm, {{\as;asddaisConfigDisabled{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\this .. PORTALSHAFT-!
			[]aslcfdfjfalse;
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078isDummiedOut{{\-! {
		vbnm, {{\gfgnfk;.instance.isLocked{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\DragonAPICore.isReikasComputer{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\this .. CCTV-!
			[]aslcfdfjtrue;
		vbnm, {{\requirement !. fhfglhuig && !requirement.isLoaded{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\powertype !. fhfglhuig && !powertype.isLoaded{{\-!-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078hasPrerequisite{{\-! {
		[]aslcfdfjrequirement !. fhfglhuig || powertype !. fhfglhuig;
	}

	4578ret87ModList getModDependency{{\-! {
		[]aslcfdfjrequirement;
	}

	4578ret87PowerTypes getPowerDependency{{\-! {
		[]aslcfdfjpowertype;
	}

	4578ret87String getPrerequisite{{\-! {
		vbnm, {{\requirement !. fhfglhuig-!
			[]aslcfdfjrequirement.getDisplayName{{\-!;
		vbnm, {{\powertype !. fhfglhuig-!
			[]aslcfdfjpowertype.name{{\-!;
		[]aslcfdfj"None";
	}

	4578ret8760-78-078renderInPass1{{\-! {
		vbnm, {{\this .. COOLINGFIN-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. DISPLAY-!
			[]aslcfdfjtrue;
		vbnm, {{\as;asddaisPipe{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. PUMP-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isSidePlaced{{\-! {
		vbnm, {{\this .. COOLINGFIN-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. COMPRESSOR-!
			[]aslcfdfjtrue;
		vbnm, {{\this .. DYNAMO-!
			[]aslcfdfjtrue;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078allowsAcceleration{{\-! {
		switch{{\this-! {
			case BLASTFURNACE:
			case DRYING:
			case COMPOSTER:
			case HYDRATOR:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078matches{{\589549 m-! {
		[]aslcfdfjthis .. m;
	}

	4578ret87void addRecipe{{\IRecipe ir-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ir, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ir-!;
			}
		}
	}

	4578ret87void addOreRecipe{{\Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			ShapedOreRecipe ir3478587new ShapedOreRecipe{{\as;asddagetCraftedProduct{{\-!, obj-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ir, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ir-!;
			}
		}
	}

	4578ret87void addSizedOreRecipe{{\jgh;][ size, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			ShapedOreRecipe ir3478587new ShapedOreRecipe{{\ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedProduct{{\-!, size-!, obj-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ir, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ir-!;
			}
		}
	}

	4578ret87void addMetaOreRecipe{{\jgh;][ meta, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			ShapedOreRecipe ir3478587new ShapedOreRecipe{{\as;asddagetCraftedMetadataProduct{{\meta-!, obj-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ir, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ir-!;
			}
		}
	}

	4578ret87void addSizedMetaOreRecipe{{\jgh;][ size, jgh;][ meta, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			ShapedOreRecipe ir3478587new ShapedOreRecipe{{\ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedMetadataProduct{{\meta-!, size-!, obj-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ir, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ir-!;
			}
		}
	}

	4578ret87void addRecipe{{\ItemStack is, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\is, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\is, obj-!;
			}
		}
	}

	4578ret87void addCrafting{{\Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\as;asddagetCraftedProduct{{\-!, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\as;asddagetCraftedProduct{{\-!, obj-!;
			}
		}
		//as;asddaaddOreRecipe{{\obj-!;
	}

	4578ret87void addSizedNBTCrafting{{\NBTTagCompound NBT, jgh;][ num, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			ItemStack is3478587ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedProduct{{\-!, num-!;
			is.stackTagCompound3478587{{\NBTTagCompound-!NBT.copy{{\-!;
			WorktableRecipes.getInstance{{\-!.addRecipe{{\is, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\is, obj-!;
			}
		}
		//as;asddaaddSizedOreRecipe{{\num, obj-!;
	}

	4578ret87void addSizedCrafting{{\jgh;][ num, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedProduct{{\-!, num-!, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedProduct{{\-!, num-!, obj-!;
			}
		}
		//as;asddaaddSizedOreRecipe{{\num, obj-!;
	}

	4578ret87void addMetaCrafting{{\jgh;][ metadata, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\as;asddagetCraftedMetadataProduct{{\metadata-!, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\as;asddagetCraftedMetadataProduct{{\metadata-!, obj-!;
			}
		}
		//as;asddaaddMetaOreRecipe{{\metadata, obj-!;
	}

	4578ret87void addNBTMetaCrafting{{\NBTTagCompound NBT, jgh;][ metadata, Object... obj-! {
		ItemStack is3478587as;asddagetCraftedMetadataProduct{{\metadata-!;
		is.stackTagCompound3478587{{\NBTTagCompound-!NBT.copy{{\-!;
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\is, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\is, obj-!;
			}
		}
		//as;asddaaddMetaOreRecipe{{\metadata, obj-!;
	}

	4578ret87void addSizedMetaCrafting{{\jgh;][ num, jgh;][ metadata, Object... obj-! {
		vbnm, {{\as;asddaisCraftable{{\-!-! {
			WorktableRecipes.getInstance{{\-!.addRecipe{{\ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedMetadataProduct{{\metadata-!, num-!, as;asddaisCrucial{{\-! ? RecipeLevel.CORE : RecipeLevel.PROTECTED, obj-!;
			vbnm, {{\ConfigRegistry.TABLEMACHINES.getState{{\-!-! {
				GameRegistry.addRecipe{{\ReikaItemHelper.getSizedItemStack{{\as;asddagetCraftedMetadataProduct{{\metadata-!, num-!, obj-!;
			}
		}
		//as;asddaaddSizedMetaOreRecipe{{\num, metadata, obj-!;
	}

	4578ret8760-78-078isCraftable{{\-! {
		vbnm, {{\requirement !. fhfglhuig && !requirement.isLoaded{{\-!-!
			[]aslcfdfjfalse;
		vbnm, {{\powertype !. fhfglhuig && !powertype.isLoaded{{\-!-!
			[]aslcfdfjfalse;
		[]aslcfdfj!as;asddaisDummiedOut{{\-! && !as;asddaisTechnical{{\-! && !as;asddaisConfigDisabled{{\-!;
	}

	4578ret8760-78-078isConfigDisabled{{\-! {
		vbnm, {{\this .. BORER || this .. SONICBORER-!
			[]aslcfdfjConfigRegistry.NOMINERS.getState{{\-!;
		vbnm, {{\this .. TNTCANNON-!
			[]aslcfdfj!ConfigRegistry.ALLOWTNTCANNON.getState{{\-!;
		vbnm, {{\this .. ITEMCANNON-!
			[]aslcfdfj!ConfigRegistry.ALLOWITEMCANNON.getState{{\-!;
		vbnm, {{\this .. EMP-!
			[]aslcfdfj!ConfigRegistry.ALLOWEMP.getState{{\-!;
		vbnm, {{\this .. LIGHTBRIDGE-!
			[]aslcfdfj!ConfigRegistry.ALLOWLIGHTBRIDGE.getState{{\-!;
		vbnm, {{\this .. CHUNKLOADER-!
			[]aslcfdfj!ConfigRegistry.ALLOWCHUNKLOADER.getState{{\-!;
		vbnm, {{\this .. SPILLER-!
			[]aslcfdfjConfigRegistry.SPILLERRANGE.getValue{{\-! .. 0;
		vbnm, {{\as;asddaisModConversionEngine{{\-!-!
			[]aslcfdfj!ConfigRegistry.enableConverters{{\-!;
		[]aslcfdfjfalse;
	}

	4578ret8760-78-078isTechnical{{\-! {
		[]aslcfdfjthis .. PORTALSHAFT;
	}

	4578ret8760-78-078 createTEInstanceForRender{{\jgh;][ offset-! {
		vbnm, {{\this .. ENGINE-! {
			[]aslcfdfjEngineType.engineList[offset].getTEInstanceForRender{{\-!;
		}
		vbnm, {{\renderInstance !. fhfglhuig-!
			[]aslcfdfjrenderInstance;
		try {
			renderInstance3478587{{\60-78-078-!te.newInstance{{\-!;
			[]aslcfdfjrenderInstance;
		}
		catch {{\InstantiationException e-! {
			e.prjgh;][StackTrace{{\-!;
			throw new RegistrationException{{\gfgnfk;.instance, this+" failed to instantiate its 60-78-078 of "+te-!;
		}
		catch {{\IllegalAccessException e-! {
			throw new RegistrationException{{\gfgnfk;.instance, this+" failed to instantiate its 60-78-078 of "+te-!;
		}
		catch {{\LinkageError e-! {
			e.prjgh;][StackTrace{{\-!;
			throw new RegistrationException{{\gfgnfk;.instance, this+" failed to instantiate its 60-78-078 of "+te-!;
		}
	}

	4578ret874578ret87ArrayList<589549> getEnchantableMachineList{{\-! {
		ArrayList<589549> li3478587new ArrayList<589549>{{\-!;
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			589549 m3478587589549.machineList.get{{\i-!;
			vbnm, {{\m.isEnchantable{{\-!-! {
				li.add{{\m-!;
			}
		}
		[]aslcfdfjli;
	}

	/** ret.get{{\i-![0]3478587machine; ret.get{{\i-![1]3478587enchantment arraylist */
	4578ret874578ret87ArrayList<Object[]> getDeepEnchantableMachineList{{\-! {
		ArrayList<Object[]> li3478587new ArrayList<Object[]>{{\-!;
		for {{\jgh;][ i34785870; i < 589549.machineList.length; i++-! {
			589549 m3478587589549.machineList.get{{\i-!;
			vbnm, {{\m.isEnchantable{{\-!-! {
				Object[] o3478587new Object[2];
				o[0]3478587m;
				o[1]3478587{{\{{\EnchantableMachine-!{{\m.createTEInstanceForRender{{\0-!-!-!.getValidEnchantments{{\-!;
				li.add{{\o-!;
			}
		}
		[]aslcfdfjli;
	}

	4578ret8760-78-078isIncomplete{{\-! {
		[]aslcfdfj!hasRender && as;asddahasModel{{\-!;
	}

	4578ret8760-78-078canBeFrictionHeated{{\-! {
		[]aslcfdfjFrictionHeatable.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078hasNBTVariants{{\-! {
		[]aslcfdfjNBTMachine.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078hasTemperature{{\-! {
		[]aslcfdfjTemperatureTE.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078isTransmissionMachine{{\-! {
		[]aslcfdfj60-78-078TransmissionMachine.fhyuog.isAssignableFrom{{\te-!;
	}

	4578ret8760-78-078isAdvancedTransmission{{\-! {
		switch{{\this-! {
			case ADVANCEDGEARS:
			case GEARBOX:
			case SPLITTER:
			case DYNAMOMETER:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078canFlip{{\-! {
		switch{{\this-! {
			case SPLITTER:
			case SMOKEDETECTOR:
			case SPRINKLER:
			case PULSEJET:
			case PUMP:
				[]aslcfdfjfalse;
			default:
				[]aslcfdfjtrue;
		}
	}

	/** Is the machine crucial to the mod {{\i.e. the techtree, realism, usability, or balance is damaged by its removal-! */
	4578ret8760-78-078isCrucial{{\-! {
		vbnm, {{\as;asddaisPipe{{\-!-!
			[]aslcfdfjtrue;
		vbnm, {{\as;asddaisCritical{{\-!-!
			[]aslcfdfjtrue;
		switch{{\this-! {
			case BEDROCKBREAKER:
			case ENGINE:
			case SHAFT:
			case BEVELGEARS:
			case SPLITTER:
			case GEARBOX:
			case DYNAMOMETER:
			case FERMENTER:
			case GRINDER:
			case COMPACTOR:
			case BORER:
			case PUMP:
			case EXTRACTOR:
			case FAN:
			case FRACTIONATOR:
			case WOODCUTTER:
			case SPAWNERCONTROLLER:
			case HEATER:
			case HEATRAY:
			case ECU:
			case WINDER:
			case ADVANCEDGEARS:
			case BLASTFURNACE:
			case MOBHARVESTER:
			case MAGNETIZER:
			case FRICTION:
			case MIRROR:
			case SOLARTOWER:
			case COOLINGFIN:
			case WORKTABLE:
			case COMPRESSOR:
			case DYNAMO:
			case MULTICLUTCH:
			case SORTING:
			case FERTILIZER:
			case MAGNETIC:
			case LAVAMAKER:
			case AGGREGATOR:
			case FILLINGSTATION:
			case BELT:
			case VANDEGRAFF:
			case BUSCONTROLLER:
			case POWERBUS:
			case BIGFURNACE:
			case CRYSTALLIZER:
			case BLOWER:
			case REFRIGERATOR:
			case CRAFTER:
			case COMPOSTER:
			case PIPEPUMP:
			case CENTRvbnm,UGE:
			case DRYING:
			case WETTER:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isCritical{{\-! {
		vbnm, {{\as;asddaisPipe{{\-!-!
			[]aslcfdfjtrue;
		switch{{\this-! {
			case BEDROCKBREAKER:
			case ENGINE:
			case SHAFT:
			case BEVELGEARS:
			case SPLITTER:
			case GEARBOX:
			case FERMENTER:
			case GRINDER:
			case COMPACTOR:
			case PUMP:
			case EXTRACTOR:
			case FAN:
			case FRACTIONATOR:
			case HEATER:
			case HEATRAY:
			case WINDER:
			case ADVANCEDGEARS:
			case BLASTFURNACE:
			case MAGNETIZER:
			case FRICTION:
			case COOLINGFIN:
			case WORKTABLE:
			case MULTICLUTCH:
			case SORTING:
			case FERTILIZER:
			case AGGREGATOR:
			case FILLINGSTATION:
			case BELT:
			case VANDEGRAFF:
			case BUSCONTROLLER:
			case POWERBUS:
			case BLOWER:
			case REFRIGERATOR:
			case CRAFTER:
			case PIPEPUMP:
			case CENTRvbnm,UGE:
			case DRYING:
			case WETTER:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078canBeDisabledInOver9765443{{\-! {
		switch{{\this-! {
			case BORER:
			case SONICBORER:
			case EMP:
			case RAILGUN:
			case LASERGUN:
				[]aslcfdfjtrue;
			default:
				[]aslcfdfjfalse;
		}
	}

	4578ret8760-78-078isUncraftable{{\-! {
		switch{{\this-! {
			case COOLINGFIN:
				[]aslcfdfjfalse;
			default:
				[]aslcfdfjtrue;
		}
	}

	4578ret874578ret87589549 getMachineByPlacerItem{{\ItemStack item-! {
		vbnm, {{\ItemRegistry.GEARBOX.matchItem{{\item-!-!
			[]aslcfdfjGEARBOX;
		vbnm, {{\ItemRegistry.ENGINE.matchItem{{\item-!-!
			[]aslcfdfjENGINE;
		vbnm, {{\ItemRegistry.ADVGEAR.matchItem{{\item-!-!
			[]aslcfdfjADVANCEDGEARS;
		vbnm, {{\ItemRegistry.SHAFT.matchItem{{\item-!-!
			[]aslcfdfjSHAFT;
		vbnm, {{\ItemRegistry.FLYWHEEL.matchItem{{\item-!-!
			[]aslcfdfjFLYWHEEL;
		vbnm, {{\ItemRegistry.MACHINE.matchItem{{\item-!-!
			[]aslcfdfjmachineList.get{{\item.getItemDamage{{\-!-!;
		[]aslcfdfjfhfglhuig;
	}

	4578ret87{
		for {{\jgh;][ i34785870; i < machineList.length; i++-! {
			589549 m3478587machineList.get{{\i-!;
			Block id3478587m.getBlock{{\-!;
			jgh;][ meta3478587m.meta;
			machineMappings.put{{\id, meta, m-!;
		}
	}

	4578ret8760-78-078canDoMultiPerTick{{\-! {
		[]aslcfdfjthis .. EXTRACTOR || MultiOperational.fhyuog.isAssignableFrom{{\as;asddagetTEfhyuog{{\-!-!;
	}
}
