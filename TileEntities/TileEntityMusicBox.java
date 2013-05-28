/*******************************************************************************
 * @author Reika
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaRedstoneHelper;
import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Auxiliary.DemoMusic;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;

public class TileEntityMusicBox extends TileEntityPowerReceiver implements GuiController {

	/** 16 channels, 7 note states, 24 pitch states. Array Structure: [channel][params(voice/pitch/volume)]. Voices:
	 * 0 = silence, 1 = piano, 2 = bass, 3 = pling, 4 = bassdrum, 5 = snare, 6 = click */
	//public int[][][] musicQueue = new int[16][9600][2];
	//public List<int[][]> musicQueue = new ArrayList<int[][]>();
	public int[][][] musicQueue = new int[8192][16][3];

	public int pauseDelay = 0;
	public boolean demo = false;
	public int demoTrack = 2;

	/** For GUI */
	private int currentNoteLength = 48;
	private int[] entryPosn = new int[16];
	private int entryVolume = 100;
	private int entryChannel = 0;
	private int entryNote = 0;
	private int entryVoice = 1;
	private boolean[] fullChannels = new boolean[16];
	public boolean editmode = false;

	private boolean toSave = false;
	private boolean toRead = false;

	private boolean isOneTimePlaying = false;

	private boolean lastPower = false;

	/** Position of last note */
	private int[] lastNote = new int[16];

	public static final int LOOPPOWER = 1024;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getSummativeSidedPower();
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

		if (musicQueue != null) {
			if (musicQueue.length > 0) {
				if (tickcount >= musicQueue.length) {
					tickcount = -pauseDelay;
					return;
				}
			}
		}
		if (demo && tickcount == 0) {
			int[][][] demoMusic = DemoMusic.getDemo(demoTrack);
			if (musicQueue == null || !Arrays.equals(musicQueue, demoMusic)) {
				musicQueue = demoMusic;
				lastNote[0] = demoMusic.length;
			}
		}
		if (musicQueue != null) {
			if (tickcount >= 0 && musicQueue.length > 0)
				this.playSound(tickcount);
			tickcount++;
		}
		if (this.hasReachedEnd()) {
			tickcount = -pauseDelay;
			isOneTimePlaying = false;
		}
	}

	public int[] getCurrentEntryData() {
		int[] data = {entryChannel, entryVoice, entryVolume};
		return data;
	}

	private boolean hasReachedEnd() {
		return (tickcount > this.getLastNote());
	}

	public void playSound(int tick) {
		String pit;
		int[][] notes = musicQueue[tick];
		for (int i = 0; i < 16; i++) {
			float pitch = (float)Math.pow(2.0D, (notes[i][1]-24)/12.0D);
			float volume = notes[i][2]/100F;
			if (pitch < 0.5F) {
				pitch *= 2F;
				pit = "lo";
			}
			else if (pitch > 2F) {
				pitch *= 0.25F;
				pit = "hi";
			}
			else
				pit = "";
			//ReikaJavaLibrary.pConsole(pit);
			if (notes[i][0] != 0)
				worldObj.spawnParticle("note", xCoord+0.5, yCoord+1.2, zCoord+0.5, notes[i][1]/24D, 0.0D, 0.0D);
			switch(notes[i][0]) {
				case 1:
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "Reika.RotaryCraft.music.harp"+pit, volume, pitch);
				break;
				case 2:
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "Reika.RotaryCraft.music.bass"+pit, volume, pitch);
				break;
				case 3:
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "Reika.RotaryCraft.music.pling"+pit, volume, pitch);
				break;
				case 4:
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "note.bd", volume, pitch);
				break;
				case 5:
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "note.snare", volume, pitch);
				break;
				case 6:
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "note.hat", volume, pitch);
				break;
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	public void setEntryData(int channel, int voice, int note, int volume) {
		entryChannel = channel;
		entryVoice = voice;
		entryNote = note;
		entryVolume = volume;
	}

	/** 4: 16th, 3: 8th, 2: qtr, 1: half, 0: whole */
	public void chooseNoteType(int type) {
		switch(type) {
		case 4:
			currentNoteLength = 3;
		return;
		case 3:
			currentNoteLength = 6;
		return;
		case 2:
			currentNoteLength = 12;
		return;
		case 1:
			currentNoteLength = 24;
		return;
		case 0:
			currentNoteLength = 48;
		return;
		}
	}

	public void addNote() {
		if (fullChannels[entryChannel]) {
			ReikaChatHelper.write("This channel is full!");
			return;
		}
		musicQueue[entryPosn[entryChannel]][entryChannel][0] = entryVoice;
		musicQueue[entryPosn[entryChannel]][entryChannel][1] = entryNote;
		musicQueue[entryPosn[entryChannel]][entryChannel][2] = entryVolume;
		this.playSound(entryPosn[entryChannel]);
		entryPosn[entryChannel] += currentNoteLength;
		if (entryPosn[entryChannel] > lastNote[entryChannel])
			lastNote[entryChannel] = entryPosn[entryChannel];
		if (entryPosn[entryChannel] >= musicQueue.length) {
			ReikaChatHelper.write("Music Capacity Reached!");
			fullChannels[entryChannel] = true;
		}
	}

	public int getCurrentNoteType() {
		switch(currentNoteLength) {
		case 3:
			return 4;
		case 6:
			return 3;
		case 12:
			return 2;
		case 24:
			return 1;
		case 48:
			return 0;
		}
		return -1;
	}

	public void backSpace() {
		if (this.isOnlyLastNote())
			lastNote[entryChannel] -= currentNoteLength;
		entryPosn[entryChannel] -= currentNoteLength;
		if (entryPosn[entryChannel] < 0)
			entryPosn[entryChannel] = 0;
		this.deleteEntry(entryPosn[entryChannel]);
	}

	public void clearChannel(int channel) {
		for (int i = 0; i < musicQueue.length; i++) {
			for (int k = 0; k < 3; k++) {
				musicQueue[i][channel][k] = 0;
			}
		}
		lastNote[channel] = 0;
		fullChannels[channel] = false;
		entryPosn[channel] = 0;
	}

	private void deleteEntry(int time) {
		for (int j = 0; j < 16; j++) {
			for (int k = 0; k < 3; k++) {
				musicQueue[time][j][k] = 0;
			}
		}
	}

	private boolean isOnlyLastNote() {
		if (entryPosn[entryChannel] < this.getLastNote())
			return false;
		for (int i = 0; i < 16; i++)
			if (entryPosn[i] >= this.getLastNote())
				return false;
		return true;
	}

	public void clearMusic() {
		for (int i = 0; i < musicQueue.length; i++) {
			this.deleteEntry(i);
		}
		for (int i = 0; i < 16; i++) {
			fullChannels[i] = false;
			entryPosn[i] = 0;
			lastNote[i] = 0;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("delay", pauseDelay);
		NBT.setBoolean("isdemo", demo);
		NBT.setInteger("demotr", demoTrack);
		NBT.setInteger("len", currentNoteLength);
		NBT.setInteger("vol", entryVolume);
		NBT.setInteger("ch", entryChannel);
		NBT.setInteger("note", entryNote);
		NBT.setInteger("voice", entryVoice);

		NBT.setBoolean("onetime", isOneTimePlaying);
		NBT.setBoolean("lastpwr", lastPower);

		if (toSave) {
			//ReikaJavaLibrary.pConsole("Saving");
			NBT.setIntArray("last", lastNote);
			NBT.setIntArray("posn", entryPosn);

			for (int i = 0; i < 16; i++)
				NBT.setBoolean("full"+String.valueOf(i), fullChannels[i]);

			for (int i = 0; i < 8192; i++) {
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 3; k++) {
						//ReikaJavaLibrary.pConsole("i: "+i+"; j: "+j+"; k: "+k);
						NBT.setInteger(String.format("T%dCh%dN%d", i, j, k), musicQueue[i][j][k]);
					}
				}
			}
			toSave = false;
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);

		pauseDelay = NBT.getInteger("delay");
		demo = NBT.getBoolean("isdemo");
		demoTrack = NBT.getInteger("demotr");
		currentNoteLength = NBT.getInteger("len");
		entryVolume = NBT.getInteger("vol");
		entryChannel = NBT.getInteger("ch");
		entryNote = NBT.getInteger("note");
		entryVoice = NBT.getInteger("voice");

		isOneTimePlaying = NBT.getBoolean("onetime");
		lastPower = NBT.getBoolean("lastpwr");

		if (toRead) {
			//ReikaJavaLibrary.pConsole("Reading");
			lastNote = NBT.getIntArray("last");
			entryPosn = NBT.getIntArray("posn");

			for (int i = 0; i < 16; i++)
				fullChannels[i] = NBT.getBoolean("full"+String.valueOf(i));

			for (int i = 0; i < 8192; i++) {
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 3; k++) {
						musicQueue = new int[8192][16][3];
						//ReikaJavaLibrary.pConsole("i: "+i+"; j: "+j+"; k: "+k);
						musicQueue[i][j][k] = NBT.getInteger(String.format("T%dCh%dN%d", i, j, k));
					}
				}
			}
			toRead = false;
		}
	}

	public void save() {
		toSave = true;
	}

	public void read() {
		toRead = true;
	}

	public int getLastNote() {
		int last = -1;
		for (int i = 0; i < 16; i++) {
			if (lastNote[i] > last)
				last = lastNote[i];
		}
		return last;
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.MUSICBOX.ordinal();
	}

}
