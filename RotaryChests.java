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
import Reika.DragonAPI.Instantiable.LootController;
import Reika.DragonAPI.Instantiable.LootController.Location;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RotaryChests {

	private static final LootController data = new LootController();

	public static void addToChests() {
		data.addItem(1, Location.BONUS, ItemStacks.steelingot, 1, 5, 50);

		data.addItem(2, Location.DUNGEON, ItemStacks.scrap, 1, 6, 40);
		data.addItem(2, Location.DUNGEON, ItemStacks.ironscrap, 1, 12, 80);
		data.addItem(1, Location.DUNGEON, ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 80);

		data.addItem(2, Location.MINESHAFT, ItemStacks.basepanel, 1, 3, 3);
		data.addItem(1, Location.MINESHAFT, ItemStacks.steelingot, 1, 8, 25);
		data.addItem(2, Location.MINESHAFT, ItemStacks.shaftitem, 1, 4, 20);
		data.addItem(2, Location.MINESHAFT, ItemStacks.mount, 1, 1, 5);
		data.addItem(2, Location.MINESHAFT, ItemStacks.steelgear, 1, 3, 18);
		data.addItem(3, Location.MINESHAFT, ItemStacks.gearunit, 1, 2, 10);
		data.addItem(2, Location.MINESHAFT, ItemStacks.drill, 1, 2, 7);
		data.addItem(3, Location.MINESHAFT, ItemStacks.goldcoil, 1, 1, 2);
		data.addItem(2, Location.MINESHAFT, ItemStacks.scrap, 1, 12, 25);
		data.addItem(2, Location.MINESHAFT, ItemStacks.ironscrap, 1, 12, 25);
		data.addItem(1, Location.MINESHAFT, ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 20);
		data.addItem(2, Location.MINESHAFT, ItemRegistry.SCREWDRIVER.getStackOf(), 1, 1, 2);
		data.addItem(3, Location.MINESHAFT, ItemRegistry.METER.getStackOf(), 1, 1, 1);
		data.addItem(4, Location.MINESHAFT, ItemStacks.bedrockdust, 1, 3, 1);
		data.addItem(2, Location.MINESHAFT, ItemStacks.sawdust, 1, 10, 15);
		data.addItem(2, Location.MINESHAFT, ItemRegistry.ETHANOL.getStackOf(), 1, 4, 10);

		data.addItem(4, Location.STRONGHOLD_HALLWAY, ItemStacks.lens, 1, 1, 10);
		data.addItem(1, Location.STRONGHOLD_HALLWAY, ItemStacks.steelingot, 1, 3, 50);
		data.addItem(2, Location.STRONGHOLD_HALLWAY, ItemStacks.scrap, 1, 8, 40);
		data.addItem(2, Location.STRONGHOLD_HALLWAY, ItemStacks.ironscrap, 1, 8, 40);
		data.addItem(3, Location.STRONGHOLD_HALLWAY, ItemStacks.lonsda, 1, 2, 20);
		data.addItem(2, Location.STRONGHOLD_HALLWAY, new ItemStack(RotaryCraft.obsidianglass), 1, 6, 30);

		data.addItem(4, Location.STRONGHOLD_CROSSING, ItemStacks.lens, 1, 1, 10);
		data.addItem(1, Location.STRONGHOLD_CROSSING, ItemStacks.steelingot, 1, 3, 50);
		data.addItem(2, Location.STRONGHOLD_CROSSING, ItemStacks.scrap, 1, 8, 40);
		data.addItem(2, Location.STRONGHOLD_CROSSING, ItemStacks.ironscrap, 1, 8, 40);
		data.addItem(3, Location.STRONGHOLD_CROSSING, ItemStacks.lonsda, 1, 2, 20);
		data.addItem(2, Location.STRONGHOLD_CROSSING, new ItemStack(RotaryCraft.obsidianglass), 1, 6, 30);

		//data.addItem(2, Location.STRONGHOLD_LIBRARY, ItemRegistry.HANDBOOK.getStackOf(), 1, 1, 50);
		data.addItem(3, Location.STRONGHOLD_LIBRARY, ItemRegistry.METER.getStackOf(), 1, 1, 5);

		data.addItem(1, Location.VILLAGE, ItemStacks.steelingot, 1, 3, 20);
		data.addItem(2, Location.VILLAGE, ItemStacks.scrap, 1, 4, 30);
		data.addItem(2, Location.VILLAGE, ItemStacks.ironscrap, 1, 8, 60);

		data.registerToWorldGen(RotaryCraft.instance, ConfigRegistry.CHESTGEN.getValue());
	}
}
