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
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.ChatLogger;
import Reika.DragonAPI.LanguageArray;
import Reika.RotaryCraft.Auxiliary.AchievementDescriptions;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.TabRotaryCraft;
import Reika.RotaryCraft.Auxiliary.TabRotaryItems;
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
import Reika.RotaryCraft.Items.ItemModOre;
import Reika.RotaryCraft.Items.ItemSpawner;
import Reika.RotaryCraft.Items.Placers.ItemAdvGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemEnginePlacer;
import Reika.RotaryCraft.Items.Placers.ItemFlywheelPlacer;
import Reika.RotaryCraft.Items.Placers.ItemGearPlacer;
import Reika.RotaryCraft.Items.Placers.ItemMachinePlacer;
import Reika.RotaryCraft.Items.Placers.ItemPipePlacer;
import Reika.RotaryCraft.Items.Placers.ItemShaftPlacer;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod( modid = "RotaryCraft", name="RotaryCraft", version="beta", certificateFingerprint = "@GET_FINGERPRINT@")
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "RotaryCraftData" }, packetHandler = ClientPackets.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "RotaryCraftData" }, packetHandler = ServerPackets.class))

public class RotaryCraft {
	public static final String packetChannel = "RotaryCraftData";

	public static CreativeTabs tabRotary = new TabRotaryCraft(CreativeTabs.getNextID(),"RotaryCraft");
	public static CreativeTabs tabRotaryItems = new TabRotaryItems(CreativeTabs.getNextID(),"RotaryItems");

	private static int[] dmgs = {EnumArmorMaterial.DIAMOND.getDamageReductionAmount(0), EnumArmorMaterial.DIAMOND.getDamageReductionAmount(1),
		EnumArmorMaterial.DIAMOND.getDamageReductionAmount(2), EnumArmorMaterial.DIAMOND.getDamageReductionAmount(3)};
	public static EnumArmorMaterial NVHM = EnumHelper.addArmorMaterial("NVHelmet", EnumArmorMaterial.DIAMOND.getDurability(0), dmgs, EnumArmorMaterial.GOLD.getEnchantability());
	public static EnumArmorMaterial NVGM = EnumHelper.addArmorMaterial("NVGoggles", 65536, new int[]{0, 0, 0, 0}, 0);
	public static EnumArmorMaterial IOGM = EnumHelper.addArmorMaterial("IOGoggles", 65536, new int[]{0, 0, 0, 0}, 0);
	/*
	public static Item debug;
	public static Item worldedit;

	public static Item screwdriver;
	public static Item meter;
	public static Item infobook;
	 */
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
	public static Item modextracts;

	public static Block canola;
	public static Block miningpipe;
	public static Block bedrockslice;
	public static Block lightblock;
	public static Block beamblock;
	public static Block lightbridge;

	public static Block gravlog;
	public static Block gravleaves;

	public static Block[] machineBlocks = new Block[BlockRegistry.blockList.length];
	public static Item[] basicItems = new Item[ItemRegistry.itemList.length];

	public static Achievement[] achievements;
	
	public static final ChatLogger chatLogger = new ChatLogger();

	public static Entity arrow;
	public static Entity fallblock;

	@Instance("RotaryCraft")
	public static RotaryCraft instance;

	@SidedProxy(clientSide="Reika.RotaryCraft.ClientProxy", serverSide="Reika.RotaryCraft.CommonProxy")
	public static CommonProxy proxy;

	@FingerprintWarning
	public void invalidFingerprint(final FMLFingerprintViolationEvent event)
	{
		//throw new RuntimeException("This is an illegitimate copy of RotaryCraft! You must download the mod from the forum thread!");
	}

	@PreInit
	public void loadConfig(FMLPreInitializationEvent evt) {
		RotaryConfig.initProps("RotaryCraft", evt);
		proxy.registerSounds();
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
		NetworkRegistry.instance().registerChatListener(chatLogger);
		RotaryRegistration.addTileEntities();
		RotaryChests.addToChests();
		RotaryRegistration.addEntities();
		AchievementDescriptions.loadDesc();
		RotaryAchievements.registerAcheivements();
		RotaryDescriptions.loadData();
		HandbookAuxData.loadNames();
		//DemoMusic.addTracks();

		new LanguageArray("Reika/RotaryCraft/Language/", new String[]{"en_US"});

		OreDictionary.registerOre("HSLA", ItemStacks.steelingot);
		ItemStacks.registerSteels();

		MinecraftForge.setBlockHarvestLevel(blastglass, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(obsidianglass, "pickaxe", 3);
		MinecraftForge.addGrassSeed(new ItemStack(ItemRegistry.CANOLA.getID(), 1, 0), 2);
	}

	@PostInit // Like the modsLoaded thing from ModLoader
	public void myNewPostLoadMethod(FMLPostInitializationEvent evt)
	{
		//LoadAux.texMsg();
	}

	private static void setupClassFiles() {
		RotaryRegistration.instantiateItems();

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

		modextracts = new ItemModOre(RotaryConfig.modextractsid).setUnlocalizedName("modextracts");

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
