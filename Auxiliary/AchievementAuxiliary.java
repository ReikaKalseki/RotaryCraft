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

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Libraries.Java.ReikaStringParser;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

public abstract class AchievementAuxiliary {

	private static ArrayList<String> labels = new ArrayList<String>();

	public static final ItemStack[] icons = {
		ItemStacks.steelingot,
		MachineRegistry.FERMENTER.getCraftedProduct(),
		MachineRegistry.EXTRACTOR.getCraftedProduct(),
		MachineRegistry.BORER.getCraftedProduct(),
		MachineRegistry.PULSEJET.getCraftedProduct(),
		new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.JET.ordinal()),
		MachineRegistry.RAILGUN.getCraftedProduct(),
		new ItemStack(Item.rottenFlesh),
		MachineRegistry.BEDROCKBREAKER.getCraftedProduct(),
		new ItemStack(RotaryCraft.engineitems.itemID, 1, EnumEngineType.STEAM.ordinal()),
		new ItemStack(RotaryCraft.shaftitems.itemID, 1, MaterialRegistry.STEEL.ordinal()),
		new ItemStack(RotaryCraft.advgearitems.itemID, 1, 1),
		new ItemStack(RotaryCraft.shaftitems.itemID, 1, MaterialRegistry.BEDROCK.ordinal()),
		ItemRegistry.BEDPICK.getStackOf(),
		MachineRegistry.FRACTIONATOR.getCraftedProduct(),
		new ItemStack(Item.feather),
		new ItemStack(Block.tnt),
		MachineRegistry.LIGHTBRIDGE.getCraftedProduct(),
		MachineRegistry.SPRINKLER.getCraftedProduct(),
		MachineRegistry.FLOODLIGHT.getCraftedProduct(),
		ItemStacks.scrap,
		new ItemStack(RotaryCraft.gbxitems.itemID, 1, RotaryNames.getNumberGearTypes()-2),
		new ItemStack(Item.sugar),
		new ItemStack(Block.blockRedstone),
		MachineRegistry.TNTCANNON.getCraftedProduct(),
		new ItemStack(Block.dragonEgg),
		new ItemStack(Item.skull.itemID, 1, 0),
		ItemRegistry.GRAVELGUN.getStackOf(),
		MachineRegistry.LANDMINE.getCraftedProduct(),
		new ItemStack(Block.endPortalFrame),
		MachineRegistry.HEATRAY.getCraftedProduct(),
		new ItemStack(Block.mobSpawner),
	};

	public static void loadDesc() {
		String path = "Resources/AchievementDesc.txt";
		InputStream in = RotaryCraft.class.getResourceAsStream(path);
		try {
			BufferedReader p = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int i = 0;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty()) {
					labels.add(ReikaStringParser.getStringWithEmbeddedReferences(line));
					i++;
				}
			}
			p.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String getDesc(int i) {
		if (i < labels.size())
			return labels.get(i);
		else
			return "[NO DESC]";
	}

}
