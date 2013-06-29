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

import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.ControlledConfig;
import Reika.DragonAPI.Interfaces.IDRegistry;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Registry.BlockRegistry;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ExtraConfigIDs;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RotaryConfig extends ControlledConfig {

	public RotaryConfig(DragonAPIMod mod, Reika.DragonAPI.Interfaces.ConfigRegistry[] option, IDRegistry[] blocks, IDRegistry[] items, IDRegistry[] id, int cfg) {
		super(mod, option, blocks, items, id, cfg);
	}

	public int[] achievementIDs = new int[RotaryAchievements.list.length]; //

	/** Non-config-file control data used by the machines */

	public static final int friction = 0;
	public static final int torquelimit = (Integer.MAX_VALUE-1)/2;	// ~1 billion
	public static final int omegalimit = (Integer.MAX_VALUE-1)/2;
	public static final boolean debugmode = false;

	//Initialization of the config
	// Args: String mod used to name the config file to mods name
	@Override
	public void initProps(FMLPreInitializationEvent event) {

		super.initProps(event);

		config.load();
		for (int i = 0; i < RotaryAchievements.list.length; i++) {
			String name = RotaryAchievements.list[i].name();
			achievementIDs[i] = config.get("Achievement IDs", name, 24000+i).getInt();
		}

		/*******************************/
		//save the data
		config.save();
	}

	@Override
	protected void resetConfigFile() {
		String path = this.getConfigPath()+"_Old_Config_Backup.txt";
		File backup = new File(path);
		if (backup.exists())
			backup.delete();
		try {
			ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Writing Backup File to "+path);
			ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Use this to restore custom IDs if necessary.");
			backup.createNewFile();
			if (!backup.exists())
				ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Could not create backup file at "+path+"!");
			else {
				PrintWriter p = new PrintWriter(backup);
				p.println("#####----------THESE ARE ALL THE OLD CONFIG SETTINGS YOU WERE USING----------#####");
				p.println("#####---IF THEY DIFFER FROM THE DEFAULTS, YOU MUST RE-EDIT THE CONFIG FILE---#####");
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
					blockIDs[i] = config.get("Machine Block IDs", name, 490+i).getInt();
					p.println(name+": "+String.valueOf(blockIDs[i]));
				}

				for (int i = 0; i < ItemRegistry.itemList.length; i++) {
					String name = ItemRegistry.itemList[i].getBasicName();
					itemIDs[i] = config.get("Item IDs", name, 30500+i).getInt();
					p.println(name+": "+String.valueOf(itemIDs[i]));
				}

				for (int i = 0; i < RotaryAchievements.list.length; i++) {
					String name = RotaryAchievements.list[i].name();
					achievementIDs[i] = config.get("Achievement IDs", name, 24000+i).getInt();
					p.println(name+": "+String.valueOf(achievementIDs[i]));
				}

				for (int i = 0; i < ExtraConfigIDs.idList.length; i++) {
					String name = ExtraConfigIDs.idList[i].getName();
					String cat = ExtraConfigIDs.idList[i].getCategory();
					int def = ExtraConfigIDs.idList[i].getDefaultID();
					otherIDs[i] = config.get(cat, name, def).getInt();
					p.println(name+": "+String.valueOf(otherIDs[i]));
				}

				p.close();
			}
		}
		catch (IOException e) {
			ReikaJavaLibrary.pConsole(configMod.getDisplayName().toUpperCase()+": Could not create backup file due to IOException!");
			e.printStackTrace();
		}
		configFile.delete();
	}

}
