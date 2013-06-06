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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Libraries.ReikaStringParser;
import Reika.RotaryCraft.PowerReceivers;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityFan;
import Reika.RotaryCraft.TileEntities.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.TileEntityPileDriver;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.TileEntitySprinkler;

public final class RotaryDescriptions {

	public static String[][] data;

	private static String[][] engines;
	private static String[][] machines;
	private static String[] crafting;
	private static String[][] tools;
	private static String[] resource;
	private static String[] misc;
	private static String[][] trans;
	private static String[] info;
	private static String[] category;
	private static final String ToC = "Page "+GuiHandbook.INFOSTART+" - Terms and Physics Explanations\nPage "+GuiHandbook.MISCSTART+" - Important Notes\nPage "+GuiHandbook.ENGINESTART+" - Engines\nPage "+GuiHandbook.TRANSSTART+" - Transmission\nPage "+GuiHandbook.MACHINESTART+" - Machines\nPage "+GuiHandbook.TOOLSTART+" - Tools\nPage "+GuiHandbook.CRAFTSTART+" - Crafting Items\nPage "+GuiHandbook.RESOURCESTART+" - Resource Items";

	public static Object[][] machineNotes = {

		{PowerReceivers.BEDROCKBREAKER.getMinPower(), PowerReceivers.BEDROCKBREAKER.getMinTorque(), 1},
		{PowerReceivers.FERMENTER.getMinPower(), 1, PowerReceivers.FERMENTER.getMinSpeed()},
		{PowerReceivers.GRINDER.getMinPower(), PowerReceivers.GRINDER.getMinTorque(), 1},
		{PowerReceivers.FLOODLIGHT.getMinPower()},
		{PowerReceivers.HEATRAY.getMinPower(), TileEntityHeatRay.FALLOFF},
		{TileEntityBorer.DIGPOWER*500, TileEntityBorer.OBSIDIANTORQUE},
		{TileEntityPileDriver.BASEPOWER},
		{PowerReceivers.AEROSOLIZER.getMinPower()},
		{PowerReceivers.LIGHTBRIDGE.getMinPower()},
		{PowerReceivers.EXTRACTOR.getMinPower(0), PowerReceivers.EXTRACTOR.getMinPower(1), PowerReceivers.EXTRACTOR.getMinPower(2), PowerReceivers.EXTRACTOR.getMinPower(3), PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinTorque(3), PowerReceivers.EXTRACTOR.getMinSpeed(1), PowerReceivers.EXTRACTOR.getMinSpeed(2)},
		{PowerReceivers.PULSEJET.getMinSpeed(), TileEntityPulseFurnace.MAXTEMP},
		{PowerReceivers.PUMP.getMinPower()},
		{TileEntityReservoir.CAPACITY},
		{PowerReceivers.FAN.getMinPower(), PowerReceivers.FAN.getMinPower(), TileEntityFan.FALLOFF},
		{PowerReceivers.COMPACTOR.getMinPower(), PowerReceivers.COMPACTOR.getMinTorque(), TileEntityCompactor.REQPRESS, TileEntityCompactor.REQTEMP, TileEntityCompactor.MAXPRESSURE, TileEntityCompactor.MAXTEMP},
		{PowerReceivers.AUTOBREEDER.getMinPower(), PowerReceivers.AUTOBREEDER.getMinPower(), TileEntityAutoBreeder.FALLOFF},
		{PowerReceivers.BAITBOX.getMinPower(), PowerReceivers.BAITBOX.getMinPower(), TileEntityBaitBox.FALLOFF},
		{PowerReceivers.FIREWORK.getMinPower(), PowerReceivers.FIREWORK.getMinSpeed()},
		{PowerReceivers.FRACTIONATOR.getMinPower(), PowerReceivers.FRACTIONATOR.getMinSpeed()},
		{PowerReceivers.GPR.getMinPower(), PowerReceivers.GPR.getMinPower()},
		{PowerReceivers.HEATER.getMinPower(), TileEntityHeater.MAXTEMP},
		{PowerReceivers.OBSIDIAN.getMinPower(), PowerReceivers.OBSIDIAN.getMinSpeed(), TileEntityObsidianMaker.MAXTEMP, TileEntityObsidianMaker.CAPACITY},
		{TileEntityPlayerDetector.FALLOFF, TileEntityPlayerDetector.BASESPEED, TileEntityPlayerDetector.SPEEDFACTOR},
		{PowerReceivers.SPAWNERCONTROLLER.getMinPower(), TileEntitySpawnerController.BASEDELAY},
		{TileEntitySprinkler.CAPACITY},
		{PowerReceivers.VACUUM.getMinPower(), PowerReceivers.VACUUM.getMinPower()},
		{PowerReceivers.WOODCUTTER.getMinPower(), PowerReceivers.WOODCUTTER.getMinTorque()},
		{},
		{PowerReceivers.MOBRADAR.getMinPower(), PowerReceivers.MOBRADAR.getMinPower(), TileEntityMobRadar.FALLOFF},
		{},
		{PowerReceivers.TNTCANNON.getMinPower(), PowerReceivers.TNTCANNON.getMinTorque()},
		{PowerReceivers.SONICWEAPON.getMinPower(), PowerReceivers.SONICWEAPON.getMinPower(), TileEntitySonicWeapon.FALLOFF, TileEntitySonicWeapon.EYEDAMAGE, TileEntitySonicWeapon.BRAINDAMAGE, TileEntitySonicWeapon.LUNGDAMAGE, TileEntitySonicWeapon.LETHALVOLUME},
		{},
		{PowerReceivers.FORCEFIELD.getMinPower(), PowerReceivers.FORCEFIELD.getMinPower(), TileEntityForceField.FALLOFF},
		{TileEntityMusicBox.LOOPPOWER},
		{PowerReceivers.MOBHARVESTER.getMinPower(), PowerReceivers.MOBHARVESTER.getMinPower()},
		{PowerReceivers.PROJECTOR.getMinPower()},
		{PowerReceivers.RAILGUN.getMinPower()},
		{PowerReceivers.WEATHERCONTROLLER.getMinPower()},
		{PowerReceivers.ITEMREFRESHER.getMinPower(), PowerReceivers.ITEMREFRESHER.getMinPower(), TileEntityItemRefresher.FALLOFF},
		{PowerReceivers.CAVESCANNER.getMinPower()},
		{PowerReceivers.SCALECHEST.getMinPower(), PowerReceivers.SCALECHEST.getMinPower(), TileEntityScaleableChest.FALLOFF, TileEntityScaleableChest.MAXSIZE},
		{},
		{},
		{PowerReceivers.IGNITER.getMinPower()},
		{PowerReceivers.FREEZEGUN.getMinPower(), PowerReceivers.FREEZEGUN.getMinTorque()},
		{PowerReceivers.MAGNETIZER.getMinPower(), PowerReceivers.MAGNETIZER.getMinSpeed()},
		{PowerReceivers.CONTAINMENT.getMinPower(), PowerReceivers.CONTAINMENT.getMinPower(), TileEntityContainment.FALLOFF, TileEntityContainment.WITHERPOWER, TileEntityContainment.DRAGONPOWER},
		{PowerReceivers.SCREEN.getMinPower(), PowerReceivers.SCREEN.getMinTorque()},
		{},
		{PowerReceivers.PURIFIER.getMinPower(), PowerReceivers.PURIFIER.getMinTorque(), TileEntityPurifier.SMELTTEMP},
	};

