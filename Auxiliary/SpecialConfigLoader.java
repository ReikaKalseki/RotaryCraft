package Reika.RotaryCraft.Auxiliary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class SpecialConfigLoader {

	private static final String key = loadConfigKey();
	private static boolean loaded = false;

	private static final boolean[] booleans = new boolean[ReikaJavaLibrary.getEnumLengthWithoutInitializing(MachineRegistry.class)];

	static {
		readFile();
	}

	private static String loadConfigKey() {
		try {
			URL url = new URL("http://ReikaKalseki.opendrive.com/files/NF8zOTc4MDQ1MF80aW9SQQ/j043ffd48.txt");
			BufferedReader p = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = p.readLine();
			p.close();
			loaded = true;
			return line;
		}
		catch (IOException e) {
			return "";
		}
	}

	public static boolean exists() {
		return RotaryCraft.class.getResourceAsStream(getFile()) != null;
	}

	public static void readFile() {
		String path = getFile();
		int linecount = -1;
		try {
			BufferedReader p;
			p = new BufferedReader(new InputStreamReader(RotaryCraft.class.getResourceAsStream(path)));
			String line = p.readLine();
			int i = 0;
			while (line != null) {
				linecount++;
				String[] pieces = line.split("=");
				boolean b = Boolean.parseBoolean(pieces[1]);
				booleans[i] = b;
				line = p.readLine();
				i++;
			}
			p.close();
		}
		catch (Exception e) {
			if (linecount >= 0)
				ReikaJavaLibrary.pConsole("LINE "+linecount+":\n");
			e.printStackTrace();
		}
	}

	private static String getFile() {
		return "Resources/"+key+".cfg";
	}

	public static boolean getMachine(MachineRegistry m) {
		return booleans[m.ordinal()];
	}

}
