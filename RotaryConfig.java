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

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.Configuration;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RotaryConfig {

	//Configuration, used for save and load data
	public static Configuration config;

	/** Change this to cause auto-deletion of users' config files to load new copies */
	private static final int CURRENT_CONFIG_ID = 0;
	private static int readID;
	private static File configFile;

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
	public static Object[] controls = new Object[ConfigRegistry.optionList.length];

	private static HashMap<String,Integer> id = new HashMap<String,Integer>();

	/** Non-config-file control data used by the machines */

	public static final int friction = 0;
	public static final int torquelimit = (Integer.MAX_VALUE-1)/2;	// ~1 billion
	public static final int omegalimit = (Integer.MAX_VALUE-1)/2;
	public static final boolean debugmode = false;

	//Initialization of the config
	// Args: String mod used to name the config file to mods name
	public static void initProps(String mod, FMLPreInitializationEvent event) {

		//creating a folder for the mod in the folder config within the .minecraft folder
		//File file = new File(Minecraft.getMinecraftDir() + "/config/" + mod);
		//file.mkdir(); //creates the folder
		//getting the config file

		//allocate the file to the config
		configFile = event.getSuggestedConfigurationFile();
		config = new Configuration(configFile);

		//load data
		config.load();

		if (checkReset(config)) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Config File Format Changed. Resetting...");
			resetConfigFile();
			initProps(mod, event);
			return;
		}

		/********************************/
		machineplacerid = 	config.getItem("ItemBlock IDs", "Machine Items", 30616).getInt();
		heatcraftid = 		config.getItem("Crafting Item IDs", "Heat Ray Crafting Items", 30628).getInt();
		enginecraftid = 	config.getItem("Crafting Item IDs", "Engine Crafting Items", 30629).getInt();
		borecraftid = 		config.getItem("Crafting Item IDs", "Borer Crafting Items", 30630).getInt();
		shaftcraftid = 		config.getItem("Crafting Item IDs", "Shaft Crafting Items", 30631).getInt();
		extractsid = 		config.getItem("Resource Item IDs", "Extractor Items", 30632).getInt();
		compactsid = 		config.getItem("Resource Item IDs", "Compactor Items", 30633).getInt();
		engineitemsid = 	config.getItem("ItemBlock IDs", "Engine Items", 30634).getInt();
		powderid = 			config.getItem("Resource Item IDs", "Powders", 30635).getInt();
		spawnerid = 		config.getItem("ItemBlock IDs", "Spawner", 30636).getInt();
		pipeplacerid = 		config.getItem("ItemBlock IDs", "Pipe Items", 30637).getInt();
		shaftitemsid = 		config.getItem("ItemBlock IDs", "Shaft Items", 30639).getInt();
		gbxitemsid = 		config.getItem("ItemBlock IDs", "Gearbox Items", 30640).getInt();
		gearunitsid = 		config.getItem("Crafting Item IDs", "Gear Units", 30641).getInt();
		advgearitemsid = 	config.getItem("ItemBlock IDs", "Advanced Gear Items", 30642).getInt();
		flywheelitemsid = 	config.getItem("ItemBlock IDs", "Flywheel Items", 30643).getInt();
		modextractsid = 	config.getItem("Resource Item IDs", "Mod Ore Extractor Items", 30644).getInt();

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

		for (int i = 0; i < ConfigRegistry.optionList.length; i++) {
			String label = ConfigRegistry.optionList[i].getLabel();
			if (ConfigRegistry.optionList[i].isBoolean())
				controls[i] = ConfigRegistry.optionList[i].setState(config);
			if (ConfigRegistry.optionList[i].isNumeric())
				controls[i] = ConfigRegistry.optionList[i].setValue(config);
		}

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

	private static boolean checkReset(Configuration config) {
		readID = config.get("Control", "Config ID - Edit to have your config auto-deleted", CURRENT_CONFIG_ID).getInt();
		//ReikaJavaLibrary.pConsole("ReadID: "+readID);
		return readID != CURRENT_CONFIG_ID;
	}

	private static void resetConfigFile() {
		configFile.delete();
	}

}
