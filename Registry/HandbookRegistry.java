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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaOreHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
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
	HOSE("Lubricant Hose", MachineRegistry.HOSE),
	CANOLA("Canola", ItemRegistry.CANOLA),
	METER("Angular Transducer", ItemRegistry.METER),
	SCREW("Screwdriver", ItemRegistry.SCREWDRIVER),
	PIPE("Pipe", MachineRegistry.PIPE),
	FUELLINE("Fuel Line", MachineRegistry.FUELLINE),
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
	MULTI(MachineRegistry.MULTICLUTCH),

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
	WORKTABLE(MachineRegistry.WORKTABLE),
	AIRCOMPRESSOR(MachineRegistry.COMPRESSOR),
	PNEUMATIC(MachineRegistry.PNEUENGINE),
	BALANCER(MachineRegistry.BALANCER),
	DISPLAY(MachineRegistry.DISPLAY),
	TERRA(MachineRegistry.TERRAFORMER),
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
	STEELINGOT(ItemStacks.steelingot),
	PANEL(ItemStacks.basepanel),
	SHAFTITEM(ItemStacks.shaftitem),
	GEAR(ItemStacks.steelgear),
	GEARUNIT(ItemStacks.gearunit),
	MOUNT(ItemStacks.mount),
	SCRAP(ItemStacks.scrap),
	IMPELLER(ItemStacks.impeller),
	COMPRESSOR(ItemStacks.compressor),
	TURBINE(ItemStacks.turbine),
	COMPOUND(ItemStacks.compoundturb),
	DIFFUSER(ItemStacks.diffuser),
	COMBUSTOR(ItemStacks.combustor),
	RADIATOR(ItemStacks.radiator),
	CONDENSER(ItemStacks.condenser),
	GOLDCOIL(ItemStacks.goldcoil),
	CYLINDER(ItemStacks.cylinder),
	PADDLE(ItemStacks.waterplate),
	CORE(ItemStacks.shaftcore),
	IGNITER(ItemStacks.igniter),
	PROP(ItemStacks.prop),
	HUB(ItemStacks.hub),
	BARREL(ItemStacks.barrel),
	LENS(ItemStacks.lens),
	POWERMODULE(ItemStacks.power),
	HEATCORE(ItemStacks.bulb),
	DRILL(ItemStacks.drill),
	PRESSHEAD(ItemStacks.presshead),
	FLYCORE(),
	RADARITEM(ItemStacks.radar),
	SONAR(ItemStacks.sonar),
	PCB(ItemStacks.pcb),
	SCREEN(ItemStacks.screen),
	MIXER(ItemStacks.mixer),
	SAW(ItemStacks.saw),
	BEARING(ItemStacks.bearing),
	BELT(ItemStacks.belt),
	BALLBEARING(ItemStacks.bearingitem),
	BRAKE(ItemStacks.brake),
	WORMITEM(ItemStacks.wormgear),
	OTHERGEAR(),
	OTHERSHAFT(),
	OTHERGEARUNIT(),
	INDUCTION(ItemStacks.lim),
	TENSCOIL(ItemStacks.tenscoil),
	MIRRORITEM(ItemStacks.mirror),
	GENERATOR(ItemStacks.generator),
	RAILHEAD(ItemStacks.railhead),
	TURRETBASE(ItemStacks.railbase),
	TURRETAIM(ItemStacks.railaim),
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
	SLIDES(),
	EXPLOSIVES(),
	MINECART();

	private MachineRegistry machine;
	private ItemRegistry item;
	private int offset;
	private int basescreen;
	private boolean isParent;
	private String title;
	private ItemStack crafted;

	public static final HandbookRegistry[] tabList = HandbookRegistry.values();

	private HandbookRegistry(MachineRegistry m, int o) {
		machine = m;
		offset = o;
		isParent = false;
		item = null;
		crafted = m.getCraftedMetadataProduct(o);
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

	private HandbookRegistry(String s, int base) {
		machine = null;
		offset = -1;
		basescreen = base;
		isParent = true;
		item = null;
		title = s;
		crafted = null;
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
		//return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+this.getParent().name().toLowerCase()+".png";
		return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+TOC.getParent().name().toLowerCase()+".png";
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
				li.add(new ImagedGuiButton(id, j-20, k+tabList[i].getRelativeTabPosn()*20, 20, 20, 0*tabList[i].getTabColumn(), 0*tabList[i].getTabRow(), 0, tabList[i].getTabImageFile()));
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

	public static List<HandbookRegistry> getEntriesForScreen(int screen) {
		//ReikaJavaLibrary.pConsole(screen+"   "+page);
		List<HandbookRegistry> li = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen) {
				li.add(tabList[i]);
			}
		}
		return li;
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

	public boolean isCrafting() {
		if (isParent)
			return false;
		if (this.isSmelting())
			return false;
		if (this.getParent() == TOC || this.getParent() == TERMS)
			return false;
		if (this == STEELINGOT)
			return false;
		if (this == LUBE)
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
		if (this == SCRAP)
			return false;
		if (this == CANOLA)
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
			return li;
		}
		if (this == SOLAR)
			return ReikaJavaLibrary.makeListFrom(MachineRegistry.SOLARTOWER.getCraftedProduct());
		if (this.getParent() == ENGINEDESC)
			return ReikaJavaLibrary.makeListFrom(EnumEngineType.engineList[offset].getCraftedProduct());
		if (machine == MachineRegistry.ADVANCEDGEARS)
			return ReikaJavaLibrary.makeListFrom(MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(offset));
		if (this.getParent() == TRANSDESC || this.getParent() == MACHINEDESC) {
			if (machine.hasCustomPlacerItem())
				return ReikaJavaLibrary.makeListFrom(machine.getCraftedMetadataProduct(0));
			return ReikaJavaLibrary.makeListFrom(machine.getCraftedProduct());
		}
		if (this == FLYCORE) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.flywheelcore);
			li.add(ItemStacks.flywheelcore2);
			li.add(ItemStacks.flywheelcore3);
			li.add(ItemStacks.flywheelcore4);
			return li;
		}
		if (this == OTHERGEAR) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.woodgear);
			li.add(ItemStacks.stonegear);
			li.add(ItemStacks.diamondgear);
			li.add(ItemStacks.bedrockgear);
			return li;
		}
		if (this == OTHERSHAFT) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.stonerod);
			li.add(ItemStacks.diamondshaft);
			li.add(ItemStacks.bedrockshaft);
			return li;
		}
		if (this == OTHERGEARUNIT) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.wood2x);
			li.add(ItemStacks.wood4x);
			li.add(ItemStacks.wood8x);
			li.add(ItemStacks.wood16x);
			li.add(ItemStacks.stone2x);
			li.add(ItemStacks.stone4x);
			li.add(ItemStacks.stone8x);
			li.add(ItemStacks.stone16x);
			li.add(ItemStacks.diamond2x);
			li.add(ItemStacks.diamond4x);
			li.add(ItemStacks.diamond8x);
			li.add(ItemStacks.diamond16x);
			li.add(ItemStacks.bedrock2x);
			li.add(ItemStacks.bedrock4x);
			li.add(ItemStacks.bedrock8x);
			li.add(ItemStacks.bedrock16x);
			return li;
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
		if (this == BEDINGOT)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.bedingot);
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
		if (this.getParent() == MACHINEDESC)
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
		if (this == FLAKES)
			return true;
		if (this == SILVERINGOT)
			return true;
		return false;
	}

	public ItemStack getSmelting() {
		if (!this.isSmelting())
			return null;
		if (this == ETHANOL)
			return ItemRegistry.ETHANOL.getStackOf();
		if (this == FLAKES)
			return ReikaOreHelper.oreList[(int)((System.nanoTime()/2000000000)%ReikaOreHelper.oreList.length)].getResource();
		if (this == SILVERINGOT)
			return ItemStacks.silveringot;
		return ItemStacks.barrel;
	}

	public ItemStack getTabIcon() {
		if (this == ENGINES)
			return EnumEngineType.AC.getCraftedProduct();
		if (this == MISC)
			return ItemRegistry.SCREWDRIVER.getStackOf();
		if (this == TRANS)
			return MachineRegistry.GEARBOX.getCraftedMetadataProduct(RotaryNames.gearboxItemNames.length-3);
		if (this == MACHINES)
			return MachineRegistry.RAILGUN.getCraftedProduct();
		if (this == TOOLS)
			return ItemRegistry.MOTION.getStackOf();
		if (this == CRAFTING)
			return ItemStacks.steelingot;
		if (this == RESOURCE)
			return ItemStacks.bedrockdust;
		if (this == FLAKES)
			return ItemStacks.goldoreflakes;
		if (this == OTHERGEARUNIT)
			return ItemStacks.diamond2x;
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
			return new ItemStack(RotaryCraft.obsidianglass);
		if (this == SPAWNERS)
			return new ItemStack(DragonAPICore.getItem("spawner"));
		if (this == YEAST)
			return ItemStacks.sludge;
		if (this == SALT)
			return ItemStacks.salt;
		if (this == ALUMINUM)
			return ItemStacks.aluminumpowder;
		if (this == RAILGUNAMMO)
			return ItemRegistry.RAILGUN.getStackOf();
		if (this == SCRAP)
			return ItemStacks.scrap;
		if (this == CANOLA)
			return ItemRegistry.CANOLA.getStackOf();
		return null;
	}

}
