/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import Reika.DragonAPI.Instantiable.IO.XMLInterface;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.ReikaBuildCraftHelper;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.ModInterface.TileEntityAirCompressor;
import Reika.RotaryCraft.ModInterface.TileEntityDynamo;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.ModInterface.TileEntityGenerator;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityEngineController;
import Reika.RotaryCraft.TileEntities.Auxiliary.TileEntityHeater;
import Reika.RotaryCraft.TileEntities.Decorative.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityAutoBreeder;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityBaitBox;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityComposter;
import Reika.RotaryCraft.TileEntities.Farming.TileEntityFan;
import Reika.RotaryCraft.TileEntities.Farming.TileEntitySpawnerController;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityCompactor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityDistillery;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityExtractor;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPulseFurnace;
import Reika.RotaryCraft.TileEntities.Processing.TileEntityPurifier;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBlastFurnace;
import Reika.RotaryCraft.TileEntities.Production.TileEntityBorer;
import Reika.RotaryCraft.TileEntities.Production.TileEntityObsidianMaker;
import Reika.RotaryCraft.TileEntities.Production.TileEntitySolar;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.Storage.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.Surveying.TileEntityMobRadar;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityFlywheel;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.World.TileEntityLamp;
import Reika.RotaryCraft.TileEntities.World.TileEntityPileDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraftforge.fluids.FluidContainerRegistry;

public final class RotaryDescriptions {

	public static final String PARENT = getParent();
	public static final String DESC_SUFFIX = ":desc";
	public static final String NOTE_SUFFIX = ":note";

	private static HashMap<HandbookRegistry, String> data = new HashMap<HandbookRegistry, String>();
	private static HashMap<HandbookRegistry, String> notes = new HashMap<HandbookRegistry, String>();

	private static HashMap<MachineRegistry, Object[]> machineData = new HashMap<MachineRegistry, Object[]>();
	private static HashMap<MachineRegistry, Object[]> machineNotes = new HashMap<MachineRegistry, Object[]>();
	private static HashMap<HandbookRegistry, Object[]> miscData = new HashMap<HandbookRegistry, Object[]>();

	private static ArrayList<HandbookRegistry> categories = new ArrayList<HandbookRegistry>();

	private static final boolean mustLoad = !ReikaObfuscationHelper.isDeObfEnvironment();
	private static final XMLInterface parents = new XMLInterface(RotaryCraft.class, PARENT+"categories.xml", mustLoad);
	private static final XMLInterface machines = new XMLInterface(RotaryCraft.class, PARENT+"machines.xml", mustLoad);
	private static final XMLInterface trans = new XMLInterface(RotaryCraft.class, PARENT+"trans.xml", mustLoad);
	private static final XMLInterface engines = new XMLInterface(RotaryCraft.class, PARENT+"engines.xml", mustLoad);
	private static final XMLInterface tools = new XMLInterface(RotaryCraft.class, PARENT+"tools.xml", mustLoad);
	private static final XMLInterface resources = new XMLInterface(RotaryCraft.class, PARENT+"resource.xml", mustLoad);
	private static final XMLInterface miscs = new XMLInterface(RotaryCraft.class, PARENT+"misc.xml", mustLoad);
	private static final XMLInterface infos = new XMLInterface(RotaryCraft.class, PARENT+"info.xml", mustLoad);

	public static void addCategory(HandbookRegistry h) {
		categories.add(h);
	}

	private static String getParent() {
		Language language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
		String lang = language.getLanguageCode();
		String sg = lang.toUpperCase().substring(0, 2)+"/";
		if (hasLocalizedFor(language) && !"EN".equals(sg))
			return "Resources/"+sg;
		return "Resources/";
	}

	private static boolean hasLocalizedFor(Language language) {
		String lang = language.getLanguageCode();
		String sg = lang.toUpperCase().substring(0, 2)+"/";
		Object o = RotaryCraft.class.getResourceAsStream("Resources/"+sg+"categories.xml");
		return o != null;
	}

	public static int getCategoryCount() {
		return categories.size();
	}

