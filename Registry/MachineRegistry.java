/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Base.BlockModelledMultiTE;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntityIOMachine;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Blocks.BlockAdvGear;
import Reika.RotaryCraft.Blocks.BlockDMIMachine;
import Reika.RotaryCraft.Blocks.BlockDMMachine;
import Reika.RotaryCraft.Blocks.BlockDMachine;
import Reika.RotaryCraft.Blocks.BlockEngine;
import Reika.RotaryCraft.Blocks.BlockFlywheel;
import Reika.RotaryCraft.Blocks.BlockGPR;
import Reika.RotaryCraft.Blocks.BlockGearbox;
import Reika.RotaryCraft.Blocks.BlockIMachine;
import Reika.RotaryCraft.Blocks.BlockMIMachine;
import Reika.RotaryCraft.Blocks.BlockMMachine;
import Reika.RotaryCraft.Blocks.BlockMachine;
import Reika.RotaryCraft.Blocks.BlockModEngine;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.Blocks.BlockShaft;
import Reika.RotaryCraft.Blocks.BlockSolar;
import Reika.RotaryCraft.Blocks.BlockTrans;
import Reika.RotaryCraft.ModInterface.TileEntityAirCompressor;
import Reika.RotaryCraft.ModInterface.TileEntityBoiler;
import Reika.RotaryCraft.ModInterface.TileEntityElectricMotor;
import Reika.RotaryCraft.ModInterface.TileEntityFuelConverter;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.ModInterface.TileEntityGenerator;
import Reika.RotaryCraft.ModInterface.TileEntityPneumaticEngine;
import Reika.RotaryCraft.ModInterface.TileEntitySteam;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityBeamMirror;
import Reika.RotaryCraft.TileEntities.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.TileEntityBridgeEmitter;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityChunkLoader;
import Reika.RotaryCraft.TileEntities.TileEntityDisplay;
import Reika.RotaryCraft.TileEntities.TileEntityFireworkMachine;
import Reika.RotaryCraft.TileEntities.TileEntityFlooder;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityIgniter;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityLamp;
import Reika.RotaryCraft.TileEntities.TileEntityLineBuilder;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.TileEntityPileDriver;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.TileEntitySmokeDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySonicBorer;
import Reika.RotaryCraft.TileEntities.TileEntitySorting;
import Reika.RotaryCraft.TileEntities.TileEntityTerraformer;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWeatherController;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFillingStation;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFurnaceHeater;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFan;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFertilizer;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityMobHarvester;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityWoodcutter;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityBypass;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHose;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.Piping.TileEntitySeparatorPipe;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityValve;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityMagnetizer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.Production.TileEntityAggregator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBedrockBreaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntitySpyCam;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearBevel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMonitor;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityAirGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityBlockCannon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityFreezeGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLandmine;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityLaserGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityMachineGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityRailGun;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySelfDestruct;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityTNTCannon;
import cpw.mods.fml.common.registry.GameRegistry;

/** ONLY ADD NEW MACHINES TO THE BOTTOM OF THIS LIST */
public enum MachineRegistry {

