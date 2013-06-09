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

import java.util.HashMap;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RotaryConfig {

	//Configuration, used for save and load data
	public static Configuration config;
	/*
	public static int debugitemid;
	public static int worldeditid;
	public static int screwdriverid;
	public static int meterid;

	public static int yeastid;
	public static int ethanolid;
	public static int canolaseedid;
	public static int windid;
	public static int ultraid;
	public static int motionid;
	public static int vacid;
	public static int gravelgunid;
	public static int stunid;
	public static int fireballid;
	public static int bedpickid;
	public static int bedaxeid;
	public static int bedshovid;
	public static int calcid;
	public static int nvgid;
	public static int nvhid;
	public static int handcraftid;
	public static int railammoid;
	public static int fuelbucketid;
	public static int targetid;
	public static int slidesid;
	public static int iogogglesid;
	public static int diskid;*/

	public static int modextractsid;

	public static int heatcraftid;
	public static int enginecraftid;
	public static int borecraftid;
	public static int shaftcraftid;
	public static int extractsid;
	public static int compactsid;
	public static int engineitemsid;
	public static int powderid;
	public static int spawnerid;
	public static int pipeplacerid;
	public static int smokeplacerid;
	public static int shaftitemsid;
	public static int gbxitemsid;
	public static int gearunitsid;
	public static int machineplacerid;
	public static int flywheelitemsid;
	public static int advgearitemsid;

	//public static int infobookid;

	public static boolean playsounds;
	public static boolean renderores;
	public static boolean SIUnits;
	public static boolean alwaysGravLeaves;
	public static boolean gravtree;
	public static boolean renderforcefield;
	public static boolean craftableBedrock;
	public static boolean oreDict;
	public static boolean consoleMsg;
	public static int maxlamprange;
	public static int maxheatrayrange;
	public static int maxbridgerange;
	public static int maxfanrange;
	public static int maxaerorange;
	public static int maxvacuumrange;
	public static int spawnerlimit;
	public static int maxdetectorrange;
	public static int maxbreederrange;
	public static int maxbaitrange;
	public static int maxbaitmobs;
	public static int maxsonicrange;
	public static int maxforcerange;
	public static int cavefinderrange;

	public static int beamblockid;
	public static int gravlogid;
	public static int gravleavesid;
	public static int decoblockid;
	public static int bedrocksliceid;
	public static int lightblockid;
	public static int canolaid;
	public static int lightbridgeid;
	public static int miningpipeid;
	public static int blastpaneid;
	public static int blastglassid;

	public static int[] machineids = new int[BlockRegistry.blockList.length];
	public static int[] itemids = new int[ItemRegistry.itemList.length];

	private static HashMap<String,Integer> id = new HashMap<String,Integer>();

	/** Non-config-file control data used by the machines */

	public static final int friction = 0;
	public static final int torquelimit = (Integer.MAX_VALUE-1)/2;	// ~1 billion
	public static final int omegalimit = (Integer.MAX_VALUE-1)/2;
	public static final boolean debugmode = false;

	//Initialization of the config
	// Args: String mod used to name the config file to mods name
	public static void initProps(String mod, FMLPreInitializationEvent event){

		//creating a folder for the mod in the folder config within the .minecraft folder
		//File file = new File(Minecraft.getMinecraftDir() + "/config/" + mod);
		//file.mkdir(); //creates the folder
		//getting the config file

		//allocate the file to the config
		config = new Configuration(event.getSuggestedConfigurationFile());

		//load data
		config.load();
		/********************************/
		machineplacerid = 	config.getItem("Item IDs", "Machine Items", 30616).getInt();
		heatcraftid = 		config.getItem("Item IDs", "Heat Ray Crafting Items", 30628).getInt();
		enginecraftid = 	config.getItem("Item IDs", "Engine Crafting Items", 30629).getInt();
		borecraftid = 		config.getItem("Item IDs", "Borer Crafting Items", 30630).getInt();
		shaftcraftid = 		config.getItem("Item IDs", "Shaft Crafting Items", 30631).getInt();
		extractsid = 		config.getItem("Item IDs", "Extractor Items", 30632).getInt();
		compactsid = 		config.getItem("Item IDs", "Compactor Items", 30633).getInt();
		engineitemsid = 	config.getItem("Item IDs", "Engine Items", 30634).getInt();
		powderid = 			config.getItem("Item IDs", "Powders", 30635).getInt();
		spawnerid = 		config.getItem("Item IDs", "Spawner", 30636).getInt();
		pipeplacerid = 		config.getItem("Item IDs", "Pipe Items", 30637).getInt();
		shaftitemsid = 		config.getItem("Item IDs", "Shaft Items", 30639).getInt();
		gbxitemsid = 		config.getItem("Item IDs", "Gearbox Items", 30640).getInt();
		gearunitsid = 		config.getItem("Item IDs", "Gear Units", 30641).getInt();
		advgearitemsid = 	config.getItem("Item IDs", "Advanced Gear Items", 30642).getInt();
		flywheelitemsid = 	config.getItem("Item IDs", "Flywheel Items", 30643).getInt();
		modextractsid = 	config.getItem("Item IDs", "Mod Ore Extractor Items", 30644).getInt();

		decoblockid = 		config.getBlock("Extra Block IDs", "Deco Block", 450).getInt();
		bedrocksliceid = 	config.getBlock("Extra Block IDs", "Bedrock Slice", 451).getInt();
		gravlogid = 		config.getBlock("Extra Block IDs", "Tree Log", 452).getInt();
		gravleavesid = 		config.getBlock("Extra Block IDs", "Tree Leaves", 453).getInt();
		lightblockid = 		config.getBlock("Extra Block IDs", "LightBlock", 454).getInt();
		canolaid = 			config.getBlock("Extra Block IDs", "Canola", 455).getInt();
		miningpipeid = 		config.getBlock("Extra Block IDs", "Mining Pipe", 456).getInt();
		lightbridgeid = 	config.getBlock("Extra Block IDs", "Light Bridge", 457).getInt();
		blastpaneid = 		config.getBlock("Extra Block IDs", "Blast Glass Pane", 458).getInt();
		blastglassid = 		config.getBlock("Extra Block IDs", "Blast Glass", 459).getInt();
		beamblockid = 		config.getBlock("Extra Block IDs", "Beam Block", 460).getInt();

		playsounds = 		config.get("Control Setup", "Engine Running Sounds", true).getBoolean(true);
		renderores = 		config.get("Control Setup", "GPR Renders Ores", false).getBoolean(false);
		gravtree = 			config.get("Control Setup", "Woodcutter Falling Trees", true).getBoolean(true);
		alwaysGravLeaves = 	config.get("Control Setup", "Unlimited Jungle Tree Felling", false).getBoolean(false);
		renderforcefield = 	config.get("Control Setup", "Show Force Fields", true).getBoolean(true);
		craftableBedrock = 	config.get("Control Setup", "Allow Craftable Bedrock", true).getBoolean(true);
		oreDict = 			config.get("Control Setup", "Ore Dictionary Interchangeability", true).getBoolean(true);
		consoleMsg =		config.get("Control Setup", "Console Loading Info", true).getBoolean(true);
		//SIUnits = 			config.getOrCreateBooleanProperty("SI Metric Units", "Control Setup", true).getBoolean(true);
		maxlamprange = 		config.get("Control Setup", "Max Floodlight Range", 128).getInt();
		maxheatrayrange = 	config.get("Control Setup", "Max Heat Ray Range", 128).getInt();
		maxbridgerange = 	config.get("Control Setup", "Max Bridge Range", 128).getInt();
		maxfanrange = 		config.get("Control Setup", "Max Fan Range", 128).getInt();
		maxaerorange = 		config.get("Control Setup", "Max Aerosolizer Range", 128).getInt();
		maxvacuumrange = 	config.get("Control Setup", "Max Vacuum Range", 128).getInt();
		maxsonicrange = 	config.get("Control Setup", "Max Sonic Weapon Range", 128).getInt();
		maxforcerange = 	config.get("Control Setup", "Max Force Field Range", 128).getInt();
		spawnerlimit = 		config.get("Control Setup", "Spawner Mob Limit", 128).getInt();
		maxdetectorrange = 	config.get("Control Setup", "Player Detector Range", 128).getInt();
		maxbreederrange = 	config.get("Control Setup", "Breeder Range", 128).getInt();
		maxbaitrange = 		config.get("Control Setup", "Bait Box Range", 128).getInt();
		maxbaitmobs = 		config.get("Control Setup", "Max Bait Box Mob Count", 256).getInt();
		cavefinderrange = 	config.get("Control Setup", "Cave Finder FOV", 16).getInt();

		for (int i = 0; i < BlockRegistry.blockList.length; i++) {
			String name = BlockRegistry.blockList[i].getBlockVariableName();
			machineids[i] = config.get("Machine Block IDs", name, 490+i).getInt();
		}

		for (int i = 0; i < ItemRegistry.itemList.length; i++) {
			String name = ItemRegistry.itemList[i].getBasicName();
			itemids[i] = config.get("Item IDs", name, 30500+i).getInt();
		}

		/*******************************/
		//save the data
		config.save();
	}

}
