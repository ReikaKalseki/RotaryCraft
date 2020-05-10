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

import net.minecraft.item.ItemStack;

import Reika.DragonAPI.Instantiable.GUI.SortedCreativeTab;
import Reika.RotaryCraft.Registry.GearboxTypes;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabRotaryPower extends SortedCreativeTab {

	public TabRotaryPower() {
		super("RotaryCraft Power");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		return GearboxTypes.STEEL.getGearboxItem(16);
	}

	@Override
	protected Comparator<ItemStack> getComparator() {
		return sorter;
	}

	private static final PowerItemSorter sorter = new PowerItemSorter();

	private static final class PowerItemSorter implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack is1, ItemStack is2) {
			ItemRegistry r1 = ItemRegistry.getEntry(is1);
			ItemRegistry r2 = ItemRegistry.getEntry(is2);
			if (r1 == r2) {
				if (r1 == null)
					return 0;
				switch(r1) {
					case GEARBOX: {
						GearboxTypes g1 = GearboxTypes.getMaterialFromGearboxItem(is1);
						GearboxTypes g2 = GearboxTypes.getMaterialFromGearboxItem(is2);
						return g1.compareTo(g2)*50+Integer.compare(is1.getItemDamage(), is2.getItemDamage());
					}
					case SHAFT: {
						MaterialRegistry m1 = MaterialRegistry.getMaterialFromShaftItem(is1);
						MaterialRegistry m2 = MaterialRegistry.getMaterialFromShaftItem(is2);
						return m1.compareTo(m2);
					}
					case ENGINE:
					case ADVGEAR:
						return Integer.compare(is1.getItemDamage(), is2.getItemDamage());
					default:
						return 0;
				}
			}
			else {
				if (r1 == null)
					return -1;
				else if (r2 == null)
					return 1;
				else
					return r1.compareTo(r2);
			}
		}
	}
}
