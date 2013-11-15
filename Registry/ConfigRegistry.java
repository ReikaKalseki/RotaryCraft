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

import net.minecraftforge.common.Configuration;
import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Interfaces.ConfigList;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;


public enum ConfigRegistry implements ConfigList {

	ENGINESOUNDS("Engine Running Sounds", true),
	GPRORES("GPR Renders Ores", false),
	INSTACUT("Instant Woodcutter", false),
	RENDERFORCEFIELD("Show Force Fields", true),
	CRAFTABLEBEDROCK("Allow Craftable Bedrock", true),
	LOGLOADING("Console Loading Info", true),
	LOCKMACHINES("Owner-Only Machine Use", false),
	MACHINEVOLUME("Machine Volume Multiplier", 1.0F),
	FLOODLIGHTRANGE("Max Floodlight Range", 128),
	HEATRAYRANGE("Max Heat Ray Range", 128),
	BRIDGERANGE("Max Bridge Range", 128),
	FANRANGE("Max Fan Range", 128),
	AERORANGE("Max Aerosolizer Range", 128),
	VACUUMRANGE("Max Vacuum Range", 128),
	SONICRANGE("Max Sonic Weapon Range", 128),
	FORCERANGE("Max Force Field Range", 128),
	SONICBORERRANGE("Sonic Borer Range", 512),
	SPAWNERLIMIT("Spawner Mob Limit", 128),
	DETECTORRANGE("Player Detector Range", 128),
	BREEDERRANGE("Breeder Range", 128),
	BAITRANGE("Bait Box Range", 128),
	LINEBUILDER("Block Ram Range", 512),
	BAITMOBS("Max Bait Box Mob Count", 256),
	CAVEFINDERRANGE("Cave Scanner FOV", 16),
	DEBUGMODE("Debug Mode", false),
	ACHIEVEMENTS("Enable Achievements", false, true),
	MODORES("Force Inter-Mod Ore Compatibility", true),
	BEDPICKSPAWNERS("Allow Bedrock Pickaxe to Harvest Spawners", true),
	SPAWNERLEAK("Spawn Mobs When Harvesting Spawners By Hand", true),
	BLOCKDAMAGE("Direct Block Damage from Machines and Failures", true),
	DIFFICULTY("Difficulty Control", 2),
	ALARM("Machine Warning Alarms", false),
	BIOMEBLOCKS("Terraformer Block Editing", true),
	DYNAMICHANDBOOK("Reload Handbook Data on Open", false),
	TABLEMACHINES("Crafting Table can Make Machines", false),
	EMPLOAD("EMP Charging Speed", 4),
	ROTATEHOSE("Rotate Hose/Pipe/Fuel Line Recipes", false),
	RAILGUNDAMAGE("Railgun Block Damage", true),
	GRAVELPLAYER("Allow Gravel Gun PvP", true),
	CHESTGEN("Chest Generation Tier", 4),
	HOSTILECRASH("Crash on hostile interference from other mods", true);

	private String label;
	private boolean defaultState;
	private int defaultValue;
	private float defaultFloat;
	private Class type;
	private boolean isLocked;

	public static final ConfigRegistry[] optionList = ConfigRegistry.values();

	private ConfigRegistry(String l, boolean d) {
		label = l;
		defaultState = d;
		type = boolean.class;
	}

	private ConfigRegistry(String l, boolean d, boolean lock) {
		label = l;
		defaultState = d;
		type = boolean.class;
		isLocked = true;
	}

	private ConfigRegistry(String l, int d) {
		label = l;
		defaultValue = d;
		type = int.class;
	}

	private ConfigRegistry(String l, float d) {
		label = l;
		defaultFloat = d;
		type = float.class;
	}

	public boolean isBoolean() {
		return type == boolean.class;
	}

	public boolean isNumeric() {
		return type == int.class;
	}

	public boolean isDecimal() {
		return type == float.class;
	}

	public Class getPropertyType() {
		return type;
	}

	public int setValue(Configuration config) {
		if (!this.isNumeric())
			throw new RegistrationException(RotaryCraft.instance, "Config Property \""+this.getLabel()+"\" is not numerical!");
		return config.get("Control Setup", this.getLabel(), defaultValue).getInt();
	}

	public float setDecimal(Configuration config) {
		if (!this.isDecimal())
			throw new RegistrationException(RotaryCraft.instance, "Config Property \""+this.getLabel()+"\" is not decimal!");
		return (float)config.get("Control Setup", this.getLabel(), defaultFloat).getDouble(defaultFloat);
	}

	public String getLabel() {
		return label;
	}

	public boolean setState(Configuration config) {
		if (!this.isBoolean())
			throw new RegistrationException(RotaryCraft.instance, "Config Property \""+this.getLabel()+"\" is not boolean!");
		return config.get("Control Setup", this.getLabel(), defaultState).getBoolean(defaultState);
	}

	public boolean getState() {
		if (isLocked)
			return defaultState;
		return (Boolean)RotaryCraft.config.getControl(this.ordinal());
	}

	public int getValue() {
		if (this == DIFFICULTY) {
			return EnumDifficulty.getBoundedDifficulty((Integer)RotaryCraft.config.getControl(this.ordinal()), RotaryConfig.EASIEST, RotaryConfig.HARDEST).ordinal();
		}
		return (Integer)RotaryCraft.config.getControl(this.ordinal());
	}

	public float getFloat() {
		return (Float)RotaryCraft.config.getControl(this.ordinal());
	}

	public boolean isDummiedOut() {
		return type == null;
	}

}
