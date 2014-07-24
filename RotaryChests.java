/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.LootController;
import Reika.DragonAPI.Instantiable.LootController.Location;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RotaryChests {

	private static final LootController data = new LootController();

	public static void addToChests() {
		data.addItem(1, Location.BONUS, ItemStacks.steelingot, 1, 5, 12);

		data.addItem(2, Location.DUNGEON, ItemStacks.scrap, 6, 18, 20);
		data.addItem(2, Location.DUNGEON, ItemStacks.ironscrap, 1, 12, 40);
		data.addItem(1, Location.DUNGEON, ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 40);

		data.addItem(2, Location.MINESHAFT, ItemStacks.basepanel, 1, 3, 1);
		data.addItem(1, Location.MINESHAFT, ItemStacks.steelingot, 1, 8, 12);
		data.addItem(2, Location.MINESHAFT, ItemStacks.shaftitem, 1, 4, 10);
		data.addItem(2, Location.MINESHAFT, ItemStacks.mount, 1, 1, 2);
		data.addItem(2, Location.MINESHAFT, ItemStacks.steelgear, 1, 3, 9);
		data.addItem(3, Location.MINESHAFT, ItemStacks.gearunit, 1, 2, 5);
		data.addItem(2, Location.MINESHAFT, ItemStacks.drill, 1, 2, 3);
		data.addItem(3, Location.MINESHAFT, ItemStacks.goldcoil, 1, 1, 1);
		data.addItem(2, Location.MINESHAFT, ItemStacks.scrap, 12, 36, 12);
		data.addItem(2, Location.MINESHAFT, ItemStacks.ironscrap, 1, 12, 12);
		data.addItem(1, Location.MINESHAFT, ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 10);
		data.addItem(2, Location.MINESHAFT, ItemRegistry.SCREWDRIVER.getStackOf(), 1, 1, 1);
		data.addItem(3, Location.MINESHAFT, ItemRegistry.METER.getStackOf(), 1, 1, 1);
		//data.addItem(4, Location.MINESHAFT, ItemStacks.bedrockdust, 1, 3, 1);
		data.addItem(2, Location.MINESHAFT, ItemStacks.sawdust, 1, 10, 7);
		data.addItem(2, Location.MINESHAFT, ItemRegistry.ETHANOL.getStackOf(), 1, 4, 5);

		data.addItem(4, Location.STRONGHOLD_HALLWAY, ItemStacks.lens, 1, 1, 5);
		data.addItem(1, Location.STRONGHOLD_HALLWAY, ItemStacks.steelingot, 1, 3, 25);
		data.addItem(2, Location.STRONGHOLD_HALLWAY, ItemStacks.scrap, 4, 16, 20);
		data.addItem(2, Location.STRONGHOLD_HALLWAY, ItemStacks.ironscrap, 1, 8, 20);
		data.addItem(3, Location.STRONGHOLD_HALLWAY, ItemStacks.lonsda, 1, 2, 10);
		data.addItem(2, Location.STRONGHOLD_HALLWAY, new ItemStack(RotaryCraft.blastglass), 1, 6, 15);

		data.addItem(4, Location.STRONGHOLD_CROSSING, ItemStacks.lens, 1, 1, 5);
		data.addItem(1, Location.STRONGHOLD_CROSSING, ItemStacks.steelingot, 1, 3, 25);
		data.addItem(2, Location.STRONGHOLD_CROSSING, ItemStacks.scrap, 8, 24, 20);
		data.addItem(2, Location.STRONGHOLD_CROSSING, ItemStacks.ironscrap, 1, 8, 20);
		data.addItem(3, Location.STRONGHOLD_CROSSING, ItemStacks.lonsda, 1, 2, 10);
		data.addItem(2, Location.STRONGHOLD_CROSSING, new ItemStack(RotaryCraft.blastglass), 1, 6, 15);

		//data.addItem(2, Location.STRONGHOLD_LIBRARY, ItemRegistry.HANDBOOK.getStackOf(), 1, 1, 25);
		data.addItem(3, Location.STRONGHOLD_LIBRARY, ItemRegistry.METER.getStackOf(), 1, 1, 2);

		data.addItem(1, Location.VILLAGE, ItemStacks.steelingot, 1, 3, 10);
		data.addItem(2, Location.VILLAGE, ItemStacks.scrap, 4, 18, 15);
		data.addItem(2, Location.VILLAGE, ItemStacks.ironscrap, 1, 8, 30);

		data.registerToWorldGen(RotaryCraft.instance, ConfigRegistry.CHESTGEN.getValue());
	}
}
