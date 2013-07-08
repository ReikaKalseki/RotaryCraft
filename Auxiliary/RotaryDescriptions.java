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
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.TileEntityFan;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
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
import Reika.RotaryCraft.TileEntities.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.TileEntitySprinkler;
import Reika.RotaryCraft.TileEntities.TileEntityWinder;

public final class RotaryDescriptions {

	public static String[][] data;

	private static ArrayList<String[]> engines = new ArrayList<String[]>();
	private static ArrayList<String[]> machines = new ArrayList<String[]>();
	private static ArrayList<String[]> crafting = new ArrayList<String[]>();
	private static ArrayList<String[]> tools = new ArrayList<String[]>();
	private static ArrayList<String[]> resource = new ArrayList<String[]>();
	private static ArrayList<String[]> misc = new ArrayList<String[]>();
	private static ArrayList<String[]> trans = new ArrayList<String[]>();
	private static ArrayList<String[]> info = new ArrayList<String[]>();
	private static ArrayList<String[]> category = new ArrayList<String[]>();
	private static final String ToC = "Page "+GuiHandbook.INFOSTART+" - Terms and Physics Explanations\nPage "+GuiHandbook.MISCSTART+" - Important Notes\nPage "+GuiHandbook.ENGINESTART+" - Engines\nPage "+GuiHandbook.TRANSSTART+" - Transmission\nPage "+GuiHandbook.MACHINESTART+" - Machines\nPage "+GuiHandbook.TOOLSTART+" - Tools\nPage "+GuiHandbook.CRAFTSTART+" - Crafting Items\nPage "+GuiHandbook.RESOURCESTART+" - Resource Items";

	private static final ArrayList<String> partDescs = new ArrayList<String>();

	public static Object[][] machineNotes = {
		{PowerReceivers.BEDROCKBREAKER.getMinPower(), PowerReceivers.BEDROCKBREAKER.getMinTorque()},
		{PowerReceivers.FERMENTER.getMinPower(), PowerReceivers.FERMENTER.getMinSpeed()},
		{PowerReceivers.GRINDER.getMinPower(), PowerReceivers.GRINDER.getMinTorque()},
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
		{PowerReceivers.LASERGUN.getMinPower()},
		{PowerReceivers.ITEMCANNON.getMinPower(), PowerReceivers.ITEMCANNON.getMinTorque()},
		{PowerReceivers.FRICTION.getMinPower(), PowerReceivers.FRICTION.getMinTorque()},
		{},
		{PowerReceivers.BUCKETFILLER.getMinPower(), PowerReceivers.BUCKETFILLER.getMinSpeed()},
		{PowerReceivers.BLOCKCANNON.getMinPower()},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
	};

	public static Object[][] machineData = {
		{},
		{},
		{},
		{TileEntityFloodlight.FALLOFF},
		{},
		{TileEntityBorer.DIGPOWER, TileEntityBorer.OBSIDIANTORQUE},
		{TileEntityPileDriver.BASEPOWER},
		{},
		{},
		{PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinSpeed(2)},
		{},
		{},
		{TileEntityReservoir.CAPACITY},
		{PowerReceivers.FAN.getMinPower(), TileEntityFan.MAXPOWER},
		{TileEntityCompactor.REQPRESS, TileEntityCompactor.REQTEMP},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{TileEntityWinder.UNWINDTORQUE, TileEntityWinder.UNWINDSPEED},
		{},
		{},
		{TileEntityBlastFurnace.SMELTTEMP},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{TileEntityScaleableChest.MAXSIZE},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{TileEntityPurifier.SMELTTEMP},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
		{},
	};

