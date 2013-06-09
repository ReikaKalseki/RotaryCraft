/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Reika.DragonAPI.Libraries.ReikaStringParser;
import Reika.RotaryCraft.RotaryCraft;

public abstract class AchievementDescriptions {

	private static ArrayList<String> labels = new ArrayList<String>();

	public static void loadDesc() {
		String path = "Resources/AchievementDesc.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int i = 0;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty()) {
					labels.add(ReikaStringParser.getStringWithEmbeddedReferences(line));
					i++;
				}
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String getDesc(int i) {
		if (i < labels.size())
			return labels.get(i);
		else
			return "[NO DESC]";
	}

}
