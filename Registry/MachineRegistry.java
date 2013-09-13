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
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
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
import Reika.RotaryCraft.Blocks.BlockBCEngine;
import Reika.RotaryCraft.Blocks.BlockDMIMachine;
import Reika.RotaryCraft.Blocks.BlockDMMachine;
import Reika.RotaryCraft.Blocks.BlockDMachine;
import Reika.RotaryCraft.Blocks.BlockDeadMachine;
import Reika.RotaryCraft.Blocks.BlockEngine;
import Reika.RotaryCraft.Blocks.BlockFlywheel;
import Reika.RotaryCraft.Blocks.BlockGPR;
import Reika.RotaryCraft.Blocks.BlockGearbox;
import Reika.RotaryCraft.Blocks.BlockIMachine;
import Reika.RotaryCraft.Blocks.BlockMIMachine;
import Reika.RotaryCraft.Blocks.BlockMMachine;
import Reika.RotaryCraft.Blocks.BlockMachine;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.Blocks.BlockShaft;
import Reika.RotaryCraft.Blocks.BlockSolar;
import Reika.RotaryCraft.Blocks.BlockTrans;
import Reika.RotaryCraft.ModInterface.TileEntityAirCompressor;
import Reika.RotaryCraft.ModInterface.TileEntityFuelConverter;
import Reika.RotaryCraft.ModInterface.TileEntityLiquidConverter;
import Reika.RotaryCraft.ModInterface.TileEntityPneumaticEngine;
import Reika.RotaryCraft.TileEntities.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.TileEntityAerosolizer;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.TileEntityBedrockBreaker;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityBlockCannon;
import Reika.RotaryCraft.TileEntities.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.TileEntityBridgeEmitter;
import Reika.RotaryCraft.TileEntities.TileEntityBucketFiller;
import Reika.RotaryCraft.TileEntities.TileEntityCCTV;
import Reika.RotaryCraft.TileEntities.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.TileEntityChunkLoader;
import Reika.RotaryCraft.TileEntities.TileEntityClutch;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityCoolingFin;
import Reika.RotaryCraft.TileEntities.TileEntityDeadMachine;
import Reika.RotaryCraft.TileEntities.TileEntityDisplay;
import Reika.RotaryCraft.TileEntities.TileEntityEMP;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;
import Reika.RotaryCraft.TileEntities.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.TileEntityFan;
import Reika.RotaryCraft.TileEntities.TileEntityFermenter;
import Reika.RotaryCraft.TileEntities.TileEntityFireworkMachine;
import Reika.RotaryCraft.TileEntities.TileEntityFlooder;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.TileEntityFractionator;
import Reika.RotaryCraft.TileEntities.TileEntityFreezeGun;
import Reika.RotaryCraft.TileEntities.TileEntityFuelLine;
import Reika.RotaryCraft.TileEntities.TileEntityFurnaceHeater;
import Reika.RotaryCraft.TileEntities.TileEntityGPR;
import Reika.RotaryCraft.TileEntities.TileEntityGearBevel;
import Reika.RotaryCraft.TileEntities.TileEntityGearbox;
import Reika.RotaryCraft.TileEntities.TileEntityGrinder;
import Reika.RotaryCraft.TileEntities.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.TileEntityHose;
import Reika.RotaryCraft.TileEntities.TileEntityIgniter;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityLamp;
import Reika.RotaryCraft.TileEntities.TileEntityLandmine;
import Reika.RotaryCraft.TileEntities.TileEntityLaserGun;
import Reika.RotaryCraft.TileEntities.TileEntityLineBuilder;
import Reika.RotaryCraft.TileEntities.TileEntityMachineGun;
import Reika.RotaryCraft.TileEntities.TileEntityMagnetizer;
import Reika.RotaryCraft.TileEntities.TileEntityMirror;
import Reika.RotaryCraft.TileEntities.TileEntityMobHarvester;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.TileEntityMonitor;
import Reika.RotaryCraft.TileEntities.TileEntityMultiClutch;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPileDriver;
import Reika.RotaryCraft.TileEntities.TileEntityPipe;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityProjector;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPump;
import Reika.RotaryCraft.TileEntities.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.TileEntityRailGun;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.TileEntitySelfDestruct;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;
import Reika.RotaryCraft.TileEntities.TileEntitySmokeDetector;
import Reika.RotaryCraft.TileEntities.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.TileEntitySplitter;
import Reika.RotaryCraft.TileEntities.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.TileEntitySpyCam;
import Reika.RotaryCraft.TileEntities.TileEntityTNTCannon;
import Reika.RotaryCraft.TileEntities.TileEntityTerraformer;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
import Reika.RotaryCraft.TileEntities.TileEntityWeatherController;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;
import Reika.RotaryCraft.TileEntities.TileEntityWoodcutter;
import Reika.RotaryCraft.TileEntities.TileEntityWorktable;
import cpw.mods.fml.common.registry.GameRegistry;

