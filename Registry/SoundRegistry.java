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

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;

import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.SoundList;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public enum SoundRegistry implements SoundList {

	ELECTRIC("#elecengine"),
	WIND("#windengine"),
	STEAM("#steamengine"),
	CAR("#gasengine"),
	HYDRO("#hydroengine"),
	MICRO("#microengine"),
	JET("#jetengine"),
	KNOCKBACK("knockback"),
	PULSEJET("#pulsejet"),
	PUMP("#pump"),
	PILEDRIVER("piledriver"),
	SMOKE("smokealarm"),
	SPRINKLER("#sprinkler"),
	FLYWHEEL("#flywheel"),
	PROJECTOR("projector"),
	LOWBASS("basslo"),
	BASS("bass"),
	HIBASS("basshi"),
	LOWHARP("harplo"),
	HARP("harp"),
	HIHARP("harphi"),
	LOWPLING("plinglo"),
	PLING("pling"),
	HIPLING("plinghi"),
	FRICTION("#friction"),
	CRAFT("#craft"),
	AIRCOMP("#compress"),
	PNEUMATIC("#pneu"),
	LINEBUILDER("linebuild"),
	JETPACK("pack"),
	DIESEL("#diesel"),
	BELT("#belt"),
	FAN("#fan"),
	SPARK("spark"),
	DYNAMO("#dynamo"),
	JETDAMAGE("jetdamage"),
	INGESTION("ingest_short"),
	FRIDGE("#fridge"),
	JETSTART("#jetstart"),
	SONIC("#sonic");

	public static final SoundRegistry[] soundList = SoundRegistry.values();

	public static final String SOUND_FOLDER = "Reika/RotaryCraft/Sounds/";
	private static final String SOUND_PREFIX = "Reika.RotaryCraft.Sounds.";
	private static final String SOUND_DIR = "Sounds/";
	private static final String SOUND_EXT = ".ogg";
	private static final String MUSIC_FOLDER = "music/";
	private static final String MUSIC_PREFIX = "music.";

	private String path;
	private String name;

	private boolean isVolumed = false;

	private SoundRegistry(String n) {
		if (n.startsWith("#")) {
			isVolumed = true;
			n = n.substring(1);
		}
		name = n;
		if (this.isNote())
			path = SOUND_FOLDER+MUSIC_FOLDER+name+SOUND_EXT;
		else
			path = SOUND_FOLDER+name+SOUND_EXT;
	}

	public float getSoundVolume() {
		float vol = ConfigRegistry.MACHINEVOLUME.getFloat();
		if (vol < 0)
			vol = 0;
		if (vol > 1)
			vol = 1F;
		return vol;
	}

	public float getModVolume() {
		if (!isVolumed)
			return 1F;
		else
			return this.getSoundVolume();
	}

	public void playSound(World world, double x, double y, double z, float vol, float pitch) {
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
			return;
		//Packet250CustomPayload p = new Packet62LevelSound(s.getPlayableReference(), x, y, z, vol, pitch);
		//PacketDispatcher.sendPacketToAllInDimension(p, world.provider.dimensionId);
		ReikaPacketHelper.sendSoundPacket(RotaryCraft.packetChannel, this.getPlayableReference(), world, x, y, z, vol*this.getModVolume(), pitch);
	}

	public void playSoundAtBlock(World world, int x, int y, int z, float vol, float pitch) {
		this.playSound(world, x+0.5, y+0.5, z+0.5, vol, pitch);
	}

	public void playSoundAtBlock(World world, int x, int y, int z) {
		this.playSound(world, x+0.5, y+0.5, z+0.5, 1, 1);
	}

	public String getName() {
		return this.name();
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

	public static void playSoundPacket(DataInputStream in) {
		String name;
		try {
			name = Packet.readString(in, Short.MAX_VALUE);
			//ReikaJavaLibrary.pConsole(name+" on "+FMLCommonHandler.instance().getEffectiveSide());
			double x = in.readDouble();
			double y = in.readDouble();
			double z = in.readDouble();
			float v = in.readFloat();
			float p = in.readFloat();
			FMLClientHandler.instance().getClient().sndManager.playSound(name, (float)x, (float)y, (float)z, v, p);
		}
		catch (IOException e) {
			e.printStackTrace();
			ReikaJavaLibrary.pConsole("Sound could not be played due to IOException!");
		}
	}
}