	public static void loadData() {
		loadCategories();
		loadEngineData();
		loadMachineData();
		loadToolData();
		loadTransData();
		loadCraftingData();
		loadResourceData();
		loadMiscData();
		loadInfoData();
		assignData();
	}

	private static void assignData() {
		data = new String[GuiHandbook.MAXPAGE*8+8][2];

		for (int i = 0; i < engines.length; i++) {
			data[GuiHandbook.ENGINESTART*8+i][0] = engines[i][0];
			data[GuiHandbook.ENGINESTART*8+i][1] = engines[i][1];
		}
		for (int i = 0; i < machines.length; i++) {
			data[GuiHandbook.MACHINESTART*8+i][0] = machines[i][0];
			data[GuiHandbook.MACHINESTART*8+i][1] = machines[i][1];
		}
		for (int i = 0; i < trans.length; i++) {
			data[GuiHandbook.TRANSSTART*8+i][0] = trans[i][0];
			data[GuiHandbook.TRANSSTART*8+i][1] = trans[i][1];
		}
		for (int i = 0; i < tools.length; i++) {
			data[GuiHandbook.TOOLSTART*8+i][0] = tools[i][0];
			data[GuiHandbook.TOOLSTART*8+i][1] = tools[i][1];
		}
		for (int i = 0; i < info.length; i++) {
			data[GuiHandbook.INFOSTART*8+i][0] = info[i];
		}
		for (int i = 0; i < crafting.length; i++) {
			data[GuiHandbook.CRAFTSTART*8+i][0] = crafting[i];
		}
		for (int i = 0; i < misc.length; i++) {
			data[GuiHandbook.MISCSTART*8+i][0] = misc[i];
		}
		for (int i = 0; i < resource.length; i++) {
			data[GuiHandbook.RESOURCESTART*8+i][0] = resource[i];
		}
		for (int i = 0; i < category.length; i++) {
			if (category[i] != null && !category[i].isEmpty() && !category[i].equalsIgnoreCase("EMPTY"))
				data[i*8][0] = category[i];
		}
		data[0][0] = ToC;
	}

