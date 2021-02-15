/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;

import Reika.DragonAPI.Instantiable.Data.Maps.PluralMap;
import Reika.DragonAPI.Instantiable.Event.Client.ResourceReloadEvent;
import Reika.DragonAPI.Instantiable.IO.XMLInterface;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaEngLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.ModInteract.Power.ReikaEUHelper;
import Reika.DragonAPI.ModInteract.Power.ReikaRFHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesFrictionHeater;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesMagnetizer;
import Reika.RotaryCraft.Base.TileEntity.EnergyToPowerBase;
import Reika.RotaryCraft.ModInterface.TileEntityFuelEngine;
import Reika.RotaryCraft.ModInterface.Conversion.TileEntityAirCompressor;
import Reika.RotaryCraft.ModInterface.Conversion.TileEntityDynamo;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EngineType;
import Reika.RotaryCraft.Registry.Flywheels;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.Registry.PowerReceivers;
import Reika.RotaryCraft.TileEntities.TileEntityChunkLoader;
import Reika.RotaryCraft.TileEntities.TileEntityItemCannon;
import Reika.RotaryCraft.TileEntities.TileEntityItemRefresher;
import Reika.RotaryCraft.TileEntities.TileEntityPlayerDetector;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;
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
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityAdvancedGear;
import Reika.RotaryCraft.TileEntities.Transmission.TileEntityBeltHub;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityContainment;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityEMP;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityForceField;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntityHeatRay;
import Reika.RotaryCraft.TileEntities.Weaponry.TileEntitySonicWeapon;
import Reika.RotaryCraft.TileEntities.World.TileEntityLamp;
import Reika.RotaryCraft.TileEntities.World.TileEntityPileDriver;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class RotaryDescriptions {

	private static String PARENT = getParent(true);
	private static final String DESC_SUFFIX = ":desc";
	private static final String NOTE_SUFFIX = ":note";
	private static final String SUBNOTE_SUFFIX = ":sub";

	private static HashMap<HandbookRegistry, String> data = new HashMap<HandbookRegistry, String>();
	private static PluralMap<String> notes = new PluralMap(2);

	private static HashMap<MachineRegistry, Object[]> machineData = new HashMap<MachineRegistry, Object[]>();
	private static PluralMap<Object[]> machineNotes = new PluralMap(2);
	private static HashMap<HandbookRegistry, Object[]> miscData = new HashMap<HandbookRegistry, Object[]>();

	private static HashMap<HandbookRegistry, Integer> lengths = new HashMap<HandbookRegistry, Integer>();

	private static ArrayList<HandbookRegistry> categories = new ArrayList<HandbookRegistry>();

	private static final XMLInterface parents = loadData("categories");
	private static final XMLInterface machines = loadData("machines");
	private static final XMLInterface trans = loadData("trans");
	private static final XMLInterface converter = loadData("converter");
	private static final XMLInterface engines = loadData("engines");
	private static final XMLInterface tools = loadData("tools");
	private static final XMLInterface resources = loadData("resource");
	private static final XMLInterface miscs = loadData("misc");
	private static final XMLInterface infos = loadData("info");

	private static XMLInterface loadData(String name) {
		XMLInterface xml = new XMLInterface(RotaryCraft.class, PARENT+name+".xml", !ReikaObfuscationHelper.isDeObfEnvironment());
		xml.setFallback(getParent(false)+name+".xml");
		xml.init();
		return xml;
	}

	public static void addCategory(HandbookRegistry h) {
		categories.add(h);
	}

	private static String getParent(boolean locale) {
		return locale && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? getLocalizedParent() : "Resources/";
	}

	@SideOnly(Side.CLIENT)
	private static String getLocalizedParent() {
		Language language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
		String lang = language.getLanguageCode();
		if (hasLocalizedFor(language) && !"en_US".equals(lang))
			return "Resources/"+lang+"/";
		return "Resources/";
	}

	@SideOnly(Side.CLIENT)
	private static boolean hasLocalizedFor(Language language) {
		String lang = language.getLanguageCode();
		try (InputStream o = RotaryCraft.class.getResourceAsStream("Resources/"+lang+"/categories.xml")) {
			return o != null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
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
		machineNotes.put(data, m, 0);
	}

	private static void addSubNotes(MachineRegistry m, int subpage, Object... data) {
		machineNotes.put(data, m, subpage);
	}

	/** Call this from the SERVER side! */
	public static void reload() {
		PARENT = getParent(true);

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
		List<HandbookRegistry> parenttabs = HandbookRegistry.getCategoryTabs(false);

		List<HandbookRegistry> enginetabs = HandbookRegistry.getEngineTabs();
		List<HandbookRegistry> transtabs = HandbookRegistry.getTransTabs();
		List<HandbookRegistry> convertertabs = HandbookRegistry.getConverterTabs();
		List<HandbookRegistry> machinetabs = HandbookRegistry.getMachineTabs();
		List<HandbookRegistry> tooltabs = HandbookRegistry.getToolTabs();
		List<HandbookRegistry> resourcetabs = HandbookRegistry.getResourceTabs();
		List<HandbookRegistry> misctabs = HandbookRegistry.getMiscTabs();
		List<HandbookRegistry> infotabs = HandbookRegistry.getInfoTabs();

		for (HandbookRegistry h : parenttabs) {
			String desc = parents.getValueAtNode("categories:"+h.name().toLowerCase(Locale.ENGLISH).substring(0, h.name().length()-4));
			addEntry(h, desc);
		}

		for (HandbookRegistry h : machinetabs) {
			loadMachineTab(h, machines);
		}

		for (HandbookRegistry h : convertertabs) {
			loadMachineTab(h, converter);
		}

		for (HandbookRegistry h : transtabs) {
			MachineRegistry m = h.getMachine();
			String desc = trans.getValueAtNode("trans:"+h.name().toLowerCase(Locale.ENGLISH));
			Collection<String> sub = trans.getNodesWithin("trans:"+h.name().toLowerCase(Locale.ENGLISH)+SUBNOTE_SUFFIX);

			if (sub != null && !sub.isEmpty()) {
				int k = 0;
				for (String s : sub) {
					String val = trans.getValueAtNode(s);
					if (k == 0) {
						val = String.format(val, machineData.get(m));
						val = String.format(val, miscData.get(h));
						addEntry(h, val);
					}
					else {
						val = String.format(val, machineNotes.get(m, k));
						notes.put(val, h, k-1);
					}
					k++;
					lengths.put(h, k);
				}
				continue;
			}

			if (XMLInterface.NULL_VALUE.equals(desc))
				desc = "There is no handbook data for this machine yet.";
			//ReikaJavaLibrary.pConsole(h.name().toLowerCase()+":"+desc);

			if (machineData.containsKey(m))
				desc = String.format(desc, machineData.get(m));
			if (miscData.containsKey(h))
				desc = String.format(desc, miscData.get(h));
			addEntry(h, desc);
		}

		for (HandbookRegistry h : tooltabs) {
			String desc = tools.getValueAtNode("tools:"+h.name().toLowerCase(Locale.ENGLISH));
			Collection<String> sub = tools.getNodesWithin("tools:"+h.name().toLowerCase(Locale.ENGLISH)+SUBNOTE_SUFFIX);

			if (sub != null && !sub.isEmpty()) {
				int k = 0;
				for (String s : sub) {
					String val = tools.getValueAtNode(s);
					if (k == 0) {
						val = String.format(val, miscData.get(h));
						addEntry(h, val);
					}
					else {
						notes.put(val, h, k-1);
					}
					k++;
					lengths.put(h, k);
				}
				continue;
			}

			addEntry(h, desc);
		}

		for (HandbookRegistry h : resourcetabs) {
			String desc = resources.getValueAtNode("resource:"+h.name().toLowerCase(Locale.ENGLISH));
			desc = String.format(desc, miscData.get(h));
			addEntry(h, desc);
		}

		for (HandbookRegistry h : misctabs) {
			String desc = miscs.getValueAtNode("misc:"+h.name().toLowerCase(Locale.ENGLISH));
			//ReikaJavaLibrary.pConsole(desc);
			desc = String.format(desc, miscData.get(h));
			addEntry(h, desc);
		}

		for (HandbookRegistry h : infotabs) {
			String desc = infos.getValueAtNode("info:"+h.name().toLowerCase(Locale.ENGLISH));
			desc = String.format(desc, miscData.get(h));
			addEntry(h, desc);
		}

		for (HandbookRegistry h : enginetabs) {
			String desc;
			String aux;
			Collection<String> sub = null;
			EngineType e = h.getEngine();
			if (e != null) {
				desc = engines.getValueAtNode("engines:"+e.name().toLowerCase(Locale.ENGLISH)+DESC_SUFFIX);
				aux = engines.getValueAtNode("engines:"+e.name().toLowerCase(Locale.ENGLISH)+NOTE_SUFFIX);
				sub = engines.getNodesWithin("engines:"+e.name().toLowerCase(Locale.ENGLISH)+NOTE_SUFFIX+SUBNOTE_SUFFIX);

				desc = RotaryAux.formatTorqueSpeedPowerForBook(desc, e.getTorque(), e.getSpeed(), e.getPower());
				aux = RotaryAux.formatTorqueSpeedPowerForBook(aux, e.getTorque(), e.getSpeed(), e.getPower());
			}
			else {
				desc = engines.getValueAtNode("engines:"+"solar".toLowerCase(Locale.ENGLISH)+DESC_SUFFIX);
				aux = engines.getValueAtNode("engines:"+"solar".toLowerCase(Locale.ENGLISH)+NOTE_SUFFIX);

				desc = String.format(desc, TileEntitySolar.GENOMEGA);
				aux = String.format(aux, TileEntitySolar.GENOMEGA);
			}

			data.put(h, desc);
			notes.put(aux, h, 0);

			if (sub != null) {
				int k = 0;
				for (String s : sub) {
					String val = engines.getValueAtNode(s);
					if (k == 0 && e != null) {
						val = RotaryAux.formatTorqueSpeedPowerForBook(val, e.getTorque(), e.getSpeed(), e.getPower());
					}
					notes.put(val, h, k);
					k++;
					lengths.put(h, k);
				}
			}
		}
	}

	private static void loadMachineTab(HandbookRegistry h, XMLInterface xml) {
		MachineRegistry m = h.getMachine();
		String desc = xml.getValueAtNode("machines:"+m.name().toLowerCase(Locale.ENGLISH)+DESC_SUFFIX);
		String aux = xml.getValueAtNode("machines:"+m.name().toLowerCase(Locale.ENGLISH)+NOTE_SUFFIX);
		Collection<String> sub = machines.getNodesWithin("machines:"+m.name().toLowerCase(Locale.ENGLISH)+NOTE_SUFFIX+SUBNOTE_SUFFIX);
		desc = RotaryAux.formatValuesForBook(desc, machineData.get(m));
		aux = RotaryAux.formatValuesForBook(aux, machineNotes.get(m, 0));

		if (XMLInterface.NULL_VALUE.equals(desc))
			desc = "There is no handbook data for this machine yet.";

		//ReikaJavaLibrary.pConsole(m.name().toLowerCase()+":"+desc);

		if (m.isDummiedOut()) {
			desc += "\nThis machine is currently unavailable.";
			if (m.getModDependency() != null && !m.getModDependency().isLoaded())
				desc += "\nThis machine depends on another mod.";
			aux += "\nNote: Dummied Out";
		}
		if (m.hasPrerequisite()) {
			aux += "\nDependencies: "+m.getPrerequisite();
		}
		if (m.isIncomplete()) {
			desc += "\nThis machine is incomplete. Use at your own risk.";
		}

		addEntry(h, desc);
		notes.put(aux, h, 0);

		if (sub != null) {
			int k = 0;
			for (String s : sub) {
				String val = xml.getValueAtNode(s);
				val = RotaryAux.formatValuesForBook(val, machineNotes.get(m, k));
				notes.put(val, h, k);
				k++;
				lengths.put(h, k);
			}
		}
	}

	public static String getData(HandbookRegistry h) {
		if (!data.containsKey(h))
			return "";
		return data.get(h);
	}

	public static String getNotes(HandbookRegistry h, int subpage) {
		subpage--;
		if (!notes.containsKeyV(h, subpage))
			return "";
		return notes.get(h, subpage);
	}

	public static int getNotesLength(HandbookRegistry h) {
		return lengths.containsKey(h) ? lengths.get(h) : 1;
	}

	static {
		loadNumericalData();

		MinecraftForge.EVENT_BUS.register(new ReloadListener());
	}

	public static class ReloadListener {

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void reload(ResourceReloadEvent evt) {
			RotaryDescriptions.reload();
		}

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

				RotaryAux.tungstenDensity,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Ttungsten),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Ttungsten),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Stungsten),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Stungsten),

				ReikaEngLibrary.rhodiamond,
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Tdiamond),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Tdiamond),
				ReikaMathLibrary.getThousandBase(ReikaEngLibrary.Sdiamond),
				ReikaEngLibrary.getSIPrefix(ReikaEngLibrary.Sdiamond)
				);

		addData(HandbookRegistry.SHAFTS, MaterialRegistry.getAllLimitLoadsAsInts());
		addData(HandbookRegistry.FLYWHEELS, Flywheels.getLimitsForDisplay());

		addData(HandbookRegistry.MODINTERFACE,
				ReikaMathLibrary.getThousandBase(ReikaRFHelper.getWattsPerRF()),
				ReikaEngLibrary.getSIPrefix(ReikaRFHelper.getWattsPerRF()),

				//ReikaMathLibrary.getThousandBase(ReikaBuildCraftHelper.getFuelBucketEnergy()),
				//ReikaEngLibrary.getSIPrefix(ReikaBuildCraftHelper.getFuelBucketEnergy()),

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
		addData(MachineRegistry.BLASTFURNACE, TileEntityBlastFurnace.SMELTTEMP, TileEntityBlastFurnace.BEDROCKTEMP);
		addData(MachineRegistry.SCALECHEST, TileEntityScaleableChest.MAXSIZE);
		addData(MachineRegistry.PURIFIER, TileEntityPurifier.SMELTTEMP);
		addData(MachineRegistry.GENERATOR, ReikaEUHelper.getWattsPerEU());
		addData(MachineRegistry.BELT, ((TileEntityBeltHub)MachineRegistry.BELT.createTEInstanceForRender(0)).getMaxTorque(), ((TileEntityBeltHub)MachineRegistry.BELT.createTEInstanceForRender(0)).getMaxSmoothSpeed());
		addData(MachineRegistry.CHAIN, ((TileEntityBeltHub)MachineRegistry.CHAIN.createTEInstanceForRender(0)).getMaxTorque(), ((TileEntityBeltHub)MachineRegistry.CHAIN.createTEInstanceForRender(0)).getMaxSmoothSpeed());
		addData(MachineRegistry.DYNAMO, TileEntityDynamo.MAXTORQUE, TileEntityDynamo.MAXTORQUE_UPGRADE, TileEntityDynamo.MAXOMEGA);
		addData(MachineRegistry.ITEMCANNON, TileEntityItemCannon.STACKPOWER);
		addData(MachineRegistry.ADVANCEDGEARS, TileEntityAdvancedGear.WORMRATIO);

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
		addNotes(MachineRegistry.VACUUM, PowerReceivers.VACUUM.getMinPower(), TileEntityVacuum.FALLOFF);
		addNotes(MachineRegistry.WOODCUTTER, PowerReceivers.WOODCUTTER.getMinPower(), PowerReceivers.WOODCUTTER.getMinTorque());
		addNotes(MachineRegistry.MOBRADAR, PowerReceivers.MOBRADAR.getMinPower(), PowerReceivers.MOBRADAR.getMinPower(), TileEntityMobRadar.FALLOFF);
		addNotes(MachineRegistry.TNTCANNON, PowerReceivers.TNTCANNON.getMinPower(), PowerReceivers.TNTCANNON.getMinTorque());
		int fudge = TileEntitySonicWeapon.fudge;
		addNotes(MachineRegistry.SONICWEAPON, PowerReceivers.SONICWEAPON.getMinPower(), PowerReceivers.SONICWEAPON.getMinPower(), TileEntitySonicWeapon.FALLOFF, TileEntitySonicWeapon.EYEDAMAGE/fudge, TileEntitySonicWeapon.BRAINDAMAGE/fudge, TileEntitySonicWeapon.LUNGDAMAGE/fudge, TileEntitySonicWeapon.LETHALVOLUME/fudge);
		addNotes(MachineRegistry.FORCEFIELD, PowerReceivers.FORCEFIELD.getMinPower(), PowerReceivers.FORCEFIELD.getMinPower(), TileEntityForceField.FALLOFF);
		addNotes(MachineRegistry.MUSICBOX, TileEntityMusicBox.LOOPPOWER);
		addNotes(MachineRegistry.MOBHARVESTER, PowerReceivers.MOBHARVESTER.getMinPower(), PowerReceivers.MOBHARVESTER.getMinPower()*2);
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
		addNotes(MachineRegistry.DISTILLER, TileEntityDistillery.getValidConversions());
		addNotes(MachineRegistry.CRYSTALLIZER, PowerReceivers.CRYSTALLIZER.getMinPower(), PowerReceivers.CRYSTALLIZER.getMinSpeed());
		addNotes(MachineRegistry.GRINDSTONE, PowerReceivers.GRINDSTONE.getMinPower(), PowerReceivers.GRINDSTONE.getMinTorque());
		addNotes(MachineRegistry.BLOWER, PowerReceivers.BLOWER.getMinPower(), PowerReceivers.BLOWER.getMinSpeed());
		addNotes(MachineRegistry.REFRIGERATOR, PowerReceivers.REFRIGERATOR.getMinPower(), PowerReceivers.REFRIGERATOR.getMinTorque());
		addNotes(MachineRegistry.COMPOSTER, TileEntityComposter.MINTEMP, TileEntityComposter.KILLTEMP);
		addNotes(MachineRegistry.GASTANK, PowerReceivers.GASTANK.getMinPower(), PowerReceivers.GASTANK.getMinTorque());
		addNotes(MachineRegistry.CRAFTER, PowerReceivers.CRAFTER.getMinPower());
		addNotes(MachineRegistry.ANTIAIR, PowerReceivers.ANTIAIR.getMinPower(), PowerReceivers.ANTIAIR.getMinTorque());
		addNotes(MachineRegistry.PIPEPUMP, PowerReceivers.PIPEPUMP.getMinPower(), PowerReceivers.PIPEPUMP.getMinSpeed());
		addNotes(MachineRegistry.CENTRIFUGE, PowerReceivers.CENTRIFUGE.getMinPower(), PowerReceivers.CENTRIFUGE.getMinSpeed());
		addNotes(MachineRegistry.WETTER, PowerReceivers.WETTER.getMinPower(), PowerReceivers.WETTER.getMinSpeed());
		addNotes(MachineRegistry.CHUNKLOADER, PowerReceivers.CHUNKLOADER.getMinSpeed(), TileEntityChunkLoader.BASE_RADIUS, PowerReceivers.CHUNKLOADER.getMinSpeed(), TileEntityChunkLoader.FALLOFF);
		addNotes(MachineRegistry.DROPS, PowerReceivers.DROPS.getMinPower(), PowerReceivers.DROPS.getMinTorque());
		addNotes(MachineRegistry.SPILLER, PowerReceivers.SPILLER.getMinPower());
		addNotes(MachineRegistry.FILLER, PowerReceivers.FILLER.getMinPower());
		addNotes(MachineRegistry.GATLING, PowerReceivers.GATLING.getMinPower(), PowerReceivers.GATLING.getMinSpeed());

		addNotes(MachineRegistry.MAGNETIC, EnergyToPowerBase.MAXTEMP);
		addNotes(MachineRegistry.PNEUENGINE, EnergyToPowerBase.MAXTEMP);
		addNotes(MachineRegistry.ELECTRICMOTOR, EnergyToPowerBase.MAXTEMP);
		addNotes(MachineRegistry.STEAMTURBINE, EnergyToPowerBase.MAXTEMP);

		addNotes(MachineRegistry.EMP, TileEntityEMP.BLAST_ENERGY);

		addSubNotes(MachineRegistry.MAGNETIC, 1, EnergyToPowerBase.getTiersAsString());
		addSubNotes(MachineRegistry.PNEUENGINE, 1, EnergyToPowerBase.getTiersAsString());
		addSubNotes(MachineRegistry.ELECTRICMOTOR, 1, EnergyToPowerBase.getTiersAsString());
		addSubNotes(MachineRegistry.STEAMTURBINE, 1, EnergyToPowerBase.getTiersAsString());

		addSubNotes(MachineRegistry.MAGNETIZER, 1, RecipesMagnetizer.getRecipes().getRecipesAsString());

		addSubNotes(MachineRegistry.ADVANCEDGEARS, 1, TileEntityAdvancedGear.getMaxStorageCapacity(false), TileEntityAdvancedGear.getMaxStorageCapacityFormatted(false), TileEntityAdvancedGear.getMaxStorageCapacity(true), TileEntityAdvancedGear.getMaxStorageCapacityFormatted(true));
		addSubNotes(MachineRegistry.ADVANCEDGEARS, 2, TileEntityAdvancedGear.getRequiredInputTorque(), TileEntityAdvancedGear.getRequiredInputPower());
		addSubNotes(MachineRegistry.ADVANCEDGEARS, 3, TileEntityAdvancedGear.getOutputCap(false), TileEntityAdvancedGear.getOutputCap(false), TileEntityAdvancedGear.getOutputCap(true), TileEntityAdvancedGear.getOutputCap(true), TileEntityAdvancedGear.getOutputFunction());

		addData(HandbookRegistry.TUNGSTEN, RecipesFrictionHeater.getRecipes().getRecipeByInput(ItemStacks.tungstenflakes).requiredTemperature);
	}

	public static String getParentPage() {
		return PARENT;
	}
}
