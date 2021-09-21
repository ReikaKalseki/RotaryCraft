/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import Reika.DragonAPI.Auxiliary.Trackers.PackModificationTracker;
import Reika.DragonAPI.Instantiable.GUI.ImagedGuiButton;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.HandbookNotifications;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Auxiliary.Interfaces.HandbookEntry;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.RecipesBlastFurnace.BlastFurnacePattern;
import Reika.RotaryCraft.Base.TileEntity.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.TileEntity.TileEntityEngine;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.Registry.GearboxTypes.GearPart;

public enum HandbookRegistry implements HandbookEntry {

	//---------------------TOC--------------------//
	TOC("Table of Contents", "TOC"),
	INFO("Information"),
	ENGINES("Engines"),
	TRANS("Transmission"),
	CONVERTER("Converters"),
	PRODMACHINES("Production"),
	PROCMACHINES("Processing"),
	FARMMACHINES("Farming"),
	PIPEMACHINES("Piping"),
	ACCMACHINES("Accessory"),
	WEPMACHINES("Weaponry"),
	SURVMACHINES("Surveying"),
	COSMACHINES("Cosmetic"),
	UTILMACHINES("Utility"),
	TOOLS("Tools"),
	RESOURCE("Resources"),

	//---------------------INFO--------------------//
	TERMS("Basic Terms", "Terms and Physics Explanations"),
	PHYSICS("Relevant Physics"),
	MATERIAL("Material Properties"),
	SHAFTS("Shaft Load Limits"),
	FLYWHEELS("Flywheel Load Limits"),
	TRANSFER("Basics of Power Transfer"),
	TIERS("Machine Tiers"),
	TIMING("Duration Time"),
	MUFFLING("Sounds and Muffling"),
	INTERDIM("Interdimensional Power Transport"),
	ALERTS("Config Alerts"),
	PACKMODS("Modpack Changes"),

	//---------------------MISC--------------------//
	INFODESC("Important Notes", "Important Notes"),
	LUBE("Lubricant"),
	BEARINGS("Gearbox Bearings"),
	CANOLA("Canola", ItemRegistry.CANOLA),
	METER("Angular Transducer", ItemRegistry.METER),
	SCREW("Screwdriver", ItemRegistry.SCREWDRIVER),
	ENCHANTING("Enchanting Machines"),
	MODINTERFACE("Inter-Mod Interactions"),
	COMPUTERCRAFT("ComputerCraft"),

	//---------------------ENGINES--------------------//
	ENGINEDESC("Power Supply", "Engines"),
	DCENGINE(MachineRegistry.ENGINE, EngineType.DC),
	WINDENGINE(MachineRegistry.ENGINE, EngineType.WIND),
	STEAMENGINE(MachineRegistry.ENGINE, EngineType.STEAM),
	GASENGINE(MachineRegistry.ENGINE, EngineType.GAS),
	ACENGINE(MachineRegistry.ENGINE, EngineType.AC),
	PERFENGINE(MachineRegistry.ENGINE, EngineType.SPORT),
	HYDROENGINE(MachineRegistry.ENGINE, EngineType.HYDRO),
	MICROTURB(MachineRegistry.ENGINE, EngineType.MICRO),
	JETENGINE(MachineRegistry.ENGINE, EngineType.JET),
	SOLAR(MachineRegistry.SOLARTOWER),

	//---------------------TRANSMISSION--------------------//
	TRANSDESC("Power Transfer", "Transmission"),
	SHAFT(MachineRegistry.SHAFT),
	GEARBOX(MachineRegistry.GEARBOX),
	BEVEL(MachineRegistry.BEVELGEARS),
	SPLITTER(MachineRegistry.SPLITTER),
	CLUTCH(MachineRegistry.CLUTCH),
	DYNA(MachineRegistry.DYNAMOMETER),
	FLYWHEEL(MachineRegistry.FLYWHEEL),
	WORM(MachineRegistry.ADVANCEDGEARS, 0),
	CVT(MachineRegistry.ADVANCEDGEARS, 1),
	COIL(MachineRegistry.ADVANCEDGEARS, 2),
	HIGEAR(MachineRegistry.ADVANCEDGEARS, 3),
	MULTI(MachineRegistry.MULTICLUTCH),
	BELT(MachineRegistry.BELT),
	BUSCONTROLLER(MachineRegistry.BUSCONTROLLER),
	BUS(MachineRegistry.POWERBUS),
	CHAIN(MachineRegistry.CHAIN),
	DISTRIBCLUTCH(MachineRegistry.DISTRIBCLUTCH),

	//---------------------CONVERTER--------------------//
	CONVERTERDESC("Conversion Engines", "Converters"),
	STATIC(MachineRegistry.DYNAMO), //Rotational Dynamo
	GENERATOR(MachineRegistry.GENERATOR),
	BOILER(MachineRegistry.BOILER),
	AIRCOMPRESSOR(MachineRegistry.COMPRESSOR),
	DYNAMO(MachineRegistry.MAGNETIC), //Magnetostatic
	MOTOR(MachineRegistry.ELECTRICMOTOR),
	STEAMTURB(MachineRegistry.STEAMTURBINE),
	PNEUMATIC(MachineRegistry.PNEUENGINE),
	FUELENGINE(MachineRegistry.FUELENGINE),

