/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaPacketHelper;
import Reika.DragonAPI.Libraries.ReikaRedstoneHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.DemoMusic;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.PacketRegistry;
import Reika.RotaryCraft.Registry.SoundRegistry;

public class TileEntityMusicBox extends TileEntityPowerReceiver implements GuiController {

	/** 16 channels, 7 note states, 24 pitch states. Array Structure: [channel][params(voice/pitch/volume)]. Voices:
	 * 0 = silence, 1 = piano, 2 = bass, 3 = pling, 4 = bassdrum, 5 = snare, 6 = click */
	//public int[][][] musicQueue = new int[16][9600][2];
	//public List<int[][]> musicQueue = new ArrayList<int[][]>();
	public int[][][] musicQueue = new int[8192][16][4];

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

	public boolean isOneTimePlaying = false;

	private boolean lastPower = false;

	private int activeVolume;
	private int activeVoice;
	private int activeNote;

	private int saveCount = 0;
	private int readCount = 0;

	private String musicFile;

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
			if (!worldObj.isRemote) {
				activeVolume = notes[i][2];
				activeNote = notes[i][1];
				activeVoice = notes[i][0];
			}
			float pitch = (float)Math.pow(2.0D, (activeNote-24)/12.0D);
			float volume = activeVolume/100F;
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
			if (activeVoice != 0) {
				//worldObj.spawnParticle("note", xCoord+0.5, yCoord+1.2, zCoord+0.5, activeNote/24D, 0.0D, 0.0D);
				//Packet63WorldParticles p = new Packet63WorldParticles();
				//("note", xCoord+0.5, yCoord+1.2, zCoord+0.5, activeNote/24D, 0.0D, 0.0D);
			}
			switch(activeVoice) {
			case 1:
				SoundRegistry.playSoundAtBlock(SoundRegistry.getNoteFromVoiceAndPitch(SoundRegistry.HARP, pit), worldObj, xCoord, yCoord, zCoord, volume, pitch);
				break;
			case 2:
				SoundRegistry.playSoundAtBlock(SoundRegistry.getNoteFromVoiceAndPitch(SoundRegistry.BASS, pit), worldObj, xCoord, yCoord, zCoord, volume, pitch);
				break;
			case 3:
				SoundRegistry.playSoundAtBlock(SoundRegistry.getNoteFromVoiceAndPitch(SoundRegistry.PLING, pit), worldObj, xCoord, yCoord, zCoord, volume, pitch);
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
			if (activeVoice != 0)
				ReikaPacketHelper.sendUpdatePacket(RotaryCraft.packetChannel, PacketRegistry.MUSICPARTICLE.getMinValue(), this);
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
		currentNoteLength = this.getNoteLengthFromValue(type);
	}

	public void addNote() {
		if (fullChannels[entryChannel]) {
			ReikaChatHelper.write("This channel is full!");
			return;
		}
		musicQueue[entryPosn[entryChannel]][entryChannel][0] = entryVoice;
		musicQueue[entryPosn[entryChannel]][entryChannel][1] = entryNote;
		musicQueue[entryPosn[entryChannel]][entryChannel][2] = entryVolume;
		musicQueue[entryPosn[entryChannel]][entryChannel][3] = this.getCurrentNoteType();
		this.playSound(entryPosn[entryChannel]);
		entryPosn[entryChannel] += currentNoteLength;
		if (entryPosn[entryChannel] > lastNote[entryChannel])
			lastNote[entryChannel] = entryPosn[entryChannel]-1;
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

		NBT.setInteger("avol", activeVolume);
		NBT.setInteger("avoice", activeVoice);
		NBT.setInteger("anote", activeNote);

		if (musicFile != null && !musicFile.isEmpty())
			NBT.setString("music", musicFile);

		if (saveCount > 0) {
			saveCount--;
			for (int i = 0; i < 8192; i++) {
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 4; k++) {
						NBT.setInteger(String.format("t%dc%dn%d", i, j, k), musicQueue[i][j][k]);
					}
				}
			}
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

		activeNote = NBT.getInteger("anote");
		activeVoice = NBT.getInteger("avoice");
		activeVolume = NBT.getInteger("avol");

		isOneTimePlaying = NBT.getBoolean("onetime");
		lastPower = NBT.getBoolean("lastpwr");

		musicFile = NBT.getString("music");