	BEDROCKBREAKER(		"machine.bedrock", 			BlockDMMachine.class,		TileEntityBedrockBreaker.class,		0,	"RenderBedrockBreaker"),
	ENGINE(				"machine.engine", 			BlockEngine.class,			TileEntityEngine.class,				0,	"RenderSEngine"),
	FLYWHEEL(			"machine.flywheel", 		BlockFlywheel.class,		TileEntityFlywheel.class,			0,	"RenderFlywheel"),
	SHAFT(				"machine.shaft", 			BlockShaft.class,			TileEntityShaft.class,				0,	"RenderShaft"),
	BEVELGEARS(			"machine.bevel", 			BlockTrans.class,			TileEntityGearBevel.class,			0,	"RenderBevel"),
	GEARBOX(			"machine.gearbox", 			BlockGearbox.class,			TileEntityGearbox.class,			0,	"RenderGearbox"),
	SPLITTER(			"machine.splitter", 		BlockTrans.class,			TileEntitySplitter.class,			1,	"RenderSplitter"),
	FERMENTER(			"machine.fermenter", 		BlockIMachine.class,		TileEntityFermenter.class,			0),
	FLOODLIGHT(			"machine.floodlight", 		BlockDMMachine.class,		TileEntityFloodlight.class,			1,	"RenderLamp"),
	CLUTCH(				"machine.clutch", 			BlockTrans.class,			TileEntityClutch.class,				2,	"RenderClutch"),
	DYNAMOMETER(		"machine.dyna", 			BlockTrans.class,			TileEntityMonitor.class,			3,	"RenderMonitor"),
	GRINDER(			"machine.grinder", 			BlockDMIMachine.class,		TileEntityGrinder.class,			0,	"RenderGrinder"),
	HEATRAY(			"machine.heatray", 			BlockDMMachine.class,		TileEntityHeatRay.class,			2,	"RenderHRay"),
	HOSE(				"machine.hose", 			BlockPiping.class,			TileEntityHose.class,				0,	"PipeRenderer"),
	BORER(				"machine.borer", 			BlockDMachine.class,		TileEntityBorer.class,				0),
	LIGHTBRIDGE(		"machine.lightbridge", 		BlockDMMachine.class,		TileEntityBridgeEmitter.class,		3,	"RenderBridge"),
	PUMP(				"machine.pump", 			BlockDMMachine.class,		TileEntityPump.class,				4,	"RenderPump"),
	PIPE(				"machine.pipe", 			BlockPiping.class,			TileEntityPipe.class,				1,	"PipeRenderer"),
	RESERVOIR(			"machine.reservoir", 		BlockMMachine.class,		TileEntityReservoir.class,			0,	"RenderReservoir"),
	AEROSOLIZER(		"machine.aerosolizer", 		BlockMIMachine.class,		TileEntityAerosolizer.class,		0,	"RenderAerosolizer"),
	EXTRACTOR(			"machine.extractor", 		BlockMIMachine.class,		TileEntityExtractor.class,			1,	"RenderExtractor"),
	PULSEJET(			"machine.pulsejet", 		BlockMIMachine.class,		TileEntityPulseFurnace.class,		2,	"RenderPulseFurnace"),
	COMPACTOR(			"machine.compactor", 		BlockDMIMachine.class,		TileEntityCompactor.class,			1,	"RenderCompactor"),
	FAN(				"machine.fan", 				BlockDMMachine.class,		TileEntityFan.class,				5,	"RenderFan"),
	FUELLINE(			"machine.fuelline", 		BlockPiping.class,			TileEntityFuelLine.class,			2,	"PipeRenderer"),
	FRACTIONATOR(		"machine.fractionator", 	BlockMIMachine.class,		TileEntityFractionator.class,		3,	"RenderFraction"),
	GPR(				"machine.gpr",				BlockGPR.class,				TileEntityGPR.class,				0),
	OBSIDIAN(			"machine.obsidian", 		BlockMIMachine.class,		TileEntityObsidianMaker.class,		4,	"RenderObsidian"),
	PILEDRIVER(			"machine.piledriver", 		BlockDMMachine.class,		TileEntityPileDriver.class,			6,	"RenderPileDriver"),
	VACUUM(				"machine.vacuum", 			BlockMIMachine.class,		TileEntityVacuum.class,				5,	"RenderVacuum"),
	FIREWORK(			"machine.firework", 		BlockIMachine.class,		TileEntityFireworkMachine.class,	1),
	SPRINKLER(			"machine.sprinkler", 		BlockMMachine.class,		TileEntitySprinkler.class,			1,	"RenderSprinkler"),
	WOODCUTTER(			"machine.woodcutter", 		BlockDMMachine.class,		TileEntityWoodcutter.class,			7,	"RenderWoodcutter"),
	SPAWNERCONTROLLER(	"machine.spawnercontroller",BlockMMachine.class,		TileEntitySpawnerController.class,	2,	"RenderSpawner"),
	PLAYERDETECTOR(		"machine.playerdetector", 	BlockMMachine.class,		TileEntityPlayerDetector.class,		3,	"RenderDetector"),
	HEATER(				"machine.heater", 			BlockMIMachine.class,		TileEntityHeater.class,				6,	"RenderHeater"),
	BAITBOX(			"machine.baitbox", 			BlockMIMachine.class,		TileEntityBaitBox.class,			7,	"RenderBaitBox"),
	AUTOBREEDER(		"machine.breeder", 			BlockMIMachine.class,		TileEntityAutoBreeder.class,		8,	"RenderBreeder"),
	ECU(				"machine.ecu", 				BlockMachine.class,			TileEntityEngineController.class,	0),
	SMOKEDETECTOR(		"machine.smokedetector", 	BlockMIMachine.class,		TileEntitySmokeDetector.class,		9,	"RenderSmokeDetector"),
	MOBRADAR(			"machine.mobradar", 		BlockMMachine.class,		TileEntityMobRadar.class,			4,	"RenderMobRadar"),
	WINDER(				"machine.winder", 			BlockDMIMachine.class,		TileEntityWinder.class,				2,	"RenderWinder"),
	ADVANCEDGEARS(		"machine.advgear", 			BlockAdvGear.class,			TileEntityAdvancedGear.class,		0,	"RenderAdvGear"),
	TNTCANNON(			"machine.tntcannon", 		BlockMIMachine.class,		TileEntityTNTCannon.class,			10,	"RenderCannon"),
	SONICWEAPON(		"machine.sonicweapon", 		BlockMMachine.class,		TileEntitySonicWeapon.class,		5,	"RenderSonic"),
	BLASTFURNACE(		"machine.blastfurnace", 	BlockIMachine.class,		TileEntityBlastFurnace.class,		2),
	FORCEFIELD(			"machine.forcefield", 		BlockMMachine.class,		TileEntityForceField.class,			6,	"RenderForceField"),
	MUSICBOX(			"machine.musicbox", 		BlockMachine.class,			TileEntityMusicBox.class,			1),
	SPILLER(			"machine.spiller", 			BlockMachine.class,			TileEntityFlooder.class,			8),
	CHUNKLOADER(		"machine.chunkloader", 		BlockMMachine.class,		TileEntityChunkLoader.class,		7,	"RenderChunkLoader"),
	MOBHARVESTER(		"machine.mobharvester", 	BlockMMachine.class,		TileEntityMobHarvester.class,		8,	"RenderHarvester"),
	CCTV(				"machine.cctv", 			BlockMIMachine.class,		TileEntityCCTV.class,				11,	"RenderCCTV"),
	PROJECTOR(			"machine.projector", 		BlockDMIMachine.class,		TileEntityProjector.class,			3,	"RenderProjector"),
	RAILGUN(			"machine.railgun", 			BlockMIMachine.class,		TileEntityRailGun.class,			12,	"RenderRailGun"),
	WEATHERCONTROLLER(	"machine.weather", 			BlockMIMachine.class,		TileEntityWeatherController.class,	13,	"RenderIodide"),
	REFRESHER(			"machine.refresher", 		BlockMachine.class,			TileEntityItemRefresher.class,		2),
	FREEZEGUN(			"machine.freezegun", 		BlockMIMachine.class,		TileEntityFreezeGun.class,			14,	"RenderFreezeGun"),
	CAVESCANNER(		"machine.cavescanner", 		BlockMMachine.class,		TileEntityCaveFinder.class,			9,	"RenderCaveFinder"),
	SCALECHEST(			"machine.chest", 			BlockDMIMachine.class,		TileEntityScaleableChest.class,		4,	"RenderScaleChest"),
	IGNITER(			"machine.firestarter", 		BlockIMachine.class,		TileEntityIgniter.class,			3),
	MAGNETIZER(			"machine.magnetizer",		BlockDMIMachine.class,		TileEntityMagnetizer.class,			5,	"RenderMagnetizer"),
	CONTAINMENT(		"machine.containment",		BlockMMachine.class,		TileEntityContainment.class,		10,	"RenderContainment"),
	SCREEN(				"machine.screen",			BlockDMIMachine.class,		TileEntityScreen.class,				6,	"RenderCCTVScreen"),
	PURIFIER(			"machine.purifier",			BlockIMachine.class,		TileEntityPurifier.class,			4),
	LASERGUN(			"machine.lasergun",			BlockMMachine.class,		TileEntityLaserGun.class,			11, "RenderLaserGun"),
	ITEMCANNON(			"machine.itemcannon",		BlockMIMachine.class,		TileEntityItemCannon.class,			15, "RenderItemCannon"),
	LANDMINE(			"machine.landmine",			BlockMIMachine.class,		TileEntityLandmine.class,			16, "RenderLandmine"),
	FRICTION(			"machine.friction",			BlockDMMachine.class,		TileEntityFurnaceHeater.class,		8, "RenderFriction"),
	BLOCKCANNON(		"machine.blockcannon",		BlockMIMachine.class,		TileEntityBlockCannon.class,		17, "RenderCannon"),
	BUCKETFILLER(		"machine.bucketfiller",		BlockIMachine.class,		TileEntityBucketFiller.class,		5),
	MIRROR(				"machine.mirror",			BlockSolar.class,			TileEntityMirror.class,				0,	"RenderMirror"),
	SOLARTOWER(			"machine.solartower",		BlockSolar.class,			TileEntitySolar.class,				1,	"RenderSolar"),
	SPYCAM(				"machine.spycam",			BlockMIMachine.class,		TileEntitySpyCam.class,				18,	"RenderSpyCam"),
	SELFDESTRUCT(		"machine.selfdestruct",		BlockMachine.class,			TileEntitySelfDestruct.class,		3),
	COOLINGFIN(			"machine.coolingfin",		BlockDMMachine.class,		TileEntityCoolingFin.class,			9, "RenderFin"),
	WORKTABLE(			"machine.worktable",		BlockIMachine.class,		TileEntityWorktable.class,			6),
	COMPRESSOR(			"machine.compressor", 		BlockModEngine.class,		TileEntityAirCompressor.class,		0, "RenderCompressor", ModList.BUILDCRAFTENERGY),
	PNEUENGINE(			"machine.pneuengine",		BlockModEngine.class,		TileEntityPneumaticEngine.class,	1, "RenderPneumatic", ModList.BUILDCRAFTENERGY),
	DISPLAY(			"machine.display",			BlockMMachine.class,		TileEntityDisplay.class,			12, "RenderDisplay"),
	LAMP(				"machine.lamp",				BlockMachine.class,			TileEntityLamp.class,				4),
	EMP(				"machine.emp",				BlockMMachine.class,		TileEntityEMP.class,				14, "RenderEMP"),
	LINEBUILDER(		"machine.linebuilder",		BlockDMIMachine.class,		TileEntityLineBuilder.class,		7, "RenderLineBuilder"),
	BEAMMIRROR(			"machine.beammirror",		BlockDMMachine.class,		TileEntityBeamMirror.class,			11, "RenderBeamMirror"),
	MULTICLUTCH(		"machine.multiclutch",		BlockTrans.class,			TileEntityMultiClutch.class,		4, "RenderMultiClutch"),
	TERRAFORMER(		"machine.terraformer",		BlockMachine.class,			TileEntityTerraformer.class,		6),
	SORTING(			"machine.sorting",			BlockDMachine.class,		TileEntitySorting.class,			2),
	FUELENHANCER(		"machine.fuelenhancer",		BlockMMachine.class,		TileEntityFuelConverter.class,		13, "RenderFuelConverter", ModList.BUILDCRAFTENERGY),
	ARROWGUN(			"machine.arrowgun",			BlockDMachine.class,		TileEntityMachineGun.class,			1),
	BOILER(				"machine.frictionboiler", 	BlockMMachine.class, 		TileEntityBoiler.class, 			15, "RenderBoiler", ModList.RAILCRAFT),
	STEAMTURBINE(		"machine.steamturbine", 	BlockDMMachine.class, 		TileEntitySteam.class, 				10, "RenderSteam", ModList.RAILCRAFT),
	FERTILIZER(			"machine.fertilizer",		BlockMIMachine.class,		TileEntityFertilizer.class,			19, "RenderFertilizer"),
	LAVAMAKER(			"machine.lavamaker",		BlockMIMachine.class,		TileEntityLavaMaker.class,			20, "RenderRockMelter"),
	GENERATOR(			"machine.generator",		BlockModEngine.class,		TileEntityGenerator.class,			2, "RenderGenerator", ModList.MEKANISM),
	ELECTRICMOTOR(		"machine.electricmotor",	BlockModEngine.class,		TileEntityElectricMotor.class,		3, "RenderElecMotor", ModList.MEKANISM),
	VALVE(				"machine.valve",			BlockPiping.class,			TileEntityValve.class,				4, "PipeRenderer"),
	BYPASS(				"machine.bypass",			BlockPiping.class,			TileEntityBypass.class,				5, "PipeRenderer"),
	SEPARATION(			"machine.separation",		BlockPiping.class,			TileEntitySeparatorPipe.class,		6, "PipeRenderer"),
	AGGREGATOR(			"machine.aggregator",		BlockMMachine.class,		TileEntityAggregator.class,			16, "RenderAggregator"),
	AIRGUN(				"machine.airgun",			BlockDMMachine.class,		TileEntityAirGun.class,				12, "RenderAirGun"),
	SONICBORER(			"machine.sonicborer",		BlockDMMachine.class,		TileEntitySonicBorer.class,			13, "RenderSonicBorer"),
	FUELENGINE(			"machine.fuelengine",		BlockModEngine.class,		TileEntityFuelEngine.class,			4, "RenderFuelEngine", ModList.BUILDCRAFTENERGY),
	FILLINGSTATION(		"machine.fillingstation",	BlockMMachine.class,		TileEntityFillingStation.class,		17, "RenderFillingStation"),
	BELT(				"machine.belt",				BlockDMMachine.class,		TileEntityBeltHub.class,			14);