	//---------------------MACHINES--------------------//
	PRODMACHINEDESC("Production Machines", "Production"),
	BLAST(MachineRegistry.BLASTFURNACE),
	WORKTABLE(MachineRegistry.WORKTABLE),
	FERMENTER(MachineRegistry.FERMENTER),
	FRACTION(MachineRegistry.FRACTIONATOR),
	BEDROCK(MachineRegistry.BEDROCKBREAKER),
	BORER(MachineRegistry.BORER),
	PUMP(MachineRegistry.PUMP),
	OBSIDIAN(MachineRegistry.OBSIDIAN),
	LAVAMAKER(MachineRegistry.LAVAMAKER),
	AGGREGATOR(MachineRegistry.AGGREGATOR),
	FRIDGE(MachineRegistry.REFRIGERATOR),

	PROCMACHINEDESC("Processing Machines", "Processing"),
	GRINDER(MachineRegistry.GRINDER),
	EXTRACTOR(MachineRegistry.EXTRACTOR),
	PULSEJET(MachineRegistry.PULSEJET),
	COMPACTOR(MachineRegistry.COMPACTOR),
	PURIFIER(MachineRegistry.PURIFIER),
	ENHANCER(MachineRegistry.FUELENHANCER),
	MAGNET(MachineRegistry.MAGNETIZER),
	DISTILER(MachineRegistry.DISTILLER),
	FURNACE(MachineRegistry.BIGFURNACE),
	CRYSTAL(MachineRegistry.CRYSTALLIZER),
	COMPOST(MachineRegistry.COMPOSTER),
	CENTRIFUGE(MachineRegistry.CENTRIFUGE),
	DRYING(MachineRegistry.DRYING),
	WETTER(MachineRegistry.WETTER),
	DROPS(MachineRegistry.DROPS),

	FARMMACHINEDESC("Farming Machines", "Farming"),
	FAN(MachineRegistry.FAN),
	BREEDER(MachineRegistry.AUTOBREEDER),
	BAITBOX(MachineRegistry.BAITBOX),
	SPAWNER(MachineRegistry.SPAWNERCONTROLLER),
	SPRINKLER(MachineRegistry.SPRINKLER),
	WOODCUTTER(MachineRegistry.WOODCUTTER),
	HARVESTER(MachineRegistry.MOBHARVESTER),
	FERTILIZER(MachineRegistry.FERTILIZER),
	LAWNSPRINKLER(MachineRegistry.LAWNSPRINKLER),
	HYDRATOR(MachineRegistry.HYDRATOR),

	PIPEMACHINEDESC("Piping", "Piping"),
	HOSE(MachineRegistry.HOSE.getName(), MachineRegistry.HOSE),
	PIPE(MachineRegistry.PIPE.getName(), MachineRegistry.PIPE),
	FUELLINE(MachineRegistry.FUELLINE.getName(), MachineRegistry.FUELLINE),
	VALVE(MachineRegistry.VALVE.getName(), MachineRegistry.VALVE),
	BYPASS(MachineRegistry.BYPASS.getName(), MachineRegistry.BYPASS),
	SEPARATOR(MachineRegistry.SEPARATION.getName(), MachineRegistry.SEPARATION),
	SUCTION(MachineRegistry.SUCTION.getName(), MachineRegistry.SUCTION),
	BEDPIPE(MachineRegistry.BEDPIPE.getName(), MachineRegistry.BEDPIPE),

	ACCMACHINEDESC("Accessory Machines", "Aux Machines"),
	FURNACEHEATER(MachineRegistry.FRICTION),
	HEATER(MachineRegistry.HEATER),
	VACUUM(MachineRegistry.VACUUM),
	ECU(MachineRegistry.ECU),
	WINDER(MachineRegistry.WINDER),
	REFRESHER(MachineRegistry.REFRESHER),
	CCTVSCREEN(MachineRegistry.SCREEN),
	MIRROR(MachineRegistry.MIRROR),
	COOLINGFIN(MachineRegistry.COOLINGFIN),
	SORTING(MachineRegistry.SORTING),
	FILLING(MachineRegistry.FILLINGSTATION),
	PIPEPUMP(MachineRegistry.PIPEPUMP),
	ITEMFILTER(MachineRegistry.ITEMFILTER),

	WEPMACHINEDESC("Defence/Offence Machines", "Defence/Offense"),
	ARROWGUN(MachineRegistry.ARROWGUN),
	HEATRAY(MachineRegistry.HEATRAY),
	TNT(MachineRegistry.TNTCANNON),
	SONIC(MachineRegistry.SONICWEAPON),
	FORCE(MachineRegistry.FORCEFIELD),
	ANTIAIR(MachineRegistry.ANTIAIR),
	RAILGUN(MachineRegistry.RAILGUN),
	FREEZE(MachineRegistry.FREEZEGUN),
	CONTAIN(MachineRegistry.CONTAINMENT),
	LASERGUN(MachineRegistry.LASERGUN),
	LANDMINE(MachineRegistry.LANDMINE),
	BLOCKCANNON(MachineRegistry.BLOCKCANNON),
	SELFDESTRUCT(MachineRegistry.SELFDESTRUCT),
	EMP(MachineRegistry.EMP),
	AIRGUN(MachineRegistry.AIRGUN),
	VDG(MachineRegistry.VANDEGRAFF),
	GATLING(MachineRegistry.GATLING),
	FLAMETURRET(MachineRegistry.FLAMETURRET),

