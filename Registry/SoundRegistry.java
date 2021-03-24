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

import java.net.URL;
import java.util.HashMap;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Interfaces.Registry.SoundEnum;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.RotaryCraft.RotaryCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public enum SoundRegistry implements SoundEnum {

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
	//JETDAMAGE("jetdamage"),
	INGESTION("ingest_short"),
	FRIDGE("#fridge"),
	JETSTART("#jetstart"),
	SONIC("#sonic"),
	SHORTJET("shortjet"),
	AFTERBURN("afterburner"),
	RUMBLE("rumble2"),
	COIL("#coil"),
	GATLINGRELOAD("gatlingreload"),
	GATLING("gatling"),
	FLAMETURRET("flameturret");

	public static final String PREFIX = "Reika/RotaryCraft/";
	public static final String SOUND_FOLDER = "Sounds/";
	private static final String SOUND_PREFIX = "Reika.RotaryCraft.Sounds.";
	private static final String SOUND_DIR = "Sounds/";
	private static final String SOUND_EXT = ".ogg";
	private static final String MUSIC_FOLDER = "music/";
	private static final String MUSIC_PREFIX = "music.";

	private final String path;
	private final String name;

	private boolean isVolumed = false;

	private static final HashMap<String, SoundRegistry> soundNames = new HashMap();

	private SoundRegistry(String n) {
		if (n.startsWith("#")) {
			isVolumed = true;
			n = n.substring(1);
		}
		name = n;
		if (this.isNote())
			path = PREFIX+SOUND_FOLDER+MUSIC_FOLDER+name+SOUND_EXT;
		else
			path = PREFIX+SOUND_FOLDER+name+SOUND_EXT;
	}

	public float getSoundVolume() {
		float vol = ConfigRegistry.MACHINEVOLUME.getFloat();
		if (this.isEngineSound()) {
			vol *= ConfigRegistry.ENGINEVOLUME.getFloat();
		}
		if (vol < 0)
			vol = 0;
		if (vol > 1)
			vol = 1F;
		return vol;
	}

	@Override
	public float getModulatedVolume() {
		if (!isVolumed)
			return 1F;
		else
			return this.getSoundVolume();
	}

	public void playSound(Entity e) {
		this.playSound(e, 1, 1);
	}

	public void playSound(Entity e, float vol, float pitch) {
		this.playSound(e.worldObj, e.posX, e.posY, e.posZ, vol, pitch);
	}

	public void playSound(World world, double x, double y, double z, float vol, float pitch) {
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
			return;
		//Packet250CustomPayload p = new Packet62LevelSound(s.getPlayableReference(), x, y, z, vol, pitch);
		//PacketDispatcher.sendPacketToAllInDimension(p, world.provider.dimensionId);
		ReikaSoundHelper.playSound(this, world, x, y, z, vol/* *this.getModulatedVolume()*/, pitch);
	}

	public void playSound(World world, double x, double y, double z, float vol, float pitch, boolean attenuate) {
		if (world.isRemote)
			return;
		ReikaSoundHelper.playSound(this, world, x, y, z, vol/* *this.getModulatedVolume()*/, pitch, attenuate);
	}

	public void playSoundAtBlock(World world, int x, int y, int z, float vol, float pitch) {
		this.playSound(world, x+0.5, y+0.5, z+0.5, vol, pitch);
	}

	public void playSoundAtBlock(World world, int x, int y, int z) {
		this.playSound(world, x+0.5, y+0.5, z+0.5, 1, 1);
	}

	public void playSoundAtBlock(TileEntity te) {
		this.playSoundAtBlock(te, 1, 1);
	}

	public void playSoundAtBlock(TileEntity te, float vol, float pitch) {
		this.playSoundAtBlock(te.worldObj, te.xCoord, te.yCoord, te.zCoord, vol, pitch);
	}

	public void playSoundAtBlock(WorldLocation loc) {
		this.playSoundAtBlock(loc.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord);
	}

	public void playSoundNoAttenuation(World world, double x, double y, double z, float vol, float pitch, int broadcast) {
		if (world.isRemote)
			return;
		//ReikaSoundHelper.playSound(this, RotaryCraft.packetChannel, te.worldObj, x, y, z, vol/* *this.getModulatedVolume()*/, pitch, false);
		ReikaPacketHelper.sendSoundPacket(this, world, x, y, z, vol, pitch, false, broadcast);
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

	public static SoundRegistry getSoundByName(String s) {
		return soundNames.get(s);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SoundCategory getCategory() {
		return SoundCategory.MASTER;
	}

	@Override
	public boolean canOverlap() {
		return this == JETPACK || /*this == JETDAMAGE || */this == RUMBLE;
	}

	private boolean isEngineSound() {
		return name.endsWith("engine");
	}

	@Override
	public boolean attenuate() {
		return true;
	}

	@Override
	public boolean preload() {
		return this == JETSTART;
	}

	static {
		for (SoundRegistry s : values()) {
			soundNames.put(s.name(), s);
		}
	}
}
