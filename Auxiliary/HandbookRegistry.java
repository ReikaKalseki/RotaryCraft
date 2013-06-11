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

import net.minecraft.tileentity.TileEntity;

import Reika.RotaryCraft.MachineRegistry;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.GUIs.GuiHandbook;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public enum HandbookRegistry {

	DCENGINE(MachineRegistry.ENGINE, EnumEngineType.DC.ordinal()),
	WINDENGINE(MachineRegistry.ENGINE, EnumEngineType.WIND.ordinal()),
	STEAMENGINE(MachineRegistry.ENGINE, EnumEngineType.STEAM.ordinal()),
	GASENGINE(MachineRegistry.ENGINE, EnumEngineType.GAS.ordinal()),
	ACENGINE(MachineRegistry.ENGINE, EnumEngineType.AC.ordinal()),
	PERFENGINE(MachineRegistry.ENGINE, EnumEngineType.SPORT.ordinal()),
	HYDROENGINE(MachineRegistry.ENGINE, EnumEngineType.HYDRO.ordinal()),
	MICROTURB(MachineRegistry.ENGINE, EnumEngineType.MICRO.ordinal()),
	JETENGINE(MachineRegistry.ENGINE, EnumEngineType.JET.ordinal()),

	//-----------TRANSMISSION----------//
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

	//------------MACHINES-------------//
	BEDROCK(MachineRegistry.BEDROCKBREAKER),
	FERMENT(MachineRegistry.FERMENTER),
	GRINDER(MachineRegistry.GRINDER),
	FLOODLIGHT(MachineRegistry.FLOODLIGHT),
	HEATRAY(MachineRegistry.HEATRAY),
	BORER(MachineRegistry.BORER),
	PILEDRIVER(MachineRegistry.PILEDRIVER),
	AEROSOL(MachineRegistry.AEROSOLIZER),
	LIGHTBRID(MachineRegistry.LIGHTBRIDGE),
	EXTRACT(MachineRegistry.EXTRACTOR),
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
	WOODCUT(MachineRegistry.WOODCUTTER),
	ECU(MachineRegistry.ECU),
	RADAR(MachineRegistry.MOBRADAR),
	WINDER(MachineRegistry.WINDER),
	TNT(MachineRegistry.TNTCANNON),
	SONIC(MachineRegistry.SONICWEAPON),
	BLAST(MachineRegistry.BLASTFURNACE),
	FORCE(MachineRegistry.FORCEFIELD),
	MUSIC(MachineRegistry.MUSICBOX),
	HARVEST(MachineRegistry.MOBHARVESTER),
	PROJECT(MachineRegistry.PROJECTOR),
	RAILGUN(MachineRegistry.RAILGUN),
	WEATHER(MachineRegistry.WEATHERCONTROLLER),
	REFRESH(MachineRegistry.REFRESHER),
	SCANNER(MachineRegistry.CAVESCANNER),
	CHEST(MachineRegistry.SCALECHEST),
	SPILLER(MachineRegistry.SPILLER),
	SMOKE(MachineRegistry.SMOKEDETECTOR),
	FIRESTART(MachineRegistry.IGNITER),
	FREEZE(MachineRegistry.FREEZEGUN),
	MAGNET(MachineRegistry.MAGNETIZER),
	CONTAIN(MachineRegistry.CONTAINMENT),
	CCTVSCREEN(MachineRegistry.SCREEN),
	CCTV(MachineRegistry.CCTV),
	PURIFIER(MachineRegistry.PURIFIER),
	BUCKET(MachineRegistry.BUCKETFILLER),
	BLOCKCANNON(MachineRegistry.BLOCKCANNON);

	private MachineRegistry machine;
	private int offset;

	public static final HandbookRegistry[] tabList = HandbookRegistry.values();

	private HandbookRegistry(MachineRegistry m, int o) {
		offset = o;
	}

	private HandbookRegistry(MachineRegistry m) {
		machine = m;
		offset = -1;
	}

	public static int getScreen(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEngineScreen(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return GuiHandbook.TRANSSTART+1;
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].machine == m)
				return tabList[i].getShiftedOrdinal()/8+tabList[i].getBaseScreen();
		}
		return -1;
	}

	private int getShiftedOrdinal() {
		int base = this.ordinal();
		if (this.isEngine())
			return base;
		if (this.isTrans())
			return base-JETENGINE.ordinal();
		if (this.isMachine())
			return base-COIL.ordinal();
		return -1;
	}

	public int getBaseScreen() {
		if (this.isEngine())
			return GuiHandbook.ENGINESTART;
		if (this.isTrans())
			return GuiHandbook.TRANSSTART;
		if (this.isMachine())
			return GuiHandbook.MACHINESTART;
		return -1;
	}

	public boolean hasOffset() {
		return offset > -1;
	}

	public boolean isTrans() {
		return !this.isEngine() && !this.isMachine();
	}

	public boolean isEngine() {
		return this.ordinal() < SHAFT.ordinal();
	}

	public boolean isMachine() {
		return this.ordinal() >= BEDROCK.ordinal();
	}

	public static int getPage(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEnginePage(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return getAdvGearPage(te);
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].machine == m)
				return tabList[i].getShiftedOrdinal()-(getScreen(m, te)-tabList[i].getBaseScreen())*8;
		}
		return -1;
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

}