	SURVMACHINEDESC("Surveying Machines", "Surveying"),
	GPR(MachineRegistry.GPR),
	RADAR(MachineRegistry.MOBRADAR),
	SCANNER(MachineRegistry.CAVESCANNER),
	CCTV(MachineRegistry.CCTV),
	SPYCAM(MachineRegistry.SPYCAM),

	COSMACHINEDESC("Cosmetic Machines", "Cosmetic"),
	FIREWORK(MachineRegistry.FIREWORK),
	MUSIC(MachineRegistry.MUSICBOX),
	PROJECTOR(MachineRegistry.PROJECTOR),
	WEATHER(MachineRegistry.WEATHERCONTROLLER),
	DISPLAY(MachineRegistry.DISPLAY),
	PARTICLE(MachineRegistry.PARTICLE),

	UTILMACHINEDESC("Utility Machines", "Utility"),
	FLOODLIGHT(MachineRegistry.FLOODLIGHT),
	PILEDRIVER(MachineRegistry.PILEDRIVER),
	AEROSOL(MachineRegistry.AEROSOLIZER),
	LIGHTBRID(MachineRegistry.LIGHTBRIDGE),
	RESERVOIR(MachineRegistry.RESERVOIR),
	DETECTOR(MachineRegistry.PLAYERDETECTOR),
	CHEST(MachineRegistry.SCALECHEST),
	SPILLER(MachineRegistry.SPILLER),
	SMOKE(MachineRegistry.SMOKEDETECTOR),
	FIRESTARTER(MachineRegistry.IGNITER),
	ITEMCANNON(MachineRegistry.ITEMCANNON),
	BUCKETFILLER(MachineRegistry.BUCKETFILLER),
	LAMP(MachineRegistry.LAMP),
	TERRA(MachineRegistry.TERRAFORMER),
	LINE(MachineRegistry.LINEBUILDER),
	BEAMMIRROR(MachineRegistry.BEAMMIRROR),
	SONICBORER(MachineRegistry.SONICBORER),
	DEFOLIATOR(MachineRegistry.DEFOLIATOR),
	GRINDSTONE(MachineRegistry.GRINDSTONE),
	BLOWER(MachineRegistry.BLOWER),
	GASTANK(MachineRegistry.GASTANK),
	CRAFTER(MachineRegistry.CRAFTER),
	CHUNKLOADER(MachineRegistry.CHUNKLOADER),
	FILLER(MachineRegistry.FILLER),
	SPILLWAY(MachineRegistry.SPILLWAY),
	BUNDLEDBUS(MachineRegistry.BUNDLEDBUS),

	//---------------------TOOLS--------------------//
	TOOLDESC("Tool Items", "Tools"),
	SPRING(ItemRegistry.SPRING),
	STRONGSPRING(ItemRegistry.STRONGCOIL),
	ULTRA(ItemRegistry.ULTRASOUND),
	MOTION(ItemRegistry.MOTION),
	VAC(ItemRegistry.VACUUM),
	STUN(ItemRegistry.STUNGUN),
	GRAVEL(ItemRegistry.GRAVELGUN),
	FIREBALL(ItemRegistry.FIREBALL),
	HANDCRAFT(ItemRegistry.HANDCRAFT),
	NVG(ItemRegistry.NVG),
	BUCKETS(ItemRegistry.BUCKET),
	BEDTOOLS("Bedrock Tools"),
	BEDARMOR("Bedrock Armor"),
	TARGETER(ItemRegistry.TARGET),
	IOGOGGLES(ItemRegistry.IOGOGGLES),
	CKEY(ItemRegistry.KEY),
	MINECART(ItemRegistry.MINECART),
	TILESELECT(ItemRegistry.TILESELECTOR),
	JETPACK("Jetpacks"),
	STEELTOOLS("Steel Tools"),
	STEELARMOR("Steel Armor"),
	ITEMPUMP(ItemRegistry.PUMP),
	JUMPBOOTS("Spring Boots"),
	TANKS(ItemRegistry.FUEL),
	DISK(ItemRegistry.DISK),

	//---------------------RESOURCE--------------------//
	RESOURCEDESC("Resource Items", "Resource Items"),
	STEELINGOT("Steel Ingot"),
	COKE("Coal Coke"),
	NETHERDUST("Netherrack Dust and Tar"),
	SAWDUST("Sawdust"),
	BEDDUST("Bedrock Dust"),
	EXTRACTS("Dust/Slurry/Solution"),
	FLAKES("Ore Flakes"),
	COMPACTS("Anthracite/Prismane/Lonsdaleite"),
	DECOBLOCKS("Decorative Blocks"),
	GLASS("Blast Glass"),
	SPAWNERS("Monster Spawners"),
	YEAST("Yeast"),
	ETHANOL("Ethanol"),
	//BEDINGOT("Bedrock Alloy Ingot"),
	ALLOYING("Alloying"),
	SILVERINGOT("Silver Ingot"),
	SALT("Salt"),
	AMMONIUM("Ammonium Nitrate"),
	SILVERIODIDE("Silver Iodide"),
	ALUMINUM("Aluminum Powder"),
	RAILGUNAMMO("Railgun Ammunition"),
	SLIDES("Projector Slides"),
	EXPLOSIVES("Explosive Shells"),
	TUNGSTEN("Tungsten");

