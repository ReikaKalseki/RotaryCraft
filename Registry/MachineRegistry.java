/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Instantiable.WorldLocation;
import Reika.DragonAPI.Instantiable.Data.BlockMap;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Auxiliary.Interfaces.CachedConnection;
import Reika.RotaryCraft.Auxiliary.Interfaces.DamagingContact;
import Reika.RotaryCraft.Auxiliary.Interfaces.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.FrictionHeatable;
import Reika.RotaryCraft.Auxiliary.Interfaces.NBTMachine;
import Reika.RotaryCraft.Auxiliary.Interfaces.TemperatureTE;
import Reika.RotaryCraft.Auxiliary.Interfaces.TransmissionReceiver;
import Reika.RotaryCraft.Base.BlockModelledMultiTE;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityAimedCannon;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Base.TileEntity.TileEntityTransmissionMachine;
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
import Reika.RotaryCraft.ModInterface.TileEntityDynamo;
import Reika.RotaryCraft.ModInterface.TileEntityElectricMotor;
import Reika.RotaryCraft.ModInterface.TileEntityFuelConverter;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.ModInterface.TileEntityGenerator;
import Reika.RotaryCraft.ModInterface.TileEntityMagnetic;
import Reika.RotaryCraft.ModInterface.TileEntityPneumaticEngine;
import Reika.RotaryCraft.ModInterface.TileEntitySteam;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityBlower;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityChunkLoader;
import Reika.RotaryCraft.TileEntities.TileEntityGrindstone;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySmokeDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySorting;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFillingStation;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityFurnaceHeater;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityMirror;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityPipePump;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityDisplay;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityFireworkMachine;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityParticleEmitter;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityWeatherController;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFan;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFertilizer;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityLawnSprinkler;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityMobHarvester;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityWoodcutter;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityBypass;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityHose;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.Piping.TileEntitySeparatorPipe;
import Reika.RotaryCraft.TileEntities.Piping.TileEntitySuctionPipe;
import Reika.RotaryCraft.TileEntities.Piping.TileEntityValve;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityAutoCrafter;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityBigFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCentrifuge;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCrystallizer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDistillery;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityMagnetizer;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.Production.TileEntityAggregator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBedrockBreaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.Production.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.Production.TileEntityLavaMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntityPump;
import Reika.RotaryCraft.TileEntities.Production.TileEntityRefrigerator;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityFluidCompressor;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntitySpyCam;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBevelGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBusController;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityChainDrive;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMonitor;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityMultiClutch;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPortalShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityPowerBus;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityAAGun;
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
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityVanDeGraff;
import Reika.RotaryCraft.TileEntities.World.TileEntityBeamMirror;
import Reika.RotaryCraft.TileEntities.World.TileEntityDefoliator;
import Reika.RotaryCraft.TileEntities.World.TileEntityFlooder;
import Reika.RotaryCraft.TileEntities.World.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.World.TileEntityIgniter;
import Reika.RotaryCraft.TileEntities.World.TileEntityLamp;
import Reika.RotaryCraft.TileEntities.World.TileEntityLightBridge;
import Reika.RotaryCraft.TileEntities.World.TileEntityLineBuilder;
import Reika.RotaryCraft.TileEntities.World.TileEntityPileDriver;
import Reika.RotaryCraft.TileEntities.World.TileEntitySonicBorer;
import Reika.RotaryCraft.TileEntities.World.TileEntityTerraformer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/** ONLY ADD NEW MACHINES TO THE BOTTOM OF THIS LIST */
public enum MachineRegistry {

