/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModInteract.TwilightForestHandler;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;

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
	TITANIUM("ingotTitanium"),
	DARKSTEEL("ingotDarkSteel"),
	STEELEAF(TwilightForestHandler.getInstance().steelleaf),
	IRONWOOD(TwilightForestHandler.getInstance().ironwood),
	MANYULLYN("ingotManyullyn"),
	;

	private ItemStack item;

	private static final BlastGate[] matList = values();

	private BlastGate(Block b) {
		this(b != null ? new ItemStack(b) : null);
	}

	private BlastGate(Item i) {
		this(i != null ? new ItemStack(i) : null);
	}

	private BlastGate(ItemStack is) {
		item = is;
	}

	private BlastGate(String s) {
		Collection<ItemStack> c = OreDictionary.getOres(s);
		for (ItemStack is : c) {
			if (is.getItem() == ItemRegistry.MODINGOTS.getItemInstance())
				continue;
			else if (ReikaItemHelper.matchStacks(is, ItemStacks.steelingot))
				continue;
			else {
				item = is;
				break;
			}
		}
	}

	public ItemStack getItem() {
		return item != null ? item.copy() : item;
	}

	static {
		if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION))
			throw new RegistrationException(RotaryCraft.instance, "Blast Furnace gating material registry accessed too early, cannot populate!");
	}
}
