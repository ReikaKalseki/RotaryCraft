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

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

import Reika.RotaryCraft.Auxiliary.DemoMusic;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.TabRotaryCraft;
import Reika.RotaryCraft.Auxiliary.TabRotaryItems;
import Reika.RotaryCraft.Base.ItemBasic;
import Reika.RotaryCraft.Base.ItemMulti;
import Reika.RotaryCraft.Blocks.BlockBeam;
import Reika.RotaryCraft.Blocks.BlockBedrockSlice;
import Reika.RotaryCraft.Blocks.BlockBlastGlass;
import Reika.RotaryCraft.Blocks.BlockCanola;
import Reika.RotaryCraft.Blocks.BlockDeco;
import Reika.RotaryCraft.Blocks.BlockGravLeaves;
import Reika.RotaryCraft.Blocks.BlockGravLog;
import Reika.RotaryCraft.Blocks.BlockLightBridge;
import Reika.RotaryCraft.Blocks.BlockLightblock;
import Reika.RotaryCraft.Blocks.BlockMiningPipe;
import Reika.RotaryCraft.Blocks.BlockObsidianGlass;
import Reika.RotaryCraft.Items.ItemBedrockAxe;
import Reika.RotaryCraft.Items.ItemBedrockPickaxe;
import Reika.RotaryCraft.Items.ItemBedrockShovel;
import Reika.RotaryCraft.Items.ItemCanolaSeed;
import Reika.RotaryCraft.Items.ItemCoil;
import Reika.RotaryCraft.Items.ItemDebug;
import Reika.RotaryCraft.Items.ItemFireballLauncher;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Items.ItemGravelGun;
import Reika.RotaryCraft.Items.ItemHandBook;
import Reika.RotaryCraft.Items.ItemHandheldCrafting;
import Reika.RotaryCraft.Items.ItemIOGoggles;
import Reika.RotaryCraft.Items.ItemMeter;
import Reika.RotaryCraft.Items.ItemMotionTracker;
import Reika.RotaryCraft.Items.ItemNightVisionGoggles;
import Reika.RotaryCraft.Items.ItemNightVisionHelmet;
import Reika.RotaryCraft.Items.ItemRailGunAmmo;
import Reika.RotaryCraft.Items.ItemScrewdriver;
import Reika.RotaryCraft.Items.ItemSlide;
import Reika.RotaryCraft.Items.ItemSpawner;
import Reika.RotaryCraft.Items.ItemStunGun;
import Reika.RotaryCraft.Items.ItemTarget;
import Reika.RotaryCraft.Items.ItemUltrasound;
import Reika.RotaryCraft.Items.ItemVacuum;
import Reika.RotaryCraft.Items.ItemWorldEdit;
import Reika.RotaryCraft.Items.Placers.ItemAdvGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemEnginePlacer;
import Reika.RotaryCraft.Items.Placers.ItemFlywheelPlacer;
import Reika.RotaryCraft.Items.Placers.ItemGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemMachinePlacer;
import Reika.RotaryCraft.Items.Placers.ItemPipePlacer;
import Reika.RotaryCraft.Items.Placers.ItemShaftPlacer;

/** @author Reika
 *
 * This code as part of the mod "RotaryCraft" for the game "Minecraft" are the property of and owned by Reika
 *
 * Any unauthorized distribution, modification, and especially sale of this code is a full violation of copyright law and will be
 * dealt with accordingly.
 *
 * This restriction and these copyrights apply not only to this class file, but also to every other RotaryCraft class.
 *
 */

@Mod( modid = "RotaryCraft", name="RotaryCraft", version="beta")/*
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
channels={"RotaryCraftData"}, packetHandler = PacketHandler.class)*/
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "RotaryCraftData" }, packetHandler = ClientPackets.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "RotaryCraftData" }, packetHandler = ServerPackets.class))

public class RotaryCraft {
	public static final String packetChannel = "RotaryCraftData";

