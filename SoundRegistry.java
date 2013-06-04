/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.net.URL;

import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import Reika.DragonAPI.Libraries.ReikaJavaLibrary;

public enum SoundRegistry {

	ELECTRIC("elecengine"),
	WIND("windengine"),
	STEAM("steamengine"),
	CAR("gasengine"),
	HYDRO("hydroengine"),
	MICRO("microengine"),
	JET("jetengine"),
	KNOCKBACK("knockback"),
	PULSEJET("pulsejet"),
	PUMP("pump"),
	PILEDRIVER("piledriver"),
	SMOKE("smokealarm"),
	SPRINKLER("sprinkler"),
	FLYWHEEL("flywheel"),
	PROJECTOR("projector"),
	LOWBASS("basslo"),
	BASS("bass"),
	HIBASS("basshi"),
	LOWHARP("harplo"),
	HARP("harp"),
	HIHARP("harphi"),
	LOWPLING("plinglo"),
	PLING("pling"),
	HIPLING("plinghi");

	public static final SoundRegistry[] soundList = SoundRegistry.values();

	private static final String SOUND_FOLDER = "Reika/RotaryCraft/Sounds/";
	private static final String SOUND_PREFIX = "Reika.RotaryCraft.Sounds.";
	private static final String SOUND_DIR = "Sounds/";
	private static final String SOUND_EXT = ".ogg";
	private static final String MUSIC_FOLDER = "music/";
	private static final String MUSIC_PREFIX = "music.";

	private String path;
	private String name;

	private SoundRegistry(String n) {
		name = n;
		if (this.isNote())
			path = SOUND_FOLDER+MUSIC_FOLDER+name+SOUND_EXT;
		else
			path = SOUND_FOLDER+name+SOUND_EXT;
	}

	public static void playSound(SoundRegistry s, World world, double x, double y, double z, float vol, float pitch) {
		//ReikaJavaLibrary.pConsole(FMLCommonHandler.instance().getEffectiveSide());
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
			return;
		FMLClientHandler.instance().getClient().sndManager.playSound(s.getPlayableReference(), (float)x, (float)y, (float)z, vol, pitch);
	}

	public static void playSoundAtBlock(SoundRegistry s, World world, int x, int y, int z, float vol, float pitch) {
		playSound(s, world, x+0.5, y+0.5, z+0.5, vol, pitch);
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getPlayableReference() {
		if (this.isNote())
			return SOUND_PREFIX+MUSIC_PREFIX+name;
		return SOUND_PREFIX+name;
	}

	public URL getURL() {
		if (this.isNote())
			return RotaryCraft.class.getResource(SOUND_DIR+MUSIC_FOLDER+name+SOUND_EXT);
		else
			return RotaryCraft.class.getResource(SOUND_DIR+name+SOUND_EXT);
	}

	public boolean isNote() {
		return (this.name().contains("HARP") || this.name().contains("BASS") || this.name().contains("PLING"));
	}

	public static SoundRegistry getNoteFromVoiceAndPitch(SoundRegistry voice, String pitch) {
		return SoundRegistry.getSoundByName(pitch.toUpperCase()+voice.name());
	}

	public static SoundRegistry getSoundByName(String name) {
		for (int i = 0; i < soundList.length; i++) {
			if (soundList[i].name().equals(name))
				return soundList[i];
		}
		ReikaJavaLibrary.pConsole("\""+name+"\" does not correspond to a registered sound!");
		return null;
	}
}
