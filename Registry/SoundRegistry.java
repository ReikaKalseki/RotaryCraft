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

import Reika.DragonAPI.Instantiable.WorldLocation;
import Reika.DragonAPI.Interfaces.SoundEnum;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;

import java.net.URL;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public enum SoundRegistry implements SoundEnum {

	ELECTRIC("#elecengine", SoundCategory.AMBIENT),
	WIND("#windengine", SoundCategory.AMBIENT),
	STEAM("#steamengine", SoundCategory.AMBIENT),
	CAR("#gasengine", SoundCategory.AMBIENT),
	HYDRO("#hydroengine", SoundCategory.AMBIENT),
	MICRO("#microengine", SoundCategory.AMBIENT),
	JET("#jetengine", SoundCategory.AMBIENT),
	KNOCKBACK("knockback", SoundCategory.PLAYERS),
	PULSEJET("#pulsejet", SoundCategory.AMBIENT),
	PUMP("#pump", SoundCategory.AMBIENT),
	PILEDRIVER("piledriver", SoundCategory.BLOCKS),
	SMOKE("smokealarm", SoundCategory.AMBIENT),
	SPRINKLER("#sprinkler", SoundCategory.AMBIENT),
	FLYWHEEL("#flywheel", SoundCategory.AMBIENT),
	PROJECTOR("projector", SoundCategory.BLOCKS),
	LOWBASS("basslo", SoundCategory.MUSIC),
	BASS("bass", SoundCategory.MUSIC),
	HIBASS("basshi", SoundCategory.MUSIC),
	LOWHARP("harplo", SoundCategory.MUSIC),
	HARP("harp", SoundCategory.MUSIC),
	HIHARP("harphi", SoundCategory.MUSIC),
	LOWPLING("plinglo", SoundCategory.MUSIC),
	PLING("pling", SoundCategory.MUSIC),
	HIPLING("plinghi", SoundCategory.MUSIC),
	FRICTION("#friction", SoundCategory.AMBIENT),
	CRAFT("#craft", SoundCategory.PLAYERS),
	AIRCOMP("#compress", SoundCategory.AMBIENT),
	PNEUMATIC("#pneu", SoundCategory.AMBIENT),
	LINEBUILDER("linebuild", SoundCategory.BLOCKS),
	JETPACK("pack", SoundCategory.AMBIENT),
	DIESEL("#diesel", SoundCategory.AMBIENT),
	BELT("#belt", SoundCategory.AMBIENT),
	FAN("#fan", SoundCategory.AMBIENT),
	SPARK("spark", SoundCategory.AMBIENT),
	DYNAMO("#dynamo", SoundCategory.AMBIENT),
	JETDAMAGE("jetdamage", SoundCategory.BLOCKS),
	INGESTION("ingest_short", SoundCategory.BLOCKS),
	FRIDGE("#fridge", SoundCategory.AMBIENT),
	JETSTART("#jetstart", SoundCategory.BLOCKS),
	SONIC("#sonic", SoundCategory.AMBIENT);

	public static final SoundRegistry[] soundList = SoundRegistry.values();

	public static final String PREFIX = "Reika/RotaryCraft/";
	public static final String SOUND_FOLDER = "Sounds/";
	private static final String SOUND_PREFIX = "Reika.RotaryCraft.Sounds.";
	private static final String SOUND_DIR = "Sounds/";
	private static final String SOUND_EXT = ".ogg";
	private static final String MUSIC_FOLDER = "music/";
	private static final String MUSIC_PREFIX = "music.";

	private final String path;
	private final String name;
	private final SoundCategory category;

	private boolean isVolumed = false;

	private SoundRegistry(String n, SoundCategory cat) {
		if (n.startsWith("#")) {
			isVolumed = true;
			n = n.substring(1);
		}
		name = n;
		if (this.isNote())
			path = SOUND_FOLDER+MUSIC_FOLDER+name+SOUND_EXT;
		else
			path = SOUND_FOLDER+name+SOUND_EXT;
		category = cat;
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
		ReikaPacketHelper.sendSoundPacket(RotaryCraft.packetChannel, this, world, x, y, z, vol*this.getModVolume(), pitch);
	}

	public void playSoundAtBlock(World world, int x, int y, int z, float vol, float pitch) {
		this.playSound(world, x+0.5, y+0.5, z+0.5, vol, pitch);
	}

	public void playSoundAtBlock(World world, int x, int y, int z) {
		this.playSound(world, x+0.5, y+0.5, z+0.5, 1, 1);
	}

	public void playSoundAtBlock(TileEntity te) {
		this.playSoundAtBlock(te.worldObj, te.xCoord, te.yCoord, te.zCoord);
	}

	public void playSoundAtBlock(WorldLocation loc) {
		this.playSoundAtBlock(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord);
	}

	public String getName() {
		return this.name();
	}

	public String getPath() {
		return path;
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

	@Override
	public SoundCategory getCategory() {
		return category;
	}
}