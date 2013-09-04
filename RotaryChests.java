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
import net.minecraftforge.common.ChestGenHooks;
import Reika.DragonAPI.Libraries.ReikaGenHelper;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Registry.ItemRegistry;

public class RotaryChests {

	public static void addToChests() {
		ReikaGenHelper.addStackToChestGen(ChestGenHooks.BONUS_CHEST, ItemStacks.steelingot, 1, 5, 50);

		ReikaGenHelper.addStackToDungeon(ItemStacks.scrap, 1, 6, 40);
		ReikaGenHelper.addStackToDungeon(ItemStacks.ironscrap, 1, 12, 80);
		ReikaGenHelper.addStackToDungeon(ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 80);

		ReikaGenHelper.addStackToMineshaft(ItemStacks.basepanel, 1, 3, 3);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.steelingot, 1, 8, 25);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.shaftitem, 1, 4, 20);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.mount, 1, 1, 5);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.steelgear, 1, 3, 18);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.gearunit, 1, 2, 10);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.drill, 1, 2, 7);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.goldcoil, 1, 1, 2);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.scrap, 1, 12, 25);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.ironscrap, 1, 12, 25);
		ReikaGenHelper.addStackToMineshaft(ItemRegistry.CANOLA.getStackOfMetadata(0), 1, 12, 20);
		ReikaGenHelper.addStackToMineshaft(ItemRegistry.SCREWDRIVER.getStackOf(), 1, 1, 2);
		ReikaGenHelper.addStackToMineshaft(ItemRegistry.METER.getStackOf(), 1, 1, 1);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.bedrockdust, 1, 3, 1);
		ReikaGenHelper.addStackToMineshaft(ItemStacks.sawdust, 1, 10, 15);
		ReikaGenHelper.addStackToMineshaft(ItemRegistry.ETHANOL.getStackOf(), 1, 4, 10);

		ReikaGenHelper.addStackToStronghold(ItemStacks.lens, 1, 1, 10);
		ReikaGenHelper.addStackToStronghold(ItemStacks.steelingot, 1, 3, 50);
		ReikaGenHelper.addStackToStronghold(ItemStacks.scrap, 1, 8, 40);
		ReikaGenHelper.addStackToStronghold(ItemStacks.ironscrap, 1, 8, 40);
		ReikaGenHelper.addStackToStronghold(ItemStacks.lonsda, 1, 2, 20);
		ReikaGenHelper.addStackToStronghold(new ItemStack(RotaryCraft.obsidianglass), 1, 6, 30);

		ReikaGenHelper.addStackToStronghold2(ItemStacks.lens, 1, 1, 10);
		ReikaGenHelper.addStackToStronghold2(ItemStacks.steelingot, 1, 3, 50);
		ReikaGenHelper.addStackToStronghold2(ItemStacks.scrap, 1, 8, 40);
		ReikaGenHelper.addStackToStronghold2(ItemStacks.ironscrap, 1, 8, 40);
		ReikaGenHelper.addStackToStronghold2(ItemStacks.lonsda, 1, 2, 20);
		ReikaGenHelper.addStackToStronghold2(new ItemStack(RotaryCraft.obsidianglass), 1, 6, 30);

		ReikaGenHelper.addStackToLibrary(ItemRegistry.HANDBOOK.getStackOf(), 1, 1, 50);
		ReikaGenHelper.addStackToLibrary(ItemRegistry.METER.getStackOf(), 1, 1, 5);

		ReikaGenHelper.addStackToVillage(ItemStacks.steelingot, 1, 3, 20);
		ReikaGenHelper.addStackToVillage(ItemStacks.scrap, 1, 4, 30);
		ReikaGenHelper.addStackToVillage(ItemStacks.ironscrap, 1, 8, 60);
	}
}
