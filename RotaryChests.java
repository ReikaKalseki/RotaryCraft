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

public class RotaryChests {

    public static void addToChests() {
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 1), 1, 5, 50));

		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 9), 1, 6, 40)); //steel
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 10), 1, 12, 80)); //iron
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.canolaseed.itemID, 0, 0), 1, 12, 80));

		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 0), 1, 3, 3)); //panel
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 1), 1, 8, 25)); //ingot
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 2), 1, 4, 20)); //shaft
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 3), 1, 1, 5)); //mount
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 4), 1, 3, 18)); //gear
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 5), 1, 2, 10)); //2x
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.borecraft.itemID, 0, 0), 1, 2, 7)); //drill
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.enginecraft.itemID, 0, 8), 1, 1, 2)); //gold coil
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 9), 1, 12, 25)); //steel
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 10), 1, 12, 25)); //iron
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.canolaseed.itemID, 0, 0), 1, 12, 20));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.screwdriver.itemID, 0, 0), 1, 1, 2));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.meter.itemID, 0, 0), 1, 1, 1));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.powders.itemID, 0, ItemStacks.bedrockdust.getItemDamage()), 1, 3, 1)); //bedrock
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.powders.itemID, 0, ItemStacks.sawdust.getItemDamage()), 1, 10, 15)); //sawdust
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.ethanol.itemID, 0, 0), 1, 4, 10));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.heatcraft.itemID, 0, 1), 1, 1, 10)); //lens
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 1), 1, 3, 50));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 9), 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 10), 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.compacts.itemID, 0, 2), 1, 2, 20)); //lonsdaleite
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.obsidianglass.blockID, 0, 0), 1, 6, 30));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.heatcraft.itemID, 0, 1), 1, 1, 10)); //lens
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 1), 1, 3, 50));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 9), 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 10), 1, 8, 40));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.compacts.itemID, 0, 2), 1, 2, 20)); //lonsdaleite
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.obsidianglass.blockID, 0, 0), 1, 6, 30));

		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.infobook, 0, 0), 1, 1, 50));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.meter.itemID, 0, 0), 1, 1, 5));

		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 1), 1, 3, 20));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 9), 1, 4, 30)); //steel
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(RotaryCraft.shaftcraft.itemID, 0, 10), 1, 8, 60)); //iron
    }
}
