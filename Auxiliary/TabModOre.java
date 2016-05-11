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
import java.util.Comparator;

import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.GUI.SortedCreativeTab;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.Auxiliary.RecipeManagers.ExtractorModOres;
import Reika.RotaryCraft.ModInterface.ItemCustomModOre;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabModOre extends SortedCreativeTab {

	public TabModOre() {
		super("Mod Ores");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
	{
		return ExtractorModOres.getDustProduct(ModOreList.COPPER);
	}

	private static final OreItemComparator comparator = new OreItemComparator();

	@Override
	protected Comparator<ItemStack> getComparator() {
		return comparator;
	}

	private static class OreItemComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack o1, ItemStack o2) {
			return this.getIndex(o1)-this.getIndex(o2);
		}

		private int getIndex(ItemStack is) {
			if (ItemRegistry.MODEXTRACTS.matchItem(is))
				return ExtractorModOres.getOreFromExtract(is).ordinal()*4+is.getItemDamage();
			if (ItemRegistry.MODINGOTS.matchItem(is))
				return 10000+is.getItemDamage(); //dmg is ore ordinal
			if (ItemRegistry.CUSTOMEXTRACT.matchItem(is))
				return 20000+ItemCustomModOre.getEntryIndex(is)*4+is.getItemDamage();
			if (ItemRegistry.CUSTOMINGOT.matchItem(is))
				return 30000+ItemCustomModOre.getEntryIndex(is);
			return 0;
		}

	}
}
