/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import Reika.DragonAPI.ModList;
import Reika.DragonAPI.ModInteract.ItemHandlers.TwilightForestHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public enum BlastGate {

	ALUMITE("ingotAlumite"),
	OBSIDINGOT("ingotObsidian"),
	OBSIDIAN(Blocks.obsidian),
	STEEL("ingotSteel"),
	COPPER("ingotCopper"),
	GOLD(Items.gold_ingot),
	SILVER("ingotSilver"),
	BRASS("ingotBrass"),
	BRONZE("ingotBronze"),
	INVAR("ingotInvar"),
	TITANIUM("ingotTitanium"),
	DARKSTEEL("ingotDarkSteel"),
	STEELEAF(TwilightForestHandler.ItemEntry.STEELLEAF.getItem()),
	IRONWOOD(TwilightForestHandler.ItemEntry.IRONWOOD.getItem()),
	MANYULLYN("ingotManyullyn"),
	GOLDGEAR(GameRegistry.findItem(ModList.BUILDCRAFT.modLabel, "goldGearItem")),
	TERRASTEEL("ingotTerrasteel"),
	MANASTEEL("ingotManasteel"),
	VOIDMETAL("ingotVoid"),
	THAUMIUM("ingotThaumium"),
	OSMIUM("ingotOsmium"),
	SIGNALUM("ingotSignalum"),
	ENDERIUM("ingotEnderium"),
	;

	private Object item;

	private static final BlastGate[] matList = values();

	private BlastGate(Object o) {
		item = o;
	}

	public Object getItem() {
		return item;
	}
}