	private MachineRegistry machine;
	private ItemRegistry item;
	private int offset;
	private int parentindex;
	private int basescreen;
	private boolean isParent;
	private String title;
	private ItemStack crafted;
	private String name;
	private EngineType engine;

	public static final HandbookRegistry[] tabList = HandbookRegistry.values();

	private HandbookRegistry(MachineRegistry m, int o) {
		machine = m;
		offset = o;
		isParent = false;
		item = null;
		crafted = m.getCraftedMetadataProduct(offset);
	}

	private HandbookRegistry(MachineRegistry m, EngineType o) {
		machine = m;
		engine = o;
		offset = o != null ? o.ordinal() : -1;
		isParent = false;
		item = null;
		crafted = offset >= 0 ? m.getCraftedMetadataProduct(offset) : m.getCraftedProduct();
	}

	private HandbookRegistry(MachineRegistry m) {
		machine = m;
		offset = -1;
		isParent = false;
		item = null;
		crafted = m.getCraftedProduct();
	}

	private HandbookRegistry(ItemRegistry i) {
		offset = -1;
		isParent = false;
		item = i;
		crafted = i.getStackOf();
	}

	private HandbookRegistry(String s) {
		machine = null;
		offset = -1;
		isParent = false;
		item = null;
		title = s;
		crafted = null;
	}

	private HandbookRegistry(ItemStack is) {
		machine = null;
		offset = -1;
		isParent = false;
		crafted = is.copy();
	}

	private HandbookRegistry(String s, MachineRegistry m) {
		machine = m;
		offset = -1;
		isParent = false;
		item = null;
		title = s;
		crafted = m.getCraftedProduct();
	}

	private HandbookRegistry(String s, ItemRegistry m) {
		machine = null;
		offset = -1;
		isParent = false;
		item = m;
		title = s;
		crafted = m.getStackOf();
	}

	private HandbookRegistry() {
		machine = null;
		offset = -1;
		isParent = false;
		item = null;
	}

	private HandbookRegistry(String s, String toc) {
		machine = null;
		offset = -1;
		parentindex = RotaryDescriptions.getCategoryCount();
		isParent = true;
		item = null;
		title = s;
		crafted = null;
		name = toc;
		RotaryDescriptions.addCategory(this);
	}

	public static int getScreen(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEngineScreen(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return TRANSDESC.getBaseScreen()+1;
		for (int i = ENGINEDESC.ordinal(); i < TOOLDESC.ordinal(); i++) {
			if (tabList[i].machine == m)
				return tabList[i].getScreen();
		}
		return -1;
	}

	public static List<HandbookRegistry> getEngineTabs() {
		ArrayList<HandbookRegistry> li = getSubList(ENGINEDESC);
		Collections.sort(li, tabSorter);
		return li;
	}

	public static List<HandbookRegistry> getTransTabs() {
		ArrayList<HandbookRegistry> li = getSubList(TRANSDESC);
		Collections.sort(li, tabSorter);
		return li;
	}

	public static List<HandbookRegistry> getConverterTabs() {
		ArrayList<HandbookRegistry> li = getSubList(CONVERTERDESC);
		Collections.sort(li, tabSorter);
		return li;
	}

	public static List<HandbookRegistry> getMachineTabs() {
		List<HandbookRegistry> tabs = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			HandbookRegistry h = tabList[i];
			if (h.isMachine() && !h.isParent)
				tabs.add(h);
		}
		return tabs;
	}

	public static List<HandbookRegistry> getToolTabs() {
		ArrayList<HandbookRegistry> li = getSubList(TOOLDESC);
		return li;
	}

	public static List<HandbookRegistry> getResourceTabs() {
		ArrayList<HandbookRegistry> li = getSubList(RESOURCEDESC);
		return li;
	}

	public static List<HandbookRegistry> getMiscTabs() {
		ArrayList<HandbookRegistry> li = getSubList(INFODESC);
		return li;
	}

	public static List<HandbookRegistry> getInfoTabs() {
		ArrayList<HandbookRegistry> li = getSubList(TERMS);
		li.add(0, TERMS);
		return li;
	}

	private static ArrayList<HandbookRegistry> getSubList(HandbookRegistry parent) {
		ArrayList<HandbookRegistry> li = new ArrayList();
		for (int i = parent.ordinal(); i < tabList.length; i++) {
			HandbookRegistry h = tabList[i];
			if (h != parent && h.isParent)
				break;
			if (!h.isParent)
				li.add(h);
		}
		return li;
	}