	private String name;
	private Class te;
	private Class blockClass;
	private int meta;
	private boolean hasRender = false;
	private String renderClass;
	private int rollover;
	private ModList requirement;
	private static HashMap<List<Integer>, MachineRegistry> machineMappings = new HashMap();

	public static final MachineRegistry[] machineList = MachineRegistry.values();

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m) {
		name = n;
		blockClass = b;
		meta = m;
		te = tile;
		if (meta > 15)
			//	throw new RegistrationException(RotaryCraft.instance, "Machine "+name+" assigned to metadata > 15 for Block "+blockClass);
			rollover = m/16;
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, ModList a) {
		this(n, b, tile, m);
		requirement = a;
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, String r) {
		this(n, b, tile, m);
		hasRender = true;
		renderClass = r;
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, String r, ModList a) {
		this(n, b, tile, m, r);
		requirement = a;
	}

	public static TileEntity createTEFromIDAndMetadata(int id, int metad) {
		int index = getMachineIndexFromIDandMetadata(id, metad);
		if (index == -1) {
			RotaryCraft.logger.logError("ID "+id+" and metadata "+metad+" are not a valid machine identification pair!");
			return null;
		}
		Class TEClass = machineList[index].te;
		try {
			return (TileEntity)TEClass.newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			throw new RegistrationException(RotaryCraft.instance, "ID "+id+" and metadata "+metad+" failed to instantiate their TileEntity of "+TEClass);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RegistrationException(RotaryCraft.instance, "ID "+id+" and metadata "+metad+" failed illegally accessed their TileEntity of "+TEClass);
		}
	}

	public boolean hasRender() {
		return hasRender;
	}

	public String getRenderer() {
		if (!hasRender)
			throw new RuntimeException("Machine "+this.getName()+" has no render to call!");
		return this.getRenderPackage()+renderClass;
	}

	public String getRenderPackage() {
		if (this.hasPrerequisite() || BlockModEngine.class.isAssignableFrom(blockClass)) {
			return "Reika.RotaryCraft.ModInterface.";
		}
		switch(this) {
		default:
			return "Reika.RotaryCraft.Renders.";
		}
	}

	public int getBlockOffset() {
		return rollover;
	}

	public int getBlockID() {
		return this.getBlockVariable().blockID;
	}

	public int getNumberDirections() {
		if (this.is2Sided())
			return 2;
		if (this.is4Sided())
			return 4;
		if (this.is6Sided())
			return 6;
		return 1;
	}

	public static int getMachineIndexFromIDandMetadata(int id, int metad) {
		metad += BlockRegistry.getOffsetFromBlockID(id)*16;
		MachineRegistry m = getMachineMapping(id, metad);
		if (m == null)
			return -1;
		return m.ordinal();
	}

	public static MachineRegistry getMachine(IBlockAccess world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (id == 0)
			return null;
		BlockRegistry r = BlockRegistry.getMachineBlock(id);
		if (r == null)
			return null;
		meta += BlockRegistry.getOffsetFromBlockID(id)*16;
		return getMachineMapping(id, meta);
	}

	/** A convenience feature */
	public static MachineRegistry getMachine(IBlockAccess world, double x, double y, double z) {
		return getMachine(world, (int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
	}

	public static MachineRegistry getMachineFromIDandMetadata(int id, int metad) {
		int index = getMachineIndexFromIDandMetadata(id, metad);
		if (index == -1) {
			RotaryCraft.logger.logError("ID "+id+" and metadata "+metad+" are not a valid machine identification pair!");
			return null;
		}
		return machineList[index];
	}

	public int getMachineMetadata() {
		return meta;
	}

	public String getName() {
		//return LanguageRegistry.instance().getStringLocalization("rcmachine."+this.name().toLowerCase());
		return StatCollector.translateToLocal(name);
		//return name;
	}

	public String getDefaultName() {
		return this.getName();
	}

	public float getMinX(RotaryCraftTileEntity tile) {
		if (this == SPRINKLER)
			return 0.3125F;
		if (this == WOODCUTTER && tile.getBlockMetadata() == 0)
			return 0.0625F;
		if (this == SMOKEDETECTOR)
			return 0.25F;
		if (this == CCTV)
			return 0.25F;
		if (this == SCALECHEST)
			return 0.0625F;
		return 0;
	}

	public float getMinY(RotaryCraftTileEntity tile) {
		if (this == SPRINKLER)
			return 0.4375F;
		if (this == SMOKEDETECTOR)
			return 0.875F;
		if (this == SPYCAM)
			return 0.375F;
		if (this == CCTV && tile.getBlockMetadata() == 1)
			return 0.5F-0.5F*(float)Math.sin(Math.toRadians(((TileEntityCCTV)tile).theta));
		return 0;
	}

	public float getMinZ(RotaryCraftTileEntity tile) {
		if (this == SPRINKLER)
			return 0.3125F;
		if (this == WOODCUTTER && tile.getBlockMetadata() == 2)
			return 0.0625F;
		if (this == SMOKEDETECTOR)
			return 0.25F;
		if (this == CCTV)
			return 0.25F;
		if (this == SCALECHEST)
			return 0.0625F;
		return 0;
	}

	public float getMaxX(RotaryCraftTileEntity tile) {
		if (this == SPRINKLER)
			return 0.6875F;
		if (this == SMOKEDETECTOR)
			return 0.75F;
		if (this == CCTV)
			return 0.75F;
		if (this == WOODCUTTER && tile.getBlockMetadata() == 1)
			return 0.9375F;
		if (this == SCALECHEST)
			return 0.9375F;
		return 1;
	}

	public float getMaxY(RotaryCraftTileEntity tile) {
		if (this == FLOODLIGHT) {
			if (((TileEntityFloodlight)tile).beammode)
				return 1;
			return 0.875F;
		}
		if (this == CCTV && tile.getBlockMetadata() == 0)
			return 0.5F-0.5F*(float)Math.sin(Math.toRadians(((TileEntityCCTV)tile).theta));
		if (this == GRINDER)
			return 0.8125F;
		if (this == HEATRAY)
			return 0.6875F;
		if (this == LIGHTBRIDGE)
			return 0.6875F;
		if (this == PUMP)
			return 0.75F;
		if (this == AEROSOLIZER)
			return 0.875F;
		if (this == PULSEJET)
			return 0.5625F;
		if (this == HEATER)
			return 0.5F;
		if (this == AUTOBREEDER)
			return 0.5F;
		if (this == OBSIDIAN)
			return 0.75F;
		if (this == WOODCUTTER)
			return 0.875F;
		if (this == SPAWNERCONTROLLER)
			return 0.375F;
		if (this == PLAYERDETECTOR)
			return 0.6875F;
		if (this == MOBRADAR)
			return 0.75F;
		if (this == WINDER)
			return 0.8125F;
		if (this == TNTCANNON)
			return 0.9375F;
		if (this == MOBHARVESTER)
			return 0.999F;
		if (this == PROJECTOR)
			return 0.8125F;
		if (this == WEATHERCONTROLLER)
			return 0.675F;
		if (this == MAGNETIZER)
			return 0.9375F;
		if (this == SCALECHEST)
			return 0.875F;
		if (this == LANDMINE)
			return 0.4375F;
		if (this == BLOCKCANNON)
			return 0.9375F;
		if (this == EMP)
			return 0.5F;
		if (this == FERTILIZER)
			return 0.875F;
		return 1;
	}

	public float getMaxZ(RotaryCraftTileEntity tile) {
		if (this == SPRINKLER)
			return 0.6875F;
		if (this == SMOKEDETECTOR)
			return 0.75F;
		if (this == CCTV)
			return 0.75F;
		if (this == WOODCUTTER && tile.getBlockMetadata() == 3)
			return 0.9375F;
		if (this == SCALECHEST)
			return 0.9375F;
		return 1;
	}

	public boolean hasSneakActions() {
		switch(this) {
		case CAVESCANNER:
		case SCREEN:
			return true;
		default:
			return false;
		}
	}

	public boolean isXFlipped() {
		if (this == BEDROCKBREAKER)
			return true;
		return false;
	}

	public boolean isZFlipped() {
		switch(this) {
		case BEDROCKBREAKER:
		case FLOODLIGHT:
		case LIGHTBRIDGE:
		case HEATRAY:
		case FAN:
		case PROJECTOR:
		case SCALECHEST:
			return true;
		default:
			return false;
		}
	}

	public Block getBlockVariable() {
		return BlockRegistry.blockList[this.getBlockVariableIndex()].getBlockVariable();
	}

	public int getBlockVariableIndex() {
		return BlockRegistry.getBlockVariableIndexFromClassAndMetadata(blockClass, meta);
	}

	public boolean isPipe() {
		return BlockPiping.class.isAssignableFrom(blockClass);
	}

	public boolean isTrans() {
		return (this == DYNAMOMETER || this == CLUTCH || this == BEVELGEARS || this == SPLITTER);
	}

	public boolean hasInv() {
		if (BlockIMachine.class.isAssignableFrom(blockClass))
			return true;
		if (BlockDMIMachine.class.isAssignableFrom(blockClass))
			return true;
		if (BlockMIMachine.class.isAssignableFrom(blockClass))
			return true;
		return false;
	}

	public Class getTEClass() {
		return te;
	}

	public String getMultiName(RotaryCraftTileEntity tile) {
		if (!this.isMultiNamed())
			throw new RuntimeException("Machine "+this.getName()+" has no multi name and yet was called for it!");
		if (this == GEARBOX) {
			TileEntityGearbox gbx = (TileEntityGearbox)tile;
			return RotaryNames.getGearboxName(gbx.getBlockMetadata()/4*5+gbx.type.ordinal());
		}
		if (this == ENGINE) {
			TileEntityEngine eng = (TileEntityEngine)tile;
			return RotaryNames.getEngineName(eng.type.ordinal());
		}
		if (this == SHAFT) {
			TileEntityShaft sha = (TileEntityShaft)tile;
			return RotaryNames.getShaftName(sha.type.ordinal());
		}
		if (this == FLYWHEEL) {
			TileEntityFlywheel fly = (TileEntityFlywheel)tile;
			return RotaryNames.getFlywheelName(fly.getBlockMetadata()/4);
		}
		if (this == ADVANCEDGEARS) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)tile;
			return RotaryNames.getAdvGearName(adv.getBlockMetadata()/4);
		}
		throw new RegistrationException(RotaryCraft.instance, "Machine "+this.getName()+" has an unspecified multi name!");
	}

	public boolean isMultiNamed() {
		switch(this) {
		case ENGINE:
		case GEARBOX:
		case SHAFT:
		case ADVANCEDGEARS:
		case FLYWHEEL:
			return true;
		default:
			return false;
		}
	}

	public boolean isPowerReceiver() {
		return (TileEntityPowerReceiver.class.isAssignableFrom(te));
	}

	public boolean dealsContactDamage(Entity e) {
		if (e instanceof EntityItem || e instanceof EntityXPOrb)
			return false;
		if (this == GRINDER)
			return true;
		if (this == WOODCUTTER)
			return true;
		if (this == FRICTION)
			return true;
		return false;
	}

	public boolean dealsHeatDamage(Entity e) {
		if (e instanceof EntityItem || e instanceof EntityXPOrb)
			return false;
		switch(this) {
		case COMPACTOR:
		case HEATER:
		case IGNITER:
		case OBSIDIAN:
		case PULSEJET:
			return true;
		default:
			return false;
		}
	}

	public int getContactDamage(RotaryCraftTileEntity tile) {
		if (this == WOODCUTTER) {
			if (((TileEntityWoodcutter)tile).power <= 0)
				return 0;
			return 3;
		}
		if (this == GRINDER) {
			if (((TileEntityGrinder)tile).power <= 0)
				return 0;
			return 2;
		}
		if (this == FRICTION) {
			if (((TileEntityFurnaceHeater)tile).power <= 0)
				return 0;
			return 1;
		}
		return 0;
	}

	public boolean is4Sided() {
		switch(this) {
		case ENGINE:
		case BORER:
		case LIGHTBRIDGE:
		case FLYWHEEL:
		case GEARBOX:
		case SPLITTER:
		case FERMENTER:
		case CLUTCH:
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
			return true;
		default:
			return false;
		}
	}

	public boolean is6Sided() {
		switch(this) {
		case SHAFT:
		case BEDROCKBREAKER:
		case FLOODLIGHT:
		case FAN:
		case COOLINGFIN:
		case COMPRESSOR:
		case LINEBUILDER:
		case SONICBORER:
		case BELT:
			return true;
		default:
			return false;
		}
	}

	public boolean is2Sided() {
		if (this.isCannon())
			return true;
		switch(this) {
		case PILEDRIVER:
		case GPR:
		case PUMP:
			return true;
		default:
			return false;
		}
	}

	public boolean isCannon() {
		return TileEntityAimedCannon.class.isAssignableFrom(te);
	}

	public boolean hasModel() {
		switch(this) {
		case ENGINE:
		case SHAFT:
		case GEARBOX:
		case FLYWHEEL:
		case ADVANCEDGEARS:
			return true;
		default:
			return (BlockModelledMultiTE.class.isAssignableFrom(blockClass));
		}
	}

	public boolean hasCustomPlacerItem() {
		if (this.isPipe())
			return true;
		switch(this) {
		case ENGINE:
		case SHAFT:
		case GEARBOX:
		case ADVANCEDGEARS:
		case FLYWHEEL:
			return true;
		default:
			return false;
		}
	}

	public ItemStack getCraftedProduct() {
		if (this == HOSE)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 0);
		if (this == PIPE)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1);
		if (this == FUELLINE)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 2);
		if (this == VALVE)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 4);
		if (this == BYPASS)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 5);
		if (this == SEPARATION)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 6);
		return new ItemStack(RotaryCraft.machineplacer.itemID, 1, this.ordinal());
	}

	public ItemStack getCraftedMetadataProduct(int metadata) {
		if (this == ADVANCEDGEARS) {
			return new ItemStack(RotaryCraft.advgearitems.itemID, 1, metadata);
		}
		if (this == FLYWHEEL) {
			return new ItemStack(RotaryCraft.flywheelitems.itemID, 1, metadata);
		}
		if (this == ENGINE) {
			return new ItemStack(RotaryCraft.engineitems.itemID, 1, metadata);
		}
		if (this == SHAFT) {
			return new ItemStack(RotaryCraft.shaftitems.itemID, 1, metadata);
		}
		if (this == GEARBOX) {
			return new ItemStack(RotaryCraft.gbxitems.itemID, 1, metadata);
		}
		return null;
	}

	public boolean isEnchantable() {
		return EnchantableMachine.class.isAssignableFrom(te);
	}

	public boolean hasSubdivisions() {
		switch(this) {
		case ENGINE:
		case GEARBOX:
		case SHAFT:
		case ADVANCEDGEARS:
		case FLYWHEEL:
			return true;
		default:
			return false;
		}
	}

	public String getManufacturerName() {
		return this.getManufacturer().getName();
	}

	public String getMachineDescription() {
		return this.getManufacturer().getPartDesc();
	}

	public Manufacturers getManufacturer() {
		if (Manufacturers.hasSubMakers(this))
			return Manufacturers.getSpecificMaker(this, 0);
		return Manufacturers.getMaker(this);
	}

	public boolean canBeBroken() {
		switch(this) {
		case MIRROR:
		case SHAFT:
		case FLYWHEEL:
		case ENGINE:
			return true;
		default:
			return false;
		}
	}

	public boolean isBroken(RotaryCraftTileEntity tile) {
		if (!this.canBeBroken())
			return false;
		if (this == SHAFT)
			return ((TileEntityShaft)tile).failed;
		if (this == FLYWHEEL)
			return ((TileEntityFlywheel)tile).failed;
		if (this == ENGINE)
			return (((TileEntityEngine)tile).FOD >= 8);
		if (this == MIRROR)
			return ((TileEntityMirror)tile).broken;
		return false;
	}

	public List<ItemStack> getBrokenProducts() {
		if (this == MIRROR) {
			ItemStack[] is = {
					ReikaItemHelper.getSizedItemStack(ItemStacks.basepanel, 2),
					ReikaItemHelper.getSizedItemStack(ItemStacks.pcb, 1),
					ReikaItemHelper.getSizedItemStack(ItemStacks.steelgear, 1),
			};
			return ReikaJavaLibrary.makeListFromArray(is);
		}
		return null;
	}

	public boolean isAvailableInCreativeInventory() {
		if (this.isDummiedOut())
			return false;
		if (this.isIncomplete() && !(DragonAPICore.isReikasComputer() || ConfigRegistry.DEBUGMODE.getState()))
			return false;
		return true;
	}

	public boolean isDummiedOut() {
		if (this == CCTV)
			return !DragonAPICore.isReikasComputer();
		if (this == CHUNKLOADER)
			return !DragonAPICore.isReikasComputer();
		if (this.hasPrerequisite() && !this.getPrerequisite().isLoaded())
			return true;
		if (this.hasPrerequisite() && this.getPrerequisite() == ModList.MEKANISM)
			return !DragonAPICore.isReikasComputer();
		return false;
	}

	public boolean hasPrerequisite() {
		return requirement != null;
	}

	public ModList getPrerequisite() {
		if (!this.hasPrerequisite())
			;//throw new RegistrationException(RotaryCraft.instance, this.getName()+" has no prerequisites and yet was called for them!");
		return requirement;
	}

	public boolean preReqSatisfied() {
		if (!this.hasPrerequisite())
			return true;
		return this.getPrerequisite().isLoaded();
	}

	public boolean renderInPass1() {
		if (this.hasModel() && TileEntityIOMachine.class.isAssignableFrom(te))
			return true;
		if (this == COOLINGFIN)
			return true;
		if (this == DISPLAY)
			return true;
		if (this.isPipe())
			return true;
		return false;
	}

	public boolean isSidePlaced() {
		if (this == COOLINGFIN)
			return true;
		if (this == COMPRESSOR)
			return true;
		return false;
	}

	public boolean matches(MachineRegistry m) {
		return this == m;
	}

	public void addRecipe(IRecipe ir) {
		if (!this.isDummiedOut()) {
			WorktableRecipes.getInstance().addRecipe(ir);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ir);
			}
		}
	}

	public void addRecipe(ItemStack is, Object... obj) {
		if (!this.isDummiedOut()) {
			WorktableRecipes.getInstance().addRecipe(is, obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(is, obj);
			}
		}
	}

	public void addCrafting(Object... obj) {
		if (!this.isDummiedOut()) {
			WorktableRecipes.getInstance().addRecipe(this.getCraftedProduct(), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(this.getCraftedProduct(), obj);
			}
		}
	}

	public void addSizedCrafting(int num, Object... obj) {
		if (!this.isDummiedOut()) {
			WorktableRecipes.getInstance().addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedProduct(), num), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedProduct(), num), obj);
			}
		}
	}

	public void addMetaCrafting(int metadata, Object... obj) {
		if (!this.isDummiedOut()) {
			WorktableRecipes.getInstance().addRecipe(this.getCraftedMetadataProduct(metadata), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(this.getCraftedMetadataProduct(metadata), obj);
			}
		}
	}

	public void addSizedMetaCrafting(int num, int metadata, Object... obj) {
		if (!this.isDummiedOut()) {
			WorktableRecipes.getInstance().addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedMetadataProduct(metadata), num), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedMetadataProduct(metadata), num), obj);
			}
		}
	}

	public TileEntity createTEInstanceForRender() {
		try {
			return (TileEntity)te.newInstance();
		}
		catch (InstantiationException e) {
			throw new RegistrationException(RotaryCraft.instance, this+" failed to instantiate its TileEntity of "+te);
		}
		catch (IllegalAccessException e) {
			throw new RegistrationException(RotaryCraft.instance, this+" failed to instantiate its TileEntity of "+te);
		}
	}

	public static ArrayList<MachineRegistry> getEnchantableMachineList() {
		ArrayList<MachineRegistry> li = new ArrayList<MachineRegistry>();
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.isEnchantable()) {
				li.add(m);
			}
		}
		return li;
	}

	/** ret.get(i)[0] = machine; ret.get(i)[1] = enchantment arraylist */
	public static ArrayList<Object[]> getDeepEnchantableMachineList() {
		ArrayList<Object[]> li = new ArrayList<Object[]>();
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			MachineRegistry m = MachineRegistry.machineList[i];
			if (m.isEnchantable()) {
				Object[] o = new Object[2];
				o[0] = m;
				o[1] = ((EnchantableMachine)(m.createTEInstanceForRender())).getValidEnchantments();
				li.add(o);
			}
		}
		return li;
	}

	public static MachineRegistry getMachineMapping(int id, int meta) {
		List li = Arrays.asList(id, meta);
		return machineMappings.get(li);
	}

	public boolean isIncomplete() {
		return !hasRender && this.hasModel();
	}

	static {
		for (int i = 0; i < machineList.length; i++) {
			MachineRegistry m = machineList[i];
			int id = m.getBlockID();
			int meta = m.getMachineMetadata();
			machineMappings.put(Arrays.asList(id, meta), m);
		}
	}
}