	BEDROCKBREAKER(		"machine.bedrock", 			BlockDMMachine.class,		TileEntityBedrockBreaker.class,		0,	"RenderBedrockBreaker"),
	ENGINE(				"machine.engine", 			BlockEngine.class,			TileEntityEngine.class,				0,	"RenderSEngine"),
	FLYWHEEL(			"machine.flywheel", 		BlockFlywheel.class,		TileEntityFlywheel.class,			0,	"RenderFlywheel"),
	SHAFT(				"machine.shaft", 			BlockShaft.class,			TileEntityShaft.class,				0,	"RenderShaft"),
	BEVELGEARS(			"machine.bevel", 			BlockTrans.class,			TileEntityBevelGear.class,			0,	"RenderBevel"),
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
	LIGHTBRIDGE(		"machine.lightbridge", 		BlockDMMachine.class,		TileEntityLightBridge.class,		3,	"RenderBridge"),
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
	CONTAINMENT(		"machine.containment",		BlockMMachine.class,		TileEntityContainment.class,		10,	"RenderProtectionDome"),
	SCREEN(				"machine.screen",			BlockDMIMachine.class,		TileEntityScreen.class,				6,	"RenderCCTVScreen"),
	PURIFIER(			"machine.purifier",			BlockIMachine.class,		TileEntityPurifier.class,			4),
	LASERGUN(			"machine.lasergun",			BlockMMachine.class,		TileEntityLaserGun.class,			11, "RenderLaserGun"),
	ITEMCANNON(			"machine.itemcannon",		BlockMIMachine.class,		TileEntityItemCannon.class,			15, "RenderItemCannon"),
	LANDMINE(			"machine.landmine",			BlockMIMachine.class,		TileEntityLandmine.class,			16, "RenderLandmine"),
	FRICTION(			"machine.friction",			BlockDMMachine.class,		TileEntityFurnaceHeater.class,		8, 	"RenderFriction"),
	BLOCKCANNON(		"machine.blockcannon",		BlockMIMachine.class,		TileEntityBlockCannon.class,		17, "RenderCannon"),
	BUCKETFILLER(		"machine.bucketfiller",		BlockIMachine.class,		TileEntityBucketFiller.class,		5),
	MIRROR(				"machine.mirror",			BlockSolar.class,			TileEntityMirror.class,				0,	"RenderMirror"),
	SOLARTOWER(			"machine.solartower",		BlockSolar.class,			TileEntitySolar.class,				1,	"RenderSolar"),
	SPYCAM(				"machine.spycam",			BlockMIMachine.class,		TileEntitySpyCam.class,				18,	"RenderSpyCam"),
	SELFDESTRUCT(		"machine.selfdestruct",		BlockMachine.class,			TileEntitySelfDestruct.class,		3),
	COOLINGFIN(			"machine.coolingfin",		BlockDMMachine.class,		TileEntityCoolingFin.class,			9, 	"RenderFin"),
	WORKTABLE(			"machine.worktable",		BlockIMachine.class,		TileEntityWorktable.class,			6),
	COMPRESSOR(			"machine.compressor", 		BlockModEngine.class,		TileEntityAirCompressor.class,		0, 	"RenderCompressor", ModList.BCENERGY),
	PNEUENGINE(			"machine.pneuengine",		BlockModEngine.class,		TileEntityPneumaticEngine.class,	1, 	"RenderPneumatic", ModList.BCENERGY),
	DISPLAY(			"machine.display",			BlockMMachine.class,		TileEntityDisplay.class,			12, "RenderDisplay"),
	LAMP(				"machine.lamp",				BlockMachine.class,			TileEntityLamp.class,				4),
	EMP(				"machine.emp",				BlockMMachine.class,		TileEntityEMP.class,				14, "RenderEMP"),
	LINEBUILDER(		"machine.linebuilder",		BlockDMIMachine.class,		TileEntityLineBuilder.class,		7, 	"RenderLineBuilder"),
	BEAMMIRROR(			"machine.beammirror",		BlockDMMachine.class,		TileEntityBeamMirror.class,			11, "RenderBeamMirror"),
	MULTICLUTCH(		"machine.multiclutch",		BlockTrans.class,			TileEntityMultiClutch.class,		4, 	"RenderMultiClutch"),
	TERRAFORMER(		"machine.terraformer",		BlockMachine.class,			TileEntityTerraformer.class,		6),
	SORTING(			"machine.sorting",			BlockDMachine.class,		TileEntitySorting.class,			2),
	FUELENHANCER(		"machine.fuelenhancer",		BlockMMachine.class,		TileEntityFuelConverter.class,		13, "RenderFuelConverter", ModList.BCENERGY),
	ARROWGUN(			"machine.arrowgun",			BlockDMachine.class,		TileEntityMachineGun.class,			1),
	BOILER(				"machine.frictionboiler", 	BlockMMachine.class, 		TileEntityBoiler.class, 			15, "RenderBoiler", ModList.RAILCRAFT),
	STEAMTURBINE(		"machine.steamturbine", 	BlockDMMachine.class, 		TileEntitySteam.class, 				10, "RenderSteam", ModList.RAILCRAFT),
	FERTILIZER(			"machine.fertilizer",		BlockMIMachine.class,		TileEntityFertilizer.class,			19, "RenderFertilizer"),
	LAVAMAKER(			"machine.lavamaker",		BlockMIMachine.class,		TileEntityLavaMaker.class,			20, "RenderRockMelter"),
	GENERATOR(			"machine.generator",		BlockModEngine.class,		TileEntityGenerator.class,			2, 	"RenderGenerator", ModList.UE),
	ELECTRICMOTOR(		"machine.electricmotor",	BlockModEngine.class,		TileEntityElectricMotor.class,		3, 	"RenderElecMotor", ModList.UE),
	VALVE(				"machine.valve",			BlockPiping.class,			TileEntityValve.class,				4, 	"PipeRenderer"),
	BYPASS(				"machine.bypass",			BlockPiping.class,			TileEntityBypass.class,				5, 	"PipeRenderer"),
	SEPARATION(			"machine.separation",		BlockPiping.class,			TileEntitySeparatorPipe.class,		6, 	"PipeRenderer"),
	AGGREGATOR(			"machine.aggregator",		BlockMMachine.class,		TileEntityAggregator.class,			16, "RenderAggregator"),
	AIRGUN(				"machine.airgun",			BlockDMMachine.class,		TileEntityAirGun.class,				12, "RenderAirGun"),
	SONICBORER(			"machine.sonicborer",		BlockDMMachine.class,		TileEntitySonicBorer.class,			13, "RenderSonicBorer"),
	FUELENGINE(			"machine.fuelengine",		BlockModEngine.class,		TileEntityFuelEngine.class,			4, 	"RenderFuelEngine", ModList.BCENERGY),
	FILLINGSTATION(		"machine.fillingstation",	BlockDMIMachine.class,		TileEntityFillingStation.class,		8, "RenderFillingStation"),
	BELT(				"machine.belt",				BlockDMMachine.class,		TileEntityBeltHub.class,			14, "RenderBelt"),
	VANDEGRAFF(			"machine.vandegraff",		BlockMMachine.class,		TileEntityVanDeGraff.class,			17, "RenderVanDeGraff"),
	DEFOLIATOR(			"machine.defoliator",		BlockMIMachine.class,		TileEntityDefoliator.class,			21, "RenderDefoliator"),
	BIGFURNACE(			"machine.bigfurnace",		BlockMIMachine.class,		TileEntityBigFurnace.class,			22, "RenderBigFurnace"),
	DISTILLER(			"machine.distiller",		BlockMMachine.class,		TileEntityDistillery.class,			18, "RenderDistillery"),
	SUCTION(			"machine.suction",			BlockPiping.class,			TileEntitySuctionPipe.class,		7, "PipeRenderer"),
	DYNAMO(				"machine.dynamo", 			BlockModEngine.class,		TileEntityDynamo.class,				5, "RenderDynamo", ModList.THERMALEXPANSION),
	MAGNETIC(			"machine.magnetic",			BlockModEngine.class,		TileEntityMagnetic.class,			6, "RenderMagnetic", ModList.THERMALEXPANSION),
	CRYSTALLIZER(		"machine.crystal",			BlockDMIMachine.class,		TileEntityCrystallizer.class,		9, "RenderCrystal"),
	BUSCONTROLLER(		"machine.buscontroller",	BlockDMachine.class,		TileEntityBusController.class,		3),
	POWERBUS(			"machine.bus",				BlockMachine.class,			TileEntityPowerBus.class,			5),
	PARTICLE(			"machine.particle",			BlockMachine.class,			TileEntityParticleEmitter.class,	7),
	LAWNSPRINKLER(		"machine.lawnsprinkler",	BlockMMachine.class, 		TileEntityLawnSprinkler.class,		19, "RenderLawnSprinkler"),
	GRINDSTONE(			"machine.grindstone",		BlockDMIMachine.class,		TileEntityGrindstone.class,			10, "RenderGrindstone"),
	BLOWER(				"machine.blower",			BlockDMachine.class,		TileEntityBlower.class,				4),
	PORTALSHAFT(		"machine.portalshaft",		BlockDMMachine.class,		TileEntityPortalShaft.class,		15,	"RenderPortalShaft"),
	REFRIGERATOR(		"machine.refrigerator",		BlockDMIMachine.class,		TileEntityRefrigerator.class,		11,	"RenderFridge"),
	GASTANK(			"machine.gastank",			BlockMMachine.class,		TileEntityFluidCompressor.class,		20, "RenderGasCompressor"),
	CRAFTER(			"machine.crafter",			BlockIMachine.class,		TileEntityAutoCrafter.class,		7),
	COMPOSTER(			"machine.composter",		BlockMIMachine.class,		TileEntityComposter.class,			23, "RenderComposter"),
	ANTIAIR(			"machine.antiair",			BlockMIMachine.class,		TileEntityAAGun.class,				24, "RenderAAGun"),
	PIPEPUMP(			"machine.pipepump",			BlockDMMachine.class,		TileEntityPipePump.class,			16,	"RenderPipePump"),
	CHAIN(				"machine.chain",			BlockDMMachine.class,		TileEntityChainDrive.class,			17, "RenderBelt"),
	CENTRIFUGE(			"machine.centrifuge",		BlockMIMachine.class,		TileEntityCentrifuge.class,			25, "RenderCentrifuge");