/** ONLY ADD NEW MACHINES TO THE BOTTOM OF THIS LIST */
public enum MachineRegistry {

	BEDROCKBREAKER(		"Bedrock Breaker", 			BlockDMMachine.class,		TileEntityBedrockBreaker.class,		0,	"RenderBedrockBreaker"),
	ENGINE(				"Engine", 					BlockEngine.class,			TileEntityEngine.class,				0,	"RenderSEngine"),
	FLYWHEEL(			"Flywheel", 				BlockFlywheel.class,		TileEntityFlywheel.class,			0,	"RenderFlywheel"),
	SHAFT(				"Shaft", 					BlockShaft.class,			TileEntityShaft.class,				0,	"RenderShaft"),
	BEVELGEARS(			"Bevel Gears", 				BlockTrans.class,			TileEntityGearBevel.class,			0,	"RenderBevel"),
	GEARBOX(			"Gearbox", 					BlockGearbox.class,			TileEntityGearbox.class,			0,	"RenderGearbox"),
	SPLITTER(			"Shaft Junction", 			BlockTrans.class,			TileEntitySplitter.class,			1,	"RenderSplitter"),
	FERMENTER(			"Fermenter", 				BlockIMachine.class,		TileEntityFermenter.class,			0),
	FLOODLIGHT(			"Floodlight", 				BlockDMMachine.class,		TileEntityFloodlight.class,			1,	"RenderLamp"),
	CLUTCH(				"Clutch", 					BlockTrans.class,			TileEntityClutch.class,				2,	"RenderClutch"),
	DYNAMOMETER(		"Dynamometer", 				BlockTrans.class,			TileEntityMonitor.class,			3,	"RenderMonitor"),
	GRINDER(			"Grinder", 					BlockDMIMachine.class,		TileEntityGrinder.class,			0,	"RenderGrinder"),
	HEATRAY(			"Heat Ray", 				BlockDMMachine.class,		TileEntityHeatRay.class,			2,	"RenderHRay"),
	HOSE(				"Lubricant Hose", 			BlockPiping.class,			TileEntityHose.class,				0,	"PipeRenderer"),
	BORER(				"Boring Machine", 			BlockDMachine.class,		TileEntityBorer.class,				0),
	LIGHTBRIDGE(		"Light Bridge", 			BlockDMMachine.class,		TileEntityBridgeEmitter.class,		3,	"RenderBridge"),
	PUMP(				"Pump", 					BlockDMMachine.class,		TileEntityPump.class,				4,	"RenderPump"),
	PIPE(				"Liquid Pipe", 				BlockPiping.class,			TileEntityPipe.class,				1,	"PipeRenderer"),
	RESERVOIR(			"Reservoir", 				BlockMMachine.class,		TileEntityReservoir.class,			0,	"RenderReservoir"),
	AEROSOLIZER(		"Aerosolizer", 				BlockMIMachine.class,		TileEntityAerosolizer.class,		0,	"RenderAerosolizer"),
	EXTRACTOR(			"Extractor", 				BlockMIMachine.class,		TileEntityExtractor.class,			1,	"RenderExtractor"),
	PULSEJET(			"Pulse Jet Furnace", 		BlockMIMachine.class,		TileEntityPulseFurnace.class,		2,	"RenderPulseFurnace"),
	COMPACTOR(			"Compactor", 				BlockDMIMachine.class,		TileEntityCompactor.class,			1,	"RenderCompactor"),
	FAN(				"Fan", 						BlockDMMachine.class,		TileEntityFan.class,				5,	"RenderFan"),
	FUELLINE(			"Fuel Line", 				BlockPiping.class,			TileEntityFuelLine.class,			2,	"PipeRenderer"),
	FRACTIONATOR(		"Fractionation Unit", 		BlockMIMachine.class,		TileEntityFractionator.class,		3,	"RenderFraction"),
	GPR(				"Ground Penetrating Radar",	BlockGPR.class,				TileEntityGPR.class,				0),
	OBSIDIAN(			"Obsidian Factory", 		BlockMIMachine.class,		TileEntityObsidianMaker.class,		4,	"RenderObsidian"),
	PILEDRIVER(			"Pile Driver", 				BlockDMMachine.class,		TileEntityPileDriver.class,			6,	"RenderPileDriver"),
	VACUUM(				"Item Vacuum", 				BlockMIMachine.class,		TileEntityVacuum.class,				5,	"RenderVacuum"),
	FIREWORK(			"Fireworks Display", 		BlockIMachine.class,		TileEntityFireworkMachine.class,	1),
	SPRINKLER(			"Sprinkler", 				BlockMMachine.class,		TileEntitySprinkler.class,			1,	"RenderSprinkler"),
	WOODCUTTER(			"Woodcutter", 				BlockDMMachine.class,		TileEntityWoodcutter.class,			7,	"RenderWoodcutter"),
	SPAWNERCONTROLLER(	"Spawner Controller", 		BlockMMachine.class,		TileEntitySpawnerController.class,	2,	"RenderSpawner"),
	PLAYERDETECTOR(		"Player Detector", 			BlockMMachine.class,		TileEntityPlayerDetector.class,		3,	"RenderDetector"),
	HEATER(				"Heater", 					BlockMIMachine.class,		TileEntityHeater.class,				6,	"RenderHeater"),
	BAITBOX(			"Bait Box", 				BlockMIMachine.class,		TileEntityBaitBox.class,			7,	"RenderBaitBox"),
	AUTOBREEDER(		"Auto Breeder", 			BlockMIMachine.class,		TileEntityAutoBreeder.class,		8,	"RenderBreeder"),
	ECU(				"Engine Control Unit", 		BlockMachine.class,			TileEntityEngineController.class,	0),
	SMOKEDETECTOR(		"Smoke Detector", 			BlockMIMachine.class,		TileEntitySmokeDetector.class,		9,	"RenderSmokeDetector"),
	MOBRADAR(			"Mob Radar", 				BlockMMachine.class,		TileEntityMobRadar.class,			4,	"RenderMobRadar"),
	WINDER(				"Coil Winder", 				BlockDMIMachine.class,		TileEntityWinder.class,				2,	"RenderWinder"),
	ADVANCEDGEARS(		"Advanced Gears", 			BlockAdvGear.class,			TileEntityAdvancedGear.class,		0,	"RenderAdvGear"),
	TNTCANNON(			"TNT Cannon", 				BlockMIMachine.class,		TileEntityTNTCannon.class,			10,	"RenderCannon"),
	SONICWEAPON(		"Sonic Weapon", 			BlockMMachine.class,		TileEntitySonicWeapon.class,		5,	"RenderSonic"),
	BLASTFURNACE(		"Blast Furnace", 			BlockIMachine.class,		TileEntityBlastFurnace.class,		2),
	FORCEFIELD(			"Force Field", 				BlockMMachine.class,		TileEntityForceField.class,			6,	"RenderForceField"),
	MUSICBOX(			"Music Box", 				BlockMachine.class,			TileEntityMusicBox.class,			1),
	SPILLER(			"Liquid Spiller", 			BlockPiping.class,			TileEntityFlooder.class,			3,	"PipeRenderer"),
	CHUNKLOADER(		"Chunk Loader", 			BlockMMachine.class,		TileEntityChunkLoader.class,		7,	"RenderChunkLoader"),
	MOBHARVESTER(		"Mob Harvester", 			BlockMMachine.class,		TileEntityMobHarvester.class,		8,	"RenderHarvester"),
	CCTV(				"CCTV", 					BlockMIMachine.class,		TileEntityCCTV.class,				11,	"RenderCCTV"),
	PROJECTOR(			"Projector", 				BlockDMIMachine.class,		TileEntityProjector.class,			3,	"RenderProjector"),
	RAILGUN(			"RailGun", 					BlockMIMachine.class,		TileEntityRailGun.class,			12,	"RenderRailGun"),
	WEATHERCONTROLLER(	"Silver Iodide Cannon", 	BlockMIMachine.class,		TileEntityWeatherController.class,	13,	"RenderIodide"),
	REFRESHER(			"Item Refresher", 			BlockMachine.class,			TileEntityItemRefresher.class,		2),
	FREEZEGUN(			"Freeze Gun", 				BlockMIMachine.class,		TileEntityFreezeGun.class,			14,	"RenderFreezeGun"),
	CAVESCANNER(		"Cave Scanner", 			BlockMMachine.class,		TileEntityCaveFinder.class,			9,	"RenderCaveFinder"),
	SCALECHEST(			"Scaleable Chest", 			BlockDMIMachine.class,		TileEntityScaleableChest.class,		4,	"RenderScaleChest"),
	IGNITER(			"Firestarter", 				BlockIMachine.class,		TileEntityIgniter.class,			3),
	MAGNETIZER(			"Magnetizing Unit",			BlockDMIMachine.class,		TileEntityMagnetizer.class,			5,	"RenderMagnetizer"),
	CONTAINMENT(		"Containment Field",		BlockMMachine.class,		TileEntityContainment.class,		10,	"RenderContainment"),
	SCREEN(				"CCTV Screen",				BlockDMIMachine.class,		TileEntityScreen.class,				6,	"RenderCCTVScreen"),
	PURIFIER(			"Steel Purifier",			BlockIMachine.class,		TileEntityPurifier.class,			4),
	LASERGUN(			"Laser Gun",				BlockMMachine.class,		TileEntityLaserGun.class,			11, "RenderLaserGun"),
	ITEMCANNON(			"Item Cannon",				BlockMIMachine.class,		TileEntityItemCannon.class,			15, "RenderItemCannon"),
	LANDMINE(			"Land Mine",				BlockMIMachine.class,		TileEntityLandmine.class,			16, "RenderLandmine"),
	FRICTION(			"Friction Heater",			BlockDMMachine.class,		TileEntityFurnaceHeater.class,		8, "RenderFriction"),
	BLOCKCANNON(		"Block Cannon",				BlockMIMachine.class,		TileEntityBlockCannon.class,		17, "RenderCannon"),
	BUCKETFILLER(		"Bucket Filler",			BlockIMachine.class,		TileEntityBucketFiller.class,		5),
	MIRROR(				"Solar Mirror",				BlockSolar.class,			TileEntityMirror.class,				0,	"RenderMirror"),
	SOLARTOWER(			"Solar Tower",				BlockSolar.class,			TileEntitySolar.class,				1,	"RenderSolar"),
	SPYCAM(				"Aerial Camera",			BlockMIMachine.class,		TileEntitySpyCam.class,				18,	"RenderSpyCam"),
	SELFDESTRUCT(		"Self Destruct Mechanism",	BlockMachine.class,			TileEntitySelfDestruct.class,		3),
	COOLINGFIN(			"Cooling Fin",				BlockDMMachine.class,		TileEntityCoolingFin.class,			9, "RenderFin"),
	WORKTABLE(			"WorkTable",				BlockIMachine.class,		TileEntityWorktable.class,			6),
	COMPRESSOR(			"Air Compressor", 			BlockBCEngine.class,		TileEntityAirCompressor.class,		0, "RenderCompressor", APIRegistry.BUILDCRAFTENERGY),
	PNEUENGINE(			"Pneumatic Engine",			BlockBCEngine.class,		TileEntityPneumaticEngine.class,	1, "RenderPneumatic", APIRegistry.BUILDCRAFTENERGY),
	DISPLAY(			"Display Screen",			BlockMMachine.class,		TileEntityDisplay.class,			12, "RenderDisplay"),
	LAMP(				"Bright Lamp",				BlockMachine.class,			TileEntityLamp.class,				4),
	EMP(				"EMP Machine",				BlockMMachine.class,		TileEntityEMP.class,				14, "RenderEMP"),
	LINEBUILDER(		"Block Ram",				BlockDMMachine.class,		TileEntityLineBuilder.class,		10, "RenderLineBuilder"),
	DEAD(				"Dead Machine",				BlockDeadMachine.class,		TileEntityDeadMachine.class,		0),
	MULTICLUTCH(		"Multi-Directional Clutch",	BlockTrans.class,			TileEntityMultiClutch.class,		4, "RenderMultiClutch"),
	TERRAFORMER(		"Terraformer",				BlockMachine.class,			TileEntityTerraformer.class,		6),
	LIQUIDCONVERTER(	"Pressure Balancer",		BlockMachine.class,			TileEntityLiquidConverter.class,	7),
	FUELENHANCER(		"Fuel Enhancer",			BlockMMachine.class,		TileEntityFuelConverter.class,		13, "RenderFuelConverter", APIRegistry.BUILDCRAFTENERGY),
	ARROWGUN(			"Arrow Gun",				BlockDMMachine.class,		TileEntityMachineGun.class,			11, "RenderMachineGun");