	public static CreativeTabs tabRotary = new TabRotaryCraft(CreativeTabs.getNextID(),"RotaryCraft"); //Our custom creative tab's object
	public static CreativeTabs tabRotaryItems = new TabRotaryItems(CreativeTabs.getNextID(),"RotaryItems"); //Our custom creative tab's object

	private static int[] dmgs = {EnumArmorMaterial.DIAMOND.getDamageReductionAmount(0), EnumArmorMaterial.DIAMOND.getDamageReductionAmount(1),
		EnumArmorMaterial.DIAMOND.getDamageReductionAmount(2), EnumArmorMaterial.DIAMOND.getDamageReductionAmount(3)};
	public static EnumArmorMaterial NVHM = EnumHelper.addArmorMaterial("NVHelmet", EnumArmorMaterial.DIAMOND.getDurability(0), dmgs, EnumArmorMaterial.GOLD.getEnchantability());
	public static EnumArmorMaterial NVGM = EnumHelper.addArmorMaterial("NVGoggles", 0, new int[]{0, 0, 0, 0}, 0);
	public static EnumArmorMaterial IOGM = EnumHelper.addArmorMaterial("IOGoggles", 0, new int[]{0, 0, 0, 0}, 0);

	public static Item debug;
	public static Item worldedit;

	//public static Item nullitem;

	//public static Item bedrockingot;

	public static Item screwdriver;
	public static Item meter;
	public static Item infobook;

	// public static Item ItemStacks.steelingot;
	// public static Item ItemStacks.steelgear;
	public static Block decoblock;
	public static Block blastglass;
	public static Block obsidianglass;

	public static Item shaftcraft;
	public static Item enginecraft;
	public static Item heatcraft;
	public static Item borecraft;
	public static Item extracts;
	public static Item compacts;
	public static Item engineitems;
	public static Item powders;
	public static Item spawner;
	public static Item pipeplacer;
	public static Item shaftitems;
	public static Item gbxitems;
	public static Item gearunits;
	public static Item machineplacer;
	public static Item flywheelitems;
	public static Item advgearitems;

	public static Item yeast;
	public static Item ethanol;
	public static Item canolaseed;
	public static Item wind;
	public static Item ultra;
	public static Item motiontracker;
	public static Item vac;
	public static Item stun;
	public static Item gravelgun;
	public static Item fireball;
	// public static Item calc;
	public static Item bedpick;
	public static Item bedaxe;
	public static Item bedshov;
	public static Item nvg;
	public static Item nvh;
	public static Item handcraft;
	public static Item railammo;
	public static Item fuelbucket;
	public static Item target;
	public static Item iogoggles;
	public static Item slides;

	public static Block canola;
	public static Block miningpipe;
	public static Block bedrockslice;
	public static Block lightblock;
	public static Block beamblock;
	public static Block lightbridge;

	public static Block gravlog;
	public static Block gravleaves;

	public static Block[] machineBlocks = new Block[BlockRegistry.blockList.length];

	public static Entity arrow;
	public static Entity fallblock;
	//public static int fallblockid = EntityRegistry.findGlobalUniqueEntityId();
	//public static int tntid = EntityRegistry.findGlobalUniqueEntityId();

