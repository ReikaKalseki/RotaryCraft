/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2017
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Auxiliary;

import java.util.Comparator;

import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Instantiable.GUI.SortedCreativeTab;
import Reika.DragonAPI.Libraries.ReikaSpawnerHelper;
import Reika.RotaryCraft.Registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabSpawner extends SortedCreativeTab {

	public TabSpawner() {
		super("Spawners");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		return ItemRegistry.SPAWNER.getStackOf();
	}

	private static final SpawnerComparator comparator = new SpawnerComparator();

	@Override
	protected Comparator<ItemStack> getComparator() {
		return comparator;
	}

	private static class SpawnerComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack o1, ItemStack o2) {
			String s1 = ReikaSpawnerHelper.getSpawnerFromItemNBT(o1);
			String s2 = ReikaSpawnerHelper.getSpawnerFromItemNBT(o2);
			if (s1 != null && !s1.isEmpty() && s2 != null && !s2.isEmpty()) {
				return (Integer)EntityList.stringToIDMapping.get(s1)-(Integer)EntityList.stringToIDMapping.get(s2);
			}
			else if (s1 != null && !s1.isEmpty() && EntityList.stringToIDMapping.containsKey(s1)) {
				return Integer.MIN_VALUE;
			}
			else if (s2 != null && !s2.isEmpty() && EntityList.stringToIDMapping.containsKey(s2)) {
				return Integer.MAX_VALUE;
			}
			else
				return 0;
		}

	}
}
