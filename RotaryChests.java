/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RotaryChests {

	public static void addToChests() {
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(ItemStacks.steelingot, 1, 5, 50));

		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(ItemStacks.scrap, 1, 6, 40));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(ItemStacks.ironscrap, 1, 12, 80));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 80));

		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.basepanel, 1, 3, 3));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.steelingot, 1, 8, 25));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.shaftitem, 1, 4, 20));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.mount, 1, 1, 5));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.steelgear, 1, 3, 18));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.gearunit, 1, 2, 10));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.drill, 1, 2, 7));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.goldcoil, 1, 1, 2));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.scrap, 1, 12, 25));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.ironscrap, 1, 12, 25));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 20));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemRegistry.SCREWDRIVER.getStackOf(), 1, 1, 2));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemRegistry.METER.getStackOf(), 1, 1, 1));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.bedrockdust, 1, 3, 1));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.sawdust, 1, 10, 15));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(ItemRegistry.ETHANOL.getStackOf(), 1, 4, 10));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.lens, 1, 1, 10));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.steelingot, 1, 3, 50));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.scrap, 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.ironscrap, 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(ItemStacks.lonsda, 1, 2, 20));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.obsidianglass), 1, 6, 30));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(ItemStacks.lens, 1, 1, 10));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(ItemStacks.steelingot, 1, 3, 50));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(ItemStacks.scrap, 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(ItemStacks.ironscrap, 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(ItemStacks.lonsda, 1, 2, 20));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.obsidianglass), 1, 6, 30));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(ItemRegistry.HANDBOOK.getStackOf(), 1, 1, 50));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(ItemRegistry.METER.getStackOf(), 1, 1, 5));

		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(ItemStacks.steelingot, 1, 3, 20));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(ItemStacks.scrap, 1, 4, 30));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(ItemStacks.ironscrap, 1, 8, 60));
	}
}
