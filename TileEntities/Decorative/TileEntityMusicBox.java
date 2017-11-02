/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Instantiable.MusicScore;
import Reika.DragonAPI.Instantiable.IO.MIDIInterface;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Interfaces.TileEntity.BreakAction;
import Reika.DragonAPI.Interfaces.TileEntity.GuiController;
import Reika.DragonAPI.Interfaces.TileEntity.TriggerableAction;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaMusicHelper.MusicKey;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.NoteEvent;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMusicBox extends TileEntityPowerReceiver implements GuiController, BreakAction, TriggerableAction {

	/** 16 channels, 7 voices, 64 pitch states */
	private final ArrayList<Note>[] musicQueue;

	private boolean isOneTimePlaying = false;

	public static final int LOOPPOWER = 1024;

	private int[] playDelay = new int[16];
	private int[] playIndex = new int[16];

	private static final int[] channelColors = {
		0x3636FF, 0xD336FF, 0xFFACAC, 0xFF3636, 0xFFAC36, 0xD3D336,
		0x65BC8F, 0x36D336, 0x36FFFF, 0x58ABF9, 0x8484FF, 0xFF36FF,
		0x8436FF, 0xB49C8A, 0x8FA9B5, 0x94B581
	};

	public TileEntityMusicBox() {
		super();

		musicQueue = new ArrayList[16];
		for (int i = 0; i < 16; i++) {
			musicQueue[i] = new ArrayList();
		}
	}

	public static int getColorForChannel(int channel) {
		return channelColors[channel];
	}

	public int getMusicLength() {
		int size = -1;
		for (int i = 0; i < 16; i++) {
			size = Math.max(size, musicQueue[i].size());
		}
		return size;
	}

	public int getChannelLength(int channel) {
		return musicQueue[channel].size();
	}

	public ArrayList<Note> getNotesAtIndex(int index) {
		ArrayList<Note> li = new ArrayList();
		for (int i = 0; i < 16; i++) {
			if (musicQueue[i].size() > index)
				li.add(musicQueue[i].get(index));
		}
		return li;
	}

	public List<Note> getNotesInChannel(int channel) {
		return Collections.unmodifiableList(musicQueue[channel]);
	}

	@Override
	protected void onFirstTick(World world, int x, int y, int z) {
		if (this.hasSavedFile())
			this.read();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		//ReikaJavaLibrary.pConsole(Arrays.toString(musicQueue), Side.SERVER);

		if (world.isRemote) {
			return;
		}

		if (power < LOOPPOWER) {
			if (!isOneTimePlaying) {
				this.startPlaying();
				return;
			}
		}
		else {
			isOneTimePlaying = false;
		}

		if (isOneTimePlaying || power >= LOOPPOWER) {
			for (int i = 0; i < 16; i++) {
				if (playDelay[i] > 0)
					playDelay[i]--;
				if (playDelay[i] == 0) {
					if (!musicQueue[i].isEmpty()) {
						if (playIndex[i] < musicQueue[i].size()) {
							Note n = musicQueue[i].get(playIndex[i]);
							if (n != null) {
								this.playNote(i, n);
							}
						}
					}
					else {
						playIndex[i] = 0;
					}
				}
			}
		}
		//ReikaJavaLibrary.pConsole(Arrays.toString(playDelay)+":"+Arrays.toString(playIndex), Side.SERVER);
		if (this.isAtEnd() && this.hasNoDelays()) {
			this.resetPlayback();
		}
	}

	@Override
	protected void onPositiveRedstoneEdge() {
		isOneTimePlaying = true;
		this.startPlaying();
	}

	private boolean hasNoDelays() {
		for (int i = 0; i < 16; i++) {
			if (playDelay[i] > 0)
				return false;
		}
		return true;
	}

	private boolean isAtEnd() {
		for (int i = 0; i < 16; i++) {
			if (playIndex[i] < musicQueue[i].size()-1)
				return false;
		}
		return true;
	}

	private void resetPlayback() {
		for (int i = 0; i < 16; i++) {
			playIndex[i] = 0;
		}
		isOneTimePlaying = false;
	}

	private void startPlaying() {
		for (int i = 0; i < 16; i++) {
			playIndex[i] = 0;
			playDelay[i] = 0;
		}
	}

	private void playNote(int channel, Note n) {
		if (!n.isRest()) {
			for (int i = 0; i < 3; i++)
				n.play(worldObj, xCoord, yCoord, zCoord);
			ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.MUSICPARTICLE.getMinValue(), this, new PacketTarget.RadiusTarget(this, 32));
		}
		playDelay[channel] = n.length.tickLength;
		playIndex[channel]++;
		NoteEvent e = new NoteEvent(this, n.pitch, n.getTickLength(), channel);
		MinecraftForge.EVENT_BUS.post(e);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	protected void animateWithTick(World world, int x, int y, int z) {

	}

	public void addNote(int channel, Note n) {
		musicQueue[channel].add(n);
	}

	public void addRest(int channel, Note n) {
		n = n.getRest();
		musicQueue[channel].add(n);
	}

	public void backspace(int channel) {
		musicQueue[channel].remove(musicQueue[channel].size()-1);
	}

	public void clearChannel(int channel) {
		musicQueue[channel].clear();
	}

	public void clearMusic() {
		for (int i = 0; i < 16; i++)
			this.clearChannel(i);
	}

	@Override
	protected void writeSyncTag(NBTTagCompound NBT) {
		super.writeSyncTag(NBT);

		NBT.setBoolean("onetime", isOneTimePlaying);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT) {
		super.readSyncTag(NBT);

		isOneTimePlaying = NBT.getBoolean("onetime");
	}

	@SideOnly(Side.CLIENT)
	public void loadLocalMIDI(String file) {
		File f = new File(file);
		if (!f.exists() || f.isDirectory()) {
			RotaryCraft.logger.logError("Could not load local MIDI: file is not a MIDI file!");
			return;
		}
		if (f.length() > 60000) {
			RotaryCraft.logger.logError("Could not load local MIDI: file is too large ("+f.length()+" bytes) and cannot be safely used!");
			return;
		}
		try {
			MusicScore mus = new MIDIInterface(f).fillToScore(true).scaleSpeed(11);
			this.dispatchTrack(mus);
		}
		catch (Exception e) {
			RotaryCraft.logger.logError(e.toString());
			e.printStackTrace();
		}
	}

	private void dispatchTrack(MusicScore mus) {
		ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue()+7, this);
		for (int i = 0; i < mus.countTracks(); i++) {
			Map<Integer, Collection<MusicScore.Note>> track = mus.getTrack(i);
			int lastNoteTime = -1;
			int lastNoteLength = -1;
			for (int time : track.keySet()) {
				Collection<MusicScore.Note> c = track.get(time);
				for (MusicScore.Note n : c) {
					if (n != null && n.key != null) {
						if (lastNoteTime >= 0) {
							int t1 = lastNoteTime+lastNoteLength;
							int dt = time-t1;
							while (dt >= NoteLength.SIXTEENTH.tickLength) {
								NoteLength l = NoteLength.getLargestNotMoreThan(dt);
								t1 += l.tickLength;
								dt = time-t1;
								this.sendNote(0, i, l, Instrument.REST);
							}
						}
						Note n2 = Note.getFromMusicScore(n);
						this.sendNote(n2.pitch, i, n2.length, n2.voice);//ReikaPacketHelper.sendDataPacket(RotaryCraft.packetChannel, PacketRegistry.FIXEDMUSICNOTE.getMinValue(), this, i, n.key.ordinal(), n.length, n.voice == -1 ? 1 : 0, time);
						lastNoteTime = time;
						lastNoteLength = n2.length.tickLength;
						break;
					}
				}
			}
		}
	}

	public void save() {
		if (worldObj.isRemote)
			return;
		try {
			File save = DimensionManager.getCurrentSaveRootDirectory();
			String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
			File dir = new File(save.getPath()+"/RotaryCraft/");
			if (!dir.exists())
				dir.mkdir();
			File f = new File(save.getPath()+"/RotaryCraft/"+name);
			if (f.exists())
				f.delete();
			f.createNewFile();
			PrintWriter p = new PrintWriter(f);
			f.createNewFile();

			int length = this.getMusicLength();
			for (int i = 0; i < length; i++) {
				for (int k = 0; k < 16; k++) {
					if (musicQueue[k].size() > i) {
						Note n = musicQueue[k].get(i);
						String s = n.toSerialString();
						p.append(s+";");
					}
					else {
						p.append("-;");
					}
				}
				p.append("\n");
			}

			p.close();
		}
		catch (Exception e) {
			ReikaChatHelper.write(e.getCause()+" caused the save to fail!");
			e.printStackTrace();
		}
	}

	public boolean hasSavedFile() {
		if (worldObj.isRemote)
			return false;
		File save = DimensionManager.getCurrentSaveRootDirectory();
		String base = save.getPath();
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		File f = new File(base+"/RotaryCraft/"+name);
		return f.exists();
	}

	public void read() {
		if (worldObj.isRemote)
			return;
		File save = DimensionManager.getCurrentSaveRootDirectory();
		//ReikaJavaLibrary.pConsole(musicFile);
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		String path = save.getPath()+"/RotaryCraft/"+name;
		this.readFile(path, false);
	}

	private void readFile(String path, boolean internal) {
		this.clearMusic();
		int linecount = -1;
		try {
			BufferedReader p;
			if (internal)
				p = new BufferedReader(new InputStreamReader(RotaryCraft.class.getResourceAsStream(path)));
			else
				p = ReikaFileReader.getReader(path);
			String line = p.readLine();
			while (line != null) {
				linecount++;
				String[] pieces = line.split(";");
				for (int i = 0; i < 16; i++) {
					Note n = Note.getFromSerialString(pieces[i]);
					if (n != null) {
						musicQueue[i].add(n);
						//ReikaJavaLibrary.pConsole(n);
					}
				}
				line = p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			if (linecount >= 0)
				RotaryCraft.logger.log("LINE "+linecount+":\n");
			e.printStackTrace();
			ReikaChatHelper.write(e.getMessage()+" caused the read to fail!");
		}
	}

	public void loadDemo() {
		if (worldObj.isRemote)
			return;
		String path = "Resources/demomusic.rcmusic";
		this.readFile(path, true);
		isOneTimePlaying = true;
	}

	@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.MUSICBOX;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public void setMusicFromDisc(ItemStack is) {
		if (worldObj.isRemote)
			return;
		if (is.getItem() != ItemRegistry.DISK.getItemInstance())
			return;
		if (is.stackTagCompound == null)
			return;
		this.clearMusic();
		try {
			for (int i = 0; i < 16; i++) {
				if (is.stackTagCompound.hasKey("ch"+i)) {
					NBTTagList li = is.stackTagCompound.getTagList("ch"+i, NBTTypes.COMPOUND.ID);
					for (int k = 0; k < li.tagCount(); k++) {
						NBTTagCompound nbt = li.getCompoundTagAt(k);
						//ReikaJavaLibrary.pConsole(i+":"+k+":"+nbt, Side.SERVER);
						Note n = Note.readFromNBT(nbt);
						this.addNote(i, n);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMusicToDisk(ItemStack is) {
		if (worldObj.isRemote)
			return;
		if (is.getItem() != ItemRegistry.DISK.getItemInstance())
			return;
		is.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < 16; i++) {
			NBTTagList li = new NBTTagList();
			ArrayList<Note> channel = musicQueue[i];
			for (int k = 0; k < channel.size(); k++) {
				Note n = channel.get(k);
				NBTTagCompound nbt = n.writeToNBT();
				//ReikaJavaLibrary.pConsole(i+":"+k+":"+nbt, Side.SERVER);
				li.appendTag(nbt);
			}
			is.stackTagCompound.setTag("ch"+i, li);
		}
	}

	private void deleteFiles(int x, int y, int z) {
		File save = DimensionManager.getCurrentSaveRootDirectory();
		//ReikaJavaLibrary.pConsole(musicFile);
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		File f = new File(save.getPath()+"/RotaryCraft/"+name);
		if (f.exists())
			f.delete();
	}

	public static final class Note {

		public final NoteLength length;
		/** Standard MC notation */
		public final int pitch;

		public final Instrument voice;

		private static final String[] notes = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};

		public Note(NoteLength length, int pitch, Instrument voice) {
			this.length = length;
			this.pitch = pitch;
			this.voice = voice;
		}

		public static String getNoteName(int pitch) {
			return notes[pitch%12];
		}

		public String getName() {
			return notes[pitch%12];
		}

		public int getTickLength() {
			return length.tickLength;
		}

		public boolean isRest() {
			return pitch < 0;
		}

		public Note getRest() {
			return new Note(length, -1, voice);
		}

		public void play(TileEntityMusicBox te) {
			this.play(te.worldObj, te.xCoord, te.yCoord, te.zCoord);
		}

		public void play(World world, int x, int y, int z) {
			if (this.isRest())
				return;

			String pit;
			float pitch = (float)Math.pow(2.0D, (this.pitch-24)/12.0D);
			float volume = 200/100F;
			if (pitch < 0.5F) {
				pitch *= 2F;
				pit = "low";
			}
			else if (pitch > 2F) {
				pitch *= 0.25F;
				pit = "hi";
			}
			else
				pit = "";
			switch(voice) {
				case GUITAR:
					SoundRegistry.getNoteFromVoiceAndPitch(SoundRegistry.HARP, pit).playSoundAtBlock(world, x, y, z, volume, pitch);
					break;
				case BASS:
					SoundRegistry.getNoteFromVoiceAndPitch(SoundRegistry.BASS, pit).playSoundAtBlock(world, x, y, z, volume, pitch);
					break;
				case PLING:
					SoundRegistry.getNoteFromVoiceAndPitch(SoundRegistry.PLING, pit).playSoundAtBlock(world, x, y, z, volume, pitch);
					break;
				case BASSDRUM:
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "note.bd", volume, pitch);
					break;
				case SNARE:
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "note.snare", volume, pitch);
					break;
				case CLAVE:
					world.playSoundEffect(x+0.5, y+0.5, z+0.5, "note.hat", volume, pitch);
					break;
				default:
					break;
			}
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Note) {
				Note n = (Note)o;
				return n.length == length && n.pitch == pitch && n.voice == voice;
			}
			return false;
		}

		@Override
		public String toString() {
			if (this.isRest()) {
				return ReikaStringParser.capFirstChar(length.name())+" Rest";
			}
			StringBuilder sb = new StringBuilder();
			sb.append(voice);
			sb.append(" plays ");
			sb.append(pitch);
			sb.append(" for ");
			sb.append(length);
			return sb.toString();
		}

		public String toSerialString() {
			StringBuilder sb = new StringBuilder();
			sb.append(length.ordinal());
			sb.append(":");
			sb.append(pitch);
			sb.append(":");
			sb.append(voice.ordinal());
			return sb.toString();
		}

		protected static Note getFromSerialString(String s) {
			if (s.equals("-"))
				return null;
			String[] sgs = s.split(":");
			int l1 = Integer.parseInt(sgs[0]);
			int note = Integer.parseInt(sgs[1]);
			int i1 = Integer.parseInt(sgs[2]);
			return new Note(NoteLength.values()[l1], note, Instrument.values()[i1]);
		}

		public static Note readFromNBT(NBTTagCompound NBT) {
			int length = NBT.getInteger("len");
			int pitch = NBT.getInteger("pch");
			int voice = NBT.getInteger("vc");
			return new Note(NoteLength.values()[length], pitch, Instrument.values()[voice]);
		}

		public NBTTagCompound writeToNBT() {
			NBTTagCompound NBT = new NBTTagCompound();
			NBT.setInteger("len", length.ordinal());
			NBT.setInteger("pch", pitch);
			NBT.setInteger("vc", voice.ordinal());
			//ReikaJavaLibrary.pConsole(this+":"+NBT, Side.SERVER);
			return NBT;
		}

		public MusicKey getMusicKey() {
			return MusicKey.getByIndex(MusicKey.F2.ordinal()+pitch);
		}

		public static int getPitch(MusicKey k) {
			return k.ordinal()-MusicKey.F2.ordinal();
		}

		public static Note getFromMusicScore(MusicScore.Note n) {
			Instrument i = Instrument.getFromVoiceAndPitch(n);
			MusicKey key = n.key;
			key = key.getInterval(-12);
			if (i == Instrument.BASS)
				key = key.getOctave().getOctave().getOctave();
			int pitch = getPitch(key);
			while (pitch > 48)
				pitch -= 12;
			return new Note(NoteLength.getByTickLength(n.length/8), pitch, i);
		}

	}

	public static enum NoteLength {
		WHOLE(48),
		HALF(24),
		QUARTER(12),
		EIGHTH(6),
		SIXTEENTH(3);

		public final int tickLength;

		private static final HashMap<Integer, NoteLength> lengthMap = new HashMap();

		private NoteLength(int length) {
			tickLength = length;
		}

		@Override
		public String toString() {
			return ReikaStringParser.capFirstChar(this.name());
		}

		static {
			for (NoteLength n : values()) {
				lengthMap.put(n.tickLength, n);
			}
		}

		public static NoteLength getByTickLength(int ticks) {
			NoteLength len = lengthMap.get(ticks);
			if (len == null) {
				len = WHOLE;
				for (NoteLength n : values()) {
					if (Math.abs(n.tickLength-ticks) < Math.abs(len.tickLength-ticks)) {
						len = n;
					}
				}
			}
			return len;
		}

		public static NoteLength getLargestNotMoreThan(int dt) {
			for (NoteLength n : values()) {
				if (n.tickLength <= dt)
					return n;
			}
			return null;
		}
	}

	public static enum Instrument {
		REST(-1),
		GUITAR(18),
		BASS(32),
		PLING(98),
		BASSDRUM(116),
		SNARE(48),
		CLAVE(-1);

		public final int MIDIvalue;

		private static final HashMap<Integer, Instrument> MIDIMap = new HashMap();

		private Instrument(int mid) {
			MIDIvalue = mid;
		}

		public static Instrument getFromVoiceAndPitch(MusicScore.Note n) {
			if (n.percussion) {
				return CLAVE;
			}
			else {
				Instrument i = MIDIMap.get(n.voice);
				if (i != null)
					return i;
				if (n.key.ordinal() < MusicKey.F4.ordinal())
					return BASS;
				if (n.voice >= 81 && n.voice <= 96) //synth leads and pads
					return PLING;
				return GUITAR;
			}
		}

		public boolean isPitched() {
			return this.ordinal() < 4;
		}

		static {
			for (Instrument n : values()) {
				MIDIMap.put(n.MIDIvalue, n);
			}
		}
	}

	@Override
	public void breakBlock() {
		this.deleteFiles(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean trigger() {
		this.startPlaying();
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void sendNote(int pitch, int channel, NoteLength len, Instrument voice) {
		ReikaPacketHelper.sendPacketToServer(RotaryCraft.packetChannel, PacketRegistry.MUSIC.getMinValue(), this, pitch, channel, len.ordinal(), voice.ordinal());
	}

}
