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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import net.minecraftforge.common.Configuration;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RotaryConfig {

	//Configuration, used for save and load data
	public static Configuration config;

	/** Change this to cause auto-deletion of users' config files to load new copies */
	private static final int CURRENT_CONFIG_ID = 1;
	private static int readID;
	private static File configFile;

	public static int[] machineids = new int[BlockRegistry.blockList.length];
	public static int[] itemids = new int[ItemRegistry.itemList.length];
	public static int[] extraids = new int[ExtraConfigIDs.idList.length];
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

		for (int i = 0; i < ExtraConfigIDs.idList.length; i++) {
			extraids[i] = ExtraConfigIDs.idList[i].getValueFromConfig(config);
		}

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
		String path = configFile.getAbsolutePath().substring(0, configFile.getAbsolutePath().length()-4)+"_Old_ID_Backup.txt";
		File backup = new File(path);
		if (backup.exists())
			backup.delete();
		try {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Writing Backup File to "+path);
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Use this to restore custom IDs if necessary.");
			backup.createNewFile();
			if (!backup.exists())
				ReikaJavaLibrary.pConsole("ROTARYCRAFT: Could not create backup file at "+path+"!");
			PrintWriter p = new PrintWriter(backup);
			for (int i = 0; i < ConfigRegistry.optionList.length; i++) {
				String label = ConfigRegistry.optionList[i].getLabel();
				if (ConfigRegistry.optionList[i].isBoolean())
					controls[i] = ConfigRegistry.optionList[i].setState(config);
				if (ConfigRegistry.optionList[i].isNumeric())
					controls[i] = ConfigRegistry.optionList[i].setValue(config);
				p.println(label+": "+String.valueOf(controls[i]));
			}

			for (int i = 0; i < BlockRegistry.blockList.length; i++) {
				String name = BlockRegistry.blockList[i].getBlockVariableName();
				machineids[i] = config.get("Machine Block IDs", name, 490+i).getInt();
				p.println(name+": "+String.valueOf(machineids[i]));
			}

			for (int i = 0; i < ItemRegistry.itemList.length; i++) {
				String name = ItemRegistry.itemList[i].getBasicName();
				itemids[i] = config.get("Item IDs", name, 30500+i).getInt();
				p.println(name+": "+String.valueOf(itemids[i]));
			}

			for (int i = 0; i < ExtraConfigIDs.idList.length; i++) {
				String name = ExtraConfigIDs.idList[i].getName();
				String cat = ExtraConfigIDs.idList[i].getCategory();
				int def = ExtraConfigIDs.idList[i].getDefault();
				extraids[i] = config.get(cat, name, def).getInt();
				p.println(name+": "+String.valueOf(extraids[i]));
			}

			p.close();
		}
		catch (IOException e) {
			ReikaJavaLibrary.pConsole("ROTARYCRAFT: Could not create backup file due to IOException!");
			e.printStackTrace();
		}
		configFile.delete();
	}

}
