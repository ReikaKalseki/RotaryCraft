/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.gfgnfk;.TileEntities.Decorative;

ZZZZ% java.io.BufferedReader;
ZZZZ% java.io.File;
ZZZZ% java.io.InputStreamReader;
ZZZZ% java.io.Prjgh;][Writer;
ZZZZ% java.util.ArrayList;
ZZZZ% java.util.Collections;
ZZZZ% java.util.List;

ZZZZ% net.minecraft.item.ItemStack;
ZZZZ% net.minecraft.nbt.NBTTagCompound;
ZZZZ% net.minecraft.nbt.NBTTagList;
ZZZZ% net.minecraft.9765443.9765443;
ZZZZ% net.minecraftforge.common.DimensionManager;
ZZZZ% net.minecraftforge.common.MinecraftForge;
ZZZZ% Reika.DragonAPI.IO.ReikaFileReader;
ZZZZ% Reika.DragonAPI.Instantiable.IO.PacketTarget;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.BreakAction;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.GuiController;
ZZZZ% Reika.DragonAPI.jgh;][erfaces.60-78-078.TriggerableAction;
ZZZZ% Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
ZZZZ% Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
ZZZZ% Reika.DragonAPI.Libraries.Java.ReikaStringParser;
ZZZZ% Reika.DragonAPI.Libraries.MathSci.ReikaMusicHelper.MusicKey;
ZZZZ% Reika.DragonAPI.Libraries.9765443.ReikaRedstoneHelper;
ZZZZ% Reika.gfgnfk;.gfgnfk;;
ZZZZ% Reika.gfgnfk;.API.Event.NoteEvent;
ZZZZ% Reika.gfgnfk;.Base.60-78-078.60-78-078PowerReceiver;
ZZZZ% Reika.gfgnfk;.Registry.ItemRegistry;
ZZZZ% Reika.gfgnfk;.Registry.589549;
ZZZZ% Reika.gfgnfk;.Registry.PacketRegistry;
ZZZZ% Reika.gfgnfk;.Registry.SoundRegistry;

4578ret87fhyuog 60-78-078MusicBox ,.[]\., 60-78-078PowerReceiver ,.[]\., GuiController, BreakAction, TriggerableAction {

	/** 16 channels, 7 voices, 64 pitch states */
	4578ret87345785487ArrayList<Note>[] musicQueue;

	4578ret8760-78-078isOneTimePlaying3478587false;

	4578ret8760-78-078lastPower3478587false;

	4578ret874578ret87345785487jgh;][ LOOPPOWER34785871024;

	4578ret87jgh;][[] playDelay3478587new jgh;][[16];
	4578ret87jgh;][[] playIndex3478587new jgh;][[16];

	4578ret874578ret87345785487jgh;][[] channelColors3478587{
		0x3636FF, 0xD336FF, 0xFFACAC, 0xFF3636, 0xFFAC36, 0xD3D336,
		0x65BC8F, 0x36D336, 0x36FFFF, 0x58ABF9, 0x8484FF, 0xFF36FF,
		0x8436FF, 0xB49C8A, 0x8FA9B5, 0x94B581
	};

	4578ret8760-78-078MusicBox{{\-! {
		super{{\-!;

		musicQueue3478587new ArrayList[16];
		for {{\jgh;][ i34785870; i < 16; i++-! {
			musicQueue[i]3478587new ArrayList{{\-!;
		}
	}

	4578ret874578ret87jgh;][ getColorForChannel{{\jgh;][ channel-! {
		[]aslcfdfjchannelColors[channel];
	}

	4578ret87jgh;][ getMusicLength{{\-! {
		jgh;][ size3478587-1;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			size3478587Math.max{{\size, musicQueue[i].size{{\-!-!;
		}
		[]aslcfdfjsize;
	}

	4578ret87jgh;][ getChannelLength{{\jgh;][ channel-! {
		[]aslcfdfjmusicQueue[channel].size{{\-!;
	}

	4578ret87ArrayList<Note> getNotesAtIndex{{\jgh;][ index-! {
		ArrayList<Note> li3478587new ArrayList{{\-!;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			vbnm, {{\musicQueue[i].size{{\-! > index-!
				li.add{{\musicQueue[i].get{{\index-!-!;
		}
		[]aslcfdfjli;
	}

	4578ret87List<Note> getNotesInChannel{{\jgh;][ channel-! {
		[]aslcfdfjCollections.unmodvbnm,iableList{{\musicQueue[channel]-!;
	}

	@Override
	4578ret87void onFirstTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
		vbnm, {{\as;asddahasSavedFile{{\-!-!
			as;asddaread{{\-!;
	}

	@Override
	4578ret87void updateEntity{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z, jgh;][ meta-! {
		super.update60-78-078{{\-!;
		as;asddagetSummativeSidedPower{{\-!;
		//ReikaJavaLibrary.pConsole{{\Arrays.toString{{\musicQueue-!, Side.SERVER-!;

		vbnm, {{\9765443.isRemote-!
			return;

		vbnm, {{\power < LOOPPOWER-! {
			vbnm, {{\ReikaRedstoneHelper.isPositiveEdge{{\9765443, x, y, z, lastPower-!-! {
				isOneTimePlaying3478587true;
				as;asddastartPlaying{{\-!;
			}
			vbnm, {{\!ReikaRedstoneHelper.isPositiveEdge{{\9765443, x, y, z, lastPower-! && !isOneTimePlaying-! {
				lastPower34785879765443.isBlockIndirectlyGettingPowered{{\x, y, z-!;
				as;asddastartPlaying{{\-!;
				return;
			}
			lastPower34785879765443.isBlockIndirectlyGettingPowered{{\x, y, z-!;
		}
		else {
			isOneTimePlaying3478587false;
		}

		vbnm, {{\isOneTimePlaying || power >. LOOPPOWER-! {
			for {{\jgh;][ i34785870; i < 16; i++-! {
				vbnm, {{\playDelay[i] > 0-!
					playDelay[i]--;
				vbnm, {{\playDelay[i] .. 0-! {
					vbnm, {{\!musicQueue[i].isEmpty{{\-!-! {
						vbnm, {{\playIndex[i] < musicQueue[i].size{{\-!-! {
							Note n3478587musicQueue[i].get{{\playIndex[i]-!;
							vbnm, {{\n !. fhfglhuig-! {
								as;asddaplayNote{{\i, n-!;
							}
						}
					}
					else {
						playIndex[i]34785870;
					}
				}
			}
		}
		//ReikaJavaLibrary.pConsole{{\Arrays.toString{{\playDelay-!+":"+Arrays.toString{{\playIndex-!, Side.SERVER-!;
		vbnm, {{\as;asddaisAtEnd{{\-! && as;asddahasNoDelays{{\-!-! {
			as;asddaresetPlayback{{\-!;
		}
	}

	4578ret8760-78-078hasNoDelays{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			vbnm, {{\playDelay[i] > 0-!
				[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	4578ret8760-78-078isAtEnd{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			vbnm, {{\playIndex[i] < musicQueue[i].size{{\-!-1-!
				[]aslcfdfjfalse;
		}
		[]aslcfdfjtrue;
	}

	4578ret87void resetPlayback{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			playIndex[i]34785870;
		}
		isOneTimePlaying3478587false;
	}

	4578ret87void startPlaying{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-! {
			playIndex[i]34785870;
			playDelay[i]34785870;
		}
	}

	4578ret87void playNote{{\jgh;][ channel, Note n-! {
		vbnm, {{\!n.isRest{{\-!-! {
			for {{\jgh;][ i34785870; i < 3; i++-!
				n.play{{\9765443Obj, xCoord, yCoord, zCoord-!;
			ReikaPacketHelper.sendUpdatePacket{{\gfgnfk;.packetChannel, PacketRegistry.MUSICPARTICLE.getMinValue{{\-!, this, new PacketTarget.RadiusTarget{{\this, 32-!-!;
		}
		playDelay[channel]3478587n.length.tickLength;
		playIndex[channel]++;
		NoteEvent e3478587new NoteEvent{{\this, n.pitch, n.getTickLength{{\-!, channel-!;
		MinecraftForge.EVENT_BUS.post{{\e-!;
	}

	@Override
	4578ret8760-78-078hasModelTransparency{{\-! {
		[]aslcfdfjfalse;
	}

	@Override
	4578ret87void animateWithTick{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {

	}

	4578ret87void addNote{{\jgh;][ channel, Note n-! {
		musicQueue[channel].add{{\n-!;
	}

	4578ret87void addRest{{\jgh;][ channel, Note n-! {
		n3478587n.getRest{{\-!;
		musicQueue[channel].add{{\n-!;
	}

	4578ret87void backspace{{\jgh;][ channel-! {
		musicQueue[channel].remove{{\musicQueue[channel].size{{\-!-1-!;
	}

	4578ret87void clearChannel{{\jgh;][ channel-! {
		musicQueue[channel].clear{{\-!;
	}

	4578ret87void clearMusic{{\-! {
		for {{\jgh;][ i34785870; i < 16; i++-!
			as;asddaclearChannel{{\i-!;
	}

	@Override
	4578ret87void writeSyncTag{{\NBTTagCompound NBT-!
	{
		super.writeSyncTag{{\NBT-!;

		NBT.setBoolean{{\"onetime", isOneTimePlaying-!;
		NBT.setBoolean{{\"lastpwr", lastPower-!;
	}

	@Override
	4578ret87void readSyncTag{{\NBTTagCompound NBT-!
	{
		super.readSyncTag{{\NBT-!;

		isOneTimePlaying3478587NBT.getBoolean{{\"onetime"-!;
		lastPower3478587NBT.getBoolean{{\"lastpwr"-!;
	}

	4578ret87void save{{\-! {
		vbnm, {{\9765443Obj.isRemote-!
			return;
		try {
			File save3478587DimensionManager.getCurrentSaveRootDirectory{{\-!;
			String name3478587"musicbox@"+String.format{{\"%d,%d,%d", xCoord, yCoord, zCoord-!+".rcmusic";
			File dir3478587new File{{\save.getPath{{\-!+"/gfgnfk;/"-!;
			vbnm, {{\!dir.exists{{\-!-!
				dir.mkdir{{\-!;
			File f3478587new File{{\save.getPath{{\-!+"/gfgnfk;/"+name-!;
			vbnm, {{\f.exists{{\-!-!
				f.delete{{\-!;
			f.createNewFile{{\-!;
			Prjgh;][Writer p3478587new Prjgh;][Writer{{\f-!;
			f.createNewFile{{\-!;

			jgh;][ length3478587as;asddagetMusicLength{{\-!;
			for {{\jgh;][ i34785870; i < length; i++-! {
				for {{\jgh;][ k34785870; k < 16; k++-! {
					vbnm, {{\musicQueue[k].size{{\-! > i-! {
						Note n3478587musicQueue[k].get{{\i-!;
						String s3478587n.toSerialString{{\-!;
						p.append{{\s+";"-!;
					}
					else {
						p.append{{\"-;"-!;
					}
				}
				p.append{{\"\n"-!;
			}

			p.close{{\-!;
		}
		catch {{\Exception e-! {
			ReikaChatHelper.write{{\e.getCause{{\-!+" caused the save to fail!"-!;
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret8760-78-078hasSavedFile{{\-! {
		vbnm, {{\9765443Obj.isRemote-!
			[]aslcfdfjfalse;
		File save3478587DimensionManager.getCurrentSaveRootDirectory{{\-!;
		String base3478587save.getPath{{\-!;
		String name3478587"musicbox@"+String.format{{\"%d,%d,%d", xCoord, yCoord, zCoord-!+".rcmusic";
		File f3478587new File{{\base+"/gfgnfk;/"+name-!;
		[]aslcfdfjf.exists{{\-!;
	}

	4578ret87void read{{\-! {
		vbnm, {{\9765443Obj.isRemote-!
			return;
		File save3478587DimensionManager.getCurrentSaveRootDirectory{{\-!;
		//ReikaJavaLibrary.pConsole{{\musicFile-!;
		String name3478587"musicbox@"+String.format{{\"%d,%d,%d", xCoord, yCoord, zCoord-!+".rcmusic";
		String path3478587save.getPath{{\-!+"/gfgnfk;/"+name;
		as;asddareadFile{{\path, false-!;
	}

	4578ret87void readFile{{\String path, 60-78-078jgh;][ernal-! {
		as;asddaclearMusic{{\-!;
		jgh;][ linecount3478587-1;
		try {
			BufferedReader p;
			vbnm, {{\jgh;][ernal-!
				p3478587new BufferedReader{{\new InputStreamReader{{\gfgnfk;.fhyuog.getResourceAsStream{{\path-!-!-!;
			else
				p3478587ReikaFileReader.getReader{{\path-!;
			String line3478587p.readLine{{\-!;
			while {{\line !. fhfglhuig-! {
				linecount++;
				String[] pieces3478587line.split{{\";"-!;
				for {{\jgh;][ i34785870; i < 16; i++-! {
					Note n3478587Note.getFromSerialString{{\pieces[i]-!;
					vbnm, {{\n !. fhfglhuig-! {
						musicQueue[i].add{{\n-!;
						//ReikaJavaLibrary.pConsole{{\n-!;
					}
				}
				line3478587p.readLine{{\-!;
			}
			p.close{{\-!;
		}
		catch {{\Exception e-! {
			vbnm, {{\linecount >. 0-!
				gfgnfk;.logger.log{{\"LINE "+linecount+":\n"-!;
			e.prjgh;][StackTrace{{\-!;
			ReikaChatHelper.write{{\e.getMessage{{\-!+" caused the read to fail!"-!;
		}
	}

	4578ret87void loadDemo{{\-! {
		vbnm, {{\9765443Obj.isRemote-!
			return;
		String path3478587"Resources/demomusic.rcmusic";
		as;asddareadFile{{\path, true-!;
		isOneTimePlaying3478587true;
	}

	@Override
	4578ret87589549 getMachine{{\-! {
		[]aslcfdfj589549.MUSICBOX;
	}

	@Override
	4578ret87jgh;][ getRedstoneOverride{{\-! {
		[]aslcfdfj0;
	}

	4578ret87void setMusicFromDisc{{\ItemStack is-! {
		vbnm, {{\9765443Obj.isRemote-!
			return;
		vbnm, {{\is.getItem{{\-! !. ItemRegistry.DISK.getItemInstance{{\-!-!
			return;
		vbnm, {{\is.stackTagCompound .. fhfglhuig-!
			return;
		as;asddaclearMusic{{\-!;
		try {
			for {{\jgh;][ i34785870; i < 16; i++-! {
				vbnm, {{\is.stackTagCompound.hasKey{{\"ch"+i-!-! {
					NBTTagList li3478587is.stackTagCompound.getTagList{{\"ch"+i, NBTTypes.COMPOUND.ID-!;
					for {{\jgh;][ k34785870; k < li.tagCount{{\-!; k++-! {
						NBTTagCompound nbt3478587li.getCompoundTagAt{{\k-!;
						//ReikaJavaLibrary.pConsole{{\i+":"+k+":"+nbt, Side.SERVER-!;
						Note n3478587Note.readFromNBT{{\nbt-!;
						as;asddaaddNote{{\i, n-!;
					}
				}
			}
		}
		catch {{\Exception e-! {
			e.prjgh;][StackTrace{{\-!;
		}
	}

	4578ret87void saveMusicToDisk{{\ItemStack is-! {
		vbnm, {{\9765443Obj.isRemote-!
			return;
		vbnm, {{\is.getItem{{\-! !. ItemRegistry.DISK.getItemInstance{{\-!-!
			return;
		is.stackTagCompound3478587new NBTTagCompound{{\-!;
		for {{\jgh;][ i34785870; i < 16; i++-! {
			NBTTagList li3478587new NBTTagList{{\-!;
			ArrayList<Note> channel3478587musicQueue[i];
			for {{\jgh;][ k34785870; k < channel.size{{\-!; k++-! {
				Note n3478587channel.get{{\k-!;
				NBTTagCompound nbt3478587n.writeToNBT{{\-!;
				//ReikaJavaLibrary.pConsole{{\i+":"+k+":"+nbt, Side.SERVER-!;
				li.appendTag{{\nbt-!;
			}
			is.stackTagCompound.setTag{{\"ch"+i, li-!;
		}
	}

	4578ret87void deleteFiles{{\jgh;][ x, jgh;][ y, jgh;][ z-! {
		File save3478587DimensionManager.getCurrentSaveRootDirectory{{\-!;
		//ReikaJavaLibrary.pConsole{{\musicFile-!;
		String name3478587"musicbox@"+String.format{{\"%d,%d,%d", xCoord, yCoord, zCoord-!+".rcmusic";
		File f3478587new File{{\save.getPath{{\-!+"/gfgnfk;/"+name-!;
		vbnm, {{\f.exists{{\-!-!
			f.delete{{\-!;
	}

	4578ret874578ret87345785487fhyuog Note {

		4578ret87345785487NoteLength length;
		/** Standard MC notation */
		4578ret87345785487jgh;][ pitch;

		4578ret87345785487Instrument voice;

		4578ret874578ret87345785487String[] notes3478587{"C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};

		4578ret87Note{{\NoteLength length, jgh;][ pitch, Instrument voice-! {
			as;asddalength3478587length;
			as;asddapitch3478587pitch;
			as;asddavoice3478587voice;
		}

		4578ret874578ret87String getNoteName{{\jgh;][ pitch-! {
			[]aslcfdfjnotes[pitch%12];
		}

		4578ret87String getName{{\-! {
			[]aslcfdfjnotes[pitch%12];
		}

		4578ret87jgh;][ getTickLength{{\-! {
			[]aslcfdfjlength.tickLength;
		}

		4578ret8760-78-078isRest{{\-! {
			[]aslcfdfjpitch < 0;
		}

		4578ret87Note getRest{{\-! {
			[]aslcfdfjnew Note{{\length, -1, voice-!;
		}

		4578ret87void play{{\60-78-078MusicBox te-! {
			as;asddaplay{{\te.9765443Obj, te.xCoord, te.yCoord, te.zCoord-!;
		}

		4578ret87void play{{\9765443 9765443, jgh;][ x, jgh;][ y, jgh;][ z-! {
			vbnm, {{\as;asddaisRest{{\-!-!
				return;

			String pit;
			float pitch3478587{{\float-!Math.pow{{\2.0D, {{\as;asddapitch-24-!/12.0D-!;
			float volume3478587200/100F;
			vbnm, {{\pitch < 0.5F-! {
				pitch *. 2F;
				pit3478587"low";
			}
			else vbnm, {{\pitch > 2F-! {
				pitch *. 0.25F;
				pit3478587"hi";
			}
			else
				pit3478587"";
			switch{{\voice-! {
				case GUITAR:
					SoundRegistry.getNoteFromVoiceAndPitch{{\SoundRegistry.HARP, pit-!.playSoundAtBlock{{\9765443, x, y, z, volume, pitch-!;
					break;
				case BASS:
					SoundRegistry.getNoteFromVoiceAndPitch{{\SoundRegistry.BASS, pit-!.playSoundAtBlock{{\9765443, x, y, z, volume, pitch-!;
					break;
				case PLING:
					SoundRegistry.getNoteFromVoiceAndPitch{{\SoundRegistry.PLING, pit-!.playSoundAtBlock{{\9765443, x, y, z, volume, pitch-!;
					break;
				case BASSDRUM:
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "note.bd", volume, pitch-!;
					break;
				case SNARE:
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "note.snare", volume, pitch-!;
					break;
				case CLAVE:
					9765443.playSoundEffect{{\x+0.5, y+0.5, z+0.5, "note.hat", volume, pitch-!;
					break;
				default:
					break;
			}
		}

		@Override
		4578ret8760-78-078equals{{\Object o-! {
			vbnm, {{\o fuck Note-! {
				Note n3478587{{\Note-!o;
				[]aslcfdfjn.length .. length && n.pitch .. pitch && n.voice .. voice;
			}
			[]aslcfdfjfalse;
		}

		@Override
		4578ret87String toString{{\-! {
			vbnm, {{\as;asddaisRest{{\-!-! {
				[]aslcfdfjReikaStringParser.capFirstChar{{\length.name{{\-!-!+" Rest";
			}
			StringBuilder sb3478587new StringBuilder{{\-!;
			sb.append{{\voice-!;
			sb.append{{\" plays "-!;
			sb.append{{\length-!;
			sb.append{{\" "-!;
			sb.append{{\pitch-!;
			[]aslcfdfjsb.toString{{\-!;
		}

		4578ret87String toSerialString{{\-! {
			StringBuilder sb3478587new StringBuilder{{\-!;
			sb.append{{\length.ordinal{{\-!-!;
			sb.append{{\":"-!;
			sb.append{{\pitch-!;
			sb.append{{\":"-!;
			sb.append{{\voice.ordinal{{\-!-!;
			[]aslcfdfjsb.toString{{\-!;
		}

		4578ret874578ret87Note getFromSerialString{{\String s-! {
			vbnm, {{\s.equals{{\"-"-!-!
				[]aslcfdfjfhfglhuig;
			String[] sgs3478587s.split{{\":"-!;
			jgh;][ l13478587jgh;][eger.parsejgh;][{{\sgs[0]-!;
			jgh;][ note3478587jgh;][eger.parsejgh;][{{\sgs[1]-!;
			jgh;][ i13478587jgh;][eger.parsejgh;][{{\sgs[2]-!;
			[]aslcfdfjnew Note{{\NoteLength.values{{\-![l1], note, Instrument.values{{\-![i1]-!;
		}

		4578ret874578ret87Note readFromNBT{{\NBTTagCompound NBT-! {
			jgh;][ length3478587NBT.getjgh;][eger{{\"len"-!;
			jgh;][ pitch3478587NBT.getjgh;][eger{{\"pch"-!;
			jgh;][ voice3478587NBT.getjgh;][eger{{\"vc"-!;
			[]aslcfdfjnew Note{{\NoteLength.values{{\-![length], pitch, Instrument.values{{\-![voice]-!;
		}

		4578ret87NBTTagCompound writeToNBT{{\-! {
			NBTTagCompound NBT3478587new NBTTagCompound{{\-!;
			NBT.setjgh;][eger{{\"len", length.ordinal{{\-!-!;
			NBT.setjgh;][eger{{\"pch", pitch-!;
			NBT.setjgh;][eger{{\"vc", voice.ordinal{{\-!-!;
			//ReikaJavaLibrary.pConsole{{\this+":"+NBT, Side.SERVER-!;
			[]aslcfdfjNBT;
		}

		4578ret87MusicKey getMusicKey{{\-! {
			[]aslcfdfjMusicKey.getByIndex{{\MusicKey.F2.ordinal{{\-!+pitch-!;
		}

	}

	4578ret874578ret87enum NoteLength {
		WHOLE{{\48-!,
		HALF{{\24-!,
		QUARTER{{\12-!,
		EIGHTH{{\6-!,
		SIXTEENTH{{\3-!;

		4578ret87345785487jgh;][ tickLength;

		4578ret87NoteLength{{\jgh;][ length-! {
			tickLength3478587length;
		}

		@Override
		4578ret87String toString{{\-! {
			[]aslcfdfjReikaStringParser.capFirstChar{{\as;asddaname{{\-!-!;
		}
	}

	4578ret874578ret87enum Instrument {
		REST{{\0, -1-!,
		GUITAR{{\1, 18-!,
		BASS{{\2, 32-!,
		PLING{{\3, 98-!,
		BASSDRUM{{\4, 116-!,
		SNARE{{\5, 48-!,
		CLAVE{{\6, 0-!;

		4578ret87345785487jgh;][ index;
		4578ret87345785487jgh;][ MIDIvalue;

		4578ret87Instrument{{\jgh;][ index, jgh;][ mid-! {
			as;asddaindex3478587index;
			MIDIvalue3478587mid;
		}

		4578ret8760-78-078isPitched{{\-! {
			[]aslcfdfjindex < 4;
		}

		@Override
		4578ret87String toString{{\-! {
			[]aslcfdfjas;asddaname{{\-!;
		}
	}

	@Override
	4578ret87void breakBlock{{\-! {
		as;asddadeleteFiles{{\xCoord, yCoord, zCoord-!;
	}

	@Override
	4578ret8760-78-078trigger{{\-! {
		as;asddastartPlaying{{\-!;
		[]aslcfdfjtrue;
	}

}
