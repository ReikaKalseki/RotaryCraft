/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.API;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Reika.RotaryCraft.RotaryCraft;

public class AdditionalMachines {

	private static final String relativePath = "/API/";
	private static final String fileNamePrefix = "RC_Addon_Machine_";

	private static final String tilePrefix = "TileEntity Class: ";
	private static final String blockPrefix = "Block Class: ";
	private static final String metaPrefix = "Metadata Value: ";
	private static final String renderPrefix = "Render Class: ";
	private static final String namePrefix = "Machine Name: ";

	//load list from text file ("enum style") and load classes in special folder accordingly


	public static void loadList() {

	}

	public static void registerMachines() {

	}

	public static List<InputStream> getRelevantFiles() {
		List<InputStream> list = new ArrayList<InputStream>();
		File[] files = new File(relativePath).listFiles();
		for (int i = 0; i < files.length; i++) {
			String filename = files[i].getName();
			if (filename.startsWith(fileNamePrefix) && filename.endsWith(".txt")) {
				list.add(RotaryCraft.class.getResourceAsStream(relativePath+filename));
			}
		}
		return list;
	}

}
