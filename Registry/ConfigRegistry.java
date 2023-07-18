/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
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
import Reika.DragonAPI.Interfaces.Configuration.UserSpecificConfig;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;


public enum ConfigRegistry implements SegmentedConfigList, SelectiveConfig, IntegerConfig, BooleanConfig, DecimalConfig, StringConfig, MatchingConfig, UserSpecificConfig {

	ENGINEVOLUME("Engine Volume", 1F),
	GPRORES("GPR Renders Ores", true),
	INSTACUT("Instant Woodcutter", true), //Whether the woodcutter cuts blocks instantly as opposed to making them fall to the ground
	CRAFTABLEBEDROCK("Allow Craftable Bedrock", true),
	LOCKMACHINES("Owner-Only Machine Use", false),
	MACHINEVOLUME("Machine Volume Multiplier", 1.0F),
	FLOODLIGHTRANGE("Max Floodlight Range", 128),
	HEATRAYRANGE("Max Heat Ray Range", 128),
	BRIDGERANGE("Max Bridge Range", 128),
	FANRANGE("Max Fan Range", 128),
	AERORANGE("Max Aerosolizer Range", 128),
	VACUUMRANGE("Max Vacuum Range", 128),
	//SONICRANGE("Max Sonic Weapon Range", 128),
	FORCERANGE("Max Force Field Range", 128),
	SONICBORERRANGE("Sonic Borer Range", 512),
	SPAWNERLIMIT("Spawner Mob Limit", 128),
	DETECTORRANGE("Player Detector Range", 128),
	BREEDERRANGE("Breeder Range", 128),
	BAITRANGE("Bait Box Range", 24),
	LINEBUILDER("Block Ram Range", 512),
	BAITMOBS("Max Bait Box Mob Count", 256),
	CAVEFINDERRANGE("Cave Scanner FOV", 16),
	BANRAIN("Disable Silver Iodide Cannon Rain", false),
	ACHIEVEMENTS("Enable Achievements", true),
	MODORES("Force Inter-Mod Ore Compatibility", true),
	BEDPICKSPAWNERS("Allow Bedrock Pickaxe to Harvest Spawners", true),
	SPAWNERLEAK("Spawn Mobs When Harvesting Spawners By Hand", true),
	BLOCKDAMAGE("Direct Block Damage from Machine Failures", true),
	DIFFICULTY("Difficulty Control", 2), //1-3 for easy/medium/hard; controls some recipe/fuel costs, failure chances, and other numerical tweaks. Progression is <b>not</b> changed.
	//ALARM("Machine Warning Alarms", false), //Whether to
	BIOMEBLOCKS("Terraformer Block Editing", true), //Whether the terraformer can not only change the biome but set blocks, eg spawning trees
	DYNAMICHANDBOOK("Reload Handbook Data on Open", false),
	TABLEMACHINES("Crafting Table can Make Machines", false), //If false, the worktable will be required to craft machines
	EMPLOAD("EMP Charging Speed", 4),
	ROTATEHOSE("Rotate Hose/Pipe/Fuel Line Recipes", false), //Recipe conflict avoidance
	RAILGUNDAMAGE("Railgun Block Damage", true),
	GRAVELPLAYER("Allow Gravel Gun PvP", true),
	CHESTGEN("Chest Generation Tier", 4), //How advanced/expensive items generating in worldgen chests can be. Higher is more
	//HOSTILECRASH("Crash on hostile interference from other mods", true),
	PROJECTORLINES("Render projector lines", true),
	COLORBLIND("Color Blind Mode", false),
	TURRETPLAYERS("Turrets can target players", true),
	HSLADICT("Allow RC steel to be used in other mods", false), //ie registers HSLA as ingotSteel
	PREENCHANT("Lock enchants on bedrock tools", true),
	//EXPLODEPACK("Explode jetpack if player is in lava", true),
	SPRINKLER("Sprinkler Particle Density", 4), //0-4, with lower being fewer particles
	HANDBOOK("Spawn with RC Handbook", true),
	CONSERVEPACK("Conservative Jetpack Firing", true), //Whether to make the jetpack be a bit less likely to fire from things like jumping, helping reduce fuel usage at the cost of making it a bit less immediate
	ALLOWBAN("Allow Build Blocking of Some Machines", false),
	LOGBLOCKS("Log Block Placement and Removal", false),
	//PACKETDELAY("Sync Packet Interval in Ticks", 1),
	FLOWSPEED("Fluid Flow Speed", 5),
	ATTACKBLOCKS("Block Damage from Destructive Machines", true),
	VOIDHOLE("Allow Bedrock Breaker to Break Y=0", false),
	JETFUELPACK("Jetpack Requires Jet Fuel", false),
	ALLOWTNTCANNON("Allow TNT Cannon", true),
	ALLOWEMP("Allow EMP", true),
	EXTRAIRON("Iron Ore Density", 1F), //If set to >1, this will spawn extra iron ore in the world, to help with the fact RC uses a lot of it. Does nothing at <= 1
	TEGLASS("Allow Blast Glass to be Used as TE Hardened Glass", false),
	CLEARCHAT("Tools Clear Chat", true), //Whether using a chat-writing tool will clear your chat buffer to make it easier to read their output
	KICKFLYING("Jetpack bypasses allow-flight property", true),
	BLOWERSPILL("Item Pump Spills Items If Dumping To Air", true), //Whether a PIP without a target inventory will spray the items everywhere
	EXTRACTORMAINTAIN("Extractor Drill Wears Down", false),
	HARDGRAVELGUN("Hardmode Gravel Gun", false), //Adjusts charge-damage curves to require more charge per unit damage and cap the damage at a lower value
	BORERMAINTAIN("Borer Requires Maintenance", false), //Makes the borer's operation require a drill item it slowly consumes
	NOMINERS("Disable Automining Machines", false),
	HARDEU("Hard Mode EU Compatibility", ModList.GREGTECH.isLoaded()), //TypeHelper to Website Generator: boolean; Whether to make EU compat more difficult, suitable for GT packs
	PIPEHARDNESS("Pipe Block Hardness", 0F),
	FRICTIONXP("Spawn XP from Friction Heater", true),
	SPILLERRANGE("Liquid Spiller Range, Use Zero to Disable", 16),
	POWERCLIENT("Run power transfer code on client", false),  //Whether to also run power sync code on the client, to help with sync at the cost of additional load on the client tick
	//TUTORIAL("Tutorial Mode", false),
	FRAMES("Allow Frames to move Machines (May cause corruption)", false),
	CONVERTERLOSS("Power Converter Loss Percent", 0), //Efficiency of the RC-to-other-mod converters. 0% loss = 100% efficiency
	FAKEBEDROCK("Allow special bedrock tool abilities in automation", true), //Whether things like the bedrock axe and its tree cutting will apply when used by fake players, eg auto activators
	BORERGEN("Borer Chunk Gen Radius", 0), //How big of an AoE of chunks will the borer generate around the mining head
	ALLOWLIGHTBRIDGE("Enable Light Bridge", true),
	ALLOWITEMCANNON("Enable Item Cannon", true),
	ALLOWCHUNKLOADER("Enable Chunk Loader", true),
	CHUNKLOADERSIZE("Chunk Loader Max Radius in Chunks", 8),
	RECIPEMOD("Allow Nonstandard Recipe Modifications", false),
	STRONGRECIPEMOD("Strong Recipe Editing", false),
	//CORERECIPEMOD("Core Recipe Editing", "X"),
	CRAFTERPROFILE("AutoCrafter Lag Profiling And Compensation", true), //Make the autocrafter tick less often to reduce lag
	HSLAHARVEST("Increased Harvest Level for HSLA tools", false),
	LATEDYNAMO("Rotational Dynamo Recipe Difficulty", 0), //0-5 to choose a gating item; see above
	BORERPOW("Borer Power Requirement Factor", 1F),
	BEEYEAST("Use Forestry Bees To Produce Yeast", 0), //0 = disabled, 1 = slippery bee also produces yeast (alternate source); 2 = the bee is the only source of yeast
	HARDCONVERTERS("Harder Converter Unit Recipes", false),
	OREALUDUST("Allow other mods' aluminum dust to make Silicon", false),
	GATEBLAST("Enable Blast Furnace recipe gating", false), //Whether to enable the recipe gate functionality for the blast furnace
	GATEWORK("Enable Worktable recipe gating", false), //Whether to enable the recipe gate functionality for the worktable
	VACPOWER("Item Vacuum Power Per Meter", (int)PowerReceivers.VACUUM.getMinPower()/4), //TypeHelper to Website Generator: int
	HYDROSTREAMFALLMAX("Streams Waterfall Min Height for Max Hydrokinetic Yield", 8), //How tall a Streams waterfall must be to count as max yield for a hydrokinetic
	TINKERFLAKES("TiC Smeltery Flake Yield Amount In Ingots", 1.5F),
	IC2BLAZECOMPRESS("Increase Blaze Powder To Rod Cost In IC2 Compressor (Exploit Fix)", true), //Normally the ic2 compressor requires 5 powder per rod, but as the grinder makes six powder per rod this is a feedback exploit. This option increases the ic2 cost to 8.
	FREEWATER("Free Water Production Factor", 1F), //How much if any free water can be produced by things like the pump power surplus, spillway "scraping" (ie back to a waterfall) and rain in reservoirs
	SNEAKWINGS("Jetpack wings enable with sneak vs disable", false);

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