	private final String name;
	private final Class te;
	private final BlockRegistry block;
	private final Class blockClass;
	private final int meta;
	private boolean hasRender = false;
	private String renderClass;
	private ModList requirement;
	private PowerReceivers receiver;
	private TileEntity renderInstance;

	public static final MachineRegistry[] machineList = values();
	private static final BlockMap<MachineRegistry> machineMappings = new BlockMap();

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m) {
		name = n;
		blockClass = b;
		block = BlockRegistry.getBlockFromClassAndOffset(b, m/16);
		if (block == null) {
			throw new RegistrationException(RotaryCraft.instance, "Machine "+this.name()+" registered with a null block!");
		}
		te = tile;
		meta = m%16;

		receiver = PowerReceivers.initialize(this);
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, ModList a) {
		this(n, b, tile, m);
		requirement = a;

		receiver = PowerReceivers.initialize(this);
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, String r) {
		this(n, b, tile, m);
		hasRender = true;
		renderClass = r;

		receiver = PowerReceivers.initialize(this);
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, String r, ModList a) {
		this(n, b, tile, m, r);
		requirement = a;

		receiver = PowerReceivers.initialize(this);
	}

	public static TileEntity createTEFromIDAndMetadata(Block id, int metad) {
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
		return this.getRenderPackage()+"."+renderClass;
	}

	public String getRenderPackage() {
		if (this.hasPrerequisite() || BlockModEngine.class.isAssignableFrom(blockClass)) {
			return "Reika.RotaryCraft.ModInterface";
		}

		if (blockClass == BlockTrans.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockEngine.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockSolar.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockFlywheel.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockGearbox.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockShaft.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockAdvGear.class)
			return "Reika.RotaryCraft.Renders";
		if (blockClass == BlockPiping.class)
			return "Reika.RotaryCraft.Renders";

		String base = "Reika.RotaryCraft.Renders";
		String app = ".";
		app += this.getBlockType();
		return base+app;
	}

	public String getBlockType() {
		return blockClass.getSimpleName().replaceAll("Block", "").replaceAll("Machine", "");
	}

	public Block getBlock() {
		return this.getBlockRegistryEntry().getBlockInstance();
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

	public PowerReceivers getPowerReceiverEntry() {
		return receiver;
	}

	public static int getMachineIndexFromIDandMetadata(Block id, int metad) {
		MachineRegistry m = getMachineMapping(id, metad);
		return m != null ? m.ordinal() : -1;
	}

	public static MachineRegistry getMachine(WorldLocation loc) {
		return getMachine(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord);
	}

	public static MachineRegistry getMachine(IBlockAccess world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (b == Blocks.air)
			return null;
		return getMachineMapping(b, meta);
	}

	/** A convenience feature */
	public static MachineRegistry getMachine(IBlockAccess world, double x, double y, double z) {
		return getMachine(world, (int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
	}

	public static MachineRegistry getMachineFromIDandMetadata(Block id, int metad) {
		return getMachineMapping(id, metad);
	}

	private static MachineRegistry getMachineMapping(Block id, int meta) {
		if (id == BlockRegistry.GPR.getBlockInstance())
			return GPR;
		if (id == BlockRegistry.SHAFT.getBlockInstance())
			return SHAFT;
		if (id == BlockRegistry.ENGINE.getBlockInstance())
			return ENGINE;
		if (id == BlockRegistry.GEARBOX.getBlockInstance())
			return GEARBOX;
		return machineMappings.get(id, meta);
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
		if (this == PNEUENGINE)
			return 0.8125F;
		if (this == FERTILIZER)
			return 0.875F;
		if (this == DEFOLIATOR)
			return 0.625F;
		if (this == LAWNSPRINKLER)
			return 0.75F;
		if (this == GRINDSTONE)
			return 0.9375F;
		if (this == COMPOSTER)
			return 0.75F;
		if (this == CENTRIFUGE)
			return 0.375F;
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
		case GPR:
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

	public BlockRegistry getBlockRegistryEntry() {
		return block;
	}

	public boolean isPipe() {
		return BlockPiping.class.isAssignableFrom(blockClass);
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
			return RotaryNames.getGearboxName(gbx.getBlockMetadata()/4*5+gbx.getGearboxType().ordinal());
		}
		if (this == ENGINE) {
			TileEntityEngine eng = (TileEntityEngine)tile;
			return RotaryNames.getEngineName(eng.getEngineType().ordinal());
		}
		if (this == SHAFT) {
			TileEntityShaft sha = (TileEntityShaft)tile;
			return RotaryNames.getShaftName(sha.getShaftType().ordinal());
		}
		if (this == FLYWHEEL) {
			TileEntityFlywheel fly = (TileEntityFlywheel)tile;
			return RotaryNames.getFlywheelName(fly.getBlockMetadata()/4);
		}
		if (this == ADVANCEDGEARS) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)tile;
			return RotaryNames.getAdvGearName(adv.getBlockMetadata()/4);
		}
		//if (this == HYDRAULIC) {
		//	TileEntityHydraulicPump hyd = (TileEntityHydraulicPump)tile;
		//	return RotaryNames.getHydraulicName(hyd.getBlockMetadata()/6);
		//}
		throw new RegistrationException(RotaryCraft.instance, "Machine "+this.getName()+" has an unspecified multi name!");
	}

	public boolean isMultiNamed() {
		switch(this) {
		case ENGINE:
		case GEARBOX:
		case SHAFT:
		case ADVANCEDGEARS:
		case FLYWHEEL:
			//case HYDRAULIC:
			return true;
		default:
			return false;
		}
	}

	public boolean isPowerReceiver() {
		return TileEntityPowerReceiver.class.isAssignableFrom(te);
	}

	public boolean dealsContactDamage() {
		return DamagingContact.class.isAssignableFrom(te);
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
		case FRICTION:
			return true;
		default:
			return false;
		}
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
		case DYNAMO:
		case BLOWER:
		case PIPEPUMP:
		case CHAIN:
		case CLUTCH:
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
		case GRINDSTONE:
			return true;
		default:
			return false;
		}
	}

	public boolean isCannon() {
		return TileEntityAimedCannon.class.isAssignableFrom(te);
	}

	public boolean hasModel() {
		if (this.isPipe())
			return true;
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
		switch(this) {
		case ENGINE:
		case SHAFT:
		case GEARBOX:
		case ADVANCEDGEARS:
		case FLYWHEEL:
			//case HYDRAULIC:
			return true;
		default:
			return false;
		}
	}

	public ItemStack getCraftedProduct() {
		if (this == ADVANCEDGEARS) {
			return ItemRegistry.ADVGEAR.getStackOf();
		}
		if (this == FLYWHEEL) {
			return ItemRegistry.FLYWHEEL.getStackOf();
		}
		if (this == ENGINE) {
			return ItemRegistry.ENGINE.getStackOf();
		}
		if (this == SHAFT) {
			return ItemRegistry.SHAFT.getStackOf();
		}
		if (this == GEARBOX) {
			return ItemRegistry.GEARBOX.getStackOf();
		}
		return ItemRegistry.MACHINE.getStackOfMetadata(this.ordinal());
	}

	public ItemStack getCraftedMetadataProduct(int metadata) {
		if (this == ADVANCEDGEARS) {
			return ItemRegistry.ADVGEAR.getStackOfMetadata(metadata);
		}
		if (this == FLYWHEEL) {
			return ItemRegistry.FLYWHEEL.getStackOfMetadata(metadata);
		}
		if (this == ENGINE) {
			return ItemRegistry.ENGINE.getStackOfMetadata(metadata);
		}
		if (this == SHAFT) {
			return ItemRegistry.SHAFT.getStackOfMetadata(metadata);
		}
		if (this == GEARBOX) {
			return ItemRegistry.GEARBOX.getStackOfMetadata(metadata);
		}
		return this.getCraftedProduct();
	}

	public boolean isEnchantable() {
		return EnchantableMachine.class.isAssignableFrom(te);
	}

	public boolean cachesConnections() {
		return CachedConnection.class.isAssignableFrom(te);
	}

	public boolean isModConversionEngine() {
		switch(this) {
		case DYNAMO:
		case COMPRESSOR:
		case BOILER:
		case GENERATOR:
			return true;
		default:
			return false;
		}
	}

	public boolean isEnergyToPower() {
		return EnergyToPowerBase.class.isAssignableFrom(te);
	}

	public boolean isPoweredTransmissionMachine() {
		return TransmissionReceiver.class.isAssignableFrom(te);
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

	public int getNumberSubtypes() {
		switch(this) {
		case ENGINE:
			return EngineType.engineList.length;
		case GEARBOX:
		case SHAFT:
			return MaterialRegistry.matList.length+1;
		case ADVANCEDGEARS:
			return TileEntityAdvancedGear.GearType.list.length;
		case FLYWHEEL:
			return 4;
		default:
			return 1;
		}
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
			return ((TileEntityShaft)tile).failed();
		if (this == FLYWHEEL)
			return ((TileEntityFlywheel)tile).failed;
		if (this == ENGINE)
			return (((TileEntityEngine)tile).isBroken());
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
		if (this.isIncomplete() && !(DragonAPICore.isReikasComputer() || DragonOptions.DEBUGMODE.getState()))
			return false;
		if (this == PORTALSHAFT)
			return false;
		return true;
	}

	public boolean isDummiedOut() {
		if (RotaryCraft.instance.isLocked())
			return true;
		if (DragonAPICore.isReikasComputer())
			return false;
		if (this == CCTV)
			return true;
		if (this == CHUNKLOADER)
			return true;
		if (this == SPILLER)
			return true;
		if (this.hasPrerequisite() && !this.getPrerequisite().isLoaded())
			return true;
		if (this.hasPrerequisite() && this.getPrerequisite() == ModList.MEKANISM)
			return true;
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
		if (this == DYNAMO)
			return true;
		return false;
	}

	public boolean allowsAcceleration() {
		switch(this) {
		case BLASTFURNACE:
			return true;
		default:
			return false;
		}
	}

	public boolean matches(MachineRegistry m) {
		return this == m;
	}

	public void addRecipe(IRecipe ir) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(ir);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ir);
			}
		}
	}

	public void addOreRecipe(Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			ShapedOreRecipe ir = new ShapedOreRecipe(this.getCraftedProduct(), obj);
			WorktableRecipes.getInstance().addRecipe(ir);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ir);
			}
		}
	}

	public void addSizedOreRecipe(int size, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			ShapedOreRecipe ir = new ShapedOreRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedProduct(), size), obj);
			WorktableRecipes.getInstance().addRecipe(ir);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ir);
			}
		}
	}

	public void addMetaOreRecipe(int meta, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			ShapedOreRecipe ir = new ShapedOreRecipe(this.getCraftedMetadataProduct(meta), obj);
			WorktableRecipes.getInstance().addRecipe(ir);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ir);
			}
		}
	}

	public void addSizedMetaOreRecipe(int size, int meta, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			ShapedOreRecipe ir = new ShapedOreRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedMetadataProduct(meta), size), obj);
			WorktableRecipes.getInstance().addRecipe(ir);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ir);
			}
		}
	}

	public void addRecipe(ItemStack is, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(is, obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(is, obj);
			}
		}
	}

	public void addCrafting(Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(this.getCraftedProduct(), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(this.getCraftedProduct(), obj);
			}
		}
		//this.addOreRecipe(obj);
	}

	public void addSizedCrafting(int num, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedProduct(), num), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedProduct(), num), obj);
			}
		}
		//this.addSizedOreRecipe(num, obj);
	}

	public void addMetaCrafting(int metadata, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(this.getCraftedMetadataProduct(metadata), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(this.getCraftedMetadataProduct(metadata), obj);
			}
		}
		//this.addMetaOreRecipe(metadata, obj);
	}

	public void addNBTMetaCrafting(NBTTagCompound NBT, int metadata, Object... obj) {
		ItemStack is = this.getCraftedMetadataProduct(metadata);
		is.stackTagCompound = (NBTTagCompound)NBT.copy();
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(is, obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(is, obj);
			}
		}
		//this.addMetaOreRecipe(metadata, obj);
	}

	public void addSizedMetaCrafting(int num, int metadata, Object... obj) {
		if (!this.isDummiedOut() && this.isCraftable()) {
			WorktableRecipes.getInstance().addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedMetadataProduct(metadata), num), obj);
			if (ConfigRegistry.TABLEMACHINES.getState()) {
				GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(this.getCraftedMetadataProduct(metadata), num), obj);
			}
		}
		//this.addSizedMetaOreRecipe(num, metadata, obj);
	}

	public boolean isCraftable() {
		return !this.isDummiedOut() && !this.isTechnical();
	}

	public boolean isTechnical() {
		return this == PORTALSHAFT;
	}

	public TileEntity createTEInstanceForRender(int offset) {
		if (this == ENGINE) {
			return EngineType.engineList[offset].getTEInstanceForRender();
		}
		if (renderInstance != null)
			return renderInstance;
		try {
			renderInstance = (TileEntity)te.newInstance();
			return renderInstance;
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
				o[1] = ((EnchantableMachine)(m.createTEInstanceForRender(0))).getValidEnchantments();
				li.add(o);
			}
		}
		return li;
	}

	public boolean isIncomplete() {
		return !hasRender && this.hasModel();
	}

	public boolean canBeFrictionHeated() {
		return FrictionHeatable.class.isAssignableFrom(te);
	}

	public boolean hasNBTVariants() {
		return NBTMachine.class.isAssignableFrom(te);
	}

	public boolean hasTemperature() {
		return TemperatureTE.class.isAssignableFrom(te);
	}

	public boolean isTransmissionMachine() {
		return TileEntityTransmissionMachine.class.isAssignableFrom(te);
	}

	public boolean isAdvancedTransmission() {
		switch(this) {
		case ADVANCEDGEARS:
		case GEARBOX:
		case SPLITTER:
			return true;
		default:
			return false;
		}
	}

	public boolean canFlip() {
		switch(this) {
		case SPLITTER:
			return false;
		default:
			return true;
		}
	}

	/** Is the machine crucial to the mod (i.e. the techtree, realism, usability, or balance is damaged by its removal) */
	public boolean isCrucial() {
		if (this.isPipe())
			return true;
		switch(this) {
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
			return true;
		default:
			return false;
		}
	}

	public boolean isCritical() {
		if (this.isPipe())
			return true;
		switch(this) {
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
			return true;
		default:
			return false;
		}
	}

	public boolean canBeDisabledInOverworld() {
		switch(this) {
		case BORER:
		case SONICBORER:
		case EMP:
		case RAILGUN:
		case LASERGUN:
			return true;
		default:
			return false;
		}
	}

	static {
		for (int i = 0; i < machineList.length; i++) {
			MachineRegistry m = machineList[i];
			Block id = m.getBlock();
			int meta = m.meta;
			machineMappings.put(id, meta, m);
		}
	}
}