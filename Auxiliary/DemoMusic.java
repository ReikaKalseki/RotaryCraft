/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * 
 * Distribution of the software in any form is only allowed
 * with explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.MIDIInterface;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;

public final class DemoMusic {
	private static final MIDIInterface DEMO1 = new MIDIInterface(RotaryCraft.class, "MIDIs/test.mid", RotaryAux.mididir+"test.mid");
	private static final MIDIInterface DEMO2 = new MIDIInterface(RotaryCraft.class, "MIDIs/test2.mid", RotaryAux.mididir+"test2.mid");
	private static final MIDIInterface DEMO3 = new MIDIInterface(RotaryCraft.class, "MIDIs/test3.mid", RotaryAux.mididir+"test3.mid");
	private static final MIDIInterface DEMO4 = new MIDIInterface(RotaryCraft.class, "MIDIs/test4.mid", RotaryAux.mididir+"test4.mid");
	private static final MIDIInterface DEMO5 = new MIDIInterface(RotaryCraft.class, "MIDIs/test5.mid", RotaryAux.mididir+"test5.mid");
	private static final MIDIInterface DEMO6 = new MIDIInterface(RotaryCraft.class, "", "");
	private static final MIDIInterface DEMO7 = new MIDIInterface(RotaryCraft.class, "", "");
	private static final MIDIInterface DEMO8 = new MIDIInterface(RotaryCraft.class, "", "");
	private static final MIDIInterface DEMO9 = new MIDIInterface(RotaryCraft.class, "", "");

	private static int[][][] demo1 = new int[DEMO1.getLength()][16][3];
	private static int[][][] demo2 = new int[DEMO2.getLength()][64][3];
	private static int[][][] demo3 = new int[DEMO3.getLength()][16][3];
	private static int[][][] demo4 = new int[DEMO4.getLength()][16][3];
	private static int[][][] demo5 = new int[DEMO5.getLength()][16][3];
	private static int[][][] demo6 = new int[DEMO6.getLength()][16][3];
	private static int[][][] demo7 = new int[DEMO7.getLength()][16][3];
	private static int[][][] demo8 = new int[DEMO8.getLength()][16][3];
	private static int[][][] demo9 = new int[DEMO9.getLength()][16][3];

	private static final int[][][][] demo = {
		demo1, demo2, demo3, demo4, demo5, demo6, demo7, demo8, demo9
	};

	private static final MIDIInterface[] midis = {
		DEMO1, DEMO2, DEMO3, DEMO4, DEMO5, DEMO6, DEMO7, DEMO8, DEMO9
	};

	public static int[][][] getDemo(int track) {
		if (midis[track] == null) {
			ReikaJavaLibrary.pConsole("Track "+track+" is empty!");
			return new int[1][16][3];
		}
		return demo[track];
	}

	public static void addTracks() {
		//for (int i = 0; i < midis.length; i++)
		setTrack2(2);
	}

	private static void setTrack2(int track) {
		if (midis[track] == null)
			return;
		//int length = midis[track].getLength();
		demo[track] = midis[track].fill();
	}
	/*
	private static void setTrack(int track) {
		if (midis[track] == null)
			return;
		//midis[track].debug();
		//demo[track].clear();
		if (demo[track].length > 0)
			return;
		int length = midis[track].getLength();
		for (int i = 0; i < length; i++) {
			int[][] data = new int[16][3];
			for (int j = 0; j < 16; j++) {
				if (midis[track].isPlayingAt(j, i)) {
					data[j][0] = midis[track].getVoiceAtTrackAndTime(j, i);
					data[j][1] = midis[track].getNoteAtTrackAndTime(j, i);
					data[j][2] = midis[track].getVolumeAtTrackAndTime(j, i);
					data[5][0] = 3;
					data[6][0] = 3;
					//data[9][0] = 5; //perc
					//ReikaJavaLibrary.pConsole(String.format("%2d", i)+"  @  "+j+"  "+data[j][0]+" "+data[j][1]+" "+data[j][2]);
				}
			}
			//demo[track].add(data);
		}
	}*/
}