	// The instance of your mod that Forge uses, in my case tutorial.
	@Instance("RotaryCraft")
	public static RotaryCraft instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="Reika.RotaryCraft.ClientProxy", serverSide="Reika.RotaryCraft.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void loadConfig(FMLPreInitializationEvent evt) {
		RotaryConfig.initProps("RotaryCraft", evt);
	}

	@Init
	public void load(FMLInitializationEvent event) {
		proxy.addArmorRenders();
		this.setupClassFiles();
		proxy.registerRenderers();
		RotaryRegistration.addBlocks();
		RotaryNames.addNames();
		RotaryRecipes.addRecipes();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		RotaryRegistration.addTileEntities();
		RotaryChests.addToChests();
		RotaryRegistration.addEntities();
		DemoMusic.addTracks();
		proxy.registerSounds();

		OreDictionary.registerOre("HSLA", ItemStacks.steelingot);
		ItemStacks.registerSteels();

		MinecraftForge.setBlockHarvestLevel(blastglass, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(obsidianglass, "pickaxe", 3);
		MinecraftForge.addGrassSeed(new ItemStack(canolaseed.itemID, 1, 0), 2);
	}

	@PostInit // Like the modsLoaded thing from ModLoader
	public void myNewPostLoadMethod(FMLPostInitializationEvent evt)
	{
		/*
    	Minecraft mc = ModLoader.getMinecraftInstance();
    	EnumOS os = mc.getOs();
    	if (os == EnumOS.MACOS)
    		ReikaJavaLibrary.pConsole("Hi, person with lots of money! ;)");
    	if (os == EnumOS.WINDOWS)
    		ReikaJavaLibrary.pConsole("Hi!");*/
	}

	private static void setupClassFiles() {
		debug = new ItemDebug(RotaryConfig.debugitemid).setUnlocalizedName("debug");
		worldedit = new ItemWorldEdit(RotaryConfig.worldeditid).setUnlocalizedName("worldedit");

		screwdriver = new ItemScrewdriver(RotaryConfig.screwdriverid).setUnlocalizedName("Screwdriver");
		meter = new ItemMeter(RotaryConfig.meterid).setUnlocalizedName("Angular Transducer");
		infobook = new ItemHandBook(RotaryConfig.infobookid).setUnlocalizedName("RotaryCraft Handbook");

		shaftcraft = new ItemMulti(RotaryConfig.shaftcraftid, 0).setUnlocalizedName("shaftcraft");
		enginecraft = new ItemMulti(RotaryConfig.enginecraftid, 1).setUnlocalizedName("enginecraft");
		heatcraft = new ItemMulti(RotaryConfig.heatcraftid, 2).setUnlocalizedName("heatcraft");
		borecraft = new ItemMulti(RotaryConfig.borecraftid, 3).setUnlocalizedName("borecraft");
		extracts = new ItemMulti(RotaryConfig.extractsid, 4).setUnlocalizedName("extracts");
		compacts = new ItemMulti(RotaryConfig.compactsid, 6).setUnlocalizedName("compacts");
		engineitems = new ItemEnginePlacer(RotaryConfig.engineitemsid).setUnlocalizedName("engines");
		powders = new ItemMulti(RotaryConfig.powderid, 8).setUnlocalizedName("powder");
		spawner = new ItemSpawner(RotaryConfig.spawnerid).setUnlocalizedName("spawner");
		pipeplacer = new ItemPipePlacer(RotaryConfig.pipeplacerid).setUnlocalizedName("pipeplacer");
		shaftitems = new ItemShaftPlacer(RotaryConfig.shaftitemsid).setUnlocalizedName("shafts");
		gbxitems = new ItemGearPlacer(RotaryConfig.gbxitemsid).setUnlocalizedName("gbxs");
		gearunits = new ItemMulti(RotaryConfig.gearunitsid, 23).setUnlocalizedName("gearunits");
		machineplacer = new ItemMachinePlacer(RotaryConfig.machineplacerid).setUnlocalizedName("machineplacer");
		advgearitems = new ItemAdvGearPlacer(RotaryConfig.advgearitemsid).setUnlocalizedName("advgearitem");
		flywheelitems = new ItemFlywheelPlacer(RotaryConfig.flywheelitemsid).setUnlocalizedName("flywheelitem");

		yeast = new ItemBasic(RotaryConfig.yeastid, 32).setUnlocalizedName("Yeast");
		ethanol = new ItemBasic(RotaryConfig.ethanolid, 64).setUnlocalizedName("EthanolCrystals");
		canolaseed = new ItemCanolaSeed(RotaryConfig.canolaseedid).setUnlocalizedName("CanolaSeed");
		wind = new ItemCoil(RotaryConfig.windid).setUnlocalizedName("Winder");
		motiontracker = new ItemMotionTracker(RotaryConfig.motionid).setUnlocalizedName("Motion");
		ultra = new ItemUltrasound(RotaryConfig.ultraid).setUnlocalizedName("Ultra");
		vac = new ItemVacuum(RotaryConfig.vacid).setUnlocalizedName("Vac");
		gravelgun = new ItemGravelGun(RotaryConfig.gravelgunid).setUnlocalizedName("GravelGun");
		stun = new ItemStunGun(RotaryConfig.stunid).setUnlocalizedName("StunGun");
		//calc = new ItemCalculator(RotaryConfig.calcid).setUnlocalizedName("Calc");
		fireball = new ItemFireballLauncher(RotaryConfig.fireballid).setUnlocalizedName("FireBall");
		bedpick = new ItemBedrockPickaxe(RotaryConfig.bedpickid).setUnlocalizedName("BedPick");
		bedaxe = new ItemBedrockAxe(RotaryConfig.bedaxeid).setUnlocalizedName("BedAxe");
		bedshov = new ItemBedrockShovel(RotaryConfig.bedshovid).setUnlocalizedName("BedShovel");
		railammo = new ItemRailGunAmmo(RotaryConfig.railammoid).setUnlocalizedName("Railgunammo");
		fuelbucket = new ItemFuelLubeBucket(RotaryConfig.fuelbucketid).setUnlocalizedName("Fuelbucket");
		target = new ItemTarget(RotaryConfig.targetid).setUnlocalizedName("Target");

		slides = new ItemSlide(RotaryConfig.slidesid, 2).setUnlocalizedName("Slides");
		iogoggles = new ItemIOGoggles(RotaryConfig.iogogglesid, proxy.IOGoggles).setUnlocalizedName("IOGMgoggles");

		nvg = new ItemNightVisionGoggles(RotaryConfig.nvgid, proxy.NVGoggles).setUnlocalizedName("NVGMhelmet");
		handcraft = new ItemHandheldCrafting(RotaryConfig.handcraftid).setUnlocalizedName("HandCraft");
		nvh = new ItemNightVisionHelmet(RotaryConfig.nvhid, proxy.NVHelmet).setUnlocalizedName("NVHMhelmet");

		decoblock = new BlockDeco(RotaryConfig.decoblockid);
		blastglass = new BlockBlastGlass(RotaryConfig.blastpaneid).setUnlocalizedName("BlastGlassPane");
		obsidianglass = new BlockObsidianGlass(RotaryConfig.blastglassid).setUnlocalizedName("BlastGlass");
		canola = new BlockCanola(RotaryConfig.canolaid).setUnlocalizedName("Canola");

		RotaryRegistration.instantiateMachines();

		miningpipe = new BlockMiningPipe(RotaryConfig.miningpipeid).setUnlocalizedName("MiningPipe");
		gravlog = new BlockGravLog(RotaryConfig.gravlogid).setUnlocalizedName("GravLog");
		gravleaves = new BlockGravLeaves(RotaryConfig.gravleavesid).setUnlocalizedName("GravLeaves");
		lightblock = new BlockLightblock(RotaryConfig.lightblockid).setUnlocalizedName("LightBlock");
		beamblock = new BlockBeam(RotaryConfig.beamblockid).setUnlocalizedName("BeamBlock");
		lightbridge = new BlockLightBridge(RotaryConfig.lightbridgeid).setUnlocalizedName("Bridge");
		bedrockslice = new BlockBedrockSlice(RotaryConfig.bedrocksliceid).setUnlocalizedName("BedrockSlice");
	}

	public String getVersion() {
		return "RotaryCraft RC v1.0";
	}

}

