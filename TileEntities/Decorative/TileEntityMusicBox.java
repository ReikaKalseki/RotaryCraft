/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.API.Event.NoteEvent;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityMusicBox extends TileEntityPowerReceiver implements GuiController {

	/** 16 channels, 7 voices, 64 pitch states */
	private final ArrayList<Note>[] musicQueue;

	private boolean isOneTimePlaying = false;

	private boolean lastPower = false;

	public static final int LOOPPOWER = 1024;

	private int[] playDelay = new int[16];
	private int[] playIndex = new int[16];

	private static final Color[] channelColors = {
		new Color(0x3636FF), new Color(0xD336FF), new Color(0xFFACAC), new Color(0xFF3636), new Color(0xFFAC36), new Color(0xD3D336),
		new Color(0x65BC8F), new Color(0x36D336), new Color(0x36FFFF), new Color(0x58ABF9), new Color(0x8484FF), new Color(0xFF36FF),
		new Color(0x8436FF), new Color(0xB49C8A), new Color(0x8FA9B5), new Color(0x94B581)
	};

	public TileEntityMusicBox() {
		super();

		musicQueue = new ArrayList[16];
		for (int i = 0; i < 16; i++) {
			musicQueue[i] = new ArrayList();
		}
	}

	public static Color getColorForChannel(int channel) {
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
		return ReikaJavaLibrary.copyList(musicQueue[channel]);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
		//ReikaJavaLibrary.pConsole(Arrays.toString(musicQueue), Side.SERVER);

		if (this.getTicksExisted() == 0) {
			if (this.hasSavedFile())
				this.read();
		}

		if (power < LOOPPOWER) {
			if (ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower)) {
				isOneTimePlaying = true;
				this.startPlaying();
			}
			if (!ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower) && !isOneTimePlaying) {
				lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
				this.startPlaying();
				return;
			}
			lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
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
								if (!world.isRemote)
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
			ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.MUSICPARTICLE.getMinValue(), this);
		}
		playDelay[channel] = n.length.tickLength;
		playIndex[channel]++;
		NoteEvent e = new NoteEvent(this, n, channel);
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
	protected void writeSyncTag(NBTTagCompound NBT)
	{
		super.writeSyncTag(NBT);

		NBT.setBoolean("onetime", isOneTimePlaying);
		NBT.setBoolean("lastpwr", lastPower);
	}

	@Override
	protected void readSyncTag(NBTTagCompound NBT)
	{
		super.readSyncTag(NBT);

		isOneTimePlaying = NBT.getBoolean("onetime");
		lastPower = NBT.getBoolean("lastpwr");
	}

	public void save() {
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
		File save = DimensionManager.getCurrentSaveRootDirectory();
		String base = save.getPath();
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		try {
			BufferedReader p = ReikaFileReader.getReader(base+"/RotaryCraft/"+name);
			p.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public void read() {
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
				ReikaJavaLibrary.pConsole("LINE "+linecount+":\n");
			e.printStackTrace();
			ReikaChatHelper.write(e.getMessage()+" caused the read to fail!");
		}
	}

	public void loadDemo() {
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
		if (is.getItem() != ItemRegistry.DISK.getItemInstance())
			return;
		if (is.stackTagCompound == null)
			return;
		this.clearMusic();
		try {
			for (int i = 0; i < 16; i++) {
				if (is.stackTagCompound.hasKey("ch"+i)) {
					NBTTagList li = is.stackTagCompound.getTagList("ch"+i, is.stackTagCompound.getId());
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

	public void deleteFiles(int x, int y, int z) {
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
			sb.append(length);
			sb.append(" ");
			sb.append(pitch);
			return sb.toString();
		}

		public String toSerialString() {
			StringBuilder sb = new StringBuilder();
			sb.append(length);
			sb.append(":");
			sb.append(pitch);
			sb.append(":");
			sb.append(voice);
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

		protected static Note readFromNBT(NBTTagCompound NBT) {
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

	}

	public static enum NoteLength {
		WHOLE(48),
		HALF(24),
		QUARTER(12),
		EIGHTH(6),
		SIXTEENTH(3);

		public final int tickLength;

		private NoteLength(int length) {
			tickLength = length;
		}

		@Override
		public String toString() {
			return ReikaStringParser.capFirstChar(this.name());
		}
	}

	public static enum Instrument {
		REST(0),
		GUITAR(1),
		BASS(2),
		PLING(3),
		BASSDRUM(4),
		SNARE(5),
		CLAVE(6);

		public final int index;

		private Instrument(int index) {
			this.index = index;
		}

		public boolean isPitched() {
			return index < 4;
		}

		@Override
		public String toString() {
			return this.name();
		}
	}

}