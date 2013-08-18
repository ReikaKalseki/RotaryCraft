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
import java.util.HashMap;

import Reika.DragonAPI.Libraries.ReikaStringParser;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.MachineRegistry;
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
	/*
	public static Object[][] machineNotes = {
		{},

	};*/

	private static HashMap<MachineRegistry, Object[]> machineData = new HashMap<MachineRegistry, Object[]>();
	private static HashMap<MachineRegistry, Object[]> machineNotes = new HashMap<MachineRegistry, Object[]>();

	private static void addData(MachineRegistry m, Object... data) {
		machineData.put(m, data);
	}

	private static void addNotes(MachineRegistry m, Object... data) {
		machineNotes.put(m, data);
	}

	static {
		addData(MachineRegistry.FLOODLIGHT, TileEntityFloodlight.FALLOFF);
		addData(MachineRegistry.BORER, TileEntityBorer.DIGPOWER, TileEntityBorer.OBSIDIANTORQUE);
		addData(MachineRegistry.PILEDRIVER, TileEntityPileDriver.BASEPOWER);
		addData(MachineRegistry.EXTRACTOR, PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinSpeed(2));
		addData(MachineRegistry.RESERVOIR, TileEntityReservoir.CAPACITY);
		addData(MachineRegistry.FAN, PowerReceivers.FAN.getMinPower(), TileEntityFan.MAXPOWER);
		addData(MachineRegistry.COMPACTOR, TileEntityCompactor.REQPRESS, TileEntityCompactor.REQTEMP);
		addData(MachineRegistry.WINDER, TileEntityWinder.UNWINDTORQUE, TileEntityWinder.UNWINDSPEED);
		addData(MachineRegistry.BLASTFURNACE, TileEntityBlastFurnace.SMELTTEMP);
		addData(MachineRegistry.SCALECHEST, TileEntityScaleableChest.MAXSIZE);
		addData(MachineRegistry.PURIFIER, TileEntityPurifier.SMELTTEMP);

		addNotes(MachineRegistry.BEDROCKBREAKER, PowerReceivers.BEDROCKBREAKER.getMinPower(), PowerReceivers.BEDROCKBREAKER.getMinTorque());
		addNotes(MachineRegistry.FERMENTER, PowerReceivers.FERMENTER.getMinPower(), PowerReceivers.FERMENTER.getMinSpeed());
		addNotes(MachineRegistry.GRINDER, PowerReceivers.GRINDER.getMinPower(), PowerReceivers.GRINDER.getMinTorque());
		addNotes(MachineRegistry.FLOODLIGHT, PowerReceivers.FLOODLIGHT.getMinPower());
		addNotes(MachineRegistry.HEATRAY, PowerReceivers.HEATRAY.getMinPower(), TileEntityHeatRay.FALLOFF);
		addNotes(MachineRegistry.BORER, TileEntityBorer.DIGPOWER*500, TileEntityBorer.OBSIDIANTORQUE);
		addNotes(MachineRegistry.PILEDRIVER, TileEntityPileDriver.BASEPOWER);
		addNotes(MachineRegistry.AEROSOLIZER, PowerReceivers.AEROSOLIZER.getMinPower());
		addNotes(MachineRegistry.LIGHTBRIDGE, PowerReceivers.LIGHTBRIDGE.getMinPower());
		addNotes(MachineRegistry.EXTRACTOR, PowerReceivers.EXTRACTOR.getMinPower(0), PowerReceivers.EXTRACTOR.getMinPower(1), PowerReceivers.EXTRACTOR.getMinPower(2), PowerReceivers.EXTRACTOR.getMinPower(3), PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinTorque(3), PowerReceivers.EXTRACTOR.getMinSpeed(1), PowerReceivers.EXTRACTOR.getMinSpeed(2));
		addNotes(MachineRegistry.PULSEJET, PowerReceivers.PULSEJET.getMinSpeed(), TileEntityPulseFurnace.MAXTEMP);
		addNotes(MachineRegistry.PUMP, PowerReceivers.PUMP.getMinPower());
		addNotes(MachineRegistry.RESERVOIR, TileEntityReservoir.CAPACITY);
		addNotes(MachineRegistry.FAN, PowerReceivers.FAN.getMinPower(), PowerReceivers.FAN.getMinPower(), TileEntityFan.FALLOFF);
		addNotes(MachineRegistry.COMPACTOR, PowerReceivers.COMPACTOR.getMinPower(), PowerReceivers.COMPACTOR.getMinTorque(), TileEntityCompactor.REQPRESS, TileEntityCompactor.REQTEMP, TileEntityCompactor.MAXPRESSURE, TileEntityCompactor.MAXTEMP);
		addNotes(MachineRegistry.AUTOBREEDER, PowerReceivers.AUTOBREEDER.getMinPower(), PowerReceivers.AUTOBREEDER.getMinPower(), TileEntityAutoBreeder.FALLOFF);
		addNotes(MachineRegistry.BAITBOX, PowerReceivers.BAITBOX.getMinPower(), PowerReceivers.BAITBOX.getMinPower(), TileEntityBaitBox.FALLOFF);
		addNotes(MachineRegistry.FIREWORK, PowerReceivers.FIREWORK.getMinPower(), PowerReceivers.FIREWORK.getMinSpeed());
		addNotes(MachineRegistry.FRACTIONATOR, PowerReceivers.FRACTIONATOR.getMinPower(), PowerReceivers.FRACTIONATOR.getMinSpeed());
		addNotes(MachineRegistry.GPR, PowerReceivers.GPR.getMinPower(), PowerReceivers.GPR.getMinPower());
		addNotes(MachineRegistry.HEATER, PowerReceivers.HEATER.getMinPower(), TileEntityHeater.MAXTEMP);
		addNotes(MachineRegistry.OBSIDIAN, PowerReceivers.OBSIDIAN.getMinPower(), PowerReceivers.OBSIDIAN.getMinSpeed(), TileEntityObsidianMaker.MAXTEMP, TileEntityObsidianMaker.CAPACITY);
		addNotes(MachineRegistry.PLAYERDETECTOR, TileEntityPlayerDetector.FALLOFF, TileEntityPlayerDetector.BASESPEED, TileEntityPlayerDetector.SPEEDFACTOR);
		addNotes(MachineRegistry.SPAWNERCONTROLLER, PowerReceivers.SPAWNERCONTROLLER.getMinPower(), TileEntitySpawnerController.BASEDELAY);
		addNotes(MachineRegistry.SPRINKLER, TileEntitySprinkler.CAPACITY);
		addNotes(MachineRegistry.VACUUM, PowerReceivers.VACUUM.getMinPower(), PowerReceivers.VACUUM.getMinPower());
		addNotes(MachineRegistry.WOODCUTTER, PowerReceivers.WOODCUTTER.getMinPower(), PowerReceivers.WOODCUTTER.getMinTorque());
		addNotes(MachineRegistry.MOBRADAR, PowerReceivers.MOBRADAR.getMinPower(), PowerReceivers.MOBRADAR.getMinPower(), TileEntityMobRadar.FALLOFF);
		addNotes(MachineRegistry.TNTCANNON, PowerReceivers.TNTCANNON.getMinPower(), PowerReceivers.TNTCANNON.getMinTorque());
		addNotes(MachineRegistry.SONICWEAPON, PowerReceivers.SONICWEAPON.getMinPower(), PowerReceivers.SONICWEAPON.getMinPower(), TileEntitySonicWeapon.FALLOFF, TileEntitySonicWeapon.EYEDAMAGE, TileEntitySonicWeapon.BRAINDAMAGE, TileEntitySonicWeapon.LUNGDAMAGE, TileEntitySonicWeapon.LETHALVOLUME);
		addNotes(MachineRegistry.FORCEFIELD, PowerReceivers.FORCEFIELD.getMinPower(), PowerReceivers.FORCEFIELD.getMinPower(), TileEntityForceField.FALLOFF);
		addNotes(MachineRegistry.MUSICBOX, TileEntityMusicBox.LOOPPOWER);
		addNotes(MachineRegistry.MOBHARVESTER, PowerReceivers.MOBHARVESTER.getMinPower(), PowerReceivers.MOBHARVESTER.getMinPower());
		addNotes(MachineRegistry.PROJECTOR, PowerReceivers.PROJECTOR.getMinPower());
		addNotes(MachineRegistry.RAILGUN, PowerReceivers.RAILGUN.getMinPower());
		addNotes(MachineRegistry.WEATHERCONTROLLER, PowerReceivers.WEATHERCONTROLLER.getMinPower());
		addNotes(MachineRegistry.REFRESHER, PowerReceivers.ITEMREFRESHER.getMinPower(), PowerReceivers.ITEMREFRESHER.getMinPower(), TileEntityItemRefresher.FALLOFF);
		addNotes(MachineRegistry.CAVESCANNER, PowerReceivers.CAVESCANNER.getMinPower());
		addNotes(MachineRegistry.SCALECHEST, PowerReceivers.SCALECHEST.getMinPower(), PowerReceivers.SCALECHEST.getMinPower(), TileEntityScaleableChest.FALLOFF, TileEntityScaleableChest.MAXSIZE);
		addNotes(MachineRegistry.IGNITER, PowerReceivers.IGNITER.getMinPower());
		addNotes(MachineRegistry.FREEZEGUN, PowerReceivers.FREEZEGUN.getMinPower(), PowerReceivers.FREEZEGUN.getMinTorque());
		addNotes(MachineRegistry.MAGNETIZER, PowerReceivers.MAGNETIZER.getMinPower(), PowerReceivers.MAGNETIZER.getMinSpeed());
		addNotes(MachineRegistry.CONTAINMENT, PowerReceivers.CONTAINMENT.getMinPower(), PowerReceivers.CONTAINMENT.getMinPower(), TileEntityContainment.FALLOFF, TileEntityContainment.WITHERPOWER, TileEntityContainment.DRAGONPOWER);
		addNotes(MachineRegistry.SCREEN, PowerReceivers.SCREEN.getMinPower(), PowerReceivers.SCREEN.getMinTorque());
		addNotes(MachineRegistry.PURIFIER, PowerReceivers.PURIFIER.getMinPower(), PowerReceivers.PURIFIER.getMinTorque(), TileEntityPurifier.SMELTTEMP);
		addNotes(MachineRegistry.LASERGUN, PowerReceivers.LASERGUN.getMinPower());
		addNotes(MachineRegistry.ITEMCANNON, PowerReceivers.ITEMCANNON.getMinPower(), PowerReceivers.ITEMCANNON.getMinTorque());
		addNotes(MachineRegistry.FRICTION, PowerReceivers.FRICTION.getMinPower(), PowerReceivers.FRICTION.getMinTorque());
		addNotes(MachineRegistry.BUCKETFILLER, PowerReceivers.BUCKETFILLER.getMinPower(), PowerReceivers.BUCKETFILLER.getMinSpeed());
		addNotes(MachineRegistry.BLOCKCANNON, PowerReceivers.BLOCKCANNON.getMinPower());
	}

	private static MachineRegistry getMachineFromString(String line) {
		for (int i = 0; i < MachineRegistry.machineList.length; i++) {
			String name = MachineRegistry.machineList[i].getName().toLowerCase();
			if (line.toLowerCase().contains(name))
				return MachineRegistry.machineList[i];
		}
		return null;
	}

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
					if (line2 == null)
						line2 = "";
					machines.add(new String[]{String.format(line, machineData.get(getMachineFromString(line))), String.format(line2.replaceAll("\\\\n", "\n"), machineNotes.get(getMachineFromString(line)))});
					i++;
				}
			}
			p.close();
			p2.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e.getMessage()+" from "+e.getClass()+" on loading line "+i);
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
