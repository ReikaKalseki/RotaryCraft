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
import Reika.DragonAPI.RegistrationException;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;


public enum ConfigRegistry {

	ENGINESOUNDS("Engine Running Sounds", true),
	GPRORES("GPR Renders Ores", false),
	INSTACUT("Instant Woodcutter", false),
	JUNGLECUTTER("Unlimited Jungle Tree Felling", false),
	RENDERFORCEFIELD("Show Force Fields", true),
	CRAFTABLEBEDROCK("Allow Craftable Bedrock", true),
	OREDICT("Ore Dictionary Interchangeability", true),
	LOGLOADING("Console Loading Info", true),
	LOCKMACHINES("Owner-Only Machine Use", false),
	//SIUNITS("SI Metric Units", true),
	FLOODLIGHTRANGE("Max Floodlight Range", 128),
	HEATRAYRANGE("Max Heat Ray Range", 128),
	BRIDGERANGE("Max Bridge Range", 128),
	FANRANGE("Max Fan Range", 128),
	AERORANGE("Max Aerosolizer Range", 128),
	VACUUMRANGE("Max Vacuum Range", 128),
	SONICRANGE("Max Sonic Weapon Range", 128),
	FORCERANGE("Max Force Field Range", 128),
	SPAWNERLIMIT("Spawner Mob Limit", 128),
	DETECTORRANGE("Player Detector Range", 128),
	BREEDERRANGE("Breeder Range", 128),
	BAITRANGE("Bait Box Range", 128),
	BAITMOBS("Max Bait Box Mob Count", 256),
	CAVEFINDERRANGE("Cave Scanner FOV", 16);

	private String label;
	private boolean defaultState;
	private int defaultValue;
	private Class type;

	public static final ConfigRegistry[] optionList = ConfigRegistry.values();

	private ConfigRegistry(String l, boolean d) {
		label = l;
		defaultState = d;
		type = boolean.class;
	}

	private ConfigRegistry(String l, int d) {
		label = l;
		defaultValue = d;
		type = int.class;
	}

	public boolean isBoolean() {
		return type == boolean.class;
	}

	public boolean isNumeric() {
		return type == int.class;
	}

	public Class getPropertyType() {
		return type;
	}

	public int setValue(Configuration config) {
		if (!this.isNumeric())
			throw new RegistrationException(RotaryCraft.instance, "Config Property \""+this.getLabel()+"\" is not numerical!");
		return config.get("Control Setup", this.getLabel(), defaultValue).getInt();
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
		return (Boolean)RotaryConfig.controls[this.ordinal()];
	}

	public int getValue() {
		return (Integer)RotaryConfig.controls[this.ordinal()];
	}

}
