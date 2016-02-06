/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.util.MathHelper;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Interfaces.Configuration.BooleanConfig;
import Reika.DragonAPI.Interfaces.Configuration.DecimalConfig;
import Reika.DragonAPI.Interfaces.Configuration.IntegerConfig;
import Reika.DragonAPI.Interfaces.Configuration.MatchingConfig;
import Reika.DragonAPI.Interfaces.Configuration.SegmentedConfigList;
import Reika.DragonAPI.Interfaces.Configuration.SelectiveConfig;
import Reika.DragonAPI.Interfaces.Configuration.StringConfig;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;


public enum ConfigRegistry implements SegmentedConfigList, SelectiveConfig, IntegerConfig, BooleanConfig, DecimalConfig, StringConfig, MatchingConfig {

	ENGINESOUNDS("Engine Running Sounds", true),
	ENGINEVOLUME("Engine Volume", 1F),
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
	//HOSTILECRASH("Crash on hostile interference from other mods", true),
	PROJECTORLINES("Render projector lines", true),
	COLORBLIND("Color Blind Mode", false),
	TURRETPLAYERS("Turrets can target players", true),
	HSLADICT("Allow RC steel to be used in other mods", false),
	PREENCHANT("Lock enchants on bedrock tools", true),
	//EXPLODEPACK("Explode jetpack if player is in lava", true),
	SPRINKLER("Sprinkler Particle Density", 4),
	HANDBOOK("Spawn with RC Handbook", true),
	CONSERVEPACK("Conservative Jetpack Firing", true),
	ALLOWBAN("Allow Build Blocking of Some Machines", false),
	LOGBLOCKS("Log Block Placement and Removal", false),
	//PACKETDELAY("Sync Packet Interval in Ticks", 1),
	FLOWSPEED("Fluid Flow Speed", 5),
	ATTACKBLOCKS("Block Damage from Destructive Machines", true),
	VOIDHOLE("Allow Bedrock Breaker to Break Y=0", false),
	JETFUELPACK("Jetpack Requires Jet Fuel", false),
	ALLOWTNTCANNON("Allow TNT Cannon", true),
	ALLOWEMP("Allow EMP", true),
	EXTRAIRON("Iron Ore Density", 1F),
	TEGLASS("Allow Blast Glass to be Used as TE Hardened Glass", false),
	CLEARCHAT("Tools Clear Chat", true),
	KICKFLYING("Jetpack bypasses allow-flight property", true),
	BLOWERSPILL("Item Pump Spills Items If Dumping To Air", true),
	EXTRACTORMAINTAIN("Extractor Drill Wears Down", false),
	HARDGRAVELGUN("Hardmode Gravel Gun", false),
	BORERMAINTAIN("Borer Requires Maintenance", false),
	NOMINERS("Disable Automining Machines", false),
	HARDEU("Hard Mode EU Compatibility", ModList.GREGTECH.isLoaded()),
	PIPEHARDNESS("Pipe Block Hardness", 0F),
	FRICTIONXP("Spawn XP from Friction Heater", true),
	SPILLERRANGE("Liquid Spiller Range, Use Zero to Disable", 16),
	POWERCLIENT("Run power transfer code on client", false),  //caused many issues
	TUTORIAL("Tutorial Mode", false),
	FRAMES("Allow Frames to move Machines (May cause corruption)", false),
	CONVERTERLOSS("Power Converter Loss Percent", 0),
	FAKEBEDROCK("Allow special bedrock tool abilities in automation", true),
	BORERGEN("Borer Chunk Gen Radius", 0),
	ALLOWLIGHTBRIDGE("Enable Light Bridge", true),
	ALLOWITEMCANNON("Enable Item Cannon", true),
	ALLOWCHUNKLOADER("Enable Chunk Loader", true),
	CHUNKLOADERSIZE("Chunk Loader Max Radius in Chunks", 8),
	RECIPEMOD("Allow Nonstandard Recipe Modifications", false),
	STRONGRECIPEMOD("Strong Recipe Editing", false),
	CORERECIPEMOD("Core Recipe Editing", "X"),
	CRAFTERPROFILE("AutoCrafter Lag Profiling And Compensation", true),
	HSLAHARVEST("Increased Harvest Level for HSLA", false),
	LATEDYNAMO("Rotational Dynamo Recipe Difficulty", 0),
	BORERPOW("Borer Power Requirement Factor", 1F),
	BEEYEAST("Use Forestry Bees To Produce Yeast", 0);

	private String label;
	private boolean defaultState;
	private int defaultValue;
	private float defaultFloat;
	private String defaultString;
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

	private ConfigRegistry(String l, String d) {
		label = l;
		defaultString = d;
		type = String.class;
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

	@Override
	public boolean isString() {
		return type == String.class;
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

	@Override
	public String getString() {
		return (String)RotaryCraft.config.getControl(this.ordinal());
	}

	@Override
	public String getDefaultString() {
		return defaultString;
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

	public static double getConverterEfficiency() {
		return MathHelper.clamp_double(1-CONVERTERLOSS.getValue()/100D, 0, 1);
	}

	public static boolean enableConverters() {
		return getConverterEfficiency() > 0;
	}

	public static int getRecipeModifyPower() {
		if (RECIPEMOD.getState()) {
			if (STRONGRECIPEMOD.getState()) {
				String s = CORERECIPEMOD.getString();
				if (isValidRecipeModString(s)) {
					return 3;
				}
				return 2;
			}
			return 1;
		}
		return 0;
	}

	private static boolean isValidRecipeModString(String s) {
		return false;//s.equals("RotaryCraft_RecipeModify@"+RotaryCraft.instance.getModVersion().toString());
	}

	@Override
	public String getCustomConfigFile() {
		switch (this) {
			case STRONGRECIPEMOD:
			case CORERECIPEMOD:
				return "*_RecipeModding";
			default:
				return null;
		}
	}

	@Override
	public boolean saveIfUnspecified() {
		switch (this) {
			case STRONGRECIPEMOD:
			case CORERECIPEMOD:
				return false;
			default:
				return true;
		}
	}

	public static float getBorerPowerMult() {
		return MathHelper.clamp_float(BORERPOW.getFloat(), 0.5F, 8F);
	}

	public static boolean enableFermenterYeast() {
		return BEEYEAST.getValue() <= 1;
	}

	public static boolean enableBeeYeast() {
		return BEEYEAST.getValue() >= 1;
	}

	@Override
	public boolean enforceMatch() {
		switch(this) {
			case GPRORES:
			case CRAFTABLEBEDROCK:
			case MODORES:
			case DIFFICULTY:
			case TABLEMACHINES:
			case ROTATEHOSE:
			case HSLADICT:
			case PREENCHANT:
			case ALLOWTNTCANNON:
			case ALLOWEMP:
			case NOMINERS:
			case PIPEHARDNESS:
			case CONVERTERLOSS:
			case ALLOWLIGHTBRIDGE:
			case ALLOWITEMCANNON:
			case ALLOWCHUNKLOADER:
			case RECIPEMOD:
			case STRONGRECIPEMOD:
			case CORERECIPEMOD:
			case LATEDYNAMO:
			case BORERPOW:
			case BEEYEAST:
				//case BLASTGATE: Not a registry config
				return true;
			default:
				return false;
		}
	}

}