		if (readCount > 0) {
			readCount--;
			for (int i = 0; i < 8192; i++) {
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 4; k++) {
						musicQueue[i][j][k] = NBT.getInteger(String.format("t%dc%dn%d", i, j, k));
					}
				}
			}
		}
	}

	public void save() {
		if (musicFile == null)
			;//this.generateMusicName();
		try {
			File save = DimensionManager.getCurrentSaveRootDirectory();
			String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
			File dir = new File(save.getPath()+"\\Music Box\\");
			if (!dir.exists())
				dir.mkdir();
			File f = new File(save.getPath()+"\\Music Box\\"+name);
			if (f.exists())
				f.delete();
			PrintWriter p = new PrintWriter(f);
			f.createNewFile();
			for (int i = 0; i < 8192; i++) {
				for (int j = 0; j < 16; j++) {
					String line = String.format("%d,%d,%d,%d", musicQueue[i][j][0], musicQueue[i][j][1], musicQueue[i][j][2], musicQueue[i][j][3]);
					String ex;
					if (j == 15)
						ex = "\n";
					else
						ex = ";";
					p.append(line+ex);
				}
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			ReikaChatHelper.write(e.getCause()+" caused the save to fail!");
		}
	}

	public void read() {
		File save = DimensionManager.getCurrentSaveRootDirectory();
		//ReikaJavaLibrary.pConsole(musicFile);
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(new FileInputStream(save.getPath()+"\\Music Box\\"+name)));
			musicQueue = new int[8192][16][4];
			for (int i = 0; i < 8192; i++) {
				String line = p.readLine();
				String[] groups = line.split("[,;]");
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 4; k++) {
						int val = Integer.parseInt(groups[4*j+k]);
						if (k != 0 || (val > 0)) {
							musicQueue[i][j][k] = val;
							if (k == 0 && val > 0) {
								int len = Integer.parseInt(groups[4*j+3]);
								lastNote[j] = i+this.getNoteLengthFromValue(len)-1;
							}
						}
					}
				}
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
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(RotaryCraft.class.getResourceAsStream(path)));
			musicQueue = new int[8192][16][4];
			for (int i = 0; i < 8192; i++) {
				String line = p.readLine();
				String[] groups = line.split("[,;]");
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 4; k++) {
						int val = Integer.parseInt(groups[4*j+k]);
						if (k != 0 || (val > 0)) {
							musicQueue[i][j][k] = val;
							if (k == 0 && val > 0) {
								int len = Integer.parseInt(groups[4*j+3]);
								lastNote[j] = i+this.getNoteLengthFromValue(len)-1;
							}
						}
					}
				}
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			ReikaChatHelper.write(e.getMessage()+" caused the read to fail!");
		}
		//readCount = 2;
		//saveCount = 2;
	}

	public static int getNoteLengthFromValue(int val) {
		switch(val) {
		case 4:
			return 3;
		case 3:
			return 6;
		case 2:
			return 12;
		case 1:
			return 24;
		case 0:
			return 48;
		}
		return 0;
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

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	public void setMusicFile(ItemStack is) {
		if (!is.stackTagCompound.hasKey("music"))
			return;
		musicFile = is.stackTagCompound.getString("music");
	}
	/*
	public ItemStack getMusicFile() {
		ItemStack is = new ItemStack(RotaryCraft.disk);
		if (musicFile == null || musicFile.isEmpty())
			return is;
		is.stackTagCompound = new NBTTagCompound();
		//ReikaJavaLibrary.spamConsole(musicFile);
		is.stackTagCompound.setString("music", musicFile);
		return is;
	}
	 */
	private void generateMusicName() {/*
		if (musicFile == null)
			musicFile = "0000";
		else {
			int num = Integer.parseInt(musicFile);
			num++;
			musicFile = String.format("%04d", num);
		}*/
		String world = DimensionManager.getCurrentSaveRootDirectory().getPath().substring(8);
		String biome = worldObj.getBiomeGenForCoords(xCoord, zCoord).biomeName;
		String mob = EntityList.getStringFromID(50+par5Random.nextInt(15));
		String day = "Day"+String.valueOf(worldObj.getWorldTime()/24000);
		String time = String.valueOf(System.nanoTime());
		String ench = EnchantmentNameParts.instance.generateRandomEnchantName();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MMMMM.dd HH:mm:ss");
		String date = sdf.format(cal.getTime());
		musicFile = mob+" "+ench+" "+biome+" "+date;
		//ReikaJavaLibrary.pConsole(musicFile);
	}

	public void deleteFiles(int x, int y, int z) {
		File save = DimensionManager.getCurrentSaveRootDirectory();
		//ReikaJavaLibrary.pConsole(musicFile);
		String name = "musicbox@"+String.format("%d,%d,%d", xCoord, yCoord, zCoord)+".rcmusic";
		File f = new File(save.getPath()+"\\Music Box\\"+name);
		if (f.exists())
			f.delete();
	}

}