	public Class getPropertyType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public boolean isString() {
		return type == String.class;
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
			if (STRONGRECIPEMOD.getState()) {/*
				String s = CORERECIPEMOD.getString();
				if (isValidRecipeModString(s)) {
					return 3;
				}*/
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
				//case CORERECIPEMOD:
				return "*_RecipeModding";
			default:
				return null;
		}
	}

	@Override
	public boolean isAccessible() {
		return true;
	}

	@Override
	public boolean saveIfUnspecified() {
		switch (this) {
			case STRONGRECIPEMOD:
				//case CORERECIPEMOD:
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

	public static float getSmelteryFlakeYield() {
		return MathHelper.clamp_float(TINKERFLAKES.getFloat(), 0.25F, 2F);
	}

	public static float getFreeWaterProduction() {
		return MathHelper.clamp_float(FREEWATER.getFloat(), 0.1F, 2F);
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
				//case CORERECIPEMOD:
			case LATEDYNAMO:
			case BORERPOW:
			case BEEYEAST:
				//case BLASTGATE: Not a registry config
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean isUserSpecific() {
		switch(this) {
			case ENGINEVOLUME:
			case MACHINEVOLUME:
			case PROJECTORLINES:
			case DYNAMICHANDBOOK:
			case COLORBLIND:
			case SPRINKLER:
			case CLEARCHAT:
			case POWERCLIENT:
				return true;
			default:
				return false;
		}
	}

}
