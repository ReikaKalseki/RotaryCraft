/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public enum HandbookRegistry {

	//---------------------TOC--------------------//
	TOC("Table of Contents", GuiHandbook.TOCSTART),
	MISC(),
	ENGINES(),
	TRANS(),
	MACHINES(),
	TOOLS(),
	CRAFTING(),
	RESOURCE(),
	//---------------------INFO--------------------//
	TERMS("Basic Terms", GuiHandbook.INFOSTART),
	PHYSICS("Relevant Physics"),
	//---------------------MISC--------------------//
	MISCDESC("Important Notes", GuiHandbook.MISCSTART),
	LUBE("Lubricant"),
	HOSE("Lubricant Hose"),
	CANOLA("Canola"),
	METER("Angular Transducer"),
	SCREW("Screwdriver"),
	PIPE("Pipe"),
	FUELLINE("Fuel Line"),
	//---------------------ENGINES--------------------//
	ENGINEDESC("Power Supply", GuiHandbook.ENGINESTART),
	DCENGINE(MachineRegistry.ENGINE, EnumEngineType.DC.ordinal()),
	WINDENGINE(MachineRegistry.ENGINE, EnumEngineType.WIND.ordinal()),
	STEAMENGINE(MachineRegistry.ENGINE, EnumEngineType.STEAM.ordinal()),
	GASENGINE(MachineRegistry.ENGINE, EnumEngineType.GAS.ordinal()),
	ACENGINE(MachineRegistry.ENGINE, EnumEngineType.AC.ordinal()),
	PERFENGINE(MachineRegistry.ENGINE, EnumEngineType.SPORT.ordinal()),
	HYDROENGINE(MachineRegistry.ENGINE, EnumEngineType.HYDRO.ordinal()),
	MICROTURB(MachineRegistry.ENGINE, EnumEngineType.MICRO.ordinal()),
	JETENGINE(MachineRegistry.ENGINE, EnumEngineType.JET.ordinal()),
	SOLAR(MachineRegistry.SOLARTOWER),

	//---------------------TRANSMISSION--------------------//
	TRANSDESC("Power Transfer", GuiHandbook.TRANSSTART),
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

	//---------------------MACHINES--------------------//
	MACHINEDESC("Machines", GuiHandbook.MACHINESTART),
	BEDROCK(MachineRegistry.BEDROCKBREAKER),
	FERMENTER(MachineRegistry.FERMENTER),
	GRINDER(MachineRegistry.GRINDER),
	FLOODLIGHT(MachineRegistry.FLOODLIGHT),
	HEATRAY(MachineRegistry.HEATRAY),
	BORER(MachineRegistry.BORER),
	PILEDRIVER(MachineRegistry.PILEDRIVER),
	AEROSOL(MachineRegistry.AEROSOLIZER),
	LIGHTBRID(MachineRegistry.LIGHTBRIDGE),
	EXTRACTOR(MachineRegistry.EXTRACTOR),
	PULSEJET(MachineRegistry.PULSEJET),
	PUMP(MachineRegistry.PUMP),
	RESERVOIR(MachineRegistry.RESERVOIR),
	FAN(MachineRegistry.FAN),
	COMPACTOR(MachineRegistry.COMPACTOR),
	BREEDER(MachineRegistry.AUTOBREEDER),
	BAITBOX(MachineRegistry.BAITBOX),
	FIREWORK(MachineRegistry.FIREWORK),
	FRACTION(MachineRegistry.FRACTIONATOR),
	GPR(MachineRegistry.GPR),
	HEATER(MachineRegistry.HEATER),
	OBSIDIAN(MachineRegistry.OBSIDIAN),
	DETECTOR(MachineRegistry.PLAYERDETECTOR),
	SPAWNER(MachineRegistry.SPAWNERCONTROLLER),
	SPRINKLER(MachineRegistry.SPRINKLER),
	VACUUM(MachineRegistry.VACUUM),
	WOODCUTTER(MachineRegistry.WOODCUTTER),
	ECU(MachineRegistry.ECU),
	RADAR(MachineRegistry.MOBRADAR),
	WINDER(MachineRegistry.WINDER),
	TNT(MachineRegistry.TNTCANNON),
	SONIC(MachineRegistry.SONICWEAPON),
	BLAST(MachineRegistry.BLASTFURNACE),
	FORCE(MachineRegistry.FORCEFIELD),
	MUSIC(MachineRegistry.MUSICBOX),
	HARVESTER(MachineRegistry.MOBHARVESTER),
	PROJECTOR(MachineRegistry.PROJECTOR),
	RAILGUN(MachineRegistry.RAILGUN),
	WEATHER(MachineRegistry.WEATHERCONTROLLER),
	REFRESHER(MachineRegistry.REFRESHER),
	SCANNER(MachineRegistry.CAVESCANNER),
	CHEST(MachineRegistry.SCALECHEST),
	SPILLER(MachineRegistry.SPILLER),
	SMOKE(MachineRegistry.SMOKEDETECTOR),
	FIRESTARTER(MachineRegistry.IGNITER),
	FREEZE(MachineRegistry.FREEZEGUN),
	MAGNET(MachineRegistry.MAGNETIZER),
	CONTAIN(MachineRegistry.CONTAINMENT),
	CCTVSCREEN(MachineRegistry.SCREEN),
	CCTV(MachineRegistry.CCTV),
	PURIFIER(MachineRegistry.PURIFIER),
	LASERGUN(MachineRegistry.LASERGUN),
	ITEMCANNON(MachineRegistry.ITEMCANNON),
	FURNACEHEATER(MachineRegistry.FRICTION),
	LANDMINE(MachineRegistry.LANDMINE),
	BUCKETFILLER(MachineRegistry.BUCKETFILLER),
	BLOCKCANNON(MachineRegistry.BLOCKCANNON),
	SPYCAM(MachineRegistry.SPYCAM),
	MIRROR(MachineRegistry.MIRROR),
	SELFDESTRUCT(MachineRegistry.SELFDESTRUCT),
	COOLINGFIN(MachineRegistry.COOLINGFIN),
	//---------------------TOOLS--------------------//
	TOOLDESC("Tool Items", GuiHandbook.TOOLSTART),
	SPRING(ItemRegistry.SPRING),
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
	TARGETER(ItemRegistry.TARGET),
	IOGOGGLES(ItemRegistry.IOGOGGLES),
	//---------------------CRAFTING--------------------//
	CRAFTDESC("Crafting Items", GuiHandbook.CRAFTSTART),
	STEELINGOT(),
	PANEL(),
	SHAFTITEM(),
	GEAR(),
	GEARUNIT(),
	MOUNT(),
	SCRAP(),
	IMPELLER(),
	COMPRESSOR(),
	TURBINE(),
	DIFFUSER(),
	COMBUSTOR(),
	RADIATOR(),
	CONDENSER(),
	GOLDCOIL(),
	CYLINDER(),
	PADDLE(),
	CORE(),
	IGNITER(),
	PROP(),
	HUB(),
	BARREL(),
	LENS(),
	POWERMODULE(),
	HEATCORE(),
	DRILL(),
	PRESSHEAD(),
	FLYCORE(),
	RADARITEM(),
	SONAR(),
	PCB(),
	SCREEN(),
	MIXER(),
	SAW(),
	BEARING(),
	BELT(),
	BALLBEARING(),
	BRAKE(),
	WORMITEM(),
	OTHERGEAR(),
	OTHERSHAFT(),
	OTHERGEARUNIT(),
	INDUCTION(),
	TENSCOIL(),
	MIRRORITEM(),
	GENERATOR(),
	RAILHEAD(),
	TURRETBASE(),
	TURRETAIM(),
	//---------------------RESOURCE--------------------//
	RESOURCEDESC("Resource Items", GuiHandbook.RESOURCESTART),
	NETHERDUST(),
	SAWDUST(),
	BEDDUST(),
	EXTRACTS(),
	FLAKES(),
	COMPACTS(),
	DECOBLOCKS(),
	GLASS(),
	SPAWNERS(),
	YEAST(),
	ETHANOL(),
	BEDINGOT(),
	SILVERINGOT(),
	SALT(),
	AMMONIUM(),
	SILVERIODIDE(),
	ALUMINUM(),
	RAILGUNAMMO(),
	SLIDES();

	private MachineRegistry machine;
	private ItemRegistry item;
	private int offset;
	private int basescreen;
	private boolean isParent;
	private String title;

	public static final HandbookRegistry[] tabList = HandbookRegistry.values();

	private HandbookRegistry(MachineRegistry m, int o) {
		machine = m;
		offset = o;
		isParent = false;
		item = null;
	}

	private HandbookRegistry(MachineRegistry m) {
		machine = m;
		offset = -1;
		isParent = false;
		item = null;
	}

	private HandbookRegistry(ItemRegistry i) {
		offset = -1;
		isParent = false;
		item = i;
	}

	private HandbookRegistry(String s) {
		machine = null;
		offset = -1;
		isParent = false;
		item = null;
		title = s;
	}

	private HandbookRegistry() {
		machine = null;
		offset = -1;
		isParent = false;
		item = null;
	}

	private HandbookRegistry(String s, int base) {
		machine = null;
		offset = -1;
		basescreen = base;
		isParent = true;
		item = null;
		title = s;
	}

	public static int getScreen(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEngineScreen(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return GuiHandbook.TRANSSTART+1;
		for (int i = ENGINEDESC.ordinal(); i < TOOLDESC.ordinal(); i++) {
			if (tabList[i].machine == m)
				return tabList[i].getScreen();
		}
		return -1;
	}

	private int getScreen() {
		return this.getParent().basescreen+this.getRelativeScreen();
	}

	private int getPage() {
		return (this.ordinal()-this.getParent().ordinal())%8;
	}

	public boolean hasOffset() {
		return offset > -1;
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
		return this.getParent().basescreen+this.ordinal()-this.getParent().ordinal();
	}

	private static int getAdvGearPage(TileEntity te) {
		return ((RotaryCraftTileEntity)te).getBlockMetadata()/4;
	}

	private static int getEnginePage(TileEntity te) {
		EnumEngineType e = ((TileEntityEngine)te).type;
		return 1+e.ordinal()-(getEngineScreen(te)-GuiHandbook.ENGINESTART)*8;
	}

	private static int getEngineScreen(TileEntity te) {
		EnumEngineType e = ((TileEntityEngine)te).type;
		return GuiHandbook.ENGINESTART+(1+e.ordinal())/8;
	}

	public String getTabImageFile() {
		return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+this.getParent().name().toLowerCase()+".png";
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
		return offset-this.getRelativeScreen()*8;
	}

	public int getRelativeScreen() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset/8;
	}

	public HandbookRegistry getParent() {/*
		if (this.ordinal() < TERMS.ordinal())
			return TOC;
		else if (this.ordinal() < MISCDESC.ordinal())
			return TERMS;
		else if (this.ordinal() < ENGINEDESC.ordinal())
			return MISCDESC;
		else if (this.ordinal() < TRANSDESC.ordinal())
			return ENGINEDESC;
		else if (this.ordinal() < MACHINEDESC.ordinal())
			return TRANSDESC;
		else if (this.ordinal() < TOOLDESC.ordinal())
			return MACHINEDESC;
		else if (this.ordinal() < CRAFTDESC.ordinal())
			return TOOLDESC;
		else if (this.ordinal() < RESOURCEDESC.ordinal())
			return CRAFTDESC;
		else
			return RESOURCEDESC;*/
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
			if (tabList[i].getScreen() == screen) {
				li.add(new ImagedGuiButton(id, j-20, k+tabList[i].getRelativeTabPosn()*20, 20, 20, tabList[i].getTabColumn(), tabList[i].getTabRow(), 0, tabList[i].getTabImageFile()));
				//ReikaJavaLibrary.pConsole("Adding "+tabList[i]+" with ID "+id+" to screen "+screen);
				id++;
			}
		}
	}

	public static HandbookRegistry getEntry(int screen, int page) {
		//ReikaJavaLibrary.pConsole(screen+"   "+page);
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen && tabList[i].getPage() == page) {
				return tabList[i];
			}
		}
		throw new RuntimeException("Handbook screen "+screen+" and page "+page+" do not correspond to an entry!");
	}

	public boolean isPlainGui() {
		//ReikaJavaLibrary.pConsole(this);
		if (this == TOC)
			return true;
		if (this == TERMS)
			return true;
		if (this == PHYSICS)
			return true;
		if (isParent)
			return true;
		if (this == BEDDUST)
			return true;
		if (this == SPAWNERS)
			return true;
		if (this == LUBE)
			return true;
		if (this == CANOLA)
			return true;
		if (this == ALUMINUM)
			return true;
		return false;
	}

	public String getTitle() {
		if (isParent)
			return title;
		if (this.getParent() == ENGINEDESC) {
			if (this == SOLAR)
				return MachineRegistry.SOLARTOWER.getName();
			else
				return RotaryNames.engineNames[offset];
		}
		if (this.getParent() == MACHINEDESC)
			return machine.getName();
		if (machine == MachineRegistry.ADVANCEDGEARS)
			return RotaryNames.advGearItemNames[offset];
		if (this.getParent() == TRANSDESC)
			return machine.getName();
		if (this.getParent() == TOOLDESC && this != BEDTOOLS)
			return item.getBasicName();
		if (this.getParent() == CRAFTDESC)
			return this.getCraftName();
		if (this.getParent() == RESOURCEDESC)
			return this.getResourceName();
		return title;
	}

	private String getResourceName() {
		return HandbookAuxData.resource.get(this.ordinal()-this.getParent().ordinal());
	}

	private String getCraftName() {
		return HandbookAuxData.crafting.get(this.ordinal()-this.getParent().ordinal());
	}

}
