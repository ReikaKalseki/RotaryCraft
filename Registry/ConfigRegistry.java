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

import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Interfaces.ConfigList;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;


public enum ConfigRegistry implements ConfigList {

	ENGINESOUNDS("Engine Running Sounds", true),
	GPRORES("GPR Renders Ores", true),
	INSTACUT("Instant Woodcutter", true),
	RENDERFORCEFIELD("Show Force Fields", true),
	CRAFTABLEBEDROCK("Allow Craftable Bedrock", true),
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
	BANRAIN("Disable Silver Iodide Cannon Rain", false),
	ACHIEVEMENTS("Enable Achievements", true),
	MODORES("Force Inter-Mod Ore Compatibility", true),
	BEDPICKSPAWNERS("Allow Bedrock Pickaxe to Harvest Spawners", true),
	SPAWNERLEAK("Spawn Mobs When Harvesting Spawners By Hand", true),
	BLOCKDAMAGE("Direct Block Damage from Machine Failures", true),
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
	HOSTILECRASH("Crash on hostile interference from other mods", true),
	PROJECTORLINES("Render projector lines", true),
	COLORBLIND("Color Blind Mode", false),
	TURRETPLAYERS("Turrets can target players", true),
	HSLADICT("Allow RC steel to be used in other mods", false),
	PREENCHANT("Lock enchants on bedrock tools", true),
	EXPLODEPACK("Explode jetpack if player is in lava", true),
	SPRINKLER("Sprinkler Particle Density", 4),
	HANDBOOK("Spawn with RC Handbook", true),
	CONSERVEPACK("Conservative Jetpack Firing", true),
	ALLOWBAN("Allow Build Blocking of Some Machines", false),
	LOGBLOCKS("Log Block Placement and Removal", false),
	//PACKETDELAY("Sync Packet Interval in Ticks", 1),
	FLOWSPEED("Fluid Flow Speed", 5),
	ATTACKBLOCKS("Block Damage from Destructive Machines", true),
	VOIDHOLE("Allow Bedrock Breaker to Break Y=0", false),
	JETFUELPACK("Jetpack Uses Jet Fuel", false),
	ALLOWTNTCANNON("Allow TNT Cannon", true),
	EXTRAIRON("Iron Ore Density", 1F),
	TEGLASS("Allow Blast Glass to be Used as TE Hardened Glass", false),
	CLEARCHAT("Tools Clear Chat", true),
	KICKFLYING("Jetpack bypasses allow-flight property", true),
	BLOWERSPILL("Item Pump Spills Items If Dumping To Air", true);

	private String label;
	private boolean defaultState;
	private int defaultValue;
	private float defaultFloat;
	private Class type;
	private boolean enforcing = false;

	public static final ConfigRegistry[] optionList = values();

	private ConfigRegistry(String l, boolean d) {
		label = l;
		defaultState = d;
		type = boolean.class;
	}

	private ConfigRegistry(String l, boolean d, boolean tag) {
		this(l, d);
		enforcing = true;
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

	public String getLabel() {
		return label;
	}

	public boolean getState() {
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

	@Override
	public boolean getDefaultState() {
		return defaultState;
	}

	@Override
	public int getDefaultValue() {
		return defaultValue;
	}

	@Override
	public float getDefaultFloat() {
		return defaultFloat;
	}

	@Override
	public boolean isEnforcingDefaults() {
		return enforcing;
	}

	@Override
	public boolean shouldLoad() {
		return true;
	}

}