	public static String getTOC() {
		List<HandbookRegistry> toctabs = HandbookRegistry.getTOCTabs();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < toctabs.size(); i++) {
			HandbookRegistry h = toctabs.get(i);
			sb.append("Page ");
			sb.append(h.getScreen());
			sb.append(" - ");
			sb.append(h.getTOCTitle());
			if (i < toctabs.size()-1)
				sb.append("\n");
		}
		return sb.toString();
	}

	private static void addData(MachineRegistry m, Object... data) {
		machineData.put(m, data);
	}

	private static void addData(HandbookRegistry h, Object... data) {
		miscData.put(h, data);
	}

	private static void addData(HandbookRegistry h, int[] data) {
		Object[] o = new Object[data.length];
		for (int i = 0; i < o.length; i++)
			o[i] = data[i];
		miscData.put(h, o);
	}

	private static void addNotes(MachineRegistry m, Object... data) {
		machineNotes.put(m, data);
	}

	/** Call this from the SERVER side! */
	public static void reload() {
		loadNumericalData();

		machines.reread();
		trans.reread();
		engines.reread();
		tools.reread();
		resources.reread();
		miscs.reread();
		infos.reread();

		parents.reread();

		loadData();
	}

	private static void addEntry(HandbookRegistry h, String sg) {
		data.put(h, sg);
	}

	public static void loadData() {
		List<HandbookRegistry> parenttabs = HandbookRegistry.getCategoryTabs();

		HandbookRegistry[] enginetabs = HandbookRegistry.getEngineTabs();
		List<HandbookRegistry> machinetabs = HandbookRegistry.getMachineTabs();
		HandbookRegistry[] transtabs = HandbookRegistry.getTransTabs();
		HandbookRegistry[] tooltabs = HandbookRegistry.getToolTabs();
		HandbookRegistry[] resourcetabs = HandbookRegistry.getResourceTabs();
		HandbookRegistry[] misctabs = HandbookRegistry.getMiscTabs();
		HandbookRegistry[] infotabs = HandbookRegistry.getInfoTabs();

		for (int i = 0; i < parenttabs.size(); i++) {
			HandbookRegistry h = parenttabs.get(i);
			String desc = parents.getValueAtNode("categories:"+h.name().toLowerCase().substring(0, h.name().length()-4));
			addEntry(h, desc);
		}

		for (int i = 0; i < machinetabs.size(); i++) {
			HandbookRegistry h = machinetabs.get(i);
			MachineRegistry m = h.getMachine();
			String desc = machines.getValueAtNode("machines:"+m.name().toLowerCase()+DESC_SUFFIX);
			String aux = machines.getValueAtNode("machines:"+m.name().toLowerCase()+NOTE_SUFFIX);
			desc = String.format(desc, machineData.get(m));
			aux = String.format(aux, machineNotes.get(m));

			if (XMLInterface.NULL_VALUE.equals(desc))
				desc = "There is no handbook data for this machine yet.";

			//ReikaJavaLibrary.pConsole(m.name().toLowerCase()+":"+desc);

			if (m.isDummiedOut()) {
				desc += "\nThis machine is currently unavailable.";
				if (m.hasPrerequisite() && !m.getPrerequisite().isLoaded())
					desc += "\nThis machine depends on another mod.";
				aux += "\nNote: Dummied Out";
			}
			if (m.hasPrerequisite()) {
				String sg = m.getPrerequisite().getModLabel().replaceAll("[|]", "");
				aux += "\nDependencies: "+ReikaStringParser.splitCamelCase(sg).replaceAll(" Craft", "Craft");
			}
			if (m.isIncomplete()) {
				desc += "\nThis machine is incomplete. Use at your own risk.";
			}

			addEntry(h, desc);
			notes.put(h, aux);
		}

		for (int i = 0; i < transtabs.length; i++) {
			HandbookRegistry h = transtabs[i];
			MachineRegistry m = h.getMachine();
			String desc = trans.getValueAtNode("trans:"+h.name().toLowerCase());

			if (XMLInterface.NULL_VALUE.equals(desc))
				desc = "There is no handbook data for this machine yet.";
			//ReikaJavaLibrary.pConsole(h.name().toLowerCase()+":"+desc);

			desc = String.format(desc, machineData.get(m));
			addEntry(h, desc);
		}

		for (int i = 0; i < tooltabs.length; i++) {
			HandbookRegistry h = tooltabs[i];
			String desc = tools.getValueAtNode("tools:"+h.name().toLowerCase());
			addEntry(h, desc);
		}

		for (int i = 0; i < resourcetabs.length; i++) {
			HandbookRegistry h = resourcetabs[i];
			String desc = resources.getValueAtNode("resource:"+h.name().toLowerCase());
			addEntry(h, desc);
		}

		for (int i = 0; i < misctabs.length; i++) {
			HandbookRegistry h = misctabs[i];
			String desc = miscs.getValueAtNode("misc:"+h.name().toLowerCase());
			//ReikaJavaLibrary.pConsole(desc);
			desc = String.format(desc, miscData.get(h));
			addEntry(h, desc);
		}

		for (int i = 0; i < infotabs.length; i++) {
			HandbookRegistry h = infotabs[i];
			String desc = infos.getValueAtNode("info:"+h.name().toLowerCase());
			desc = String.format(desc, miscData.get(h));
			addEntry(h, desc);
		}

		for (int i = 0; i < enginetabs.length; i++) {
			HandbookRegistry h = enginetabs[i];
			String desc;
			String aux;
			if (i < EngineType.engineList.length) {
				EngineType e = EngineType.engineList[i];
				desc = engines.getValueAtNode("engines:"+e.name().toLowerCase()+DESC_SUFFIX);
				aux = engines.getValueAtNode("engines:"+e.name().toLowerCase()+NOTE_SUFFIX);

				desc = String.format(desc, e.getTorque(), e.getSpeed(), e.getPowerForDisplay());
				aux = String.format(aux, e.getTorque(), e.getSpeed(), e.getPowerForDisplay());
			}
			else {
				desc = engines.getValueAtNode("engines:"+"solar".toLowerCase()+DESC_SUFFIX);
				aux = engines.getValueAtNode("engines:"+"solar".toLowerCase()+NOTE_SUFFIX);

				desc = String.format(desc, TileEntitySolar.GENOMEGA);
				aux = String.format(aux, TileEntitySolar.GENOMEGA);
			}

			data.put(h, desc);
			notes.put(h, aux);
		}
	}


	public static String getData(HandbookRegistry h) {
		if (!data.containsKey(h))
			return "";
		return data.get(h);
	}

	public static String getNotes(HandbookRegistry h) {
		if (!notes.containsKey(h))
			return "";
		return notes.get(h);
	}

	static {
		loadNumericalData();
	}

	private static void loadNumericalData() {
		addData(HandbookRegistry.MATERIAL,
				ReikaEngLibrary.rhowood,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Twood),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Twood),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Swood),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Swood),

				ReikaEngLibrary.rhorock,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Tstone),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Tstone),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Sstone),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Sstone),

				ReikaEngLibrary.rhoiron,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Tiron),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Tiron),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Siron),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Siron),

				ReikaEngLibrary.rhoiron,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Tsteel),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Tsteel),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Ssteel),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Ssteel),

				ReikaEngLibrary.rhogold,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Tgold),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Tgold),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Sgold),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Sgold),

				ReikaEngLibrary.rhodiamond,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Tdiamond),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Tdiamond),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Sdiamond),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Sdiamond)
				);

		addData(HandbookRegistry.SHAFTS, MaterialRegistry.getAllLimitLoads());
		addData(HandbookRegistry.FLYWHEELS, TileEntityFlywheel.getLimitLoads());

		addData(HandbookRegistry.MODINTERFACE,
				ReikaMathLibrary.getThousandBase(ReikaBuildCraftHelper.getWattsPerMJ()),
				ReikaEngLibrary.getSIPrefix(ReikaBuildCraftHelper.getWattsPerMJ()),

				ReikaMathLibrary.getThousandBase(ReikaBuildCraftHelper.getFuelBucketEnergy()),
				ReikaEngLibrary.getSIPrefix(ReikaBuildCraftHelper.getFuelBucketEnergy()),

				TileEntityExtractor.oreCopy,
				TileEntityExtractor.oreCopyNether,
				TileEntityExtractor.oreCopyRare
				);

		ArrayList<MachineRegistry> li = MachineRegistry.getEnchantableMachineList();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < li.size(); i++) {
			sb.append(li.get(i).getName());
			if (i < li.size()-1)
				sb.append(", ");
		}
		addData(HandbookRegistry.ENCHANTING, sb.toString());

		addData(MachineRegistry.BORER, TileEntityBorer.DIGPOWER*10, TileEntityBorer.OBSIDIANTORQUE);
		addData(MachineRegistry.PILEDRIVER, TileEntityPileDriver.BASEPOWER);
		addData(MachineRegistry.EXTRACTOR, PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinSpeed(2));
		addData(MachineRegistry.RESERVOIR, TileEntityReservoir.CAPACITY/FluidContainerRegistry.BUCKET_VOLUME);
		addData(MachineRegistry.FAN, PowerReceivers.FAN.getMinPower(), TileEntityFan.MAXPOWER);
		addData(MachineRegistry.COMPACTOR, TileEntityCompactor.REQPRESS, TileEntityCompactor.REQTEMP);
		addData(MachineRegistry.BLASTFURNACE, TileEntityBlastFurnace.SMELTTEMP);
		addData(MachineRegistry.SCALECHEST, TileEntityScaleableChest.MAXSIZE);
		addData(MachineRegistry.PURIFIER, TileEntityPurifier.SMELTTEMP);
		addData(MachineRegistry.GENERATOR, TileEntityGenerator.OUTPUT_VOLTAGE);
		addData(MachineRegistry.BELT, TileEntityBeltHub.getMaxTorque(), TileEntityBeltHub.getMaxSmoothSpeed());
		addData(MachineRegistry.DYNAMO, TileEntityDynamo.MAXTORQUE, TileEntityDynamo.MAXOMEGA);

		addNotes(MachineRegistry.BEDROCKBREAKER, PowerReceivers.BEDROCKBREAKER.getMinPower(), PowerReceivers.BEDROCKBREAKER.getMinTorque());
		addNotes(MachineRegistry.FERMENTER, PowerReceivers.FERMENTER.getMinPower(), PowerReceivers.FERMENTER.getMinSpeed());
		addNotes(MachineRegistry.GRINDER, PowerReceivers.GRINDER.getMinPower(), PowerReceivers.GRINDER.getMinTorque());
		addNotes(MachineRegistry.FLOODLIGHT, PowerReceivers.FLOODLIGHT.getMinPower());
		addNotes(MachineRegistry.HEATRAY, PowerReceivers.HEATRAY.getMinPower(), PowerReceivers.HEATRAY.getMinPower(), TileEntityHeatRay.FALLOFF);
		addNotes(MachineRegistry.PILEDRIVER, TileEntityPileDriver.BASEPOWER, PowerReceivers.PILEDRIVER.getMinTorque());
		addNotes(MachineRegistry.AEROSOLIZER, PowerReceivers.AEROSOLIZER.getMinPower());
		addNotes(MachineRegistry.LIGHTBRIDGE, PowerReceivers.LIGHTBRIDGE.getMinPower(), PowerReceivers.LIGHTBRIDGE.getMinPower()/ConfigRegistry.BRIDGERANGE.getValue());
		addNotes(MachineRegistry.EXTRACTOR, PowerReceivers.EXTRACTOR.getMinPower(0), PowerReceivers.EXTRACTOR.getMinPower(1), PowerReceivers.EXTRACTOR.getMinPower(2), PowerReceivers.EXTRACTOR.getMinPower(3), PowerReceivers.EXTRACTOR.getMinTorque(0), PowerReceivers.EXTRACTOR.getMinTorque(3), PowerReceivers.EXTRACTOR.getMinSpeed(1), PowerReceivers.EXTRACTOR.getMinSpeed(2));
		addNotes(MachineRegistry.PULSEJET, PowerReceivers.PULSEJET.getMinSpeed(), TileEntityPulseFurnace.MAXTEMP);
		addNotes(MachineRegistry.PUMP, PowerReceivers.PUMP.getMinPower(), PowerReceivers.PUMP.getMinTorque());
		addNotes(MachineRegistry.RESERVOIR, TileEntityReservoir.CAPACITY/1000);
		addNotes(MachineRegistry.FAN, PowerReceivers.FAN.getMinPower(), TileEntityFan.FALLOFF, TileEntityFan.HARVESTSPEED, TileEntityFan.FIRESPEED);
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
		addNotes(MachineRegistry.VACUUM, PowerReceivers.VACUUM.getMinPower(), PowerReceivers.VACUUM.getMinPower());
		addNotes(MachineRegistry.WOODCUTTER, PowerReceivers.WOODCUTTER.getMinPower(), PowerReceivers.WOODCUTTER.getMinTorque());
		addNotes(MachineRegistry.MOBRADAR, PowerReceivers.MOBRADAR.getMinPower(), PowerReceivers.MOBRADAR.getMinPower(), TileEntityMobRadar.FALLOFF);
		addNotes(MachineRegistry.TNTCANNON, PowerReceivers.TNTCANNON.getMinPower(), PowerReceivers.TNTCANNON.getMinTorque());
		int fudge = TileEntitySonicWeapon.fudge;
		addNotes(MachineRegistry.SONICWEAPON, PowerReceivers.SONICWEAPON.getMinPower(), PowerReceivers.SONICWEAPON.getMinPower(), TileEntitySonicWeapon.FALLOFF, TileEntitySonicWeapon.EYEDAMAGE/fudge, TileEntitySonicWeapon.BRAINDAMAGE/fudge, TileEntitySonicWeapon.LUNGDAMAGE/fudge, TileEntitySonicWeapon.LETHALVOLUME/fudge);
		addNotes(MachineRegistry.FORCEFIELD, PowerReceivers.FORCEFIELD.getMinPower(), PowerReceivers.FORCEFIELD.getMinPower(), TileEntityForceField.FALLOFF);
		addNotes(MachineRegistry.MUSICBOX, TileEntityMusicBox.LOOPPOWER);
		addNotes(MachineRegistry.MOBHARVESTER, PowerReceivers.MOBHARVESTER.getMinPower(), PowerReceivers.MOBHARVESTER.getMinPower());
		addNotes(MachineRegistry.PROJECTOR, PowerReceivers.PROJECTOR.getMinPower());
		addNotes(MachineRegistry.RAILGUN, PowerReceivers.RAILGUN.getMinPower());
		addNotes(MachineRegistry.WEATHERCONTROLLER, PowerReceivers.WEATHERCONTROLLER.getMinPower());
		addNotes(MachineRegistry.REFRESHER, PowerReceivers.REFRESHER.getMinPower(), PowerReceivers.REFRESHER.getMinPower(), TileEntityItemRefresher.FALLOFF);
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
		addNotes(MachineRegistry.COMPRESSOR, TileEntityAirCompressor.MAXPRESSURE);
		addNotes(MachineRegistry.LAMP, TileEntityLamp.MAXRANGE);
		addNotes(MachineRegistry.ECU, TileEntityEngineController.getSettingsAsString());
		addNotes(MachineRegistry.BLASTFURNACE, TileEntityBlastFurnace.SMELT_XP);
		addNotes(MachineRegistry.FUELENHANCER, PowerReceivers.FUELENHANCER.getMinPower(), PowerReceivers.FUELENHANCER.getMinSpeed());
		addNotes(MachineRegistry.ARROWGUN, PowerReceivers.ARROWGUN.getMinPower(), PowerReceivers.ARROWGUN.getMinTorque());
		//addNotes(MachineRegistry.STEAMTURBINE, TileEntitySteam.GEN_OMEGA, TileEntitySteam.MAX_TORQUE);
		addNotes(MachineRegistry.FERTILIZER, PowerReceivers.FERTILIZER.getMinPower());
		/*addNotes(MachineRegistry.ELECTRICMOTOR,
				TileEntityElectricMotor.Tier.LOW.inputVoltage, TileEntityElectricMotor.Tier.LOW.inputCurrent, TileEntityElectricMotor.Tier.LOW.outputTorque, TileEntityElectricMotor.Tier.LOW.outputSpeed, TileEntityElectricMotor.Tier.LOW.getPowerForDisplay(),
				TileEntityElectricMotor.Tier.MEDIUM.inputVoltage, TileEntityElectricMotor.Tier.MEDIUM.inputCurrent, TileEntityElectricMotor.Tier.MEDIUM.outputTorque, TileEntityElectricMotor.Tier.MEDIUM.outputSpeed, TileEntityElectricMotor.Tier.MEDIUM.getPowerForDisplay(),
				TileEntityElectricMotor.Tier.HIGH.inputVoltage, TileEntityElectricMotor.Tier.HIGH.inputCurrent, TileEntityElectricMotor.Tier.HIGH.outputTorque, TileEntityElectricMotor.Tier.HIGH.outputSpeed, TileEntityElectricMotor.Tier.HIGH.getPowerForDisplay()
				);*/
		addNotes(MachineRegistry.AGGREGATOR, PowerReceivers.AGGREGATOR.getMinPower(), PowerReceivers.AGGREGATOR.getMinSpeed());
		addNotes(MachineRegistry.FUELENGINE, TileEntityFuelEngine.GEN_TORQUE, TileEntityFuelEngine.GEN_OMEGA, TileEntityFuelEngine.GEN_TORQUE*TileEntityFuelEngine.GEN_OMEGA);
		addNotes(MachineRegistry.AIRGUN, PowerReceivers.AIRGUN.getMinPower(), PowerReceivers.AIRGUN.getMinTorque());
		addNotes(MachineRegistry.SONICBORER, PowerReceivers.SONICBORER.getMinPower(), PowerReceivers.SONICBORER.getMinTorque());
		addNotes(MachineRegistry.FILLINGSTATION, PowerReceivers.FILLINGSTATION.getMinPower());
		addNotes(MachineRegistry.SORTING, PowerReceivers.SORTING.getMinPower());
		addNotes(MachineRegistry.DEFOLIATOR, PowerReceivers.DEFOLIATOR.getMinPower());
		addNotes(MachineRegistry.BIGFURNACE, PowerReceivers.BIGFURNACE.getMinPower());
		addNotes(MachineRegistry.DISTILLER, PowerReceivers.DISTILLER.getMinPower(), PowerReceivers.DISTILLER.getMinTorque(), TileEntityDistillery.getValidConversions());
		addNotes(MachineRegistry.CRYSTALLIZER, PowerReceivers.CRYSTALLIZER.getMinPower(), PowerReceivers.CRYSTALLIZER.getMinSpeed());
		addNotes(MachineRegistry.GRINDSTONE, PowerReceivers.GRINDSTONE.getMinPower(), PowerReceivers.GRINDSTONE.getMinTorque());
		addNotes(MachineRegistry.BLOWER, PowerReceivers.BLOWER.getMinPower(), PowerReceivers.BLOWER.getMinSpeed());
		addNotes(MachineRegistry.REFRIGERATOR, PowerReceivers.REFRIGERATOR.getMinPower(), PowerReceivers.REFRIGERATOR.getMinTorque());
		addNotes(MachineRegistry.COMPOSTER, TileEntityComposter.MINTEMP, TileEntityComposter.MAXTEMP);
		addNotes(MachineRegistry.GASTANK, PowerReceivers.GASTANK.getMinPower(), PowerReceivers.GASTANK.getMinTorque());
		addNotes(MachineRegistry.CRAFTER, PowerReceivers.CRAFTER.getMinPower());
		addNotes(MachineRegistry.ANTIAIR, PowerReceivers.ANTIAIR.getMinPower(), PowerReceivers.ANTIAIR.getMinTorque());
		addNotes(MachineRegistry.PIPEPUMP, PowerReceivers.PIPEPUMP.getMinPower(), PowerReceivers.PIPEPUMP.getMinSpeed());
		addNotes(MachineRegistry.CENTRIFUGE, PowerReceivers.CENTRIFUGE.getMinPower(), PowerReceivers.CENTRIFUGE.getMinSpeed());
	}
}