	private String name;
	private Class te;
	private Class blockClass;
	private int meta;
	private static HashMap<Class,boolean[]> mappedData = new HashMap<Class,boolean[]>();
	private boolean hasRender = false;
	private String renderClass;
	private int rollover;
	private APIRegistry requirement;

	public static final MachineRegistry[] machineList = MachineRegistry.values();

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m) {
		name = n;
		blockClass = b;
		meta = m;
		te = tile;
		if (meta > 15)
			//	throw new RegistrationException(RotaryCraft.instance, "Machine "+name+" assigned to metadata > 15 for Block "+blockClass);
			rollover = m/16;
		//this.updateMappingRegistry();
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, APIRegistry a) {
		this(n, b, tile, m);
		requirement = a;
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, String r) {
		this(n, b, tile, m);
		hasRender = true;
		renderClass = r;
	}

	private MachineRegistry(String n, Class<? extends Block> b, Class<? extends RotaryCraftTileEntity> tile, int m, String r, APIRegistry a) {
		this(n, b, tile, m, r);
		requirement = a;
	}

	private void updateMappingRegistry() {
		if (mappedData == null)
			throw new RegistrationException(RotaryCraft.instance, "Mapped data has not yet been created!");
		if (mappedData.containsKey(blockClass)) {
			boolean[] maps = mappedData.get(blockClass);
			if (maps[meta])
				throw new RegistrationException(RotaryCraft.instance, "Machine "+name+" tried to map into "+blockClass+" slot "+meta+" which is already occupied!");
			else {
				maps[meta] = true;
				ReikaJavaLibrary.pConsole("ROTARYCRAFT: Block "+blockClass+" with metadata "+meta+" assigned to machine "+name);
			}
			mappedData.put(blockClass, maps);
		}
		else {
			boolean[] maps = new boolean[16];
			maps[meta] = true;
			mappedData.put(blockClass, maps);
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Block "+blockClass+" with metadata "+meta+" assigned to machine "+name);
		}
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
			throw new RuntimeException("Machine "+name+" has no render to call!");
		return "Reika.RotaryCraft.Renders."+renderClass;
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
		for (int i = 0; i < machineList.length; i++) {
			MachineRegistry m = machineList[i];
			if (m.getBlockID() == id && ReikaMathLibrary.isValueInsideBoundsIncl(m.getMachineMetadata(), m.getMachineMetadata()+m.getNumberMetadatas()-1, metad))
				return i;
		}
		//throw new RegistrationException(RotaryCraft.instance, "ID "+id+" and metadata "+metad+" are not a valid machine identification pair!");
		return -1;
	}

	public static MachineRegistry getMachine(World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (!(te instanceof RotaryCraftTileEntity))
			return null;
		RotaryCraftTileEntity tc = (RotaryCraftTileEntity)te;
		return machineList[tc.getMachineIndex()];
	}

	public static MachineRegistry getMachine(IBlockAccess iba, int x, int y, int z) {
		TileEntity te = iba.getBlockTileEntity(x, y, z);
		if (!(te instanceof RotaryCraftTileEntity))
			return null;
		RotaryCraftTileEntity tc = (RotaryCraftTileEntity)te;
		return machineList[tc.getMachineIndex()];
	}

	/** A convenience feature */
	public static MachineRegistry getMachine(World world, double x, double y, double z) {
		return getMachine(world, (int)x, (int)y, (int)z);
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
		return name;
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

	public int getNumberMetadatas() {
		return 1;
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
		return (this == HOSE || this == PIPE || this == FUELLINE || this == SPILLER);
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
			throw new RuntimeException("Machine "+name+" has no multi name and yet was called for it!");
		if (this == GEARBOX) {
			TileEntityGearbox gbx = (TileEntityGearbox)tile;
			return RotaryNames.gearboxItemNames[gbx.getBlockMetadata()/4*5+gbx.type.ordinal()];
		}
		if (this == ENGINE) {
			TileEntityEngine eng = (TileEntityEngine)tile;
			return RotaryNames.engineNames[eng.type.ordinal()];
		}
		if (this == SHAFT) {
			TileEntityShaft sha = (TileEntityShaft)tile;
			return RotaryNames.shaftItemNames[sha.type.ordinal()];
		}
		if (this == FLYWHEEL) {
			TileEntityFlywheel fly = (TileEntityFlywheel)tile;
			return RotaryNames.flywheelItemNames[fly.getBlockMetadata()/4];
		}
		if (this == ADVANCEDGEARS) {
			TileEntityAdvancedGear adv = (TileEntityAdvancedGear)tile;
			if (adv.getBlockMetadata() < 4)
				return "Worm Gear";
			else if (adv.getBlockMetadata() < 8)
				return "CVT Unit";
			else if (adv.getBlockMetadata() < 12)
				return "Industrial Coil";
		}
		throw new RegistrationException(RotaryCraft.instance, "Machine "+name+" has an unspecified multi name!");
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
		if (this == SPILLER)
			return new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 3);
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
		if (this == DEAD)
			return false;
		return true;
	}

	public boolean isDummiedOut() {
		if (this == CCTV)
			return true;
		if (this == CHUNKLOADER)
			return true;
		if (this.hasPrerequisite() && !this.getPrerequisite().conditionsMet())
			return true;
		return false;
	}

	public boolean hasPrerequisite() {
		return requirement != null;
	}

	public APIRegistry getPrerequisite() {
		if (!this.hasPrerequisite())
			throw new RegistrationException(RotaryCraft.instance, name+" has no prerequisites and yet was called for them!");
		return requirement;
	}

	public boolean preReqSatisfied() {
		if (!this.hasPrerequisite())
			return true;
		return this.getPrerequisite().conditionsMet();
	}

	public boolean renderInPass1() {
		if (this.hasModel() && TileEntityIOMachine.class.isAssignableFrom(te))
			return true;
		if (this == COOLINGFIN)
			return true;
		if (this == DISPLAY)
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
}
