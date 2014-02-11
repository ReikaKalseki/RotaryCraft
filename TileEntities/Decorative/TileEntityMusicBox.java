/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities.Decorative;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityMusicBox extends TileEntityPowerReceiver implements GuiController {

	/** unlimited channels, 7 voices, 48+ pitch states */ //channels locked to one for now
	public final ArrayList<Note> musicQueue = new ArrayList();

	private boolean isOneTimePlaying = false;

	private boolean lastPower = false;

	private String musicFile;

	public static final int LOOPPOWER = 1024;

	private int playDelay = 0;
	private int playIndex = 0;

	public TileEntityMusicBox() {
		super();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();

		if (this.getTicksExisted() == 0) {
			if (this.hasSavedFile())
				this.read();
		}

		if (musicQueue.isEmpty()) {
			this.addNote(0, new Note(NoteLength.EIGHTH, 18, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 22, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 25, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 30, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 18, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 22, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 25, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 30, Instrument.GUITAR));

			this.addNote(0, new Note(NoteLength.EIGHTH, 13, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 17, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 20, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 25, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 13, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 17, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 20, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 25, Instrument.GUITAR));

			this.addNote(0, new Note(NoteLength.EIGHTH, 15, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 18, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 22, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 27, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 15, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 18, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 22, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 27, Instrument.GUITAR));

			this.addNote(0, new Note(NoteLength.EIGHTH, 10, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 13, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 17, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 22, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 10, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 13, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 17, Instrument.GUITAR));
			this.addNote(0, new Note(NoteLength.EIGHTH, 22, Instrument.GUITAR));
		}

		if (power < LOOPPOWER) {
			if (ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower)) {
				isOneTimePlaying = true;
				playIndex = 0;
				tickcount = 0;
				playDelay = 0;
			}
			if (!ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower) && !isOneTimePlaying) {
				lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
				tickcount = 0;
				playIndex = 0;
				playDelay = 0;
				return;
			}
			lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
		}
		else {
			if (playIndex == musicQueue.size())
				playIndex = 0;
		}
		//ReikaJavaLibrary.pConsole(playIndex+":"+playDelay, Side.SERVER);

		if (playDelay > 0)
			playDelay--;
		if (playDelay == 0) {
			if (!musicQueue.isEmpty()) {
				if (playIndex < musicQueue.size()) {
					Note n = musicQueue.get(playIndex);
					if (n != null) {
						if (!world.isRemote)
							this.playNote(n);
					}
				}
			}
			else {
				playIndex = 0;
			}
		}

		//ReikaJavaLibrary.pConsole(playIndex+":"+playDelay+":"+musicQueue, Side.SERVER);
	}

	private void playNote(Note n) {
		n.play(worldObj, xCoord, yCoord, zCoord);
		ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.MUSICPARTICLE.getMinValue(), this);
		playDelay = n.length.tickLength;
		playIndex++;
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	public void addNote(int channel, Note n) {
		this.addNoteAtIndex(channel, n, musicQueue.size());
	}

	public void addNoteAfter(int channel, Note n, Note ref) {
		int i = musicQueue.indexOf(ref);
		if (i != -1) {
			this.addNoteAtIndex(channel, n, i+1);
		}
	}

	public void addNoteBefore(int channel, Note n, Note ref) {
		int i = musicQueue.indexOf(ref);
		if (i != -1) {
			this.addNoteAtIndex(channel, n, i);
		}
	}

	private void addNoteAtIndex(int channel, Note n, int index) {
		if (index >= musicQueue.size()) {
			musicQueue.add(n);
		}
		else
			musicQueue.add(index, n);
	}

	public void backspace() {
		musicQueue.remove(musicQueue.size()-1);
	}

	public void clearMusic() {
		musicQueue.clear();
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

		NBT.setBoolean("onetime", isOneTimePlaying);
		NBT.setBoolean("lastpwr", lastPower);

		if (musicFile != null && !musicFile.isEmpty())
			NBT.setString("music", musicFile);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		isOneTimePlaying = NBT.getBoolean("onetime");
		lastPower = NBT.getBoolean("lastpwr");

		musicFile = NBT.getString("music");
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

			for (int i = 0; i < musicQueue.size(); i++) {
				Note n = musicQueue.get(i);
				String s = n.toSerialString();
				p.append(s+"\n");
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
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(new FileInputStream(save.getPath()+"/Music Box/"+name)));
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
		String path = save.getPath()+"/Music Box/"+name;
		this.readFile(path);
	}

	private void readFile(String path) {
		musicQueue.clear();
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(RotaryCraft.class.getResourceAsStream(path)));
			String line = p.readLine();
			while (line != null) {
				Note n = Note.getFromSerialString(line);
				if (n != null) {
					musicQueue.add(n);
				}
				line = p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			ReikaChatHelper.write(e.getMessage()+" caused the read to fail!");
		}
	}

	public void loadDemo() {
		String path = "Resources/demomusic.txt";
		this.readFile(path);
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.MUSICBOX.ordinal();
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public void setMusicFile(ItemStack is) {
		if (!is.stackTagCompound.hasKey("music"))
			return;
		musicFile = is.stackTagCompound.getString("music");
	}

	public void deleteFiles(int x, int y, int z) {
		File save = DimensionManager.getCurrentSaveRootDirectory();
		//ReikaJavaLibrary.pConsole(musicFile);
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		File f = new File(save.getPath()+"\\RotaryCraft\\"+name);
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

		public String getName() {
			return notes[pitch%12];
		}

		public void play(World world, int x, int y, int z) {
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

			return sb.toString();
		}

		protected static Note getFromSerialString(String s) {
			return new Note(null, 0, null);
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
