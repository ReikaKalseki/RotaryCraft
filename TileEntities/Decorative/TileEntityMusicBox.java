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
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.World.ReikaRedstoneHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Base.TileEntity.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMusicBox extends TileEntityPowerReceiver implements GuiController {

	/** unlimited channels, 7 voices, 48+ pitch states */
	public final ArrayList<Note> musicQueue = new ArrayList();

	private boolean isOneTimePlaying = false;

	private boolean lastPower = false;

	private String musicFile;

	public static final int LOOPPOWER = 1024;

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

		if (power < LOOPPOWER) {
			if (ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower)) {
				isOneTimePlaying = true;
				tickcount = 0;
			}
			if (!ReikaRedstoneHelper.isPositiveEdge(world, x, y, z, lastPower) && !isOneTimePlaying) {
				lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
				tickcount = 0;
				return;
			}
			lastPower = world.isBlockIndirectlyGettingPowered(x, y, z);
		}
	}

	public void playSound() {

	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	public void addNote(Note n) {
		this.addNoteAtIndex(n, musicQueue.size());
	}

	public void addNoteAfter(Note n, Note ref) {
		int i = musicQueue.indexOf(ref);
		if (i != -1) {
			this.addNoteAtIndex(n, i+1);
		}
	}

	public void addNoteBefore(Note n, Note ref) {
		int i = musicQueue.indexOf(ref);
		if (i != -1) {
			this.addNoteAtIndex(n, i);
		}
	}

	private void addNoteAtIndex(Note n, int index) {
		if (index >= musicQueue.size()) {
			NoteCompound nc = new NoteCompound();
			nc.addNote(n);
			musicQueue.add(nc);
		}
		NoteCompound nc = musicQueue.get(index);
		if (nc == null)
			nc = new NoteCompound();
		nc.addNote(n);
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
				NoteCompound n = musicQueue.get(i);
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
				NoteCompound n = NoteCompound.getFromSerialString(line);
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

	public static final class NoteCompound {

		private final ArrayList<Note> notes = new ArrayList();

		public NoteCompound() {

		}

		private NoteCompound(String s) {

		}

		public void addNote(Note note) {
			if (!notes.contains(note))
				notes.add(note);
		}

		public void removeNote(Note note) {
			notes.remove(note);
		}

		public void clear() {
			notes.clear();
		}

		public int getNumberNotes() {
			return notes.size();
		}

		public void play(World world, double x, double y, double z) {
			for (int i = 0; i < notes.size(); i++) {
				notes.get(i).play(world, x, y, z);
			}
		}

		@Override
		public String toString() {
			return notes.toString();
		}

		public String toSerialString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < notes.size(); i++) {

			}
			return sb.toString();
		}

		protected static NoteCompound getFromSerialString(String s) {
			return new NoteCompound(s);
		}

	}

	public static final class Note {

		public final NoteLength length;
		/** Standard MC notation */
		public final int pitch;

		public final Instrument voice;

		public Note(NoteLength length, int pitch, Instrument voice) {
			this.length = length;
			this.pitch = pitch;
			this.voice = voice;
		}

		public void play(World world, double x, double y, double z) {

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