	public static List<HandbookRegistry> getCategoryTabs(boolean info) {
		ArrayList<HandbookRegistry> li = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].isParent && tabList[i] != TOC && (info || tabList[i] != TERMS))
				li.add(tabList[i]);
		}
		return li;
	}

	public static List<HandbookRegistry> getTOCTabs() {
		ArrayList<HandbookRegistry> li = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].isParent && tabList[i] != TOC)
				li.add(tabList[i]);
		}
		return li;
	}

	public String getTOCTitle() {
		return name;
	}

	public EngineType getEngine() {
		return engine;
	}

	public boolean isMachine() {
		if (this.getParent() == PROCMACHINEDESC)
			return true;
		if (this.getParent() == UTILMACHINEDESC)
			return true;
		if (this.getParent() == WEPMACHINEDESC)
			return true;
		if (this.getParent() == SURVMACHINEDESC)
			return true;
		if (this.getParent() == PRODMACHINEDESC)
			return true;
		if (this.getParent() == FARMMACHINEDESC)
			return true;
		if (this.getParent() == COSMACHINEDESC)
			return true;
		if (this.getParent() == ACCMACHINEDESC)
			return true;
		if (this.getParent() == PIPEMACHINEDESC)
			return true;
		//if (this.getParent() == CONVERTERDESC)
		//	return true;
		return false;
	}

	public boolean isEngine() {
		return (this.getParent() == ENGINEDESC);
	}

	public boolean isTrans() {
		return (this.getParent() == TRANSDESC);
	}

	public MachineRegistry getMachine() {
		return machine;
	}

	public ItemRegistry getItem() {
		return item;
	}

	public int getBaseScreen() {
		int sc = 0;
		for (int i = 0; i < this.ordinal(); i++) {
			HandbookRegistry h = tabList[i];
			if (h.isParent) {
				sc += h.getNumberChildren()/GuiHandbook.PAGES_PER_SCREEN+1;
			}
		}
		return sc;
	}

	public int getNumberChildren() {
		if (!isParent)
			return 0;
		int ch = 0;
		for (int i = this.ordinal()+1; i < tabList.length; i++) {
			HandbookRegistry h = tabList[i];
			if (h.isParent) {
				return ch;
			}
			else {
				ch++;
			}
		}
		return ch;
	}

	public int getScreen() {
		return this.getParent().getBaseScreen()+this.getRelativeScreen();
	}

	public int getPage() {
		return (this.ordinal()-this.getParent().ordinal())%GuiHandbook.PAGES_PER_SCREEN;
	}

	public boolean hasOffset() {
		return offset > -1;
	}

	public int getOffset() {
		return offset;
	}

	public static int getPage(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEnginePage(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return getAdvGearPage(te);
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].machine == m)
				return tabList[i].getPage();
		}
		return -1;
	}

	public int getShiftedOrdinal() {
		return this.getParent().getBaseScreen()+this.ordinal()-this.getParent().ordinal();
	}

	private static int getAdvGearPage(TileEntity te) {
		return ((RotaryCraftTileEntity)te).getBlockMetadata()/4;
	}

	private static int getEnginePage(TileEntity te) {
		EngineType e = ((TileEntityEngine)te).getEngineType();
		return 1+e.ordinal()-(getEngineScreen(te)-ENGINEDESC.getBaseScreen())*GuiHandbook.PAGES_PER_SCREEN;
	}

	private static int getEngineScreen(TileEntity te) {
		EngineType e = ((TileEntityEngine)te).getEngineType();
		int ei = (1+e.ordinal())/GuiHandbook.PAGES_PER_SCREEN;
		return ENGINEDESC.getBaseScreen()+ei;
	}

	public String getTabImageFile() {
		//return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+this.getParent().name().toLowerCase()+".png";
		return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+TOC.getParent().name().toLowerCase(Locale.ENGLISH)+".png";
	}

	public int getTabRow() {
		return (this.getRelativePage()/12)*20;
	}

	public int getTabColumn() {
		return (this.getRelativePage()%12)*20;
	}

	public int getRelativePage() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset;
	}

	public int getRelativeTabPosn() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset-this.getRelativeScreen()*GuiHandbook.PAGES_PER_SCREEN;
	}

	public int getRelativeScreen() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset/GuiHandbook.PAGES_PER_SCREEN;
	}

	public HandbookRegistry getParent() {
		HandbookRegistry parent = null;
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].isParent) {
				if (this.ordinal() >= tabList[i].ordinal()) {
					parent = tabList[i];
				}
			}
		}
		//ReikaJavaLibrary.pConsole("Setting parent for "+this+" to "+parent);
		return parent;
	}

	public static void addRelevantButtons(int j, int k, int screen, List<GuiButton> li) {
		int id = 0;
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen/* && !tabList[i].isDummiedOut()*/) {
				li.add(new ImagedGuiButton(id, j-20, k+tabList[i].getRelativeTabPosn()*20, 20, 20, 0, 0, tabList[i].getTabImageFile(), RotaryCraft.class));
				//ReikaJavaLibrary.pConsole("Adding "+tabList[i]+" with ID "+id+" to screen "+screen);
				id++;
			}
		}
	}

	public static HandbookRegistry getEntry(int screen, int page) {
		//ReikaJavaLibrary.pConsole(screen+"   "+page);
		if (screen < TERMS.getScreen())
			return TOC;
		HandbookRegistry h = HandbookAuxData.getMapping(screen, page);
		return h != null ? h : TOC;
		//throw new RuntimeException("Handbook screen "+screen+" and page "+page+" do not correspond to an entry!");
	}

	public static List<HandbookRegistry> getEntriesForScreen(int screen) {
		//ReikaJavaLibrary.pConsole(screen);
		List<HandbookRegistry> li = new ArrayList();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen/* && !tabList[i].isDummiedOut()*/) {
				li.add(tabList[i]);
			}
		}
		return li;
	}

	public boolean isPlainGui() {
		//ReikaJavaLibrary.pConsole(this);
		if (this == TOC)
			return true;
		if (this == TIERS)
			return false;
		if (this == TIMING)
			return false;
		if (this == ALERTS)
			return false;
		if (this == PACKMODS)
			return false;
		if (this.getParent() == TERMS)
			return true;
		if (isParent)
			return true;
		if (this == MODINTERFACE)
			return true;
		if (this == ENCHANTING)
			return true;
		if (this == BEDDUST)
			return true;
		if (this == SPAWNERS)
			return true;
		if (this == LUBE)
			return true;
		if (this == BEARINGS)
			return true;
		if (this == CANOLA)
			return true;
		if (this == ALUMINUM)
			return true;
		if (this == COMPUTERCRAFT)
			return true;
		return false;
	}

	public String getTitle() {
		if (this == TOC)
			return "Table Of Contents";
		if (isParent)
			return title;
		if (this.getParent() == ENGINEDESC) {
			if (this == SOLAR)
				return MachineRegistry.SOLARTOWER.getName();
			else
				return RotaryNames.getEngineName(offset);
		}
		if (this.isMachine() || this.getParent() == CONVERTERDESC)
			return machine.getName();
		if (machine == MachineRegistry.ADVANCEDGEARS)
			return RotaryNames.getAdvGearName(offset);
		if (this.getParent() == TRANSDESC)
			return machine.getName();
		if (this.getParent() == TOOLDESC && item != null)
			return item.getBasicName();
		return title;
	}

	public boolean isCrafting() {
		if (isParent)
			return false;
		if (this.isSmelting())
			return false;
		if (this.getParent() == TOC || this.getParent() == TERMS)
			return false;
		if (this == MODINTERFACE)
			return false;
		if (this == COMPUTERCRAFT)
			return false;
		if (this == ENCHANTING)
			return false;
		if (this == LUBE)
			return false;
		if (this == BEARINGS)
			return false;
		if (this == STEELINGOT)
			return false;
		if (this == NETHERDUST)
			return false;
		if (this == GLASS)
			return false;
		if (this == EXTRACTS)
			return false;
		if (this == COMPACTS)
			return false;
		if (this == BEDDUST)
			return false;
		if (this == SAWDUST)
			return false;
		if (this == SPAWNERS)
			return false;
		if (this == YEAST)
			return false;
		if (this == ALUMINUM)
			return false;
		if (this == RAILGUNAMMO)
			return false;
		if (this == CANOLA)
			return false;
		if (this == FLAKES)
			return false;
		if (this == SILVERINGOT)
			return false;
		if (this == JETPACK)
			return false;
		if (this == JUMPBOOTS)
			return false;
		if (this == BEDTOOLS)
			return false;
		if (this == BEDARMOR)
			return false;
		if (this == STRONGSPRING)
			return false;
		//if (this == BEDINGOT)
		//	return false;
		if (this == ALLOYING)
			return false;
		if (this == COKE)
			return false;
		return true;
	}

	public List<ItemStack> getCrafting() {
		if (!this.isCrafting())
			return null;
		if (this == SHAFT) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < MaterialRegistry.values().length; i++) {
				li.add(MachineRegistry.SHAFT.getCraftedMetadataProduct(i));
			}
			return li;
		}
		if (this == GEARBOX) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < MaterialRegistry.values().length; i++) {
				li.add(MachineRegistry.GEARBOX.getCraftedMetadataProduct(i));
			}
			return li;
		}
		if (this == COIL) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			ItemStack is = MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(2);
			li.add(is);
			is = is.copy();
			is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setBoolean("bedrock", true);
			li.add(is);
			return li;
		}
		if (this == FLYWHEEL) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < 4; i++) {
				li.add(MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(i));
			}
			return li;
		}
		if (crafted != null)
			return ReikaJavaLibrary.makeListFrom(crafted);
		if (machine != null && machine.isPipe())
			return ReikaJavaLibrary.makeListFrom(machine.getCraftedProduct());
		if (this == BEDTOOLS) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemRegistry.BEDPICK.getStackOf());
			li.add(ItemRegistry.BEDSHOVEL.getStackOf());
			li.add(ItemRegistry.BEDAXE.getStackOf());
			li.add(ItemRegistry.BEDSWORD.getStackOf());
			li.add(ItemRegistry.BEDHOE.getStackOf());
			li.add(ItemRegistry.BEDSHEARS.getStackOf());
			li.add(ItemRegistry.BEDSICKLE.getStackOf());
			return li;
		}
		if (this == BEDARMOR) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemRegistry.BEDHELM.getStackOf());
			li.add(ItemRegistry.BEDCHEST.getStackOf());
			li.add(ItemRegistry.BEDLEGS.getStackOf());
			li.add(ItemRegistry.BEDBOOTS.getStackOf());
			return li;
		}
		if (this == STEELTOOLS) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemRegistry.STEELPICK.getStackOf());
			li.add(ItemRegistry.STEELSHOVEL.getStackOf());
			li.add(ItemRegistry.STEELAXE.getStackOf());
			li.add(ItemRegistry.STEELSWORD.getStackOf());
			li.add(ItemRegistry.STEELHOE.getStackOf());
			li.add(ItemRegistry.STEELSHEARS.getStackOf());
			li.add(ItemRegistry.STEELSICKLE.getStackOf());
			return li;
		}
		if (this == STEELARMOR) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemRegistry.STEELHELMET.getStackOf());
			li.add(ItemRegistry.STEELCHEST.getStackOf());
			li.add(ItemRegistry.STEELLEGS.getStackOf());
			li.add(ItemRegistry.STEELBOOTS.getStackOf());
			return li;
		}
		if (this == SOLAR)
			return ReikaJavaLibrary.makeListFrom(MachineRegistry.SOLARTOWER.getCraftedProduct());
		if (this.getParent() == ENGINEDESC)
			return ReikaJavaLibrary.makeListFrom(EngineType.engineList[offset].getCraftedProduct());
		if (machine == MachineRegistry.ADVANCEDGEARS)
			return ReikaJavaLibrary.makeListFrom(MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(offset));
		if (this.getParent() == TRANSDESC || this.isMachine()) {
			if (machine.hasCustomPlacerItem())
				return ReikaJavaLibrary.makeListFrom(machine.getCraftedMetadataProduct(0));
			return ReikaJavaLibrary.makeListFrom(machine.getCraftedProduct());
		}
		if (this == DECOBLOCKS) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.steelblock);
			li.add(ItemStacks.anthrablock);
			li.add(ItemStacks.lonsblock);
			return li;
		}
		if (this == SLIDES) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < ItemRegistry.SLIDE.getNumberMetadatas(); i++)
				li.add(ItemRegistry.SLIDE.getStackOfMetadata(i));
			return li;
		}
		//if (this == BEDINGOT)
		//	return ReikaJavaLibrary.makeListFrom(ItemStacks.bedingot);
		if (this == ALLOYING) {
			ArrayList<ItemStack> is = new ArrayList();
			ArrayList<BlastFurnacePattern> li = RecipesBlastFurnace.getRecipes().getAllAlloyingRecipes();
			for (BlastFurnacePattern p : li) {
				is.add(p.outputItem());
			}
			return is;
		}
		if (this == COKE)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.coke);
		if (this == AMMONIUM)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.nitrate);
		if (this == SALT)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.salt);
		if (this == SILVERIODIDE)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.silveriodide);
		if (this == EXPLOSIVES)
			return ReikaJavaLibrary.makeListFrom(ItemRegistry.SHELL.getStackOf());
		if (this == MINECART)
			return ReikaJavaLibrary.makeListFrom(ItemRegistry.MINECART.getStackOf());
		return ReikaJavaLibrary.makeListFrom(ItemStacks.basepanel);
	}

	public boolean isCustomRecipe() {
		if (this.getParent() == ENGINEDESC)
			return true;
		if (this.getParent() == TRANSDESC)
			return true;
		if (this.getParent() == CONVERTERDESC)
			return true;
		if (this.isMachine())
			return true;
		if (machine != null && machine.isPipe())
			return true;
		return false;
	}

	public int getTabIconIndex() {
		if (this == SHAFT)
			return MaterialRegistry.STEEL.ordinal();
		if (this == GEARBOX)
			return MaterialRegistry.STEEL.ordinal();
		if (this == FLYWHEEL)
			return 3;
		if (this == DECOBLOCKS)
			return 1;
		return 0;
	}

	public boolean isSmelting() {
		if (this == ETHANOL)
			return true;
		if (this == TUNGSTEN)
			return true;
		return false;
	}

	public ItemStack getSmelting() {
		if (!this.isSmelting())
			return null;
		if (this == ETHANOL)
			return ItemRegistry.ETHANOL.getStackOf();
		if (this == TUNGSTEN)
			return ItemStacks.tungsteningot;
		return ItemStacks.barrel;
	}

	public ItemStack getTabIcon() {
		if (this == TOC)
			return ItemRegistry.HANDBOOK.getStackOf();
		if (this == TERMS)
			return ItemRegistry.HANDBOOK.getStackOf();
		if (this == PHYSICS)
			return new ItemStack(Items.book);
		if (this == MATERIAL)
			return ItemStacks.steelingot;
		if (this == SHAFTS)
			return MachineRegistry.SHAFT.getCraftedMetadataProduct(3);
		if (this == FLYWHEELS)
			return MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(0);
		if (this == TIERS)
			return MachineRegistry.EMP.getCraftedProduct();
		if (this == LUBE)
			return ItemStacks.lubebucket;
		if (this == BEARINGS)
			return GearboxTypes.TUNGSTEN.getPart(GearPart.BEARING);
		if (this == MODINTERFACE)
			return MachineRegistry.COMPRESSOR.getCraftedProduct();
		if (this == ENCHANTING)
			return new ItemStack(Items.enchanted_book);
		if (this == TIMING)
			return new ItemStack(Items.clock);
		if (this == MUFFLING)
			return ReikaItemHelper.whiteWool.asItemStack();
		if (this == INTERDIM)
			return new ItemStack(Blocks.portal);
		if (this == COMPUTERCRAFT)
			return ItemStacks.pcb;
		if (this == TRANSFER)
			return ItemStacks.gearunit;
		if (this == ENGINES)
			return EngineType.AC.getCraftedProduct();
		if (this == INFO)
			return ItemRegistry.SCREWDRIVER.getStackOf();
		if (this == TRANS)
			return GearboxTypes.STEEL.getGearboxItem(8);
		if (this == CONVERTER)
			return MachineRegistry.MAGNETIC.getCraftedProduct();
		if (this == PRODMACHINES)
			return MachineRegistry.BEDROCKBREAKER.getCraftedProduct();
		if (this == PROCMACHINES)
			return MachineRegistry.EXTRACTOR.getCraftedProduct();
		if (this == FARMMACHINES)
			return MachineRegistry.FAN.getCraftedProduct();
		if (this == PIPEMACHINES)
			return MachineRegistry.FUELLINE.getCraftedProduct();
		if (this == ACCMACHINES)
			return MachineRegistry.FRICTION.getCraftedProduct();
		if (this == WEPMACHINES)
			return MachineRegistry.RAILGUN.getCraftedProduct();
		if (this == COSMACHINES)
			return MachineRegistry.MUSICBOX.getCraftedProduct();
		if (this == SURVMACHINES)
			return MachineRegistry.GPR.getCraftedProduct();
		if (this == UTILMACHINES)
			return MachineRegistry.FLOODLIGHT.getCraftedProduct();
		if (this == TOOLS)
			return ItemRegistry.MOTION.getStackOf();
		if (this == RESOURCE)
			return ItemStacks.bedrockdust;
		if (this == FLAKES)
			return ItemStacks.goldoreflakes;
		if (this == BEDTOOLS)
			return ItemRegistry.BEDPICK.getEnchantedStack();
		if (this == BEDARMOR)
			return ItemRegistry.BEDHELM.getEnchantedStack();
		if (this == STEELTOOLS)
			return ItemRegistry.STEELPICK.getEnchantedStack();
		if (this == STEELARMOR)
			return ItemRegistry.STEELHELMET.getEnchantedStack();
		if (this == JETPACK)
			return ItemRegistry.BEDPACK.getEnchantedStack();
		if (this == JUMPBOOTS)
			return ItemRegistry.JUMP.getStackOf();
		if (this.isCrafting())
			return this.getCrafting().get(this.getTabIconIndex());
		if (this.isSmelting())
			return this.getSmelting();
		if (this == STEELINGOT)
			return ItemStacks.steelingot;
		if (this == NETHERDUST)
			return ItemStacks.netherrackdust;
		if (this == SAWDUST)
			return ItemStacks.sawdust;
		if (this == BEDDUST)
			return ItemStacks.bedrockdust;
		if (this == EXTRACTS)
			return ItemStacks.goldoredust;
		if (this == COMPACTS)
			return ItemStacks.prismane;
		if (this == GLASS)
			return BlockRegistry.BLASTGLASS.getStackOf();
		if (this == SPAWNERS)
			return ItemRegistry.SPAWNER.getStackOf();
		if (this == YEAST)
			return ItemStacks.sludge;
		if (this == SALT)
			return ItemStacks.salt;
		if (this == ALUMINUM)
			return ItemStacks.aluminumpowder;
		if (this == RAILGUNAMMO)
			return ItemRegistry.RAILGUN.getStackOf();
		if (this == CANOLA)
			return ItemStacks.canolaSeeds;
		if (this == SILVERINGOT)
			return ItemStacks.silveringot;
		if (this == STRONGSPRING)
			return ItemRegistry.STRONGCOIL.getStackOf();
		//if (this == BEDINGOT)
		//	return ItemStacks.bedingot;
		if (this == ALLOYING)
			return ItemStacks.bedingot;
		if (this == COKE)
			return ItemStacks.coke;
		if (this == ALERTS || this == PACKMODS)
			;//return ItemStacks.steelgear;
		return null;
	}

	public String getData() {
		if (this == TOC)
			return RotaryDescriptions.getTOC();
		if (this == ALERTS || this == PACKMODS)
			return "";
		return RotaryDescriptions.getData(this);
	}

	public String getNotes(int subpage) {
		return RotaryDescriptions.getNotes(this, subpage);
	}

	public boolean hasSubpages() {
		if (isParent)
			return false;
		if (this.getParent() == ENGINEDESC)
			return true;
		if (this.getParent() == CONVERTERDESC)
			return true;
		if (this.isMachine())
			return true;
		if (this == TIERS)
			return true;
		if (this == COMPUTERCRAFT)
			return true;
		if (this == ENCHANTING)
			return true;
		if (this == ALERTS)
			return !HandbookNotifications.instance.getNewAlerts().isEmpty();
		if (this == PACKMODS)
			return PackModificationTracker.instance.modificationsExist(RotaryCraft.instance);
		return false;
	}

	public boolean sameTextAllSubpages() {
		if (this == TIERS)
			return true;
		return false;
	}

	@Override
	public boolean hasMachineRender() {
		return this.isEngine() || this.isTrans() || this.isMachine() || this.getParent() == CONVERTERDESC;
	}

	@Override
	public boolean isConfigDisabled() {
		return machine != null && machine.isConfigDisabled();
	}
	/*
	public boolean isDummiedOut() {
		if (this == ALERTS)
			return HandbookNotifications.instance.getNewAlerts().isEmpty();
		if (this == PACKMODS)
			return !PackModificationTracker.instance.modificationsExist(RotaryCraft.instance);
		return false;
	}
	 */
	public int getBonusSubpages() {
		return RotaryDescriptions.getNotesLength(this)-1;
	}

	private static Comparator<HandbookRegistry> tabSorter = new Comparator<HandbookRegistry>() {

		@Override
		public int compare(HandbookRegistry o1, HandbookRegistry o2) {
			if (o1.isParent != o2.isParent) {
				if (o1.isParent) {
					return Integer.MIN_VALUE;
				}
				else {
					return Integer.MAX_VALUE;
				}
			}
			if (o1.machine != null && o2.machine != null) {
				return o1.machine.compareTo(o2.machine);
			}
			else if (o1.machine != null) {
				return Integer.MIN_VALUE;
			}
			else if (o2.machine != null) {
				return Integer.MAX_VALUE;
			}
			else {
				return o1.compareTo(o2);
			}
		}

	};

}