	private static void loadCategories() {
		String path = RotaryCraft.class.getResource("Resources/Categories.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			category = new String[len];
			for (int i = 0; i < len; i++) {
				category[i] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadEngineData() {
		String path = RotaryCraft.class.getResource("Resources/EngineDesc.txt").getPath();
		String path2 = RotaryCraft.class.getResource("Resources/EngineNotes.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		File data2 = new File(path2);
		if (!data2.exists()) {
			throw new RuntimeException(path2+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		int len2 = ReikaFileReader.getFileLength(data2)/2;
		if (len != len2)
			throw new RuntimeException("File lengths are different!");
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			BufferedReader p2 = new BufferedReader(new FileReader(data2));
			engines = new String[len+1][2];
			for (int i = 1; i < len+1; i++) {
				engines[i][0] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				engines[i][1] = ReikaStringParser.getStringWithEmbeddedReferences(String.format(p2.readLine(), EnumEngineType.engineList[i-1].getTorque(), EnumEngineType.engineList[i-1].getSpeed(), EnumEngineType.engineList[i-1].getPowerForDisplay()));
				p.readLine();
				p2.readLine();
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage()+" from "+e.getClass());
		}
	}

	private static void loadMachineData() {
		String path = RotaryCraft.class.getResource("Resources/MachineDesc.txt").getPath();
		String path2 = RotaryCraft.class.getResource("Resources/MachineNotes.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		File data2 = new File(path2);
		if (!data2.exists()) {
			throw new RuntimeException(path2+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		int len2 = ReikaFileReader.getFileLength(data2)/2;
		if (len != len2)
			throw new RuntimeException("File lengths are different! "+path+" is "+len+" and "+path2+" is "+len2);
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			BufferedReader p2 = new BufferedReader(new FileReader(data2));
			machines = new String[len+1][2];
			for (int i = 1; i < len+1; i++) {
				machines[i][0] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				machines[i][1] = String.format(p2.readLine().replaceAll("\\\\n", "\n"), machineNotes[i-1]);
				p.readLine();
				p2.readLine();
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadToolData() {
		String path = RotaryCraft.class.getResource("Resources/ToolDesc.txt").getPath();
		String path2 = RotaryCraft.class.getResource("Resources/ToolNotes.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		File data2 = new File(path2);
		if (!data2.exists()) {
			throw new RuntimeException(path2+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		int len2 = ReikaFileReader.getFileLength(data2)/2;
		if (len != len2)
			throw new RuntimeException("File lengths are different!");
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			BufferedReader p2 = new BufferedReader(new FileReader(data2));
			tools = new String[len+1][2];
			for (int i = 1; i < len+1; i++) {
				tools[i][0] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				tools[i][1] = ReikaStringParser.getStringWithEmbeddedReferences(p2.readLine());
				p.readLine();
				p2.readLine();
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadTransData() {
		String path = RotaryCraft.class.getResource("Resources/TransDesc.txt").getPath();
		String path2 = RotaryCraft.class.getResource("Resources/TransNotes.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		File data2 = new File(path2);
		if (!data2.exists()) {
			throw new RuntimeException(path2+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		int len2 = ReikaFileReader.getFileLength(data2)/2;
		if (len != len2)
			throw new RuntimeException("File lengths are different!");
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			BufferedReader p2 = new BufferedReader(new FileReader(data2));
			trans = new String[len+1][2];
			for (int i = 1; i < len+1; i++) {
				trans[i][0] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				trans[i][1] = ReikaStringParser.getStringWithEmbeddedReferences(p2.readLine());
				p.readLine();
				p2.readLine();
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadCraftingData() {
		String path = RotaryCraft.class.getResource("Resources/CraftDesc.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			crafting = new String[len+1];
			for (int i = 1; i < len+1; i++) {
				crafting[i] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadResourceData() {
		String path = RotaryCraft.class.getResource("Resources/ResourceDesc.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			resource = new String[len+1];
			for (int i = 1; i < len+1; i++) {
				resource[i] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadMiscData() {
		String path = RotaryCraft.class.getResource("Resources/MiscDesc.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			misc = new String[len+1];
			for (int i = 1; i < len+1; i++) {
				misc[i] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadInfoData() {
		String path = RotaryCraft.class.getResource("Resources/Info.txt").getPath();
		File data = new File(path);
		if (!data.exists()) {
			throw new RuntimeException(path+" does not exist!");
		}
		int len = ReikaFileReader.getFileLength(data)/2;
		try {
			BufferedReader p = new BufferedReader(new FileReader(data));
			info = new String[len];
			for (int i = 0; i < len; i++) {
				info[i] = ReikaStringParser.getStringWithEmbeddedReferences(p.readLine());
				p.readLine();
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