	public static void loadData() {
		pad();
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

	private static void pad() {
		engines.add(null);
		crafting.add(null);
		machines.add(null);
		trans.add(null);
		tools.add(null);
		misc.add(null);
		resource.add(null);
	}

	private static void assignData() {
		data = new String[GuiHandbook.MAXPAGE*8+8][2];

		for (int i = 1; i < engines.size(); i++) {
			data[GuiHandbook.ENGINESTART*8+i][0] = engines.get(i)[0];
			data[GuiHandbook.ENGINESTART*8+i][1] = engines.get(i)[1];
		}
		for (int i = 1; i < machines.size(); i++) {
			data[GuiHandbook.MACHINESTART*8+i][0] = machines.get(i)[0];
			data[GuiHandbook.MACHINESTART*8+i][1] = machines.get(i)[1];
		}
		for (int i = 1; i < trans.size(); i++) {
			data[GuiHandbook.TRANSSTART*8+i][0] = trans.get(i)[0];
			data[GuiHandbook.TRANSSTART*8+i][1] = trans.get(i)[1];
		}
		for (int i = 1; i < tools.size(); i++) {
			data[GuiHandbook.TOOLSTART*8+i][0] = tools.get(i)[0];
			data[GuiHandbook.TOOLSTART*8+i][1] = tools.get(i)[1];
		}
		for (int i = 0; i < info.size(); i++) {
			data[GuiHandbook.INFOSTART*8+i][0] = info.get(i)[0];
		}
		for (int i = 1; i < crafting.size(); i++) {
			data[GuiHandbook.CRAFTSTART*8+i][0] = crafting.get(i)[0];
		}
		for (int i = 1; i < misc.size(); i++) {
			data[GuiHandbook.MISCSTART*8+i][0] = misc.get(i)[0];
		}
		for (int i = 1; i < resource.size(); i++) {
			data[GuiHandbook.RESOURCESTART*8+i][0] = resource.get(i)[0];
		}
		for (int i = 0; i < category.size(); i++) {
			if (category.get(i)[0] != null && !category.get(i)[0].isEmpty() && !category.get(i)[0].equalsIgnoreCase("empty"))
				data[i*8][0] = category.get(i)[0];
		}
		data[0][0] = ToC;
	}

	private static void loadCategories() {
		String path = "Resources/Categories.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty())
					category.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line),""});
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadEngineData() {
		String path = "Resources/EngineDesc.txt";
		String path2 = "Resources/EngineNotes.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		InputStream in2 = RotaryCraft.class.getResourceAsStream(path2);
		int i = 0;
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			BufferedReader p2 = new BufferedReader(new InputStreamReader(in2));
			String line = null;
			while((line = p.readLine()) != null) {
				String line2 = p2.readLine();
				if (!line.isEmpty()) {
					Object[] args = new Object[3];
					if (i < EnumEngineType.engineList.length) {
						args[0] = EnumEngineType.engineList[i].getTorque();
						args[1] = EnumEngineType.engineList[i].getSpeed();
						args[2] = EnumEngineType.engineList[i].getPowerForDisplay();
					}
					else {
						args[0] = TileEntitySolar.GENOMEGA;
					}
					engines.add(new String[]{String.format(line, args), String.format(line2.replaceAll("\\\\n", "\n"), args)});
					i++;
				}
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage()+" from "+e.getClass()+" on loading line "+i);
		}
	}

	private static void loadMachineData() {
		String path = "Resources/MachineDesc.txt";
		String path2 = "Resources/MachineNotes.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		InputStream in2 = RotaryCraft.class.getResourceAsStream(path2);
		int i = 0;
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			BufferedReader p2 = new BufferedReader(new InputStreamReader(in2));
			String line = null;
			while((line = p.readLine()) != null) {
				String line2 = p2.readLine();
				if (!line.isEmpty()) {
					machines.add(new String[]{String.format(line, machineData[i]), String.format(line2.replaceAll("\\\\n", "\n"), machineNotes[i])});
					i++;
				}
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage()+" from "+e.getClass()+" on loading line "+i);
		}
	}

	private static void loadToolData() {
		String path = "Resources/ToolDesc.txt";
		String path2 = "Resources/ToolNotes.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		InputStream in2 = RotaryCraft.class.getResourceAsStream(path2);
		int i = 0;
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			BufferedReader p2 = new BufferedReader(new InputStreamReader(in2));
			String line = null;
			while((line = p.readLine()) != null) {
				String line2 = p2.readLine();
				if (!line.isEmpty()) {
					tools.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line), ReikaStringParser.getStringWithEmbeddedReferences(line2)});
					i++;
				}
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage()+" on loading line "+i);
		}
	}

	private static void loadTransData() {
		String path = "Resources/TransDesc.txt";
		String path2 = "Resources/TransNotes.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		InputStream in2 = RotaryCraft.class.getResourceAsStream(path2);
		int i = 0;
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			BufferedReader p2 = new BufferedReader(new InputStreamReader(in2));
			String line = null;
			while((line = p.readLine()) != null) {
				String line2 = p2.readLine();
				if (!line.isEmpty()) {
					trans.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line), ReikaStringParser.getStringWithEmbeddedReferences(line2)});
					i++;
				}
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage()+" on loading line "+i);
		}
	}

	private static void loadCraftingData() {
		String path = "Resources/CraftDesc.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty())
					crafting.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line),""});
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadResourceData() {
		String path = "Resources/ResourceDesc.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty())
					resource.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line),""});
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadMiscData() {
		String path = "Resources/MiscDesc.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty())
					misc.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line),""});
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void loadInfoData() {
		String path = "Resources/Info.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty())
					info.add(new String[]{ReikaStringParser.getStringWithEmbeddedReferences(line),""});
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String getPartDesc(int i) {
		if (partDescs.size() == 0)
			fillPartDescs();
		return partDescs.get(i);
	}

	private static void fillPartDescs() {
		String path = "Resources/ManufacturerDescs.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty())
					partDescs.add(line);
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
